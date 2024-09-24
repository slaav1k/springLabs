package labs.electicstore.entities;

import lombok.Data;

/**
 * Покупатель
 */
@Data
public class Customer {
    private int id;
    private String name;
    private String address;
    private String email;
}
