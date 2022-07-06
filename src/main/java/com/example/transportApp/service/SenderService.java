package com.example.transportApp.service;

import com.example.transportApp.exceptions.ItemNotFoundException;
import com.example.transportApp.model.Sender;
import com.example.transportApp.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SenderService {
    private final SenderRepository senderRepository;

    @Autowired
    public SenderService(SenderRepository senderRepository) {
        this.senderRepository = senderRepository;
    }
    public List<Sender> getAllSenders(){
        return senderRepository.findAll();
    }
    public Sender getSenderById(Long id) throws ItemNotFoundException {
        return senderRepository.findById(id)
                .orElseThrow(()->new ItemNotFoundException("Sender with id "+id+" not found."));
    }
    public Sender addSender(Sender newSender){
        newSender.setId(UUID.randomUUID().getMostSignificantBits());
        return senderRepository.save(newSender);
    }
    public Sender updateSender(Sender newSender, Long id) throws ItemNotFoundException {
        return senderRepository.findById(id)
                .map(pack->{
                    try {
                        pack.setPhoneNumber(newSender.getPhoneNumber());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pack.setEmail(newSender.getEmail());
                    pack.setPacks(newSender.getPacks());
                    //pack.setLastName(newSender.getLastName());
                    //pack.setDateOfBirth(newSender.getDateOfBirth());

                    return senderRepository.save(pack);
                })
                .orElseThrow(()->new ItemNotFoundException("Sender with id "+id+" not found."));
    }
    public void deleteSender(Long id){
        senderRepository.deleteById(id);
    }
}
