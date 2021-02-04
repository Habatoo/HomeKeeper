package com.homekeeper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homekeeper.models.User;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestUtil {

    public static String objectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static MockHttpServletRequestBuilder putJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return put(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static RequestPostProcessor authentication() {
        return request -> {
            request.addHeader("Authorization", getBasicAuthHeader("admin", "12345"));
            return request;
        };
    }

    public static String getBasicAuthHeader(String username, String password) {
        return "Basic auth";
    }

    public static ResultMatcher user(String prefix, User user) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".userName").value(user.getUserName()),
                jsonPath(prefix + ".userEmail").value(user.getUserEmail())
        );
    }

    public static ResultMatcher noCacheHeader() {
        return header().string("Cache-Control", "no-cache");
    }
}
