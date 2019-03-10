package com.example.orderfoodnow.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }
    // getter and setter
    public  User(){

    }


    public User(String name , String password ){
        Name = name;
        Password = password;
    }
    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
}
