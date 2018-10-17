package geso.traphaco.erp.beans.khauhaotaisancodinh.imp;

import geso.traphaco.erp.beans.khauhaotaisancodinh.IGiaTriKhauHao;;

public class GiaTriKhauHao implements IGiaTriKhauHao{
	String thang;
	String taisanid;
	String mataisan;
	String diengiai;
	String khauhaodukien;
	String khauhaoluykedukien;
	String giatriconlaidukien;
	String khauhaothucte;
	String tonggiatri;
	String khauhaoluykethucte;
	String nhomTaiSan;
	String loaiTaiSan;
	String giatriconlaithucte;
	String thangKhauHaoTrenHeThong;
	String thangKhauHaoThucTe;
public  GiaTriKhauHao() 
{
	this.thang="";
	this.nhomTaiSan="";
	this.loaiTaiSan="";
	this.mataisan="";
	this.diengiai="";
	this.taisanid="";
	this.khauhaodukien="";
	this.khauhaoluykedukien="";
	this.giatriconlaidukien="";
	this.khauhaothucte="";
	this.khauhaoluykethucte="";
	this.giatriconlaithucte="";
	this.thangKhauHaoTrenHeThong="";
	this.thangKhauHaoThucTe="";
	this.tonggiatri="";
}
public String getThang() {
	return thang;
}
public void setThang(String thang) {
	this.thang = thang;
}
public String getTaisanid() {
	return taisanid;
}
public void setTaisanid(String taisanid) {
	this.taisanid = taisanid;
}
public String getKhauhaodukien() {
	return khauhaodukien;
}
public void setKhauhaodukien(String khauhaodukien) {
	this.khauhaodukien = khauhaodukien;
}
public String getKhauhaoluykedukien() {
	return khauhaoluykedukien;
}
public void setKhauhaoluykedukien(String khauhaoluykedukien) {
	this.khauhaoluykedukien = khauhaoluykedukien;
}
public String getGiatriconlaidukien() {
	return giatriconlaidukien;
}
public void setGiatriconlaidukien(String giatriconlaidukien) {
	this.giatriconlaidukien = giatriconlaidukien;
}
public String getKhauhaothucte() {
	return khauhaothucte;
}
public void setKhauhaothucte(String khauhaothucte) {
	this.khauhaothucte = khauhaothucte;
}
public String getKhauhaoluykethucte() {
	return khauhaoluykethucte;
}
public void setKhauhaoluykethucte(String khauhaoluykethucte) {
	this.khauhaoluykethucte = khauhaoluykethucte;
}
public String getGiatriconlaithucte() {
	return giatriconlaithucte;
}
public void setGiatriconlaithucte(String giatriconlaithucte) {
	this.giatriconlaithucte = giatriconlaithucte;
}
public String getMataisan() {
	return mataisan;
}
public void setMataisan(String mataisan) {
	this.mataisan = mataisan;
}
public String getDiengiai() {
	return diengiai;
}
public void setDiengiai(String diengiai) {
	this.diengiai = diengiai;
}
public String getThangKhauHaoTrenHeThong() {
	return thangKhauHaoTrenHeThong;
}
public void setThangKhauHaoTrenHeThong(String thangKhauHaoTrenHeThong) {
	this.thangKhauHaoTrenHeThong = thangKhauHaoTrenHeThong;
}
public String getThangKhauHaoThucTe() {
	return thangKhauHaoThucTe;
}
public void setThangKhauHaoThucTe(String thangKhauHaoThucTe) {
	this.thangKhauHaoThucTe = thangKhauHaoThucTe;
}
public String getNhomTaiSan() {
	return nhomTaiSan;
}
public void setNhomTaiSan(String nhomTaiSan) {
	this.nhomTaiSan = nhomTaiSan;
}
public String getTonggiatri() {
	return tonggiatri;
}
public void setTonggiatri(String tonggiatri) {
	this.tonggiatri = tonggiatri;
}
public String getLoaiTaiSan() {
	return loaiTaiSan;
}
public void setLoaiTaiSan(String loaiTaiSan) {
	this.loaiTaiSan = loaiTaiSan;
}
	

}
