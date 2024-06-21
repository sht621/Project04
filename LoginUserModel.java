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
    public String Id;
    public String Name;
    public String Password;
    
    /****************************************************************************
     *** Method Name         : getId()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ID取得
     *** Return              : ID
     ****************************************************************************/
    public String getId() {
        return Id;
    }
    
    /****************************************************************************
     *** Method Name         : setId()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ID挿入
     ****************************************************************************/
    public void setId(String id) {
        Id = id;
    }
    
    /****************************************************************************
     *** Method Name         : getName()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Name取得
     *** Return              : Name
     ****************************************************************************/
    public String getName() {
        return Name;
    }
    
    /****************************************************************************
     *** Method Name         : setName()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Name挿入
     ****************************************************************************/
    public void setName(String name) {
        Name = name;
    }
    
    /****************************************************************************
     *** Method Name         : getPassword()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Password取得
     *** Return              : Password
     ****************************************************************************/
    public String getPassword() {
        return Password;
    }
    
    /****************************************************************************
     *** Method Name         : setPassword()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : Password挿入
     ****************************************************************************/
    public void setPassword(String Password) {
        this.Password = Password;
    }
}
