package com.assignment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.models.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
	Account findByAccountNo(String accountNo);
}
