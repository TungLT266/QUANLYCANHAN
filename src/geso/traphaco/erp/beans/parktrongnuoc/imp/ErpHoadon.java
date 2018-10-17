package geso.traphaco.erp.beans.parktrongnuoc.imp;
import java.util.ArrayList;
import java.util.List;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.nhanhang.ISpDetail;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadon;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadonSp;

public class ErpHoadon implements IErpHoadon
{
	String ID,KYHIEU,SOHOADON,THUESUAT,USERID,PARK,NGAYHOADON,SOTIENAVAT,SOTIENBVAT,VAT,CHIETKHAU,TRANGTHAI, TIENTE, SOTIENAVATVND;
	String mauhoadon;
	String mausohoadon;
	String soBL;
	String ngayBL;
	List<IErpHoadonSp> pnkList;
	String[] poId,loai, mahang, donvi, soluong, dongia, thanhtien;
	
	String ngaydenhanTT;
	
	public ErpHoadon()
	{
		this.ID="";
		this.KYHIEU="";
		this.SOHOADON="";
		this.PARK="";
		this.NGAYHOADON="";
		this.SOTIENAVAT="0";
		this.SOTIENAVATVND="0";
		this.SOTIENBVAT="0";
		this.VAT="0";
		this.CHIETKHAU="0";
		this.TRANGTHAI="";
		this.TIENTE = "";
		this.mauhoadon = "";
		this.mausohoadon = "";
		this.soBL = "";
		this.ngayBL = "";
		
		this.ngaydenhanTT ="";
		
		this.pnkList = new ArrayList<IErpHoadonSp>();
	}
	public ErpHoadon(String id,String kyhieu,String thuesuat,String sohoadon,String park,String ngayhoadon,String sotienavat,String sotienbvat,String vat,String chietkhau,String trangthai, String tiente )
	{
		this.ID=id;
		this.KYHIEU=kyhieu;
		this.SOHOADON=sohoadon;
		this.PARK=park;
		this.NGAYHOADON=ngayhoadon;
		this.SOTIENAVAT=sotienavat;
		this.SOTIENBVAT=sotienbvat;
		this.VAT=vat;
		this.THUESUAT=thuesuat;
		this.CHIETKHAU=chietkhau;
		this.TRANGTHAI=trangthai;
		this.TIENTE = tiente;
		this.mauhoadon = "";
		this.mausohoadon = "";
		this.soBL = "";
		this.ngayBL = "";
		
		this.ngaydenhanTT ="";
		
		this.pnkList = new ArrayList<IErpHoadonSp>();
	}
	public String getId() 
	{	
		return this.ID;
	}

	public String getUserId() 
	{
		return this.USERID;
	}

	public void setUserId(String userId) 
	{
		this.USERID=userId;
	}
	
	public void setId(String id) 
	{
		this.ID=id;	
	}

	public String getKyhieu() 
	{
		
		return this.KYHIEU;
	}
	
	public void setKyhieu(String kyhieu) 
	{
		this.KYHIEU=kyhieu;
		
	}
	
	public String getSohoadon() 
	{
		
		return this.SOHOADON;
	}
	
	public void setSohoadon(String sohoadon) 
	{
		this.SOHOADON=sohoadon;
		
	}
	
	public String getPark() 
	{
		
		return this.PARK;
	}
	
	public void setPark(String park) 
	{
		this.PARK=park;
		
	}
	
	public String getNgayhoadon() 
	{
		
		return this.NGAYHOADON;
	}
	
	public void setNgayhoadon(String ngayhoadon) 
	{
		this.NGAYHOADON=ngayhoadon;
		
	}
	
	public String getSotienavat() 
	{
		
		return this.SOTIENAVAT;
	}
	
	public void setSotienavat(String sotienavat) 
	{
		
		this.SOTIENAVAT=sotienavat;
	}
	
	public String getSotienavatVND() 
	{
		
		return this.SOTIENAVATVND;
	}
	
	public void setSotienavatVND(String sotienavatVND) 
	{
		
		this.SOTIENAVATVND=sotienavatVND;
	}
	
	public String getSotienbvat() 
	{
		
		return this.SOTIENBVAT;
	}
	
	public void setSotienbvat(String sotienBvat) 
	{
		this.SOTIENBVAT=sotienBvat;
		
	}
	
	public String getVAT() 
	{
		
		return this.VAT;
	}

	public void setVAT(String vat) 
	{
		this.VAT=vat;
		
	}

	public String getChieckhau() 
	{
		
		return this.CHIETKHAU;
	}

	public void setChieckhau(String chieckhau) 
	{
		
		this.CHIETKHAU=chieckhau;
	}
	
	public String getTrangthai() 
	{
		
		return this.TRANGTHAI;
	}

	
	public void setTrangthai(String trangthai) 
	{
		
		this.TRANGTHAI=trangthai;
	}
	
	public String getTiente() 
	{
		
		return this.TIENTE;
	}

	
	public void setTiente(String tiente) 
	{
		
		this.TIENTE=tiente;
	}

	public String getThuesuat() 
	{
		return this.THUESUAT;
	}

	public void setThuesuat(String thuesuat) 
	{
		this.THUESUAT=thuesuat;
	}
	public List<IErpHoadonSp> getPnkList() 
	{
		return this.pnkList;
	}
	public void setPnkList(List<IErpHoadonSp> pnkList) 
	{
		this.pnkList=pnkList;
	}
	
	public String getMauhoadon() {
		
		return this.mauhoadon;
	}
	
	public void setMauhoadon(String mauhoadon) {
		
		this.mauhoadon = mauhoadon;
	}
	
	public String getMausohoadon() {
		
		return this.mausohoadon;
	}
	
	public void setMausohoadon(String mausohoadon) {
		
		this.mausohoadon = mausohoadon;
	}

	public String getSoBL() {
		
		return this.soBL;
	}
	
	public void setSoBL(String soBL) {
		
		this.soBL = soBL;
	}

	public String getNgayBL() {
		
		return this.ngayBL;
	}
	
	public void setNgayBL(String ngayBL) {
		
		this.ngayBL = ngayBL;
	}

	public String[] getLoai() {
		
		return this.loai;
	}
	
	public void setLoai(String[] loai) {
		
		this.loai = loai;
	}
		
	public String[] getPoId() {
		
		return this.poId;
	}
	
	public void setPoId(String[] PoId) {
		
		this.poId = PoId;
	}

	public String[] getMahang() {
		
		return this.mahang;
	}
	
	public void setMahang(String[] mahang) {
		
		this.mahang = mahang;
	}
	

	public String[] getDonvi() {
		
		return this.donvi;
	}
	
	public void setDonvi(String[] donvi) {
		
		this.donvi = donvi;
	}

	public String[] getSoluong() {
		
		return this.soluong;
	}
	
	public void setSoluong(String[] soluong) {
		
		this.soluong = soluong;
	}

	public String[] getDongia() {
		
		return this.dongia;
	}
	
	public void setDongia(String[] dongia) {
		
		this.dongia = dongia;
	}

	public String[] getThanhtien() {
		
		return this.thanhtien;
	}
	
	public void setThanhtien(String[] thanhtien) {
		
		this.thanhtien = thanhtien;
	}
	
	public String getNgaydenhanTT() 
	{
		
		return this.ngaydenhanTT;
	}
	
	public void setNgaydenhanTT(String ngaydenhanTT) 
	{
		
		this.ngaydenhanTT = ngaydenhanTT;
	}
}
