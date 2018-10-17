package geso.traphaco.erp.beans.bangcandoiphatsinh;

import java.sql.ResultSet;

public interface IBangcandoiphatsinh 
{
	public void setuserId(String userId);

	public String getuserId();

	public String getErpCongtyId();
	public void setErpCongtyId(String rs);
	
	public void setCtyId(String ctyId);
	
	public String getCtyId();
	
	public void setMsg(String msg);

	public String getMsg();

	public String getDate();

	public void setMonth(String month);
	
	public String getMonth();

	public void setYear(String year);

	public String getYear();
	
	public String getCtyTen();
	
	public String getDiachi();

	public String getMasothue();
	
	public void init();
	
	public ResultSet getData();

	public void setView(String view);

	public String getView();
	
	public void DBClose();
	
	public void createRs();
	
	public void getQuery();
	
	public void setNppId(String nppId);
	
	public String getNppId();
	
	public void setNPPRs(ResultSet nppRs);

	public ResultSet getNPPRs();
}
