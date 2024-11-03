package labs.electicstore.controllers;


import jakarta.validation.Valid;
import labs.electicstore.repositories.UserRepository;
import labs.electicstore.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }


    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "registration"; // Возвращаем на страницу регистрации с ошибками
        }
        userRepository.save(
                form.toUser(passwordEncoder)
        );
        return "redirect:/login";
    }

}
