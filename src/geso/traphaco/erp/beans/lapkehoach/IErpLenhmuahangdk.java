package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpLenhmuahangdk 
{
	public String ctyId(); 

	public void setCtyId(String ctyId); 

	public String getUserId();
	
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getMasp(); 

	public void setMasp(String masp); 

	public String getTensp(); 

	public void setTensp(String tensp); 

	public String getSoluong(); 

	public void setSoluong(String soluong); 
	
	public String getNgay(); 

	public void setNgay(String ngay); 

	public String getMsg();
	
	public void setMsg(String msg);

	public String getDonvi(); 

	public void setDonvi(String donvi); 
		
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public void init();
	
	public boolean update();
	
	public void DbClose();
}
