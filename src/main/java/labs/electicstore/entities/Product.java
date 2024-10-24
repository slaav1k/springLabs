package labs.electicstore.entities;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


/**
 * Товар
 */
@Data
@NoArgsConstructor
@Document(collection = "product")
public class Product {
    @Id
    private String id;

    @NotNull
    @Size(min = 5, message = "Название минимум из 5 букв")
    private String name;
    private String description;

    @NotNull
    private double price;

    private Category category;
    private String imageUrl;

    // Конструктор для создания продукта с параметрами
    public Product(String name, String description, double price, Category category, String imageUrl) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

}
