package com.dev.etsena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.etsena.entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

}
