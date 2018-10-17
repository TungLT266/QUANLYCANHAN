package geso.traphaco.erp.servlets.thietbikiemnghiem;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSoList;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSoList;
import geso.traphaco.erp.beans.thietbikiemnghiem.IThietBiKiemNghiem;
import geso.traphaco.erp.beans.thietbikiemnghiem.IThietBiKiemNghiemList;
import geso.traphaco.erp.beans.thietbikiemnghiem.imp.ThietBiKiemNghiem;
import geso.traphaco.erp.beans.thietbikiemnghiem.imp.ThietBiKiemNghiemList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpThietBiKiemNghiemSvl
 */
@WebServlet("/ErpThietBiKiemNghiemSvl")
public class ErpThietBiKiemNghiemSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpThietBiKiemNghiemSvl() {
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
	    
	    if (userId == null || userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IThietBiKiemNghiemList obj = new ThietBiKiemNghiemList();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    if(action.trim().equals("delete")) {
	    	String Id = util.getId(querystring);
	    	obj.delete(Id);
	    }
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/Thietbikiemnghiem.jsp";
		response.sendRedirect(nextJSP);
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
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    if(action.equals("new")) {
	    	IThietBiKiemNghiem obj = new ThietBiKiemNghiem();
	    	
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ThietbikiemnghiemNew.jsp");
	    } else if (action.equals("search")){
	    	IThietBiKiemNghiemList obj = new ThietBiKiemNghiemList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
	    	String ma = util.antiSQLInspection(request.getParameter("ma"));
			if (ma == null)
				ma = "";
			obj.setMa(ma);
			
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			if (ten == null)
				ten = "";
			obj.setTen(ten);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			obj.setGhichu(ghichu);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			obj.init();
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/Thietbikiemnghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}
