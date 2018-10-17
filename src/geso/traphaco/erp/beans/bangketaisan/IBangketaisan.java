package geso.traphaco.erp.beans.bangketaisan;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBangketaisan
{
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
	
	public String init();
	
	public String getDate();	
	
	public ResultSet getRsLoaiTS();

	public void setRsLoaiTS(ResultSet Rsloaits);
	
	public ResultSet getRsTrungTamCP();

	public void setRsTrungTamCP(ResultSet Rstrungtamcp);
	
	public String getLoaitsId();
	
	public void setLoaitsId(String loaitsId);
	
	public String getTTCpId();
	
	public void setTTCpId(String ttcpId);
	
	public void createRs();
	
	public void DBClose();
}
