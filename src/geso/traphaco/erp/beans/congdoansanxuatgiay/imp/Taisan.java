package geso.traphaco.erp.beans.congdoansanxuatgiay.imp;

import geso.traphaco.erp.beans.congdoansanxuatgiay.ITaisan;

public class Taisan implements ITaisan {
	String pk_seq;
	String ma;
	String ten;
	public String getPk_seq() {
		return pk_seq;
	}
	public void setPk_seq(String pk_seq) {
		this.pk_seq = pk_seq;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
		
}
