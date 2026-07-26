package com.satyanand.caching_demo_app.services.impl;

import com.satyanand.caching_demo_app.dtos.EmployeeDto;
import com.satyanand.caching_demo_app.entities.Employee;
import com.satyanand.caching_demo_app.exceptions.ResourceNotFoundException;
import com.satyanand.caching_demo_app.repositories.EmployeeRepository;
import com.satyanand.caching_demo_app.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () ->{
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                }
        );
        log.info("Successfully fetched employee with id: {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {

        log.info("Creating new employee with email: {}", employeeDto.getEmail());
        List<Employee> employeesList = employeeRepository.findByEmail(employeeDto.getEmail());

        if(!employeesList.isEmpty()){
            log.error("Employee already exists with email: {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exists with email: " + employeeDto.getEmail());
        }

        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);

        log.info("Successfully created new employee with id: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDto.class);

    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                }
        );

        if(!employee.getEmail().equals(employeeDto.getEmail())){
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }
        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with id: {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        boolean exists = employeeRepository.existsById(id);
        if (!exists) {
            log.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id: {}", id);
    }
}
