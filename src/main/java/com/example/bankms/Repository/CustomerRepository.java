package com.example.bankms.Repository;

import com.example.bankms.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(Integer id);

    Customer findCustomerByUserId(Integer userId);
}
