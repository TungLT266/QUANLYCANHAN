package geso.traphaco.distributor.beans.cttongketnapchai;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ICttongketnapchaiList extends Serializable {
	public String getUserId();

	public void setUserId(String userId);
	
	public String getNppId();
	public void setMa(String ma);
	public String getMa();
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	public void setNppTen(String nppTen);
	public String getNppTen();
	public void setNppId(String nppId);

	public void setTen(String ten);
	public String getTen();
	
	public void setCttongketRS(ResultSet CttongketRS);
	public ResultSet getCttongketRS();
	public void init();
	public void DBclose();
}
