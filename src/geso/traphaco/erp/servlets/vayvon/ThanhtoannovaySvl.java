package geso.traphaco.erp.servlets.vayvon;

import geso.traphaco.erp.beans.vayvon.IThanhtoannovay;
import geso.traphaco.erp.beans.vayvon.IThanhtoannovayList;
import geso.traphaco.erp.beans.vayvon.imp.Thanhtoannovay;
import geso.traphaco.erp.beans.vayvon.imp.ThanhtoannovayList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ThanhtoannovaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ThanhtoannovaySvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    IThanhtoannovayList obj;    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj = new ThanhtoannovayList();
	    obj.setCtyId(ctyId);
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    obj.init("");
	    session.setAttribute("obj",obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Thanhtoannovay.jsp");	    	
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");

 	    IThanhtoannovayList obj;
 	    obj = new ThanhtoannovayList();		
 	    
 		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    obj.setCtyId(ctyId);

 		Utility util = new Utility();
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));

 	    String sohd = util.antiSQLInspection(request.getParameter("sohd"));
 	    if(sohd == null)
 	    	sohd = "";
 	    obj.setSoHD(sohd);
 	    
 	   String tkvay = util.antiSQLInspection(request.getParameter("tkvay"));
	    if(tkvay == null)
	    	tkvay = "";
	    obj.setTkvay(tkvay);

 	    String st = "";
	    if(sohd.length()>0)
	    { 
	    	st = st + " and upper(HDV.SOHD) like upper(N'%"+ util.replaceAEIOU(sohd) +"%')";
        }
	    
	    if(tkvay.length()>0)
	    { 
	    	st = st + " and upper(NTV.TKVAY) like upper(N'%"+ util.replaceAEIOU(tkvay) +"%')";
        }
	    	    
        
	    obj.init(st);
         
        String action = request.getParameter("action");
        
        if(action.equals("new"))
	    { 
	    	IThanhtoannovay objnv = new Thanhtoannovay();
	    	objnv.setAction("new");
	     	objnv.setUserId(userId); 
	     	objnv.init();         	
	     	
	 	    session.setAttribute("obj",objnv);
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/ThanhtoannovayUpdate.jsp");	  
	    }
	    else
	    {
	       session.setAttribute("obj",obj);
		   response.sendRedirect("/TraphacoHYERP/pages/Erp/Thanhtoannovay.jsp");	  
	    }
 	
	}

}
