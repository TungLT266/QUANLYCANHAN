package geso.traphaco.erp.beans.phuongphapthunghiem;

import java.sql.ResultSet;

public interface IPhuongPhapThuNghiemList {
	public String getMaPPTN();
	public void setMaPPTN(String maPPTN);
	public String getTenVT();
	public void setTenVT(String tenVT);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public String getLoaiTieuChi();
	public void setLoaiTieuChi(String loaiTieuChi);
	public String getNgayTao();
	public void setNgayTao(String ngayTao);
	public String getNgaySua() ;
	public void setNgaySua(String ngaySua);
	public String getUserId();
	public void setUserId(String userId);
	public String getUserName();
	public void setUserName(String userName);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getRsPPTNList();
	public void setRsPPTNList(ResultSet rsPPTNList);
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	public void init();
	public void DBClose();
	public void creates();
	public ResultSet getRsLoaiTieuChi();
	public void setRsLoaiTieuChi(ResultSet rsLoaiTieuChi);
	public ResultSet getRsYeuCauKyThuat();
	public void setRsYeuCauKyThuat(ResultSet rsYeuCauKyThuat);
	public String getYeuCauKyThuat();
	public void setYeuCauKyThuat(String yeuCauKyThuat);
	public String getSoLuongMau();
	public void setSoLuongMau(String soLuongMau);
}
