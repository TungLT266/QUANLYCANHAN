package geso.traphaco.erp.beans.timnhacc.imp;
import geso.traphaco.erp.beans.timnhacc.INhacungcap;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;

public class Nhacungcap implements INhacungcap
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String ma;
	String ten;
	String nguoilienhe;
	String diachi;
	String dienthoai;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;

	String tucach;
	String uytin;
	String chatluongsp;
	String thoigiangiaohang;
	String giaca;
	String phuongthuctt;
	String phuongthucvc;
	String haumai;
	String chinhsachkhac;
	
	String dongia;
	String sopo;
	String tongluong;
	String chon;
	dbutils db;
	
	public Nhacungcap()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.diachi = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nguoilienhe = "";
		this.dienthoai = "";

		this.tucach = "0";
		this.uytin = "0";
		this.chatluongsp = "0";
		this.thoigiangiaohang = "0";
		this.giaca = "";
		this.phuongthuctt = "";
		this.phuongthucvc = "";
		this.haumai = "";
		this.chinhsachkhac = "";
		
		this.sopo = "";
		this.tongluong = "";
		this.chon = "0";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String ma)
	{
		this.ma = ma;
	}
	
	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	@Override
	public String getNguoilienhe() {
		
		return this.nguoilienhe;
	}

	@Override
	public void setNguoilienhe(String nglienhe) {
		
		this.nguoilienhe = nglienhe;
	}

	@Override
	public String getDienthoai() {
		
		return this.dienthoai;
	}

	@Override
	public void setDienthoai(String dienthoai) {
		
		this.dienthoai = dienthoai;
	}

	@Override
	public String getTucach() {
		
		return this.tucach;
	}
	@Override
	public void setTucach(String tucach) {
		
		this.tucach = tucach;
	}
	@Override
	public String getUytin() {
		
		return this.uytin;
	}
	@Override
	public void setUytin(String uytin) {
		
		this.uytin = uytin;
	}
	@Override
	public String getChatluong() {
		
		return this.chatluongsp;
	}
	@Override
	public void setChatluong(String chatluong) {
		
		this.chatluongsp = chatluong;
	}
	@Override
	public String getGiaca() {
		
		return this.giaca;
	}
	@Override
	public void setGiaca(String giaca) {
		
		this.giaca = giaca;
	}
	@Override
	public String getPtthanhtoan() {
		
		return this.phuongthuctt;
	}
	@Override
	public void setPtthanhtoan(String ptthanhtoan) {
		
		this.phuongthuctt = ptthanhtoan;
	}
	@Override
	public String getPtvanchuyen() {
		
		return this.phuongthucvc;
	}
	@Override
	public void setPtvanchuyen(String ptvanchuyen) {
		
		this.phuongthucvc = ptvanchuyen;
	}
	@Override
	public String getTggiaohang() {
		
		return this.thoigiangiaohang;
	}
	@Override
	public void setTggiaohang(String tggiaohang) {
		
		this.thoigiangiaohang = tggiaohang;
	}
	@Override
	public String getHaumai() {
		
		return this.haumai;
	}
	@Override
	public void setHaumai(String haumai) {
		
		this.haumai = haumai;
	}
	@Override
	public String getChinhsachkhac() {
		
		return this.chinhsachkhac;
	}
	@Override
	public void setChinhsachkhac(String chinhsachkhac) {
		
		this.chinhsachkhac = chinhsachkhac;
	}
	@Override
	public String getSopo() {
		
		return this.sopo;
	}
	@Override
	public void setSopo(String sopo) {
		
		this.sopo = sopo;
	}
	@Override
	public String getTongluong() {
		
		return this.tongluong;
	}

	@Override
	public void setTongluong(String tongluong) {
		
		this.tongluong = tongluong;
	}

	@Override
	public String getChon() {
		
		return this.chon;
	}

	@Override
	public void setChon(String chon) {
		
		this.chon = chon;
	}

	@Override
	public String getDongia() {
		
		return this.dongia;
	}

	@Override
	public void setDongia(String dongia) {
		
		this.dongia = dongia;
	}
}
