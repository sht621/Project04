/*******************************************************************
***  File Name		: HomeMapper.java
***  Version		: V1.0
***  Designer		: 菅 匠汰
***  Date			: 2024.07.02
***  Purpose       	: データベース処理を行う
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 菅 匠汰, 2024.07.02
*/

package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.example.demo.model.PaymentModel;

@Mapper
@Component
public interface HomeMapper {
	
    /****************************************************************************
    *** Method Name         : getIncomes()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 指定されたユーザと月の収支データを全て取得する
    *** Return              : PAYMENTデータ
    ****************************************************************************/
	
	@Select("SELECT * FROM PAYMENT "
	        + "WHERE USERId = #{userid} AND DAY LIKE #{day} || '%'")
	List<PaymentModel> getIncomes(@Param("userid") int userId, @Param("day") int day);
	
	
    /****************************************************************************
    *** Method Name         : getRecord()
    *** Designer            : 菅 匠汰
    *** Date                : 2024.07.02
    *** Function            : 指定されたユーザの入力履歴を新しい順に最大10件取得する
    *** Return              : 入力履歴データ
    ****************************************************************************/
	
	@Select("SELECT * FROM PAYMENT "
	        + "WHERE USERId = #{userid} "
	        + "ORDER BY ID DESC "
	        + "LIMIT 10")
	List<PaymentModel> getRecord(@Param("userid")int userId);

}
