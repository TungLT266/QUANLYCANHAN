package geso.traphaco.distributor.beans.xuatkho.imp;

import geso.traphaco.distributor.beans.xuatkho.IErpXacnhannhanhang;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpXacnhannhanhang implements IErpXacnhannhanhang
{
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaygiaohangGui;
	String ghichu;

	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String tinhthanhId;
	ResultSet tinhthanhRs;
	String quanhuyenId;
	ResultSet quanhuyenRs;
	String nvgnId;
	ResultSet nvgnRs;
	String nvbhId;
	ResultSet nvbhRs;
	
	String khId;
	ResultSet khRs;
	
	String nvIds;
	ResultSet nvRs;
	
	String ddhId;
	ResultSet ddhRs;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	Hashtable<String, String> sanpham_soluong;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluongDat;
	String[] spTonkho;
	String[] spDaxuat;
	String[] spSoluong;
	String[] spGianhap;
	String[] spLoai;
	String[] spSCheme;
	
	String nppId;
	String nppTen;
	String sitecode;
	String xuatcho;
	
	ResultSet soloOLD;
	
	String phanloai;
	
	String tungay;
	String denngay;
	
	String machungtu;
	String nguoitao;
	
	dbutils db;
	Utility util;
	
	public ErpXacnhannhanhang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "";
		this.ngaygiaohangGui = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.nvbhId = "";
		this.nvgnId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.phanloai = "";
		this.db = new dbutils();
		this.util = new Utility();
		
		this.tungay = "";
		this.denngay = "";
		this.nvIds = "";
		this.machungtu = "";
		this.nguoitao = "";
	}
	
	public ErpXacnhannhanhang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "";
		this.ngaygiaohangGui = "";

		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.nvbhId = "";
		this.nvgnId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.phanloai = "";
		this.db = new dbutils();
		this.util = new Utility();
		
		this.tungay = "";
		this.denngay = "";
		this.nvIds = "";
		this.machungtu = "";
		this.nguoitao = "";
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public void createRs() 
	{
		this.getNppInfo();
		
		this.tinhthanhRs = db.get("select PK_SEQ, TEN from TINHTHANH where TRANGTHAI = '1'");
		String sql = "select PK_SEQ, TEN from QUANHUYEN where TRANGTHAI = '1'";
		if(this.tinhthanhId.trim().length() > 0)
			sql += " AND TINHTHANH_FK = '" + this.tinhthanhId + "' ";
		this.quanhuyenRs = db.get(sql);
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)     );
		
		sql = "select PK_SEQ, TEN from DAIDIENKINHDOANH a where TRANGTHAI = '1' AND pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		sql += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvbhRs = db.get(sql);
		
		sql = "select PK_SEQ, TEN from NHANVIENGIAONHAN a where TRANGTHAI = '1' AND npp_fk = '" + this.nppId + "' ";
		sql += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvgnRs = db.get(sql);
		
		String query = "";
		if( !this.xuatcho.equals("0") )
		{
			query = " select pk_seq, MAFAST + ' - ' + TEN as TEN from KHACHHANG a where trangthai = '1' and NPP_FK = '" + this.nppId + "' ";
			if(this.tinhthanhId.trim().length() > 0)
				query += " and tinhthanh_fk = '" + this.tinhthanhId + "' ";
			if(this.quanhuyenId.trim().length() > 0)
				query += " and quanhuyen_fk = '" + this.quanhuyenId + "' ";
			
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}
		else
		{
			query = " select pk_seq, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI a where trangthai = '1'  ";
			if(this.tinhthanhId.trim().length() > 0)
				query += " and tinhthanh_fk = '" + this.tinhthanhId + "' ";
			if(this.quanhuyenId.trim().length() > 0)
				query += " and quanhuyen_fk = '" + this.quanhuyenId + "' ";
			
			query += " and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ";
			query += util.getPhanQuyen_TheoNhanVien("DLPP", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}
		
		System.out.println("::: LAY KHACH HANG: " + query );
		this.khRs = db.get(query);
		
		query = "select PK_SEQ, TEN + ' ' + isnull(DIENTHOAI, 'NA') + ' ' + isnull(EMAIL, 'NA') as diengiai " + 
				" from NHANVIEN where TRANGTHAI = '1' order by TEN + ' ' + isnull(DIENTHOAI, 'NA') + ' ' + isnull(EMAIL, 'NA') asc";
		System.out.println("::: LAY NHAN VIEN: " + query );
		this.nvRs = db.getScrol(query);
		
		if( this.khId.trim().length() > 0 || this.tinhthanhId.trim().length() > 0 || this.quanhuyenId.trim().length() > 0 
				|| this.nvbhId.trim().length() > 0 || this.nvgnId.trim().length() > 0
				|| this.machungtu.trim().length() > 0 || this.nguoitao.trim().length() > 0 || this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 )
		{
			query = "select ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = hd.pk_seq ) ) as machungtu, " + 
					" hd.PK_SEQ, hd.SOHOADON + '  ' + hd.NGAYXUATHD + '  ' + ISNULL( kh.TEN, npp.TEN ) + '  ' + hd.TENXUATHD as ten, " +
					" hd.SOHOADON, hd.NGAYXUATHD, ISNULL( kh.TEN, npp.TEN ) khTEN, hd.TENXUATHD, hd.tongtienavat, '' as kenh		" +
					"from ERP_HOADONNPP hd left join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ " +
					"		left join NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ  " +
					"where hd.chungtudh is null and hd.NPP_FK = '" + this.nppId + "'   ";
						
			/*if( this.khoNhanId.trim().length() > 0 )
				query += " and hd.Kho_FK = '" + this.khoNhanId + "' ";*/
			if( this.xuatcho.trim().length() > 0 )
				query += " and hd.LOAIXUATHD = '" + this.xuatcho + "' ";
			if( this.tungay.trim().length() > 0 )
				query += " AND ( ( hd.donhangMUON = '0' and hd.trangthai in ( 2, 4 ) and NGAYXUATHD >= '" + this.tungay + "' ) or ( hd.donhangMUON = '1' and hd.trangthai not in ( 0, 3, 5 ) ) ) ";
			if( this.denngay.trim().length() > 0 )
				query += " AND ( ( hd.donhangMUON = '0' and hd.trangthai in ( 2, 4 ) and NGAYXUATHD <= '" + this.denngay + "' ) or ( hd.donhangMUON = '1' and hd.trangthai not in ( 0, 3, 5 ) ) ) ";
			
			if(this.nvbhId.trim().length() > 0)
				query += " AND hd.DDKD_FK = '" + this.nvbhId + "' ";
			if(this.nvgnId.trim().length() > 0 )
			{
				if(!this.xuatcho.equals("0")) 
					query += " and hd.KHACHHANG_FK in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + this.nvgnId + "' ) ";
			}
			
			if(this.xuatcho.equals("0"))
			{
				if( this.tinhthanhId.trim().length() > 0 )
					query += " and hd.NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where TINHTHANH_FK = '" + this.tinhthanhId + "' ) ";
				if( this.quanhuyenId.trim().length() > 0 )
					query += " and hd.NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where QUANHUYEN_FK = '" + this.quanhuyenId + "' ) ";
			}
			else
			{
				if( this.tinhthanhId.trim().length() > 0 )
					query += " and hd.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where NPP_FK = '" + this.nppId + "' and TINHTHANH_FK = '" + this.tinhthanhId + "' ) ";
				if( this.quanhuyenId.trim().length() > 0 )
					query += " and hd.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where NPP_FK = '" + this.nppId + "' and QUANHUYEN_FK = '" + this.quanhuyenId + "' ) ";
			}
			
			if( this.machungtu.trim().length() > 0 )
			{
				//query += " AND hd.machungtu like '%" + this.machungtu + "%' ";
				query += " AND ( ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = hd.pk_seq ) ) like '%" + this.machungtu + "%' ) ";
			}
			if( this.nguoitao.trim().length() > 0 )
				query += " AND hd.nguoitao in ( select pk_seq from NHANVIEN where dbo.ftBoDau(ten) like N'%" + util.replaceAEIOU(this.nguoitao) + "%' ) ";
			
			
			if(this.khId.trim().length() > 0)
			{
				if(this.xuatcho.equals("0")) //XUAT CHO DOI TAC
					query += " and hd.NPP_DAT_FK = '" + this.khId + "' ";
				else
					query += " and hd.KHACHHANG_FK = '" + this.khId + "' ";
			}
			
			query += " AND hd.pk_seq not in ( select hoadon_fk from ERP_XACNHANNHANHANG_DDH where xacnhannhanhang_fk in ( select pk_seq from ERP_XACNHANNHANHANG where npp_fk = '" + this.nppId + "' and trangthai in ( 0, 1, 2 ) and pk_seq != '" + ( this.id.trim().length() > 0 ? this.id : "1" ) + "' ) ) ";
			
			if(this.nvbhId.trim().length() > 0)
				query += " AND hd.DDKD_FK = '" + this.nvbhId + "' ";
			if(this.nvgnId.trim().length() > 0 )
			{
				if(!this.xuatcho.equals("0")) 
					query += " and KHACHHANG_FK in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + this.nvgnId + "' ) ";
			}
			
			if(this.xuatcho.equals("0"))
			{
				if( this.tinhthanhId.trim().length() > 0 )
					query += " and NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where TINHTHANH_FK = '" + this.tinhthanhId + "' ) ";
				if( this.quanhuyenId.trim().length() > 0 )
					query += " and NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where QUANHUYEN_FK = '" + this.quanhuyenId + "' ) ";
			}
			else
			{
				if( this.tinhthanhId.trim().length() > 0 )
					query += " and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where NPP_FK = '" + this.nppId + "' and TINHTHANH_FK = '" + this.tinhthanhId + "' ) ";
				if( this.quanhuyenId.trim().length() > 0 )
					query += " and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where NPP_FK = '" + this.nppId + "' and QUANHUYEN_FK = '" + this.quanhuyenId + "' ) ";
			}
			
			String conditonNVGN_KH = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "kh", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			String conditonNVGN_DLPP = util.getPhanQuyen_TheoNhanVien("DLPP", "npp", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			
			query += " AND ( ( hd.LOAIXUATHD = 0 " + conditonNVGN_DLPP + " ) or ( hd.LOAIXUATHD != 0 " + conditonNVGN_KH + " )  ) ";
			
			query += " AND isnull( kh.tinhthanh_fk, npp.tinhthanh_fk ) != ( select tinhthanh_fk from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ) ";
			
			System.out.println("----LAY HOA DON: " + query );
			this.ddhRs = db.get(query);
			
			/*if( this.ddhId.trim().length() <= 0 )
			{
				ResultSet rs = db.get(query);
				String ddhIds = "";
				if( rs != null )
				{
					try 
					{
						while( rs.next() )
						{
							ddhIds += rs.getString("PK_SEQ") + ",";
						}
						rs.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				
				if( ddhIds.trim().length() > 0 )
				{
					ddhIds = ddhIds.substring(0, ddhIds.length() - 1 );
					this.ddhId = ddhIds;
				}
			}*/
		}
		else //Mặc định load hết đơn hàng tạo trong ngày
		{
			this.tungay = this.getDateTime();
			
			query = "select ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = hd.pk_seq ) ) as machungtu, " + 
					" hd.PK_SEQ, hd.SOHOADON + '  ' + hd.NGAYXUATHD + '  ' + ISNULL( kh.TEN, npp.TEN ) + '  ' + hd.TENXUATHD as ten, " +
					" hd.SOHOADON, hd.NGAYXUATHD, ISNULL( kh.TEN, npp.TEN ) khTEN, hd.TENXUATHD, hd.tongtienavat, '' as kenh		" +
					"from ERP_HOADONNPP hd left join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ " +
					"		left join NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ  " +
					"where hd.NPP_FK = '" + this.nppId + "'   ";
			
			query += " AND ( ( hd.donhangMUON = '0' and hd.trangthai in ( 2, 4 ) and NGAYXUATHD = '" + this.getDateTime() + "' ) or ( hd.donhangMUON = '1' and hd.trangthai not in ( 0, 3, 5 ) ) ) ";
			query += " AND hd.pk_seq not in ( select hoadon_fk from ERP_XACNHANNHANHANG_DDH where xacnhannhanhang_fk in ( select pk_seq from ERP_XACNHANNHANHANG where npp_fk = '" + this.nppId + "' and trangthai in ( 0, 1, 2 ) and pk_seq != '" + ( this.id.trim().length() > 0 ? this.id : "1" ) + "' ) ) ";
			query += " AND isnull( kh.tinhthanh_fk, npp.tinhthanh_fk ) != ( select tinhthanh_fk from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ) ";
			
			if( !this.xuatcho.equals("0") )
				query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "kh", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			else
				query += " and hd.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ";
			
			this.ddhRs = db.get(query);
		}
		
		
		System.out.println(":::: DON DAT HANG ID: " + this.ddhId);
		if(this.ddhId.trim().length() > 0  )
		{
			//INIT SP
			String ycxkID = ( this.id.trim().length() <= 0 ) ? "-1" : this.id;
			query = " select ddh.*, ISNULL(xuat.soluongXUAT, 0) as xuat,   " +
					" 	ISNULL( daxuat.soluongDAXUAT, 0) as daxuat, 0 as tonkho    " +
					" from   " +
					" (   " +
					/*" 	select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai, SUM(dathang.soluong) as soluongDAT    " +
					" 	from   " +
					" 	(   " +
					" 			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,   " +
					" 					case when a.dvdl_fk IS null then a.soluong    " +
					" 						 when a.dvdl_fk = b.DVDL_FK then a.soluong   " +
					" 						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )    " +
					" 										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk  and DVDL1_FK=b.DVDL_FK )	 end as soluong, 0 as loai, ' ' as scheme  " +
					" 			from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					" 			where a.dondathang_fk in (   " + this.ddhId + "   )  " +
					"		union ALL   " + 
					"			select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME    " + 
					"			from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA    " + 
					"				inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ    " + 
					"				inner join ERP_DONDATHANGNPP d on  a.DONDATHANGID = d.pk_seq  " + 
					"			where a.DONDATHANGID in ( " + this.ddhId + " )  " +
					" 	)   " +
					" 	dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					" 			inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ   " +
					" 	group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai  " +*/
					"		select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, case when SCHEME != '' then 1 else 0 end as loai, SUM(SoLuong_Chuan) as soluongDAT " +
					"		from ERP_HOADONNPP_SP hd inner join SANPHAM sp on hd.SANPHAM_FK = sp.PK_SEQ " +
					"				inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ " +
					"		where hd.HOADON_FK in ( " + this.ddhId + " ) " +
					"		group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme	" +
					" )   " +
					" ddh left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, b.soluongXUAT as soluongXUAT   " +
					" 	from ERP_XACNHANNHANHANG a inner join ERP_XACNHANNHANHANG_SANPHAM b on a.PK_SEQ = b.xacnhannhanhang_fk  " +
					" 	where a.PK_SEQ = '" + ycxkID + "'  " +
					" )  " +
					" xuat on ddh.PK_SEQ = xuat.sanpham_fk and ddh.loai = xuat.LOAI and ddh.scheme = xuat.scheme left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, SUM( b.soluongXUAT ) as soluongDAXUAT   " +
					" 	from ERP_XACNHANNHANHANG a inner join ERP_XACNHANNHANHANG_SANPHAM b on a.PK_SEQ = b.xacnhannhanhang_fk  " +
					" 	where a.PK_SEQ != '" + ycxkID + "' and a.TRANGTHAI != 3   " +
					" 		and a.PK_SEQ in ( select xacnhannhanhang_fk from  ERP_XACNHANNHANHANG_DDH where ddh_fk in (  " + this.ddhId + "  ) )  " +
					" 	group by b.sanpham_fk, b.LOAI, b.SCHEME  " +
					" )  " +
					" daxuat on ddh.PK_SEQ = daxuat.sanpham_fk and ddh.loai = daxuat.LOAI and ddh.scheme = daxuat.scheme order by SCHEME asc  ";
			
			System.out.println("---INIT SAN PHAM: " + query);
			ResultSet spRs = db.get(query);
			NumberFormat formater = new DecimalFormat("##,###,###");
			
			if(spRs != null)
			{
				try 
				{
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONGDAT = "";
					String spTONKHO = "";
					String spDAXUAT = "";
					String spSOLUONGXUAT = "";
					String spLOAI = "";
					String spSCHEME = "";
					
					while(spRs.next())
					{
						double conLAI = 1000000;
						if(this.id.trim().length() <= 0)
							conLAI = spRs.getDouble("soluongDAT") - spRs.getDouble("daxuat");
						else
							conLAI = spRs.getDouble("xuat");
						 
						if(conLAI > 0)
						{
							spID += spRs.getString("PK_SEQ") + "__";
							spMA += spRs.getString("MA") + "__";
							spTEN += spRs.getString("TEN") + "__";
							spDONVI += spRs.getString("DONVI") + "__";
							spSOLUONGDAT += formater.format(spRs.getDouble("soluongDAT")) + "__";
							spTONKHO += formater.format(spRs.getDouble("tonkho")) + "__";
							spDAXUAT += formater.format(spRs.getDouble("daxuat")) + "__";
							//if(this.id.trim().length() <= 0)
								//spSOLUONGXUAT += formater.format( conLAI ) + "__";
							//else
								//spSOLUONGXUAT += formater.format( spRs.getDouble("xuat") ) + "__";
								
							//SO LUONG XUAT LUC NAO CUNG BANG SO LUONG DAT
							spSOLUONGXUAT += formater.format( spRs.getDouble("soluongDAT") ) + "__";
								
							spLOAI += spRs.getString("LOAI") + "__";
							
							if( spRs.getString("SCHEME").trim().length() > 1 )
								spSCHEME += spRs.getString("SCHEME") + "__";
							else
								spSCHEME += " __";
						}
					}
					spRs.close();
					
					if(spMA.trim().length() > 0)
					{
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");
						
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");
						
						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");
						
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spSOLUONGDAT = spSOLUONGDAT.substring(0, spSOLUONGDAT.length() - 2);
						this.spSoluongDat = spSOLUONGDAT.split("__");
						
						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						this.spTonkho = spTONKHO.split("__");
						
						spDAXUAT = spDAXUAT.substring(0, spDAXUAT.length() - 2);
						this.spDaxuat = spDAXUAT.split("__");
						
						spSOLUONGXUAT = spSOLUONGXUAT.substring(0, spSOLUONGXUAT.length() - 2);
						this.spSoluong = spSOLUONGXUAT.split("__");
						
						spLOAI = spLOAI.substring(0, spLOAI.length() - 2);
						this.spLoai = spLOAI.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
					}
					
				} 
				catch (Exception e) {e.printStackTrace(); System.out.println("EXCEPTION SP: " + e.getMessage() ); }
				
			}
			
		}
		else
		{
			this.spId = null;
			this.spMa = null;
			this.spTen = null;
			this.spDonvi = null;
			this.spSoluongDat = null;
			this.spTonkho = null;
			this.spDaxuat = null;
			this.spSoluong = null;
			this.spLoai = null;
			this.spSCheme = null;
		}
		
	}

	public void init() 
	{
		String query = "select ngayyeucau, isnull(ngaygiaohangGUI, '') as ngaygiaohangGUI, xuatcho, ISNULL(ghichu, '') as ghichu, isnull(npp_dat_fk, khachhang_fk) as khId, kho_fk, trangthai, tinhthanh_fk, quanhuyen_fk, nvbh_fk, nvgn_fk, tungay, denngay " +
						"from ERP_XACNHANNHANHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.ghichu = rs.getString("ghichu");
					this.khId = rs.getString("khId") == null ? "" : rs.getString("khId");
					this.khoNhanId = rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk");
					this.xuatcho = rs.getString("xuatcho") == null ? "" : rs.getString("xuatcho");
					this.trangthai = rs.getString("trangthai");
					this.ngaygiaohangGui = rs.getString("ngaygiaohangGUI");
					
					this.tinhthanhId = rs.getString("tinhthanh_fk") == null ? "" : rs.getString("tinhthanh_fk");
					this.quanhuyenId = rs.getString("quanhuyen_fk") == null ? "" : rs.getString("quanhuyen_fk");
					
					this.nvbhId = rs.getString("nvbh_fk") == null ? "" : rs.getString("nvbh_fk");
					this.nvgnId = rs.getString("nvgn_fk") == null ? "" : rs.getString("nvgn_fk");
					
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					
				}
				rs.close();
				
				//INIT DDH
				query = "select hoadon_fk from ERP_XACNHANNHANHANG_DDH where xacnhannhanhang_fk = '" + this.id + "' ";
				rs = db.get(query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("hoadon_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.ddhId = ddhID.substring(0, ddhID.length() - 1);
				}
				
				query = "select nhanvien_fk from ERP_XACNHANNHANHANG_NHANVIEN where xacnhannhanhang_fk = '" + this.id + "' order by stt asc ";
				rs = db.get(query);
				ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("nhanvien_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.nvIds = ddhID.substring(0, ddhID.length() - 1);
				}
				
				//INIT SO LUONG
				query = "select sanpham_fk, solo, isnull(LOAI, 0) as LOAI, soluong, ngayhethan, isnull(scheme, '') as scheme " +
						"from ERP_XACNHANNHANHANG_SANPHAM_CHITIET where xacnhannhanhang_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						if(this.trangthai.equals("0"))
						{
							//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
							sp_soluong.put(rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim() + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan"), rs.getString("soluong") );
						}
						else
						{
							//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
							String key = rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim();
							
							String value = sp_soluong.get(key);
							if(value != null)
								value += ";" + rs.getString("solo") + "__" + rs.getString("soluong") + "__" + rs.getString("ngayhethan");
							else
								value = rs.getString("solo") + "__" + rs.getString("soluong") + "__" + rs.getString("ngayhethan");
							
							sp_soluong.put( key, value );
						}
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}

		this.createRs();
		
	}
	
	public void initDisplay() 
	{
		String query = "select ngayyeucau, isnull(ngaygiaohangGUI, '') as ngaygiaohangGUI, xuatcho, ISNULL(ghichu, '') as ghichu, isnull(npp_dat_fk, khachhang_fk) as khId, kho_fk, trangthai, tinhthanh_fk, quanhuyen_fk, nvbh_fk, nvgn_fk, tungay, denngay " +
						"from ERP_XACNHANNHANHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.ghichu = rs.getString("ghichu");
					this.khId = rs.getString("khId") == null ? "" : rs.getString("khId");
					this.khoNhanId = rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk");
					this.xuatcho = rs.getString("xuatcho") == null ? "" : rs.getString("xuatcho");
					this.trangthai = rs.getString("trangthai");
					this.ngaygiaohangGui = rs.getString("ngaygiaohangGUI");
					
					this.tinhthanhId = rs.getString("tinhthanh_fk") == null ? "" : rs.getString("tinhthanh_fk");
					this.quanhuyenId = rs.getString("quanhuyen_fk") == null ? "" : rs.getString("quanhuyen_fk");
					
					this.nvbhId = rs.getString("nvbh_fk") == null ? "" : rs.getString("nvbh_fk");
					this.nvgnId = rs.getString("nvgn_fk") == null ? "" : rs.getString("nvgn_fk");
					
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					
				}
				rs.close();
				
				//INIT DDH
				query = "select hoadon_fk from ERP_XACNHANNHANHANG_DDH where xacnhannhanhang_fk = '" + this.id + "' ";
				rs = db.get(query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("hoadon_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.ddhId = ddhID.substring(0, ddhID.length() - 1);
				}
				
				query = "select nhanvien_fk from ERP_XACNHANNHANHANG_NHANVIEN where xacnhannhanhang_fk = '" + this.id + "' order by stt asc ";
				rs = db.get(query);
				ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("nhanvien_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.nvIds = ddhID.substring(0, ddhID.length() - 1);
				}
				
				//INIT SO LUONG
				query = "select sanpham_fk, solo, isnull(LOAI, 0) as LOAI, soluong, ngayhethan, isnull(scheme, '') as scheme " +
						"from ERP_XACNHANNHANHANG_SANPHAM_CHITIET where xacnhannhanhang_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						if(this.trangthai.equals("0"))
						{
							//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
							sp_soluong.put(rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim() + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan"), rs.getString("soluong") );
						}
						else
						{
							//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
							String key = rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim();
							
							String value = sp_soluong.get(key);
							if(value != null)
								value += ";" + rs.getString("solo") + "__" + rs.getString("soluong") + "__" + rs.getString("ngayhethan");
							else
								value = rs.getString("solo") + "__" + rs.getString("soluong") + "__" + rs.getString("ngayhethan");
							
							sp_soluong.put( key, value );
						}
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
	
		this.getNppInfo();
		
		this.tinhthanhRs = db.get("select PK_SEQ, TEN from TINHTHANH where TRANGTHAI = '1'");
		String sql = "select PK_SEQ, TEN from QUANHUYEN where TRANGTHAI = '1'";
		if(this.tinhthanhId.trim().length() > 0)
			sql += " AND TINHTHANH_FK = '" + this.tinhthanhId + "' ";
		this.quanhuyenRs = db.get(sql);
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)     );
		
		sql = "select PK_SEQ, TEN from DAIDIENKINHDOANH a where TRANGTHAI = '1' AND pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		sql += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvbhRs = db.get(sql);
		
		sql = "select PK_SEQ, TEN from NHANVIENGIAONHAN a where TRANGTHAI = '1' AND npp_fk = '" + this.nppId + "' ";
		sql += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvgnRs = db.get(sql);
		
		query = "";
		if( !this.xuatcho.equals("0") )
		{
			query = " select pk_seq, MAFAST + ' - ' + TEN as TEN from KHACHHANG a where trangthai = '1' and NPP_FK = '" + this.nppId + "' ";
			if(this.tinhthanhId.trim().length() > 0)
				query += " and tinhthanh_fk = '" + this.tinhthanhId + "' ";
			if(this.quanhuyenId.trim().length() > 0)
				query += " and quanhuyen_fk = '" + this.quanhuyenId + "' ";
			
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}
		else
		{
			query = " select pk_seq, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI a where trangthai = '1'  ";
			if(this.tinhthanhId.trim().length() > 0)
				query += " and tinhthanh_fk = '" + this.tinhthanhId + "' ";
			if(this.quanhuyenId.trim().length() > 0)
				query += " and quanhuyen_fk = '" + this.quanhuyenId + "' ";
			
			query += " and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ";
		}
		
		System.out.println("::: LAY KHACH HANG: " + query );
		this.khRs = db.get(query);
		
		query = "select PK_SEQ, TEN + ' ' + isnull(DIENTHOAI, 'NA') + ' ' + isnull(EMAIL, 'NA') as diengiai " + 
				" from NHANVIEN where TRANGTHAI = '1' order by TEN + ' ' + isnull(DIENTHOAI, 'NA') + ' ' + isnull(EMAIL, 'NA') asc";
		System.out.println("::: LAY NHAN VIEN: " + query );
		this.nvRs = db.getScrol(query);
	
		query = "select ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = hd.pk_seq ) ) as machungtu, " + 
				" hd.PK_SEQ, hd.SOHOADON + '  ' + hd.NGAYXUATHD + '  ' + ISNULL( kh.TEN, npp.TEN ) + '  ' + hd.TENXUATHD as ten, " +
				" hd.SOHOADON, hd.NGAYXUATHD, ISNULL( kh.TEN, npp.TEN ) khTEN, hd.TENXUATHD, hd.tongtienavat, '' as kenh		" +
				"from ERP_HOADONNPP hd left join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ " +
				"		left join NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ  " +
				"where hd.chungtudh is null and hd.NPP_FK = '" + this.nppId + "' and hd.trangthai not in ( 0, 3, 5 )  ";

		query += " and hd.pk_seq in ( " + this.ddhId + " ) ";
		System.out.println("----LAY HOA DON: " + query );
		this.ddhRs = db.get(query);

		if(this.ddhId.trim().length() > 0  )
		{
			//INIT SP
			String ycxkID = ( this.id.trim().length() <= 0 ) ? "-1" : this.id;
			query = " select ddh.*, ISNULL(xuat.soluongXUAT, 0) as xuat,   " +
					" 	ISNULL( daxuat.soluongDAXUAT, 0) as daxuat, 0 as tonkho    " +
					" from   " +
					" (   " +
					/*" 	select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai, SUM(dathang.soluong) as soluongDAT    " +
					" 	from   " +
					" 	(   " +
					" 			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,   " +
					" 					case when a.dvdl_fk IS null then a.soluong    " +
					" 						 when a.dvdl_fk = b.DVDL_FK then a.soluong   " +
					" 						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )    " +
					" 										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk  and DVDL1_FK=b.DVDL_FK )	 end as soluong, 0 as loai, ' ' as scheme  " +
					" 			from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					" 			where a.dondathang_fk in (   " + this.ddhId + "   )  " +
					"		union ALL   " + 
					"			select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME    " + 
					"			from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA    " + 
					"				inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ    " + 
					"				inner join ERP_DONDATHANGNPP d on  a.DONDATHANGID = d.pk_seq  " + 
					"			where a.DONDATHANGID in ( " + this.ddhId + " )  " +
					" 	)   " +
					" 	dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					" 			inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ   " +
					" 	group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai  " +*/
					"		select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, case when SCHEME != '' then 1 else 0 end as loai, SUM(SoLuong_Chuan) as soluongDAT " +
					"		from ERP_HOADONNPP_SP hd inner join SANPHAM sp on hd.SANPHAM_FK = sp.PK_SEQ " +
					"				inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ " +
					"		where hd.HOADON_FK in ( " + this.ddhId + " ) " +
					"		group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme	" +
					" )   " +
					" ddh left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, b.soluongXUAT as soluongXUAT   " +
					" 	from ERP_XACNHANNHANHANG a inner join ERP_XACNHANNHANHANG_SANPHAM b on a.PK_SEQ = b.xacnhannhanhang_fk  " +
					" 	where a.PK_SEQ = '" + ycxkID + "'  " +
					" )  " +
					" xuat on ddh.PK_SEQ = xuat.sanpham_fk and ddh.loai = xuat.LOAI and ddh.scheme = xuat.scheme left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, SUM( b.soluongXUAT ) as soluongDAXUAT   " +
					" 	from ERP_XACNHANNHANHANG a inner join ERP_XACNHANNHANHANG_SANPHAM b on a.PK_SEQ = b.xacnhannhanhang_fk  " +
					" 	where a.PK_SEQ != '" + ycxkID + "' and a.TRANGTHAI != 3   " +
					" 		and a.PK_SEQ in ( select xacnhannhanhang_fk from  ERP_XACNHANNHANHANG_DDH where ddh_fk in (  " + this.ddhId + "  ) )  " +
					" 	group by b.sanpham_fk, b.LOAI, b.SCHEME  " +
					" )  " +
					" daxuat on ddh.PK_SEQ = daxuat.sanpham_fk and ddh.loai = daxuat.LOAI and ddh.scheme = daxuat.scheme order by SCHEME asc  ";
			
			System.out.println("---INIT SAN PHAM: " + query);
			ResultSet spRs = db.get(query);
			NumberFormat formater = new DecimalFormat("##,###,###");
			
			if(spRs != null)
			{
				try 
				{
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONGDAT = "";
					String spTONKHO = "";
					String spDAXUAT = "";
					String spSOLUONGXUAT = "";
					String spLOAI = "";
					String spSCHEME = "";
					
					while(spRs.next())
					{
						double conLAI = 1000000;
						if(this.id.trim().length() <= 0)
							conLAI = spRs.getDouble("soluongDAT") - spRs.getDouble("daxuat");
						else
							conLAI = spRs.getDouble("xuat");
						 
						if(conLAI > 0)
						{
							spID += spRs.getString("PK_SEQ") + "__";
							spMA += spRs.getString("MA") + "__";
							spTEN += spRs.getString("TEN") + "__";
							spDONVI += spRs.getString("DONVI") + "__";
							spSOLUONGDAT += formater.format(spRs.getDouble("soluongDAT")) + "__";
							spTONKHO += formater.format(spRs.getDouble("tonkho")) + "__";
							spDAXUAT += formater.format(spRs.getDouble("daxuat")) + "__";
							//if(this.id.trim().length() <= 0)
								//spSOLUONGXUAT += formater.format( conLAI ) + "__";
							//else
								//spSOLUONGXUAT += formater.format( spRs.getDouble("xuat") ) + "__";
								
							//SO LUONG XUAT LUC NAO CUNG BANG SO LUONG DAT
							spSOLUONGXUAT += formater.format( spRs.getDouble("soluongDAT") ) + "__";
								
							spLOAI += spRs.getString("LOAI") + "__";
							
							if( spRs.getString("SCHEME").trim().length() > 1 )
								spSCHEME += spRs.getString("SCHEME") + "__";
							else
								spSCHEME += " __";
						}
					}
					spRs.close();
					
					if(spMA.trim().length() > 0)
					{
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");
						
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");
						
						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");
						
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spSOLUONGDAT = spSOLUONGDAT.substring(0, spSOLUONGDAT.length() - 2);
						this.spSoluongDat = spSOLUONGDAT.split("__");
						
						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						this.spTonkho = spTONKHO.split("__");
						
						spDAXUAT = spDAXUAT.substring(0, spDAXUAT.length() - 2);
						this.spDaxuat = spDAXUAT.split("__");
						
						spSOLUONGXUAT = spSOLUONGXUAT.substring(0, spSOLUONGXUAT.length() - 2);
						this.spSoluong = spSOLUONGXUAT.split("__");
						
						spLOAI = spLOAI.substring(0, spLOAI.length() - 2);
						this.spLoai = spLOAI.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
					}
					
				} 
				catch (Exception e) {e.printStackTrace(); System.out.println("EXCEPTION SP: " + e.getMessage() ); }
				
			}
			
		}
		else
		{
			this.spId = null;
			this.spMa = null;
			this.spTen = null;
			this.spDonvi = null;
			this.spSoluongDat = null;
			this.spTonkho = null;
			this.spDaxuat = null;
			this.spSoluong = null;
			this.spLoai = null;
			this.spSCheme = null;
		}

	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}

	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String khId) {
		
		this.nppId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getDondathangId() {
		
		return this.ddhId;
	}

	
	public void setDondathangId(String kbhId) {
		
		this.ddhId = kbhId;
	}

	
	public ResultSet getDondathangRs() {
		
		return this.ddhRs;
	}

	
	public void setDondathangRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}

	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}

	
	public ResultSet getSoloTheoSp(String spIds, String tongluong, String scheme )
	{
		/*String query = "select sum(soluong) as tuDEXUAT, SOLO, NGAYHETHAN, 0 as AVAILABLE " +
					   "from ERP_DONDATHANGNPP_SANPHAM_CHITIET " + 
					   " where sanpham_fk = '" + spIds + "' and dondathang_fk in ( " + this.ddhId + " ) and ltrim(rtrim(scheme)) = '" + scheme + "' group by SOLO, NGAYHETHAN ";*/
		
		String query = 	"select sum(soluong_chuan) as tuDEXUAT, SOLO, NGAYHETHAN, 0 as AVAILABLE " +
				   		"from ERP_HOADONNPP_SP_CHITIET " + 
				   		" where MA = ( select ma from SANPHAM where pk_seq = '" + spIds + "' ) and hoadon_fk in ( " + this.ddhId + " )"
				   				+ " and ltrim(rtrim(scheme)) = '" + scheme + "' group by SOLO, NGAYHETHAN ";
		System.out.println("---LAY SO LO OLD:: " + query);
		return db.get(query);
	}

	public ResultSet getSoloTheoSpOLD(String spIds, String tongluong)
	{
		/*String query = "select soluong, SOLO, NGAYHETHAN " +
					   "from ERP_DONDATHANGNPP_SANPHAM_CHITIET where sanpham_fk = '" + spIds + "' and dondathang_fk = ( select ddh_fk from ERP_XACNHANNHANHANG where PK_SEQ = '" + this.id + "' ) ";*/
		
		String query = 	"select sum(soluong_chuan) as soluong, SOLO, NGAYHETHAN, 0 as AVAILABLE " +
		   				"from ERP_HOADONNPP_SP_CHITIET " + 
		   				" where MA = ( select ma from SANPHAM where pk_seq = '" + spIds + "' ) and hoadon_fk in ( " + this.ddhId + " )"
		   					+ " group by SOLO, NGAYHETHAN ";
		System.out.println("---LAY SO LO OLD:: " + query);
		return db.get(query);
	}
	
	public String[] getSpSoluongDat() {
		
		return this.spSoluongDat;
	}

	
	public void setSpSoluongDat(String[] spSoluong) {
		
		this.spSoluongDat = spSoluong;
	}

	
	public String[] getSpTonKho() {
		
		return this.spTonkho;
	}

	
	public void setSpTonKho(String[] spTonkho) {
		
		this.spTonkho = spTonkho; 
	}

	
	public String[] getSpDaXuat() {
		
		return this.spDaxuat;
	}

	
	public void setSpDaXuat(String[] spDaXuat) {
		
		this.spDaxuat = spDaXuat;
	}

	
	public boolean create() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xác nhận";
			return false;
		}

		/*if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng";
			return false;
		}*/
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn hóa đơn";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khachhang_fk = "NULL";
			String npp_dat_fk = "NULL";
			String xuatcho = this.xuatcho.trim().length() <= 0 ? "NULL" : this.xuatcho;
			
			if(this.xuatcho.equals("0"))
				npp_dat_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			else
				khachhang_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			
			String tinhthanh_fk = this.tinhthanhId.trim().length() <= 0 ? "NULL" : this.tinhthanhId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL" : this.quanhuyenId;
			String nvbh_fk = this.nvbhId.trim().length() <= 0 ? "NULL" : this.nvbhId;
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL" : this.nvgnId;
			
			String query = " insert ERP_XACNHANNHANHANG(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, NPP_DAT_FK, KHACHHANG_FK, NHOMKENH_FK, ngaytao, nguoitao, ngaysua, nguoisua, tinhthanh_fk, quanhuyen_fk, nvbh_fk, nvgn_fk, tungay, denngay, xuatcho) " +
						   " select top(1) '" + this.ngayyeucau + "', N'" + this.ghichu + "', '0', '" + this.nppId + "', NULL, " + npp_dat_fk + ", " + khachhang_fk + ", NHOMKENH_FK, " +
						   		" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + tinhthanh_fk + ", " + quanhuyen_fk + ", " + nvbh_fk + ", " + nvgn_fk + ", '" + this.tungay + "', '" + this.denngay + "', " + xuatcho +
						   " from ERP_HOADONNPP where pk_seq in ( " + this.ddhId + " ) ";
			
			System.out.println("1.Insert ERP_XACNHANNHANHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_XACNHANNHANHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select scope_identity() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			query = "Insert ERP_XACNHANNHANHANG_DDH(xacnhannhanhang_fk, hoadon_fk) " +
					"select '" + this.id + "', pk_seq from ERP_HOADONNPP where pk_seq in ( " + this.ddhId + " )  ";
			System.out.println("2.chen ERP_XACNHANNHANHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_XACNHANNHANHANG_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.nvIds.trim().length() > 0 )
			{
				query = "Insert ERP_XACNHANNHANHANG_NHANVIEN(xacnhannhanhang_fk, nhanvien_fk) " +
						"select '" + this.id + "', pk_seq from NHANVIEN where pk_seq in ( " + this.nvIds + " )  ";
				System.out.println("2.chen ERP_XACNHANNHANHANG_NHANVIEN: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_XACNHANNHANHANG_NHANVIEN " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert ERP_XACNHANNHANHANG_SANPHAM( xacnhannhanhang_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select '" + this.id + "', '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i] + "' ";
					
					System.out.println("1.1.Insert YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_XACNHANNHANHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim() ))
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								query = "insert ERP_XACNHANNHANHANG_SANPHAM_CHITIET( xacnhannhanhang_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
										"select '" + this.id + "', pk_seq, N'" + _sp[3] + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			msg = util.CapNhat_MaChungTu(db, this.id, "ERP_XACNHANNHANHANG", "ngayyeucau");
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public boolean update() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		/*if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}*/
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn hóa đơn";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String khachhang_fk = "NULL";
			String npp_dat_fk = "NULL";
			String xuatcho = this.xuatcho.trim().length() <= 0 ? "NULL" : this.xuatcho;
			
			if(this.xuatcho.equals("0"))
				npp_dat_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			else
				khachhang_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			
			String tinhthanh_fk = this.tinhthanhId.trim().length() <= 0 ? "NULL" : this.tinhthanhId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL" : this.quanhuyenId;
			String nvbh_fk = this.nvbhId.trim().length() <= 0 ? "NULL" : this.nvbhId;
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL" : this.nvgnId;
			
			String query = " Update ERP_XACNHANNHANHANG set NgayYeuCau = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', npp_fk = '" + this.nppId + "', kho_fk = NULL, " +
						   "	xuatcho = " + xuatcho + ", khachhang_fk = " + khachhang_fk + ", nhomkenh_fk = ( select top(1) nhomkenh_FK from ERP_DONDATHANGNPP where pk_seq in (" + this.ddhId + ") ), npp_dat_fk = " + npp_dat_fk + ", " +
							"	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', tinhthanh_fk = " + tinhthanh_fk + ", quanhuyen_fk = " + quanhuyen_fk + ", nvbh_fk = " + nvbh_fk + ", nvgn_fk = " + nvgn_fk + ", tungay = '" + this.tungay + "', denngay = '" + this.denngay + "' where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Update ERP_XACNHANNHANHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XACNHANNHANHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "delete ERP_XACNHANNHANHANG_DDH where xacnhannhanhang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XACNHANNHANHANG_NHANVIEN where xacnhannhanhang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XACNHANNHANHANG_NHANVIEN " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XACNHANNHANHANG_SANPHAM where xacnhannhanhang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XACNHANNHANHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XACNHANNHANHANG_SANPHAM_CHITIET where xacnhannhanhang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XACNHANNHANHANG_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_XACNHANNHANHANG_DDH(xacnhannhanhang_fk, hoadon_fk) " +
					"select '" + this.id + "', pk_seq from ERP_HOADONNPP where pk_seq in ( " + this.ddhId + " )  ";
			System.out.println("2.chen ERP_XACNHANNHANHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_XACNHANNHANHANG_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.nvIds.trim().length() > 0 )
			{
				query = "Insert ERP_XACNHANNHANHANG_NHANVIEN(xacnhannhanhang_fk, nhanvien_fk) " +
						"select '" + this.id + "', pk_seq from NHANVIEN where pk_seq in ( " + this.nvIds + " )  ";
				System.out.println("2.chen ERP_XACNHANNHANHANG_NHANVIEN: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_XACNHANNHANHANG_NHANVIEN " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert ERP_XACNHANNHANHANG_SANPHAM( xacnhannhanhang_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select IDENT_CURRENT('ERP_XACNHANNHANHANG'), '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i] + "' ";
					
					//System.out.println("1.1.Insert YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_XACNHANNHANHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim() ))
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);

								query = "insert ERP_XACNHANNHANHANG_SANPHAM_CHITIET( xacnhannhanhang_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
										"select IDENT_CURRENT('ERP_XACNHANNHANHANG'), pk_seq, N'" + _sp[3] + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								//System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
				
					}
				}
			}
			
			msg = util.CapNhat_MaChungTu(db, this.id, "ERP_XACNHANNHANHANG", "ngayyeucau");
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	public String[] getSpLoai() {

		return this.spLoai;
	}


	public void setSpLoai(String[] spLoai) {

		this.spLoai = spLoai;
	}


	public String[] getSpScheme() {

		return this.spSCheme;
	}

	
	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}
	
	public String getKhId() 
	{
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {
		
		this.xuatcho = xuatcho;
	}

	
	public ResultSet getSoloOLD() {

		return this.soloOLD;
	}


	public void setSoloOLD(ResultSet soloOLD) {
		
		this.soloOLD = soloOLD;
	}

	public String getNgaygiaohanggui() {

		return this.ngaygiaohangGui;
	}


	public void setNgaygiaohanggui(String ngaygiaohanggui) {
		
		this.ngaygiaohangGui = ngaygiaohanggui;
	}

	public String getPhanloai() 
	{
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) 
	{
		this.phanloai = phanloai;
	}

	
	public String getTinhthanhId() {
		
		return this.tinhthanhId;
	}

	
	public void setTinhthanhId(String tinhthanhId) {
		
		this.tinhthanhId = tinhthanhId;
	}

	
	public ResultSet getTinhthanhRs() {
		
		return this.tinhthanhRs;
	}

	
	public void setTinhthanhRs(ResultSet tinhthanhRs) {
		
		this.tinhthanhRs = tinhthanhRs;
	}

	
	public String getQuanhuyenId() {
		
		return this.quanhuyenId;
	}

	
	public void setQuanhuyenId(String quanhuyenId) {
		
		this.quanhuyenId = quanhuyenId;
	}

	
	public ResultSet getQuanhuyenRs() {
		
		return this.quanhuyenRs;
	}

	
	public void setQuanhuyenRs(ResultSet qunahuyenRs) {
		
		this.quanhuyenRs = qunahuyenRs;
	}

	
	public String getNVGNId() {
		
		return this.nvgnId;
	}

	
	public void setNVGNId(String nvgnId) {
		
		this.nvgnId = nvgnId;
	}

	
	public ResultSet getNVGNRs() {
		
		return this.nvgnRs;
	}

	
	public void setNVGNRs(ResultSet nvgnRs) {
		
		this.nvgnRs = nvgnRs;
	}

	
	public String getNVBHId() {
		
		return this.nvbhId;
	}

	
	public void setNVBHId(String nvbhId) {
		
		this.nvbhId = nvbhId;
	}

	
	public ResultSet getNVBHRs() {
		
		return this.nvbhRs;
	}

	
	public void setNVBHRs(ResultSet nvbhRs) {
		
		this.nvbhRs = nvbhRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNhanvienIds() {
		
		return this.nvIds;
	}

	
	public void setNhanvienIds(String nvIds) {
		
		this.nvIds = nvIds;
	}

	
	public ResultSet getNhanvienRs() {
		
		return this.nvRs;
	}

	
	public void setNhanvienRs(ResultSet nvRs) {
		
		this.nvRs = nvRs;
	}
	
	public String getMachungtu() {
		
		return this.machungtu;
	}

	
	public void setMachungtu(String machungtu) {
		
		this.machungtu = machungtu;
	}

	
	public String getNguoitaodon() {
		
		return this.nguoitao;
	}

	
	public void setNguoitaodon(String nguoitaodon) {
		
		this.nguoitao = nguoitaodon;
	}

	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}	
	
  public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

}
