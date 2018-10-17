package geso.traphaco.erp.beans.yclaymaukiemnghiem.imp;

public class PhieuKiemDinh {
	private String Id;
	private String ngaychungtu;
	private String loaikiemdinh;
	private String quytrinhlaymau;
	private String trangthai;
	private String sophieukiemdinh;

	//----------
	private String maSp;
	private String tenSp;
	private String sanPhamId;
	private String dvt;
	private String loHang;
	private String ngaySX;
	private String ngayHetHan;
	private String maThung;
	private String maMe;
	private String soLuongPhieuNop;
	private String soLuongLayMau;
	private String soLuongMauLuu;
	private String soLuongTheoDoiDoOnDinh;
	private String soLuongConLai;
	private String thoiGianHuyMai;
	private int sott;
	
	private String vitri,maphieu,phieudt,phieueo,marq,nhasanxuat,hamluong,hamam,nhasx_Id;
	private String soLuongPhieuNop_pop;
	
	public PhieuKiemDinh(String maSp, String tenSp, String sanPhamId,
			String dvt, String loHang, String ngaySX, String ngayHetHan,
			String maThung, String maMe, String soLuongPhieuNop,
			String soLuongLayMau, String soLuongMauLuu,
			String soLuongTheoDoiDoOnDinh, String soLuongConLai,
			String thoiGianHuyMai) {
		super();
		this.maSp = maSp;
		this.tenSp = tenSp;
		this.sanPhamId = sanPhamId;
		this.dvt = dvt;
		this.loHang = loHang;
		this.ngaySX = ngaySX;
		this.ngayHetHan = ngayHetHan;
		this.maThung = maThung;
		this.maMe = maMe;
		this.soLuongPhieuNop = soLuongPhieuNop;
		this.soLuongLayMau = soLuongLayMau;
		this.soLuongMauLuu = soLuongMauLuu;
		this.soLuongTheoDoiDoOnDinh = soLuongTheoDoiDoOnDinh;
		this.soLuongConLai = soLuongConLai;
		this.thoiGianHuyMai = thoiGianHuyMai;
		this.Id="";
		this.loaikiemdinh="";
		this.ngaychungtu="";
		this.quytrinhlaymau="";
		this.ngaychungtu="";
		
		this.vitri=""; 
		this.maphieu=""; 
		this.phieudt=""; 
		this.phieueo=""; 
		this.marq=""; 
		this.nhasanxuat=""; 
		this.hamluong=""; 
		this.hamam="";
		this.nhasx_Id="";
		
		this.soLuongPhieuNop_pop="";
	}
	
	
	
	public PhieuKiemDinh() {
		this.maSp = "";
		this.tenSp = "";
		this.sanPhamId = "";
		this.dvt = "";
		this.loHang = "";
		this.ngaySX = "";
		this.ngayHetHan = "";
		this.maThung = "";
		this.maMe = "";
		this.soLuongPhieuNop = "";
		this.soLuongLayMau = "";
		this.soLuongMauLuu = "";
		this.soLuongTheoDoiDoOnDinh = "";
		this.soLuongConLai = "";
		this.thoiGianHuyMai = "";
		this.Id="";
		this.loaikiemdinh="";
		this.ngaychungtu="";
		this.quytrinhlaymau="";
		this.ngaychungtu="";
		
		this.vitri=""; 
		this.maphieu=""; 
		this.phieudt=""; 
		this.phieueo=""; 
		this.marq=""; 
		this.nhasanxuat=""; 
		this.hamluong=""; 
		this.hamam="";
		this.nhasx_Id="";
		this.soLuongPhieuNop_pop="";
	}

	

	public int getSott() {
		return sott;
	}

	public void setSott(int sott) {
		this.sott = sott;
	}

