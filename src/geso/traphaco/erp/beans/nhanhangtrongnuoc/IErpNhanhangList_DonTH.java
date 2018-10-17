package geso.traphaco.erp.beans.trongnuoc;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpNhanhangList_DonTH extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getNCC();
	public void setNCC(String ncc);
	public String getSoPO();
	public void setSoPO(String soPO);
	
	public String getSoNhanhang();
	public void setSoNhanhang(String soNhanhang);
	public String getSoHoadon();
	public void setSoHoadon(String soHoadon);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getNhList();
	public void setNhList(ResultSet nhlist);
	
	public ResultSet getRsDontrahang_daduyet();
	public void setRsDontrahang_daduyet(ResultSet rs);
	
	
	
	public void init(String search);
	public void DBclose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
}
