package com.codezero.fashiop.users;

import com.codezero.fashiop.shared.route.Routes;
import com.codezero.fashiop.users.resources.UserRequest;
import com.codezero.fashiop.users.resources.UserResource;
import com.codezero.fashiop.users.resources.UserResourceList;
import com.codezero.fashiop.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public UserResource saveUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    public long getUsersCount() { return userService.getUsersCount(); }

    @PostMapping("/{userId}")
    public UserResource saveUserProfilePicture(@RequestParam(value="file") MultipartFile file, @PathVariable long userId) {
        return userService.saveUserImage(file, userId);
    }

    @PutMapping("/{username}")
    public UserResource updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        UserResource userResource = userService.deleteUser(username);
        if(userResource != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
