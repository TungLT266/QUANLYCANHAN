package geso.traphaco.erp.beans.kichbansanxuatgiay;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpKichBanSanXuatGiayList  extends IPhan_Trang, Serializable
{
	public String getCtyId();

	public void setCtyId(String ctyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getMa();

	public void setMa(String ma);

	public String getSanpham();

	public void setSanpham(String sanpham);

	public String getTungay();

	public void setTungay(String tungay);

	public String getDenngay();

	public void setDenngay(String denngay);

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getNhaMayID();

	public void setNhaMayID(String nhamayid);
	
	public String getTenNhaMay();

	public void setTenNhaMay(String tennhamay);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public String getMsg();

	public void setMsg(String msg);

	public ResultSet getRsKbsx();

	public void setRsKbsx(ResultSet khlRs);
	
	public ResultSet getRsNhaMay();

	public void setRsNhaMay(ResultSet nhamayrs);

	public void init(String query);

	public void DbClose();
	
	
	public ResultSet getSpRs();

	public void setSpRs(ResultSet spRs) ;

}
