package geso.traphaco.erp.beans.shiphang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.shiphang.ISanpham;

public class Sanpham implements ISanpham
{
	String stt;
	String id;
	String masp;
	String diengiai;
	String soluongdat;
	String soluongnhan;
	String ngaynhandukien;
	String hansudung;
	String dungsai;
	String conlai;
	String Dvdl;
	String dongia;
	String dongiaVND;
	String thanhtien;
	String thanhtienVND;
	String thanhtienVATVND;
	
	String loaisp;
	
	String tiente;
	String tigiaquidoi;
	String dongiaViet;
	
	String khonhanId;
	String khonhanTen;
	String songayluukho;
	
	String soluongDaNhan;
	String soluongdaship;
	String soluongship;
	String soluongMaxNhan;
	ResultSet khoNhanRs;
	
	String thetich;
	String trongluong;
	
	String soPO;
	
	String muahang_fk;
	String khuId;
	ResultSet rsKhu;
	boolean IsKhoNhanQuanLyKhuvuc;
	
	String vat;
	
	List<ISpDetail> spDetail;
	
	String idmarquette;
	String taiKhoan;
	private long soLuong;
	private double soLuong2;
	public Sanpham()
	{
		this.stt = "";
		this.IsKhoNhanQuanLyKhuvuc=false;
		this.id = "";
		this.masp = "";
		this.diengiai = "";
		this.soluongdat = "";
		this.soluongnhan = "";
		this.ngaynhandukien = "";
		this.dungsai = "0";
		this.conlai = "";
		this.Dvdl = "";
		this.dongia = "";
		this.loaisp="";
		
		this.tiente = "";
		this.tigiaquidoi = "";
		this.dongiaViet = "";
		
		this.khonhanId = "";
		this.khonhanTen = "";
		this.songayluukho = "";
		
		this.soluongDaNhan = "0";
		this.soluongdaship = "0"; 
		this.soluongMaxNhan = "";

		this.thetich = "";
		this.trongluong = "";
		
		this.soPO = "";
		this.muahang_fk = "";
		this.khuId="";
		
		this.thanhtien ="";
		
		this.dongiaVND = "";
		this.thanhtienVND = "";
		this.thanhtienVATVND = "";
		this.vat = "";
		this.spDetail = new ArrayList<ISpDetail>();
		this.idmarquette = "";
		this.taiKhoan = "";
		this.soLuong = 0;
		this.soLuong2 = 0.0;
	}
	
	public Sanpham(String id, String masp, String ten, String soluong, String hansudung, String ngaynhandukien, String soluongdat,String dvdl)
	{
		this.stt = "";
		this.id = id;
		this.masp = masp;
		this.diengiai = ten;
		this.soluongnhan = soluong;
		this.hansudung = hansudung;
		this.ngaynhandukien = ngaynhandukien;
		this.soluongdat = soluongdat;
		this.dungsai = "0";
		this.conlai = "";
		this.Dvdl = dvdl;
		this.dongia = "";
		
		this.tiente = "";
		this.tigiaquidoi = "";
		this.dongiaViet = "";
		
		this.songayluukho = "";
		this.khonhanId = "";
		this.khonhanTen = "";
		
		this.soluongDaNhan = "0";
		this.soluongdaship = "0";
		this.soluongMaxNhan = "";

		this.thetich = "";
		this.trongluong = "";
		
		this.soPO = "";
		this.muahang_fk = "";
		this.vat = "";
		this.spDetail = new ArrayList<ISpDetail>();
		this.idmarquette = "";
		this.dongiaVND = "";
		this.thanhtienVND = "";
		this.thanhtienVATVND = "";
		this.taiKhoan = "";
		this.soLuong = 0;
		this.soLuong2 = 0.0;
		
	}
	@Override
	public double getSoLuong2() {
		return soLuong2;
	}

	@Override
	public void setSoLuong2(double soLuong2) {
		this.soLuong2 = soLuong2;
	}

	public String getThanhtienVATVND() {
		return thanhtienVATVND;
	}

	public void setThanhtienVATVND(String thanhtienVATVND) {
		this.thanhtienVATVND = thanhtienVATVND;
	}

	public String getDongiaVND() {
		return dongiaVND;
	}

	public void setDongiaVND(String dongiaVND) {
		this.dongiaVND = dongiaVND;
	}

	public String getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(String thanhtien) {
		this.thanhtien = thanhtien;
	}

	public String getThanhtienVND() {
		return thanhtienVND;
	}

	public void setThanhtienVND(String thanhtienVND) {
		this.thanhtienVND = thanhtienVND;
	}

	public String getIdmarquette() {
		return idmarquette;
	}

	public void setIdmarquette(String idmarquette) {
		this.idmarquette = idmarquette;
	}

	public String getMuahang_fk() {
		return muahang_fk;
	}

	public void setMuahang_fk(String muahang_fk) {
		this.muahang_fk = muahang_fk;
	}

	public String getSoPO() {
		return soPO;
	}

	public void setSoPO(String soPO) {
		this.soPO = soPO;
	}

	public String getStt()
	{
		return this.stt;
	}
	
