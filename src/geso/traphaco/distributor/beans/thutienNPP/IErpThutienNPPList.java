package geso.traphaco.distributor.beans.thutienNPP;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpThutienNPPList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCtyId();
	public void setCtyId(String ctyId); 

	public String getMaphieu();
	public void setMaphieu(String maphieu);
	
	public String getSoHoaDon();
	public void setSoHoaDon(String sohoadon);
	
	public String getSophieunop();
	public void setSophieunop(String sophieunop);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNccId();
	public void setNccId(String nccid);
	public ResultSet getNccList();
	public void setNccList(ResultSet ncclist);
	
	public String getNvgnId();
	public void setNvgnId(String ngnId);
	public ResultSet getNvgnList();
	public void setNvgnList(ResultSet nvgnList);
	
	public String getTdvId();
	public void setTdvId(String tdvId);
	public ResultSet getTdvList();
	public void setTdvList(ResultSet tdvList);

	public String getHtttId();
	public void setHtttId(String htttid);
	public ResultSet getHtttList();
	public void setHtttList(ResultSet htttlist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public void setSotienConlai(String sotienConlai);
	public String getSotienConlai();
	
	public ResultSet getTThoadonList();
	public void setTThoadonList(ResultSet tthdlist);
	
	public void init(String search);
	public void DBclose();
}
