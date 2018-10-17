package geso.traphaco.erp.beans.kiemkho;



import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpKiemKhoList extends Serializable, IPhan_Trang
{
	public String getKhoTT_FK();
	public void setKhoTT_FK(String khoTT_FK);
	
	public String getTuNgay();
	public void setTuNgay(String tungay);
	
	public void setFromMonth(String month);
	public String getFromMonth();
	public void setToMonth(String month);
	public String getToMonth();
	public void setFromYear(String fromyear);
	public String getFromYear();
	public void setToYear(String toyear);
	public String getToYear();
	
	
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	
	public String getMSG();
	public void setMSG(String MSG);
	
	public ResultSet getRsKho();
	public void setRsKho(ResultSet kho);
	
	public ResultSet getRsKhu();
	public void setRsKhu(ResultSet RsKhu);
	
	public ResultSet getRsSanPham();
	public void setRsSanPham(ResultSet sanpham);
	
	public void  init(String query);
	
	public ResultSet getRsDieuChinhTonKho();
	public void setRsDieChinhTonKho(ResultSet rsDieuChinhTonKho);
	
	public String getKhu();
	public void setKhu(String Khu);
	public String getUsedId();
	public void setUsedId(String usedid);
	


	public void Delete(String Id);

	
	public void DbClose();
}
