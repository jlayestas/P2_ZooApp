package com.zoo.controller;

import static com.zoo.util.ClientMessageUtil.CREATION_FAILED;
import static com.zoo.util.ClientMessageUtil.CREATION_SUCCESSFUL;
import static com.zoo.util.ClientMessageUtil.DELETION_FAILED;
import static com.zoo.util.ClientMessageUtil.DELETION_SUCCESSFUL;
import static com.zoo.util.ClientMessageUtil.UPDATE_FAILED;
import static com.zoo.util.ClientMessageUtil.UPDATE_SUCCESSFUL;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpResponse;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zoo.models.Animals;
import com.zoo.models.ClientMessage;
import com.zoo.models.User;
import com.zoo.service.JwtService;
import com.zoo.services.UserService;

import io.jsonwebtoken.security.InvalidKeyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/api/users")
@Api(value= "UserRestController", description = "REST controller related to User Entities")
public class UserController {
	
	@Autowired
	private UserService userv;
	
	@Autowired
	JwtService jwtService;
	
	@ApiOperation(value="User login with username and password", notes="Provide username and password to login", response= User.class)
	@GetMapping(path = "/login")
	public @ResponseBody User userLogin(@RequestBody(required=true) Map<String, String> credentials, ServerHttpResponse response){
		User user = userv.login(credentials.get("username"), credentials.get("password"));
		if(user != null) {
			String jwt = null;
			try {
				jwt = jwtService.createJwt(user);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getHeaders().add("X-Auth-Token", "Bearer " + jwt);

		}
		
		return user;
	}
	
	@ApiOperation(value="Find user by id number", notes="Provide an id to look up a specific user from the API", response = User.class)
	@GetMapping(path = "/user")
	public @ResponseBody User getById(@RequestParam(value="id", name="id") int id) {
		System.out.println("TEST: " + userv.findUsernameById(id));
		return userv.findUsernameById(id);
	}
	
	@ApiOperation(value="Find animal by id", notes="Provide an animal name to look up a specific animal from the API", response = Animals.class)
	@GetMapping(path = "/animals")
	public @ResponseBody Animals getAnimalName (@RequestParam(value="id", name="id") int id) {
		System.out.println("TEST: " + userv.findAnimalById(id));
		return userv.findAnimalById(id);
	}
	
	
	@PostMapping("/createUser")
	@ApiOperation("create new user entity")
	public @ResponseBody ClientMessage createAccount(@RequestBody User user) {
		return userv.createAccount(user) ? CREATION_SUCCESSFUL : CREATION_FAILED;
	}
	
//	public @ResponseBody ClientMessage createAccount(@RequestParam(value="username", name="username") String name) {
//		
//	}
//	

	@PutMapping("/updateUser")
	@ApiOperation("update new user entity")
	public @ResponseBody ClientMessage editUser(@RequestBody User user) {
		return userv.editUser(user) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}

	@DeleteMapping("/deleteUser")
	@ApiOperation("delete user entity")
	public @ResponseBody ClientMessage deleteUser(@RequestBody User user) {
		return userv.deleteUser(user) ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}

}
