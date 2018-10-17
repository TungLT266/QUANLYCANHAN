package geso.traphaco.distributor.servlets.thanhlyhopdong;

import geso.traphaco.distributor.beans.thanhlyhopdong.IErpThanhlyhopdongNpp;
import geso.traphaco.distributor.beans.thanhlyhopdong.IErpThanhlyhopdongNppList;
import geso.traphaco.distributor.beans.thanhlyhopdong.imp.ErpThanhlyhopdongNpp;
import geso.traphaco.distributor.beans.thanhlyhopdong.imp.ErpThanhlyhopdongNppList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.sql.LxMetaData;

public class ErpThanhlyhopdongNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpThanhlyhopdongNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThanhlyhopdongNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
    
	    HttpSession session = request.getSession();	  
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();

	    Utility util = new Utility();
	       
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    String action = util.getAction(querystring);
	   
    	String lsxId = util.getId(querystring);
	    obj = new ErpThanhlyhopdongNppList();
	 
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    if(action.equals("chot"))
    	{
	    	String msg = "";
	    	if(loaidonhang.equals("0"))
	    		 msg = this.Chot(lsxId,0);
	    	else 
	    		msg = this.Chot(lsxId,1);
			obj.setMsg(msg);
    	}
	    else
	    if(action.equals("delete"))
	    {
	    	String msg = Delete( lsxId) ;
	    	obj.setMsg(msg);
	    }
	    System.out.println("user id :"+userId);
	    obj.setUserId(userId);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}
	private String Delete(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String	query = "Delete  ERP_Thanhlyhopdong_chitiet where Thanhlyhopdong_fk = '"+lsxId+"'";
			if(!db.update(query))
			{
				msg = "Không thể xóa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			query = "Delete  ERP_Thanhlyhopdong where pk_seq = '"+lsxId+"' and trangthai = '0'";
			if(!db.update(query))
			{
				msg = "Không thể xóa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		return "";
	}
	
	private String Chot(String lsxId,int loaitl) {
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			int kt = 0;
			db.getConnection().setAutoCommit(false);
			String query ="";
			if(loaitl == 0)
			{
			
				query = " select SUM(soluong) as soluongchuagiao from erp_hopdongnpp a inner join ERP_HOPDONGNPP_SANPHAM b on a.PK_SEQ = b.hopdong_fk "
						  +"	where LoaiDonHang = 0 and a.PK_SEQ in ( select hopdongnpp_fk from ERP_THANHLYHOPDONG where pk_seq = '"+lsxId+"')"
						 +"	group by sanpham_fk ";
				ResultSet rssl1 = db.get(query);
				rssl1.next();
				int sl1 = rssl1.getInt("soluongchuagiao");
				query = "select SUM(soluong) as soluongdagiao "
						+"from ERP_HOADONNPP_SP a inner join ERP_THANHLYHOPDONG_CHITIET b on a.HOADON_FK = b.HOADONNPP_FK "
						+"where  HOADON_FK in (select HOADONNPP_FK from ERP_THANHLYHOPDONG_CHITIET) "
						+"and b.HOPDONGNPP_FK in  (select HOPDONGNPP_FK from ERP_THANHLYHOPDONG where PK_SEQ = '"+lsxId+"') ";
				ResultSet rssl2 = db.get(query);
				rssl2.next();
				int sl2 = rssl1.getInt("soluongdagiao");
				if(sl1 == sl2)
				{
					query = "update ERP_Thanhlyhopdong set trangthai = '2' where pk_seq = '" + lsxId + "'";
					kt = 1;
				}
				else
					query = "update ERP_Thanhlyhopdong set trangthai = '1' where pk_seq = '" + lsxId + "'";
				
			}
			else
				query = "update ERP_Thanhlyhopdong set trangthai = '2' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Không thể chốt: " + query;
				db.getConnection().rollback();
				return msg;
			}
			System.out.println("Chot "+query);
			if(loaitl == 1 || kt == 1)
			{
				query = "update ERP_HOPDONGNPP set TRANGTHAI = 4 where PK_SEQ in"
						+ "("
							+" select HOPDONGNPP_FK as pk_seq from ERP_THANHLYHOPDONG where PK_SEQ = '"+lsxId+"'"
							+" )";
				if(!db.update(query))
				{
					msg = "Không thể cập nhật: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
		IErpThanhlyhopdongNppList obj = new ErpThanhlyhopdongNppList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpThanhlyhopdongNpp lsxBean = new ErpThanhlyhopdongNpp();
	    	
	    	lsxBean.setUserId(userId);
	    	lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    		obj.setNpp_duocchon_id(npp_duocchon_id);
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    		obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.setUserId(userId);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpThanhlyhopdongNppList obj)
	{
		//Utility util = new Utility();
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
		
		String query = "select a.PK_SEQ, a.trangthai, a.mahopdong, a.loaidonhang, a.tungay, a.denngay, isnull(c.ten, '') as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE   " +
						"from ERP_HopDongNPP a inner join KHO b on a.kho_fk = b.pk_seq left join KHACHHANG c on a.khachhang_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0  and a.kho_fk in "+util.quyen_kho(obj.getUserId())+" and  a.PK_SEQ in(select HOPDONGNPP_FK from ERP_thanhlyhopdong) ";
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		  String userId = util.antiSQLInspection(request.getParameter("userId")); 
		    obj.setUserId(userId);
		    
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String mahd = request.getParameter("mahd");
		if(mahd == null)
			mahd = "";
		obj.setMaHD(mahd);
		
		String loaihdid=request.getParameter("loaihdId");
		if(loaihdid == null)
			loaihdid = "";
		obj.setLoaiHD(loaihdid);		
		query = "select  d.PK_SEQ as id, a.PK_SEQ, d.trangthai, a.mahopdong,a.loaidonhang , d.loaitl, a.tungay, a.denngay, isnull(c.ten, '') as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE   " +
				"from ERP_HOPDONGNPP a inner join KHO b on a.kho_fk = b.pk_seq left join KHACHHANG c on a.khachhang_FK = c.pk_seq  " +
				"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ  inner join ERP_THANHLYHOPDONG d on d.HOPDONGNPP_FK = a.pk_seq " +
				"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + nppId + "'  and a.kho_fk in "+util.quyen_kho(userId)+"";
		if(tungay.length() > 0)
			query += " and a.tungay >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.denngay <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0)
			query += " and a.NPP_FK = '" + nppId + "'";
		
		if(khId.length() > 0)
			query += " and a.KhachHang_FK = '" + khId + "'";
		
		if(loaihdid.length()>0)
			query+=" and a.loaidonhang = '"+loaihdid+"'";
		
		if(mahd.length()>0)
			query+=" and a.mahopdong like '%"+mahd+"%' ";
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
