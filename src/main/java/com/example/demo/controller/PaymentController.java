/*******************************************************************
***  File Name		: PaymentController.java
***  Version		: V1.0
***  Designer		: 佐藤　巧都
***  Date			: 2024.07.09
***  Purpose       	: Serviceから処理を呼び出し、収支登録、更新、削除の操作を行う
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 佐藤　巧都, 2024.07.09
*/

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.PaymentModel;
import com.example.demo.service.PaymentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

    private final PaymentService service;
    
    @Autowired
    private PaymentModel payment;
    
    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }
    
    /****************************************************************************
     *** Method Name         : goInput()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 収入支出画面を表示する
     *** Return              : 収入支出画面
     ****************************************************************************/
    @GetMapping("/input")
    public String goInput(Model model, HttpSession session) {
    	Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
    	payment.setUserId(userId);
    	model.addAttribute("payment", payment);
    	return "payment-input";
    }
    
    /****************************************************************************
     *** Method Name         : inputIncome()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 収入入力処理を行う
     *** Return              : 入力完了画面
     ****************************************************************************/
    @PostMapping("/inputIncome")
    public String inputIncome(
    			@RequestParam("date") String date,
    			@RequestParam("income") int income,
    			@RequestParam("itemId") String itemId,
    			Model model, HttpSession session) {
    	Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
    	
    // 日付を日付フォーマットに変換し、dayに設定する
    	try {
    		int day = Integer.parseInt(date.replaceAll("-", ""));
    		payment.setDay(day);
    	} catch (NumberFormatException e) {
    		model.addAttribute("error", "Invalid date format");
    		return "payment-input";
    	}
    	payment.setUserId(userId);
    	payment.setSpend(0);
    	payment.setIncome(income);
    	payment.setItemId(itemId);
    	model.addAttribute("payment", payment);
    	service.insertPayment(payment);
    	return "input-complete";
    }
    
    /****************************************************************************
     *** Method Name         : inputSpend()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 収入支出画面を表示する
     *** Return              : 入力完了画面
     ****************************************************************************/
    @PostMapping("/inputSpend")
    public String inputSpend(
    			@RequestParam("date") String date,
    			@RequestParam("spend") int spend,
    			@RequestParam("itemId") String itemId,
    			Model model, HttpSession session) {
    	Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
    	
    // 日付を日付フォーマットに変換し、dayに設定する
    	try {
    		int day = Integer.parseInt(date.replaceAll("-", ""));
    		payment.setDay(day);
    	} catch (NumberFormatException e) {
    		model.addAttribute("error", "Invalid date format");
    		return "payment-input";
    	}
    	payment.setUserId(userId);
    	payment.setIncome(0);
    	payment.setSpend(spend);
    	payment.setItemId(itemId);
    	model.addAttribute("payment", payment);
    	service.insertPayment(payment);
    	return "input-complete";
    }

    /****************************************************************************
     *** Method Name         : goUpdateAndDelete()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : データ一覧画面を表示する
     *** Return              : データ一覧画面
     ****************************************************************************/
    // データ一覧画面へ遷移
    @GetMapping("/record")
    public String goUpdateAndDelete(Model model, HttpSession session) {
    	Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
    	List<PaymentModel> list = service.findAll(userId);
        model.addAttribute("list", list);
        model.addAttribute("payment", payment);
        return "record"; 
    }
    
    /****************************************************************************
     *** Method Name         : goUpdateInput()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 収入支出画面を表示する
     *** Return              : 収入支出画面
     ****************************************************************************/
    @GetMapping("/updateInput")
    public String goUpdateInput(
            @RequestParam("userId") int userId,
            @RequestParam("id") int id, 
            Model model) {
    	payment.setId(id);
        PaymentModel payment = service.findById(id); // IDで検索してモデルを取得する
        if (payment == null) {
            // IDに対応するデータが見つからない場合の処理
            model.addAttribute("error", "Payment not found");
            return "record";
        }
        model.addAttribute("payment", payment);
        return "update-input";
    }

    /****************************************************************************
     *** Method Name         : updateIncome()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 収入更新処理を行う
     *** Return              : 更新完了画面
     ****************************************************************************/
    // 更新処理を行う
    @PostMapping("/updateIncome")
    public String updateIncome(
    		@RequestParam("date") String date,
			@RequestParam("income") int income,
			@RequestParam("itemId") String itemId,
			Model model) {
    	
    	int id = payment.getId();
    	PaymentModel payment = service.findById(id); // IDで検索してモデルを取得する
    	try {
    		int day = Integer.parseInt(date.replaceAll("-", ""));
    		payment.setDay(day);
    	} catch (NumberFormatException e) {
    		model.addAttribute("error", "Invalid date format");
    		return "record";
    	}
    	
    	payment.setIncome(income);
        payment.setSpend(0);
        payment.setItemId(itemId);
        int userId = payment.getUserId();
        int DAY = payment.getDay();
        int spend = payment.getSpend();
        model.addAttribute("payment", payment);
		service.paymentUpdate(income, spend, DAY, itemId, id, userId);
        return "update-complete";
    }
    
    /****************************************************************************
     *** Method Name         : updateSpend()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 支出更新処理を行う
     *** Return              : 更新完了画面
     ****************************************************************************/
    // 更新処理を行う
    @PostMapping("/updateSpend")
    public String updateSpend(
    		@RequestParam("date") String date,
			@RequestParam("spend") int spend,
			@RequestParam("itemId") String itemId,
			Model model) {
    	
    	int id = payment.getId();
    	PaymentModel payment = service.findById(id); // IDで検索してモデルを取得する
    	try {
    		int day = Integer.parseInt(date.replaceAll("-", ""));
    		payment.setDay(day);
    	} catch (NumberFormatException e) {
    		model.addAttribute("error", "Invalid date format");
    		return "record";
    	}
    	
    	payment.setIncome(0);
        payment.setSpend(spend);
        payment.setItemId(itemId);
        int userId = payment.getUserId();
        int DAY = payment.getDay();
        int income = payment.getIncome();
        model.addAttribute("payment", payment);
		service.paymentUpdate(income, spend, DAY, itemId, id, userId);
        return "update-complete";
    }
    
    
    /****************************************************************************
     *** Method Name         : deletePayment()
     *** Designer            : 佐藤　巧都
     *** Date                : 2024.07.09
     *** Function            : 削除処理を行う
     *** Return              : 削除完了画面
     ****************************************************************************/
    // 削除処理を行う
    @PostMapping("/delete")
    public String deletePayment(Model model, int id, int userId) {
    	 PaymentModel payment = service.findByIdAndUserId(id, userId);
    	 model.addAttribute("payment", payment);
         service.deleteAndRearrange(id, userId);
        return "delete-complete"; // Assuming delete-complete.html is your template file name
    }
}
