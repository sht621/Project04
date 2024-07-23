/*******************************************************************
***  File Name		: PatmentModel.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.06.18
***  Purpose       	: アノテーションを用いてデータベース処理を行う際のmodel
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 東野魁耶, 2024.06.18
*
*/

package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class PaymentModel {
	
	private int id;
    private int income;
    private int spend;
    private int day;
    private String itemId;
    private int userId;
    
    
    /****************************************************************************
     *** Method Name         : getId()  getUserId() getIncome() getSpend() getday() getItemId()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : getterとして値を取り出すメソッド
     *** Return              : int, int , int, int, int, String
     ****************************************************************************/
    
    /****************************************************************************
     *** Method Name         : setId()  setUserId() setIncome() setSpend() setday() setItemId()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : setterとして値を取り出すメソッド
     *** Return              : 返り値なし
     ****************************************************************************/
    
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
