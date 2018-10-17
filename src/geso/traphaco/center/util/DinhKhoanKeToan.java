package geso.traphaco.center.util;


public class DinhKhoanKeToan implements IDinhKhoanKeToan 
{
	String id;
	String NO_CO;
	String sohieu;
	String sotien;
	String doituong;
	String trungtamCP;
	String trungtamDT;

	String loai;
	String tenloai;
	
	String machungtu;
	
	public DinhKhoanKeToan()
	{
		this.id = "";
		this.NO_CO = "";
		this.sohieu = "";
		this.sotien = "";
		this.loai = "";
		this.tenloai = "";
		this.doituong = "";
		this.trungtamCP = "";
		this.trungtamDT = "";
		this.machungtu = "";
	}
	
	public DinhKhoanKeToan(String id, String NO_CO, String sohieu, String sotien, String doituong, String trungtamCP, String trungtamDT, String loai)
	{
		this.id = id;
		this.NO_CO = NO_CO;
		this.sohieu = sohieu;
		this.sotien = sotien;
		this.doituong = doituong;
		this.trungtamCP = trungtamCP;
		this.trungtamDT = trungtamDT;		
		this.loai = loai;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}


	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}

	public String getSotien() 
	{
		return this.sotien;
	}

	public void setSotien(String sotien) 
	{
		this.sotien = sotien;
	}

	
	public String getTenLoai() 
	{
		if(this.loai.equals("1")){
			this.tenloai = "Tiền hàng";
		}else if(this.loai.equals("2")){
			this.tenloai = "Tiền thuế";
		}else if(this.loai.equals("3")){
			this.tenloai = "Tiền phí ngân hàng";
		}else if(this.loai.equals("4")){
			this.tenloai = "Tiền chênh lệch";
		}
		else{
			this.tenloai = "";
		}
		return this.tenloai ;
	}

	public void setTenLoai(String tenloai) 
	{
		this.tenloai = tenloai;
	}

	
	public String getTrungtamCP() {
		
		return this.trungtamCP;
	}

	
	public void setTrungtamCP(String trungtamcp) {
		
		this.trungtamCP = trungtamcp;
	}

	
	public String getTrungtamDT() {
		
		return this.trungtamDT;
	}

	
	public void setTrungtamDT(String trungtamdt) {
		
		this.trungtamDT = trungtamdt;
	}

	
	public String getNO_CO() {
		
		return this.NO_CO;
	}

	
	public void setNO_CO(String NO_CO) {
		
		this.NO_CO = NO_CO;
	}

	
	public String getSoHieu() {
		
		return this.sohieu;
	}

	
	public void setSoHieu(String SoHieu) {
		
		this.sohieu = SoHieu;
	}

	
	public String getDoiTuong() {
		
		return this.doituong;
	}

	
	public void setDoiTuong(String doituong) {
		
		this.doituong = doituong;
	}

	

}
