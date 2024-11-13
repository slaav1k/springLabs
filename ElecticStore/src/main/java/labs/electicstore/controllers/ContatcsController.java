package labs.electicstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class ContatcsController {

    @GetMapping("/contacts")
    public String contactsForm() {
        return "contact";
    }
}
