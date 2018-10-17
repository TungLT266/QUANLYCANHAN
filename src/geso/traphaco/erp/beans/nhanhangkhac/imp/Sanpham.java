package geso.traphaco.erp.beans.nhanhangkhac.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhanhangkhac.ISanpham;
import geso.traphaco.erp.beans.nhanhangkhac.ISpDetail;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.DetailMeSp;

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
	String thanhtien;
	String loaisp;
	
	String tiente;
	String tigiaquidoi;
	String dongiaViet;
	
	String khonhanId;
	String khonhanTen;
	
	String soluongDaNhan;
	String soluongMaxNhan;
	ResultSet khoNhanRs;
	
	String thetich;
	String trongluong;
	
	String soPO;
	
	String muahang_fk;
	String khuId;
	ResultSet rsKhu;
	List<ISpDetail> spDetail;
	boolean IsKhoNhanQuanLyKhuvuc;
	
	List<DetailMeSp> listDetailMeSp;
	String isKiemDinh;
	
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
		
		this.soluongDaNhan = "0";
		this.soluongMaxNhan = "";

		this.thetich = "";
		this.trongluong = "";
		
		this.soPO = "";
		this.muahang_fk = "";
		this.khuId="";
		
		this.thanhtien ="";
		this.isKiemDinh = "";
		this.listDetailMeSp = new ArrayList<DetailMeSp>();
		this.spDetail = new ArrayList<ISpDetail>();
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
		
		this.khonhanId = "";
		this.khonhanTen = "";
		
		this.soluongDaNhan = "0";
		this.soluongMaxNhan = "";

		this.thetich = "";
		this.trongluong = "";
		
		this.soPO = "";
		this.muahang_fk = "";
		this.isKiemDinh = "";
		this.listDetailMeSp = new ArrayList<DetailMeSp>();
		this.spDetail = new ArrayList<ISpDetail>();
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
	
	public List<ISpDetail> getSpDetail()
	{
		return this.spDetail;
	}
	
	public void setSpDetail(List<ISpDetail> spDetail)
	{
		this.spDetail = spDetail;
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
 
	public void setloaisp(String loaisp) {
		this.loaisp= loaisp;
	}
 

	@Override
	public boolean getIskhoQL_khuvuc() {
		// TODO Auto-generated method stub
		return this.IsKhoNhanQuanLyKhuvuc;
	}

	@Override
	public void setIskhoQL_khuvuc(boolean bien) {
		// TODO Auto-generated method stub
		this.IsKhoNhanQuanLyKhuvuc= bien;
	}

	@Override
	public ResultSet getKhuRs() {
		// TODO Auto-generated method stub
		return this.rsKhu;
	}

	@Override
	public void setKhoKhuRs(ResultSet KhuRs) {
		// TODO Auto-generated method stub
		this.rsKhu=KhuRs;
	}

	@Override
	public String getKhuId() {
		// TODO Auto-generated method stub
		return this.khuId;
	}

	@Override
	public void setKhuId(String _khuId) {
		// TODO Auto-generated method stub
		this.khuId= _khuId;
	}
 
	
	public String getthanhtien() {
		
		return this.thanhtien;
	}

	
	public void setthanhtien(String thanhtien) {
		
		this.thanhtien = thanhtien;
	}
	
	public List<DetailMeSp> getListDetailMeSp() {
		return listDetailMeSp;
	}

	public void setListDetailMeSp(List<DetailMeSp> listDetailMeSp) {
		this.listDetailMeSp = listDetailMeSp;
	}
	
	public String getIsKiemDinh() {
		return isKiemDinh;
	}

	public void setIsKiemDinh(String isKiemDinh) {
		this.isKiemDinh = isKiemDinh;
	}
	
}
