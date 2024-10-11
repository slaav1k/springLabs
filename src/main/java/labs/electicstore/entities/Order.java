package labs.electicstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о заказе
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Min(value = 1, message = "Количество должно быть не меньше 1")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    public enum StatusOrder {
        ISSUED, WAIT, DONE, CANCEL
    }


    public Order(Product product, Customer customer, Integer quantity) {
        this.product = product;
        this.customer = customer;
        this.quantity = quantity;
        this.status = StatusOrder.WAIT;
    }
}
