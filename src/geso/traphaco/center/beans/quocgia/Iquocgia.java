package geso.traphaco.center.beans.quocgia;

import geso.traphaco.center.beans.khuvuc.IKhuvuc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface Iquocgia extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getTen();
	public void setTen(String tenquocgia);
	
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
	
	public void init(String Query);
	public ResultSet GetQuocgiars();
	public void SetQuocgiars(ResultSet rs);
	public void closeDB();
	public boolean update ();
	public List<Iquocgia> getQgList();
	public void setQgList(List<Iquocgia> quocgialist);
	
}
