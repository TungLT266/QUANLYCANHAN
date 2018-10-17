package geso.traphaco.erp.beans.tinhgiathanh;

import java.sql.ResultSet;

public interface IErpTieuhaonvlList 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	
	public ResultSet getTieuhoanvlRs();
	public void setTieuhoanvlRs(ResultSet khlRs);
	
	public void init(String query);
	public void DbClose();
	
}
