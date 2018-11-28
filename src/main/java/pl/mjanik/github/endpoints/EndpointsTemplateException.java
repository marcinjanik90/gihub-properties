package pl.mjanik.github.endpoints;

import pl.mjanik.github.base.GithubPropertiesBaseException;

public class EndpointsTemplateException extends GithubPropertiesBaseException {

    EndpointsTemplateException(Exception e) {
        super(e);
    }
}
