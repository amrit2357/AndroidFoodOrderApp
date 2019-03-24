package com.example.orderfoodnow.Common;

import com.example.orderfoodnow.Model.User;

public class Common {
    public static User currentUser;
    
    public static String convertCodeToStatus(String code){
        if(code.equals( "0" )){
            return "Placed";
        }else if(code.equals( "1" )){
            return "on my way";
        }else if(code.equals( "2" )){
            return "Shipped";
        }else{
            return "Delivered";
        }
    }

}
