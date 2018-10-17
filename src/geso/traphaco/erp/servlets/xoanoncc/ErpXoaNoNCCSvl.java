package geso.traphaco.erp.servlets.xoanoncc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoanoncc.IErpXoaNoNCC;
import geso.traphaco.erp.beans.xoanoncc.IErpXoaNoNCCList;
import geso.traphaco.erp.beans.xoanoncc.imp.ErpXoaNoNCC;
import geso.traphaco.erp.beans.xoanoncc.imp.ErpXoaNoNCCList;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpXoaNoNCCSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXoaNoNCCSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXoaNoNCCList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

	    String ServerletName = this.getServletName();
	    
	    util.setSearchToHM(userId, session, ServerletName, "");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String tthdId = util.getId(querystring);
	    
	    obj = new ErpXoaNoNCCList();
	    
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(tthdId);
	    	
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setnppdangnhap(util.getIdNhapp(userId));
		    
			if(msg.length() > 0)
	    		obj.setmsg(msg);
		    obj.init("");
		    session.setAttribute("obj", obj);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaNoNCC.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("chot"))
	    	{
	    		IErpXoaNoNCC tthd = new ErpXoaNoNCC(tthdId);
	    		tthd.setUserId(userId);
	    		tthd.setCongtyId((String)session.getAttribute("congtyId"));
	    		
	    		
	    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
    		    obj.setUserId(userId);
    		    obj.setCongtyId((String)session.getAttribute("congtyId"));
    		    obj.setnppdangnhap(util.getIdNhapp(userId));
	    		if(!tthd.chotTTHD(userId))
	    		{
	    			obj.setmsg(tthd.getMsg());
	    		}
	    		obj.init("");
	    		tthd.DBclose();
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaNoNCC.jsp";
    			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
	    obj.init("");
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaNoNCC.jsp";
		response.sendRedirect(nextJSP);
	    }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXoaNoNCCList obj;
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
	    String ServerletName = this.getServletName();
	    if(action.equals("Tao moi"))
	    {
	    	IErpXoaNoNCC tthdBean = new ErpXoaNoNCC();
	    	tthdBean.setUserId(userId);
	    	tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	tthdBean.setnppdangnhap(util.getIdNhapp(userId));
		    
	    	tthdBean.createRs();
    		
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaNoNCCNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpXoaNoNCCList();
		    	
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setnppdangnhap(util.getIdNhapp(userId));
		    	
		    	this.getSearchQuery(request, obj);

		    	
		    	

		    				
		    	
		    	obj.init("");
		    	
		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	

		    	String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);
		    	
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpXoaNoNCC.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpXoaNoNCCList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setnppdangnhap(util.getIdNhapp(userId));
		    	this.getSearchQuery(request, obj);		    	
				obj.setUserId(userId);
				
		    	
		    	obj.init("");
		    	
		    	
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		
	    		String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpXoaNoNCC.jsp");
	    	}
	    }
	}
	
	public void getSearchQuery(HttpServletRequest request, IErpXoaNoNCCList obj)
	{
//		String 	query = "select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua," +
//						" 		case when a.loaixoano = 0 then ncc.ten else nv.ten end  as tendoituong, " +
//						" 		d.ten as nguoitao, e.ten as nguoisua " +
//						" from ERP_XOANONCC a " +
//						"     left join ERP_NHACUNGCAP ncc on a.NCC_FK= ncc.PK_SEQ  " +
//						"     left join ERP_NHANVIEN nv on a.NHANVIEN_FK= nv.PK_SEQ  " +
//						"     inner join NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq "+
//						"where a.pk_seq > 0 and a.CONGTY_FK = "+obj.getCongtyId();
		
		if(request != null)
		{
			String tungay = request.getParameter("tungay");
			if(tungay == null)
				tungay = "";
			obj.setTungay(tungay);
			
			String ngaychungtu = request.getParameter("ngaychungtu");
			if(ngaychungtu == null)
				ngaychungtu = "";
			obj.setNgayChungTu(ngaychungtu);
			
			String maphieu = request.getParameter("maphieu");
			if(maphieu == null)
				maphieu = "";
			obj.setMaPhieu(maphieu);
			
			String denngay = request.getParameter("denngay");
			if(denngay == null)
				denngay = "";
			obj.setDenngay(denngay);
			
			String nccId = request.getParameter("nccId");
			if(nccId == null)
				nccId = "";
			obj.setNccId(nccId);
			
			String nvId = request.getParameter("nvId");
			if(nvId == null)
				nvId = "";
			obj.setNvId(nvId);
			
			String trangthai = request.getParameter("trangthai");
			if(trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			   Utility util = new Utility();
			   String ServerletName = this.getServletName();
			   HttpSession session = request.getSession();	 
			    String querystring = request.getQueryString();
			    String userId = util.getUserId(querystring);

			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		}
		
		
//		if(obj.getTungay().length() > 0)
//			query += " and a.ngaychungtu >= '" + obj.getTungay() + "'";
//		
//		if(obj.getDenngay().length() > 0)
//			query += " and a.ngaychungtu <= '" + obj.getDenngay() + "'";
//		
//		if(obj.getNgayChungTu().length() > 0)
//			query += " and a.ngaychungtu = '" + obj.getNgayChungTu() + "'";
//		
//		if(obj.getMaPhieu().length() > 0)
//			query += " and a.pk_seq like '%" + obj.getMaPhieu() + "%'";
//		
//		if(obj.getNccId().length() > 0)
//			query += " and ncc.pk_seq = '" + obj.getNccId() + "'";
//		
//		if(obj.getNvId() != null && obj.getNvId().length() > 0)
//			query += " and nv.pk_seq = '" + obj.getNvId() + "'";
//		
//		if(obj.getTrangthai().length() > 0)
//			query += " and a.trangthai = '" + obj.getTrangthai() + "'";
//		
//		return query;
	}
	
	private String Delete(String tthdId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_XOANONCC set trangthai = '2' where pk_seq = '" + tthdId + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa ERP_XOANONCC"; 
		}
		
	}

}
