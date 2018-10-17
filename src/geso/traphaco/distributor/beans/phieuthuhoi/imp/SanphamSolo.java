package geso.traphaco.distributor.beans.phieuthuhoi.imp;

import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamSoLo;

public class SanphamSolo implements  ISanphamSoLo{

	String SoLo="";
	String Masanpham="";
	String IdSp="";
	String SoLuong="";
	String Tensanpham;
	String Ngayhethan;
	String SoLuongXk="";
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMasanpham() {
		// TODO Auto-generated method stub
		return this.Masanpham;
	}

	@Override
	public void setMasanpham(String masp) {
		// TODO Auto-generated method stub
		this.Masanpham=masp;
	}

	@Override
	public String getTensanpham() {
		// TODO Auto-generated method stub
		return this.Tensanpham;
	}

	@Override
	public void setTensanpham(String tensp) {
		// TODO Auto-generated method stub
		this.Tensanpham=tensp;
		
	}

	@Override
	public void setSoluong(String soluong1) {
		// TODO Auto-generated method stub
		this.SoLuong=soluong1;
	}

	@Override
	public String getSoluong() {
		// TODO Auto-generated method stub
		return this.SoLuong;
	}

	@Override
	public void setSolo(String solo) {
		// TODO Auto-generated method stub
		this.SoLo=solo;
	}

	@Override
	public String getSOLo() {
		// TODO Auto-generated method stub
		return this.SoLo;
	}

	@Override
	public void setSPid(String spid) {
		// TODO Auto-generated method stub
		this.IdSp=spid;
	}

	@Override
	public String getSpId() {
		// TODO Auto-generated method stub
		return this.IdSp;
	}

	@Override
	public void setNgayhethang(String ngayhethan) {
		// TODO Auto-generated method stub
		this.Ngayhethan=ngayhethan;
	}

	@Override
	public String getNgayhethan() {
		// TODO Auto-generated method stub
		return this.Ngayhethan;
	}

	@Override
	public void setSoluongXK(String soluongxk) {
		// TODO Auto-generated method stub
		this.SoLuongXk=soluongxk;
	}

	@Override
	public String getSoluongXK() {
		// TODO Auto-generated method stub
		return this.SoLuongXk;
	}

}
