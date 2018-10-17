package geso.traphaco.erp.beans.debit.imp;

public class TrungTamChiPhi {
	private String trungTamChiPhiId;
	private String dienGiai;
	
	public TrungTamChiPhi() {
		super();
		this.trungTamChiPhiId = "";
		this.dienGiai = "";
	}
	
	public TrungTamChiPhi(String trungTamChiPhiId, String dienGiai) {
		super();
		this.trungTamChiPhiId = trungTamChiPhiId;
		this.dienGiai = dienGiai;
	}
	public String getTrungTamChiPhiId() {
		return trungTamChiPhiId;
	}
	public void setTrungTamChiPhiId(String trungTamChiPhiId) {
		this.trungTamChiPhiId = trungTamChiPhiId;
	}
	public String getDienGiai() {
		return dienGiai;
	}
	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
}
