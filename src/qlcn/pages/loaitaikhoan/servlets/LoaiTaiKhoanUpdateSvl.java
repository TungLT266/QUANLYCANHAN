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
 * Servlet implementation class LoaiTaiKhoanUpdateSvl
 */
@WebServlet("/LoaiTaiKhoanUpdateSvl")
public class LoaiTaiKhoanUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoaiTaiKhoanUpdateSvl() {
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
	    	ILoaiTaiKhoan obj = new LoaiTaiKhoan();
	    	obj.setUserId(userId);
	    	
	    	String id = util.getId(querystring);
		    if(id == null)
		    	id = "";
	    	obj.setID(id);
	    	
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
		    
			String action = util.getAction(querystring);
			if(action == null)
				action = "";
			
			if(action.equals("update")) {
				response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/LoaitaikhoanNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/LoaitaikhoanDisplay.jsp");
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
	    
	    String userId = request.getParameter("userId");
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ILoaiTaiKhoan obj = new LoaiTaiKhoan();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id == null)
		    	id = "";
	    	obj.setID(id);
		    
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			if (ten != null)
				obj.setTen(ten);
		    
		    String trangthai = request.getParameter("trangthai");
			if (trangthai == null || trangthai.length() == 0)
				trangthai = "0";
			obj.setTrangthai(trangthai);
	    	
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.trim().equals("save")) {
				if(id.length() > 0){
					if(!obj.update()) {
						session.setAttribute("obj", obj);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/LoaitaikhoanNew.jsp");
					} else {
						obj.DBClose();
						ILoaiTaiKhoanList objList = new LoaiTaiKhoanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/Loaitaikhoan.jsp");
					}
				} else {
					if(!obj.create()) {
						session.setAttribute("obj", obj);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/LoaitaikhoanNew.jsp");
					} else {
						obj.DBClose();
						ILoaiTaiKhoanList objList = new LoaiTaiKhoanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/Loaitaikhoan.jsp");
					}
				}
		    }
	    }
	}

}
