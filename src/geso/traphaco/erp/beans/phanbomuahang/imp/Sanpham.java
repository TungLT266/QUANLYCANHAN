package geso.traphaco.erp.beans.phanbomuahang.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.phanbomuahang.*;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String id;
	String masp;
	String tensp;
	String dvt;
	String soluong;
	String soluongpb;
	String dongia;
	String thuexuat;
	String nhomkenh;
	String solo;
	List<ICongty> lstCt;
	
	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.dvt = "";
		this.soluong = "0";
		this.dongia = "0";
		this.soluongpb = "0";
		this.nhomkenh = "";
		this.thuexuat = "0";
		this.solo = "";
	}

	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.dvt = param[3];
		this.soluong = "0";
		this.dongia = "0";
		this.soluongpb = "0";
		this.thuexuat = "0";
		this.nhomkenh = "";
		this.solo = "";
	}

	public Sanpham(String spId, String spMa, String spTen, String dvt, String soluong, String dongia, String nhomkenh, String thuexuat)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.dvt = dvt;
		this.soluong = soluong;
		this.dongia = dongia;
		this.nhomkenh = nhomkenh;
		this.thuexuat = thuexuat;
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

	@Override
	public String getDonvi() {
		
		return this.dvt;
	}

	@Override
	public void setDonvi(String dvt) {
		
		this.dvt = dvt;
	}

	@Override
	public String getSoluong() {
		
		return this.soluong;
	}

	@Override
	public void setSoluong(String soluong) {
		
		this.soluong = soluong;
	}

	@Override
	public String getSoluongpb() {
		
		return this.soluongpb;
	}

	@Override
	public void setSoluongpb(String soluongpb) {
		
		this.soluongpb = soluongpb;
	}

	@Override
	public String getDongia() {
		
		return this.dongia;
	}

	@Override
	public void setDongia(String dongia) {
		
		this.dongia = dongia;
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
	public String getNhomkenh() {
		
		return this.nhomkenh;
	}

	@Override
	public void setNhomkenh(String nhomkenh) {
		
		this.nhomkenh = nhomkenh;
	}

	@Override
	public List<ICongty> getCongty() {
		
		return this.lstCt;
	}

	@Override
	public void setCongty(List<ICongty> lstCt) {
		
		this.lstCt = lstCt;
	}

	@Override
	public String getSolo() {
		return this.solo;
	}

	@Override
	public void setSolo(String solo) {
		this.solo = solo;
	}

}
