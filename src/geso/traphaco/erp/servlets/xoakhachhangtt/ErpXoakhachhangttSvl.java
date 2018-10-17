package geso.traphaco.erp.servlets.xoakhachhangtt;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpXoakhachhangtt;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpXoakhachhangttList;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.ErpXoakhachhangtt;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.ErpXoakhachhangttList;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpXoakhachhangttSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXoakhachhangttSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXoakhachhangttList obj;
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
	    
	    String tthdId = util.getId(querystring);
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	   
	   
	    String tungay = "";
	    String denngay = "";
	    String maphieu = "";
	    String nccId = "";
	    String kbhId =  "";
	    String nhomkhId = "";
	    String sotien = "";
	    String trangthai = "";
	    
	    obj = new ErpXoakhachhangttList(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	    
	    obj.setCongtyId(ctyId);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    
	    if (action.equals("delete"))
	    {	
	    	
	    
	    	obj.setUserId(userId);
	    	obj.setCongtyId(ctyId);
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	String msg = Delete(tthdId);
	    	if(msg.length() > 0)
		    	obj.setmsg(msg);
	 	    obj.init("");
	 	    
//	 	    util.setSearchToHM(userId, session, this.getServletName(), "");

	 	    session.setAttribute("tungaytk", obj.getTungay());
	 		session.setAttribute("obj", obj);
	 				
	 		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
	 		response.sendRedirect(nextJSP);
	    	
	    }
	    
	    else if (action.equals("chot")) {
	    		IErpXoakhachhangtt tthd = new ErpXoakhachhangtt(tthdId,tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	    		
	    		
	    		 obj.setUserId(userId);
	    		 obj.setCongtyId(ctyId);
	    		 String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    	 geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	 if(!tthd.chotTTHD(userId,tthdId))
	    		 {
	    			obj.setmsg(tthd.getMsg());
	    		 }
    		     obj.init("");
    		     session.setAttribute("tungaytk", obj.getTungay());
    			 session.setAttribute("obj", obj);
    					
    			 String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
    			 response.sendRedirect(nextJSP);
	    		
	    }
	    else {
	    
		    
		    obj.setUserId(userId);
		    obj.init("");
		    
		    util.setSearchToHM(userId, session, this.getServletName(), "");
	
		    session.setAttribute("tungaytk", obj.getTungay());
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
			response.sendRedirect(nextJSP);
	   }
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXoakhachhangttList obj;
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
	    
	    String chungtu=util.antiSQLInspection(request.getParameter("chungtu"));
		if (chungtu == null)
			chungtu = "";
		
		String tungay = request.getParameter("tungay");
    	String sotien = request.getParameter("sotien");
    	String denngay = request.getParameter("denngay");
    	String nccId = request.getParameter("nppId");
    	String maphieu = request.getParameter("maphieu");
    	String kbhId = request.getParameter("kbhId");
    	String nhomkhId = request.getParameter("nhomkhId");
    	String trangthai = request.getParameter("trangthai");
    	if(trangthai==null)
    	{
    		trangthai="";
    	}
    	
	    String ctyId = (String)session.getAttribute("congtyId");
    	
	    if(action.equals("Tao moi"))
	    {
	    	IErpXoakhachhangtt tthdBean = new ErpXoakhachhangtt(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	    	
	    	tthdBean.setCongtyId(ctyId);	    	
	    	tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	tthdBean.setDoituongId(session.getAttribute("doituongId"));
	    	
	    	tthdBean.createRs();
		    System.out.println("loainhanvien:"+session.getAttribute("loainhanvien"));
    		
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("chot"))
    	{
    		IErpXoakhachhangtt tthd = new ErpXoakhachhangtt(chungtu,tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
    		obj = new ErpXoakhachhangttList(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
    		
    		
    		obj.setUserId(userId);
    		
    		obj.setCongtyId(ctyId);
    		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
    		obj.setDoituongId(session.getAttribute("doituongId"));
	    	
    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	  	    GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		  	if(!tthd.chotTTHD(userId,chungtu))
	  		{
	  			obj.setmsg(tthd.getMsg());
	  		}	
    	    obj.init("");
    	    
    		session.setAttribute("obj", obj);
    				
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
    		response.sendRedirect(nextJSP);
    		
    	} else if (action.equals("delete"))
	    {	
    		obj = new ErpXoakhachhangttList(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	    	
	    	
    		obj.setUserId(userId);
    		
    		obj.setCongtyId(ctyId);
	    	obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	  	    obj.setDoituongId(session.getAttribute("doituongId"));
	    	
	  	    this.getSearchQuery(request, obj);
	  	    
	  	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	  	    geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	  	    
	  	    String msg = Delete(chungtu);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);		
    	    obj.init("");
    	    
    		session.setAttribute("obj", obj);
    				
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpXoakhachhangttList(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	    		
	    		obj.setCongtyId(ctyId);
	    		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    		obj.setDoituongId(session.getAttribute("doituongId"));
	    		
		    	this. getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init("");
		    	
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp");	
		    }
	    	
	    	else
	    	{
		    	obj = new ErpXoakhachhangttList(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
		    	
		    	obj.setCongtyId(ctyId);
		    	obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    		obj.setDoituongId(session.getAttribute("doituongId"));
	    		
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
				obj.setUserId(userId);
				
				String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpXoakhachhangttList obj)
	{
		
//		String query = "select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua," +
//		"      case when a.khachhang_fk is not null then b.ten" +
//		"      when a.nhanvien_fk is not null then nv.ten" +
//		"      when a.ncc_fk is not null then ncc.ten end as tendoituong, " +
//		"     d.ten as nguoitao, e.ten as nguoisua, a.ISHUYCHUNGTU " +
//		"   from ERP_XOAKHTRATRUOC a left join KHACHHANG b on a.khachhang_fk = b.pk_seq " +
//		"     left join ERP_NHANVIEN nv on a.NHANVIEN_FK=nv.PK_SEQ " +
//		"     left join ERP_NHACUNGCAP ncc on a.NCC_FK= ncc.PK_SEQ  " +
//		"     inner join NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq " +
//		"   where a.pk_seq > 0 and a.congty_fk = "+obj.getCongtyId() ;
//		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String sotien = request.getParameter("sotien");
		if(sotien == null)
			sotien = "";
		obj.setSotien(sotien.trim().replaceAll(",", ""));
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String nccId = request.getParameter("nppId");
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String maphieu = request.getParameter("maphieu");
		if(maphieu == null)
			maphieu = "";
		obj.setMaPhieu(maphieu);
		
		String kbhId = request.getParameter("kbhId");
		if(kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);
		
		String nhomkhId = request.getParameter("nhomkhId");
		if(nhomkhId == null)
			nhomkhId = "";
		obj.setNhomkhId(nhomkhId);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
//		if(tungay.length() > 0)
//			query += " and a.ngaychungtu >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaychungtu <= '" + denngay + "'";
//		
//		if(nccId.length() > 0)
//			query += " and b.pk_seq = '" + nccId + "'";
//		
//
//		if(maphieu.length() > 0)
//			query += " and a.pk_seq like '%" + maphieu + "%'";
//		
//		if(kbhId.length()>0)
//			query += " and a.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG_KENHBANHANG WHERE KENHBANHANG_FK = "+kbhId+")";
//		
//		if(nhomkhId.length()>0)
//			query += " and a.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG WHERE NHOMKHACHHANG_FK = "+nhomkhId+")";
//		
//		if(trangthai.length()>0)
//			query+= " and a.trangthai = "+ trangthai;
//		
//		return query;
	}
	
	private String Delete(String tthdId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_XOAKHTRATRUOC set trangthai = '2' where pk_seq = '" + tthdId + "' and trangthai=0");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa ERP_XOAKHTRATRUOC"; 
		}
		
	}

}
