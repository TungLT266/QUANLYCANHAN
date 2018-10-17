package geso.traphaco.erp.beans.doituongkhac;

import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.util.List;

public interface IErpDoiTuongKhac extends Serializable, IPhan_Trang
{
	public void init();

	public boolean edit();

	public boolean delete();

	public boolean create();
	public void DBClose();
	
	public boolean CheckNumerOrNot(String number);

	public String getId();
	public void setId(String id);

	public String getUserId() ;
	public void setUserId(String userId);

	public String getCongTyId();
	public void setCongTyId(String congTyId) ;

	public String getMaDoiTuong();
	public void setMaDoiTuong(String maDoiTuong) ;

	public String getTenDoiTuong();
	public void setTenDoiTuong(String tenDoiTuong) ;

	public String getSoHieuTaiKhoan();
	public void setSoHieuTaiKhoan(String soHieuTaiKhoan);

	public String getTenTaiKhoan() ;
	public void setTenTaiKhoan(String tenTaiKhoan);

	public String getTrangThai();
	public void setTrangThai(String trangThai);

	public String getNppId();
	public void setNppId(String nppId);

	public String getDiaChi();
	public void setDiaChi(String diaChi);

	public String getDienThoai();
	public void setDienThoai(String dienThoai);

	public String getFax();
	public void setFax(String fax);

	public String getTenNguoiLienHe();
	public void setTenNguoiLienHe(String tenNguoiLienHe);

	public String getDtNguoiLienHe();
	public void setDtNguoiLienHe(String dtNguoiLienHe);

	public String getEmailNguoiLienHe();
	public void setEmailNguoiLienHe(String emailNguoiLienHe);

	public String getSoTaiKhoan();
	public void setSoTaiKhoan(String soTaiKhoan);

	public String getNganHangId();
	public void setNganHangId(String nganHangId);

	public String getChiNhanhId();
	public void setChiNhanhId(String chiNhanhId);

	public String getMaSoThue();
	public void setMaSoThue(String maSoThue);

	public String getQuanLyCongNo();
	public void setQuanLyCongNo(String quanLyCongNo);

	public String getMsg();
	public void setMsg(String msg);

	public List<Erp_Item> getNppList();
	public void setNppList(List<Erp_Item> nppList);

	public List<Erp_Item> getChiNhanhList();
	public void setChiNhanhList(List<Erp_Item> chiNhanhList);

	public List<Erp_Item> getNganHangList();
	public void setNganHangList(List<Erp_Item> nganHangList);

	public String getTenNganHang();
	public void setTenNganHang(String tenNganHang);

	public String getTenChiNhanh();
	public void setTenChiNhanh(String tenChiNhanh);
}