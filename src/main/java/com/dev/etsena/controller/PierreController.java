package com.dev.etsena.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.etsena.entity.Pierre;
import com.dev.etsena.service.FileService;
import com.dev.etsena.service.PierreService;

@RestController
@RequestMapping("/api/v01")
@CrossOrigin(origins = "*")
public class PierreController {

	private PierreService pierreService;
	private FileService fileService;
	
	/**
	 * @param pierreService
	 * @param fileService
	 */
	public PierreController(PierreService pierreService, FileService fileService) {
		super();
		this.pierreService = pierreService;
		this.fileService = fileService;
	}

	
	@Value("${project.image}")
	private String path;

	
	// delete pierre
	@DeleteMapping("/{id}")
	public void supprimer(@PathVariable("id") Long id) {
		System.out.println(id);
		pierreService.supprimerPiere(id);
	}

	@PostMapping(value = "/add_pierres")
	public ResponseEntity<Pierre> addPierres(@RequestBody Pierre pierre) {
		return new ResponseEntity<Pierre>(pierreService.ajouterPierre(pierre), HttpStatus.OK);
	}

	@PostMapping(value = "/add_pierre/{nom}/{description}/{forme}/{taille}/{pureter}/{qualite}/{type}/{prix}/{poids}/{couleur}")
	public ResponseEntity<Pierre> addPierres(@RequestPart @RequestParam("image") MultipartFile image, 
			@PathVariable("nom") String nom,
			@PathVariable("description") String description,
			@PathVariable("forme") String forme,
			@PathVariable("taille") String taille,
			@PathVariable("pureter") String pureter,
			@PathVariable("qualite") String qualite,
			@PathVariable("type") String type,
			@PathVariable("prix") double prix,
			@PathVariable("poids") String poids,
			@PathVariable("couleur") String couleur
			
			) throws IOException {
		
		String fileName = null;
		
		Pierre pierre = new Pierre();
	
		pierre.setNom(nom);
		pierre.setCouleur(couleur);
		pierre.setDescription(description);
		pierre.setForme(forme);
		pierre.setPoids(poids);
		
		pierre.setPrix(prix);
		pierre.setTaille(taille);
		
		
		fileName = fileService.uploadImage(path, image);
		
		pierre.setImage(fileName);
		Pierre saveP = pierreService.ajouterPierre(pierre);
		return new ResponseEntity<Pierre>(saveP, HttpStatus.OK);
		
	}

	@PutMapping(value = "/update_pierre/{id}")
	public ResponseEntity<Pierre> edit(@RequestParam("img") MultipartFile img, @PathVariable Long id

	) throws IOException {
		String fileName = null;
		Pierre pierre = pierreService.getPierreById(id).orElse(null);
		fileName = fileService.uploadImage(path, img);
		pierre.setImage(fileName);
		Pierre updatePerre = pierreService.updatePierre(pierre, id);
		return new ResponseEntity<Pierre>(updatePerre, HttpStatus.OK);
	}

	// Return the image from the classpath location
	@GetMapping(value = "/classpath/{imageName:.+}")

	public ResponseEntity<byte[]> fromClasspathAsResEntity(@PathVariable String imageName) throws IOException {

		return pierreService.fromClasspathAsResEntity(imageName);
	}

	// get by Id
	@GetMapping("/{id}")
	public Object showPierreById(@PathVariable("id") Long id) throws IOException {
		return pierreService.getPierreById(id);
	}

	@GetMapping("/pierre_list")
	public List<Object> showAllPierre() throws IOException {
		List<Pierre> pierres = pierreService.getAllPierre();
		List<Object> myList = new ArrayList<Object>();
		myList.clear();
		Object pierre = null;
		for (Pierre p : pierres) {
			pierre = showPierreById(p.getId());
			myList.add(pierre);
		}
		return myList;
	}

}
