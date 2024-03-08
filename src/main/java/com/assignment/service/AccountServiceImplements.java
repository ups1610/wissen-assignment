package com.assignment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;
import org.springframework.stereotype.Service;

import com.assignment.Repository.AccountRepository;
import com.assignment.models.Account;
import com.assignment.models.LoginRequest;
import com.assignment.models.Transaction;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImplements implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean login(LoginRequest loginRequest) {
        String customerId = loginRequest.getCustomerId();
        String password = loginRequest.getPassword();
        Account account = accountRepository.findById(customerId).orElse(null);
        return account != null && account.getPassword().equals(password);
    }


    @Override
    public void register(Account account) 
    {
    	if(!accountRepository.existsById(account.getCustomerId()))
    		accountRepository.save(account);
    	else
    		throw new RuntimeException("Customer_Id exists");
    }

    @Override
    public boolean changePassword(String customerId, String oldPassword, String newPassword) 
    {
        Account account = accountRepository.findByAccountNo(customerId);
        if (account != null && account.getPassword().equals(oldPassword)) {
            account.setOldPassword(oldPassword);
            account.setPassword(newPassword);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean transfer(String fromAccount, String toAccount, Double amount) {
        Account senderAccount = accountRepository.findByAccountNo(fromAccount);
        Account receiverAccount = accountRepository.findByAccountNo(toAccount);

        if (senderAccount != null && receiverAccount != null && senderAccount.getBalance() >= amount) 
        {
            senderAccount.setBalance(senderAccount.getBalance() - amount);
            accountRepository.save(senderAccount);
            receiverAccount.setBalance(receiverAccount.getBalance() + amount);
            accountRepository.save(receiverAccount);

            Transaction transaction = new Transaction();
            transaction.setFromAccount(fromAccount);
            transaction.setToAccount(toAccount);
            transaction.setAmount(amount);
            transaction.setTransactionDate(LocalDateTime.now());
            TransactionalRepository.save(transaction);

            return true;
        }
        return false;
    }

    @Override
    public Double getBalance(String accountNo) {
        Account account = accountRepository.findByAccountNo(accountNo);
        return account != null ? account.getBalance() : null;
    }
    
    @Override
    public List<Account> getAccountsBelowBalance(Double balance) 
    {
        return accountRepository.findAll().stream()
                .filter(account -> account.getBalance() < balance)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAccountsAboveBalance(Double balance) 
    {
        return accountRepository.findAll().stream()
                .filter(account -> account.getBalance() > balance)
                .collect(Collectors.toList());
    }
}
