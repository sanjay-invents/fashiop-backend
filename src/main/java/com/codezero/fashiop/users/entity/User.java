package com.codezero.fashiop.users.entity;

import com.codezero.fashiop.shared.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String username;

    private String password;

    @Column(name = "recovery_key")
    private String recoveryKey = null;

    @Column(name = "profile_picture")
    private String profilePicture;

    private String gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "joined_date")
    private Date joinedDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;
}