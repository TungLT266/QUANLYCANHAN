package geso.traphaco.erp.beans.bcthekho.imp;

import geso.traphaco.erp.beans.bcthekho.ISanpham;

public class Sanpham implements  ISanpham
{
	String ngaychungtu;
	String ngaynhapxuat;

	String sochungtu_nhap;
	String sochungtu_xuat;
	String diengiai;
	double SLnhap;
	double SLxuat;

	
	public Sanpham()
	{
		this.ngaychungtu="";
		this.ngaynhapxuat="";
	
		this.sochungtu_nhap="";
		this.sochungtu_xuat="";
		this.SLnhap=0;
		this.SLxuat=0;
	}
	
	
	public String get_ngaychungtu(){
		return this.ngaychungtu;
	}
	public void set_ngaychungtu(String ngaychungtu){
		this.ngaychungtu = ngaychungtu;
	}
	
	public String get_sochungtu_nhap(){
		return this.sochungtu_nhap;
	}
	public void set_sochungtu_nhap(String sochungtu_nhap){
		this.sochungtu_nhap = sochungtu_nhap;
	}
	
	
	public String get_sochungtu_xuat(){
		 return this.sochungtu_xuat;
	}
	public void set_sochungtu_xuat(String sochungtu_xuat){
		this.sochungtu_xuat = sochungtu_xuat;
	}
	
	
	
	public String get_ngaynhapxuat(){
		return this.ngaynhapxuat;
	}
	public void set_ngaynhapxuat(String ngaynhapxuat){
		this.ngaynhapxuat = ngaynhapxuat;
	}
	
	public String get_diengiai(){
		return this.diengiai;
	}
	public void set_diengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	
	public double get_SLnhap(){
		return this.SLnhap;
	}
	public void set_SLnhap(double SLnhap){
		this.SLnhap = SLnhap;
	}
	
	public double get_SLxuat(){
		return this.SLxuat;
	}
	public void set_SLxuat(double SLxuat){
		this.SLxuat = SLxuat;
	}




	

}
