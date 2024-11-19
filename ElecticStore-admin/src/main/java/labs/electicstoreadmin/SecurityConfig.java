package labs.electicstoreadmin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.context.annotation.RequestScope;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(passwordEncoder().encode("1234"))
                        .roles("USER")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123"))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    @RequestScope
//    public RestCategoryService ingredientService(
//            OAuth2AuthorizedClientService clientService) {
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        String accessToken = null;
//
//        if (authentication.getClass()
//                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
//            OAuth2AuthenticationToken oauthToken =
//                    (OAuth2AuthenticationToken) authentication;
//            String clientRegistrationId =
//                    oauthToken.getAuthorizedClientRegistrationId();
//            if (clientRegistrationId.equals("electicstore-admin-client")) {
//                OAuth2AuthorizedClient client =
//                        clientService.loadAuthorizedClient(
//                                clientRegistrationId, oauthToken.getName());
//                accessToken = client.getAccessToken().getTokenValue();
//            }
//        }
//        return new RestCategoryService(accessToken);
//    }

}
