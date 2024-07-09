package com.example.demo.model;

public class PaymentModel {
	
	private int id;
    private int income;
    private int spend;
    private int day;
    private String itemId;
    private int userId;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }
    
    public void setIncome(int income) {
    	this.income = income;
    }

    public int getSpend() {
        return spend;
    }
    
    public void setSpend(int spend) {
        this.spend = spend;
    }
    
    public int getDay() {
        return day;
    }
    
    public void setDay(int day) {
        this.day = day;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
