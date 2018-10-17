package geso.traphaco.erp.beans.hoadon.imp;

import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;

public class ErpHoanDon_SP implements IErpHoaDon_SP
{
	String Id;
	String IdSanPham;
	String TenSanPham;
	String TenXHD;
	
	String dvkdId = "";

	/**
	 * Dành làm khóa group các sản phẩm DVKD CORE cùng Mã lớn và cùng quy cách dktrong x dau lon x dai x dau nho x day (trừ mẫu in & màu sắc)
	 */
	String QuyCachGroup = "";
	
	double DonGia;
	double DonGiaViet;
	double ChietKhau;
	double Vat;
	double SoLuong;
	int SoLuongDat;
	String MaSanPham;
	String DonViTinh;
	String DonViTinhEng;
	double thanhtien;
	double thanhtien1;
	String ctkmid;
	double GiaNet;
	String kichthuoc, trongluong, quycach,dinhluong;
	int quydoi;
	String loaisanphm;
	String quydoiStr;
	String soluongchuan;
	
	String ghichu1 = "", ghichu2 = "", ghichu3 = "";
	String ten1 = "", ten2 = "", ten3 = "";
	String in = "1";
	String donViInAn = "";
	String netweight;
	String weight;
	String dvtSP;
	String weightquydoi;

	public ErpHoanDon_SP()
	{
		this.Id = "";
		this.IdSanPham = "";
		this.TenSanPham = "";
		this.TenXHD = "";
		this.DonGia = 0;
		this.DonGiaViet = 0;
		this.ChietKhau = 0;
		this.Vat = 0;
		this.SoLuong = 0;
		this.SoLuongDat = 0;
		this.MaSanPham = "";
		this.DonViTinh = "";
		this.thanhtien = 0;
		this.thanhtien1 = 0;
		this.ctkmid = "";
		this.GiaNet = 0;
		this.quydoi = 1;
		this.loaisanphm = "";
		this.quydoiStr = "";
		this.soluongchuan="";
		this.netweight="";
		this.weight="";
		this.dvtSP="";
		this.weightquydoi="";
	}

	public String getWeightQuydoi(){
		return this.weightquydoi;
	}
	public void setWeightQuydoi(String weightquydoi){
		this.weightquydoi= weightquydoi;
	}
	
	
	
	public String getDonvitinhSP(){
		return this.dvtSP;
	}
	public void setDonvitinhSP(String dvtSP){
		this.dvtSP=dvtSP;
	}
	
	
	public String getWeight(){
		return this.weight;
	}
	public void setWeight(String weight){
		this.weight= weight;
	}
	
	
	public String getNetWeight(){
		return this.netweight;
	}
	public void setNetWeight(String netweight){
		this.netweight = netweight;
	}
	
	
	public String getId()
	{

		return this.Id;
	}

	public void setId(String id)
	{

		this.Id = id;
	}

	public String getIdSanPham()
	{

		return this.IdSanPham;
	}

	public void setIdSanPham(String idsanpham)
	{

		this.IdSanPham = idsanpham;
	}

	public String getTenSanPham()
	{

		return this.TenSanPham;
	}

	public void setTenSanPham(String tensanpham)
	{

		this.TenSanPham = tensanpham;
	}

	public void setSoLuong(double soluong)
	{

		this.SoLuong = soluong;
	}

	public double getSoLuong()
	{

		return this.SoLuong;
	}

	public double getDonGia()
	{

		return this.DonGia;
	}

	public void setDonGia(double dongia)
	{

		this.DonGia = dongia;
	}

	public void setVAT(double vat)
	{

		this.Vat = vat;
	}

	public double getVAT()
	{

		return this.Vat;
	}

	public void setChietKhau(double chietkhau)
	{

		this.ChietKhau = chietkhau;
	}

	public double getChietKhau()
	{

		return this.ChietKhau;
	}

	public double getThanhTien()
	{

		return (this.SoLuong * this.DonGia);
	}
	
	public double getThanhTien1()
	{

		return this.thanhtien1;
	}

	public String getMaSanPham()
	{

		return this.MaSanPham;
	}

	public void setMaSanPham(String masanpham)
	{

		this.MaSanPham = masanpham;
	}

	public void setDonViTinh(String donvitinh)
	{

		this.DonViTinh = donvitinh;
	}

	public String getDonViTinh()
	{

		return this.DonViTinh;
	}

	public void setDonViTinhEng(String donvitinh)
	{

		this.DonViTinhEng = donvitinh;
	}

	public String getDonViTinhEng()
	{

		return this.DonViTinhEng;
	}

	public void setSoLuongDat(int soluongdat)
	{

		this.SoLuongDat = soluongdat;
	}

	public int getSoLuongDat()
	{

		return this.SoLuongDat;
	}

	public void setThanhTien(double thanhtien)
	{

		this.thanhtien = thanhtien;
	}
	
	public void setThanhTien1(double thanhtien1)
	{

		this.thanhtien1 = thanhtien1;
	}

