package qlcn.pages.noidungthuchi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.noidungthuchi.beans.INoiDungThuChi;
import qlcn.pages.noidungthuchi.beans.INoiDungThuChiList;
import qlcn.pages.noidungthuchi.beans.imp.NoiDungThuChi;
import qlcn.pages.noidungthuchi.beans.imp.NoiDungThuChiList;

/**
 * Servlet implementation class NoiDungThuChiUpdateSvl
 */
@WebServlet("/NoiDungThuChiUpdateSvl")
public class NoiDungThuChiUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoiDungThuChiUpdateSvl() {
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
	    	session.setAttribute("userTen", userTen);
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	INoiDungThuChi obj = new NoiDungThuChi();
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
				response.sendRedirect("/QUANLYCANHAN/qlcn/pages/NoidungthuchiNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/qlcn/pages/NoidungthuchiDisplay.jsp");
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
	    	INoiDungThuChi obj = new NoiDungThuChi();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id != null)
		    	obj.setID(id);
		    
		    String loai = request.getParameter("loai");	
		    if(loai != null)
		    	obj.setLoai(loai);
	    	
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			if (ten != null)
				obj.setTen(ten);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai != null)
				obj.setDiengiai(diengiai);
		    
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
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/NoidungthuchiNew.jsp");
					} else {
						obj.DBClose();
						INoiDungThuChiList objList = new NoiDungThuChiList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Noidungthuchi.jsp");
					}
				} else {
					if(!obj.create()) {
						session.setAttribute("obj", obj);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/NoidungthuchiNew.jsp");
					} else {
						obj.DBClose();
						INoiDungThuChiList objList = new NoiDungThuChiList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Noidungthuchi.jsp");
					}
				}
		    }
	    }
	}

}
