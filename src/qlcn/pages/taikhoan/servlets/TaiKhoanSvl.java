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
 * Servlet implementation class TaiKhoanSvl
 */
@WebServlet("/TaiKhoanSvl")
public class TaiKhoanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaiKhoanSvl() {
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
	    
	    String userTen = (String)session.getAttribute("userTen");
	    String userId = request.getParameter("userId");
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userTen");
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ITaiKhoanList obj = new TaiKhoanList();
	    	obj.setUserId(userId);
			
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoan.jsp");
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
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.equals("new")) {
				ITaiKhoan obj = new TaiKhoan();
				obj.setUserId(userId);
				
				obj.createRS();
				
				session.setAttribute("obj", obj);
				session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanNew.jsp");
			} else {
		    	ITaiKhoanList obj = new TaiKhoanList();
		    	obj.setUserId(userId);
		    	
		    	String pinUser = request.getParameter("pinUser");
		    	
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
					obj.deleteDB(pinUser);
				} else if(action.trim().equals("delete")) {
					String idrow = request.getParameter("idrow");
			    	obj.delete(idrow);
				}
				
		    	obj.init();
		    	
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoan.jsp");
			}
	    }
	}

}
