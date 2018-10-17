package geso.traphaco.erp.servlets.baohong;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.baohong.imp.ErpBaoHong;
import geso.traphaco.erp.beans.baohong.imp.ErpBaoHongList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpBaoHongUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	   
	   public ErpBaoHongUpdateSvl() {
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
		    
		    ErpBaoHong obj = new ErpBaoHong();
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
			obj.DbClose();
			session.setAttribute("action", action);
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBaoHongUpdate.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
//		    OutputStream out = response.getOutputStream();
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");
		    String id = request.getParameter("id");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		        
		    ErpBaoHong obj = new ErpBaoHong();
		    obj.setId(id);
		    obj.setNguoiSua(userId);
		    String ctyId = (String)session.getAttribute("congtyId");
//		    String ctyTen = (String)session.getAttribute("congtyTen");
		    obj.setCongTyId(ctyId);
		    obj.setNguoiTao(userId);
		    
		    
		    obj.init();
		    
		    String ccdcId = request.getParameter("ccdcId");
		    obj.setCcdcId(ccdcId);
		    
		    String ghiChu = request.getParameter("ghiChu");
		    obj.setGhiChu(ghiChu);
		    
		    String ngayBaoHong = request.getParameter("ngayBaoHong");
		    obj.setNgayBaoHong(ngayBaoHong);
		    
		    obj.setId(ccdcId);
		    
		    
		    System.out.println("action: " + action);
		    if (action.equals("new"))
		    {
		    	if (!obj.updateCCDC())
		    	{
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", obj);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoHongUpdate.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    	else
		    	{
		    		obj.DbClose();
		    		
		    		ErpBaoHongList ob = new ErpBaoHongList();
		    		ob.init();
		    		ob.DBClose();
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", ob);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoHong.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    }else
		    	if (action.equals("update"))
		    	{
		    		if (!obj.updateCCDC())
			    	{
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", obj);
				    	
				    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoHongUpdate.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
			    	}
			    	else
			    	{
			    		obj.DbClose();
			    		
			    		ErpBaoHongList ob = new ErpBaoHongList();
			    		ob.init();
			    		ob.DBClose();
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", ob);
				    	
				    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoHong.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
			    	}
		    	}
		    
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoHongUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		obj.DbClose();
    		return;
		}  	
}