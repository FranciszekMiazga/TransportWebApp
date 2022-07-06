package com.example.transportApp.controller;

import com.example.transportApp.exceptions.ItemNotFoundException;
import com.example.transportApp.model.Pack;
import com.example.transportApp.service.PackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/pack")
public class PackController {
    private final PackService packService;

    public PackController(PackService packService) {
        this.packService = packService;
    }
    @GetMapping
    public ResponseEntity<List<Pack>> getAllPacks(){
        List<Pack> packs=packService.getAllPacks();
        return new ResponseEntity<>(packs, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pack> getPack(@PathVariable Long id) throws ItemNotFoundException {
        Pack pack= packService.getPackById(id);
        return new ResponseEntity<>(pack,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addPack(@RequestBody Pack newPack){
        Pack pack= packService.addPack(newPack);
        return new ResponseEntity<>("Pomyslnie dodano uzytkownika.",HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Pack> updatePack(@PathVariable Long id, @RequestBody Pack newPack) throws ItemNotFoundException {
        Pack pack= packService.updatePack(newPack,id);
        return new ResponseEntity<>(pack,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePack(@PathVariable Long id){
        packService.deletePack(id);
        return new ResponseEntity<>("Pomyslnie usunieto uzytkownika.",HttpStatus.OK);
    }

}
