package geso.traphaco.erp.servlets.phanbomuahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phanbomuahang.IErpPhanbomuahang;
import geso.traphaco.erp.beans.phanbomuahang.IErpPhanbomuahangList;
import geso.traphaco.erp.beans.phanbomuahang.imp.ErpPhanbomuahang;
import geso.traphaco.erp.beans.phanbomuahang.imp.ErpPhanbomuahangList;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhanbomuahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ErpPhanbomuahangSvl() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpPhanbomuahangList obj;
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
	    
	    String timnccId = util.getId(querystring);
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(timnccId,userId);
	    }
	    /*else if(action.equals("chot"))
    	{
    		dbutils db = new dbutils();
    		db.update(" update ERP_phanbomuahang set trangthai = '1' where pk_seq = '" + timnccId + "'");
    		
    		String query = "select PK_SEQ, TRANGTHAI from ERP_MUAHANG where PK_SEQ in (select PODUOCPB from ERP_PHANBOMUAHANG_PO where PHANBO_FK = '" + timnccId + "') ";
    		ResultSet rs = db.get(query);
    		if(rs != null)
    		{
    			try
    			{
	    			while(rs.next())
	    			{
	    				String trangthai = rs.getString("trangthai");
	    				String mhId = rs.getString("pk_seq");
	    				if(trangthai.trim().equals("0"))
	    					db.update(" update ERP_muahang set trangthai = '1' where pk_seq = '" + mhId + "'");;
	    			}
    			}
    			catch(Exception ex)
    			{
    				ex.printStackTrace();
    				msg = "Không thể chốt phân bổ";
    			}
    		}
    		
    		db.shutDown();
    	}*/
	    obj = new ErpPhanbomuahangList();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    if(msg.length() > 0) obj.setMsg(msg);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHang.jsp";
		response.sendRedirect(nextJSP);
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpPhanbomuahangList obj;
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
	    	IErpPhanbomuahang timnccBean = new ErpPhanbomuahang("");
	    	timnccBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	timnccBean.setUserId(userId);
	    	
	    	timnccBean.createRs();
	    	
	    	session.setAttribute("timnccBean", timnccBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpPhanbomuahangList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		String search = getSearchQuery(request, obj);
		    	
		    	obj.init(search);

		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHang.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpPhanbomuahangList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHang.jsp");  
	    	}
	    }
	}
    
    private String getSearchQuery(HttpServletRequest request, IErpPhanbomuahangList obj)
	{
		Utility util = new Utility();
		
		geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
		
		String sodenghi = request.getParameter("sopo");
		if(sodenghi == null)
			sodenghi = "";
		obj.setSodenghi(sodenghi);
		
		String pbId = request.getParameter("pbId");
		if(pbId == null)
			pbId = "";
		obj.setPBId(pbId);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		String query = 	  "	select a.pk_seq as id, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.diengiai, 'na') as diengiai, d.sopo "
						+ " from erp_phanbomuahang a, nhanvien b, nhanvien c, erp_muahang d "
						+ " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.muahang_fk = d.pk_seq and a.congty_fk = '" + obj.getCongtyId() + "' AND A.NHAPHANPHOI_FK=" +util.getIdNhapp(obj.getUserId()) ; 		
		
		if(sodenghi.trim().length() > 0)
			query += " and d.sopo like '%" + sodenghi + "%'";
		
		if(pbId.trim().length() > 0)
			query += " and a.pk_seq like '%" + pbId + "%'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";

		System.out.println("Query la: " + query + "\n");

		return query;
	}

	private String Delete(String timnccId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "update ERP_MUAHANG set isduocphanbo = '0' where pk_seq = (select muahang_fk from ERP_PHANBOMUAHANG where pk_seq =  '"+timnccId+"' )";
			System.out.println("2.delete ERP_MUAHANG_SP: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the xoa erp_phanbomuahang: " + query;
			}
			
			query = "delete ERP_MUAHANG_SP where MUAHANG_FK in (select PODUOCPB from ERP_PHANBOMUAHANG_PO where PHANBO_FK =  '"+timnccId+"' )";
			System.out.println("2.delete ERP_MUAHANG_SP: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the xoa erp_phanbomuahang: " + query;
			}
			query = "delete ERP_MUAHANG where PK_SEQ in (select PODUOCPB from ERP_PHANBOMUAHANG_PO where PHANBO_FK = '"+timnccId+"')";
			System.out.println("2.delete ERP_MUAHANG: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the xoa erp_phanbomuahang: " + query;
			}
			query = "delete erp_phanbomuahang_po where phanbo_fk = '"+timnccId+"' ";
			
			System.out.println("2.delete erp_phanbomuahang_po: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the xoa erp_phanbomuahang: " + query;
			}
			query = "delete ERP_PHANBOMUAHANG_SP_CHITIET where phanbo_fk = '"+timnccId+"' ";
			
			System.out.println("2.delete ERP_PHANBOMUAHANG_SP_CHITIET: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the xoa erp_phanbomuahang: " + query;
			}
			query = "delete erp_phanbomuahang where pk_seq = '"+timnccId+"' ";
			
			System.out.println("2.delete erp_phanbomuahang: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the xoa erp_phanbomuahang: " + query;
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
