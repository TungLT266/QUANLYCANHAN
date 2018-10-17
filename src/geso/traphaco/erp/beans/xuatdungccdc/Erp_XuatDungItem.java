package geso.traphaco.erp.beans.xuatdungccdc;

public class Erp_XuatDungItem
{
	private String id;
	private String maCCDC;
	private String ngayTao;
	private String ngaySua;
	private String nguoiTao;
	private String nguoiSua;
	private String trangThai;
	private int thangDaPB;//những tháng chưa khóa sổ
	
	public Erp_XuatDungItem()
	{
		this.id= "";
		this.maCCDC= "";
		this.ngayTao= "";
		this.ngaySua= "";
		this.nguoiTao= "";
		this.nguoiSua= "";
		this.trangThai= "";
	}
	
	public Erp_XuatDungItem(String id, String maCCDC, String ngayTao
			, String ngaySua, String nguoiTao, String nguoiSua, String trangThai)
	{
		this.id = id;
		this.maCCDC = maCCDC;
		this.ngayTao = ngayTao;
		this.ngaySua = ngaySua;
		this.nguoiTao = nguoiTao;
		this.nguoiSua = nguoiSua;
		this.trangThai = trangThai;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaCCDC() {
		return maCCDC;
	}
	public void setMaCCDC(String maCCDC) {
		this.maCCDC = maCCDC;
	}
	public String getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}
	public String getNgaySua() {
		return ngaySua;
	}
	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}
	public String getNguoiTao() {
		return nguoiTao;
	}
	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}
	public String getNguoitSua() {
		return nguoiSua;
	}
	public void setNguoitSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	
	public int getThangDaPB() {
		return thangDaPB;
	}

	public void setThangDaPB(int thangDaPB) {
		this.thangDaPB = thangDaPB;
	}
}