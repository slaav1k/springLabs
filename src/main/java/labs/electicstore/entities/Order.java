package labs.electicstore.entities;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Информация о заказе
 */
@Data
@NoArgsConstructor
@Document(collection = "\"order\"")
public class Order {

    @Id
    private String id;

    @Valid
    private Product product;

    @Valid
    private Customer customer;

    @NotNull
    @Min(value = 1, message = "Количество должно быть не меньше 1")
    private Integer quantity;

    private StatusOrder status;

    public enum StatusOrder {
        ISSUED, WAIT, DONE, CANCEL
    }


    public Order(Product product, Customer customer, Integer quantity) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.customer = customer;
        this.quantity = quantity;
        this.status = StatusOrder.WAIT;
    }
}
