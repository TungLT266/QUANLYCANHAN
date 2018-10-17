package geso.traphaco.erp.beans.hoadontrahang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;


public interface IErpHdTraHangList  extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getSoHoaDon();

	public void setSoHoaDon(String SoHoaDon);
	
	public String getNgayHoaDon();

	public void setNgayHoaDon(String ngayhoadon);
	
	
	public String getKhachHang();

	public void setKhachHang(String khachhang);

	public String getSoChungTu();

	public void setSoChungTu(String SoChungTu);

	public String getTuNgay();

	public void setTuNgay(String Tungay);

	public String getDenNgay();

	public void setDenNgay(String DenNgay);

	public String getTrangthai();

	public void setTrangthai(String Trangthai);
	
	public String getMessage();

	public void setMessage(String Message);
	
	public String getUserId();
	public void setUserId(String UserId);

	public void closeDB();
	
	public ResultSet getRsHdTraHang();
	
	public void setRsHdTraHang(ResultSet rsHdTraHang);

	public void init();

	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
}
