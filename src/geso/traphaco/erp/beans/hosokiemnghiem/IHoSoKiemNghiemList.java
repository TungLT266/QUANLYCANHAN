package geso.traphaco.erp.beans.hosokiemnghiem;

import java.sql.ResultSet;

public interface IHoSoKiemNghiemList {

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
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getHosoRs();
	public void setHosoRs(ResultSet hosoRs);
	
	public void Delete(String id, String userId);
	public void init();
}
