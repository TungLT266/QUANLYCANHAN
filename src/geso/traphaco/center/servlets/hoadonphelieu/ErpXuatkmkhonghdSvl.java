package geso.traphaco.center.servlets.hoadonphelieu;

import geso.traphaco.center.beans.hoadonphelieu.*;
import geso.traphaco.center.beans.hoadonphelieu.imp.*;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpXuatkmkhonghdSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpXuatkmkhonghdSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpXuatkmkhonghdList obj = new ErpXuatkmkhonghdList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    System.out.println(":: ACTION: " + action);
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update ERP_XUATKMKHONGHD set trangthai = '2' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa ERP_XUATKMKHONGHD";
	    	}
	    	db.shutDown();
	    }
	    else if(action.trim().equals("chot"))
	    {
	    	msg = ChotHoaDon(khlId, userId);
	    	System.out.println(":: KQ CHOT: " + msg);
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHD.jsp";
		response.sendRedirect(nextJSP);
	}

	private static String ChotHoaDon(String Id, String userId )
	{
		String msg = "";
		dbutils db = new dbutils();
		Utility util = new Utility();
		/*util.Check_Huy_NghiepVu_KhoaSo("ERP_XUATKMKHONGHD", Id, "ngayghinhan", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}*/
    	try 
    	{
    		db.getConnection().setAutoCommit(false);			
    		String query = "update ERP_XUATKMKHONGHD set trangthai = '1' where pk_seq = '" + Id + "'";
    		if(!db.update(query))
    		{
    			db.getConnection().rollback();
    			msg = "Không thể cập nhật ERP_XUATKMKHONGHD";
    			return msg;
    		}
    		
    		//TU DONG HOAN TAT CAC HOA DON TU CU TOI MOI
			query = " select xuatcho, ( select count(*) from ERP_XUATKMKHONGHD_SANPHAM_CHITIET where xuatkm_fk = '" + Id + "' ) as soDong " + 
					" from ERP_XUATKMKHONGHD where pk_seq = '" + Id + "' ";
			ResultSet rsDDH = db.get(query);
			String xuatCHO = "";
			int soDong = 0;
			if(rsDDH != null)
			{
				if(rsDDH.next())
				{
					xuatCHO = rsDDH.getString("xuatcho");
					soDong = rsDDH.getInt("soDong");
				}
				rsDDH.close();
			}
			
			if( soDong <= 0 )
			{
				db.getConnection().rollback();
    			msg = "Vui lòng chọn lô xuất trong hóa đơn";
    			return msg;
			}
			
    		//Tạo nhận hàng cho đối tác
    		/*System.out.println("---XUAT CHO: " + xuatCHO);
			if(xuatCHO.equals("0"))  //XUẤT CHO ĐỐI TÁC THÌ TẠO NHẬP HÀNG CHO ĐỐI TÁC
			{
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, SOCHUNGTUGOC, XUATKM_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						"  select distinct a.ngayghinhan, a.ngayghinhan, doituongId as NPP_FK,   " + 
						"   			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.doituongId ) ),  " + 
						"  			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.doituongId ),  " + 
						"  			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.doituongId )),  " + 
						"  			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.doituongId ) ) ),  " + 
						"   	   '100001' as DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = a.doituongId ) as KBH_FK, '" + Id + "', '" + Id + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "'  " + 
						"   from ERP_XUATKMKHONGHD a inner join ERP_XUATKMKHONGHD_SANPHAM b on a.PK_SEQ = b.xuatkm_fk  " + 
						"   where a.PK_SEQ = '" + Id + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						"  select ( select pk_seq from NHAPHANG where XUATKM_FK = a.PK_SEQ  ),   " + 
						"   		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, 0 asLOAI, '' SCHEME, isnull(d.solo, 'NA') solo, isnull(d.ngayhethan, '2019-01-01')  ngayhethan  " + 
						"  from ERP_XUATKMKHONGHD a inner join ERP_XUATKMKHONGHD_SANPHAM b on a.PK_SEQ = b.xuatkm_fk  " + 
						"   		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ    " + 
						"   		left join ERP_XUATKMKHONGHD_SANPHAM_CHITIET d on b.sanpham_fk = d.PK_SEQ and b.xuatkm_fk = d.xuatkm_fk    " + 
						"  where a.PK_SEQ = '" + Id +  "' and b.soluong > 0  ";
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(db.updateReturnInt(query) < 1 )
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, hoadon_fk, isHO )  " +
						"select ( select PK_SEQ from NHAPHANG where XUATKM_FK = '" + Id + "' ) as nhID, '" + Id + "', 1  ";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					db.getConnection().rollback();
					return msg;
				}
			}*/
    		
    		db.getConnection().commit();
    		db.shutDown();
    	} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    		db.update("rollback");
    		return "Có lỗi phát sinh "+ e.getMessage();
    	}
    	finally
    	{
    		db.shutDown();
    	}
		return msg;
	}
	
	public static String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public  static void main(String[] args) {
		String[] ids = new String[] {"100001","100002","100003","100004","100005","100006","100007","100008","100010","100011","100012","100013","100014","100015","100016","100017","100018","100019","100020","100021","100022","100023","100024","100025","100026","100027","100028","100029","100030","100031","100032","100033","100034","100035","100036","100037","100038","100039","100040","100041","100042","100043","100044","100045","100046","100047","100048","100049","100050","100051","100052","100053","100054","100055","100056","100057","100058","100059","100060","100061","100062","100063","100064","100065","100066","100067","100068","100069","100070","100071"};
		
		for(int i = 0; i < ids.length; i++) {
			ChotHoaDon(ids[i], "");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    
	    
	    IErpXuatkmkhonghdList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpXuatkmkhonghd khl = new ErpXuatkmkhonghd();
    		
    		session.setAttribute("nppId", "");
    		khl.setUserId(userId);
    		khl.createRS();
    		
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDNew.jsp");
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpXuatkmkhonghdList();
			    obj.setUserId(userId);

			    session.setAttribute("nppId", "");
			    
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		String search = getSearchQuery(request, obj);
		    	
		    	obj.init(search);

		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	session.setAttribute("obj", obj);
		    	
		    	response.sendRedirect("/TraphacoHYERP/pages/Center/ErpXuatKMKhongHD.jsp");	
		    }
	    	else{
	    	
	    	obj = new ErpXuatkmkhonghdList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	session.setAttribute("nppId", "");
	    	
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/ErpXuatKMKhongHD.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpXuatkmkhonghdList obj) 
	{
		Utility util = new Utility();
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String tennguoitao = request.getParameter("tennguoitao");
		if(tennguoitao == null)
			tennguoitao = "";
		tennguoitao=tennguoitao.trim();
		obj.setTennguoitao(tennguoitao);
		
		
		String ma = request.getParameter("mahoadon");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String khachhang = request.getParameter("khachhang");
		if(khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);
		
		String sql =  	"  select a.pk_seq, a.ngayghinhan, case a.xuatcho when 0 then d.TEN else e.TEN end as khTen, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua,  " + 
						"         a.vat, a.avat as tongtien      " + 
						"  from ERP_XUATKMKHONGHD a inner join NHANVIEN b on a.nguoitao = b.pk_seq       " + 
						"       inner join NHANVIEN c on a.nguoisua = c.pk_seq " + 
						"       left join NHAPHANPHOI d on a.doituongId = d.pk_seq " + 
						"       left join KHACHHANG e on a.doituongId = e.pk_seq " + 
						"  where a.pk_seq > 0	";
		
		if(tennguoitao.length() > 0)
			sql += " and b.ten like N'%" + tennguoitao + "%' ";
		if(diengiai.length() > 0)
			sql += " and a.diengiai like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		if(ma.length() > 0)
		{
			sql += " and a.pk_seq like N'%" + ma + "%' ";
		}
		if(khachhang.length() > 0)
		{
			sql += " and case a.xuatcho when 0 then d.TEN else e.TEN end like '%" + khachhang + "%'  ";
			
		}
		
		return sql;
	}
	
	

}
