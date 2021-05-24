package com.example.project1;

public class Persons {
    private  String email,password,name,city,gender,birthdate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Persons(String email, String password, String name, String city, String gender, String birthdate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.birthdate = birthdate;


    }
}
