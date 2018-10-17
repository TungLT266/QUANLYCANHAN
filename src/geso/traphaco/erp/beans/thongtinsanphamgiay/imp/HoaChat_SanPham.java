package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.thongtinsanphamgiay.IHoaChat_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinNCC;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;

public class HoaChat_SanPham implements IHoaChat_SanPham{
	private String PK_SEQSanPhamDuocKiemDinh, dvtSanPhamDuocKiemDinh;
	private String PK_SEQHoaChatKiemDinh, maHoaChatKiemDinh, tenHoaChatKiemDinh, dvtHoaChatKiemDinh;
	private int soLuongChuan, soLuongChatKiemDinh;
	private IThongtinNCC nhaCungCap;
	
	
	public HoaChat_SanPham() {
		super();
	}
	public HoaChat_SanPham(String pK_SEQSanPhamDuocKiemDinh,
			String pK_SEQHoaChatKiemDinh, int soLuongChuan,
			int soLuongChatKiemDinh) {
		super();
		PK_SEQSanPhamDuocKiemDinh = pK_SEQSanPhamDuocKiemDinh;
		PK_SEQHoaChatKiemDinh = pK_SEQHoaChatKiemDinh;
		this.soLuongChuan = soLuongChuan;
		this.soLuongChatKiemDinh = soLuongChatKiemDinh;
	}
	public HoaChat_SanPham(String pK_SEQSanPhamDuocKiemDinh,
			String dvtSanPhamDuocKiemDinh, String pK_SEQHoaChatKiemDinh,
			String maHoaChatKiemDinh, String tenHoaChatKiemDinh,
			String dvtHoaChatKiemDinh, int soLuongChuan,
			int soLuongChatKiemDinh) {
		super();
		PK_SEQSanPhamDuocKiemDinh = pK_SEQSanPhamDuocKiemDinh;
		this.dvtSanPhamDuocKiemDinh = dvtSanPhamDuocKiemDinh;
		PK_SEQHoaChatKiemDinh = pK_SEQHoaChatKiemDinh;
		this.maHoaChatKiemDinh = maHoaChatKiemDinh;
		this.tenHoaChatKiemDinh = tenHoaChatKiemDinh;
		this.dvtHoaChatKiemDinh = dvtHoaChatKiemDinh;
		this.soLuongChuan = soLuongChuan;
		this.soLuongChatKiemDinh = soLuongChatKiemDinh;
	}
	@Override
	public String getPK_SEQSanPhamDuocKiemDinh() {
		return PK_SEQSanPhamDuocKiemDinh;
	}
	@Override
	public void setPK_SEQSanPhamDuocKiemDinh(String pK_SEQSanPhamDuocKiemDinh) {
		PK_SEQSanPhamDuocKiemDinh = pK_SEQSanPhamDuocKiemDinh;
	}
	@Override
	public String getDvtSanPhamDuocKiemDinh() {
		return dvtSanPhamDuocKiemDinh;
	}
	@Override
	public void setDvtSanPhamDuocKiemDinh(String dvtSanPhamDuocKiemDinh) {
		this.dvtSanPhamDuocKiemDinh = dvtSanPhamDuocKiemDinh;
	}
	@Override
	public String getTenHoaChatKiemDinh() {
		return tenHoaChatKiemDinh;
	}
	@Override
	public void setTenHoaChatKiemDinh(String tenHoaChatKiemDinh) {
		this.tenHoaChatKiemDinh = tenHoaChatKiemDinh;
	}
	@Override
	public String getPK_SEQHoaChatKiemDinh() {
		return PK_SEQHoaChatKiemDinh;
	}
	@Override
	public void setPK_SEQHoaChatKiemDinh(String pK_SEQHoaChatKiemDinh) {
		PK_SEQHoaChatKiemDinh = pK_SEQHoaChatKiemDinh;
	}
	@Override
	public String getDvtHoaChatKiemDinh() {
		return dvtHoaChatKiemDinh;
	}
	@Override
	public void setDvtHoaChatKiemDinh(String dvtHoaChatKiemDinh) {
		this.dvtHoaChatKiemDinh = dvtHoaChatKiemDinh;
	}
	@Override
	public String getMaHoaChatKiemDinh() {
		return maHoaChatKiemDinh;
	}
	@Override
	public void setMaHoaChatKiemDinh(String maHoaChatKiemDinh) {
		this.maHoaChatKiemDinh = maHoaChatKiemDinh;
	}
	@Override
	public int getsoLuongChuan() {
		return soLuongChuan;
	}
	@Override
	public void setsoLuongChuan(int soLuongChuan) {
		this.soLuongChuan = soLuongChuan;
	}
	@Override
	public int getSoLuongChatKiemDinh() {
		return soLuongChatKiemDinh;
	}
	@Override
	public void setSoLuongChatKiemDinh(int soLuongChatKiemDinh) {
		this.soLuongChatKiemDinh = soLuongChatKiemDinh;
	}
	@Override
	public IThongtinNCC getNhaCungCap() {
		return nhaCungCap;
	}
	@Override
	public void setNhaCungCap(IThongtinNCC nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}
	



	
	

}
