package com.example.transportApp.repository;

import com.example.transportApp.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository
    extends JpaRepository<Pack,Long> {
}
