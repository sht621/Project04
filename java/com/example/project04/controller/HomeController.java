package com.example.project04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/HomeScreen")
	public String home(Model model) {
		
		//今月の収入、支出、収支を取得するメソッドを利用
		//数値は仮置き
		int income = 100000;
		int expense = 70000;
		int balance = income - expense;
		int target = 80000;
		int difference = target - expense;
		
		//model.addAttributeで値をhtmlに渡す
		model.addAttribute("income", income);
		model.addAttribute("expense", expense);
		model.addAttribute("balance", balance);
		model.addAttribute("target", target);
		model.addAttribute("difference", difference);
		
		//今月の支出を円グラフにするメソッドを利用
		//円グラフを出力
		
		
		//直近の入力履歴を新しい順に最大10件表示
		//履歴画面のメソッドを利用
			
		
		return "HomeScreen";
	}
}
