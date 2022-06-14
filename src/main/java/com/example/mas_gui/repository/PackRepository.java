package com.example.mas_gui.repository;

import com.example.mas_gui.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository
    extends JpaRepository<Pack,Long> {
}
