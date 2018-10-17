package geso.traphaco.erp.beans.tamung;

import java.sql.ResultSet;

public interface INhanvienList 
{
	public void setMa(String Ma);
	public String getMa();	
	public void setTen(String Ten);
	public String getTen();	
	public void setCtyId(String ctyId);
	public String getCtyId();
	public void setEmail(String email);
	public String getEmail();
	public void setTkId(String tkId);
	public String getTkId();
	public void setTungay(String Tungay);
	public String getTungay();
	public void setDenngay(String Denngay);
	public String getDenngay();
	public ResultSet getDSNV();
	public void init();
	public void setuserId(String userId);
	public String getuserId();
	public void setTrangthai(String Trangthai);
	public String getTrangthai();
	public boolean xoa(String Id);
	public void setmsg(String msg);
	public String getmsg();
	public void DbClose();
	
	public void setPbRs(ResultSet pbRs);	
	public ResultSet getPbRs();
	
	public void setPbId(String pbId);
	public String getPbId();
	
	public void setChixem(String chixem);
	public String getChixem();
}
