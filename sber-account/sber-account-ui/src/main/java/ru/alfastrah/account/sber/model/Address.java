package ru.alfastrah.account.sber.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Address implements Serializable {
    private String city;
    private String area;
    private String street;
    private String house;
    private String building;
    private String appartment;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getAppartment() {
        return appartment;
    }

    public void setAppartment(String appartment) {
        this.appartment = appartment;
    }

    public String getAddress() {
        return Stream.of(area, city, street, house, building, appartment)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", building='" + building + '\'' +
                ", appartment='" + appartment + '\'' +
                '}';
    }
}
