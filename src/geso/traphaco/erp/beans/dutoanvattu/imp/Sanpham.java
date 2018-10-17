package geso.traphaco.erp.beans.dutoanvattu.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.dutoanvattu.INgaynhan;
import geso.traphaco.erp.beans.dutoanvattu.ISanpham;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	String PK_SEQ;
	String id;
	String masp;
	String tensp;
	String tenXHD;
	String soluong;
	String tiente;
	String donvitinh;
	String dongia;
	String thanhtien;
	String nhomhang;
	List<INgaynhan> ngaynhan;
	String khonhan;
	String dungsai;
	String mnlId;
	String quycach;
	String dvkd;
	String inraHd;
	/* 31-08-2012 Them TrungTamChiPhi_FK,Thue Nhap Khau */
	String TrungTamChiPhi_FK;
	String ThueNhapKhau;
	String PhanTramThue;
	float TyGiaQuyDoi;
	String ngaynhanstr="";
	String tenHd = "";
	
	String nccId = "";
	ResultSet nccRs;

	/* 31-08-2012 */
	public Sanpham()
	{
		this.PK_SEQ="";
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.tiente = ""; // 4
		this.donvitinh = "";
		this.dongia = "";
		this.thanhtien = "";
		this.nhomhang = "";
		this.ngaynhan = new ArrayList<INgaynhan>();
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
		this.dungsai = "0";
		this.tenXHD = "";
	}

	public Sanpham(String spId, String spMa, String spTen, String soluong, String tiente, String dvt, String dongia, String thanhtien, String nccId)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.tiente = tiente;
		this.donvitinh = dvt;
		this.dongia = dongia;
		this.thanhtien = thanhtien;
		this.nccId = nccId;
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

	@Override
	public String getQuyDoiStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQuyDoiStr(String quydoi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNgaynhan(List<INgaynhan> list) {
		this.ngaynhan = list;
	}

	@Override
	public List<INgaynhan> getNgaynhan() {
		return this.ngaynhan;
	}

	@Override
	public void setNgaynhanstr(String ngaynhan) {
		// TODO Auto-generated method stub
		ngaynhanstr=ngaynhan;
	}

	@Override
	public String getNgaynhanstr() {
		// TODO Auto-generated method stub
		return ngaynhanstr;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}

	public void setNccId(String nccid)
	{
		this.nccId = nccid;
	}

	@Override
	public ResultSet getNccRs() {
		return this.nccRs;
	}

	@Override
	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}
}
