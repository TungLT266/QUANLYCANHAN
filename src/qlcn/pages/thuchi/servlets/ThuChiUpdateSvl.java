package qlcn.pages.thuchi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.thuchi.beans.IThuChi;
import qlcn.pages.thuchi.beans.IThuChiList;
import qlcn.pages.thuchi.beans.imp.ThuChi;
import qlcn.pages.thuchi.beans.imp.ThuChiList;

/**
 * Servlet implementation class ThuChiUpdateSvl
 */
@WebServlet("/ThuChiUpdateSvl")
public class ThuChiUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThuChiUpdateSvl() {
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
	    	IThuChi obj = new ThuChi();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");
		    if(id != null)
		    	obj.setID(id);
	    	
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
		    
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.equals("update")) {
				response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ThuchiNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ThuchiDisplay.jsp");
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
	    	IThuChi obj = new ThuChi();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id != null)
		    	obj.setID(id);
	    	
			String ngay = request.getParameter("ngay");
			if (ngay != null)
				obj.setNgay(ngay);
			
			String sotien = util.antiSQLInspection(request.getParameter("sotien"));
			if (sotien != null)
				obj.setSotien(sotien);
			
			String loai = request.getParameter("loai");
			if (loai != null)
				obj.setLoai(loai);
			
			String noidungthuchiId = request.getParameter("noidungthuchiId");
			if (noidungthuchiId != null)
				obj.setNoidungthuchiId(noidungthuchiId);
			
			String taikhoanId = request.getParameter("taikhoanId");
			if (taikhoanId != null)
				obj.setTaikhoanId(taikhoanId);
			
			String taikhoanthanhtoanId = request.getParameter("taikhoanthanhtoanId");
			if (taikhoanthanhtoanId != null)
				obj.setTaikhoanthanhtoanId(taikhoanthanhtoanId);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai != null)
				obj.setDiengiai(diengiai);
	    	
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.trim().equals("save")) {
				if(id.length() > 0){
					if(obj.update()) {
						obj.DBClose();
						IThuChiList objList = new ThuChiList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Thuchi.jsp");
			    		
			    		return;
					}
				} else {
					if(obj.create()) {
						obj.DBClose();
						IThuChiList objList = new ThuChiList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Thuchi.jsp");
			    		
			    		return;
					}
				}
		    }
			
			obj.createRs();
			
			session.setAttribute("obj", obj);
			session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ThuchiNew.jsp");
	    }
	}

}
