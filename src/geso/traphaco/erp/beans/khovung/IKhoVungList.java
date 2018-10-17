package geso.traphaco.erp.beans.khovung;

import geso.traphaco.erp.beans.khovung.IKhoVung;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IKhoVungList extends Serializable 
{
	public String getCtyId(); 

	public void setCtyId(String ctyId); 

	public String getUserId();
	public void setUserId(String userId);
		
	public String getMa();	
	public void setMa(String ma);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IKhoVung> getKVList();	
	public void setKVList(List<IKhoVung> KVList);
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public void closeDB();
	public void setKVRs(ResultSet rs);
	public ResultSet getKVRs();
}

