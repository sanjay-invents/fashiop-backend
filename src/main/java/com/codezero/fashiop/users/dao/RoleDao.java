package com.codezero.fashiop.users.dao;

import com.codezero.fashiop.users.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

    Role findByName (String name);

}
