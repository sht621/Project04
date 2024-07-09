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
    private PaymentModel payment;
    
    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }
    
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
    
    @PostMapping("/inputIncome")
    public String inputIncome(
    			@RequestParam("date") String date,
    			@RequestParam("income") int income,
    			@RequestParam("itemId") String itemId,
    			Model model) {
    	
    // 日付を日付フォーマットに変換し、dayに設定する
    	try {
    		int day = Integer.parseInt(date.replaceAll("-", ""));
    		payment.setDay(day);
    	} catch (NumberFormatException e) {
    		model.addAttribute("error", "Invalid date format");
    		return "payment-input";
    	}
    	
    	payment.setSpend(0);
    	
    	payment.setIncome(income);
    	payment.setItemId(itemId);
    	model.addAttribute("payment", payment);
    	service.insertIncome(payment);
    	return "input-complete";
    }

    @PostMapping("/inputSpend")
    public String inputSpend(
    			@RequestParam("date") String date,
    			@RequestParam("spend") int spend,
    			@RequestParam("itemId") String itemId,
    			Model model) {
    // 日付を日付フォーマットに変換し、dayに設定する
    	try {
    		int day = Integer.parseInt(date.replaceAll("-", ""));
    		payment.setDay(day);
    	} catch (NumberFormatException e) {
    		model.addAttribute("error", "Invalid date format");
    		return "payment-input";
    	}
    	
    	payment.setIncome(0);
    	payment.setSpend(spend);
    	payment.setItemId(itemId);
    	model.addAttribute("payment", payment);
    	service.insertSpend(payment);
    	return "input-complete";
    }


    // 更新画面へ遷移
    @GetMapping("/update")
    public String goUpdate(Model model, HttpSession session) {
    	Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
    	List<PaymentModel> list = service.findAll(userId);
        model.addAttribute("list", list);
        model.addAttribute("payment", payment);
        return "update"; 
    }
    
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
            return "update";
        }
        model.addAttribute("payment", payment);
        return "update-input";
    }

    
    // 更新処理を行う
    @PostMapping("/updateInput")
    public String updateInput(
    		@RequestParam("date") String date,
			@RequestParam("spend") int spend,
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
    		return "update";
    	}
    	
    	payment.setIncome(income);
        payment.setSpend(spend);
        payment.setItemId(itemId);
        int userId = payment.getUserId();
        int DAY = payment.getDay();
        model.addAttribute("payment", payment);
		service.paymentUpdate(income, spend, DAY, itemId, id, userId);
        return "update-complete";
    }

    // 削除画面へ遷移
    @GetMapping("/delete")
    public String goDelete(Model model, HttpSession session) {
    	Object loggedInUser =  session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; //idを取得できない場合はログイン画面にリダイレクト
        }
        int userId = (int) loggedInUser;
    	List<PaymentModel> list = service.findAll(userId);
        model.addAttribute("list", list);
        model.addAttribute("userId", userId);
        return "delete"; // Assuming delete.html is your template file name
    }

    // 削除処理を行う
    @PostMapping("/delete")
    public String deletePayment(Model model, int id, int userId) {
    	 PaymentModel payment = service.findByIdAndUserId(id, userId);
    	 model.addAttribute("payment", payment);
         service.deleteAndRearrange(id, userId);
        return "delete-complete"; // Assuming delete-complete.html is your template file name
    }
}
