package geso.traphaco.erp.beans.yeucaukythuat;

import java.sql.ResultSet;

public interface IErpYeuCauKyThuatList {
	public void init();
	public void delete(String id);
	public void DBClose();
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
	public ResultSet getYeucauKTRs();
	public void setYeucauKTRs(ResultSet YeucauKTRs);
}
