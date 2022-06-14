package com.example.mas_gui.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Location {
    private String city;
    private String street;
    private String postCode;
    @OneToMany(mappedBy="location")
    private List<Pack> packs = new ArrayList<>();
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Location(String city, String street, String postCode) {
        this.city = city;
        this.street = street;
        this.postCode = postCode;
    }

    public Location() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                ", packs=" + packs +
                ", id=" + id +
                '}';
    }
}
