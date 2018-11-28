package pl.mjanik.github.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mjanik.github.base.Endpoints;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.ENDPOINTS)
public final class EndpointsRestController {

    private final EndpointsFetcher endpointsFetcher;

    @GetMapping
    Map getEndpoints() {
        log.info("> getEndpoints");

        Map endpoints = endpointsFetcher.getEndpoints();

        log.info("< getEndpoints, endpoints={}", endpoints);
        return endpoints;
    }
}
