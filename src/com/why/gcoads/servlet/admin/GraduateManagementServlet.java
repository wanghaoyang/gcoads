package com.why.gcoads.servlet.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.commons.VerifyDateFormat;
import com.why.gcoads.dao.graduate.GraduateDao;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.StringUtil;

/**
 * Servlet implementation class UserManagementServlet
 */
@WebServlet("/admin/GraduateManagementServlet")
public class GraduateManagementServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private GraduateService graduateService = new GraduateService();

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

	public String findStudent(HttpServletRequest req, HttpServletResponse resp)
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

		PageBean<Student> pageStudent = new PageBean<Student>();
		pageStudent.setPc(pc);
		pageStudent.setPs(10);

		String filed = req.getParameter("filed");
		String value = req.getParameter("value");
		/*
		 * 4. 得到PageBean
		 */
		pageStudent = graduateService.findStudentByPager(pageStudent, filed,
				value);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/student/list.jsp
		 */
		pageStudent.setUrl(url);

		req.setAttribute("pageBean", pageStudent);
		req.setAttribute("value", value);
		return "f:/jsps/admin/student/list.jsp";
	}

	public String findGraduate(HttpServletRequest req, HttpServletResponse resp)
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

		PageBean<Graduate> pageGraduate = new PageBean<Graduate>();
		pageGraduate.setPc(pc);
		pageGraduate.setPs(10);

		String filed = req.getParameter("filed");
		String value = req.getParameter("value");
		/*
		 * 4. 得到PageBean
		 */
		pageGraduate = graduateService.findGraduateByPager(pageGraduate, filed,
				value);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/student/list.jsp
		 */
		pageGraduate.setUrl(url);

		req.setAttribute("pageBean", pageGraduate);
		req.setAttribute("value", value);
		return "f:/jsps/admin/graduate/list.jsp";
	}

	public String deleteStudent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String xuehao = req.getParameter("xuehaos");
		String[] xuehaos = xuehao.split(",");
		String msg = "";
		if (xuehaos != null) {
			try {
				int row = graduateService.deleteStudentByXuehaos(xuehaos);
				if (row < 0) {
					msg = "删除失败！";
				} else {
					msg = row + "个学生被成功删除！";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		req.setAttribute("msg", msg);
		return "f:/jsps/dialog.jsp";
	}

	public String addStudentByForm(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		Map<String, String[]> parameterMap = req.getParameterMap();

		Map<String, Object> beanMap = new HashMap<String, Object>();
		Map<String, String> errorMap = null;

		for (String key : parameterMap.keySet()) {
			beanMap.put(key, parameterMap.get(key));
		}

		try {
			errorMap = graduateService.addGraduateInfoByForm(beanMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("errorMap", errorMap);
		return "f:/jsps/dialog.jsp";
	}

	public String addStudentByExcel(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		Map<String, String> errorMap = new HashMap<String, String>();
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = this.getServletContext()
				.getRealPath("/excel/upload/");
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdirs();
		}
		// 消息提示
		String message = "";
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(req)) {
				// 按照传统方式获取数据
				errorMap.put("code", "error");
				errorMap.put("msg", "数据导入失败, 请检查!");
				return "f:/jsps/msg.jsp";
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(req);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				} else {// 如果fileitem中封装的是上传文件
					// 得到上传的文件名称，
					String filename = item.getName();
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename
							.substring(filename.lastIndexOf("\\") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\"
							+ filename);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
						// + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();
					//message = "文件上传成功！";
					// ReadExcelUtils.parseExcel(savePath + filename);
					try {
						graduateService.addGraduateInfoByExcel(savePath
								+ filename);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();

		}
		//req.setAttribute("message", message);
		// request.getRequestDispatcher("/message.jsp").forward(request,
		// response);
		req.setAttribute("errorMap", errorMap);
		if (errorMap.size()==0){
			req.setAttribute("code", "success");
			req.setAttribute("msg", "数据导入成功！");
		}else {
			errorMap.put("code", "error");
			errorMap.put("msg", "数据导入失败, 请检查!");
		}
		
		return "f:/jsps/msg.jsp";
	}
}
