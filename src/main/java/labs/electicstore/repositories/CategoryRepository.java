package labs.electicstore.repositories;

import labs.electicstore.entities.Category;
import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, String> {
}
