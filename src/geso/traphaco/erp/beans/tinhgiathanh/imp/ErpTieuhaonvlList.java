package geso.traphaco.erp.beans.tinhgiathanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.tinhgiathanh.*;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpTieuhaonvlList implements IErpTieuhaonvlList 
{
	String userId;
	String tungay;
	String denngay;
	String trangthai; 

	String diengiai;
	String msg;
	
	ResultSet thnvlRs;
	
	dbutils db;
	
	public ErpTieuhaonvlList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
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

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "select a.pk_seq, d.ma as spMa, d.ten as spTen, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
				  "from ERP_TIEUHAONVL a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq inner join sanpham d on a.sanpham_fk = d.pk_seq " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Tieu Hao NVL : " + sql);
		this.thnvlRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.thnvlRs != null)
				this.thnvlRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getTieuhoanvlRs() 
	{
		return this.thnvlRs;
	}

	public void setTieuhoanvlRs(ResultSet thnvlRs) 
	{
		this.thnvlRs = thnvlRs;
	}

	

}
