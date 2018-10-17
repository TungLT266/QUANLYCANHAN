package geso.traphaco.erp.beans.phanbomuahang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpPhanbomuahangList extends Serializable, IPhan_Trang{

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSodenghi();
	public void setSodenghi(String sodenghi);
	public String getPBId();
	public void setPBId(String pbId);
	public ResultSet getDnmhRs();
	public void setDnmhRs(ResultSet dnmhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getTimNccList();
	public void setTimNccList(ResultSet timnccRs);
	
	public void init(String search);
	public void DBclose();
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void setSoItems(int soItems);
	public int getSoItems();
}
