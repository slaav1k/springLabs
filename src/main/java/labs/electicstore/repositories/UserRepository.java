package labs.electicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import labs.electicstore.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
