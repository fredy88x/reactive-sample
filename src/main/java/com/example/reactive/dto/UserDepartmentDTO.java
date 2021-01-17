package com.example.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDepartmentDTO {
	private String userId;
    private String userName;
    private int age;
    private double salary;
    private Integer departmentId;
    private String departmentName;
    private String loc;
}
