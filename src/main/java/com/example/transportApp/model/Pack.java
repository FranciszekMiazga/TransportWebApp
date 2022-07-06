package com.example.transportApp.model;

import com.example.transportApp.model.enums.PackStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pack {
    @Column(unique = true)
    private int nrPaczki;
    private double cena;
    private PackStatus status;
    private double distanceToClient;
    @ManyToOne
    @JoinColumn(name="courier_id", nullable=false)
    private Courier courier;
    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private Sender sender;
    @ManyToOne
    @JoinColumn(name="location_id", nullable=false)
    private Location location;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Pack(int nrPaczki, double cena, PackStatus status, double distanceToClient
            , Courier courier, Sender sender, Location location) {
        this.nrPaczki = nrPaczki;
        this.cena = cena;
        this.status = status;
        this.distanceToClient = distanceToClient;
        this.courier = courier;
        this.sender = sender;
        this.location = location;
    }

    public Pack() {

    }

    public Long getId() {
        return id;
    }
    @Transient
    public double getPrice(){
        var isValidData= validateData();
        if(!isValidData)
            return -1;
        if(distanceToClient<20)
            return cena*1.1;
        else if(distanceToClient>=20 && distanceToClient<100)
            return cena*1.5;
        else
            return cena*2;
    }
    public boolean validateData(){
        if(distanceToClient<0) {
            System.out.println("Dystans do klienta jest bledny.");
            return false;
        }else if(cena<0) {
            System.out.println("Cena paczki jest bledna.");
            return false;
        }else
            return true;
    }
    @Override
    public String toString() {
        var info ="Paczka nr: "+nrPaczki + ", cena: "+getPrice()+"\n";
        if(courier!= null)
            info+= "Kurier: "+courier.getNazwisko()+" ,id: "+courier.getId();
        return info;
    }
}
