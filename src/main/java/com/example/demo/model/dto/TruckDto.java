package com.example.demo.model.dto;

public class TruckDto {

    private Long id;
    private String chassisNumber;
    private String licensePlate;

    public TruckDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "TruckDto{" +
                "id=" + id +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
