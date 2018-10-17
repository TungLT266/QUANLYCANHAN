package geso.traphaco.center.beans.huynhaphangtt.imp;

import java.io.Serializable;

import geso.traphaco.center.beans.huynhaphangtt.ISanpham;

public class Sanpham implements ISanpham, Serializable
{	
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String tensp;
	String soluong;
	String donvitinh;
	String dongia;
	String ctkm;
	String chietkhau;
	String Ngayhethan;
	String Solo;
	String isxuatkho;
	String Giagross;
	String Gianet;
	
	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.donvitinh = "";
		this.dongia = "";
		this.ctkm = "";
		this.chietkhau = "";
		this.Ngayhethan="";
		this.Solo="";
		this.isxuatkho="";
		this.Giagross="";
		this.Gianet="";
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.donvitinh = param[4];
		this.dongia = param[5];
		this.ctkm = param[6];
		this.chietkhau = param[7];
		
	}
	
	public Sanpham(String spId, String spMa, String spTen, String soluong, String dvt, String dongia, String ctkm, String chietkhau)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.donvitinh = dvt;
		this.dongia = dongia;
		this.ctkm = ctkm;
		this.chietkhau = chietkhau;
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
	
	public String getDonvitinh() 
	{
		return this.donvitinh;
	}
	
	public void setDonvitinh(String donvitinh) 
	{
		this.donvitinh = donvitinh;		
	}

	public String getCTKM() 
	{
		return this.ctkm;
	}

	public void setCTKM(String ctkm) 
	{
		this.ctkm = ctkm;
	}

	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	@Override
	public void setSoLo(String solo) {
		// TODO Auto-generated method stub
		this.Solo=solo;
	}

	@Override
	public String getSoLo() {
		// TODO Auto-generated method stub
		return this.Solo;
	}

	@Override
	public void setNgayhethan(String ngayhethan) {
		// TODO Auto-generated method stub
		this.Ngayhethan=ngayhethan;
		
	}

	@Override
	public String getNgayhethan() {
		// TODO Auto-generated method stub
		return this.Ngayhethan;
	}

	@Override
	public void setISXUATKHO(String _isxuatkho) {
		// TODO Auto-generated method stub
		this.isxuatkho=_isxuatkho;
	}

	@Override
	public String getISXUATKHO() {
		// TODO Auto-generated method stub
		return this.isxuatkho;
	}

	@Override
	public void setGiagross(String Giagross) {
		// TODO Auto-generated method stub
		this.Giagross=Giagross;
	}

	@Override
	public String getGiagross() {
		// TODO Auto-generated method stub
		return this.Giagross;
	}

	@Override
	public void setGianet(String Gianet) {
		// TODO Auto-generated method stub
		this.Gianet=Gianet;
	}

	@Override
	public String getGianet() {
		// TODO Auto-generated method stub
		return this.Gianet;
	}




}
