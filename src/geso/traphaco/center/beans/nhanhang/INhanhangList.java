package geso.traphaco.center.beans.nhanhang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhanhangList extends Serializable {
	
	public String getNhanhang();
	
	public void setNhanhang(String nhanhang);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);
	
	public String getTungay();
	
	public void setTungay(String tungay);
	
	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public void setNhlist(ResultSet nhlist);
	
	public ResultSet getNhlist();  
	
	public void setDvkdList(ResultSet dvkdlist);
	
	public ResultSet getDvkdList();  
	public void setMsg(String Msg);
	public String getMsg();

	

	public void DBClose();
}