package com.example.transportApp.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Route {
    private double dlugosctrasy;
    private LocalDate czasPrzejazdu;
    @ManyToOne
    @JoinColumn(name="courier_id", nullable=false)
    private Courier courier;
    @ManyToOne
    @JoinColumn(name="vehicle_id", nullable=false)
    private Vehicle vehicle;
    @ManyToMany
    private Set<Logistician> logisticians;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Route(double dlugosctrasy, LocalDate czasPrzejazdu, Courier courier, Vehicle vehicle) {
        this.dlugosctrasy = dlugosctrasy;
        this.czasPrzejazdu = czasPrzejazdu;
        this.courier = courier;
        this.vehicle = vehicle;
    }

    public Route() {

    }
    @Override
    public String toString() {
        String info = "";
        info+="Route{" +
                "dlugosctrasy=" + dlugosctrasy +
                ", czasPrzejazdu=" + czasPrzejazdu+
                ", vehicle=" + vehicle + "} ";
        return info;
    }
    public Long getId() {
        return id;
    }
}
