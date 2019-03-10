package com.example.orderfoodnow.Model;

import java.util.PriorityQueue;

public class Order {
    private  String ProductName;
    private String  ProductId;
    private String Quantity;
    private  String Price;
    private String Discount;

    public Order( String productName, String productId, String quantity,String price, String discount) {

        ProductName = productName;
        ProductId = productId;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public Order() {
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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
}
