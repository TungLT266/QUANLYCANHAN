package qlcn.pages.chuyentien.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qlcn.center.util.Utility;
import qlcn.pages.chuyentien.beans.IChuyenTien;
import qlcn.pages.chuyentien.beans.IChuyenTienList;
import qlcn.pages.chuyentien.beans.imp.ChuyenTien;
import qlcn.pages.chuyentien.beans.imp.ChuyenTienList;

/**
 * Servlet implementation class ChuyenTienUpdateSvl
 */
@WebServlet("/ChuyenTienUpdateSvl")
public class ChuyenTienUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChuyenTienUpdateSvl() {
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
	    	IChuyenTien obj = new ChuyenTien();
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
				response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ChuyentienNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ChuyentienDisplay.jsp");
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
	    	IChuyenTien obj = new ChuyenTien();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id != null)
		    	obj.setID(id);
	    	
		    String ngay = request.getParameter("ngay");
			if (ngay != null)
				obj.setNgay(ngay);
			
			String noidung = util.antiSQLInspection(request.getParameter("noidung"));
			if (noidung != null)
				obj.setNoidung(noidung);
			
			String taikhoanchuyenId = request.getParameter("taikhoanchuyenId");
			if (taikhoanchuyenId != null)
				obj.setTaikhoanchuyenId(taikhoanchuyenId);
			
			String taikhoannhanId = request.getParameter("taikhoannhanId");
			if (taikhoannhanId != null)
				obj.setTaikhoannhanId(taikhoannhanId);
			
			String sotienchuyen = util.antiSQLInspection(request.getParameter("sotienchuyen"));
			if (sotienchuyen != null)
				obj.setSotienchuyen(sotienchuyen);
			
			String sotiennhan = util.antiSQLInspection(request.getParameter("sotiennhan"));
			if (sotiennhan != null)
				obj.setSotiennhan(sotiennhan);
			
			String tkphi = request.getParameter("tkphi");
			if (tkphi != null)
				obj.setTkphi(tkphi);
			
			String phi = util.antiSQLInspection(request.getParameter("phi"));
			if (phi != null)
				obj.setPhi(phi);
	    	
			String action = request.getParameter("action");
			if(action == null)
				action = "";
			
			if(action.trim().equals("save")) {
				if(id.length() > 0){
					if(obj.update()) {
						obj.DBClose();
						IChuyenTienList objList = new ChuyenTienList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Chuyentien.jsp");
			    		
			    		return;
					}
				} else {
					if(obj.create()) {
						obj.DBClose();
						IChuyenTienList objList = new ChuyenTienList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
						session.setAttribute("userTen", userTen);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Chuyentien.jsp");
			    		
			    		return;
					}
				}
		    }
			
			obj.createRs();
			
			session.setAttribute("obj", obj);
			session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ChuyentienNew.jsp");
	    }
	}

}
