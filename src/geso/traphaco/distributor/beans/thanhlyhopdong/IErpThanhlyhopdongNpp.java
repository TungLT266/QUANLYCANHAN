package geso.traphaco.distributor.beans.thanhlyhopdong;

import java.sql.ResultSet;

public interface IErpThanhlyhopdongNpp
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getMahopdong();
	public void setMahopdong(String ma);

	public String getHopdongchung();
	public void setHopdongchung(String hopdongchung);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNgaythanhly();
	public void setNgaythanhly(String ngaytl);
	public String getNgaythanhlykt();
	public void setNgaythanhlykt(String ngaytlkt);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	
	public String[] getMahoadon();
	public void setMahoadon(String[] mahoadon);
	
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhId();
	public void setKhId(String khId);
	
	public int getLoaithanhly();
	public void setLoaithanhly(int loaithanhly);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getKhApdungId();
	public void setKhApdungId(String khApdungId);
	public ResultSet getKhApdungRs();
	public void setKhApdungRs(ResultSet khApdungRs);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	public ResultSet getMahdRs();
	public void setMahdRs(ResultSet MahdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	public ResultSet getGsbhRs();
	public void setGsbhRs(ResultSet gsbhRs);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	public ResultSet getDdkdRs();
	public void setDddkdRs(ResultSet ddkdRs);
	
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
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	
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
	
	public String getHopdongId();
	public void setHopdongId(String hopdongId);
	public ResultSet getHopdongRs();
	public void setHopdongRs(ResultSet hopdongRs);
	
	public String getNgaytrungthau();
	public void setNgaytrungthau(String ngaytrungthau);
	public String getChiphibaolanh();
	public void setChiphibaolanh(String chiphibaolanh);
	
	public boolean createNK();
	public boolean updateNK(String checkKM);
	public boolean convertSO();
	
	public void createRs();
	public void init();
	public void initnew();
	public void DBclose();
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
}
