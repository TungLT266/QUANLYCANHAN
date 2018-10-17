package geso.traphaco.erp.servlets.vayvon;
import geso.traphaco.erp.beans.vayvon.IHopdongvay;
import geso.traphaco.erp.beans.vayvon.IHopdongvayList;
import geso.traphaco.erp.beans.vayvon.imp.Hopdongvay;
import geso.traphaco.erp.beans.vayvon.imp.HopdongvayList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class HopdongvaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public HopdongvaySvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    IHopdongvayList obj;    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj = new HopdongvayList();
	    obj.setCtyId(ctyId);
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    obj.init("");
	    session.setAttribute("obj",obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Hopdongvay.jsp");	    	
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");

 	    IHopdongvayList obj;
 	    obj = new HopdongvayList();		
 	    
 		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    obj.setCtyId(ctyId);

 		Utility util = new Utility();
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));

 	    String sohd = util.antiSQLInspection(request.getParameter("sohd"));
 	    if(sohd == null)
 	    	sohd = "";
 	    obj.setSoHD(sohd);

 	    String ngayBD = util.antiSQLInspection(request.getParameter("ngaybd"));
 	    if(ngayBD == null)
 	    	ngayBD = "";
 	    obj.setNgayBD(ngayBD);

 	    String thoihan = util.antiSQLInspection(request.getParameter("thoihan"));
 	    if(thoihan == null)
 	    	thoihan = "";
 	    obj.setThoihan(thoihan);

 	    String st = "";
	    if(sohd.length()>0)
	    { 
	    	st = st + " and upper(HDV.SOHD) like upper(N'%"+ util.replaceAEIOU(sohd) +"%')";
        }
	    
	    
	    if(ngayBD.length()>0)
	    { 
	    	st = st + " and HDV.NGAY >= '" + ngayBD + "' ";
        }

	    if(thoihan.length()>0)
	    { 
	    	st = st + " and HDV.THOIHAN = '" + thoihan + "' ";
        }
        
	    obj.init(st);
         
        String action = request.getParameter("action");
        
        if(action.equals("new"))
        { 
        	IHopdongvay objnv = new Hopdongvay();
         	objnv.setUserId(userId); 
         	objnv.setSession(session);
         	objnv.init();         	
     	
     	    session.setAttribute("obj",objnv);
     		response.sendRedirect("/TraphacoHYERP/pages/Erp/HopdongvayUpdate.jsp");	  
         }
         else
         {
  	       session.setAttribute("obj",obj);
  		   response.sendRedirect("/TraphacoHYERP/pages/Erp/Hopdongvay.jsp");	  
         }
 	
	}

}
