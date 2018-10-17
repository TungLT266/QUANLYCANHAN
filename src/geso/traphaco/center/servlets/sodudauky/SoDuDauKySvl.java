package geso.traphaco.center.servlets.sodudauky;

import geso.traphaco.center.beans.sodudauky.imp.SoDuDauKy;
import geso.traphaco.center.beans.sodudauky.imp.SoDuDauKyList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SoDuDauKySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	   
   public SoDuDauKySvl() {
		super();
   }   
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    SoDuDauKyList obj = new SoDuDauKyList();
	    
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCongTyId(ctyId);
	    String id = util.getId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);

	    if (action.equals("delete")){	   		  	    	
	    	SoDuDauKy sd = new SoDuDauKy();
	    	sd.setId(id);
	    	sd.delete(id);
	    	obj.setMsg(sd.getMsg());
	    	sd.DbClose();
	    }
    	else if (action.equals("chot"))
    	{
    		SoDuDauKy sd = new SoDuDauKy();
	    	sd.setId(id);
		    obj.setCongTyId(ctyId);
	    	sd.setUserId(userId);
	    	sd.setCongTyId(ctyId);
	    	sd.init();
	    	sd.chot();
	    	obj.setMsg(sd.getMsg());
	    	sd.DbClose();
    	}
//	    	else if (action.equals("chuyen"))
//	    	{
//	    		Erp_MaSCLon maSCLon = new Erp_MaSCLon();
//	    		maSCLon.setId(id);
//	    		maSCLon.setCongTyId(ctyId);
//	    		maSCLon.init();
//	    		maSCLon.chuyenMASCLON();
//		    	obj.setMsg(maSCLon.getMsg());
//		    	maSCLon.DbClose();
//	    	}
	   	
		obj.init();
		obj.DBClose();
		
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Center/SoDuDauKy.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = util.getAction(querystring);
	    	if (action == null)
	    		action = "";
	    }
	        
	    if (action.equals("new"))
	    {
	    	SoDuDauKy obj = new SoDuDauKy();
		    String ctyId = (String)session.getAttribute("congtyId");
		    
		    obj.setCongTyId(ctyId);
		    obj.init();
		    session.setAttribute("action", action);
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		return;
	    }
	    
	    SoDuDauKyList obj = new SoDuDauKyList();
	    if (action.equals("search"))
	    {
		    String ctyId = (String)session.getAttribute("congtyId");

		    obj.setCongTyId(ctyId);
		    
		    String chiNhanhId = request.getParameter("chiNhanhId");
		    String soChungTu = request.getParameter("soChungTu");
		    String ngayBatDau = request.getParameter("ngayBatDau");
		    String ngayKetThuc = request.getParameter("ngayKetThuc");
		    
		    obj.setChiNhanhId(chiNhanhId);
		    obj.setSoChungTu(soChungTu);
		    obj.setNgayBatDau(ngayBatDau);
		    obj.setNgayKetThuc(ngayKetThuc);
		    
		    String ma = request.getParameter("ma");
		    obj.setMa(ma);
			    
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/SoDuDauKy.jsp");	    	
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
		    String ctyId = (String)session.getAttribute("congtyId");

		    obj.setCongTyId(ctyId);

	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.init();
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Center/SoDuDauKy.jsp");
	    } 
	}   
}