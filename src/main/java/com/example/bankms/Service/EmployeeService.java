package com.example.bankms.Service;

import com.example.bankms.Api.ApiException;


import com.example.bankms.DTO.EmployeeDTO;


import com.example.bankms.Model.Employee;
import com.example.bankms.Model.User;
import com.example.bankms.Repository.AuthRepository;
import com.example.bankms.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public void registerEmployee(EmployeeDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("EMPLOYEE");

        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        user.setEmployee(employee);
        employee.setUser(user);
        authRepository.save(user);
        employeeRepository.save(employee);
    }

    public void updateEmployee(Integer userId, EmployeeDTO dto) {
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("Employee not found with id: " + userId);
        }
        Employee employee = employeeRepository.findEmployeeByUserId(userId);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setUser(user);
        user.setEmployee(employee);

        authRepository.save(user);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer userId, Integer employeeId) {
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found with id: " + userId);
        }

        User employee = authRepository.findUserById(employeeId);
        if (employee == null) {
            throw new ApiException("Employee not found with id: " + employeeId);
        }
        authRepository.delete(employee);
    }
}
