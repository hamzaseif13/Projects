package main;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private  int ID;
    private int phoneNumber;
    private double owedBalance;
    private double totalPaid;
    private String email;
    private List<Visit> previousVisits;

    public Patient(String name, int ID, int phoneNumber , String email) {
        this.name = name;
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.owedBalance = 0;
        this.totalPaid = 0;
        this.email = email;
        previousVisits=new ArrayList<Visit>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getOwedBalance() {
        return owedBalance;
    }

    public void setOwedBalance(double owedBalance) {
        this.owedBalance = owedBalance;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Visit> getPreviousVisits() {
        return previousVisits;
    }

    public void setPreviousVisits(List<Visit> previousVisits) {
        this.previousVisits = previousVisits;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", phoneNumber=" + phoneNumber +
                ", owedBalance=" + owedBalance +
                ", totalPaid=" + totalPaid +
                ", email='" + email + '\'' +
                '}';
    }
}
