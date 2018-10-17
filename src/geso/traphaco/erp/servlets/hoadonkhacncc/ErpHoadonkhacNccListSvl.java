package geso.traphaco.erp.servlets.hoadonkhacncc;

import geso.traphaco.erp.beans.hoadonkhacncc.*;
import geso.traphaco.erp.beans.hoadonkhacncc.imp.ErpHoadonkhacNcc;
import geso.traphaco.erp.beans.hoadonkhacncc.imp.ErpHoadonkhacNccList;
 
import geso.traphaco.center.db.sql.dbutils;
/*import geso.traphaco.center.util.GiuDieuKienLoc;*/
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadonkhacNccListSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHoadonkhacNccListSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHoadonkhacNccList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    // LOAI : 0 Mua hang nhap khau, 1 mua trong nuoc, 2 mua CP/TS/CCDC
	    String loai = util.antiSQLInspection(request.getParameter("loai"));
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();		
		
	    String action = util.getAction(querystring);
	    
	    String dmhId = util.getId(querystring);
	   
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(dmhId,userId);
	    	 
	        obj = new ErpHoadonkhacNccList();
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		  
		    obj.setLoai(loai);
		    obj.setIsdontrahang("0");
		    //GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

		    if(msg.length() > 0) obj.setmsg(msg);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp";
			response.sendRedirect(nextJSP);
	    	
	    }
	    
	    	else if(action.equals("chot"))
	    	{
	    	 
	    		 
	    	    obj = new ErpHoadonkhacNccList();
	    	    
	    	  
	    	    
	    	    obj.setUserId(userId);
	    	    
	    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	    
	    	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    	     
			    obj.ChotHoadonNcc(dmhId);
			    
	    	  
	    	   // GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	    obj.init("");
	    	    if(msg.length() > 0) obj.setmsg(msg);
	    		session.setAttribute("obj", obj);
	    				
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp";
	    		response.sendRedirect(nextJSP);
	    	} 
	    	else {

		    obj = new ErpHoadonkhacNccList();
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setNpp_duocchon_id(npp_duocchon_id);
		   
		    
		    obj.setLoai(loai);
		    obj.setIsdontrahang("0");
		   // GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	
		    if(msg.length() > 0) obj.setmsg(msg);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp";
			response.sendRedirect(nextJSP);
	}
}

	 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ErpHoadonkhacNccList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String loai = util.antiSQLInspection(request.getParameter("loai")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpHoadonkhacNcc dmhBean = new ErpHoadonkhacNcc();
	    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dmhBean.setUserId(userId);
	    	dmhBean.setLoai(loai);
	    	
	    	dmhBean.createRs();
    		
	    	session.setAttribute("ctyId", (String)session.getAttribute("congtyId"));
	    	session.setAttribute("lhhId", "");
	    	session.setAttribute("lspId", "");
	    	session.setAttribute("noibo", "");
	    	
	    	session.setAttribute("dmhBean", dmhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHoadonkhacNccList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoai(loai);
	    		
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	//GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpHoadonkhacNccList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setLoai(loai);
		    	
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	//GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp");  
	    	}
	    }
	    
	}

	private void getSearchQuery(HttpServletRequest request, ErpHoadonkhacNccList obj)
	{
	 
 
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null) 
			ncc = "";
		obj.setNCC(ncc);		
		
		String sodonmuahang = request.getParameter("sodonmuahang");
		if(sodonmuahang == null)
			sodonmuahang = "";
		obj.setSodonmuahang(sodonmuahang);
		
 
		 
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);

	 
 
	}

	private String Delete(String dmhId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			 
			
			  query="UPDATE ERP_HOADONKHACNCC  set TRANGTHAI='2',NGUOISUA="+userId+" WHERE TRANGTHAI='0' AND PK_SEQ="+dmhId;
				
			  if(db.updateReturnInt(query)!=1){
					db.update("rollback");
					return "Không thể xóa hóa đơn điều chỉnh :"+ query;
			  }
			 
					 
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa don mua hang:"+query; 
		}
		
	}
}
