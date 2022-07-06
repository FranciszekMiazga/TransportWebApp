package com.example.transportApp.repository;

import com.example.transportApp.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderRepository
        extends JpaRepository<Sender,Long> {
}
