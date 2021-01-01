package com.homekeeper.repository;

import com.homekeeper.models.ERoles;
import com.homekeeper.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERoles roleName);
}
