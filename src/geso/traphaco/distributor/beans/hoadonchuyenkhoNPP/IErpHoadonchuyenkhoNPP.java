package geso.traphaco.distributor.beans.hoadonchuyenkhoNPP;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpHoadonchuyenkhoNPP
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getLoaiXHD();
	public void setLoaiXHD(String loaiXHD);
	
	public String getnppDangnhap() ;
	public void setnppDangnhap(String nppDangnhap) ;

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
	
	public String getDondathangId();
	public void setDondathangId(String kbhId);
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet ddhRs);
	
	
	public String[] getCkId();
	public void setCkId(String[] ckId);
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
	public String[] getSpTienthue();
	public void setSpTienthua(String[] spTienthue);
	public String[] getSpThanhtien();
	public void setSpThanhtien(String[] spThanhtien);
	public String[] getSpChonin();
	public void setSpChonin(String[] spChonin);
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	
	public String getTongtienBVAT();
	public void setTongtienBVAT(String bvat);
	public String getTongCK();
	public void setTongCK(String tongCK);
	public String getTongVAT();
	public void setTongVAT(String vat);
	public String getTongtienAVAT();
	public void setTongtienAVAT(String avat);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create(String congtyId);
	public boolean update();
	public boolean chot(String vitribam);
	
	public void createRs();
	public void init();
	
	public void DBclose();
	public void loadContents();
	
	public String getInNguoimua();
	public void setInNguoimua(String innguoimua);
	
	
	//CHO SUA THUE VAT THEO LO
	
	//LUU SO LO, SOLUONG THAY DOI  --> LUC DUYET SE TU DONG DE XUAT LO VA CO THE THAY DOI LAI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong, String chuyenkhoId);
	public String getMavuviec() ;

	public void setMavuviec(String mavuviec) ;
	
	public String getPtchietkhau();

	public void setPtchietkhau(String ptchietkhau);

	public String[] getTenXuatHD();
	public void setTenXuanHD(String[] value);
	
	public String getTenxuathd();
	public void setTenxuathd(String Tenxuathd);
	
	public String getKhGhiNo();
	public void setKhGhiNo(String KhGhiNo);
	
	public String getGhichu2();
	public void setGhichu2(String Ghichu2);
	
	public String getcongtyId();
	public void setcongtyId(String congtyId);
	
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	
	public String getnhanvienId();
	public void setnhanvienId(String nhanvienId);
	
	public ResultSet getNhanvienRs();
	public void setNhanvienRs(ResultSet nhanvienRs);
	
	public String getKhachhangKGId();
	public void setKhachhangKGId(String khkgId);
	
	public ResultSet getInvoiceInfo(String khId);

	public void setTthdId(String tthdId);
	public String getTthdId();
	
	public void setNguoimuahang(String nguoimuahang);
	public String getNguoimuahang();

	public void setDonvimua(String donvimua);
	public String getDonvimua();

	public void setDiachi(String diachi);
	public String getDiachi();
	
	public void setMasothue(String masothue);
	public String getMasothue();

	public String getTenxuathdNew();
	public void setTenxuathdNew(String tenxhdNew);
	
	public String getGhichudonhang();
	public void setGhuchidonhang(String ghichudonhang);
	
	//THONG TIN TICH LUY
	public Hashtable<String, String> getSchemeTichluy();
	public void setSchemeTichluy(Hashtable<String, String> schemeTichluy); 
	
	public String getTientichluy();
	public void setTientichluy(String tientichluy);

	public String getDoituong();
	public void setDoituong(String doituong);
}
