package geso.traphaco.erp.beans.loaimaukiemnghiem;

import java.sql.ResultSet;

public interface IErpLoaiMauKiemNghiem {
	public void init();
	public void initCapnhat();
	public boolean create();
	public boolean update();
	public void delete();
	public void DBClose();
	public String getUserId();
	public void setUserId(String userId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getLoaimauKNRs();
	public void setLoaimauKNRs(ResultSet loaimauKNRs);
}
