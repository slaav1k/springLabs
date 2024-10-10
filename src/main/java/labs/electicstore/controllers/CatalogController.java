package labs.electicstore.controllers;

import labs.electicstore.entities.Category;
import labs.electicstore.entities.Product;
import labs.electicstore.repositories.CategoryRepository;
import labs.electicstore.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catalog")
@SessionAttributes("productOrder")
public class CatalogController {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public CatalogController(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping()
    public String catalog(@RequestParam(required = false) String category, Model model) {
        List<Product> products = getProductsByCategory(category);
        List<Category> categories = getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);

        return "catalog";
    }

    @ModelAttribute(name = "categories")
    private List<Category> getAllCategories() {
//        List<Category> categories = Arrays.asList(
//                new Category(1, "TV"),
//                new Category(2, "FRIG"),
//                new Category(3, "Bake"),
//                new Category(4, "CoffeeMakers")
//                );
//        return categories;
        return categoryRepo.findAll();
    }

    private List<Product> getProductsByCategory(String categoryName) {
//        List<Product> allProducts = Arrays.asList(
//                new Product(1, "Samsung TV", "42-inch Smart TV", 500.0, new Category(1, "TV"), "/images/tv1.jpg"),
//                new Product(2, "LG Fridge", "Energy efficient fridge", 600.0, new Category(2, "FRIG"), "/images/fridge1.jpg"),
//                new Product(3, "Bosch Oven", "Multi-function oven", 300.0, new Category(3, "Bake"), "/images/bake1.jpg"),
//                new Product(4, "De'Longhi Coffee Maker", "Automatic coffee machine", 200.0, new Category(4, "CoffeeMakers"), "/images/coffeeMakers1.jpg")
//        );

        List<Product> allProducts = productRepo.findAll();

        if (categoryName == null || categoryName.isEmpty()) {
            return allProducts;
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getCategory().getName().equalsIgnoreCase(categoryName)) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }


}
