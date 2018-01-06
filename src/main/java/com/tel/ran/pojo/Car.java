package com.tel.ran.pojo;

public class Car {
    private String brand;
    private String model;
    private Integer year;
    private Integer killometerRun;

    public Car(String brand, String model, Integer year, Integer killometerRun) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.killometerRun = killometerRun;
    }

    public Car() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getKillometerRun() {
        return killometerRun;
    }

    public void setKillometerRun(Integer killometerRun) {
        this.killometerRun = killometerRun;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", killometerRun=" + killometerRun +
                '}';
    }
}
