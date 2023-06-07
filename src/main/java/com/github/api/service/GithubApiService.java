package com.github.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.model.Branch;
import com.github.api.model.RepositoryResponse;
import com.github.api.utilities.HttpRequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubApiService{
    @Value("${api.baseUrl}")
    private String apiBaseUrl;

    @Value("${api.reposPath}")
    private String apiReposPath;
    @Value("${api.branchesPath}")
    private String apiBranchesPath;
    private final HttpClient httpClient;

    public GithubApiService() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public List<RepositoryResponse> getRepositories(String username) throws IOException, InterruptedException {
        String url = String.format("%s%s", apiBaseUrl, String.format(apiReposPath, username));

        List<RepositoryResponse> repositories = HttpRequestHandler
                .httpRequestHandle(url, httpClient, username, RepositoryResponse.class);

        for(RepositoryResponse repository: repositories){
            repository.setBranches(getBranches(username, repository.getName()));
        }
        return repositories.stream().filter(x->!x.isFork()).collect(Collectors.toList());
    }

    public List<Branch> getBranches(String username, String repositoryName) throws IOException, InterruptedException {
        String url = String.format("%s%s", apiBaseUrl, String.format(apiBranchesPath, username, repositoryName));

        List<Branch> branches = HttpRequestHandler.httpRequestHandle(url, httpClient, username, Branch.class);

        return branches;
    }
}