package com.codezero.fashiop.users;

import com.codezero.fashiop.shared.route.Routes;
import com.codezero.fashiop.users.resources.UserRequest;
import com.codezero.fashiop.users.resources.UserResource;
import com.codezero.fashiop.users.resources.UserResourceList;
import com.codezero.fashiop.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Routes.USERS)
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public UserResourceList getAllUsers(Pageable page) {
        return userService.getAllUsers(page);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{username}")
    public UserResource getUser(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    public void saveUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    public long getUsersCount() { return userService.getUsersCount(); }
}
