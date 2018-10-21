package qlcn.pages.loaitaikhoan.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.loaitaikhoan.beans.ILoaiTaiKhoan;
import qlcn.pages.loaitaikhoan.beans.ILoaiTaiKhoanList;
import qlcn.pages.loaitaikhoan.beans.imp.LoaiTaiKhoan;
import qlcn.pages.loaitaikhoan.beans.imp.LoaiTaiKhoanList;

/**
 * Servlet implementation class LoaiTaiKhoanSvl
 */
@WebServlet("/LoaiTaiKhoanSvl")
public class LoaiTaiKhoanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoaiTaiKhoanSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ILoaiTaiKhoanList obj = new LoaiTaiKhoanList();
	    	obj.setUserId(userId);
	    	
			String action = util.getAction(querystring);
			if(action == null)
				action = "";
			
			if(action.trim().equals("delete")) {
				String id = util.getId(querystring);
		    	obj.delete(id);
		    }
			
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/Loaitaikhoan.jsp");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String userId = request.getParameter("userId");
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.equals("new")) {
				ILoaiTaiKhoan obj = new LoaiTaiKhoan();
				obj.setUserId(userId);
				
				session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/LoaitaikhoanNew.jsp");
		    } else {
		    	ILoaiTaiKhoanList obj = new LoaiTaiKhoanList();
		    	obj.setUserId(userId);
		    	
		    	String id = request.getParameter("id");
				if(id != null)
					obj.setID(id);
				
				String ten = util.antiSQLInspection(request.getParameter("ten"));
				if(ten != null)
					obj.setTen(ten);
				
				String trangthai = request.getParameter("trangthai");
				if(trangthai != null)
					obj.setTrangthai(trangthai);
				
				String soitems = request.getParameter("soitems");
				if(soitems == null)
					soitems = "";
				if(soitems.trim().length() > 0)
					obj.setSoItems(soitems);
				
				if(action.equals("view") || action.equals("next") || action.equals("prev")) {
					obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
					obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				} else if(action.equals("deletedb")) {
					obj.deleteDB();
				}
				
		    	obj.init();
		    	
		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/Loaitaikhoan.jsp");
		    }
	    }
	}

}
