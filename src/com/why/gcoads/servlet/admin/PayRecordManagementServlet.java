package com.why.gcoads.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PayRecord;
import com.why.gcoads.service.printreportrecord.PayRecordService;
import com.why.gcoads.servlet.BaseServlet;

/**
 * Servlet implementation class PayRecordManagementServlet
 */
@WebServlet("/admin/PayRecordManagementServlet")
public class PayRecordManagementServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private PayRecordService payRecordService = new PayRecordService();

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

    public String findPayRecord(HttpServletRequest req, HttpServletResponse resp)
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

        PageBean<PayRecord> pagePayRecord = new PageBean<PayRecord>();
        pagePayRecord.setPc(pc);
        pagePayRecord.setPs(10);

        String loginname = req.getParameter("loginname");
        /*
         * 4. 得到PageBean
         */
        pagePayRecord = payRecordService.findPayRecordByPager(pagePayRecord, loginname);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/payrecord/list.jsp
         */
        pagePayRecord.setUrl(url);

        req.setAttribute("pageBean", pagePayRecord);
        req.setAttribute("loginname", loginname);
        return "f:/jsps/admin/payrecord/list.jsp";
    }
}
