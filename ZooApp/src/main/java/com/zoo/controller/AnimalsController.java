package com.zoo.controller;

import java.util.List;

import static com.zoo.util.ClientMessageUtil.CREATION_FAILED;
import static com.zoo.util.ClientMessageUtil.CREATION_SUCCESSFUL;
import static com.zoo.util.ClientMessageUtil.DELETION_FAILED;
import static com.zoo.util.ClientMessageUtil.DELETION_SUCCESSFUL;
import static com.zoo.util.ClientMessageUtil.UPDATE_FAILED;
import static com.zoo.util.ClientMessageUtil.UPDATE_SUCCESSFUL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoo.models.Animals;
import com.zoo.models.ClientMessage;
import com.zoo.services.AnimalsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/api")
@Api(value = "AnimalsRestController", description = "REST controller related to Animals Entities")
public class AnimalsController {
	
	private static Logger log = Logger.getLogger(AnimalsController.class);
	
	@Autowired
	private AnimalsService aserv;

	 @ApiOperation(value="Find animal by id number", notes="Provide an id to lookup a specific animal from the API", response = Animals.class)
	 
	 @GetMapping(path = "/animalsid") 
	 public @ResponseBody Animals getById(@RequestParam int id) { 
		 
		 log.info("finding animal by Id in controller...");
		 
		 return aserv.getAnimalsById(id); 
	 
	 }
	 
	@GetMapping("/animalsall")
	@ApiOperation(value="Find all animals")
	public @ResponseBody List<Animals> getAll() {
		
		log.info("looking up list of animals in controller...");
		return aserv.getAllAnimals();
	}
	
	@PostMapping("/animalsnew")
	@ApiOperation(value="Create new animal entity")
	public @ResponseBody ClientMessage createAnimals(@RequestBody Animals animals) {
		
		log.info("creating a new animal in controller...");
		return aserv.createAnimal(animals) ? CREATION_SUCCESSFUL : CREATION_FAILED;
	}
	
	@PutMapping("/animalsupdate")
	@ApiOperation(value="Update animal entity")
	public @ResponseBody ClientMessage updateAnimals(@RequestBody Animals animals) {
		
		log.info("updating an animal in controller...");
		return aserv.updateAnimals(animals) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}
	
	@DeleteMapping("/animalsdelete")
	@ApiOperation(value="Remove animal entity")

	public @ResponseBody ClientMessage deleteAnimals(@RequestBody Animals animals) {

		log.info("deleting an animal in controller...");
		return aserv.deleteAnimals(animals) ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}
	
}