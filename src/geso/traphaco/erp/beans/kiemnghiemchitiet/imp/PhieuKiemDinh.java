package geso.traphaco.erp.beans.kiemnghiemchitiet.imp;

import java.util.ArrayList;
import java.util.List;


public class PhieuKiemDinh {
	
	
	private String sott;
	private String stt;
	private String ngayDanhGia;
	private String ngayDanhGiaLai;
	private String ngayThucTe;
	private String ghiChu;
	
	private List<String> thamSoMau;
	private String tenThamSo;
	private String donViTinh;
	private String kyHieu;
	private boolean isCongThuc;
	
	private String hoaChat;
	private String soLuongDinhMuc;
	private String soLuongThucTe;
	private String maSo;
	private String cachPha;
	private boolean isDanhGia;
	private boolean isGroup;
	private String congThuc;
	private boolean isMin;
	private boolean isMax;
	private boolean isAvg;
	private boolean isSum;
	private int _loai;
	
	public PhieuKiemDinh(String sott, String stt, String ngayDanhGia, String ngayDanhGiaLai, String ghiChu) {
		super();
		this.sott = sott;
		this.stt = stt;
		this.ngayDanhGia = ngayDanhGia;
		this.ngayDanhGiaLai = ngayDanhGiaLai;
	}
	
	public int __loai(){
		return _loai;
	}
	
	public void setLoai(int l){
		this._loai = l;
	}
	
	public PhieuKiemDinh() {
		this.sott = "";
		this.stt = "";
		this.ngayDanhGia = "";
		this.ngayDanhGiaLai = "";
		this.tenThamSo = "";
		this.donViTinh = "";
		this.kyHieu = "";
		this.isCongThuc = false;
		this.thamSoMau = new ArrayList<String>();
		this.hoaChat = "";
		this.soLuongDinhMuc = "";
		this.soLuongThucTe = "";
		this.maSo = "";
		this.cachPha = "";
		this.isDanhGia = false;
		this.isGroup = false;
		this._loai = 0;
		this.congThuc = "";
		this.isMin = false;
		this.isMax = false;
		this.isAvg = false;
		this.isSum = false;
	}

	

	public boolean isMin() {
		return isMin;
	}

	public void setMin(boolean isMin) {
		this.isMin = isMin;
	}

	public boolean isMax() {
		return isMax;
	}

	public void setMax(boolean isMax) {
		this.isMax = isMax;
	}

	public boolean isAvg() {
		return isAvg;
	}

	public void setAvg(boolean isAvg) {
		this.isAvg = isAvg;
	}

	public boolean isSum() {
		return isSum;
	}

	public void setSum(boolean isSum) {
		this.isSum = isSum;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public String getCongThuc() {
		return congThuc;
	}

	public void _CongThuc(String congThuc) {
		this.congThuc = congThuc;
	}

	public int get_loai() {
		return _loai;
	}

	public void set_loai(int _loai) {
		this._loai = _loai;
	}

	public boolean isDanhGia() {
		return isDanhGia;
	}

	public void setDanhGia(boolean isDanhGia) {
		this.isDanhGia = isDanhGia;
	}

	public String getKyHieu() {
		return kyHieu;
	}



	public void setKyHieu(String kyHieu) {
		this.kyHieu = kyHieu;
	}



	public List<String> getThamSoMau() {
		return thamSoMau;
	}



	public void setThamSoMau(List<String> thamSoMau) {
		this.thamSoMau = thamSoMau;
	}



	public String getTenThamSo() {
		return tenThamSo;
	}



	public void setTenThamSo(String tenThamSo) {
		this.tenThamSo = tenThamSo;
	}



	public String getDonViTinh() {
		return donViTinh;
	}



	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}



	public boolean isCongThuc() {
		return isCongThuc;
	}



	public void setCongThuc(boolean isCongThuc) {
		this.isCongThuc = isCongThuc;
	}



	public String getSott() {
		return this.sott;
	}



	public void setSott(String sott) {
		this.sott = sott;
	}



	public String getNgayDanhGia() {
		return ngayDanhGia;
	}



	public void setNgayDanhGia(String ngayDanhGia) {
		this.ngayDanhGia = ngayDanhGia;
	}



	public String getNgayDanhGiaLai() {
		return ngayDanhGiaLai;
	}



	public void setNgayDanhGiaLai(String ngayDanhGiaLai) {
		this.ngayDanhGiaLai = ngayDanhGiaLai;
	}



	public String getGhiChu() {
		return this.ghiChu;
	}



	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}



	public String getNgayThucTe() {
		return this.ngayThucTe;
	}



	public void setNgayThucTe(String ngayThucTe) {
		this.ngayThucTe = ngayThucTe;
	}



	public String getHoaChat() {
		return this.hoaChat;
	}



	public void setHoaChat(String hoaChat) {
		this.hoaChat = hoaChat;
	}



	public String getSoLuongDinhMuc() {
		return this.soLuongDinhMuc;
	}



	public void setSoLuongDinhMuc(String soLuongDinhMuc) {
		this.soLuongDinhMuc = soLuongDinhMuc;
	}



	public String getSoLuongThucTe() {
		return this.soLuongThucTe;
	}



	public void setSoLuongThucTe(String soLuongThucTe) {
		this.soLuongThucTe = soLuongThucTe;
	}



	public String getMaSo() {
		return this.maSo;
	}



	public void setMaSo(String maSo) {
		this.maSo = maSo;
	}



	public String getCachPha() {
		return this.cachPha;
	}



	public void setCachPha(String cachPha) {
		this.cachPha = cachPha;
	}
 

	public String getStt() {
		return stt;
	}
 
	public void setStt(String stt) {
		this.stt = stt;
	}
	
	
	
}
