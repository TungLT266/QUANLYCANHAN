package geso.traphaco.erp.beans.thietbikiemnghiem;

import java.sql.ResultSet;

public interface IThietBiKiemNghiemList {
	public void init();
	public void delete(String id);
	public void DBClose();

	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getMsg();
	public void setMsg(String msg);

	public String getMa();
	public void setMa(String ma);

	public String getTen();
	public void setTen(String ten);

	public String getGhichu();
	public void setGhichu(String ghichu);

	public ResultSet getThietbiKNRs();
	public void setThietbiKNRs(ResultSet thietbiKNRs);
}
