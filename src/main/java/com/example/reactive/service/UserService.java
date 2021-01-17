package com.example.reactive.service;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reactive.dto.UserDepartmentDTO;
import com.example.reactive.entitys.Department;
import com.example.reactive.entitys.User;
import com.example.reactive.repository.DepartmentRepository;
import com.example.reactive.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public Mono<User> createUser(User user) {
		return userRepository.save(user);
	}

	public Flux<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Mono<User> findById(Integer userId) {
		return userRepository.findById(userId.longValue());
	}

	public Flux<User> findUsersByAge(int age) {
		return userRepository.findByAge(age);
	}

	public Mono<User> deleteUser(Integer userId) {
		return userRepository.findById(userId.longValue())
                .flatMap(existingUser -> userRepository.delete(existingUser)
                .then(Mono.just(existingUser)));
	}

	public Mono<UserDepartmentDTO> fetchUserAndDepartment(Integer userId) {
		 Mono<User> user = findById(userId).subscribeOn(Schedulers.boundedElastic());
	        Mono<Department> department = getDepartmentByUserId(userId).subscribeOn(Schedulers.boundedElastic());
	        return Mono.zip(user, department, userDepartmentDTOBiFunction);
	}

	private Mono<Department> getDepartmentByUserId(Integer userId) {
		return departmentRepository.findByUserId(userId);
	}

	/*public Flux<User> fetchUsers(List<Integer> userIds) {
		return Flux.fromIterable(userIds)
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .flatMap(i -> findById(i))
        .ordered((u1, u2) -> u2.getId() - u1.getId());
	}*/

	public Mono<User> updateUser(Integer userId, User user) {
		return userRepository.findById(userId.longValue())
                .flatMap(dbUser -> {
                    dbUser.setAge(user.getAge());
                    dbUser.setSalary(user.getSalary());
                    return userRepository.save(dbUser);
                });
	}
	
	private BiFunction<User, Department, UserDepartmentDTO> userDepartmentDTOBiFunction = (x1, x2) -> UserDepartmentDTO.builder()
            .age(x1.getAge())
            .departmentId(x2.getId())
            .departmentName(x2.getName())
            .userName(x1.getName())
            .userId(x1.getId())
            .loc(x2.getLoc())
            .salary(x1.getSalary()).build();
}
