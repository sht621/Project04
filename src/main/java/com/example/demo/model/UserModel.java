/*******************************************************************
***  File Name		: UserModel.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.06.18
***  Purpose       	: アノテーションを用いてデータベース処理を行う際のmodel
***
*******************************************************************/

package com.example.demo.model;

public class UserModel {
    private int id;
    private int userId;
    private String pass;
    
    /****************************************************************************
     *** Method Name         : getId()  getUserId() getPass()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : getterとして値を取り出すメソッド
     *** Return              : int, int ,String
     ****************************************************************************/
    
    /****************************************************************************
     *** Method Name         : setId()  setUserId() setPass()
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

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
    	this.userId = userId;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
}
