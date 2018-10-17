package geso.traphaco.erp.beans.congty;
import java.sql.ResultSet;
public interface IErpCongTyList 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	
	public String getID();
	public void setID(String pk_seo);
	
	public String getMA();
	public void setMA(String ma);
	
	public String getTEN();
	public void setTEN(String ten);
	
	public String getDIACHI();
	public void setDIACHI(String diachi);
	
	public String getMASOTHUE();
	public void setMASOTHUE(String masothue);
	
	public String getNGANHNGHEKINHDOANH();
	public void setNGANHNGHEKINHDOANH(String nganhnghekinhdoanh);
	
	public String getDIENTHOAI();
	public void setDIENTHOAI(String dienthoai);
	
	public String getFAX();
	public void setFAX(String fax);
	
	public String getGIAMDOC();
	public void setGIAMDOC(String giamdoc);
	
	public String getKETOANTRUONG();
	public void setKETOANTRUONG(String ketoantruong);

	public String getTRANGTHAI();
	public void setTRANGTHAI(String trangthai);
	
	public void closeDB();
	
	public void init(String query);
	
	public String getMessage();
	public void setMessage(String msg);
	
	public ResultSet getCongtyList();
}
