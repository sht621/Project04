/*******************************************************************
***  File Name		: GPTProcess.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.06.13
***  Purpose       	: GPTに指示文を送り、レシピを生成する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.13
*/

package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {
    private static final int MAX_RETRIES = 3; // 最大リトライ回数
    private static final long RETRY_DELAY_MS = 1000; // リトライ間隔（ミリ秒）
    
	/****************************************************************************
     *** Method Name         : chatGPT()
     *** Designer            : 菅 匠汰
     *** Date                : 2024.06.13
     *** Function            : ChatGPTに指示文を送り、生成された文章を返す
     *** Return              : レシピ文章
     ****************************************************************************/
    
    @Autowired
    private ApiService apiService;
    
    public String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = apiService.getApiKey(); // APIキー メソッドで取得
        String model = "gpt-3.5-turbo"; //使用モデル

        int retry = 0;
        while (retry < MAX_RETRIES) { //回数制限でリクエストの制限超過を防ぐ
            try {
            	//ChatGPTに接続
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json");

                // リクエスト送信
                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\","
                		    + " \"content\": \"" + prompt + "\"}]}";
                connection.setDoOutput(true);
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(body);
                    writer.flush();
                }

                // レスポンス取得
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }
                        
                        //不要な部分を切り取り
                        response.toString();
                        int start = response.indexOf("content") + 11;
                        int end = response.indexOf("\"", start);
                        String recipeText = response.substring(start, end);
                        
                        return recipeText; //生成したレシピ
                    }
                } else if (responseCode == 429) {
                    // 429 エラーの場合、リトライする
                    System.out.println("Rate limit exceeded. Retrying in " + RETRY_DELAY_MS + " milliseconds...");
                    Thread.sleep(RETRY_DELAY_MS);
                    retry++;
                } else {
                	//それ以外のエラー
                    return "レシピを生成できませんでした";
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "レシピを生成できませんでした";
            }
        }
        return "レシピを生成できませんでした";
    }
}
