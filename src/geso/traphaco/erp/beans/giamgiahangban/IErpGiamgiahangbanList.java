package geso.traphaco.erp.beans.giamgiahangban;

import geso.traphaco.center.util.IThongTinHienThi;

import java.sql.ResultSet;
import java.util.List;

public interface IErpGiamgiahangbanList 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getGiamgiaRs();
	public void setGiamgiaRs(ResultSet giamgiaRs);
	public String getSohoadon(String Id);
	
	public String getKhachhang(); 

	public void setKhachhang(String khachhang); 

	public void init(String query);
	public void DbClose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
}
