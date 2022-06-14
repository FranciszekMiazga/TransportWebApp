package com.example.mas_gui.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Firm extends Sender{
    private String name;
    private String REGON;
    private String NIP;
    private static final int discLimit=5;
    private int counter=0;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Firm(String phoneNumber, String email, String name, String REGON, String NIP) throws Exception {
        super(phoneNumber, email);
        this.name = name;
        this.REGON = REGON;
        this.NIP = NIP;
    }

    public Firm() {

    }

    @Override
    void makeDiscount(float discountValue, int packNumber) {
        if(counter>=discLimit) {
            System.out.println("This operation limit of "+discLimit+" is used. Buy premium plan.");
            return;
        }
        if(discountValue<=0 && discountValue>=100){
            System.out.println("Bad discount value must be 1 to 99.");
            return;
        }
        for(Pack pack: getPacks()){
            if(pack.getNrPaczki()==packNumber /*&& !pack.isCzyDostarczona()*/){
                var ds= discountValue/100;
                double disc= pack.getCena()*ds;
                pack.setCena(pack.getCena()-disc);
                System.out.println("Discount is granted. Price now: "+pack.getPrice());
                counter++;
            }
        }
    }
    @Override
    public String toString() {
        return "Firm{" +
                "name='" + name + '\'' +
                ", REGON='" + REGON + '\'' +
                ", NIP='" + NIP + '\'' +
                "} " + super.toString();
    }

    public Long getId() {
        return id;
    }
}
