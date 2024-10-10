package labs.electicstore;

import labs.electicstore.entities.Category;
import labs.electicstore.entities.Product;
import labs.electicstore.repositories.CategoryRepository;
import labs.electicstore.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitializerDB {

    @Bean
    public CommandLineRunner dataLoader(ProductRepository productRepo, CategoryRepository categoryRepo) {
        return args -> {
            // Создаем категории
            Category tvCategory = new Category("TV");
            Category fridgeCategory = new Category("FRIG");
            Category bakeCategory = new Category("Bake");
            Category coffeeMakersCategory = new Category("CoffeeMakers");
            categoryRepo.save(tvCategory);
            categoryRepo.save(fridgeCategory);
            categoryRepo.save(bakeCategory);
            categoryRepo.save(coffeeMakersCategory);

            // Создаем продукты
            productRepo.save(new Product("Samsung TV", "42-inch Smart TV", 500.0, tvCategory, "/images/tv1.jpg"));
            productRepo.save(new Product("LG Fridge", "Energy efficient fridge", 600.0, fridgeCategory, "/images/fridge1.jpg"));
            productRepo.save(new Product("Bosch Oven", "Multi-function oven", 300.0, bakeCategory, "/images/bake1.jpg"));
            productRepo.save(new Product("De'Longhi Coffee Maker", "Automatic coffee machine", 200.0, coffeeMakersCategory, "/images/coffeeMakers1.jpg"));
        };
    }
}
