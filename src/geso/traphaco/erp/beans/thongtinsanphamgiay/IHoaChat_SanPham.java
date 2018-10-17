package geso.traphaco.erp.beans.thongtinsanphamgiay;



public interface IHoaChat_SanPham {

	String getPK_SEQSanPhamDuocKiemDinh();

	void setPK_SEQSanPhamDuocKiemDinh(String pK_SEQSanPhamDuocKiemDinh);
	public String getDvtSanPhamDuocKiemDinh();
	public void setDvtSanPhamDuocKiemDinh(String dvtSanPhamDuocKiemDinh);
	public String getTenHoaChatKiemDinh();
	public void setTenHoaChatKiemDinh(String tenHoaChatKiemDinh);
	public String getPK_SEQHoaChatKiemDinh();
	public void setPK_SEQHoaChatKiemDinh(String pK_SEQHoaChatKiemDinh);
	public String getDvtHoaChatKiemDinh();
	public void setDvtHoaChatKiemDinh(String dvtHoaChatKiemDinh);
	public String getMaHoaChatKiemDinh();
	public void setMaHoaChatKiemDinh(String maHoaChatKiemDinh);
	public int getsoLuongChuan();
	public void setsoLuongChuan(int soLuongChuan);
	public int getSoLuongChatKiemDinh();
	public void setSoLuongChatKiemDinh(int soLuongChatKiemDinh);

	IThongtinNCC getNhaCungCap();

	void setNhaCungCap(IThongtinNCC nhaCungCap);
}
