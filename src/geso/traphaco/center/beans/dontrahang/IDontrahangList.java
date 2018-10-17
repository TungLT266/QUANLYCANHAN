package geso.traphaco.center.beans.dontrahang;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IDontrahangList extends  Serializable
{
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public ResultSet getDthList();
	public void setDthList(ResultSet dthList);
	
	public String getSKU();
	public void setSKU(String sku);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getMalist();
	public void setMalist(String malist);
	
	public void createDthlist(String querystr);
	public void DBclose();
	public String getMessage();
	public void setMesage(String msg);
	
}
