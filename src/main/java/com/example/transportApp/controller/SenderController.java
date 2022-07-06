package com.example.transportApp.controller;

import com.example.transportApp.exceptions.ItemNotFoundException;
import com.example.transportApp.model.Sender;
import com.example.transportApp.service.SenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/sender")
public class SenderController {
    private final SenderService senderService;

    public SenderController(SenderService senderService) {
        this.senderService = senderService;
    }
    @GetMapping
    public ResponseEntity<List<Sender>> getAllSenders(){
        List<Sender> senders=senderService.getAllSenders();
        return new ResponseEntity<>(senders, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Sender> getSender(@PathVariable Long id) throws ItemNotFoundException {
        Sender sender= senderService.getSenderById(id);
        return new ResponseEntity<>(sender,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addSender(@RequestBody Sender newSender){
        Sender sender= senderService.addSender(newSender);
        return new ResponseEntity<>("Pomyslnie dodano uzytkownika.",HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Sender> updateSender(@PathVariable Long id, @RequestBody Sender newSender) throws ItemNotFoundException {
        Sender sender= senderService.updateSender(newSender,id);
        return new ResponseEntity<>(sender,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSender(@PathVariable Long id){
        senderService.deleteSender(id);
        return new ResponseEntity<>("Pomyslnie usunieto uzytkownika.",HttpStatus.OK);
    }
}
