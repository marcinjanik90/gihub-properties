package pl.mjanik.github.config;


import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ExceptionSchema {
    private String title;
    private String type;
    private Map<String, TypedField> properties;
    private List<String> required;

    static ExceptionSchema getDefaultSchema() {
        return ExceptionSchema.builder()
                .title("Error")
                .type("object")
                .properties(ImmutableMap.of("errorCode", new TypedField("string"),
                        "errorMessage", new TypedField("string")))
                .required(Arrays.asList("errorCode", "errorMessage"))
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TypedField {
        private String type;
    }
}