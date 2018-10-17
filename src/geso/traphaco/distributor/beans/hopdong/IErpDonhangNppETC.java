package geso.traphaco.distributor.beans.hopdong;

import geso.traphaco.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpDonhangNppETC
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public ResultSet getHopdongRs();
	public void setHopdongRs(ResultSet hopdongRs);
	public String getMahopdong();
	public void setMahopdong(String ma);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	public String getDungchungKenh();
	public void setDungchungKenh(String dungchungKenh);
	
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	public ResultSet getGsbhRs();
	public void setGsbhRs(ResultSet gsbhRs);
	
	
	public ResultSet getSPRs();
	public void setSPRs(ResultSet SPRs);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	public ResultSet getDdkdRs();
	public void setDddkdRs(ResultSet ddkdRs);
	
	public ResultSet getDdkdSPRs();
	public void setDddkdSPRs(ResultSet ddkdSPRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
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
	public String[] getSpGianhapGOC();
	public void setSpGianhapGOC(String[] spGianhapgoc);
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpChiavaodongia();
	public void setSpChiavaodongia(String[] spChiavaodongia);
	
	public String[] getSpDonviChuan();
	public void setSpDonviChuan(String[] spDonvi);
	public String[] getSpSoluongChuan();
	public void setSpSoluongChuan(String[] spSoluong);
	
	public String[] getSpTrongluong();
	public void setSpTrongluong(String[] spTrongluong);
	public String[] getSpThetich();
	public void setSpThetich(String[] spThetich);
	public String[] getSpQuyDoi();
	public void setSpQuyDoi(String[] spQuyDoi);
	
	public String[] getSpTungay();
	public void setSpTungay(String[] spTungay);
	public String[] getSpDenngay();
	public void setSpDenngay(String[] spDenngay);
	
	public String[] getSpTDV();
	public void setSpTDV(String[] spTDV);
	
	public String[] getSpChietkhauBHKM();
	public void setSpChietkhauBHKM(String[] SpChietkhauBHKM);
	
	public String[] getSpDagiao();
	public void setSpDagiao(String[] spDagiao);
	
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);

	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public boolean createNK();
	public boolean updateNK(String checkKM);
	public String[] getSpSoluongton();
	public void setSpSoluongton(String[] spSoluongton);
	
	//LUU SO LO, SOLUONG THAY DOI  --> LUC DUYET SE TU DONG DE XUAT LO VA CO THE THAY DOI LAI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN();
	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);
	public boolean duyetETC(String congtyId, String vitriBAM);
	
	public String getDonhangmuon();
	public void setDonhangmuon(String donhangmuon);
	
	public void createRs( String tdv_dangnhap_id );
	public void init( String tdv_dangnhap_id );
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void DBclose();
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public String getCapduyet();
	public void setCapduyet(String capduyet);
	
	public String getDoituong();
	public void setDoituong(String doituong);
	
	public String getChophepsuagia();
	public void setChophepsuagia(String chophepsuagia);
	
	public String getKhachhangKGId();
	public void setKhachhangKGId(String khkgId);
	public ResultSet getKhachhangKGRs();
	public void setKhachhangKGRs(ResultSet khkgRs);
	
	public String getIsKhuyenmai();
	public void setIsKhuyenmai(String isKhuyenmai);
	
	public void ApChietKhau(String ddhId, dbutils db, String dung_db_moi, String aplaiKM);
	public void ApTichLuy_TheoGiaiDoan(String ddhId, dbutils db, String dung_db_moi);
	
	public String getIsMTV();
	public void setIsMTV(String isMTV);
	
	public String getSohopdong();
	public void setSohopdong(String sohopdong);
	
	public String getCapdogiaohang();
	public void setCapdogiaohang(String value);
	
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	
	public String getMaphieuMH();
	public void setMaphieuMH(String mapmh);
	public String getSotiengiam();
	public void setSotiengiam(String sotiengiam);
	
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	public ResultSet getTichluyRs();
	public void setTichluyRs(ResultSet tichluyRs);
	
	public String getTratichluy();
	public void setTratichluy(String value);
	
	public void checkKSKD();
	public void ApTichLuy();
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getTientichluy();
	public void setTientichluy(String tientichluy);
	
	public String getTienchietkhau();
	public void setTienchietkhau(String tienchietkhau);
	
	public String getCapnhatTDV();
	public void setCapnhatTDV(String capnhatTDV);
	
	public String getLydokhongduyet();
	public void setLydokhongduyet(String lydokhongduyet);
	
	// CÔNG NỢ
	public Double TongNo(); //1.
	public Double HanMucNo(); //2.
	public Double SoNgayNo(); //3.
	public Double NoTrongHan(); //4.
	public Double NoQuaHan(); //5.
	public Double NoXau();
	public Double NoQuaXau();
	
	public void XoaKhuyenMai(String schemeXOA);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
	
}
