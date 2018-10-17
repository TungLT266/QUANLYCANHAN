package geso.traphaco.center.beans.hoadonphelieu;

import geso.traphaco.center.util.IPhan_Trang;

import java.sql.ResultSet;

public interface IErpXuatkmkhonghdList extends IPhan_Trang
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
	
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getGiamgiaRs();
	public void setGiamgiaRs(ResultSet giamgiaRs);

	
	public void init(String query);
	public void initBC(String search);
	
	public void DbClose();
	
}
