package pl.mjanik.github.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mjanik.github.base.Constants;
import pl.mjanik.github.base.Endpoints;
import pl.mjanik.github.endpoints.EndpointsFetcher;
import pl.mjanik.github.endpoints.EndpointsRestController;
import pl.mjanik.github.repositories.RepositoriesCollector;
import pl.mjanik.github.repositories.RepositoriesRestController;
import pl.mjanik.github.repositories.RepositoriesTemplateException;
import pl.mjanik.github.repositories.RepositoriesUrlException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({ValidateRequestInterceptor.class, RepositoriesRestController.class, EndpointsRestController.class})
public class GithubPropertiesExceptionHandlerIntegrationTest {

    private static final String USERNAME = "username";

    @MockBean
    private RepositoriesCollector repositoriesCollector;

    @MockBean
    private EndpointsFetcher endpointsFetcher;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCallRepositoriesCorrectly() throws Exception {
        //given
        Mockito.when(repositoriesCollector.getNonForkRepositoriesByUsername(USERNAME)).thenReturn(Lists.newArrayList());

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.REPOSITORIES)
                .param(Constants.USERNAME_PARAM, USERNAME))
                .andExpect(status().isOk());

        Mockito.verify(repositoriesCollector).getNonForkRepositoriesByUsername(USERNAME);
    }

    @Test
    public void shouldThrowBadRequestWhenCallingRepositoriesWithoutUsernameInParams() throws Exception {
        //given

        //expect
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.REPOSITORIES))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Mockito.verifyZeroInteractions(repositoriesCollector);
        ExceptionSchema result = new ObjectMapper().readValue(contentAsString, ExceptionSchema.class);
        Assertions.assertThat(result).isEqualToComparingFieldByFieldRecursively(ExceptionSchema.getDefaultSchema());
    }

    @Test
    public void shouldThrowInternalServerError() throws Exception {
        //given
        Mockito.when(repositoriesCollector.getNonForkRepositoriesByUsername(USERNAME)).thenThrow(RepositoriesUrlException.class);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.REPOSITORIES)
                .param(Constants.USERNAME_PARAM, USERNAME))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldThrowServiceUnavailableForRepositories() throws Exception {
        //given
        Mockito.when(repositoriesCollector.getNonForkRepositoriesByUsername(USERNAME)).thenThrow(RepositoriesTemplateException.class);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.REPOSITORIES)
                .param(Constants.USERNAME_PARAM, USERNAME))
                .andExpect(status().isServiceUnavailable());
    }


    @Test
    public void shouldThrowServiceUnavailableForEndpoints() throws Exception {
        //given
        Mockito.when(endpointsFetcher.getEndpoints()).thenThrow(RepositoriesTemplateException.class);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.ENDPOINTS))
                .andExpect(status().isServiceUnavailable());
    }
}
