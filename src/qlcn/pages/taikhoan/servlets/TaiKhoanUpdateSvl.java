package qlcn.pages.taikhoan.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.taikhoan.beans.ITaiKhoan;
import qlcn.pages.taikhoan.beans.ITaiKhoanList;
import qlcn.pages.taikhoan.beans.imp.TaiKhoan;
import qlcn.pages.taikhoan.beans.imp.TaiKhoanList;

/**
 * Servlet implementation class TaiKhoanUpdateSvl
 */
@WebServlet("/TaiKhoanUpdateSvl")
public class TaiKhoanUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaiKhoanUpdateSvl() {
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
	    String userTen = (String)session.getAttribute("userTen");
	    String userId = util.getUserId(querystring);
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userTen");
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ITaiKhoan obj = new TaiKhoan();
	    	obj.setUserId(userId);
	    	
	    	String id = util.getId(querystring);
		    if(id == null)
		    	id = "";
	    	obj.setID(id);
	    	
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
		    
			String action = util.getAction(querystring);
			if(action == null)
				action = "";
			
			if(action.equals("update") || action.equals("copy")) {
				if(action.equals("copy")){
					obj.setID("");
				}
				response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanDisplay.jsp");
		    }
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
	    
	    String userTen = (String)session.getAttribute("userTen");
	    String userId = request.getParameter("userId");
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userTen");
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ITaiKhoan obj = new TaiKhoan();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id == null)
		    	id = "";
	    	obj.setID(id);
	    	
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			if (ten != null)
				obj.setTen(ten);
			
			String sotien = request.getParameter("sotien");
			if (sotien != null)
				obj.setSotien(sotien);
			
			String donvi = request.getParameter("donvi");
			if (donvi != null)
				obj.setDonvi(donvi);
			
			String istknganhang = request.getParameter("istknganhang");
			if (istknganhang == null)
				istknganhang = "0";
			obj.setIsTknganhang(istknganhang);
			
			String nganhang = util.antiSQLInspection(request.getParameter("nganhang"));
			if (nganhang != null)
				obj.setNganhang(nganhang);
		    
		    String trangthai = request.getParameter("trangthai");
			if (trangthai == null)
				trangthai = "0";
			obj.setTrangthai(trangthai);
	    	
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.trim().equals("save")) {
				if(id.length() > 0){
					if(!obj.update()) {
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanNew.jsp");
					} else {
						obj.DBClose();
						ITaiKhoanList objList = new TaiKhoanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoan.jsp");
					}
				} else {
					if(!obj.create()) {
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanNew.jsp");
					} else {
						obj.DBClose();
						ITaiKhoanList objList = new TaiKhoanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoan.jsp");
					}
				}
		    }
	    }
	}

}
