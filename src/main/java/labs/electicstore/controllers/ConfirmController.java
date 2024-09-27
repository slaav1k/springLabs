package labs.electicstore.controllers;

import labs.electicstore.entities.Order;
import labs.electicstore.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes({"productOrder", "selectedProduct"})
public class ConfirmController {

    @GetMapping("/confirm")
    public String showConfirmationPage(@ModelAttribute("productOrder") Order productOrder,
                                       @ModelAttribute("selectedProduct") Product selectedProduct,
                                       Model model) {
        model.addAttribute("order", productOrder);
        model.addAttribute("product", selectedProduct);
        return "orderConfirmation"; // Здесь будет ваше представление для подтверждения
    }
}
