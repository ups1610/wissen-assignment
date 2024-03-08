package com.assignment.service;

import java.util.List;

import com.assignment.models.Account;
import com.assignment.models.LoginRequest;

public interface AccountService {
    boolean login(LoginRequest login);
    void register(Account account);
    boolean changePassword(String customerId, String oldPassword, String newPassword);
    boolean transfer(String fromAccount, String toAccount, Double amount);
    Double getBalance(String accountNo);
    List<Account> getAccountsBelowBalance(Double balance);
    List<Account> getAccountsAboveBalance(Double balance);
}

