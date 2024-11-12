package labs.electicstore;

import labs.electicstore.entities.Category;
import labs.electicstore.entities.Product;
import labs.electicstore.entities.Role;
import labs.electicstore.entities.User;
import labs.electicstore.repositories.CategoryRepository;
import labs.electicstore.repositories.ProductRepository;
import labs.electicstore.repositories.RoleRepository;
import labs.electicstore.repositories.UserRepository;
import labs.electicstore.security.AdminProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitializerDB {

    private final AdminProperties adminProperties;

    @Autowired
    public InitializerDB(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }


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

    @Bean
    @Profile("!production")
    public CommandLineRunner adminUserLoader(RoleRepository roleRepo, UserRepository userRepo) {
        return args -> {
            // Создаем роли
            Role userRole = new Role(0, "ROLE_USER");
            Role adminRole = new Role(0, "ROLE_ADMIN");
            roleRepo.save(userRole);
            roleRepo.save(adminRole);


            // Создаем админа
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(userRole);
            adminRoles.add(adminRole);

            User userAdmin = new User(
                    adminProperties.getUsername(),
                    passwordEncoder.encode(adminProperties.getPassword()),
                    adminProperties.getEmail(),
                    adminProperties.getAddress(),
                    adminRoles
            );
            userRepo.save(userAdmin);


        };
    }
}
