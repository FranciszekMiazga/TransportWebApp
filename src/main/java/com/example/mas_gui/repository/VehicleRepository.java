package com.example.mas_gui.repository;

import com.example.mas_gui.model.Pack;
import com.example.mas_gui.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
    extends JpaRepository<Vehicle,Long> {
}
