package net.lecigne.coronamailsender.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyMatchers {

    private final ObjectMapper objectMapper = new ObjectMapper();

    static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }

    public <T> ResultMatcher containsObjectAsJson(Object expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(expectedObject).isEqualToComparingFieldByField(actualObject);
        };
    }

}
