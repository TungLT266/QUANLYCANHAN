package geso.traphaco.distributor.beans.hoadontaichinhKM;

import geso.traphaco.distributor.beans.donhang.ISanpham;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IHoadontaichinhKM
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);


	public Double getTienKM();
	public void setTienKM(Double tienKM);
	
	public Double getTienSauKM();
	public void setTienSauKM(Double tienSauKM);
	
	public Double getTienCK();
	public void setTienCK(Double tienCK);
	
	public Double getTienSauCK();
	public void setTienSauCK(Double tienSauCK);
	
	public Double getTienVAT();
	public void setTienVAT(Double tienVAT);
	
	public String getKyhieuhoadon();
	public void setKyhieuhoadon(String kyhieuhoadon);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getNgayxuatHD();
	public void setNgayxuatHD(String ngayxuatHD);
	
	public String getNgayghinhanCN();
	public void setNgayghinhanCN(String ngayghinhanCN);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getHinhthucTT();
	public void setHinhthucTT(String hinhthucTT);
	
	public String getNguoimua();
	public void setNguoimua(String nguoimua);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String[] getDonDatHang();
	public void setDonDatHang(String[] dondathang);
	
	public String getDondathangId();
	public void setDondathangId(String kbhId);
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpDongia();
	public void setSpDongia(String[] spDongia);
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpLoai();
	public void setSpLoai(String[] spLoai);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public String[] getSpThue();
	public void setSpThue(String[] spThue);
	
	//Tra KM tich luy
	public String[] getTichLuy_Scheme();
	public void setTichLuy_Scheme(String[] tichluy_scheme);
	public String[] getTichLuy_Tongtien();
	public void setTichLuy_Tongtien(String[] tichluy_tongtien);
	public String[] getTichLuy_VAT();
	public void setTichLuy_TVAT(String[] tichluy_vat);
	
	//tra khuyen mai
	public Hashtable<String, Float> getScheme_Tongtien();
	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien);
	public Hashtable<String, Float> getScheme_Chietkhau();
	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau);
	public List<ISanpham> getScheme_SpList();
	public void setScheme_SpList(List<ISanpham> splist);

	public float getTongtiensauVAT();
	public void setTongtiensauVAT(float tongtiensauVAT);
	public float getTongtienCKKM();
	public void setTongtienCKKM(float ttckkm);
	public boolean isAplaikhuyenmai() ;
	public void setAplaikhuyenmai(boolean aplaikm) ;	
	public boolean isCokhuyenmai() ;
	public void setCokhuyenmai(boolean cokm) ;
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create();
	public boolean update();
	public boolean chot();
	
	public void createRs();
	public void init();
	
	public void DBclose();
	public void loadContents();
	
	public String getInNguoimua();
	public void setInNguoimua(String innguoimua);
	
	public String getDonHangKhac();
	public void setDonHangKhac(String dh);
	
	
	public String getPxkId();
	public void setPxkId(String pxkId);
	
}
