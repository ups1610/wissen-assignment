package com.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.models.Account;
import com.assignment.models.LoginRequest;
import com.assignment.service.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean loggedIn = accountService.login(loginRequest);
            if (loggedIn) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try 
        {
            accountService.register(account);
            return ResponseEntity.ok().build();
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/changepwd/{customerId}/{oldPassword}/{newPassword}")
    public ResponseEntity<?> changePassword(@PathVariable String customerId,
                                            @PathVariable String oldPassword,
                                            @PathVariable String newPassword) {
        try 
        {
            boolean changed = accountService.changePassword(customerId, oldPassword, newPassword);
            if (changed) 
            {
                return ResponseEntity.ok().build();
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/balance/{accountNo}")
    public ResponseEntity<?> getBalance(@PathVariable String accountNo) {
        try 
        {
            Double balance = accountService.getBalance(accountNo);
            if (balance != null) 
            {
                return ResponseEntity.ok(balance);
            } 
            else 
            {
                return ResponseEntity.notFound().build();
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam String fromAccount,
                                      @RequestParam String toAccount,
                                      @RequestParam Double amount)
    {
        try 
        {
            boolean transferred = accountService.transfer(fromAccount, toAccount, amount);
            if (transferred) 
            {
                return ResponseEntity.ok().build();
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transfer failed");
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    
    @GetMapping("/accounts/below")
    public ResponseEntity<List<Account>> getAccountsBelowBalance(@RequestParam Double amount) 
    {
        try 
        {
            List<Account> accounts = accountService.getAccountsBelowBalance(amount);
            return ResponseEntity.ok(accounts);
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/accounts/above")
    public ResponseEntity<List<Account>> getAccountsAboveBalance(@RequestParam Double amount) {
        try 
        {
            List<Account> accounts = accountService.getAccountsAboveBalance(amount);
            return ResponseEntity.ok(accounts);
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
