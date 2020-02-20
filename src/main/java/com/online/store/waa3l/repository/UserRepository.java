package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.RoleType;
import com.online.store.waa3l.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "select * from USER a, USER_ROLE b where a.user_id = b.user_id and b.role_id = :role_id", nativeQuery = true)
    public List<User> getAllUsersByRole(@Param("role_id") Integer role);



}
