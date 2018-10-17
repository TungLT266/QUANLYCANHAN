package geso.traphaco.erp.beans.hosokiemnghiem;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi;

import java.sql.ResultSet;
import java.util.List;

public interface IErpHoSoKiemNghiemList {

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getHosoRs();
	public void setHosoRs(ResultSet hosoRs);
	
	public String Delete(String id, String userId);
	public void init();
	
	public dbutils getDb();
	public void setDb(dbutils db);
	public String getPk_seq();
	public void setPk_seq(String pk_seq) ;
	public String getNgayChungTu();
	public void setNgayChungTu(String ngayChungTu);
	public String getMaSoKN();
	public void setMaSoKN(String maSoKN);
	public String getMaPhongBan();
	public void setMaPhongBan(String maPhongBan);
	public String getSoPhieuKN();
	public void setSoPhieuKN(String soPhieuKN);
	public String getThoiGianGiaoMau();
	public void setThoiGianGiaoMau(String thoiGianGiaoMau);
	public String getNguoiGuiMau();
	public void setNguoiGuiMau(String nguoiGuiMau);
	public String getMaPhieuYeuCauLayMau() ;
	public void setMaPhieuYeuCauLayMau(String maPhieuYeuCauLayMau);
	public String getMaSanPham();
	public void setMaSanPham(String maSanPham);
	public String getTenSanPham();
	public void setTenSanPham(String tenSanPham);
	public String getMaLoaiMauKN();
	public void setMaLoaiMauKN(String maLoaiMauKN);
	public String getTenLoaiKN();
	public void setTenLoaiKN(String tenLoaiKN);
	public String getMaTieuChuanKiemNghiem();
	public void setMaTieuChuanKiemNghiem(String maTieuChuanKiemNghiem);
	public String getLoaiKiemTra();
	public void setLoaiKiemTra(String loaiKiemTra);
	public String getThoiDiemLayMau();
	public void setThoiDiemLayMau(String thoiDiemLayMau);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	public String getUserName();
	public void setUserName(String userName);
	public ResultSet getRsPhongBan();
	public void setRsPhongBan(ResultSet rsPhongBan) ;
	public ResultSet getRsYCLayMau();
	public void setRsYCLayMau(ResultSet rsYCLayMau);
	public ResultSet getRsTCKiemNghiem();
	public void setRsTCKiemNghiem(ResultSet rsTCKiemNghiem);
	public ResultSet getRsNhanVien();
	public void setRsNhanVien(ResultSet rsNhanVien);
	public List<IHoSoKiemNghiemChiTiet> getListCT();
	public void setListCT(List<IHoSoKiemNghiemChiTiet> listCT);
	public List<IHoSoKiemNghiemThietBi> getListTB();
	public void setListTB(List<IHoSoKiemNghiemThietBi> listTB);
	public String getDateTime();
	public void creates();
	
	public ResultSet getSanPhamRs();
	public void setSanPhamRs(ResultSet sanPhamRs);
	public String getSpId();
	public void setSpId(String spId);
}
