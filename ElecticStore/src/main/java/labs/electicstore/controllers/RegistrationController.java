package labs.electicstore.controllers;


import jakarta.validation.Valid;
import labs.electicstore.entities.Role;
import labs.electicstore.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }


    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, BindingResult result) {
        if (userRepository.findByUsername(form.getUsername()) != null) {
            result.rejectValue("username", "error.username", "Пользователь с таким именем уже существует.");
        }

        if (userRepository.findByEmail(form.getEmail()) != null) {
            result.rejectValue("email", "error.email", "Пользователь с такой почтой уже существует.");
        }

        if (!form.isPasswordConfirmed()) {
            result.rejectValue("confirm", "error.confirm", "Пароли не совпадают.");
        }

        if (result.hasErrors()) {
            return "registration"; // Возвращаем на страницу регистрации с ошибками
        }

        Role userRole = roleRepository.findByName("ROLE_USER");

        userRepository.save(
                form.toUser(passwordEncoder, userRole)
        );
        return "redirect:/login";
    }

}
