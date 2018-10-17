package geso.traphaco.erp.beans.bangchitiettaikhoan;

import java.sql.ResultSet;

public interface IBcThongKeChiPhiQLBH 
{
	public void setuserId(String userId);

	public String getuserId();

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
	
	public ResultSet getTaikhoan();
	
	public void setTkId(String tkId);

	public String getTkId();
	
	public String getSohieu() ;
	
	public String getDaukyno();
	
	public String getDaukyco();
	
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
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getTaikhoanNhom();
	
	public void setTkNhomId(String tknhomId);

	public String getTkNhomId();
	
}
