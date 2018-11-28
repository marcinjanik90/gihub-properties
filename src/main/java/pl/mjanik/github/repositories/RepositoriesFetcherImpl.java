package pl.mjanik.github.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.mjanik.github.config.RestTemplateFactory;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class RepositoriesFetcherImpl implements RepositoriesFetcher {

    private static final String USERNAME_FIELD = "{username}";

    @Value("${url.repositories}")
    private String repositoriesUrl;

    private final RestTemplateFactory restTemplateFactory;

    @Override
    public List<Repository> getReposByUsername(String username) {
        RestTemplate restTemplate = restTemplateFactory.getRestTemplate();
        List<Repository> result;
        String urlForUsername = addUsernameToUrl(username);
        try {
            result = restTemplate.exchange(urlForUsername,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Repository>>() {
                            })
                            .getBody();
        } catch (Exception e) {
            log.error("getReposByUsername threw exception={}", e);
            throw new RepositoriesTemplateException(e);
        }
        return result;
    }

    private String addUsernameToUrl(String username) {
        validateUrl();
        return repositoriesUrl.replace(USERNAME_FIELD, username);
    }

    private void validateUrl() {
        if (!repositoriesUrl.contains(USERNAME_FIELD)) {
            log.error("There is no placeholder \"{username}\" in repositoriesUrl={}", repositoriesUrl);
            throw new RepositoriesUrlException();
        }
    }
}
