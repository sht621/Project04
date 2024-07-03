/*******************************************************************
***  File Name		: PromptProcess.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.06.13
***  Purpose       	: ChatGPTに送る文章を生成する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.13
*/

package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class PromptProcess {
	
    /****************************************************************************
    *** Method Name         : generatePrompt()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.06.13
    *** Function            : ChatGPTに送るプロンプトを作成する
    *** Return              : プロンプト
    ****************************************************************************/
	
	public String generatePrompt(int budget) {
		//GPTに送る文章
		String prompt = String.format("一人暮らし向けの料理のレシピを考えてください。"
					  + "1食あたりの予算は%d円です。材料は必ず箇条書きにすること。"
					  + "料理名、材料、作り方は必ず書き、他の余計なことは書かないこと。"
					  + "材料は【材料】、作り方は【作り方】という見出しをそれぞれ付けること。"
					  + "作り方は分かりやすく詳細に書くこと。材料ごとの費用は書かないこと。"
					  + "調味料の費用は除いた費用にするが、材料には分量も含めて表記すること。"
					  + "最初に料理名を出すこと。料理名は『』で囲うこと", budget);
	
        return prompt;
    }
}
