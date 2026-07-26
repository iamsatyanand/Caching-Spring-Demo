package com.satyanand.caching_demo_app.controllers;

import com.satyanand.caching_demo_app.dtos.EmployeeDto;
import com.satyanand.caching_demo_app.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


}
