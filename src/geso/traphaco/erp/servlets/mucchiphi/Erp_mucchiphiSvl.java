package geso.traphaco.erp.servlets.mucchiphi;

import geso.traphaco.erp.beans.mucchiphi.IMucchiphiList;
import geso.traphaco.erp.beans.mucchiphi.imp.MucchiphiList;
import geso.traphaco.erp.beans.mucchiphi.IMucchiphi;
import geso.traphaco.erp.beans.mucchiphi.imp.Mucchiphi;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_mucchiphiSvl extends HttpServlet {
	   static final long serialVersionUID = 1L;	    

	   public Erp_mucchiphiSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    
		    HttpSession session = request.getSession();
		    String ctyId = (String)session.getAttribute("congtyId");
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    IMucchiphiList obj = (IMucchiphiList) new MucchiphiList();
		    obj.setUserId(userId);
		    obj.setCtyId(ctyId);
		    
		    String chixem = request.getParameter("chixem");
		    if(chixem == null) chixem = "0";
		    obj.setChixem(chixem);
			
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    	    
		    String id = util.getId(querystring).split(";")[0];

		    if (action.equals("delete")){	   		
			 	obj.delete(id);
			 	System.out.println("[Erp_mucchiphiSvl] delete = " + obj.getMsg());
		    }
		    
		    obj.createDvthList();
		    obj.createMcpList();
		   
		    session.setAttribute("obj", obj);
		    session.setAttribute("userId", userId);
				
		    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Mucchiphi.jsp";
		    response.sendRedirect(nextJSP);
			
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			HttpSession session = request.getSession();
			String ctyId = (String)session.getAttribute("congtyId");
			
		    IMucchiphiList obj = (IMucchiphiList) new MucchiphiList();
		    obj.setCtyId(ctyId);
		    
		    String chixem = request.getParameter("chixem");
		    obj.setChixem(chixem);
		    
		    String userId = request.getParameter("userId");
		    
		    if (request.getParameter("action").equals("search"))
		    {
		    	obj.setUserId(userId);
		    	obj.setCtyId(ctyId);
		    	obj.getSearchQuery(request);

		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Mucchiphi.jsp");
		    }
		    
		    if (request.getParameter("action").equals("new")){

		    	IMucchiphi mcpBean = new Mucchiphi();
		    	mcpBean.setUserId(userId);
		    	mcpBean.setCtyId(ctyId);
		    	mcpBean.createDvthList();
		    	
		    	// Save Data into session
	    		session.setAttribute("mcpBean", mcpBean);
	    		session.setAttribute("userId", userId);
	    		
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucchiphiNew.jsp";
	    		response.sendRedirect(nextJSP);
		    
		    }

		}

}
