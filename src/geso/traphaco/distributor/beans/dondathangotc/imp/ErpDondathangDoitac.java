package geso.traphaco.distributor.beans.dondathang.imp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitac;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpDondathangDoitac implements IErpDondathangDoitac
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
	String maddh;
	
	int ngay_Chenhlech;
	
	public String getMaddh() {
		return maddh;
	}

	public void setMaddh(String maddh) {
		this.maddh = maddh;
	}

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
	String[] spGiagoc;
	
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
	
	public ErpDondathangDoitac()
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
		this.iskm="0";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
	}
	
	public ErpDondathangDoitac(String id)
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
		this.iskm="0";
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
			
			String query = 
					" insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, npp_dat_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NPP_DACHOT,isKM) " +
				   " values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', '" + this.khId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '0','"+this.iskm+"' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DondathangNPP " + query;
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
					
					query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, dongiagoc ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), "+ spGiagoc[i].replaceAll(",", "")  +" " +
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
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
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
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " +
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
			
						
			query = "delete ERP_DondathangNPP_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DondathangNPP_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			// PHAI CẬP NHẬT KHO SAU TRONG TRƯỜNG HỢP ĐỔI KHO KHÁC
			query =	" Update ERP_DondathangNPP set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
						" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "',isKM='"+this.iskm+"' " + 
						" where pk_seq = '" + this.id + "' ";
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DondathangNPP " + query;
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
					
					query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, dongiagoc ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), "+ spGiagoc[i].replaceAll(",", "")  +" " +
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
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
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
		
		String query = "select PK_SEQ, ISNULL(MAFAST, '') + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '4' and tructhuoc_fk = '" + this.nppId + "' ";
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
	
		String query = "select b.MA,(select kho.available from nhapp_kho kho where kho.sanpham_fk=b.pk_seq and kho.KHO_FK= " + this.getKhoNhapId() + " and NPP_FK in(select NPP_FK from ERP_DONDATHANGNPP where  PK_SEQ=a.dondathang_fk) and kho.KBH_FK="
				+ (this.dungchungKENH.equals("1") ? "100025" : this.kbhId) + " )as soluongton," + " b.TEN, DV.donvi, a.soluong, a.dongia, a.chietkhau, a.thueVAT, isnull(a.dongiagoc,0) as dongiagoc   " +
						" from ERP_DondathangNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
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
				String spGIAGOC = "";
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
					spGIAGOC += spRs.getDouble("DONGIAGOC") + "__";
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
					
					spGIAGOC = spGIAGOC.substring(0, spGIAGOC.length() - 2);
					this.spGiagoc = spGIAGOC.split("__");
					
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
		
		String query = "select  ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, loaidonhang, npp_dat_fk, " +
						" Isnull( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh,trangthai, DATEDIFF (day, ngaytao, '2015-01-29') as CL,isnull(a.iskm,0) as iskm " +
						"from ERP_DondathangNPP a where pk_seq = '" + this.id + "'";
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
					this.khId = rs.getString("npp_dat_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.dungchungKENH = rs.getString("dungchungkenh");
					this.trangthai = rs.getString("trangthai");
					this.ngay_Chenhlech = rs.getInt("CL");
					this.iskm = rs.getString("iskm")==null?"0":rs.getString("iskm");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		this.initSANPHAM();
		this.createRs();
		
		
	
		
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
			"						  FROM ERP_DONDATHANGNPP dh LEFT JOIN ERP_DONDATHANGNPP_SANPHAM sp on dh.PK_SEQ = sp.dondathang_fk \n"+	      
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
			"					SELECT HOADONDATT.NPP_DAT_FK,SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
			"					FROM \n"+  
			"						( \n"+ 	
			"							SELECT	hd.NPP_DAT_FK, SUM(ISNULL(TTHD.SOTIENTT,0)) DATHANHTOAN \n"+ 
			"							FROM \n"+		
			"									( SELECT PK_SEQ , NPP_DAT_FK \n"+ 
			"									  FROM ERP_HOADONNPP \n"+ 
			"									  WHERE NPP_DAT_FK IS NOT NULL AND LOAIXUATHD = 0 AND NPP_FK = '"+this.nppId+"' AND NPP_DAT_FK ='"+this.khId+"' ) hd \n"+ 
			"									LEFT JOIN ERP_THUTIENNPP_HOADON TTHD ON  hd.PK_SEQ = TTHD.HOADONNPP_FK and hd.NPP_DAT_FK = TTHD.NPP_FK \n"+ 
			"									LEFT JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+ 
			"							WHERE	TT.NPP_FK= '"+this.nppId+"' AND  TT.TRANGTHAI = 1 \n"+ 
			" 							GROUP BY HD.PK_SEQ,hd.NPP_DAT_FK \n"+ 
					 					
			"						) HOADONDATT \n"+  
			"						GROUP BY HOADONDATT.NPP_DAT_FK \n"+ 						
			"				) DATHANHTOAN ON dh.NPP_DAT_FK= DATHANHTOAN.NPP_DAT_FK \n"+ 
			"			GROUP BY dh.NPP_DAT_FK, DATHANHTOAN.DATHANHTOAN \n"+
			"	) HDD LEFT JOIN NHAPHANPHOI npp ON HDD.NPP_DAT_FK = npp.PK_SEQ \n"+ 
			"		 LEFT JOIN CONGNO_NPP cn_npp ON npp.PK_SEQ = cn_npp.NPP_FK \n"+ 
			" WHERE HDD.NPP_DAT_FK in ("+this.khId+") and NPP.TRUCTHUOC_FK = '"+this.nppId+"' and npp.loaiNPP = '4' \n"+  
			"  GROUP BY HDD.NPP_DAT_FK  " +
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
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			
			db.getConnection().setAutoCommit(false);
			
			//NEU CO DOI NGAY THI GHI NHAN LAI
			String query = " Update ERP_DondathangNPP set ngaydonhang = '" + this.ngayyeucau +  "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "' " +
						   "where pk_seq = '" + this.id + "' and TrangThai!=4 ";
			
			if(db.updateReturnInt(query)!=1)
			{
				this.msg = "Lỗi khi duyệt: " + query;
				db.getConnection().rollback();
				return false;
			}
	
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_DondathangNPP", id, "ngaydonhang", db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' ";
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
								
								query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan )  " +
										"select '" + this.id + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' and sanpham_fk = a.pk_seq ),  N'" + _sp[1] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '"+_sp[2]+"' as NgayHetHan   " +
										"from SANPHAM a where MA = '" + spMa[i] + "'  ";
								
								System.out.println("1.2.Insert DDH - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
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
					"from ERP_DONDATHANGNPP_SANPHAM tong left join   " +
					"	(  " +
					"		select sanpham_fk, sum(soluong) as soluong   " +
					"		from ERP_DONDATHANGNPP_SANPHAM_CHITIET  " +
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
			
			
			query = "select khachhang_fk, a.kbh_fk, a.npp_fk, a.npp_dat_fk, " +
						"( select priandsecond from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatOTC,  " +
						"( select tuxuatETC from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatETC,  " +
						"( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, " +
						"( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.npp_fk ) as tructhuoc_fk,  " +
						" ISNULL( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh, a.kho_fk, a.ngaydonhang  " +
					"from ERP_DondathangNPP a where a.pk_seq = '" + id + "' order by pk_seq desc";
			String khachhangID = "";
			String loaiNPP = "";
			String tructhuoc = "";
			String nppId = "";
			String npp_dat_fk = "";
			String kbh_fk = "";
			String khonhanID = "";
			String ngaydonhang = "";
			
			String tuquanlyKHO_OTC = "0";
			String tuquanlyKHO_ETC = "0";
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					ngaydonhang = rs.getString("ngaydonhang");
					if(rs.getString("khachhang_fk") != null)
						khachhangID = rs.getString("khachhang_fk");
					
					loaiNPP = rs.getString("loaiNPP");
					tructhuoc = rs.getString("tructhuoc_fk");
					nppId = rs.getString("npp_fk");
					
					if(rs.getString("npp_dat_fk") != null)
						npp_dat_fk = rs.getString("npp_dat_fk");
					
					if(rs.getString("dungchungkenh").equals("1"))
						kbh_fk = "100025";
					else
						kbh_fk = rs.getString("kbh_fk");
					
					if(rs.getString("tuxuatOTC") != null)
						tuquanlyKHO_OTC = rs.getString("tuxuatOTC");
					
					if(rs.getString("tuxuatETC") != null)
						tuquanlyKHO_ETC = rs.getString("tuxuatETC");
					
					khonhanID = rs.getString("kho_fk");
				}
				rs.close();
			}
			
			// Nếu là khách hàng ETC của CN2, đối tác thì cấp trên xuât kho
			// (---> neu co check la tu quan ly kho thi tru luon cua no)
			//if( khachhangID.trim().length() > 0 && ( loaiNPP.equals("1") || loaiNPP.equals("4") ) && tuquanlyKHO_OTC.equals("0") )
			if( ( khachhangID.trim().length() > 0 && tuquanlyKHO_ETC.equals("0") )  )
			{
				if (tructhuoc.trim().length() >= 5) // TRỰC THUỘC NPP
				{
					msg = this.TaoPhieuXuatKho_CapTren_NPP(db, id, userId, khonhanID, nppId, tructhuoc, kbh_fk );  //100000 la kho hang ban cua NPP
					if(msg.trim().length() > 0)
					{
						msg = "Khong the chot: " + msg;
						db.getConnection().rollback();
						return false;
					}
				}
 else
				// PHIẾU YCXK của HO
				{
					msg = this.TaoPhieuXuatKho_CapTren_HO(db, id, userId, khonhanID, nppId, tructhuoc, kbh_fk ); //100001 la kho TT cua HO
					if(msg.trim().length() > 0)
					{
						msg = "Khong the chot: " + msg;
						db.getConnection().rollback();
						return false;
					}
				}
			}
 else
			// Tạo PXK cho NPP (NPP TU QUAN LY TON KHO)
			{
				msg = this.TaoPhieuXuatKhoNPP(db, id, userId, khonhanID, nppId, npp_dat_fk, kbh_fk );
				if(msg.trim().length() > 0)
				{
					msg = "Khong the chot: " + msg;
					db.getConnection().rollback();
					return false;
				}
			}

			query = "update ERP_DondathangNPP set trangthai = '4', NPP_DACHOT = '1', nguoisua = '" + userId + "' where pk_seq = '" + id + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// PHAI HUY DON DUOI Đối tác trực thuộc đặt lên (trường hợp không
			// phải tự tao mới)
			query = "update ERP_Dondathang set trangthai = '4' where pk_seq = ( select from_dondathang from ERP_DondathangNPP where pk_seq = '" + id + "' ) ";
			if(!db.update(query))
			{
				msg = "1.Khong the chot: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			// KIỂM TRA CN/DOITAC CHECK TUTAOHOADON : TỰ ĐỘNG XUẤT HÓA ĐƠN TÀI
			// CHÍNH
			query = " select count(pk_seq) as dem from NHAPHANPHOI where pk_seq= '"+ this.nppId +"'  and tutaohoadon = '1'  ";
			System.out.println("Cau kiem tra "+query);
			ResultSet ktraNPP = db.get(query);
			int dem= 0;
			if(ktraNPP!= null)
			{
				while(ktraNPP.next())
				{
					dem = ktraNPP.getInt("dem");
				}
				ktraNPP.close();
			}
			
			if(dem > 0)
			{
				 // Tu dong tao Hoa don tai chinh cho NPP					
				msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, npp_dat_fk, ngaydonhang);
				if(msg.trim().length() > 0)
				{
					msg = "Khong the tao hoa don tai chinh: " + msg;
					db.getConnection().rollback();
					return false;
				}
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
		
		
		return true;
	}


	private String TaoHoaDonTaiChinhNPP(dbutils db, String id, String userId, String nppId, String npp_dat_fk, String ngaydonhang) 
	{
		String msg1 = "";
		try
		{
			String query = "";
			
			query =" update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
		 	if(!db.update(query))
		 	{
			   msg1 = "Không thể cập nhật thông tin NHANVIEN " + query;
			   return msg1;
		 	}
			
			int kbDLN =0;
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
			
			sohoadon="NA";
			kyhieuhoadon="NA";
			
			String chuoi="";
			long sohoadontu=0;
			long sohoadonden= 0;
		
			query =" select loaiNPP from NHAPHANPHOI where PK_SEQ ='"+this.nppId+"'";
			System.out.println("Cau kiem tra loại nhà phân phối" + query);
			ResultSet ktLoaiNPP = db.get(query);
			int loai=0;
			if(ktLoaiNPP!=null)
			{
				while(ktLoaiNPP.next())
				{
					loai = ktLoaiNPP.getInt("loaiNPP");
				}
				ktLoaiNPP.close();
			}
			
			if(loai!=4)
			{


				// CN HÀ NỘI && (CN HCM CÓ KH KHAI BÁO MẪU 2) DÙNG MẪU 2 (HO), CÒN LẠI DÙNG MẪU 1
				String query_kyhieu = " NV.KYHIEU ";				
				String query_sohdTU = " NV.SOHOADONTU ";	
				String query_sohdDEN = " NV.SOHOADONDEN ";	
				String query_mauhd = "1";
				String query_ngayhd = " NV.NGAYHOADON  ";
				
				if(nppId.equals("100002") )
				{
					query_kyhieu = " NV.KYHIEU2 ";				
					query_sohdTU = " NV.SOHOADONTU2 ";	
					query_sohdDEN = " NV.SOHOADONDEN2 ";				
					query_mauhd = "2";
					query_ngayhd = " NV.NGAYHOADON2 ";
				}
				
				// LAY TT KHAI BAO SO HD TRONG DLN
				query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
					   "        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
					   "        (select count(hd.pk_seq) as dem  "+
					   "         from HOADON hd               "+
					   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n" +
					   "        (select count(hd.pk_seq) as dem  "+
					   "         from ERP_HOADONNPP hd               "+
					   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
					   " FROM NHANVIEN NV  \n" +
					   " WHERE NV.pk_seq = '" + userId + "' \n";
				System.out.println("Câu check khai báo SHD "+query);
				ResultSet rsLayDL = db.get(query);
				
				int check_OTC = 0;
				int check_ETC = 0;
									
					while(rsLayDL.next())
					{
						kyhieuhoadon= rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getLong("sohoadontu");
						sohoadonden = rsLayDL.getLong("sohoadonden");
						ngayhoadon = rsLayDL.getString("ngayhoadon");
						if(ngayhoadon.trim().length() <= 0)  ngayhoadon = ngaydonhang;
						check_OTC = rsLayDL.getInt("isSd_OTC");
						check_ETC = rsLayDL.getInt("isSd_ETC");
					}
					rsLayDL.close();
				
				if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
				{
					msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
					return msg;
				}
							
				if(check_OTC <= 0 && check_ETC <= 0)
				{
					chuoi = ("000000" + sohoadontu);
					chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
				}
				else
				{
					// LAY SOIN MAX TRONG OTC && ETC
					query= " SELECT  \n"+
						   "       (select max(cast(sohoadon as float)) as soin_max  "+
						   "        from HOADON hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ ) soinMAX_OTC, \n" +
						   "       (select max(cast(sohoadon as float)) as soin_max "+
						   "        from ERP_HOADONNPP hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
						   " FROM NHANVIEN NV  \n" +
						   " WHERE NV.pk_seq = '" + userId + "' \n";
					
					System.out.println("Câu lấy SHD Max: "+query);
					long soinMAX_OTC = 0;
				    long soinMAX_ETC = 0;
				    
					ResultSet laySOIN = db.get(query);						     
					while(laySOIN.next())
					{
						soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
						soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
					}
					laySOIN.close();

					if( soinMAX_OTC > soinMAX_ETC ) 
					{
						chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
					}
					else
					{
						chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
					}
					
					 chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
				}
				
				
				if(Integer.parseInt(chuoi) > sohoadonden )
				{ 
					//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
					query= " SELECT  \n"+
						   "      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
						   "       from HOADON hd                                     \n"+
						   "       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
						   "             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
						   "             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
						   "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from HOADON where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
						   "       ) soinMAX_OTC 										  \n"+								  
						   " FROM NHANVIEN NV   \n" +
						   " WHERE NV.pk_seq = '" + userId + "' \n";
					
					System.out.println("Câu lấy HD không dùng "+query);
					ResultSet SoMAX_HD = db.get(query);
					String soinmax = "";
					while(SoMAX_HD.next())
					{
						soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
						chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));
							
					} SoMAX_HD.close();
					
					chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			  
					if(soinmax.trim().length() <= 0 )
					{
						msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
						return msg;
					}
				}
				
				 sohoadon = chuoi ;
				
			    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
				query = " select (select count(*) from HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
						"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
						" from NHANVIEN where pk_seq = '" + userId + "' ";
				
				System.out.println("Câu kiểm tra lại SHD: "+query);
				ResultSet RsRs = db.get(query);
				int KT_OTC = 0;
				int KT_ETC = 0;
					while(RsRs.next())
					{
						KT_OTC = RsRs.getInt("KtraOTC") ;
						KT_ETC = RsRs.getInt("KtraETC") ;
					}
				
				if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
				{
					msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
					return msg;
				}
			
			
			}
			// LAY TIEN DE LUU
			
			double tienck= 0;
			double tienbvat= 0;
			double tienavat= 0;
			String nguoimua ="";
				
			query = " select (case when dh.khachhang_fk is not null then " +
					"                               (select isnull(chucuahieu,'') from KHACHHANG where pk_seq = dh.khachhang_fk ) " +
					"             else '' end ) as nguoimua  ," +
					"        dh_sp.chietkhau, dh_sp.bvat , (dh_sp.bvat + dh.Vat) as AVAT "+
					" from ERP_DONDATHANGNPP dh inner join  "+
					"	(select a.dondathang_fk, SUM(a.chietkhau)as chietkhau , sum(a.soluong * a.dongia - a.chietkhau) as bvat "+
					"	from ERP_DONDATHANGNPP_SANPHAM a   "+
					"	group by  a.dondathang_fk ) dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					" where dh.PK_SEQ = "+ id +"  ";
			
			ResultSet rsLayTien = db.get(query);
			if(rsLayTien!= null)
			{
				while(rsLayTien.next())
				{
					tienck = rsLayTien.getDouble("chietkhau");
					tienbvat = rsLayTien.getDouble("bvat");
					tienavat = rsLayTien.getDouble("avat");
					nguoimua =  rsLayTien.getString("nguoimua");
					
				}rsLayTien.close();
			}
			
			// CN TẠI HÀ NỘI DÙNG MẪU 2 (HO)
			String mau = "1";
			if(nppId.equals("100002")) mau = "2";			
			
			 query =   " insert ERP_HOADONNPP(DDKD_fK,KBH_FK,KHO_fK,nguoimua ,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk,  npp_dat_fk, mauhoadon,TENKHACHHANG,DIACHI,MASOTHUE,LoaiHoaDon ) " +
					   " SELECT  DH.ddkd_Fk, DH.KBH_FK, DH.kho_Fk , N'" + nguoimua + "', '" + ngayhoadon + "', '1','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "'," +
					   "       '" + sohoadon + "', N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"'," +
					   " '" + this.vat.replaceAll(",", "") + "', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + id + " ', '0', '" + nppId + "', " + npp_dat_fk + ", " + mau + "" +
					   " , NPP.ten as nppMua " +
					   " , ISNULL(NPP.DIACHI,'')  as diachinpp " +
					   " , ISNULL(NPP.MASOTHUE,'')  as mst,case when dh.iskm=1 then 1 else 0 end as LoaiHoaDon "+
					   " FROM Erp_DonDatHangNPP DH INNER JOIN NHAPHANPHOI NPP ON DH.NPP_DAT_FK = NPP.PK_SEQ "+
					   " WHERE DH.PK_SEQ = '"+id+"' " ;
						   
			 
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
			
			query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat) " +
					" select "+ hdId +", "+
					"b.PK_SEQ, a.sanphamTEN, DV.donvi, SUM( a.soluong), a.dongia, SUM( a.soluong) * a.dongia ,SUM( isnull(a.chietkhau, 0)), "+
					"  isnull(scheme,' ') , isnull(a.thuevat,0) as vat   " +
					"from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
					" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  " +
					" inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq    "+
					"where a.dondathang_fk in ( "+ id +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANGNPP where NPP_FK="+ nppId +") and a.soluong > 0  " +
					"group by b.PK_SEQ , a.sanphamTEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0) ";
			
			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
	
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) " +
					" values( "+ hdId +",  " + id + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			
			query =
					"insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA,SoLuong_Chuan,DonGia_Chuan,SoLuong_DatHang)  " +
					"select '" + hdId + "' as hoadon_fk, dh.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,   " +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong   " +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,   " +
					"	case solo when 'NA' then ' ' else b.solo end as solo,   " +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat,  " +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" +hdId + "' and sanpham_fk = b.sanpham_fk ) as dongia  	,  " +
					"	case when d.pk_seq = dhsp.dvdl_fk then dhsp.soluong   "+   
					"	else dhsp.soluong /   "+
					"	( select SOLUONG2 / SOLUONG1  "+
					"			from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as SoLuong_Chuan,  "+
					"			case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA     "+
					"	else dhsp.DONGIA /   "+
					"	( select SOLUONG2 / SOLUONG1 "+
					"	from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,  "+
					"	dhsp.soluong as SoLuong_DatHang  "+
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	  " +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									  " +
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									  " +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = dh.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk	  " +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						  " +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	  " +
					"where dh.trangthai != '3' and a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = '" + hdId + "' ) ) and b.soluong > 0 and a.TRANGTHAI != '3'  ";
			
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
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
					"			case c.npp_fk when '106179' then (b.soluong * b.dongia )  " +
					"			else cast( (b.soluong * b.dongia) as numeric(18, 0) ) end as bVAT, isnull(b.chietkhau, 0) as chietkhau,    " +
					"			case c.npp_fk when '106179' then ( ( b.soluong * b.dongia - isnull(b.chietkhau, 0) ) * thueVAT / 100 )  " +
					"			else	cast ( (	cast( (b.soluong * b.dongia - isnull(b.chietkhau, 0) ) as numeric(18, 0) ) * thueVAT / 100 ) as numeric(18, 0) ) end as VAT " +
					"		from ERP_HOADONNPP_DDH a inner join ERP_DONDATHANGNPP_SANPHAM b on a.ddh_fk = b.dondathang_fk  " +
					"				inner join ERP_DONDATHANGNPP c on a.ddh_fk = c.pk_seq  " +
					"		where a.ddh_fk = '" + id + "'  " +
					"	)  " +
					"	etc group by hoadonnpp_fk  " +
					")  " +
					"giatri on hd.pk_seq = giatri.hoadonnpp_fk  ";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}
			
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
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
				msg = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				return msg;
			}
			
			
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}

	
	private String TaoPhieuXuatKho_CapTren_NPP(dbutils db, String id, String userId, String khoId, String nppId, String tructhuoc, String kbh_fk) 
	{
		String query = "";
		String msg = "";
		
		try
		{
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
 " select '" + this.getDateTime()
					+ "', N'Phiếu xuất kho xuất dùm cho khách hàng ETC của CN cấp 2 / đối tác (đơn hàng số: " + id + ")', '2', '" + tructhuoc + "', '100000', " +
							" '0' as xuatcho, '" + nppId + "' as npp_dat_fk, khachhang_fk, kbh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
					" from ERP_DONDATHANGNPP where pk_seq = '" + id + "' ";
			
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
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + id + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
					"group by sp.PK_SEQ, scheme, loai  " ;
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			//CHECK TON KHO
			query =  "select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = '" + tructhuoc + "' ), 0) as tonkho  " +
					"from     " +
					"(     " +
					"	select c.kho_fk as khoxuat_fk, '" + tructhuoc + "' as npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
					"			case when a.dvdl_fk IS null then a.soluong      " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
					"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
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
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '"+khoID+"' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						//db.getConnection().rollback();
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
							
							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ) " +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT  + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' AND KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' AND NgayHetHan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
					
						}
						else
						{
							soluongCT = soluong - ( totalXUAT - avai );
							
							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ) " +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and NgayHetHan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
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

	private String TaoPhieuXuatKho_CapTren_HO(dbutils db, String id, String userId, String khoNhanId, String nppId, String tructhuoc, String kbh_fk) 
	{
		String msg = "";
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//CHECK TON KHO
			query = "select sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT, " +
					"	ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) as tonkho " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
					"group by sp.PK_SEQ, sp.TEN " +
					"having  SUM(dathang.soluong) > ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) " ;
			
			System.out.println("--CHECK TON KHO: " + query);
			
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					msg = "Sản phẩm ( " + rsCHECK.getString("TEN") + " ) với số lượng yêu cầu ( " + rsCHECK.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rsCHECK.getString("tonkho") + " ) của HO. Vui lòng liên hệ với HO để xử lý.";
					//db.getConnection().rollback();
					rsCHECK.close();
					return msg;
				}
				rsCHECK.close();
			}
			
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
 " values('" + this.getDateTime() + "', N'Phiếu xuất kho xuất dùm cho khách hàng ETC của CN cấp 2 / đối tác (đơn hàng số: " + id + ")', '2', '"
					+ nppId + "', " + khoNhanId + ", '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' )";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
					"select IDENT_CURRENT('ERP_YCXUATKHO'), pk_seq from ERP_DONDATHANG where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
					"select IDENT_CURRENT('ERP_YCXUATKHO'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
					"group by sp.PK_SEQ, scheme, loai  " ;
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "select sp.PK_SEQ, sp.TEN, LOAI, SCHEME, SUM(dathang.soluong) as soluongXUAT " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
					"group by sp.PK_SEQ, sp.TEN, LOAI, SCHEME ";
			System.out.println("--CHECK KHO CHI TIET: " + query);
			rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					String sanpham_fk = rsCHECK.getString("PK_SEQ");
					String LOAI = rsCHECK.getString("LOAI");
					String SCHEME = rsCHECK.getString("SCHEME");
					double soLUONG = rsCHECK.getDouble("soluongXUAT");
					
					query = "Update ERP_KHOTT_SANPHAM set soluong = soluong - '" + soLUONG + "', AVAILABLE = AVAILABLE - '" + soLUONG + "' " +
							"where KHOTT_FK = '" + khoNhanId + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
					if(!db.update(query))
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						//db.getConnection().rollback();
						return msg;
					}
					
					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO from ERP_KHOTT_SP_CHITIET " +
							"where KHOTT_FK = '" + khoNhanId + "'  and SANPHAM_FK = '" + sanpham_fk + "' order by ngayhethan asc ";
					
					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						
						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;
						
						if(totalXUAT <= soLUONG)
						{
							soluongCT = avai;
							
							query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
									"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
							query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHOTT_FK = '" + khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							if(!db.update(query))
							{
								msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
						}
						else
						{
							soluongCT = soLUONG - ( totalXUAT - avai );
							
							query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
									"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
							query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHOTT_FK = '" + khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							if(!db.update(query))
							{
								msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
							break;
						}
					}
					rsTK.close();

				}
				rsCHECK.close();
			}
		} 
		catch (Exception e) 
		{
			msg = "--LOI DUYET: " + e.getMessage();
			e.printStackTrace();
			return msg;
		}
		
		return "";
	}

	private String TaoPhieuXuatKhoNPP(dbutils db, String id, String userId, String khoNhanId, String nppId, String npp_dat_fk, String kbh_fk)
	{
		String query = "";
		String msg = "";
		
		try
		{
			query =" update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
			   if(!db.update(query))
			   {
			    msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
			    //db.getConnection().rollback();
			    return msg;
			   }
			// CHECK XEM CO SP NAO CÓ SỐ LƯỢNG TRONG ĐƠN HÀNG MÀ CHƯA THIẾT LẬP
			// QUY CÁCH KHÔNG
			query = "		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong, " +
					"			0 as loai, ' ' as scheme, b.ten as spTEN   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' ) and a.soluong > 0   ";
			ResultSet rsCHECK = db.get(query);
			String spCHUACOQC = "";
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					if (rsCHECK.getString("soluong") == null) // Chưa có thiết
																// lập quy cách
																// mà có số
																// lượng
						spCHUACOQC += rsCHECK.getString("spTEN") + ", ";
				}
				rsCHECK.close();
			}
			
			if(spCHUACOQC.trim().length() > 0)
			{
				msg = "Các sản phẩm sau chưa thiết lập quy cách: " + spCHUACOQC;
				//db.getConnection().rollback();
				return msg;
			}
			
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
							" select '" + this.getDateTime() + "', N'Phiếu xuất kho tạo tự động từ duyệt đơn đặt hàng " + id+ "', '2', '" + nppId + "', " + khoNhanId + ", " +
							" case when npp_dat_fk is not null then '0' else '1' end as xuatcho, npp_dat_fk, khachhang_fk, KBH_FK as kbh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
					" from ERP_DONDATHANGNPP where pk_seq = '" + id + "' ";
			
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
					"select '" + ycxkId + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
					"select '" + ycxkId + "', sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = '" + kbh_fk + "' and npp_fk = dathang.npp_fk ), 0) as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
					"from    " +
					"(    " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
					"group by dathang.khoxuat_fk, dathang.npp_fk, sp.PK_SEQ, scheme, loai  " ;
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			
			// CẬP NHẬT KHO TỔNG
			query = "update kho   " +
					"set kho.soluong = kho.soluong - BOOK_KHO.soluong,  " +
					"	kho.booked = kho.booked - BOOK_KHO.soluong  " +
					"from " +
					"( " +
					"	select khoxuat_fk, npp_fk, kbh_fk, sanpham_fk, sum(soluong) as soluong  " +
					"	from " +
					"	( " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, case when isnull(d.dungchungkenh, 0) = 0 then c.kbh_fk else 100025 end as kbh_fk, a.sanpham_fk,       " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq  inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq   " +
					"		where a.dondathang_fk in (  " + this.id + "  ) and a.soluong > 0 " +
					"	) " +
					"	DATHANG  " +
					"	group by khoxuat_fk, npp_fk, kbh_fk, sanpham_fk " +
					") " +
					"BOOK_KHO inner join NHAPP_KHO kho on BOOK_KHO.khoxuat_fk = kho.kho_fk and BOOK_KHO.npp_fk = kho.npp_fk and BOOK_KHO.kbh_fk = kho.kbh_fk and BOOK_KHO.sanpham_fk = kho.sanpham_fk ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				return msg;
			}
			
			query = "select c.npp_fk, case when isnull(d.dungchungkenh, 0) = 0 then c.kbh_fk else 100025 end as kbh_fk, " +
					"		c.kho_fk, a.sanpham_fk, b.ten as TEN, a.soluong as soluongDAT, a.solo, a.ngayhethan,  " +
					"		case when a.dvdl_fk IS null then a.soluong      " +
					"			 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"			 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong,  " +
					"	0 as loai, ' ' as scheme    " +
					"from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"		inner join ERP_DONDATHANGNPP c on  a.dondathang_fk = c.pk_seq inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq " +
					"where a.dondathang_fk in ( " + this.id + " )   ";
			
			System.out.println("--CHECK TON KHO CHI TIET: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("sanpham_fk");
					
					double soluong = rs.getDouble("soluong");
					String solo = rs.getString("solo");
					String ngayhethan = rs.getString("ngayhethan");
					
					double tonkho = 0;
					query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
							"where kho_fk = '" + khoID + "' and sanpham_fk = '" + spID + "' and kbh_fk = '" + kbhID + "' and npp_fk = '" + nppID + "' and solo = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
					System.out.println("CHECK TON KHO: " + query);
					ResultSet rsTONKHO = db.get(query);
					if(rsTONKHO != null)
					{
						if(rsTONKHO.next())
							tonkho = rsTONKHO.getDouble("AVAILABLE");
						rsTONKHO.close();
					}
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ), số lô ( " + rs.getString("solo") + " ) với số lượng yêu cầu ( " + rs.getString("soluong") + " ) không đủ tồn kho ( " + tonkho + " ). Vui lòng kiểm tra lại.";
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}

					/*
					 * query = "select AVAILABLE from NHAPP_KHO " +
					 * "where kho_fk = '" + khoID + "' and sanpham_fk = '" +
					 * spID + "' and kbh_fk = '" + kbhID + "' and npp_fk = '" +
					 * nppID + "'  "; System.out.println("CHECK TON KHO: " +
					 * query); rsTONKHO = db.get(query); if(rsTONKHO != null) {
					 * if(rsTONKHO.next()) tonkho =
					 * rsTONKHO.getDouble("AVAILABLE"); rsTONKHO.close(); }
					 * 
					 * if(soluong > tonkho) { msg = "Sản phẩm ( " +
					 * rs.getString("TEN") + " ), với số lượng yêu cầu ( " +
					 * rs.getString("soluong") + " ) không đủ tồn kho ( " +
					 * tonkho + " ). Vui lòng kiểm tra lại.";
					 * //db.getConnection().rollback(); rs.close(); return msg;
					 * }
					 */
					
					//CAP NHAT KHO XUAT TONG
					/*query = "Update NHAPP_KHO set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}*/
					
					query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
								  "where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}
					
					query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan, kho_fk ) " +
							"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluong  + "', '" + ngayhethan + "', '" + khoID + "' ";
					
					System.out.println("1.2.Insert YCXK - SP - CT: " + query);
					if(!db.update(query))
					{
						msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}							
				}
				rs.close();
			}
	
			//CHECK TONG PHAI BANG CHI TIET
			query = "select count(*) as soDONG   " +
					"from ERP_YCXUATKHONPP_SANPHAM tong left join   " +
					"	(  " +
					"		select sanpham_fk, sum(soluong) as soluong   " +
					"		from ERP_YCXUATKHONPP_SANPHAM_CHITIET  " +
					"		where  ycxk_fk = '" + ycxkId + "'  " +
					"		group by sanpham_fk " +
					"	)  " +
					"	CT on tong.sanpham_fk = CT.sanpham_fk " +
					"where ycxk_fk = '" + ycxkId + "' and tong.soluongXUAT != isnull(CT.soluong, 0)  ";
			rsCHECK = db.get(query);
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
				return "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
			}
			
			if(npp_dat_fk.trim().length() > 0)
			{
				//Tu dong tao nhan hang
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, a.KBH_FK, '" + ycxkId + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + ycxkId + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					//db.getConnection().rollback();
					return msg;
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select ( select pk_seq from NHAPHANG where YCXKNPP_FK = a.PK_SEQ  ),  " +
						" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + ycxkId + "' and b.soluong > 0 ";
				
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					//db.getConnection().rollback();
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
						"select ( select PK_SEQ from NHAPHANG where YCXKNPP_FK = '" + id + "' ) as nhID, ddh_fk  " +
						"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + ycxkId + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					//db.getConnection().rollback();
					return msg;
				}
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

	public String[] getSpGiagoc()
	{
		return this.spGiagoc;
	}


	public void setSpGiagoc(String[] spGiagoc)
	{
		this.spGiagoc = spGiagoc;
		
	}


	public int getNgay_Chenhlech() 
	{
		return this.ngay_Chenhlech;
	}


	public void setNgay_Chenhlech(int ngay_Chenhlech) 
	{
		this.ngay_Chenhlech = ngay_Chenhlech;
		
	}
	

	/*// CN HÀ NỘI DÙNG MẪU HD TRÊN TT
	if(this.nppId.equals("100002") ) 
    {

		// KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
		query= " select count(pk_seq) as dem" +
			   " from NHANVIEN" +
			   " where pk_seq= '"+ this.userId+"' and  isnull(sohoadontu2, '') != '' and isnull( sohoadonden2, '') != ''" +
			   "       and isnull(kyhieu2,'') != '' ";
		
		System.out.println("_KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA__" + query);
		ResultSet KTDLN = db.get(query);
		if(KTDLN!= null)
		{
			while(KTDLN.next())
			{
				kbDLN = KTDLN.getInt("dem");
			}
			KTDLN.close();
		}
		
		if(kbDLN <= 0 )
		{					
			msg1 = "Vui lòng khai báo Số hóa đơn trong (Thiết lập dữ liệu nền > Số hóa đơn) cho user này ";
			return msg1;
		}
		else
		{
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			query= " select kyhieu2 as kyhieuhoadon, isnull(sohoadontu2, -1) as sohoadontu, isnull(sohoadonden2, -1) as sohoadonden, " +
				   "        isnull(ngayhoadon2, '') as ngayhoadon " +
				   " from NHANVIEN" +
				   " where pk_seq = '" + userId + "'";
	
			ResultSet rsLayDL = db.get(query);
			if(rsLayDL != null )
			{
				while(rsLayDL.next())
				{
					kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
					sohoadontu = rsLayDL.getLong("sohoadontu");
					sohoadonden = rsLayDL.getString("sohoadonden");
				}
				rsLayDL.close();
			}
			
			if(sohoadontu == -1 || sohoadonden.equals("-1") )
			{
				msg = "Vui lòng thiết lập khoảng số hóa đơn cho USER";
				return msg;
			}
			// KIEM TRA SOHOADON DA DUOC DUNG CHUA
			    // OTC
			query =" select count(pk_seq) as dem " +
				   " from HOADON " +
				   " where kyhieu = '" + kyhieuhoadon + "' and cast(sohoadon as int) >=  "+ sohoadontu +" and cast(sohoadon as int) <=  "+ Integer.parseInt(sohoadonden) + "  " +
				   "       and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
			System.out.println("1.Câu kiểm tra OTC: " + query);
			ResultSet KiemTra = db.get(query);
			int check = 0;
			if(KiemTra != null)
			{
				while(KiemTra.next())
				{
					check = KiemTra.getInt("dem");
				}
				KiemTra.close();
			}

			// ETC
			query = " select count(pk_seq) as dem " +
					" from ERP_HOADONNPP " +
					" where kyhieu = '" + kyhieuhoadon + "' and cast(sohoadon as numeric(18,0)) >= " + sohoadontu + " and cast(sohoadon as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
					"       and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
			System.out.println("2.Câu kiểm tra ETC: " + query);
			ResultSet KiemTra_OTC = db.get(query);
			int check_OTC = 0;
			if(KiemTra_OTC != null)
			{
				while(KiemTra_OTC.next())
				{
					check_OTC = KiemTra_OTC.getInt("dem");
				}
				KiemTra_OTC.close();
			}
				
			// LAY SOIN MAX	
			if(check <= 0 && check_OTC <= 0)
			{
				chuoi = ("000000"+ sohoadontu);
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			}
			else
			{// LAY SOIN MAX TRONG HOADON : 
					  //OTC
					query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
						" from HOADON where KYHIEU = '" + kyhieuhoadon + "' and cast(sohoadon as numeric(18, 0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
						"      and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
				
				System.out.println("Câu lấy shd max " + query);
					ResultSet laySOIN = db.get(query);
				    long soinMAX_OTC= 0;
					if(laySOIN!= null)
					{
						while(laySOIN.next())
						{
							soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
							
						}laySOIN.close();
					}
				
				
					 //ETC
					query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
							" from ERP_HOADONNPP where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
							"  and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
				System.out.println("Câu lấy shd max " + query);
					ResultSet laySOIN_ETC = db.get(query);
				    long soinMAX_ETC= 0;
					if(laySOIN_ETC!= null)
					{
						while(laySOIN_ETC.next())
						{
							soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
							
						}laySOIN_ETC.close();
					}
				
				if(soinMAX_OTC > soinMAX_ETC) 
				{
					chuoi = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
				}else
				{
					chuoi = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
				}
				
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
				
			}
			
			
			System.out.println("---SO HOA DON LAY DUOC KHI CHUA VUOT: " + chuoi );
			
			if(Integer.parseInt(chuoi) > Integer.parseInt(sohoadonden.trim()))
			{ 
				//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
				query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  " +
						"from HOADON a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
						"where  a.nguoisua= '" + userId + "' and a.kyhieu = '" + kyhieuhoadon + "' and a.trangthai != 3 and mauhoadon = 2 " +
						"		and cast(a.SOHOADON as numeric) >= CAST(b.sohoadontu as numeric(18, 0) )   " +
						"		and cast(a.SOHOADON as numeric) <= CAST(b.sohoadonden as numeric(18, 0) ) and a.sohoadon != 'NA'  " +
						"having "+
						" MAX(cast(SOHOADON as numeric)) != ( select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  from HOADON where  kyhieu = '" + kyhieuhoadon + "' and nguoisua= '" + userId + "' and sohoadon != 'NA' )";
				
				System.out.println("Câu check khoang HOADON: " + query);
				ResultSet SoMAX_HD = db.get(query);
				String soinmax= "";
				if(SoMAX_HD!= null)
				{
					while(SoMAX_HD.next())
					{
						soinmax = SoMAX_HD.getString("SOIN_MAX")== null ? "" : SoMAX_HD.getString("SOIN_MAX") ;
						chuoi = ("000000" + (SoMAX_HD.getLong("SOIN_MAX")));
						System.out.println("---SO HOA DON LAY DUOC KHI VUOT: " + chuoi );
						
					}
					SoMAX_HD.close();
				}
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
		  
				if(soinmax.trim().length() <= 0)
				{
					msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
					return msg;
				}
			}
			
			sohoadon =  chuoi;
			
			// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
			query = " select (select count(*) from HOADON where SOHOADON = '"+ sohoadon +"' and kyhieu = '"+ kyhieuhoadon +"' and trangthai != '3' and npp_fk = '" + nppId + "' and mauhoadon = 2 and sohoadon != 'NA' ) as KtraOTC, " +
					"        (select count(*) from ERP_HOADONNPP where SOHOADON = '"+ sohoadon +"' and kyhieu = '"+ kyhieuhoadon +"' and trangthai != '3' and npp_fk = '" + nppId + "' and mauhoadon = 2 and sohoadon != 'NA' ) as KtraETC " +
					" from NHANVIEN where pk_seq = '" + userId + "' ";
			ResultSet RsRs = db.get(query);
			int KT_OTC = 0;
			int KT_ETC = 0;
			if(RsRs != null)
			{
				while(RsRs.next())
				{
					KT_OTC = RsRs.getInt("KtraOTC") ;
					KT_ETC = RsRs.getInt("KtraETC") ;
				}
			}
			
			if (KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER
											// KHÁC) CÓ SỐ HD TRÙNG
											// VS SỐ HÓA ĐƠN MỚI TẠO
			{
				msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
				return msg;
			}
			
		}
	  
	}
	else
	{
		// KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
	query= " select count(pk_seq) as dem" +
		   " from NHANVIEN" +
		   " where pk_seq= '"+ this.userId+"' and  isnull(sohoadontu, '') != '' and isnull( sohoadonden, '') != ''" +
		   "       and isnull(kyhieu,'') != '' ";
	
		System.out.println("_KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA_" + query);
	
	ResultSet KTDLN = db.get(query);
	if(KTDLN!= null)
	{
		while(KTDLN.next())
		{
			kbDLN = KTDLN.getInt("dem");
		}
		KTDLN.close();
	}
	
	if(kbDLN <= 0 )
	{					
			msg1 = "Vui lòng khai báo Số hóa đơn trong (Thiết lập dữ liệu nền > Số hóa đơn) cho user này ";
		return msg1;
	}
	else
	{
		// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
		query= " select kyhieu as kyhieuhoadon, isnull(sohoadontu, -1) as sohoadontu, isnull(sohoadonden, -1) as sohoadonden, " +
			   "        isnull(ngayhoadon, '') as ngayhoadon " +
			   " from NHANVIEN where pk_seq = '" + userId + "'";

		ResultSet rsLayDL = db.get(query);
		if(rsLayDL != null )
		{
			while(rsLayDL.next())
			{
				kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
				sohoadontu = rsLayDL.getLong("sohoadontu");
				sohoadonden = rsLayDL.getString("sohoadonden");
			}
			rsLayDL.close();
		}
		
		if(sohoadontu == -1 || sohoadonden.equals("-1") )
		{
				msg = "Vui lòng thiết lập khoảng số hóa đơn cho USER";
			return msg;
		}
		// KIEM TRA SOHOADON DA DUOC DUNG CHUA
		    // OTC
		query =" select count(pk_seq) as dem " +
			   " from HOADON " +
			   " where kyhieu = '" + kyhieuhoadon + "' and cast(sohoadon as int) >=  "+ sohoadontu +" and cast(sohoadon as int) <=  "+ Integer.parseInt(sohoadonden) + "  " +
			   "       and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
			System.out.println("1.Câu kiểm tra OTC: " + query);
		ResultSet KiemTra = db.get(query);
		int check = 0;
		if(KiemTra != null)
		{
			while(KiemTra.next())
			{
				check = KiemTra.getInt("dem");
			}
			KiemTra.close();
		}

		// ETC
		query = " select count(pk_seq) as dem " +
				" from ERP_HOADONNPP " +
				" where kyhieu = '" + kyhieuhoadon + "' and cast(sohoadon as numeric(18,0)) >= " + sohoadontu + " and cast(sohoadon as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
				"       and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
			System.out.println("2.Câu kiểm tra ETC: " + query);
		ResultSet KiemTra_OTC = db.get(query);
		int check_OTC = 0;
		if(KiemTra_OTC != null)
		{
			while(KiemTra_OTC.next())
			{
				check_OTC = KiemTra_OTC.getInt("dem");
			}
			KiemTra_OTC.close();
		}
			
		// LAY SOIN MAX	
		if(check <= 0 && check_OTC <= 0)
		{
			chuoi = ("000000"+ sohoadontu);
			chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
		}
		else
		{// LAY SOIN MAX TRONG HOADON : 
				  //OTC
				query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
					" from HOADON where KYHIEU = '" + kyhieuhoadon + "' and cast(sohoadon as numeric(18, 0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
					"      and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
			
				System.out.println("Câu lấy shd max " + query);
				ResultSet laySOIN = db.get(query);
			    long soinMAX_OTC= 0;
				if(laySOIN!= null)
				{
					while(laySOIN.next())
					{
						soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
						
					}laySOIN.close();
				}
			
			
				 //ETC
				query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
						" from ERP_HOADONNPP where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
						"   and trangthai != 3 and nguoisua= "+ userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
				System.out.println("Câu lấy shd max " + query);
				ResultSet laySOIN_ETC = db.get(query);
			    long soinMAX_ETC= 0;
				if(laySOIN_ETC!= null)
				{
					while(laySOIN_ETC.next())
					{
						soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
						
					}laySOIN_ETC.close();
				}
			
			if(soinMAX_OTC > soinMAX_ETC) 
			{
				chuoi = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
			}else
			{
				chuoi = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
			}
			
			chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			
		}
		
		
		System.out.println("---SO HOA DON LAY DUOC KHI CHUA VUOT: " + chuoi );
		
		if(Integer.parseInt(chuoi) > Integer.parseInt(sohoadonden.trim()))
		{ 
			//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
			query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  " +
					"from HOADON a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
					"where  a.nguoisua= '" + userId + "' and a.kyhieu = '" + kyhieuhoadon + "' and a.trangthai != 3 and mauhoadon = 1 " +
					"		and cast(a.SOHOADON as numeric) >= CAST(b.sohoadontu as numeric(18, 0) )   " +
					"		and cast(a.SOHOADON as numeric) <= CAST(b.sohoadonden as numeric(18, 0) ) and a.sohoadon != 'NA'  " +
					"having "+
					" MAX(cast(SOHOADON as numeric)) != ( select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  from HOADON where  kyhieu = '" + kyhieuhoadon + "' and nguoisua= '" + userId + "' and sohoadon != 'NA' )";
			
				System.out.println("Câu check khoang HOADON: " + query);
			ResultSet SoMAX_HD = db.get(query);
			String soinmax= "";
			if(SoMAX_HD!= null)
			{
				while(SoMAX_HD.next())
				{
					soinmax = SoMAX_HD.getString("SOIN_MAX")== null ? "" : SoMAX_HD.getString("SOIN_MAX") ;
					chuoi = ("000000" + (SoMAX_HD.getLong("SOIN_MAX")));
					System.out.println("---SO HOA DON LAY DUOC KHI VUOT: " + chuoi );
					
				}
				SoMAX_HD.close();
			}
			chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
	  
			if(soinmax.trim().length() <= 0)
			{
					msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
				return msg;
			}
		}
		
		sohoadon =  chuoi;
		
		// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
		query = " select (select count(*) from HOADON where SOHOADON = '"+ sohoadon +"' and kyhieu = '"+ kyhieuhoadon +"' and trangthai != '3' and npp_fk = '" + nppId + "' and mauhoadon = 1 and sohoadon != 'NA' ) as KtraOTC, " +
				"        (select count(*) from ERP_HOADONNPP where SOHOADON = '"+ sohoadon +"' and kyhieu = '"+ kyhieuhoadon +"' and trangthai != '3' and npp_fk = '" + nppId + "' and mauhoadon = 1 and sohoadon != 'NA' ) as KtraETC " +
				" from NHANVIEN where pk_seq = '" + userId + "' ";
		ResultSet RsRs = db.get(query);
		int KT_OTC = 0;
		int KT_ETC = 0;
		if(RsRs != null)
		{
			while(RsRs.next())
			{
				KT_OTC = RsRs.getInt("KtraOTC") ;
				KT_ETC = RsRs.getInt("KtraETC") ;
			}
			RsRs.close();
		}
		
			if (KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER
											// KHÁC) CÓ SỐ HD TRÙNG
											// VS SỐ HÓA ĐƠN MỚI TẠO
		{
				msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
			return msg;
		}
		
	}
  }*/

	
	String iskm;
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }
	
}
