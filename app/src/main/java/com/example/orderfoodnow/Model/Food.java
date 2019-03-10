package com.example.orderfoodnow.Model;

public class Food {
    private String Name , Description , Image,Price,Discount,MenuId;

    public Food(String name, String description, String image, String price, String discount, String menuId) {
        Name = name;
        Description = description;
        Image = image;
        Price = price;
        Discount = discount;
        MenuId = menuId;
    }

    public Food() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
