package com.example.hustle;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String DOB;
    private String password;


    public User() {
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String phone, String DOB, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.DOB = DOB;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDOB() {
        return DOB;
    }

    public String getPassword() {
        return password;
    }
}


