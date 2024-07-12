/*******************************************************************
***  File Name        : GraphMapper.java
***  Version          : V1.1
***  Designer         : 保泉 雄祐, 菅 匠汰
***  Date             : 2024.07.07
***  Purpose          : データベースからグラフ用データを取得する
***
*******************************************************************/

package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.MonthModel;

@Mapper
public interface GraphMapper {

    /****************************************************************************
    *** Method Name         : selectGraphData
    *** Designer            : 保泉 雄祐
    *** Date                : 2024.07.07
    *** Function            : 指定されたユーザーIDと年月のグラフデータを取得する
    *** Return              : 月別データのリスト
    ****************************************************************************/
    @Select("SELECT * FROM MONTH WHERE USERID = #{userId} AND MONTH = #{yearMonth}")
    List<MonthModel> selectGraphData(int userId, int yearMonth);
    
    
    /****************************************************************************
    *** Method Name         : getExpenseData()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.04
    *** Function            : 指定されたユーザと月のカテゴリごとの支出を多い順に取得
    *** Return              : 月の支出データ
    ****************************************************************************/
	
    @Select("SELECT * FROM MONTH "
            + "WHERE USERId = #{userid} AND MONTH = #{month} "
            + "ORDER BY spendSum DESC")
	List<MonthModel> getExpenseData(@Param("userid") int userId, @Param("month") int month);
}