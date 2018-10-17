package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface IDuyetsuunghoList 
{
	public String getUserId();
	public void setUserId(String userId);
	
	
	public String getTuquy();
	public void setTuquy(String tuquy);
	
	
	public String getDenquy();
	public void setDenquy(String denquy);
	
	public String getNam();
	public void setNam(String nam);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public ResultSet getTieuchiSKUInRs();
	public void setTieuchiSKUInRs(ResultSet tieuchiSKU);
	
	public void init(String query);
	public int getSl();

	public void setSl(int sl);
	
	
}
