package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import geso.traphaco.erp.beans.phieuthanhtoan.IHoadon;

public class Hoadon implements IHoadon
{
	private static final long serialVersionUID = -9217977546733610214L;
	String PK_SEQ;
	String id;
	String maHD;
	String mausoHD;
	String kyhieu;
	String sohd;
	String ngayhd;
	String tenncc;
	String mst;
	String tienhang;
	String thuesuat;
	String thue;
	String cong;
	String ghichu;
	
	String nccHDId;
	//pk_seq cua bang Erp_muahang_Sp
	String muahang_Sp_Fk;
	String phongbanId;
	String kenhbhId;
	String kenhbhTen;
	String PhongBanId;
	String PhongBanTen;
	
	String SanPhamId;
	String DiaBanId;
	String BenhVienId;
	private String tienThanhToan;
	private String diaChiNCC;
	
	/* 31-08-2012 */
	public Hoadon()
	{
		  PK_SEQ="";
		  id="";
		  maHD="";
		  mausoHD="";
		  kyhieu="";
		  sohd="";
		  ngayhd="";
		  tenncc="";
		  mst="";
		  tienhang="";
		  thuesuat="";
		  thue="";
		  cong="";
		  this.tienThanhToan = "0";
		  ghichu="";
		//pk_seq cua bang Erp_muahang_Sp
		  muahang_Sp_Fk="";
		  this.phongbanId = "";
		  this.kenhbhId = "";
		  this.nccHDId = "";
		  this.kenhbhTen = "";
		  this.PhongBanId = "";
		  this.PhongBanTen = "";
		  this.SanPhamId = "";
		  this.DiaBanId = "";
		  this.BenhVienId = "";
		  this.diaChiNCC = "";
	}
 
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPK_SEQ() {
		return null;
	}

	public void setPK_SEQ(String pk_seq) {
	}
	
	public void setMahoadon(String str) {
		this.maHD=str;
	}
	
	public String getMahoadon() {
		return this.maHD;
	}

	public void setMauhoadon(String str) {
		this.mausoHD=str;
	}

	public String getMauhoadon() {
		return this.mausoHD;
	}

	public void setSohoadon(String str) {
		this.sohd=str;
	}

	public String getSohoadon() {
		return this.sohd;
	}

	public void setNgayhoadon(String str) {
		this.ngayhd=str;
	}
	
	public String getNgayhoadon() {
		return this.ngayhd;
	}

	public void setTenNCC(String str) {
		this.tenncc=str;
	}
	
	public String getTenNCC() {
		return this.tenncc;
	}

	public void setMasothue(String str) {
		this.mst=str;
	}
	
	public String getMasothue() {
		return this.mst;
	}

	public void setKyhieu(String str) {
		this.kyhieu=str;
	}
	
	public String getKyhieu() {
		return this.kyhieu;
	}
	
	public void setTienhang(String str) {
		this.tienhang=str;
	}

	public String getTienhang() {
		return this.tienhang;
	}
	
	public void setThuesuat(String str) {
		this.thuesuat=str;
	}
	
	public String getThuesuat() {
		return this.thuesuat;
	}

	public void setTienthue(String str) {
		this.thue=str;
	}
	
	public String getTienthue() {
		return this.thue;
	}

	public void setCong(String str) {
		this.cong=str;
	}

	public String getCong() {
		return this.cong;
	}
	
	public void setGhichu(String str) {
		this.ghichu=str;
	}

	public String getGhichu() {
		return this.ghichu;
	}
	
	public String getMuahang_Sp_fk() {
		return muahang_Sp_Fk;
	}
	
	public void setMuahang_Sp_fk(String Muahang_Sp_fk) {
		this.muahang_Sp_Fk=Muahang_Sp_fk;
	}

	public String getNccHDId() 
	{
		return this.nccHDId;
	}

	public void setNccHDId(String nccHDId) 
	{
		this.nccHDId = nccHDId;
	}

	public String getKenhBhId() {
		return this.kenhbhId;
	}
	
	public void setKenhBhId(String KenhBhId) {
		this.kenhbhId = KenhBhId;
	}
	
	public String getPhongBanId() {
		return this.phongbanId;
	}

	public void setPhongBanId(String PhongBanId) {
		this.phongbanId = PhongBanId;
	}

	public String getKenhBhTen() {
		return this.kenhbhTen;
	}
	
	public void setKenhBhTen(String KenhBhTen) {
		this.kenhbhTen = KenhBhTen;
	}

	public String getPhongBanTen() {
		return this.PhongBanTen;
	}
	
	public void setPhongBanTen(String PhongBanTen) {
		this.PhongBanTen = PhongBanTen;
	}

	public String getSanPhamId() {
		return this.SanPhamId;
	}
	
	public void setSanPhamId(String SanPhamId) {
		this.SanPhamId = SanPhamId;
	}

	public String getDiaBanId() {
		return this.DiaBanId;
	}

	public void setDiaBanId(String DiaBanId) {
		this.DiaBanId = DiaBanId;
	}
	
	public String getBenhVienId() {
		return this.BenhVienId;
	}
	
	public void setBenhVienId(String BenhvienId) {
		this.BenhVienId = BenhvienId;
	}

	public void setTienThanhToan(String tienThanhToan) {
		this.tienThanhToan = tienThanhToan;
	}

	public String getTienThanhToan() {
		return tienThanhToan;
	}

	public void setDiaChiNCC(String diaChiNCC) {
		this.diaChiNCC = diaChiNCC;
	}

	public String getDiaChiNCC() {
		return diaChiNCC;
	}
}