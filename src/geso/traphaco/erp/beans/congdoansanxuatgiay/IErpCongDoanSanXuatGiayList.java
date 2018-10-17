package geso.traphaco.erp.beans.congdoansanxuatgiay;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpCongDoanSanXuatGiayList  extends IPhan_Trang, Serializable 
{
	public String getCtyId();

	public void setCtyId(String ctyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getTungay();

	public void setTungay(String tungay);

	public String getDenngay();

	public void setDenngay(String denngay);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public String getSanPhamId();

	public void setSanPhamId(String sanphamId);

	public ResultSet getRsSp();

	public void setRsSp(ResultSet rsSp);

	public String getMsg();

	public void setMsg(String msg);

	public String getMa();

	public void setMa(String ma);

	public ResultSet getCumsanxuatRs();

	public void setCumsanxuatRs(ResultSet khlRs);

	public void init(String query);

	public void DbClose();

}
