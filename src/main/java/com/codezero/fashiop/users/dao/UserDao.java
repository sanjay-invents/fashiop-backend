package com.codezero.fashiop.users.dao;

import com.codezero.fashiop.users.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

}
