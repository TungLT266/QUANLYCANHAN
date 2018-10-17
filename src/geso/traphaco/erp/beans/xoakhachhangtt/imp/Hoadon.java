package geso.traphaco.erp.beans.xoakhachhangtt.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.xoakhachhangtt.IHoadon;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadonct;

public class Hoadon implements IHoadon
{
	String id;
	String kyhieu;
	String so;
	String ngay;
	String aVat;
	String aVatVND;
	String thanhtoan;
	String checked;
	String loaihd;
	String thanhtoanVND;
	String tienteId;
	String tigiaHD;
	String loaict;
	String tenkh;
	String khId;
	String kenhTen;
	String tiengocHD;
	String ngaydenhantt;
	String kenhId;
	String maKH;
	String sotienAVAT;
	String ptck;
	String thucthu;
	String tienck;
	
	String ghichu;
	String isNPP;
	
	List<IHoadonct> hoadonctList = new ArrayList<IHoadonct>();
	
	public Hoadon()
	{
		this.id = "";
		this.kyhieu = "";
		this.so = "";
		this.ngay = "";
		this.aVat = "";
		this.thanhtoan = "";
		this.checked = "";
		this.loaihd = "";
		this.aVatVND = "";
		this.thanhtoanVND = "";
		this.tienteId = "";
		this.tigiaHD = "";
		this.loaict = "";
		this.tenkh = "";
		this.khId = "";
		this.kenhTen = "";
		this.tiengocHD = "";
		this.ngaydenhantt = "";
		this.kenhId = "";
		this.maKH = "";
		this.sotienAVAT = "";
		this.ptck =  "";
		this.thucthu = "";
		this.tienck = "";
		this.ghichu ="";
		this.isNPP = "";
	}
	
	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String thanhtoan, String checked)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.thanhtoan = thanhtoan;
		this.checked = checked;
		this.tienteId = "";
		this.tigiaHD = "";
		
	}

	public String getTigiaHD()
	{
		return this.tigiaHD;
	}

	public void setTigiaHD(String tigiaHD) 
	{
		this.tigiaHD = tigiaHD;	
	}
	
	public String getTienteId()
	{
		return this.tienteId;
	}

	public void setTienteId(String tienteId) 
	{
		this.tienteId = tienteId;	
	}
	
	public String getLoaihd()
	{
		return this.loaihd;
	}

	public void setLoaihd(String loaihd) 
	{
		this.loaihd = loaihd;	
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
		
		if(this.aVat.length() == 0 && tt.length() <= 0)
			return "0";
		
		if(this.aVat.length() != 0 && tt.length() > 0)
		{
			String tienVAT = this.aVat.replaceAll(",", "");
			String thanhtoan = tt.replaceAll(",", "");
			
			NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			if(this.tienteId.equals("100000"))
			{
			 return formatter.format(Math.round(Double.parseDouble(tienVAT) - Double.parseDouble(thanhtoan)));
			}
			else
			{
			 return formatterNT.format((Double.parseDouble(tienVAT) - Double.parseDouble(thanhtoan)));
			}
		}
		
		return "0";
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

	public String getTongtiencoVATVND() 
	{
		return this.aVatVND;
	}

	public void setTongtiencoVATVND(String aVatVND) 
	{
		this.aVatVND = aVatVND;
	}
	
	public String getThanhtoanVND() 
	{
		return this.thanhtoanVND;
	}

	public void setThanhtoanVND(String thanhtoanVND)
	{
		this.thanhtoanVND = thanhtoanVND;
	}

	
	public String getLoaict() {
		
		return this.loaict;
	}

	
	public void setLoaict(String loaict) {
		
		this.loaict = loaict;
	}

	
	public String getTenkh() {
		
		return this.tenkh;
	}

	
	public void setTenkh(String tenkh) {
		
		this.tenkh = tenkh;
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String KhId) {
		
		this.khId = KhId;
	}

	
	public String getKenhTen() {
		
		return this.kenhTen;
	}

	
	public void setKenhTen(String Kenhten) {
		
		this.kenhTen = Kenhten;
	}

	
	public String getTiengocHD() {
		
		return this.tiengocHD;
	}

	
	public void setTiengocHD(String TiengocHD) {
		
		this.tiengocHD = TiengocHD;
	}

	
	public List<IHoadonct> getHoadonList() {
		
		return this.hoadonctList;
	}

	
	public void setHoadonList(List<IHoadonct> spList) {
		
		this.hoadonctList = spList;
	}


	public String getNgaydenhanTT() {
		
		return this.ngaydenhantt;
	}


	public void setNgaydenhanTT(String ngaydenhantt) {
		
		this.ngaydenhantt = ngaydenhantt;
	}

	
	public String getKenhId() {
		
		return this.kenhId;
	}

	
	public void setKenhId(String Kenhid) {
		
		this.kenhId = Kenhid;
	}

	
	public String getMaKH() {
		
		return this.maKH;
	}

	
	public void setMaKH(String makh) {
		
		this.maKH = makh;
	}

	
	public String getSotiengoc() {
	
		return this.sotienAVAT;
	}

	
	public void setSotiengoc(String sotiengoc) {
	
		this.sotienAVAT = sotiengoc;
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

	
	public String getghichu() {
	
		return this.ghichu;
	}

	
	public void setghichu(String ghichu) {
	
		this.ghichu = ghichu;
	}

	
	public String getisNPP() {
		
		return this.isNPP;
	}

	
	public void setisNPP(String isNPP) {
		
		this.isNPP = isNPP;
	}
	
}
