package com.satyanand.caching_demo_app.repositories;

import com.satyanand.caching_demo_app.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByEmail(String email);

}
