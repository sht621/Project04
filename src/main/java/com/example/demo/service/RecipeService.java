/*******************************************************************
***  File Name		: RecipeService.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.07.05
***  Purpose       	: レシピ画面に表示するデータを取得する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.07.05
*/

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MonthModel;

@Service
public class RecipeService {
	
    @Autowired
    private com.example.demo.service.DifferService differService;
    
	@Autowired
    private com.example.demo.service.BudgetService calculateBudgetProcess;
	
    @Autowired
    private com.example.demo.util.PromptProcess promptProcess;
    
    @Autowired
    private com.example.demo.service.ChatGPTService chatGPTService;
    
    /****************************************************************************
    *** Method Name         : getRecipeData()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.05
    *** Function            : レシピ画面に表示するデータを取得する
    *** Return              : レシピ画面に表示するデータ
    ****************************************************************************/
	
	public int[] getRecipeData(int userId) {
		int[]  recipeData = new int[2];
		
		//差額計算メソッドを実行
		List<MonthModel> differPay = differService.differCalculation(userId);
		
		//食費の差額のみを取得
		int difference = differPay.get(0).getDiffer();
		
		//1食分の予算を計算
		int recipeBudget = calculateBudgetProcess.calculateBudget(difference);
		
		recipeData[0] = difference;
		recipeData[1] = recipeBudget;
		
		return recipeData; //レシピ提案画面に表示するデータ
	}
	
    /****************************************************************************
    *** Method Name         : getRecipeText()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.05
    *** Function            : レシピ文章を取得する
    *** Return              : レシピ文章
    ****************************************************************************/
	
	public String getRecipeText(int recipeBudget){
		//質問文を作成
		String prompt = promptProcess.generatePrompt(recipeBudget);

		//レシピ生成
	    String recipeText = chatGPTService.chatGPT(prompt);
	    
	    return recipeText; //レシピ文章
	}
}
