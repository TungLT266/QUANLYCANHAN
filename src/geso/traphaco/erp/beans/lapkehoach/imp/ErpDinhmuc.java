package geso.traphaco.erp.beans.lapkehoach.imp;

import geso.traphaco.erp.beans.lapkehoach.IErpDinhmuc;

public class ErpDinhmuc implements IErpDinhmuc
{
	public ErpDinhmuc(){
		Id = "";
		Ten = "";
		donvitinh = "";
		soluong = 0;
		dongia = 0;
		thanhtien = 0;
		loai = "0";
	}
	private String Id;
	private String Ten;
	private String donvitinh;
	private double soluong;
	private double dongia;
	private double thanhtien;
	private String loai;
	@Override
	public String getId() {
		
		return this.Id;
	}
	@Override
	public String getTen() {
		
		return this.Ten;
	}
	@Override
	public String getDVT() {
		
		return this.donvitinh;
	}
	@Override
	public double getSoluong() {
		
		return this.soluong;
	}
	@Override
	public double getDongia() {
		
		return this.dongia;
	}
	@Override
	public double getThanhtien() {
		
		return this.thanhtien;
	}
	@Override
	public String getLoai() {
		
		return this.loai;
	}
	@Override
	public void setId(String value) {
		this.Id = value;
	}
	@Override
	public void setTen(String value) {
		this.Ten = value;
	}
	@Override
	public void setDVT(String value) {
		this.donvitinh = value;
	}
	@Override
	public void setSoluong(double value) {
		this.soluong = value;
	}
	@Override
	public void setDongia(double value) {
		this.dongia = value;
	}
	@Override
	public void setThanhtien(double value) {
		this.thanhtien = value;
	}
	@Override
	public void setLoai(String value) {
		this.loai = value;
	}	
}
