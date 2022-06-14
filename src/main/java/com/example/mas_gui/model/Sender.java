package com.example.mas_gui.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Sender{
    private String phoneNumber;
    private String email;
    @Getter
    @Transient
    private int packsAmount;
    @OneToMany(fetch = FetchType.EAGER,mappedBy="sender")
    private List<Pack> packs = new ArrayList<>();
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    public Sender(String phoneNumber, String email) throws Exception {
        setPhoneNumber(phoneNumber);
        this.email = email;
    }

    public Sender() {

    }

    abstract void makeDiscount(float discountValue, int packNumber);

    public void setPhoneNumber(String phoneNumber) throws Exception {
        if(phoneNumber.length()!=9){
            throw new Exception("This is not valid phone number in PL. It must have 9 numbers.");
        }
        try{
            var parsedPhone = Long.parseLong(phoneNumber);
        }catch(Exception e){
            throw new Exception("It is not valid phone number. Must contains only numbers.");
        }
        this.phoneNumber = phoneNumber;
    }
    @Transient
    public int getPacksAmount() {
        return packs.size();
    }

    @Override
    public String toString() {
        String info = "Nadawca: "+email;
        for(Pack pack : packs) {
            info += " ,Paczka nr " + pack.getNrPaczki() + "\n";
        }
        return info;
    }
}
