package pl.mjanik.github.repositories;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Repository {
    private boolean forks;
    private String name;
}