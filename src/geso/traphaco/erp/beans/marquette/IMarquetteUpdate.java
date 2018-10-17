package geso.traphaco.erp.beans.marquette;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IMarquetteUpdate extends Serializable  {
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTen();
	public void setTen(String ten);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	public String getMessage();
	public void setMessage(String msg);
	
	public String getspId();
	public void setspId(String _spid);
	public void setspList(ResultSet spList);
	public ResultSet getspList(); 
	
	public String getspTen();
	public void setspTen(String _spten);
	
	public ResultSet createspList();
	public boolean create();
	public boolean update();
	public void DBClose();
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getSodangky();
	public void setSodangky(String _sodangky);
	
	public String getquycach();
	public void setquycach(String _quycach);
	
	
	
}



