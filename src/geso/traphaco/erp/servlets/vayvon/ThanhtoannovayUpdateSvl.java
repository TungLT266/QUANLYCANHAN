package geso.traphaco.erp.servlets.vayvon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.vayvon.IThanhtoannovay;
import geso.traphaco.erp.beans.vayvon.IThanhtoannovayList;
import geso.traphaco.erp.beans.vayvon.imp.Thanhtoannovay;
import geso.traphaco.erp.beans.vayvon.imp.ThanhtoannovayList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThanhtoannovayUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public ThanhtoannovayUpdateSvl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    Utility util = new Utility();
//	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String id = util.getId(querystring);  
	    //System.out.println("UserId Thanhtoannovay "+id);
	    String action = util.getAction(querystring);
	    
	    IThanhtoannovay obj = new Thanhtoannovay(id); 
	    obj.setCtyId(ctyId);
	    
	    obj.setUserId(userId);
   		
	     
	    if(action.equals("delete"))
	    { 
	    	obj.Xoa();
	    	IThanhtoannovayList obj1 = new ThanhtoannovayList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
		    session.setAttribute("obj",obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Thanhtoannovay.jsp");	
	    }
	    else if(action.equals("finish")){
	    	String msg = obj.Hoantat();
	    	IThanhtoannovayList obj1 = new ThanhtoannovayList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
	        obj1.setMsg(msg);
		    session.setAttribute("obj",obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Thanhtoannovay.jsp");		    	
	    }else if(action.equals("update")){	
	    	obj.setAction("update");
	    	obj.init();
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ThanhtoannovayUpdate.jsp");
	    
	    }else{ // display
	        obj.setAction("display");
	        obj.init();
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ThanhtoannovayDisplay.jsp");
	    	
	    }
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
// 	    PrintWriter out = response.getWriter();
 	    IThanhtoannovay obj = new Thanhtoannovay();		
 		HttpSession session = request.getSession();
 		Utility util = new Utility();
 		
 		String ctyId  = (String)session.getAttribute("congtyId");
 		obj.setCtyId(ctyId);
 		
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));
 	    if(userId == null)
 	    	userId = "";
 	    obj.setUserId(userId);
 	    
 	    String Id = util.antiSQLInspection(request.getParameter("Id"));
 	    if(Id ==null)
 		   Id ="";
 	    obj.setId(Id);
 	   
 	    String soHD = util.antiSQLInspection(request.getParameter("hdId"));
 	    if(soHD == null)
 	    	soHD = "";
 	    obj.setSoHD(soHD);

 	    String ntvId = util.antiSQLInspection(request.getParameter("ntvId"));
 	    if(ntvId == null)
 	    	ntvId = "";
 	    obj.setNtvId(ntvId);

 	    String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
 	    if(hinhthuc == null)
 	    	hinhthuc = "";
 	    obj.setHinhthuc(hinhthuc);
 	   
 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null){
 	    	ngay ="";
 	    } 	    	
 	    obj.setNgay(ngay);
 	   
 	    String tkctyId = util.antiSQLInspection(request.getParameter("tkctyId"));
 	    if(tkctyId == null)
 	    	tkctyId = "";
 	    obj.setTkCtyId(tkctyId);

 	    String tienlai = util.antiSQLInspection(request.getParameter("tienlai"));
 	    if(tienlai == null)
 	    	tienlai = "0";
 	    obj.setTienlai(tienlai.replace(",", ""));
 	    
 	    String tiengoc = util.antiSQLInspection(request.getParameter("tiengoc"));
 	    if(tiengoc == null)
 	    	tiengoc = "0";
 	    obj.setTiengoc(tiengoc.replace(",", ""));

 	    String tienphat = util.antiSQLInspection(request.getParameter("tienphat"));
 	    if(tienphat == null)
 	    	tienphat = "0";
 	    obj.setTienphat(tienphat.replace(",", ""));

 	    String phikhac = util.antiSQLInspection(request.getParameter("phikhac"));
 	    if(phikhac == null)
 	    	phikhac = "0";
 	    obj.setPhikhac(phikhac.replace(",", ""));

 	    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
 	    if(ghichu == null)
 	    	ghichu = "";
 	    obj.setGhichu(ghichu);

 	    String ttId = util.antiSQLInspection(request.getParameter("ttId"));
 	    if(ttId == null)
 	    	ttId = "";
 	    obj.setTtId(ttId);

 	    boolean err = false;
 	     	    	
 	    String action = request.getParameter("action");
 	    System.out.println("action is: " + action);
	    if(action.equals("save") & !err)
	    {
	    	
	    	if(!obj.save())
	    	{
	    		System.out.println("I am at 1");
	    		session.setAttribute("obj",obj);
	    	    response.sendRedirect("/TraphacoHYERP/pages/Erp/ThanhtoannovayUpdate.jsp");	
	     	}
	    	else
	    	{
	    		System.out.println("I am at 2");
	    		IThanhtoannovayList obj1 = new ThanhtoannovayList();
	    		obj1.setCtyId(ctyId);
	    		obj1.init("");
	    		session.setAttribute("obj",obj1);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Thanhtoannovay.jsp");	    	

	    	}
	    	
	    }
	    else
	    {
	    	System.out.println("I am at 3");
	 		obj.setAction("update");
	    	obj.init_RS();
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ThanhtoannovayUpdate.jsp");	
	    }
	    
	    
	    
	    
	       	
	    }

}
