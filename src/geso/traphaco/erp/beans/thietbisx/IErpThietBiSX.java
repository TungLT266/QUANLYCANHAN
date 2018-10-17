package geso.traphaco.erp.beans.thietbisx;

import java.sql.ResultSet;

public interface IErpThietBiSX {
	public String getUserId();
	public void setUserId(String userId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getRsThietbi();
	public void setRsThietbi(ResultSet rsThietbi);
	public void init(String search);
	public void DbClose();
	public String getTscdFk();
	public void setTscdFk(String tscdFk);
	public void createRs();
	public ResultSet getRsTscd();
	public void setRsTscd(ResultSet rsTscd);
	public void delete(String id);
}
