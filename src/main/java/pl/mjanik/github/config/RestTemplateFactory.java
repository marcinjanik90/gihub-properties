package pl.mjanik.github.config;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {
    RestTemplate getRestTemplate();
}
