package com.example.demo.model;

public class MonthModel {
	private int id;
    private int target;
    private int spendSum;
    private int differ;
    private int month;
    private String itemId;
    private int userId;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getTarget() {
        return target;
    }
    
    public void setTarget(int target) {
    	this.target = target;
    }

    public int getSpendSum() {
        return spendSum;
    }
    
    public void setSpendSum(int spendSum) {
        this.spendSum = spendSum;
    }
    
    public int getDiffer() {
        return differ;
    }
    
    public void setDiffer(int differ) {
        this.differ = differ;
    }
    
    public int getMonth() {
        return month;
    }
    
    public void setMonth(int month) {
        this.month = month;
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
