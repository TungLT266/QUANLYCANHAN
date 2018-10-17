package geso.traphaco.distributor.servlets.dangkykhuyenmaitichluy;

import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.imp.Dangkykhuyenmaitichluy;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyList;
import geso.traphaco.distributor.beans.tuyenbanhang.imp.Tuyenbanhang;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DangkykhuyenmaitichluySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public DangkykhuyenmaitichluySvl() {
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
		
		IDangkykhuyenmaitichluyList obj;
		  PrintWriter out; 
			
		
		
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    obj  = new DangkykhuyenmaitichluyList();
	    obj.setUserId(userId);
	    obj.init("0");
        session.setAttribute("obj",obj);
        String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
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
		IDangkykhuyenmaitichluyList obj;
		  PrintWriter out; 
			
		
			
			out = response.getWriter();
			Utility util = new Utility();
			  obj  = new DangkykhuyenmaitichluyList();
			   
			  obj.setUserId(userId);
			    
			    userId= util.antiSQLInspection(request.getParameter("userId"));
			    obj.setUserId(userId);
			    
			    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			    if(diengiai == null)
			    	diengiai ="";
			    obj.setDiengiai(diengiai);
			    
			    String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			    if(tungay == null)
			    	tungay ="";
			    obj.setTungay(tungay);
			    String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			    if(denngay == null)
			    	denngay ="";
			    obj.setDenngay(denngay);
			    
			    String action = request.getParameter("action");
			    
			    obj.init("0");
			    System.out.println("Action :"+action);
			    if(action.equals("new"))
			    { 
			    	IDangkykhuyenmaitichluy objj = new Dangkykhuyenmaitichluy();
			    	objj.setUserId(userId);
			    	objj.createRs();
		    	    session.setAttribute("obj",objj);
			        String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuyNew.jsp";
			        response.sendRedirect(nextJSP);    	
			    }
			    else
			    {
			    	
			    	session.setAttribute("obj",obj);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
			    	response.sendRedirect(nextJSP);
			    }
			
	}}

}
