/*******************************************************************
***  File Name		: HomeController.java
***  Version		: V1.1
***  Designer		: 菅 匠汰
***  Date			: 2024.07.04
***  Purpose       	: ホーム画面を表示する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.18
*** V1.1 : 菅 匠汰, 2024.07.04
*/

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private com.example.demo.service.HomeService homeService;
	
	 /****************************************************************************
	 *** Method Name         : displayHome()
	 *** Designer            : 菅 匠汰
	 *** Date                : 2024.07.02
	 *** Function            : ホーム画面を表示する
	 *** Return              : ホーム画面
	 ****************************************************************************/
	@GetMapping("/home")
	public String displayHome(Model model, HttpSession session) {
		Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
		
		//今月の収入、支出、収支を取得するメソッドを利用
		int[] homeData = homeService.getHomeData(userId);
		int balance = homeData[0];
		int income = homeData[1];
		int expense = homeData[2];
		int target = homeData[3];
		int difference = homeData[4];
		
		//model.addAttributeで値をhtmlに渡す
		model.addAttribute("userId", userId);
		model.addAttribute("income", income);
		model.addAttribute("expense", expense);
		model.addAttribute("balance", balance);
		model.addAttribute("target", target);
		model.addAttribute("difference", difference);
		
		//円グラフのデータを取得
		List<Object> sortedData = homeService.getSortedData(userId);
		int[] chartData = (int[]) sortedData.get(0);
		String[] chartLabels = (String[]) sortedData.get(1);
		model.addAttribute("chartData", chartData);
		model.addAttribute("chartLabels", chartLabels);
		
		
		//直近の入力履歴を新しい順に最大10件表示
		List<String[]> record = homeService.getRecords(userId);
		model.addAttribute("record", record);
		
		return "home";
	}
}
