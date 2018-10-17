package geso.traphaco.distributor.beans.hoadonphelieu;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.PhanTrang;

import java.sql.ResultSet;
import java.util.List;

public interface IErpHoadonphelieuList extends IPhan_Trang
{
	
	public String getTennguoitao();
	public void setTennguoitao(String tennguoitao);
	
	public String getUserId();
	public void setUserId(String userId);

	public void setNppId(String nppId) ;
	public String getNppId();
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getKhachhang();
	public void setKhachhang(String Khachhang);
	public ResultSet getKhRs() ;
	public void setKhRs(ResultSet khRs) ;
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getGiamgiaRs();
	public void setGiamgiaRs(ResultSet giamgiaRs);

	
	public void init(String query);
	
	public void DbClose();
	
}
