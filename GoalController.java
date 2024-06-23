/*******************************************************************
***  File Name		: GoalController.java
***  Version		: V1.0
***  Designer		: 上村　結
***  Date			: 2024.06.18
***  Purpose       	: 目標入力画面とGoalService.javaをつなぎ、
***					  difference.htmlへ画面を引き継ぐ
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 上村結, 2024.06.18
*/

package com.example.demo.controller;
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
	*** Date                : 2024.06.18
	*** Function            : goal.htmlでデータを入力するための準備
	*** Return              : 実際に入力を行う画面である"goal.html"の名前を返す
	****************************************************************************/
    @GetMapping("/goal")
    public String addNewTarget(Model model) {
    	
        model.addAttribute("month", new MonthModel());
        model.addAttribute("monthList", this.monthList);
        
        return "goal.html";
    }

    /****************************************************************************
	*** Method Name         : createNewObject()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : goal.htmlでデータを表示する
	*** 					  また、入力されたデータをリストで引数とし、GoalServiceクラスのデータ挿入を行うメソッドを呼び出す
	*** Return              : "新規"ボタンを入力後に表示するために"redirect:difference.html"のStringを返す
	****************************************************************************/
    @PostMapping("/goal")
    public String createNewObject(@Validated @RequestParam("targetValues") int[] targetValues, Model model) {
        MonthModel monthModel = new MonthModel();
        
    	for(int i = 0 ; i < this.monthList.size() ; ++i) {
        	try {
        		monthModel = this.monthList.get(i);
        		int target = targetValues[i];
        		this.monthList.get(i).setTarget(target);
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
    	
    	
    	goalService.insertGoalDatabase(this.monthList);
        return "redirect:difference";
    }  
    
    
    //デバッグ用
    @GetMapping("/goaltestlist")
    public String displayUsers(Model model) {
    	MonthModel month = new MonthModel();
    	month.setId(1);
    	
        List<MonthModel> months = goalService.selectAll();
        model.addAttribute("months", months);
        return "GoalTestList.html";
    }
    
	
		
}
