package com.why.gcoads.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.why.gcoads.model.Echarts;
import com.why.gcoads.model.EmploymentStatistics;
import com.why.gcoads.model.Series;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.service.statistics.EmploymentStatisticsService;
import com.why.gcoads.utils.StringUtil;

@WebServlet("/StatisticsServlet")
public class StatisticsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private GraduateService graduateService = new GraduateService();
    private EmploymentStatisticsService employmentStatisticsService = new EmploymentStatisticsService();

    public void ajaxEmploymentStatistics(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        String key = req.getParameter("key");
        String value = req.getParameter("value");
        if (StringUtil.isNullOrEmpty(key) || StringUtil.isNullOrEmpty(value)) {
            return;
        }
        List<EmploymentStatistics> findEmploymentStatistics = null;
        List<String> axis = null;
        List<Integer> gcount = null;
        List<Integer> ecount = null;
        if (!"xueyuan".equals(key)) {
            // List<Integer> yearList = employmentStatisticsService.findYear();
            int year = -1;
            try {
                year = Integer.parseInt(value);
            } catch (NumberFormatException nfe) {
                return;
            }
            findEmploymentStatistics = employmentStatisticsService
                    .findEmploymentStatisticsByYear(year);
            if (findEmploymentStatistics != null) {
                axis = new ArrayList<String>();
                gcount = new ArrayList<Integer>();
                ecount = new ArrayList<Integer>();
                for (EmploymentStatistics es : findEmploymentStatistics) {
                    axis.add(es.getXueyuan());
                    gcount.add(es.getGraduatecount());
                    ecount.add(es.getEmploymentcount());
                }
            }

        } else {
            findEmploymentStatistics = employmentStatisticsService
                    .findEmploymentStatisticsByXueyuan(value);
            if (findEmploymentStatistics != null) {
                axis = new ArrayList<String>();
                gcount = new ArrayList<Integer>();
                ecount = new ArrayList<Integer>();
                int i=0;
                for (EmploymentStatistics es : findEmploymentStatistics) {
                    axis.add(String.valueOf(es.getYear()));
                    gcount.add(es.getGraduatecount());
                    ecount.add(es.getEmploymentcount());
                    if ( i > 10){
                        break;
                    }
                    i++;
                }
            }

        }

        if (axis == null) {
            axis = new ArrayList<String>();
        }
        if (gcount == null) {
            gcount = new ArrayList<Integer>();
        }
        if (ecount == null) {
            ecount = new ArrayList<Integer>();
        }

        List<String> legend = new ArrayList<String>(Arrays.asList(new String[] {
                "毕业人数", "就业人数" }));
        List<Series> series = new ArrayList<Series>();
        series.add(new Series("毕业人数", "line", gcount));
        series.add(new Series("就业人数", "line", ecount));
        Echarts echarts = new Echarts(legend, axis, series);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(echarts));
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(objectMapper.writeValueAsString(echarts));
        out.flush();
        out.close();

    }

    public void ajaxFindValue(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String key = req.getParameter("key");
        String data = StringUtil.Empty;
        if (!"xueyuan".equals(key)) {
            List<Integer> yearList = employmentStatisticsService.findYear();
            data = yearList.toString()
                    .substring(1, yearList.toString().length() - 1)
                    .replace(" ", "");
        } else {
            List<String> xueyuanList = employmentStatisticsService
                    .findXueyuan();
            data = xueyuanList.toString()
                    .substring(1, xueyuanList.toString().length() - 1)
                    .replace(" ", "");
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(data);
        out.flush();
        out.close();
    }

    public String employmentStatistics(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        return "r:/jsps/employmentstatistics.jsp";
    }

    @Test
    public void pub() {
        List<String> list = new ArrayList<String>();
        System.out.println(list);
        String data = list.toString()
                .substring(1, list.toString().length() - 1);
        System.out.println(data);
    }

    public void copyto(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String savePath = this.getServletContext().getRealPath(
                "/pdfjs/generic/web/pdf");
        File file = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file.mkdirs();
        }
        File srcFile = new File("D:\\_testreport");// 需要复制的文件的源路径
        String srcPath = srcFile.getAbsolutePath();// 获得源路径
        // 过滤出的文件
        File oldFile = new File(srcPath + "\\" + "mytest.pdf"); // 需要复制的文件
        File newFile = new File(savePath + "\\" + "mytest.pdf");// 复制后的文件
        // 创建流对象
        DataInputStream dis = new DataInputStream(new FileInputStream(oldFile));
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                newFile));
        int temp;
        // 读写数据
        while ((temp = dis.read()) != -1) {// 读数据
            dos.write(temp);// 把读到的数据写入到Temp文件中
        }
        // 关闭流
        dis.close();
        dos.close();
        System.out.println("文件已复制成功！");
        System.out.println(savePath);
    }
}