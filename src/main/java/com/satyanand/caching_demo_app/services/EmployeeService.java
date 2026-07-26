package com.satyanand.caching_demo_app.services;

import com.satyanand.caching_demo_app.dtos.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createNewEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}
