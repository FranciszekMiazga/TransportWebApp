package com.example.transportApp.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class ParcelLocker extends Location {
    @Column(unique = true)
    private Long idParcel;
    private int freeLockers;

    public ParcelLocker(String city, String street, String postCode, Long idParcel, int freeLockers) {
        super(city, street, postCode);
        this.idParcel = idParcel;
        this.freeLockers = freeLockers;
    }

    public ParcelLocker() {

    }

    public Long getIdParcel() {
        return idParcel;
    }

    public void setIdParcel(Long idParcel) {
        this.idParcel = idParcel;
    }

    public int getFreeLockers() {
        return freeLockers;
    }

    public void setFreeLockers(int freeLockers) {
        this.freeLockers = freeLockers;
    }

    @Override
    public String toString() {
        return "ParcelLocker{" +
                "idParcel=" + idParcel +
                ", freeLockers=" + freeLockers +
                "} " + super.toString();
    }
}
