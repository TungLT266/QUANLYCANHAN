package geso.traphaco.erp.beans.xoakhachhangtt.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.xoakhachhangtt.IHoadon;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadonct;

public class Hoadonct implements IHoadonct
{
	String hdid;
	String spId;
	String tensp;
	String soluonghd;
	String dongiahd;
	String thanhtienhd;
	String soluongconlai;
	String thanhtienconlaihd;
	String thanhtoan;
	String conlai;
	String trahet;
	
	String soluongtt;
	String thucthu;
	String kbhId;
	
	String ptchietkhau;
	String sotienck;
	
	String sotienckhd;
	String vat;
	
	String isdathanhtoan;
	
	public Hoadonct()
	{
		this.hdid = "";
		this.spId = "";
		this.tensp = "";
		this.soluonghd = "";
		this.dongiahd = "";
		this.thanhtoan = "";
		this.thanhtienhd = "";
		this.soluongconlai = "";
		this.thanhtienconlaihd = "";
		this.thanhtoan = "";
		this.conlai = "";
		this.trahet = "";
		this.soluongtt = "";
		this.thucthu = "";
		this.kbhId = "";
		this.ptchietkhau = "";
		this.sotienck = "";
		this.sotienckhd = "";
		this.vat = "";
		this.isdathanhtoan = "";
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
		return this.trahet;
	}

	public void setTrahet(String trahet) 
	{
		this.trahet = trahet;
	}

	public String getConlai()
	{
		String tt = this.thanhtoan;
		if(tt.length() <= 0)
			tt = "0";
		
		if(this.thanhtienhd.length() <= 0 && tt.length() <= 0 && this.thanhtienconlaihd.length() <=0)
			return "0";
		
		if(this.thanhtienconlaihd.length() > 0 && tt.length() > 0)
		{
			String spthanhtienconlai = this.thanhtienconlaihd.replaceAll(",", "");
			String thanhtoan = tt.replaceAll(",", "");
			
			NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			 return formatterNT.format((Double.parseDouble(spthanhtienconlai) - Double.parseDouble(thanhtoan)));
			
		}
		
		return "0";
	}

	public void setConlai(String conlai) 
	{
		this.conlai = conlai;
	}
	

	
	public String gethdId() {
		
		return this.hdid;
	}

	
	public void sethdId(String hdid) {
		
		this.hdid = hdid;
	}

	
	public String getTensp() {
		
		return this.tensp;
	}

	
	public void setTensp(String tensp) {
		
		this.tensp = tensp;
	}

	
	public String getSoluonghd() {
		
		return this.soluonghd;
	}

	
	public void setSoluonghd(String soluonghd) {
		
		this.soluonghd = soluonghd;
	}

	
	public String getDongiahd() {
		
		return this.dongiahd ;
	}

	
	public void setDongiahd(String dongiahd) {
		
		this.dongiahd = dongiahd;
	}

	
	public String getThanhtienhd() {
		
		return this.thanhtienhd;
	}

	
	public void setThanhtienhd(String thanhtienhd) {
		
		this.thanhtienhd = thanhtienhd;
	}

	
	public String getSoluongconlai() {
		
		return this.soluongconlai;
	}

	
	public void setSoluongconlai(String soluongconlai) {
		
		this.soluongconlai = soluongconlai;
	}

	
	public String getThanhtienconlaihd() {
		
		return this.thanhtienconlaihd;
	}

	
	public void setThanhtienconlaihd(String thanhtienconlaihd) {
		
		this.thanhtienconlaihd = thanhtienconlaihd;
	}

	
	public String getspId() {
		
		return this.spId;
	}

	
	public void setspId(String spId) {
		
		this.spId = spId;
	}



	
	public String getSoluongtt() {
		
		return this.soluongtt;
	}



	
	public void setSoluongtt(String soluongtt) {
		
		this.soluongtt = soluongtt;
	}



	
	public String getkbhId() {
		
		return this.kbhId;
	}



	
	public void setkbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	public String getThucthu() {
		
		return this.thucthu;
	}

	public void setThucthu(String thucthu) {
		
		this.thucthu = thucthu;
	}

	public String getptchietkhau() {
		
		return this.ptchietkhau;
	}


	public void setptchietkhau(String ptchietkhau) {
		
		this.ptchietkhau = ptchietkhau;
	}

	
	public String getsotienck() {
		
		return this.sotienck;
	}


	public void setsotienck(String sotienck) {
		
		this.sotienck = sotienck;
	}

	
	public String getsotienckhd() {
	
		return this.sotienckhd;
	}

	
	public void setsotienckhd(String sotienckhd) {
	
		this.sotienckhd = sotienckhd;
	}

	
	public String getvat() {
	
		return this.vat;
	}
	
	public void setvat(String vat) {
	
		this.vat = vat;
	}
	
	public String getIsdathanhtoan() {
		
		return isdathanhtoan;
	}

	public void setIsdathanhtoan(String isdathanhtoan) {
		
		this.isdathanhtoan = isdathanhtoan;
		
	}


}
