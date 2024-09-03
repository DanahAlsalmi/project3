package com.example.bankms.Service;

import com.example.bankms.Api.ApiException;
import com.example.bankms.DTO.CustomerDTO;
import com.example.bankms.Model.Customer;
import com.example.bankms.Model.User;
import com.example.bankms.Repository.AuthRepository;
import com.example.bankms.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("CUSTOMER");

        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);
        user.setCustomer(customer);
        authRepository.save(user);

         customerRepository.save(customer);
    }

    public void updateCustomer(Integer userId, CustomerDTO dto) {
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("Customer not found with id: " + userId);
        }
        Customer customer = customerRepository.findCustomerByUserId(userId);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);

        authRepository.save(user);
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer userId, Integer customerId) {
    User user = authRepository.findUserById(userId);
    if (user == null) {
        throw new ApiException("User not found with id: " + userId);
    }

    User customer = authRepository.findUserById(customerId);
    if (customer == null) {
        throw new ApiException("Customer not found with id: " + customerId);
    }
    authRepository.delete(customer);
}
}



