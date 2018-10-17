package geso.traphaco.erp.beans.huychungtu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
 
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

public class ErpHuychungtubanhang implements IErpHuychungtubanhang 
{
	String userId;
	String id;
	String sochungtu;
	
	String hdkhacId;
	String hdId;
	String pxkId;
	String ddhId;
	String msg;
	
	String loaichungtu;
	String nppId;
	
	String congtyId;
	
	// FORM IN
	String sobienban;
	String ngaybienban;
	String benA_bb;
	String diachiA_bb;
	String dienthoaiA_bb;
	String mstA_bb;
	String ongbaA_bb;
	String chucvuA_bb;
	String benB_bb;
	String diachiB_bb;
	String dienthoaiB_bb;
	String mstB_bb;
	String ongbaB_bb;
	String chucvuB_bb;
	String sohoadon1_bb;
	String sohoadon2_bb;
	String ngayhoadon1_bb;
	String sohoadon3_bb;
	String kyhieu1_bb;
	String kyhieu2_bb;
	String sohoadon4_bb;
	String ngayhoadon2_bb;
	String lydothuhoi_bb;
	String ngaybb;
	
	ResultSet sochungtuRequest, TTHoaDonRequest, TTHoaDonCKRequest;
	private Utility_Kho util_kho=new Utility_Kho();
	dbutils db;
	
	public ErpHuychungtubanhang()
	{
		this.id = "";
		this.sochungtu = "";
		this.hdId = "";
		this.hdkhacId = "";
		this.msg = "";
		this.loaichungtu = "";
		this.nppId = "";
		
		this.sobienban = "";
		this.ngaybienban = "";
		this.benA_bb = "";
		this.diachiA_bb = "";
		this.dienthoaiA_bb = "";
		this.mstA_bb = "";
		this.ongbaA_bb = "";
		this.chucvuA_bb = "";
		this.benB_bb = "";
		this.diachiB_bb = "";
		this.dienthoaiB_bb = "";
		this.mstB_bb = "";
		this.ongbaB_bb = "";
		this.chucvuB_bb = "";
		this.sohoadon1_bb = "";
		this.sohoadon2_bb = "";
		this.ngayhoadon1_bb = "";
		this.sohoadon3_bb = "";
		this.kyhieu1_bb = "";
		this.kyhieu2_bb = "";
		this.sohoadon4_bb = "";
		this.ngayhoadon2_bb = "";
		this.lydothuhoi_bb = "";
		
		this.congtyId = "";
		this.ngaybb = "";
		
		this.db = new dbutils();
	}
	
	public ErpHuychungtubanhang(String id)
	{
		this.id = id;
		this.sochungtu = "";
		this.hdId = "";
		this.msg = "";
		this.hdkhacId = "";
		
		this.loaichungtu = "";
		this.nppId = "";
		
		this.sobienban = "";
		this.ngaybienban = "";
		this.benA_bb = "";
		this.diachiA_bb = "";
		this.dienthoaiA_bb = "";
		this.mstA_bb = "";
		this.ongbaA_bb = "";
		this.chucvuA_bb = "";
		this.benB_bb = "";
		this.diachiB_bb = "";
		this.dienthoaiB_bb = "";
		this.mstB_bb = "";
		this.ongbaB_bb = "";
		this.chucvuB_bb = "";
		this.sohoadon1_bb = "";
		this.sohoadon2_bb = "";
		this.ngayhoadon1_bb = "";
		this.sohoadon3_bb = "";
		this.kyhieu2_bb = "";
		this.sohoadon4_bb = "";
		this.ngayhoadon2_bb = "";
		this.lydothuhoi_bb = "";
		this.kyhieu1_bb = "";
		
		this.congtyId = "";
		this.ngaybb = "";
		
		this.db = new dbutils();
	}
	
	public String getHdkhacId() 
	{
		return this.hdkhacId;
	}

	public void setHdkhacId(String hdkhacId) 
	{
		this.hdkhacId = hdkhacId;
	}
	
	public String getloaichungtu() 
	{
		return this.loaichungtu;
	}

