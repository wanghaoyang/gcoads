package com.why.gcoads.servlet.User;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.commons.GeneratePDF;
import com.why.gcoads.model.PayRecord;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.model.User;
import com.why.gcoads.service.payrecord.PayRecordService;
import com.why.gcoads.service.printreportrecord.PrintReportRecordService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.JdbcUtils;

/**
 * Servlet implementation class GraduateManagementServlet
 */
@WebServlet("/GraduateServlet")
public class PayServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private PayRecordService payRecordService = new PayRecordService();
    private PrintReportRecordService printReportRecordService = new PrintReportRecordService();

    public String Pay(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        return "r:/jsps/paylist.jsp";
    }

    public String addPayRecord(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }

        PayRecord payRecord = new PayRecord();
        payRecord.setLoginname(user.getLoginname());
        payRecord.setPaystartdatetime(new Date());
        payRecord.setTotalcost(2);
        payRecord.setCertificationquantity(1);
        payRecord.setPaystatus(false);
        int prid = payRecordService.addPayRecord(payRecord);
        payRecord.setPrid(prid);
        req.setAttribute("payRecord", payRecord);

        return "r:/jsps/paylist.jsp";
    }

    public String finishPay(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String param = req.getParameter("payid");
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        if (StringUtil.isNullOrEmpty(param)) {
            req.setAttribute("code", "error");
            req.setAttribute("msg", "支付失败!");
            return "f:/jsps/user/msg.jsp";
        }
        int prid = -1;
        try {
            prid = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            req.setAttribute("code", "error");
            req.setAttribute("msg", "支付失败!");
            return "f:/jsps/user/msg.jsp";
        }
        PayRecord payRecord = new PayRecord();
        payRecord.setPrid(prid);
        payRecord.setPayfinisheddatetime(new Date());
        payRecord.setPaystatus(true);

        try {
            JdbcUtils.beginTransaction();
            int row = payRecordService.updatePayRecord(payRecord);
            if (row > 0) {
                PayRecord pay = payRecordService.findPayRecordByPrid(prid);
                String savePath = this.getServletContext().getRealPath("/pdf/");
                savePath = savePath.substring(0, savePath.indexOf("\\")) + "\\"
                        + req.getContextPath().substring(1) + "\\pdf" + "\\";
                File file = new File(savePath);
                // 判断上传文件的保存目录是否存在
                if (!file.exists() && !file.isDirectory()) {
                    System.out.println(savePath + "目录不存在，需要创建");
                    // 创建目录
                    file.mkdirs();
                }
                String fileName = new SimpleDateFormat("yyyy_MM_dd")
                        .format(new Date())
                        + "_"
                        + pay.getLoginname()
                        + "_"
                        + pay.getShenfenzhenghao();

                PrintReportRecord printReportRecord = new PrintReportRecord();
                printReportRecord.setLoginname(pay.getLoginname());
                printReportRecord.setShenfenzhenghao(pay.getShenfenzhenghao());
                printReportRecord.setReportpath(savePath);
                printReportRecord.setPrintdatetime(pay.getPayfinisheddatetime());;
                printReportRecord.setReportname(fileName);
                printReportRecord.setPrintpagenum(pay
                        .getCertificationquantity());
                printReportRecord.setPrintstatus(true);
                try {
                    GeneratePDF.writeSimplePdf(savePath, fileName,
                            printReportRecord);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    JdbcUtils.rollbackTransaction();
                    e.printStackTrace();
                }
                int pprid = printReportRecordService
                        .addPrintReportRecord(printReportRecord);
                JdbcUtils.commitTransaction();
                req.setAttribute("code", "success");
                req.setAttribute("msg", "支付成功!");
                printReportRecord = printReportRecordService.findPrintReportRecordByPrrid(pprid);
                req.setAttribute("printReportRecord", printReportRecord);
            } else {
                JdbcUtils.rollbackTransaction();
                req.setAttribute("code", "error");
                req.setAttribute("msg", "支付失败!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        
        return "r:/jsps/user/paymsg.jsp";
    }
    
    public String loadPdf(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        
        String prridStr = req.getParameter("prrid");
        int pprid = -1;
        try{
            pprid = Integer.parseInt(prridStr);
            
        }catch(NumberFormatException e){
            req.setAttribute("msg", "文件编号错误！");
            return "f:/jsps/user/pdflist.jsp";
        }
        PrintReportRecord printReportRecord = printReportRecordService.findPrintReportRecordByPrrid(pprid);
        
        return "r:/jsps/user/paymsg.jsp";
    }
}
