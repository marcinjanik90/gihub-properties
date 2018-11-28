package pl.mjanik.github.repositories;

import java.util.List;

public interface RepositoriesFetcher {

    List<Repository> getReposByUsername(String username);
}
