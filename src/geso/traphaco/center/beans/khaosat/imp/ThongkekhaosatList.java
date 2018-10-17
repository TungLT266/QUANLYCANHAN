package geso.traphaco.center.beans.khaosat.imp;

import geso.traphaco.center.beans.khaosat.IThongkekhaosatList;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongkekhaosatList implements IThongkekhaosatList
{
	String userId;
	String tennguoitraloi;
	String dienthoai;
	String msg;
	
	ResultSet KhaosatRs;
	String khaosatId;
	
	ResultSet kqKhaosatRs;
	
	dbutils db;
	
	public ThongkekhaosatList()
	{
		this.userId = "";

		this.tennguoitraloi = "";
		this.dienthoai = "";
		this.khaosatId = "";
		
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
			sql = "select a.pk_seq, a.tieude, a.diengiai, a.bophan, a.socauhoi, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
				  "from KHAOSAT a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq  " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Khao sat : " + sql);
		this.KhaosatRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.KhaosatRs != null)
				this.KhaosatRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKhaosatRs() 
	{
		return this.KhaosatRs;
	}

	public void setKhaosatRs(ResultSet KhaosatRs) 
	{
		this.KhaosatRs = KhaosatRs;
	}

	
	public String getKhaosatId() {
		
		return this.khaosatId;
	}

	
	public void setKhaosatId(String khaosatId) {
		
		this.khaosatId = khaosatId;
	}

	
	public String getTennguoitraloi() {
		
		return this.tennguoitraloi;
	}

	
	public void setTennguoitraloi(String tennguoitraloi) {
		
		this.tennguoitraloi = tennguoitraloi;
	}

	
	public String getSodienthoai() {
		
		return this.dienthoai;
	}

	
	public void setSodienthoai(String sodienthoai) {
		
		this.dienthoai = sodienthoai;
	}

	
	public ResultSet getKetquaKsRs() {
		
		return this.kqKhaosatRs;
	}

	
	public void setKetquaKsRs(ResultSet ketquaKsRs) {
		
		this.kqKhaosatRs = ketquaKsRs;
	}


	public void createRs() 
	{
		this.KhaosatRs = db.get("select pk_seq, tieude from KHAOSAT where trangthai = '1' ");
		
		if(this.khaosatId.trim().length() > 0)
		{
			String query = "select distinct isnull(a.tennguoithuchien, b.TEN) as nguoithuchien, a.ten, a.sodienthoai, case when a.gioitinh = 0 then N'Nữ' else N'Nam' end as gioitinh,  " +
								"a.diachi, a.tuoi, a.doituong, a.muctieu, a.thunhap  " +
						   "from KHAOSAT_CAUHOI_THUCHIEN a left join DAIDIENKINHDOANH b on a.nguoithuchien = b.PK_SEQ  " +
						   "where khaosat_cauhoi_fk in ( select pk_seq from KHAOSAT_CAUHOI where KHAOSAT_FK = '" + this.khaosatId + "' )";
			
			this.kqKhaosatRs = db.get(query);
			
		}
		
	}

	

}
