package labs.electicstore.controllers;

import jakarta.validation.Valid;
import labs.electicstore.entities.Customer;
import labs.electicstore.entities.Order;
import labs.electicstore.entities.Product;
import labs.electicstore.repositories.CustomerRepository;
import labs.electicstore.repositories.OrderRepository;
import labs.electicstore.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes({"productOrder", "selectedProduct"})
public class OrderController {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;

    public OrderController(ProductRepository productRepo, OrderRepository orderRepo, CustomerRepository customerRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    @GetMapping()
    public String showOrderForm(@RequestParam(value = "productId", required = false) Integer productId, Model model) {
        if (productId == null) {
            return "redirect:/catalog";
        }

//        Product product = getProductByID(productId);
        Product product = productRepo.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/catalog";
        }

        Order productOrder = new Order();
        productOrder.setProduct(product);
        Customer customer = new Customer();
        model.addAttribute("selectedProduct", product);
        model.addAttribute("productOrder", productOrder);
        model.addAttribute("customer", customer);
        log.info("showOrderForm Order details: {}", productOrder);
        log.info("showOrderForm Product details: {}", product);

        return "order";
    }


//    @PostMapping
//    public String processOrder(
//            @Valid @ModelAttribute("productOrder") Order productOrder,
//            BindingResult result,
//            @ModelAttribute("selectedProduct") Product selectedProduct,
//            Model model) {
//
//        if (result.hasErrors()) {
//            return "order";
//        }
//
//        log.info("Order received: {}", productOrder);
//
//
//        model.addAttribute("productOrder", productOrder);
//        model.addAttribute("selectedProduct", selectedProduct);
//
//        return "redirect:/orders/confirm";
//    }

    @PostMapping
    public String processOrder(
            @Valid @ModelAttribute("productOrder") Order productOrder,
            BindingResult result,
//            @ModelAttribute("customer") Customer customer,
            @ModelAttribute("selectedProduct") Product selectedProduct,
            Model model) {

        if (result.hasErrors()) {
            return "order";
        }

        customerRepo.save(productOrder.getCustomer());
//
//        productOrder.setCustomer(customer);


        orderRepo.save(productOrder);

        log.info("Order received: {}", productOrder);
//        log.info("Customer created: {}", customer);

        model.addAttribute("productOrder", productOrder);
        model.addAttribute("selectedProduct", selectedProduct);

        return "redirect:/orders/confirm";
    }



//    @GetMapping("/confirmation")
//    public String showConfirmationPage(@ModelAttribute("productOrder") Order productOrder,
//                                       @ModelAttribute("selectedProduct") Product selectedProduct,
//                                       Model model) {
//        model.addAttribute("order", productOrder);
//        model.addAttribute("product", selectedProduct);
//        return "orderConfirmation";
//    }

//    @GetMapping("/confirmation")
//    public String showConfirmationPage() {
//        return "orderConfirmation";
//    }



//    @GetMapping("/confirmation")
//    public String showConfirmationPage(@RequestParam(value = "productId") Integer productId,
//                                       Model model) {
////        Product product = getProductByID(productId);
//        Product product = productRepo.findById(productId).orElse(null);
//        if (product == null) {
//            return "redirect:/catalog"; // Перенаправляем, если продукт не найден
//        }
//
//        // Извлекаем данные из сессии
//        Order productOrder = (Order) model.getAttribute("productOrder");
//        model.addAttribute("order", productOrder);
//        model.addAttribute("product", product);
//        return "orderConfirmation";
//    }


    @GetMapping("/confirmation")
    public String showConfirmationPage(Model model) {
        Order productOrder = (Order) model.getAttribute("productOrder");
        Product selectedProduct = (Product) model.getAttribute("selectedProduct");

        model.addAttribute("order", productOrder);
        model.addAttribute("product", selectedProduct);

        return "orderConfirmation";
    }


    @ModelAttribute(name = "product")
    private Product getProductByID(int productId) {
//        List<Product> allProducts = Arrays.asList(
//                new Product(1, "Samsung TV", "42-inch Smart TV", 500.0, new Category(1, "TV"), "/images/tv1.jpg"),
//                new Product(2, "LG Fridge", "Energy efficient fridge", 600.0, new Category(2, "FRIG"), "/images/fridge1.jpg"),
//                new Product(3, "Bosch Oven", "Multi-function oven", 300.0, new Category(3, "Bake"), "/images/bake1.jpg"),
//                new Product(4, "De'Longhi Coffee Maker", "Automatic coffee machine", 200.0, new Category(4, "CoffeeMakers"), "/images/coffeeMakers1.jpg")
//        );
        List<Product> allProducts = productRepo.findAll();

        return allProducts.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElse(null);
//        return null;
    }
}
