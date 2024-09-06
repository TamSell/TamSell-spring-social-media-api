package com.example.controller;


import org.jboss.logging.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;


    @RequestMapping("register")
    public ResponseEntity registerAccount(@RequestBody Account outBody){
        if(accountService.CheckAccountByUsername(outBody.getUsername()) == true){
        return ResponseEntity.status(409).body("Error User Registration");
        }

        accountService.CreateAccount(outBody);
        return ResponseEntity.status(200).body(outBody);
    }


    @RequestMapping("login")
    public ResponseEntity loginToAccount(@RequestBody Account outBody){
        Optional<Account> checkAccount = accountService.FindByUsernameAndPassword(outBody.getUsername(), outBody.getPassword());
        if(checkAccount.isPresent())
            return ResponseEntity.status(200).body(checkAccount);
        
        
        return ResponseEntity.status(401).body("Username or Password Incorrect");
    }

    @PostMapping("messages")
    public ResponseEntity postMessage(@RequestBody Message outBody){
        if(outBody.getMessageText().length() > 254 || outBody.getMessageText().length() <= 0)
            return ResponseEntity.status(400).body("Message is of incorrect size, must be above 0 or below 255 characters");

        if(accountService.CheckAccountById(outBody.getPostedBy()) == false)
            return ResponseEntity.status(400).body("User Does not Exists");

        return ResponseEntity.status(200).body(messageService.CreateMessage(outBody));
    }

    @GetMapping("messages")
    public ResponseEntity getMessage(){
        return ResponseEntity.status(200).body(messageService.GetAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity getMessageById(@PathVariable("messageId") int id){
        if(messageService.GetMessageById(id).isPresent())
        return ResponseEntity.status(200).body(messageService.GetMessageById(id));

        return ResponseEntity.status(200).body("");
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity deleteMessage(@PathVariable("messageId") int id){
        return ResponseEntity.status(200).body(messageService.DeleteMessage(id));
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity updateMessage(@RequestBody Message outBody, @PathVariable("messageId") int id){
        String outMessageText = outBody.getMessageText();
        if(outMessageText.length() <= 0 || outMessageText.length() > 255) {
            return ResponseEntity.status(400).body("Message is of incorrect length");
        }

        Optional<Message> optMessage = messageService.GetMessageById(id);

        if(!optMessage.isPresent()) {
            return ResponseEntity.status(400).body("This message cannot be updated to databse as it does not exist");
        }

        Message outMessage = optMessage.get();
        outMessage.setMessageText(outMessageText);
        messageService.CreateMessage(outMessage);
        return ResponseEntity.status(200).body(1);
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity getMessageByAccount(@PathVariable("accountId") int id){

        return ResponseEntity.status(200).body(messageService.GetMessageByUserId(id));
    }
}
