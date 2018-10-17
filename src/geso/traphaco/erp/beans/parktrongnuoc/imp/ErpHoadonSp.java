package geso.traphaco.erp.beans.parktrongnuoc.imp;
import java.util.List;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadonSp;

public class ErpHoadonSp implements IErpHoadonSp
{

	String POID, POTEN, ID, sanphamId, ten, soluong, soluongRaHD, dongia, thanhtien, CHON, hoantat, VAT, TIENTE , donvi, loai, sanphamma, dungsai, soluonghd, ngaynhan, tienvat, solo;

	List<IErpHoadonSp> pknList;
	public ErpHoadonSp()
	{
		this.ID = "";
		this.POID = "";
		this.POTEN = "";
		this.sanphamId="";
		this.ten="";
		this.soluong="";
		this.dongia="";
		this.thanhtien = "";
		this.donvi="";
		this.loai = "";
		this.CHON = "0";
		this.hoantat = "0";
		this.sanphamma = "";
		this.dungsai = "";
		this.soluonghd = "";
		this.ngaynhan = "";
		this.TIENTE = "";
		this.VAT = "";
		this.soluongRaHD = "";
		this.tienvat = "";
	}
	public ErpHoadonSp(String poId,String poTen, String sanphamId, String ten,String soluong,String dongia,String thanhtien,String donvi,String loai, String chon, String tiente)
	{
		this.ID = "";
		this.POID = poId;
		this.POTEN = poTen;
		this.sanphamId = sanphamId;
		this.ten = ten;
		this.soluong = soluong;
		this.dongia = dongia;
		this.thanhtien = thanhtien;
		this.donvi = donvi;
		this.loai = loai;
		this.loai = "";
		this.CHON = "0";
		this.hoantat = "0";
		this.sanphamma = "";
		this.dungsai = "";
		this.TIENTE = tiente;
		this.VAT = "";
		this.soluongRaHD = "";
	}
	public String getId() 
	{
		
		return this.ID;
	}

	
	public void setId(String Id) 
	{
		this.ID=Id;
		
	}

	public String getPoId() 
	{
		
		return this.POID;
	}

	
	public void setPoId(String PoId) 
	{
		this.POID=PoId;
		
	}
	
	public void setChon(String chon)
	{
		this.CHON = chon;
	}
	
	public String getChon()
	{
		return this.CHON;
	}

	public List<IErpHoadonSp> getPhieunhapkhoList() 
	{	
		return this.pknList;
	}
	public void close() 
	{
		
	}

	public String getTiente() 
	{
		return this.TIENTE;
	}
	public void setTiente(String tiente) 
	{
		this.TIENTE=tiente;
	}

	public int compareTo(IErpHoadonSp arg0) 
	{
		if(this.POID.length() > 0)
		{
			if(Integer.parseInt(this.POID) >= Integer.parseInt(arg0.getPoId()))
				return 1;
		}		
		return -1;
	}
	
	public String getVAT() {
		
		return this.VAT;
	}
	
	public void setVAT(String vat) {
		
		this.VAT = vat;
	}

	public void setHoanTat(String chon) {
		
		this.hoantat = chon;
	}

	public String getHoanTat() {

		return this.hoantat;
	}
	
	public String getSoluong() 
	{
		return this.soluong;
	}
	
	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	public String getDonvi() 
	{
		return this.donvi ;
	}

	public void setDonvi(String donvi) 
	{
		this.donvi = donvi;
	}

	public String getThanhtien() 
	{
		return this.thanhtien ;
	}

	public void setThanhtien(String thanhtien) 
	{
		this.thanhtien = thanhtien;
	}
	
	public String getSanphamId() 
	{
		return this.sanphamId;
	}

	public void setSanphamId(String sanphamId) 
	{
		this.sanphamId = sanphamId;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}

	public String getPoTen() {
		return this.POTEN;
	}

	public void setPoTen(String poTen) {

		this.POTEN = poTen;
	}

	public String getSanphamMa() {
		return this.sanphamma;
	}

	public void setSanphamMa(String sanphamMa) {
		this.sanphamma = sanphamMa;
	}

	public String getDungsai() {
		return this.dungsai;
	}

	public void setDungsai(String dungsai) {
		this.dungsai = dungsai;
	}

	public String getHoantat() {
		return this.hoantat;
	}

	public void setHoantat(String hoantat) {
		this.hoantat = hoantat;
	}
	
	public String getSoluonghd() {
		
		return this.soluonghd;
	}
	
	public void setSoluonghd(String soluonghd) {
		
		this.soluonghd = soluonghd;
	}
	
	public String getSoluongRaHD() {
		
		return this.soluongRaHD;
	}
	
	public void setSoluongRaHD(String soluongRaHD) {
		
		this.soluongRaHD = soluongRaHD;
	}
	
	public String getNgaynhan() {
		
		return this.ngaynhan;
	}
	
	public void setNgaynhan(String ngaynhan) {
		
		this.ngaynhan = ngaynhan;
	}
	
	public String getTienVat() {
	
		return this.tienvat;
	}
	
	public void setTienVat(String tienvat) {
	
		this.tienvat = tienvat;
	}
	
	public String getSolo() {
		
		return this.solo;
	}
	
	public void setSolo(String solo) {
		
		this.solo = solo;
	}
	
	
}
