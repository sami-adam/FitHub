package com.fithub.service.employee;

import com.fithub.dto.employee.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<EmployeeDTO> getEmployees();
    EmployeeDTO getEmployee(Long id);
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    Map<String, String> deleteEmployee(Long id);
    List<EmployeeDTO> searchEmployees(String keyword);
}
