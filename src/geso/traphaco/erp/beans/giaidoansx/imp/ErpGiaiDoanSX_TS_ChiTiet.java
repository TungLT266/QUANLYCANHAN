package geso.traphaco.erp.beans.giaidoansx.imp;

public class ErpGiaiDoanSX_TS_ChiTiet {
	private String dienGiai;
	private String yeucau;
	private String thongsoTu;
	private String thongsoDen;
	private String donVi;
	private String tick;
	
	public ErpGiaiDoanSX_TS_ChiTiet() {
		this.dienGiai = "";
		this.yeucau = "";
		this.thongsoTu = "";
		this.thongsoDen = "";
		this.donVi = "";
		this.tick = "0";
	}
	
	public String getThongsoTu() {
		return thongsoTu;
	}
	public void setThongsoTu(String thongsoTu) {
		this.thongsoTu = thongsoTu;
	}
	public String getThongsoDen() {
		return thongsoDen;
	}
	public void setThongsoDen(String thongsoDen) {
		this.thongsoDen = thongsoDen;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getTick() {
		return tick;
	}
	public void setTick(String tick) {
		this.tick = tick;
	}

	public String getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	public String getYeucau() {
		return yeucau;
	}

	public void setYeucau(String yeucau) {
		this.yeucau = yeucau;
	}
}
