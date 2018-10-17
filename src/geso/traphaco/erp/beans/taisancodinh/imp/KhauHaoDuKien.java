package geso.traphaco.erp.beans.taisancodinh.imp;

import geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien;

public class KhauHaoDuKien implements IKhauHaoDuKien{
	String thang;
	String taisanid;
	String khauhaodukien;
	String khauhaoluykedukien;
	String giatriconlaidukien;
	String khauhaothucte;
	String khauhaoluykethucte;
	String giatriconlaithucte;
public  KhauHaoDuKien() 
{
	this.thang="";
	this.taisanid="";
	this.khauhaodukien="";
	this.khauhaoluykedukien="";
	this.giatriconlaidukien="";
	this.khauhaothucte="";
	this.khauhaoluykethucte="";
	this.giatriconlaithucte="";
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
	

}
