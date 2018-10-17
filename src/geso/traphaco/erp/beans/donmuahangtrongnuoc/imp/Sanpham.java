package geso.traphaco.erp.beans.donmuahangtrongnuoc.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.INgaynhan;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanPhamPhanBo;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanphamBom;

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
	String thuexuat;
	String thanhtien;
	String nhomhang;
	List<INgaynhan> ngaynhan;
	List<ISanPhamPhanBo> sanphamPB;
	String khonhan;
	String dungsai;
	String mnlId;
	String quycach;
	String dvkd;
	String inraHd;
	ResultSet rsbom;
	/* 31-08-2012 Them TrungTamChiPhi_FK,Thue Nhap Khau */
	String TrungTamChiPhi_FK;
	String ThueNhapKhau;
	String PhanTramThue;
	float TyGiaQuyDoi;
	String ngaynhanstr="";
	String tenHd = "";
	String BomId="";
	

	// thêm marquette
	String idmarquette;
	String ngaynhandukien;
	List<ISanphamBom> spListBom;
	String ismienthue="0";
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
		this.thuexuat = "";
		this.thanhtien = "";
		this.nhomhang = "";
		this.ngaynhan = new ArrayList<INgaynhan>();
		this.sanphamPB = new ArrayList<ISanPhamPhanBo>();
		this.khonhan = "";
		this.dungsai = "0";
		this.TrungTamChiPhi_FK = "";
		this.ThueNhapKhau = "";
		this.PhanTramThue = "";
		this.tenXHD = "";
		this.mnlId = "";
		this.quycach = "" ;
		this.dvkd = "";
		this.inraHd = "" ;
		this.idmarquette = "";
		this.ngaynhandukien = "";
		this.spListBom = new ArrayList<ISanphamBom>();
	}

	public Sanpham(String[] param, List<INgaynhan>ngaynhan)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.tiente = param[4];
		this.donvitinh = param[5];
		this.dongia = param[6];
		this.thanhtien = param[7];
		this.nhomhang = param[8];
		this.ngaynhan = ngaynhan;
		this.khonhan = param[10];
		this.ThueNhapKhau = param[11];
		this.PhanTramThue = param[12];
		this.TrungTamChiPhi_FK = param[13];
		this.thuexuat = param[14];
		this.dungsai = "0";
		this.tenXHD = "";
		this.idmarquette = "";
		this.ngaynhandukien = "";
		this.spListBom = new ArrayList<ISanphamBom>();
	}

	public Sanpham(String spId, String spMa, String spTen, String soluong, String soluongOLD, String tiente, String dvt, String dongia, String dongiaOLD, String thanhtien, String nhomhang, List<INgaynhan> ngaynhan,
		String khonhan, String TrungTamChiPhi_FK, String thuenhapkhau, String phantramthue, String mnlId, String thuexuat)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.soluongOLD = soluongOLD;
		this.tiente = tiente;
		this.donvitinh = dvt;
		this.dongia = dongia;
		this.dongiaOLD = dongiaOLD;
		this.thanhtien = thanhtien;
		this.nhomhang = nhomhang;
		this.ngaynhan = ngaynhan;
		this.khonhan = khonhan;
		this.dungsai = "0";
		this.TrungTamChiPhi_FK = TrungTamChiPhi_FK;
		this.ThueNhapKhau = thuenhapkhau;
		this.PhanTramThue = phantramthue;
		this.mnlId = mnlId;
		this.thuexuat = thuexuat;
		this.ngaynhandukien = "";
		this.spListBom = new ArrayList<ISanphamBom>();
	}
	
	public String getIsmienthue() {
		return ismienthue;
	}

	public void setIsmienthue(String ismienthue) {
		this.ismienthue = ismienthue;
	}
	public List<ISanphamBom> getSpListBom() {
		return spListBom;
	}

	public void setSpListBom(List<ISanphamBom> spListBom) {
		this.spListBom = spListBom;
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

	public String getThanhtien()
	{
		return this.thanhtien;
	}

	public void setThanhtien(String thanhtien)
	{
		this.thanhtien = thanhtien;
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

	public void setPhanTramThue(String phantramthue)
	{
		this.PhanTramThue = phantramthue;
	}

	public String getPhanTramThue()
	{
		return this.PhanTramThue;
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
	public String getThuexuat() {
		return this.thuexuat;
	}

	@Override
	public void setThuexuat(String thuexuat) {
		this.thuexuat = thuexuat;
	}

	@Override
	public ResultSet getRsBom() {
		// TODO Auto-generated method stub
		return this.rsbom;
	}

	@Override
	public void setRsBom(ResultSet RsBom) {
		// TODO Auto-generated method stub
		this.rsbom=RsBom;
	}

	@Override
	public String getBomId() {
		// TODO Auto-generated method stub
		return this.BomId;
	}

	@Override
	public void setBomId(String BomId) {
		// TODO Auto-generated method stub
		 this.BomId=BomId;
	}
}
