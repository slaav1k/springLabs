package labs.electicstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Товар
 */
@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private Category category;
    private String imageUrl;

}
