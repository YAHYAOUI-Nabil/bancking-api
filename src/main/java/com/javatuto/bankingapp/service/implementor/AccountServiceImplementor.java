package com.javatuto.bankingapp.service.implementor;

import com.javatuto.bankingapp.dto.AccountDto;
import com.javatuto.bankingapp.mapper.AccountMapper;
import com.javatuto.bankingapp.model.Account;
import com.javatuto.bankingapp.repository.AccountRepository;
import com.javatuto.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplementor implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                                    .findById(id)
                                    .orElseThrow(() -> new RuntimeException("Account does not exist!!!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream()
                    .map(AccountMapper::mapToAccountDto)
                    .collect(Collectors.toList());
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist!!!"));
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist!!!"));
        if(account.getBalance() == 0) {
            throw new RuntimeException("You does not have balance in your account");
        } else if (amount>account.getBalance()) {
            throw new RuntimeException("You does not have sufficient balance in your account");
        } else {
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);
            Account updatedAccount = accountRepository.save(account);

            return AccountMapper.mapToAccountDto(updatedAccount);
        }

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist!!!"));
        accountRepository.deleteById(id);
    }
}
