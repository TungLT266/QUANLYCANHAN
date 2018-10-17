package geso.traphaco.erp.servlets.doituongunghang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doituongunghang.IDoituongunghang;
import geso.traphaco.erp.beans.doituongunghang.IDoituongunghangList;
import geso.traphaco.erp.beans.doituongunghang.imp.Doituongunghang;
import geso.traphaco.erp.beans.doituongunghang.imp.DoituongunghangList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class DoituongunghangUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public DoituongunghangUpdateSvl() {
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
	    session.setAttribute("congtyId",ctyId);
	    System.out.println("congty ID " + ctyId);
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String id = util.getId(querystring);  
	    //System.out.println("UserId nhanvien "+id);
	    String action = util.getAction(querystring);
	    
	    IDoituongunghang obj = new Doituongunghang(id); 
	    obj.setCtyId(ctyId);
	    
	    obj.setuserId(userId);
   		obj.init();
	    if(action.equals("delete"))
	    { 
	    	IDoituongunghangList obj1 = new DoituongunghangList();
	    	obj1.setCtyId(ctyId);
	    	obj1.xoa(id);	    	
	        obj1.init("");
		    session.setAttribute("obj",obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Doituongunghang.jsp");	
	    }
	    else
	    {
	    	session.setAttribute("obj",obj);
	    	if(querystring.contains("display"))
	    	{
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/DoituongunghangDisplay.jsp");
	    	}else
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/DoituongunghangUpdate.jsp");
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
 	    PrintWriter out = response.getWriter();
 	    IDoituongunghang obj = new Doituongunghang();		
 		HttpSession session = request.getSession();
 		Utility util = new Utility();
 		
	    String ctyId = (String)session.getAttribute("congtyId");	
	    session.setAttribute("congtyId",ctyId);
	    System.out.println("congty ID " + ctyId);
	    obj.setCtyId(ctyId);
 		obj.init();
 		
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));
 	    if(userId == null)
 	    	userId = "";
 	    obj.setuserId(userId);
 	    
 	    String Id = util.antiSQLInspection(request.getParameter("Id"));
 	    if(Id ==null)
 		   Id ="";
 	    obj.setId(Id);
 	   
 	    String ma = util.antiSQLInspection(request.getParameter("ma"));
 	    if(ma == null)
 	    	ma = "";
 	    obj.setMa(ma);

 	    String Ten = util.antiSQLInspection(request.getParameter("ten"));
 	    if(Ten==null)
 	    	Ten = "";
 	    obj.setTen(Ten);
 	   
 	    String email = util.antiSQLInspection(request.getParameter("email"));
 	    if(email == null)
 	    	email ="";
 	    obj.setEmail(email);
 	   
 	   //System.out.println("ngay sinh" + Ngaysinh);
 	  
 	    String Dienthoai = util.antiSQLInspection(request.getParameter("dienthoai")).replace(" ", "");
 	    if(Dienthoai == null)
 	    	Dienthoai ="";
	    obj.setdienthoai(Dienthoai);
	    
	    String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    if(Trangthai == null)
      	    Trangthai = "0";
 	    obj.settrangthai(Trangthai);

 	    String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
 	    if(sotaikhoan == null)
 	    	sotaikhoan = "";
 	    obj.setSotaikhoan(sotaikhoan);
 	    
 	    String nhId = util.antiSQLInspection(request.getParameter("nhId"));
 	    if(nhId == null || nhId == "")
 	    	nhId = "NULL";
 	    obj.setNhId(nhId);

 	    String cnId = util.antiSQLInspection(request.getParameter("cnId"));
 	    if(cnId == null || cnId =="")
 	    	cnId = "NULL";
 	    obj.setCnId(cnId);
 	    
 	   String tkktId = util.antiSQLInspection(request.getParameter("tkktId"));
	    if(tkktId == null)
	    	tkktId = "";
	    obj.setTkktId(tkktId);
 	    
 	   String pbId = util.antiSQLInspection(request.getParameter("pbId"));
	    if(pbId == null)
	    	pbId = "";
	    obj.setPbId(pbId);
	    
	    String diachi = util.antiSQLInspection(request.getParameter("diachi"));
	    if(diachi == null)
	    	diachi = "";
	    obj.setdiachi(diachi);
 	    
	    String cmnd = util.antiSQLInspection(request.getParameter("cmnd"));
	    if(cmnd == null)
	    	cmnd = "";
	    obj.setcmnd(cmnd);
	    
	    String ngaycap = util.antiSQLInspection(request.getParameter("ngaycap"));
	    if(ngaycap == null)
	    	ngaycap = "";
	    obj.setngaycap(ngaycap);
	    
	    String noicap = util.antiSQLInspection(request.getParameter("noicap"));
	    if(noicap == null)
	    	noicap = "";
	    obj.setnoicap(noicap);
	    
	    
	    String action = request.getParameter("action");
	    if(action.equals("save"))
	    {
	    	if(!obj.save())
	    	{
	    		session.setAttribute("obj",obj);
	    	    response.sendRedirect("/TraphacoHYERP/pages/Erp/DoituongunghangUpdate.jsp");	
	     	}
	    	else
	    	{
	    		IDoituongunghangList obj1 = new DoituongunghangList();
	    		obj1.setCtyId(ctyId);
	    		obj1.setuserId(userId);   
	    		obj1.init("");
	    		session.setAttribute("obj",obj1);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Doituongunghang.jsp");
	    	}	
	    }
	    else
	    {    	
	    	 session.setAttribute("obj",obj);
	    	 response.sendRedirect("/TraphacoHYERP/pages/Erp/DoituongunghangUpdate.jsp");	
	    }
	       	
	}

}
