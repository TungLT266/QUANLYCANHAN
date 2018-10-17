package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import geso.traphaco.erp.beans.thongtinsanphamgiay.ITieuchikiemdinh;

public class Tieuchikiemdinh implements ITieuchikiemdinh 
{
	String chungloai, ten;
	String tumuc;
	String denmuc;
	String tieuchi;
	String toantu;
	String giatrichuan;
	String soluongchophep;
	String diemdat;
	String trangthai;
	String dat;
	String dungsai;
	String quyetdinh, nguoisua;
	String ghichu, ghinhan;
	
	public Tieuchikiemdinh()
	{
		this.tieuchi = "";
		this.ten = "";
		this.toantu = "";
		this.giatrichuan = "";
		this.diemdat = "";
		this.trangthai = "";
		this.dungsai = "";
		this.dat = "0";
		this.chungloai = "";
		this.tumuc = "";
		this.denmuc = "";
		this.soluongchophep = "";
		this.ghichu = "";
		this.ghinhan = "";
	}
	
	public Tieuchikiemdinh(String tieuchi, String toantu, String giatrichuan)
	{
		this.tieuchi = tieuchi;
		this.toantu = toantu;
		this.giatrichuan = giatrichuan;

		this.diemdat = "";
		this.trangthai = "";
		this.dungsai = "";
		this.dat = "0";
		
		this.chungloai = "";
		this.ten = "";
		this.tumuc = "";
		this.denmuc = "";
		this.soluongchophep = "";
		this.ghichu = "";
		this.ghinhan = "";
	}

	public void setTieuchi(String tieuchi)
	{
		this.tieuchi = tieuchi;
	}

	public String getTieuchi() 
	{
		return this.tieuchi;
	}

	public void setToantu(String toantu) 
	{
		this.toantu = toantu;
	}

	public String getToantu()
	{
		return this.toantu;
	}

	public void setGiatrichuan(String giatrichuan) 
	{
		this.giatrichuan = giatrichuan;
	}

	public String getGiatrichuan()
	{
		return this.giatrichuan;
	}

	public void setDiemdat(String diemdat)
	{
		this.diemdat = diemdat;
	}

	public String getDiemdat()
	{
		return this.diemdat;
	}

	public void setTrangthai(String tt) 
	{
		this.trangthai = tt;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setDat(String dat)
	{
		this.dat = dat;
	}

	public String getDat() 
	{
		return this.dat;
	}
	


	public String getQuyetdinh()
	{

		return this.quyetdinh;
	}

	public void setQuyetdinh(String Quyetdinh)
	{
		this.quyetdinh = Quyetdinh;

	}


	public String getGhiNhan()
	{

		return this.ghinhan;
	}

	public void setGhiNhan(String ghinhan)
	{
		this.ghinhan = ghinhan;

	}

	public void setDungsai(String dungsai) 
	{
		this.dungsai = dungsai;
	}

	public String getDungsai()
	{
		return this.dungsai;
	}

	public String getNguoiSua()
	{

		return this.nguoisua;
	}

	public void setNguoiSua(String nguoisua)
	{
		this.nguoisua = nguoisua;

	}

	@Override
	public void setChungloai(String chungloai) {
		this.chungloai = chungloai;
	}

	@Override
	public String getChungloai() {
		return this.chungloai;
	}

	@Override
	public void setTen(String ten) {
		this.ten = ten;
	}

	@Override
	public String getTen() {
		return this.ten;
	}

	@Override
	public void setTumuc(String tumuc) {
		this.tumuc = tumuc;
	}

	@Override
	public String getTumuc() {
		return this.tumuc;
	}

	@Override
	public void setDenmuc(String denmuc) {
		this.denmuc = denmuc;
	}

	@Override
	public String getDenmuc() {
		return this.denmuc;
	}

	@Override
	public void setSoluongchophep(String slcp) {
		this.soluongchophep = slcp;		
	}

	@Override
	public String getSoluongchophep() {
		return this.soluongchophep;
	}

	@Override
	public String getGhiChu() {
		return this.ghichu;
	}

	@Override
	public void setGhiChu(String ghichu) {
		this.ghichu = ghichu;
	}
	
}
