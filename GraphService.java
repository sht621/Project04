/*******************************************************************
***  File Name        : GraphService.java
***  Version          : V1.1
***  Designer         : 保泉 雄祐
***  Date             : 2024.07.07
***  Purpose          : グラフデータの取得と処理を行う
***
*******************************************************************/

package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GraphMapper;
import com.example.demo.model.MonthModel;

@Service
public class GraphService {

    @Autowired
    private GraphMapper graphMapper;

    /****************************************************************************
    *** Method Name         : getGraphData
    *** Designer            : 保泉 雄祐
    *** Date                : 2024.07.07
    *** Function            : 指定された年月のグラフデータを取得し処理する
    *** Return              : 月別データのリスト
    ****************************************************************************/
    public List<MonthModel> getGraphData(int userId, int year, int month) {
        int yearMonth = year * 100 + month;
        return graphMapper.selectGraphData(userId, yearMonth);
    }

    /****************************************************************************
    *** Method Name         : getYearList
    *** Designer            : 保泉 雄祐
    *** Date                : 2024.07.07
    *** Function            : 選択可能な年のリストを生成する
    *** Return              : 年のリスト
    ****************************************************************************/
    public List<Integer> getYearList() {
        List<Integer> years = new ArrayList<>();
        for (int year = 2024; year <= 2026; year++) {
            years.add(year);
        }
        return years;
    }

    /****************************************************************************
    *** Method Name         : getMonthList
    *** Designer            : 保泉 雄祐
    *** Date                : 2024.07.07
    *** Function            : 選択可能な月のリストを生成する
    *** Return              : 月のリスト
    ****************************************************************************/
    public List<String> getMonthList() {
        List<String> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            months.add(String.format("%02d", month));
        }
        return months;
    }
}