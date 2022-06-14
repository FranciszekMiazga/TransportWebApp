package com.example.mas_gui.model;

import com.example.mas_gui.model.enums.EmployeeType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Data
public class Courier extends Employee{
    private LocalDate dataUplywuPrawaJazdy;
    @OneToMany(mappedBy="courier")
    private List<Pack> packs= new ArrayList<>();
    @OneToMany(mappedBy="courier")
    private List<Route> routes = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "couriers")
    private Set<Vehicle> vehicles = new HashSet<>();
    @Transient
    private Map<String, Vehicle> vehicleQualif = new TreeMap<>();

    public Courier(String imie, String nazwisko, LocalDate dataRozpoczeciaPracy,
                   double wynagrodzenie, LocalDate dataUrodzenia,
                   LocalDate dataUplywuPrawaJazdy) {
        super(imie, nazwisko, dataRozpoczeciaPracy, wynagrodzenie, dataUrodzenia);
        this.dataUplywuPrawaJazdy = dataUplywuPrawaJazdy;
    }

    public Courier() {
        super();
    }

    public void addVehicleQualif(Vehicle vehicle) {
        if(!vehicleQualif.containsKey(vehicle.getNumerVIN())) {
            vehicleQualif.put(vehicle.getNumerVIN(), vehicle);
            vehicle.addCourier(this);
        }
    }
    public Vehicle findVehicleQualif(String numerVIN) throws Exception {
        if(!vehicleQualif.containsKey(numerVIN)) {
            throw new Exception("Unable to find a vehicle with efficient: " + numerVIN);
        }
        return vehicleQualif.get(numerVIN);
    }


    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.KURIER;
    }
    @Transient
    public int getWiek() {
        LocalDate currentDate = LocalDate.now();
        var dataUrodzenia=getDataUrodzenia();

        if ((dataUrodzenia != null) && (currentDate != null))
            return Period.between(dataUrodzenia, currentDate).getYears();
        else
            return -1;
    }

    @Override
    public String toString() {
        var info = "Kurier: " + getNazwisko() + "\n";
        for(Pack pack : packs) {
            info += "Paczka nr" + pack.getNrPaczki() +" ,id: "+pack.getId()+ "\n";
        }
        /*
        for (String name: vehicleQualif.keySet()) {
            String key = name;
            String value = vehicleQualif.get(name).toString();
            info+= "Qualifier: "+key+", "+value+"\n";
        }*/

        return info;
    }
}
