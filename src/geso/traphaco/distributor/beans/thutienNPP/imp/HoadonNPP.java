package geso.traphaco.distributor.beans.thutienNPP.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import geso.traphaco.distributor.beans.thutienNPP.IHoadonNPP;

public class HoadonNPP implements IHoadonNPP
{
	String id;
	String mahopdong;
	String kyhieu;
	String so;
	String ngay;
	String aVat;
	String sotienno;
	String sotienNT;
	String thanhtoan;
	String checked;
	String ttId;
	String loaihd;
	String tenloaihd;
	String sohoadon;
	String ngayhoadon;
	String nppId;
	String nppMa;
	String trangthaihd;
	
	String duno;
	
	String khId;
	String khMa;
	String isKHLe; // 1: là KH lẻ, 0: KH bán buôn / Không ký HĐ
	
	String cantru;
	private String thutienId;
	
	String Tygia;
	String loaiHD;
	
	
	public HoadonNPP()
	{
		this.id = "";
		this.mahopdong = "";
		this.loaihd = "";
		this.kyhieu = "";
		this.Tygia="0";
		this.so = "";
		this.ngay = "";
		this.aVat = "";
		this.sotienNT = "";
		this.thanhtoan = "";
		this.checked = "";
		this.thutienId = "";
		this.ttId = "";
		this.sohoadon = "";
		this.ngayhoadon = "";
		this.nppId ="";
		this.nppMa="";
		this.khId ="";
		this.khMa="";
		this.isKHLe = "0";
		this.trangthaihd = "";
		this.duno= "";
		this.cantru="";
	}
	
	public HoadonNPP(String id, String kyhieu, String so, String ngay, String avat, String sotiennt, String thanhtoan, String ttId,String tygia)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.thanhtoan = thanhtoan;
		this.sotienNT = sotiennt;
		this.ttId = ttId;
		this.Tygia= tygia;
	}

	public HoadonNPP(String id, String kyhieu, String so, String ngay, String avat, String thanhtoan, String tienteId,String tygia)
	{
		this.id = id;
		this.mahopdong = "";
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		
		this.aVat = avat;
		this.sotienNT = avat;
		this.thanhtoan = thanhtoan;
		
		this.thutienId = "";
		this.ttId = tienteId;
		this.ttId = "";
		this.Tygia=tygia;
	}

	public HoadonNPP(String id, String mahopdong, String kyhieu, String so, String ngay, String avat, String sotienNT, String thanhtoan, String ttId, String checked,String Tygia)
	{
		this.id = id;		
		this.mahopdong = mahopdong;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		this.checked = checked;
		this.thutienId = "";
		this.ttId = ttId;
		this.Tygia=Tygia;
				
	}

	public HoadonNPP(String id, String mahopdong, String kyhieu, String so, String ngay, String avat, String sotienno, String thanhtoan, String ttId, String tigia)
	{
		this.id = id;
		this.mahopdong = mahopdong;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.sotienno = sotienno;
		this.thanhtoan = thanhtoan;		
		this.thutienId = "";
		this.ttId = ttId;
		this.Tygia = tigia;
		
	}

	public String getTtId()
	{
		return this.ttId;
	}

	public void setTtId(String ttId) 
	{
		this.ttId = ttId;	
	}

	public String getSotienNT()
	{
		return this.sotienNT;
	}

	public void setSotienNT(String sotienNT) 
	{
		this.sotienNT = sotienNT;	
	}

	public String getMahopdong()
	{
		return this.mahopdong;
	}

	public void setMahopdong(String mahopdong) 
	{
		this.mahopdong = mahopdong;	
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
		return this.aVat;
	}

	public void setTongtiencoVAT(String aVat) 
	{
		this.aVat = aVat;	
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

	public String getConlai()
	{
		String tt = this.thanhtoan;
		if(tt.length() <= 0)
			tt = "0";
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		
			if(this.aVat.length() <= 0 && tt.length() <= 0)
				return "";
		
			if(this.aVat.length() > 0 && tt.length() > 0)
			{
				String tienVAT = this.aVat.replaceAll(",", "");
				String thanhtoan = tt.replaceAll(",", "");
			
			
				return formatter.format(Math.round(Double.parseDouble(tienVAT) - Double.parseDouble(thanhtoan)));
			}
		
		return "";
	}

	public String getConlai_CantruCN()
	{
		String tt = this.thanhtoan;
		if(tt.length() <= 0) tt = "0";
		
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

		if(this.sotienno.length() <= 0 && tt.length() <= 0)
			return "";
		
		if(this.sotienno.length() > 0 && tt.length() > 0)
		{
			return formatter.format(Math.round(Double.parseDouble(this.sotienno.replaceAll(",", "")) - Double.parseDouble(thanhtoan.replaceAll(",", ""))));
		}

		return "";
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

	public int compareTo(IHoadonNPP obj) 
	{
		return 0;
	}
	public String getThutienId() {
		if(thutienId==null){
			thutienId = "";
		}
		return thutienId;
	}
	
	public void setThutienId(String thutienId) {
		this.thutienId = thutienId;
	}

	
	public String getTygia() {
		
		return this.Tygia;
	}

	
	public void setTygia(String Tygia) {
		
		this.Tygia=Tygia;
	}

	public String getLoaihd() {
		return loaihd;
	}

	public void setLoaihd(String loaihd) {

		this.loaihd = loaihd ;
		
	}

	public String getTenloaihd() {
		if(this.loaihd.equals("0")){ 
			return "Hóa đơn tài chính";
		}else if(this.loaihd.equals("1")){
			return "Hóa đơn khác";
		}else if(this.loaihd.equals("2")){
			return "Giảm/Tăng giá hàng bán";
		}else return "";
	}

	public void setTenloaihd(String tenloaihd) {
		this.tenloaihd = tenloaihd;
		
	}

	
	public String getNppId() {
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
	this.nppId =nppId;
		
	}

	
	public String getNppMa() {
		return this.nppMa;
	}

	
	public void setNppMa(String nppMa) {
		this.nppMa = nppMa;
		
	}

	
	public String getKhId() {
		return this.khId;
	}

	
	public void setKhId(String khId) {
	this.khId =khId;
		
	}

	
	public String getKhMa() {
		return this.khMa;
	}

	
	public void setKhMa(String khMa) {
		this.khMa = khMa;
		
	}
	
	public String getIsKHLe() 
	{
		return isKHLe;
	}

	public void setIsKHLe(String isKHLe)
	{
		this.isKHLe = isKHLe;
	}

	public String getTrangthaihd()
	{
		return this.trangthaihd;
	}

	public void setTrangthaihd(String trangthaihd) 
	{
		this.trangthaihd = trangthaihd;	
	}
	
	public String getDuno()
	{
		return this.duno;
	}

	public void setDuno(String duno) 
	{
		this.duno = duno;	
	}

	
	public String getCantru() {
		
		return this.cantru;
	}

	
	public void setCantru(String cantru) {
		
		this.cantru=cantru;
	}
	
}
