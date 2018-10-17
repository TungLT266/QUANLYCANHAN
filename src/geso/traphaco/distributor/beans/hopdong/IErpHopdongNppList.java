package geso.traphaco.distributor.beans.hopdong;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHopdongNppList extends Serializable, IPhan_Trang
{
	public String getSoHopDong();
	public void setSoHopDong(String soHopDong);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getMafast();
	public void setMafast(String mafast);
	
	public String getHieuLuc();
	public void setHieuLuc(String hieuLuc);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getKhTen();
	public void setKhTen(String khTen);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getLoaiHD();
	public void setLoaiHD(String loaihd);
	
	public String getMaHD();
	public void setMaHD(String mahd);
	
	public ResultSet getLoaiHDRs();
	public void setLoaiHDRs(ResultSet loaihdRs);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public void setDDkdRs(ResultSet ddkdRs) ;
	public ResultSet getDdkdRs();
	public void setDdkdId(String ddkdId) ;
	public String getDdkdId();
	
	public void init(String search);
	public void DBclose();
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
	
}
