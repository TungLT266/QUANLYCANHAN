package geso.traphaco.erp.beans.banggiaban;

import java.sql.ResultSet;
import java.util.List;

public interface IErpBanggiabanGiay
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getTenbanggia();
	public void setTenbanggia(String tenbanggia);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public String getLkhId();
	public void setLkhId(String lkhId);
	public ResultSet getLkhRs();
	public void setLkhRs(ResultSet lkhRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhachhangRs();
	public void setKhachhangRs(ResultSet khRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public List<IBanggia_sp> getSpList();
	public void setSpList(List<IBanggia_sp> spList);
	
	
	public String[] getHopdongIds();
	public void setHopdongIds(String[] hdIds);
	
	
	public String getKhApDungIds();
	public void setKhApDungIds(String khApdungId);
	public ResultSet getKhachhangApdungRs();
	public void setKhachhangApdungRs(ResultSet khApdungRs);
	
	public void setBlock(String block); 
	public String getBlock();
	
	public boolean createBanggia();
	public boolean updateBanggia();
	
	public void init();
	public void createRs();
	
	public String getSudung();
	public void setSudung(String sudung);
	
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay) ;
	
	public void DbClose();
	
	public ResultSet getDonViDoLuongRs();
	public void setDonViDoLuongRs(ResultSet DonViDoLuongRs);
	
	public ResultSet createDvdlRS();
	
	public ResultSet getLspRs();
	public void setLspRs(ResultSet rs);
	
	public String getLspstr();	
	public void setLspstr(String Lspstr);
	
	
	
}
