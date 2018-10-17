package geso.traphaco.center.beans.danhmucquyen.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.danhmucquyen.IDanhmucquyenList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

public class Danhmucquyenlist implements IDanhmucquyenList
{
   String MaQuyen;
   String TenQuyen;
   String Tungay;
   String Denngay;
   String UserId;
	public Danhmucquyenlist()
	{
		MaQuyen = "";
		TenQuyen ="";
		Tungay ="";
		Denngay ="";
	}
	public void setMaQuyen(String MaQuyen) {
		this.MaQuyen = MaQuyen;
		
	}

	
	public String getMaQuyen() {
		return this.MaQuyen;
	}

	
	public void setTenQuyen(String TenQuyen) {
	 this.TenQuyen = TenQuyen;	
	}

	
	public String getTenQuyen() {
	
		return this.TenQuyen;
	}

	
	public void setUserId(String UserId) {
	 this.UserId = UserId;
	}
	public String getUserId() {
		
		return this.UserId;
	}

	
	public ResultSet getDSQuyen() {
		dbutils db = new dbutils();
		ResultSet rs = db.get("select * from danhmucquyen");
		return rs;
	}
	
	public void setTungay(String Tungay) {
		this.Tungay = Tungay;
		
	}
	public String getTungay() {
		return this.Tungay;
	}
	public void setDenngay(String Denngay) {
	  this.Denngay = Denngay;
	}
	public String getDenngay() {
		return this.Denngay;
	}
	
	public ResultSet BangQuyen() 
	{
		Utility util = new Utility();
		String st="";
		if(MaQuyen.length()>0)
			st = st + " and a.pk_seq ='"+ this.MaQuyen +"'";
		if(TenQuyen.length() >0)
			st= st + " and dbo.ftBoDau(a.ten) like upper(N'%"+ util.replaceAEIOU(this.TenQuyen) +"%')";
	   if(Tungay.length() >0)
		   st = st + " and a.ngaytao >= '"+ this.Tungay+"'";
	   if(Denngay.length()>0)
		   st = st + " and a.ngaytao <= '"+ this.Denngay+"'";
		dbutils db = new dbutils();
		String tg ="select a.pk_seq as maquyen,a.ten,b.ten as nguoitao,c.ten as nguoisua,a.ngaytao,a.ngaysua,a.hoatdong from danhmucquyen a,nhanvien b,nhanvien c where a.nguoitao = b.pk_seq and a.nguoitao = c.pk_seq " +st;
		System.out.println(tg);
		ResultSet rs = db.get(tg);
		return rs;
	}
	
	public void delete(String ma) 
	{
		dbutils db = new dbutils();
		String sql ="delete from nhomquyen where DMQ_fk ='"+ ma +"'";
		
		db.update(sql);
		System.out.println("Test Delete 1: "+ma+" Abc ");
		sql ="delete from danhmucquyen where pk_seq ='"+ ma +"'";
		db.update(sql);
		System.out.println("Test Delete 2: "+ma+" Bcd ");
	}
	

	
	

}
