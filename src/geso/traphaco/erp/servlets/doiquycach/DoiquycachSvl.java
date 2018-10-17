package geso.traphaco.erp.servlets.doiquycach;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doiquycach.IDoiquycach;
import geso.traphaco.erp.beans.doiquycach.IDoiquycachList;
import geso.traphaco.erp.beans.doiquycach.imp.Doiquycach;
import geso.traphaco.erp.beans.doiquycach.imp.DoiquycachList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class DoiquycachSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DoiquycachSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    IDoiquycachList obj;    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj = new DoiquycachList();
	    obj.setCtyId(ctyId);
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    obj.init("");
	    session.setAttribute("obj",obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Doiquycach.jsp");	    	
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");

 	    IDoiquycachList obj;
 	    obj = new DoiquycachList();		
 		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    obj.setCtyId(ctyId);

 		Utility util = new Utility();
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));

 	    String spTen = util.antiSQLInspection(request.getParameter("spTen"));
 	    if(spTen == null)
 	    	spTen = "";
 	    spTen=spTen.trim();
 	    obj.setSpTen(spTen);
 	    
 	   String spnTen = util.antiSQLInspection(request.getParameter("spnTen"));
	    if(spnTen == null)
	    	spnTen = "";
	    spnTen=spnTen.trim();
	    obj.setSpnTen(spnTen);
 	    
 	   
 	    
 	    
 	    String spId = "";
 	    if(spTen.indexOf("-") > 0){
 	    		spId = spTen.substring(0, spTen.indexOf("-")).trim();
 	   	}
 	    obj.setSpId(spId);
 	    
 	    String tungay = util.antiSQLInspection(request.getParameter("tungay"));
 	    if(tungay == null)
 	    	tungay = "";
 	    obj.setTungay(tungay);
 	    
 	   String denngay = util.antiSQLInspection(request.getParameter("denngay"));
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
	    if(sochungtu == null)
	    	sochungtu = "";
	    obj.setSochungtu(sochungtu);

 	    String soluong = util.antiSQLInspection(request.getParameter("soluong"));
 	    if(soluong == null)
 	    	soluong = "";
 	    obj.setSoluong(soluong);
 	    
 	   String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    if(trangthai == null)
	    	trangthai = "";
	    obj.settrangthai(trangthai);
	    
	    String nguoitao = util.antiSQLInspection(request.getParameter("nguoitao"));
	    if(nguoitao == null)
	    	nguoitao = "";
	    obj.setNguoitao(nguoitao);

	  	Utility Ult = new Utility();
	     
	   	
	   	
 	    String st = "";
	    if(spTen.length()>0)
	    { 
  
	    	st = st + " AND SP.TIMKIEM  like N'%" +Ult.replaceAEIOU(spTen)+ "%' ";
        }
	    if(tungay.length()>0)
	    { 
	    	st = st + " AND DQC.NGAY >= '" + tungay + "' ";
        }
	    
	    if(denngay.length()>0)
	    { 
	    	st = st + " AND DQC.NGAY <= '" + denngay + "' ";
        }
	    
	    if(sochungtu.length()>0)
	    { 
	    	st = st + " AND DQC.PK_SEQ like '%" + sochungtu + "%' ";
        }
	    
	    if(trangthai.length() >0){
	    	st = st + " AND DQC.trangthai = '" + trangthai + "' ";
	    }
	    
	    if(nguoitao.length() >0){
	    	st = st + " AND NT.TEN like N'%" + nguoitao + "%' ";
	    }
        
	    if(spnTen.trim().length()> 0)
	    {
	    	st += " AND DQC.PK_SEQ in ( select DOIQUYCACH_FK from ERP_NHANDOIQUYCACH where SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where timkiem like   N'%" + Ult.replaceAEIOU(spnTen) + "%' ) ) ";
	    }
	    	
	    obj.init(st);
         
        String action = request.getParameter("action");
        
        if(action.equals("new"))
        { 
        	IDoiquycach objnv = new Doiquycach();
         	objnv.setUserId(userId);
         	objnv.init();
         	objnv.createRs();         	
         	obj.DbClose();
     	    session.setAttribute("obj",objnv);
     		response.sendRedirect("/TraphacoHYERP/pages/Erp/DoiquycachUpdate.jsp");	  
         }
         else
         {
        	if(action.equals("view") || action.equals("next") || action.equals("prev"))
     	    {
     	    	obj = new DoiquycachList();	
     	    	obj.setCtyId((String)session.getAttribute("congtyId"));
     	     	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
         		
     	     	obj.setUserId(userId);
     	     	obj.init(st);
             	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
     	    	
             	session.setAttribute("obj", obj);
         	    		
             	String nextJSP = "/TraphacoHYERP/pages/Erp/Doiquycach.jsp";	    	
         	    response.sendRedirect(nextJSP);
     	    }
        	
        	else{
	  	       session.setAttribute("obj",obj);
	  		   response.sendRedirect("/TraphacoHYERP/pages/Erp/Doiquycach.jsp");	  
        	}
         }
 	
	}

}
