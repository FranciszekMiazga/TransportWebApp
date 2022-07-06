package com.example.transportApp.controller;

import com.example.transportApp.exceptions.ItemNotFoundException;
import com.example.transportApp.model.Location;
import com.example.transportApp.service.LocationService;
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
