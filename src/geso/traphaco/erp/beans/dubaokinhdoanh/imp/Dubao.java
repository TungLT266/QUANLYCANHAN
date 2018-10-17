package geso.traphaco.erp.beans.dubaokinhdoanh.imp;

import geso.traphaco.erp.beans.dubaokinhdoanh.IDubao;

public class Dubao implements IDubao 
{

	String TENSP,MASP,IDSP,THANG,TONDAU,TANGTRUONG,DUKIENBAN,TONKHOANTOAN,TONCUOI,SOVOI,SANXUAT,NAM,SONGAYBAN,LOAISOVOI,TUAN1,TUAN2,TUAN3,TUAN4;
	
	public void Dubao()
	{
		this.TENSP="";
		this.IDSP="";
		this.TANGTRUONG="";
		this.DUKIENBAN="";
		this.TONKHOANTOAN="";
		this.TONCUOI="";
		this.SOVOI="";
		this.SANXUAT="";
		this.NAM="";
		this.THANG="";
		this.TUAN1="";
		this.TUAN2="";
		this.TUAN3="";
		this.TUAN4="";
		this.SONGAYBAN="";
		
	}
	
	public String getTuan1()
	{
		return this.TUAN1;
	}
	
	public void setTuan1(String tuan1)
	{
		this.TUAN1=tuan1;
	}
	
	public String getTuan2()
	{
		return this.TUAN2;
	}
	
	public void setTuan2(String tuan2)
	{
		this.TUAN2=tuan2;
	}
	
	public String getTuan3()
	{
		return this.TUAN3;
	}
	
	public void setTuan3(String tuan3)
	{
		this.TUAN3=tuan3;
	}
	
	public void setTuan4(String tuan4)
	{
		this.TUAN4=tuan4;
	}
	
	public String getTuan4()
	{
		return this.TUAN4;
	}
	
	public String getTensp() {
		
		return this.TENSP;
	}

	
	public void setTensp(String sanpham) {
		
		this.TENSP=sanpham;
	}

	
	public String getMasp() {
		
		return this.MASP;
	}

	
	public void setMasp(String sanpham) {
		
		this.MASP=sanpham;
	}

	
	public String getIDsp() {
		
		return this.IDSP;
	}

	
	public void setIDsp(String sanpham) {
		
		this.IDSP=sanpham;
	}

	
	public String getThang() {
		
		return this.THANG;
	}

	
	public void setThang(String thang) {
		
		this.THANG=thang;
	}

	
	public String getTondau() 
	{
		
		return this.TONDAU;
	}

	
	public void setTondau(String tondau) {
		
		this.TONDAU=tondau;
	}

	
	public String getTangtruong() {
		
		return this.TANGTRUONG;
	}

	
	public void setTangtruong(String tangtruong) {
		
		this.TANGTRUONG=tangtruong;
	}

	
	public String getDukienban() {
		
		return this.DUKIENBAN;
	}

	
	public void setDukienban(String dukienban) {
		
		this.DUKIENBAN=dukienban;
	}

	
	public String getTonkhoantoan() {
		
		return this.TONKHOANTOAN;
	}

	
	public void setTonkhoantoan(String tonkhoantoan) {
		
		this.TONKHOANTOAN=tonkhoantoan;
	}

	
	public String getToncuoi() {
		
		return this.TONCUOI;
	}

	
	public void setToncuoi(String toncuoi) {
		
		this.TONCUOI=toncuoi;
	}

	
	public String getSovoi() {
		
		return this.SOVOI;
	}

	
	public void setSovoi(String sovoi) {
		
		this.SOVOI=sovoi;
	}

	
	public String getSanxuat() {
		
		return this.SANXUAT;
	}

	
	public void setSanxuat(String sanxuat) {
		
		this.SANXUAT=sanxuat;
	}



	public String getNam() {

		return this.NAM;
	}



	public void setNam(String nam) {
		
		this.NAM=nam;
	}



	public String getSongayban() {

		return this.SONGAYBAN;
	}

	

	public void setSongayban(String songayban) {

		this.SONGAYBAN=songayban;
	}

	


}
