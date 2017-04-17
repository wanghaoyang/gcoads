package com.why.gcoads.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.service.printreportrecord.PrintReportRecordService;
import com.why.gcoads.servlet.BaseServlet;

/**
 * Servlet implementation class PrintReportRecordManagementServlet
 */
@WebServlet("/admin/PrintReportRecordManagementServlet")
public class PrintReportRecordManagementServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
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

        String loginname = req.getParameter("loginname");
        /*
         * 4. 得到PageBean
         */
        pagePrintReportRecord = printReportRecordService.findPrintReportRecordByPager(pagePrintReportRecord, loginname);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/printreportrecord/list.jsp
         */
        pagePrintReportRecord.setUrl(url);

        req.setAttribute("pageBean", pagePrintReportRecord);
        req.setAttribute("loginname", loginname);
        return "f:/jsps/admin/printreportrecord/list.jsp";
    }
}
