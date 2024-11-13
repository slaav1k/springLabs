package labs.electicstoreadmin;

public interface CategoryService {

  Iterable<Category> findAll();
  
  Category addCategory(Category category);

}
