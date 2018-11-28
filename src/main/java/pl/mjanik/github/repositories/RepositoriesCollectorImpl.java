package pl.mjanik.github.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RepositoriesCollectorImpl implements RepositoriesCollector {

    private final RepositoriesFetcher repositoriesFetcher;

    @Override
    public List<String> getNonForkRepositoriesByUsername(String username) {
        List<Repository> reposByUsername = repositoriesFetcher.getReposByUsername(username);

        return reposByUsername.stream().filter(r -> !r.isForks())
                .map(Repository::getName)
                .collect(Collectors.toList());
    }
}
