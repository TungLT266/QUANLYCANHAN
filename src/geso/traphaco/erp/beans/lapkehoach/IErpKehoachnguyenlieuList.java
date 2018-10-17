package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpKehoachnguyenlieuList 
{
	public String getCtyId(); 

	public void setCtyId(String ctyId); 

	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	
	public ResultSet getKehoachnguyenlieuRs();
	public void setKehoachnguyenlieuRs(ResultSet khnlRs);
	
	public void init(String query);
	public void DbClose();
	
}
