package geso.traphaco.center.beans.baocao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.beans.baocao.IBaocao;

public class Baocao implements IBaocao 
{
	String userId;
	String userTen;
	
	ResultSet loaiSpRs;
	ResultSet spRs;
	ResultSet khoRs;
	ResultSet chungloaiRs;
	
	String loaisanphamIds;
	String spIds;
	String khoIds;
	String chungloaiIds;
	String khoTen;
	
	String tungay;
	String denngay;
	String msg;
	String flag;
	
	dbutils db;
	
	public Baocao()
	{
		this.userId = "";
		this.userTen = "";
		this.loaisanphamIds = "";
		this.chungloaiIds = "";
		this.spIds = "";
		this.khoIds = "";
		this.khoTen = "";
		this.tungay = "";
		this.denngay = "";
		this.msg = "";
		this.flag = "1";
		
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

	public String getUserTen() 
	{
		return this.userTen;
	}

	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}
	
	public ResultSet getLoaiSanPhamRs() 
	{
		return this.loaiSpRs;
	}
	
	public void setLoaiSanPhamRs(ResultSet loaisp) 
	{
		this.loaiSpRs = loaisp;
	}

	public String getLoaiSanPhamIds() 
	{
		return this.loaisanphamIds;
	}

	public void setLoaiSanPhamIds(String loaispIds)
	{
		this.loaisanphamIds = loaispIds; 
	}

	public String getTuNgay()
	{
		return this.tungay;
	}

	public void setTuNgay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenNgay() 
	{
		return this.denngay;
	}

	public void setDenNgay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public ResultSet getSanPhamRs() 
	{
		return this.spRs;
	}

	public void setSanPhamRs(ResultSet sanpham) 
	{
		this.spRs = sanpham;	
	}

	public String getSanPhamIds() 
	{	
		return this.spIds;
	}

	public void setSanPhamIds(String spIds) 
	{
		this.spIds = spIds;
	}

	public void createRs() 
	{
		/*this.loaiSpRs = db.get("select pk_seq, ma + ', ' + ten as ten from erp_loaisanpham where trangthai = '1'");
		this.chungloaiRs = db.get("select pk_seq, ten from chungloai where trangthai = '1'");*/
		
		String query = "select pk_seq, ma, ten from sanpham where trangthai = '1' ";
		if(this.loaisanphamIds.length() > 0)
		{
			query += "and loaisanpham_fk = '" + this.loaisanphamIds + "'";
		}
		
		if(this.chungloaiIds.length() > 0)
		{
			query += "and chungloai_fk in (" + this.chungloaiIds + ") ";
		}
		
		this.spRs = db.get(query);
		
		query = "select pk_seq, ten + ', ' + isnull(diachi, '') as khoTen from erp_khott where trangthai = '1' ";
		this.khoRs = db.get(query);
	}

	public void close()
	{
		this.db.shutDown();
		
		try 
		{
			if(this.loaiSpRs != null)
				this.loaiSpRs.close();
			
			if(this.chungloaiRs != null)
				this.chungloaiRs.close();
				
			if(this.spRs != null)
				this.spRs.close();
		} 
		catch (SQLException e) {}
		
		
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	public String getKhoIds() 
	{
		return this.khoIds;
	}

	public void setKhoIds(String khoId) 
	{
		this.khoIds = khoId;
	}

	public String getKhoTen() 
	{
		return this.khoTen;
	}

	public void setKhoTen(String khoTen) 
	{
		this.khoTen = khoTen;
	}

	
	public ResultSet getChungloaiRs() 
	{
		return this.chungloaiRs;
	}

	public void setChungloaiRs(ResultSet clRs)
	{
		this.chungloaiRs = clRs;
	}

	
	public String getChungloaiIds() {
		
		return this.chungloaiIds;
	}

	
	public void setChungloaiIds(String loaispIds) {
		
		this.chungloaiIds = loaispIds;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String msg) {
		
		this.msg = msg;
	}

	public String getFlag() {

		return this.flag;
	}

	public void setFlag(String flag) {
	
		this.flag = flag;
	}

	

}
