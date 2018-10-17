package geso.traphaco.distributor.beans.donhangctv.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhangctv.IDonhangctv;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Donhangctv implements IDonhangctv, Serializable
{
	private static final long serialVersionUID = -4877175563344609017L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ghichu;

	String msg;
	String trangthai;
	
	String duyetss = "";
	String khId;
	ResultSet khRs;
	
	String dlppId;
	ResultSet dlppRs;
	
	ResultSet dvtRs;

	ResultSet ctvRs;
	
	String tinhdoiId = "";
	ResultSet spRs;
	
	String[] spCtvId;
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spTonkho;
	String[] spBooked;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	
	dbutils db;
	Utility util;
	
	String loaidonhang;
	
	String ddkdId;
	ResultSet ddkdRs;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	public Donhangctv()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.sochungtu ="";
		this.db = new dbutils();
		this.util = new Utility();
		this.loaidonhang = "";
		this.ddkdId = "";
		this.dlppId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
	}
	
	public Donhangctv(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.sochungtu ="";
		this.db = new dbutils();
		this.util = new Utility();
		this.loaidonhang = "";
		this.ddkdId = "";
		this.dlppId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
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
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createNK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày nhập";
			return false;
		}
		
		if(this.ddkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn khách hàng";
			return false;
		}

		boolean coSP = false;
		for(int i = 0; i < spId.length; i++)
		{
			if(spId[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}
		
		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm báo cáo";
			return false;
		}
		
		//CHECK TRUNG MA 
		for(int i = 0; i < spId.length - 1; i++)
		{
			for(int j = i + 1; j < spId.length; j++)
			{
				if(spId[i].trim().length() > 0 && spId[j].trim().length() > 0 )
				{
					if( spId[i].trim().equals(spId[j].trim()) && spCtvId[i].trim().equals(spCtvId[j].trim())  )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng cộng tác viên.";
						return false;
					}
				}
			}
		}	
		
		//CHECK CHI DUOC TAO TU NGAY 01-05 dau thang
		/*int ngayTAO = Integer.parseInt( this.getDateTime().split("-")[2] );
		if( !( ngayTAO >= 1 && ngayTAO <= 5 ) )
		{
			this.msg = "Bạn chỉ được làm báo cáo từ ngày 01 đến 05 đầu tháng";
			return false;
		}*/
		
		try
		{
			db.getConnection().setAutoCommit(false);

			String query =  "INSERT DONHANGCTV ( NGAYNHAP, NGUOITAO, NGUOISUA, TRANGTHAI, NPP_FK, VAT, SOTIENAVAT, SOTIENBVAT, SoChungTu, GhiChu, khachhang_fk, TINHDOI, DDKD_FK)" +
							"select '" + this.ngayyeucau + "', '" + this.userId + "', '" + this.userId + "', 0, '" + this.nppId + "', 0, 0, 0, '" + this.sochungtu + "', N'" + this.ghichu + "', '" + this.khId + "', '" + this.tinhdoiId + "', '" + this.ddkdId + "' ";

			System.out.println("1.Insert CK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới DONHANGCTV " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("hdId");
			rs.close();
			
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 && spCtvId[i].trim().length() > 0 )
				{
					String[] arr = spId[i].split("__");
					
					query = "insert into donhangctv_sp( DONHANGCTV_FK, CTV_FK, SANPHAM_FK, DONGIA, DONVI, TONKHO, SOLUONG ) " +
							"select '" + this.id + "', '" + spCtvId[i] + "', '" + arr[0] + "', " + spGianhap[i].replaceAll(",", "") + ", N'" + spDonvi[i].replaceAll(",", "") + "', " + spTonkho[i].replaceAll(",", "") + ", " + spSoluong[i].replaceAll(",", "");
					System.out.println("1.2.Insert donhangctv_sp: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the tao moi donhangctv_sp: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//CHECK MÃ HÀNG VƯỢT QUÁ TỒN KHO
			query = "select b.MA_FAST, b.TEN, sum(a.SOLUONG) as soluongXUAT, max(a.TONKHO) as tonkho  "+
					 "from DONHANGCTV_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  "+
					 "where DONHANGCTV_FK = '" + this.id + "'  "+
					 "group by b.MA_FAST, b.TEN having sum(a.SOLUONG) > max(a.TONKHO) ";
			ResultSet rsCHECK = db.get(query);
			if( rsCHECK != null )
			{
				while( rsCHECK.next() )
				{
					this.msg = "Sản phẩm: " + rsCHECK.getString("TEN") + ", tồn kho chỉ có tối đa ( " + rsCHECK.getString("tonkho") + " ), không thể xuất với số lượng ( " + rsCHECK.getString("soluongXUAT") + " ) ";
					
					rsCHECK.close();
					db.getConnection().rollback();
					return false;
				}
				rsCHECK.close();
				
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
	}

	public boolean updateNK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày nhập";
			return false;
		}
		
		if(this.ddkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn khách hàng";
			return false;
		}

		boolean coSP = false;
		for(int i = 0; i < spId.length; i++)
		{
			if(spId[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}
		
		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm báo cáo";
			return false;
		}
		
		//CHECK TRUNG MA 
		for(int i = 0; i < spId.length - 1; i++)
		{
			for(int j = i + 1; j < spId.length; j++)
			{
				if(spId[i].trim().length() > 0 && spId[j].trim().length() > 0 )
				{
					if( spId[i].trim().equals(spId[j].trim()) && spCtvId[i].trim().equals(spCtvId[j].trim())  )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng cộng tác viên.";
						return false;
					}
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";

			query = "delete Donhangctv_SP where Donhangctv_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Donhangctv_SP " + query;
				db.getConnection().rollback();
				return false;
			}		

			query = " Update Donhangctv set NGAYNHAP = '" + this.ngayyeucau + "', NguoiSua='" + this.userId + "', SoChungTu = '" + this.sochungtu + "'" +					
					", Modified_Date=getdate(), GhiChu=N'"+this.ghichu+"', Khachhang_fk = '"+this.khId+"',TINHDOI = '"+this.tinhdoiId+"', ddkd_fk = '" + this.ddkdId + "' "+ 
					" where pk_seq = '" + this.id + "' and trangthai  = 0 ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Donhangctv " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 && spCtvId[i].trim().length() > 0 )
				{
					String[] arr = spId[i].split("__");
					
					query = "insert into donhangctv_sp( DONHANGCTV_FK, CTV_FK, SANPHAM_FK, DONGIA, DONVI, TONKHO, SOLUONG ) " +
							"select '" + this.id + "', '" + spCtvId[i] + "', '" + arr[0] + "', " + spGianhap[i].replaceAll(",", "") + ", N'" + spDonvi[i].replaceAll(",", "") + "', " + spTonkho[i].replaceAll(",", "") + ", " + spSoluong[i].replaceAll(",", "");
					System.out.println("1.2.Insert donhangctv_sp: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the tao moi donhangctv_sp: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			
			//CHECK MÃ HÀNG VƯỢT QUÁ TỒN KHO
			query = "select b.MA_FAST, b.TEN, sum(a.SOLUONG) as soluongXUAT, max(a.TONKHO) as tonkho  "+
					 "from DONHANGCTV_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  "+
					 "where DONHANGCTV_FK = '" + this.id + "'  "+
					 "group by b.MA_FAST, b.TEN having sum(a.SOLUONG) > max(a.TONKHO) ";
			
			System.out.println("::: CHECK XUAT VUOT: " + query );
			ResultSet rsCHECK = db.get(query);
			if( rsCHECK != null )
			{
				while( rsCHECK.next() )
				{
					this.msg = "Sản phẩm: " + rsCHECK.getString("TEN") + ", tồn kho chỉ có tối đa ( " + rsCHECK.getString("tonkho") + " ), không thể xuất với số lượng ( " + rsCHECK.getString("soluongXUAT") + " ) ";
					
					rsCHECK.close();
					db.getConnection().rollback();
					return false;
				}
				rsCHECK.close();
				
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


	public void createRs() 
	{
		Utility util = new Utility();
		
		this.getNppInfo();
		
		String query = "select PK_SEQ, MaFAST + ', ' + TEN as TEN from NHAPHANPHOI where loaiNPP = '1' and pk_seq in " + util.quyen_npp(this.userId);
		this.dlppRs = db.get(query);
		
		query = "select PK_SEQ, maFAST + ', ' + TEN as TEN from DAIDIENKINHDOANH a where TRANGTHAI = '1'";
		query += " and pk_seq in ( select DDKD_FK from DAIDIENKINHDOANH_KBH where KBH_FK in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";
		
		/*if( this.dlppId.trim().length() <= 0 )
			query += " and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		else
			query += " and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.dlppId + "' ) ";*/
		
		//PHAN QUYEN
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println(":::DDKD: " + query);
		this.ddkdRs = db.get(query);

		if( this.ddkdId.trim().length() > 0 )
		{
			//CHỈ BÁN CHO ETC
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"from KHACHHANG a where TRANGTHAI = '1'  and ( NPP_FK = '" + this.nppId + "' or pk_seq in ( select khachhang_fk from KHACHHANG_TRUCTHUOC where tructhuoc_fk = '" + this.nppId + "' ) ) " + 
					" AND pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) )   ";
			
			//PHAN QUYEN
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			
			query += " AND a.pk_seq in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + this.ddkdId + "' ) ";
			
			System.out.println("::: LAY KHACH HANG: " + query );
			this.khRs = db.get(query);
		}
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");

		if( this.ngayyeucau.trim().length() > 0 && this.khId.trim().length() > 0 && this.ddkdId.trim().length() > 0 )
		{
			query = "select PK_SEQ, Ma + ', ' + TEN as ten from congtacvien " + 
					" where trangthai = '1' and pk_seq in ( select ctv_fk from congtacvien_khachhang where kh_fk = '" + this.khId + "' ) order by Ma + ', ' + TEN asc ";
			this.ctvRs = db.getScrol(query);
			
			//Kiểm tra tháng trước đó đã khóa sổ kinh doanh chưa
			
			//Xác định tháng khóa sổ kinh doanh trước đó
			query = "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) ) + ']' as ten  "+
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
					 "where a.ddkd_fk = '" + this.ddkdId + "' and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  "+
					 "														where TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + this.khId + "' ) and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) ";
			
			//XUNG QUANH BỆNH VIỆN ( PHẢI TÁCH RIÊNG DO LẤY TDV KHÁC NHAU )
			query += "union ";
			query += "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) ) + ']' as ten  "+
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
					 "where dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  "+
					 "														where TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "'  ) and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) " +
					 " and sanpham_fk in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' ) and tdv_fk = '" + this.ddkdId + "' ) ";
			
			//DONHANG DLPP
			query += "union ";
			query += "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( a.dongiaGOC * ( 1 + a.thueVAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongiaGOC * ( 1 + a.thueVAT / 100 ), 0 ) ) + ']' as ten  "+
					 "from DONHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ inner join DONHANG c on a.donhang_fk = c.pk_seq "+
					 "where c.ddkd_fk = '" + this.ddkdId + "' and c.CS_duyet = '1' and c.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' or PK_SEQ = '" + this.khId + "' ) " + 
					 "	 and SUBSTRING( NgayNhap , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 )  ";
			
			//TON DAU KY
			query += "union ";
			query += "select cast(sanpham_fk as varchar(10)) + '__' + CAST( a.dongia as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( a.dongia ) + ']' as ten  "+
					 "from TONKHOTHANG_CTV a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  "+
					 "where KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' or PK_SEQ = '" + this.khId + "' ) " ;
					 //"	 and SUBSTRING( NgayNhap , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 )  ";
			
			query = " select distinct pk_seq, ten from ( " + query + " ) SP ";
			
			/*query = "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( isnull( a.DonGia_Chuan, a.dongia ) * ( 1 + VAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( isnull( a.DonGia_Chuan, a.dongia ) * ( 1 + VAT / 100 ), 0 ) ) + ']' as ten   "+
					 "from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  "+
					 "where dbo.trim(a.scheme) = '' and HOADON_FK in ( select PK_SEQ from ERP_HOADONNPP  														 "+
					 "				where TRANGTHAI != 3 and KHACHHANG_FK = '" + this.khId + "' and SUBSTRING( NGAYXUATHD , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) )  ";*/
			
			System.out.println("::: LAY SAN PHAM: " + query );
			this.spRs = db.getScrol(query);
			
			//INIT CÁC THÔNG TIN TỪNG DÒNG SAU KHI CHỌN
			if( spId != null )
			{
				Hashtable<String, Double> daxuat = new Hashtable<String, Double>();
				for( int i = 0; i < spId.length; i++ )
				{
					if( spId[i].trim().length() > 0 && spCtvId[i].trim().length() > 0 )
					{
						//INIT THÔNG TIN THEO TÊN VÀ ĐƠN GIÁ
						String[] arr = spId[i].split("__");
						
						query = "select DT.soluong - "+
								 " ISNULL( (   select SUM(SOLUONG) "+
								 "				from DONHANGCTV dh inner join DONHANGCTV_SP dh_sp on dh.PK_SEQ = dh_sp.DONHANGCTV_FK "+
								 "				where dh.KHACHHANG_FK = '" + this.khId + "' and SUBSTRING( dh.ngaynhap , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, 0, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) and dh_sp.SANPHAM_FK = '" + arr[0] + "' and dh_sp.dongia = DT.dongia and TRANGTHAI in ( 0, 1, 2 ) ) , 0 ) as soluong, DT.dongia, DT.DONVI "+
								 "from "+
								 "( "+									
									"select DONVI, dongia, SUM(soluong) as soluong  "+
									"from "+
									"( "+		
										" select SOLUONG, DonVi, DONGIA " + 
										" from TONKHOTHANG_CTV where KHACHHANG_FK = '" + this.khId + "' and SANPHAM_FK = '" + arr[0] + "' and DONGIA = '" + arr[1] + "'  " +
									"  union ALL	" +
										" select SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
										 " from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
										 " 		inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
										 " 		inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ"+
										 " where a.dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  						   "+
										 " 						   where chuyenSALES = '0' and TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + this.khId + "' ) "+
										 " 									and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) "+
										 "		 and a.sanpham_fk = '" + arr[0] + "' and round( a.dongiaCHUAN * ( 1 + a.thueVAT / 100 ), 0 ) = '" + arr[1] + "'   "+
										 " group by d.DONVI, a.dongiaCHUAN, thueVAT " +
									"	union ALL	" +
										 " select SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
										 " from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
										 " 		inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
										 " 		inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ"+
										 " where a.dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  						   "+
										 " 						   where chuyenSALES = '0' and TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "'  ) "+
										 " 									and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) "+
										 "		 and a.sanpham_fk = '" + arr[0] + "' and round( a.dongiaCHUAN * ( 1 + a.thueVAT / 100 ), 0 ) = '" + arr[1] + "'   "+
										 "		 and a.sanpham_fk in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' ) and tdv_fk = '" + this.ddkdId + "' )	" +
										 " group by d.DONVI, a.dongiaCHUAN, thueVAT " +
									"	union ALL	" +
										 " select SUM( a.soluong ) as soluong, d.DONVI, round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
										 " from DONHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
										 " 		inner join DONHANG c on a.DONHANG_FK = c.PK_SEQ "+
										 " 		inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ "+
										 " where c.CS_DUYET = 1 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' or PK_SEQ = '" + this.khId + "' ) "+
										 " 		 and SUBSTRING( NgayNhap, 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) "+
										 "		 and a.sanpham_fk = '" + arr[0] + "' and round( a.dongiaGOC * ( 1 + a.thueVAT / 100 ), 0 ) = '" + arr[1] + "'   "+
										 " group by d.DONVI, a.dongiaGOC, thueVAT " +
									"  union ALL	" +
											" select SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
											 " from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
											 " 		inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
											 " 		inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ"+
											 " where a.dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  						   "+
											 " 						   where chuyenSALES = '1' and TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + this.khId + "' ) "+
											 " 									and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, 0, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) "+
											 "		 and a.sanpham_fk = '" + arr[0] + "' and round( a.dongiaCHUAN * ( 1 + a.thueVAT / 100 ), 0 ) = '" + arr[1] + "'   "+
											 " group by d.DONVI, a.dongiaCHUAN, thueVAT " +
									 " ) " +
									 " DT group by DONVI, dongia " +
								 ") "+
								 "DT "+
								 "where ( DT.soluong -  "+
								 "	    ISNULL( (   select SUM(SOLUONG) "+
								 "					from DONHANGCTV dh inner join DONHANGCTV_SP dh_sp on dh.PK_SEQ = dh_sp.DONHANGCTV_FK "+
								 "					where dh.KHACHHANG_FK = '" + this.khId + "' and SUBSTRING( dh.ngaynhap , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, 0, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) " + 
								 " 						and dh_sp.SANPHAM_FK = '" + arr[0] + "' and dh_sp.dongia = DT.dongia and TRANGTHAI in ( 0, 1, 2 ) ) , 0 ) ) > 0 ";
						
						System.out.println("::: INIT SAN PHAM: " + query );
						ResultSet rsINFO = db.get(query);
						int conTON = 0;
						if( rsINFO != null )
						{
							try 
							{
								NumberFormat formatter = new DecimalFormat("#,###,###");
								if( rsINFO.next() )
								{
									conTON = 1;
									
									spGianhap[i] = formatter.format(rsINFO.getDouble("dongia"));
									spDonvi[i] = rsINFO.getString("DONVI");
									
									if( daxuat.get(spId[i]) != null )
									{
										double conLAI = rsINFO.getDouble("soluong") - daxuat.get(spId[i]);
										spTonkho[i] = formatter.format(conLAI);
									}
									else
										spTonkho[i] = formatter.format(rsINFO.getDouble("soluong"));
								}
								rsINFO.close();
							} 
							catch (Exception e) { e.printStackTrace(); }
						}
						
						if( conTON <= 0 )
						{
							spGianhap[i] = arr[1];
							spTonkho[i]  = "";
							spTonkho[i] = "0";
							spSoluong[i] = "0";
						}
						
						//Trừ bớt tồn kho trong trường hợp người dùng chọn lại
						if( spSoluong[i].trim().length() > 0 )
						{
							if( daxuat.get(spId[i]) == null )
								daxuat.put(spId[i], Double.parseDouble( spSoluong[i].replaceAll(",", "") ) );
							else
							{
								double _daxuat = daxuat.get(spId[i]) + Double.parseDouble(spSoluong[i].replaceAll(",", ""));
								daxuat.put(spId[i], _daxuat);
							}
						}
					}
				}
			}
		}
	}
	
	public void initSANPHAM() 
	{
		Utility util = new Utility();
		
		this.getNppInfo();
		
		String query = "select PK_SEQ, maFAST + ', ' + TEN as TEN from DAIDIENKINHDOANH a where TRANGTHAI = '1'";
		query += " and pk_seq in ( select DDKD_FK from DAIDIENKINHDOANH_KBH where KBH_FK in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";
		
		//PHAN QUYEN
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println(":::DDKD: " + query);
		this.ddkdRs = db.get(query);

		if( this.ddkdId.trim().length() > 0 )
		{
			//CHỈ BÁN CHO ETC
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"from KHACHHANG a where TRANGTHAI = '1'  and ( NPP_FK = '" + this.nppId + "' or pk_seq in ( select khachhang_fk from KHACHHANG_TRUCTHUOC where tructhuoc_fk = '" + this.nppId + "' ) ) " + 
					" AND pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) )   ";
			
			//PHAN QUYEN
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			
			query += " AND a.pk_seq in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + this.ddkdId + "' ) ";
			
			System.out.println("::: LAY KHACH HANG: " + query );
			this.khRs = db.get(query);
		}
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		if( this.ngayyeucau.trim().length() > 0 && this.khId.trim().length() > 0 )
		{
			query = "select PK_SEQ, Ma + ', ' + TEN as ten from congtacvien " + 
					" where trangthai = '1' and pk_seq in ( select ctv_fk from congtacvien_khachhang where kh_fk = '" + this.khId + "' ) order by Ma + ', ' + TEN asc ";
			this.ctvRs = db.getScrol(query);
			
			//Kiểm tra tháng trước đó đã khóa sổ kinh doanh chưa
			
			//Xác định tháng khóa sổ kinh doanh trước đó
			query = "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) ) + ']' as ten  "+
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
					 "where a.ddkd_fk = '" + this.ddkdId + "' and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  "+
					 "														where TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + this.khId + "' ) and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) ";
			
			//XUNG QUANH BỆNH VIỆN ( PHẢI TÁCH RIÊNG DO LẤY TDV KHÁC NHAU )
			query += "union ";
			query += "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) ) + ']' as ten  "+
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
					 "where dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP  "+
					 "														where TRANGTHAI = 4 and KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "'  ) and SUBSTRING( NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 ) ) " +
					 " and sanpham_fk in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' ) and tdv_fk = '" + this.ddkdId + "' ) ";
			
			//DONHANG DLPP
			query += "union ";
			query += "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( round( a.dongiaGOC * ( 1 + a.thueVAT / 100 ), 0 ) as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongiaGOC * ( 1 + a.thueVAT / 100 ), 0 ) ) + ']' as ten  "+
					 "from DONHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ inner join DONHANG c on a.donhang_fk = c.pk_seq "+
					 "where c.ddkd_fk = '" + this.ddkdId + "' and c.CS_duyet = '1' and c.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' or PK_SEQ = '" + this.khId + "' ) " + 
					 "	 and SUBSTRING( NgayNhap , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 )  ";
			
			//TON DAU KY
			query += "union ";
			query += "select cast(sanpham_fk as varchar(10)) + '__' + CAST( a.dongia as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( a.dongia ) + ']' as ten  "+
					 "from TONKHOTHANG_CTV a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  "+
					 "where KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + this.khId + "' or PK_SEQ = '" + this.khId + "' ) " ;
					 //"	 and SUBSTRING( NgayNhap , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + this.ngayyeucau + "'), 120 ), 0, 8 )  ";
			
			query = " select distinct pk_seq, ten from ( " + query + " ) SP ";
			
			System.out.println("::: LAY SAN PHAM: " + query );
			this.spRs = db.getScrol(query);
		}
	}
	
	public void init() 
	{
		String query =  "select PK_SEQ, NGAYNhap, TRANGTHAI, NPP_FK, GhiChu, sochungtu, trangthai, khachhang_fk, ISNULL(TINHDOI,'') AS TINHDOI, ddkd_fk " +
						"from DONHANGCTV where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		/*if(rs != null)*/
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("NGAYNhap");
					this.ghichu = rs.getString("ghichu");
					this.trangthai = rs.getString("trangthai");
					this.sochungtu = rs.getString("sochungtu");
					this.trangthai = rs.getString("trangthai");
					this.khId = rs.getString("khachhang_fk");
					this.tinhdoiId = rs.getString("TINHDOI");
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk");
				}
				rs.close();
				
				//INIT SO LUONG
				query = "select CTV_FK, SANPHAM_FK, round(DONGIA, 0) as dongia, DONVI, TONKHO, SOLUONG " +
						"from DONHANGCTV_SP a where DONHANGCTV_fk = '" + this.id + "'";
				
				System.out.println("---INIT SP: " + query);
				
				String _ctvId = "";
				String _spId = "";
				String _dongia = "";
				String _donvi = "";
				String _tonkho = "";
				String _soluong = "";
				
				rs = db.get(query);
				if(rs != null)
				{
					NumberFormat formatter = new DecimalFormat("#,###,###");
					while(rs.next())
					{
						_ctvId += rs.getString("CTV_FK") + ",";
						_spId += rs.getString("SANPHAM_FK") + "__" + rs.getInt("DONGIA") + ",";
						_dongia += rs.getDouble("DONGIA") + ",";
						_donvi += rs.getString("DONVI") + ",";
						_tonkho += formatter.format(rs.getDouble("TONKHO")) + ",";
						_soluong += formatter.format(rs.getDouble("SOLUONG")) + ",";
					}
					rs.close();
					
					if( _ctvId.trim().length() > 0 )
					{
						_ctvId = _ctvId.substring(0, _ctvId.length() - 1);
						this.spCtvId = _ctvId.split(",");

						_spId = _spId.substring(0, _spId.length() - 1);
						this.spId = _spId.split(",");
						
						_dongia = _dongia.substring(0, _dongia.length() - 1);
						this.spGianhap = _dongia.split(",");
						
						_donvi = _donvi.substring(0, _donvi.length() - 1);
						this.spDonvi = _donvi.split(",");
						
						_tonkho = _tonkho.substring(0, _tonkho.length() - 1);
						this.spTonkho = _tonkho.split(",");
						
						_soluong = _soluong.substring(0, _soluong.length() - 1);
						this.spSoluong = _soluong.split(",");
					}
				}
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		this.initSANPHAM();
		
	}
	
	public void initDisplay() 
	{
		String query =  "select PK_SEQ, NGAYNhap, TRANGTHAI, NPP_FK, GhiChu, sochungtu, trangthai, khachhang_fk, ISNULL(TINHDOI,'') AS TINHDOI, ddkd_fk " +
						"from DONHANGCTV where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		/*if(rs != null)*/
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("NGAYNhap");
					this.ghichu = rs.getString("ghichu");
					this.trangthai = rs.getString("trangthai");
					this.sochungtu = rs.getString("sochungtu");
					this.trangthai = rs.getString("trangthai");
					this.khId = rs.getString("khachhang_fk");
					this.tinhdoiId = rs.getString("TINHDOI");
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk");
				}
				rs.close();
				
				//INIT SO LUONG
				query = "select CTV_FK, SANPHAM_FK, round(DONGIA, 0) as dongia, DONVI, TONKHO, SOLUONG " +
						"from DONHANGCTV_SP a where DONHANGCTV_fk = '" + this.id + "'";
				
				System.out.println("---INIT SP: " + query);
				
				String _ctvId = "";
				String _spId = "";
				String _dongia = "";
				String _donvi = "";
				String _tonkho = "";
				String _soluong = "";
				
				rs = db.get(query);
				if(rs != null)
				{
					NumberFormat formatter = new DecimalFormat("#,###,###");
					while(rs.next())
					{
						_ctvId += rs.getString("CTV_FK") + ",";
						_spId += rs.getString("SANPHAM_FK") + "__" + rs.getInt("DONGIA") + ",";
						_dongia += rs.getDouble("DONGIA") + ",";
						_donvi += rs.getString("DONVI") + ",";
						_tonkho += formatter.format(rs.getDouble("TONKHO")) + ",";
						_soluong += formatter.format(rs.getDouble("SOLUONG")) + ",";
					}
					rs.close();
					
					if( _ctvId.trim().length() > 0 )
					{
						_ctvId = _ctvId.substring(0, _ctvId.length() - 1);
						this.spCtvId = _ctvId.split(",");

						_spId = _spId.substring(0, _spId.length() - 1);
						this.spId = _spId.split(",");
						
						_dongia = _dongia.substring(0, _dongia.length() - 1);
						this.spGianhap = _dongia.split(",");
						
						_donvi = _donvi.substring(0, _donvi.length() - 1);
						this.spDonvi = _donvi.split(",");
						
						_tonkho = _tonkho.substring(0, _tonkho.length() - 1);
						this.spTonkho = _tonkho.split(",");
						
						_soluong = _soluong.substring(0, _soluong.length() - 1);
						this.spSoluong = _soluong.split(",");
					}
				}
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		this.getNppInfo();
		
		
		Utility util = new Utility();
		
		this.getNppInfo();
		
		query = "select PK_SEQ, maFAST + ', ' + TEN as TEN from DAIDIENKINHDOANH a where TRANGTHAI = '1'";
		query += " and pk_seq in ( select DDKD_FK from DAIDIENKINHDOANH_KBH where KBH_FK in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";
		
		//PHAN QUYEN
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println(":::DDKD: " + query);
		this.ddkdRs = db.get(query);

		if( this.ddkdId.trim().length() > 0 )
		{
			//PHAN QUYEN
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			this.khRs = db.get(query);
			
			//CHỈ BÁN CHO ETC
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"from KHACHHANG a where TRANGTHAI = '1'  and npp_fk = '" + this.nppId + "' " + 
					" AND pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) )   ";
			
			//PHAN QUYEN
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			
			query += " AND a.pk_seq in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + this.ddkdId + "' ) ";
			
			this.khRs = db.get(query);
		}
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		if( this.ngayyeucau.trim().length() > 0 && this.khId.trim().length() > 0 )
		{
			query = "select PK_SEQ, Ma + ', ' + TEN as ten from congtacvien " + 
					" where trangthai = '1' and pk_seq in ( select ctv_fk from congtacvien_khachhang where kh_fk = '" + this.khId + "' ) order by Ma + ', ' + TEN asc ";
			this.ctvRs = db.getScrol(query);
			
			//Kiểm tra tháng trước đó đã khóa sổ kinh doanh chưa
			
			//Xác định tháng khóa sổ kinh doanh trước đó
			query = "select distinct cast(sanpham_fk as varchar(10)) + '__' + CAST( a.dongia as varchar(10) ) as pk_seq, b.TEN + ' [' +  dbo.DinhDangTien ( round( a.dongia, 0 ) ) + ']' as ten   "+
					 "from DONHANGCTV_SP a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  "+
					 "where a.DONHANGCTV_FK = '" + this.id + "' ";
			
			System.out.println("::: LAY SAN PHAM: " + query );
			this.spRs = db.getScrol(query);
		}
		
	}

	public void DBclose() {
		
		try{
	
			this.db.shutDown();
			
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}
	
	public String getDateTime() 
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

	
	public String[] getSpTonkho() {

		return this.spTonkho;
	}


	public void setSpTonkho(String[] spTonkho) {
		
		this.spTonkho = spTonkho;
	}

	
	public String[] getSpBooked() {

		return this.spBooked;
	}


	public void setSpBooked(String[] spBooked) {
		
		this.spBooked = spBooked;
	}

	
	public ResultSet getSanphamRs() {

		return this.spRs;
	}


	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
	}
	
	public ResultSet getCtvRs() {
		
		return this.ctvRs;
	}

	
	public void setCtvRs(ResultSet ctvRs) {
		
		this.ctvRs = ctvRs;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
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

	
	public String getSoChungTu()
	{
		return sochungtu;
	}
	public void setSoChungTu(String sochungtu)
	{
		this.sochungtu=sochungtu;
	}

	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
	{
		return spNgayHetHan;
	}

	public void setSpNgayHetHan(String[] ngayHetHan)
    {
		this.spNgayHetHan = ngayHetHan;
    }
	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs) 
	{
		this.spRs = spRs;
	}
	
	String[] spVat;

	public String[] getSpVat()
	{
		return spVat;
	}

	public void setSpVat(String[] spVat)
	{
		this.spVat = spVat;
	}
	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	public String getDuyetSS() {
		
		return this.duyetss;
	}

	
	public void setDuyetSS(String duyetss) {
		
		this.duyetss = duyetss;
	}


	public String getTinhdoiId() {
		
		return this.tinhdoiId;
	}


	public void setTinhdoiId(String TinhdoiId) {
		
		this.tinhdoiId = TinhdoiId;
	}

	
	public String[] getSpCtvId() {

		return this.spCtvId;
	}


	public void setSpCtvId(String[] spCtvId) {
		
		this.spCtvId = spCtvId;
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

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDdkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
	}

	
	public String getDlppId() {
		
		return this.dlppId;
	}

	
	public void setDlppId(String dlppId) {
		
		this.dlppId = dlppId;
	}

	
	public ResultSet getDlppRs() {
		
		return this.dlppRs;
	}

	
	public void setDlppRs(ResultSet dlppRs) {
		
		this.dlppRs = dlppRs;
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
