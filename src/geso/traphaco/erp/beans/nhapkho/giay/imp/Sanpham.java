package geso.traphaco.erp.beans.nhapkho.giay.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.beans.nhapkho.giay.ISanphamCon;

public class Sanpham implements ISanpham
{
	String id;
	String masp;
	String soluongsx;
	String soluongnhapkho;
	String solo;
	String diengiai;
	String ngayhethan;

	String donvitinh;
	String quycach;
	String thung;
	String le;
	String thetich;
	String trongluong;
	String dongia;

	String tiente;
	String dongiaViet;
	String nhapkhoId, ngaysanxuat, ngaynhapkho;
	String khuvucId = "";
	ResultSet khuvucRs;
	ResultSet RsDVDL;
	String DvdlId;
	String Dvdl_mau;
	String Dvdl_mau_id;
	String Soluonglaymau;
	List<ISanphamCon> spConList;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.diengiai = "";
		this.solo = "";
		this.soluongsx = "";
		this.soluongnhapkho = "";
		this.ngayhethan = "";
		this.Soluonglaymau="0";
		this.Dvdl_mau="";
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.trongluong = "";
		this.dongia = "";
		this.tiente = "";
		this.dongiaViet = "";
		this.DvdlId="";
		this.spConList = new ArrayList<ISanphamCon>();
	}

	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.diengiai = param[2];
		this.solo = param[3];
		this.soluongsx = param[4];
		this.soluongnhapkho = param[5];
		this.khuvucId = param[6];
		this.Dvdl_mau="";
		this.ngayhethan = "";
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.DvdlId="";
		this.thetich = "";
		this.trongluong = "";
		this.dongia = "";
		this.tiente = "";
		this.dongiaViet = "";
		this.Soluonglaymau="0";

		this.spConList = new ArrayList<ISanphamCon>();
	}

	public Sanpham(String id, String masp, String diengiai, String solo, String soluongnhan, String soluongnhap)
	{
		this.id = id;
		this.masp = masp;
		this.diengiai = diengiai;
		this.solo = solo;
		this.soluongsx = soluongnhan;
		this.soluongnhapkho = soluongnhap;
		this.Dvdl_mau="";
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.DvdlId="";
		this.trongluong = "";
		this.Soluonglaymau="0";
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

	public String getSolo()
	{
		return this.solo;
	}

	public void setSolo(String solo)
	{
		this.solo = solo;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getSoluongnhapkho()
	{
		return this.soluongnhapkho;
	}

	public void setSoluongnhapkho(String soluongnhap)
	{
		this.soluongnhapkho = soluongnhap;
	}

	public List<ISanphamCon> getSpConList()
	{
		return this.spConList;
	}

	public void setSpConList(List<ISanphamCon> spConList)
	{
		this.spConList = spConList;
	}

	public String getDVT()
	{
		return this.donvitinh;
	}

	public void setDVT(String dvt)
	{
		this.donvitinh = dvt;
	}

	public String getQuycach()
	{
		return this.quycach;
	}

	public void setQuycach(String quycach)
	{
		this.quycach = quycach;
	}

	public String getThung()
	{
		return this.thung;
	}

	public void setThung(String thung)
	{
		this.thung = thung;
	}

	public String getLe()
	{
		return this.le;
	}

	public void setLe(String le)
	{
		this.le = le;
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

	public String getNgayhethan()
	{
		return this.ngayhethan;
	}

	public void setNgayhethan(String ngayhethan)
	{
		this.ngayhethan = ngayhethan;
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

	public String getDongiaViet()
	{
		return this.dongiaViet;
	}

	public void setDongiaViet(String dongiaViet)
	{
		this.dongiaViet = dongiaViet;
	}

	public String getNhapKhoId()
	{

		return this.nhapkhoId;
	}

	public void setNhapKhoId(String nhapkhoId)
	{
		this.nhapkhoId = nhapkhoId;
	}

	public String getNgayNhapKho()
	{

		return this.ngaynhapkho;
	}

	public void setNgayNhapKho(String ngaynhapkho)
	{
		this.ngaynhapkho = ngaynhapkho;

	}

	public String getNgaySanXuat()
	{

		return this.ngaysanxuat;
	}

	public void setNgaySanXuat(String ngaysanxuat)
	{
		this.ngaysanxuat = ngaysanxuat;

	}

	public String getSoluongSx()
	{

		return this.soluongsx;
	}

	public void setSoluongSx(String SoluongSx)
	{

		this.soluongsx = SoluongSx;
	}

	@Override
	public String getKhuvucId() {
		return this.khuvucId;
	}

	@Override
	public void setKhuvucId(String value) {
		this.khuvucId = value;
	}

	@Override
	public void setKhuvucRs(ResultSet rs) {
		this.khuvucRs = rs;
	}

	@Override
	public ResultSet getKhuvucRs() {
		return this.khuvucRs;
	}

	@Override
	public void setRsDvld(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsDVDL=rs;
	}

	@Override
	public ResultSet getRsDvld() {
		// TODO Auto-generated method stub
		return this.RsDVDL;
	}

	@Override
	public String getDvdlId() {
		// TODO Auto-generated method stub
		return this.DvdlId;
	}

	@Override
	public void setDvdlId(String DvdlId) {
		// TODO Auto-generated method stub
		this.DvdlId=DvdlId;
	}

	@Override
	public String getSoluonglaymau() {
		// TODO Auto-generated method stub
		return this.Soluonglaymau;
	}

	@Override
	public void setSoluonglaymau(String soluonglaymau) {
		// TODO Auto-generated method stub
		this.Soluonglaymau= soluonglaymau;
	}

	@Override
	public String getDvdl_Mau() {
		// TODO Auto-generated method stub
		return this.Dvdl_mau;
	}

	@Override
	public void setDvdl_Mau(String Dvdl_Mau) {
		// TODO Auto-generated method stub
		this.Dvdl_mau= Dvdl_Mau;
	}

	@Override
	public String getDvdl_Mau_Id() {
		// TODO Auto-generated method stub
		return this.Dvdl_mau_id;
	}

	@Override
	public void setDvdl_Mau_Id(String Dvdl_Mau_Id) {
		// TODO Auto-generated method stub
		this.Dvdl_mau_id=Dvdl_Mau_Id;
	}

}