	public void setStt(String stt)
	{
		this.stt = stt;
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
		return this.masp;
	}
	
	public void setMa(String masp)
	{
		this.masp = masp;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public String getSoluongnhan()
	{
		return this.soluongnhan;
	}
	
	public void setSoluongnhan(String soluongnhan)
	{
		this.soluongnhan = soluongnhan;
	}
	
	public String getHansudung()
	{
		return this.hansudung;
	}
	
	public void setHansudung(String hansudung)
	{
		this.hansudung = hansudung;
	}
	
	public String getNgaynhandukien()
	{
		return this.ngaynhandukien;
	}
	
	public void setNgaynhandukien(String ngaynhandukien)
	{
		this.ngaynhandukien = ngaynhandukien;
	}
	
	public String getSoluongdat()
	{
		return this.soluongdat;
	}
	
	public void setSoluongdat(String soluongdat)
	{
		this.soluongdat = soluongdat;
	}
	
	public String getDungsai()
	{
		return this.dungsai;
	}
	
	public void setDungsai(String dungsai)
	{
		this.dungsai = dungsai;
	}
	
	public String getConlai()
	{
		return this.conlai;
	}
	
	public void setCOnlai(String conlai)
	{
		this.conlai = conlai;
	}
	
	public String getDvdl()
	{
		
		return this.Dvdl;
	}
	
	public void setDvdl(String Dvdl)
	{
		this.Dvdl = Dvdl;
		
	}
	
	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	public String getTiente()
	{
		return this.tiente;
	}

	public void setTiente(String tiente) 
	{
		this.tiente = tiente;
	}
	
	public String getTigiaquydoi() 
	{
		return this.tigiaquidoi;
	}

	public void setTigiaquydoi(String tigiaquydoi) 
	{
		this.tigiaquidoi = tigiaquydoi;
	}

	public String getDongiaViet() 
	{
		return this.dongiaViet;
	}

	public void setDongiaViet(String dongiaViet) 
	{
		this.dongiaViet = dongiaViet;
	}

	
	public String getKhonhanId() {
		
		return this.khonhanId;
	}

	
	public void setKhonhanId(String khonhan) {
		
		this.khonhanId = khonhan;
	}

	
	public String getKhonhanTen() {
		
		return this.khonhanTen;
	}

	
	public void setKhonhanTen(String khonhanTen) {
		
		this.khonhanTen = khonhanTen;
	}

	public String getSoluongMaxNhan() 
	{
		return this.soluongMaxNhan;
	}

	public void setSoluongMaxNhan(String soluongMaxNhan) 
	{
		this.soluongMaxNhan = soluongMaxNhan;
	}

	public String getSoluongDaNhan() 
	{
		return this.soluongDaNhan;
	}

	public void setSoluongDaNhan(String soluongDaNhan) 
	{
		this.soluongDaNhan = soluongDaNhan;
	}

	public ResultSet getKhoNhanRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNhanRs(ResultSet khonhanRs) 
	{
		this.khoNhanRs = khonhanRs;
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


	public String getloaisp() {
	
		return this.loaisp;
	}
 
	public void setloaisp(String loaisp) 
	{
		this.loaisp= loaisp;
	}
 

	public boolean getIskhoQL_khuvuc()
	{
		return this.IsKhoNhanQuanLyKhuvuc;
	}

	public void setIskhoQL_khuvuc(boolean bien) 
	{
		this.IsKhoNhanQuanLyKhuvuc= bien;
	}

	public ResultSet getKhuRs() 
	{
		return this.rsKhu;
	}

	public void setKhoKhuRs(ResultSet KhuRs) 
	{
		this.rsKhu=KhuRs;
	}

	public String getKhuId() 
	{
		return this.khuId;
	}


	public void setKhuId(String _khuId) 
	{
		this.khuId= _khuId;
	}
 
	
	public String getthanhtien() 
	{
		
		return this.thanhtien;
	}

	
	public void setthanhtien(String thanhtien) 
	{
		
		this.thanhtien = thanhtien;
	}
	
	public String getSongayluukho() 
	{		
		return this.songayluukho;
	}

	
	public void setSongayluukho(String songayluukho)
	{		
		this.songayluukho = songayluukho;
	}

	@Override
	public String getSoluongdaship() {
		return this.soluongdaship;
	}

	@Override
	public void setSoluongdaship(String soluongdaship) {
		this.soluongdaship = soluongdaship;
	}

	@Override
	public String getSoluongship() {
		return this.soluongship;
	}

	@Override
	public void setSoluongship(String soluongship) {
		this.soluongship = soluongship;
	}

	@Override
	public String getVat() {
		return this.vat;
	}

	@Override
	public void setVat(String vat) {
		this.vat = vat;
	}

	@Override
	public List<ISpDetail> getSpDetail() {
		return this.spDetail;
	}

	@Override
	public void setSpDetail(List<ISpDetail> spDetail) {
		this.spDetail = spDetail;
	}

	@Override
	public String getTaiKhoan() {
		return taiKhoan;
	}

	@Override
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public long getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(long soLuong) {
		this.soLuong = soLuong;
	}
	
	
}
