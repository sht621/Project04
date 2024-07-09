/*******************************************************************
***  File Name		: BudgetService.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.06.13
***  Purpose       	: 今月の食費の差額から1食分の予算を計算する
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.06.13
*/

package com.example.demo.service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class BudgetService {
	
	/****************************************************************************
    *** Method Name         : calculateBudget()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.06.13
    *** Function            : 今月の食費の差額から1食分の予算を計算する
    *** Return              : 1食分の予算
    ****************************************************************************/
	
	public int calculateBudget(int difference) {
		//差額が負の値の場合は予算を0円とする
		if(difference < 0) {
			difference = 0;
		}
		
		LocalDate today = LocalDate.now(); //今日の日付を取得
		YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth());
		LocalDate endOfMonth = yearMonth.atEndOfMonth(); //今月末の日付を取得
		long remainingDays = ChronoUnit.DAYS.between(today, endOfMonth); //今月の残り日数を計算
		
		int recipeBudget = (int) (difference / (remainingDays * 3)); //1食分の予算を計算
	
		return recipeBudget; //1食分の予算
	}
}
