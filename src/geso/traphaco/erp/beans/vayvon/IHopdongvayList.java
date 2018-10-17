package geso.traphaco.erp.beans.vayvon;

import java.sql.ResultSet;

public interface IHopdongvayList 
{
	public void setSoHD(String SoHD);

	public String getSoHD();
	
	public void setNgayBD(String ngayBD);

	public String getNgayBD();

	public void setThoihan(String thoihan);

	public String getThoihan();

	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public ResultSet getHDrs();

	public void setUserId(String userId);

	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void init(String st);
	
	public void Xoa(String Id);
	
	public void DbClose();
}
