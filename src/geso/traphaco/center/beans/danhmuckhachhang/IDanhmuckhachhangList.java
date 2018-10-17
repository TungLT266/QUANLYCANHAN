package geso.traphaco.center.beans.danhmuckhachhang;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.beans.khachhang.IKhachhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDanhmuckhachhangList extends IPhan_Trang, Serializable 
{	
	public String getUserId();
	public void setUserId(String userId);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTpId(); 
	public void setTpId(String tpId); 
	public String getQhId(); 
	public void setQhId(String qhId); 	
	
	public ResultSet getTp(); 
	public void setTp(ResultSet tp); 
	public ResultSet getQh(); 
	public void setQh(ResultSet qh); 	
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	
	/*public String getTtTen();
	public void setttTen(String ttTen);
	
	public String getQhTen();
	public void setQhTen(String qhTen);*/
	
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public List<IDanhmuckhachhang> getKhList();
	public void setKhList(List<IDanhmuckhachhang> khlist);
	
	//Cac phuong thuc Get, Set tra ve ResultSet tuong ung
	public ResultSet getHangcuahang();
	public void setHangcuahang(ResultSet hangcuahang);	
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	public ResultSet getVitricuahang();
	public void setVitricuahang(ResultSet vitricuahang);
	public ResultSet getLoaicuahang();
	public void setLoaicuahang(ResultSet loaicuahang);
	public ResultSet getNhomcuahang();
	public void setNhomcuahang(ResultSet nhomcuahang);
	
	public ResultSet getNhaphanphoi();
	public void setNhaphanphoi(ResultSet nhaphanphoi);
	
	//Cac phuong thuc Get, Set cua thuoc tinh duoc chon
	public String getHchId();
	public void setHchId(String hchId);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public String getVtchId();
	public void setVtId(String vtchId);
	public String getLchId();
	public void setLchId(String lchId);
	public String getNchId();
	public void setNchId(String nchId);
	
	public void init(String search);
	public void khChuaPhanTuyen(String dk);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	public void DBclose();
	
}