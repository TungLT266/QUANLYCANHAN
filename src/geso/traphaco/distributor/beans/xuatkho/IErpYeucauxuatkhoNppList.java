package geso.traphaco.distributor.beans.xuatkho;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpYeucauxuatkhoNppList extends Serializable, IPhan_Trang
{
	public String getCtyId();
	public void setCtyId(String ctyId);
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	
	public String getKbhId();
	public void setKbhId(String KbhId);
	
	public String getKvId();
	public void setKvId(String KvId);
	
	public ResultSet getKbhRs();
	
	public ResultSet getKvRs();
	public String getSpId();
	public void setSpId(String SpId);
	public ResultSet getSpRs();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public ResultSet Laydinhkhoan(String Id);
	
	public String getSodonhang();
	public void setSodonhang(String sodonhang);
	public String getMaso();
	public void setMaso(String Maso);

	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public ResultSet getRsrskhoid() ;
	public void setRsrskhoid(ResultSet rsrskhoid) ;
	public String getNguoigiao();
	public void setNguoigiao(String nguoigiao);
	public String getKhohh() ;
	public void setKhohh(String khohh);
	
	public String getKhten();
	public void setKhten(String khten);
	
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
