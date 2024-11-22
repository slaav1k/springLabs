package servs.authserver;



import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


//@EntityScan("labs.electicstore.entities")
//@EnableJpaRepositories("labs.electicstore.repositories")
@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

//	@Bean
//	public ApplicationRunner dataLoader(
//			UserRepository repo, RoleRepository roleRepository, PasswordEncoder encoder) {
//		Role userRole = new Role(0, "ROLE_USER");
//		Role adminRole = new Role(0, "ROLE_ADMIN");
//		roleRepository.save(userRole);
//		roleRepository.save(adminRole);
//
//		Set<Role> adminRoles = new HashSet<>();
//		adminRoles.add(userRole);
//		adminRoles.add(adminRole);
//
//		return args -> {
//			repo.save(
//					new User("habuma", encoder.encode("password"), "email@ya.ru", "wefwefqwfqwfqwfwfwqfqw", adminRoles));
//			repo.save(
//					new User("tacochef", encoder.encode("password"), "email2@ya.ru", "wefwefqwfqwfqwfwfwqfqw", adminRoles));
//		};
//	}
}
