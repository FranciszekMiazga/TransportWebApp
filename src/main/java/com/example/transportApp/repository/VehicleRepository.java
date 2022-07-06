package com.example.transportApp.repository;

import com.example.transportApp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
    extends JpaRepository<Vehicle,Long> {
}
