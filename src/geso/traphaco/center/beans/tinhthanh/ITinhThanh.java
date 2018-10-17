package geso.traphaco.center.beans.tinhthanh;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITinhThanh extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	

	public void setQuocgia(ResultSet qg);
	public ResultSet getQuocgia();
	
	public void setMaQuocgia(String maqg);
	public String getMaQuocGia();
	
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
	

	
	public boolean CreateTt();
	public boolean UpdateTt();
	public void createRS();
}
