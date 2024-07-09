package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.PaymentMapper;
import com.example.demo.model.PaymentModel;

@Service
@Transactional
public class PaymentService {

    private final PaymentMapper mapper;
    
    @Autowired
    public PaymentService(PaymentMapper mapper) {
        this.mapper = mapper;
    }

    public List<PaymentModel> findAll(int userId) {
        return mapper.findAll(userId);
    }

    public PaymentModel insertIncome(PaymentModel payment) {
    	int max = 0;
    	if(mapper.selectMaxIdFromPayment() != null) {
			max = mapper.selectMaxIdFromPayment().intValue();
		}
		else {
			max = 0;
		}
		int startId = max + 1;	
    	payment.setId(startId);
    	mapper.inputIncome(payment);
        return payment;
    }

    public PaymentModel insertSpend(PaymentModel payment) {
    	int max = 0;
    	if(mapper.selectMaxIdFromPayment() != null) {
			max = mapper.selectMaxIdFromPayment().intValue();
		}
		else {
			max = 0;
		}
		int startId = max + 1;	
    	payment.setId(startId);
    	mapper.inputSpend(payment);
        return payment;
    }

    @Transactional
    public int paymentUpdate(int income, int spend, int day, String itemId, int id, int userId) {
        return mapper.updatePayment(income, spend, day, itemId, id, userId);
    }
    
    public PaymentModel findById(int id) {
        return mapper.findById(id);  // 引数を PaymentModel から int に変更
    }
    
    public PaymentModel findByIdAndUserId(int id, int userId) {
        return mapper.findByIdAndUserId(id, userId);
    }
    
    @Transactional
    public void deleteAndRearrange(int id, int userId) {
    	 
    	try {
    	        // データの削除
    	        mapper.deletePaymentById(id, userId);
    	        // IDの更新
    	        mapper.decrementIdsAfter(id);
    	    } catch (Exception e) {
    	        // エラーハンドリング
    	        throw new RuntimeException("Error occurred while deleting and rearranging payments", e);
    	    }
    	
    }
}
