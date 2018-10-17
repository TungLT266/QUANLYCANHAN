package geso.traphaco.erp.beans.nhomkhoanmuc.imp;

public class ErpKhoanMucChiPhi {
	private String idKhoanMuc;
	private String tenKhoanMuc;
	private String maKhoanMuc;
	private String checked;
	
	public ErpKhoanMucChiPhi() {
		super();
		this.idKhoanMuc = "";
		this.tenKhoanMuc = "";
		this.maKhoanMuc = "";
		this.checked = "0";
	}
	public String getIdKhoanMuc() {
		return idKhoanMuc;
	}
	public void setIdKhoanMuc(String idKhoanMuc) {
		this.idKhoanMuc = idKhoanMuc;
	}
	public String getTenKhoanMuc() {
		return tenKhoanMuc;
	}
	public void setTenKhoanMuc(String tenKhoanMuc) {
		this.tenKhoanMuc = tenKhoanMuc;
	}
	public String getMaKhoanMuc() {
		return maKhoanMuc;
	}
	public void setMaKhoanMuc(String maKhoanMuc) {
		this.maKhoanMuc = maKhoanMuc;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
