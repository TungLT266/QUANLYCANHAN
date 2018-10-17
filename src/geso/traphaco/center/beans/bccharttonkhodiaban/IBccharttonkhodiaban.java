package geso.traphaco.center.beans.bccharttonkhodiaban;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBccharttonkhodiaban extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangThai();
	public void setTrangThai(String trangthai);
	public String[] getArrTenDiaBan();
	public String[] getArrIDDiaBan();
	public ResultSet getRs();
	public void initTonKho();
	public void initXuatNhapTon();
	public void DbClose();
}
