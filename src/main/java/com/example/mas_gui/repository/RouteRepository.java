package com.example.mas_gui.repository;

import com.example.mas_gui.model.Pack;
import com.example.mas_gui.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository
    extends JpaRepository<Route,Long> {
}
