/*******************************************************************
***  File Name		: MonthModel.java
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

public class MonthModel {
	private int id;
    private int target;
    private int spendSum;
    private int differ;
    private int month;
    private String itemId;
    private int userId;
    
    
    /****************************************************************************
     *** Method Name         : getId()  getUserId() getTarget() getSpendSum() getMonth() getDiffer getItemId()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : getterとして値を取り出すメソッド
     *** Return              : int, int , int, int, int, int, String
     ****************************************************************************/
    
    /****************************************************************************
     *** Method Name         : setId()  setUserId() setTarget() setSpendSum() setMonth() setDiffer setItemId()
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
