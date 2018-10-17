package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpYeucauxuatkhoNpp;
import geso.traphaco.distributor.beans.xuatkho.IErpYeucauxuatkhoNppList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpYeucauxuatkhoNpp;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpYeucauxuatkhoNppList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;

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

public class ErpPhieugiaohangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieugiaohangNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpYeucauxuatkhoNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
    	String Id = util.getId(querystring);
	    obj = new ErpYeucauxuatkhoNppList();
	    obj.setCtyId(ctyId);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    /*String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String phanloai = request.getParameter("loai");
	    if(phanloai == null)
	    	phanloai = "0";
	    obj.setPhanloai(phanloai);*/
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    //obj.setNppId(nppId);
	    System.out.println("---NPP ID: " + nppId);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(Id, userId, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(Id, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("mochot"))
    	{
    		String msg = this.MoChot(Id, userId, nppId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuGiaoHangNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Delete(String Id, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			/*query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + Id + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + nppId + "' and CT.NgayHetHan=kho.NgayHetHan ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + Id + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + nppId + "'  ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}*/
			
			query = "delete ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + Id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP_SANPHAM where ycxk_fk = '" + Id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk = '" + Id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP  where pk_seq = '" + Id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP " + query;
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
	
	private String Chot(String Id, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			/*String query = "select khachhangKG_FK from ERP_DondathangNPP where PK_SEQ = ( select ddh_fk from ERP_YCXUATKHONPP where pk_seq = '" + Id + "' ) ";
			String khachhangKG_FK = "";
			ResultSet rsKG = db.get(query);
			if(rsKG != null)
			{
				if(rsKG.next())
				{
					khachhangKG_FK = rsKG.getString("khachhangKG_FK") == null ? "" :  rsKG.getString("khachhangKG_FK");
				}
				rsKG.close();
			}*/
			
			//CAP NHAT KHO LUC NAY, LUC XAC NHAN XUAT KHO MOI TRU -> KHO XUẤT ĐÃ TRỪ KHI LÀM LỆNH XUẤT HÀNG RỒI
			/*if(khachhangKG_FK.trim().length() <= 0 )
			{
				query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
						" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.solo, b.ngayhethan, SUM(b.soluong) as tongxuat  " +
						" 	from ERP_YCXUATKHONPP a " +
						"	inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
						" 	where ycxk_fk = '" + Id + "' " +
						" 	group by a.kho_fk, a.nhomkenh_fk, b.solo, b.ngayhethan, b.sanpham_fk " +
						" ) " +
						" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
						" and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO " +
						" and CT.ngayhethan = kho.ngayhethan and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "' ";
			}
			else
			{
				query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
						" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.solo, b.ngayhethan, SUM(b.soluong) as tongxuat  " +
						" 	from ERP_YCXUATKHONPP a " +
						"	inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
						" 	where ycxk_fk = '" + Id + "' " +
						" 	group by a.kho_fk, a.nhomkenh_fk, b.solo, b.ngayhethan, b.sanpham_fk " +
						" ) " +
						" CT inner join NHAPP_KHO_KYGUI_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
						" and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO " +
						" and CT.ngayhethan = kho.ngayhethan and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "' and kho.KHACHHANG_FK = '" + khachhangKG_FK + "' and kho.isNPP = '0' ";
			}
			System.out.println("---1 CAP NHAT KHO: " + query);
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			if(khachhangKG_FK.trim().length() <= 0 )
			{
				query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
						" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
						" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
						" 	where ycxk_fk = '" + Id + "' " +
						" 	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk " +
						" ) " +
						" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
						" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "'  ";
			}
			else
			{
				query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
						" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
						" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
						" 	where ycxk_fk = '" + Id + "' " +
						" 	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk " +
						" ) " +
						" CT inner join NHAPP_KHO_KYGUI kho on CT.kho_fk = kho.KHO_FK  " +
						" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "' and kho.khachhang_fk = '" + khachhangKG_FK + "'  and kho.isNPP = '0' ";
			}
			System.out.println("---2 CAP NHAT KHO: " + query);
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}*/
			
// BUT TOAN KE TOAN
			
			Utility_Kho util_kho=new Utility_Kho();
			query = "SELECT YC.NGAYYEUCAU, YC_CT.SANPHAM_FK, SUM(YC_CT.SOLUONG) AS TONGXUAT,YC_CT.SOLO , " +

					"CASE WHEN CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) > 1 THEN " + // TRƯỜNG HỢP KHÔNG LÀ THÁNG 1, THÌ LẤY THÁNG - 1
					"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG " +
					"	          WHERE THANG = (CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) - 1) AND NAM = SUBSTRING(NGAYYEUCAU, 1, 4) " +
					"	          AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 ) " +
					"ELSE " +
					"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG " +
					"			  WHERE THANG = 12 and NAM = (SUBSTRING(NGAYYEUCAU, 1, 4) - 1) " +
					"			  AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 ) " +
					"END AS DONGIA, " + 
			
			 		"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		"'XK01' " +
			 		"ELSE " +
			 		"'XK02' END AS MAXUATKHO, " +
			 		
			 		"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' " +
					"	AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +
					
					"ELSE " +  // ĐƠN HÀNG KHUYẾN MẠI
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' " +
					"	AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +

					"END AS TAIKHOANNO_FK,  " + 
					
					" (	SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN (" +
					"		SELECT TOP 1 LSP.TAIKHOANKT_FK  " +
			 		"		FROM SANPHAM SP " +
					"		INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
					"		WHERE SP.PK_SEQ = YC_CT.SANPHAM_FK " +
					"   ) AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') " +
					" ) AS TAIKHOANCO_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" '100002' " +
			 		" ELSE " +
			 		" '100008' END AS NOIDUNGXUAT_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" N'Xuất bán hàng (theo đơn hàng bán) - Kho không ký gửi' " +
			 		" ELSE " +
			 		" N'Xuất khuyến mại - Kho không ký gửi' END AS KHOANMUC ," +
			 		" (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') as congty_fk " +	
			 		" FROM ERP_YCXUATKHONPP YC " +
					" INNER JOIN ERP_YCXUATKHONPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.YCXK_FK " + 
					" INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = YC.HOADON_FK " +
					" WHERE YCXK_FK = '" + Id + "' " +
					" GROUP BY NGAYYEUCAU, YC_CT.SANPHAM_FK, YC_CT.KHO_FK, YC.NHOMKENH_FK, YC_CT.SCHEME,YC_CT.SOLO ";
			
			System.out.println("Định khoản: " + query);
			ResultSet rs = db.get(query);
			int dem=0;
			while(rs.next()){
				dem++;
				String ngaychungtu = rs.getString("NGAYYEUCAU");
				String nam = ngaychungtu.substring(0, 4);
				String thang = "" + (Integer.parseInt(ngaychungtu.substring(5, 7)));
				String ngayghinhan = ngaychungtu;
				String loaichungtu = rs.getString("MAXUATKHO");
				String sochungtu = Id;
				String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
				String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
				String NOIDUNGXUAT_FK = rs.getString("NOIDUNGXUAT_FK");
				
				String congty_fk= rs.getString("congty_fk");
				
				String  SANPHAM_FK= rs.getString("SANPHAM_FK");
				String solo=rs.getString("SOLO");
				double dongia_capnhat=0;
				String str[]=util_kho.getGiaChayKT(ngaychungtu, db,congty_fk , nppId, SANPHAM_FK, solo);
				if(str[1].length() >0){

					msg = "Không thể cập nhật lỗi :  " + str[1];
					db.getConnection().rollback();
					return msg;
				}else{
					dongia_capnhat=Double.parseDouble(str[0]);
				}
				 
				String NO = "" + (rs.getDouble("TONGXUAT")* dongia_capnhat);
				String CO = NO;
				String DOITUONG_NO="";
				if(NOIDUNGXUAT_FK.equals("100002")){
						DOITUONG_NO = "Giá vốn hàng xuất bán";
				}else{
					   DOITUONG_NO = "Giá vốn hàng xuất khuyến mãi";
				}
				String MADOITUONG_NO = "";
				String DOITUONG_CO = "Sản phẩm";
				String MADOITUONG_CO = rs.getString("SANPHAM_FK");
				String LOAIDOITUONG = "";
				String SOLUONG = "" + rs.getDouble("TONGXUAT");
				String DONGIA = "" + rs.getDouble("DONGIA");
				String TIENTEGOC_FK = "100000";
				String DONGIANT = "0";
				String TIGIA_FK = "1";
				String TONGGIATRI = NO;
				String TONGGIATRINT = TONGGIATRI;
				String khoanmuc = rs.getString("KHOANMUC");
				util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
								 	NOIDUNGXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
								 	SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc);
			}
			
			if(dem==0){
				msg = "Không cập nhật được khoản mục kế toán , vui lòng báo admin để được xử lý .";
				db.getConnection().rollback();
				return msg;
			}
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_YCXUATKHONPP set trangthai = '1'  where pk_seq = '" + Id + "' ";
			//System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG HOAN TAT CAC DON DAT HANG TU CU TOI MOI ==> ĐƠN HÀNG ĐÃ DUYỆT CS THÌ LÚC NÀO TRẠNG THÁI CŨNG = 4
			query = "select ddh_fk, ( select xuatcho from ERP_YCXUATKHONPP where pk_seq = a.ycxk_fk ) as xuatcho " +
					"from ERP_YCXUATKHONPP_DDH a where ycxk_fk = '" + Id + "' order by ddh_fk asc";
			ResultSet rsDDH = db.get(query);
			String ddhID = "";
			String xuatCHO = "";
			if(rsDDH != null)
			{
				while(rsDDH.next())
				{
					ddhID += rsDDH.getString("ddh_fk") + ",";
					xuatCHO = rsDDH.getString("xuatcho");
					
					/*query = "  select COUNT(*) as soDONG,   " +
							" 		(   select count(distinct sanpham_fk) as soSP      " +
							"   			from     " +
							"   			(     " +
							"   					select a.sanpham_fk " +
							"   					from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   					where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   			)     " +
							"   			dathang  )	 as soSP  " +
							"  from  " +
							"  (  " +
							"  	select sanpham_fk, sum(soluongXUAT) as soluongXUAT  " +
							"  	from ERP_YCXUATKHONPP_SANPHAM  " +
							" 	where ycxk_fk in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " ) ) " +
							"  	group by sanpham_fk  " +
							"  )   " +
							"  XUAT inner join   " +
							"  (  " +
							"   	select dathang.sanpham_fk, SUM(dathang.soluong) as soluongDAT      " +
							"   	from     " +
							"   	(     " +
							"   			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
							"   					case when a.dvdl_fk IS null then a.soluong      " +
							"   						 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
							"   						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )      " +
							"   										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme    " +
							"   			from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   			where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   	)     " +
							"   	dathang   " +
							"   	group by dathang.sanpham_fk  " +
							"  )  " +
							"  DDH on XUAT.sanpham_fk = DDH.sanpham_fk  " +
							"  where XUAT.soluongXUAT >= DDH.soluongDAT ";
					
					System.out.println("CHECK HOAN TAT: " + query);
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						String trangthai = "";
						if(rsCHECK.getInt("soDONG") == rsCHECK.getInt("soSP"))
							trangthai = "4";  //HOAN TAT
						else
							trangthai = "2";  //KE TOAN DUYET
						
						query = " UPDATE ERP_DONDATHANGNPP set trangthai = '" + trangthai + "' " +
								" where pk_seq in ( " + ( rsDDH.getString("ddh_fk") ) + " ) ";
						if(!db.update(query))
						{
							msg = "Không thể chốt ERP_YCXUATKHO " + query;
							db.getConnection().rollback();
							return msg;
						}
					}*/
				}
				rsDDH.close();
			}
			
			System.out.println("---XUAT CHO: " + xuatCHO);
			if(xuatCHO.equals("0"))  //XUẤT CHO ĐỐI TÁC THÌ TẠO NHẬP HÀNG CHO ĐỐI TÁC = LUONG THUC GIAO, LUONG CON DU CHUYEN VAO KHO KY GUI
			{
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, SOCHUNGTU, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, NHOMKENH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select sohoadon from ERP_YCXUATKHONPP where PK_SEQ = a.pk_seq ), " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, a.NHOMKENH_FK, '" + Id + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + Id + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select IDENT_CURRENT('NHAPHANG'),  " +
						" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + Id + "' and b.soluong > 0 ";
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(db.updateReturnInt(query) < 1 )
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
						"select IDENT_CURRENT('NHAPHANG') as nhID, ddh_fk  " +
						"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + Id + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
				
			//Lượng chênh lệch giữa lúc duyệt hóa đơn và lúc phiếu giao hàng sẽ được cho vào kho gửi của npp, khách hàng tại PHANAM
			query =  "select '100003' as kho_fk, a.sanpham_fk, c.nhomkenh_fk, c.NPP_DAT_FK, c.KHACHHANG_FK, a.solo, a.ngayhethan, 1 as DUNGCHUNGKENH, a.soluong - b.soluong as conLAI  " + 
					 "from ERP_YCXUATKHONPP_SANPHAM_CHITIET a inner join ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET b  " + 
					 "		on a.sanpham_fk = b.sanpham_fk and a.solo = b.solo and a.ngayhethan = b.ngayhethan and a.LOAI = b.LOAI and a.ycxk_fk = b.ycxk_fk " + 
					 "	 inner join ERP_YCXUATKHONPP c on a.ycxk_fk = c.PK_SEQ " +
					 "where a.ycxk_fk = '" + Id + "' and a.soluong - b.soluong > 0 ";
			System.out.println("CHECK SO LUONG GIAO CON LAI: " + query);
			ResultSet rsKYGUI = db.get(query);
			if(rsKYGUI != null)
			{
				while(rsKYGUI.next())
				{
					String khokyguiID = rsKYGUI.getString("kho_fk");
					String sanpham_fk = rsKYGUI.getString("sanpham_fk");
					String nhomkenh_fk = rsKYGUI.getString("nhomkenh_fk");
					if(rsKYGUI.getString("DUNGCHUNGKENH").equals("1"))
						nhomkenh_fk = "100000";
						
					String isNPP = "0";
					if(xuatCHO.equals("0")) //đơn hàng bán cho NPP
						isNPP = "1";
					
					String KHACHHANG_FK = rsKYGUI.getString("KHACHHANG_FK");
					if(isNPP.equals("1"))
						KHACHHANG_FK = rsKYGUI.getString("NPP_DAT_FK");
					
					String solo = rsKYGUI.getString("solo");
					String ngayhethan = rsKYGUI.getString("ngayhethan");
					double soluongCONLAI = rsKYGUI.getDouble("conLAI");
					
					query = " select count(*) as sodong from NHAPP_KHO_KYGUI " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					
					boolean exitKHO = false;
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getInt("sodong") > 0 )
								exitKHO = true;
							rsCHECK.close();
						}
					}
					
					if(exitKHO)
					{
						query = " update NHAPP_KHO_KYGUI set soluong = soluong + '" + soluongCONLAI + "', available = available + '" + soluongCONLAI + "' " + 
								" where isNPP = '" + isNPP + "' and npp_fk = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					}
					else
					{
						query = "insert NHAPP_KHO_KYGUI( isNPP, KHO_FK, NPP_FK, KHACHHANG_FK, SANPHAM_FK, nhomkenh_fk, SOLUONG, BOOKED, AVAILABLE ) " + 
								" values( '" + isNPP + "', '" + khokyguiID + "', '" + nppId +  "', '" + KHACHHANG_FK + "', '" + sanpham_fk + "', '" + nhomkenh_fk + "', '" + soluongCONLAI + "', '0', '" + soluongCONLAI + "' )";
					}
					
					System.out.println(":::: CHEN VAO KHO KY GUI: " + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Lỗi khi chốt phiếu giao hàng " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = " select count(*) as sodong from NHAPP_KHO_KYGUI_CHITIET " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
					
					exitKHO = false;
					rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getInt("sodong") > 0 )
								exitKHO = true;
							rsCHECK.close();
						}
					}
					
					if(exitKHO)
					{
						query = " update NHAPP_KHO_KYGUI_CHITIET set soluong = soluong + '" + soluongCONLAI + "', available = available + '" + soluongCONLAI + "' " + 
								" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
					}
					else
					{
						query = "insert NHAPP_KHO_KYGUI_CHITIET( isNPP, KHO_FK, NPP_FK, KHACHHANG_FK, SANPHAM_FK, nhomkenh_fk, solo, ngayhethan, SOLUONG, BOOKED, AVAILABLE ) " + 
								" values( '" + isNPP + "', '" + khokyguiID + "', '" + nppId + "', '" + KHACHHANG_FK + "', '" + sanpham_fk + "', '" + nhomkenh_fk + "', '" + solo + "', '" + ngayhethan + "', '" + soluongCONLAI + "', '0', '" + soluongCONLAI + "' )";
					}
					
									
					System.out.println(":::: CHEN VAO KHO KY GUI CHI TIET: " + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Lỗi khi chốt phiếu giao hàng " + query;
						db.getConnection().rollback();
						return msg;
					}
				}
				rsKYGUI.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String MoChot(String Id, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			
			//MỞ KE TOAN
			
			/*Utility_Kho util_kho=new Utility_Kho();
			query = "SELECT YC.NGAYYEUCAU, YC_CT.SANPHAM_FK, SUM(YC_CT.SOLUONG) AS TONGXUAT,YC_CT.SOLO , " +

					"CASE WHEN CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) > 1 THEN " + // TRƯỜNG HỢP KHÔNG LÀ THÁNG 1, THÌ LẤY THÁNG - 1
					"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG " +
					"	          WHERE THANG = (CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) - 1) AND NAM = SUBSTRING(NGAYYEUCAU, 1, 4) " +
					"	          AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 ) " +
					"ELSE " +
					"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG " +
					"			  WHERE THANG = 12 and NAM = (SUBSTRING(NGAYYEUCAU, 1, 4) - 1) " +
					"			  AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 ) " +
					"END AS DONGIA, " + 
			
			 		"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		"'XK01' " +
			 		"ELSE " +
			 		"'XK02' END AS MAXUATKHO, " +
			 		
			 		"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' " +
					"	AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +
					
					"ELSE " +  // ĐƠN HÀNG KHUYẾN MẠI
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' " +
					"	AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +

					"END AS TAIKHOANNO_FK,  " + 
					
					" (	SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN (" +
					"		SELECT TOP 1 LSP.TAIKHOANKT_FK  " +
			 		"		FROM SANPHAM SP " +
					"		INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
					"		WHERE SP.PK_SEQ = YC_CT.SANPHAM_FK " +
					"   ) AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') " +
					" ) AS TAIKHOANCO_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" '100002' " +
			 		" ELSE " +
			 		" '100008' END AS NOIDUNGXUAT_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" N'Xuất bán hàng (theo đơn hàng bán) - Kho không ký gửi' " +
			 		" ELSE " +
			 		" N'Xuất khuyến mại - Kho không ký gửi' END AS KHOANMUC ," +
			 		" (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') as congty_fk " +	
			 		" FROM ERP_YCXUATKHONPP YC " +
					" INNER JOIN ERP_YCXUATKHONPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.YCXK_FK " + 
					" INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = YC.HOADON_FK " +
					" WHERE YCXK_FK = '" + Id + "' " +
					" GROUP BY NGAYYEUCAU, YC_CT.SANPHAM_FK, YC_CT.KHO_FK, YC.NHOMKENH_FK, YC_CT.SCHEME,YC_CT.SOLO ";
			
			System.out.println("Định khoản: " + query);
			ResultSet rs = db.get(query);
			int dem=0;
			while(rs.next()){
				dem++;
				String ngaychungtu = rs.getString("NGAYYEUCAU");
				String nam = ngaychungtu.substring(0, 4);
				String thang = "" + (Integer.parseInt(ngaychungtu.substring(5, 7)));
				String ngayghinhan = ngaychungtu;
				String loaichungtu = rs.getString("MAXUATKHO");
				String sochungtu = Id;
				String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
				String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
				String NOIDUNGXUAT_FK = rs.getString("NOIDUNGXUAT_FK");
				
				String congty_fk= rs.getString("congty_fk");
				
				String  SANPHAM_FK= rs.getString("SANPHAM_FK");
				String solo=rs.getString("SOLO");
				double dongia_capnhat=0;
				String str[]=util_kho.getGiaChayKT(ngaychungtu, db,congty_fk , nppId, SANPHAM_FK, solo);
				if(str[1].length() >0){

					msg = "Không thể cập nhật lỗi :  " + str[1];
					db.getConnection().rollback();
					return msg;
				}else{
					dongia_capnhat=Double.parseDouble(str[0]);
				}
				 
				String NO = "" + (rs.getDouble("TONGXUAT")* dongia_capnhat);
				String CO = NO;
				String DOITUONG_NO="";
				if(NOIDUNGXUAT_FK.equals("100002")){
						DOITUONG_NO = "Giá vốn hàng xuất bán";
				}else{
					   DOITUONG_NO = "Giá vốn hàng xuất khuyến mãi";
				}
				String MADOITUONG_NO = "";
				String DOITUONG_CO = "Sản phẩm";
				String MADOITUONG_CO = rs.getString("SANPHAM_FK");
				String LOAIDOITUONG = "";
				String SOLUONG = "" + rs.getDouble("TONGXUAT");
				String DONGIA = "" + rs.getDouble("DONGIA");
				String TIENTEGOC_FK = "100000";
				String DONGIANT = "0";
				String TIGIA_FK = "1";
				String TONGGIATRI = NO;
				String TONGGIATRINT = TONGGIATRI;
				String khoanmuc = rs.getString("KHOANMUC");
				util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
								 	NOIDUNGXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
								 	SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc);
			}
			
			if(dem==0){
				msg = "Không cập nhật được khoản mục kế toán , vui lòng báo admin để được xử lý .";
				db.getConnection().rollback();
				return msg;
			}*/
			
			
			//CHECK XEM ĐÃ RA SỔ XUẤT HÀNG CHƯA
			query = "select COUNT(*) as sodong from ERP_SOXUATHANGNPP_DDH " +
					"where hoadon_fk = ( select hoadon_fk from ERP_YCXUATKHONPP where PK_SEQ = '" + Id + "' ) " + 
					" 	and soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where NPP_FK = ( select npp_fk from ERP_YCXUATKHONPP where PK_SEQ = '" + Id + "' )   )";
			ResultSet rs = db.get(query);
			int soDONG = 0;
			if( rs != null )
			{
				if(rs.next())
				{
					soDONG = rs.getInt("soDONG");
				}
				rs.close();
			}
			
			if( soDONG > 0 )
			{
				msg = "Phiếu giao hàng này đã có sổ xuất hàng, bạn không thể mở chốt.";
				db.getConnection().rollback();
				return msg;
			}
			
			//CHECK XEM PHIẾU GIAO HÀNG NÀY DƯỚI NPP ĐÃ NHẬN HÀNG CHƯA --> CÁI NÀY MẤY NỮA CHUYỂN SANG CHỖ MỞ HÓA ĐƠN
			/*query = " select count(*) as soDONG " + 
					" from NHAPHANG where trangthai = '1' and SOCHUNGTU = ( select sohoadon from ERP_YCXUATKHONPP where PK_SEQ = '" + Id + "' ) ";
			rs = db.get(query);
			soDONG = 0;
			if( rs != null )
			{
				if(rs.next())
				{
					soDONG = rs.getInt("soDONG");
				}
				rs.close();
			}
			
			if( soDONG > 0 )
			{
				msg = "Phiếu giao hàng này đã có nhận hàng, bạn không thể mở chốt.";
				db.getConnection().rollback();
				return msg;
			}*/
			
			query = "update ERP_YCXUATKHONPP set trangthai = '0', ngaymochot = getdate(), nguoimochot = '" + userId + "'  where pk_seq = '" + Id + "' ";
			//System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể mở chốt ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//Mở lại những đơn đã đánh dấu hoàn tất
			query = " UPDATE ERP_DONDATHANGNPP set trangthai = '2' where pk_seq in ( select ddh_fk from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + Id + "' ) ";
			if(!db.update(query))
			{
				msg = "Không thể mở chốt ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//Lượng chênh lệch giữa lúc duyệt hóa đơn và lúc phiếu giao hàng sẽ được cho vào kho gửi của npp, khách hàng tại PHANAM
			query =  "select c.loaidonhang, '100003' as kho_fk, a.sanpham_fk, c.nhomkenh_fk, c.NPP_DAT_FK, c.KHACHHANG_FK, a.solo, a.ngayhethan, 1 as DUNGCHUNGKENH, a.soluong - b.soluong as conLAI  " + 
					 "from ERP_YCXUATKHONPP_SANPHAM_CHITIET a inner join ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET b  " + 
					 "		on a.sanpham_fk = b.sanpham_fk and a.solo = b.solo and a.ngayhethan = b.ngayhethan and a.LOAI = b.LOAI and a.ycxk_fk = b.ycxk_fk " + 
					 "	 inner join ERP_YCXUATKHONPP c on a.ycxk_fk = c.PK_SEQ " +
					 "where a.ycxk_fk = '" + Id + "' and a.soluong - b.soluong > 0 ";
			System.out.println("CHECK SO LUONG GIAO CON LAI: " + query);
			ResultSet rsKYGUI = db.get(query);
			if(rsKYGUI != null)
			{
				while(rsKYGUI.next())
				{
					String khokyguiID = rsKYGUI.getString("kho_fk");
					String sanpham_fk = rsKYGUI.getString("sanpham_fk");
					String nhomkenh_fk = rsKYGUI.getString("nhomkenh_fk");
					if(rsKYGUI.getString("DUNGCHUNGKENH").equals("1"))
						nhomkenh_fk = "100000";
						
					String isNPP = "0";
					if(rsKYGUI.getString("loaidonhang").equals("0")) //đơn hàng bán cho NPP
						isNPP = "1";
					
					String KHACHHANG_FK = rsKYGUI.getString("KHACHHANG_FK");
					if(isNPP.equals("1"))
						KHACHHANG_FK = rsKYGUI.getString("NPP_DAT_FK");
					
					String solo = rsKYGUI.getString("solo");
					String ngayhethan = rsKYGUI.getString("ngayhethan");
					double soluongCONLAI = rsKYGUI.getDouble("conLAI");
					
					query = " select available from NHAPP_KHO_KYGUI " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getDouble("available") > soluongCONLAI )
							{
								rsCHECK.close();
								msg = "1.Tồn kho ký gửi chỉ còn ( " + rsCHECK.getDouble("available") + " ) không đủ để mở phiếu giao hàng cần ( " + soluongCONLAI + " ) ";
								db.getConnection().rollback();
								return msg;
							}
							rsCHECK.close();
						}
					}
					
					query = " update NHAPP_KHO_KYGUI set soluong = soluong - '" + soluongCONLAI + "', available = available - '" + soluongCONLAI + "' " + 
							" where isNPP = '" + isNPP + "' and npp_fk = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					
					System.out.println(":::: CHEN VAO KHO KY GUI: " + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Lỗi khi chốt phiếu giao hàng " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = " select available from NHAPP_KHO_KYGUI_CHITIET " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
					
					rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getDouble("available") > soluongCONLAI )
							{
								rsCHECK.close();
								msg = "2.Tồn kho ký gửi chỉ còn ( " + rsCHECK.getDouble("available") + " ) không đủ để mở phiếu giao hàng cần ( " + soluongCONLAI + " ) ";
								db.getConnection().rollback();
								return msg;
							}
							rsCHECK.close();
						}
					}
					
					query = " update NHAPP_KHO_KYGUI_CHITIET set soluong = soluong - '" + soluongCONLAI + "', available = available - '" + soluongCONLAI + "' " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
								
					System.out.println(":::: CHEN VAO KHO KY GUI CHI TIET: " + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Lỗi khi chốt phiếu giao hàng " + query;
						db.getConnection().rollback();
						return msg;
					}
				}
				rsKYGUI.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
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
	    
		IErpYeucauxuatkhoNppList obj = new ErpYeucauxuatkhoNppList();
		
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
		obj.setLoaidonhang(loaidonhang);
		
		/*String phanloai = request.getParameter("phanloai");
	    if(phanloai == null)
	    	phanloai = "";
		obj.setPhanloai(phanloai);*/
		
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    String masophieu = request.getParameter("masophieu");
	    if(masophieu == null)
	    	masophieu = "";
	    obj.setMaso(masophieu);
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    System.out.println("---NPP ID: " + nppId);
	    if(action.equals("Tao moi"))
	    {
	    	IErpYeucauxuatkhoNpp lsxBean = new ErpYeucauxuatkhoNpp();
	    	lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	lsxBean.setUserId(userId);
	    	//lsxBean.setXuatcho(phanloai);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuGiaoHangNppNew.jsp";
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
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuGiaoHangNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
			    obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuGiaoHangNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpYeucauxuatkhoNppList obj)
	{
		Utility util = new Utility();
		String query = "select distinct a.PK_SEQ, a.machungtu, a.trangthai, a.ngayyeucau, case "+
								"  when a.loaidonhang = '0' then c.ten "
								  +" when a.loaidonhang = '1' then d.ten "
								  +" when a.loaidonhang =  '2' then d.ten "
								  +" when a.loaidonhang =  '3' then e.ten "
								 +" end  as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
						"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ',' AS [text()]  " +
						"		From ERP_YCXUATKHONPP_DDH YCXK1   " +
						"		Where YCXK1.ycxk_fk = a.pk_seq  " +
						"		For XML PATH ('') )  as ddhIds, hd.sohoadon    " +
						"from ERP_YCXUATKHONPP a inner join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						util.getPhanQuyen_TheoNhanVien("KHACHHANG", "d", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() ) +
						"   left join NVGN_KH nvgn on    nvgn.khachhang_fk=a.khachhang_fk  "+
						"   left join nhanviengiaonhan nvgnn on nvgnn.pk_seq=nvgn.nvgn_fk "+					  
						"	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq  " +
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
						"	left join ERP_HOADONNPP hd on a.hoadon_fk = hd.pk_seq " +
						" left join KHACHHANG_KENHBANHANG kbh on d.pk_seq = kbh.khachhang_fk   " +
						"	left join diaban db on db.pk_seq = d.diaban   " +
						"	inner join ERP_YCXUATKHONPP_SANPHAM ycxksp on ycxksp.ycxk_fk = a.pk_seq   " +
						"where a.npp_fk = '" + obj.getNppId() + "' and a.kho_fk in "+util.quyen_kho(obj.getUserId()); 
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
	    String masophieu = request.getParameter("masophieu");
	    if(masophieu == null)
	    	masophieu = "";
	    obj.setMaso(masophieu);
	    
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sodonhang = request.getParameter("sodonhang");
	    if(sodonhang == null)
	    	sodonhang = "";
	    obj.setSodonhang(sodonhang);
	    
	    String sohoadon = request.getParameter("sohoadon");
	    if(sohoadon == null)
	    	sohoadon = "";
	    obj.setSohoadon(sohoadon);
	    
	    String khohh = request.getParameter("khohhid");
	    if(khohh == null)
	    	khohh = "";
	    obj.setKhohh(khohh);
	    
	    String khten = request.getParameter("khTen");
	    if(khten == null)
	    	khten = "";
	    obj.setKhten(khten);
	    
	    String nguoigiaohang = request.getParameter("nguoigiaohang");
	    if(nguoigiaohang == null)
	    	nguoigiaohang = "";
	    obj.setNguoigiao(nguoigiaohang);
	    
	    String kbhid=request.getParameter("kbhid");
		if(kbhid==null)
			kbhid="";
		obj.setKbhId(kbhid);
		
		String kvid=request.getParameter("kvid");
		if(kvid==null)
			kvid="";
		obj.setKvId(kvid);
		
		String spid=request.getParameter("spid");
		if(spid==null)
			spid="";
		obj.setSpId(spid);
		
	    if(nguoigiaohang.length()>0)
	    {
	    	query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiaohang ) + "%'";
	    }
	    
	    if(khohh.length()>0)
	    {
	    	query+=" and a.kho_fk = "+khohh ;
	    }
		
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}
		
		if(sodonhang.trim().length() > 0 )
			query += " and cast( a.ddh_fk as varchar(10) ) like '%" + sodonhang + "%' ";
		
		if(sohoadon.trim().length() > 0)
			query += " and hd.sohoadon like '%" + sohoadon + "%' ";
		
		if(masophieu.length() > 0 )
		{
			query += " and a.machungtu like '%" + masophieu + "%' ";
		}
		
		if(khten.trim().length() > 0)
		{
			//query+=" and (d.ten like N'%"+khten+"%' or d.mafast like N'"+khten+"') ";
			query += " and isnull(d.timkiem, c.timkiem) like N'%" + khten + "%'";
		}
		if(kbhid.length()>0)
		{
			query += " and kbh.kbh_fk = '"+kbhid+"'" ;
		}
		if(kvid.length()>0)
		{
			query += " and db.khuvuc_fk = '"+kvid+"'" ;
		}
		
		if(spid.length()>0)
		{
			query += " and ycxksp.sanpham_fk = '"+spid+"'" ;
		}
		
		System.out.print("::: SEARCH: " + query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] args) 
	{
		dbutils db = new dbutils();
		// Mở chốt phiếu giao hàng
		try 
		{
			db.getConnection().setAutoCommit(false);
			// thay câu truy vấn bằng số phiếu hoặc hóa đơn
			String query = "select pk_seq, npp_fk from ERP_YCXUATKHONPP where pk_seq in ( ) ";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String pghId = rs.getString("pk_seq");
				String nppId = rs.getString("npp_fk");
				
				//Mở lại trạng thái PHIẾU GIAO HÀNG ( làm trươc trường hợp đơn giản, thực giao = xuát, chưa có nhận hàng )
				query =  "update kho set kho.SOLUONG = kho.SOLUONG + CT.tongxuat,  " + 
						 "			   kho.BOOKED = kho.BOOKED + CT.tongxuat  " + 
						 "from  " + 
						 "(  " + 
						 "	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.solo, b.ngayhethan, SUM(b.soluong) as tongxuat   " + 
						 "	from ERP_YCXUATKHONPP a  " + 
						 "	inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk  " + 
						 "	where ycxk_fk = '" + pghId + "'  " + 
						 "	group by a.kho_fk, a.nhomkenh_fk, b.solo, b.ngayhethan, b.sanpham_fk  " + 
						 ")  " + 
						 "CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK   " + 
						 "and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO  " + 
						 "and CT.ngayhethan = kho.ngayhethan and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "'  ";
				if(!db.update(query))
				{
					System.out.println("::: 1.LOI TAI DON: " + pghId + " : " + query);
					db.getConnection().rollback();
					return;
				}
				
				query =  "update kho set kho.SOLUONG = kho.SOLUONG + CT.tongxuat,  " + 
						 "			   kho.BOOKED = kho.BOOKED + CT.tongxuat  " + 
						 "from  " + 
						 "(  " + 
						 "	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat   " + 
						 "	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk  " + 
						 "	where ycxk_fk = '" + pghId + "'  " + 
						 "	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk  " + 
						 ")  " + 
						 "CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK   " + 
						 "	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "' ";
				if(db.updateReturnInt(query) <= 0)
				{
					System.out.println("::: 2.LOI TAI DON: " + pghId + " : " + query);
					db.getConnection().rollback();
					return;
				}
				
				query =  "update  ERP_YCXUATKHONPP set TRANGTHAI = 0 where PK_SEQ = '" + pghId + "' ";
				if(db.updateReturnInt(query) <= 0)
				{
					System.out.println("::: 3.LOI TAI DON: " + pghId + " : " + query);
					db.getConnection().rollback();
					return;
				}
				
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			
			System.out.println("::: CHAY LOI.... ");
			return;
		}
		
		System.out.println("::: CHAY XONG.... ");
		
	}
	
	
	
}
