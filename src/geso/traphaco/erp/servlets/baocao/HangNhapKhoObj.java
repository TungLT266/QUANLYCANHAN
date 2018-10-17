package geso.traphaco.erp.servlets.baocao;

public class HangNhapKhoObj {
	
	String makho ;
	String tenkho ;
	String loaisp ;
	String masp;
	String tensp;
	String donvi;
	double soluong;
	double dongia;
	double thanhtien;
	
	public HangNhapKhoObj() {
		super();
		this.makho ="";
		this.tenkho ="";
		this.loaisp ="";
		this.masp="";
		this.tensp="";
		this.donvi="";
		this.soluong =0;
		this.dongia=0;
		this.thanhtien=0;
	}

	public HangNhapKhoObj( String mkho, String tkho, String lsp, String msp, String tsp, String dv, double sl,double dg, double t) {
		super();
		this.makho=mkho;
		this.tenkho=tkho;
		this.loaisp=lsp;
		this.masp=msp;
		this.tensp=tsp;
		this.donvi=dv;
		this.dongia=dg;
		this.soluong=sl;
		this.thanhtien=t;
	}

	public String getMakho() {
		return makho;
	}

	public void setMakho(String makho) {
		this.makho = makho;
	}

	public String getTenkho() {
		return tenkho;
	}

	public void setTenkho(String tenkho) {
		this.tenkho = tenkho;
	}

	public String getLoaisp() {
		return loaisp;
	}

	public void setLoaisp(String loaisp) {
		this.loaisp = loaisp;
	}

	public String getMasp() {
		return masp;
	}

	public void setMasp(String masp) {
		this.masp = masp;
	}

	public String getTensp() {
		return tensp;
	}

	public void setTensp(String tensp) {
		this.tensp = tensp;
	}

	public String getDonvi() {
		return donvi;
	}

	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	public double getSoluong() {
		return soluong;
	}

	public void setSoluong(double soluong) {
		this.soluong = soluong;
	}

	public double getDongia() {
		return dongia;
	}

	public void setDongia(double dongia) {
		this.dongia = dongia;
	}

	public double getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(double thanhtien) {
		this.thanhtien = thanhtien;
	}
	
	
}
