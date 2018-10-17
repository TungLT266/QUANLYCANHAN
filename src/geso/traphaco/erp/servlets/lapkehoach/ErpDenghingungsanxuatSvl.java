package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.lapkehoach.*;
import geso.traphaco.erp.beans.lapkehoach.imp.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDenghingungsanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpDenghingungsanxuatSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    
	    Utility util = new Utility();
		    	    
	    String querystring = request.getQueryString();
		    
	    String userId = util.getUserId(querystring);
		    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
	    IErpDenghingungsanxuat obj = new ErpDenghingungsanxuat();
		obj.setCtyId(ctyId);
		obj.setUserId(userId);
		 
		obj.init();
		session.setAttribute("obj", obj);
		    
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDenghingungsanxuat.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null) nam = "";
		
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		
    	String msg = "";

    	IErpDenghingungsanxuat obj = new ErpDenghingungsanxuat();
    	obj.setCtyId(ctyId);
	    obj.setUserId(userId);

	    obj.setNam(nam);
	    obj.setThang(thang);
	    
	    obj.init();
	    obj.setMsg(msg);
				
	    session.setAttribute("obj", obj);  	
    	session.setAttribute("userId", userId);
		
    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDenghingungsanxuat.jsp");	
	    
	}
	
	

}
