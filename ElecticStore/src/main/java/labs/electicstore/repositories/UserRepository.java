package labs.electicstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import labs.electicstore.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
