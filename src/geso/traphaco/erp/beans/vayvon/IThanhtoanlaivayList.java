package geso.traphaco.erp.beans.vayvon;

import geso.traphaco.center.util.IThongTinHienThi;

import java.sql.ResultSet;
import java.util.List;

public interface IThanhtoanlaivayList {
	public void setSoHD(String SoHD);

	public String getSoHD();
	
	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public ResultSet getTtnvRS();

	public void setUserId(String userId);

	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void init(String st);
	
	public void Xoa(String Id);
	
	public void DbClose();
	
	public String getTkvay();

	public void setTkvay(String tkvay);
	
	public List<IThongTinHienThi> getHienthiList();
	
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
}
