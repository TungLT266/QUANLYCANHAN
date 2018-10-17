package geso.traphaco.erp.beans.thuenhapkhau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhauList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpThuenhapkhauList_bk implements IErpThuenhapkhauList 
{
	String userId;
	String congtyId;
	String Id;
	String poId;
	String ncc;
	String nccId;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet tnkRs;
	
	dbutils db;
	
	public ErpThuenhapkhauList_bk()
	{
		this.userId = "";
		this.poId = "";
		this.ncc = "";
		this.nccId = "";

		this.Id = "";
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

	public String getChungtu() 
	{
		return this.Id;
	}

	public void setChungtu(String Id) 
	{
		this.Id = Id;
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

	public void setNcc(String ncc) 
	{
		
		this.ncc = ncc;
	}

	
	public String getNcc()
	{
		
		return this.ncc;
	}
	
	public void setNccId(String nccId) 
	{
		
		this.nccId = nccId;
	}

	
	public String getNccId()
	{
		
		return this.nccId;
	}

	public void setPoId(String poId) 
	{
		
		this.poId = poId;
	}

	
	public String getPoId()
	{
		
		return this.poId;
	}
	
	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = 	"SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI, TNK.NGAY, NH.PK_SEQ AS PHIEUNHAP, TNK.TRANGTHAI, " +
					"NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA " +
					"FROM ERP_THUENHAPKHAU TNK " +
					"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK " +
					"INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = TNK.PHIEUNHAPKHO_FK " +
					"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO " +
					"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA " +
					"ORDER BY TNK.PK_SEQ DESC ";
		}
		
		System.out.println("__Thue nhap khau : " + sql);
		this.tnkRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.tnkRs != null)
				this.tnkRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getThuenhapkhauRs() 
	{
		return this.tnkRs;
	}

	public void setThuenhapkhauRs(ResultSet tnkRs) 
	{
		this.tnkRs = tnkRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public void DBClose(){
		try{
			if(this.tnkRs != null) tnkRs.close();
			if(this.db != null) this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	
		

}
