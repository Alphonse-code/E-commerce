package com.dev.etsena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.etsena.entity.Pierre;

public interface PierreRepository extends JpaRepository<Pierre, Long> {
	public List<Pierre> findAllByOrderByIdDesc();
}
