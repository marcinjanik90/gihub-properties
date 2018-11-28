package pl.mjanik.github.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mjanik.github.base.Constants;
import pl.mjanik.github.base.Endpoints;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.REPOSITORIES)
public final class RepositoriesRestController {

    private final RepositoriesCollector repositoriesCollector;

    @GetMapping
    List<String> getReposForUser(@RequestParam(Constants.USERNAME_PARAM) String username) {
        log.info("> getReposForUser");

        List<String> reposByUsername = repositoriesCollector.getNonForkRepositoriesByUsername(username);

        log.info("< getReposForUser, reposByUsername={}", reposByUsername);
        return reposByUsername;
    }
}
