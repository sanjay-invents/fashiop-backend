package com.codezero.fashiop.users.resources;

import com.codezero.fashiop.users.entity.Role;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResource {

    private long userId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String username;

    private String profilePicture;

    private String gender;

    private Set<Role> roles;

    private Date dateOfBirth;

    private Date joinedDate;
}
