package com.example.mas_gui.repository;

import com.example.mas_gui.model.IndividualClient;
import com.example.mas_gui.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderRepository
        extends JpaRepository<Sender,Long> {
}
