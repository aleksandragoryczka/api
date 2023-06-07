package com.github.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryResponse {
    private String name;
    private Owner owner;
    private List<Branch> branches;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean fork;
}