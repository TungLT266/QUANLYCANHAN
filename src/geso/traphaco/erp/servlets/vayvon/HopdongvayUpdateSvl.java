package geso.traphaco.erp.servlets.vayvon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.vayvon.IHopdongvay;
import geso.traphaco.erp.beans.vayvon.IHopdongvayList;
import geso.traphaco.erp.beans.vayvon.imp.Hopdongvay;
import geso.traphaco.erp.beans.vayvon.imp.HopdongvayList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HopdongvayUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public HopdongvayUpdateSvl() {
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
	    //System.out.println("UserId Hopdongvay "+id);
	    String action = util.getAction(querystring);
	    
	    IHopdongvay obj = new Hopdongvay(id); 
	    obj.setCtyId(ctyId);
	    
	    obj.setUserId(userId);
	    obj.setSession(session);
   		obj.init();
	     
	    if(action.equals("delete"))
	    { 
	    	obj.Xoa();
	    	IHopdongvayList obj1 = new HopdongvayList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
		    session.setAttribute("obj",obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Hopdongvay.jsp");	
	    }
	    else if(action.equals("finish")){
	    	obj.Hoantat();
	    	IHopdongvayList obj1 = new HopdongvayList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
		    session.setAttribute("obj",obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Hopdongvay.jsp");		    	
	    }else if(action.equals("update")){	    	
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/HopdongvayUpdate.jsp");
	    }else{
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/HopdongvayDisplay.jsp");
	    	
	    }
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
 	    PrintWriter out = response.getWriter();
 	    IHopdongvay obj = new Hopdongvay();		
 		HttpSession session = request.getSession();
 		obj.setSession(session);
 		
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
 	   
 	    String soHD = util.antiSQLInspection(request.getParameter("sohd"));
 	    if(soHD == null)
 	    	soHD = "";
 	    obj.setSoHD(soHD);

 	    String hdtt = util.antiSQLInspection(request.getParameter("hdtt"));
 	    if(hdtt == null)
 	    	hdtt = "";
 	    obj.setHDTT(hdtt);

 	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
 	    if(diengiai == null)
 	    	diengiai = "";
 	    obj.setDiengiai(diengiai);
 	   
 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null)
 	    	ngay ="";
 	    obj.setNgay(ngay);
 	   
 	    String nhId = util.antiSQLInspection(request.getParameter("nhId"));
 	    if(nhId == null)
 	    	nhId = "";
 	    obj.setNhId(nhId);
 	    session.setAttribute("nhId", nhId);
 	    
 	    String cnId = util.antiSQLInspection(request.getParameter("cnId"));
 	    if(cnId == null)
 	    	cnId = "";
 	    obj.setCnId(cnId);
 	    
 	    String ttId = util.antiSQLInspection(request.getParameter("ttId"));
 	    if(ttId == null)
 	    	ttId = "";
 	    obj.setTTId(ttId);

 	    String tongtien = util.antiSQLInspection(request.getParameter("tongtien")).replace(",", "");
 	    if(tongtien == null)
 	    	tongtien = "";
 	    obj.setTongtien(tongtien);

 	    String ntId = util.antiSQLInspection(request.getParameter("ntId"));
 	    if(ntId == null)
 	    	ntId = "";
 	    obj.setNTId(ntId);
 	    System.out.println("Ngoai te"+ntId);

 	    String tongngoaite = util.antiSQLInspection(request.getParameter("tongngoaite")).replace(",", "");
 	    if(tongngoaite == null)
 	    	tongngoaite = "";
 	    obj.setTongngoaite(tongngoaite);

 	    String loai = util.antiSQLInspection(request.getParameter("loai"));
 	    if(loai == null)
 	    	loai = "";
 	    obj.setLoai(loai);

 	    String ngaybd = util.antiSQLInspection(request.getParameter("ngaybd"));
 	    if(ngaybd == null)
 	    	ngaybd = "";
 	    obj.setNgayBD(ngaybd);

 	    String thoihan = util.antiSQLInspection(request.getParameter("thoihan"));
 	    if(thoihan == null)
 	    	thoihan = "";
 	    obj.setThoihan(thoihan);

 	    String action = request.getParameter("action");
	    if(action.equals("save"))
	    {
	    	
	    	if(!obj.save())
	    	{
	    		 obj.CreateRs();
	    		session.setAttribute("obj",obj);
	    	    response.sendRedirect("/TraphacoHYERP/pages/Erp/HopdongvayUpdate.jsp");	
	     	}
	    	else
	    	{
	    		IHopdongvayList obj1 = new HopdongvayList();
	    		obj1.setCtyId(ctyId);
	    		obj1.init("");
	    		session.setAttribute("obj",obj1);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Hopdongvay.jsp");	    	

	    	}
	    	
	    }
	    else
	    {
	    	 obj.CreateRs();
	    	 
	    	 session.setAttribute("obj",obj);
	    	 response.sendRedirect("/TraphacoHYERP/pages/Erp/HopdongvayUpdate.jsp");	
	    }
	    
	    
	    
	    
	       	
	    }

}
