package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.Role;
import com.online.store.waa3l.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    @Query(value = "select * from ROLE where role_id != :role_id", nativeQuery = true)
    public List<Role> getAllRolesNotAdmin(@Param("role_id") Integer role);
}
