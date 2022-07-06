package com.example.transportApp.model;

import com.example.transportApp.model.enums.CertificatesLevels;
import com.example.transportApp.model.enums.EmployeeType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Logistician extends Employee implements Serializable {
    @ElementCollection
    private List<CertificatesLevels> certificatesLevelsList;
    @ManyToMany
    @JoinTable(
            name = "Logistician_Route",
            joinColumns = { @JoinColumn(name = "logistician_id") },
            inverseJoinColumns = { @JoinColumn(name = "route_id") }
    )
    private Set<Route> routes=new HashSet<>();

    public Logistician(String imie, String nazwisko, LocalDate dataRozpoczeciaPracy, Double wynagrodzenie, LocalDate dataUrodzenia,List<CertificatesLevels> certificatesLevelsList) {
        super(imie, nazwisko, dataRozpoczeciaPracy, wynagrodzenie, dataUrodzenia);
        this.certificatesLevelsList = certificatesLevelsList;
    }

    public Logistician() {

    }

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.LOGISTYK;
    }

    @Override
    public String toString() {
        return "Logistician{" +
                "certificatesLevelsList=" + certificatesLevelsList +
                "} " + super.toString();
    }
}
