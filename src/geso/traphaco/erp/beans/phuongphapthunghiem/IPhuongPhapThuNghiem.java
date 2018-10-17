package geso.traphaco.erp.beans.phuongphapthunghiem;

import geso.traphaco.erp.beans.phuongphapthunghiemthamso.IPhuongPhapThuNghiemTieuDeMau;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.IPhuongPhapThuNghiemThamSo;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface IPhuongPhapThuNghiem {
	public String getPK_SEQ() ;
	public void setPK_SEQ(String pK_SEQ);
	public String getMaPPTN();
	public void setMaPPTN(String maPPTN);
	public String getTenPPTN();
	public void setTenPPTN(String tenPPTN);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public String getMaLoaiTieuChi();
	public void setMaLoaiTieuChi(String maLoaiTieuChi);
	public String getUserName();
	public void setUserName(String userName);
	public String getUserId();
	public void setUserId(String userId);
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangThai();
	public void setTrangThai(String trangThai);
	public ResultSet getRsLoaiTieuChi() ;
	public void setRsLoaiTieuChi(ResultSet rsLoaiTieuChi);
	public ResultSet getRsYeuCauKyThuat();
	public void setRsYeuCauKyThuat(ResultSet rsYeuCauKyThuat);
	public String getMaYeuCauKyThuat();
	public void setMaYeuCauKyThuat(String maYeuCauKyThuat);
	public List<IPhuongPhapThuNghiemThamSo> getListThamSo() ;
	public void setListThamSo(List<IPhuongPhapThuNghiemThamSo> listThamSo);
	public ResultSet getRsDonViDoLuong() ;
	public void setRsDonViDoLuong(ResultSet rsDonViDoLuong);
	public String getSoLuongTs();
	public void setSoLuongTs(String soLuongTs) ;
	public String getSoLuongMau();
	public void setSoLuongMau(String soLuongMau);
	public String getDateTime();
	public void init();
	public void creates();
	public void DBClose();
	public boolean save();
	public boolean update();
	public boolean delete();
	public ResultSet getRsSanPham();
	public void setRsSanPham(ResultSet rsSanPham);
	public String getMasanpham();
	public void setMasanpham(String masanpham);
	public List<IPhuongPhapThuNghiemTieuDeMau> getListTieuDeMau();
	public void setListTieuDeMau(List<IPhuongPhapThuNghiemTieuDeMau> listTieuDeMau);
}
