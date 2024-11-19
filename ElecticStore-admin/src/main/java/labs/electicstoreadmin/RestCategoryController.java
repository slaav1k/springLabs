package labs.electicstoreadmin;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RestController
public class RestCategoryController {

    private final RestClient restClient;
    private final OAuth2AuthorizedClientManager authorizedClientManager;
//    private RestTemplate restTemplate;

    public RestCategoryController(RestClient restClient, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restClient = restClient;
        this.authorizedClientManager = authorizedClientManager;
    }


  private RestTemplate restTemplate = new RestTemplate();

//  @GetMapping("/categories")
//  public List<Category> getCategories() {
//    // Получаем данные с внешнего API с помощью RestTemplate
//    String url = "http://localhost:8080/api/categories";
//    ResponseEntity<CategoryListResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
//            CategoryListResponse.class);
//
//    // Извлекаем и возвращаем список категорий
//    return response.getBody().getEmbedded().getCategories();
//  }


    @GetMapping("/api/categories")
    public String RestIngredientService() {
        return restClient.get()
                .uri("http://localhost:8080/api/categories")
                .attributes(clientRegistrationId("admin-client"))
                .retrieve()
                .body(String.class);
    }


  @GetMapping("/token")
  public TokenResponse fetchToken() {

    OAuth2AuthorizedClient authorizedClient = authorizedClientManager
            .authorize(OAuth2AuthorizeRequest.withClientRegistrationId("admin-client").principal("principal").build());

    if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
      String accessToken = authorizedClient.getAccessToken().getTokenValue();
      Instant accessTokenExpiresAt = authorizedClient.getAccessToken().getExpiresAt();
      String refreshToken = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : null;
      Instant refreshTokenExpiresAt = authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getExpiresAt() : null;
      String clientRegistrationId = authorizedClient.getClientRegistration().getRegistrationId();
      String tokenType = authorizedClient.getAccessToken().getTokenType().getValue();
      String clientId = authorizedClient.getClientRegistration().getClientId();
      String clientSecret = authorizedClient.getClientRegistration().getClientSecret();
      return new TokenResponse(accessToken, accessTokenExpiresAt, refreshToken, refreshTokenExpiresAt, clientRegistrationId, tokenType, clientId, clientSecret);
    } else {
      return new TokenResponse("Токен не найден", null, null, null, null, null, null, null);
    }
  }


//  @Override
//  public Iterable<Category> findAll() {
//    return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(
//            "http://localhost:8080/data-api/categories",
//            Category[].class)));
//  }
//
//  @Override
//  public Category addCategory(Category category) {
//    return restTemplate.postForObject(
//            "http://localhost:8080/data-api/categories",
//            category,
//            Category.class);
//  }



}
