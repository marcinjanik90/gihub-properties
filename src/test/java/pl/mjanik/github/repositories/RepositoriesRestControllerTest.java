package pl.mjanik.github.repositories;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.mjanik.github.base.Constants;
import pl.mjanik.github.base.Endpoints;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class RepositoriesRestControllerTest {

    private static final String USERNAME = "username";

    @Mock
    private RepositoriesCollector repositoriesCollector;

    @InjectMocks
    private RepositoriesRestController controller;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void shouldCallRepositories() throws Exception {
        //given
        Mockito.when(repositoriesCollector.getNonForkRepositoriesByUsername(USERNAME)).thenReturn(Lists.newArrayList());

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.REPOSITORIES)
                .param(Constants.USERNAME_PARAM, USERNAME))
                .andExpect(status().isOk());

        Mockito.verify(repositoriesCollector).getNonForkRepositoriesByUsername(USERNAME);
    }
}