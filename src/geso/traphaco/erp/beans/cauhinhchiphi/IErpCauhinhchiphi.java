package geso.traphaco.erp.beans.cauhinhchiphi;

import java.sql.ResultSet;

public interface IErpCauhinhchiphi
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

	public ResultSet getKhoanmucCPRs();
	public void setKhoanmucCPList(ResultSet khoanmucRs);
	public void setKhoanmucCpIds(String khoanmucIs);
	public String getKhoanmucCPIds();
	
	public ResultSet getGroupCPRs();
	public void setGroupCPRs(ResultSet groupRs);
	public void setGroupCpIds(String khoanmucIs);
	public String getGroupCPIds();
	
	
	public void init();
	public void createRs();
	
	public boolean Update();	
	public boolean New();
	
	public void setMsg(String msg);
	public String getMsg();
	
	public void setTimkiem(String timkiem);
	public String getTimkiem();
	
	public ResultSet getChophanbothem(String Id);
	
	public void DBClose();
	
	
}
