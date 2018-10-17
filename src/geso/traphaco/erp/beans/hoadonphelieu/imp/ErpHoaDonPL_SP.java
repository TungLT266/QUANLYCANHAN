package geso.traphaco.erp.beans.hoadonphelieu.imp;

import geso.traphaco.erp.beans.hoadonphelieu.IErpHoaDonPL_SP;

public class ErpHoaDonPL_SP implements IErpHoaDonPL_SP
{
	String Id;
	String IdSanPham;
	String TenSanPham;


	String SoLuong;
	String MaSanPham;
	String DonViTinh;
	String DonGia;
	String DonGiaDaGiam;
	String thanhtien;
	String thuesuat;
	String tienthue;
	String quycach;
	
	//Dùng cho hóa đơn điều chỉnh
	String idSP;
	String maSP;
	String tenSP;

	String in ="1";
	
	String ghichu1 = "", ghichu2 = "", ghichu3 = "";
	String ten1 = "", ten2 = "", ten3 = "";
	
	String IS_KHONGTHUE="0";

	public String getIS_KHONGTHUE() {
		return IS_KHONGTHUE;
	}

	public void setIS_KHONGTHUE(String iS_KHONGTHUE) {
		IS_KHONGTHUE = iS_KHONGTHUE;
	}


	public ErpHoaDonPL_SP()
	{
		this.Id = "";
		this.IdSanPham = "";
		this.TenSanPham = "";
		this.DonGiaDaGiam="";
		this.idSP = "";
		this.maSP = "";
		this.tenSP = "";
		this.SoLuong = "";
		this.DonGia = "";
		this.MaSanPham = "";
		this.DonViTinh = "";
		this.thanhtien = "";

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

	public void setSoLuong(String soluong)
	{

		this.SoLuong = soluong;
	}

	public String getSoLuong()
	{

		return this.SoLuong;
	}

	public String getDonGia()
	{

		return this.DonGia;
	}

	public void setDonGia(String dongia)
	{

		this.DonGia = dongia;
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


	public String getThanhTien() 
	{
		return this.thanhtien;
	}


	public void setThanhTien(String thanhtien) 
	{
		this.thanhtien = thanhtien;
		
	}


	public String getIn() 
	{
		return this.in;
	}

	public void setIn(String in) 
	{
		this.in = in;
		
	}

	public String getDonGiaDaGiam() {
		return this.DonGiaDaGiam;
	}

	public void setDonGiaDaGiam(String dongiadagiam) {
		this.DonGiaDaGiam=dongiadagiam;
	}

	@Override
	public void setIdSP(String idSP) {
		// TODO Auto-generated method stub
		this.idSP = idSP;
	}

	@Override
	public String getIdSP() {
		// TODO Auto-generated method stub
		return this.idSP;
	}

	@Override
	public void setMaSP(String maSP) {
		// TODO Auto-generated method stub
		this.maSP = maSP;
	}

	@Override
	public String getMaSP() {
		// TODO Auto-generated method stub
		return this.maSP;
	}

	@Override
	public void setTenSP(String tenSP) {
		// TODO Auto-generated method stub
		this.tenSP = tenSP;
	}

	@Override
	public String getTenSP() {
		// TODO Auto-generated method stub
		return this.tenSP;
	}


	public String getThuesuat() {
		return thuesuat;
	}

	public void setThuesuat(String thuesuat) {
		this.thuesuat = thuesuat;
	}

	public String getTienthue() {
		return tienthue;
	}

	public void setTienthue(String tienthue) {
		this.tienthue = tienthue;
	}


}
