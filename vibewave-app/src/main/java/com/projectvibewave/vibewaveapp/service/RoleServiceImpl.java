package com.projectvibewave.vibewaveapp.service;

import com.projectvibewave.vibewaveapp.entity.Role;
import com.projectvibewave.vibewaveapp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void saveAll(Iterable<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