	public String getMaSp() {
		return maSp;
	}
	public void setMaSp(String maSp) {
		this.maSp = maSp;
	}
	public String getTenSp() {
		return tenSp;
	}
	public void setTenSp(String tenSp) {
		this.tenSp = tenSp;
	}
	public String getSanPhamId() {
		return sanPhamId;
	}
	public void setSanPhamId(String sanPhamId) {
		this.sanPhamId = sanPhamId;
	}
	public String getDvt() {
		return dvt;
	}
	public void setDvt(String dvt) {
		this.dvt = dvt;
	}
	public String getLoHang() {
		return loHang;
	}
	public void setLoHang(String loHang) {
		this.loHang = loHang;
	}
	public String getNgaySX() {
		return ngaySX;
	}
	public void setNgaySX(String ngaySX) {
		this.ngaySX = ngaySX;
	}
	public String getNgayHetHan() {
		return ngayHetHan;
	}
	public void setNgayHetHan(String ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}
	public String getMaThung() {
		return maThung;
	}
	public void setMaThung(String maThung) {
		this.maThung = maThung;
	}
	public String getMaMe() {
		return maMe;
	}
	public void setMaMe(String maMe) {
		this.maMe = maMe;
	}
	public String getSoLuongPhieuNop() {
		return soLuongPhieuNop;
	}
	public void setSoLuongPhieuNop(String soLuongPhieuNop) {
		this.soLuongPhieuNop = soLuongPhieuNop;
	}
	public String getSoLuongLayMau() {
		if(soLuongLayMau == null) return "";
		return soLuongLayMau;
	}
	public void setSoLuongLayMau(String soLuongLayMau) {
		this.soLuongLayMau = soLuongLayMau;
	}
	public String getSoLuongMauLuu() {
		if(soLuongMauLuu == null) return "";
		return soLuongMauLuu;
	}
	public void setSoLuongMauLuu(String soLuongMauLuu) {
		this.soLuongMauLuu = soLuongMauLuu;
	}
	public String getSoLuongTheoDoiDoOnDinh() {
		if(soLuongTheoDoiDoOnDinh == null) return "";
		return soLuongTheoDoiDoOnDinh;
	}
	public void setSoLuongTheoDoiDoOnDinh(String soLuongTheoDoiDoOnDinh) {
		this.soLuongTheoDoiDoOnDinh = soLuongTheoDoiDoOnDinh;
	}
	public String getSoLuongConLai() {
		if(soLuongConLai == null) return "";
		return soLuongConLai;
	}
	public void setSoLuongConLai(String soLuongConLai) {
		this.soLuongConLai = soLuongConLai;
	}
	public String getThoiGianHuyMai() {
		return thoiGianHuyMai;
	}
	public void setThoiGianHuyMai(String thoiGianHuyMai) {
		this.thoiGianHuyMai = thoiGianHuyMai;
	}
	

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getLoaikiemdinh() {
		return loaikiemdinh;
	}

	public void setLoaikiemdinh(String loaikiemdinh) {
		this.loaikiemdinh = loaikiemdinh;
	}

	public String getQuytrinhlaymau() {
		return quytrinhlaymau;
	}

	public void setQuytrinhlaymau(String quytrinhlaymau) {
		this.quytrinhlaymau = quytrinhlaymau;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	
	public String getSophieukiemdinh() {
		return sophieukiemdinh;
	}

	public void setSophieukiemdinh(String sophieukiemdinh) {
		this.sophieukiemdinh = sophieukiemdinh;
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

	public String getMarq() {
		return marq;
	}

	public void setMarq(String marq) {
		this.marq = marq;
	}

	public String getNhasanxuat() {
		return nhasanxuat;
	}

	public void setNhasanxuat(String nhasanxuat) {
		this.nhasanxuat = nhasanxuat;
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
	
	public String getNhasx_Id() {
		return nhasx_Id;
	}

	public void setNhasx_Id(String nhasx_Id) {
		this.nhasx_Id = nhasx_Id;
	}


	public String getSoLuongPhieuNop_pop() {
		return soLuongPhieuNop_pop;
	}

	public void setSoLuongPhieuNop_pop(String soLuongPhieuNop_pop) {
		this.soLuongPhieuNop_pop = soLuongPhieuNop_pop;
	}




}
