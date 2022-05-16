package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class TruckModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "truck_seq")
    @SequenceGenerator(name = "truck_seq", sequenceName = "truck_id_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String chassisNumber;
    @Column(unique = true)
    private String licensePlate;

    public TruckModel(){}

    public Long getId() {
        return id;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String toString() {
        return "TruckModel{" +
                "id=" + id +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
