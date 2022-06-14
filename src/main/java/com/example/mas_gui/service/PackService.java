package com.example.mas_gui.service;

import com.example.mas_gui.exceptions.ItemNotFoundException;
import com.example.mas_gui.model.Pack;
import com.example.mas_gui.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PackService {
    private final PackRepository packRepository;

    @Autowired
    public PackService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }
    public List<Pack> getAllPacks(){
        return packRepository.findAll();
    }
    public Pack getPackById(Long id) throws ItemNotFoundException {
        return packRepository.findById(id)
                .orElseThrow(()->new ItemNotFoundException("Pack with id "+id+" not found."));
    }
    public Pack addPack(Pack newPack){
        newPack.setId(UUID.randomUUID().getMostSignificantBits());
        return packRepository.save(newPack);
    }
    public Pack updatePack(Pack newPack, Long id) throws ItemNotFoundException {
        return packRepository.findById(id)
                .map(pack->{
                    pack.setCena(newPack.getCena());
                    pack.setNrPaczki(newPack.getNrPaczki());
                    pack.setStatus(newPack.getStatus());
                    pack.setCourier(newPack.getCourier());
                    pack.setDistanceToClient(newPack.getDistanceToClient());
                    pack.setSender(newPack.getSender());
                    pack.setLocation(newPack.getLocation());

                    return packRepository.save(pack);
                })
                .orElseThrow(()->new ItemNotFoundException("Pack with id "+id+" not found."));
    }
    public void deletePack(Long id){
        packRepository.deleteById(id);
    }
}
