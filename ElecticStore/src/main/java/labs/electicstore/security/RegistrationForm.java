package labs.electicstore.security;

import jakarta.validation.constraints.*;
import labs.electicstore.entities.Role;
import labs.electicstore.entities.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Data
public class RegistrationForm {
    @NotBlank(message = "Имя клиента не должно быть пустым")
    @Size(min = 5, message = "Имя клиента должно содержать не менее 5 букв")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$", message = "Имя клиента должно содержать только буквы")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 4, message = "Длина пароля не менее 4 букв")
    private String password;

    @NotBlank(message = "Адрес эл. почты не должен быть пустым")
    @Email(message = "Неверный формат адреса эл. почты")
    private String email;

    @NotBlank(message = "Адрес доставки не должен быть пустым")
    @Size(min = 10, message = "Адрес доставки должен содержать не менее 10 символов")
    private String address;

    @NotBlank(message = "Подтверждение пароля не должно быть пустым")
    private String confirm;

    @AssertTrue(message = "Пароль и подтверждение пароля должны совпадать")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirm);
    }

    public User toUser(PasswordEncoder passwordEncoder, Role userRole) {
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        return new User(
                username,
                passwordEncoder.encode(password),
                email,
                address,
                roles
        );
    }
}
