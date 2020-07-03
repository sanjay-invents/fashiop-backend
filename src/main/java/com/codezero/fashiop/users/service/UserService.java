package com.codezero.fashiop.users.service;

import com.codezero.fashiop.users.resources.UserRequest;
import com.codezero.fashiop.users.resources.UserResource;
import com.codezero.fashiop.users.resources.UserResourceList;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResourceList getAllUsers(Pageable page);

    UserResource getUserById(long userId);

    UserResource getUserByUsername(String username);

    UserResource saveUserImage(MultipartFile file, long userId);

    UserResource saveUser(UserRequest userRequest);

    UserResource updateUser(UserRequest userRequest);

    UserResource deleteUser(String username);

    long getUsersCount();
}
