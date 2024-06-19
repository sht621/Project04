/*******************************************************************
***  File Name		: DifferController.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.06.18
***  Purpose       	: Serviceクラスを呼び出し画面遷移の処理を行う
***
*******************************************************************/

package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.MonthModel;
import com.example.demo.service.DifferService;

@Controller
public class DifferController {
    private final DifferService paymentService;

    @Autowired
    public DifferController(DifferService paymentServise) {
        this.paymentService = paymentServise;
    }
    
    /****************************************************************************
    *** Method Name         : displayDiffer(Model model)
    *** Designer            : 東野　魁耶
    *** Date                : 2024.06.18
    *** Function            : 現在の年月を計算し、それを基に差額計算を行い、
    						　対応したhtmlを返す
    *** Return              : htmlファイル
    ****************************************************************************/
    @GetMapping("/difference")
    public String displayDiffer(Model model) {
        // 年の選択肢を生成（2024年から2026年まで）
        List<Integer> years = generateYearList();
        model.addAttribute("years", years);
        
        // 月の選択肢を生成（01月から12月まで）
        List<String> months = generateMonthList();
        model.addAttribute("months", months);
        
        // 初回表示は現在の年月で表示
        List<MonthModel> differ = paymentService.differCalculation();
        model.addAttribute("differ", differ);
        
        return "difference.html";
    }
    
    /****************************************************************************
     *** Method Name         : displayDifferByMonth(@RequestParam("year") int year, 
     											@RequestParam("month") int month, Model model)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : htmlファイルから受けとった年月を基に差額計算を行い
     						　対応したhtmlを返す
     *** Return              : htmlファイル
     ****************************************************************************/
    @PostMapping("/difference")
    public String displayDifferByMonth(@RequestParam("year") int year, @RequestParam("month") int month, Model model) {
        // 年の選択肢を生成（2024年から2026年まで）
        List<Integer> years = generateYearList();
        model.addAttribute("years", years);
        
        // 月の選択肢を生成（01月から12月まで）
        List<String> months = generateMonthList();
        model.addAttribute("months", months);
        
        List<MonthModel> differ = paymentService.differCalculation(year, month);
        model.addAttribute("differ", differ);
        return "difference.html";
    }
    
    
    /****************************************************************************
     *** Method Name         : updatedisplay(Model model)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 年月の取得を行いデータベースを更新する次のhtmlを返す
     *** Return              : htmlファイル
     ****************************************************************************/
    @GetMapping("/updatediffer")
    public String updatedisplay(Model model) {
        // 年の選択肢を生成（2024年から2026年まで）
        List<Integer> years = generateYearList();
        model.addAttribute("years", years);
        
        // 月の選択肢を生成（01月から12月まで）
        List<String> months = generateMonthList();
        model.addAttribute("months", months);
        
        
        return "updatediffer.html";
    }
    
    /****************************************************************************
     *** Method Name         : updateTarget(Model model, @RequestParam("year") int year, @RequestParam("month") String month,
                			   @RequestParam("itemId") String itemId, @RequestParam(value = "target", required = false) Integer target,
                               RedirectAttributes redirectAttributes)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : htmlから受けとった値を基にデータベース更新を行う
     *** Return              : htmlファイル
     ****************************************************************************/
    @PostMapping("/updatetarget")
    public String updateTarget(Model model, @RequestParam("year") int year, @RequestParam("month") String month,
                			   @RequestParam("itemId") String itemId, @RequestParam(value = "target", required = false) Integer target,
                               RedirectAttributes redirectAttributes) {
        try {
            int monthInt = Integer.parseInt(month);
            int updateYear = year * 100 + monthInt;
            paymentService.updateTarget(updateYear, itemId, target);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("message", "目標金額に正しく数字を入力してください");
            return "redirect:/errornumber";
        }

        // 更新が完了したらリダイレクトするなどの処理を行う
        List<Integer> years = generateYearList();
        model.addAttribute("years", years);

        // 月の選択肢を生成（01月から12月まで）
        List<String> months = generateMonthList();
        model.addAttribute("months", months);

        // 初回表示は現在の年月で表示
        List<MonthModel> differ = paymentService.differCalculation();
        model.addAttribute("differ", differ);

        return "redirect:/difference"; // 更新後の画面にリダイレクト
    }

    /****************************************************************************
     *** Method Name         : handleNumberFormatException(NumberFormatException e, Model model)
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 例外が起こった場合の処理行う
     *** Return              : htmlファイル
     ****************************************************************************/
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(NumberFormatException e, Model model) {
        model.addAttribute("message", "目標金額に正しく数字を入力してください");
        return "errornumber";
    }
    
    /****************************************************************************
     *** Method Name         : generateYearList()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 年の生成を行う
     *** Return              : List<Integer>
     ****************************************************************************/
    // 年の選択肢を生成するメソッド
    private List<Integer> generateYearList() {
        List<Integer> years = new ArrayList<>();
        for (int year = 2024; year <= 2026; year++) {
            years.add(year);
        }
        return years;
    }
    
    /****************************************************************************
     *** Method Name         : generateMonthList()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : 月の生成を行う
     *** Return              : List<Integer>
     ****************************************************************************/
    private List<String> generateMonthList() {
        List<String> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            String monthStr = String.format("%02d", month); // 1桁の月を2桁にフォーマット
            months.add(monthStr);
        }
        return months;
    }
}
