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
	*** Date                : 2024.06.29
	*** Function            : goal.htmlでデータを入力するための準備
	*** Return              : データがまだ未入力なら実際に入力を行う画面である"objective.html"の名前を返し
	***						  すでにデータがある場合はupdatediiferの画面に飛ぶ　
	****************************************************************************/
    @GetMapping("/objective")
    public String addNewTarget( Model model, HttpSession session) {
    	
		int userId = (int) session.getAttribute("loggedInUser");
		int yearMonth = yearMonth();
		
		//今月のデータがまだ存在しない場合
		if(goalService.isExisting(yearMonth, userId) == false) {
			model.addAttribute("month", new MonthModel());
	        model.addAttribute("monthList", this.monthList);
	        model.addAttribute("loggedInUser", userId);
	        model.addAttribute("yearmonth", yearMonth);
	        //ユーザIDと年月をセット
	        for(int i = 0 ; i < this.monthList.size() ; ++i) {
	        	this.monthList.get(i).setUserId(userId);
	        	this.monthList.get(i).setMonth(yearMonth);
	        }
	        
	        return "objective.html";
	        
		}
		//すでに存在する場合更新画面へ推移
		else {	
			return "redirect:updatediffer?userId="+userId;
		}
    }

    /****************************************************************************
	*** Method Name         : createNewObject()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : goal.htmlでデータを表示する
	*** 					  また、入力されたデータをリストで引数とし、GoalServiceクラスのデータ挿入を行うメソッドを呼び出す
	*** Return              : "新規"ボタンを入力後に表示するために"redirect:difference.html"のStringを返す
	****************************************************************************/

    
    @PostMapping("/objective")
    public String createNewObject(@Validated @RequestParam("targetValues") Integer[] targetValues, Model model, HttpSession session) {
        MonthModel monthModel = new MonthModel();
        int userId = (int) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", userId);
        
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
    public int yearMonth() {
    	int yearMonth = 0;
    	YearMonth currentYearMonth = YearMonth.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
    	String formattedYearMonth = currentYearMonth.format(formatter);
    	
    	yearMonth = Integer.parseInt(formattedYearMonth);
    	return yearMonth;
    }
	
		
}
