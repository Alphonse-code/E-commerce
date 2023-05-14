package com.dev.etsena.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import com.dev.etsena.entity.Pierre;
import com.dev.etsena.exception.ResourceNotFoundException;
import com.dev.etsena.repository.PierreRepository;
import com.dev.etsena.utils.PierreDTO;

@Service
public class PierreService {

	private PierreRepository pierreRepository;

	/**
	 * @param pierreRepository
	 */
	public PierreService(PierreRepository pierreRepository) {
		super();
		this.pierreRepository = pierreRepository;
	}

	// recuperer tout les liste de pierre dans la base de donné
	public List<Pierre> getAllPierre() {
		return pierreRepository.findAllByOrderByIdDesc();
	}

	// recuperer la pierre dans la base de donné par id
	public Optional<Pierre> getPierreById(@PathVariable Long id) {
		Optional<Pierre> pierre = Optional.of(pierreRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pierre not exist with id :" + id)));
		return pierre;
	}

	// ajouter pierre dans la base
	public Pierre ajouterPierre(Pierre pierre) {
		try {
			return pierreRepository.save(pierre);
		} catch (Exception e) {
			throw e;
		}

	}

	/*
	 * Supprimer un pierre
	 */
	public void supprimerPiere(Long id) {
		try {
			pierreRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Mettre à jour pierre
	 */
	public Pierre updatePierre(Pierre p, Long id) {
		Pierre pierre = pierreRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pierre not exist with id :" + id));

		pierre.setNom(p.getNom());
		pierre.setCouleur(p.getCouleur());
		pierre.setDescription(p.getDescription());
		pierre.setForme(p.getForme());
		pierre.setPoids(p.getPoids());
		
		pierre.setPrix(p.getPrix());
		pierre.setTaille(p.getTaille());
		pierre.setType(p.getType());
		pierre.setQualite(p.getQualite());
		pierre.setImage(p.getImage());
		Pierre updatePierre = pierreRepository.save(pierre);
		return updatePierre;
	}

	public ResponseEntity<byte[]> fromClasspathAsResEntity(@PathVariable String imageName) throws IOException {
		ClassPathResource imageFile = new ClassPathResource("static/image/" + imageName);
		byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);

	}

	// Load the image from the specified file path
	public byte[] getImageFromPath(String imageName) {
		try {
			Path path = Paths.get("src/main/resources/static/image/" + imageName);
			byte[] data = Files.readAllBytes(path);
			return data;
		} catch (IOException e) {
			// Handle the error
			e.printStackTrace();
		}
		return null;
	}

	// load by id
	public Object loadPierreById(Long id) throws IOException {
		PierreDTO resp = new PierreDTO();
		Pierre pierre = getPierreById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pierre not exist with id :" + id));
		resp.setNom(pierre.getNom());
		resp.setCouleur(pierre.getCouleur());
		resp.setDescription(pierre.getDescription());
		resp.setForme(pierre.getForme());
		resp.setPoids(pierre.getPoids());
		
		resp.setPrix(pierre.getPrix());
		resp.setTaille(pierre.getTaille());
		
		resp.setImage("/classpath/"+pierre.getImage());
		return resp;
	}

}
