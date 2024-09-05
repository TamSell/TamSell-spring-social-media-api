package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    Optional<Message> findById(int id);

    List<Message> findByPostedBy(int postedBy);

    List<Message> findAll();

    Message save(Message message);

    boolean existsById(int id);

    void deleteById(int id);

    void delete(Message message);
}
