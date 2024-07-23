/*******************************************************************
***  File Name	: PaymentService.java
***  Version	: V1.0
***  Designer	: 佐藤　巧都　
***  Date		: 2024.07.09
***  Purpose    : Mapperクラスから処理を呼び出し、Controllerから呼ばれた処理を行うクラス
***
*******************************************************************/

package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.PaymentMapper;
import com.example.demo.model.MonthModel;
import com.example.demo.model.PaymentModel;

@Service
@Transactional
public class PaymentService {

    private final PaymentMapper mapper;
    
    public PaymentService(PaymentMapper mapper) {
        this.mapper = mapper;
    }
    
    @Autowired
    private com.example.demo.service.DifferService differService;
    

    /****************************************************************************
     *** Method Name         : findAll()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : mapperによりuserIdの情報からデータ一覧を取得し返す
     *** Return              : mapper.findAll()
     ****************************************************************************/
    public List<PaymentModel> findAll(int userId) {
        return mapper.findAll(userId);
    }

    /****************************************************************************
     *** Method Name         : insertPayment()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : idを設定し、mapperのinputPaymentを呼び出しpaymentを返す
     *** Return              : payment
     ****************************************************************************/
    public PaymentModel insertPayment(PaymentModel payment) {
    	int max = 0;
    	if(mapper.selectMaxIdFromPayment() != null) {
			max = mapper.selectMaxIdFromPayment().intValue();
		}
		else {
			max = 0;
		}
		int startId = max + 1;	
    	payment.setId(startId);
    	mapper.inputPayment(payment);
        return payment;
    }

    /****************************************************************************
     *** Method Name         : paymentUpdate()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : mapperのupdatePaymentを呼び出しそれを返す
     *** Return              : mapper.updatePayment
     ****************************************************************************/
    @Transactional
    public int paymentUpdate(int income, int spend, int day, String itemId, int id, int userId) {
        return mapper.updatePayment(income, spend, day, itemId, id, userId);
    }
    
    /****************************************************************************
     *** Method Name         : findById()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : mapperのfindByIdを呼び出しそれを返す
     *** Return              : mapper.findById()
     ****************************************************************************/
    public PaymentModel findById(int id) {
        return mapper.findById(id);  // 引数を PaymentModel から int に変更
    }
    
    /****************************************************************************
     *** Method Name         : findByIdAndUserId()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : mapperのfindByIdAndUserIdを呼び出しそれを返す
     *** Return              : mapper.findByIdAndUserId
     ****************************************************************************/
    public PaymentModel findByIdAndUserId(int id, int userId) {
        return mapper.findByIdAndUserId(id, userId);
    }
    
    /****************************************************************************
     *** Method Name         : deleteAndRearrange()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : mapperのdeletePaymentByIdとdcrementIdsAfterを呼び出す
     *** Return              : なし
     ****************************************************************************/
    @Transactional
    public void deleteAndRearrange(int id, int userId) {
    	 
    	try {
    	        // データの削除
    	        mapper.deletePaymentById(id, userId);
    	        // IDの更新
    	        mapper.decrementIdsAfter(id);
    	    } catch (Exception e) {
    	        // エラーハンドリング
    	        throw new RuntimeException("Error occurred while deleting and rearranging payments", e);
    	    }
    	
    }
    
    
    /****************************************************************************
    *** Method Name         : getHomeData()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 今月の収支情報を取得する
    *** Return              : 今月の収支情報
    ****************************************************************************/

	public int[] getHomeData(int userId) {
		
		int[] homeData = new int[5];
		
		int income = 0;
		int expense = 0;
		int target = 0;
		int difference = 0;
		int balance = 0;
		
		LocalDate today = LocalDate.now();
    	int year = today.getYear();
    	int month = today.getMonthValue();
		int yearMonth = year * 100 + month;
		
		//収入取得
		income = calculateTotalIncome(userId, yearMonth);
		
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
		balance = income - expense;
		
		//配列にデータを格納
		homeData[0] = balance;
		homeData[1] = income;
		homeData[2] = expense;
		homeData[3] = target;
		homeData[4] = difference;
		
		return homeData; //上のデータが入った配列		
	}
    
    /****************************************************************************
    *** Method Name         : getRecords()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : ホームに表示する履歴を取得する
    *** Return              : 履歴データ
    ****************************************************************************/
	
	public List<String[]> getRecords(int userId){
		
		List<String[]> record = new ArrayList<>();
		List<PaymentModel> dataList = mapper.getRecord(userId);
		
		for(int i=0; i<dataList.size(); i++) {
			String[] data = new String[3];
			data[0] = convertToDate(dataList.get(i).getDay()); //日付を取得
			data[1] = dataList.get(i).getItemId(); //カテゴリを取得
			if(!data[1].equals("収入")) { //支出を取得
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
        List<PaymentModel> incomeList = mapper.getIncomes(userId, yearMonth);
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
