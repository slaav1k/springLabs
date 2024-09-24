package labs.electicstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Категория товара
 */
@Data
@AllArgsConstructor
public class Category {
    private int id;
    private String name;
}
