package geso.traphaco.erp.beans.thanhtoanhoadon.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;

public class Hoadon implements IHoadon
{
	String id;
	String kyhieu;
	String so;
	String ngay;
	String aVat;
	String sotienNT;
	String sotienno;
	String thanhtoan;
	String checked;
	String soPO;	
	String thanhtoanId;
	String mancc;
	String tigia;
	String tienteId;
	String loaihd;
	String loaihd1;
	String tenloaihd;
	String tenloaihd1;
	String tenCQT;
	String ngaydenhantt;
	String sotiengoc;
	//Đối với trường hợp hóa đơn thuế nhập khẩu có 2 loại : 1 - thuế nhập khẩu , 2 - thuế VAT
	String loaiThue; 
	// 0 : Nhân viên, 1 : NCC
	String doituong;
	String doituongId;
	String madoituong;
	
	String sohopdong;
	
	String sohopdongngoai;
	
	String diaChi;
	
	public String getMancc() {
		return mancc;
	}

	public void setMancc(String mancc) {
		this.mancc = mancc;
	}

	public Hoadon()
	{
		this.id = "";
		this.kyhieu = "";
		this.so = "";
		this.ngay = "";
		this.aVat = "0";
		this.sotienNT = "0";
		this.thanhtoan = "0";
		this.checked = "";
		this.soPO = "";
		this.mancc="";
		this.tigia="1";
		this.tienteId = "100000";
		this.loaihd = "0";
		this.tenloaihd = "";
		this.tenCQT= "";
		this.ngaydenhantt = "";
		this.sotiengoc = "";
		this.loaihd1 = "";
		this.tenloaihd1 = "";
		this.sohopdong = "";
		this.sohopdongngoai = "";
		this.diaChi = "";
		this.loaiThue = "";
	}
	
	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String sotienno, String thanhtoan)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.thanhtoan = thanhtoan;
		this.sotienno = sotienno;
		this.soPO = "";
		
	}
	


	public Hoadon(String mancc, String id, String kyhieu, String so, String ngay, String avat, String sotienNT, String thanhtoan, String checked ,String thanhtoanID)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		this.checked = checked;
		this.soPO = "";
		this.mancc = mancc;
		
	}
	

	public Hoadon(String mancc, String id, String kyhieu, String so, String ngay, String avat, String sotienNT, String thanhtoan, String checked ,String thanhtoanID, String diaChi)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		this.checked = checked;
		this.soPO = "";
		this.mancc = mancc;
		this.diaChi = diaChi;
		
	}
	
	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String sotienNT, String thanhtoan,  String tienteId){
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		
		if(tienteId.equals("100000")){
			this.sotiengoc = avat;
		}else{
			this.sotiengoc = sotienNT;
		}
		
		this.tienteId = tienteId;
	}
	
	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String sotienNT, String thanhtoan,  String tienteId, String diaChi)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		
		if(tienteId.equals("100000")){
			this.sotiengoc = avat;
		}else{
			this.sotiengoc = sotienNT;
		}
		
		this.tienteId = tienteId;
		this.diaChi = diaChi;
	
	}


	public String getTienteId()
	{
		return this.tienteId;
	}

	public void setTienteId(String ttId) 
	{
		this.tienteId  = ttId;	
	}

	public String getTigia()
	{
		return this.tigia;
	}

	public void setTigia(String tigia) 
	{
		this.tigia = tigia;	
	}

	public String getKyhieu()
	{
		return this.kyhieu;
	}

	public void setKyhieu(String kyhieu) 
	{
		this.kyhieu = kyhieu;	
	}

	public String getSo()
	{
		return this.so;
	}

	public void setSo(String so) 
	{
		this.so = so;	
	}

	public String getNgay() 
	{
		return this.ngay;
	}

	public void setNgay(String ngay) 
	{
		this.ngay = ngay;
	}

	public String getTongtiencoVAT()
	{
		if(aVat==null||"".equals(aVat.trim())){return "0";}
		return this.aVat;
	}

	public void setTongtiencoVAT(String aVat) 
	{
		this.aVat = aVat;	
	}

	public String getSotienNT()
	{
		
		return this.sotienNT;
	}

	public void setSotienNT(String sotienNT) 
	{
		this.sotienNT = sotienNT;	
	}

	public String getSotienno()
	{
		
		return this.sotienno;
	}

	public void setSotienno(String sotienno) 
	{
		this.sotienno = sotienno;	
	}

	public String getThanhtoan() 
	{
		if(thanhtoan==null||"".equals(thanhtoan.trim())){return "0";}
		return this.thanhtoan;
	}

	public void setThanhtoan(String thanhtoan)
	{
		this.thanhtoan = thanhtoan;
	}

	public String getTrahet()
	{
		return this.checked;
	}

	public void setTrahet(String trahet) 
	{
		this.checked = trahet;
	}
	public void setLoaiThue(String loaiThue)
	{
		this.loaiThue = loaiThue;
	}
	public String getLoaiThue(){
		return this.loaiThue;
	}

	public String getConlai()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		String tmp= "";
				
		if(this.tienteId == null) this.tienteId = "100000";
		
		if(thanhtoan.length() == 0) thanhtoan = "0";
		
		if(this.tienteId.equals("100000"))
		{
			
		  tmp = formatter.format(Math.round(Double.parseDouble(this.aVat.replaceAll(",", "")) - Double.parseDouble(thanhtoan.replaceAll(",", ""))));
		  
		}else{	
			//double avatNT= Double.parseDouble(this.aVat.replaceAll(",", ""))* Double.parseDouble(this.tigia.replaceAll(",", ""));
			double avatNT= Double.parseDouble(this.sotienNT.replaceAll(",", ""));
			tmp = formatter1.format(avatNT - Double.parseDouble(thanhtoan.replaceAll(",", "")));
		  
		}
		  return tmp;
	}

	public String getConlai_2()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");

		if(thanhtoan.length() == 0) thanhtoan = "0";
		
		String tmp = "0";
		
		if(this.aVat.length() == 0) this.aVat = "0";
		
		if(this.thanhtoan.length() == 0) this.thanhtoan = "0";
		
		tmp = formatter.format(Math.round(Double.parseDouble(this.aVat.replaceAll(",", "")) - Double.parseDouble(thanhtoan.replaceAll(",", ""))));
		
		return tmp;
	}

	public void setConlai(String conlai) 
	{
		
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public int compareTo(IHoadon obj) 
	{
		return 0;
	}

	public String getSoPO() 
	{
		return this.soPO;
	}

	public void setSoPO(String soPO) 
	{
		this.soPO = soPO;
	}

	public String getTenCQT() {
		return tenCQT;
	}

	public void setTenCQT(String tenCQT) {

		this.tenCQT = tenCQT ;
		
	}
	
	public String getLoaihd() {
		return loaihd;
	}

	public void setLoaihd(String loaihd) {

		this.loaihd = loaihd ;
		
	}

	public String getTenloaihd() {

		if(this.loaihd.equals("0")){ 
			return "Mua hàng";
			
		}else if(this.loaihd.equals("1")){
			
			return "Tạm ứng";
			
		}else if(this.loaihd.equals("2")){
			
			return "Thuế nhập khẩu";
			
		}else if(this.loaihd.equals("3")){
			
			return "Chi phí nhập khẩu";
			
		}else if(this.loaihd.equals("4")){
			
			return "Chi phí khác";
			
		}else{
			
			return "";
		}
	}
	
	public String getTenloaihd1() {

		if(this.loaihd1.equals("0")){ 
			return "Hóa đơn NCC";
			
		}else if(this.loaihd1.equals("1")){
			
			return "Đề nghị tạm ứng";
			
		}else if(this.loaihd1.equals("2")){
			
			return "Chi phí nội bộ";
			
		}else if(this.loaihd1.equals("3")){
			
			return "Chi phí nhận hàng";
			
		}else if(this.loaihd1.equals("4")){
			
			return "Thuế nhập khẩu";
			
		}else if(this.loaihd1.equals("5")){
			
			return "Chi phí khác";
				
		}else if(this.loaihd1.equals("6")){
			
			return "Đề nghị thanh toán";
				
		}else if(this.loaihd1.equals("7")){
			
			return "Khách hàng trả trước";
				
		}else if(this.loaihd1.equals("8")){
			
			return "Chi đề nghị thanh toán";
				
		}
		else if(this.loaihd1.equals("9")){
			
			return "Chi đề nghị tạm ứng";
				
		}
		else{						
			return "";
		}
	}


	public void setTenloaihd1(String tenloaihd1) 
	{
		this.tenloaihd1 = tenloaihd1;
		
	}
	
	public String getNgaydenhanTT()
	{
		return this.ngaydenhantt;
	}

	public void setNgaydenhanTT(String ngaydenhantt) 
	{
		this.ngaydenhantt = ngaydenhantt;	
	}

	public void setTenloaihd(String tenloaihd) {
		this.tenloaihd = tenloaihd;
		
	}

	public String getSoTienGoc2()
	{
		return this.sotiengoc;
	}

	public void setSoTienGoc2(String sotiengoc) 
	{
		this.sotiengoc  = sotiengoc;	
	}
	
	public String getSoTienGoc() {
	
		if(this.thanhtoan.trim().length() == 0) this.thanhtoan = "0";		

		Double tmp = (Double.parseDouble(this.thanhtoan.replaceAll(",", "")) + Double.parseDouble(this.aVat.replaceAll(",", "")));
		NumberFormat formatter = new DecimalFormat("#,###,###");
		return formatter.format(tmp);
	}
	


	
	public void setConlai_2(String conlai) {
		
	}

	public String getLoaihd1() 
	{		
		return this.loaihd1;
	}

	public void setLoaihd1(String loaihd1) 
	{
		this.loaihd1 = loaihd1 ;
	}

	public String getDoituong() 
	{		
		return this.doituong;
	}

	public void setDoituong(String doituong) 
	{
		this.doituong = doituong ;
	}
	
	public String getDoituongId() 
	{		
		return this.doituongId;
	}

	public void setDoituongId(String doituongId) 
	{
		this.doituongId = doituongId ;
	}
	
	public String getMadoituong() 
	{		
		return this.madoituong;
	}

	public void setMadoituong(String madoituong) 
	{
		this.madoituong = madoituong ;
	}

	
	public String getSohopdong() {
	
		return this.sohopdong;
	}

	
	public void setSohopdong(String sohopdong) {
	
		this.sohopdong = sohopdong;
	}

	
	public String getSohopdongNGOAI() {
		
		return this.sohopdongngoai;
	}

	
	public void setSohopdongNGOAI(String sohopdongngoai) {
		
		this.sohopdongngoai = sohopdongngoai;
	}
	@Override
	public String getDiaChi() {
		return diaChi;
	}

	@Override
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}


}
