package com.example.orderfoodnow.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String isStaff;
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
        isStaff = false;
    }
     public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
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
