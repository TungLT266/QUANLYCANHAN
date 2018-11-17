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
 * Servlet implementation class VayNoSvl
 */
@WebServlet("/VayNoSvl")
public class VayNoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VayNoSvl() {
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
	    	IVayNoList obj = new VayNoList();
	    	obj.setUserId(userId);
	    	
	    	String id = util.getId(querystring);
	    	
			String action = util.getAction(querystring);
			if(action == null)
				action = "";
			
			if(action.equals("delete")) {
		    	obj.delete(id);
		    } else if(action.equals("unnhantra")) {
		    	obj.unNhantra(id);
		    }
			
	    	obj.init();
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userTen", userTen);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Vayno.jsp");
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
				IVayNo obj = new VayNo();
				obj.setUserId(userId);
				obj.createRS();
				
				session.setAttribute("obj", obj);
				session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/VaynoNew.jsp");
		    } else {
		    	IVayNoList obj = new VayNoList();
		    	obj.setUserId(userId);
		    	
		    	String pinUser = request.getParameter("pinUser");
		    	
		    	String tungay = request.getParameter("tungay");
				if(tungay != null)
					obj.setTungay(tungay);
				
				String denngay = request.getParameter("denngay");
				if(denngay != null)
					obj.setDenngay(denngay);
		    	
		    	String id = request.getParameter("id");
				if(id != null)
					obj.setID(id);
				
				String sotientu = util.antiSQLInspection(request.getParameter("sotientu"));
				if(sotientu != null)
					obj.setSotientu(sotientu);
				
				String sotienden = util.antiSQLInspection(request.getParameter("sotienden"));
				if(sotienden != null)
					obj.setSotienden(sotienden);
				
				String loai = request.getParameter("loai");
				if(loai != null)
					obj.setLoai(loai);
				
				String nguoivayno = request.getParameter("nguoivayno");
				if(nguoivayno != null)
					obj.setNguoivayno(nguoivayno);
				
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
				}
				
		    	obj.init();
		    	
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userTen", userTen);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/qlcn/pages/Vayno.jsp");
		    }
	    }
	}

}
