package geso.traphaco.erp.beans.quydautu;

import java.sql.ResultSet;

public interface IQuydautu {
	public void setuserId(String userId);

	public String getuserId();

	public void setCtyId(String ctyId);

	public String getCtyId();

	public String getCtyTen();

	public String getDiachi();

	public String getMasothue();
	
	public void setTungay(String tungay);

	public String getTungay();

	public String getDenngay();

	public void setDenngay(String denngay);

	public void setMsg(String msg);

	public String getMsg();

	public String getSodudau();

	public ResultSet getData();
	
	public ResultSet getDauky();
	
	public void init();
	
	public String getDate();
	
	public void setView(String view);

	public String getView();
	
	public void setCtyRs(ResultSet ctyRs);
	
	public ResultSet getCtyRs();
	
	public void setCtyIds(String[] ctyIds);

	public String[] getCtyIds();
	
	public String getErpCongtyId();
	
	public void setErpCongtyId(String rs);
	
	public void init_0();

	public void DBClose();
}


