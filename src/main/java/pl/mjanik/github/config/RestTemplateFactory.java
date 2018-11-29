package pl.mjanik.github.config;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {

    RestTemplate getRestTemplate();

    HttpHeaders getHeaders();
}
