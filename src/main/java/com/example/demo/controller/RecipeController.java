/*******************************************************************
***  File Name		: RecipeController.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.06.13
***  Purpose       	: 予算に基づいた料理のレシピを生成して表示する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.13
*/

package com.example.demo.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.MonthModel;


@Controller
public class RecipeController {
	
	@Autowired
    private com.example.demo.service.BudgetService calculateBudgetProcess;
    
    @Autowired
    private com.example.demo.util.PromptProcess promptProcess;
    
    @Autowired
    private com.example.demo.service.ChatGPTService chatGPTService;
    
    @Autowired
    private com.example.demo.service.DifferService differService;

	
    /****************************************************************************
    *** Method Name         : suggestRecipe()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.06.13
    *** Function            : 予算に基づいた料理のレシピを生成して表示する
    *** Return              : レシピ提案画面(RecipeScreen)
    ****************************************************************************/
    
	@GetMapping("/recipe")
	String suggestRecipe(Model model/*, HttpSession session*/) {
		//int userid = (int) session.getAttribute("loggedInUser");
		int userid = 123;//仮
		
		//現在の年と月を取得
		YearMonth currentYearMonth = YearMonth.now();
		String thisYear = String.valueOf(currentYearMonth.getYear());
		String thisMonth = String.valueOf(currentYearMonth.getMonthValue());
		
		//差額計算メソッドを実行
		List<MonthModel> differPay = differService.differCalculation();
		
		int difference = 20000; 
		//食費の差額のみを取得
		//int difference = differPay.get(0).getDiffer();
		
		//1食分の予算を計算
		int recipeBudget = calculateBudgetProcess.calculateBudget(difference);
		
		//質問文を作成
		String prompt = promptProcess.generatePrompt(recipeBudget);

		//レシピ生成
		String recipeText = "";
	    if (!prompt.trim().isEmpty()) {
	    	recipeText = chatGPTService.chatGPT(prompt);
	    }
	    
		// モデルに値を設定
		model.addAttribute("difference", difference);
		model.addAttribute("budget", recipeBudget);
		model.addAttribute("recipe", recipeText);
		
		return "recipe"; 
	}
}

