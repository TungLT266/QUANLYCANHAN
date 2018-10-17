package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.lapkehoach.IErpLenhmuahangdk;
import geso.traphaco.erp.beans.lapkehoach.IErpLenhmuahangdkList;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpLenhmuahangdk;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpLenhmuahangdkList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.*;
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
import java.sql.ResultSet;

public class ErpLenhmuahangdukienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpLenhmuahangdukienUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		IErpLenhmuahangdk lmhBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    System.out.println("Id is :" + id);
	    
	    String action = util.getAction(querystring);
	    String nextJSP;
	    
	    if(action.equals("order")){
	    	try{
	    		dbutils db = new dbutils();
	    		String query = 	"SELECT MHDK.*, SP.TEN + ' ' + SP.QUYCACH AS TEN, DVDL.DONVI " + 
	    						"FROM ERP_MUANGUYENLIEUDUKIEN MHDK " +
	    						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHDK.SANPHAM_FK " +
	    						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
	    						"WHERE MHDK.PK_SEQ = '" + id + "' ";
	    	
	    		System.out.println(query);
	    	
	    		ResultSet rs = db.get(query);
	    		IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(); 
//	    		IErpDonmuahang dmhBean = new ErpDonmuahang();
			
	    		dmhBean.setCongtyId(ctyId);
	    		dmhBean.setUserId(userId);
	    		dmhBean.CreatePOfromPR(rs, id);
	    		dmhBean.init();
			
	    		nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangUpdate_Giay.jsp";
			
	    		session.setAttribute("lhhId", "0");
	    		session.setAttribute("lspId", "100002");
	    		session.setAttribute("dmhBean", dmhBean);
	    		response.sendRedirect(nextJSP);
			
	    		if(rs != null) rs.close();
	    			db.shutDown();
	    	}catch(java.sql.SQLException e){return;}
	    	return;
	    }
	    
	    lmhBean = new ErpLenhmuahangdk();
	    lmhBean.setCtyId(ctyId);
	    
	    lmhBean.setId(id);
	    lmhBean.setUserId(userId);
	    
	    lmhBean.init();
        session.setAttribute("lmhBean", lmhBean);
                
        if(action.equals("display")){
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKienDisplay.jsp";
        }else{
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKienUpdate.jsp";
        }
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpLenhmuahangdk lmhBean;

		Utility util = new Utility();
	    String Id = util.antiSQLInspection(request.getParameter("id"));	
	    
	    lmhBean = new ErpLenhmuahangdk();
	    lmhBean.setCtyId(ctyId);
	    
	    lmhBean.setId(Id);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		lmhBean.setUserId(userId);	       
		
		String soluong = util.antiSQLInspection(request.getParameter("soluong"));
		lmhBean.setSoluong(soluong);
		
//		String ngay = util.antiSQLInspection(request.getParameter("ngay"));
//		lmhBean.setNgay(ngay);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null) trangthai = "2";
		lmhBean.setTrangthai(trangthai);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{	
 			if(!lmhBean.update()){
 			    lmhBean.init();
 		        session.setAttribute("lmhBean", lmhBean);
 		        
 		        String nextJSP;
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKienUpdate.jsp";
 		        response.sendRedirect(nextJSP);
 				
 			}else{
 			    IErpLenhmuahangdkList obj = new ErpLenhmuahangdkList();
 			    obj.setCtyId(ctyId);
 			    obj.setUserId(userId);
 			
 			    obj.init();
 				session.setAttribute("obj", obj);
 			    
 			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKien.jsp";
 				response.sendRedirect(nextJSP);
 			}
    		
	    }
		else
		{
			lmhBean.init();
			session.setAttribute("userId", userId);
			session.setAttribute("lmhBean", lmhBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKienDisplay.jsp";
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
