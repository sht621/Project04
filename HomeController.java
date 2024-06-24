/*******************************************************************
***  File Name		: HomeController.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.06.18
***  Purpose       	: ホーム画面を表示する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.18
*/

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	 /****************************************************************************
	 *** Method Name         : ()
	 *** Designer            : 菅 匠汰
	 *** Date                : 2024.06.13
	 *** Function            : 予算に基づいた料理のレシピを生成して表示する
	 *** Return              : レシピ提案画面(RecipeScreen)
	 ****************************************************************************/
	    
	@GetMapping("/home")
	public String displayHome(Model model, HttpSession session) {
		int userid = (int) session.getAttribute("loggedInUser");
		
		//今月の収入、支出、収支を取得するメソッドを利用
		//数値は仮置き
		int income = 100000;
		int expense = 70000;
		int balance = income - expense;
		int target = 80000;
		int difference = target - expense;
		
		//model.addAttributeで値をhtmlに渡す
		model.addAttribute("username", userid);
		model.addAttribute("income", income);
		model.addAttribute("expense", expense);
		model.addAttribute("balance", balance);
		model.addAttribute("target", target);
		model.addAttribute("difference", difference);
		
		
		//今月の支出を円グラフにするメソッドを利用
		//円グラフを出力
		
		
		//直近の入力履歴を新しい順に最大10件表示
		//履歴画面のメソッドを利用
		
		return "home";
	}
	
	
}
