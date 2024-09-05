package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    Optional<Account> findById(int Id);

    List<Account> findAll();

    Optional<Account> findByUsernameAndPassword(String username, String password);

    Account save(Account account);

    boolean existsByUsername(String username);

    boolean existsById(int id);

    void deleteById(int id);

    void delete(Account account);

}
