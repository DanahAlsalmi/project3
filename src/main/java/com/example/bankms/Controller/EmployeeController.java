package com.example.bankms.Controller;

import com.example.bankms.DTO.EmployeeDTO;
import com.example.bankms.Model.User;
import com.example.bankms.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    //get all
    @GetMapping("/get-all")
    public ResponseEntity getAllEmployee() {
        return ResponseEntity.status(200).body(employeeService.getAllEmployee());
    }

    //register Employee
    @PostMapping("/register-employee")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTO registerEmployee) {
        employeeService.registerEmployee(registerEmployee);
        return ResponseEntity.status(200).body("Employee registered successfully");
    }

    //Update Employee
    @PutMapping("/update")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user , @RequestBody @Valid EmployeeDTO updateEmployee) {
        employeeService.updateEmployee(user.getId(), updateEmployee);
        return ResponseEntity.status(200).body("Employee updated successfully");
    }

    //Delete Employee
    @DeleteMapping("/del/{employeeId}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user, @PathVariable Integer employeeId) {
        employeeService.deleteEmployee(user.getId(),employeeId);
        return ResponseEntity.status(200).body("Employee deleted successfully");
    }
}
