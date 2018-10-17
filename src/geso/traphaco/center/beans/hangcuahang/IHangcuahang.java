package geso.traphaco.center.beans.hangcuahang;

import java.io.Serializable;

public interface IHangcuahang extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getHangcuahang();
	public void setHangcuahang(String hangcuahang);
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
	
	public boolean CreateHch();
	public boolean UpdateHch();
	public void closeDB();
	
	public void setDoanhThu_Tu(String doanhthu_tu);
	public String getDoanhThu_Tu();
	public void setDoanhThu_Den(String doanhthu_den);
	public String getDoanhThu_Den();
	public boolean KiemTraHMDoanhThuTrung();
	
	
}
