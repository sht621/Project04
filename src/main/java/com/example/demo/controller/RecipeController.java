/*******************************************************************
***  File Name		: RecipeController.java
***  Version		: V1.1
***  Designer		: 菅 匠汰
***  Date			: 2024.07.04
***  Purpose       	: 予算に基づいた料理のレシピを生成して表示する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.13
*** V1.1 : 菅 匠汰, 2024.07.04
*/

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.MonthModel;

import jakarta.servlet.http.HttpSession;

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
    *** Return              : レシピ提案画面
    ****************************************************************************/
    
	@GetMapping("/recipe")
	String suggestRecipe(Model model, HttpSession session) {
		Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
		
		//差額計算メソッドを実行
		List<MonthModel> differPay = differService.differCalculation(userId);
		
		//食費の差額のみを取得
		int difference = differPay.get(0).getDiffer();
		
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
	    model.addAttribute("userId", userId);
		model.addAttribute("difference", difference);
		model.addAttribute("budget", recipeBudget);
		model.addAttribute("recipe", recipeText);
		
		return "recipe"; 
	}
}

