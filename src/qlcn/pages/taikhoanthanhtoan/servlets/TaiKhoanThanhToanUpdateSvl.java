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
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String userIdSS = (String)session.getAttribute("userId");
	    
	    if(!util.check(userId, userIdSS)){
	    	session.removeAttribute("userId");
	    	response.sendRedirect("/QUANLYCANHAN/");
	    } else {
	    	ITaiKhoanThanhToan obj = new TaiKhoanThanhToan();
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
				response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/TaikhoanthanhtoanNew.jsp");
		    } else {
		    	response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/TaikhoanthanhtoanDisplay.jsp");
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
	    	ITaiKhoanThanhToan obj = new TaiKhoanThanhToan();
	    	obj.setUserId(userId);
	    	
	    	String id = request.getParameter("id");	
		    if(id == null)
		    	id = "";
	    	obj.setID(id);
	    	
	    	String loai = util.antiSQLInspection(request.getParameter("loai"));
			if (loai != null)
				obj.setLoai(loai);
			
			String taikhoan = util.antiSQLInspection(request.getParameter("taikhoan"));
			if (taikhoan != null)
				obj.setTaikhoan(taikhoan);
		    
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			if (ten != null)
				obj.setTen(ten);
			
			String loaithe = util.antiSQLInspection(request.getParameter("loaithe"));
			if (loaithe != null)
				obj.setLoaithe(loaithe);
			
			/*String nganhang = util.antiSQLInspection(request.getParameter("nganhang"));
			if (nganhang != null)
				obj.setNganhang(nganhang);*/
			
			String sothe = util.antiSQLInspection(request.getParameter("sothe"));
			if (sothe != null)
				obj.setSothe(sothe);
			
			String mapin = util.antiSQLInspection(request.getParameter("mapin"));
			if (mapin != null)
				obj.setMapin(mapin);
			
			String tenchuthe = util.antiSQLInspection(request.getParameter("tenchuthe"));
			if (tenchuthe != null)
				obj.setTenchuthe(tenchuthe);
			
			/*String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
			if (sotaikhoan != null)
				obj.setSotaikhoan(sotaikhoan);
			
			String sodienthoai = util.antiSQLInspection(request.getParameter("sodienthoai"));
			if (sodienthoai != null)
				obj.setSodienthoai(sodienthoai);
			
			String email = util.antiSQLInspection(request.getParameter("email"));
			if (email != null)
				obj.setEmail(email);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay != null)
				obj.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay != null)
				obj.setDenngay(denngay);*/
			
			String thanghieuluc = util.antiSQLInspection(request.getParameter("thanghieuluc"));
			if (thanghieuluc != null)
				obj.setThanghieuluc(thanghieuluc);
			
			String namhieuluc = util.antiSQLInspection(request.getParameter("namhieuluc"));
			if (namhieuluc != null)
				obj.setNamhieuluc(namhieuluc);
			
			String thanghethan = util.antiSQLInspection(request.getParameter("thanghethan"));
			if (thanghethan != null)
				obj.setThanghethan(thanghethan);
			
			String namhethan = util.antiSQLInspection(request.getParameter("namhethan"));
			if (namhethan != null)
				obj.setNamhethan(namhethan);
			
			String chuky = util.antiSQLInspection(request.getParameter("chuky"));
			if (chuky != null)
				obj.setChuky(chuky);
		    
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
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/TaikhoanthanhtoanNew.jsp");
					} else {
						obj.DBClose();
						ITaiKhoanThanhToanList objList = new TaiKhoanThanhToanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/Taikhoanthanhtoan.jsp");
					}
				} else {
					if(!obj.create()) {
						obj.createRS();
						
						session.setAttribute("obj", obj);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/TaikhoanthanhtoanNew.jsp");
					} else {
						obj.DBClose();
						ITaiKhoanThanhToanList objList = new TaiKhoanThanhToanList();
						objList.setUserId(userId);
						
						objList.init();
						
						session.setAttribute("obj", objList);
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/Taikhoanthanhtoan.jsp");
					}
				}
		    } else {
		    	obj.createRS();
		    	
		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/QUANLYCANHAN/Pages/QLTS/Pages/TaikhoanthanhtoanNew.jsp");
		    }
	    }
	}

}
