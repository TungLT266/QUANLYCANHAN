package geso.traphaco.erp.beans.xuatdungccdc;

public class Erp_VatTuSoLo
{
	private String soLo;
	private double soLuong;
	private String ngayNhapKho;
	
	public Erp_VatTuSoLo()
	{
		this.soLo = "";
		this.soLuong = 0;
		this.ngayNhapKho = "";
	}
	
	public Erp_VatTuSoLo(String soLo, double soLuongTinh, String ngayNhapKho)
	{
		this.soLo = soLo;
		this.soLuong = soLuongTinh;
		this.ngayNhapKho = ngayNhapKho;
	}
	
	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}
	public String getSoLo() {
		return soLo;
	}
	public void setSoLuongTinh(double soLuong) {
		this.soLuong = soLuong;
	}
	public double getSoLuong() {
		return soLuong;
	}

	public void setNgayNhapKho(String ngayNhapKho) {
		this.ngayNhapKho = ngayNhapKho;
	}

	public String getNgayNhapKho() {
		return ngayNhapKho;
	}
}
