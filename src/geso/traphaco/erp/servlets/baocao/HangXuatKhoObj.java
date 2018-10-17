package geso.traphaco.erp.servlets.baocao;

public class HangXuatKhoObj {

	
	
	String id;
	String makhoxuat ;
	String tenkhoxuat ;
	String makhonhan ;
	String tenkhonhan ;
	String loaichungtu;
	String machungtu;
	String ngaychungtu;
	String loaisp ;
	String masp;
	String tensp;
	String donvi;
	double soluong;
	double dongia;
	double thanhtien;
	String ghichu;
	String lydo;
	String lenhsanxuat;
	

	public String getLenhsanxuat() {
		return lenhsanxuat;
	}

	public void setLenhsanxuat(String lenhsanxuat) {
		this.lenhsanxuat = lenhsanxuat;
	}

	public String getThanhphamsp() {
		return thanhphamsp;
	}

	public void setThanhphamsp(String thanhphamsp) {
		this.thanhphamsp = thanhphamsp;
	}

	String thanhphamsp;
	
	public HangXuatKhoObj() {
		super();
		this.id = "";
		this.makhoxuat ="";
		this.tenkhoxuat ="";
		this.makhonhan ="";
		this.tenkhonhan ="";
		this.loaichungtu="";
		this.machungtu="";
		this.ngaychungtu ="";
		this.loaisp ="";
		this.masp="";
		this.tensp="";
		this.donvi="";
		this.soluong =0;
		this.dongia=0;
		this.thanhtien=0;
		this.ghichu="";
		this.lydo="";
		this.thanhphamsp="";
		this.lenhsanxuat="";
	}

	public HangXuatKhoObj( String mkhoxuat, String tkhoxuat, String mkhonhan, String tkhonhan, String loaichungtu, String machungtu,String ngaychungtu,
			String lsp, String msp, String tsp, String dv, double sl,double dg, double t , String ghichu, String ldo, String tpsp, String lsx) {
		super();
		this.id = "";
		this.makhoxuat=mkhoxuat;
		this.tenkhoxuat=tkhoxuat;
		this.makhonhan=mkhonhan;
		this.tenkhonhan=tkhonhan;
		this.loaichungtu=loaichungtu;
		this.machungtu=machungtu;
		this.ngaychungtu=ngaychungtu;
		this.loaisp=lsp;
		this.masp=msp;
		this.tensp=tsp;
		this.donvi=dv;
		this.dongia=dg;
		this.soluong=sl;
		this.thanhtien=t;
		this.ghichu=ghichu;
		this.lydo=ghichu;
		this.thanhphamsp=tpsp;
		this.lenhsanxuat=lsx;
	}
	
	public String getMakhoxuat() {
		return makhoxuat;
	}

	public void setMakhoxuat(String makhoxuat) {
		this.makhoxuat = makhoxuat;
	}

	public String getTenkhoxuat() {
		return tenkhoxuat;
	}

	public void setTenkhoxuat(String tenkhoxuat) {
		this.tenkhoxuat = tenkhoxuat;
	}

	public String getMakhonhan() {
		return makhonhan;
	}

	public void setMakhonhan(String makhonhan) {
		this.makhonhan = makhonhan;
	}

	public String getTenkhonhan() {
		return tenkhonhan;
	}

	public void setTenkhonhan(String tenkhonhan) {
		this.tenkhonhan = tenkhonhan;
	}

	public String getLoaichungtu() {
		return loaichungtu;
	}

	public void setLoaichungtu(String loaichungtu) {
		this.loaichungtu = loaichungtu;
	}

	public String getMachungtu() {
		return machungtu;
	}

	public void setMachungtu(String machungtu) {
		this.machungtu = machungtu;
	}

	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
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

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	
	public String getLydo() {
		return lydo;
	}

	public void setLydo(String lydo) {
		this.lydo = lydo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
