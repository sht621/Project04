/*******************************************************************
***  File Name		: LoginController.java
***  Version		: V1.0
***  Designer		: 堀江咲希
***  Date		　　: 2024.06.18
***  Purpose       	: ログイン処理
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 堀江咲希, 2024.06.18
*/


package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.LoginUserModel;
import com.example.demo.service.LoginUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final LoginUserService userService;
    
    
    /****************************************************************************
    *** Method Name         : LoginController(LoginUserService userService)
    *** Designer            : 堀江咲希
    *** Date                : 2024.06.18
    *** Function            : UserServiceを挿入する
    *** Return              : なし
    ****************************************************************************/
    @Autowired
    public LoginController(LoginUserService userService) {
        this.userService = userService;

    }
    
    /****************************************************************************
     *** Method Name         : loginUser(Model model)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ログイン画面に飛ぶ
     *** Return              : Login.html
     ****************************************************************************/
    @GetMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("User", new LoginUserModel());
        return "Login.html";
    }

    /****************************************************************************
     *** Method Name         : create( LoginUserModel user, Model model, HttpSession session)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : ログイン画面での処理
     *** Return              : 管理者ならuselist.html
                               データベースと一致したらhome.html
                               他はerrorに飛ぶ
     ****************************************************************************/
    @PostMapping("/login")
    public String create(@Validated @ModelAttribute LoginUserModel user, Model model, HttpSession session) {
    	
    	
    	
    	String enteredUsername = user.getUserid();
    	String enteredUserpass = user.getPass();

    	List<LoginUserModel> use = userService.selectAll(); 
    	
        for (LoginUserModel existingUser : use) {
        	if(enteredUsername.equals("kanri00") && enteredUserpass.equals("kanri010")){
        		
        		return "redirect:userlist";
        		
        	}
        	else if (enteredUsername.equals(existingUser.getUserid()) && enteredUserpass.equals(existingUser.getPass())) {
        		
        		session.setAttribute("loggedInUser", existingUser.getId());
                model.addAttribute("users", use);
                return "redirect:home";
                
            }else if(enteredUsername.equals(existingUser.getUserid()) && !enteredUserpass.equals(existingUser.getPass())) {
            	
            	model.addAttribute("error", "パスワードが違います");
                return "redirect:login?error=true";
            	
            }
        }
        model.addAttribute("error2", "このアカウントは存在しません");
        
    	
        return "redirect:login?error2=true";
    	
    	
    }
    
    /****************************************************************************
     *** Method Name         : addUser(Model model)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : 新規登録画面に飛ぶ
     *** Return              : newuser.html
     ****************************************************************************/
    
    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("User", new LoginUserModel());
        return "newuser.html";
    }
    
    /****************************************************************************
     *** Method Name         : newcreate(@Validated LoginUserModel user, Model model)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : 新規登録画面での処理
     *** Return              : 新規アカウントであればLogin.html
                               それ以外はエラーメッセージに飛ぶ
     ****************************************************************************/
    @PostMapping("/new")
    public String newcreate(@Validated @ModelAttribute LoginUserModel user, Model model) {
        
        
        String enteredUsername = user.getUserid();
    	String enteredUserpass = user.getPass();
    	

    	List<LoginUserModel> use = userService.selectAll(); 
    	boolean validCredentials = true;
    	

        for (LoginUserModel existingUser : use) {
        	if(enteredUsername.equals(existingUser.getUserid()) && enteredUserpass.equals(existingUser.getPass())) {
        		validCredentials = false;
        		model.addAttribute("error2", "このアカウントは存在します");
        		
        		return "redirect:new?error2=true";
        	}
        	
        	else if (enteredUsername.equals(existingUser.getUserid()) 
        			&& !enteredUserpass.equals(existingUser.getPass())) {
        		validCredentials = false;
        		model.addAttribute("error", "この名前は使用できません");
                
                return "redirect:new?error=true";
            }
        }
        
        user.Id = use.size()+1;
        userService.insert(user);
        return "redirect:login";
    }
    
    /****************************************************************************
     *** Method Name         : displayUsers(Model model)
     *** Designer            : 堀江咲希
     *** Date                : 2024.06.18
     *** Function            : 登録しているユーザのリストを表示する
     *** Return              : UserList.html
     ****************************************************************************/
    @GetMapping("/userlist")
    public String displayUsers(Model model) {
        List<LoginUserModel> users = userService.selectAll();
        model.addAttribute("users", users);
        return "UserList.html";
    }
    

}
