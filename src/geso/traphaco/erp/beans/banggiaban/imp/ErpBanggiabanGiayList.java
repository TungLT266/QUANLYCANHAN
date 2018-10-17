package geso.traphaco.erp.beans.banggiaban.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.banggiaban.IErpBanggiabanGiayList;

public class ErpBanggiabanGiayList implements IErpBanggiabanGiayList 
{
	String userId;
	String congtyId;
	String ma;
	String loaikhachhang;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kenhIds;
	String kenhId;	
	ResultSet loaikhIds;
	String loaikhId;	
	ResultSet bangiaRs;
	
	dbutils db;
	
	public ErpBanggiabanGiayList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.loaikhachhang = "";
		this.diengiai = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.loaikhId = "";
		this.msg = "";
		
		this.db = new dbutils();
		
		this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from DonViKinhDoanh where trangthai = '1' ");
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
		this.loaikhIds  =  this.db.get("select diengiai as loaikh, pk_seq as loaikhId from erp_loaikhachhang where trangthai='1'");
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getLoaikhachhang() 
	{
		return this.loaikhachhang;
	}

	public void setLoaikhachhang(String loaikhachhang) 
	{
		this.loaikhachhang = loaikhachhang;
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

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = " select a.pk_seq, a.ten, a.trangthai, a.tinhtrang, d.ten as nguoitao, a.ngaytao as ngaytao, tungay, denngay, \n " +
				  " 	e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId,  \n " +
				  "		case when isnull((SELECT count(pk_seq) as num FROM ERP_SUAGIABAN WHERE BGBAN_FK = a.pk_seq AND TINHTRANG = '2'), 0) > 0 then 0 else 1 end as moitao " +
				  " from ERP_BANGGIABAN a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e   \n  " +
				  " where a.dvkd_fk = b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq   \n  " +
				  "		and a.congty_fk = '" + this.congtyId + "' AND SUDUNG = 0  \n " +
				  
				  " UNION ALL  \n " +
				  " SELECT BG.PK_SEQ, BG.TEN, BG.TRANGTHAI, BG.TINHTRANG, NV1.TEN AS NGUOITAO, BG.NGAYTAO, BG.TUNGAY, BG.DENNGAY,  \n " +
				  " NV2.TEN AS NGUOISUA, BG.NGAYSUA, '' AS DVKD, '' AS KENH, 0 AS KENHID, 1 AS MOITAO  \n " +
				  " FROM ERP_BANGGIABAN BG  \n " +
				  " INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = BG.NGUOITAO  \n " +
				  " INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = BG.NGUOISUA  \n " +
				  " WHERE BG.CONGTY_FK = '" + this.congtyId + "' AND SUDUNG = 1  \n " +
				  " order by a.pk_seq desc ";
		}
		
		System.out.println("__Bang gia : " + sql);
		this.bangiaRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.bangiaRs != null)
				this.bangiaRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getBanggiaRs() 
	{
		return this.bangiaRs;
	}

	public void setBanggiaRs(ResultSet banggiaRs) 
	{
		this.bangiaRs = banggiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public String getLoaikhId() 
	{
		return this.loaikhId;
	}

	public void setLoaikhId(String loaikhId) 
	{
		this.loaikhId = loaikhId;
	}
	
	public ResultSet getDvkd() 
	{
		return this.dvkdIds;
	}

	public void setDvkd(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenh() 
	{
		return this.kenhIds;
	}

	public void setKenh(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}
	
	public ResultSet getLoaikh() 
	{
		return this.loaikhIds;
	}

	public void setLoaikh(ResultSet loaikh) 
	{
		this.loaikhIds = loaikh;
	}
	

}
