/*******************************************************************
***  File Name        : GraphController.java
***  Version          : V1.1
***  Designer         : 保泉 雄祐
***  Date             : 2024.07.07
***  Purpose          : グラフ表示ページのコントロールを行う
***
*******************************************************************/

package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.MonthModel;
import com.example.demo.service.GraphService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GraphController {

    @Autowired
    private GraphService graphService;

    /****************************************************************************
    *** Method Name         : displayGraph
    *** Designer            : 保泉 雄祐
    *** Date                : 2024.07.07
    *** Function            : グラフ表示ページを表示し、ユーザー入力を処理する
    *** Return              : グラフ表示ページのビュー名
    ****************************************************************************/
    @GetMapping("/graph")
    public String displayGraph(@RequestParam(required = false) Integer year,
                               @RequestParam(required = false) Integer month,
                               Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("loggedInUser");
        if (userId == null) {
            return "redirect:/login";
        }

        LocalDate today = LocalDate.now();
        year = (year != null) ? year : today.getYear();
        month = (month != null) ? month : today.getMonthValue();

        return getGraphData(model, userId, year, month);
    }

    /****************************************************************************
    *** Method Name         : getGraphData
    *** Designer            : 保泉 雄祐
    *** Date                : 2024.07.07
    *** Function            : グラフデータを取得し、モデルに追加する
    *** Return              : グラフ表示ページのビュー名
    ****************************************************************************/
    private String getGraphData(Model model, int userId, int year, int month) {
        List<MonthModel> graphData = graphService.getGraphData(userId, year, month);
        model.addAttribute("graphData", graphData);
        
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("userId", userId);

        return "graph";
    }
}