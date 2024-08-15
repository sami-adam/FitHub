package com.fithub.service.employee;

import com.fithub.dto.employee.EmployeeDTO;
import com.fithub.model.employee.Employee;
import com.fithub.repository.employee.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        employeeRepository.save(mapper.map(employeeDTO, Employee.class));
        return employeeDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        if(employeeDTO.getIdentificationNumber() != null) {
            employee.setIdentificationNumber(employeeDTO.getIdentificationNumber());
        }
        if(employeeDTO.getName() != null && !employeeDTO.getName().isBlank()) {
            employee.setName(employeeDTO.getName());
        }
        if(employeeDTO.getEmail() != null && !employeeDTO.getEmail().isBlank()) {
            employee.setEmail(employeeDTO.getEmail());
        }
        if(employeeDTO.getPhone() != null && !employeeDTO.getPhone().isBlank()) {
            employee.setPhone(employeeDTO.getPhone());
        }
        if(employeeDTO.getAddress() != null && !employeeDTO.getAddress().isBlank()) {
            employee.setAddress(employeeDTO.getAddress());
        }
        if(employeeDTO.getEmployeeType() != null) {
            employee.setEmployeeType(employeeDTO.getEmployeeType());
        }
        if(employeeDTO.getUser() != null) {
            employee.setUser(employeeDTO.getUser());
        }
        employeeRepository.save(employee);
        return mapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public Map<String, String> deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return Map.of("message", "Employee deleted successfully", "status", "success");
    }

    @Override
    public List<EmployeeDTO> searchEmployees(String keyword) {
        List<Employee> employees = employeeRepository.searchByIdentificationNumberContainingIgnoreCaseOrNameContainsIgnoreCaseOrEmailContaining(keyword, keyword, keyword);
        return employees.stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }
}
