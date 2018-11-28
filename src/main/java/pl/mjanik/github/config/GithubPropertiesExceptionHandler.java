package pl.mjanik.github.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.mjanik.github.endpoints.EndpointsTemplateException;
import pl.mjanik.github.repositories.RepositoriesTemplateException;
import pl.mjanik.github.repositories.RepositoriesUrlException;

@RestControllerAdvice
class GithubPropertiesExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionSchema handleBadRequest() {
        return ExceptionSchema.getDefaultSchema();
    }

    @ExceptionHandler({EndpointsTemplateException.class, RepositoriesTemplateException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    ExceptionMessage handleRestTemplateExceptions() {
        return new ExceptionMessage("Github not working correctly");
    }


    @ExceptionHandler(RepositoriesUrlException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionMessage handleInvalidRepositoriesUrl() {
        return new ExceptionMessage("Internal server error: inner configuration issue");
    }

    @Data
    @AllArgsConstructor
    private class ExceptionMessage {
        private String message;
    }
}
