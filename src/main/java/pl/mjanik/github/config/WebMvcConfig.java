package pl.mjanik.github.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.mjanik.github.base.Endpoints;

@Configuration
@RequiredArgsConstructor
class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateRequestInterceptor).addPathPatterns(Endpoints.REPOSITORIES);
    }

    private final ValidateRequestInterceptor validateRequestInterceptor;
}
