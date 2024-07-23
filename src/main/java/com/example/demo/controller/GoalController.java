/*******************************************************************
***  File Name		: GoalController.java
***  Version		: V1.1
***  Designer		: 上村　結
***  Date			: 2024.06.29
***  Purpose       	: 目標入力画面とGoalService.javaをつなぎ、
***					  difference.htmlへ画面を引き継ぐ
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 上村結, 2024.06.18
*** V1.1 : 上村結　2024.06.29
*/

package com.example.demo.controller;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.MonthModel;
import com.example.demo.service.GoalService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GoalController {
	private final GoalService goalService;
	private ArrayList<MonthModel> monthList;	//入力されたデータをリストでやり取りする
	private DifferController differController;
	
	/****************************************************************************
	*** Method Name         : GoalController()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : コンストラクタであり、フィールドの初期化を行う
	*** Return              : なし
	****************************************************************************/
	@Autowired
	public GoalController(GoalService goalService) {
		this.goalService = goalService;
		this.monthList = new ArrayList<MonthModel>();
		
		String[] itemName = {"食費","外食費","日用品","交際費","趣味・娯楽","教育・教養","美容・衣服",
							 "通信費","交通費","医療・保険","水道・光熱費","住まい","税金","その他"};
        for(String item : itemName) {
        	MonthModel monthModel = new MonthModel();
        	monthModel.setItemId(item);
        	this.monthList.add(monthModel);
        }
	}
    
	/****************************************************************************
	*** Method Name         : addNewTarget()
	*** Designer            : 上村　結
	*** Date                : 2024.07.09
	*** Function            : goal.htmlでデータを入力するための準備
	*** Return              : データがまだ未入力なら実際に入力を行う画面である"objective.html"の名前を返し
	***						  すでにデータがある場合はupdatediiferの画面に飛ぶ　
	****************************************************************************/
    @GetMapping("/objective")
    public String addNewTarget( Model model, HttpSession session) {
    	//UserIdがないときログイン画面へ飛ばす
    	if((Integer)session.getAttribute("loggedInUser") == null) {
    		return "redirect:login";
    	}
		int userId = (int) session.getAttribute("loggedInUser");
		
		//現在の年月をセットする
		int yearMonth = yearMonth();
		String monthStr = Integer.toString(yearMonth).substring(4);
		String yearStr = Integer.toString(yearMonth).substring(0, 4);
		int month = Integer.parseInt(monthStr);
		int year = Integer.parseInt(yearStr);
		model.addAttribute("defaultYear", year);
		model.addAttribute("defaultMonth", month);
		
		// 年の選択肢を生成（2024年から2026年まで）
        List<Integer> years = generateYearList();
        model.addAttribute("years", years);
        
        // 月の選択肢を生成（01月から12月まで）
        List<String> months = generateMonthList();
        model.addAttribute("months", months);
		
		
		model.addAttribute("month", new MonthModel());
        model.addAttribute("monthList", this.monthList);
        model.addAttribute("loggedInUser", userId);
	    //ユーザIDをセット
        for(int i = 0 ; i < this.monthList.size() ; ++i) {
        	this.monthList.get(i).setUserId(userId);
        }
	        
	    return "objective.html";
	        
    }

    /****************************************************************************
	*** Method Name         : createNewObject()
	*** Designer            : 上村　結
	*** Date                : 2024.07.09
	*** Function            : goal.htmlでデータを表示する
	*** 					  また、入力されたデータをリストで引数とし、GoalServiceクラスのデータ挿入を行うメソッドを呼び出す
	*** Return              : "新規"ボタンを入力後に表示するために"redirect:difference.html"のStringを返す
	****************************************************************************/

    
    @PostMapping("/objective")
    public String createNewObject(@Validated @RequestParam("targetValues") Integer[] targetValues, Model model, 
								  HttpSession session,@RequestParam("year") int year, 
								  @RequestParam("month") int month) {
    	//UserIdがないときログイン画面へ飛ばす
    	if((Integer)session.getAttribute("loggedInUser") == null) {
    		return "redirect:login";
    	}
    	
    	MonthModel monthModel = new MonthModel();
        int userId = (int) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", userId);

        //現在の年月のセット
        int nowYearMonth = yearMonth();
		String monthStr = Integer.toString(nowYearMonth).substring(4);
		String yearStr = Integer.toString(nowYearMonth).substring(0, 4);
		int nowMonth = Integer.parseInt(monthStr);
		int nowYear = Integer.parseInt(yearStr);
		model.addAttribute("defaultYear", nowYear);
		model.addAttribute("defaultMonth", nowMonth);
        
		// 更新が完了したらリダイレクトするなどの処理を行う
        List<Integer> years = generateYearList();
        model.addAttribute("years", years);

        // 月の選択肢を生成（01月から12月まで）
        List<String> months = generateMonthList();
        model.addAttribute("months", months);
        
        int yearMonth = year * 100 + month;
        
        
        //既にデータの設定が行われている場合
        try {
	        if(goalService.isExisting(yearMonth, userId) == true) {
	        	throw new IllegalArgumentException("この月の目標金額はすでに設定されています．目標更新画面から更新してください");
	        }
        }
	     catch(IllegalArgumentException e) {
	    	model.addAttribute("errorMessage", e.getMessage());
     		model.addAttribute("monthList", this.monthList);
     		return "objective.html";
	     }
        
        //入力されたデータを格納
    	for(int i = 0 ; i < this.monthList.size() ; ++i) {
        	try {
        		monthModel = this.monthList.get(i);
        		//入力されていない項目がある場合
        		if(targetValues[i] == null) {
        			throw new IllegalArgumentException("目標金額を入力してください");
        		}
        		//0未満または10000000より大きい金額が入力された場合
        		int target = targetValues[i].intValue();
        		if(target < 0 || target > 10000000) {
        			throw new IllegalArgumentException("目標金額は0円から10000000円の範囲にしてください");
        		}
        		//問題ない場合リストに入力されたデータを追加
        		this.monthList.get(i).setTarget(target);
        		this.monthList.get(i).setMonth(yearMonth);
        	}
        	//数字以外が入力された場合
        	catch(NumberFormatException e) {
        		model.addAttribute("errorMessage", "半角数字を入力してください");
        		model.addAttribute("monthList", this.monthList);
        		return "objective.html";
        	}
        	//入力エラーがある場合
        	catch(IllegalArgumentException e) {
        		model.addAttribute("errorMessage", e.getMessage());
        		model.addAttribute("monthList", this.monthList);
        		return "objective.html";
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        }
    	
    	
    	//データ格納したら、Serviceクラスのデータベースへ挿入を行うメソッドを呼び出す
    	goalService.insertGoalDatabase(this.monthList);
        return "redirect:difference";
    }  
    
   
    /****************************************************************************
	*** Method Name         : yearMonth()
	*** Designer            : 上村　結
	*** Date                : 2024.06.29
	*** Function            : 現在の年月をyyyyMM形式で取得
	*** Return              : 現在の年月をyyyyMM形式で返す　
	****************************************************************************/
    private int yearMonth() {
    	int yearMonth = 0;
    	YearMonth currentYearMonth = YearMonth.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
    	String formattedYearMonth = currentYearMonth.format(formatter);
    	
    	yearMonth = Integer.parseInt(formattedYearMonth);
    	return yearMonth;
    }
	
    /****************************************************************************
     *** Method Name         : generateYearList()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 年の生成を行う
     *** Return              : List<Integer>
     ****************************************************************************/
    // 年の選択肢を生成するメソッド
    private List<Integer> generateYearList() {
        List<Integer> years = new ArrayList<>();
        for (int year = 2000; year <= 2099; year++) {
            years.add(year);
        }
        return years;
    }
    
    /****************************************************************************
     *** Method Name         : generateMonthList()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 月の生成を行う
     *** Return              : List<Integer>
     ****************************************************************************/
    private List<String> generateMonthList() {
        List<String> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            String monthStr = String.format("%02d", month); // 1桁の月を2桁にフォーマット
            months.add(monthStr);
        }
        return months;
    }
}
		
