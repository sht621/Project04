/*******************************************************************
***  File Name		: DifferPatmentMapper.java
***  Version		: V1.1
***  Designer		: 東野　魁耶
***  Date		: 2024.07.02
***  Purpose       	: Serviceから呼ばれた処理を行うクラス。データベース処理を行う
***
*******************************************************************/

package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.example.demo.model.MonthModel;
import com.example.demo.model.PaymentModel;

@Mapper
@Component
public interface DifferPaymentMapper {
	
	
	/****************************************************************************
     *** Method Name         : selectPayment(@Param("userid") int userId, @Param("day") int day)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.07.02
     *** Function            : 受け取った年月、userIdを基にデータベースからデータを取り出す
     *** Return              : List<PaymentModel>
     ****************************************************************************/
	@Select("SELECT * FROM PAYMENT "
	        + "WHERE USERId = #{userid} AND DAY LIKE #{day} || '%'")
	List<PaymentModel> selectPayment(@Param("userid") int userId, @Param("day") int day);

    
    /****************************************************************************
     *** Method Name         : selectMonth(@Param("userid") int userId, @Param("month") int month)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 受け取った年月、userIdを基にデータベースからデータを取り出す
     *** Return              : List<MonthModel> 
     ****************************************************************************/
    @Select("SELECT * FROM MONTH "
    		+ "WHERE USERId = #{userid} AND MONTH = #{month}")
    List<MonthModel> selectMonth(@Param("userid") int userId, @Param("month") int month);
    
    /****************************************************************************
     *** Method Name         : updateMonthData(@Param("userId") int userId, @Param("month") int month,
                        			@Param("itemId") String itemId, @Param("spendSum") int spendSum,
                        			@Param("differ") int differ);
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 受け取った年月、userId、spendSum、differ、itemIdを基にデータベースを更新する
     *** Return              : 更新数
     ****************************************************************************/
    @Update("UPDATE MONTH SET spendSum = #{spendSum}, differ = #{differ} " +
            "WHERE userId = #{userId} AND month = #{month} AND itemId = #{itemId}")
    int updateMonthData(@Param("userId") int userId, @Param("month") int month,
                        @Param("itemId") String itemId, @Param("spendSum") int spendSum,
                        @Param("differ") int differ);
    
    /****************************************************************************
     *** Method Name         : updateTarget(@Param("userId") int userId, @Param("month") int month,
                     				@Param("itemId") String itemId, @Param("target") int target);
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 受け取った年月、userId、target、itemIdを基にデータベースを更新する
     *** Return              : 更新数
     ****************************************************************************/
    @Update("UPDATE MONTH SET target = #{target} " +
            "WHERE userId = #{userId} AND month = #{month} AND itemId = #{itemId}")
    int updateTarget(@Param("userId") int userId, @Param("month") int month,
                     @Param("itemId") String itemId, @Param("target") int target);
}
