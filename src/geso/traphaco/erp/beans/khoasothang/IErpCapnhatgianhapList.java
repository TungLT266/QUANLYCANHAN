package geso.traphaco.erp.beans.khoasothang;

import java.sql.ResultSet;

public interface IErpCapnhatgianhapList
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMacapnhat();
	public void setMacapnhat(String macapnhat);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getCngnRs();
	public void setCngnRs(ResultSet cngnRs);
	
	public void init(String search);
	public void DBclose();
}
