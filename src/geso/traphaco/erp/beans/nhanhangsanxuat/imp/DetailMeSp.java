package geso.traphaco.erp.beans.nhanhangsanxuat.imp;

public class DetailMeSp {
	private String maThung;
	private String KhuVuc;
	private String soLuong;
	private String soLo;
	private String me;
	private String ngayHetHan;
	private String ngaySanXuat;
	
	public DetailMeSp() {
		super();
		this.maThung = "";
		KhuVuc = "";
		this.soLuong = "";
		this.soLo = "";
		this.me = "";
		this.ngaySanXuat = "";
		this.ngayHetHan = "";
	}
	
	public DetailMeSp(String maThung, String khuVuc, String soLuong, String soLo, String me, String ngaySanXuat, String ngayHetHan) {
		super();
		this.maThung = maThung;
		KhuVuc = khuVuc;
		this.soLuong = soLuong;
		this.soLo = soLo;
		this.me = me;
		this.ngaySanXuat = ngayHetHan;
		this.ngayHetHan = ngaySanXuat;
	}

	public String getNgayHetHan() {
		return ngayHetHan;
	}

	public void setNgayHetHan(String ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}

	public String getNgaySanXuat() {
		return ngaySanXuat;
	}

	public void setNgaySanXuat(String ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}

	public String getMaThung() {
		return maThung;
	}

	public void setMaThung(String maThung) {
		this.maThung = maThung;
	}

	public String getKhuVuc() {
		return KhuVuc;
	}

	public void setKhuVuc(String khuVuc) {
		KhuVuc = khuVuc;
	}
	
	public String getSoLo() {
		return soLo;
	}

	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public String getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(String soLuong) {
		this.soLuong = soLuong;
	}
}
