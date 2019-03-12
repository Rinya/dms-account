package ru.alfastrah.account.sber.model;

import ru.alfastrah.account.sber.constants.Gender;

import java.io.Serializable;
import java.time.LocalDate;

public class InsuredRelative implements Serializable {
    private String surname;
    private String name;
    private String patronymic;
    private Gender gender;
    private Passport passport;
    /***
     * Степень родства
     */
    private String degree;
    private LocalDate birthDate;
    private Address address;
    private String phone;
    private String attachCity;

    public InsuredRelative() {
        this.passport = new Passport();
        this.address = new Address();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAttachCity() {
        return attachCity;
    }

    public void setAttachCity(String attachCity) {
        this.attachCity = attachCity;
    }

    @Override
    public String toString() {
        return "InsuredRelative{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", gender=" + gender +
                ", passport=" + passport +
                ", degree='" + degree + '\'' +
                ", birthDate=" + birthDate +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", attachCity='" + attachCity + '\'' +
                '}';
    }
}
