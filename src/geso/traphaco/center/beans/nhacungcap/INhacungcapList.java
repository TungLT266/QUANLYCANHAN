package geso.traphaco.center.beans.nhacungcap;

import java.io.Serializable;
import geso.traphaco.center.beans.nhacungcap.INhacungcap;
import java.util.List;
import java.sql.ResultSet;

public interface INhacungcapList extends Serializable {
	public void init();	
	
	public String getQuery();
	
	public void setQuery(String query);
	
	public ResultSet getNccList();
	
	public void setNccList(ResultSet ncclist);
	
	public String getNhacungcap();
	
	public void setNhacungcap(String nhacungcap);
	
	public String getTenviettat();
	
	public void setTenviettat(String tenviettat);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	public void setMsg(String Msg);
	public String getMsg();
   
	public void DBClose();
}