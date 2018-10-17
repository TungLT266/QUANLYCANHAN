package geso.traphaco.erp.beans.khoasothang.imp;
import geso.traphaco.erp.beans.khoasothang.IErpNuocda;
 
public class ErpNuocda implements IErpNuocda 
{
	String IdNhamay;
	String TenNhamay;
	double soluong;
	double dongia;
	double thanhtien;
 
	public ErpNuocda()
	{
		  IdNhamay="";
		  TenNhamay="";
		  soluong=0;
		  dongia=0;
		  thanhtien=0;
	}

	@Override
	public String getIdNhamay() {
		// TODO Auto-generated method stub
		return this.IdNhamay;
	}

	@Override
	public void setIdNhamay(String _IdNhamay) {
		// TODO Auto-generated method stub
		this.IdNhamay=_IdNhamay;
	}

	@Override
	public String getTenNhamay() {
		// TODO Auto-generated method stub
		return this.TenNhamay;
	}

	@Override
	public void setTenNhamay(String _TenNhamay) {
		// TODO Auto-generated method stub
		this.TenNhamay=_TenNhamay;
	}

	@Override
	public double getSoLuong() {
		// TODO Auto-generated method stub
		return this.soluong;
	}

	@Override
	public void setSoLuong(double soluong) {
		// TODO Auto-generated method stub
		this.soluong=soluong;
	}

	@Override
	public double getDongia() {
		// TODO Auto-generated method stub
		return this.dongia;
	}

	@Override
	public void setDongia(double Dongia) {
		// TODO Auto-generated method stub
		this.dongia=Dongia;
	}

	@Override
	public double getThanhtien() {
		// TODO Auto-generated method stub
		return this.thanhtien;
	}

	@Override
	public void setThanhtien(double _thanhtien) {
		// TODO Auto-generated method stub
		this.thanhtien=_thanhtien;
	}
	
}
