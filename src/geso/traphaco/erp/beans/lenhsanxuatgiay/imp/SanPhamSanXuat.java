package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.lenhsanxuatgiay.ISanPhamSanXuat;

public class SanPhamSanXuat  implements ISanPhamSanXuat{

	private String IdLsx;
	private String IdSp;
	private String Masp;
	private String Tensp;
	private String Soluong;
	private String BomId;
	private String Khorong;
	private float quydoi;
	private ResultSet rsbom;
	private String chongbui="";
	
	private String donvi;
	private String Iscore="";
	
	private String codemau="";
	
	
	String Tenkh="";
	String Idkh="";
 
	@Override
	public void setLSXId(String LsxId) {
		// TODO Auto-generated method stub
		this.IdLsx=LsxId;
	}

	@Override
	public String getLSXId() {
		// TODO Auto-generated method stub
		return IdLsx;
	}

	@Override
	public void setIdSp(String _IdSp) {
		// TODO Auto-generated method stub
		this.IdSp=_IdSp;
	}

	@Override
	public String getIdSp() {
		// TODO Auto-generated method stub
		return this.IdSp;
	}

	@Override
	public void setMaSp(String _masp) {
		// TODO Auto-generated method stub
		this.Masp=_masp;
	}

	@Override
	public String getMaSp() {
		// TODO Auto-generated method stub
		return this.Masp;
	}

	@Override
	public void setSoluong(String soluong) {
		// TODO Auto-generated method stub
		this.Soluong=soluong;
	}

	@Override
	public String getSoluong() {
		// TODO Auto-generated method stub
		return this.Soluong;
	}

	@Override
	public void setTenSp(String _TenSp) {
		// TODO Auto-generated method stub
		this.Tensp=_TenSp;
	}

	@Override
	public String getTenSp() {
		// TODO Auto-generated method stub
		return this.Tensp;
	}

	@Override
	public void setBom(String bomid) {
		// TODO Auto-generated method stub
		this.BomId=bomid;
	}

	@Override
	public String getBomId() {
		// TODO Auto-generated method stub
		return this.BomId;
	}

	@Override
	public void SetRsBom(ResultSet rs) {
		// TODO Auto-generated method stub
		this.rsbom=rs;
	}

	@Override
	public ResultSet getRsBom() {
		// TODO Auto-generated method stub
		return this.rsbom;
	}

	@Override
	public void setKhorong(String _Khorong) {
		// TODO Auto-generated method stub
		this.Khorong=_Khorong;
	}

	@Override
	public String getKhorong() {
		// TODO Auto-generated method stub
		return this.Khorong;
	}

	@Override
	public void setdonvi(String donvi) {
		// TODO Auto-generated method stub
		this.donvi=donvi;
	}

	@Override
	public String getDonvi() {
		// TODO Auto-generated method stub
		return this.donvi;
	}

	@Override
	public void setquydoi(float qd) {
		// TODO Auto-generated method stub
		this.quydoi=qd;
	}

	@Override
	public float getquydoi() {
		// TODO Auto-generated method stub
		return this.quydoi;
	}

	@Override
	public void setChongbui(String chongbui) {
		// TODO Auto-generated method stub
		this.chongbui=chongbui;
	}

	@Override
	public String getChongbui() {
		// TODO Auto-generated method stub
		return this.chongbui;
	}

	@Override
	public void setIsCore(String iscore) {
		// TODO Auto-generated method stub
		this.Iscore=iscore;
	}

	@Override
	public String getIsCore() {
		// TODO Auto-generated method stub
		return this.Iscore;
	}

	@Override
	public void setIdKhachhang(String idkh) {
		// TODO Auto-generated method stub
		this.Idkh=idkh;
	}

	@Override
	public String getIdkhachhang() {
		// TODO Auto-generated method stub
		return this.Idkh;
	}

	@Override
	public void setTenkhachhang(String tenkh) {
		// TODO Auto-generated method stub
		this.Tenkh=tenkh;
	}

	@Override
	public String getTenkhachhang() {
		// TODO Auto-generated method stub
		return this.Tenkh;
	}

	@Override
	public void setcodemau(String codemau) {
		// TODO Auto-generated method stub
		this.codemau=codemau;
	}

	@Override
	public String getcodemau() {
		// TODO Auto-generated method stub
		return this.codemau;
	}

}
