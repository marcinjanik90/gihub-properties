package pl.mjanik.github.repositories;

import org.apache.logging.log4j.util.Strings;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RepositoriesCollectorImplTest {

    private static final String USERNAME = "username";

    private static final String NAME_1 = "name1";

    private static final String NAME_2 = "name2";

    private List<Repository> rawRepos;

    @Mock
    private RepositoriesFetcher fetcher;

    @InjectMocks
    private RepositoriesCollectorImpl collector;

    @Test
    public void shouldFilter() {
        //given
        rawRepos = Arrays.asList(buildRepo(NAME_1, false), buildRepo(NAME_2, true));
        Mockito.when(fetcher.getReposByUsername(USERNAME)).thenReturn(rawRepos);

        //when
        List<String> nonForkRepos = collector.getNonForkRepositoriesByUsername(USERNAME);

        //then
        Assertions.assertThat(nonForkRepos).containsOnly(NAME_1);
    }

    @Test
    public void shouldFilterNone() {
        //given
        rawRepos = Arrays.asList(buildRepo(NAME_1, false), buildRepo(NAME_2, false));
        Mockito.when(fetcher.getReposByUsername(USERNAME)).thenReturn(rawRepos);

        //when
        List<String> nonForkRepos = collector.getNonForkRepositoriesByUsername(USERNAME);

        //then
        Assertions.assertThat(nonForkRepos).containsOnly(NAME_1, NAME_2);
    }

    @Test
    public void shouldFilterAll() {
        //given
        rawRepos = Arrays.asList(buildRepo(NAME_1, true), buildRepo(NAME_2, true));
        Mockito.when(fetcher.getReposByUsername(USERNAME)).thenReturn(rawRepos);

        //when
        List<String> nonForkRepos = collector.getNonForkRepositoriesByUsername(USERNAME);

        //then
        Assertions.assertThat(nonForkRepos).isEmpty();
    }

    @Test
    public void shouldWorkWithNullableAndEmptyNames() {
        //given
        rawRepos = Arrays.asList(buildRepo(null, false), buildRepo(Strings.EMPTY, false));
        Mockito.when(fetcher.getReposByUsername(USERNAME)).thenReturn(rawRepos);

        //when
        List<String> nonForkRepos = collector.getNonForkRepositoriesByUsername(USERNAME);

        //then
        Assertions.assertThat(nonForkRepos).containsOnly(null, Strings.EMPTY);
    }

    private Repository buildRepo(String name, boolean forks) {
        Repository repository = new Repository();
        repository.setForks(forks);
        repository.setName(name);
        return repository;
    }
}