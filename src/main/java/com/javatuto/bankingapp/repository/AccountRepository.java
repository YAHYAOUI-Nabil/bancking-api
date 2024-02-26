package com.javatuto.bankingapp.repository;

import com.javatuto.bankingapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {
}
