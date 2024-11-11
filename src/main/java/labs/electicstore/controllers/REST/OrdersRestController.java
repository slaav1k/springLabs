package labs.electicstore.controllers.REST;

import labs.electicstore.entities.Category;
import labs.electicstore.entities.Customer;
import labs.electicstore.entities.Order;
import labs.electicstore.entities.Product;
import labs.electicstore.repositories.CategoryRepository;
import labs.electicstore.repositories.CustomerRepository;
import labs.electicstore.repositories.OrderRepository;
import labs.electicstore.repositories.ProductRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

 /* http://localhost:8080/swagger-ui/index.html#/ */
/* http://localhost:8080/data-api */


@RestController
@RequestMapping(path="/api",
                produces = "application/json")
@CrossOrigin()
public class OrdersRestController {
    private OrderRepository orderRepository;
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private RestTemplate restTemplate  = new RestTemplate();

    public OrdersRestController(OrderRepository orderRepository, CategoryRepository categoryRepository,
                                CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/orders")
    public Iterable<Order> getOrders() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return orderRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
//        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "/category", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Category postCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PostMapping(path = "/order", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        Customer customer = order.getCustomer();
        if (customer != null) {
            Customer finalCustomer = customer;
            customer = customerRepository.findById(customer.getId())
                    .orElseGet(() -> {
                        Customer newCustomer = new Customer(finalCustomer.getCustomerName(), finalCustomer.getEmail(), finalCustomer.getAddress());
                        return customerRepository.save(newCustomer);
                    });
            // Обновляем Customer в заказе
            order.setCustomer(customer);
        }
        return orderRepository.save(order);
    }

    @PutMapping(path = "/order/{id}", consumes = "application/json")
    public Order putOrder(@PathVariable("id") Integer id,
                          @RequestBody Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/order/{id}", consumes = "application/json")
    public ResponseEntity<Order> patchOrder(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        // Проверяем, существует ли заказ с данным ID
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }

        // Обновление полей в заказе
        updates.forEach((key, value) -> {
            switch (key) {
                case "product":
                    Map<String, Object> productData = (Map<String, Object>) value;
                    Long productId = ((Number) productData.get("id")).longValue();
                    Product product = productRepository.findById(Math.toIntExact(productId)).orElse(null);
                    if (product != null) {
                        existingOrder.setProduct(product);
                    }
                    break;

                case "customer":
                    Map<String, Object> customerData = (Map<String, Object>) value;
                    Long customerId = ((Number) customerData.get("id")).longValue();
                    Customer customer = customerRepository.findById(Math.toIntExact(customerId)).orElse(null);
                    if (customer == null) {
                        customer = new Customer();
                        customer.setCustomerName((String) customerData.get("customerName"));
                        customer.setEmail((String) customerData.get("email"));
                        customer.setAddress((String) customerData.get("address"));
                        customer = customerRepository.save(customer);
                    }
                    existingOrder.setCustomer(customer);
                    break;

                case "quantity":
                    existingOrder.setQuantity((Integer) value);
                    break;

                case "status":
                    existingOrder.setStatus(Order.StatusOrder.valueOf((String) value));
                    break;

                default:
                    throw new IllegalArgumentException("Неподдерживаемое поле: " + key);
            }
        });

        // Сохранение обновленного заказа
        orderRepository.save(existingOrder);

        return ResponseEntity.ok(existingOrder);
    }

    @DeleteMapping("/order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") Integer id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }


    // По факту обертка, если используем стронний апи, или микросервиса, упрощает обработку ошибок
    // restTemplate.setErrorHandler(new CustomErrorHandler());
    @GetMapping("/rt2/orders")
    public List<Order> getListOrders() {
        // Отправляем GET запрос для получения всех заказов на новом пути
        return restTemplate.exchange("http://localhost:8080/api/orders",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Order>>() {}).getBody();
    }


}
