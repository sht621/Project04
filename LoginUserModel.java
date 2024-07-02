/*******************************************************************
***  File Name		: LoginUserModel.java
***  Version		: V1.0
***  Designer		: 堀江咲希
***  Date		　　: 2024.06.18
***  Purpose       	: データベースに挿入する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 堀江咲希, 2024.06.18
*/

package com.example.demo.model;

public class LoginUserModel {
    public int Id;
    public String Userid;
    public String Pass;
    
    /****************************************************************************
     *** Method Name         : getId()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ID取得
     *** Return              : ID
     ****************************************************************************/
    public int getId() {
        return Id;
    }
    
    /****************************************************************************
     *** Method Name         : setId()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ID挿入
     ****************************************************************************/
    public void setId(int id) {
        Id = id;
    }
    
    /****************************************************************************
     *** Method Name         : getUserid()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Userid取得
     *** Return              : Userid
     ****************************************************************************/
    public String getUserid() {
        return Userid;
    }
    
    /****************************************************************************
     *** Method Name         : setUserid()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Userid挿入
     ****************************************************************************/
    public void setUserid(String name) {
        Userid = name;
    }
    
    /****************************************************************************
     *** Method Name         : getPass()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Pass取得
     *** Return              : Pass
     ****************************************************************************/
    public String getPass() {
        return Pass;
    }
    
    /****************************************************************************
     *** Method Name         : setPass()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Pass挿入
     ****************************************************************************/
    public void setPass(String Password) {
        this.Pass = Password;
    }
}
