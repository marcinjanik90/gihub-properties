package pl.mjanik.github.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.mjanik.github.config.RestTemplateFactory;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
class EndpointsFetcherImpl implements EndpointsFetcher {

    @Value("${url.endpoints}")
    private String endpointsUrl;

    private final RestTemplateFactory restTemplateFactory;

    @Override
    public Map getEndpoints() {
        RestTemplate restTemplate = restTemplateFactory.getRestTemplate();
        Map result;
        try {
            result = restTemplate.getForObject(endpointsUrl, Map.class);
        } catch (Exception e) {
            log.error("getEndpoints threw exception={}", e);
            throw new EndpointsTemplateException(e);
        }
        return result;
    }
}
