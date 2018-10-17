package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.tieuchithuong.IDuyetbandunggiaList;
import geso.traphaco.center.db.sql.dbutils;

public class DuyetbandunggiaList implements IDuyetbandunggiaList 
{
	String userId;
	
	String tuthang;
	String denthang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet rsTieuchi;
	
	dbutils db;
	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
	public DuyetbandunggiaList()
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
		
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql =   "select a.pk_seq, a.thang, a.nam, ( select ten from NHAPHANPHOI where pk_seq = a.npp_fk ) as nppTEN, diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from DUYETBANDUNGGIA a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq inner join NHANVIEN c on a.NGUOISUA = c.pk_seq " +
					"where a.trangthai = '1' and a.npp_fk in "+ util.quyen_npp(this.userId)+
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
	
}
