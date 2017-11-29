package com.example.joseph.mobileproject;

/**
 * Created by Joseph on 11/17/17.
 */
public class Expenses {

    String name;
    String amount;
    String category;
    String date;

    public Expenses(String name, String amount, String category, String date){
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }
    public String getCategory()
    {
        return category;
    }
    public String getAmount()
    {
        return amount;
    }
    public String getDate()
    {
        return date;
    }

}

