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
	
	@Select("SELECT MAX(Id) FROM PAYMENT")
	Integer selectMaxIdFromPayment();
	
    @Select("SELECT * FROM PAYMENT WHERE USERID = #{userId} ORDER BY DAY DESC")
    List<PaymentModel> findAll(@Param("userId") int userId);

    @Insert("INSERT INTO PAYMENT (ID, INCOME, SPEND, DAY, ITEMID, USERID) VALUES (#{id}, #{income}, #{spend}, #{day}, #{itemId}, #{userId})")
    int inputPayment(PaymentModel payment);

    @Update("UPDATE PAYMENT SET INCOME = #{income}, SPEND = #{spend}, DAY = #{day}, ITEMID = #{itemId} WHERE ID = #{id} AND USERID = #{userId}")
    int updatePayment(@Param("income") int income, @Param("spend") int spend, @Param("day") int day, @Param("itemId") String itemId, @Param("id") int id, @Param("userId") int userId);
    
    @Select("SELECT * FROM PAYMENT WHERE ID = #{id}")
    PaymentModel findById(@Param("id") int id);
    
    @Select("SELECT * FROM PAYMENT WHERE ID = #{id} AND USERID = #{userId}")
    PaymentModel findByIdAndUserId(@Param("id") int id, @Param("userId") int userId);
    
    @Delete("DELETE FROM PAYMENT WHERE ID = #{id} AND USERID = #{userId}")
    void deletePaymentById(int id, int userId);

    @Update("UPDATE PAYMENT SET ID = ID - 1 WHERE ID > #{id}")
    int decrementIdsAfter(int id);
}
