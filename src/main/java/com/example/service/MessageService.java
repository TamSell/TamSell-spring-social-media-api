package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public Message CreateMessage(Message message){
        return messageRepository.save(message);
    }

    public Integer DeleteMessage(int id){
        //Placing Extra check inside of service for readability in Controller

        if(GetMessageById(id).isPresent() == true){
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
    }

    public void UpdateMessage(Message message, int id){
        if(GetMessageById(id).isPresent() == true){
            messageRepository.save(message);
        }
    }

    public Optional<Message> GetMessageById(int id){
        return messageRepository.findById(id);
    }

    public List<Message> GetAllMessages(){
        return messageRepository.findAll();
    }

    public void GetUserMessages(int id){
        
    }
}
