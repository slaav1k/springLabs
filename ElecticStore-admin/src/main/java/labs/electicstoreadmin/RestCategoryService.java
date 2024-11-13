package labs.electicstoreadmin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

public class RestCategoryService implements CategoryService {

  private RestTemplate restTemplate;

  /*
  public RestIngredientService() {
   */

  public void RestIngredientService(String accessToken) {
    this.restTemplate = new RestTemplate();
    if (accessToken != null) {
      this.restTemplate
          .getInterceptors()
          .add(getBearerTokenInterceptor(accessToken));
    }
  }

  public RestCategoryService(String accessToken) {
  }

  @Override
  public Iterable<Category> findAll() {
    return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(
            "http://localhost:8080/data-api/categories",
            Category[].class)));
  }

  @Override
  public Category addCategory(Category category) {
    return restTemplate.postForObject(
            "http://localhost:8080/data-api/categories",
            category,
            Category.class);
  }


  private ClientHttpRequestInterceptor
            getBearerTokenInterceptor(String accessToken) {
    ClientHttpRequestInterceptor interceptor =
          new ClientHttpRequestInterceptor() {
      @Override
      public ClientHttpResponse intercept(
            HttpRequest request, byte[] bytes,
            ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Authorization", "Bearer " + accessToken);
        return execution.execute(request, bytes);
      }
    };

    return interceptor;
  }

}
