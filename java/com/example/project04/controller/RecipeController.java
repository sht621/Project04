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

package com.example.project04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RecipeController {
	
	@Autowired
    private com.example.project04.service.BudgetService calculateBudgetProcess;
    
    @Autowired
    private com.example.project04.PromptProcess PromptProcess;
    
    @Autowired
    private com.example.project04.service.ChatGPTService ChatGPTService;
    
//    @Autowired
//    private com.example.project04.service.DifferService differService;
//    差額を取得するクラスが実装されたらコメントから外す
	
    /****************************************************************************
    *** Method Name         : suggestRecipe()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.06.13
    *** Function            : 予算に基づいた料理のレシピを生成して表示する
    *** Return              : レシピ提案画面(RecipeScreen)
    ****************************************************************************/
    
	@GetMapping("/RecipeScreen")
	String suggestRecipe(Model model) {
		
		int difference = 20000;  //differService.differCalculation();が実装されたら置き換え
		
		//1食分の予算を計算
		int recipeBudget = calculateBudgetProcess.calculateBudget(difference);
		
		//質問文を作成
		String prompt = PromptProcess.generatePrompt(recipeBudget);

		//レシピ生成
		String recipeText = "";
	    if (!prompt.trim().isEmpty()) {
	    	recipeText = ChatGPTService.chatGPT(prompt);
	    }
	    
		// モデルに値を設定
		model.addAttribute("difference", difference);
		model.addAttribute("budget", recipeBudget);
		model.addAttribute("recipe", recipeText);
		
		return "RecipeScreen"; 
	}

}

