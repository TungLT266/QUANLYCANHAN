package geso.traphaco.erp.servlets.donmuahangtrongnuoc;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahang;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahang;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDonmuahangListSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDonmuahangListSvl() {
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
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(dmhId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		dbutils db = new dbutils();
	    		db.update("update ERP_MUAHANG set trangthai = '1' where pk_seq = '" + dmhId + "'");
	    		db.shutDown();
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonmuahangList obj;
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
	    	IErpDonmuahang dmhBean = new ErpDonmuahang();
	    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dmhBean.createRs();
    		
	    	session.setAttribute("lhhId", "");
	    	session.setAttribute("lspId", "");
	    	
	    	session.setAttribute("dmhBean", dmhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpDonmuahangList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpDonmuahangList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp");  
	    	}
	    }
	    
	}

	private String getSearchQuery(HttpServletRequest request, IErpDonmuahangList obj)
	{
		String query = "select a.PK_SEQ as dmhId, a.NGAYMUA, b.TEN, c.TEN as nccTen, c.MA as nccMa, " +
						"b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, " +
						"a.TONGTIENAVAT, a.VAT, " +
						"a.TONGTIENBVAT, " +
						"a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua " +
						"from erp_muahang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ inner join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ " +
						"inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ where a.type = '0' and a.congty_fk = '" + obj.getCongtyId() + "' ";
		
		System.out.println("cau search "+query);
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
		
		String sodonmuahang = request.getParameter("sodonmuahang");
		if(sodonmuahang == null)
			sodonmuahang = "";
		obj.setSodonmuahang(sodonmuahang);				
		
		String loaisanpham = request.getParameter("loaisanpham");
		if(loaisanpham == null)
			loaisanpham = "";
		obj.setLoaisanphamid(loaisanpham);
		
		
		

		if(tungay.length() > 0)
			query += " and a.ngaymua >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaymua <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(loaisanpham.length() > 0)
			query += " and a.loaihanghoa_fk = '" + loaisanpham + "'";
		
		if(ncc.length() > 0)
			query += " and (c.ma like '%" + ncc + "%' or c.ten like N'%" + ncc + "%')";
		
		if(sodonmuahang.length() > 0)
			query += " and ( b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) = '" + sodonmuahang + "') ";
		
		if(tongtien.length() > 0)
			query += " and a.TONGTIENBVAT >= '" + tongtien + "'";
		
		//query += " order by a.NGAYMUA desc, a.trangthai asc, a.pk_seq desc";
		return query;
	}

	private String Delete(String dmhId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "select COUNT(*) as sodong from ERP_NHANHANG where MUAHANG_FK = '" + dmhId + "'";
			System.out.println("1.Query check mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
			System.out.println("So dong la: " + sodong + "\n");
			
			if(sodong > 0)
			{
				return "Đơn mua hàng này đã có nhận hàng, bạn phải xóa nhận hàng trước khi xóa đơn mua hàng này";
			}
			
			query = "Delete From Erp_MuaHang_SP Where MuaHang_FK = '" + dmhId + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn mua hàng này: " + query;
			}
			
			query = "Delete ERP_DuyetMuaHang where muahang_fk = '" + dmhId + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn mua hàng này: " + query;
			}
			
			query = "Delete From  ERP_MUAHANG  where pk_seq = '" + dmhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn mua hàng này: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa don mua hang:"+query; 
		}
		
	}
}
