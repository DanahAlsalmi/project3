package com.example.bankms.Repository;

import com.example.bankms.Model.Account;
import com.example.bankms.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(Integer id);

    List<Account> findAllByCustomerId(Customer customer);

    List<Account> findByCustomerId(Integer customerId);

}
