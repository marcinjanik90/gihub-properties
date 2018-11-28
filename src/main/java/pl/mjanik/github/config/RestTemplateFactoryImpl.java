package pl.mjanik.github.config;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class RestTemplateFactoryImpl implements RestTemplateFactory {

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
