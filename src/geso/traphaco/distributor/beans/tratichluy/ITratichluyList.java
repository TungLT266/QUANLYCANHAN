package geso.traphaco.distributor.beans.tratichluy;

import java.sql.ResultSet;

public interface ITratichluyList 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public ResultSet getKenhRs();
	public void setKenhRs(ResultSet kenhRs);
	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getTratichluyRs();
	public void setTratichluyRs(ResultSet nganhhangRs);
	
	public void init(String query);
	public void DbClose();
	
}
