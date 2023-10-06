package com.jinleebriller.jinleespace.dao;

import com.jinleebriller.jinleespace.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findOneById(Long id);
}
