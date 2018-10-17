package geso.traphaco.erp.beans.giaidoansx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.giaidoansx.IErpGiaidoanSXList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpGiaidoanSXList implements IErpGiaidoanSXList 
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String nppId;
	ResultSet giaidoanRs;
	
	dbutils db;
	
	public ErpGiaidoanSXList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		
		this.msg = "";
		
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

	public void init(String search) 
	{
		String query = "";
		
		if(search.length() > 0)
			query = search;
		else
		{	
			query = "select a.pk_seq, a.ma, a.ten, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
					"from ERP_GIAIDOANSX a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
					"	inner join nhanvien c on a.nguoisua = c.pk_seq  " +
					"where 1 = 1";
			if(this.congtyId.length()>0){
				query += " and a.congty_fk = "+this.congtyId;
			}
			query += " order by a.pk_seq desc";
		}
		
		System.out.println("__Nha may : " + query);
		this.giaidoanRs = db.get(query);
	}
	
	public void delete(String idxoa){
		String query = "delete Erp_Giaidoansx where pk_seq = '" + idxoa + "'"
				+ " delete ERP_GIAIDOANSX_LOAISANPHAM where GIAIDOANSX_FK = '" + idxoa + "'"
				+ " delete ERP_GIAIDOANSX_THIETBI where GIAIDOAN_FK = '" + idxoa + "'"
				+ " delete ERP_GIAIDOANSX_THIETBI_CHITIET where GIAIDOAN_FK = '" + idxoa + "'";
		
		if(!db.update(query)){
    		this.msg = "Không thể xóa Erp_Giaidoansx";
    	}
	}

	public void DbClose() 
	{
		try 
		{
			if(this.giaidoanRs != null)
				this.giaidoanRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getGiaidoanRs() 
	{
		return this.giaidoanRs;
	}

	public void setGiaidoanRs(ResultSet giaidoanRs) 
	{
		this.giaidoanRs = giaidoanRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	

}
