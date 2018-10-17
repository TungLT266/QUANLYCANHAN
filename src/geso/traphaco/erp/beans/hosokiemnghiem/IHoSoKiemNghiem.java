package geso.traphaco.erp.beans.hosokiemnghiem;

import geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi;

import java.sql.ResultSet;
import java.util.List;

public interface IHoSoKiemNghiem {

	public String getNgaygiaohang();
	public void createsloaimau();
	public void setNgaygiaohang(String ngaygiaohang);
	public ResultSet getRsSanPham();

	public void setRsSanPham(ResultSet rsSanPham) ;

	public ResultSet getRsLoaiMauKN();
	public void setRsLoaiMauKN(ResultSet rsLoaiMauKN);
	public String getTenYeuCauKyThuat();

	public void setTenYeuCauKyThuat(String tenYeuCauKyThuat) ;
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getPk_seq();
	public void setPk_seq(String pk_seq);
	public String getNgayChungTu();
	public void setNgayChungTu(String ngayChungTu);
	public String getMaSoKN();
	public void setMaSoKN(String maSoKN);
	public String getMaPhongBan() ;
	public void setMaPhongBan(String maPhongBan);
	public String getSoPhieuKN();
	public void setSoPhieuKN(String soPhieuKN);
	public String getThoiGianGiaoMau() ;
	public void setThoiGianGiaoMau(String thoiGianGiaoMau);
	public String getNguoiGuiMau();
	public void setNguoiGuiMau(String nguoiGuiMau);
	public String getMaPhieuYeuCauLayMau() ;
	public void setMaPhieuYeuCauLayMau(String maPhieuYeuCauLayMau);
	public String getMaSanPham();
	public void setMaSanPham(String maSanPham);
	public String getMaLoaiMauKN();
	public void setMaLoaiMauKN(String maLoaiMauKN);
	public String getMaTieuChuanKiemNghiem();
	public void setMaTieuChuanKiemNghiem(String maTieuChuanKiemNghiem);
	public String getLoaiKiemTra();
	public void setLoaiKiemTra(String loaiKiemTra) ;
	public String getThoiDiemLayMau();
	public void setThoiDiemLayMau(String thoiDiemLayMau);
	public String getDienGiai() ;
	public void setDienGiai(String dienGiai) ;
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	public String getUserId() ;
	public void setUserId(String userId);
	public String getUserName();
	public void setUserName(String userName);
	public String getMsg() ;
	public void setMsg(String msg);
	public String getTenSanPham();
	public void setTenSanPham(String tenSanPham);
	public String getTenLoaiKN() ;
	public void setTenLoaiKN(String tenLoaiKN);
	public String getDateTime() ;
	public ResultSet getRsPhongBan() ;
	public void setRsPhongBan(ResultSet rsPhongBan);
	public ResultSet getRsYCLayMau();
	public void setRsYCLayMau(ResultSet rsYCLayMau);
	public ResultSet getRsTCKiemNghiem() ;
	public void setRsTCKiemNghiem(ResultSet rsTCKiemNghiem) ;
	public ResultSet getRsNhanVien();
	public void setRsNhanVien(ResultSet rsNhanVien);
	public List<IHoSoKiemNghiemChiTiet> getListCT();
	public void setListCT(List<IHoSoKiemNghiemChiTiet> listCT);
	public List<IHoSoKiemNghiemThietBi> getListTB();
	public void setListTB(List<IHoSoKiemNghiemThietBi> listTB);
	public void creates();
	public void init();
	public boolean save();
	public boolean update();
	public void DBClose();
}
