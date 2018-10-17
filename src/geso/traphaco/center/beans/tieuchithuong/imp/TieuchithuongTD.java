package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTD;
import geso.traphaco.center.db.sql.dbutils;

public class TieuchithuongTD implements ITieuchithuongTD 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	
	ResultSet sanphamRs;
	String spIds;
	ResultSet nppRs;
	String nppIds;
	
	ResultSet tichluyRs;
	String tichluyId;
	
	String[] diengiaiMuc;
	String[] tumuc;
	String[] denmuc;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
	
	String mucvuot;
	String ckMucvuot;
	String dvMucvuot;
	
	String msg;

	
	String apdungchoDAILY;
	String apdungchoHOPDONG;
	
	ResultSet kenhRs;
	String kbhIds;
	
	ResultSet loaiKhRs;
	String loaiKhIds;
	
	ResultSet nhomKhRs;
	String nhomKhIds;
	String chiavaodongia;
	
	String khoanmuccpId;
	ResultSet khoanmuccpRs;
	
	
	dbutils db;
	
	public TieuchithuongTD()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		
		this.mucvuot = "";
		this.ckMucvuot = "";
		this.dvMucvuot = "";	

		this.apdungchoDAILY = "0";
		this.apdungchoHOPDONG = "0";
		
		this.kbhIds = "";
		this.loaiKhIds = "";
		this.nhomKhIds = "";
		
		this.tichluyId = "";
		this.chiavaodongia = "1";
		
		this.khoanmuccpId = "";
	
		this.db = new dbutils();
	}
	
	public TieuchithuongTD(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		
		this.mucvuot = "";
		this.ckMucvuot = "";
		this.dvMucvuot = "";

		this.apdungchoDAILY = "0";
		this.apdungchoHOPDONG = "0";
		
		this.kbhIds = "";
		this.loaiKhIds = "";
		this.nhomKhIds = "";
		
		this.tichluyId = "";
		this.chiavaodongia = "1";
		
		this.khoanmuccpId = "";
		
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

	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createTctSKU( ) 
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			
			if(this.nppIds.trim().length() <= 0 )
			{
				this.msg = "Vui lòng chọn nhà phân phối áp dụng ";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String tichluy_fk = this.tichluyId.trim().length() <= 0 ? "NULL" : this.tichluyId;
			String khoanmuccp_fk = this.khoanmuccpId.trim().length() <= 0 ? "NULL" : this.khoanmuccpId;
			
			String query = "insert CHINHSACHBANHANG(scheme, tungay, denngay, ghichu, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, apdungchoDAILY, apdungchoHOPDONG, tichluy_fk, chiavaodongia, kmcp_fk) " +
						   "values(N'" + this.scheme + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							   "'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.apdungchoDAILY + "', '" + this.apdungchoHOPDONG + "', " + tichluy_fk + ", '" + this.chiavaodongia + "', " + khoanmuccp_fk + " )";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi CHINHSACHBANHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								" '" + this.thuongSR[i].replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert CHINHSACHBANHANG_TIEUCHI(chinhsachbanhang_fk, ghichu, tumuc, denmuc, chietkhau, donvi) " +
							"select IDENT_CURRENT('CHINHSACHBANHANG') as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "2.Khong the tao moi CHINHSACHBANHANG_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}	
			}
			
			if(this.nppIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_NPP(chinhsachbanhang_fk, npp_fk) " +
						"select IDENT_CURRENT('CHINHSACHBANHANG') as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.nhomKhIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_NHOMKHONGAPDUNG(chinhsachbanhang_fk, nhomkhachhang_fk) " +
						"select IDENT_CURRENT('CHINHSACHBANHANG') as tctId, pk_seq from NHOMKHACHHANG where pk_seq in (" + this.nhomKhIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_NHOMKHONGAPDUNG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_LOAIKHACHHANG(chinhsachbanhang_fk, loaikhachhang_fk) " +
						"select IDENT_CURRENT('CHINHSACHBANHANG') as tctId, pk_seq from LOAICUAHANG where pk_seq in (" + this.loaiKhIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_LOAIKHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.spIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_SANPHAM(chinhsachbanhang_fk, sanpham_fk) " +
						"select IDENT_CURRENT('CHINHSACHBANHANG') as tctId, pk_seq from SANPHAM where pk_seq in (" + this.spIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.kbhIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_KENH(chinhsachbanhang_fk, kbh_fk) " +
						"select IDENT_CURRENT('CHINHSACHBANHANG') as tctId, pk_seq from KENHBANHANG where pk_seq in (" + this.kbhIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_KENH: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} 
			catch (SQLException e1) {}
		}
		
		return true;
	}

	public boolean updateTctSKU()
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.nppIds.trim().length() <= 0 )
			{
				this.msg = "Vui lòng chọn nhà phân phối áp dụng ";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String tichluy_fk = this.tichluyId.trim().length() <= 0 ? "NULL" : this.tichluyId;
			String khoanmuccp_fk = this.khoanmuccpId.trim().length() <= 0 ? "NULL" : this.khoanmuccpId;
			
			String query =  "update CHINHSACHBANHANG set scheme = N'" + this.scheme + "', tungay = '" + this.thang + "', denngay = '" + this.nam + "', ghichu = N'" + this.diengiai + "', apdungchoDAILY = '" + this.apdungchoDAILY + "', apdungchoHOPDONG = '" + this.apdungchoHOPDONG + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', tichluy_fk = " + tichluy_fk + ", chiavaodongia = '" + this.chiavaodongia + "' , kmcp_fk = " + khoanmuccp_fk +
							" where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTD: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete CHINHSACHBANHANG_NPP where chinhsachbanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat CHINHSACHBANHANG_NHOMKHONGAPDUNG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete CHINHSACHBANHANG_NHOMKHONGAPDUNG where chinhsachbanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat CHINHSACHBANHANG_NHOMKHONGAPDUNG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete CHINHSACHBANHANG_TIEUCHI where chinhsachbanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat CHINHSACHBANHANG_NHOMKHONGAPDUNG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat CHINHSACHBANHANG_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete CHINHSACHBANHANG_KENH where chinhsachbanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat CHINHSACHBANHANG_KENH: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete CHINHSACHBANHANG_LOAIKHACHHANG where chinhsachbanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat CHINHSACHBANHANG_LOAIKHACHHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								" '" + this.thuongSR[i].replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert CHINHSACHBANHANG_TIEUCHI(chinhsachbanhang_fk, ghichu, tumuc, denmuc, chietkhau, donvi) " +
							"select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "2.Khong the tao moi CHINHSACHBANHANG_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}	
			}
			
			if(this.nppIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_NPP(chinhsachbanhang_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.nhomKhIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_NHOMKHONGAPDUNG(chinhsachbanhang_fk, nhomkhachhang_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from NHOMKHACHHANG where pk_seq in (" + this.nhomKhIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_NHOMKHONGAPDUNG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_LOAIKHACHHANG(chinhsachbanhang_fk, loaikhachhang_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from LOAICUAHANG where pk_seq in (" + this.loaiKhIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_LOAIKHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.spIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_SANPHAM(chinhsachbanhang_fk, sanpham_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from SANPHAM where pk_seq in (" + this.spIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.kbhIds.trim().length() > 0)
			{
				query = "Insert CHINHSACHBANHANG_KENH(chinhsachbanhang_fk, kbh_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from KENHBANHANG where pk_seq in (" + this.kbhIds + ") ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi CHINHSACHBANHANG_KENH: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1) {}
			
			System.out.println("115.Error: " + e.getMessage());
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select scheme, tungay, denngay, ghichu, mucvuot, donviMucVuot, apdungchoDAILY, apdungchoHOPDONG, tichluy_fk, chiavaodongia  " +
					   "from CHINHSACHBANHANG where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				//NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("tungay");
					this.nam = rs.getString("denngay");					
					this.diengiai= rs.getString("ghichu");
					this.apdungchoDAILY = rs.getString("apdungchoDAILY");
					this.apdungchoHOPDONG = rs.getString("apdungchoHOPDONG");	
					this.tichluyId = rs.getString("tichluy_fk") == null ? "" : rs.getString("tichluy_fk");
					this.chiavaodongia = rs.getString("chiavaodongia") == null ? "0" : rs.getString("chiavaodongia");
						
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		this.createNdk();
		this.createRs();
	}
	
	private void createNdk() 
	{
		String query =  "select ghichu, tumuc, denmuc, chietkhau, donvi " +
						"from CHINHSACHBANHANG_TIEUCHI " +
						"where chinhsachbanhang_fk = '" + this.id + "'  ";

		System.out.println("___Khoi tao tieu chi: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			NumberFormat format2 = new DecimalFormat("##,###,###.##");
			try 
			{
				String tieu_chi = "";
				String tu_muc = "";
				String den_muc = "";
				String chiet_khau = "";
				String don_vi = "";


				while(rs.next())
				{
					tieu_chi += rs.getString("ghichu") + ",,";
					tu_muc += format.format(rs.getDouble("tumuc")) + ",,";
					den_muc += format.format(rs.getDouble("denmuc")) + ",,";
					chiet_khau += format2.format(rs.getDouble("chietkhau")) + ",,";
					don_vi += format.format(rs.getDouble("donvi")) + ",,";
				}
				rs.close();

				if(tieu_chi.trim().length() > 0)
				{
					tieu_chi = tieu_chi.substring(0, tieu_chi.length() - 2);
					this.diengiaiMuc = tieu_chi.split(",,");

					tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
					this.tumuc = tu_muc.split(",,");

					den_muc = den_muc.substring(0, den_muc.length() - 2);
					this.denmuc = den_muc.split(",,");

					chiet_khau = chiet_khau.substring(0, chiet_khau.length() - 2);
					this.thuongSR = chiet_khau.split(",,");

					don_vi = don_vi.substring(0, don_vi.length() - 2);
					this.thuongTDSR = don_vi.split(",,");

				}
			} 
			catch (Exception e) {

				System.out.println("Loi khoi tao: " + e.toString());
			}
		}

		query = "select npp_fk from CHINHSACHBANHANG_NPP where chinhsachbanhang_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select nhomkhachhang_fk from CHINHSACHBANHANG_NHOMKHONGAPDUNG where chinhsachbanhang_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO NHOM: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("nhomkhachhang_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nhomKhIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("44.Loi khoi tao: " + e.toString());
			}
		}
		
		
		query = "select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO SP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("sanpham_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.spIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("44.Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select kbh_fk from CHINHSACHBANHANG_KENH where chinhsachbanhang_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO KENH: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("kbh_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.kbhIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("44.Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select loaikhachhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where chinhsachbanhang_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO LOAI KHACH HANG: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("loaikhachhang_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.loaiKhIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("44.Loi khoi tao: " + e.toString());
			}
		}
		
		
		
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}
	
	public String[] getDiengiaiMuc() {
		
		return this.diengiaiMuc;
	}

	
	public void setDiengiaiMuc(String[] diengiai) {
		
		this.diengiaiMuc = diengiai;
	}

	
	public String[] getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String[] tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String[] getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String[] denmuc) {
		
		this.denmuc = denmuc;
	}

	
	public String[] getThuongSR() {
		
		return this.thuongSR;
	}

	
	public void setThuongSR(String[] thuongSR) {
		
		this.thuongSR = thuongSR;
	}

	
	public String[] getThuongTDSR() {
		
		return this.thuongTDSR;
	}

	
	public void setThuongTDSR(String[] thuongTDSR) {
		
		this.thuongTDSR = thuongTDSR;
	}

	
	public String[] getThuongSS() {
		
		return this.thuongSS;
	}

	
	public void setThuongSS(String[] thuongSS) {
		
		this.thuongSS = thuongSS;
	}

	
	public String[] getThuongTDSS() {
		
		return this.thuongTDSS;
	}

	
	public void setThuongTDSS(String[] thuongTDSS) {
		
		this.thuongTDSS = thuongTDSS;
	}

	
	public String[] getThuongASM() {
		
		return this.thuongASM;
	}

	
	public void setThuongASM(String[] thuongASM) {
		
		this.thuongASM = thuongASM;
	}

	
	public String[] getThuongTDASM() {
		
		return this.thuongTDASM;
	}

	
	public void setThuongTDASM(String[] thuongTDASM) {
		
		this.thuongTDASM = thuongTDASM;
	}

	public void createRs() {
		
		String query = "select PK_SEQ, DIENGIAI as ten from NHOMKHACHHANG where TRANGTHAI = '1' order by DIENGIAI asc ";
		this.nhomKhRs = db.get(query);

		query = " select PK_SEQ, MA, TEN from NhaPhanPhoi where trangthai = '1' and loaiNPP in ( 0, 1 ) order by TEN asc ";
		this.nppRs = db.get(query);
		
		query = " select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where trangthai = '1'  order by DIENGIAI asc ";
		this.kenhRs = db.get(query);
		
		query = " select PK_SEQ, DIENGIAI as TEN from LOAICUAHANG where trangthai = '1'  order by DIENGIAI asc ";
		this.loaiKhRs = db.get(query);
		
		query = " select PK_SEQ, MA, TEN from SANPHAM where trangthai = '1' and LOAISANPHAM_FK in ( 10043, 10045 )  order by TEN asc ";
		this.sanphamRs = db.get(query);
		
		query = " select PK_SEQ, SCHEME from TIEUCHITHUONGTL where TRANGTHAI = '1' and loaict = '0' order by PK_SEQ desc ";
		this.tichluyRs = db.get(query);
		
		query = "select PK_SEQ, TEN + ', ' + DIENGIAI as diengiai from ERP_NHOMCHIPHI where TRANGTHAI = '1' order by DIENGIAI asc";
		this.khoanmuccpRs = db.get(query);
	}
	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public String getSpIds() {
		
		return this.spIds;
	}

	
	public void setSpIds(String spIds) {
		
		this.spIds = spIds;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds(String nppIds) {
		
		this.nppIds = nppIds;
	}

	
	public String getMucvuot() {
		
		return this.mucvuot;
	}

	
	public void setMucvuot(String mucvuot) {
		
		this.mucvuot = mucvuot;
	}

	
	public String getChietkhauMucvuot() {
		
		return this.ckMucvuot;
	}

	
	public void setChietkhauMucvuot(String ckMv) {
		
		this.ckMucvuot = ckMv;
	}

	
	public String getDonviMucvuot() {
		
		return this.dvMucvuot;
	}

	
	public void setDonviMucvuot(String dvMv) {
		
		this.dvMucvuot = dvMv;
	}

	public String getApdungchodaily() {

		return this.apdungchoDAILY;
	}


	public void setApdungchodaily(String isThung) {
		
		this.apdungchoDAILY = isThung;
	}


	public String getApdungchohopdong() {

		return this.apdungchoHOPDONG;
	}


	public void setApdungchohopdong(String isThung) {
		
		this.apdungchoHOPDONG = isThung;
	}
	
	public void setKbhRs(ResultSet kbh)
	{
		this.kenhRs = kbh;
	}

	public ResultSet getKbhRs()
	{
		return this.kenhRs;
	}

	public void setKbhIds(String kbhIds) 
	{
		this.kbhIds = kbhIds;
	}

	public String getKbhIds() 
	{
		return this.kbhIds;
	}
	
	public void setLoaikhRs(ResultSet loaiKh) {
		
		this.loaiKhRs = loaiKh;
	}

	
	public ResultSet getLoaikhRs() {
		
		return this.loaiKhRs;
	}

	
	public void setLoaikhIds(String lkhIds) {
		
		this.loaiKhIds = lkhIds;
	}

	
	public String getLoaikhIds() {
		
		return this.loaiKhIds;
	}

	
	public void setNhomkhachhangRs(ResultSet nhomRs) {
		
		this.nhomKhRs = nhomRs;
	}

	
	public ResultSet getNhomkhachhangRs() {
		
		return this.nhomKhRs;
	}

	
	public String getNhomkhachhangIds() {
		
		return this.nhomKhIds;
	}

	
	public void setNhomkhachhangIds(String nhomIds) {
		
		this.nhomKhIds = nhomIds;
	}

	
	public void setTichluyRs(ResultSet tichluyRs) {
		
		this.tichluyRs = tichluyRs;
	}

	
	public ResultSet getTichluyRs() {
		
		return this.tichluyRs;
	}

	
	public String getTichluyIds() {
		
		return this.tichluyId;
	}

	
	public void setTichluyIds(String tichluyIds) {
		
		this.tichluyId = tichluyIds;
	}
	
	public String getChiavaodongia() {

		return this.chiavaodongia;
	}


	public void setChiavaodongia(String chiavaodongia) {
		
		this.chiavaodongia = chiavaodongia;
	}

	public void setKhoanmuccpRs(ResultSet khoanmucRs) {
		
		this.khoanmuccpRs = khoanmucRs;
	}

	
	public ResultSet getKhoanmuccpRs() {
		
		return this.khoanmuccpRs;
	}

	
	public void setKhoanmuccpId(String khoanmuccpId) {
		
		this.khoanmuccpId = khoanmuccpId;
	}

	
	public String getKhoanmuccpId() {
		
		return this.khoanmuccpId;
	}

}
