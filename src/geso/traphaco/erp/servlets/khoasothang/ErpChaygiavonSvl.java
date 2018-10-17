package geso.traphaco.erp.servlets.khoasothang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpKhoasothang;
import geso.traphaco.erp.beans.khoasothang.IErpkhoasothanglist;
import geso.traphaco.erp.beans.khoasothang.IKiemTraDLN;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoaSoThang;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoasothanglist;
import geso.traphaco.erp.beans.khoasothang.imp.KiemTraDLN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChaygiavonSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ErpChaygiavonSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpKhoasothang obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpKhoaSoThang();
	    obj.setUserId(userId);
	    obj.setCtyId((String)session.getAttribute("congtyId"));
	    
	    String task = request.getParameter("task");
	    if(task == null)
	    	task = "";
	    
	    String nextJSP = "";
	    String id="";
	    obj.setId(id);
	    
	     
		    obj.init_chaygiavon();
			nextJSP = "/TraphacoHYERP/pages/Center/ErpChayGiaVon.jsp";
	    
	    session.setAttribute("obj", obj);
	    response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpKhoasothang obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    
	   
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpKhoaSoThang();
	    obj.setCtyId((String)session.getAttribute("congtyId"));
	    
	    obj.setUserId(userId);
	    
	    String thang = util.antiSQLInspection(request.getParameter("thang"));
	    if(thang == null)
	    	thang = "";
	    obj.setThang(thang);
	    
	    String nam = util.antiSQLInspection(request.getParameter("nam"));
	    if(nam == null)
	    	nam = "";
	    obj.setNam(nam);
	    
	    
		
	    String action = "chaygiavon";
	    if(action == null)
	    	action = "";
	    
	    String msg = "";
	    String nextJSP = "";
	    
	   
	     
	      if(action.equals("chaygiavon"))
	    { 	 
		    	obj.ChayGiaVon();
		    	nextJSP = "/TraphacoHYERP/pages/Center/ErpChayGiaVon.jsp";
		    	  session.setAttribute("obj", obj);
		  	    response.sendRedirect(nextJSP);
		  	    
		    
		  
	    } 
	    
	  
	   
	}

}
