package geso.traphaco.erp.beans.khoasothang.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.IErpCapnhatgianhapList;

public class ErpCapnhatgianhapList implements IErpCapnhatgianhapList
{
	String userId;
	String tungay;
	String denngay;
	String macapnhat;
	String diengiai;
	
	String trangthai;
	String msg;
	
	ResultSet cngnRs;
	dbutils db;
	
	public ErpCapnhatgianhapList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.macapnhat="";
		this.diengiai="";
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

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getNhList() 
	{
		return this.cngnRs;
	}

	public void setNhList(ResultSet nhlist) 
	{
		this.cngnRs = nhlist;
	}

	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = "select a.pk_seq, isnull(ghichu, 'na') as ghichu, a.trangthai, a.tungay, a.denngay, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from ERP_CAPNHATGIANHAP a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq " +
					"order by a.trangthai desc";
		}
		else
			query = search;
		
		System.out.println("1.init: " + query);
		this.cngnRs = db.get(query);
	}

	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public ResultSet getCngnRs() 
	{
		return this.cngnRs;
	}

	public void setCngnRs(ResultSet cngnRs) 
	{
		this.cngnRs = cngnRs;
	}

	
	public String getMacapnhat() {
		
		return this.macapnhat;
	}

	
	public void setMacapnhat(String macapnhat) {
		
		this.macapnhat= macapnhat;
	}

	
	public String getDiengiai() {
		
		return this.diengiai;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.diengiai= diengiai;
	}
	
}
