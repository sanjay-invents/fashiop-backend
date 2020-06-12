package com.codezero.fashiop.users.service.impl;

import com.codezero.fashiop.shared.util.FileUploaderUtil;
import com.codezero.fashiop.users.dao.UserDao;
import com.codezero.fashiop.users.entity.User;
import com.codezero.fashiop.users.exception.UserException;
import com.codezero.fashiop.users.resources.UserRequest;
import com.codezero.fashiop.users.resources.UserResource;
import com.codezero.fashiop.users.resources.UserResourceList;
import com.codezero.fashiop.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set getAuthority(User user) {
        Set authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public UserResourceList getAllUsers(Pageable page) {
        Page<User> users = userDao.findAll(page);
        return convertToUserResources(users);
    }

    @Override
    public UserResource getUserById(long userId) {
        User user = userDao.findById(userId)
                .orElseThrow((() -> new UserException(HttpStatus.CONFLICT.value(), "User not found.")));
        return convertToUserResource(user);
    }

    @Override
    public void saveUser(UserRequest userRequest) {
        userDao.save(convertUserRequestToUser(userRequest));
    }

    @Override
    public long getUsersCount() {
        return userDao.count();
    }

    @Override
    public UserResource getUserByUsername(String username) {
        User user = userDao.findByUsername(username);
        return convertToUserResource(user);
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setGender(userRequest.getGender());
        user.setJoinedDate(userRequest.getJoinedDate());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setJoinedDate(userRequest.getJoinedDate());

        user.setProfilePicture(FileUploaderUtil.uploadImage("users/profileimage/",
                userRequest.getProfilePicture(),
                createFullName(userRequest.getFirstName(), userRequest.getMiddleName(), userRequest.getLastName())));
        return user;
    }

    private String createFullName(String firstName, String middleName, String lastName){
        if(middleName != null){
            middleName = " " + middleName;
        }
        return (firstName + middleName  + " " +  lastName);
    }

    private UserResourceList convertToUserResources(Page<User> users) {
        UserResourceList userResourceList = new UserResourceList();
        userResourceList.setTotalPages(users.getTotalPages());
        userResourceList.setUserResources(convertToUserResourceList(users.getContent()));
        return userResourceList;
    }

    private List<UserResource> convertToUserResourceList(List<User> users){
        List<UserResource> userResources = new ArrayList<>();
        users.forEach(user -> userResources.add(
                convertToUserResource(user)
        ));
        return userResources;
    }

    private UserResource convertToUserResource(User user){
        return UserResource
                .builder()
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .gender(user.getGender())
                .joinedDate(user.getJoinedDate())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .profilePicture(user.getProfilePicture())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

}
