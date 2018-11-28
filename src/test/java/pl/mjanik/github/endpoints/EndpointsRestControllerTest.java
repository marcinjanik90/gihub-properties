package pl.mjanik.github.endpoints;

import com.google.common.collect.Maps;
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
import pl.mjanik.github.base.Endpoints;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class EndpointsRestControllerTest {

    @Mock
    private EndpointsFetcher endpointsFetcher;

    @InjectMocks
    private EndpointsRestController controller;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void shouldCallEndpoints() throws Exception {
        //given
        Mockito.when(endpointsFetcher.getEndpoints()).thenReturn(Maps.newHashMap());

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.ENDPOINTS))
                .andExpect(status().isOk());
        Mockito.verify(endpointsFetcher).getEndpoints();
    }
}
