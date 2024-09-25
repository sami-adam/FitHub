package com.fithub.service.employee;

import com.fithub.config.SecurityContextGenerator;
import com.fithub.dto.employee.EmployeeDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.employee.Employee;
import com.fithub.repository.employee.EmployeeRepository;
import com.fithub.service.odoo.OdooService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private SecurityContextGenerator securityContextGenerator;
    // Odoo Properties
    @Value("${odoo.url}")
    private String odooUrl;
    @Value("${odoo.db}")
    private String odooDb;
    @Value("${odoo.username}")
    private String odooLogin;
    @Value("${odoo.password}")
    private String odooPassword;
    private final OdooService odooService;

    @Override
    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(mapper.map(employeeDTO, Employee.class));
        return mapper.map(employee, EmployeeDTO.class);
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

    // Odoo Integration
    void syncEmployees(SecurityContext context){
        logger.info("Syncing employees with Odoo");
        try {
            odooService.authenticate(odooUrl, odooDb, odooLogin, odooPassword);
            String response = odooService.getRecords(odooUrl, "hr.employee", new String[]{"id", "name", "work_phone", "work_email"});
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray employees = jsonObject.get("result").getAsJsonArray();
            for(JsonElement jsonElement : employees){
                JsonObject employeeJson = jsonElement.getAsJsonObject();
                String id = employeeJson.get("id").getAsString();
                String name = employeeJson.get("name").getAsString();
                String phone = employeeJson.get("work_phone").getAsString();
                String email = employeeJson.get("work_email").getAsString();
                List<Employee> existingEmployees = employeeRepository.findByEmail(email);
                if(existingEmployees.isEmpty()) {
                    Employee employee = new Employee();
                    employee.setName(name);
                    employee.setPhone(phone);
                    employee.setEmail(email);
                    try {
                        employeeRepository.save(employee);
                    } catch (IllegalArgumentException e) {
                        logger.error("Something went wrong: " + e);
                    }
                } else {
                    boolean updated = false;
                    Employee employee = existingEmployees.getFirst();
                    if(!employee.getName().equals(name)) {
                        employee.setName(name);
                        updated = true;
                    }
                    if(employee.getPhone().contains(")")) {
                        employee.setPhone(phone.replace("(", "").replace(")", "").replace(" ", "").replace("-", ""));
                        updated = true;
                    }
                    if(employee.getIdentificationNumber() == null || !employee.getIdentificationNumber().equals(id)) {
                        employee.setIdentificationNumber(id);
                        updated = true;
                    }
                    if(updated) {
                        employeeRepository.save(employee);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //@Transactional
    @Scheduled(fixedRate = 1000 * 60 * 30) // Adjust the fixedRate as needed
    public void performTask() {
        SecurityContext context = securityContextGenerator.createSecurityContext();
        try {
            syncEmployees(context);
            //System.out.println("Syncing Employees with Odoo");
        } finally {
            //context.setAuthentication(null);
        }
    }
}
