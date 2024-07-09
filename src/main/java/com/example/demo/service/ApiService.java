/*******************************************************************
***  File Name		: ApiService.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.06.14
***  Purpose       	: APIキーの値を取得する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.14
*/

package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class ApiService {
	
	/****************************************************************************
     *** Method Name         : getApiKey()
     *** Designer            : 菅 匠汰
     *** Date                : 2024.06.14
     *** Function            : APIキーの値を取得する
     *** Return              : APIキー
     ****************************************************************************/
	public String getApiKey() {
		String apiKey = System.getenv("GPT_API_KEY"); //環境変数から取得
		return apiKey; //APIキー
	}
}
