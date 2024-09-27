package labs.electicstore.entities;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Информация о заказе
 */
@Data
public class Order {

    private int productId;

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

    @NotNull
    @Min(value = 1, message = "Количество должно быть не меньше 1")
    private Integer quantity;

    private StatusOrder status;

    public enum StatusOrder {
        ISSUED, WAIT, DONE, CANCEL
    }

    public Order(int productId) {
        this.productId = productId;
    }

    public Order() {
    }
}
