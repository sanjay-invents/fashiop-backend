package com.codezero.fashiop.users.resources;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserRequest {

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private MultipartFile profilePicture;

    private String gender;

    private int roles;

    private Date dateOfBirth;

    private Date joinedDate;

}
