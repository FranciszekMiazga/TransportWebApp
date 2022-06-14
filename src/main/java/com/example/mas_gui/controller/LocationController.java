package com.example.mas_gui.controller;

import com.example.mas_gui.exceptions.ItemNotFoundException;
import com.example.mas_gui.model.Location;
import com.example.mas_gui.model.Pack;
import com.example.mas_gui.model.Sender;
import com.example.mas_gui.service.LocationService;
import com.example.mas_gui.service.PackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations(){
        List<Location> locations=locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateSender(@PathVariable Long id, @RequestBody Location newLocation) throws ItemNotFoundException {
        Location location= locationService.updateLocation(newLocation,id);
        return new ResponseEntity<>(location,HttpStatus.OK);
    }
}
