package geso.traphaco.erp.servlets.khoasothang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.*;
import geso.traphaco.erp.beans.khoasothang.imp.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpTaphopdulieuDMSSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public ErpTaphopdulieuDMSSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpTaphopdulieuDMS obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpTaphopdulieuDMS();
	    obj.setUserId(userId);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTapHopDuLieuDMS.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpTaphopdulieuDMS obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
		HttpSession session = request.getSession();

	    obj = new ErpTaphopdulieuDMS();
	    obj.setUserId(session.getAttribute("userId").toString());
	    obj.setCongtyId( session.getAttribute("congtyId").toString() );
	    obj.setNppId( session.getAttribute("nppId").toString() );
	    	    
	    String thang =  request.getParameter("thang");
	    obj.setThang(thang);
	    
		String nam = request.getParameter("nam");
		obj.setNam(nam);
	    
	    obj.TapHopDuLieu();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTapHopDuLieuDMS.jsp";
		response.sendRedirect(nextJSP);
	}


}
