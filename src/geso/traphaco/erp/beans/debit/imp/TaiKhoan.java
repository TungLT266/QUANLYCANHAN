package geso.traphaco.erp.beans.debit.imp;

public class TaiKhoan {
	private String taiKhoanId;
	private String dienTa;
	
	public TaiKhoan() {
		super();
		this.taiKhoanId = "";
		this.dienTa = "";
	}
	
	public TaiKhoan(String taiKhoanId, String dienTa) {
		super();
		this.taiKhoanId = taiKhoanId;
		this.dienTa = dienTa;
	}
	public String getTaiKhoanId() {
		return taiKhoanId;
	}
	public void setTaiKhoanId(String taiKhoanId) {
		this.taiKhoanId = taiKhoanId;
	}
	public String getDienTa() {
		return dienTa;
	}
	public void setDienTa(String dienTa) {
		this.dienTa = dienTa;
	}
}
