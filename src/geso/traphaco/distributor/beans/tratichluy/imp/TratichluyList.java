package geso.traphaco.distributor.beans.tratichluy.imp;

import geso.traphaco.distributor.beans.tratichluy.ITratichluyList;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TratichluyList implements ITratichluyList 
{
	String userId;
	String nam;
	String thang;
	String trangthai; 
	String msg;
	
	ResultSet dvkdRs;
	String dvkdId;
	
	ResultSet kbhRs;
	String kbhId;
	
	ResultSet TratichluyRs;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public TratichluyList()
	{
		this.userId = "";

		this.nam = "";
		this.trangthai = "";
		this.thang = "";
		this.dvkdId = "";
		this.kbhId = "";
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
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
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
		
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public void init(String query) 
	{
		this.getNppInfo();
		
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "select a.duyetkm_fk, c.pk_seq, c.SCHEME + ', ' + c.DIENGIAI as SCHEME, a.nppId, b.NGAYDUYET,  " +
					"case when SUM(a.trangthai) = COUNT(a.trangthai) then N'Hoàn tất' else N'Chưa hoàn tất' end as trangthai  " +
				  "from DuyetTraKhuyenMai_KhachHang a inner join DuyetTraKhuyenMai b on a.duyetkm_fk = b.PK_SEQ " +
				  	"inner join TIEUCHITHUONGTL c on b.CTKM_FK = c.PK_SEQ  " +
				  "where a.nppId = '" + this.nppId + "' and a.donvi = '2' and a.thuong > 0 " +
				  "group by a.duyetkm_fk, c.pk_seq, c.SCHEME + ', ' + c.DIENGIAI, a.nppId, b.NGAYDUYET  " +
				  "order by b.NGAYDUYET desc";
		}
		
		System.out.println("__Kho TT : " + sql);
		this.TratichluyRs = db.get(sql);
		
		this.dvkdRs = db.get("select PK_SEQ, SCHEME  from TIEUCHITHUONGTL where TRANGTHAI = '1' order by PK_SEQ desc ");
		
	}

	public void DbClose() 
	{
		try 
		{
			if(this.TratichluyRs != null)
				this.TratichluyRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getTratichluyRs() 
	{
		return this.TratichluyRs;
	}

	public void setTratichluyRs(ResultSet TratichluyRs) 
	{
		this.TratichluyRs = TratichluyRs;
	}

	
	public String getThang() {
		
		return this.thang;
	}

	
	public void setThang(String thang) {
		
		this.thang = thang;
	}

	
	public String getNam() {
		
		return this.nam;
	}

	
	public void setNam(String nam) {
		
		this.nam = nam;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getKenhRs() {
		
		return this.kbhRs;
	}

	
	public void setKenhRs(ResultSet kenhRs) {
		
		this.kbhRs = kenhRs;
	}

	
	public String getKenhId() {
		
		return this.kbhId;
	}

	
	public void setKenhId(String kenhId) {
		
		this.kbhId = kenhId;
	}

	

}
