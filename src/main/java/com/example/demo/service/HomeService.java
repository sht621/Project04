/*******************************************************************
***  File Name		: HomeService.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.07.02
***  Purpose       	: ホーム画面に表示するデータを取得する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.07.02
*/

package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MonthModel;
import com.example.demo.model.PaymentModel;

@Service
public class HomeService {
	
	@Autowired
	private com.example.demo.service.DifferService differService;
	
	@Autowired
	private com.example.demo.mapper.HomeMapper homeMapper;
	
    /****************************************************************************
    *** Method Name         : getHomeData()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 今月の収支情報を取得する
    *** Return              : 今月の収支情報
    ****************************************************************************/

	public int[] getHomeData(int userId) {
		
		int[] homeData = new int[5];
		
		int expense = 0;
		int target = 0;
		int difference = 0;
		
		LocalDate today = LocalDate.now();
    	int year = today.getYear();
    	int month = today.getMonthValue();
		int yearMonth = year * 100 + month;
		
		//収入取得
		int income = calculateTotalIncome(userId, yearMonth);
		
		//支出・目標・差額取得
		//differCalculationメソッドを流用
		List<MonthModel> monthData = differService.differCalculation(userId);
		
		//全カテゴリのデータを加算して、全体のデータを作成
		for(int i=0; i<monthData.size(); i++) {
			expense += monthData.get(i).getSpendSum();
			target += monthData.get(i).getTarget();
			difference += monthData.get(i).getDiffer();
		}
		
		//収支取得
		int balance = income - expense;
		
		//配列にデータを格納
		homeData[0] = balance;
		homeData[1] = income;
		homeData[2] = expense;
		homeData[3] = target;
		homeData[4] = difference;
		
		return homeData; //上のデータが入った配列		
	}
	
    /****************************************************************************
    *** Method Name         : getSortedData()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.04
    *** Function            : ホームに表示するグラフのデータを取得する
    *** Return              : ホームに表示するグラフのデータ
    ****************************************************************************/
    
	public List<Object> getSortedData(int userId) {
		
		LocalDate today = LocalDate.now();
    	int year = today.getYear();
    	int month = today.getMonthValue();
		int yearMonth = year * 100 + month;
		
		List<MonthModel> dataList = homeMapper.getExpenseData(userId, yearMonth);

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

        return sortedData;
	}
	
    /****************************************************************************
    *** Method Name         : getRecords()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 入力履歴を取得する
    *** Return              : 入力履歴データ
    ****************************************************************************/
	
	public List<String[]> getRecords(int userId){
		
		List<String[]> record = new ArrayList<>();
		List<PaymentModel> dataList = homeMapper.getRecord(userId);
		
		for(int i=0; i<dataList.size(); i++) {
			String[] data = new String[3];
			data[0] = convertToDate(dataList.get(i).getDay()); //日付を取得
			data[1] = dataList.get(i).getItemId(); //カテゴリを取得
			if(!data[1].equals("")) { //支出を取得
				data[1] = dataList.get(i).getItemId();
				data[2] = String.valueOf(dataList.get(i).getSpend()) + "円";
			}else { //収入を取得
				data[1] = "収入";
				data[2] = String.valueOf(dataList.get(i).getIncome()) + "円";
			}
			record.add(data);
		}
		return record; //履歴データを格納したリスト
	}
	
    /****************************************************************************
    *** Method Name         : calculateTotalIncome()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 月の合計収入を計算する
    *** Return              : 月の収入
    ****************************************************************************/
	
    int calculateTotalIncome(int userId, int yearMonth) {
    	int income = 0;
        //月の収入を取得して合計
        List<PaymentModel> incomeList = homeMapper.getIncomes(userId, yearMonth);
        for(int i=0; i<incomeList.size(); i++) {
        	income += incomeList.get(i).getIncome();
        }
        return income; //月の収入
    }
    
    /****************************************************************************
    *** Method Name         : convertToDate()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 日付データをフォーマットする
    *** Return              : フォーマットされた日付データ
    ****************************************************************************/
    
    public String convertToDate(int day) {
    	String stringDay = String.valueOf(day);
        LocalDate date = LocalDate.parse(stringDay, DateTimeFormatter.BASIC_ISO_DATE);
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); //フォーマットされた日付データ
    }
}
