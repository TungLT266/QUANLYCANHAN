package geso.traphaco.distributor.servlets.hoadontaichinhNPP;

import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPP;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPPList;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPP;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPPList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadontaichinhNPPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHoadontaichinhNPPSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHoadontaichinhNPPList obj;
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
	    obj = new ErpHoadontaichinhNPPList();
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String phanloai = request.getParameter("loai");
		if(phanloai == null)
			phanloai = "";
		obj.setPhanloai(phanloai);
		
		String nppId = util.getIdNhapp(userId);
	    if(nppId == null)
	    	nppId = "";
	    obj.setnppId(util.getIdNhapp(userId));
	    
	    System.out.println("ACTION: " + action );
	    
    	if(action.equals("chot"))
    	{
    		IErpHoadontaichinhNPP hdBean = new ErpHoadontaichinhNPP();
    		hdBean.setcongtyId((String)session.getAttribute("congtyId"));
    		hdBean.setId(lsxId);
    		hdBean.setUserId(userId);
    		hdBean.setnppDangnhap(nppId);
    		hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
    		hdBean.setDoituongId(session.getAttribute("doituongId"));
    		hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
    		hdBean.setNpp_duocchon_id(npp_duocchon_id);
    		hdBean.chot("0");
    		
    		if( hdBean.getMsg().trim().length() > 0 )
    			obj.setMsg( hdBean.getMsg() );
    		else
    		{
    			String nextJSP = "ErpHoadontaichinhNPPPdfSvl?userId=" + userId + "&pdf=" + lsxId + "&nppId=" + obj.getnppId();
    			response.sendRedirect(nextJSP);
    			
    			return;
    		}
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId,userId, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("huy"))
    	{
    		String msg = this.Huy(lsxId,userId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Huy(String lsxId, String userId) 
	{
		return "";
	}
	
	private String Delete(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";

		Utility util = new Utility();
		msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", lsxId, "ngayxuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}

		/*int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, "", "ERP_HOADONNPP", "ngayxuatHD", lsxId);
		if(checkKS_KINHDOANH != -1 )
		{
			if(checkKS_KINHDOANH == 0)
			{
				db.shutDown();
				return "Ngày đơn hàng nằm trong tháng đã khóa sổ kinh doanh. Vui lòng kiểm tra lại.";
			}
		}*/
		
		try
		{	
			//Nếu là hóa đơn của đơn import, thì không được xóa sửa gì
			String query = "select COUNT(*) as soDong from ERP_DONDATHANGNPP " +
						   "where  isnull( note, '' ) like N'%Import%' and PK_SEQ in ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "' )";
			ResultSet rsCheck = db.get(query);
			int sodong = 0;
			if( rsCheck != null )
			{
				if( rsCheck.next() )
				{
					sodong = rsCheck.getInt("soDong");
				}
				rsCheck.close();
			}
			
			if( sodong > 0 )
			{
				msg = "Hóa đơn của đơn hàng Import, bạn không thể xóa hóa đơn";
				return msg;
			}
			
			
			query = " SELECT NGUOITAO FROM ERP_HOADONNPP WHERE PK_SEQ = "+lsxId;
			ResultSet rsCheck1 = db.get(query);
			
			if(rsCheck1!=null)
			{
				if(rsCheck1.next())
				{
					if(!userId.equals(rsCheck1.getString("NGUOITAO")))
						{
							msg = "Hoa don chi duoc xoa boi nguoi tao hoa don. ";
							return msg;
						}
				}
				rsCheck1.close();
			}
			
			query = " 	SELECT SUM(A.dem) dem \n"+
					"	FROM ( \n"+
					"	SELECT COUNT(B.hoadon_fk) dem \n"+
					"	from ERP_BANGKETHUTIEN A INNER JOIN ERP_BANGKETHUTIEN_HOADON B ON A.pk_seq = B.bangke_fk \n"+
					"	WHERE A.trangthai != 2 and B.HOADON_FK = "+lsxId+" \n"+
	
					"	UNION ALL \n"+
	
					" 	SELECT COUNT(B.HOADON_FK) dem \n"+
					"	FROM ERP_THUTIEN A INNER JOIN ERP_THUTIEN_HOADON B ON A.PK_SEQ = B.THUTIEN_FK \n"+
					"	WHERE A.TRANGTHAI != 2 and B.HOADON_FK = "+lsxId+" AND B.LOAIHOADON = 0 \n"+
					" 	) A \n";
			
			System.out.println(query);
			rsCheck1 = db.get(query);
			
			int isDaThuTien = 0;
			if(rsCheck1!=null)
			{
				if(rsCheck1.next())
				{
					isDaThuTien = rsCheck1.getInt("dem");
				}
				rsCheck1.close();
			}
			
			if(isDaThuTien >=1)
			{
				msg = "Bạn không thể xóa hóa đơn này vì hóa đơn đã có trong bảng kê || phiếu thu tiền.  ";
				return msg;
			}				
			
			db.getConnection().setAutoCommit(false);
			
			String ngaynghiepvu= "";

			query = 
				"select DDH_FK,  \n" +
				/*"	ISNULL ( (  select max(TRANGTHAI) from ERP_YCXUATKHONPP " +
				"				where TRANGTHAI != '2' and PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "' ) )  " +
				"		 ), 0) as trangthai,  " +*/
				"	isnull(( select trangthai from ERP_YCXUATKHONPP where trangthai != 2 and hoadon_fk = '" + lsxId + "' ),0) as trangthai, \n"+
				"	b.loaixuatHD , b.NGAYXUATHD, b.NGAYGHINHAN, b.kho_fk, "+
				"   ( select khachhangKG_FK from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "' ) ) as khachhangKG_FK, "+
				"   isnull(b.donhangmuon,0) isdonhangmuon \n" +
				"from ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.pk_seq \n" + 
				"where HOADONNPP_FK = '" + lsxId + "' ";

			System.out.println("---- CHECK PHIEU GIAO HANG: " + query );
			ResultSet rsDDH = db.get(query);
			String phieugiaohang_fk = "";
			String ddh_fk = "";
			String trangthai = "";
			String loaiXHD = "";
			String khachhangKG_FK = "";
			String donhangmuon = "";
			/*if(rsDDH != null)*/
			{

				if(rsDDH.next())
				{
					//phieugiaohang_fk = rsDDH.getString("phieugiaohang_fk") == null ? "" : rsDDH.getString("phieugiaohang_fk");	
					trangthai = rsDDH.getString("trangthai");	
					loaiXHD = rsDDH.getString("loaixuatHD");	
					ddh_fk = rsDDH.getString("DDH_FK") == null ? "" : rsDDH.getString("DDH_FK");
					
					ngaynghiepvu = rsDDH.getString("NGAYGHINHAN");	
					khachhangKG_FK = rsDDH.getString("khachhangKG_FK") == null ? "" : rsDDH.getString("khachhangKG_FK");
					donhangmuon = rsDDH.getString("isdonhangmuon");
				}
			}

			if(trangthai.equals("1"))
			{
				msg = "Đơn hàng của hóa đơn này đã tồn tại phiếu giao hàng ở trạng thái duyệt, bạn không thể xóa hóa đơn";
				db.getConnection().rollback();
				return msg;
			}

			if(ddh_fk.trim().length() <= 0 )
			{
				msg = "Hóa đơn IMPORT, bạn không thể xóa";
				db.getConnection().rollback();
				return msg;
			}

			
			//nếu đã có phiếu giao hàng, chưa chốt thì xóa phiếu giao hàng đó
			/*if(phieugiaohang_fk.trim().length() > 0 )
			{*/
				query = "Update ERP_YCXUATKHONPP set trangthai = '2' "
						+ "where pk_seq in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "' ) ) ";
				if(!db.update(query))
				{
					msg = "Lỗi khi xóa: " + query;
					db.getConnection().rollback();
					return msg;
				}

				/*//TANG KHO NGUOC LAI
					query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
							" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
							" from " +
							" ( " +
							" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.solo, b.ngayhethan, SUM(b.soluong) as tongxuat  " +
							" 	from ERP_YCXUATKHONPP a " +
							"	inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
							" 	where ycxk_fk = '" + phieugiaohang_fk + "' " +
							" 	group by a.kho_fk, a.nhomkenh_fk, b.solo, b.ngayhethan, b.sanpham_fk " +
							" ) " +
							" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
							" and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO " +
							" and CT.ngayhethan = kho.ngayhethan and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "' ";
					System.out.println("---1 CAP NHAT KHO: " + query);
					if(db.updateReturnInt(query) < 1 )
					{
						msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
						db.getConnection().rollback();
						return msg;
					}

					query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
							" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
							" from " +
							" ( " +
							" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
							" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
							" 	where ycxk_fk = '" + phieugiaohang_fk + "' " +
							" 	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk " +
							" ) " +
							" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
							" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "'  ";
					System.out.println("---2 CAP NHAT KHO: " + query);
					if(db.updateReturnInt(query) < 1 )
					{
						msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
						db.getConnection().rollback();
						return msg;
					}*/
			/*}*/


			//TANG KHO NGUOC LAI
			if( khachhangKG_FK.trim().length() <= 0 )
			{
				query =  "update kho set kho.AVAILABLE = kho.AVAILABLE + dathang.soluong,   " + 
						"				 kho.SOLUONG = kho.SOLUONG + dathang.soluong		" +
						"from      " + 
						"(      " + 
						"	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
						"	from " + 
						"	( " + 
						"		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
						"				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong    " + 
						"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
						"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
						"		where a.dondathang_fk in (  " + ddh_fk + "  ) " + 
						"	union ALL	 " + 
						"		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
						"		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
						"				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
						"		where a.DONDATHANGID in (  " + ddh_fk + "  )   " + 
						"	) " + 
						"	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
						")      " + 
						"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
						"		inner join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK ";
			}
			else
			{
				query =  "update kho set kho.AVAILABLE = kho.AVAILABLE + dathang.soluong,   " + 
						"				 kho.SOLUONG = kho.SOLUONG + dathang.soluong		" +
						"from      " + 
						"(      " + 
						"	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
						"	from " + 
						"	( " + 
						"		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
						"				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong    " + 
						"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
						"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
						"		where a.dondathang_fk in (  " + ddh_fk + "  ) " + 
						"	union ALL	 " + 
						"		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
						"		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
						"				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
						"		where a.DONDATHANGID in (  " + ddh_fk + "  )   " + 
						"	) " + 
						"	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
						")      " + 
						"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
						"		inner join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
						"where kho.khachhang_fk = '" + khachhangKG_FK + "' and kho.isNPP = '0' ";
			}
			
			System.out.println("---1.1 CAP NHAT KHO: " + query);
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}

			if( khachhangKG_FK.trim().length() <= 0 )
			{
				query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
						" 			   kho.SOLUONG = kho.SOLUONG + CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.SOLO, b.ngayhethan, SUM( b.soluong * dbo.LayQuyCach ( b.SANPHAM_FK, NULL, b.DVDL_FK ) ) as tongxuat  " +
						"	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk   " +
						"	where a.PK_SEQ = '" + ddh_fk + "'   " +
						"	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.SOLO, b.ngayhethan " +
						" ) " +
						" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
						" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and CT.solo = kho.solo and CT.ngayhethan = kho.ngayhethan and kho.NPP_FK = '" + nppId + "'  ";
			}
			else
			{
				query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
						" 			   kho.SOLUONG = kho.SOLUONG + CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.SOLO, b.ngayhethan, SUM( b.soluong * dbo.LayQuyCach ( b.SANPHAM_FK, NULL, b.DVDL_FK ) ) as tongxuat  " +
						"	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk   " +
						"	where a.PK_SEQ = '" + ddh_fk + "'   " +
						"	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.SOLO, b.ngayhethan " +
						" ) " +
						" CT inner join NHAPP_KHO_KYGUI_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
						" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and CT.solo = kho.solo and CT.ngayhethan = kho.ngayhethan and kho.NPP_FK = '" + nppId + "'  " + 
						"where kho.khachhang_fk = '" + khachhangKG_FK + "' and kho.isNPP = '0' ";
			}
			System.out.println("---2.1 CAP NHAT KHO: " + query);
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}

			//Mở lại trạng thái đơn hàng về chưa duyệt
			query = "update ERP_DONDATHANGNPP set trangthai = 0, cs_duyet = '0', asm_duyet = '0', ngaymoduyet = getdate(), ngay_mohoadon = getdate() " + 
					" where pk_seq = '" + ddh_fk + "' ";
			System.out.println(query);
			if(!db.update(query))
			{
				msg = "Không thể hủy ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//XOA SAN PHAM KHI DUYET CS
			query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddh_fk + "' ";
			System.out.println(query);
			if(!db.update(query))
			{
				msg = "Không thể hủy ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//XÓA CHUYỂN KHO ĐƠN HÀNG MƯỢN
			if(!donhangmuon.equals("0"))
			{
				query = "select pk_seq, trangthai from erp_chuyenkho where dondathangnpp_fk = "+ddh_fk+" and trangthai <> 4";
				ResultSet rsCK = db.get(query);
				if(rsCK.next())
				{
					String ckId = rsCK.getString("pk_seq");
					String tt = rsCK.getString("trangthai");
					//Trạng thái 1: đã xuất kho, chưa nhận hàng => xóa phiếu chuyển kho
					if(!tt.equals("3"))
					{
						query = "update erp_chuyenkho set trangthai = 4 where pk_seq = "+ckId;
						System.out.println("[TT1, update erp_chuyenkho] "+query);
						if(!db.update(query))
						{
							msg = "Không thể hủy ERP_HOADONNPP " + query;
							db.getConnection().rollback();
							return msg;
						}
					}
					//Trạng thái 3: đã nhận hàng => trừ kho ký gửi, xóa phiếu chuyển kho
					else
					{
						query = "select a.KhoNhan_FK, a.npp_fk, a.KH_NHAN_FK, a.nhomkenh_fk, b.sanpham_fk, b.solo, b.soluong as soluongnhan, \n"+
								"c.soluong, c.AVAILABLE, b.NGAYHETHAN \n"+ 
								"from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SP_NHANHANG b on a.PK_SEQ = b.chuyenkho_fk \n"+
								"inner join NHAPP_KHO_KYGUI_CHITIET c on a.KhoNhan_FK = c.KHO_FK and a.KH_NHAN_FK = c.khachhang_fk and a.npp_fk = c.npp_fk \n"+
								"and b.sanpham_fk = c.SANPHAM_FK and b.solo = c.SOLO and b.NGAYHETHAN = c.NGAYHETHAN and c.nhomkenh_fk = a.nhomkenh_fk \n"+
								"where a.PK_SEQ = "+ckId;
						ResultSet rsSP = db.get(query);
						while(rsSP.next())
						{
							String kho_fk = rsSP.getString("khonhan_fk");
							String npp_fk = rsSP.getString("npp_fk");
							String nhomkenh_fk = rsSP.getString("nhomkenh_fk");
							String kh_nhan_fk = rsSP.getString("kh_nhan_fk");
							String sanpham_fk = rsSP.getString("sanpham_fk");
							String solo = rsSP.getString("solo");
							String ngayhethan = rsSP.getString("ngayhethan");
							double soluongnhan = rsSP.getDouble("soluongnhan");
							double available = rsSP.getDouble("AVAILABLE");
							if(available >= soluongnhan)
							{
								query = "update nhapp_kho_kygui_chitiet set soluong = soluong - "+soluongnhan+", available = available - "+soluongnhan+" \n"+
										"where kho_fk = "+kho_fk+" and npp_fk = "+npp_fk+" and nhomkenh_fk = "+nhomkenh_fk+" and sanpham_fk = "+sanpham_fk+" \n"+
										"and khachhang_fk = "+kh_nhan_fk+" and solo = '"+solo+"' and ngayhethan = '"+ngayhethan+"'";
								System.out.println("[nhapp_kho_kygui_chitiet] "+query);
								if(!db.update(query))
								{
									msg = "Không thể hủy ERP_HOADONNPP " + query;
									db.getConnection().rollback();
									return msg;
								}
								query = "update nhapp_kho_kygui set soluong = soluong - "+soluongnhan+", available = available - "+soluongnhan+" \n"+
										"where kho_fk = "+kho_fk+" and npp_fk = "+npp_fk+" and nhomkenh_fk = "+nhomkenh_fk+" and sanpham_fk = "+sanpham_fk+" \n"+
										"and khachhang_fk = "+kh_nhan_fk+" ";
								System.out.println("[nhapp_kho_kygui_chitiet] "+query);
								if(!db.update(query))
								{
									msg = "Không thể hủy ERP_HOADONNPP " + query;
									db.getConnection().rollback();
									return msg;
								}
								query = "update nhapp_kho set soluong = soluong - "+soluongnhan+", available = available - "+soluongnhan+" \n"+
										"where kho_fk = "+kho_fk+" and npp_fk = "+npp_fk+" and nhomkenh_fk = "+nhomkenh_fk+" and sanpham_fk = "+sanpham_fk+"";
								System.out.println("[nhapp_kho_kygui_chitiet] "+query);
								if(!db.update(query))
								{
									msg = "Không thể hủy ERP_HOADONNPP " + query;
									db.getConnection().rollback();
									return msg;
								}
							}
							else
							{
								msg = "Không thể hủy ERP_HOADONNPP: Tồn kho không đủ để hủy chuyển kho. ";
								db.getConnection().rollback();
								return msg;
							}
						}
						query = "update erp_chuyenkho set trangthai = 4 where pk_seq = "+ckId;
						System.out.println("[TT3, update erp_chuyenkho] "+query);
						if(!db.update(query))
						{
							msg = "Không thể hủy ERP_HOADONNPP " + query;
							db.getConnection().rollback();
							return msg;
						}
					}
				}
			}
			
			//Xóa hóa đơn, mở lại trạng thái đơn hàng --> chỉ được thực hiện khi chưa chốt phiếu giao hàng ( mở hết tất cả hóa đơn của đơn hàng này )
			query = "update ERP_HOADONNPP set trangthai = '3' where pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = '" + ddh_fk + "' ) ";
			System.out.println(query);
			if(!db.update(query))
			{
				msg = "Không thể hủy ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return msg;
			}

			if(ngaynghiepvu.trim().length() > 0)
			{
				String nam = ngaynghiepvu.substring(0, 4);
				String thang = ngaynghiepvu.substring(5, 7);
				
				System.out.println("nam:"+nam);
				System.out.println("thang:"+thang);
							
				//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
				query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
					    "from ERP_PHATSINHKETOAN " +
					    "where LOAICHUNGTU = N'Hóa đơn tài chính' and SOCHUNGTU IN ( SELECT HOADONNPP_FK FROM ERP_HOADONNPP_DDH WHERE DDH_FK = '" + ddh_fk + "') ";
				
				System.out.println(query);
				
				ResultSet rsPSKT = db.get(query);
				try 
				{
					while(rsPSKT.next())
					{
						String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
						String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
						double NO = rsPSKT.getDouble("NO");
						double CO = rsPSKT.getDouble("CO");
						double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
						
						//NEU LA CO THI BAY GIO GHI GIAM CO LAI
						if( NO > 0 )
						{
							query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
									" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
						}
						else
						{
							query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
									" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
						}
						
						System.out.println("1.REVERT NO-CO: " + query);
						
						if(db.updateReturnInt(query)<0)
						{
							msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
							db.getConnection().rollback();
							return msg;
						}
						
					}
					rsPSKT.close();
				} 
				catch (Exception e) { }
				
				//HỦY KẾ TOÁN ĐÃ GHI NHẬN
				query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Hóa đơn tài chính' and SOCHUNGTU IN ( SELECT HOADONNPP_FK FROM ERP_HOADONNPP_DDH WHERE DDH_FK = '" + ddh_fk + "') ";	
				
				System.out.println(query);
				if(!db.update(query))
				{
					msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
					db.getConnection().rollback();
					return msg;
				}	
			}
			
			//CHECK TONG VA CHI TIET
			msg = util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if( msg.length() > 0 )
			{
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
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
	    
	    HttpSession session = request.getSession();	    
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		obj.setDoituongId(session.getAttribute("doituongId"));
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
		obj.setLoaidonhang(loaidonhang);
	 
		String phanloai = request.getParameter("phanloai");
		if(phanloai == null)
			phanloai = "";
		obj.setPhanloai(phanloai);
		
		
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    	   
	    if(action.equals("Tao moi"))
	    {
	    	IErpHoadontaichinhNPP hdBean = new ErpHoadontaichinhNPP();
	    	hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
	    	hdBean.setUserId(userId);
	    	hdBean.setcongtyId((String)session.getAttribute("congtyId"));
	    	hdBean.setLoaiXHD(phanloai);
	    	hdBean.setLoaidonhang(loaidonhang);
	    	hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	hdBean.setDoituongId(session.getAttribute("doituongId"));
			
	    	hdBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("hdBean", hdBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	String sql=getSumQuery(request, obj);
		    	obj.laytien(sql);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.setUserId(userId);
		    	String sql = getSumQuery(request, obj);
		    	obj.laytien(sql);
		    	obj.init(search);		    	
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHoadontaichinhNPPList obj)
	{
		Utility util = new Utility();
		
		String query= "select distinct a.PK_SEQ, a.trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon,isnull( kb.ten,'') as kenhbanhang, NV.TEN as nguoitao, isnull(a.tongtienavat, 0) as tongtien, a.tooltip, \n" +
					  " case when a.NPP_DAT_FK is not null then npp.TEN when a.NHANVIEN_FK is not null then nvct.TEN else KH.TEN end as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua ,a.LoaiHoaDon, a.capdogiaohang \n" +
					  "from ERP_HOADONNPP a \n" +
					  "inner join ERP_HOADONNPP_SP b on a.PK_SEQ=b.HOADON_FK \n"+
					  "inner join SANPHAM sp on b.SANPHAM_FK=sp.PK_SEQ \n"+
					  "left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ  \n" +
					  util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() )+
					  "left join NHAPHANPHOI npp on a.NPP_DAT_FK=npp.PK_SEQ  \n" +
					  "left join ERP_NHANVIEN  nvct on a.nhanvien_fk = nvct.PK_SEQ \n"+
					  "inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   \n" +
					  "inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ \n" +
					  "left join kenhbanhang kb on kb.pk_seq=a.kbh_fk \n"+
					  "left join KHACHHANG_KENHBANHANG kbh on KH.pk_seq = kbh.khachhang_fk   \n" +
					  "left join diaban db on db.pk_seq = KH.diaban   \n" +
					  "where a.pk_seq > 0 AND a.LoaiHoaDon IN( 0 ,1) ";
		
		String tinhthanhId = request.getParameter("tinhthanhId");
		if(tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhId(tinhthanhId);
		
		String quanhuyenId = request.getParameter("quanhuyenId");
		if(quanhuyenId == null)
			quanhuyenId = "";
		obj.setQuanhuyenId(quanhuyenId);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
				
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);		
		
		String khten = request.getParameter("khTen");	
		if (khten == null) {
			khten ="";
		}
    	obj.setKhTen(khten);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String sodonhang = request.getParameter("sodonhang");
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
		
		String thuegtgt = request.getParameter("thuegtgt");
		if(thuegtgt == null)
			thuegtgt = "";
		obj.setThuegtgt(thuegtgt);

		String tensp = request.getParameter("tensp");
		if(tensp == null)
			tensp = "";
		obj.setTensp(tensp);
		
		String tenxuathd = request.getParameter("tenxuathd");
		if(tenxuathd == null)
			tenxuathd = "";
		obj.setTenxuatHD(tenxuathd);
		
		String giasp = request.getParameter("giasp");
		if(giasp == null)
			giasp = "";
		obj.setGiasp(giasp);
		
		String kbhid=request.getParameter("kbhid");
		if(kbhid==null)
			kbhid="";
		obj.setKbhId(kbhid);
		
		String kvid=request.getParameter("kvid");
		if(kvid==null)
			kvid="";
		obj.setKvId(kvid);
		
		String htbh=request.getParameter("htbhid");
		if(htbh==null)
			htbh="";
		obj.setHtbhId(htbh);
		
		String nguoitao = request.getParameter("nguoitao");
	    if(nguoitao == null)
	    	nguoitao = "";
	    obj.setNguoitao(nguoitao);
	    
	    String nguoisua = request.getParameter("nguoisua");
	    if(nguoisua == null)
	    	nguoisua = "";
	    obj.setNguoisua(nguoisua);
		
		if(giasp.length()>0)
		{
			query+=" and b.dongia="+giasp;
		}
		if(tensp.length()>0)
		{
			query+=" and dbo.ftBoDau( sp.ten ) like N'%" + util.replaceAEIOU(tensp) + "%'";
		}
		
		if(sohoadon.length()>0)
			query += " and a.sohoadon LIKE '%" + sohoadon + "%'";
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("3") || trangthai.equals("5") )
				query += " and a.TrangThai in (3,5) ";
			else
				query += " and a.TrangThai = '" + trangthai + "'";
		}
		
		
		String loaihoadon = request.getParameter("loaidonhang") == null?"0":request.getParameter("loaidonhang");
		obj.setLoaidonhang(loaihoadon);
		
		if(loaihoadon.length() > 0)
			query += " and a.loaihoadon = '" + loaihoadon + "'";
		
		
		if(khten.length() > 0)
		{
			//query += " and ( kh.ten like N'%" + khten + "%' or npp.ten like N'%" + khten + "%' or nvct.ten like N'%" + khten + "%' or kh.mafast = N'%"+khten+"%' or npp.ma like N'%"+khten+"%' or nv.ten like N'%"+khten+"%' ) ";
			query += " and isnull( kh.timkiem, npp.timkiem ) like N'%" + util.replaceAEIOU( khten ) + "%' ";
		}
		
		if(sodonhang.length()>0)
			query += " and a.pk_seq in ( select HOADONNPP_FK from Erp_HoaDonNPP_DDH WHERE Cast(DDH_fK as varchar(20))  LIKE '%" + sodonhang + "%' ) ";
		
		if( tenxuathd.trim().length() > 0 )
			//query += " and tenxuathd_timkiem like N'%" + util.replaceAEIOU( tenxuathd ) + "%' ";
			query += " and a.khachhang_fk in ( select khachhang_fk from KHACHHANG_THONGTINHOADON " +
					"where DONVI like N'%"+tenxuathd+"%')";
		
		if(htbh.length() > 0)
		{
		
			query += "and kbh.kbh_fk  in (select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '"+htbh+"') ";	
		}
		if(kbhid.length()>0)
		{
			query += " and kbh.kbh_fk = '"+kbhid+"'" ;
		}
		if(kvid.length()>0)
		{
			query += " and db.khuvuc_fk = '"+kvid+"'" ;
		}
		
		if(tinhthanhId.length()>0)
		{
			query += " and KH.TINHTHANH_FK = "+tinhthanhId + " ";
		}
		
		if(quanhuyenId.length() > 0)
		{
			query += " and KH.QUANHUYEN_FK = "+quanhuyenId+ " ";
		}
		
		if(nguoitao.trim().length() > 0)
		{
			query += " and a.nguoitao in ( select pk_seq from NHANVIEN where timkiem like N'%" + util.replaceAEIOU( nguoitao.trim() ) + "%' ) ";	
		}
		
		if(nguoisua.trim().length() > 0)
		{
			query += " and a.nguoisua in ( select pk_seq from NHANVIEN where timkiem like N'%" + util.replaceAEIOU( nguoisua.trim() ) + "%' ) ";	
		}
		
		System.out.print("câu tìm kiếm " + query);
		return query;
	}
		

	private String getSumQuery(HttpServletRequest request, IErpHoadontaichinhNPPList obj)
	{
		Utility util = new Utility();
		
		String query= 
					"select  distinct a.pk_seq, a.tongtienavat, a.vat  \n" +
					"from ERP_HOADONNPP a \n" +
					"left join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  \n" +
					"left join NHAPHANPHOI npp on a.NPP_DAT_FK=npp.PK_SEQ  \n" +
					"left join ERP_NHANVIEN  nvct on a.nhanvien_fk = nvct.PK_SEQ \n"+
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   \n" +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ \n" +
					"left join kenhbanhang kb on kb.pk_seq=a.kbh_fk \n"+
					"where a.pk_seq > 0 ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
				
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);		
		
		String khten = request.getParameter("khTen");	
		if( khten == null ) {
			khten = "";
		}
    	obj.setKhTen(khten);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String sodonhang = request.getParameter("sodonhang");
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
		
		String thuegtgt = request.getParameter("thuegtgt");
		if(thuegtgt == null)
			thuegtgt = "";
		obj.setThuegtgt(thuegtgt);

		String tensp = request.getParameter("tensp");
		if(tensp == null)
			tensp = "";
		obj.setTensp(tensp);
		
		String tenxuathd = request.getParameter("tenxuathd");
		if(tenxuathd == null)
			tenxuathd = "";
		obj.setTenxuatHD(tenxuathd);
			
		String giasp = request.getParameter("giasp");
		if(giasp == null)
			giasp = "";
		obj.setGiasp(giasp);

//		if(thuegtgt.length()>0)
//		{
//			query+=" and b.vat = " + thuegtgt;
//		}
		
		if(giasp.length()>0)
		{
			query+=" and b.dongia="+giasp;
		}
		if(tensp.length()>0)
		{
			query+=" and dbo.ftBoDau( sp.ten ) like N'%" + util.replaceAEIOU(tensp) + "%'";
		}
		
		if(sohoadon.length()>0)
			query += " and a.sohoadon LIKE '%" + sohoadon + "%'";
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("3") || trangthai.equals("5") )
				query += " and a.TrangThai in (3,5) ";
			else
				query += " and a.TrangThai = '" + trangthai + "'";
		}
		
		
		String loaihoadon = request.getParameter("loaidonhang") == null?"0":request.getParameter("loaidonhang");
		obj.setLoaidonhang(loaihoadon);
		
		if(loaihoadon.length() > 0)
			query += " and a.loaihoadon = '" + loaihoadon + "'";
		
		
		if(khten.length() > 0)
		{
			//query += " and ( kh.ten like N'%" + khten + "%' or npp.ten like N'%" + khten + "%' or nvct.ten like N'%" + khten + "%' or kh.mafast = N'%"+khten+"%' or npp.ma like N'%"+khten+"%' or nv.ten like N'%"+khten+"%' ) ";
			query += " and isnull( kh.timkiem, npp.timkiem ) like N'%" + util.replaceAEIOU( khten ) + "%' ";
		}
		
		if(sodonhang.length()>0)
			query += " and a.pk_seq in ( select HOADONNPP_FK from Erp_HoaDonNPP_DDH WHERE Cast(DDH_fK as varchar(20))  LIKE '%" + sodonhang + "%' ) ";
		
		String sql=	" select SUM(isnull(data.vat,0)) as vat,SUM(isnull(data.tongtienavat,0)) as tongtienavat \n" +
					"  from ( "+ query +") as data \n";
		
		if( tenxuathd.trim().length() > 0 )
			query += " and tenxuathd_timkiem like N'%" + util.replaceAEIOU( tenxuathd ) + "%' ";

		System.out.println("Câu query tien: " + sql);
		return sql;
	}
	
	public static String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	public static void main(String[] args) 
	{
		 
	}
	
	
	
	public static String CapNhat_SoHoaDon()
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select PK_SEQ, SOHOADON from ERP_HOADONNPP where SOHOADON like '0%' and Kho_FK is not null";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String hoadonId = rs.getString("PK_SEQ");
				String sohoadon = rs.getString("SOHOADON");

				while(sohoadon.startsWith("0"))
					sohoadon = sohoadon.substring(1, sohoadon.length());
				
				System.out.println("-- ID: " + hoadonId + " -- SO HOA DON OLD: " + rs.getString("SOHOADON") + " --- SO HOA DON NEW: " + sohoadon);
				query = "Update ERP_HOADONNPP set SOHOADON = '" + sohoadon + "' where pk_seq = '" + hoadonId + "' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Lỗi cập nhật: " + query;
				}
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
		}
		
		System.out.println(":::: CHAY XONG....");
		return "";
	}
	

	public static String CapNhat_SoHoaDon_KM_IMPORT()
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select pk_seq, sohoadon, REPLACE( sohoadon, '_', '' ) as sohoadonCHUAN from KHUYENMAI_IMPORT where  sohoadon like '%_%'";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String hoadonId = rs.getString("PK_SEQ");
				String sohoadon = rs.getString("sohoadonCHUAN");

				while(sohoadon.startsWith("0"))
					sohoadon = sohoadon.substring(1, sohoadon.length());
				
				System.out.println("-- ID: " + hoadonId + " -- SO HOA DON OLD: " + rs.getString("SOHOADON") + " --- SO HOA DON NEW: " + sohoadon);
				query = "Update KHUYENMAI_IMPORT set sohoadonCHUAN = '" + sohoadon + "' where pk_seq = '" + hoadonId + "' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Lỗi cập nhật: " + query;
				}
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
		}
		
		System.out.println(":::: CHAY XONG....");
		return "";
	}
	
	
	public static String CapNhat_SoLo()
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select stt, solo, soloCHUAN from ERP_DONDATHANGNPP_TEMP where solo like '_%' ";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String stt = rs.getString("stt");
				String solo = rs.getString("solo");

				while(solo.startsWith("_"))
					solo = solo.substring(1, solo.length());
				
				System.out.println("-- STT: " + stt + " -- SO LO OLD: " + rs.getString("solo") + " --- SO LO NEW: " + solo);
				query = "Update ERP_DONDATHANGNPP_TEMP set soloCHUAN = '" + solo + "' where stt = '" + stt + "' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Lỗi cập nhật: " + query;
				}
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
		}
		
		System.out.println(":::: CHAY XONG....");
		return "";
	}
	
	public static String CapNhat_HoaDonKM( String ngayIMPORT )
	{
		dbutils db = new dbutils();
		
		try 
		{
			/*String query =   "select sohoadonCHUAN, hoadon_fk, stt, soluong, masanpham, solo, " + 
							 "		( select PK_SEQ from ERP_YCXUATKHONPP where hoadon_fk = km.hoadon_fk and trangthai != 2 ) as phieugiaohang_fk, " + 
							 "		( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = km.hoadon_fk and DDH_FK in ( select pk_seq from ERP_DONDATHANGNPP where trangthai not in ( 3 ) ) ) as ddh_fk, " +
							 "		( select NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = '100000' and nhomkenh_fk = '100000' and SOLO = km.solo and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) as ngayhethan,	" +
							 "		( select ma from SANPHAM where ma_FAST = km.masanpham ) as masanphamCHUAN " + 
							 "from KHUYENMAI_IMPORT km where km.sohoadonCHUAN = '15199'  ";*/
							 //"where km.hoadon_fk is not null  " + 
							 //"	and km.hoadon_fk in ( select pk_seq from ERP_HOADONNPP )  ";
			
			String query =   "select sohoadonCHUAN, hoadon_fk, " + 
							 " 	( select PK_SEQ from ERP_YCXUATKHONPP where hoadon_fk = DT.hoadon_fk and trangthai != 2 ) as phieugiaohang_fk,   " + 
							 "			( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = DT.hoadon_fk and DDH_FK in ( select pk_seq from ERP_DONDATHANGNPP where trangthai not in ( 3 ) ) ) as ddh_fk " + 
							 "from " + 
							 "( " + 
							 "	select sohoadonCHUAN, hoadon_fk, sum(soluong) as soluong " + 
							 //"	from KHUYENMAI_IMPORT km where km.sohoadonCHUAN in ( '15223', '15224' ) " + 
							 "	from KHUYENMAI_IMPORT km where ngayIMPORT = '" + ngayIMPORT + "'   " + 
							 "	group by sohoadonCHUAN, hoadon_fk " + 
							 ") " + 
							 "DT ";
			
			System.out.println(":::: DANH SACH HOA DON KM: " + query );
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String hoadonId = rs.getString("hoadon_fk") == null ? "" : rs.getString("hoadon_fk");
					String sohoadon = rs.getString("sohoadonCHUAN");
					String phieugiaohang_fk = rs.getString("phieugiaohang_fk") == null ? "" : rs.getString("phieugiaohang_fk");
					String ddh_fk = rs.getString("ddh_fk") == null ? "" : rs.getString("ddh_fk");
					
					//String stt = rs.getString("stt");
					//int soluong = rs.getInt("soluong");
					//String masanpham = rs.getString("masanphamCHUAN");
					//String solo = rs.getString("solo");
					//String ngayhethan = rs.getString("ngayhethan") == null ? "" : rs.getString("ngayhethan");
					
					//String msg = XuLyHoaDon(db, hoadonId, stt, phieugiaohang_fk, ddh_fk, soluong, masanpham, solo, ngayhethan);
					/*if(msg.trim().length() > 0)
					{
						query = "insert KHUYENMAI_IMPORT_LOG( stt, sohoadon, hoadon_fk, phieugiaohang_fk, ddh_fk, loiIMPORT ) " +
								"values( '" + stt + "', '" + sohoadon + "', '" + hoadonId + "', '" + phieugiaohang_fk + "', '" + ddh_fk + "', N'" + msg.replaceAll("'", "''") + "' )";
						
						System.out.println(":::: LOI IMPORT: " + query );
						db.update(query);
					}*/
					
					String msg = XuLyHoaDonKM(db, sohoadon, hoadonId, phieugiaohang_fk, ddh_fk, ngayIMPORT);
					if(msg.trim().length() > 0)
					{
						query = "insert KHUYENMAI_IMPORT_LOG( sohoadon, hoadon_fk, phieugiaohang_fk, ddh_fk, loiIMPORT ) " +
								"values( '" + sohoadon + "', '" + hoadonId + "', '" + phieugiaohang_fk + "', '" + ddh_fk + "', N'" + msg.replaceAll("'", "''") + "' )";
						
						System.out.println(":::: LOI IMPORT: " + query );
						db.update(query);
					}
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println(":::: CHAY XONG....");
		return "";
	}
	

	private static String XuLyHoaDon(dbutils db, String hoadonId, String stt, String phieugiaohang_fk, String ddh_fk, int soluong, String masanpham, String solo, String ngayhethan)
	{
		if(hoadonId.trim().length() <= 0)
		{
			return "::Lỗi không tìm thấy hóa đơn trong hệ thống tại dòng " + stt;
		}
		
		if(ngayhethan.trim().length() <= 0)
		{
			return "::Lỗi xác định ngày hết hạn tại dòng " + stt;
		}
		
		String ctkmId = "100110";
		String scheme = "MRMN1504OSS2";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			if(ddh_fk.trim().length() > 0)
			{
				query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + ddh_fk + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.0.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddh_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.1.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_DONDATHANGNPP_CTKM_TRAKM( DONDATHANGID, CTKMID, TRAKMID, SOXUAT, TONGGIATRI, SPMA, SOLUONG, STT ) " + 
						" values ( '" + ddh_fk + "', '" + ctkmId + "', '100051', '1', '0', '" + masanpham + "', '" + soluong + "', '" + stt + "' )";
				System.out.println("Chen DH: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.2.LỖI CẬP NHẬT: " + query;
				}
				
				//CHEN VAO BANG CHI TIET
				query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, solo, ngayhethan, soluong, LOAI, scheme, DVDL_FK, stt ) " +
						"select '" + ddh_fk + "', pk_seq, '" + solo + "' as solo, '" + ngayhethan + "' as ngayhethan, '" + soluong + "' as soluong, 1, N'" + scheme + "' as scheme, DVDL_FK, '" + stt + "' from SANPHAM where MA = '" + masanpham + "' ";
				System.out.println("Chen DH - CHI TIET: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.3.LỖI CẬP NHẬT: " + query;
				}
			}
			else
			{
				db.getConnection().rollback();
				return ":: 2.4.LỖI CẬP NHẬT: Không thể xác định được đơn hàng của hóa đơn ";
			}
			
			//CHEN VAO BẢNG HÓA ĐƠN
			query = "delete ERP_HOADONNPP_SP where hoadon_fk = '" + hoadonId + "' and ltrim(rtrim(scheme)) != '' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.0.LỖI CẬP NHẬT: " + query;
			}
			
			query = "delete ERP_HOADONNPP_SP_CHITIET where hoadon_fk = '" + hoadonId + "' and ltrim(rtrim(scheme)) != '' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.1.LỖI CẬP NHẬT: " + query;
			}
			
			query =  "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  " + 
					 "	select " + hoadonId + ", b.PK_SEQ, b.TEN, DV.donvi, ISNULL( SUM(a.soluong), 0) as soluong, ISNULL( SUM(a.soluong), 0) as souongCHUAN, 0 as dongia, SUM(a.TONGGIATRI) as thanhtien, 0 as chietkhau, d.SCHEME as scheme, 0 as vat    " + 
					 "	from ERP_DONDATHANGNPP_CTKM_TRAKM a left Join SanPham b on a.SPMA = b.MA   	  " + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK   " + 
					 "		INNER join  ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq  " + 
					 "		INNER join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ    " + 
					 "	where a.DONDATHANGID in ( " + ddh_fk + " )  and a.soluong > 0 " + 
					 "	group by b.PK_SEQ , b.TEN, DV.donvi, scheme ";
			System.out.println("Chen HD-SP: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.2.LỖI CẬP NHẬT: " + query;
			}

			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME, STT)    " + 
					 "	select '" + hoadonId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, c.TEN, d.DONVI, d.pk_seq as dvCHUAN, d.PK_SEQ  as dvDATHANG,      " + 
					 "		soluong, b.solo, b.NGAYHETHAN, 0 chietkhau, 0 thuevat,  0 as dongia,     " + 
					 "		soluong as SoLuong_Chuan,     " + 
					 "		0 as DonGia_Chuan ,     " + 
					 "		soluong SoLuong_DatHang, b.scheme, '" + stt + "'     " + 
					 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     " + 
					 "		 inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  						     " + 
					 "		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     " + 
					 "	where a.PK_SEQ = '" + ddh_fk + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) != '' and a.TRANGTHAI != '3' ";
			System.out.println("Chen HD-SP CT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.3.LỖI CẬP NHẬT: " + query;
			}
			
			//CHEN BANG PHIEU GIAO HANG
			if(phieugiaohang_fk.trim().length() > 0 )
			{
				query = "delete ERP_YCXUATKHONPP_SANPHAM where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.0.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_YCXUATKHONPP_SANPHAM_THUCGIAO where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.1.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.2.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.3.LỖI CẬP NHẬT: " + query;
				}
				
				///
				query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, STT ) " +
						"select '" + phieugiaohang_fk + "', b.SANPHAM_FK, b.SOLUONG, 0, 0, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme , '" + stt + "'  " +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  " ;
				System.out.println("Chen PGH-SP: " + query);
				if(!db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.4.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO( ycxk_fk, sanpham_fk, soluong, LOAI, SCHEME, STT ) " +
						"select '" + phieugiaohang_fk + "', b.SANPHAM_FK, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '" + stt + "'   " +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  " ;

				if( !db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.5.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia, STT ) " +
						"select '" + phieugiaohang_fk + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG, " + 
						" 	case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia, '" + stt + "' " +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
						"		inner join SANPHAM c on b.MA = c.MA  " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  ";
				System.out.println("Chen PGH-SP CT: " + query);
				if(!db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.6.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia, STT ) " +
						"select '" + phieugiaohang_fk + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG, " + 
						" case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia, '" + stt + "' " +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
						"		inner join SANPHAM c on b.MA = c.MA  " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  ";
				if( !db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.7.LỖI CẬP NHẬT: " + query;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return "Lỗi import: " + e.getMessage();
		}
		
		return "";
		
	}
	
	
	private static String XuLyHoaDonKM(dbutils db, String sohoadon, String hoadonId, String phieugiaohang_fk, String ddh_fk, String ngayIMPORT)
	{
		String ctkmId = "100110";
		String scheme = "MRMN1504OSS2";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			if(ddh_fk.trim().length() > 0)
			{
				query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + ddh_fk + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.0.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddh_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.1.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_DONDATHANGNPP_CTKM_TRAKM( DONDATHANGID, CTKMID, TRAKMID, SOXUAT, TONGGIATRI, SPMA, SOLUONG ) " + 
						"select '" + ddh_fk + "', '" + ctkmId + "', '100051', '1', '0', ( select ma from SANPHAM where ma_fast = km.masanpham ) as masanpham, sum(soluong) as soluong " +
						"from KHUYENMAI_IMPORT km where km.sohoadonCHUAN = '" + sohoadon + "' and km.ngayIMPORT = '" + ngayIMPORT + "' " +
						"group by km.masanpham " ;
				
				System.out.println("Chen DH: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.2.LỖI CẬP NHẬT: " + query;
				}
				
				//CHEN VAO BANG CHI TIET
				query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, solo, ngayhethan, soluong, LOAI, scheme, DVDL_FK, stt ) " +
						 "select '" + ddh_fk + "', sp.pk_seq, solo, ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = '100000' and nhomkenh_fk = '100000' and SOLO = km.solo and SANPHAM_FK = sp.PK_SEQ ) as ngayhethan,  " + 
						 "		 sum(soluong) as soluong, 1 as loai, '" + scheme + "' as scheme, sp.DVDL_FK, km.stt " + 
						 "from KHUYENMAI_IMPORT km inner join SANPHAM sp on km.masanpham = sp.MA_FAST " + 
						 "where km.sohoadonCHUAN = '" + sohoadon + "' and km.ngayIMPORT = '" + ngayIMPORT + "' " + 
						 "group by km.masanpham, solo, sp.PK_SEQ, sp.DVDL_FK, km.stt ";
						
				System.out.println("Chen DH - CHI TIET: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 2.3.LỖI CẬP NHẬT: " + query;
				}
			}
			else
			{
				db.getConnection().rollback();
				return ":: 2.4.LỖI CẬP NHẬT: Không thể xác định được đơn hàng của hóa đơn ";
			}
			
			//CHEN VAO BẢNG HÓA ĐƠN
			query = "delete ERP_HOADONNPP_SP where hoadon_fk = '" + hoadonId + "' and ltrim(rtrim(scheme)) != '' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.0.LỖI CẬP NHẬT: " + query;
			}
			
			query = "delete ERP_HOADONNPP_SP_CHITIET where hoadon_fk = '" + hoadonId + "' and ltrim(rtrim(scheme)) != '' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.1.LỖI CẬP NHẬT: " + query;
			}
			
			//Xử lý trường hợp 2 SP cùng 1 hoa đơn
			query =  "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  " + 
					 "	select " + hoadonId + ", b.PK_SEQ, b.TEN, DV.donvi, ISNULL( SUM(a.soluong), 0) as soluong, ISNULL( SUM(a.soluong), 0) as souongCHUAN, 0 as dongia, SUM(a.TONGGIATRI) as thanhtien, 0 as chietkhau, d.SCHEME as scheme, b.thuexuat as vat    " + 
					 "	from ERP_DONDATHANGNPP_CTKM_TRAKM a left Join SanPham b on a.SPMA = b.MA   	  " + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK   " + 
					 "		INNER join  ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq  " + 
					 "		INNER join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ    " + 
					 "	where a.DONDATHANGID in ( " + ddh_fk + " )  and a.soluong > 0 " + 
					 "	group by b.PK_SEQ , b.TEN, DV.donvi, scheme, b.thuexuat ";
			System.out.println("Chen HD-SP: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.2.LỖI CẬP NHẬT: " + query;
			}

			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME, STT)    " + 
					 "	select '" + hoadonId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, c.TEN, d.DONVI, d.pk_seq as dvCHUAN, d.PK_SEQ  as dvDATHANG,      " + 
					 "		soluong, b.solo, b.NGAYHETHAN, 0 chietkhau, c.thuexuat,  0 as dongia,     " + 
					 "		soluong as SoLuong_Chuan,     " + 
					 "		0 as DonGia_Chuan ,     " + 
					 "		soluong SoLuong_DatHang, b.scheme, b.stt     " + 
					 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     " + 
					 "		 inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  						     " + 
					 "		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     " + 
					 "	where a.PK_SEQ = '" + ddh_fk + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) != '' and a.TRANGTHAI != '3' ";
			System.out.println("Chen HD-SP CT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return ":: 3.3.LỖI CẬP NHẬT: " + query;
			}
			
			//CHEN BANG PHIEU GIAO HANG
			if(phieugiaohang_fk.trim().length() > 0 )
			{
				query = "delete ERP_YCXUATKHONPP_SANPHAM where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.0.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_YCXUATKHONPP_SANPHAM_THUCGIAO where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.1.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.2.LỖI CẬP NHẬT: " + query;
				}
				
				query = "delete ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET where ycxk_fk = '" + phieugiaohang_fk + "' and ltrim(rtrim(scheme)) != '' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return ":: 4.3.LỖI CẬP NHẬT: " + query;
				}
				
				///
				query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, STT, DVDL_FK, soluongCHUAN ) " +
						"select '" + phieugiaohang_fk + "', b.SANPHAM_FK, b.SOLUONG, 0, 0, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme , b.stt,   " +
						"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  " ;
				System.out.println("Chen PGH-SP: " + query);
				if(!db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.4.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO( ycxk_fk, sanpham_fk, soluong, LOAI, SCHEME, STT, DVDL_FK, soluongCHUAN ) " +
						"select '" + phieugiaohang_fk + "', b.SANPHAM_FK, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, b.stt,   " +
						"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  " ;

				if( !db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.5.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia, STT ) " +
						"select '" + phieugiaohang_fk + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
						" 	case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia, b.stt " +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
						"		inner join SANPHAM c on b.MA = c.MA  " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  ";
				System.out.println("Chen PGH-SP CT: " + query);
				if(!db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.6.LỖI CẬP NHẬT: " + query;
				}
				
				query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia, STT ) " +
						"select '" + phieugiaohang_fk + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
						" case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia, b.stt " +
						"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
						"		inner join SANPHAM c on b.MA = c.MA  " +
						"where a.PK_SEQ = '" + hoadonId + "' and ltrim(rtrim(b.scheme)) != ''  ";
				if( !db.update(query) )
				{
					db.getConnection().rollback();
					return ":: 4.7.LỖI CẬP NHẬT: " + query;
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return "Lỗi import: " + e.getMessage();
		}
		
		return "";
		
	}
	
	public static String CapNhat_HoaDonIMPORT()
	{
		dbutils db = new dbutils();
		
		try 
		{
			//112847 , 112863
			String query =   "select PK_SEQ, machungtu, TRANGTHAI, dh.machungtu, dh.kho_fk, dh.khachhangKG_FK,  " + 
							 "	    ( select COUNT(*) from ERP_HOADONNPP where TRANGTHAI not in ( 3, 5 ) and NPP_FK = '106313' and NGAYXUATHD >= '2015-07-01'  " + 
							 "			and PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = dh.pk_seq ) ) as dacoHOADON,  " + 
							 "		( select COUNT(*) from ERP_YCXUATKHONPP where ddh_fk = dh.PK_SEQ and TRANGTHAI != 2 ) as dacoPGH  " + 
							 "from ERP_DONDATHANGNPP dh where machungtu is not null    ";
			
			//query += " and dh.pk_seq not in ( 124237 ) ";
			
			System.out.println(":::: DANH SACH DON HANG IMPORT: " + query );
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ddhId = rs.getString("PK_SEQ");
					String machungtu = rs.getString("machungtu");
					int dacoHOADON = rs.getInt("dacoHOADON");
					//int dacoPGH = rs.getInt("dacoPGH");
					
					String kho_fk = rs.getString("kho_fk");
					String khachhangKG_FK = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
					
					if(dacoHOADON <= 0 ) //Chưa có hóa đơn, cạp nhật lại giá trong đơn hàng, xử lý hàng KM, tạo hóa đơn, phiếu giao hàng tự động
					{
						String msg = XuLy_Import_ChuaCoHoaDon( db, ddhId, machungtu, kho_fk, khachhangKG_FK );
						if(msg.trim().length() > 0)
						{
							query = "Insert ERP_DONDATHANGNPP_LOG ( ddh_fk, msg ) " + 
									" values ( '" + ddhId + "', N'" + msg.replaceAll("'", "''") + "' ) ";
							
							System.out.println("::::: LOI IMPORT: " + query );
							db.update(query);
						}
					}
					
					//Có hóa đơn rồi thì không xử lý, tự xử lý bằng tay
					
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println(":::: CHAY XONG....");
		return "";
	}
	
	

	private static String XuLy_Import_ChuaCoHoaDon(dbutils db, String ddhId, String machungtu, String kho_fk, String khachhangKG_FK) 
	{
		String msg = "";
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Cập nhật lại đơn giá theo file IMPORT
			/*query =  "update a set a.thueVAT = b.thueVAT,  " + 
					 "			 a.dongiaGOC = b.dongiaTRUOCCK, " + 
					 "			 a.dongia = b.dongiaSAUCK, " + 
					 "			 a.chietkhau = b.chietkhau " + 
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP_TEMP b on a.STT = b.stt " + 
					 "where a.dondathang_fk = '" + ddhId + "' " ;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 1.Lỗi import: " + query;
				return msg;
			}*/
			
			//TIM NHUNG MA CHUNG TU CHUA CO NGAY HET HAN
			if(!kho_fk.equals("100003"))
			{
				query = "select km.soloCHUAN,  " + 
						 "	   ISNULL(	( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), '') as ngayhethan	 " + 
						 "from ERP_DONDATHANGNPP_TEMP km   " + 
						 "where km.machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = '" + ddhId + "' ) " + 
						 "		AND ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) IS NULL " ;
			}
			else
			{
				query = "select km.soloCHUAN,  " + 
						 "	   ISNULL(	( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), '') as ngayhethan	 " + 
						 "from ERP_DONDATHANGNPP_TEMP km   " + 
						 "where km.machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = '" + ddhId + "' ) " + 
						 "		AND ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) IS NULL " ;
			}
			
			System.out.println(":::: CHECK SOLO : KHO: " + kho_fk + " - SQL: " + query );
			ResultSet rsCHECK = db.get(query);
			String soloKHONGDUNG = "";
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					soloKHONGDUNG += rsCHECK.getString("soloCHUAN") +  ",";
				}
				rsCHECK.close();
			}
			
			if(soloKHONGDUNG.trim().length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 2.Lỗi import: các lô sau không tìm thấy trong hệ thống: " + soloKHONGDUNG;
				return msg;
			}
				
			//Chèn lô vào đơn hàng chi tiết
			if(!kho_fk.equals("100003"))
			{
				query =  "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import ) " + 
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,   " + 
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan, " + 
						 "		case when km.dongiaTRUOCCK > 0 then 0 else 1 end as loai, " + 
						 "		case when km.dongiaTRUOCCK > 0 then '' else 'MRMN1504OSS2' end as scheme, km.sohoadonCHUAN " + 
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu " + 
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST " + 
						 "where dh.PK_SEQ = '" + ddhId + "' ";
			}
			else
			{
				query =  "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import ) " + 
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,   " + 
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan, " + 
						 "		case when km.dongiaTRUOCCK > 0 then 0 else 1 end as loai, " + 
						 "		case when km.dongiaTRUOCCK > 0 then '' else 'MRMN1504OSS2' end as scheme, km.sohoadonCHUAN " + 
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu " + 
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST " + 
						 "where dh.PK_SEQ = '" + ddhId + "' ";
			}
			
			System.out.println(":::: CHEN DON HANG CHI TIET: " + query );
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 3.Lỗi import: " + query;
				return msg;
			}
			
			query = " Update ERP_DondathangNPP set TUTAO_HOADON_PGH = '1', trangthai = '4', " + 
					" CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '100002', " + 
					" SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '100002' " +
					" where pk_seq = '" + ddhId + "'   ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 4.Lỗi import: " + query;
				return msg;
			}
			
			//CHECK XEM CÓ BAO NHIÊU SỐ HÓA ĐƠN
			query = " select distinct sohoadon_import " + 
					" from ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddhId + "' and sohoadon_import is not null ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String sohoadon = rs.getString("sohoadon_import");
					
					msg = TaoHoaDonTaiChinhNPP( db, ddhId, sohoadon, "100002", "106313", "100001" );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
					
					msg = createPHIEUGIAOHANG( db, ddhId, sohoadon, "100002" );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return e.getMessage();
		}
		
		return "";
	}


	private static String TaoHoaDonTaiChinhNPP(dbutils db, String id, String sohoadon, String userId, String nppId, String congtyId) 
	{
		String msg1 = "";
		try
		{
		 	String query = "";
		 	
			String kyhieuhoadon = "PG/15P";			
			String mau = "1";
			
			double tienck = 0;
			double tienbvat = 0;
			double tienavat = 0;
			String nguoimua = "";
				
			query = " select (case when dh.khachhang_fk is not null then " +
					"                               (select isnull(chucuahieu,'') from KHACHHANG where pk_seq = dh.khachhang_fk ) " +
					"             else '' end ) as nguoimua  ," +
					"        dh_sp.chietkhau, dh_sp.bvat , (dh_sp.bvat + dh.Vat) as AVAT "+
					" from ERP_DONDATHANGNPP dh inner join  "+
					"	( select a.dondathang_fk, SUM(a.chietkhau)as chietkhau , sum(a.soluong * a.dongia - a.chietkhau) as bvat "+
					"	  from ERP_DONDATHANGNPP_SANPHAM a where a.sohoadon_import = '" + sohoadon + "'  "+
					"	  group by  a.dondathang_fk  ) dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					" where dh.PK_SEQ = "+ id +"   ";
			System.out.println(":::: INIT HOA DON: " + query );
			ResultSet rsLayTien = db.get(query);
			{
				while(rsLayTien.next())
				{
					tienck = rsLayTien.getDouble("chietkhau");
					tienbvat = rsLayTien.getDouble("bvat");
					tienavat = rsLayTien.getDouble("avat");
					nguoimua =  rsLayTien.getString("nguoimua");
					
				}
				rsLayTien.close();
			}
		
			 query =   " insert ERP_HOADONNPP(LOAIHOADON, isKM, DDKD_FK, KBH_fK, KHO_FK,nguoimua ,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt , \n" +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN, KHACHHANGKG_FK ) \n" +
				       " SELECT 0 as LOAIHOADON, DH.isKM, DH.ddkd_Fk, DH.kbh_Fk, DH.kho_fk, N'" + nguoimua + "', DH.ngaydonhang, '2' as trangthai, '2015-07-20', '" + userId + "', '2015-07-20', '" + userId + "', '" + kyhieuhoadon + "', \n" +
					   //"       ( select distinct sohoadonCHUAN from ERP_DONDATHANGNPP_TEMP where machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = DH.pk_seq ) ) as sohoadon, N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"', \n" +
					   "       '" + sohoadon + "' as sohoadon, N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"', \n" +
					   "       ( select top(1) thueVAT from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = DH.pk_seq ), N'Phiếu xuất hóa đơn tự động từ đơn hàng IP " + id + " ', DH.loaidonhang, '" + nppId + "', DH.khachhang_fk, DH.npp_dat_fk, DH.nhanvien_fk, " + mau + " \n" +
					   " 		, KH.TEN as tenkh \n" +
					   " 		, ISNULL(KH.DIACHI,'') as diachikh \n" +
					   " 		, ISNULL(KH.MASOTHUE,'')  as mst, " +
					   "			case when ((select COUNT(*) from KHACHHANG WHERE PK_SEQ = KH.PK_SEQ and TENXUATHD like '%,%' ) = 0) AND LEN(isnull(TENXUATHD,'')) = 0 then KH.TEN \n"+  
					   "			when ((select COUNT(*) from KHACHHANG WHERE PK_SEQ = KH.PK_SEQ and TENXUATHD like '%,%' ) = 0) AND LEN(isnull(TENXUATHD,'')) > 0 then \n"+ 
					   "			TENXUATHD else SUBSTRING(TENXUATHD,1, CHARINDEX(',',TENXUATHD) - 1 ) end , '" + congtyId + "', KH.PK_SEQ, isnull(KH.GHICHU, '') GHICHU2, DH.ngaydonhang, DH.KHACHHANGKG_FK \n"+
					   " FROM Erp_DonDatHangNPP DH left join KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ \n" +
					   " WHERE DH.PK_SEQ = '"+ id +"' ";
								 
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		
			
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( "+ hdId +",  " + id + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME)    " + 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,      " + 
					 "		b.soluong,  b.solo, b.NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat,     " + 
					 "		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdId + "' and sanpham_fk = b.sanpham_fk and ltrim(rtrim(scheme)) = '' ) as dongia,     " + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then b.soluong         " + 
					 "		else b.soluong /      " + 
					 "		( select SOLUONG2 / SOLUONG1     " + 
					 "				from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as SoLuong_Chuan,     " + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA        " + 
					 "		else dhsp.DONGIA /      " + 
					 "		( select SOLUONG2 / SOLUONG1    " + 
					 "		from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,     " + 
					 "		dhsp.soluong as SoLuong_DatHang, b.scheme     " + 
					 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     " + 
					 "		 left join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = a.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk	and dhsp.sohoadon_import = b.sohoadon_import     " + 
					 "		 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						     " + 
					 "		 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     " + 
					 "	where a.PK_SEQ = '" + id + "' and b.sohoadon_import = '" + sohoadon + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) = '' and a.TRANGTHAI != '3'  " + 
					 "union ALL " + 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, c.TEN, d.DONVI, d.pk_seq as dvCHUAN, d.PK_SEQ  as dvDATHANG,      " + 
					 "		soluong, b.solo, b.NGAYHETHAN, 0 chietkhau, 0 thuevat,  0 as dongia,     " + 
					 "		soluong as SoLuong_Chuan,     " + 
					 "		0 as DonGia_Chuan ,     " + 
					 "		soluong SoLuong_DatHang, b.scheme     " + 
					 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     " + 
					 "		 inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  						     " + 
					 "		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     " + 
					 "	where a.PK_SEQ = '" + id + "' and b.sohoadon_import = '" + sohoadon + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) != '' and a.TRANGTHAI != '3' ";
			
			System.out.println("1.0.Insert ERP_HOADONNPP_SP_CHITIET: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}
			
			query =  "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  " + 
					 "select DATA.hoadonId, DATA.PK_SEQ as spId, DATA.sanphamTEN, DATA.donvi, SUM(DATA.soluong) as soluong, SUM(DATA.soluong_chuan) as soluongCHUAN,  " + 
					 "	 DATA.dongia, SUM(DATA.soluong) * DATA.dongia as thanhtien, SUM( chietkhau ) as chietkhau, scheme, DATA.vat   " + 
					 "from " + 
					 "( " + 
					 "	select  '" + hdId + "'  as hoadonId, b.PK_SEQ, a.sanphamTEN, DV.donvi, a.soluong, " + 
					 "		case when b.DVDL_FK = a.dvdl_fk then a.soluong           " + 
					 " 			else a.soluong /        " + 
					 " 			( select SOLUONG2 / SOLUONG1       " + 
					 " 					from QUYCACH where sanpham_fk = b.pk_seq and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) end  as SoLuong_Chuan,  " + 
					 "		a.dongia, isnull(a.chietkhau, 0) as chietkhau,  " + 
					 "		isnull(scheme, ' ') as scheme, isnull(a.thuevat,0) as vat      " + 
					 "	from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   	    " + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK     " + 
					 "		inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq       " + 
					 "	where a.dondathang_fk in (  " + id + "  ) and a.sohoadon_import = '" + sohoadon + "' and a.soluong > 0   " + 
					 ") " + 
					 "DATA " + 
					 "group by DATA.hoadonId, DATA.PK_SEQ, DATA.sanphamTEN, DATA.donvi, DATA.dongia , scheme, DATA.vat " +
					 "union ALL " + 
					 "	select " + hdId + ", b.PK_SEQ, b.TEN, DV.donvi, ISNULL( SUM(a.soluong), 0) as soluong, ISNULL( SUM(a.soluong), 0) as souongCHUAN, 0 as dongia, SUM(a.TONGGIATRI) as thanhtien, 0 as chietkhau, d.SCHEME as scheme, 0 as vat    " + 
					 "	from ERP_DONDATHANGNPP_CTKM_TRAKM a left Join SanPham b on a.SPMA = b.MA   	  " + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK   " + 
					 "		INNER join  ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq  " + 
					 "		INNER join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ    " + 
					 "	where a.DONDATHANGID in ( " + id + " ) and a.sohoadon_import = '" + sohoadon + "' and a.soluong > 0 " + 
					 "	group by b.PK_SEQ , b.TEN, DV.donvi, scheme ";
			
			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
			
			//LUU VAO BANG CHI TIET
			
			//CAP NHAT LAI CAC COT TIEN CUA ETC, SAU NAY IN RA THI CHI IN TU DAY
			query = "update hd set hd.tongtienbvat = giatri.bVAT, hd.chietkhau = giatri.chietkhau,   " +
					"			hd.vat = giatri.vat, hd.tongtienavat = giatri.avat  " +
					"from ERP_HOADONNPP hd inner join  " +
					"(  " +
					"	select hoadonnpp_fk, sum(bVAT) as bVAT, sum(chietkhau) as chietkhau, sum(VAT) as VAT, " +
					"			 sum(bVAT) - sum(chietkhau) + sum(VAT) as aVAT  " +
					"	from  " +
					"	(  " +
					"		select a.hoadonnpp_fk,   " +
					"			cast( (b.soluong * b.dongia) as numeric(18, 0) ) as bVAT, isnull(b.chietkhau, 0) as chietkhau,    " +
					"			cast ( (	cast( (b.soluong * b.dongia - isnull(b.chietkhau, 0) ) as numeric(18, 0) ) * thueVAT / 100 ) as numeric(18, 0) ) as VAT " +
					"		from ERP_HOADONNPP_DDH a inner join ERP_DONDATHANGNPP_SANPHAM b on a.ddh_fk = b.dondathang_fk  " +
					"				inner join ERP_DONDATHANGNPP c on a.ddh_fk = c.pk_seq  " +
					"		where a.ddh_fk = '" + id + "'  " +
					"	)  " +
					"	etc group by hoadonnpp_fk  " +
					")  " +
					"giatri on hd.pk_seq = giatri.hoadonnpp_fk  ";
			if( !db.update(query) )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			/*query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg1 = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				return msg1;
			}*/
			
			//TỰ ĐỘNG CẬP NHẬT LẠI BẢNG ERP_DONDATHANGNPP_SANPHAM_CHITIET
			query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET HOADON_FK = " + hdId + " WHERE DONDAThang_fk = " + id;
			System.out.println(query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cập nhật ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
				return msg1;
			}
		} 
		catch (Exception e) 
		{
			msg1 = "Lỗi tạo mới hóa đơn: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}


	private static String createPHIEUGIAOHANG(dbutils db, String ddhId, String sohoadon, String userId) 
	{
		String msg = "";
		String query = "";
		
		try
		{
			query = " select a.DDH_FK, b.SOHOADON, b.npp_fk, b.Kho_FK, b.pk_seq " + 
					" from ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ " +
					" where a.DDH_FK = '" + ddhId + "' and b.sohoadon = '" + sohoadon + "' ";
			ResultSet rs = db.get(query);
			String hdId = "";
			String nppId = "";
			String khoId = "";
			if(rs.next())
			{
				hdId = rs.getString("pk_seq");
				nppId = rs.getString("npp_fk");
				khoId = rs.getString("Kho_FK");
			}
			rs.close();
			
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, ngaytao, nguoitao, ngaysua, nguoisua, hoadon_fk, sohoadon, ddh_fk, loaidonhang, KHACHHANGKG_FK, NHANVIEN_FK) " +
							" select dh.ngaydonhang, N'Phiếu giao hàng tự động từ duyệt hóa đơn " + hdId + "', '1' as trangthai, '" + nppId + "', " + khoId + ", " +
							" loaidonhang as xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + hdId + "', '" + sohoadon + "', '" + ddhId + "', loaidonhang, KHACHHANGKG_FK, NHANVIEN_FK " +
					" from ERP_DONDATHANGNPP dh where pk_seq = '" + ddhId + "' ";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select '" + ycxkId + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + ddhId + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, DVDL_FK, soluongCHUAN ) " +
					"select '" + ycxkId + "', b.SANPHAM_FK, b.SOLUONG, 0, 0, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme,   " +
					"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
					"where a.PK_SEQ = '" + hdId + "' " ;
			
			System.out.println("1.1.Insert HD - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO( ycxk_fk, sanpham_fk, soluong, LOAI, SCHEME, DVDL_FK, soluongCHUAN ) " +
					"select '" + ycxkId + "', b.SANPHAM_FK, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme,   " +
					"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
					"where a.PK_SEQ = '" + hdId + "' " ;
			
			System.out.println("1.2.Insert HD - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia ) " +
					"select '" + ycxkId + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
					" 	case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia " +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
					"		inner join SANPHAM c on b.MA = c.MA  " +
					"where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("1.3.Insert HD - SP CT: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia ) " +
					"select '" + ycxkId + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
					" case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia " +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
					"		inner join SANPHAM c on b.MA = c.MA  " +
					"where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("1.4.Insert HD - SP CT: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "Lỗi khi tạo phiếu giao hàng: " + ex.getMessage();
		}
		
		return msg;
	}
	
	
}
