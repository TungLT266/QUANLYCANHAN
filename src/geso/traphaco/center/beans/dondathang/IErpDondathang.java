package geso.traphaco.center.beans.dondathang;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpDondathang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	public String getNgaydenghi();
	public void setNgaydenghi(String ngaydenghi);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
	public String getSchemeId();
	public void setSchemeId(String kbhId);
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public Hashtable<String, String> getScheme_Soluong();
	public void setScheme_Soluong(Hashtable<String, String> scheme_soluong); 
	
	public ResultSet getSpTheoScheme(String scheme, String tongluong);
	public ResultSet getSpTheoScheme_UngHang(String scheme, String tongluong);
	public ResultSet getSpTheoScheme_TrungBay(String scheme, String tongluong);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVat();
	public void setVat(String vat);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpGianhap();
	public void setSpGianhap(String[] spGianhap);
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpScheme();
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public void setSpScheme(String[] spScheme);
	public String[] getSpTrongluong();
	public void setSpTrongluong(String[] spTrongluong);
	public String[] getSpThetich();
	public void setSpThetich(String[] spThetich);
	public String[] getSpQuyDoi();
	public void setSpQuyDoi(String[] spQuyDoi);
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	public String[] getSpIskhongthue();

	public void setSpIskhongthue(String[] spIskhongthue);
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK(String checkKM);
	
	public boolean createKMTichLuy();
	public boolean updateKMTichLuy();
	
	public boolean createKMUngHang();
	public boolean updateKMUngHang();
	
	public boolean createTrungBay();
	public boolean updateTrungBay();
	
	public boolean createNoiBo();
	public boolean updateNoiBo();

	
	public void createRs();
	public void init();
	
	public void DBclose();
	
	public String getIsKm();
	public void setIsKm(String iskm);
	
	public String getIsdhk();
	public void setIsdhk(String isdhk) ;

	public String getIsgia();
	public void setIsgia(String isgia);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	//LUU SO LO, SOLUONG THAY DOI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN();
	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong);
	
	public String getETC();
	public void setETC(String ETC);
	
	//ĐƠN HÀNG ETC
	public String getMahopdong();
	public void setMahopdong(String ma);
	
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	public ResultSet getGsbhRs();
	public void setGsbhRs(ResultSet gsbhRs);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	public ResultSet getDdkdRs();
	public void setDddkdRs(ResultSet ddkdRs);
	
	public String getDanhmucKH();
	public void setDanhmucKH(String danhmucKH);
	
	//Đơn nội bộ lấy lại các KM scheme
	public Hashtable<String, String> getSchemeNoibo();
	public void setSchemeNoibo(Hashtable<String, String> schemeNoibo); 
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	public String[] getSpchietkhaugia() ;

	public void setSpchietkhaugia(String[] spchietkhaugia);

	public String[] getSptienvat();

	public void setSptienvat(String[] sptienvat);
	public Hashtable<String, String> getSchemeNoibo_bvat();

	public void setSchemeNoibo_bvat(Hashtable<String, String> schemeNoibo_bvat);

	public Hashtable<String, String> getSchemeNoibo_vat();

	public void setSchemeNoibo_vat(Hashtable<String, String> schemeNoibo_vat);

	public String getIshm();

	public void setIshm(String ishm) ;
}
