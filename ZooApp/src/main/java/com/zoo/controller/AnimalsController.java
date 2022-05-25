package com.revature.controller;

import java.util.List;

import static com.revature.util.AnimalsClientMessageUtil.CREATION_FAILED;
import static com.revature.util.AnimalsClientMessageUtil.CREATION_SUCCESSFUL;
import static com.revature.util.AnimalsClientMessageUtil.DELETION_FAILED;
import static com.revature.util.AnimalsClientMessageUtil.DELETION_SUCCESSFUL;
import static com.revature.util.AnimalsClientMessageUtil.UPDATE_FAILED;
import static com.revature.util.AnimalsClientMessageUtil.UPDATE_SUCCESSFUL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Animals;
import com.revature.models.AnimalsClientMessage;
import com.revature.services.AnimalsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "AnimalsRestController", description = "REST controller related to Animals Entities")
public class AnimalsController {
	
	private static Logger log = Logger.getLogger(AnimalsController.class);
	
	@Autowired
	private AnimalsService aserv;

	 @ApiOperation(value="Find animal by id number", notes="Provide an id to lookup a specific candy from the API", response = Animals.class)
	 @GetMapping(path = "/animals") 
	 public @ResponseBody Animals getById(@RequestParam(value = "id", name = "id") int id) { 
		 
		 log.info("finding animal by Id in controller...");
		 System.out.println("TEST: " + aserv.getAnimalsById(id));
		 return aserv.getAnimalsById(id); 
	 }
	
	@GetMapping("/animals")
	@ApiOperation(value="Find all animals")
	public @ResponseBody List<Animals> getAll() {
		
		log.info("looking up list of animals in controller...");
		return aserv.getAllAnimals();
	}
	
	@PostMapping("/animals")
	@ApiOperation(value="Create new animal entity")
	public @ResponseBody AnimalsClientMessage createAnimals(@RequestBody Animals animals) {
		
		log.info("creating a new animal in controller...");
		return aserv.createAnimal(animals) ? CREATION_SUCCESSFUL : CREATION_FAILED;
	}
	
	@PutMapping("/animals")
	@ApiOperation(value="Update animal entity")
	public @ResponseBody AnimalsClientMessage updateCandy(@RequestBody Animals animals) {
		
		log.info("updating an animal in controller...");
		return aserv.updateAnimals(animals) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}
	
	@DeleteMapping("/animals")
	@ApiOperation(value="Remove animal entity")
	public @ResponseBody AnimalsClientMessage deleteCandy(@RequestBody Animals animals) {
		
		log.info("deleting an animal in controller...");
		return aserv.deleteAnimals(animals) ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}
	
	
}