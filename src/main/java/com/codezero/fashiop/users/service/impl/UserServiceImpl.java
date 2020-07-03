package com.codezero.fashiop.users.service.impl;

import com.codezero.fashiop.shared.util.FileUploaderUtil;
import com.codezero.fashiop.users.dao.RoleDao;
import com.codezero.fashiop.users.dao.UserDao;
import com.codezero.fashiop.users.entity.Role;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.codezero.fashiop.shared.model.Constants.USER_PROFILE_PICTURE_UPLOAD_FOLDER;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

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
    public UserResource saveUser(UserRequest userRequest) {
        User user = userDao.save(convertUserRequestToUser(userRequest));
        return convertToUserResource(user);
    }

    @Override
    public UserResource updateUser(UserRequest userRequest) {
        User user = userDao.findByUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setGender(userRequest.getGender());
        userDao.save(user);
        return convertToUserResource(user);

    }

    @Override
    public UserResource deleteUser(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        } else {
            if(!FileUploaderUtil.deleteImage(USER_PROFILE_PICTURE_UPLOAD_FOLDER, user.getProfilePicture())) {
                System.out.println("File delete not successful. Please delete manually");
            }
            userDao.delete(user);
        }
        return convertToUserResource(user);
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

    @Override
    public UserResource saveUserImage(MultipartFile file, long userId) {
        User user = fetchUserInfo(userId);
        user.setProfilePicture(FileUploaderUtil.uploadImage(file, user.getUsername(), USER_PROFILE_PICTURE_UPLOAD_FOLDER));
        return convertToUserResource(userDao.save(user));
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
        user.setGender(userRequest.getGender());
        user.setJoinedDate(new Date());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setStatus(true);
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleDao.findByName("USER"));
        user.setRoles(roles);
        return user;
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
                .userId((user.getId()))
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

    private User fetchUserInfo(long userId) {
        return userDao.findById(userId).orElseThrow((() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")));
    }

}
