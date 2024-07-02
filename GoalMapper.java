/*******************************************************************
***  File Name		: GoalMapper.java
***  Version		: V1.1
***  Designer		: 上村　結
***  Date			: 2024.06.29
***  Purpose       	: MonthModel.javaを経由してデータベースにINSERTを行い
***					  PaymentModel.javaを経由してデータベースにSELECTを行う
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 上村結, 2024.06.18
*** V1.1 : 上村結, 2024.06.29
*/

package com.example.demo.mapper;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.example.demo.model.MonthModel;
import com.example.demo.model.PaymentModel;

@Mapper
@Component
public interface GoalMapper {
	/****************************************************************************
	*** Method Name         : insert()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : MonthModelを引数にとり、そのフィールドの値をMONTHデータベースへ挿入する
	*** Return              : 成功したら0以上、失敗したら0未満の値を返す
	****************************************************************************/
	@Insert("INSERT INTO MONTH(Id, Target, SpendSum, Differ, Month, ItemId, UserId) "
			+ "VALUES(#{id} ,#{target}, #{spendSum}, #{differ}, #{month}, #{itemId}, #{userId})")
	int insert(MonthModel model);
	
	
	/****************************************************************************
	*** Method Name         : selectFromPaymentByDay()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : PAYMENTデータベースからユーザID、商品項目、現在の月の初日、現在の月の最終日
	***						  を引数にとり、データをもってきて、そのデータのリストを返す
	*** Return              : PAYMENTデータベースからとってきたデータをリストで返す
	****************************************************************************/
	@Select("SELECT * FROM PAYMENT "
			+ "WHERE UserId = #{userId} "
			+ "AND ItemId = #{itemId} AND Day BETWEEN #{startDay} AND #{endDay}")
	ArrayList<PaymentModel> selectFromPaymentByDay(@Param("userId") int userId, @Param("itemId") String itemId,
									@Param("startDay") int startDay, @Param("endDay") int endDay);
	
	
	/****************************************************************************
	*** Method Name         : selectMaxIdFromMonth()
	*** Designer            : 上村　結
	*** Date                : 2024.06.18
	*** Function            : MONTHデータベースのIDの最大値を取り出す
	*** Return              : MONTHデータベースのIDの最大値を返す
	****************************************************************************/
	@Select("SELECT MAX(Id) FROM MONTH")
	Integer selectMaxIdFromMonth();
	
	
	/****************************************************************************
	*** Method Name         : isExisting()
	*** Designer            : 上村　結
	*** Date                : 2024.06.29
	*** Function            : 今月のデータがすでにあるかどうかを確認する
	*** Return              : MONTHデータベースからとってきたデータをリストで返す
	****************************************************************************/
	@Select("SELECT * FROM MONTH "
			+ "WHERE MONTH = #{month} AND USERID = #{userId}")
	ArrayList<MonthModel> isExisting(@Param("month") int month, @Param("userId") int userId);
	
}