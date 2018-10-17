package geso.traphaco.erp.beans.nhamay;

import java.sql.ResultSet;

public interface IErpNhamay
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);

	public String getNhamayId();
	public void setNhamayId(String NhamayId);
	
	public ResultSet  getRsNhamay();
	public void setRsNhamay(ResultSet rs);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNhamay();
	public boolean updateNhamay();
	
	public String getDvthId();
	public void setDvthId(String dvthId);
	public ResultSet  getDvthRs();
	public void setDvthRs(ResultSet dvthRs);
	
	public void init();
	public void creaters();
	public void DbClose();
	
}
