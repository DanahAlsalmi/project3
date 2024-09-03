package com.example.bankms.Controller;

import com.example.bankms.Model.Account;
import com.example.bankms.Model.User;
import com.example.bankms.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //get all for Admin
    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    //get for user
    @GetMapping("/get-my")
    public ResponseEntity getMyAccount(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getMyAccounts(user.getId()));
    }

    //create Account
    @PostMapping("/create")
    public ResponseEntity createAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account) {
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.status(201).body("Account created successfully");
    }

    //Update Account
    @PutMapping("/update")
    public ResponseEntity updateAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account) {
        accountService.updateAccount(user.getId(), account);
        return ResponseEntity.status(201).body("Account updated successfully");
    }

    //Delete Account
    @DeleteMapping("/del/{customerId}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal User user, @PathVariable Integer customerId) {
        accountService.deleteAccount(user.getId());
        return ResponseEntity.status(201).body("Account deleted successfully");
    }

    //Activate Account
    @PutMapping("/activate-account/{accountId}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body("Account activated successfully");
    }

    //Details
    @GetMapping("/details/{accountId}")
    public ResponseEntity getAccountDetails(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        Account accountDetails = accountService.getAccountDetails(user.getId(), accountId);
        return ResponseEntity.status(200).body(accountDetails);
    }

    //Users Account
    @GetMapping("/user-account")
    public ResponseEntity listUserAccounts(@AuthenticationPrincipal User user) {
        List<Account> userAccounts = accountService.listUserAccounts(user.getId());
        return ResponseEntity.status(200).body(userAccounts);
    }

    //Deposit
    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity depositMoney(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(user.getId(),accountId, amount);
        return ResponseEntity.status(200).body("Deposit successfully");
    }

    //Withdraw
    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user,@PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.withdraw(user.getId(),accountId, amount);
        return ResponseEntity.status(200).body("Withdraw successfully");
    }


    //Transfer
    @PostMapping("/transfer/from/{fromAccountId}/to/{toAccountId}/amount/{amount}")
    public ResponseEntity transferFunds(@AuthenticationPrincipal User user, @PathVariable Integer fromAccountId, @PathVariable Integer toAccountId, @PathVariable Double amount) {
        accountService.transferFunds(user.getId(), fromAccountId, toAccountId, amount);
        return ResponseEntity.status(200).body("Transfer successfully");
    }

    //Block
    @PutMapping("/block/{accountId}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.blockAccount(user.getId(),accountId);
        return ResponseEntity.status(200).body("Block Successfully");
    }
}
