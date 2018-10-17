package geso.traphaco.erp.beans.vayvon;

import java.sql.ResultSet;

public interface INhantienvayList 
{	
	public void setSoHD(String SoHD);

	public String getSoHD();
	
	public void setNgay(String ngay);

	public String getNgay();

	public void setHinhthuc(String hinhthuc);

	public String getHinhthuc();

	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public ResultSet getNTRS();

	public void setUserId(String userId);

	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void init(String st);
	
	public void Xoa(String Id);
	
	public void DbClose();
	
	public String getTkvay();

	public void setTkvay(String tkvay);
	
	public String getNppId() ;



	public void setNppId(String nppId);

}
