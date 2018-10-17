package geso.traphaco.distributor.beans.dondathang.imp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDonDatHangKhac;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpDonDatHangKhac implements  IErpDonDatHangKhac 
{
	
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang; // 0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn
						// hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn
						// hàng khác
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String dungchungKENH;
	String khId;
	ResultSet khRs;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String congno;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet dvtRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spSCheme;
	String[] spSoluongton;
	
	public String[] getSpSoluongton()
	{
		return spSoluongton;
	}

	public void setSpSoluongton(String[] spSoluongton)
	{
		this.spSoluongton = spSoluongton;
	}

	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	ResultSet congnoRs;
	Hashtable<String, String> sanpham_soluong;
	
	dbutils db;
	Utility util;
	public ErpDonDatHangKhac()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.dungchungKENH = "0";
		this.congno = "0";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
	}
	
	public ErpDonDatHangKhac(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.dungchungKENH = "0";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
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

	
	public boolean createNK() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tác đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			
			String query = " insert ERP_DONDATHANG(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, npp_dat_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NPP_DACHOT) " +
						   " values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', '" + this.khId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '0' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select SCOPE_IDENTITY() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			String kbh_fk = this.kbhId;
			query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					kbh_fk = "100025";
			}
			
			System.out.println("DDH ID: " + this.id);
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
				{
					//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
							   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
							   "from SANPHAM sp, DONVIDOLUONG dv " +
							   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";
					
					query = "insert ERP_DONDATHANG_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			
			
			//CHECK BOOKED THEO DV CHUAN
			query =  "select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
					"from     " +
					"(     " +
					"	select c.kho_fk as khoxuat_fk, c.npp_fk, '" + kbh_fk + "' kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
					"			case when a.dvdl_fk IS null then a.soluong      " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
					"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
					"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANG c on a.dondathang_fk = c.pk_seq    " +
					"	where a.dondathang_fk in ( " + this.id + " )     " +
					")     " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
			
			System.out.println("--CHECK TON KHO: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");
					
					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						rs.close();
						return false;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat NHAPP_KHO: " + query;
						db.getConnection().rollback();
						rs.close();
						return false;
					}
				}
				rs.close();
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

	public boolean updateNK() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tác đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			
			String query = "";
			
			
			//TANG KHO NGUOC LAI
			query = "update kho   " +
					"set kho.available = kho.available + BOOK_KHO.soluong,  " +
					"	kho.booked = kho.booked - BOOK_KHO.soluong  " +
					"from " +
					"( " +
					"	select khoxuat_fk, npp_fk, kbh_fk, sanpham_fk, sum(soluong) as soluong  " +
					"	from " +
					"	( " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, '100025' as kbh_fk, a.sanpham_fk,       " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
					"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANG c on a.dondathang_fk = c.pk_seq     " +
					"		where a.dondathang_fk in (  " + this.id + "  ) and a.soluong > 0 " +
					"	) " +
					"	DATHANG  " +
					"	group by khoxuat_fk, npp_fk, kbh_fk, sanpham_fk " +
					") " +
					"BOOK_KHO inner join NHAPP_KHO kho on BOOK_KHO.khoxuat_fk = kho.kho_fk and BOOK_KHO.npp_fk = kho.npp_fk and BOOK_KHO.kbh_fk = kho.kbh_fk and BOOK_KHO.sanpham_fk = kho.sanpham_fk ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
						
			query = "delete ERP_DONDATHANG_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			// PHAI CẬP NHẬT KHO SAU TRONG TRƯỜNG HỢP ĐỔI KHO KHÁC
			query =	" Update ERP_DONDATHANG set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
						" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
						" where pk_seq = '" + this.id + "' ";
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			String kbh_fk = this.kbhId;
			query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					kbh_fk = "100025";
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
				{
					//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
							   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
							   "from SANPHAM sp, DONVIDOLUONG dv " +
							   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";
					
					query = "insert ERP_DONDATHANG_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}

				}
			}
			
			//CHECK BOOKED THEO DV CHUAN
			query =  "select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
					"from     " +
					"(     " +
					"	select c.kho_fk as khoxuat_fk, c.npp_fk, '" + kbh_fk + "' kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
					"			case when a.dvdl_fk IS null then a.soluong      " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
					"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
					"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANG c on a.dondathang_fk = c.pk_seq    " +
					"	where a.dondathang_fk in ( " + this.id + " )     " +
					")     " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
			
			System.out.println("--CHECK TON KHO: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");
					
					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						rs.close();
						return false;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						db.getConnection().rollback();
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)  );
		if(this.khoNhanId.trim().length() <= 0)
		{
			if(this.nppId.equals("106210"))
				this.khoNhanId = "100002";
			else
				this.khoNhanId = "100000";
		}
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		
		String query = "select PK_SEQ, ISNULL(MAFAST, '') + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
		this.khRs = db.get(query);
		
		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
				    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
				    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
				    "where a.pk_Seq = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						if(this.dvkdId.trim().length() <= 0)
							this.dvkdId = rsInfo.getString("DVKD_FK");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsInfo.getString("KBH_FK");
					}
					rsInfo.close();
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			
			//INIT CONG NO
			query = "SELECT  HOADON.NPPID, HOADON.MANPP, HOADON.TENNPP, HOADON.PK_SEQ as SOHOADON, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON,   " +
					"			ISNULL(HOADON.TONGTIENAVAT, 0) AS SOTIENVND, ISNULL(DATHANHTOAN.DATHANHTOAN, '0') as DATHANHTOAN, " +
					"			ISNULL(HOADON.TONGTIENAVAT, 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') as CONLAI " +
					"FROM  " +
					"(  	 " +
					"	SELECT NPP.PK_SEQ as NPPID, NPP.MA as MANPP, NPP.TEN as TENNPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, HD.TONGTIENAVAT  		 " +
					"	FROM ERP_HOADON HD 	inner join NHAPHANPHOI NPP on HD.NPP_FK= NPP.PK_SEQ  		 " +
					"	WHERE  HD.TRANGTHAI = '2'  AND NPP.PK_SEQ = '" + this.nppId + "'  " +
					")  " +
					"HOADON  LEFT JOIN  " +
					"(  	 " +
					"	SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN    	 " +
					"	FROM   	 " +
					"	( 	 					 " +
					"		SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN     		 " +
					"		FROM ERP_THUTIEN_HOADON TTHD   " +
					"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ    		 " +
					"		WHERE  TT.TRANGTHAI NOT IN (2)	  " +
					" " +
					" 		GROUP BY HOADON_FK    	 " +
					"	)  " +
					"	HOADONDATT  GROUP BY HOADON_FK   " +
					") " +
					"DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK   " +
					"WHERE HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0 ";
			
			System.out.println("CONG NO: " + query);
			this.congnoRs = db.get(query);
			
		}
		
	}

	private void initSANPHAM() 
	{
		String query = "select b.MA,(select kho.available from nhapp_kho kho where kho.sanpham_fk=b.pk_seq and kho.KHO_FK= " + this.getKhoNhapId() + " and NPP_FK in(select NPP_FK from ERP_DONDATHANG where  PK_SEQ=a.dondathang_fk) and kho.KBH_FK="
				+ (this.dungchungKENH.equals("1") ? "100025" : this.kbhId) + " )as soluongton," + " b.TEN, DV.donvi, a.soluong, a.dongia, a.chietkhau, a.thueVAT   " +
						" from ERP_DONDATHANG_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where a.Dondathang_FK = '" + this.id + "' ";
		
		System.out.println("--INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spSCHEME = "";
				String spSOLUONGTON = "";
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSOLUONGTON += formater.format(spRs.getDouble("soluongton")) + "__";
					spSCHEME += " __";
				}
				spRs.close();
				
				//INIT SP KHUYEN MAI
				/*query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri " +
						"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
						"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
						"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
						"where a.dondathangID = '" + this.id + "' ";
				System.out.println("--INIT SPKM: " + query);
				
				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					spSCHEME += spRs.getString("SCHEME") + "__";
				}
				spRs.close();*/
				
				//System.out.println("---SCHEME: " + spSCHEME );
				//System.out.println("---DON GIA: " + spGIANHAP );
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					System.out.println("---VAT LA::::::" + spVAT);
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spSOLUONGTON = spSOLUONGTON.substring(0, spSOLUONGTON.length() - 2);
					this.spSoluongton = spSOLUONGTON.split("__");

				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public void init() 
	{
		String query = "select ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, loaidonhang, tructhuoc_fk, " +
						" Isnull( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.tructhuoc_Fk ), 0 ) as dungchungkenh,trangthai " +
						"from ERP_DONDATHANG a where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.khId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.dungchungKENH = rs.getString("dungchungkenh");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.createRs();
		
		
		this.initSANPHAM();
		
		
		//LẤY CÔNG NỢ // ÁP DỤNG CHO ĐƠN CỦA ĐỐI TÁC
		
		//XET XEM ĐỐI TÁC NÀY CÓ KHAI BÁO HAY KHÔNG
		query = "  SELECT COUNT(*) dem FROM CONGNO_NPP  WHERE NPP_FK ='"+this.khId+"'";
		System.out.println(query);
		
		ResultSet ktrakhaibao = db.get(query);
		
		int i = 0;
		try{
			while(ktrakhaibao.next()){
				i = ktrakhaibao.getInt("dem");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(i>0){
		//1.XET THEO HẠN MỨC NỢ CỦA ĐỐI TÁC
			query =
				
			" SELECT HDD.NPP_DAT_FK, SUM(isnull(sotienconno,0) - isnull(cn_npp.HANMUCNO,0)) SOTIENVUOTMUC \n"+ 
			" FROM ( \n"+ 
			"		SELECT dh.NPP_DAT_FK,sum(isnull(dh.TONGTIENAVAT,0)) - isnull(DATHANHTOAN.DATHANHTOAN,0) as sotienconno \n"+ 
			"		FROM ( \n"+ 
			"				  SELECT dh.NPP_DAT_FK,SUM(dh.tongtien - dh.tongtienck + dh.tongthueVat) TONGTIENAVAT \n"+ 
			"				  FROM (  SELECT dh.PK_SEQ,dh.NPP_DAT_FK, round(SUM(sp.dongia*sp.soluong),0) tongtien, round(SUM(sp.chietkhau),0) tongtienck, round(SUM((sp.soluong*sp.dongia - sp.chietkhau)*sp.thueVAT/100),0) tongthueVat \n"+ 
			"						  FROM ERP_DONDATHANG dh LEFT JOIN ERP_DONDATHANG_SANPHAM sp on dh.PK_SEQ = sp.dondathang_fk \n"+	      
			"						  WHERE NPP_DAT_FK IS NOT NULL AND dh.NPP_FK = '"+this.nppId+"' AND dh.NPP_DAT_FK ='"+this.khId+"' AND dh.PK_SEQ IN ("+this.id+") \n"+ 
			"						  GROUP BY  dh.PK_SEQ, dh.NPP_DAT_FK \n"+	  
			"					  ) dh \n"+ 
			"				  GROUP BY dh.NPP_DAT_FK \n"+ 
							  
			"				  UNION ALL \n"+
							  
			"				  SELECT NPP_DAT_FK, SUM(tongtienavat) TONGTIENAVAT \n"+
			"				  FROM ERP_HOADONNPP \n"+ 
			"				  WHERE NPP_DAT_FK IS NOT NULL AND LOAIXUATHD = 0 AND NPP_FK = '"+this.nppId+"' AND NPP_DAT_FK ="+this.khId+" AND TRANGTHAI IN (1,2,4) \n"+
			"				  GROUP BY NPP_DAT_FK \n"+
							  
			"			  ) dh \n"+ 
			"			 LEFT JOIN \n"+ 
			"				( \n"+	
			"					SELECT HOADONDATT.NPP_FK,SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
			"					FROM \n"+  
			"						( \n"+ 	
			"							SELECT	hd.NPP_FK, SUM(ISNULL(TTHD.SOTIENTT,0)) DATHANHTOAN \n"+ 
			"							FROM \n"+		
			"									( SELECT PK_SEQ , NPP_FK \n"+ 
			"									  FROM ERP_HOADONNPP \n"+ 
			"									  WHERE NPP_FK IS NOT NULL AND LOAIXUATHD = 0 AND NPP_FK = '"+this.nppId+"' AND NPP_FK ='"+this.khId+"' ) hd \n"+ 
			"									LEFT JOIN ERP_THUTIENNPP_HOADON TTHD ON  hd.PK_SEQ = TTHD.HOADONNPP_FK and hd.NPP_FK = TTHD.NPP_FK \n"+ 
			"									LEFT JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+ 
			"							WHERE	TT.NPP_FK= '"+this.nppId+"' AND  TT.TRANGTHAI = 1 \n"+ 
			" 							GROUP BY HD.PK_SEQ,hd.NPP_FK \n"+ 
					 					
			"						) HOADONDATT \n"+  
			"						GROUP BY HOADONDATT.NPP_FK \n"+ 						
			"				) DATHANHTOAN ON dh.NPP_FK= DATHANHTOAN.NPP_FK \n"+ 
			"			GROUP BY dh.NPP_FK, DATHANHTOAN.DATHANHTOAN \n"+
			"	) HDD LEFT JOIN NHAPHANPHOI npp ON HDD.NPP_FK = npp.PK_SEQ \n"+ 
			"		 LEFT JOIN CONGNO_NPP cn_npp ON npp.PK_SEQ = cn_npp.NPP_FK \n"+ 
			" WHERE HDD.NPP_FK in ("+this.khId+") and NPP.TRUCTHUOC_FK = '"+this.nppId+"' and npp.loaiNPP = '4' \n"+  
			"  GROUP BY HDD.NPP_FK  " +
			"  HAVING sum(isnull(sotienconno,0) - isnull(cn_npp.HANMUCNO,0)) >0 \n";
						
			System.out.println(query);
		
			ResultSet ktracongno = db.get(query);
			
			double tiencongno = 0;
			
			try{
				while(ktracongno.next()){
					tiencongno = ktracongno.getDouble("SOTIENVUOTMUC");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			this.congno = Double.toString(tiencongno);
			
		}
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er)
		{
			er.printStackTrace();
			
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

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}
	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public String getNgaydenghi() {
		
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {
		
		this.ngaydenghi = ngaydenghi;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public ResultSet getCongnoRs() {

		return this.congnoRs;
	}


	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
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
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
		this.spChietkhau = spChietkhau;
	}

	public String[] getSpVat() {
		
		return this.spVAT;
	}

	
	public void setSpVat(String[] spVat) {
		
		this.spVAT = spVat;
	}
	
	public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
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

	public boolean duyetDH() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//NEU CO DOI NGAY THI GHI NHAN LAI
			String query = " Update ERP_DONDATHANG set ngaydonhang = '" + this.ngayyeucau +  "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "' " +
						   "where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi duyệt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_DONDATHANG", id, "ngaydonhang", this.db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi duyệt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LUU VAO BANG CHI TIET
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i]) )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan )  " +
										"select '" + this.id + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANG_SANPHAM where dondathang_fk = '" + this.id + "' and sanpham_fk = a.pk_seq ),  N'" + _sp[1] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '"+_sp[2]+"' as NgayHetHan   " +
										"from SANPHAM a where MA = '" + spMa[i] + "'  ";
								
								System.out.println("1.2.Insert DDH - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
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
			
			
			//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
			query = "select count(*) as soDONG   " +
					"from ERP_DONDATHANG_SANPHAM tong left join   " +
					"	(  " +
					"		select sanpham_fk, sum(soluong) as soluong   " +
					"		from ERP_DONDATHANG_SANPHAM_CHITIET  " +
					"		where  dondathang_fk = '" + this.id + "'  " +
					"		group by sanpham_fk " +
					"	)  " +
					"	CT on tong.sanpham_fk = CT.sanpham_fk " +
					"where dondathang_fk = '" + this.id + "' and tong.soluong != isnull(CT.soluong, 0)  " ;
			ResultSet rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK != null )
			{
				if( rsCHECK.next() )
				{
					soDONG = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				db.getConnection().rollback();
				this.msg = "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
				return false;
			}
			
			
			query = 
				"select  a.kbh_fk, a.npp_fk, " +
				"( select priandsecond from NHAPHANPHOI where pk_seq = a.tructhuoc_Fk ) as tuxuatOTC,  " +
				"( select tuxuatETC from NHAPHANPHOI where pk_seq = a.tructhuoc_Fk ) as tuxuatETC,  " +
				"( select loaiNPP from NHAPHANPHOI where pk_seq = a.tructhuoc_Fk ) as loaiNPP, " +
				"tructhuoc_fk,  " +
				" ISNULL( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.tructhuoc_fk ), 0 ) as dungchungkenh, a.kho_fk, a.ngaydonhang  " +
				"from ERP_DONDATHANG a where a.pk_seq = '" + id + "' order by pk_seq desc";
			
			System.out.println("___Init___"+query);
			
			
			String khachhangID = "";
			String loaiNPP = "";
			String tructhuoc = "";
			String nppId = "";
			String npp_dat_fk = "";
			String kbh_fk = "";
			String khonhanID = "";
			String ngaydonhang = "";
						
			ResultSet rs = db.get(query);
			/*if(rs != null)*/
			{
				if(rs.next())
				{
					ngaydonhang = rs.getString("ngaydonhang");
			
					
					loaiNPP = rs.getString("loaiNPP");
					tructhuoc = rs.getString("tructhuoc_fk");
					nppId = rs.getString("npp_fk");
					
					if(rs.getString("dungchungkenh").equals("1"))
						kbh_fk = "100025";
					else
						kbh_fk = rs.getString("kbh_fk");					
					khonhanID = rs.getString("kho_fk");
				}
				rs.close();
			}
		
			msg = this.TaoXuatKhoKhac(db, id, userId, khonhanID, nppId, tructhuoc, kbh_fk );  //100000 la kho hang ban cua NPP
			if(msg.trim().length() > 0)
			{
				msg = "Khong the chot: " + msg;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_DONDATHANG set trangthai = '4', NPP_DACHOT = '1', nguoisua = '" + userId + "' where pk_seq = '" + id + "' and TrangThai!=4 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Đơn này đã duyệt rồi ";
				db.getConnection().rollback();
				return false;
			}
			
			msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
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
		finally
		{
			db.shutDown();
		}
		
		return true;
	}


	private String TaoXuatKhoKhac(dbutils db, String id, String userId, String khonhanID, String nppId, String tructhuoc, String kbh_fk)
  {
		String query = "";
		String msg = "";
		
		try
		{
			query = 
				"	insert into ERP_CHUYENKHO (NgayChuyen,KhoXuat_FK,TRANGTHAI,NGAYTAO,NGUOITAO,NGUOISUA,NGAYSUA,GHICHU,NPP_FK,KBH_FK,ddh_fk,lenhdieudong,SoChungTu,tructhuoc_fk,LoaiDonHang)  "+
				"	select NgayDonHang,Kho_FK,0,NGAYTAO,NGUOITAO,NGUOISUA,NGAYSUA,N'Đơn đặt hàng khác',NPP_FK,KBH_FK,PK_SEQ as ddhId,'' ,PK_SEQ as SoChungTu,tructhuoc_fk,LoaiDonHang  "+
				"	from ERP_DONDATHANG  "+
				"	where LoaiDonHang=4 and pk_seq='"+id+"'";
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select scope_identity() as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			
			query = 
				"	insert into ERP_CHUYENKHO_SANPHAM(chuyenkho_fk,sanpham_fk,soluongchuyen,dongia,dvdl_fk,SoLuong_Chuan,DonGia_Chuan,Dvdl_Chuan,ChietKhau)  "+
				"	select "+ycxkId+" ,a.sanpham_fk,soluong,dongia,a.dvdl_fk,  "+
				"			case   "+
				"			when a.dvdl_fk=b.DVDL_FK then a.soluong  "+  
				"			else a.soluong/(select soluong2/soluong1 from quycach qc where sanpham_fk=a.sanpham_fk and qc.DVDL2_FK=a.dvdl_fk  "+
				"			and qc.dvdl1_Fk=b.dvdl_fk) end as  soluong,  "+
				"			case   "+
				"			when a.dvdl_fk=b.DVDL_FK then a.dongia  "+  
				"			else a.dongia/(select soluong1/soluong2 from quycach qc where sanpham_fk=a.sanpham_fk and qc.DVDL2_FK=a.dvdl_fk  "+
				"			and qc.dvdl1_Fk=b.dvdl_fk) end as  DonGia,b.DVDL_FK,a.chietkhau  "+
				"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on b.PK_SEQ=a.sanpham_fk  "+
				"	where dondathang_fk='"+this.id+"'  ";
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
				return msg;
			}
			
			//CHECK TON KHO
			query =  
					"select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = '" + tructhuoc + "' ), 0) as tonkho  " +
					"from     " +
					"(     " +
					"	select c.kho_fk as khoxuat_fk, '" + tructhuoc + "' as npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
					"			case when a.dvdl_fk IS null then a.soluong      " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
					"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
					"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANG c on a.dondathang_fk = c.pk_seq    " +
					"	where a.dondathang_fk in ( " + id + " )     " +
					")     " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
			
			System.out.println("--CHECK TON KHO: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");
					
					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng liên hệ với chi nhánh cấp trên để xử lý.";
						rs.close();
						return msg;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set BOOKED = BOOKED + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '"+khoID+"' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						rs.close();
						return msg;
					}
					
					
					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO, ngayhethan from NHAPP_KHO_CHITIET " +
							"where AVAILABLE > 0 and KHO_FK = '" + khoID + "'  and SANPHAM_FK = '" + spID + "' and NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' order by ngayhethan asc ";
					
					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						String ngayhethan = rsTK.getString("ngayhethan");
						
						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;
						
						if(totalXUAT <= soluong)
						{
							soluongCT = avai;
							
							query = 
											"insert into ERP_CHUYENKHO_SANPHAM_CHITIET(chuyenkho_fk,SANPHAM_FK,solo,soluong,ngayhethan)" +
											"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT  + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set BOOKED = BOOKED + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' AND KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' AND NgayHetHan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								rs.close();
								return msg;
							}
						}
						else
						{
							soluongCT = soluong - ( totalXUAT - avai );
							
							query = 
									"insert into ERP_CHUYENKHO_SANPHAM_CHITIET(chuyenkho_fk,SANPHAM_FK,solo,soluong,ngayhethan)" +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set BOOKED = BOOKED + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and NgayHetHan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								rs.close();
								return msg;
							}
							break;
						}
					}
					rsTK.close();
				}
				rs.close();
			}
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			return "Không thể duyệt đơn hàng " + e.getMessage();
		}
		return "";
  }

	
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String kbh_fk = "";
		if(this.dungchungKENH.equals("1")) //DUNG CHUNG KENH THI QUY VE OTC
			kbh_fk = "100025";
		else
			kbh_fk = "100052";
			
		String query = "select case when sp.dvdl_fk != '" + donvi + "'  " +
					   "	then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end as AVAILABLE,  " +
					   "	NGAYHETHAN, SOLO " +
					   "from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq " +
					   "where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   " +
					   "	and AVAILABLE > 0 and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + kbh_fk + "'  order by NGAYHETHAN asc ";
		
		System.out.println("----LAY SO LO: " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			
			while(rs.next())
			{
				double slg = 0;
				double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
				
				System.out.println("===================== AVAI: " + avai);
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
				}
				
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	public String getDungchungKenh() {
		
		return this.dungchungKENH;
	}

	
	public void setDungchungKenh(String dungchungKenh) {
		
		this.dungchungKENH = dungchungKenh;
	}

	
	public String getCongNo() {
		
		return this.congno;
	}
	
	
	
	
	
	
}
