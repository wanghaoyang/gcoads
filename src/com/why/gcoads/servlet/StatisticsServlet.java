package com.why.gcoads.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.why.gcoads.model.Echarts;
import com.why.gcoads.model.Series;
import com.why.gcoads.service.graduate.GraduateService;

@WebServlet("/StatisticsServlet")
public class  StatisticsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private GraduateService graduateService = new GraduateService();
    
    public void ajaxEmploymentStatistics(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
        
        List<String> legend=new ArrayList<String>(Arrays.asList(new String[]{"最高值","最低值"}));
        List<String> axis=new ArrayList<String>(Arrays.asList(new String[]{"周一","周二","周三","周四","周五","周六","周日"}));
        List<Series> series=new ArrayList<Series>();
        series.add(new Series("最高值","line",new ArrayList<Integer>(Arrays.asList(21,23,28,26,21,33,44))));
        series.add(new Series("最低值","line",new ArrayList<Integer>(Arrays.asList(-2,-12,10,0,20,11,-6))));
        Echarts echarts=new Echarts(legend,axis,series);
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(echarts));
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out=resp.getWriter();
        out.println(objectMapper.writeValueAsString(echarts));
        out.flush();
        out.close();
        
    }
    
    public String employmentStatistics(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        return "r:/jsps/employmentstatistics.jsp";
    }
}