package pl.mjanik.github.endpoints;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.mjanik.github.config.RestTemplateFactory;

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
            result = restTemplate.exchange(endpointsUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(Strings.EMPTY, restTemplateFactory.getHeaders()),
		            new ParameterizedTypeReference<Map>() {
                    }).getBody();
        } catch (Exception e) {
            log.error("getEndpoints threw exception:", e);
            throw new EndpointsTemplateException(e);
        }
        return result;
    }
}
