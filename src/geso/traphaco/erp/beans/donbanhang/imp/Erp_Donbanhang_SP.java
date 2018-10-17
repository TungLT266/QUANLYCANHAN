package geso.traphaco.erp.beans.donbanhang.imp;

import geso.traphaco.erp.beans.donbanhang.IErp_Donbanhang_SP;

public class Erp_Donbanhang_SP implements IErp_Donbanhang_SP 
{
	String Id;
	String IdSanPham;
	String TenSanPham;
	String tenXHD;
	double DonGia;
	double ChietKhau;
	double Vat;
	double SoLuong;
	double SoLuongChuan;
	double SoLuongDat;
	String MaSanPham;
	String DonViTinh;
	double thanhtien;
	String ctkmid;
	String SHEME;
	double soluongton;
	String quycach="";
	String trongluong = "";
	String thetich = "";
	String ngaydukiengiao = "";
	String ghichusp = "";
	String grossweight;
	String sopallet;
	String netweight;
	String dvEng;
	String weight;
	String Khoid;
	String dongiaCK;
	
	
	public Erp_Donbanhang_SP()
	{
		this.Id = "";
		this.IdSanPham = "";
		this.TenSanPham = "";
		this.tenXHD = "";
		this.DonGia = 0;
		this.ChietKhau = 0;
		this.Vat = 10;
		this.SoLuong = 0;
		this.SoLuongChuan = 0;
		this.SoLuongDat = 0;
		this.MaSanPham = "";
		this.DonViTinh = "";
		this.thanhtien = 0;
		this.ctkmid = "";
		this.SHEME = "";
		this.soluongton = 0;
		this.quycach="";
		this.trongluong = "";
		this.thetich = "";
		this.ngaydukiengiao = "";
		this.ghichusp = "";
		this.grossweight="";
		this.sopallet="";
		this.netweight="";
		this.dvEng="";
		this.weight="";
		this.Khoid="";
		this.dongiaCK="";
	}
	
	
	public String get_DongiaCK(){
		return this.dongiaCK;
	}
	public void set_DongiaCK(String dongiaCK){
		this.dongiaCK = dongiaCK;
	}
	
	
	public String getWeight(){
		return weight;
	}
	public void setWeight(String weight){
		this.weight = weight;
	}
	
	
	public String getDonviEng(){
		return this.dvEng;
	}
	public void setDonviEng(String dvEng){
		this.dvEng = dvEng;
	}
	
	
	public String getNetWeight(){
		return this.netweight;
	}
	public void setNetWeight(String netweight){
		this.netweight= netweight;
	}
	
	
	public String getSoPallet(){
		return this.sopallet;
	}
	public void setSoPallet(String sopallet){
		this.sopallet= sopallet;
	}
	
	
	
	
	public String getGrossWeight(){
		return this.grossweight;
	}
	
	
	public void setGrossWeight(String grossweight){
		this.grossweight= grossweight;
	}
	
	
	
	
	public String getGhichusp() {
		return ghichusp;
	}



	public void setGhichusp(String ghichusp) {
		this.ghichusp = ghichusp;
	}



	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	
	public String getIdSanPham() {
		
		return this.IdSanPham;
	}

	
	public void setIdSanPham(String idsanpham) {
		
		this.IdSanPham=idsanpham;
	}

	
	public String getTenSanPham() {
		
		return this.TenSanPham;
	}

	
	public void setTenSanPham(String tensanpham) {
		
		this.TenSanPham=tensanpham;
	}

	
	public void setSoLuong(double soluong) {
		
		this.SoLuong=soluong;
	}

	
	public double getSoLuong() {
		
		return this.SoLuong;
	}

	
	public double getDonGia() {
		
		return this.DonGia;
	}

	
	public void setDonGia(double dongia) {
		
		this.DonGia=dongia;
	}

	
	public void setVAT(double vat) {
		
		this.Vat=vat;
	}

	
	public double getVAT() {
		
		return this.Vat;
	}

	
	public void setChietKhau(double chietkhau) {
		
		this.ChietKhau=chietkhau;
	}

	
	public double getChietKhau() {
		
		return this.ChietKhau;
	}

	
	public double getThanhTien() {
		
		return (this.SoLuong* this.DonGia);
	}

	
	public String getMaSanPham() {
		
		return this.MaSanPham;
	}

	
	public void setMaSanPham(String masanpham) {
		
		this.MaSanPham=masanpham;
	}

	
	public void setDonViTinh(String donvitinh) {
		
		this.DonViTinh=donvitinh;
	}

	
	public String getDonViTinh() {
		
		return this.DonViTinh;
	}

	
	public void setSoLuongDat(double soluongdat) {
		
		this.SoLuongDat=soluongdat;
	}

	
	public double getSoLuongDat() {
		
		return this.SoLuongDat;
	}

	
	public void setThanhTien(double thanhtien) {
		
		this.thanhtien=thanhtien;
	}

	
	public void setCTKMID(String _ctkmid) {
		
		this.ctkmid=_ctkmid;
	}

	
	public String getCTKMId() {
		
		return this.ctkmid;
	}

	
	public void setSoluongton(double slton) {
		
		this.soluongton=slton;
	}

	
	public double getsoluongton() {
		
		return this.soluongton;
	}

	
	public void setSHEME(String ctkmid) 
	{
		this.SHEME=ctkmid;
	}
 
	public String getSHEME() {
		
		return this.SHEME;
	}
 
	public String getTrongluong() 
	{
		return this.trongluong;
	}


	public void setTrongluong(String trongluong) 
	{
		this.trongluong = trongluong;
	}


	public String getThetich() 
	{
		return this.thetich;
	}

	public void setThetich(String thetich) 
	{
		this.thetich = thetich;
	}

	public String getTenXuatHoaDon() 
	{
		return this.tenXHD;
	}

	public void setTenXuatHoaDon(String tensanpham)
	{
		this.tenXHD = tensanpham;
	}
	
	public String getNgaydukiengiao() {
		
		return this.ngaydukiengiao;
	}


	public void setNgaydukiengiao(String ngaydukiengiao) {
		
		this.ngaydukiengiao = ngaydukiengiao;
	}

	public void setSoLuongChuan(double soluongChuan) {
		
		this.SoLuongChuan = soluongChuan;
	}

	public double getSoLuongChuan() {
		
		return this.SoLuongChuan;
	}

	public String getQuycach() {
	
		return this.quycach;
	}

	public void setQuycach(String quycach) {
		this.quycach=quycach;
		
	}

	@Override
	public String getKhoid() {
		// TODO Auto-generated method stub
		return this.Khoid;
	}

	@Override
	public void setKhoid(String khoid) {
		// TODO Auto-generated method stub
		this.Khoid=khoid;
	}
	
	
}
