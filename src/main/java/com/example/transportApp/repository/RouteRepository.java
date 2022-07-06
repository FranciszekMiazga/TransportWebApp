package com.example.transportApp.repository;

import com.example.transportApp.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository
    extends JpaRepository<Route,Long> {
}
