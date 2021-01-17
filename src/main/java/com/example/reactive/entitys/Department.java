package com.example.reactive.entitys;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private Integer id;
    private String name;
    private Integer userId;
    private String loc;
}