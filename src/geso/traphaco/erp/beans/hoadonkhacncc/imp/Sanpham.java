package geso.traphaco.erp.beans.hoadonkhacncc.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.hoadonkhacncc.INgaynhan;
import geso.traphaco.erp.beans.hoadonkhacncc.ISanPhamPhanBo;
import geso.traphaco.erp.beans.hoadonkhacncc.ISanpham;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	String PK_SEQ;
	String id;
	String masp;
	String tensp;
	String tenXHD;
	String soluong;
	String soluongduyet;
	String soluongOLD;
	String tiente;
	String donvitinh;
	String dongia;
	String dongiaOLD;
	
	String nhomhang;
	List<INgaynhan> ngaynhan;
	List<ISanPhamPhanBo> sanphamPB;
	String khonhan;
	String dungsai;
	String mnlId;
	String quycach;
	String dvkd;
	String inraHd;
	/* 31-08-2012 Them TrungTamChiPhi_FK,Thue Nhap Khau */
	String TrungTamChiPhi_FK;
	String ThueNhapKhau;
	double PhanTramThue;
	float TyGiaQuyDoi;
	String ngaynhanstr="";
	String tenHd = "";
	
	// thêm marquette
	String idmarquette;
	// them ngay nhan du kien
	String ngaynhandukien;
	
	double tienthue;
 
	// thanh tiền trước thuế
	double thanhtien;
	double thanhtiensauthue;
	
	double thanhtiennguyente;
	String TaikhoanKTId;
	String Sohieutaikhoan;
	
	
	/* 31-08-2012 */
	public Sanpham()
	{
		this.PK_SEQ="";
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.soluongduyet = "";
		this.soluongOLD = "";
		this.tiente = ""; // 4
		this.donvitinh = "";
		this.dongia = "";
		this.dongiaOLD = "";
 
		this.thanhtien = 0;
		this.nhomhang = "";
		this.ngaynhan = new ArrayList<INgaynhan>();
		this.sanphamPB = new ArrayList<ISanPhamPhanBo>();
		this.khonhan = "";
		this.dungsai = "0";
		this.TrungTamChiPhi_FK = "";
		this.ThueNhapKhau = "";
	 
		this.tenXHD = "";
		this.mnlId = "";
		this.quycach = "" ;
		this.dvkd = "";
		this.inraHd = "" ;
		this.idmarquette = "";
		this.ngaynhandukien = "";
		  TaikhoanKTId="";
		  Sohieutaikhoan="";
		
	}
 
	public String getNgaynhandukien() {
		return ngaynhandukien;
	}

	public void setNgaynhandukien(String ngaynhandukien) {
		this.ngaynhandukien = ngaynhandukien;
	}

	public String getIdmarquette() {
		return idmarquette;
	}

	public void setIdmarquette(String idmarquette) {
		this.idmarquette = idmarquette;
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
	
	public String getSoluongduyet()
	{
		return this.soluongduyet;
	}

	public void setSoluongduyet(String soluongduyet)
	{
		this.soluongduyet = soluongduyet;
	}
	
	public String getSoluongOLD()
	{
		return this.soluongOLD;
	}

	public void setSoluongOLD(String soluongOLD)
	{
		this.soluongOLD = soluongOLD;
	}

	public String getSoluong_bk()
	{
		return this.soluong;
	}

	public void setSoluong_bk(String soluong)
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

	public String getDongia()
	{
		return this.dongia;
	}

	public void setDongia(String dongia)
	{
		this.dongia = dongia;
	}

	public String getDongiaOLD()
	{
		return this.dongiaOLD;
	}

	public void setDongiaOLD(String dongiaOLD)
	{
		this.dongiaOLD = dongiaOLD;
	}
	
	public String getTiente()
	{
		return this.tiente;
	}

	public void setTiente(String tiente)
	{
		this.tiente = tiente;
	}

 

 
	public String getNhomhang()
	{
		return this.nhomhang;
	}

	public void setNhomhang(String nhomhang)
	{
		this.nhomhang = nhomhang;
	}

	public String getKhonhan()
	{
		return this.khonhan;
	}

	public void setKhonhan(String khonhan)
	{
		this.khonhan = khonhan;
	}

	public String getDungsai()
	{
		return this.dungsai;
	}

	public void setDungsai(String dungsai)
	{
		this.dungsai = dungsai;
	}

	public String getTrungTamChiPhi_FK()
	{
		return this.TrungTamChiPhi_FK;
	}

	public void setTrungTamChiPhi_FK(String TrungTamChiPhi_FK)
	{
		this.TrungTamChiPhi_FK = TrungTamChiPhi_FK;
	}

	public String getThueNhapKhau()
	{
		return this.ThueNhapKhau;
	}

	public void setThueNhapKhau(String thuenhapkhau)
	{
		this.ThueNhapKhau = thuenhapkhau;
	}

	public void setTyGiaQuyDoi(float tygiaquydoi)
	{
		this.TyGiaQuyDoi = tygiaquydoi;
	}

	public float getTyGiaQuyDoi()
	{
		return this.TyGiaQuyDoi;
	}

	 


	public String getPK_SEQ()
	{
		return this.PK_SEQ;
	}

	
	public void setPK_SEQ(String pk_seq)
	{
		this.PK_SEQ=pk_seq;
	}

	public String getTenXHD() 
	{
		return this.tenXHD;
	}

	public void setTenXHD(String tenXHD) 
	{
		this.tenXHD = tenXHD;
	}

	public String getMNLId() 
	{
		return this.mnlId;
	}

	public void setMNLId(String mnlId) 
	{
		this.mnlId = mnlId;
	}

	@Override
	public String getTenHD() {
		return this.tenHd;
	}

	@Override
	public void setTenHD(String tenHD) {
		this.tenHd = tenHD;
	}

	
	public String getQuycachsanpham() 
	{	
		return this.quycach;
	}

	public void setQuycachsanpham(String quycachsanpham) 
	{
		this.quycach = quycachsanpham;	
	}
	public String getDvkd()
	{
		return this.dvkd;
	}

	public void setDvkd(String dvkd)
	{
		this.dvkd = dvkd;
	}
	
	public String getInraHd()
	{
		return this.inraHd;
	}

	public void setInraHd(String inraHd)
	{
		this.inraHd = inraHd;
	}

	public String getQuyDoiStr() 
	{
		return null;
	}

	public void setQuyDoiStr(String quydoi) 
	{
		
	}


	public void setNgaynhan(List<INgaynhan> list) {
		this.ngaynhan = list;
	}


	public List<INgaynhan> getNgaynhan() {
		return this.ngaynhan;
	}


	public void setNgaynhanstr(String ngaynhan) {
		ngaynhanstr=ngaynhan;
	}


	public String getNgaynhanstr() {
		return ngaynhanstr;
	}


	public List<ISanPhamPhanBo> getSanphamPB() 
	{
		return this.sanphamPB;
	}


	public void setSanphamPB(List<ISanPhamPhanBo> list) 
	{
		this.sanphamPB = list;
	}
 
	@Override
	public void setThanhtien(double thanhtien) {
		// TODO Auto-generated method stub
		// tiền trước thuế
		this.thanhtien=thanhtien;
	}

	@Override
	public double getThanhtien() {
		// TODO Auto-generated method stub
		return this.thanhtien;
	}
 
	@Override
	public double getPhantramthue() {
		// TODO Auto-generated method stub
		return this.PhanTramThue;
	}

	@Override
	public void setPhantramthue(double Phantramthue) {
		// TODO Auto-generated method stub
		this.PhanTramThue=Phantramthue;
	}

	@Override
	public double getTienthue() {
		// TODO Auto-generated method stub
		return this.tienthue;
	}

	@Override
	public void setTienthue(double Tienthue) {
		// TODO Auto-generated method stub
		this.tienthue= Tienthue;
	}

	@Override
	public double getTiensauthue() {
		// TODO Auto-generated method stub
		return this.thanhtiensauthue;
	}

	@Override
	public void setTiensauthue(double Tiensauthue) {
		// TODO Auto-generated method stub
		 this.thanhtiensauthue=Tiensauthue;
	}

	@Override
	public double getThanhtienNguyenTe() {
		// TODO Auto-generated method stub
		return this.thanhtiennguyente;
	}

	@Override
	public void setThanhtienNguyenTe(double thanhtien) {
		// TODO Auto-generated method stub
		 this.thanhtiennguyente=thanhtien;
	}

	@Override
	public String getTaikhoanKTId() {
		// TODO Auto-generated method stub
		return this.TaikhoanKTId;
	}

	@Override
	public void setTaikhoanKTId(String TaikhoanKTId) {
		// TODO Auto-generated method stub
		this.TaikhoanKTId=TaikhoanKTId;
	}

	@Override
	public String getSohieutaikhoan() {
		// TODO Auto-generated method stub
		return this.Sohieutaikhoan;
	}

	@Override
	public void setSohieutaikhoan(String Sohieutaikhoan) {
		// TODO Auto-generated method stub
		 this.Sohieutaikhoan=Sohieutaikhoan;
	}

 

}
