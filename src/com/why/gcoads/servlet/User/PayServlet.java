package com.why.gcoads.servlet.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.commons.GeneratePDF;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PayRecord;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.service.payrecord.PayRecordService;
import com.why.gcoads.service.printreportrecord.PrintReportRecordService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.BankMap;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.JdbcUtils;

/**
 * Servlet implementation class GraduateManagementServlet
 */
@WebServlet("/PayServlet")
public class PayServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private PayRecordService payRecordService = new PayRecordService();
    private GraduateService graduateService = new GraduateService();
    private PrintReportRecordService printReportRecordService = new PrintReportRecordService();

    /**
     * 获取当前页码
     * 
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
        int pc = 1;
        String param = req.getParameter("pc");
        if (param != null && !param.trim().isEmpty()) {
            try {
                pc = Integer.parseInt(param);
            } catch (RuntimeException e) {

            }
        }
        return pc;
    }

    /**
     * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
     * 
     * @param req
     * @return
     */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
        /*
         * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
         */
        int index = url.lastIndexOf("&pc=");
        if (index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }
    
    public String addPayRecord(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        String shenfenzhenghao = req.getParameter("idcard");
        System.out.println(shenfenzhenghao);
        PayRecord payRecord = new PayRecord();
        payRecord.setLoginname(user.getLoginname());
        payRecord.setShenfenzhenghao(shenfenzhenghao);
        payRecord.setPaystartdatetime(new Date());
        payRecord.setTotalcost(2);
        payRecord.setCertificationquantity(1);
        payRecord.setPaystatus(false);
        String payid = payRecordService.addPayRecord(payRecord);
        int prid = Integer.parseInt(payid.substring(2));
        payRecord.setPrid(prid);
        payRecord.setPayid(payid);
        req.setAttribute("payRecord", payRecord);

        return "f:/jsps/banklist.jsp";
    }
    
    public String toBankPay(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        String pridStr = req.getParameter("prid");
        String bank = req.getParameter("yh");
        int prid = -1;
        try {
            prid = Integer.parseInt(pridStr);
        } catch(NumberFormatException nfe){
            nfe.printStackTrace();
        }
        PayRecord payRecord = payRecordService.findPayRecordByPrid(prid);
        
        req.setAttribute("payRecord", payRecord);
        req.setAttribute("yh", BankMap.getBank(bank));

        return "f:/jsps/user/pay.jsp";
    }

    public String finishPay(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String bankloginname = req.getParameter("bankloginname");
        String bankpassword = req.getParameter("bankpassword");
        if (!("pay123".equals(bankloginname) &&"pay123".equals(bankpassword))){
            req.setAttribute("msg", "用户名或密码错误!");
            return "f:/jsps/user/pay.jsp";
        }
        
        String param = req.getParameter("prid");
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        if (StringUtil.isNullOrEmpty(param)) {
            req.setAttribute("code", "error");
            req.setAttribute("msg", "支付失败!");
            return "f:/jsps/user/paymsg.jsp";
        }
        int prid = -1;
        try {
            prid = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            req.setAttribute("code", "error");
            req.setAttribute("msg", "支付失败!");
            return "f:/jsps/user/paymsg.jsp";
        }
        PayRecord payRecord = payRecordService.findPayRecordByPrid(prid);
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
                // 判断文件的保存目录是否存在
                if (!file.exists() && !file.isDirectory()) {
                    System.out.println(savePath + "目录不存在，需要创建");
                    // 创建目录
                    file.mkdirs();
                }
                String fileName = new SimpleDateFormat("yyyy_MM_dd_HHmmss")
                        .format(new Date())
                        + "_"
                        + pay.getLoginname()
                        + "_"
                        + pay.getShenfenzhenghao();

                PrintReportRecord printReportRecord = new PrintReportRecord();
                printReportRecord.setLoginname(pay.getLoginname());
                printReportRecord.setShenfenzhenghao(pay.getShenfenzhenghao());
                printReportRecord.setReportpath(savePath);
                printReportRecord.setReportname(fileName);
                printReportRecord.setPrintpagenum(pay
                        .getCertificationquantity());
                printReportRecord.setPrintdatetime(new Date());
                printReportRecord.setPrintstatus(false);
                String xuehao = graduateService.findXuehaoByshenfenzhenghao(pay.getShenfenzhenghao());
                if (StringUtil.isNullOrEmpty(xuehao)){
                    return "r:/jsps/main.jsp";
                }
                Graduate graduate = graduateService.findGraduateByXuehao(xuehao);
                Student student = graduateService.findStudentByXuehao(xuehao);
                printReportRecord.setGraduate(graduate);
                printReportRecord.setStudent(student);
                
                GeneratePDF.writeSimplePdf(savePath, fileName, printReportRecord);
              
                int prrid = printReportRecordService.addPrintReportRecord(printReportRecord);
                req.setAttribute("code", "success");
                req.setAttribute("msg", "支付成功!");
                printReportRecord = printReportRecordService.findPrintReportRecordByPrrid(prrid);
                req.setAttribute("printReportRecord", printReportRecord);
                JdbcUtils.commitTransaction();
            } else {
                JdbcUtils.rollbackTransaction();
                req.setAttribute("code", "error");
                req.setAttribute("msg", "支付失败!");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            req.setAttribute("code", "error");
            req.setAttribute("msg", "支付失败!");
        }
        
        return "f:/jsps/user/paymsg.jsp";
    }
    
    public String findPrintReportRecord(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 3. 获取查询条件
         */

        PageBean<PrintReportRecord> pagePrintReportRecord = new PageBean<PrintReportRecord>();
        pagePrintReportRecord.setPc(pc);
        pagePrintReportRecord.setPs(10);

        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        
        String loginname = user.getLoginname();
        /*
         * 4. 得到PageBean
         */
        pagePrintReportRecord = printReportRecordService.findPrintReportRecordByPager(pagePrintReportRecord, loginname);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/printreportrecord/list.jsp
         */
        pagePrintReportRecord.setUrl(url);

        req.setAttribute("pageBean", pagePrintReportRecord);
        return "f:/jsps/user/printreportrecordlist.jsp";
    }
    
    
    public String loadPDF(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        
        String prridStr = req.getParameter("prrid");
        int prrid = -1;
        try{
            prrid = Integer.parseInt(prridStr);
        }catch(NumberFormatException e){
            req.setAttribute("msg", "文件编号错误！");
            return "f:/jsps/user/pdflist.jsp";
        }
        PrintReportRecord printReportRecord = printReportRecordService.findPrintReportRecordByPrrid(prrid);
        if (printReportRecord != null){
            if (!printReportRecord.getPrintstatus()){
                printReportRecord.setPrintstatus(true);
                printReportRecordService.updatePrintReportRecord(printReportRecord);
                copyto(req, printReportRecord);
                return "r:/pdfjs/generic/web/viewer.html?file=pdf/"+printReportRecord.getReportname()+".pdf";
            }
        }
        
        return "f:/jsps/main.jsp";
    }
    
    private void copyto(HttpServletRequest req, PrintReportRecord printReportRecord)
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
        File srcFile = new File(printReportRecord.getReportpath());// 需要复制的文件的源路径
        String srcPath = srcFile.getAbsolutePath();// 获得源路径
        // 过滤出的文件
        File oldFile = new File(printReportRecord.getReportpath() + "\\" + printReportRecord.getReportname()+ ".pdf"); // 需要复制的文件
        File newFile = new File(savePath + "\\" + printReportRecord.getReportname() + ".pdf");// 复制后的文件
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
    }
}
