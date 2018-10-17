package geso.traphaco.center.servlets.xuatkho;

import geso.traphaco.center.beans.xuatkho.IErpYeucauxuatkho;
import geso.traphaco.center.beans.xuatkho.IErpYeucauxuatkhoList;
import geso.traphaco.center.beans.xuatkho.imp.ErpYeucauxuatkho;
import geso.traphaco.center.beans.xuatkho.imp.ErpYeucauxuatkhoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.util.Library;

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

public class ErpYeucauxuatkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpYeucauxuatkhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpYeucauxuatkhoList obj;
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
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpYeucauxuatkhoList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);

	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    //obj.setNppId(nppId);
	    System.out.println("---NPP ID: " + nppId);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId, nppId);
    		
    	    obj.setUserId(userId);
    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
    	    obj.init("");
    	    obj.setMsg(msg);
    	    
    		session.setAttribute("obj", obj);
    			
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKho.jsp";
    		response.sendRedirect(nextJSP);
  
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId, nppId);
			obj.setMsg(msg);
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");
    
    		session.setAttribute("obj", obj);
    			
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKho.jsp";
    		response.sendRedirect(nextJSP);
    	}
    	else
    		if(action.equals("unchot"))
        	{
        		String msg = this.MoChot(lsxId, userId, nppId);
        		
        	    obj.setUserId(userId);
        	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
        	    obj.init("");
        	    obj.setMsg(msg);
        	    
        		session.setAttribute("obj", obj);
        			
        		String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKho.jsp";
        		response.sendRedirect(nextJSP);
      
        	}
    		else
    	{
		    obj.setUserId(userId);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKho.jsp";
			response.sendRedirect(nextJSP);
    	}
	    
	}

	private String Delete(String lsxId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";

			query = "UPDATE ERP_HOADON set trangthai = 1, hoantat = 0 where pk_seq in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ) ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHO_SANPHAM where ycxk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHO  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
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
	
	private String Chot(String lsxId, String userId, String nppId) 
	{	
		dbutils db = new dbutils();
		
		String msg = "";
		String query = "";
		try
		{
	
			db.getConnection().setAutoCommit(false);
		
			/*//CHEN BANG CHI TIET ==> 1 hoa don chi xuat kho 1 lan
			query = "delete ERP_YCXUATKHO_SANPHAM_CHITIET  where ycxk_fk in (  " + lsxId + " )  ";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
				db.getConnection().rollback();
				return msg;
			}	
			
			query =  " insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong, soluong_dvban ) \n "+
					 " select '" + lsxId + "' ycxk_fk, b.PK_SEQ as SANPHAM_FK, ' ' scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, a.HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong_chuan, soluong   \n "+
					 " from ERP_HOADON_SP_CHITIET a inner join ERP_SANPHAM b on a.ma = b.MA   \n "+
					 " where a.hoadon_fk in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ) ";
			System.out.println("query: "+query);
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
				db.getConnection().rollback();
				return msg;
			}	*/
			
			Utility util = new Utility();
			msg = util.Check_Huy_NghiepVu_KhoaSo_3010("ERP_YCXUATKHO", lsxId, "ngayyeucau", db);
			if(msg.trim().length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			String loaichungtu = "Xuất kho"; 
			String chungtuId = lsxId; 
			String transactionId = util.createTransactionId();
			
			query = "  select b.ngayyeucau, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_YCXUATKHO_SANPHAM_CHITIET a inner join ERP_YCXUATKHO b on a.ycxk_fk = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.ngayyeucau, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Kho_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("ngayyeucau"), "Chốt xuất kho - giảm số lượng, booked", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, -1 * soluong, 0, 
							loaichungtu, chungtuId, transactionId	);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_YCXUATKHO set trangthai = '2'  where pk_seq = '" + lsxId + "' and trangthai != 2 ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG HOAN TAT CAC HOA DON TU CU TOI MOI
			query = "select hoadon_fk, ( select xuatcho from ERP_YCXUATKHO where pk_seq = a.ycxk_fk ) as xuatcho " +
					"from ERP_YCXUATKHO_DDH a where ycxk_fk = '" + lsxId + "' order by hoadon_fk asc";
			System.out.println("---CAP NHAT TRANG THAI HOA DON: " + query);
			
			ResultSet rsDDH = db.get(query);
			String ddhID = "";
			String xuatCHO = "";
			if(rsDDH != null)
			{
				while(rsDDH.next())
				{
					ddhID = rsDDH.getString("hoadon_fk") + ",";
					xuatCHO = rsDDH.getString("xuatcho");
					
				
				}
				rsDDH.close();
			}
			
			//HOA DON CHI XUAT 1 LAN THOI
			query = " UPDATE ERP_HOADON set hoantat = '1' where pk_seq in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ) ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//Cập nhật đơn hàng đã trừ kho, để không booked nữa
			query = " update ERP_DONDATHANG set daxuatkho = '1' " + 
					" where pk_seq in (  select DDH_FK from ERP_HOADON_DDH where hoadon_fk in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ) )";
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			// CẬP NHẬT KẾ TOÁN
			Library lib = new Library();
			msg=lib.CapNhatKeToanKho_XuatKho_Donhang_HO(userId,db,lsxId,false,"100000");
			if(msg.length()>0)
			{
				msg = "Không thể cập nhật PHIEUXUATKHO " + msg;
				db.getConnection().rollback();
				return msg;
			}
		
			
			System.out.println("---XUAT CHO: " + xuatCHO);
			if(xuatCHO.equals("0"))  //XUẤT CHO ĐỐI TÁC THÌ TẠO NHẬP HÀNG CHO ĐỐI TÁC
			{/*
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXK_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_FK,  " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = a.npp_fk ) as KBH_FK, '" + lsxId + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + lsxId + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select ( select pk_seq from NHAPHANG where YCXK_FK = a.PK_SEQ and trangthai != 2  ),  " +
						" 		b.sanpham_fk, sum(b.soluong) as soluong, NULL, avg(b.dongia), 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + lsxId + "' and b.soluong > 0 " + 
						" group by a.PK_SEQ, b.sanpham_fk, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan	";
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(db.updateReturnInt(query) < 1 )
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, hoadon_fk, isHO )  " +
						"select ( select PK_SEQ from NHAPHANG where YCXK_FK = '" + lsxId + "' and trangthai != 2 ) as nhID, hoadon_fk, 1  " +
						"from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					db.getConnection().rollback();
					return msg;
				}
			*/}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			
			
			
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
	
	private String MoChot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			Utility util = new Utility();
			msg = util.Check_Huy_NghiepVu_KhoaSo_3010("ERP_YCXUATKHO", lsxId, "ngayyeucau", db);
			if(msg.trim().length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			String loaichungtu = "Xuất kho"; 
			String chungtuId = lsxId; 
			String transactionId = util.createTransactionId();
			
			query = "  select b.ngayyeucau, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_YCXUATKHO_SANPHAM_CHITIET a inner join ERP_YCXUATKHO b on a.ycxk_fk = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.ngayyeucau, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Kho_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("ngayyeucau"), "Mở Chốt xuất kho - tăng số lượng, booked", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, +1 * soluong, 0, 
							loaichungtu, chungtuId, transactionId	);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_YCXUATKHO set trangthai = '0'  where pk_seq = '" + lsxId + "' and trangthai = 2 ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//HOA DON CHI XUAT 1 LAN THOI
			query = " UPDATE ERP_HOADON set hoantat = '0' where pk_seq in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ) ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//Cập nhật đơn hàng đã trừ kho, để không booked nữa
			query = " update ERP_DONDATHANG set daxuatkho = '0' " + 
					" where pk_seq in (  select DDH_FK from ERP_HOADON_DDH where hoadon_fk in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + lsxId + "' ) )";
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			query = " DELETE FROM ERP_PHATSINHKETOAN WHERE LOAICHUNGTU LIKE N'Xuất kho bán hàng' AND SOCHUNGTU =  "+lsxId ;
					
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
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
	    
	    
		IErpYeucauxuatkhoList obj = new ErpYeucauxuatkhoList();
		obj.setLoaidonhang(loaidonhang);
		
	    String sohoadon = request.getParameter("sohoadon");
	    if(sohoadon == null)
	    	sohoadon = "";
	    obj.setSohoadon(sohoadon);		
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    
	    System.out.println("---NPP ID: " + khId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpYeucauxuatkho lsxBean = new ErpYeucauxuatkho();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKhoNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	this.getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKho.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpYeuCauXuatKho.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpYeucauxuatkhoList obj)
	{
//		String query = "select a.PK_SEQ, a.trangthai, a.ngayyeucau, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
//						"	 (	Select HD.sohoadon + ',' AS [text()]  " +
//						"		From ERP_YCXUATKHO_DDH YCXK1 inner join ERP_HOADON HD on YCXK1.hoadon_fk = HD.pk_seq    " +
//						"		Where YCXK1.ycxk_fk = a.pk_seq  " +
//						"		For XML PATH ('') )  as ddhIds, isnull( a.tooltip, '' ) as tooltip    " +
//						"from ERP_YCXUATKHO a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq " +
//						"	inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq " +
//						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
//						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
//						" where 1 = 1 "; 
		
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
		String idxk = request.getParameter("idxk");
		if(idxk == null)
			idxk = "";
		obj.setId(idxk);
		System.out.println("idxk: "+idxk);
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		System.out.println("npp ten doi tac "+khId);
		
		String sanphamId = request.getParameter("sanphamId");
		if(sanphamId == null)
			sanphamId = "";
		obj.setSanphamId(sanphamId);
//		if(tungay.length() > 0)
//			query += " and a.ngayyeucau >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngayyeucau <= '" + denngay + "'";
//	
//		if(trangthai.length() > 0)
//		{
//			if(trangthai.equals("0"))
//				query += " and a.TrangThai = '" + trangthai + "'";
//			else
//				query += " and a.TrangThai >= '" + trangthai + "'";
//		}
//		
//		if(khId.length() > 0)
//		{
//			query += " and a.npp_fk = '" + khId + "'";
//		}
//		
//		System.out.print(query);
//		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main( String[] arg )
	{
		dbutils db = new dbutils();
		String query = "select pk_seq from ERP_YCXUATKHO " + 

					   "where trangthai = 2 and  pk_seq in ( 109602 ) ";

		ResultSet rs = db.get(query);
		try 
		{
			ErpYeucauxuatkhoSvl xk = new ErpYeucauxuatkhoSvl();
			while( rs.next() )
			{
				String pk_seq = rs.getString("pk_seq");
				xk.MoChot(pk_seq, "1", "1");
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println(":::: MO CHOT XONG........ ");
		
	}
	
	
}
