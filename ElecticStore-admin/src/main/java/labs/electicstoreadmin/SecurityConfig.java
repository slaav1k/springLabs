package labs.electicstoreadmin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/login/oauth2/**", "/oauth2/**").permitAll()
//                        .requestMatchers("/login/oauth2/code/electicstore-admin-client").permitAll() // Разрешаем доступ к redirect-uri
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/oauth2/authorization/electicstore-admin-client"))
                .oauth2Client(withDefaults());
        return http.build();
    }

    @Bean
    @RequestScope
    public RestCategoryService ingredientService(
            OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String accessToken = null;

        if (authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =
                    oauthToken.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("electicstore-admin-client")) {
                OAuth2AuthorizedClient client =
                        clientService.loadAuthorizedClient(
                                clientRegistrationId, oauthToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }
        return new RestCategoryService(accessToken);
    }

}
