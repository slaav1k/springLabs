package labs.electicstore.repositories;

import labs.electicstore.entities.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, String> {
}
