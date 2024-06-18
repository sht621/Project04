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

package com.example.project04.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
	
	@Value("${gpt.api.key}")
	private String apiKey;
	
	/****************************************************************************
     *** Method Name         : getApiKey()
     *** Designer            : 菅 匠汰
     *** Date                : 2024.06.14
     *** Function            : APIキーの値を取得する
     *** Return              : APIキー
     ****************************************************************************/
	
	public String getApiKey() {
		return apiKey;
	}
}
