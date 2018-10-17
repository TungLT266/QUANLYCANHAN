package geso.traphaco.distributor.beans.dondathang.imp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangNpp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class ErpDondathangNpp implements IErpDondathangNpp
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String nppTen;
	String sitecode;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String nhomkenhId;
	ResultSet nhomkenhRs;
	
	ResultSet dvtRs;
	
	String schemeId;
	ResultSet schemeRs;
	
	Hashtable<String, String> scheme_soluong;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spSCheme;
	String[] spVAT;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	ResultSet congnoRs;
	
	String loaiNPP;
	double tienTichluy;
	dbutils db;
	Utility util;
	
	public ErpDondathangNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "2";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		this.loaiNPP = "";
		this.iskm="0";
		this.scheme_soluong = new Hashtable<String, String>();
		this.nhomkenhId = "";
		this.tienTichluy = 0;
		
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpDondathangNpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "2";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		this.loaiNPP = "";
		this.scheme_soluong = new Hashtable<String, String>();
		this.iskm="0";
		this.nhomkenhId = "";
		this.tienTichluy = 0;
		
		this.db = new dbutils();
		this.util = new Utility();
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
		
		/*if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}*/
		
		if( this.nhomkenhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn hệ thống bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		int countsp = 0;
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
			//CHECK XEM ĐÃ KHÓA SỔ KINH DOANH CHƯA
			Utility util = new Utility();
			
			String chuyenSALES = "0";
			int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.ngayyeucau, "", "", "");
			if( checkKS_KINHDOANH != -1 ) //Đã khóa sổ
			{
				if(checkKS_KINHDOANH == 0)
				{
					this.msg = "Ngày đơn hàng nằm trong tháng đã khóa sổ kinh doanh. Vui lòng kiểm tra lại";
					return false;
				}
				else
					chuyenSALES = "1";
			}
			
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			
			String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, nhomkenh_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NPP_DACHOT, TRUCTHUOC_FK, IsKM, chuyenSALES) " +
								  " select '" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', " + this.nhomkenhId + ", NULL, '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '0', tructhuoc_fk,'"+this.iskm+"', '" + chuyenSALES + "' " +
				           " from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
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
			
			System.out.println("DDH ID: " + this.id);
			
			if(this.loaidonhang.equals("0"))  //DON DAT HANG CHI LUU NHUNG SP KO CO SCHEME
			{
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
						ResultSet rs = db.get(query);
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
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, thueVAT, dvdl_fk ) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + spVAT[i].replaceAll(",", "") + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) " +
								"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						countsp++;
					}					
				}
			}
			else
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0)
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								   "from SANPHAM sp, DONVIDOLUONG dv " +
								   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
						
						System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.get(query);
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
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, thueVAT, dvdl_fk ) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + spVAT[i].replaceAll(",", "") + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) " +
								"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						countsp++;
					}
				}
			}
			
			if(countsp-1>10)//Nếu số dòng vượt quá 10 thì k cho lưu
			{
				this.msg = "TỔNG SỐ DÒNG CỦA ĐƠN ĐẶT HÀNG KHÔNG ĐƯỢC PHÉP QUÁ 10 DÒNG.";
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
		
		/*if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}*/
		
		if( this.nhomkenhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn hệ thống bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		int countsp = 0;
		
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
				if( spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
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
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0  )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[i].trim().length() <= 0 && spSCheme[j].trim().length() <= 0 )
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
			String chuyenSALES = "0";
			int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.ngayyeucau, "", "", "");
			if( checkKS_KINHDOANH != -1 ) //Đã khóa sổ
			{
				if(checkKS_KINHDOANH == 0)
				{
					this.msg = "Ngày đơn hàng nằm trong tháng đã khóa sổ kinh doanh. Vui lòng kiểm tra lại";
					return false;
				}
				else
					chuyenSALES = "1";
			}
			
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			
			String query =	" Update ERP_Dondathang set  ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
							" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = NULL, nhomkenh_fk = '" + this.nhomkenhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "',LoaiDonHang='"+this.loaidonhang+"',IsKm='"+this.iskm+"', chuyenSALES = '" + chuyenSALES + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.loaidonhang.equals("0"))  //DON DAT HANG CHI LUU NHUNG SP KO CO SCHEME
			{
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
						ResultSet rs = db.get(query);
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
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, thueVAT, dvdl_fk ) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + spVAT[i].replaceAll(",", "") + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) " +
								"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						countsp++;
					}
				}
			}
			else
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0)
					{
						//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
						query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
								   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
								   "from SANPHAM sp, DONVIDOLUONG dv " +
								   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
						
						System.out.println("-----CHECK QUY CACH: " + query );
						ResultSet rs = db.get(query);
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
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, thueVAT, dvdl_fk ) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + spVAT[i].replaceAll(",", "") + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) " +
								"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						countsp++;
					}
				}
			}
			
			if(countsp - 1 >10)//Nếu số dòng vượt quá 10 thì k cho lưu
			{
				this.msg = "TỔNG SỐ DÒNG CỦA ĐƠN ĐẶT HÀNG KHÔNG ĐƯỢC PHÉP QUÁ 10 DÒNG.";
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
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
		
		//LẤY trực thuộc và loại nhà phân phối, nếu loại là đối tác thì được phép chọn đơn vị tính lúc đặt hàng
		String query = " select tructhuoc_fk, loaiNPP from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
		ResultSet rs = db.get(query);
		String tructhuocId = "";
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.loaiNPP = rs.getString("loaiNPP");
					tructhuocId = rs.getString("tructhuoc_fk");
				}
				rs.close();
			} 
			catch (Exception e) { }
		}
		
		if(this.sitecode.trim().length() <= 5) //Đặt trực tiếp HO
			this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' ");
		else
		{
			this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId) +" and pk_seq in ( 100000, 100002,100001 ) ");
			//System.out.println("sqle ______________:select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId) +" and pk_seq in ( 100000, 100002,100001 ) ");
			//mặc định là đặt kho sỉ
			
			/*if(this.khoNhanId.trim().length() <= 0 )
			{
				if(tructhuocId.equals("106210"))
					this.khoNhanId = "100002";
				else
					this.khoNhanId = "100000";
			}*/
		}
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		//this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		this.nhomkenhRs = db.get("select PK_SEQ, DIENGIAI as TEN from NHOMKENH where TRANGTHAI = '1'  ");
		
		query = "select PK_SEQ, TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq = '" + this.nppId + "' ";
		this.nppRs = db.get(query);
		
		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
				    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
				    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
				    "where a.pk_Seq = '" + this.nppId + "' ";
			System.out.println("::: LAY KENH BAN HANG: " + query);
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
				catch (Exception e) {}
			}
			
			//INIT CONG NO
			/*query = " select a.MA, a.TEN, b.DONVI, ISNULL(HANGMUA.noHANGMUA, 0) as noHANGMUA, ISNULL(KM.noKHUYENMAI, 0) as noKHUYENMAI, " +
					" 			ISNULL(TL.noTICHLUY, 0) as noTICHLUY, ISNULL(TB.noTRUNGBAY, 0) as noTRUNGBAY, 0 as noDOIHANG " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ left join " +
					" (	 " +
					" 	 select ddh.PK_SEQ as sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noHANGMUA " +
					" 	 from    " +
					" 	 (    " +
					"  		select sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT     " +
					"  		from    " +
					"  		(    " +
					"  				select a.sanpham_fk,   " +
					"  						case when a.dvdl_fk IS null then a.soluong     " +
					"  							 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"  							 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"  											 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong  " +
					"  				from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"  				where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "'  and LoaiDonHang = '0'  )   " +
					"  			union ALL  " +
					"  				select b.PK_SEQ,  a.soluong   " +
					"  				from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA   " +
					"  						inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ   " +
					"  				where a.DONDATHANGID in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '0'   )    " +
					"  		)    " +
					"  		dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ    " +
					"  		group by sp.PK_SEQ   " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '0'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.PK_SEQ = daxuat.sanpham_fk	 " +
					" ) " +
					" HANGMUA on a.PK_SEQ = HANGMUA.sanpham_fk left join  " +
					" ( " +
					" 	 select ddh.sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noKHUYENMAI " +
					" 	 from    " +
					" 	 (    " +
					" 		select a.sanpham_fk, sum(a.soluong) as soluongDAT  " +
					" 		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '1'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '1'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk	 " +
					" ) " +
					" KM on a.PK_SEQ = KM.sanpham_fk left join  " +
					" ( " +
					" 	 select ddh.sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noTICHLUY " +
					" 	 from    " +
					" 	 (    " +
					" 		select a.sanpham_fk, sum(a.soluong) as soluongDAT  " +
					" 		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '2'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '2'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk	 " +
					" ) " +
					" TL on a.PK_SEQ = TL.sanpham_fk left join  " +
					" ( " +
					" 	select ddh.sanpham_fk, ddh.soluongDAT - ISNULL( daxuat.soluongDAXUAT, 0) as noTRUNGBAY " +
					" 	 from    " +
					" 	 (    " +
					" 		select a.sanpham_fk, sum(a.soluong) as soluongDAT  " +
					" 		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					" 		where a.dondathang_fk in (    select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '3'   )  " +
					" 		group by  a.sanpham_fk " +
					" 	 )    " +
					" 	 ddh left join     " +
					" 	 (    " +
					"  		select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    " +
					"  		from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk   " +
					"  		where a.TRANGTHAI in (2)    " +
					"  			and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  select PK_SEQ from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and LoaiDonHang = '3'  ) )   " +
					"  		group by b.sanpham_fk, b.LOAI, b.SCHEME   " +
					" 	 )   " +
					" 	 daxuat on ddh.sanpham_fk = daxuat.sanpham_fk " +
					" ) " +
					" TB on a.PK_SEQ = TB.sanpham_fk ";
			
			System.out.println("CONG NO: " + query);
			this.congnoRs = db.get(query);*/
			
		}
		
	}

	private void initSANPHAM() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, a.thueVAT    " +
						" from ERP_Dondathang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where a.Dondathang_FK = '" + this.id + "' ";
		
		System.out.println("--INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		NumberFormat formater2 = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spVAT = "";
				String spSCHEME = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					//spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spGIANHAP += formater2.format(spRs.getDouble("DONGIA")) + "__";
					spVAT += spRs.getDouble("thueVAT") + "__";
					spSCHEME += " __";
				}
				spRs.close();
				
				//INIT SP KHUYEN MAI
				query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri " +
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
					spVAT += "0__";
					spSCHEME += spRs.getString("SCHEME") + "__";
				}
				spRs.close();
				
				System.out.println("---SCHEME: " + spSCHEME );
				System.out.println("---DON GIA: " + spGIANHAP );
				
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
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public void init() 
	{
		String query = "select ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, nhomkenh_fk, npp_fk, kho_fk, chietkhau, vat, loaidonhang,isKm " +
						"from ERP_Dondathang where pk_seq = '" + this.id + "'";
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
					this.kbhId = rs.getString("kbh_fk") == null ? "" : rs.getString("kbh_fk");
					this.nhomkenhId = rs.getString("nhomkenh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.iskm = rs.getString("isKm")==null?"0": rs.getString("isKm");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.createRs();
		
		
		this.initSANPHAM();
		
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

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
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

	
	public String getSchemeId() {
		
		return this.schemeId;
	}

	
	public void setSchemeId(String kbhId) {
		
		this.schemeId = kbhId;
	}

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
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

	
	public String getLoainpp() {

		return this.loaiNPP;
	}


	public void setLoainpp(String loainpp) {
		
		this.loaiNPP = loainpp;
	}

	String iskm;
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }

	
	public String getNhomkenhId() {
		
		return this.nhomkenhId;
	}

	
	public void setNhomkenhId(String nhomkenhId) {
		
		this.nhomkenhId = nhomkenhId;
	}

	
	public ResultSet getNhomkenhRs() {
		
		return this.nhomkenhRs;
	}

	
	public void setNhomkenhRs(ResultSet nhomkenhRs) {
		
		this.nhomkenhRs = nhomkenhRs;
	}


	public double getTientichluy() {

		return this.tienTichluy;
	}


	public void setTientichluy(double tientichluy) {
		
		this.tienTichluy = tientichluy;
	}

	
	
}
