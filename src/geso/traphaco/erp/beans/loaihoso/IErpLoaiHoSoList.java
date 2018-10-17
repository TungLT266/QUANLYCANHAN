package geso.traphaco.erp.beans.loaihoso;

import java.sql.ResultSet;

public interface IErpLoaiHoSoList {
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
	public ResultSet getLoaihosoRs();
	public void setLoaihosoRs(ResultSet LoaihosoRs);
	public String getMaLoaihoso();
	public void setMaLoaihoso(String maLoaihoso);
	public String getMaBieumau();
	public void setMaBieumau(String maBieumau);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getLoaimauin();
	public void setLoaimauin(String loaimauin);
}
