package labs.electicstore.entities;

import lombok.Data;

/**
 * Информация о заказе
 */
@Data
public class Order {
    private int order_id;
    private int customer_id;
    private int product_id;
    private String date;
    private StutusOrder status;

    public enum StutusOrder {
        ISSUED, WAIT, DONE, CANCEL
    }

}
