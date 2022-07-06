package com.example.transportApp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
public class Vehicle {
    private double przebieg;
    private int rocznik;
    private boolean czySprawny;
    private String opisTechniczny;
    @Column(unique = true)
    private String numerVIN;
    private final static String kolorPojazdu = "niebieski";
    public final static float minAge = 2000;
    public final static float maxAge = LocalDate.now().getYear();

    private static Set<Route> allRoutes = new HashSet<>();
    private static Set<String> allVins = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL,mappedBy="vehicle")
    private List<Route> routes = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "Vehicle_Courier",
            joinColumns = { @JoinColumn(name = "vehicle_id") },
            inverseJoinColumns = { @JoinColumn(name = "courier_id") }
    )
    private Set<Courier> couriers=new HashSet<>();
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Vehicle(double przebieg, int rocznik, boolean czySprawny, String opisTechniczny, String numerVIN) throws Exception {
        this.przebieg = przebieg;
        setRocznik(rocznik);
        this.czySprawny = czySprawny;
        this.opisTechniczny = opisTechniczny;
        setNumerVIN(numerVIN);
    }

    public Vehicle() {

    }
    public void addCourier(Courier courier) {
        if(!couriers.contains(courier)){
            couriers.add(courier);
            courier.addVehicleQualif(this);
        }
    }
    public void setNumerVIN(String numerVIN) throws Exception {
        if(allVins.contains(numerVIN)){
            throw new Exception("Naruszony wiezy unique. Wartość VIN "+numerVIN+" już istnieję.");
        }
        allVins.add(numerVIN);
        this.numerVIN = numerVIN;
    }

    public void setRocznik(int rocznik) throws Exception {
        if(rocznik<minAge || rocznik >maxAge){
            throw new Exception("Rocznik musi być pomiędzy 2000 i 2022 rokiem.");
        }
        this.rocznik = rocznik;
    }

    @Override
    public String toString() {
        String info ="Vehicle: VIN: "+ numerVIN+", rocznik: "+rocznik;
        for(Courier courier: couriers){
            info+= "\nKuriers vehicle: "+courier.getNazwisko()+"\n";
        }
        for(Route route : routes) {
            info += "Kursy vehicle: " + route.getDlugosctrasy() + "\n";
        }
        return info;
    }
    public Long getId() {
        return id;
    }
}
