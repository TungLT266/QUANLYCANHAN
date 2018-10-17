package geso.traphaco.center.beans.hopdong;

import java.sql.ResultSet;

public interface IErpHopdong
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
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
	
	public boolean createNK();
	public boolean updateNK(String checkKM);
	
	public void createRs();
	public void init();
	
	public void DBclose();
	
	public String getHopdongId();
	public void setHopdongId(String hopdongId);
	public ResultSet getHopdongRs();
	public void setHopdongRs(ResultSet hopdongRs);
	
	public String getHopdongchung();
	public void setHopdongchung(String hopdongchung);
	
	
	public String getKhApdungId();
	public void setKhApdungId(String khApdungId);
	public ResultSet getKhApdungRs();
	public void setKhApdungRs(ResultSet khApdungRs);
	
	
}
