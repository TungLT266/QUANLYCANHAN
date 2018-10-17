package geso.traphaco.erp.beans.nhapkhonhamay.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhapkhonhamay.ISpDetail;
import geso.traphaco.erp.beans.nhapkhonhamay.ISanpham;

public class Sanpham implements ISanpham
{
	String stt;
	String id;
	String masp;
	String diengiai;
	String soluongdat;
	String soluongnhan;
	String ngaynhan;
	String Dvdl;
	String loaisp;
	String soPO;
	String gia;
	String muahang_fk;
	String nhomkenh;
	String thuexuat;
	String dungsai;
	String hansudung;
	List<ISpDetail> spDetail;
	
	public Sanpham()
	{
		this.stt = "";
		this.id = "";
		this.masp = "";
		this.diengiai = "";
		this.soluongdat = "";
		this.soluongnhan = "";
		this.Dvdl = "";
		this.loaisp="";
		this.soPO = "";
		this.gia = "";
		this.muahang_fk = "";
		this.nhomkenh = "";
		this.thuexuat = "0";
		this.dungsai = "0";
		this.hansudung = "0";
		this.spDetail = new ArrayList<ISpDetail>();
	}
	
	public Sanpham(String id, String masp, String ten, String soluong, String soluongdat,String dvdl, String ngaynhan, String gia)
	{
		this.stt = "";
		this.id = id;
		this.masp = masp;
		this.diengiai = ten;
		this.soluongnhan = soluong;
		this.gia = gia;
		this.soluongdat = soluongdat;
		
		this.Dvdl = dvdl;
		
		this.soPO = "";
		this.muahang_fk = "";
		this.nhomkenh = "";
		this.thuexuat = "0";
		this.hansudung = "0";
		this.spDetail = new ArrayList<ISpDetail>();
	}
	
	public List<ISpDetail> getSpDetail()
	{
		return this.spDetail;
	}
	
	public void setSpDetail(List<ISpDetail> spDetail)
	{
		this.spDetail = spDetail;
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
	
	public String getDvdl()
	{
		
		return this.Dvdl;
	}
	
	public void setDvdl(String Dvdl)
	{
		this.Dvdl = Dvdl;
		
	}

	public String getloaisp() {
	
		return this.loaisp;
	}
 
	public void setloaisp(String loaisp) {
		this.loaisp= loaisp;
	}

	@Override
	public String getNgaynhandukien() {
		return this.ngaynhan;
	}

	@Override
	public void setNgaynhandukien(String ngaynhandukien) {
		this.ngaynhan = ngaynhandukien;
	}

	@Override
	public String getGia() {
		return this.gia;
	}

	@Override
	public void setGia(String gia) {
		this.gia = gia;
	}

	@Override
	public String getNhomkenh() {
		return this.nhomkenh;
	}

	@Override
	public void setNhomkenh(String nhomkenh) {
		this.nhomkenh = nhomkenh;
	}

	@Override
	public String getThuexuat() {
		return this.thuexuat;
	}

	@Override
	public void setThuexuat(String thuexuat) {
		this.thuexuat = thuexuat;
	}

	@Override
	public String getHansudung() {
		return this.hansudung;
	}

	@Override
	public void setHansudung(String hansudung) {
		this.hansudung = hansudung;
	}
	
}
