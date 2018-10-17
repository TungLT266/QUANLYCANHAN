package geso.traphaco.erp.servlets.danhgianhacc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.danhgianhacc.IErp_Danhgiancc;
import geso.traphaco.erp.beans.danhgianhacc.IErp_DanhgianccList;
import geso.traphaco.erp.beans.danhgianhacc.imp.Erp_Danhgiancc;
import geso.traphaco.erp.beans.danhgianhacc.imp.Erp_DanhgianccList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DanhgianccSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public Erp_DanhgianccSvl() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErp_DanhgianccList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String dgnccId = util.getId(querystring);
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(dgnccId,userId);
	    	 obj = new Erp_DanhgianccList();
	 	    obj.setUserId(userId);
	 	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	 	   GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	 	    if(msg.length() > 0) obj.setMsg(msg);
	 	    
	 	    obj.init("");
	 	    
	 		session.setAttribute("obj", obj);
	 				
	 		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCC.jsp";
	 		response.sendRedirect(nextJSP);
	    }
	    else{
	    obj = new Erp_DanhgianccList();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    if(msg.length() > 0) obj.setMsg(msg);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCC.jsp";
		response.sendRedirect(nextJSP);
	    }
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErp_DanhgianccList obj;
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
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErp_Danhgiancc dgnccBean = new Erp_Danhgiancc("");
	    	dgnccBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dgnccBean.setUserId(userId);
	    	
	    	dgnccBean.createRs();
	    	
	    	session.setAttribute("dgnccBean", dgnccBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new Erp_DanhgianccList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDanhGiaNCC.jsp");	
		    }
	    	else
	    	{
		    	obj = new Erp_DanhgianccList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDanhGiaNCC.jsp");  
	    	}
	    }
	}
    
    private void getSearchQuery(HttpServletRequest request, IErp_DanhgianccList obj)
	{	
		String NccId = request.getParameter("NccId");
		if(NccId == null)
			NccId = "";
		obj.setNccId(NccId);
		
		String spId = request.getParameter("spId");
		if(spId == null)
			spId = "";
		obj.setSpId(spId);
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
//		String query = "select a.PK_SEQ as id, a.NCC_FK, e.TEN as nccten, d.diengiai as spten, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, b.TEN as nguoitao, c.TEN as nguoisua, a.SP_FK, a.dvth_fk ";  
//		query += "from ERP_DANHGIANCC a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ left join erp_congcudungcu d on a.SP_FK = d.PK_SEQ inner join ERP_NHACUNGCAP e on a.NCC_FK = e.PK_SEQ "+
//				 "where a.CONGTY_FK = "+ obj.getCongtyId() +" ";	
//		
//		if(NccId.length() > 0)
//			query += " and a.NCC_FK = '" + NccId + "'";
//		
//		if(spId.length() > 0)
//			query += " and a.SP_FK = '" + spId + "'";
//
//		System.out.println("Query la: " + query + "\n");
//
//		return query;
	}

	private String Delete(String dgnccId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "delete ERP_DGNCC_TIEUCHIDG WHERE DG_FK =  '" + dgnccId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa ERP_DGNCC_TIEUCHIDG: " + query;
			}
			
			query = " DELETE ERP_DANHGIANCC WHERE PK_SEQ = '" + dgnccId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa ERP_DANHGIANCC: " + query;
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
			return "Loi-khong the xoa don tim nha cung cap: "+query; 
		}
		
	}
}
