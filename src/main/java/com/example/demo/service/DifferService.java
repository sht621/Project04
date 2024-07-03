/*******************************************************************
***  File Name		: DifferService.java
***  Version		: V1.1
***  Designer		: 東野　魁耶
***  Date		: 2024.06.24
***  Purpose       	: Mapperクラスから処理を呼び出し、Controllerから呼ばれた処理を行うクラス
***
*******************************************************************/

package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.DifferPaymentMapper;
import com.example.demo.model.MonthModel;
import com.example.demo.model.PaymentModel;

@Service
public class DifferService {
	
	private final DifferPaymentMapper dao;

    public DifferService(DifferPaymentMapper dao) {
    	this.dao = dao;
    }

    /****************************************************************************
     *** Method Name         : differCalculation(int userId)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.24
     *** Function            : 現在の年月を取得し、次の差額を計算するメソッドに渡す
     *** Return              : List<MonthModel>
     ****************************************************************************/
    public List<MonthModel> differCalculation(int userId) {
    	LocalDate today = LocalDate.now();
    	int year = today.getYear();
    	int month = today.getMonthValue();
        return differCalculation(year, month, userId);
    }
    
    /****************************************************************************
     *** Method Name         : differCalculation(int year, int month, int userId)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.24
     *** Function            : 受け取った年月を基に差額データの計算を行いListとして返す
     *** Return              : List<MonthModel>
     ****************************************************************************/
    public List<MonthModel> differCalculation(int year, int month, int userId){
        int intToday = year * 100 + month;
    	int[] sum = calculateTotalSpends(userId, intToday);
    	List<MonthModel> listMon = dao.selectMonth(userId, intToday);
    	List<MonthModel> differ = new ArrayList<>();

    	// 各ジャンルごとの合計金額を計算し、目標金額から引いて求める
    	for (MonthModel temp : listMon) {
            int index = getItemIndex(temp.getItemId());
            temp.setSpendSum(sum[index]);
            int differValue = temp.getTarget() - sum[index];
            temp.setDiffer(differValue);
            differ.add(temp);
            
            // MONTH テーブルを更新
            updateMonthData(userId, intToday, temp.getItemId(), sum[index], differValue);
        }
    	
        return differ;
    }
    
    /****************************************************************************
     *** Method Name         : updateTarget(int month, String itemId, int target, int userId)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.24
     *** Function            : 受け取った月、itemId、目標金額を基にデータベースを更新する
     *** Return              : 返り値なし
     ****************************************************************************/
    public void updateTarget(int month, String itemId, int target, int userId) {
        dao.updateTarget(userId, month, itemId, target);
    }
    
    /****************************************************************************
     *** Method Name         : calculateTotalSpends(int userId, int inttoday)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 受け取った年月、userIdを基にデータベースから情報を取り出し収支データを返す
     *** Return              : sum[]
     ****************************************************************************/
    private int[] calculateTotalSpends(int userId, int yaerMonth) {
        int[] sum = new int[14]; // 0~13まであらかじめ何のジャンルが入るか決まっている
        List<PaymentModel> listPay = dao.selectPayment(userId, yaerMonth);
        
        for (PaymentModel pay : listPay) {
            int index = getItemIndex(pay.getItemId());
            if(index != -1) {
            	sum[index] += pay.getSpend();
            }
        }
        
        return sum;
    }
    
    /****************************************************************************
     *** Method Name         : getItemIndex(String itemId)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.24
     *** Function            : 受け取ったitemIdを基にそれに対応した数字を返す
     *** Return              : int型の1~13 or -1
     ****************************************************************************/
    private int getItemIndex(String itemId) {
        switch (itemId) {
            case "食費":      return 0;
            case "外食費":    return 1;
            case "日用品":    return 2;
            case "交際費":    return 3;
            case "趣味・娯楽":return 4;
            case "教育・教養":return 5;
            case "美容・衣服":return 6;
            case "通信費":    return 7;
            case "交通費":    return 8;
            case "医療・保険":return 9;
            case "水道・光熱費":return 10;
            case "住まい":    return 11;
            case "税金":      return 12;
            case "その他":    return 13;
            default:         return -1; // マッピングされていない場合
        }
    }
    
    /****************************************************************************
     *** Method Name         : updateMonthData(int userId, int month, String itemId, int spendSum, int differ)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 受け取った年月、userId, itemId, spendSum, differを基にデータベースの更新を行う
     *** Return              : 返り値なし
     ****************************************************************************/
    public void updateMonthData(int userId, int month, String itemId, int spendSum, int differ) {
        dao.updateMonthData(userId, month, itemId, spendSum, differ);
    }
}
