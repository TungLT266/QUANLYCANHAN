package geso.traphaco.distributor.beans.duyetbandunggia.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.duyetbandunggia.IDuyetbandunggiaNppList;

public class DuyetbandunggiaNppList implements IDuyetbandunggiaNppList 
{
	String userId;
	
	String tuthang;
	String denthang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet rsTieuchi;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public DuyetbandunggiaNppList()
	{
		this.tuthang = "";
		this.denthang="";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
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


	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void init(String query)
	{
		this.getNppInfo();
		
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select a.pk_seq, a.thang, a.nam, diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from DUYETBANDUNGGIA a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq inner join NHANVIEN c on a.NGUOISUA = c.pk_seq " +
					"where a.npp_fk = '" + this.nppId + "'  " +
					"order by nam desc, thang desc";
		}
		
		System.out.println("1.Khoi tao chi tieu: " + sql);
		this.rsTieuchi = db.get(sql);
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public ResultSet getTieuchiSKUInRs()
	{
		return this.rsTieuchi;
	}

	public void setTieuchiSKUInRs(ResultSet tieuchiSKU) 
	{
		this.rsTieuchi = tieuchiSKU;
	}

	
	public String getTuthang() {
		return tuthang;
	}

	
	public void setTuthang(String tuthang) {
		this.tuthang=tuthang;
	}

	
	public String getDenthang() {
		return denthang;
	}

	
	public void setDenthang(String denthang) {
		this.denthang=denthang;
	}

	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
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
	
	
}
