/*******************************************************************
***  File Name		: GoalService.java
***  Version		: V1.1
***  Designer		: 上村　結
***  Date			: 2024.06.29
***  Purpose       	: GoalController.javaとGoalMapper.java をつなぐ
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 上村結, 2024.06.18
*** V1.1 : 上村結, 2024.06.29
*/

package com.example.demo.service;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.GoalMapper;
import com.example.demo.model.MonthModel;
import com.example.demo.model.PaymentModel;


@Service
public class GoalService {
	private final GoalMapper goalMapper;
	
	/****************************************************************************
	*** Method Name         : GoalService()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : コンストラクタでありmapperの初期化を行う．
	*** Return              : なし
	****************************************************************************/
	public GoalService(GoalMapper goalmapper){
		this.goalMapper = goalmapper;
	}
	
	
	/****************************************************************************
	*** Method Name         : insertGoalDatabase()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : データベースへ挿入する内容の確定をGoalModelクラスのsetterとgetterを用いて行う．
	*** Return              : データベースへの挿入が成功したらtrue、
	***						  失敗したらfalseを返す
	****************************************************************************/
	@Transactional
	public boolean insertGoalDatabase(ArrayList<MonthModel> monthList) {
		boolean success = false;	//データベース挿入の成功判定
		MonthModel monthModel = new MonthModel();
		int max = 0;	//現在のデータベースのIDの最大値
		String itemId = null;	//商品項目名
		int result = -1;	//mapperクラスの返り値格納用(>0なら挿入成功)
		int spendSum;		//合計
		int differ;			//差額
		
		//IDがnullなら0、最大のIDがあればその値を代入
		if(goalMapper.selectMaxIdFromMonth() != null) {
			max = goalMapper.selectMaxIdFromMonth().intValue();
		}
		else {
			max = 0;
		}
		
		int startId = max + 1;	//ID計算用(最大IDに1足したもの)
		
		//すでにセットされている現在の年月とユーザIDを取得
		int yearMonth = monthList.get(0).getMonth();	//現在の年月
		int userId = monthList.get(0).getUserId();		//ユーザID
		
		//monthModelにセットしていく
		for(int i = 0 ; i < monthList.size() ; ++i) {
			monthModel = monthList.get(i);
			itemId = monthModel.getItemId();	
			//月とユーザIDのセット
			monthModel.setMonth(yearMonth);
			monthModel.setUserId(userId);
			//支出合計の算出とセット
			spendSum = caluculateSpendSum(userId, itemId, yearMonth);
			monthModel.setSpendSum(spendSum);
			//支出合計算出後に差額計算とセット
			differ = calculateDiffer(monthModel);
			monthModel.setDiffer(differ);
			//IDを1ずつ増やしセット
			monthModel.setId(startId + i);
			
			//データベース挿入の関数をmapperから呼び出す
			result = goalMapper.insert(monthModel);
			if(result <= 0) {	//失敗
				success = false;
				break;
			}
			else {				//成功
				success = true;
			}
		}
		//失敗か成功か返す		
		return success;
	}
	
	
	
	
	/****************************************************************************
	*** Method Name         : caluculateSpendSum()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : 
	*** Return              : その月の引数に対応する項目の収支(PAYMENTデータベースのSPEND)の合計
	****************************************************************************/
	@Transactional(readOnly = true)
	public int caluculateSpendSum(int userId, String itemId, int yearMonth) {
		int sum = 0;
		int maxDay = monthToDay(yearMonth);
		int startDay = yearMonth * 100 + 1;
		int endDay = yearMonth  * 100 + maxDay;
		int spend;
		ArrayList<PaymentModel> paymentList = new ArrayList<PaymentModel>();
		paymentList = goalMapper.selectFromPaymentByDay(userId, itemId, startDay, endDay);
		
		if(paymentList == null) {
			sum = 0;
		}
		else {
			for(PaymentModel list : paymentList) {
				spend = list.getSpend();
				sum += spend;
			}
		}
		
		return sum;
	}
	
	
	/****************************************************************************
	*** Method Name         : monthToDay()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : 年と月を引数にとり、そこからその月の最大の日数を求める
	*** Return              : 引数に対応する月の最大日数を返す
	****************************************************************************/
	@Transactional(readOnly = true)
	public int monthToDay(int yearMonth) {
		//yyyyMMdd形式で値を返す
		int day = 0;
		String monthStr = Integer.toString(yearMonth).substring(4);
		String yearStr = Integer.toString(yearMonth).substring(0, 4);
		int month = Integer.parseInt(monthStr);
		int year = Integer.parseInt(yearStr);
		
		if(month == 1) {
			day = 31;
		}
		else if(month == 2) {
			if(year % 400 == 0) {
				day = 29;
			}
			else if(year % 100 == 0) {
				day = 28;
			}
			else if(year % 4 == 0) {
				day = 29;
			}
			else {
				day = 28;
			}
		}
		else if(month == 3) {
			day = 31;
		}
		else if(month == 4) {
			day = 30;
		}
		else if(month == 5) {
			day = 31;
		}
		else if(month == 6) {
			day = 30;
		}
		else if(month == 7) {
			day = 31;
		}
		else if(month == 8) {
			day = 31;
		}
		else if(month == 9) {
			day = 30;
		}
		else if(month == 10) {
			day = 31;
		}
		else if(month == 11) {
			day = 30;
		}
		else if(month == 12) {
			day = 31;
		}
	
		return day;
	}
	
	
	/****************************************************************************
	*** Method Name         : calculateDiffer()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : MonthModelのデータを引数にとり、そのフィールドのSpendSumとtargetから差額を求める
	***						  そのため、先にSpendSumの正確な値を算出する必要あり
	***						  (先にcalculateSpendSumを読んでからこの関数を呼ぶ)
	*** Return              : 求めた差額
	****************************************************************************/
	@Transactional(readOnly = true)
	public int calculateDiffer(MonthModel monthModel) {
		int sum = monthModel.getSpendSum();
		int target = monthModel.getTarget();
		int newDiffer = target - sum;
		return newDiffer;
		
	}
	
	
	/****************************************************************************
	*** Method Name         : calculateDiffer()
	*** Designer            : 上村　結
	*** Date                : 2024.06.29
	*** Function            : 今月のデータがあるかどうかを判定する
	*** Return              : すでにあればtrue、なければfalse を返す
	****************************************************************************/
	public boolean isExisting(int month, int userId) {
		boolean exist;
		if(goalMapper.isExisting(month, userId).size() == 0) {
			exist = false;
		}
		else {
			exist = true;
		}
		return exist;
	}
}
