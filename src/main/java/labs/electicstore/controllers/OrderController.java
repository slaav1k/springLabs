package labs.electicstore.controllers;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import labs.electicstore.entities.Customer;
import labs.electicstore.entities.Order;
import labs.electicstore.entities.Product;
import labs.electicstore.repositories.CustomerRepository;
import labs.electicstore.repositories.OrderRepository;
import labs.electicstore.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

        Product product = productRepo.findById(String.valueOf(productId)).orElse(null);
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


    @PostMapping
    public String processOrder(
            @Valid @ModelAttribute("productOrder") Order productOrder,
            BindingResult result,
            @ModelAttribute("selectedProduct") Product selectedProduct,
            Model model) {

        if (result.hasErrors()) {
            return "order";
        }


        Optional<Customer> existingCustomer = customerRepo.findByEmail(productOrder.getCustomer().getEmail());

        if (existingCustomer.isPresent()) {
            productOrder.setCustomer(existingCustomer.get());
        } else {
            customerRepo.save(productOrder.getCustomer());
        }


        productOrder.setStatus(Order.StatusOrder.WAIT);
        orderRepo.save(productOrder);

        log.info("Order received: {}", productOrder);

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





}
