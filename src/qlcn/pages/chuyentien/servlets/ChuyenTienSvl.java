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
 * Servlet implementation class ChuyenTienSvl
 */
@WebServlet("/ChuyenTienSvl")
public class ChuyenTienSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChuyenTienSvl() {
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
	    	IChuyenTienList obj = new ChuyenTienList();
	    	obj.setUserId(userId);
			
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Chuyentien.jsp");
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
				IChuyenTien obj = new ChuyenTien();
				obj.setUserId(userId);
				
				obj.createRs();
				
				session.setAttribute("obj", obj);
				session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/ChuyentienNew.jsp");
		    } else {
		    	IChuyenTienList obj = new ChuyenTienList();
		    	obj.setUserId(userId);
		    	
		    	String pinUser = request.getParameter("pinUser");
		    	
		    	String id = request.getParameter("id");
				if(id != null)
					obj.setID(id);
				
				String tungay = request.getParameter("tungay");
				if(tungay != null)
					obj.setTungay(tungay);
				
				String denngay = request.getParameter("denngay");
				if(denngay != null)
					obj.setDenngay(denngay);
				
				String sotientu = util.antiSQLInspection(request.getParameter("sotientu"));
				if(sotientu != null)
					obj.setSotientu(sotientu);
				
				String sotienden = util.antiSQLInspection(request.getParameter("sotienden"));
				if(sotienden != null)
					obj.setSotienden(sotienden);
				
				String taikhoanchuyenId = request.getParameter("taikhoanchuyenId");
				if(taikhoanchuyenId != null)
					obj.setTaikhoanchuyenId(taikhoanchuyenId);
				
				String taikhoannhanId = request.getParameter("taikhoannhanId");
				if(taikhoannhanId != null)
					obj.setTaikhoannhanId(taikhoannhanId);
				
				String noidung = util.antiSQLInspection(request.getParameter("noidung"));
				if(noidung != null)
					obj.setNoidung(noidung);
				
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
				} else if(action.trim().equals("chot")) {
					String idrow = request.getParameter("idrow");
					obj.chot(idrow);
				} else if(action.trim().equals("unchot")) {
					String idrow = request.getParameter("idrow");
			    	obj.unchot(idrow);
				} else if(action.trim().equals("delete")) {
					String idrow = request.getParameter("idrow");
			    	obj.delete(idrow);
				}
				
		    	obj.init();
		    	
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Chuyentien.jsp");
		    }
	    }
	}

}
