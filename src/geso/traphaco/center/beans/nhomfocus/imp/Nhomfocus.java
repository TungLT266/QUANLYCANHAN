package geso.traphaco.center.beans.nhomfocus.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.traphaco.center.beans.nhomfocus.INhomfocus;

import geso.traphaco.center.db.sql.dbutils;

public class Nhomfocus implements INhomfocus
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
	
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;
	
	String[] diengiaiMuc;
	String[] tumuc;
	String[] denmuc;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
	
	String[] diengiaiMuc3;
	String[] tumuc3;
	String[] denmuc3;
	String[] thuongSR3;
	String[] thuongTDSR3;
	String[] thuongSS3;
	String[] thuongTDSS3;
	String[] thuongASM3;
	String[] thuongTDASM3;
	
	String mucvuot;
	String ckMucvuot;
	String dvMucvuot;
	
	String hinhthucTra;
	String[] maspTra;
	String[] tenspTra;
	String[] isspTra;
	String[] soluongspTra;
	
	String doanhsotheoThung;
	String msg;
	
	dbutils db;
	
	public Nhomfocus()
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
		
		this.vungIds = "";
		this.kvIds = "";
		this.hinhthucTra = "0";
		
		this.doanhsotheoThung = "0";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.db = new dbutils();
	}
	
	public Nhomfocus(String id)
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
		
		this.vungIds = "";
		this.kvIds = "";
		this.hinhthucTra = "0";
		
		this.doanhsotheoThung = "0";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
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
	
	public boolean save()
	{
		try
		{
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if (this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}			
			if (this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm trong điều kiện";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String	query =
					" INSERT INTO [ChiTieu]([TrucThuoc_Fk],[DoiTuong],[Thang],[Nam],[Quy],[Ma],[Ten],[NguoiTao],[NguoiSua],[NgayTao],[NgaySua],[TrangThai],[ApDung],[LoaiChiTieu])" +
					" select '"+1+"','CN/DT',"+(this.thang.length()<=0?"NULL":this.thang)+","+(this.nam.length()<=0?"NULL":this.nam)+","+(this.quy.length()<=0?"NULL":this.quy)+",N'"+this.scheme+"',N'"+this.diengiai+"','"+this.userId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.getDateTime()+"',0,'"+this.apdung+"','"+this.loaichitieu+"' ";
			System.out.println("1.Insert: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select scope_identity() as pxkId";
			ResultSet	rs = db.get(query);
			rs.next();
			this.id = rs.getString("pxkId");
			rs.close();
			
			
			if (this.tumuc != null)
			{
				String sql = "";
				for (int i = 0; i < this.tumuc.length; i++)
				{
					if (this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuong[i].trim().length()>0 && this.donvi_tinh_ds[i].trim().length()>0 && this.donvi_tinh_thuong[i].trim().length()>0 )
					{
							sql += "select '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc, N'" + this.donvi_tinh_ds[i] + "' as donvi_tinh_ds ," + " '" + this.thuong[i] .replaceAll(",", "") + "' as thuong,N'" + this.donvi_tinh_thuong[i] + "' as donvi_tinh_thuong union";
					}
				}
				
				if (sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert ChiTieu_TieuChi(ChiTieu_fk,TuMuc,DenMuc,DonViTinh_ChiTieu,Thuong,DonViTinh_Thuong) " + "select '"+this.id+"' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					System.out.println("2.Insert: " + query);
					if (!db.update(query))
					{
						this.msg = "2.Khong the tao moi ChiTieu_TieuChi: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			if (this.spIds.trim().length() > 0)
			{				
				query = "Insert ChiTieu_SanPham(ChiTieu_fk, sanpham_fk) " + "select '"+this.id+"' as tctId, pk_seq from SanPham where pk_seq in (" + this.spIds + ") ";
				System.out.println("3.Insert: " + query);
				if (!db.update(query))
				{
					this.msg = "3.Khong the tao moi ChiTieu_SanPham: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			String sql="";
			Enumeration<String> keys = this.npp_chitieu.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value=this.npp_chitieu.get(key);
				String donvi=this.npp_donvi_chitieu.get(key);
				sql="select '"+key+"' as nppId,'"+value+"' as ChiTieu,'"+donvi+"' as DonVi union "; 
				System.out.println("KEY____"+key);
			}
			
			if (this.nppIds.trim().length() > 0)
			{
				sql = sql.substring(0, sql.length() - 6);
				query = 
					"Insert ChiTieu_DoiTuong(ChiTieu_fk, npp_fk,ChiTieu,DonViTinh_ChiTieu) " + 
					"select '"+this.id+"' as tctId, tieuchi.* from (" + sql + ") tieuchi  ";
				System.out.println("4.Insert: " + query);
				if (!db.update(query))
				{
					this.msg = "3.Khong the tao moi ChiTieu_Npp: " + query;
					db.getConnection().rollback();
					return false;
				}
			}			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1)
			{
			}
		}
		
		return true;
	}
	
	public boolean edit()
	{
		try
		{
			if (this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if (this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			// Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTL where scheme = N'" + this.scheme + "' and pk_seq != '" + this.id + "'";
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				int count = 0;
				if (rs.next())
				{
					count = rs.getInt("sodong");
					if (count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			// Check tieu chi
			if (this.diengiaiMuc == null)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			} else
			{
				boolean flag = false;
				for (int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if (this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						flag = true;
					}
				}
				
				if (!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các tiêu chí";
					return false;
				}
			}
			
			if (this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm trong điều kiện";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String mucvuot = this.mucvuot.trim().length() <= 0 ? "null" : this.mucvuot.replaceAll(",", "");
			String ckMucvuot = this.ckMucvuot.trim().length() <= 0 ? "null" : this.ckMucvuot.replaceAll(",", "");
			
			query = "update TieuchithuongTL set scheme = N'" + this.scheme + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " + "ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', mucvuot = " + mucvuot + ", chietkhauMucVuot = " + ckMucvuot + ", donviMucVuot = '" + this.dvMucvuot + "', hinhthuctra = '" + this.hinhthucTra + "', DOANHSOTHEOTHUNG = '" + this.doanhsotheoThung + "' " + "where pk_seq = '" + this.id + "'";
			
			System.out.println("1.Update: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTL: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTL_TIEUCHI where thuongtl_fk = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTL_TIEUCHI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_MUCTHUONG where thuongtl_fk = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_MUCTHUONG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_NPP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (this.diengiaiMuc != null)
			{
				String sql = "";
				for (int i = 0; i < this.diengiaiMuc.length; i++)
				{
					// System.out.println("___THUONG SR: " + this.thuongSR[i] +
					// " -- Thuong SS: " + this.thuongSS[i]);
					if (this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						/*
						 * String ck = this.thuongSR[i].replaceAll(",", "");
						 * if(this.thuongTDSR[i].trim().equals("2")) ck = "null";
						 */
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " + " '" + this.thuongSR[i]
						    .replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi union ";
						
					}
				}
				
				if (sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTL_TIEUCHI(thuongtl_fk, ghichu, tumuc, denmuc, chietkhau, donvi) " + "select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if (!db.update(query))
					{
						this.msg = "2.Khong the tao moi TieuchithuongTL_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			if (this.thuongSR3 != null)
			{
				String sql = "";
				for (int i = 0; i < this.diengiaiMuc3.length; i++)
				{
					// System.out.println("___THUONG SR: " + this.thuongSR[i] +
					// " -- Thuong SS: " + this.thuongSS[i]);
					if (this.thuongSR3[i].trim().length() > 0)
					{
						String thuongTD_SR = "0";
						if (this.thuongTDSR3[i].trim().length() > 0)
							thuongTD_SR = this.thuongTDSR3[i].trim().replaceAll(",", "");
						
						String thuong_SS = "0";
						if (this.thuongSS3[i].trim().length() > 0)
							thuong_SS = this.thuongSS3[i].trim().replaceAll(",", "");
						
						String thuongTD_SS = "0";
						if (this.thuongTDSS3[i].trim().length() > 0)
							thuongTD_SS = this.thuongTDSS3[i].trim().replaceAll(",", "");
						
						String thuong_ASM = "0";
						if (this.thuongASM3[i].trim().length() > 0)
							thuong_ASM = this.thuongASM3[i].trim().replaceAll(",", "");
						
						String thuongTD_ASM = "0";
						if (this.thuongTDASM3[i].trim().length() > 0)
							thuongTD_ASM = this.thuongTDASM3[i].trim().replaceAll(",", "");
						
						sql += "select N'" + this.diengiaiMuc3[i].replaceAll(",", "") + "' as ghichu,  " + "'" + this.thuongSR3[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if (sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TIEUCHITHUONGTL_MUCTHUONG(thuongtl_fk, ghichu, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " + "select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if (!db.update(query))
					{
						this.msg = "2.Khong the tao moi TIEUCHITHUONGTL_MUCTHUONG: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			
			if (this.spIds.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) " + "select '" + this.id + "' as tctId, pk_seq from SanPham where pk_seq in (" + this.spIds + ") ";
				
				System.out.println("3.Insert: " + query);
				if (!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if (this.spIds.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk) " + "select '" + this.id + "' as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("3.Insert: " + query);
				if (!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = "delete TIEUCHITHUONGTL_SPTRA where thuongtl_fk = '" + this.id + "' ";
			if (!db.update(query))
			{
				this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SPTRA: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (this.maspTra != null)
			{
				String spTra = "";
				for (int i = 0; i < this.maspTra.length; i++)
				{
					if (this.maspTra[i].trim().length() > 0)
					{
						if (this.hinhthucTra.equals("0"))
						{
							spTra += " select pk_seq, null as soluong from SANPHAM where ma = '" + this.maspTra[i] + "' ";
							spTra += " union ";
						} else
						{
							if (this.soluongspTra[i].trim().length() > 0)
							{
								spTra += " select pk_seq, null as soluong from SANPHAM where ma = '" + this.maspTra[i] + "' ";
								spTra += " union ";
							}
						}
					}
				}
				
				if (spTra.trim().length() > 0)
					spTra = spTra.substring(0, spTra.length() - 6);
				
				query = "Insert TIEUCHITHUONGTL_SPTRA(thuongtl_fk, sanpham_fk, soluong) " + "select '" + this.id + "' as tctId, * from ( " + spTra + " ) sp where sp.pk_seq is not null ";
				
				System.out.println("5.Insert: " + query);
				if (!db.update(query))
				{
					this.msg = "5.Khong the tao moi TIEUCHITHUONGTL_SPTRA: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			try
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1)
			{
			}
			
			System.out.println("115.Error: " + e.getMessage());
		}
		
		return true;
	}
	
	public void init()
	{
		String query = "select scheme, thang, nam, diengiai, mucvuot, chietkhauMucVuot, donviMucVuot, hinhthuctra, DOANHSOTHEOTHUNG  " + "from TieuchithuongTL where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				while (rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("diengiai");
					
					this.mucvuot = rs.getString("mucvuot") != null ? format.format(rs.getDouble("mucvuot")) : "";
					this.ckMucvuot = rs.getString("chietkhauMucVuot") != null ? format.format(rs.getDouble("chietkhauMucVuot")) : "";
					this.dvMucvuot = rs.getString("donviMucVuot");
					this.hinhthucTra = rs.getString("hinhthuctra");
					this.doanhsotheoThung = rs.getString("DOANHSOTHEOTHUNG");
					
				}
				rs.close();
			} catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		// Init San Pham Tra
		query = "select b.MA, b.TEN, isnull(a.soluong, 0) as soluong " + "from TIEUCHITHUONGTL_SPTRA a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " + "where a.thuongtl_fk = '" + this.id + "'";
		
		System.out.println("__LAY SP TRA: " + query);
		rs = db.get(query);
		if (rs != null)
		{
			try
			{
				String spMa = "";
				String spTen = "";
				String spSoluong = "";
				
				while (rs.next())
				{
					spMa += rs.getString("MA") + ",,";
					spTen += rs.getString("TEN") + ",,";
					
					if (this.hinhthucTra.equals("1"))
						spSoluong += rs.getString("soluong") + ",,";
					else
						spSoluong += " " + ",,";
					
				}
				rs.close();
				
				System.out.println("---TEN SP: " + spTen + "  -- MA SP: " + spMa);
				if (spMa.trim().length() > 0)
				{
					spMa = spMa.substring(0, spMa.length() - 2);
					this.maspTra = spMa.split(",,");
					
					spTen = spTen.substring(0, spTen.length() - 2);
					this.tenspTra = spTen.split(",,");
				}
				
				if (spSoluong.trim().length() > 0)
				{
					spSoluong = spSoluong.substring(0, spSoluong.length() - 2);
					this.soluongspTra = spSoluong.split(",,");
				}
				
			} catch (Exception e)
			{
				System.out.println("__EXCEPTION INIT: " + e.getMessage());
			}
		}
		
		this.createNdk();
		this.createRs();
	}
	
	private void createNdk()
	{
		String query = "select ghichu, tumuc, denmuc, chietkhau, donvi " + "from TieuchithuongTL_TIEUCHI " + "where thuongtl_fk = '" + this.id + "'  ";
		
		System.out.println("___Khoi tao tieu chi: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
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
				
				while (rs.next())
				{
					tieu_chi += rs.getString("ghichu") + ",,";
					tu_muc += format.format(rs.getDouble("tumuc")) + ",,";
					den_muc += format.format(rs.getDouble("denmuc")) + ",,";
					chiet_khau += format2.format(rs.getDouble("chietkhau")) + ",,";
					don_vi += format.format(rs.getDouble("donvi")) + ",,";
				}
				rs.close();
				
				if (tieu_chi.trim().length() > 0)
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
			} catch (Exception e)
			{
				
				System.out.println("Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select ghichu, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM " + "from TIEUCHITHUONGTL_MUCTHUONG " + "where thuongtl_fk = '" + this.id + "'  ";
		
		System.out.println("___KHOI TAO MUC 2: " + query);
		
		rs = db.get(query);
		if (rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			try
			{
				String tieu_chi = "";
				String thuong_SR = "";
				String thuong_TDSR = "";
				String thuong_SS = "";
				String thuong_TDSS = "";
				String thuong_ASM = "";
				String thuong_TDASM = "";
				
				while (rs.next())
				{
					tieu_chi += rs.getString("ghichu") + ",,";
					thuong_SR += format.format(rs.getDouble("thuongSR")) + ",,";
					thuong_TDSR += format.format(rs.getDouble("thuongTDSR")) + ",,";
					thuong_SS += format.format(rs.getDouble("thuongSS")) + ",,";
					thuong_TDSS += format.format(rs.getDouble("thuongTDSS")) + ",,";
					thuong_ASM += format.format(rs.getDouble("thuongASM")) + ",,";
					thuong_TDASM += format.format(rs.getDouble("thuongTDASM")) + ",,";
				}
				rs.close();
				
				if (tieu_chi.trim().length() > 0)
				{
					tieu_chi = tieu_chi.substring(0, tieu_chi.length() - 2);
					this.diengiaiMuc3 = tieu_chi.split(",,");
					
					thuong_SR = thuong_SR.substring(0, thuong_SR.length() - 2);
					this.thuongSR3 = thuong_SR.split(",,");
					
					thuong_TDSR = thuong_TDSR.substring(0, thuong_TDSR.length() - 2);
					this.thuongTDSR3 = thuong_TDSR.split(",,");
					
					thuong_SS = thuong_SS.substring(0, thuong_SS.length() - 2);
					this.thuongSS3 = thuong_SS.split(",,");
					
					thuong_TDSS = thuong_TDSS.substring(0, thuong_TDSS.length() - 2);
					this.thuongTDSS3 = thuong_TDSS.split(",,");
					
					thuong_ASM = thuong_ASM.substring(0, thuong_ASM.length() - 2);
					this.thuongASM3 = thuong_ASM.split(",,");
					
					thuong_TDASM = thuong_TDASM.substring(0, thuong_TDASM.length() - 2);
					this.thuongTDASM3 = thuong_TDASM.split(",,");
				}
			} catch (Exception e)
			{
				
				System.out.println("22.Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO SP: " + query);
		
		rs = db.get(query);
		if (rs != null)
		{
			try
			{
				String spId = "";
				while (rs.next())
				{
					spId += rs.getString("sanpham_fk") + ",";
				}
				rs.close();
				
				if (spId.trim().length() > 0)
					this.spIds = spId.substring(0, spId.length() - 1);
			} catch (Exception e)
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		System.out.println("__SP ID: " + this.spIds);
		
		query = "select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if (rs != null)
		{
			try
			{
				String nppId = "";
				while (rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
				}
				rs.close();
				
				if (nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} catch (Exception e)
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		System.out.println("__NPP ID: " + this.nppIds);
		
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
	
	public String[] getDiengiaiMuc()
	{
		
		return this.diengiaiMuc;
	}
	
	public void setDiengiaiMuc(String[] diengiai)
	{
		
		this.diengiaiMuc = diengiai;
	}
	
	public String[] getTumuc()
	{
		
		return this.tumuc;
	}
	
	public void setTumuc(String[] tumuc)
	{
		
		this.tumuc = tumuc;
	}
	
	public String[] getDenmuc()
	{
		
		return this.denmuc;
	}
	
	public void setDenmuc(String[] denmuc)
	{
		
		this.denmuc = denmuc;
	}
	
	public String[] getThuongSR()
	{
		
		return this.thuongSR;
	}
	
	public void setThuongSR(String[] thuongSR)
	{
		
		this.thuongSR = thuongSR;
	}
	
	public String[] getThuongTDSR()
	{
		
		return this.thuongTDSR;
	}
	
	public void setThuongTDSR(String[] thuongTDSR)
	{
		
		this.thuongTDSR = thuongTDSR;
	}
	
	public String[] getThuongSS()
	{
		
		return this.thuongSS;
	}
	
	public void setThuongSS(String[] thuongSS)
	{
		
		this.thuongSS = thuongSS;
	}
	
	public String[] getThuongTDSS()
	{
		
		return this.thuongTDSS;
	}
	
	public void setThuongTDSS(String[] thuongTDSS)
	{
		
		this.thuongTDSS = thuongTDSS;
	}
	
	public String[] getThuongASM()
	{
		
		return this.thuongASM;
	}
	
	public void setThuongASM(String[] thuongASM)
	{
		
		this.thuongASM = thuongASM;
	}
	
	public String[] getThuongTDASM()
	{
		
		return this.thuongTDASM;
	}
	
	public void setThuongTDASM(String[] thuongTDASM)
	{
		
		this.thuongTDASM = thuongTDASM;
	}
	
	public void createRs()
	{
		
		String query = "select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK  from SanPham where trangthai = '1' ";
		
		if (this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK from SanPham where pk_seq in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "' ) ";
		}
		
		query += " order by TRANGTHAI desc, NHANHANG_FK asc, CHUNGLOAI_FK asc ";
		this.sanphamRs = db.getScrol(query);
		
		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
		
		query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if (this.vungIds.trim().length() > 0)
			query += " and vung_fk in ( " + this.vungIds + " ) ";
		this.kvRs = db.get(query);
		
		query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and PRIANDSECOND = '0' ";
		if (this.kvIds.trim().length() > 0)
			query += " and khuvuc_fk in ( " + this.kvIds + " ) ";
		if (this.vungIds.trim().length() > 0)
			query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
		
		if (this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ) ";
		}
		
		//
		if (this.nppIds.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds + " ) ";
		}
		
		query += " order by PK_SEQ desc ";
		this.nppRs = db.getScrol(query);
		
	}
	
	public String[] getDiengiaiMuc3()
	{
		
		return this.diengiaiMuc3;
	}
	
	public void setDiengiaiMuc3(String[] diengiai)
	{
		
		this.diengiaiMuc3 = diengiai;
	}
	
	public String[] getTumuc3()
	{
		
		return this.tumuc3;
	}
	
	public void setTumuc3(String[] tumuc)
	{
		
		this.tumuc3 = tumuc;
	}
	
	public String[] getDenmuc3()
	{
		
		return this.denmuc3;
	}
	
	public void setDenmuc3(String[] denmuc)
	{
		
		this.denmuc3 = denmuc;
	}
	
	public String[] getThuongSR3()
	{
		
		return this.thuongSR3;
	}
	
	public void setThuongSR3(String[] thuongSR)
	{
		
		this.thuongSR3 = thuongSR;
	}
	
	public String[] getThuongTDSR3()
	{
		
		return this.thuongTDSR3;
	}
	
	public void setThuongTDSR3(String[] thuongTDSR)
	{
		
		this.thuongTDSR3 = thuongTDSR;
	}
	
	public String[] getThuongSS3()
	{
		
		return this.thuongSS3;
	}
	
	public void setThuongSS3(String[] thuongSS)
	{
		
		this.thuongSS3 = thuongSS;
	}
	
	public String[] getThuongTDSS3()
	{
		
		return this.thuongTDSS3;
	}
	
	public void setThuongTDSS3(String[] thuongTDSS)
	{
		
		this.thuongTDSS3 = thuongTDSS;
	}
	
	public String[] getThuongASM3()
	{
		
		return this.thuongASM3;
	}
	
	public void setThuongASM3(String[] thuongASM)
	{
		
		this.thuongASM3 = thuongASM;
	}
	
	public String[] getThuongTDASM3()
	{
		
		return this.thuongTDASM3;
	}
	
	public void setThuongTDASM3(String[] thuongTDASM)
	{
		
		this.thuongTDASM3 = thuongTDASM;
	}
	
	public void setSanphamRs(ResultSet spRs)
	{
		
		this.sanphamRs = spRs;
	}
	
	public ResultSet getSanphamRs()
	{
		
		return this.sanphamRs;
	}
	
	public String getSpIds()
	{
		
		return this.spIds;
	}
	
	public void setSpIds(String spIds)
	{
		
		this.spIds = spIds;
	}
	
	public void setNppRs(ResultSet nppRs)
	{
		
		this.nppRs = nppRs;
	}
	
	public ResultSet getNppRs()
	{
		
		return this.nppRs;
	}
	
	public String getNppIds()
	{
		
		return this.nppIds;
	}
	
	public void setNppIds(String nppIds)
	{
		
		this.nppIds = nppIds;
	}
	
	public String getMucvuot()
	{
		
		return this.mucvuot;
	}
	
	public void setMucvuot(String mucvuot)
	{
		
		this.mucvuot = mucvuot;
	}
	
	public String getChietkhauMucvuot()
	{
		
		return this.ckMucvuot;
	}
	
	public void setChietkhauMucvuot(String ckMv)
	{
		
		this.ckMucvuot = ckMv;
	}
	
	public String getDonviMucvuot()
	{
		
		return this.dvMucvuot;
	}
	
	public void setDonviMucvuot(String dvMv)
	{
		
		this.dvMucvuot = dvMv;
	}
	
	public void setVungRs(ResultSet vungRs)
	{
		
		this.vungRs = vungRs;
	}
	
	public ResultSet getVungRs()
	{
		
		return this.vungRs;
	}
	
	public String getVungIds()
	{
		
		return this.vungIds;
	}
	
	public void setVungIds(String vungIds)
	{
		
		this.vungIds = vungIds;
	}
	
	public void setKhuvucRs(ResultSet kvRs)
	{
		
		this.kvRs = kvRs;
	}
	
	public ResultSet getKhuvucRs()
	{
		
		return this.kvRs;
	}
	
	public String getKhuvucIds()
	{
		
		return this.kvIds;
	}
	
	public void setKhuvucIds(String kvIds)
	{
		
		this.kvIds = kvIds;
	}
	
	public String getHinhthuctra()
	{
		
		return this.hinhthucTra;
	}
	
	public void setHinhthuctra(String htTra)
	{
		
		this.hinhthucTra = htTra;
	}
	
	public String[] getMaspTra()
	{
		
		return this.maspTra;
	}
	
	public void setMaspTra(String[] maspTra)
	{
		
		this.maspTra = maspTra;
	}
	
	public String[] getTenspTra()
	{
		
		return this.tenspTra;
	}
	
	public void setTenspTra(String[] tenspTra)
	{
		
		this.tenspTra = tenspTra;
	}
	
	public String[] getSoluongspTra()
	{
		
		return this.soluongspTra;
	}
	
	public void setSoluongspTra(String[] soluongspTra)
	{
		
		this.soluongspTra = soluongspTra;
	}
	
	public String[] getIdspTra()
	{
		
		return this.isspTra;
	}
	
	public void setIdspTra(String[] idspTra)
	{
		
		this.isspTra = idspTra;
	}
	
	public String getDoanhsotheoThung()
	{
		
		return this.doanhsotheoThung;
	}
	
	public void setDoanhsotheoThung(String isThung)
	{
		
		this.doanhsotheoThung = isThung;
	}
	
	String doituong,quy,apdung;

	public String getQuy()
  {
  	return quy;
  }

	public void setQuy(String quy)
  {
  	this.quy = quy;
  }

	public String getApdung()
  {
  	return apdung;
  }

	public void setApdung(String apdung)
  {
  	this.apdung = apdung;
  }

	public String getDoituong()
  {
  	return doituong;
  }

	public void setDoituong(String doituong)
  {
  	this.doituong = doituong;
  }
	String[] donvi_tinh_ds,donvi_tinh_thuong,thuong;

	public String[] getThuong()
  {
  	return thuong;
  }

	public void setThuong(String[] thuong)
  {
  	this.thuong = thuong;
  }

	public String[] getDonvi_tinh_ds()
  {
  	return donvi_tinh_ds;
  }

	public void setDonvi_tinh_ds(String[] donvi_tinh_ds)
  {
  	this.donvi_tinh_ds = donvi_tinh_ds;
  }

	public String[] getDonvi_tinh_thuong()
  {
  	return donvi_tinh_thuong;
  }

	public void setDonvi_tinh_thuong(String[] donvi_tinh_thuong)
  {
  	this.donvi_tinh_thuong = donvi_tinh_thuong;
  } 
	
	Hashtable<String, String> npp_chitieu= new Hashtable<String, String>();
	Hashtable<String, String> npp_donvi_chitieu= new Hashtable<String, String>();;
	public Hashtable<String, String> getNpp_donvi_chitieu()
  {
  	return npp_donvi_chitieu;
  }

	public void setNpp_donvi_chitieu(Hashtable<String, String> npp_donvi_chitieu)
  {
  	this.npp_donvi_chitieu = npp_donvi_chitieu;
  }

	public Hashtable<String, String> getNpp_chitieu()
  {
  	return npp_chitieu;
  }

	public void setNpp_chitieu(Hashtable<String, String> npp_chitieu)
  {
  	this.npp_chitieu = npp_chitieu;
  }

	String loaichitieu;

	public String getLoaichitieu()
  {
  	return loaichitieu;
  }

	public void setLoaichitieu(String loaichitieu)
  {
  	this.loaichitieu = loaichitieu;
  }
	
}
