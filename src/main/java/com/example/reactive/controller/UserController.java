package com.example.reactive.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactive.dto.UserDepartmentDTO;
import com.example.reactive.entitys.User;
import com.example.reactive.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@RequestBody User user){
        return userService.createUser(user);
    }
	
	
	   @GetMapping
	    public Flux<User> getAllUsers(){
	        return userService.getAllUsers();
	    }
	   
	   @GetMapping("/{userId}")
	    public Mono<ResponseEntity<User>> getUserById(@PathVariable Integer userId){
	        Mono<User> user = userService.findById(userId);
	        return user.map( u -> ResponseEntity.ok(u))
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	   
	   @PutMapping("/{userId}")
	    public Mono<ResponseEntity<User>> updateUserById(@PathVariable Integer userId, @RequestBody User user){
	        return userService.updateUser(userId,user)
	                .map(updatedUser -> ResponseEntity.ok(updatedUser))
	                .defaultIfEmpty(ResponseEntity.badRequest().build());
	    }

	    @DeleteMapping("/{userId}")
	    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable Integer userId){
	        return userService.deleteUser(userId)
	                .map( r -> ResponseEntity.ok().<Void>build())
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }

	    @GetMapping("/age/{age}")
	    public Flux<User> getUsersByAge(@PathVariable int age) {
	        return userService.findUsersByAge(age);
	    }

	    @GetMapping("/{userId}/department")
	    public Mono<UserDepartmentDTO> fetchUserAndDepartment(@PathVariable Integer userId){
	        return userService.fetchUserAndDepartment(userId);
	    }
	
}
