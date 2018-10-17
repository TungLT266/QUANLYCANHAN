package geso.traphaco.erp.beans.hosokiemnghiem;

import java.sql.ResultSet;

public interface IErpHoSoKiemNghiem {

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getMsg();
	public void setMsg(String msg);
	public String getNppId();
	public void setNppId(String nppId);
	
	public void init();
	public void createRs();
	public void DBclose();
	public boolean createHSKN();
	public boolean UpdateHSKN();
}
