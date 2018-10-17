package geso.traphaco.erp.beans.bosanpham;

import java.sql.ResultSet;

public interface IBosanphamList 
{
	public void setSpId(String spId);

	public String getSpId();
	
	public void setSpTen(String spTen);

	public String getSpTen();
	
	public void setNgay(String ngay);

	public String getNgay();

	public void setSoluong(String soluong);

	public String getSoluong();

	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public ResultSet getBspRS();

	public void setUserId(String userId);

	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void init(String st);
	
	public void Xoa(String Id);
	
	public ResultSet getSP(String Id);
	
	public void DbClose();
}
