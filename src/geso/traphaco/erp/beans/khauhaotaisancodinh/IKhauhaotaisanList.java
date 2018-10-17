package geso.traphaco.erp.beans.khauhaotaisancodinh;

import java.sql.ResultSet;

public interface IKhauhaotaisanList {
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
	
	public ResultSet getTaisanRs();

	public void setTaisanRs(ResultSet taisanRs);
	
	public String getTentaisan(); 

	public void setTentaisan(String tentaisan); 
	
	public String getMsg(); 

	public void setMsg(String msg); 
	
	public String getAction(); 

	public void setAction(String action); 
	public boolean KiemtraKS(String nam, String thang);
	
	public void createRs();
	public void DbClose();
	
	public String getNppId();

	public void setNppId(String nppId);
	
	public String getDVKDID();

	public void setDVKDID(String dVKDID);


	

}