	public void setloaichungtu(String loaichungtu) 
	{
		this.loaichungtu = loaichungtu;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getSoHoadon() 
	{
		return this.hdId;
	}

	public void setSoHoadon(String sohoadon) 
	{
		this.hdId = sohoadon;
	}

	public String getSophieuxuatkho() 
	{
		return this.pxkId;
	}

	public void setSoPhieuxuatkho(String soxuatkho)
	{
		this.pxkId = soxuatkho;
	}

	public String getSoDondathang() 
	{
		return this.ddhId;
	}

	public void setSoDondathang(String sodathang)
	{
		this.ddhId = sodathang;
	}

	
	public void createRs()
	{
		this.hdId = "";
		this.pxkId = "";
		this.ddhId = "";
		
		if(this.sochungtu.length() > 0)
		{
			//Xem so chung tu nguoi dung nhap vao la so nao
			//String prefix = this.sochungtu.substring(0, 3);
			
			System.out.println("sochungtu:" + this.sochungtu);
			if(this.loaichungtu.equals("XUATKHO"))  //Phiếu giao hàng
			{
				this.pxkId = this.sochungtu;
				createPXK();
			}
			else
			{
				if(this.loaichungtu.equals("DONDATHANG"))  //don dat hang
				{
					this.ddhId = this.sochungtu;
					createDDH();
				}
				else if(this.loaichungtu.equals("HOADON"))  //Hoa don
				{
					this.hdId = this.sochungtu;
					createHD();
				}
				else if(this.loaichungtu.equals("HOADONKHAC")) // Hoa don khac
				{
					this.hdkhacId = this.sochungtu;
					createHDK();
				}
				
			}
		}
	}
	
	private void createHDK() 
	{

		try
		{
			String requestHDK = "";
			
			String query = " select pk_seq, pk_seq  as SOCHUNGTU, ngayhoadon as ngaychungtu, " +
					       "        case trangthai when 0 then N'Chưa chốt' when 1 then N'Đã chốt' else N'Đã hủy' end trangthai, N'Hóa đơn phế liệu' as LOAICHUNGTU, NGAYTAO, 1 as stt " +
					       " from erp_hoadonphelieu" +
					       " where pk_seq = '" + this.hdkhacId + "' and trangthai = 1 ";
			
		    System.out.println("Câu lấy chi tiết HĐK "+query);
			requestHDK = query;		
			
			this.sochungtuRequest = db.get(requestHDK);
			
		}
		catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	
		
	}

	private void createHD() 
	{
		try
		{
			
			String requestHD = "select pk_seq, pk_seq as SOCHUNGTU, NGAYXUATHD as ngaychungtu, sohoadon+''+kyhieu sohoadon, " + 
								" (case trangthai when 1 then N'Chờ xác nhận' when 2 then N'Đã xác nhận' when 3 then N'Đã xóa' when 4 then N'Đã in HĐ' when 5 then N'Đã hủy' end ) as trangthai, N'HOADON' as loaichungtu, ngaytao, 2 as stt " +
								"from erp_hoadonnpp where pk_seq = '" + this.sochungtu + "' and loaihoadon IN (0,1) and congty_fk = "+this.congtyId;
			
			requestHD += " order by stt desc ";
			
			System.out.println("Requset la: " + requestHD);
			
			this.sochungtuRequest = db.get(requestHD);
			
			
			requestHD = "select b.PK_SEQ as SPID, b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme, a.vat," +
						"	isnull( TIENVAT, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(vat, 0) / 100 ) ) as tienVAT, a.CHONIN, a.thanhtien  " +
						"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						"where a.hoadon_fk = "+ this.sochungtu +"  " ;
			
			System.out.println("Requset la: " + requestHD);
			
			this.TTHoaDonRequest = db.get(requestHD);
			
			requestHD = "select scheme, SUM( giatri ) as giatri from ERP_HOADONNPP_CHIETKHAU where HOADON_FK = '" + this.sochungtu + "' group by scheme";
			
			System.out.println("Requset la: " + requestHD);
			
			this.TTHoaDonCKRequest = db.get(requestHD);		
						
			
			String ttBienBan = " SELECT A.SOHOADON, A.NGAYXUATHD, A.KYHIEU, B.TEN TENCTY, B.DIACHI, B.MASOTHUE, B.DIENTHOAI  " +
							   " FROM ERP_HOADONNPP A INNER JOIN ERP_CONGTY B ON A.CONGTY_FK = B.PK_SEQ " +
							   " WHERE A.PK_SEQ = "+this.sochungtu + " AND B.PK_SEQ = "+this.congtyId ;
			
			System.out.println(ttBienBan);
			ResultSet Rs = db.get(ttBienBan);
			
			if(Rs!=null)
			{
				while(Rs.next())
				{
					this.sohoadon1_bb = Rs.getString("SOHOADON");
					this.kyhieu1_bb = Rs.getString("KYHIEU");
					this.sohoadon2_bb = Rs.getString("SOHOADON");
					this.ngayhoadon1_bb = Rs.getString("NGAYXUATHD");
					this.benA_bb = Rs.getString("TENCTY"); 
					this.diachiA_bb = Rs.getString("DIACHI"); 
					this.mstA_bb = Rs.getString("MASOTHUE"); 
					this.dienthoaiA_bb = Rs.getString("DIENTHOAI"); 
				}
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}

	private void createDDH() 
	{
		try
		{
			String requestHD = "";
			String requestXK = "";
			
			String query = " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYXUAT as ngaychungtu, (case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' when 2 then N'Đã xóa' end ) trangthai, N'Xuất kho' as LOAICHUNGTU, a.NGAYTAO, 3 as stt " +
						   " from erp_xuatkho a inner join erp_hoadon_ddh b on a.hoadon_fk = b.hoadon_fk " +
						   " where b.ddh_fk = '" + this.ddhId.substring(3, this.ddhId.length()) + "' and a.trangthai !=2  ";
		
			requestXK = query;
			//System.out.println("Khoi tao xuat kho: " + query);
				
			ResultSet rsDetail = db.get(query);
			if(rsDetail != null)
			{
				while(rsDetail.next())
				{
					this.pxkId = rsDetail.getString("SOCHUNGTU");
				}
				rsDetail.close();
			}
			
			query = "select a.pk_seq, a.pk_seq as SOCHUNGTU, a.NGAYXUATHD as ngaychungtu, (case a.trangthai when 0 then N'Chưa chốt' when 1 then N'Đã chốt' when 2 then N'Đã xóa' when 4 then N'Đã xuất kho(chưa hoàn tất)' when 5 then N'Hoàn tất xuất kho' end) trangthai , N'Xuất hóa đơn' as loaichungtu, ngaytao, 2 as stt " +
					"from erp_hoadon a inner join erp_hoadon_ddh b on a.pk_seq = b.hoadon_fk " +
					"where a.trangthai not in (2) and b.ddh_fk = '" + this.ddhId.substring(3, this.ddhId.length()) + "'";
			
			requestHD = query;
			rsDetail = db.get(query);
			if(rsDetail != null)
			{
				if(rsDetail.next())
				{
					this.hdId = rsDetail.getString("SOCHUNGTU");
				}
				rsDetail.close();
			}
			
			String requestDdh = 
				    " select pk_seq, prefix + cast(pk_seq as varchar(10)) as SOCHUNGTU, NGAYDAT as ngaychungtu, "+
				    " (case trangthai when 0 then N'Chờ xử lý' when 1 then N'Chưa chốt' when 2 then N'Chờ Kế toán duyệt' " +
					" when 3 then N'Chờ giám đốc duyệt' when 4 then N'Đã duyệt' when 5 then N'Ðã xuất HD GTGT' when 6 then N'Ðã xuất  kho' when 7 then N'Đã xóa' else 'Hoàn tất' end ) as trangthai, " +
				    " N'Đơn đặt hàng' as loaichungtu, ngaydat as ngaytao, 1 as stt " +
					" from Erp_dondathang " +
					" where trangthai != '7' and pk_seq = '" + this.ddhId.substring(3, this.ddhId.length()) + "'";
			
			if(requestHD.length() > 0 )
				requestDdh += " union all " + requestHD;
			
			if(requestXK.length() > 0)
				requestDdh += " union all " + requestXK;
			
			requestDdh += " order by stt desc ";
			
			System.out.println("Requset la: " + requestDdh);
			
			this.sochungtuRequest = db.get(requestDdh);
			
		}
		catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}

	private void createPXK() 
	{
		try
		{
			String requestXK = "";
			String requestDDH = "";
			
			String query = "select pk_seq, cast(pk_seq as varchar(10)) as SOCHUNGTU, NgayDonHang as ngaychungtu, " +
						   "(case trangthai when 0 then N'Chờ xử lý' when 1 then N'Chờ duyệt' when 2 then N'Đã duyệt' " +
						   "       when 3 then N'Đã hủy' when 4 then N'Hoàn tất' end ) as trangthai, " +
						   " 	N'Đơn đặt hàng' as loaichungtu, ngaytao, 1 as stt " +
						   "from ERP_DONDATHANGNPP where trangthai not in ( 3 ) and pk_seq in ( select ddh_fk from erp_ycxuatkhonpp where pk_seq = '" + this.pxkId + "')";
		
			requestDDH = query;
			System.out.println("Khoi tao don dat hang: " + query);
				
			ResultSet rsDetail = db.get(query);
			String ddh = "";
			if(rsDetail != null)
			{
				while(rsDetail.next())
				{
					this.ddhId = rsDetail.getString("SOCHUNGTU") + ", ";
					ddh = rsDetail.getString("pk_seq") + ", ";
				}
				rsDetail.close();
				
				if(this.ddhId.length() > 0)
				{
					this.ddhId = this.ddhId.substring(0, this.ddhId.length() - 2);
					ddh = ddh.substring(0, ddh.length() - 2);
				}
			}
			
			if(ddh.length() > 0) 
			{
				query = " SELECT a.pk_seq, cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NgayYeuCau as ngaychungtu, " +
						"	case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' when 2 then N'Đã xóa' end, N'Phiếu giao hàng' as LOAICHUNGTU, a.NGAYTAO, 3 as stt  " +
						" FROM   erp_ycxuatkhonpp a   " +
						" WHERE  a.pk_seq = '" + this.pxkId + "' and a.trangthai != 2 ";
				
				requestXK = query;
				rsDetail = db.get(query);
				if(rsDetail != null)
				{
					if(rsDetail.next())
					{
						this.pxkId = rsDetail.getString("SOCHUNGTU") + ", ";
					}
					rsDetail.close();
					if(this.pxkId.length() > 0)
						this.pxkId = this.pxkId.substring(0, this.pxkId.length() - 2);
				}
			}
			
			String requestHD = "select pk_seq, pk_seq as SOCHUNGTU, NGAYXUATHD as ngaychungtu, " + 
								" (case trangthai when 1 then N'Chờ xác nhận' when 2 then N'Đã xác nhận' when 3 then N'Đã xóa' when 4 then N'Đã in HĐ' when 5 then N'Đã hủy' end ) as trangthai, N'Xuất hóa đơn' as loaichungtu, ngaytao, 2 as stt " +
								"from erp_hoadonnpp where pk_seq = ( select hoadon_fk from erp_ycxuatkhonpp where pk_seq = '" + this.pxkId + "' ) ";
			
			if(requestXK.length() > 0 )
				requestHD += " union all " + requestXK;
			
			if(requestDDH.length() > 0)
				requestHD += " union all " + requestDDH;
			
			requestHD += " order by stt desc ";
			
			System.out.println("Requset la: " + requestHD);
			
			this.sochungtuRequest = db.get(requestHD);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public void init() 
	{
		String query = " select sochungtu, isnull(sodonhang, '') as sodonhang, isnull(sophieuxuatkho, '') as sophieuxuatkho," +
				       "       isnull(sohoadon, '') as sohoadon, loaichungtu, isnull(sobienban, '') sobienban, isnull(ngaybienban, '') ngaybienban, isnull(benA_bb, '') benA_bb, isnull(diachiA_bb, '') diachiA_bb," +
				       "  	   isnull(dienthoaiA_bb,'')  as dienthoaiA_bb, isnull(mstA_bb, '') mstA_bb, isnull(OngbaA_bb, '') OngbaA_bb, isnull(chucvuA_bb, '') chucvuA_bb, isnull(benB_bb,'') benB_bb," +
				       "	   isnull(diachiB_bb, '') diachiB_bb, isnull(dienthoaiB_bb, '') dienthoaiB_bb, isnull(OngbaB_bb, '') OngbaB_bb, isnull(chucvuB_bb, '') chucvuB_bb, " +
				       "	   isnull(sohoadon1_bb, '') sohoadon1_bb, isnull(kyhieu1_bb, '') kyhieu1_bb, isnull(sohoadon2_bb, '') sohoadon2_bb, " +
				       "	   isnull(ngayhoadon1_bb, '') ngayhoadon1_bb, isnull(sohoadon3_bb, '')  sohoadon3_bb, isnull(kyhieu2_bb, '') kyhieu2_bb, "+
				       "       isnull(sohoadon4_bb, '') sohoadon4_bb, isnull(ngayhoadon2_bb,'') ngayhoadon2_bb, isnull(lydothuhoi_bb, '') lydothuhoi_bb, isnull(mstb_bb, '') mstb_bb, isnull(ngaybb, '') ngaybb  "+
					   " from erp_huychungtubanhang" +
					   " where pk_seq = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				this.sochungtu = rs.getString("sochungtu");
				this.hdId = rs.getString("sohoadon");
				this.ddhId = rs.getString("sodonhang");
				this.pxkId = rs.getString("sophieuxuatkho");
				this.loaichungtu = rs.getString("loaichungtu");
				this.sobienban = rs.getString("sobienban");
				this.ngaybienban = rs.getString("ngaybienban");
				this.benA_bb = rs.getString("benA_bb");
				this.diachiA_bb = rs.getString("diachiA_bb");
				this.dienthoaiA_bb = rs.getString("dienthoaiA_bb");
				this.mstA_bb = rs.getString("mstA_bb");
				this.mstB_bb = rs.getString("mstB_bb");
				this.ongbaA_bb = rs.getString("OngbaA_bb");
				this.chucvuA_bb = rs.getString("chucvuA_bb");
				this.benB_bb = rs.getString("benB_bb");
				this.diachiA_bb = rs.getString("diachiA_bb");
				this.diachiB_bb = rs.getString("diachiB_bb");
				this.dienthoaiB_bb = rs.getString("dienthoaiB_bb");
				this.ongbaB_bb = rs.getString("OngbaB_bb");
				this.chucvuB_bb = rs.getString("chucvuB_bb");
				this.sohoadon1_bb = rs.getString("sohoadon1_bb");
				this.kyhieu1_bb = rs.getString("kyhieu1_bb");
				this.sohoadon2_bb = rs.getString("sohoadon2_bb");
				this.ngayhoadon1_bb = rs.getString("ngayhoadon1_bb");
				this.sohoadon3_bb = rs.getString("sohoadon3_bb");
				this.kyhieu2_bb = rs.getString("kyhieu2_bb");
				this.sohoadon4_bb = rs.getString("sohoadon4_bb");
				this.ngayhoadon2_bb = rs.getString("ngayhoadon2_bb");
				this.lydothuhoi_bb = rs.getString("lydothuhoi_bb");
				this.ngaybb = rs.getString("ngaybb");
			}
			rs.close();
			
			query = "select  sochungtu, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu as stt from erp_huychungtubanhang_chungtu " +
					"where hctbh_fk = '" + this.id + "' order by thutu desc, sochungtu desc";
			
			this.sochungtuRequest = db.get(query);
		}
		catch (SQLException e) {e.printStackTrace();}
		
		//createHD();
		
		String requestHD = "select pk_seq, pk_seq as SOCHUNGTU, NGAYXUATHD as ngaychungtu, sohoadon+''+kyhieu sohoadon, " + 
		" (case trangthai when 1 then N'Chờ xác nhận' when 2 then N'Đã xác nhận' when 3 then N'Đã xóa' when 4 then N'Đã in HĐ' when 5 then N'Đã hủy' end ) as trangthai, N'HOADON' as loaichungtu, ngaytao, 2 as stt " +
		"from erp_hoadonnpp where pk_seq = '" + this.sochungtu + "' and loaihoadon IN (0,1) AND CONGTY_FK = "+this.congtyId;

		requestHD += " order by stt desc ";
		
		System.out.println("Requset la: " + requestHD);
		
		this.sochungtuRequest = db.get(requestHD);
		
		
		requestHD = "select b.PK_SEQ as SPID, b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme, a.vat," +
		"	isnull( TIENVAT, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(vat, 0) / 100 ) ) as tienVAT, a.CHONIN, a.thanhtien  " +
		"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
		"where a.hoadon_fk = "+ this.sochungtu +"  " ;
		
		System.out.println("Requset la: " + requestHD);
		
		this.TTHoaDonRequest = db.get(requestHD);
		
		requestHD = "select scheme, SUM( giatri ) as giatri from ERP_HOADONNPP_CHIETKHAU where HOADON_FK = '" + this.sochungtu + "' group by scheme";
		
		System.out.println("Requset la: " + requestHD);
		
		this.TTHoaDonCKRequest = db.get(requestHD);	
	}

	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu)
	{
		if(this.sochungtu.trim().length() <= 0)
		{
			this.msg = "Không có chứng từ hủy nào được chọn";
			return false;
		}
		
		if(soct == null || soct.length <=0)
		{
			this.msg = "Không có chứng từ hủy nào được chọn";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  " select count(pk_seq) as sodong" +
					        " from erp_huychungtubanhang " +
							" where trangthai != '2' and sochungtu = '" + this.sochungtu + "'" +
							" and loaichungtu = '"+ this.loaichungtu +"' and congty_fk = "+this.congtyId;
			
			//System.out.println("Cau lenh check: " + query);
			
			ResultSet rsCheck = db.get(query);
			
			int count = 0;
			if(rsCheck.next())
			{
				count = rsCheck.getInt("sodong");
				rsCheck.close();
			}
			
			if(count > 0)
			{
				this.msg = "Số chứng từ này đã hủy rồi, vui lòng nhập số chứng từ khác";
				System.out.println(this.msg);
				return false;
			}
			
			if( this.sochungtu.trim().length() < 6 )
			{
				this.msg = "Số chứng từ hủy không hợp lệ. Vui lòng kiểm tra lại.";
				return false;
			}
			
			//
			if(!this.revertChungtu(socthuy, this.id, db))
			{
				db.getConnection().rollback();
				return false;
			}
			
			
			//khong cam ham tao moi, mac dinh tao moi xong se chot luon
			query = "insert erp_huychungtubanhang(sochungtu, trangthai, sodonhang, sophieuxuatkho, sohoadon, ngaytao, nguoitao, ngaysua, nguoisua, loaichungtu, congty_fk, sobienban," +
					" ngaybienban, benA_bb, diachiA_bb, dienthoaiA_bb, MstA_bb, MstB_bb , OngbaA_bb, chucvuA_bb, benB_bb, diachiB_bb, dienthoaiB_bb, OngbaB_bb, " +
					" chucvuB_bb, sohoadon1_bb, kyhieu1_bb, sohoadon2_bb, ngayhoadon1_bb, sohoadon3_bb, kyhieu2_bb, sohoadon4_bb, ngayhoadon2_bb, Lydothuhoi_bb, ngaybb) " +
					"values('" + this.sochungtu + "', '0', '"  + this.ddhId + "', '" + this.pxkId + "', '" + this.hdId + "', '" + this.getDateTime() + "', " +
					"'" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', N'"+ this.loaichungtu +"', "+this.congtyId+", N'"+this.sobienban+"'," +
					" N'"+this.ngaybienban+"', N'"+this.benA_bb+"', N'"+this.diachiA_bb+"', N'"+this.dienthoaiA_bb+"', N'"+this.mstA_bb+"',  N'"+this.mstB_bb+"', N'"+this.ongbaA_bb+"', N'"+this.chucvuA_bb+"'," +
					" N'"+this.benB_bb+"', N'"+this.diachiB_bb+"', N'"+this.dienthoaiB_bb+"', N'"+this.ongbaB_bb+"'," +
					" N'"+this.chucvuB_bb+"', N'"+this.sohoadon2_bb+"', N'"+this.kyhieu1_bb+"', N'"+sohoadon2_bb+"', N'"+this.ngayhoadon1_bb+"', N'"+this.sohoadon3_bb+"', N'"+this.kyhieu2_bb+"'," +
					" N'"+this.sohoadon4_bb+"', N'"+this.ngayhoadon2_bb+"', N'"+this.lydothuhoi_bb+"', N'"+this.ngaybb+"')";
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi erp_huychungtubanhang, " + query;
				System.out.println(this.msg);
				return false;
			}
			
			String hctbhCurrent = "";
			query = "select IDENT_CURRENT('erp_huychungtubanhang') as hctbhId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				hctbhCurrent = rsTthd.getString("hctbhId");
				rsTthd.close();
			}
			
			this.id = hctbhCurrent;
			
			for(int i = 0; i < socthuy.length; i++)
			{
				query = "insert erp_huychungtubanhang_chungtu(hctbh_fk, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu, ngaytao) " +
						"values('" + hctbhCurrent + "', '" + thutu[i] + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'" + loaichungtu[i] + "', '" + ngaytao[i] + "')";
				
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi erp_huychungtubanhang_chungtu, " + query;
					System.out.println(this.msg);
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}

	private boolean revertChungtu(String[] socthuy, String hctmhCurrent, dbutils db) 
	{
		try
		{	
			String query = "";

			if(this.loaichungtu.equals("XUATKHO"))  //Xuat kho
			{
				this.msg = this.HuyPhieuGiaoHang(this.sochungtu, this.userId, db);
				if(this.msg.trim().length() > 0)
				{
					db.getConnection().rollback();
					return false;
				}
			} 
			else
			{
				if(this.loaichungtu.equals("DONDATHANG"))
				{
					//1. KIỂM TRA ĐƠN HÀNG ĐÃ XUẤT KHO CHƯA
					query = " SELECT count(*) as sodong " +
							" FROM erp_ycxuatkhonpp a  " +
							" WHERE ddh_fk = '" + this.sochungtu + "' " +
							" and trangthai not in (0, 1) ";
					
					System.out.println("1.Check xuat kho: " + query);
					boolean daxuatkho = false;
					ResultSet rsCheck = db.get(query);
					if(rsCheck != null)
					{
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") > 0)
								daxuatkho = true;
						}
						rsCheck.close();
					}
					
					if(daxuatkho)
					{
						this.msg = "Đơn đặt hàng " + this.sochungtu + ", đã có phiếu giao hàng, bạn phải hủy phiếu giao hàng trước.";
						return  false;
					}
					
					//KIỂM TRA XEM ĐƠN HÀNG ĐÃ XUẤT HÓA ĐƠN CHƯA
					query = " SELECT 	count(a.pk_seq) as sodong " +
							" FROM 		erp_hoadonnpp a inner join erp_hoadonnpp_ddh b on a.pk_seq = b.hoadonnpp_fk " +
							" WHERE 	trangthai != 3  and ddh_fk = '" + this.sochungtu + "'";
					
					System.out.println("2.Check hoa don: " + query);
					
					boolean daxuathoadon = false;
					rsCheck = db.get(query);
					if(rsCheck != null)
					{
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") > 0)
								daxuathoadon = true;
						}
						rsCheck.close();
					}
					
					if(daxuathoadon)
					{
						this.msg = "Đơn đặt hàng " + this.sochungtu + ", đã có xuất hóa đơn tài chính, bạn phải hủy xuất hóa đơn tài chính trước.";
						return  false;
					}
					
					
					this.msg = "Đơn đặt hàng " + this.sochungtu + ", chưa có giao hàng, chưa có xuất hóa đơn tài chính, bạn có thể bỏ duyệt để chỉnh sửa.";
					return false;
					
				}
				else if(this.loaichungtu.equals("HOADON")) 
				{
					String sohoadon = this.sochungtu;
					String ddk_fk = "";
					// kiểm tra hóa đơn này có trong bảng kê không
					
					query = " SELECT count(a.PK_SEQ) COUNT \n" +
							" FROM ERP_BANGKETHUTIEN A INNER JOIN ERP_BANGKETHUTIEN_HOADON B ON A.pk_seq = B.bangke_fk \n"+
							" WHERE A.trangthai != 2 and B.HOADON_FK = "+this.sochungtu;
					ResultSet rsCheck1 = db.get(query);
					
					boolean bk = false;
					
					if(rsCheck1 != null)
					{
						if(rsCheck1.next())
						{
							if(rsCheck1.getInt("COUNT") > 0)
								bk = true;
						}
						rsCheck1.close();
					}
					
					if(bk == true)
					{
						this.msg = "Hóa đơn này đã làm bảng kê. Bạn phải xóa bảng kê trước. ";
						return false;
					}
					
					// kiểm tra hóa đơn này có trong thu tiền không
					
					query = " SELECT count(a.PK_SEQ) COUNT \n" +
							" FROM ERP_THUTIEN A INNER JOIN ERP_THUTIEN_HOADON B ON A.pk_seq = B.thutien_fk \n"+
							" WHERE A.trangthai != 2 and B.HOADON_FK = "+this.sochungtu;
					
					rsCheck1 = db.get(query);
					
					bk = false;
					
					if(rsCheck1 != null)
					{
						if(rsCheck1.next())
						{
							if(rsCheck1.getInt("COUNT") > 0)
								bk = true;
						}
						rsCheck1.close();
					}
					
					if(bk == true)
					{
						this.msg = "Hóa đơn này đã làm thu tiền. Bạn phải xóa thu tiền trước. ";
						return false;
					}
					
					// chỉ cho hủy những hóa đơn mà có trạng thái đã in
					
					query = "SELECT count(*) as sodong, isnull((SELECT TRANGTHAI FROM ERP_HOADONNPP WHERE PK_SEQ = "+sohoadon+"),0) TRANGTHAI, (SELECT DDH_FK FROM ERP_HOADONNPP_DDH WHERE HOADONNPP_FK =  "+sohoadon+") DDH_FK \n" +
							"FROM   erp_ycxuatkhonpp " +
							"WHERE  trangthai in ( 0, 1 ) and hoadon_fk = "+ sohoadon + " " ;
								
					System.out.println(":::: CHECK PHIEU GIAO HANG: " + query );
					ResultSet rsCheck = db.get(query);
					boolean danhanhang = false;
					boolean trangthaihd = false;
					
					if(rsCheck != null)
					{
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") > 0)
								danhanhang = true;
							
							if(rsCheck.getInt("TRANGTHAI") == 4 || rsCheck.getInt("TRANGTHAI") == 2)
								trangthaihd = true;
							
							ddk_fk = rsCheck.getString("DDH_FK");
						}
						rsCheck.close();
					}
				
					if(trangthaihd == false)
					{
						this.msg = "Bạn chỉ được hủy những hóa đơn có trạng thái là in hoặc đã xác nhận! ";
						return false;
					}
					
					if(danhanhang)
					{
						this.msg = "Hóa đơn " + this.sochungtu + " đã có phiếu giao hàng, bạn phải hủy phiếu giao hàng trước.";
						return false;
					}
					
					// KIỂM TRA XEM ĐƠN HÀNG NÀY CÓ NHIỀU HÓA ĐƠN HAY KHÔNG.
					query = "SELECT COUNT(A.PK_SEQ) DEM FROM ERP_HOADONNPP A  WHERE A.PK_SEQ IN ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = (SELECT DDH_FK FROM ERP_HOADONNPP_DDH WHERE HOADONNPP_FK = "+this.sochungtu+") ) AND A.TRANGTHAI NOT IN (3,5) \n";;
					
					ResultSet dem = db.get(query);
					int i = 0;
					if(dem != null)
					{
						while(dem.next())
						{
							i = Integer.parseInt(dem.getString("DEM"));
						}
						dem.close();
					}
						
					if(i > 1)
					{
						msg = "Lệnh xuất hàng "+ ddk_fk + " này có nhiều hóa đơn. Không thể hủy hóa đơn này!";
						db.getConnection().rollback();
						return false;
					}
										
				}
				else if(this.loaichungtu.equals("HOADONKHAC")) // Hóa đơn phế liệu
				{
					System.out.println("VAoDay");
					String trangthaihd= "";
					//CHI DUOC HUY PHIEU XUAT KHO DA CHOT
					query = " select TRANGTHAI from erp_hoadonphelieu where PK_SEQ = '" + this.sochungtu + "' ";
					
					String _trangthai = "";
					ResultSet rs = db.get(query);
					if(rs != null)
					{
						if(rs.next())
						{
							trangthaihd = rs.getString("trangthai") == null ? "" : rs.getString("trangthai");
							
							rs.close();
						}
					}
					
					if(  trangthaihd.equals("0") || trangthaihd.equals("2")   )
					{
						this.msg = "Trạng thái Hóa đơn phế liệu này không hợp lệ, bạn chỉ có thể hủy những hóa đơn đã chốt";
						return false;
					}
					
					// Cập nhật trạng thái cho Hóa đơn phế liệu: Đã hủy
					
					query = "update erp_hoadonphelieu set trangthai = '2' where pk_seq = '" + this.sochungtu + "'";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật Hóa đơn phế liệu " + query;
						db.getConnection().rollback();
						return false;
					}
					
					// Revert_Kế toán
					if(!Revert_KeToan("Hóa đơn phế liệu", this.sochungtu))
					{
						db.getConnection().rollback();
						return false;
					}
				}
			}
		}
		catch (Exception e) 
		{
			try
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			
			this.msg = "115.Exception: " + e.getMessage();
			return false;
		}
		return true;
	}
	
	private boolean Revert_KeToan(String loaichungtu, String sochungtu)
	{
		//CHECK XEM NGAY CHUNG TU CUA PHIEU MUON HUY CO HOP LE KHONG????
		String colNAME = "";
		String tableNAME = "";
		if(loaichungtu.equals("XUATKHO"))
		{
			colNAME = "ngaychot";
			tableNAME = "ERP_XUATKHO";
		}
		else if(loaichungtu.equals("HOADON"))
		{			
				colNAME = "ngayxuathd";
				tableNAME = "ERP_HOADONNPP";
		}
		else if(loaichungtu.equals("HOADONKHAC"))
		{			
				colNAME = "ngayghinhan";
				tableNAME = "ERP_HOADONPHELIEU";
		}
		
		String query = " select " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
		System.out.println("____LAY NGAY NGHIEP VU: " + loaichungtu);
		
		ResultSet ngayRS = db.get(query);
		String ngaynghiepvu = "";
		
		try 
		{
			if(ngayRS.next())
			{
				ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
			}
			ngayRS.close();
		} 
		catch (Exception e) { }
		
		
		Utility util=new Utility();
		String nam = ngaynghiepvu.substring(0, 4);
		String thang = ngaynghiepvu.substring(5, 7);
		String msg1= util.CheckNgayGhiNhanHopLe(db, ngaynghiepvu, " ", " ")  ;
		if(  msg1.length() > 0 )
		{
			this.msg = msg1;
			return false;
		}
				
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
			    "from ERP_PHATSINHKETOAN " +
			    "where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "' ";
		
		System.out.println("1.CHECK NO-CO: " + query);
		
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
					this.msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
					return false;
				}
				
			}
			rsPSKT.close();
		} 
		catch (Exception e) { }
				
		
		//HỦY KẾ TOÁN ĐÃ GHI NHẬN
		query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Hóa đơn tài chính' and SOCHUNGTU = '"+sochungtu+"'";	
		System.out.println("1.CHECK NO-CO: " + query);
		
		if(!db.update(query))
		{
			msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
			return false;
		}			
		
		// XÓA ĐƠN HÀNG
		
		query = "update ERP_DONDATHANGNPP set trangthai = '3' where pk_seq = (SELECT DDH_FK FROM ERP_HOADONNPP_DDH WHERE HOADONNPP_FK = "+sochungtu+" ) ";
		if(!db.update(query))
		{
			msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
			return false;
		}
		
		return true;
	}
 

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu;
	}

	public ResultSet getSochungtuRequest() 
	{
		return this.sochungtuRequest;
	}

	public void setSochungtuRequest(ResultSet soctRequest) 
	{
		this.sochungtuRequest = soctRequest;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String HuyHoaDon(String lsxId, String userId, dbutils db) 
	{
		String msg = "";

		try
		{	
			String query = "";
						
			// Kiểm tra import =1 thì k cho hủy
			query = "select isnull(import, 0), pk_seq as import from ERP_DONDATHANGNPP where pk_seq in (select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "')";
			
			System.out.println(query);
			ResultSet rsKT = db.get(query);
			int ktra = 0;
			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					ktra = rsKT.getInt("import");
				}
				rsKT.close();
			}
			
			if(ktra == 1)
			{
				msg = "Đơn hàng này GESO import nên không hủy được. ";
				//db.getConnection().rollback();
				return msg;
			}
			else
			{	
				// Lấy mã đơn đặt hàng
				query = "select a.PK_SEQ, a.NPP_DAT_FK, c.DUNGCHUNGKENH, ( select khachhangKG_FK from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "' ) ) as khachhangKG_FK \n" + 
						"from ERP_DondathangNPP a inner join ERP_HOADONNPP_DDH b on a.PK_SEQ = b.DDH_FK  " +
						"		inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
						"where HOADONNPP_FK = '" + lsxId + "'";
				
				System.out.println(query);
				
				ResultSet rsDDH = db.get(query);
				String dondathang_fk = "";
				String npp_dat_fk = "";
				String khachhangKG_FK = "";

				if(rsDDH != null)
				{				
					if(rsDDH.next())
					{
						dondathang_fk = rsDDH.getString("PK_SEQ");
						khachhangKG_FK = rsDDH.getString("khachhangKG_FK") == null ? "" : rsDDH.getString("khachhangKG_FK");
					}
	
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
								"		where a.dondathang_fk in (  " + dondathang_fk + "  ) " + 
								"	union ALL	 " + 
								"		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								"		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								"				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								"		where a.DONDATHANGID in (  " + dondathang_fk + "  )   " + 
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
								"		where a.dondathang_fk in (  " + dondathang_fk + "  ) " + 
								"	union ALL	 " + 
								"		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								"		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								"				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								"		where a.DONDATHANGID in (  " + dondathang_fk + "  )   " + 
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
								"	where a.PK_SEQ = '" + dondathang_fk + "'   " +
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
								"	where a.PK_SEQ = '" + dondathang_fk + "'   " +
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
					
					
					// KIỂM TRA XEM ĐƠN HÀNG NÀY CÓ NHIỀU HÓA ĐƠN HAY KHÔNG.
					query = "SELECT COUNT(A.PK_SEQ) DEM FROM ERP_HOADONNPP A  WHERE A.PK_SEQ IN ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = '" + dondathang_fk + "' ) AND A.TRANGTHAI NOT IN (3,5) \n";
					
					System.out.println("---1.1 CAP NHAT KHO: " + query);
					
					ResultSet dem = db.get(query);
					int i = 0;
					if(dem != null)
					{
						while(dem.next())
						{
							i = Integer.parseInt(dem.getString("DEM"));
						}
						dem.close();
					}
						
					if(i > 1)
					{
						msg = "Lệnh xuất hàng "+ dondathang_fk + " này có nhiều hóa đơn. Không thể hủy hóa đơn này!"+ query;
						db.getConnection().rollback();
						return msg;
					}
					
					
					query = "update ERP_DONDATHANGNPP set trangthai = '3' where pk_seq = '" + lsxId + "' \n";
					
					System.out.println("---1.1 CAP NHAT KHO: " + query);
					
					if(!db.update(query))
					{
						msg = "Khong the chot: " + query;
						db.getConnection().rollback();
						return msg;
					}
										
					//Xóa hóa đơn, mở lại trạng thái đơn hàng --> chỉ được thực hiện khi chưa chốt phiếu giao hàng ( mở hết tất cả hóa đơn của đơn hàng này )
					query = "update ERP_HOADONNPP set trangthai = '5' where pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = '" + dondathang_fk + "' ) \n";
					
					System.out.println("---2.1 CAP NHAT ERP_HOADONNPP: " + query);
					
					if(!db.update(query))
					{
						msg = "Không thể hủy ERP_HOADONNPP " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = "update ERP_HUYCHUNGTUBANHANG set trangthai = '1' where pk_seq = '" + this.id + "'  \n";
					
					System.out.println("---2.1 CAP NHAT ERP_HUYCHUNGTUBANHANG: " + query);
					
					if(!db.update(query))
					{
						msg = "Không thể hủy ERP_HOADONNPP " + query;
						db.getConnection().rollback();
						return msg;
					}
					
				 } 
			}
			
			//db.getConnection().commit();
			//db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			//db.update("rollback");
			//db.shutDown();
			return "Exception: " + e.getMessage();
		}		
		
		return "";
	}
	
	private String HuyPhieuGiaoHang(String ycxkId, String userId, dbutils db)
	{
		String msg = "";
		try
		{			
			String query = "";
			
			String hoadonId = ""; 
			String npp_dat_fk = "";
			
			query = "select a.NPP_DAT_FK, a.hoadon_fk "
					+ "from erp_ycxuatkhonpp a inner join ERP_DONDATHANGNPP b on a.ddh_fk = b.PK_SEQ  "
					+ "where a.PK_SEQ = '" + this.pxkId + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				hoadonId = rs.getString("hoadon_fk") == null ? "" : rs.getString("hoadon_fk") ;
				npp_dat_fk = rs.getString("npp_dat_fk") == null ? "" : rs.getString("npp_dat_fk");
				rs.close();
			}
			
			//CHECK XEM DUOI NPP DA NHAN HANG CHUA
			if (npp_dat_fk.trim().length() > 0)
			{
				//Tu dong tao nhan hang
				query = " select count(*) as soDONG from NHAPHANG " + 
						" where sochungtu = ( select SOHOADON from ERP_HOADONNPP where PK_SEQ = '" + hoadonId + "' ) and trangthai = '1' ";
				int soDONG = 0;
				rs = db.get(query);
				{
					if(rs.next())
						soDONG = rs.getInt("soDONG");
					rs.close();
				}
				
				if(soDONG > 0)
				{
					msg = "Hóa đơn đã có nhận hàng, bạn không thể hủy / xóa ";
					return msg;
				}
				
				//XOA NHAN HANG
				query = " delete NHAPHANG_SP where nhaphang_fk in ( select pk_seq from NHAPHANG where sochungtu = ( select SOHOADON from ERP_HOADONNPP where PK_SEQ = '" + hoadonId + "' ) )  ";
				System.out.println("::: XOA NHAP HANG - SP: " + query);
				if(!db.update(query))
				{
					msg = "Không thể cập nhật NHAPHANG_SP:  " + query;
					return msg;
				}
				
				query = " delete NHAPHANG where sochungtu = ( select SOHOADON from ERP_HOADONNPP where PK_SEQ = '" + hoadonId + "' )   ";
				System.out.println("::: XOA NHAP HANG: " + query);
				if(!db.update(query))
				{
					msg = "Không thể cập nhật NHAPHANG:  " + query;
					return msg;
				}
			}
			
			//CHECK XEM PHIẾU GIAO HÀNG ĐÃ CHỐT CHƯA
			/*query = " select pk_seq, trangthai from ERP_YCXUATKHONPP where pk_seq = '" + ycxkId + "' and trangthai in ( 0, 1 ) ";
			String ycxk_fk = "";
			String trangthai = "";
			rs = db.get(query);
			{
				if(rs.next())
				{
					ycxk_fk = rs.getString("pk_seq");
					trangthai = rs.getString("trangthai");
				}
				rs.close();
			}
			
			if(trangthai.equals("0"))
			{
				msg = "Phiếu giao hàng chưa chốt. Bạn có thể tự xóa phiếu giao hàng trước ";
				return msg;
			}
			
			if(ycxk_fk.trim().length() > 0)
			{
				//XOA PHIEU GIAO HANG
				query = " update ERP_YCXUATKHONPP set trangthai = '2' where PK_SEQ = '" + ycxk_fk + "'  ";
				System.out.println("::: XOA YCXK: " + query);
				if(!db.update(query))
				{
					msg = "Không thể cập nhật ERP_YCXUATKHONPP:  " + query;
					return msg;
				}
				
				//KHO TONG DOI LAI TANG BOOKED, TANG SO LUONG
				query = "update kho set kho.AVAILABLE = kho.AVAILABLE + xuat.soluong,  " +
						"			   kho.SOLUONG = kho.SOLUONG + xuat.soluong	  " +
						"from NHAPP_KHO kho inner join  " +
						"(  " +
						//"	select a.kho_fk, a.NPP_FK, b.SANPHAM_FK, case when isnull(c.dungchungkenh, 0) = 0 then a.nhomkenh_fk else 100000 end as nhomkenh_fk,  SUM(b.soluongXUAT) as soluong  " +
						"	select a.kho_fk, a.NPP_FK, b.SANPHAM_FK, 100000 as nhomkenh_fk,  SUM(b.soluongXUAT) as soluong  " +
						"	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.YCXK_FK " +
						"			inner join NHAPHANPHOI c on a.npp_FK = c.pk_seq  " +
						"	where a.PK_SEQ = '" + ycxk_fk + "' and b.soluongXUAT > 0 " +
						"	group by a.kho_fk, a.kbh_fk, c.dungchungkenh, a.NPP_FK, b.SANPHAM_FK  " +
						")  " +
						"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.nhomkenh_fk = xuat.nhomkenh_fk and kho.KHO_FK = xuat.kho_fk ";
				System.out.println("TANG KHO NGUOC LAI: " + query);
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Không thể cập nhật NHAPP_KHO:  " + query;
					return msg;
				}
				
				query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong,  " +
						"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	  " +
						"from NHAPP_KHO_CHITIET kho inner join  " +
						"(  " +
						//"	select a.kho_fk, a.NPP_FK, b.SANPHAM_FK, case when isnull(c.dungchungkenh, 0) = 0 then a.kbh_fk else 100000 end as nhomkenh_fk, b.solo,  SUM(b.soluong) as soluong,b.NgayHetHan  " +
						"	select a.kho_fk, a.NPP_FK, b.SANPHAM_FK, 100000 as nhomkenh_fk, b.solo,  SUM(b.soluong) as soluong,b.NgayHetHan  " +
						"	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.YCXK_FK " +
						"			inner join NHAPHANPHOI c on a.npp_FK = c.pk_seq  " +
						"	where a.PK_SEQ = '" + ycxk_fk + "' and b.soluong > 0 " +
						"	group by a.kho_fk, a.kbh_fk, c.dungchungkenh, a.NPP_FK, b.SANPHAM_FK, b.solo ,b.NgayHetHan " +
						")  " +
						"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.nhomkenh_fk = xuat.nhomkenh_fk and kho.KHO_FK = xuat.kho_fk and kho.solo = xuat.solo  and kho.NgayHetHan=xuat.NgayHetHan " ;
				System.out.println("TANG KHO CHI TIET NGUOC LAI: " + query);
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Không thể cập nhật NHAPP_KHO_CHITIET:  " + query;
					return msg;
				}
				
				//MO LAI TRANG THAI DON HANG  ==> LƯU Ý KHI ĐÃ DUYỆT CS RỒI, TRẠNG THÁI ĐƠN HÀNG SẼ LÀ 4, KHÔNG ĐỔI TRẠNG THÁI CHỖ NÀY
				query = "update ERP_DONDATHANGNPP set trangthai = '2' where pk_seq = '" + ddhId + "' and TrangThai != 0 ";
				if(db.updateReturnInt(query) != 1)
				{
					msg = "Đơn đặt hàng này đã mở trạng thái rồi " + query;
					return msg;
				}
			}
			*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Không thể hủy phiếu giao hàng " + e.getMessage();
		}
		return "";
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}


	public boolean updateHct(String[] socthuy, String[] soct,String[] ngaychungtu, String[] trangthai, String[] loaichungtu,String[] ngaytao, String[] thutu) {

		if(this.sochungtu.trim().length() <= 0)
		{
			this.msg = "Không có chứng từ hủy nào được chọn";
			return false;
		}
		
		if(soct == null || soct.length <=0)
		{
			this.msg = "Không có chứng từ hủy nào được chọn";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  " select count(pk_seq) as sodong" +
					        " from erp_huychungtubanhang " +
							" where trangthai != '2' and sochungtu = '" + this.sochungtu + "'" +
							" and loaichungtu = '"+ this.loaichungtu +"' and pk_Seq != "+this.id +" and congty_fk = "+this.congtyId;
			
			System.out.println("Cau lenh check: " + query);
			
			ResultSet rsCheck = db.get(query);
			
			int count = 0;
			if(rsCheck.next())
			{
				count = rsCheck.getInt("sodong");
				rsCheck.close();
			}
			
			if(count > 0)
			{
				this.msg = "Số chứng từ này đã hủy rồi, vui lòng nhập số chứng từ khác";
				System.out.println(this.msg);
				return false;
			}
			
			if( this.sochungtu.trim().length() < 6 )
			{
				this.msg = "Số chứng từ hủy không hợp lệ. Vui lòng kiểm tra lại.";
				return false;
			}
			
			//
			if(!this.revertChungtu(socthuy, this.id, db))
			{
				db.getConnection().rollback();
				return false;
			}
						
			//khong cam ham tao moi, mac dinh tao moi xong se chot luon
			
			query = "update erp_huychungtubanhang set sochungtu = "+this.sochungtu+" , sodonhang = '"+this.ddhId+"', congty_fk = " +this.congtyId+ ", "+
					"		sophieuxuatkho = '"+this.pxkId +"', sohoadon = '"+this.hdId+"', ngaysua = '"+this.getDateTime()+"', nguoisua = "+this.userId+", loaichungtu = N'"+ this.loaichungtu +"' , " +
					"       sobienban = N'"+this.sobienban+"', ngaybienban = N'"+this.ngaybienban+"', benA_bb = N'"+this.benA_bb+"', diachiA_bb = N'"+this.diachiA_bb+"', dienthoaiA_bb = N'"+this.dienthoaiA_bb+"', " +
					"		mstA_bb = N'"+this.mstA_bb+"', OngbaA_bb = N'"+this.ongbaA_bb+"', chucvuA_bb = N'"+this.chucvuA_bb+"', benB_bb = N'"+this.benB_bb+"', diachiB_bb = N'"+this.diachiB_bb+"', " +
					"		dienthoaiB_bb = N'"+this.dienthoaiB_bb+"', OngbaB_bb = N'"+this.ongbaB_bb+"', chucvuB_bb = N'"+this.chucvuB_bb+"', sohoadon1_bb = N'"+this.sohoadon1_bb+"', " +
					"		kyhieu1_bb = N'"+this.kyhieu1_bb+"', sohoadon2_bb = N'"+this.sohoadon2_bb+"', ngayhoadon1_bb = N'"+this.ngayhoadon1_bb + "', sohoadon3_bb = N'"+this.sohoadon3_bb+"', " +
					"		kyhieu2_bb = N'"+this.kyhieu2_bb+"', sohoadon4_bb = N'"+this.sohoadon4_bb+"', ngayhoadon2_bb = N'"+this.ngayhoadon2_bb+"', lydothuhoi_bb = N'"+this.lydothuhoi_bb+"', mstb_bb = N'"+this.mstB_bb+"', ngaybb = N'"+this.ngaybb +"' "+
					" where pk_Seq = "+this.id;
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Không thể cập nhật erp_huychungtubanhang, " + query;
				System.out.println(this.msg);
				return false;
			}
						
			query = "delete erp_huychungtubanhang_chungtu where hctbh_fk = "+this.id;
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi erp_huychungtubanhang, " + query;
				System.out.println(this.msg);
				return false;
			}
			
			for(int i = 0; i < socthuy.length; i++)
			{
				query = "insert erp_huychungtubanhang_chungtu(hctbh_fk, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu, ngaytao) " +
						"values('" + this.id + "', '" + thutu[i] + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'" + loaichungtu[i] + "', '" + ngaytao[i] + "')";
				
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi erp_huychungtubanhang_chungtu, " + query;
					System.out.println(this.msg);
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	
	}


	public String Chot(String id) {
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " SELECT sochungtu FROM erp_huychungtubanhang_chungtu WHERE hctbh_fk = "+id;
	
			ResultSet rs = db.get(query);			
			
			if(rs!=null)
			{
				while(rs.next())
				{
					this.sochungtu = rs.getString("sochungtu");
				}
				rs.close();
			}
			
			this.msg = this.HuyHoaDon(this.sochungtu, this.userId, db);
			
			if(this.msg.trim().length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			if(!Revert_KeToan("HOADON", this.sochungtu))
			{
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return msg;
		}
		catch (Exception e) {
			
			e.printStackTrace();
			return msg;
		}
		
	}

	
	public ResultSet getTTHoaDonRequest() {
		
		return this.TTHoaDonRequest;
	}

	
	public void setTTHoaDonRequest(ResultSet TTHoaDonRequest) {
		
		this.TTHoaDonRequest = TTHoaDonRequest;
	}

	
	public ResultSet getTTHoaDonCKRequest() {
	
		return this.TTHoaDonCKRequest;
	}

	
	public void setTTHoaDonCKRequest(ResultSet TTHoaDonCKRequest) {
	
		this.TTHoaDonCKRequest = TTHoaDonCKRequest;
	}

	
	public String getSobienban() {
		
		return this.sobienban;
	}

	
	public void setSobienban(String sobienban) {
		
		this.sobienban = sobienban;
	}

	
	public String getNgaybienban() {
		
		return this.ngaybienban;
	}

	
	public void setNgaybienban(String ngaybienban) {
		
		this.ngaybienban = ngaybienban;
	}

	
	public String getBenA_bb() {
		
		return this.benA_bb;
	}

	
	public void setBenA_bb(String benA_bb) {
		
		this.benA_bb = benA_bb;
	}

	
	public String getDiachiA_bb() {
		
		return this.diachiA_bb;
	}

	
	public void setDiachiA_bb(String diachiA_bb) {
		
		this.diachiA_bb = diachiA_bb;
	}

	
	public String getDienthoaiA_bb() {
		
		return this.dienthoaiA_bb;
	}

	
	public void setDienthoaiA_bb(String dienthoaiA_bb) {
		
		this.dienthoaiA_bb = dienthoaiA_bb;
	}

	
	public String getMstA_bb() {
		
		return this.mstA_bb;
	}

	
	public void setMstA_bb(String mstA_bb) {
		
		this.mstA_bb = mstA_bb;
	}

	
	public String getOngbaA_bb() {
		
		return this.ongbaA_bb;
	}

	
	public void setOngbaA_bb(String ongbaA_bb) {
		
		this.ongbaA_bb = ongbaA_bb;
	}

	
	public String getChucvuA_bb() {
		
		return this.chucvuA_bb;
	}

	
	public void setChucvuA_bb(String chucvuA_bb) {
		
		this.chucvuA_bb = chucvuA_bb;
	}

	
	public String getBenB_bb() {
		
		return this.benB_bb;
	}

	
	public void setBenB_bb(String benB_bb) {
		
		this.benB_bb = benB_bb;
	}

	
	public String getDiachiB_bb() {
		
		return this.diachiB_bb;
	}

	
	public void setDiachiB_bb(String diachiB_bb) {
		
		this.diachiB_bb = diachiB_bb;
	}

	
	public String getDienthoaiB_bb() {
		
		return this.dienthoaiB_bb;
	}

	
	public void setDienthoaiB_bb(String dienthoaiB_bb) {
		
		this.dienthoaiB_bb = dienthoaiB_bb;
	}

	
	public String getMstB_bb() {
		
		return this.mstB_bb;
	}

	
	public void setMstB_bb(String mstB_bb) {
		
		this.mstB_bb = mstB_bb;
	}

	
	public String getOngbaB_bb() {
		
		return this.ongbaB_bb;
	}

	
	public void setOngbaB_bb(String ongbaB_bb) {
		
		this.ongbaB_bb = ongbaB_bb;
	}

	
	public String getChucvuB_bb() {
		
		return this.chucvuB_bb;
	}

	
	public void setChucvuB_bb(String chucvuB_bb) {
		
		this.chucvuB_bb = chucvuB_bb;
	}

	
	public String getSohoadon1_bb() {
		
		return this.sohoadon1_bb;
	}

	
	public void setSohoadon1_bb(String sohoadon1_bb) {
		
		this.sohoadon1_bb = sohoadon1_bb;
	}

	
	public String getSohoadon2_bb() {
		
		return this.sohoadon2_bb;
	}

	
	public void setSohoadon2_bb(String sohoadon2_bb) {
		
		this.sohoadon2_bb = sohoadon2_bb;
	}

	
	public String getNgayhoadon1_bb() {
		
		return this.ngayhoadon1_bb;
	}

	
	public void setNgayhoadon1_bb(String ngayhoadon1_bb) {
		
		this.ngayhoadon1_bb = ngayhoadon1_bb;
	}

	
	public String getSohoadon3_bb() {
		
		return this.sohoadon3_bb;
	}

	
	public void setSohoadon3_bb(String sohoadon3_bb) {
		
		this.sohoadon3_bb = sohoadon3_bb;
	}

	
	public String getKyhieu2_bb() {
		
		return this.kyhieu2_bb;
	}

	
	public void setKyhieu2_bb(String kyhieu2_bb) {
		
		this.kyhieu2_bb = kyhieu2_bb;
	}

	
	public String getSohoadon4_bb() {
		
		return this.sohoadon4_bb;
	}

	
	public void setSohoadon4_bb(String sohoadon4_bb) {
		
		this.sohoadon4_bb = sohoadon4_bb;
	}

	
	public String getNgayhoadon2_bb() {
		
		return this.ngayhoadon2_bb;
	}

	
	public void setNgayhoadon2_bb(String ngayhoadon2_bb) {
		
		this.ngayhoadon2_bb = ngayhoadon2_bb;
	}

	
	public String getLydothuhoi_bb() {
		
		return this.lydothuhoi_bb;
	}

	
	public void setLydothuhoi_bb(String lydothuhoi_bb) {
		
		this.lydothuhoi_bb = lydothuhoi_bb;
	}

	
	public String getKyhieu1_bb() {
		
		return this.kyhieu1_bb;
	}

	
	public void setKyhieu1_bb(String kyhieu1_bb) {
		
		this.kyhieu1_bb = kyhieu1_bb;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	
	public String getNgaybb() {
		
		return this.ngaybb;
	}

	
	public void setNgaybb(String ngaybb) {
		
		this.ngaybb = ngaybb;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		if(this.db!=null)
		{
			db.shutDown();
		}
	}
	 
	 
}
