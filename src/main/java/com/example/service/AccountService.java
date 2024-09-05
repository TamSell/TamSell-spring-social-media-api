package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private  AccountRepository accountRepository;


    public Account CreateAccount(Account account){
        return accountRepository.save(account);
    }

    public void UpdateAccount(Account account){
        accountRepository.save(account);
    }

    public boolean CheckAccountByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    public boolean CheckAccountById(int id){
        return accountRepository.existsById(id);
    }

    public void GetAllAccount(){
        accountRepository.findAll();
    }

    public Optional<Account> FindByUsernameAndPassword(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }


    public void LoginAccount(){

    }
}
