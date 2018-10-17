package geso.traphaco.erp.beans.banggiancc;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBangGiaNccList extends  Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public String getVungId();
	public void setVungId(String vungid);
	
	public String getDiabanId();
	public void setDiabanId(String diabanid);

	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	public void setKenh(ResultSet kenh);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccrs);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungrs);
	
	public ResultSet getDiabanRs();
	public void setDiabanRs(ResultSet diabanrs);
	
	public ResultSet getBanggiaRs();
	public void setBanggiaRs(ResultSet banggiaRs);
	
	public void init(String query);
	public void DbClose();
	public void NewDbUtil();
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	public String Duyet(String bgId);
	public String BoDuyet(String bgId);
	
	
}
