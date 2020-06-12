package com.codezero.fashiop.users.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserResourceList {

    @JsonProperty("users")
    private List<UserResource> userResources;

    public List<UserResource> getUserResources() { return userResources; }

    @JsonProperty("total")
    private int totalPages;
}
