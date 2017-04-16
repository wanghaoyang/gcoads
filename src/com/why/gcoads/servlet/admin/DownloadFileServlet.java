package com.why.gcoads.servlet.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.servlet.BaseServlet;

/**
 * Servlet implementation class UserManagementServlet
 */
@WebServlet("/admin/DownloadFileServlet")
public class DownloadFileServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 下载Excel模版
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = "模板.xls"; // 下载的文件名（前提：下载的文件需要存放在服务器的位置上，这里是
                                    // WebRoot/excel 中）
        response.setContentType("text/html;charset=UTF-8");
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        request.setCharacterEncoding("UTF-8");
        String rootpath = request.getSession().getServletContext().getRealPath("/");
        try {
            File f = new File(rootpath + "excel/template/" + fileName);
            response.setContentType("application/x-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            response.setHeader("Content-Length", String.valueOf(f.length()));
            in = new BufferedInputStream(new FileInputStream(f));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = in.read(data, 0, data.length))) {
                out.write(data, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
