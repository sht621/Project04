/*******************************************************************
***  File Name        : GraphService.java
***  Version          : V1.1
***  Designer         : 保泉 雄祐
***  Date             : 2024.07.07
***  Purpose          : グラフデータの取得と処理を行う
***
*******************************************************************/

package com.example.demo.service;

import java.time.LocalDate;
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
    
    
    /****************************************************************************
     *** Method Name         : getSortedData()
     *** Designer            : 菅 匠汰
     *** Date                : 2024.07.08
     *** Function            : 月の支出グラフのデータを取得する
     *** Return              : 月の支出グラフのデータ
     ****************************************************************************/
 	
 	public List<Object> getSortedData(int userId) {
 		LocalDate today = LocalDate.now();
     	int year = today.getYear();
     	int month = today.getMonthValue();
 		int yearMonth = year * 100 + month;
 		//今月のyearMonthを入れて取得
 		return getSortedData(userId, yearMonth);
 	}
     
 	public List<Object> getSortedData(int userId, int yearMonth) {
 		//引数のIDと年月を使いデータを取得
 		List<MonthModel> dataList = graphMapper.getExpenseData(userId, yearMonth);

         List<Integer> chartData = new ArrayList<>();
         List<String> chartLabels = new ArrayList<>();

         for (int i=0; i<dataList.size(); i++) {
         	int spendSum = dataList.get(i).getSpendSum();
         	String itemId = dataList.get(i).getItemId();
         	if(spendSum > 0) { //支出が0円の場合は除く
         		chartData.add(spendSum);
         		chartLabels.add(itemId);
         	}else {
         		break;
         	}
         }
         
         int[] dataArray = new int[chartData.size()];
         for (int i = 0; i < chartData.size(); i++) {
             dataArray[i] = chartData.get(i);
         }
         
         String[] labelsArray = new String[chartLabels.size()];
         for (int i = 0; i < chartLabels.size(); i++) {
             labelsArray[i] = chartLabels.get(i);
         }

         List<Object> sortedData = new ArrayList<>();
         sortedData.add(dataArray);
         sortedData.add(labelsArray);

         return sortedData; //月の支出データ
 	}
}