package geso.traphaco.center.beans.thutien;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpThutienList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCtyId();
	public void setCtyId(String ctyId); 

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNccId();
	public void setNccId(String nccid);
	public ResultSet getNccList();
	public void setNccList(ResultSet ncclist);

	public String getHtttId();
	public void setHtttId(String htttid);
	public ResultSet getHtttList();
	public void setHtttList(ResultSet htttlist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getTThoadonList();
	public void setTThoadonList(ResultSet tthdlist);
	
	public void init(String search);
	public void DBclose();
		
	public String getBangKe();
	public void setBangke(String bangke);
	
	public String getMaPhieu();
	public void setMaPhieu(String MaPhieu);
	
	
	public String getMachungtu();

	public void setMachungtu(String machungtu);
	
}
