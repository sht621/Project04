/*******************************************************************
***  File Name		: LoginUserMapper.java
***  Version		: V1.0
***  Designer		: 堀江咲希
***  Date		　　: 2024.06.18
***  Purpose       	: データベースから引っ張ってくる
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 堀江咲希, 2024.06.18
*/

package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.example.demo.model.LoginUserModel;

@Mapper
@Component
public interface LoginUserMapper {
	
	/****************************************************************************
     *** Method Name         : insert(LoginUserModel model)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ユーザデータベースにデータを入れる
     ****************************************************************************/
    @Insert("INSERT INTO USER(Id, Userid, Pass)"
            + "VALUES(#{Id}, #{Userid}, #{Pass})")
    int insert(LoginUserModel model);
    
    /****************************************************************************
     *** Method Name         : selectAll()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ユーザデータベースの全データをとってくる
     ****************************************************************************/
    @Select("SELECT * FROM USER")
    List<LoginUserModel> selectAll();
    
    /****************************************************************************
     *** Method Name         : select()
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ユーザデータベースのNameをとってくる
     ****************************************************************************/
    @Select("SELECT NAME FROM USER")
    List<LoginUserModel> select();
}
