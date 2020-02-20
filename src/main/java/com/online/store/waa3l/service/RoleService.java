package com.online.store.waa3l.service;

import com.online.store.waa3l.domain.Role;
import com.online.store.waa3l.domain.User;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role get(Integer id);

    List<Role> getAllRolesNotAdmin(Integer id);
}
