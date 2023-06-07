package com.github.api.controller;

import com.github.api.model.ErrorResponse;
import com.github.api.model.RepositoryResponse;
import com.github.api.service.GithubApiService;
import com.github.api.utilities.XmlConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GithubApiController{
    private final GithubApiService githubApiService;

    public GithubApiController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @GetMapping(value="/{username}")
    public ResponseEntity<?> getRepositories(@PathVariable String username,
                                             @RequestHeader("Accept") String acceptHeader)
            throws IOException, InterruptedException {
        if (acceptHeader.equals("application/xml")) {
            ErrorResponse errorResponse = new ErrorResponse(406, "Not Acceptable Header");
            return XmlConverter.convertResponseFromXmlToJson(errorResponse);
        }
        List<RepositoryResponse> repositories = githubApiService.getRepositories(username);
        System.out.println(repositories.stream().map(x->x.getName()));
        return ResponseEntity.ok(repositories);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }
}