package geso.traphaco.erp.beans.erp_trungtamchiphi;

import java.sql.ResultSet;

public interface IErp_trungtamchiphi
{
	public String getId();
	
	public void setId(String Id);

	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getMa();
	
	public void setMa(String ma);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getCongansach();
	
	public void setCongansach(String congansach);

	public String getNhanphanbo();
	
	public void setNhanphanbo(String nhanphanbo);

	public String getPhanbo();
			
	public void setPhanbo(String phanbo);
	
	public ResultSet getTtcpList();

	public String[] getPbIds();
	
	public void setPbIds(String[] pbIds);

	public String getPbIdsList();
	
	public String[] getPt();
	
	public void setPt(String[] pt);
	
	public void init();
	
	public boolean Update();	
	
	public boolean New();
	
	public void setMsg(String msg);
	
	public void setTimkiem(String timkiem);
	
	public String getTimkiem();
	
	public String getMsg();
	
	public String getChon();
	
	public void setChon(String chon);
	
	public void initNew();

	public ResultSet getNhanphanbo(String ttcpId);
	
	public ResultSet getChophanbo(String ttcpId);
	
	public ResultSet getChophanbothem(String ttcpId);
	public void DBClose();
}
