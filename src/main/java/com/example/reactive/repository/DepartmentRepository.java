package com.example.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.reactive.entitys.Department;

import reactor.core.publisher.Mono;

public interface DepartmentRepository extends ReactiveMongoRepository<Department, Integer> {
	Mono<Department> findByUserId(Integer userId);
}
