package labs.electicstore.repositories;

import labs.electicstore.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
}
