package geso.traphaco.erp.beans.banggiaban;

import java.sql.ResultSet;

public interface IErpBanggiabanGiayList 
{
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
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public String getLoaikhId();
	public void setLoaikhId(String loaikhId);

	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	public void setKenh(ResultSet kenh);
	
	public ResultSet getLoaikh();
	public void setLoaikh(ResultSet loaikh);
	
	public ResultSet getBanggiaRs();
	public void setBanggiaRs(ResultSet banggiaRs);
	
	public void init(String query);
	public void DbClose();
	
}
