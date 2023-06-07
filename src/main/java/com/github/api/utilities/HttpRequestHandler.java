package com.github.api.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.model.ErrorResponse;
import com.github.api.model.RepositoryResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpRequestHandler {
    public static <T> List<T> httpRequestHandle(String url, HttpClient httpClient, String username, Class <T> responseType)
            throws IOException, InterruptedException {

        HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int responseStatusCode = response.statusCode();
        if(responseStatusCode == HttpStatus.NOT_FOUND.value()){
            throw new RuntimeException("User not found: " + username);
        }else if(responseStatusCode != HttpStatus.OK.value()){
            throw new RuntimeException("Failed to fetch repositories with error: " + responseStatusCode);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<T> returnedResponse = objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
        return returnedResponse;
    }
}
