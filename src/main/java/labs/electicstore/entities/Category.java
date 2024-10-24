package labs.electicstore.entities;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Категория товара
 */
@Data
@AllArgsConstructor
@NoArgsConstructor()
@Document(collection = "category")
public class Category {
    @Id
    private String id;
    private String name;

    // Конструктор для создания категории с именем
    public Category(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
