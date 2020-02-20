package com.online.store.waa3l.service.impl;

import com.online.store.waa3l.domain.Role;
import com.online.store.waa3l.repository.RoleRepository;
import com.online.store.waa3l.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role get(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAllRolesNotAdmin(Integer id){
        return roleRepository.getAllRolesNotAdmin(id);
    }
}
