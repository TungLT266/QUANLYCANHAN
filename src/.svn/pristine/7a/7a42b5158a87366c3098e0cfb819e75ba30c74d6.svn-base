package qlcn.pages.taikhoanthanhtoan.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToan;
import qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToanList;
import qlcn.pages.taikhoanthanhtoan.beans.imp.TaiKhoanThanhToan;
import qlcn.pages.taikhoanthanhtoan.beans.imp.TaiKhoanThanhToanList;

/**
 * Servlet implementation class TaiKhoanThanhToanUpdateSvl
 */
@WebServlet("/TaiKhoanThanhToanUpdateSvl")
public class TaiKhoanThanhToanUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaiKhoanThanhToanUpdateSvl() {
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
	    
//	    String querystring = request.getQueryString();
	    String userTen = (String)session.getAttribute("userTen");
	    String userId = request.getParameter("userId");
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userTen");
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ITaiKhoanThanhToan obj = new TaiKhoanThanhToan();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");
		    if(id == null)
		    	id = "";
	    	obj.setID(id);
	    	
	    	String action = request.getParameter("action");
			if(action == null)
				action = "";
			obj.setAction(action);
			
			String nextJSP = "";
			if(action.equals("display")) {
				String pinUser = request.getParameter("pinUser");
				String msg = obj.checkPinUser(pinUser);
				if(msg.length() > 0) {
					obj.DBClose();
					ITaiKhoanThanhToanList objList = new TaiKhoanThanhToanList();
					objList.setUserId(userId);
					objList.setMsg(msg);
					objList.init();
					
					session.setAttribute("obj", objList);
					session.setAttribute("userTen", userTen);
		    		session.setAttribute("userId", userId);
		    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoanthanhtoan.jsp");
		    		return;
				}
				
				nextJSP = "/QUANLYCANHAN/qlcn/pages/TaikhoanthanhtoanDisplay.jsp";
		    } else {
		    	nextJSP = "/QUANLYCANHAN/qlcn/pages/TaikhoanthanhtoanNew.jsp";
		    }
	    	
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
		    
			
			response.sendRedirect(nextJSP);
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
	    	ITaiKhoanThanhToan obj = new TaiKhoanThanhToan();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id != null)
		    	obj.setID(id);
			
			String taikhoan = request.getParameter("taikhoan");
			if (taikhoan != null)
				obj.setTaikhoan(taikhoan);
			
			String loaithe = request.getParameter("loaithe");
			if (loaithe != null)
				obj.setLoaithe(loaithe);
			
			String sothe = util.antiSQLInspection(request.getParameter("sothe"));
			if (sothe != null)
				obj.setSothe(sothe);
			
			String tenchuthe = util.antiSQLInspection(request.getParameter("tenchuthe"));
			if (tenchuthe != null)
				obj.setTenchuthe(tenchuthe);
			
			String mapin = util.antiSQLInspection(request.getParameter("mapin"));
			if (mapin != null)
				obj.setMapin(mapin);
			
			String ischangemapin = util.antiSQLInspection(request.getParameter("ischangemapin"));
			if (ischangemapin != null)
				obj.setIsChangeMapin(ischangemapin);
			
			String thanghieuluc = request.getParameter("thanghieuluc");
			if (thanghieuluc != null)
				obj.setThanghieuluc(thanghieuluc);
			
			String namhieuluc = request.getParameter("namhieuluc");
			if (namhieuluc != null)
				obj.setNamhieuluc(namhieuluc);
			
			String thanghethan = request.getParameter("thanghethan");
			if (thanghethan != null)
				obj.setThanghethan(thanghethan);
			
			String namhethan = request.getParameter("namhethan");
			if (namhethan != null)
				obj.setNamhethan(namhethan);
			
			String chuky = util.antiSQLInspection(request.getParameter("chuky"));
			if (chuky != null)
				obj.setChuky(chuky);
			
			String ischangechuky = util.antiSQLInspection(request.getParameter("ischangechuky"));
			if (ischangechuky != null)
				obj.setIsChangeChuky(ischangechuky);
		    
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
						obj.createRS();
						
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanthanhtoanNew.jsp");
					} else {
						obj.DBClose();
						ITaiKhoanThanhToanList objList = new TaiKhoanThanhToanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoanthanhtoan.jsp");
					}
				} else {
					if(!obj.create()) {
						obj.createRS();
						
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanthanhtoanNew.jsp");
					} else {
						obj.DBClose();
						ITaiKhoanThanhToanList objList = new TaiKhoanThanhToanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Taikhoanthanhtoan.jsp");
					}
				}
		    } else {
		    	obj.createRS();
		    	
		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/TaikhoanthanhtoanNew.jsp");
		    }
	    }
	}

}
