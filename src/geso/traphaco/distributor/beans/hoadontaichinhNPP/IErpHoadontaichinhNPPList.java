package geso.traphaco.distributor.beans.hoadontaichinhNPP;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpHoadontaichinhNPPList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getnppId();
	public void setnppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getKhTen();
	public void setKhTen(String KhTen);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet KhRs);
	
	public String getKbhId();
	public void setKbhId(String KbhId);
	
	public String getKvId();
	public void setKvId(String KvId);
	
	public ResultSet getKbhRs();
	
	public ResultSet getKvRs();
	
	public ResultSet getHtbhRs();
	
	public String getHtbhId();
	public void setHtbhId(String HtbhId);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getSodonhang();
	public void setSodonhang(String sodonhang);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	public boolean getIsSearch();
	public void setIsSearch(boolean search);
	public double getTongTruoc();
	public double getTongCK();
	public double getTongSau();
	public void getSumBySearch(String sumqr);
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	public String getTensp();

	public void setTensp(String tensp) ;

	public String getThuegtgt();

	public void setThuegtgt(String thuegtgt);
	public String getGiasp();

	public void setGiasp(String giasp);
	public void setRstien(ResultSet rstien);
	public void laytien(String sql);
	public ResultSet getRstien() ;
	
	public void setTenxuatHD(String tenxuatHD);
	public String getTenxuatHD();

	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	// TỈNH THÀNH	
	public String getTinhthanhId();
	public void setTinhthanhId(String TinhthanhId);
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet tinhthanhRs);
	
	// QUẬN HUYỆN	
	public String getQuanhuyenId();
	public void setQuanhuyenId(String QuanhuyenId);
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet quanhuyenRs);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
	
}
