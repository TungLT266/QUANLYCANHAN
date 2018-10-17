package geso.traphaco.distributor.servlets.cttongketnapchai;

import geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchai;
import geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchaiList;
import geso.traphaco.distributor.beans.cttongketnapchai.imp.Cttongketnapchai;
import geso.traphaco.distributor.beans.cttongketnapchai.imp.CttongketnapchaiList;
import geso.traphaco.distributor.beans.tuyenbanhang.imp.Tuyenbanhang;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CttongketnapchaiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public CttongketnapchaiSvl() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		ICttongketnapchaiList obj;
		PrintWriter out; 
			
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    obj  = new CttongketnapchaiList();
	    obj.setUserId(userId);
	    obj.init();
        session.setAttribute("obj",obj);
        String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChai.jsp";
        response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		ICttongketnapchaiList obj;
		PrintWriter out; 
			
		out = response.getWriter();
		Utility util = new Utility();
		obj  = new CttongketnapchaiList();
			   
		obj.setUserId(userId);
			    
	    userId= util.antiSQLInspection(request.getParameter("userId"));
	    obj.setUserId(userId);
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null)
	    	diengiai ="";
	    obj.setDiengiai(diengiai);
	    
	    String ma = util.antiSQLInspection(request.getParameter("ma"));
	    if(ma == null)
	    	ma ="";
	    obj.setMa(ma);
	    
	    String action = request.getParameter("action");
	    
	    obj.init();
	    System.out.println("Action :"+action);
	    if(action.equals("new"))
	    { 
	    	ICttongketnapchai objj = new Cttongketnapchai();
	    	objj.setUserId(userId);
	    	objj.createRs();
    	    session.setAttribute("obj",objj);
	        String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChaiNew.jsp";
	        response.sendRedirect(nextJSP);    	
	    }
	    else
	    {
	    	session.setAttribute("obj",obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChai.jsp";
	    	response.sendRedirect(nextJSP);
	    }
			
	}}

}
