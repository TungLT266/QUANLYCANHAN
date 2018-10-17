package geso.traphaco.erp.beans.nhanhangtrongnuoc.imp;

public class KhuVucKho {
	private String id;
	private String ten;
	
	public KhuVucKho() {
		super();
		this.id = "";
		this.ten = "";
	}
	public KhuVucKho(String id, String ten) {
		super();
		this.id = id;
		this.ten = ten;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	
}
