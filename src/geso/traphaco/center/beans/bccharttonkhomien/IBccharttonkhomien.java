package geso.traphaco.center.beans.bccharttonkhomien;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBccharttonkhomien extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangThai();
	public void setTrangThai(String trangthai);
	public String[] getArrTenMien();
	public String[] getArrIDMien();
	public ResultSet getRs();
	public void initTonKho();
	public void initXuatNhapTon();
	public void DbClose();
}
