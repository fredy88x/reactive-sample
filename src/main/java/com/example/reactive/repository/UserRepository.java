package com.example.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.reactive.entitys.User;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {
	Flux<User> findByAge(int age);
}
