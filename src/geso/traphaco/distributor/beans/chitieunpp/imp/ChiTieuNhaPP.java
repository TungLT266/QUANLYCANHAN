package geso.traphaco.distributor.beans.chitieunpp.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import geso.traphaco.distributor.beans.chitieunpp.IChiTieuNhaPP;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChiTieuDDKD;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChiTieuNhaPP;
import geso.traphaco.distributor.db.sql.dbutils;

public class ChiTieuNhaPP implements IChiTieuNhaPP, Serializable
{
	/**
   * 
   */
  private static final long serialVersionUID = 4521758529683013226L;
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
	
	String[] tumuc;
	String[] denmuc;

	String msg;
	
	dbutils db;
	private String trangthai;
	
	public ChiTieuNhaPP()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.view="";
		this.db = new dbutils();
	}
	
	public ChiTieuNhaPP(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.view="";
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
				this.msg = "Vui lòng chọn kết thúc";
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
							sql += "select '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc, N'" + this.donvi_tinh_ds[i] + "' as donvi_tinh_ds ," + " '" + this.thuong[i] .replaceAll(",", "") + "' as thuong,N'" + this.donvi_tinh_thuong[i] + "' as donvi_tinh_thuong union ";
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
				sql +="select '"+key+"' as nppId,'"+value+"' as ChiTieu,'"+donvi+"' as DonVi union "; 
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
			return false;
		}
		
		return true;
	}
	
	public boolean edit()
	{
		try
		{
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kết thúc";
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
					" update [ChiTieu] set [DoiTuong]='CN/DT',[Thang]="+(this.thang.trim().length()<=0?"NULL":this.thang)+",[Nam]='"+(this.nam.trim().length()<=0?"NULL":this.nam)+"',[Quy]="+(this.quy.trim().length()<=0?"NULL":this.quy)+",[Ma]=N'"+this.scheme+"',[Ten]=N'"+this.diengiai+"',[NguoiSua]='"+this.userId+"',[NgaySua]='"+this.getDateTime()+"',[ApDung]='"+this.apdung+"' " +
					" where pk_seq='"+this.id+"'  " ;
			System.out.println("1.update: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from ChiTieu_TieuChi where chitieu_fk='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from ChiTieu_DoiTuong where chitieu_fk='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from ChiTieu_SanPham where chitieu_fk='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (this.tumuc != null)
			{
				String sql = "";
				for (int i = 0; i < this.tumuc.length; i++)
				{
					if (this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuong[i].trim().length()>0 && this.donvi_tinh_ds[i].trim().length()>0 && this.donvi_tinh_thuong[i].trim().length()>0 )
					{
							sql += "select '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc, N'" + this.donvi_tinh_ds[i] + "' as donvi_tinh_ds ," + " '" + this.thuong[i] .replaceAll(",", "") + "' as thuong,N'" + this.donvi_tinh_thuong[i] + "' as donvi_tinh_thuong union ";
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
				sql +="select '"+key+"' as nppId,'"+value+"' as ChiTieu,'"+donvi+"' as DonVi union "; 
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
			return false;
		}
		
		return true;
	}
	
	public void init()
	{
		String query = 
				"select ApDung,ma as ma, thang, nam,Quy, ten,loaichitieu,trangthai  " + 
				"		, STUFF ( ( select DISTINCT TOP 100 PERCENT ' , ' +cast( tbh.sanpham_fk as nvarchar(18) ) from chitieu_sanpham tbh where chitieu_fk='"+this.id+"'  "+
				"			ORDER BY ' , ' +cast( tbh.sanpham_fk as nvarchar(18) ) FOR XML PATH('') ), 1, 2, +' ' ) as spId,  "+
				" STUFF  "+
				"	((select DISTINCT TOP 100 PERCENT ' , ' +cast( tbh.npp_fk as nvarchar(18) ) from chitieu_doituong tbh where chitieu_fk='"+this.id+"'  "+
				"		ORDER BY ' , ' +cast( tbh.npp_fk as nvarchar(18) ) FOR XML PATH('') ), 1, 2, +' ') as nppId  "+
				"from ChiTieu where pk_seq = '" + this.id + "'";
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				while (rs.next())
				{
					this.scheme = rs.getString("ma");
					this.thang = rs.getString("thang")==null?"":rs.getString("thang");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("ten");
					this.quy=rs.getString("quy")==null?"":rs.getString("quy");
					this.apdung=rs.getString("apdung")==null?"":rs.getString("apdung");
					this.trangthai=rs.getString("trangthai")==null?"":rs.getString("trangthai");
					this.loaichitieu=rs.getString("loaichitieu")==null?"":rs.getString("loaichitieu");
					this.spIds=rs.getString("spId");
					this.nppIds=rs.getString("nppId");
				}
				rs.close();
				
				query = "select npp_fk,chitieu_fk,DonViTinh_ChiTieu,chitieu " +
						" from ChiTieu_DoiTuong where chitieu_fk = '" + this.id + "'";
				rs = db.get(query);
				if(rs != null)
				{
					this.npp_chitieu = new Hashtable<String, String>();
					this.npp_donvi_chitieu = new Hashtable<String, String>();
					
					while(rs.next())
					{
						this.npp_chitieu.put(rs.getString("npp_fk"), format.format(rs.getDouble("chitieu")));
						this.npp_donvi_chitieu.put(rs.getString("npp_fk"),     rs.getString("DonViTinh_ChiTieu"));
					}
					rs.close();
				}	
				
				query = "select TuMuc,DenMuc,chitieu_fk,DonViTinh_ChiTieu,DonViTinh_Thuong,Thuong " +
						" from ChiTieu_TieuChi where chitieu_fk = '" + this.id + "'";
				
				System.out.println("__TieuChi__"+query);
				
				String _tumuc ="";
				String _denmuc="";
				String _thuong="";
				String donvi_thuong="";
				String donvi_chitieu="";
				rs=db.get(query);
				while(rs.next())
				{
					_tumuc+=rs.getString("TuMuc").trim().length() > 0?rs.getString("TuMuc") + "__": " __";
					_denmuc+=rs.getString("DenMuc").trim().length() > 0?rs.getString("DenMuc") + "__": " __";
					_thuong+=rs.getString("Thuong").trim().length() > 0?format.format(rs.getDouble("Thuong")) + "__": " __";
					donvi_thuong+=rs.getString("DonViTinh_Thuong").trim().length() > 0?rs.getString("DonViTinh_Thuong") + "__": " __";
					donvi_chitieu+=rs.getString("DonViTinh_ChiTieu").trim().length() > 0?rs.getString("DonViTinh_ChiTieu") + "__": " __";
				}
				rs.close();
				if(_tumuc.trim().length() > 0)
				{
					_tumuc = _tumuc.substring(0, _tumuc.length() - 2); this.tumuc = _tumuc.split("__");
					_denmuc = _denmuc.substring(0, _denmuc.length() - 2); this.denmuc = _denmuc.split("__");
					_thuong = _thuong.substring(0, _thuong.length() - 2); this.thuong = _thuong.split("__");
					donvi_thuong = donvi_thuong.substring(0, donvi_thuong.length() - 2); this.donvi_tinh_thuong = donvi_thuong.split("__");
					donvi_chitieu = donvi_chitieu.substring(0, donvi_chitieu.length() - 2); this.donvi_tinh_ds = donvi_chitieu.split("__");
				}
				
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		this.createRs();
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
		
		query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and isKhachHang=0 ";
		if(view.length()>0)
		{
			query+="and TrucThuoc_fk=1";
		}
		
		if (this.kvIds.trim().length() > 0)
			query += " and khuvuc_fk in ( " + this.kvIds + " ) ";
		if (this.vungIds.trim().length() > 0)
			query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
		
		if (this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from ChiTieu_DoiTuong where ChiTieu_fk = '" + this.id + "' ) ";
		}
		
		//
		if (this.nppIds.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds + " ) ";
		}
		
		query += " order by PK_SEQ desc ";
		this.nppRs = db.getScrol(query);
		
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
	
	String view;

	public String getView()
  {
  	return view;
  }

	public void setView(String view)
  {
  	this.view = view;
  }
	
	
	
}
