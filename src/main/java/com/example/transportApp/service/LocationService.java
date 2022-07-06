package com.example.transportApp.service;

import com.example.transportApp.exceptions.ItemNotFoundException;
import com.example.transportApp.model.Location;
import com.example.transportApp.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }
    public Location updateLocation(Location newLocation, Long id) throws ItemNotFoundException {
        return locationRepository.findById(id)
                .map(location->{
                    location.setCity(newLocation.getCity());
                    location.setPacks(newLocation.getPacks());
                    location.setStreet(newLocation.getStreet());
                    location.setPostCode(newLocation.getPostCode());
                    //pack.setLastName(newSender.getLastName());
                    //pack.setDateOfBirth(newSender.getDateOfBirth());

                    return locationRepository.save(location);
                })
                .orElseThrow(()->new ItemNotFoundException("Location with id "+id+" not found."));
    }
}
