/*******************************************************************
***  File Name	: PaymentMapper.java
***  Version	: V1.0
***  Designer	: 佐藤　巧都
***  Date		: 2024.07.09
***  Purpose    : Serviceから呼ばれた処理を行うクラス。データベース処理を行う
***
*******************************************************************/

package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.PaymentModel;

@Mapper
public interface PaymentMapper {
	
	/****************************************************************************
     *** Method Name         : selectMaxIdFromPayment()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : idの値が最も大きいデータをセレクトする
     *** Return              : Integer
     ****************************************************************************/
	@Select("SELECT MAX(Id) FROM PAYMENT")
	Integer selectMaxIdFromPayment();
	
	/****************************************************************************
     *** Method Name         : findAll()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 取得したuseridの値に合致するデータをListとしてセレクトする
     *** Return              : List<PaymentModel>
     ****************************************************************************/
    @Select("SELECT * FROM PAYMENT WHERE USERID = #{userId} ORDER BY DAY DESC, ID DESC")
    List<PaymentModel> findAll(@Param("userId") int userId);

    /****************************************************************************
     *** Method Name         : inputPayment()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 取得した値をデータベースに挿入する
     *** Return              : int
     ****************************************************************************/
    @Insert("INSERT INTO PAYMENT (ID, INCOME, SPEND, DAY, ITEMID, USERID) VALUES (#{id}, #{income}, #{spend}, #{day}, #{itemId}, #{userId})")
    int inputPayment(PaymentModel payment);

    /****************************************************************************
     *** Method Name         : updatePayment()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 特定のデータを取得した値に更新する
     *** Return              : int
     ****************************************************************************/
    @Update("UPDATE PAYMENT SET INCOME = #{income}, SPEND = #{spend}, DAY = #{day}, ITEMID = #{itemId} WHERE ID = #{id} AND USERID = #{userId}")
    int updatePayment(@Param("income") int income, @Param("spend") int spend, @Param("day") int day, @Param("itemId") String itemId, @Param("id") int id, @Param("userId") int userId);
    
    /****************************************************************************
     *** Method Name         : findById()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 取得したidに合致するデータをセレクトする
     *** Return              : PaymentModel
     ****************************************************************************/
    @Select("SELECT * FROM PAYMENT WHERE ID = #{id}")
    PaymentModel findById(@Param("id") int id);
    
    /****************************************************************************
     *** Method Name         : findByIdAndUserId()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 取得したidとuserIdに合致するデータをセレクトする
     *** Return              : PaymentModel
     ****************************************************************************/
    @Select("SELECT * FROM PAYMENT WHERE ID = #{id} AND USERID = #{userId}")
    PaymentModel findByIdAndUserId(@Param("id") int id, @Param("userId") int userId);
    
    /****************************************************************************
     *** Method Name         : deletePaymentById
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 指定されたidとuserIdに合致するデータを削除する
     *** Return              : なし
     ****************************************************************************/
    @Delete("DELETE FROM PAYMENT WHERE ID = #{id} AND USERID = #{userId}")
    void deletePaymentById(int id, int userId);

    /****************************************************************************
     *** Method Name         : decrementIdsAfter()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 削除されたデータより後ろのデータのid値をそれぞれ-1する
     *** Return              : int
     ****************************************************************************/
    @Update("UPDATE PAYMENT SET ID = ID - 1 WHERE ID > #{id}")
    int decrementIdsAfter(int id);
    
    
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
		+ "ORDER BY DAY DESC, ID DESC "
		+ "LIMIT 10")
	List<PaymentModel> getRecord(@Param("userid")int userId);
}