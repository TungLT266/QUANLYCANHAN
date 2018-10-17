package geso.traphaco.erp.beans.nhanhangkhac.imp;

import geso.traphaco.erp.beans.nhanhangkhac.ISpDetail;

public class SpDetail implements ISpDetail 
{
	String ma;
	String solo;
	String soluong;
	String ngaysx;
	String ngayhh;
	String KhuId;
	String NgayNhapkho="";
	double CpLuuKho=0;
	double CpCapDong=0;
	double Dongia=0;
	String mame;
	String mathung;
	String vitri;
	String maphieu;
	String phieudt;
	String phieueo;
	String marquette;
	String hamluong;
	String hamam;
	String vitriid;
	String nsxTen;
	String nsxId;
	
	String sothung;
	String soluongmax;

	public SpDetail()
	{
		this.nsxId = "";
		this.nsxTen = "";
		this.ma = "";
		this.solo = "";
		this.soluong = "";
		this.ngaysx = "";
		this.ngayhh = "";
		this.KhuId="";
		this.mame="";
		this.mathung="";
		this.vitri="";
		this.maphieu="";
		this.phieudt="";
		this.phieueo="";
		this.marquette="";
		this.hamluong="";
		this.hamam="";
		this.vitriid="";
		
		this.soluongmax = "";
	}
	
	public SpDetail(String ma, String solo, String soluong, String ngaysx, String ngayhh,String mame,String mathung,String vitri,String maphieu,String phieudt,String phieueo,String marquette,String hamluong,String hamam, String vitriid)
	{
		this.ma = ma;
		this.solo = solo;
		this.soluong = soluong;
		this.ngaysx = ngaysx;
		this.ngayhh = ngayhh;
		this.nsxId = "";
		this.nsxTen = "";
		this.mame=mame;
		this.mathung=mathung;
		this.vitri=vitri;
		this.maphieu=maphieu;
		this.phieudt=phieudt;
		this.phieueo=phieueo;
		this.marquette=marquette;
		this.hamluong=hamluong;
		this.hamam=hamam;
		this.vitriid=vitriid;
		
		this.soluongmax = "";
	}

	public SpDetail(String ma, String solo,String sothung, String soluong, String ngaysx, String ngayhh)
	{
		this.ma = ma;
		this.solo = solo;
		this.sothung=sothung;
		this.soluong = soluong;
		this.ngaysx = ngaysx;
		this.ngayhh = ngayhh;
		this.soluongmax = "";
	}
	
	public String getNsxTen() {
		return nsxTen;
	}

	public void setNsxTen(String nsxTen) {
		this.nsxTen = nsxTen;
	}

	public String getNsxId() {
		return nsxId;
	}

	public void setNsxId(String nsxId) {
		this.nsxId = nsxId;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getSolo() {
		return solo;
	}

	public void setSolo(String solo) {
		this.solo = solo;
	}

	public String getSoluong() {
		return soluong;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	public String getNgaysx() {
		return ngaysx;
	}

	public void setNgaysx(String ngaysx) {
		this.ngaysx = ngaysx;
	}

	public String getNgayhh() {
		return ngayhh;
	}

	public void setNgayhh(String ngayhh) {
		this.ngayhh = ngayhh;
	}

	public String getKhuId() {
		return KhuId;
	}

	public void setKhuId(String khuId) {
		KhuId = khuId;
	}

	public String getNgayNhapkho() {
		return NgayNhapkho;
	}

	public void setNgayNhapkho(String ngayNhapkho) {
		NgayNhapkho = ngayNhapkho;
	}

	public double getCpLuuKho() {
		return CpLuuKho;
	}

	public void setCpLuuKho(double cpLuuKho) {
		CpLuuKho = cpLuuKho;
	}

	public double getCpCapDong() {
		return CpCapDong;
	}

	public void setCpCapDong(double cpCapDong) {
		CpCapDong = cpCapDong;
	}

	public double getDongia() {
		return Dongia;
	}

	public void setDongia(double dongia) {
		Dongia = dongia;
	}

	public String getMame() {
		return mame;
	}

	public void setMame(String mame) {
		this.mame = mame;
	}

	public String getMathung() {
		return mathung;
	}

	public void setMathung(String mathung) {
		this.mathung = mathung;
	}

	public String getVitri() {
		return vitri;
	}

	public void setVitri(String vitri) {
		this.vitri = vitri;
	}

	public String getMaphieu() {
		return maphieu;
	}

	public void setMaphieu(String maphieu) {
		this.maphieu = maphieu;
	}

	public String getPhieudt() {
		return phieudt;
	}

	public void setPhieudt(String phieudt) {
		this.phieudt = phieudt;
	}

	public String getPhieueo() {
		return phieueo;
	}

	public void setPhieueo(String phieueo) {
		this.phieueo = phieueo;
	}

	public String getMarquette() {
		return marquette;
	}

	public void setMarquette(String marquette) {
		this.marquette = marquette;
	}

	public String getHamluong() {
		return hamluong;
	}

	public void setHamluong(String hamluong) {
		this.hamluong = hamluong;
	}

	public String getHamam() {
		return hamam;
	}

	public void setHamam(String hamam) {
		this.hamam = hamam;
	}

	public String getVitriid() {
		return vitriid;
	}

	public void setVitriid(String vitriid) {
		this.vitriid = vitriid;
	}
	
	
	public String getSothung() {
		return sothung;
	}

	public void setSothung(String sothung) {
		this.sothung = sothung;
	}
	
	@Override
	public String getSoluongmax() {
		return this.soluongmax;
	}

	@Override
	public void setSoluongmax(String soluongmax) {
		this.soluongmax = soluongmax;
	}	

}
