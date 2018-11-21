package qlcn.pages.vayno.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.vayno.beans.IVayNo;
import qlcn.pages.vayno.beans.IVayNoList;
import qlcn.pages.vayno.beans.imp.VayNo;
import qlcn.pages.vayno.beans.imp.VayNoList;

/**
 * Servlet implementation class VayNoUpdateSvl
 */
@WebServlet("/VayNoUpdateSvl")
public class VayNoUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VayNoUpdateSvl() {
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
	    	IVayNo obj = new VayNo();
	    	obj.setUserId(userId);
	    	
	    	String id = util.getId(querystring);
		    if(id != null)
		    	obj.setID(id);
		    
		    String action = util.getAction(querystring);
			if(action == null)
				action = "";
			obj.setAction(action);
	    	
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
		    
			if(action.equals("nhantra")){
				response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoDisplay.jsp");
			} else if(action.equals("update") || action.equals("copy")) {
				if(action.equals("copy")){
					obj.setID("");
				}
				response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoDisplay.jsp");
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
	    	IVayNo obj = new VayNo();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id != null)
		    	obj.setID(id);
		    
		    String ngay = request.getParameter("ngay");
			if (ngay != null)
				obj.setNgay(ngay);
			
			String sotien = request.getParameter("sotien");
			if (sotien != null)
				obj.setSotien(sotien);
			
			String loai = request.getParameter("loai");
			if (loai != null)
				obj.setLoai(loai);
			
			String taikhoanId = request.getParameter("taikhoanId");
			if (taikhoanId != null)
				obj.setTaikhoanId(taikhoanId);
			
			String nguoivayno = util.antiSQLInspection(request.getParameter("nguoivayno"));
			if (nguoivayno != null)
				obj.setNguoivayno(nguoivayno);
			
			String noidung = util.antiSQLInspection(request.getParameter("noidung"));
			if (noidung != null)
				obj.setNoidung(noidung);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu != null)
				obj.setGhichu(ghichu);
			
			String ngaytra = request.getParameter("ngaytra");
			if (ngaytra != null)
				obj.setNgaytra(ngaytra);
			
			String phi = request.getParameter("phi");
			if (phi != null)
				obj.setPhi(phi);
			
			String taikhoannhantra = request.getParameter("taikhoannhantra");
			if (taikhoannhantra != null)
				obj.setTaikhoannhantra(taikhoannhantra);
	    	
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			obj.setAction(action);
			
			if(action.trim().equals("save")) {
				if(id.length() > 0){
					if(!obj.update()) {
						obj.createRS();
						
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoNew.jsp");
					} else {
						obj.DBClose();
						IVayNoList objList = new VayNoList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Vayno.jsp");
					}
				} else {
					if(!obj.create()) {
						obj.createRS();
						
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoNew.jsp");
					} else {
						obj.DBClose();
						IVayNoList objList = new VayNoList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Vayno.jsp");
					}
				}
		    } else if(action.trim().equals("nhantra")){
		    	if(!obj.nhantra()){
		    		obj.createRS();
					
					session.setAttribute("obj", obj);
					session.setAttribute("userTen", userTen);
		    		session.setAttribute("userId", userId);
		    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoDisplay.jsp");
		    	} else {
					obj.DBClose();
					IVayNoList objList = new VayNoList();
					objList.setUserId(userId);
					
					objList.init();
					
					session.setAttribute("obj", objList);
					session.setAttribute("userTen", userTen);
		    		session.setAttribute("userId", userId);
		    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Vayno.jsp");
				}
		    } else {
		    	obj.createRS();
				
				session.setAttribute("obj", obj);
				session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoNew.jsp");
		    }
	    }
	}

}
