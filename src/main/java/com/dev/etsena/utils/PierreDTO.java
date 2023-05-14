package com.dev.etsena.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PierreDTO {

	private Long id;
	private String nom;
	private String description;
	private String forme;
	private String taille;
	private String pureter;
	private String qualite;
	private String type;
	private String image;
	private double prix;
	private String poids;
	private String couleur;
}
