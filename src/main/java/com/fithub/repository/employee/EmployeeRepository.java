package com.fithub.repository.employee;

import com.fithub.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> searchByIdentificationNumberContainingIgnoreCaseOrNameContainsIgnoreCaseOrEmailContaining(String identificationNumber, String name, String email);
    List<Employee> findByEmail(String email);
}
