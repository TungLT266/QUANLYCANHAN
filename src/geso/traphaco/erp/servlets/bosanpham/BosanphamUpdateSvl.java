package geso.traphaco.erp.servlets.bosanpham;

import geso.traphaco.erp.beans.bosanpham.IBosanpham;
import geso.traphaco.erp.beans.bosanpham.IBosanphamList;
import geso.traphaco.erp.beans.bosanpham.imp.Bosanpham;
import geso.traphaco.erp.beans.bosanpham.imp.BosanphamList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BosanphamUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public BosanphamUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String id = util.getId(querystring);  
	    //System.out.println("UserId Bosanpham "+id);
	    String action = util.getAction(querystring);
	    
	    IBosanpham obj = new Bosanpham(id); 
	    obj.setCtyId(ctyId);
	    
	    obj.setUserId(userId);
   		obj.init();
	     
	    if(action.equals("delete"))
	    { 
	    	obj.Xoa();
	    	IBosanphamList obj1 = new BosanphamList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
		    session.setAttribute("obj",obj1);
		    session.setAttribute("spId1", "");
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Bosanpham.jsp");
	    }
	    else if(action.equals("finish")){
	    	obj.Hoantat();
	    	IBosanphamList obj1 = new BosanphamList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
		    session.setAttribute("obj",obj1);
		    session.setAttribute("spId1", "");
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Bosanpham.jsp");		    	
	    }else if(action.equals("update")){	    	
	    	session.setAttribute("obj",obj);
	    	session.setAttribute("spId1", obj.getSpId1());
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/BosanphamUpdate.jsp");
	    }else{
	    	session.setAttribute("obj",obj);
	    	session.setAttribute("spId1", obj.getSpId1());
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/BosanphamDisplay.jsp");
	    	
	    }
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
 	    
 	    IBosanpham obj = new Bosanpham();		
 		HttpSession session = request.getSession();
 		Utility util = new Utility();
 		
 		String ctyId  = (String)session.getAttribute("congtyId");
 		obj.setCtyId(ctyId);
 		obj.init();
 		
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));
 	    if(userId == null)
 	    	userId = "";
 	    obj.setUserId(userId);
 	    
 	    String Id = util.antiSQLInspection(request.getParameter("Id"));
 	    if(Id ==null)
 		   Id ="";
 	    obj.setId(Id);
 	   
 	    String khoId = util.antiSQLInspection(request.getParameter("khoId"));
 	    if(khoId == null)
 	    	khoId = "";
 	    obj.setKhoId(khoId);

 	    String spTen1 = util.antiSQLInspection(request.getParameter("spTen1"));
 	    if(spTen1 == null)
 	    	spTen1 = "";
 	    obj.setSpTen1(spTen1);
 	    
 	    String spId1 = "";
 	    
 	   
 	    if(spTen1.lastIndexOf("][") > 0)
 	    {
 	    	spId1 = spTen1.substring(spTen1.lastIndexOf("][") + 2, spTen1.length() - 1 ).trim();
 	    }
 	  
 	    System.out.println("spId1: " + spId1);
 	    obj.setSpId1(spId1);
 	    session.setAttribute("lspId", obj.getLspId());
 	    session.setAttribute("spId1", obj.getSpId1());
 	    
 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null)
 	    	ngay = "";
 	    
 	    if(ngay.trim().length() <= 0)
 	    	ngay = getTime();
 	    obj.setNgay(ngay);

 	    String soluong1 = util.antiSQLInspection(request.getParameter("soluong1"));
 	    if(soluong1 == null)
 	    	soluong1 = "";
 	    obj.setSoluong1(soluong1);
 	   
 	    obj.setSpId2(request);
 	    obj.setSoluong2(request);
 	    
 	    String action = request.getParameter("action");
 	    System.out.println("ACTION: " + action);
	    if(action.equals("save"))
	    {
	    	
	    	if(!obj.Luulai())
	    	{
	    		session.setAttribute("obj",obj);
	    	    response.sendRedirect("/TraphacoHYERP/pages/Erp/BosanphamUpdate.jsp");	
	     	}
	    	else
	    	{
	    		IBosanphamList obj1 = new BosanphamList();
	    		obj1.setCtyId(ctyId);
	    		obj1.init("");
	    		obj.DbClose();
	    		session.setAttribute("obj",obj1);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Bosanpham.jsp");	    	

	    	}
	    }
	    else
	    {
	    	session.setAttribute("khoId",khoId);
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/BosanphamUpdate.jsp");	
	    }
	    
	    
	    
	    
	       	
	    }
	
	private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
