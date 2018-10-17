package geso.traphaco.erp.servlets.bcthekho;

public class TheKhoObj {
	String machungtu ;
	String loaichungtu ;
	String ngaychungtu ;
	String ngaychot;
	
	String solo ;
	String ngayhethan ;
	String ngaynhapkho;
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
	double dongia ;
	
	double nhap;
	double xuat;
	double ton;
	
	public TheKhoObj() {
		super();
		this.machungtu = "";
		this.loaichungtu = "";
		this.ngaychungtu = "";
		this.ngaychot = "";
		this.solo = "";
		this.ngayhethan = "";
		this.ngaynhapkho = "";
		this.mame = "";
		this.mathung = "";
		this.vitri = "";
		this.maphieu = "";
		this.phieuDT = "";
		this.phieuEO = "";
		this.marq = "";
		this.hamam = "0";
		this.hamluong = "100";
		this.dongia = 0;
		this.nhap = 0;
		this.xuat = 0;
		this.ton = 0;
		this.nsx = "";
	}
	
	public TheKhoObj(String machungtu, String loaichungtu, String ngaychungtu, String ngaychot, String solo, String ngayhethan, String mame,
			String mathung, String vitri, String maphieu, String phieuDT, String phieuEO, String marq, String hamam, String hamluong, double dongia,
			double nhap, double xuat, double ton) {
		super();
		this.machungtu = machungtu;
		this.loaichungtu = loaichungtu;
		this.ngaychungtu = ngaychungtu;
		this.ngaychot = ngaychot;
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
		this.dongia = dongia;
		this.nhap = nhap;
		this.xuat = xuat;
		this.ton = ton;
		this.nsx = "";
	}

	public String getNsx() {
		return nsx;
	}

	public void setNsx(String nsx) {
		this.nsx = nsx;
	}

	public String getNgaynhapkho() {
		return ngaynhapkho;
	}

	public void setNgaynhapkho(String ngaynhapkho) {
		this.ngaynhapkho = ngaynhapkho;
	}

	public String getMachungtu() {
		return machungtu;
	}

	public void setMachungtu(String machungtu) {
		this.machungtu = machungtu;
	}

	public String getLoaichungtu() {
		return loaichungtu;
	}

	public void setLoaichungtu(String loaichungtu) {
		this.loaichungtu = loaichungtu;
	}

	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getNgaychot() {
		return ngaychot;
	}

	public void setNgaychot(String ngaychot) {
		this.ngaychot = ngaychot;
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

	public double getDongia() {
		return dongia;
	}

	public void setDongia(double dongia) {
		this.dongia = dongia;
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

	public double getTon() {
		return ton;
	}

	public void setTon(double ton) {
		this.ton = ton;
	}
}
