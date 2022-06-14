package com.example.mas_gui.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class IndividualClient extends Sender{
    private String lastName;
    private LocalDate dateOfBirth;
    private static final int discLimit=3;
    @Getter
    @Transient
    private int counter=0;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public IndividualClient(String phoneNumber, String email, String lastName, LocalDate dateOfBirth) throws Exception {
        super(phoneNumber, email);
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public IndividualClient() {

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
                var disc= pack.getCena()*(discountValue/100);
                pack.setCena(pack.getCena()-disc);
                System.out.println("Discount is granted. Price now: "+pack.getPrice());
                counter++;
            }
        }
    }

    @Override
    public String toString() {
        return "IndividualClient{" +
                "lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                "} " + super.toString();
    }
}
