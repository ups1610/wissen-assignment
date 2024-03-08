package com.assignment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.models.Account;
import com.assignment.models.Transaction;

public interface TranscationRepository extends JpaRepository<Transaction, String>{
	Account findByAccountNo(String accountNo);
}
