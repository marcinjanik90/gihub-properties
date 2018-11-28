package pl.mjanik.github.repositories;

import java.util.List;

public interface RepositoriesCollector {

    List<String> getNonForkRepositoriesByUsername(String username);
}
