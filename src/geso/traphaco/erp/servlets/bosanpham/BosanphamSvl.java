package geso.traphaco.erp.servlets.bosanpham;

import geso.traphaco.erp.beans.bosanpham.IBosanpham;
import geso.traphaco.erp.beans.bosanpham.IBosanphamList;
import geso.traphaco.erp.beans.bosanpham.imp.Bosanpham;
import geso.traphaco.erp.beans.bosanpham.imp.BosanphamList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class BosanphamSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public BosanphamSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    IBosanphamList obj;    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj = new BosanphamList();
	    obj.setCtyId(ctyId);
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    obj.init("");
	    session.setAttribute("obj",obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Bosanpham.jsp");	    	
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");

 	    IBosanphamList obj;
 	    obj = new BosanphamList();		
 	    
 		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    obj.setCtyId(ctyId);

 		Utility util = new Utility();
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));

 	    String spTen = util.antiSQLInspection(request.getParameter("spTen"));
 	    if(spTen == null)
 	    	spTen = "";
 	    obj.setSpTen(spTen);
 	    
 	    String spId = "";
 	    if(spTen.indexOf("-") > 0){
 	    		spId = spTen.substring(0, spTen.indexOf("-")).trim();
 	    	}
 	    obj.setSpId(spId);
 	    
 	    
 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null)
 	    	ngay = "";
 	    obj.setNgay(ngay);

 	    String soluong = util.antiSQLInspection(request.getParameter("soluong"));
 	    if(soluong == null)
 	    	soluong = "";
 	    obj.setSoluong(soluong);

 	    String st = "";
	    if(spId.length()>0)
	    { 
	    	st = st + " AND SP.MA = '" + spId + "' ";
        }
	    
	    
	    if(ngay.length()>0)
	    { 
	    	st = st + " AND DQC.NGAY >= '" + ngay + "' ";
        }

        
	    obj.init(st);
         
        String action = request.getParameter("action");
        
        if(action.equals("new"))
        { 
        	IBosanpham objnv = new Bosanpham();
         	objnv.setUserId(userId); 
         	objnv.init();         	
         	obj.DbClose();
     	    session.setAttribute("obj",objnv);
     		response.sendRedirect("/TraphacoHYERP/pages/Erp/BosanphamUpdate.jsp");	  
         }
         else
         {
  	       session.setAttribute("obj",obj);
  		   response.sendRedirect("/TraphacoHYERP/pages/Erp/Bosanpham.jsp");	  
         }
 	
	}

}
