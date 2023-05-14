package com.dev.etsena.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pierre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String description;
	private String forme;
	private String taille;
	
	//private String qualite;
	//private String type;
	private String image;
	private double prix;
	private String poids;
	private String couleur;
	
	@ManyToOne
	private Purete purete;
	@ManyToOne
	private Type type;
	@ManyToOne
	private Qualite qualite;
	
	@OneToMany(mappedBy = "pierre", fetch = FetchType.LAZY)
	private Collection<Panier> panier;
	

}
