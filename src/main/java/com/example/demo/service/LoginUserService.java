/*******************************************************************
***  File Name		: LoginUserService.java
***  Version		: V1.0
***  Designer		: 堀江咲希
***  Date		　　: 2024.06.18
***  Purpose       	: データベースとのやり取り
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 堀江咲希, 2024.06.18
*/

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.LoginUserMapper;
import com.example.demo.model.LoginUserModel;

import jakarta.servlet.http.HttpServlet;

@Service
public class LoginUserService extends HttpServlet{

    private final LoginUserMapper dao;
    
    /****************************************************************************
     *** Method Name         : LoginUserService(LoginUserMapper dao)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     ****************************************************************************/
    public LoginUserService(LoginUserMapper dao) {
        this.dao = dao;
    }
    
    /****************************************************************************
     *** Method Name         : insert(LoginUserModel user)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : LoginUserModelのinsertメソッドの実行
     ****************************************************************************/
    public boolean insert(LoginUserModel user) {
        return dao.insert(user) > 0;
    }
    
    /****************************************************************************
     *** Method Name         : selectAll()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : データを取得する
     *** Return              : ユーザデータベースの全データ
     ****************************************************************************/
    public List<LoginUserModel> selectAll() {
        return dao.selectAll();
    }
    
    /****************************************************************************
     *** Method Name         : select()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : データを取得する
     *** Return              : ユーザデータベースのName
     ****************************************************************************/
    public List<LoginUserModel>select(){
    	
    	return dao.select();
    }
    
    /****************************************************************************
     *** Method Name         : update()
     *** Designer            : 堀江咲希
     *** Date                : 2024.07.09
     *** Function            : データを更新
     *** Return              : 更新したデータ
     ****************************************************************************/
    public boolean update(LoginUserModel user) {
        return dao.update(user) > 0;
    }

    
    
    
}
