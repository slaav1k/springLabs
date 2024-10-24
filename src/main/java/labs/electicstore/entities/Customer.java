package labs.electicstore.entities;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


/**
 * Покупатель
 */
@Data
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    @NotBlank(message = "Имя клиента не должно быть пустым")
    @Size(min = 5, message = "Имя клиента должно содержать не менее 5 букв")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$", message = "Имя клиента должно содержать только буквы")
    private String customerName;

    @NotBlank(message = "Адрес эл. почты не должен быть пустым")
    @Size(min = 10, message = "Адрес эл. почты должен содержать не менее 10 символов")
    @Pattern(regexp = "^[\\w\\d._%+-]+@[\\w\\d.-]+\\.[a-zA-Z]{2,6}$", message = "Неверный формат адреса эл. почты")
    private String email;

    @NotBlank(message = "Адрес доставки не должен быть пустым")
    @Size(min = 10, message = "Адрес доставки должен содержать не менее 10 символов")
    private String address;

    public Customer(String customerName, String email, String address) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.email = email;
        this.address = address;
    }

}
