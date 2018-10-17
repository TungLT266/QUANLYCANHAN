package geso.traphaco.erp.beans.tailieuphachethuoc;

import java.sql.ResultSet;

public interface IErpTaiLieuPhaCheThuocList {
	public void init();
	public void delete(String id);
	public void DBClose();
	public String getUserId();
	public void setUserId(String userId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getMa();
	public void setMa(String ma);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getTlpctRs();
	public void setTlpctRs(ResultSet TlpctRs);
	public String getNoidung();
	public void setNoidung(String noidung);
}
