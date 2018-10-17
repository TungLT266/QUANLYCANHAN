package geso.traphaco.erp.servlets.donmuahangtrongnuoc;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.*;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahangList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoantatdonmuahangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public ErpHoantatdonmuahangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonmuahangList obj;
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
	    
	    String dmhId = util.getId(querystring);
	    
	    obj = new ErpDonmuahangList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setTask("hoantat");
	    
    	if(action.equals("chot"))
    	{
    		dbutils db = new dbutils();
    		db.update("update ERP_MUAHANG set trangthai = '2' where pk_seq = '" + dmhId + "'");
    		db.shutDown();
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoanTatDonMuaHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
	    
	    IErpDonmuahangList obj = new ErpDonmuahangList();
    	
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
    	String search = getSearchQuery(request, obj);
    	obj.init(search);
		obj.setUserId(userId);
		
    	session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoanTatDonMuaHang.jsp");
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDonmuahangList obj)
	{
		Utility util=new Utility();
		String query = "select a.PK_SEQ as dmhId, a.NGAYMUA, b.TEN, c.TEN as nccTen, c.MA as nccMa, a.TONGTIENAVAT, a.VAT, a.TONGTIENBVAT," +
		" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua " +
		" from erp_muahang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ inner join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ " +
		" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  " +
		" where a.trangthai = '1' and a.congty_fk = '" + obj.getCongtyId() + "' and b.pk_seq= "+ util.quyen_donvithuchien(obj.getUserId());
		
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
		
		String tongtien = request.getParameter("tongtien");
		if(tongtien == null)
			tongtien = "";
		obj.setTongtiensauVat(tongtien);
		
		if(tungay.length() > 0)
			query += " and a.ngaymua >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaymua <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(ncc.length() > 0)
			query += " and (c.ma like '%" + ncc + "%' or c.ten like N'%" + ncc + "%')";
		
		if(tongtien.length() > 0)
			query += " and a.TONGTIENBVAT >= '" + tongtien + "'";
		
		return query;
	}

}
