package labs.electicstore.controllers;

import jakarta.validation.Valid;
import labs.electicstore.entities.Category;
import labs.electicstore.entities.Order;
import labs.electicstore.entities.Product;
import labs.electicstore.repositories.CategoryRepository;
import labs.electicstore.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes({"productOrder", "selectedProduct"})
public class OrderController {

    private final ProductRepository productRepo;

    public OrderController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping()
    public String showOrderForm(@RequestParam(value = "productId", required = false) Integer productId, Model model) {
        if (productId == null) {
            return "redirect:/catalog";
        }

        Product product = getProductByID(productId);
        if (product == null) {
            return "redirect:/catalog";
        }

        Order productOrder = new Order(productId);  // Использование конструктора с productId
        model.addAttribute("selectedProduct", product);
        model.addAttribute("productOrder", productOrder);
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

        log.info("Order received: {}", productOrder);


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



    @GetMapping("/confirmation")
    public String showConfirmationPage(@RequestParam(value = "productId") Integer productId,
                                       Model model) {
        Product product = getProductByID(productId); // Получаем продукт по ID
        if (product == null) {
            return "redirect:/catalog"; // Перенаправляем, если продукт не найден
        }

        // Извлекаем данные из сессии
        Order productOrder = (Order) model.getAttribute("productOrder");
        model.addAttribute("order", productOrder);
        model.addAttribute("product", product);
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
