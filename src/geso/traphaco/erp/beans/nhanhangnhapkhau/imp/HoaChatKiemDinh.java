package geso.traphaco.erp.beans.nhanhangnhapkhau.imp;

public class HoaChatKiemDinh {
	private String hoaChat;
	private float soChatKiemDinh;
	private float soChatDuocKiemDinh;
	
	public HoaChatKiemDinh() {
		super();
		this.hoaChat = "";
		this.soChatKiemDinh = 0;
		this.soChatDuocKiemDinh = 0;
	}
	
	public HoaChatKiemDinh(String hoaChat, float soChatKiemDinh, float soChatDuocKiemDinh) {
		super();
		this.hoaChat = hoaChat;
		this.soChatKiemDinh = soChatKiemDinh;
		this.soChatDuocKiemDinh = soChatDuocKiemDinh;
	}
	public String getHoaChat() {
		return hoaChat;
	}
	public void setHoaChat(String hoaChat) {
		this.hoaChat = hoaChat;
	}
	public float getSoChatKiemDinh() {
		return soChatKiemDinh;
	}
	public void setSoChatKiemDinh(float soChatKiemDinh) {
		this.soChatKiemDinh = soChatKiemDinh;
	}
	public float getSoChatDuocKiemDinh() {
		return soChatDuocKiemDinh;
	}
	public void setSoChatDuocKiemDinh(float soChatDuocKiemDinh) {
		this.soChatDuocKiemDinh = soChatDuocKiemDinh;
	}
}
