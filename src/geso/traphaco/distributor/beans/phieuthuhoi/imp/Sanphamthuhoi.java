package geso.traphaco.distributor.beans.phieuthuhoi.imp;

import java.util.List;
import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamSoLo;
import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamthuhoi;

public class Sanphamthuhoi implements ISanphamthuhoi{
	String id;
	String masp;
	String tensp;
	String soluong;
	String tonhientai;
	String donvitinh;
	String dongia;
	String ctkm;
	String chietkhau;
	String barcode;
	String quicach;
	
	String soluong1;
	String soluong2;
	String soluongThung;
	String SpId;
	String Ngayhethan;
	String SoLo;
	String Khoten;
	String KenhTen;
	String KhoId;
	String KBhID;
	List<ISanphamSoLo> spList;
	public Sanphamthuhoi()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.donvitinh = "";
		this.dongia = "";
		this.ctkm = "";
		this.chietkhau = "";
		this.tonhientai = "";
		this.barcode="";
		
		this.soluong1 = "";
		this.soluong2 = "";
		this.soluongThung = "";
	}
	
	public Sanphamthuhoi(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.donvitinh = param[4];
		this.dongia = param[5];
		this.ctkm = param[6];
		this.chietkhau = param[7];
		this.tonhientai = "";
		if(param.length >8){
			this.barcode=param[8];
			System.out.println("qui cach la:"+param[9]);
			this.quicach=param[9];
		}
		
		this.soluong1 = "";
		this.soluong2 = "";
		this.soluongThung = "";

	}
	
	public Sanphamthuhoi(String spId, String spMa, String spTen, String soluong, String dvt, String dongia, String ctkm, String chietkhau)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.donvitinh = dvt;
		this.dongia = dongia;
		this.ctkm = ctkm;
		this.chietkhau = chietkhau;
		this.tonhientai = "";
		this.soluong1 = "";
		this.soluong2 = "";
		this.soluongThung = "";
		
	}
	
	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}
	
	public String getMasanpham()
	{	
		return this.masp;
	}
	
	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}
	
	public String getTensanpham() 
	{	
		return this.tensp;
	}
	
	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}
	
	public String getDonvitinh() 
	{
		return this.donvitinh;
	}
	
	public void setDonvitinh(String donvitinh) 
	{
		this.donvitinh = donvitinh;		
	}

	public String getCTKM() 
	{
		return this.ctkm;
	}

	public void setCTKM(String ctkm) 
	{
		this.ctkm = ctkm;
	}

	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	public String getChietkhau()
	{
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
	}

	public String getTonhientai() 
	{
		return this.tonhientai;
	}

	public void setTonhientai(String tonhientai) 
	{
		this.tonhientai = tonhientai;
	}


	public String getBarcode() {

		return this.barcode;
	}

	public void setBarcode(String barcode_) {

		this.barcode=barcode_;
	}


	public void setQuicach(String quicach) {

		this.quicach=quicach;
	}


	public String getQuicach() {

		return this.quicach;
	}

	
	public void setSoluong1(String soluong1) {
		
		this.soluong1 = soluong1;
	}

	
	public String getSoluong1() {
		
		return this.soluong1;
	}

	
	public void setSoluong2(String soluong2) {
		
		this.soluong2 = soluong2;
	}

	
	public String getSoluong2() {
		
		return this.soluong2;
	}

	
	public String getSoluongThung() {
		
		return this.soluongThung;
	}

	
	public void setSoluongThung(String soluongThung) {
		
		this.soluongThung = soluongThung;
	}

	@Override
	public void setSPId(String spid) {
		// TODO Auto-generated method stub
		this.SpId= spid;
	}

	@Override
	public String getSPId() {
		// TODO Auto-generated method stub
		return this.SpId;
	}

	@Override
	public void setSolo(String solo) {
		// TODO Auto-generated method stub
		this.SoLo=solo.trim();
	}

	@Override
	public String getSOLo() {
		// TODO Auto-generated method stub
		return this.SoLo;
	}

	@Override
	public void setNgayHetHan(String ngayhethan) {
		// TODO Auto-generated method stub
		this.Ngayhethan= ngayhethan.trim();
	}

	@Override
	public String getNgayHetHan() {
		// TODO Auto-generated method stub
		return this.Ngayhethan;
	}

	@Override
	public void setKhoTen(String KhoTen) {
		// TODO Auto-generated method stub
		this.Khoten=KhoTen;
	}

	@Override
	public String getKhoTen() {
		// TODO Auto-generated method stub
		return this.Khoten;
	}

	@Override
	public void setKenhTen(String KenhTen) {
		// TODO Auto-generated method stub
		this.KenhTen=KenhTen;
	}

	@Override
	public String getKenhTen() {
		// TODO Auto-generated method stub
		return this.KenhTen;
	}

	@Override
	public List<ISanphamSoLo> getSPLoList() {
		// TODO Auto-generated method stub
		return this.spList;
	}

	@Override
	public void SetSpLoList(List<ISanphamSoLo> list) {
		// TODO Auto-generated method stub
		this.spList=list;
	}

	@Override
	public void setKhoId(String KhoId) {
		// TODO Auto-generated method stub
		this.KhoId=KhoId;
	}

	@Override
	public String getKhoId() {
		// TODO Auto-generated method stub
		return this.KhoId;
	}

	@Override
	public void setKenhId(String KenhId) {
		// TODO Auto-generated method stub
		this.KBhID=KenhId;
	}

	@Override
	public String getKenhId() {
		// TODO Auto-generated method stub
		return this.KBhID;
	}

}
