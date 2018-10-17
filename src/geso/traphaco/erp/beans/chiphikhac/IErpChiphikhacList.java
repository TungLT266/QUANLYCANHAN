package geso.traphaco.erp.beans.chiphikhac;

import geso.traphaco.center.util.IThongTinHienThi;

import java.sql.ResultSet;
import java.util.List;

public interface IErpChiphikhacList 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getSoHoaDon();
	public void setSoHoaDon(String sohoadon);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNgayTao();
	public void setNgayTao(String ngaytao);	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getChiphikhacRs();
	public void setChiphikhacRs(ResultSet cpkRs);	
	
	public ResultSet getNguoiTaoRs();
	public void setNguoiTaoRs(ResultSet nguoitaoRs);
	public String getNguoiTao();
	public void setNguoiTao(String nguoitaoid);
	
	public void init(String query);
	public void Chot(String Id);
	public void Xoa(String Id);
	
	public void DbClose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
}
