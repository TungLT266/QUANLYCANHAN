package geso.traphaco.distributor.servlets.chinhanhthuho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuhoList;
import geso.traphaco.distributor.beans.chinhanhthuho.imp.ChinhanhthuhoList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

public class ChinhanhthuhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ChinhanhthuhoSvl() {
        super();
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IChinhanhthuhoList obj ;
	    HttpSession session = request.getSession();	  
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    obj= new ChinhanhthuhoList();
	    PrintWriter out = response.getWriter();   
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    String action = util.getAction(querystring);
	    
	    
    	String hdId = util.getId(querystring);
	    obj = new ChinhanhthuhoList();
	    
	    if(request.getParameter("nxtApprSplitting") != null)
		{
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		}   
	    
	    	obj.setUserId(userId);
	        obj.setCongtyId((String)session.getAttribute("congtyId"));	
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    obj.init();
		    
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/Chinhanhthuho.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    Utility util = new Utility();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String congtyId=util.antiSQLInspection(request.getParameter("congtyId"));
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }

	    
		IChinhanhthuhoList obj = new ChinhanhthuhoList();

	 
		obj.setUserId(userId);
		

	 
		HttpSession session = request.getSession();
	
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		this.getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.setCongtyId(congtyId);
		    	obj.init();
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/Chinhanhthuho.jsp";
				response.sendRedirect(nextJSP);
				
		    }
	    	else
	    	{
	    		obj.setUserId(userId);
	    		obj.setCongtyId(congtyId);
		    	this. getSearchQuery(request, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		obj.setUserId(userId);
	    		
	    		
		    	obj.init();		
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/Chinhanhthuho.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	
	
	private void getSearchQuery(HttpServletRequest request, IChinhanhthuhoList obj)
	{
		if(request != null)
		{
		
		geso.traphaco.center.util.Utility utilCenter = new  geso.traphaco.center.util.Utility();
		
		String tungay = utilCenter.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		

		String denngay = utilCenter.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		
		String tenKH=utilCenter.antiSQLInspection(request.getParameter("tenkhachhang"));
		if(tenKH == null)
			tenKH = "";
		obj.setTenKH(tenKH);
		
		String chiNhanhId=utilCenter.antiSQLInspection(request.getParameter("chiNhanhId"));
		if(chiNhanhId == null)
			chiNhanhId = "";
		obj.setChiNhanhId(chiNhanhId);
	
		
		}
		
	

		
	
	}
}