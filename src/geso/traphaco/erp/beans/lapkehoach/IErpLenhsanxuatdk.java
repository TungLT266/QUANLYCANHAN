package geso.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpLenhsanxuatdk 
{
	public String ctyId(); 

	public void setCtyId(String ctyId); 

	public String getUserId();
	
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	
	public ResultSet getNhamayRs();
	
	public void setNhamayRs(ResultSet khoRs);

	public String getNhamayId();
	
	public void setNhamayId(String nhamayId);

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
	
	public void createRs();
	
	public void init();
	
	public boolean update();
	
	public void DbClose();
}
