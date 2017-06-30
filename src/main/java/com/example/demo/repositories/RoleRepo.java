package com.example.demo.repositories;

import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by student on 6/30/17.
 */
public interface RoleRepo extends CrudRepository<Role, Long> {
    public Set<Role> findAllById(Long id);
}
