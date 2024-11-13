package labs.electicstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Товар
 */
@Data
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(min = 5, message = "Название минимум из 5 букв")
    private String name;
    private String description;

    @NotNull
    private double price;

    @ManyToOne
    private Category category;
    private String imageUrl;

    // Конструктор для создания продукта с параметрами
    public Product(String name, String description, double price, Category category, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

}
