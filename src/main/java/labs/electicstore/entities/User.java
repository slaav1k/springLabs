package labs.electicstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
//@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Имя клиента не должно быть пустым")
    @Size(min = 5, message = "Имя клиента должно содержать не менее 5 букв")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$", message = "Имя клиента должно содержать только буквы")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 4, message = "Длина пароля не менее 4 букв")
    private String password;

    @NotBlank(message = "Адрес эл. почты не должен быть пустым")
    @Size(min = 10, message = "Адрес эл. почты должен содержать не менее 10 символов")
    @Pattern(regexp = "^[\\w\\d._%+-]+@[\\w\\d.-]+\\.[a-zA-Z]{2,6}$", message = "Неверный формат адреса эл. почты")
    private String email;

    @NotBlank(message = "Адрес доставки не должен быть пустым")
    @Size(min = 10, message = "Адрес доставки должен содержать не менее 10 символов")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public User(String username, String password, String email, String address, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.roles = roles;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
