package geso.traphaco.center.beans.bcchart;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBcchart extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setnam(String nam);
	public String getMsg();
	public void setMsg(String msg);
	
	public String[] getArrTenDiaBan();
	public String[] getArrIDDiaBan();
	public String[] getArrTenMien();
	public String[] getArrIDMien();
	public String[] getArrTenNSP();
	public String[] getArrIDNSP();
	public String[] getArrTenSanPhamMoi();
	public String[] getArrIDSanPhamMoi();
	public String[] getArrTenSanPhamChuLuc();
	public String[] getArrIDSanPhamChuLuc();
	
	
	public void initTonKho(String query);
	public void initDoanhSo(String query);
	public void initDoanhThu(String query);
	public void initSanPham(String query);
	public ResultSet getRsTonKhoDiaBan();
	public ResultSet getRsTonKhoMien();
	public ResultSet getRsDoanhSoDiaBan();
	public ResultSet getRsDoanhSoMien();
	public ResultSet getRsDoanhThuDiaBan();
	public ResultSet getRsDoanhThuMien();
	public ResultSet getRsDoanhSoNSP();
	public ResultSet getRsDoanhSoSPChuLuc();
	public ResultSet getRsDoanhSoSPMoi();

	public void DbClose();
}
