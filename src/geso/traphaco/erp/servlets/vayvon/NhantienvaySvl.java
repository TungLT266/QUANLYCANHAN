package geso.traphaco.erp.servlets.vayvon;

import geso.traphaco.erp.beans.vayvon.INhantienvay;
import geso.traphaco.erp.beans.vayvon.INhantienvayList;
import geso.traphaco.erp.beans.vayvon.imp.Nhantienvay;
import geso.traphaco.erp.beans.vayvon.imp.NhantienvayList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class NhantienvaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public NhantienvaySvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    INhantienvayList obj;    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	  
	    obj = new NhantienvayList();
	    obj.setCtyId(ctyId);
	    String nppId = (String)session.getAttribute("nppId");
	    obj.setNppId(nppId);
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    nppId = util.getIdNhapp(userId);
	    obj.init("");
	    session.setAttribute("obj",obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Nhantienvay.jsp");	    	
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");

 	    INhantienvayList obj;
 	    obj = new NhantienvayList();		
 	    
 		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    obj.setCtyId(ctyId);
		String nppId = (String)session.getAttribute("nppId");
   	    

 		Utility util = new Utility();
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));
 	    nppId = util.getIdNhapp(userId);
 	    obj.setNppId(nppId);

 	    String sohd = util.antiSQLInspection(request.getParameter("sohd"));
 	    if(sohd == null)
 	    	sohd = "";
 	    obj.setSoHD(sohd);

 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null)
 	    	ngay = "";
 	    obj.setNgay(ngay);

 	    String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
 	    if(hinhthuc == null)
 	    	hinhthuc = "";
 	    obj.setHinhthuc(hinhthuc);
 	    
 	   String tkvay = util.antiSQLInspection(request.getParameter("tkvay"));
	    if(tkvay == null)
	    	tkvay = "";
	    obj.setTkvay(tkvay);
 	    

 	    String st = "";
	    if(sohd.length()>0)
	    { 
	    	st = st + " and upper(HDV.SOHD) like upper(N'%"+ util.replaceAEIOU(sohd) +"%')";
        }
	    
	    
	    if(ngay.length()>0)
	    { 
	    	st = st + " and NTV.NGAYNHAN = '" + ngay + "' ";
        }

	    if(hinhthuc.length()>0)
	    { 
	    	st = st + " and NTV.HINHTHUC = '" + hinhthuc + "' ";
        }
	    
	    if(tkvay.length()>0)
	    { 
	    	st = st + " and NTV.TKVAY like N'%"+ tkvay +"%' ";
        }
        
	    obj.init(st);
         
        String action = request.getParameter("action");
        
        if(action.equals("new"))
        { 
        	INhantienvay objnv = new Nhantienvay();
        	objnv.setCtyId(ctyId);
        	objnv.setNppId(nppId);
         	objnv.setUserId(userId); 
         	objnv.init();         
         	objnv.XacdinhTienTe();
         	objnv.createPhieuChi();
     	    session.setAttribute("obj",objnv);
     		response.sendRedirect("/TraphacoHYERP/pages/Erp/NhantienvayUpdate.jsp");	  
         }
         else
         {
  	       session.setAttribute("obj",obj);
  		   response.sendRedirect("/TraphacoHYERP/pages/Erp/Nhantienvay.jsp");	  
         }
 	
	}

}
