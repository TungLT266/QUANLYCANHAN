package geso.traphaco.center.beans.banggiablkh;

import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IBanggiablKhList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getUserTen(); 
	public void setUserTen(String userTen); 
	
	public String getSpTen(); 
	public void setSpTen(String ten);
	
	public String getSpMa();
	public void setSpMa(String ma);	

	public String getMsg();
	public void setMsg(String msg);
	
	/*public String getKenhId();
	public void setKenhId(String kenhId);
	
	public String getTrangthai(); 
	public void setTrangthai(String trangthai); 

	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	public void setKenh(ResultSet kenh);
	
	public void init(String search);
	public void closeDB();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public void setDenngay(String denngay);
	public String getDenngay();
	
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getType();
	public void setType(String type);*/
	public ResultSet getBglist(); 
	public void setBglist(ResultSet bglist); 
	public void init(String search);
	public void DbClose();
	
}
