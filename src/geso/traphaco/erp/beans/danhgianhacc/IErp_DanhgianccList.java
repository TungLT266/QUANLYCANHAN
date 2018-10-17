package geso.traphaco.erp.beans.danhgianhacc;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_DanhgianccList extends Serializable, IPhan_Trang{

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getDvthId();
	public void setDvthId(String dvthId);
	public ResultSet getDvthRs();
	public void setDvthRs(ResultSet dvthRs);
	
	public String getNccId();
	public void setNccId(String NccId);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public String getSpId();
	public void setSpId(String spId);
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getDgNccList();
	public void setDgNccList(ResultSet dgnccRs);
	
	public void init(String search);
	public void DBclose();
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void setSoItems(int soItems);
	public int getSoItems();
}
