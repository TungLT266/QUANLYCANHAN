package geso.traphaco.erp.beans.nganhangcongty;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpNganHangCongTyList extends Serializable, IPhan_Trang
{
	public String getId();

	public void setId(String id);

	public String getCongTy();

	public void setCongTy(String congty);
	
	public String getCtyId();

	public void setCtyId(String ctyId);

	public String getChuTaiKhoan();

	public void setChuTaiKhoan(String chutaikhoan);

	public String getLoaiTien();

	public void setLoaiTien(String loaitien);

	public String getNganHang();

	public void setNganHang(String nganhang);

	public String getSoTaiKhoan();

	public void setSoTaiKhoan(String sotaikhoan);

	public String getChiNhanh();

	public void setChiNhanh(String chinhanh);
	
	public void closeDB();
	
	public void init(String congtyId);	

	public void createaRs();

	public ResultSet getCongTyRs();

	public void setCongTyRs(ResultSet congty);

	public ResultSet getLoaiTienRs();

	public void setLoaiTien(ResultSet loaitien);

	public ResultSet getChiNhanhRs();

	public void setNganHang(ResultSet nganhang);

	public ResultSet getNganHangRs();

	public void setChiNhanhRs(ResultSet chinhanh);

	public ResultSet getNganHangCongTy();

	public void setNganHangCongTy(ResultSet nganhangcongty);
	
	public void setChixem(String chixem);
	public String getChixem();
}
