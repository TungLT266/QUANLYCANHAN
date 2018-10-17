package geso.traphaco.erp.beans.duyetbom;

import java.sql.ResultSet;

public interface IErpDuyetbom
{
	public void setCtyId(String ctyId);
	public String getCtyId();
	
	public void setMsg(String msg);
	public String getMsg();

	public void setUserId(String userId);
	public String getUserId();

	public void setChungloaiId(String value);
	public String getChungloaiId();
	
	public void setTenSanPham(String tensanpham);
	public String getTenSanPham();
	
	public void setDienGiai(String diengiai);
	public String getDienGiai();
	
	public void setBomId(String bomid);
	public String getBomId();
	public boolean Update();
	public boolean Init();
	public void Close();

	public ResultSet getSanphamRs();
	public ResultSet getChungloaiRs();
	public void setMaSP(String spma);
	public boolean Save();
	public boolean Boduyet();
}
