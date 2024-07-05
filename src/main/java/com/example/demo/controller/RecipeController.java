/*******************************************************************
***  File Name		: RecipeController.java
***  Version		: V1.2
***  Designer		: 菅 匠汰
***  Date			: 2024.07.05
***  Purpose       	: レシピ提案画面を表示する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.13
*** V1.1 : 菅 匠汰, 2024.07.04
*** V1.2 : 菅 匠汰, 2024.07.05
*/

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeController {
	
	@Autowired
    private com.example.demo.service.RecipeService recipeService;

    /****************************************************************************
    *** Method Name         : displayRecipe()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.05
    *** Function            : レシピ提案画面を表示する
    *** Return              : レシピ提案画面
    ****************************************************************************/
    
	@GetMapping("/recipe")
	String displayRecipe(Model model, HttpSession session) {
		Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
		
        //メソッドで表示するデータを取得
        int[] recipeData = recipeService.getRecipeData(userId);
        
        int difference = recipeData[0];
        int recipeBudget = recipeData[1];
        
        //メソッドでレシピを取得
        String recipeText = recipeService.getRecipeText(recipeBudget); 
	    
		// モデルに値を設定
	    model.addAttribute("userId", userId);
		model.addAttribute("difference", difference);
		model.addAttribute("budget", recipeBudget);
		model.addAttribute("recipe", recipeText);
		
		return "recipe"; 
	}
}

