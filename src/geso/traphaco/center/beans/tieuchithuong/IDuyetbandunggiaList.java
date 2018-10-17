package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface IDuyetbandunggiaList 
{
	public String getUserId();
	public void setUserId(String userId);
	

	public String getNam();
	public void setNam(String nam);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTuthang();
	public void setTuthang(String tuthang);
	public String getDenthang();
	public void setDenthang(String denthang);
	
	public ResultSet getTieuchiSKUInRs();
	public void setTieuchiSKUInRs(ResultSet tieuchiSKU);
	
	public void init(String query);
	
	
}
