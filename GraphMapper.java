/*******************************************************************
***  File Name        : GraphMapper.java
***  Version          : V1.1
***  Designer         : 保泉 雄祐
***  Date             : 2024.07.07
***  Purpose          : データベースからグラフ用データを取得する
***
*******************************************************************/

package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
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
}