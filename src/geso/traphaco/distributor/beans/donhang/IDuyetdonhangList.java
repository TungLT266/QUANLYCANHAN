package geso.traphaco.distributor.beans.donhang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDuyetdonhangList extends IPhan_Trang, Serializable
{	
	public String getUserId(); 
	public void setUserId(String userId);
	
	public ResultSet getDaidienkd();
	public void setDaidienkd(ResultSet daidienkd);	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMafast();
	public void setMafast(String mafast);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	public String getKhachhang();
	public void setKhachhang(String khachhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);

	public ResultSet getDonhangRs();
	public void setDonhangRs(ResultSet dhRs);
	
	public void init(String search, String duyet);
	public void DBclose();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	public void getSumBySearch(String sumqr);
	public double getTongTruoc();
	public double getTongCK();
	public double getTongSau();
	public boolean getIsSearch();
	public void setIsSearch(boolean search);
	
	public String getnvgnId();
	public void setnvgnId(String nvgnId);
	public ResultSet getnvgnRs();
	public void setnvgnRs(ResultSet nvgnRs);
	
	public String getQhId();
	public void setQhId(String qhId);
	
	public ResultSet getQhRs();
	public void setQhRs(ResultSet qhRs);
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	public String getCapduyet();
	public void setCapduyet(String capduyet);
	
	public ResultSet getNppTructhuocRs();
	public void setNppTructhuocRs(ResultSet tructhuocRs);	
	public String getNppTructhuocIds();
	public void setNppTructhuocIds(String tructhuocId);
	
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

