package labs.electicstore.controllers;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import labs.electicstore.entities.Customer;
import labs.electicstore.entities.Order;
import labs.electicstore.entities.Product;
import labs.electicstore.entities.User;
import labs.electicstore.repositories.CustomerRepository;
import labs.electicstore.repositories.OrderRepository;
import labs.electicstore.repositories.ProductRepository;
import labs.electicstore.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes({"productOrder", "selectedProduct"})
public class OrderController {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final UserRepository userRepo;

    public OrderController(ProductRepository productRepo, OrderRepository orderRepo, CustomerRepository customerRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
    }

    @GetMapping()
    public String showOrderForm(@RequestParam(value = "productId", required = false) Integer productId,
                                Model model,
                                @AuthenticationPrincipal UserDetails userDetails ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

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

        String username = userDetails.getUsername();
        Optional<User> optionalUser = Optional.ofNullable(userRepo.findByUsername(username)); // Замените userRepo на ваш репозиторий

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            customer.setCustomerName(user.getUsername());
            customer.setEmail(user.getEmail());
            customer.setAddress(user.getAddress());
        } else {
            // Обработка случая, когда пользователь не найден в базе
            return "redirect:/login";
        }
        productOrder.setCustomer(customer);
        model.addAttribute("selectedProduct", product);
        model.addAttribute("productOrder", productOrder);
        model.addAttribute("customer", customer);

        log.info("showOrderForm Order details: {}", productOrder);
        log.info("showOrderForm Product details: {}", product);

        return "order";
    }



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

//        customerRepo.save(productOrder.getCustomer());

        Optional<Customer> existingCustomer = customerRepo.findByEmail(productOrder.getCustomer().getEmail());

        if (existingCustomer.isPresent()) {
            productOrder.setCustomer(existingCustomer.get());
        } else {
            customerRepo.save(productOrder.getCustomer());
        }

//
//        productOrder.setCustomer(customer);

        productOrder.setStatus(Order.StatusOrder.WAIT);
        orderRepo.save(productOrder);

        log.info("Order received: {}", productOrder);
//        log.info("Customer created: {}", customer);

        model.addAttribute("productOrder", productOrder);
        model.addAttribute("selectedProduct", selectedProduct);

        return "redirect:/orders/confirm";
    }



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

        List<Product> allProducts = productRepo.findAll();

        return allProducts.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElse(null);
    }



}
