package com.codezero.fashiop.users.service;

import com.codezero.fashiop.users.resources.UserRequest;
import com.codezero.fashiop.users.resources.UserResource;
import com.codezero.fashiop.users.resources.UserResourceList;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResourceList getAllUsers(Pageable page);

    UserResource getUserById(long userId);

    UserResource getUserByUsername(String username);

    void saveUser(UserRequest userRequest);

    long getUsersCount();
}
