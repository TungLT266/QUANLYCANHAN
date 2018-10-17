package geso.traphaco.distributor.servlets.ctkmkhachhang;

import geso.traphaco.distributor.beans.ctkmkhachhang.ICtkmkhachhangList;
import geso.traphaco.distributor.beans.ctkmkhachhang.imp.CtkmkhachhangList;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CtkmkhachhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
      
    public CtkmkhachhangSvl() {
        super();
        // TODO Auto-generated constructor stub
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
		 ICtkmkhachhangList obj;
		
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		   
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    String querystring = request.getQueryString();
//		    out.println(action);
		    
		    userId = util.getUserId(querystring);
		        
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    obj = new CtkmkhachhangList();
		    obj.setRequestObj(request);
		    obj.setuserId(userId);
		    
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdTen"));
			if(ddkdId == null)
				ddkdId="";
			obj.setDDKdId(ddkdId);
			
			String maKH = util.antiSQLInspection(request.getParameter("maKH"));
			if(maKH == null)
				maKH = "";
			obj.setMaKH(maKH);
			String tenKH = util.antiSQLInspection(request.getParameter("tenKH"));
			if(tenKH == null)
				tenKH = "";
			obj.setTenKH(tenKH);


		    obj.init();
		    
			session.setAttribute("obj", obj);
					
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/Ctkmkhachhang.jsp";
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
		 ICtkmkhachhangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   // this.out = response.getWriter();
	    Utility util = new Utility();
		
		obj = new CtkmkhachhangList();
		obj.setRequestObj(request);
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    obj.setuserId(userId);
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		   if(diengiai ==null)
			   diengiai ="";
		   obj.setDiengiai(diengiai);
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay ==null)
			tungay ="";
		obj.setTungay(tungay);
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay ==null)
			denngay ="";
		obj.setDenngay(denngay);
		
		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdTen"));
		if(ddkdId == null)
			ddkdId="";
		obj.setDDKdId(ddkdId);
		
		String maKH = util.antiSQLInspection(request.getParameter("maKH"));
		if(maKH == null)
			maKH = "";
		obj.setMaKH(maKH);
		String tenKH = util.antiSQLInspection(request.getParameter("tenKH"));
		if(tenKH == null)
			tenKH = "";
		obj.setTenKH(tenKH);
		
		String action = request.getParameter("action");
		
		//a
		if(action.equals("view") || action.equals("next") || action.equals("prev")){
			    	
			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.init();
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		}
		else
			obj.init();
			session.setAttribute("obj", obj);
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/Ctkmkhachhang.jsp";
			response.sendRedirect(nextJSP);
			  
	    
	}}

}
