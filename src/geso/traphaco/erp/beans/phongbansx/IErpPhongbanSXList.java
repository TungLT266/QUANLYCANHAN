package geso.traphaco.erp.beans.phongbansx;

import java.sql.ResultSet;

public interface IErpPhongbanSXList {
	public String getUserId();

	public void setUserId(String userId);

	public String getCongtyId();

	public void setCongtyId(String congtyId);

	public String getMa();

	public void setMa(String ma);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMsg();

	public void setMsg(String msg);

	public ResultSet getPhongbanRs();

	public void setPhongbanRs(ResultSet phongbanRs);

	public void init(String query);

	public void DbClose();
}
