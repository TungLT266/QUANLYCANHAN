package geso.traphaco.erp.beans.ThoiHanThanhToan;

import java.sql.ResultSet;

public interface IerpKeHoachThanhToan {
	public String getUserId();
	public void setUserId(String userId);
	public String getCongTyId();
	public void setCongTyId(String congTyId);
	public String getSoPO();
	public void setSoPO(String soPO);
	public String getNCC();
	public void setNCC(String ncc);
	public String getTuNgay();
	public void setTuNgay(String tuNgay);
	public String getDenNgay();
	public void setDenNgay(String denNgay);
	public String getMsg();
	public ResultSet getKeHoachRs();
	public void setKeHoachRs(ResultSet keHoachRs);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	public void createNccRs();
	public void setMsg(String msg);
	public void init();
	public void dbClose();
}
