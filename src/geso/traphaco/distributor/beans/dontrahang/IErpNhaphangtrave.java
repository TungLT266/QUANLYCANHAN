package geso.traphaco.distributor.beans.dontrahang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public interface IErpNhaphangtrave extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getLenhdieudong();
	public void setLenhdieudong(String lenhdieudong);
	
	public String getLDDcua();
	public void setLDDcua(String LDDcua);
	
	public String getLDDveviec();
	public void setLDDveviec(String LDDveviec);
	
	public String getNgaydieudong();
	public void setNgaydieudong(String ngaydieudong);	
	
	public String getNguoivanchuyen();
	public void setNguoivanchuyen(String nguoivanchuyen);
	
	public String getPtvanchuyen();
	public void setPtvanchuyen(String ptvanchuyen);
	
	public String getSohopdong();
	public void setSohopdong(String sohopdong);
	
	public String getNgayhopdong();
	public void setNgayhopdong(String ngayhopdong);
	
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	public ResultSet getKhoXuatRs();
	public void setKhoXuatRs(ResultSet khoxuatRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
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
	public String[] getSpSolo();
	public void setSpSolo(String[] spSolo);
	public String[] getSpTonkho();
	public void setSpTonkho(String[] spHansudung);
	public String[] getSpBooked();
	public void setSpBooked(String[] spBooked);
	public String[] getSpNgaysanxuat();
	public void setSpNgaysanxuat(String[] spNgaysanxuat);

	public String[] getSpNgayHetHan();
	public void setSpNgayHetHan(String[] ngayHetHan);
	
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	
	public String[] getSpSoluongNHAN_DAT();
	public void setSpSoluongNHAN_DAT(String[] spSoluong);
	public String[] getSpSoluongNHAN_KHONGDAT();
	public void setSpSoluongNHAN_KHONGDAT(String[] spSoluong);
	
	//LUU SO LO, SOLUONG THAY DOI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public Hashtable<String, String> getSanpham_SoluongNHAN_DAT();
	public void setSanpham_SoluongNHAN_DAT(Hashtable<String, String> sp_soluong); 
	
	public Hashtable<String, String> getSanpham_SoluongNHAN_KHONGDAT();
	public void setSanpham_SoluongNHAN_KHONGDAT(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String tongluong);
	
	public ResultSet getSoloTheoSpNHAN_DAT(String spIds, String tongluong);
	public ResultSet getSoloTheoSpNHAN_KHONGDAT(String spIds, String tongluong);
	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK(String congtyId);
	public boolean updateNK(String congtyId);

	public String getDateTime();
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public void createRs();
	public void init(String action);
	
	public void DBclose();
	
	public String getSoChungTu();
	public void setSoChungTu(String sochungtu);
	
	public String getDungchungkenh();
	public void setDungchungkenh(String dungchungkenh);
	
	public Hashtable<String, Integer> getSp_Soluong();
	public void setSSp_Soluong(Hashtable<String, Integer> sp_sl);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);	
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	

	public String getTrahangId();
	public void setTrahangId(String trahangId);
	public ResultSet getTrahangRs();
	public void setTrahangRs(ResultSet trahangRs);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	// cho phép sửa số lô sản phẩm
	public HashMap<String, List<String>> getSanpham_solo();
	public void setSanpham_solo(HashMap<String, List<String>> sanpham_solo) ;
	public HashMap<String, List<String>> getSanpham_solo_ngayhethan() ;
	public void setSanpham_solo_ngayhethan(HashMap<String, List<String>> sanpham_solo_ngayhethan) ;
	public HashMap<String, List<String>> getSanpham_sologoc();
	public void setSanpham_sologoc(HashMap<String, List<String>> sanpham_sologoc) ;
	
	public void updateSoLo_NgayHetHan();
	public String getTrahangdienta(); 
	public void setTrahangdienta(String trahangdienta) ;
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
}
