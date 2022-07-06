package com.example.transportApp.repository;

import com.example.transportApp.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository
    extends JpaRepository<Location,Long> {
}
