package geso.traphaco.erp.servlets.baocao;

public class XuatNhapTonObj {
	String kho ;
	String ma ;
	String ten ;
	String donvi;
	
	String solo ;
	String ngayhethan ;
	
	String mame ;
	String mathung ;
	String vitri ;
	
	String maphieu ;
	String phieuDT ;
	String phieuEO ;
	
	String marq ;
	String hamam ;
	String hamluong ;
	String nsx;
	double dauky;
	double nhap;
	double xuat;
	double toncuoi;
	
	public XuatNhapTonObj() {
		super();
		this.nsx = "";
		this.kho = "";
		this.ma = "";
		this.ten = "";
		this.donvi = "";
		this.solo = "";
		this.ngayhethan = "";
		this.mame = "";
		this.mathung = "";
		this.vitri = "";
		this.maphieu = "";
		this.phieuDT = "";
		this.phieuEO = "";
		this.marq = "";
		this.hamam = "0";
		this.hamluong = "100";
		dauky = 0;
		nhap = 0;
		xuat = 0;
		toncuoi = 0;
	}
	
	public XuatNhapTonObj(String kho, String ma, String ten, String donvi, String solo, String ngayhethan, String mame, String mathung, String vitri,
			String maphieu, String phieuDT, String phieuEO, String marq, String hamam, String hamluong) {
		super();
		this.kho = kho;
		this.ma = ma;
		this.ten = ten;
		this.donvi = donvi;
		this.solo = solo;
		this.ngayhethan = ngayhethan;
		this.mame = mame;
		this.mathung = mathung;
		this.vitri = vitri;
		this.maphieu = maphieu;
		this.phieuDT = phieuDT;
		this.phieuEO = phieuEO;
		this.marq = marq;
		this.hamam = hamam;
		this.hamluong = hamluong;
		this.nsx = "";
	}

	
	public String getNsx() {
		return nsx;
	}

	public void setNsx(String nsx) {
		this.nsx = nsx;
	}

	public double getDauky() {
		return dauky;
	}

	public void setDauky(double dauky) {
		this.dauky = dauky;
	}

	public double getNhap() {
		return nhap;
	}

	public void setNhap(double nhap) {
		this.nhap = nhap;
	}

	public double getXuat() {
		return xuat;
	}

	public void setXuat(double xuat) {
		this.xuat = xuat;
	}

	public double getToncuoi() {
		return toncuoi;
	}

	public void setToncuoi(double toncuoi) {
		this.toncuoi = toncuoi;
	}

	public String getKho() {
		return kho;
	}

	public void setKho(String kho) {
		this.kho = kho;
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

	public String getDonvi() {
		return donvi;
	}

	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	public String getSolo() {
		return solo;
	}

	public void setSolo(String solo) {
		this.solo = solo;
	}

	public String getNgayhethan() {
		return ngayhethan;
	}

	public void setNgayhethan(String ngayhethan) {
		this.ngayhethan = ngayhethan;
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

	public String getPhieuDT() {
		return phieuDT;
	}

	public void setPhieuDT(String phieuDT) {
		this.phieuDT = phieuDT;
	}

	public String getPhieuEO() {
		return phieuEO;
	}

	public void setPhieuEO(String phieuEO) {
		this.phieuEO = phieuEO;
	}

	public String getMarq() {
		return marq;
	}

	public void setMarq(String marq) {
		this.marq = marq;
	}

	public String getHamam() {
		return hamam;
	}

	public void setHamam(String hamam) {
		this.hamam = hamam;
	}

	public String getHamluong() {
		return hamluong;
	}

	public void setHamluong(String hamluong) {
		this.hamluong = hamluong;
	}
}
