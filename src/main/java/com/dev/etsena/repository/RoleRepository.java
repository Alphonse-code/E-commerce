package com.dev.etsena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.etsena.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRoleName(String roleName);

}
