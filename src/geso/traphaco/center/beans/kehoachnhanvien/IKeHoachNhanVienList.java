package geso.traphaco.center.beans.kehoachnhanvien;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKeHoachNhanVienList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getTenNhanVien();	
	public void setTenNhanVien(String kehoachnhanvien);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public String getThang();
	public void setThang(String thang);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getNhanVienId();
	public void setNhanVienId(String nhanvienId);
	
	public ResultSet getKhnvRs();	
	public void setKhnvRs(ResultSet khnvlist);
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public String getSearchQuery();
	public void closeDB();
	
	public boolean delete(String id);
	public boolean duyet(String id);
	public boolean moduyet(String id);
}
