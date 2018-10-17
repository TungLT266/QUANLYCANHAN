package geso.traphaco.erp.beans.khoasothang.imp;
import geso.traphaco.erp.beans.khoasothang.IErptiendien;

public class ErpTiendien implements IErptiendien 
{
	String IdDongHoDien;
	String TenDongHoDien;
	double soluong;
	double dongia;
	double thanhtien;
 
	public ErpTiendien()
	{
		  IdDongHoDien="";
		  TenDongHoDien="";
		  soluong=0;
		  dongia=0;
		  thanhtien=0;
	}

	@Override
	public String getIdDongHoDien() {
		// TODO Auto-generated method stub
		return this.IdDongHoDien;
	}

	@Override
	public void setIdDongHoDien(String _IdDongHoDien) {
		// TODO Auto-generated method stub
		this.IdDongHoDien=_IdDongHoDien;
	}

	@Override
	public String getTenDongHoDien() {
		// TODO Auto-generated method stub
		return this.TenDongHoDien;
	}

	@Override
	public void setTenDongHoDien(String _TenDongHoDien) {
		// TODO Auto-generated method stub
		this.TenDongHoDien=_TenDongHoDien;
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
