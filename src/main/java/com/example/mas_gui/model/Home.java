package com.example.mas_gui.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Home extends Location{
    private int bulidingNumber;
    private Integer flatNumber;

    public Home(String city, String street, String postCode, int bulidingNumber, Integer flatNumber) {
        super(city, street, postCode);
        this.bulidingNumber = bulidingNumber;
        this.flatNumber = flatNumber;
    }

    public Home() {

    }

    @Override
    public String toString() {
        return "Home{" +
                "bulidingNumber=" + bulidingNumber +
                ", flatNumber=" + flatNumber +
                "} " + super.toString();
    }
}
