package com.example.bankms.Repository;

import com.example.bankms.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeByUserId(Integer id);
}
