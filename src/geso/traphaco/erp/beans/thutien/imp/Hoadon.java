package geso.traphaco.erp.beans.thutien.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.thutien.IHoadon;
import geso.traphaco.erp.beans.thutien.IHoadonct;

public class Hoadon implements IHoadon
{
	String id;
	String mahopdong;
	String kyhieu;
	String so;
	String ngay;
	String sotiengoc;
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
	String ngaydenhanTT;
	String khachhangId;
	String khachhangten;
	String makh;
	String ptck;
	String thucthu;
	String tienck;
	String TiengocHD;
	String chenhlech;
	
	String kbhId;
	String kbhTen;
	
	String macp;
	String tencp;
	
	String xoacl;
	
	String isNPP;
	
	private String thutienId;
	
	List<IHoadonct> hoadonctList = new ArrayList<IHoadonct>();
	
	String Tygia;
	
	
	public Hoadon()
	{
		this.id = "";
		this.mahopdong = "";
		this.loaihd = "";
		this.kyhieu = "";
		this.Tygia="0";
		this.so = "";
		this.ngay = "";
		this.sotiengoc = "";
		this.aVat = "";
		this.sotienNT = "";
		this.thanhtoan = "";
		this.checked = "";
		this.thutienId = "";
		this.ttId = "";
		this.sohoadon = "";
		this.ngayhoadon = "";
		this.ngaydenhanTT = "";	
		this.khachhangId = "";
		this.khachhangten = "";
		this.makh = "";
		this.ptck =  "";
		this.thucthu = "";
		this.tienck =  "";
		this.TiengocHD = "";
		this.chenhlech = "";
		this.kbhId = "";
		this.kbhTen = "";	
		this.xoacl = "";
		this.macp = "";
		this.tencp = "";
		this.isNPP = "";
				
	}
	public Hoadon(String id, String mahopdong, String kyhieu, String so, String ngay, String sotiengoc, String avat, String sotienNT, String thanhtoan, String ttId, String checked, String Tygia)
	{
		this.id = id;
		this.mahopdong = mahopdong;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.sotiengoc = sotiengoc;
		this.aVat = avat;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		this.checked = checked;
		this.thutienId = "";
		this.ttId = ttId;
		this.Tygia=Tygia;
		
	}
	
	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String sotiennt, String thanhtoan, String ttId,String tygia)
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

	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String thanhtoan, String tienteId,String tygia)
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

	public Hoadon(String id, String mahopdong, String kyhieu, String so, String ngay, String avat, String sotienNT, String thanhtoan, String ttId, String checked,String Tygia)
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

	public Hoadon(String id, String mahopdong, String kyhieu, String so, String ngay, String avat, String sotienno, String thanhtoan, String ttId, String tigia)
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

	public String getSotiengoc(){
		return this.sotiengoc;
	}
	
	public void setSotiengoc(String sotiengoc){
		this.sotiengoc = sotiengoc;
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
		
		if(this.ttId.equals("100000")){ // Tien te la VND
			if(this.aVat.length() <= 0 && tt.length() <= 0)
				return "";
		
			if(this.aVat.length() > 0 && tt.length() > 0)
			{
				String tienVAT = this.aVat.replaceAll(",", "");
				String thanhtoan = tt.replaceAll(",", "");
			
			
				return formatter.format(Math.round( (Double.parseDouble(tienVAT)) - Double.parseDouble(thanhtoan)));
			}
		}else{ // Tien te la ngoai te
			if(this.sotienNT != null){
				return formatter.format((Double.parseDouble(this.sotienNT.replaceAll(",", "")) -  Double.parseDouble(thanhtoan.replaceAll(",", ""))));
			}else{
				return formatter.format((Double.parseDouble(this.aVat.replaceAll(",", "")) -  Double.parseDouble(thanhtoan.replaceAll(",", ""))));
			}
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

	public int compareTo(IHoadon obj) 
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
			return "Bút toán tổng hợp";
		}else if(this.loaihd.equals("3")){
			return "Khách hàng trả trước";
		}else if(this.loaihd.equals("4")){
			return "Bù trừ công nợ";
		}else if(this.loaihd.equals("7")){
			return "Hóa đơn hàng trả về";
		}
		
		else return "";
	}

	public void setTenloaihd(String tenloaihd) {
		this.tenloaihd = tenloaihd;
		
	}
	
	public String getNgaydenhanTT() {
		return ngaydenhanTT;
	}

	public void setNgaydenhanTT(String ngaydenhanTT) {

		this.ngaydenhanTT = ngaydenhanTT ;
		
	}
	
	public List<IHoadonct> getHoadonList() {
		
		return this.hoadonctList;
	}

	
	public void setHoadonList(List<IHoadonct> spList) {
		
		this.hoadonctList = spList;
	}
	
	public String getKhId() {
		
		return this.khachhangId;
	}
	
	public void setKhId(String KhId) {
		
		this.khachhangId = KhId;
	}
	
	public String getTenkh() {
	
		return this.khachhangten;
	}
	
	public void setTenkh(String Tenkh) {
	
		this.khachhangten = Tenkh;
	}
	
	public String getMaKH() {
		
		return this.makh;
	}
	
	public void setMaKH(String makh) {
		
		this.makh = makh;
	}
	
	public String getptck() {
		
		return this.ptck;
	}
	
	public void setptck(String ptck) {
		
		this.ptck = ptck;
	}
	
	public String getthucthu() {
		
		return this.thucthu;
	}
	
	public void setthucthu(String thucthu) {
		
		this.thucthu = thucthu;
	}
	
	public String gettienck() {
		
		return this.tienck;
	}
	
	public void settienck(String tienck) {
		
		this.tienck = tienck;
	}
	
	public String getTiengocHD() {
		
		return this.TiengocHD;
	}
	
	public void setTiengocHD(String TiengocHD) {
		
		this.TiengocHD = TiengocHD;
	}
	
	public String getChenhlech() {
		
		return this.chenhlech;
	}
	
	public void setChenhlech(String chenhlech) {
		
		this.chenhlech = chenhlech;
	}
	
	public String getkbhId() {
	
		return this.kbhId;
	}
	
	public void setkbhId(String kbhId) {
	
		this.kbhId = kbhId;
	}
	
	public String getkbhTen() {
	
		return this.kbhTen;
	}
	
	public void setkbhTen(String kbhTen) {
	
		this.kbhTen = kbhTen;
	}
	
	public String getxoacl() {
		
		return this.xoacl;
	}
	
	public void setxoacl(String xoacl) {
		
		this.xoacl = xoacl;
	}
	
	public String getmacp() {
		
		return this.macp;
	}
	
	public void setmacp(String macp) {
		
		this.macp = macp;
	}

	public String gettencp() {
		
		return this.tencp;
	}

	public void settencp(String tencp) {
		
		this.tencp = tencp;
	}
	
	public String getisNPP() {
		
		return this.isNPP;
	}
	
	public void setisNPP(String isNPP) {
		
		this.isNPP = isNPP;
	}

}
