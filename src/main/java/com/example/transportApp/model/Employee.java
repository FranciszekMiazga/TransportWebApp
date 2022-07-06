package com.example.transportApp.model;

import com.example.transportApp.model.enums.EmployeeType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    private String imie;
    private String nazwisko;
    private Double wynagrodzenie;
    private LocalDate dataRozpoczeciaPracy;
    private LocalDate dataUrodzenia;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Employee(String imie, String nazwisko, LocalDate dataRozpoczeciaPracy, Double wynagrodzenie, LocalDate dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataRozpoczeciaPracy = dataRozpoczeciaPracy;
        this.wynagrodzenie = wynagrodzenie;
        this.dataUrodzenia = dataUrodzenia;
    }

    public Employee() {

    }

    public void increaseSalary(double percent){
        if(percent<=0 || getWynagrodzenie() == null) {
            System.out.println("Wpisana wartość nie może być ujemna lub wynagrodzenie jest null.");
            return;
        }
        var salary= getWynagrodzenie();
        var increase=salary*(percent/100);
        var increasedSalary= salary+increase;
        setWynagrodzenie(increasedSalary);
    }
    public void increaseSalary(float value){
        if(value<=0 || getWynagrodzenie() == null) {
            System.out.println("Wpisana wartość nie może być ujemna lub wynagrodzenie jest null.");
            return;
        }
        var salary= getWynagrodzenie();
        var increasedSalary=salary+value;
        setWynagrodzenie(increasedSalary);
    }
    public EmployeeType getEmployeeType() {
        return EmployeeType.BEZ_SPECYFIKACJI;
    }

    public Long getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public LocalDate getDataRozpoczeciaPracy() {
        return dataRozpoczeciaPracy;
    }

    public void setDataRozpoczeciaPracy(LocalDate dataRozpoczeciaPracy) {
        this.dataRozpoczeciaPracy = dataRozpoczeciaPracy;
    }

    public Double getWynagrodzenie() {
        return wynagrodzenie;
    }

    public void setWynagrodzenie(Double wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(LocalDate dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    @Override
    public String toString() {
        return "Pracownik{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", dataRozpoczeciaPracy=" + dataRozpoczeciaPracy +
                ", wynagrodzenie=" + wynagrodzenie +
                ", dataUrodzenia=" + dataUrodzenia +
                "} "+this.hashCode();
    }
}
