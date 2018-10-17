package geso.traphaco.erp.beans.khauhaotaisancodinh;

import java.sql.ResultSet;

public interface IKhauhaoCCDCList {
	public String getUserId(); 

	public void setUserId(String userId); 

	public String getCongty();

	public void setCongty(String cty);
	
	public String getCtyId();

	public void setCtyId(String ctyId); 

	public ResultSet getThangRs();

	public void setThangRs(ResultSet thangRs);

	public String getThang();

	public void setThang(String thang);

	public ResultSet getNamRs();

	public void setNamRs(ResultSet namRs);

	public String getNam();

	public void setNam(String nam);
	
	public ResultSet getCCDCRs();

	public void setCCDCRs(ResultSet CCDCRs);
	
	public String getTenCCDC(); 

	public void setTenCCDC(String tenCCDC); 
	
	public String getMsg(); 

	public void setMsg(String msg); 
	
	public String getAction(); 

	public void setAction(String action); 
	public boolean KiemtraKS(String nam, String thang);
	
	public void createRs();
	public void DbClose();
	

}
