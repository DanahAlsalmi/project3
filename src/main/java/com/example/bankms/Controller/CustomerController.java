package com.example.bankms.Controller;


import com.example.bankms.DTO.CustomerDTO;
import com.example.bankms.Model.User;
import com.example.bankms.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //get All
    @GetMapping("/get-all")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    //register Customer
    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTO registerCustomerDTO) {
        customerService.registerCustomer(registerCustomerDTO);
        return ResponseEntity.status(200).body("Customer registered successfully");
    }

    //update Customer
    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user , @RequestBody @Valid CustomerDTO updateCustomerDTO) {
        customerService.updateCustomer(user.getId(), updateCustomerDTO);
        return ResponseEntity.status(200).body("Customer updated successfully");
    }

    //Delete Customer
    @DeleteMapping("/del/{customerId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user, @PathVariable Integer customerId) {
        customerService.deleteCustomer(user.getId(),customerId);
        return ResponseEntity.status(200).body("Customer deleted successfully");
    }

}