	public void setCTKMID(String _ctkmid)
	{

		this.ctkmid = _ctkmid;
	}

	public String getCTKMId()
	{

		return this.ctkmid;
	}

	public int getQuyDoi()
	{

		return this.quydoi;
	}

	public void setQuyDoi(int _quydoi)
	{

		this.quydoi = _quydoi;
	}

	public double getGiaNet()
	{

		return this.GiaNet;
	}

	public void setGiaNet(double gianet)
	{

		this.GiaNet = gianet;
	}

	public String getLoaisp()
	{

		return this.loaisanphm;
	}

	public void setLoaisp(String loaisp)
	{

		this.loaisanphm = loaisp;
	}

	public String getTenXuatHoaDon()
	{
		return this.TenXHD;
	}

	public void setTenXuatHoaDon(String tenXuatHD)
	{
		this.TenXHD = tenXuatHD;
	}

	public String getKichthuoc()
	{

		return this.kichthuoc;
	}

	public void setKichthuoc(String kichthuoc)
	{
		this.kichthuoc=kichthuoc;
	}

	public String getTrongluong()
	{
		return this.trongluong;
	}

	public void setTrongluong(String trongluong)
	{	
		this.trongluong=trongluong;

	}

	public String getQuycach()
	{

		return this.quycach;
	}

	public void setQuycach(String quycach)
	{
		this.quycach=quycach;
	}

	
	public String getDinhluong()
	{
		return this.dinhluong;
	}

	
	public void setDinhluong(String dinhluong)
	{
		this.dinhluong=dinhluong;
		
	}

	
	public String getQuyDoiStr() {
		return this.quydoiStr;
	}

	
	public void setQuyDoiStr(String quydoi) {
		this.quydoiStr  = quydoi;
	}

	
	public double getDonGiaViet() {
		
		return this.DonGiaViet;
	}

	
	public void setDonGiaViet(double dongiaViet) {
		
		this.DonGiaViet = dongiaViet;
	}

	@Override
	public String getGhiChu1() {
		return this.ghichu1;
	}

	@Override
	public void setGhiChu1(String ghichu1) {
		this.ghichu1 = ghichu1;
	}

	@Override
	public String getGhiChu2() {
		return this.ghichu2;
	}

	@Override
	public void setGhiChu2(String ghichu2) {
		this.ghichu2 = ghichu2;
	}

	@Override
	public String getGhiChu3() {
		return this.ghichu3;
	}

	@Override
	public void setGhiChu3(String ghichu3) {
		this.ghichu3 = ghichu3;
	}

	@Override
	public String getTen1() {
		return this.ten1;
	}

	@Override
	public void setTen1(String ten) {
		this.ten1 = ten;
	}

	@Override
	public String getTen2() {
		return this.ten2;
	}

	@Override
	public void setTen2(String ten) {
		this.ten2 = ten;
	}

	@Override
	public String getTen3() {
		return this.ten3;
	}

	@Override
	public void setTen3(String ten) {
		this.ten3 = ten;
	}

	@Override
	/**
	 * Tính số dòng của sản phẩm, xài khi in hóa đơn tài chính. nuocngoai = true -> lấy tên 3, nuocngoai = false -> lấy tên 2
	 */
	public int getSoDongSanPham(boolean nuocngoai, int sokytu1dong) {
		int result = 0;
		
		if(sokytu1dong <= 0) sokytu1dong = 1;
		
		//Dòng sản phẩm
		result += ( nuocngoai ? (ten3 == null ? 0 : ten3.trim().length()) : (ten2 == null ? 0 : ten2.trim().length()) ) / sokytu1dong;
		
		//Dòng quy cách
		result += quycach == null ? 0 : quycach.trim().length() / sokytu1dong;
		
		//3 dòng ghi chú
		result += ghichu1 == null ? 0 : ghichu1.trim().length() / sokytu1dong;
		result += ghichu2 == null ? 0 : ghichu2.trim().length() / sokytu1dong;
		result += ghichu3 == null ? 0 : ghichu3.trim().length() / sokytu1dong;
		
		return result;
	}

	@Override
	public String getIn() {
		return this.in;
	}

	@Override
	public void setIn(String in) {
		this.in = in;
	}

	@Override
	public void setDonViInAn(String donviInAn) {
		this.donViInAn = donviInAn;
	}

	@Override
	public String getDonViInAn() {
		return this.donViInAn;
	}

	@Override
	public String getQuyCachGroup() {
		return this.QuyCachGroup;
	}

	@Override
	public void setQuyCachGroup(String qcGroup) {
		this.QuyCachGroup = qcGroup;
	}

	@Override
	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}

	@Override
	public String getDvkdId() {
		return this.dvkdId;
	}


	public String getSoluongchuan() {
		
		return this.soluongchuan;
	}


	public void setSoluongchuan(String soluongchuan) {
		this.soluongchuan= soluongchuan;
		
	}

}
