package com.example.bankms.Service;

import com.example.bankms.AccountNumberGenerator;
import com.example.bankms.Api.ApiException;
import com.example.bankms.Model.Account;
import com.example.bankms.Model.Customer;
import com.example.bankms.Model.Employee;
import com.example.bankms.Model.User;
import com.example.bankms.Repository.AccountRepository;
import com.example.bankms.Repository.AuthRepository;
import com.example.bankms.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getMyAccounts(Integer id) {
        Customer user =customerRepository.findCustomerByUserId(id);
        return accountRepository.findAllByCustomerId(user);
    }

    public void createAccount(Integer userId, Account account) {
        Customer customer = customerRepository.findCustomerByUserId(userId);
        if (customer == null) {
            throw new ApiException("Customer not found for user id: " + userId);
        }
        String accountNumber = AccountNumberGenerator.generateAccountNumber();
        account.setAccountNumber(accountNumber);

        Account a=new Account();
        a.setAccountNumber(account.getAccountNumber());
        a.setBalance(account.getBalance());
        account.setCustomer(customer);
        account.setIsActive(false);
        customerRepository.save(customer);
        accountRepository.save(account);
    }

    public void updateAccount(Integer userId, Account account) {
        Customer customer = customerRepository.findCustomerByUserId(userId);
        if (customer == null) {
            throw new ApiException("Customer not found for user id: " + userId);
        }
        account.setAccountNumber(account.getAccountNumber());
        account.setBalance(account.getBalance());
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public void deleteAccount(Integer userId) {
        Customer customer = customerRepository.findCustomerByUserId(userId);
        if (customer == null) {
            throw new ApiException("Customer not found for user id: " + userId);
        }
        customerRepository.delete(customer);

    }



    public void activateAccount(Integer userId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        User a = authRepository.findUserById(userId);

        account.setIsActive(true);
        accountRepository.save(account);
    }

    public Account getAccountDetails(Integer userId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found for user id: " + userId);
        }

        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found for user id: " + userId);
        }

        return account;
    }


    public List<Account> listUserAccounts(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found for user id: " + userId);
        }
        return accountRepository.findByCustomerId(user.getCustomer().getId());
    }

    public void deposit(Integer userId,Integer accountId, Double amount) {
        Account account = accountRepository.findAccountById(accountId);
        User user = authRepository.findUserById(userId);
        if(user == null) {
            throw new ApiException("User not found for user id: " + userId);
        }

        if (account == null) {
            throw new ApiException("Account not found with id: " + accountId);
        }
        if (!account.getIsActive()){
            throw new ApiException("Account is not active");
        }

        if (amount <= 0) {
            throw new ApiException("Deposit amount must be greater than zero.");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }


    public void withdraw(Integer userId,Integer accountId, Double amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found with id: " + accountId);
        }
        if (!account.getIsActive()){
            throw new ApiException("Account is not active");
        }

        if (amount <= 0) {
            throw new ApiException("Withdrawal amount must be greater than zero.");
        }

        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient balance in account.");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transferFunds(Integer userId, Integer fromAccountId, Integer toAccountId, Double amount) {
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (amount<= 0) {
            throw new ApiException("Transfer amount must be positive");
        }
        if (!fromAccount.getIsActive()){
            throw new ApiException("From Account is not active");
        }
        if (!toAccount.getIsActive()){
            throw new ApiException("To Account is not active");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient funds in source account");
        }

        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }


    public void blockAccount(Integer userId,Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found for user id: " + userId);
        }
        if (account == null) {
            throw new ApiException("Account not found with id: " + accountId);
        }

        account.setIsActive(false);
        accountRepository.save(account);
    }
}
