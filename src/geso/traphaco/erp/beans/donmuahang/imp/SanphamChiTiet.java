package geso.traphaco.erp.beans.donmuahang.imp;


import geso.traphaco.erp.beans.donmuahang.ISanphamChiTiet;

public class SanphamChiTiet implements ISanphamChiTiet
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String sanpham_fk ;
	String solo ;
	String soluong ;
	String ngayhethan;
	String dvdl_fk ;
	String ngaynhapkho ;
	String mame;
	String mathung ;
	String marq ;
	String hamluong ;
	String hamam;
	String maphieu ;
	String bin_fk ;
	String phieudt;
	String phieueo;
	String khott_sp_ct_fk ;
	String is_khongthue ;
	String tienhang;
	String tienvat ;
	String tienhangsauvat ;
	public SanphamChiTiet()
	{
		this.sanpham_fk="";
		this.solo="";
		this.soluong="";
		this.ngayhethan="";
		this.dvdl_fk="";
		this.ngaynhapkho="";
		this.mame="";
		this.mathung="";
		this.marq="";
		this.hamluong="100";
		this.hamam="0";
		this.maphieu="";
		this.bin_fk="";
		this.phieudt="";
		this.phieueo="";
		this.khott_sp_ct_fk="";
		this.is_khongthue="";
		this.tienhang="";
		this.tienvat="";
		this.tienhangsauvat="";
	}

	public String getSanpham_fk() {
		return sanpham_fk;
	}
	public void setSanpham_fk(String sanpham_fk) {
		this.sanpham_fk = sanpham_fk;
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
	public String getNgayhethan() {
		return ngayhethan;
	}
	public void setNgayhethan(String ngayhethan) {
		this.ngayhethan = ngayhethan;
	}
	public String getDvdl_fk() {
		return dvdl_fk;
	}
	public void setDvdl_fk(String dvdl_fk) {
		this.dvdl_fk = dvdl_fk;
	}
	public String getNgaynhapkho() {
		return ngaynhapkho;
	}
	public void setNgaynhapkho(String ngaynhapkho) {
		this.ngaynhapkho = ngaynhapkho;
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
	public String getMarq() {
		return marq;
	}
	public void setMarq(String marq) {
		this.marq = marq;
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
	public String getMaphieu() {
		return maphieu;
	}
	public void setMaphieu(String maphieu) {
		this.maphieu = maphieu;
	}
	public String getBin_fk() {
		return bin_fk;
	}
	public void setBin_fk(String bin_fk) {
		this.bin_fk = bin_fk;
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
	public String getKhott_sp_ct_fk() {
		return khott_sp_ct_fk;
	}
	public void setKhott_sp_ct_fk(String khott_sp_ct_fk) {
		this.khott_sp_ct_fk = khott_sp_ct_fk;
	}
	public String getIs_khongthue() {
		return is_khongthue;
	}
	public void setIs_khongthue(String is_khongthue) {
		this.is_khongthue = is_khongthue;
	}
	public String getTienhang() {
		return tienhang;
	}
	public void setTienhang(String tienhang) {
		this.tienhang = tienhang;
	}
	public String getTienvat() {
		return tienvat;
	}
	public void setTienvat(String tienvat) {
		this.tienvat = tienvat;
	}
	public String getTienhangsauvat() {
		return tienhangsauvat;
	}
	public void setTienhangsauvat(String tienhangsauvat) {
		this.tienhangsauvat = tienhangsauvat;
	}

	
}
