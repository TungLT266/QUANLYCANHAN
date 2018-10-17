package geso.traphaco.erp.servlets.danhmucduan;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.danhmucduan.Erp_DanhMucDuAn;
import geso.traphaco.erp.beans.danhmucduan.Erp_DanhMucDuAnList;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_XuatDungCCDCList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DanhMucDuAnUpdateSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_DanhMucDuAnUpdateSvl() {
			super();
		}   
	   
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	   {
		   System.out.println("do get update");
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();

		    HttpSession session = request.getSession();	    
	      
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    Erp_DanhMucDuAn obj = new Erp_DanhMucDuAn();
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCongTyId(ctyId);
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    System.out.println("\n **************action:" + action);
		    
		    String id = util.getId(querystring);

		    obj.setId(id);
		   	
//	    	obj.setUserId(userId);
//	    	settingPage(obj);
			obj.init();
			
			session.setAttribute("action", action);
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAnUpdate.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");
		    String id = request.getParameter("id");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		        
		    Erp_DanhMucDuAn obj = new Erp_DanhMucDuAn();
		    obj.setId(id);
		    obj.setNguoiSua(userId);
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCongTyId(ctyId);
		    obj.setNguoiTao(userId);
		    
		    
		    obj.init();
		    
		    String ma = request.getParameter("ma");
		    obj.setMa(ma);
		    
		    String ten = request.getParameter("ten");
		    obj.setTen(ten);
		    
		    System.out.println("action: " + action);
		    if (action.equals("new"))
		    {
		    	if (!obj.create())
		    	{
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", obj);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAnUpdate.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    	else
		    	{
		    		obj.DbClose();
		    		
		    		Erp_DanhMucDuAnList ob = new Erp_DanhMucDuAnList();
		    		ob.init();
		    		obj.setCongTyId(ctyId);
		    		
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", ob);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAn.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    }
		    else if (action.equals("update"))
	    	{
	    		if (!obj.edit())
		    	{
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", obj);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAnUpdate.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    	else
		    	{
		    		obj.DbClose();
		    		
		    		Erp_DanhMucDuAnList ob = new Erp_DanhMucDuAnList();
		    		ob.setCongTyId(ctyId);
		    		ob.init();
		    		
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", ob);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAn.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
	    	}
		    
    	session.setAttribute("userId", userId);
    	session.setAttribute("obj", obj);
    	
    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAnUpdate.jsp";
		response.sendRedirect(nextJSP);
		return;
	}   
}