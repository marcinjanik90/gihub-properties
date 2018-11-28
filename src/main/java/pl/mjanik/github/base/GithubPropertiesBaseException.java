package pl.mjanik.github.base;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GithubPropertiesBaseException extends RuntimeException {

    public GithubPropertiesBaseException(Exception e) {
        super(e);
    }

}
