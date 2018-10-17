package geso.traphaco.erp.beans.khoasothang.imp;
import geso.traphaco.erp.beans.khoasothang.IErptiendien;
import geso.traphaco.erp.beans.khoasothang.IErptiennuoc;

public class ErpTiennuoc implements IErptiennuoc 
{
	String IdDongHoDien;
	String TenDongHoDien;
	double soluong;
	double dongia;
	double thanhtien;
 
	public ErpTiennuoc()
	{
		  IdDongHoDien="";
		  TenDongHoDien="";
		  soluong=0;
		  dongia=0;
		  thanhtien=0;
	}

	@Override
	public String getIdDongHoNuoc() {
		// TODO Auto-generated method stub
		return this.IdDongHoDien;
	}

	@Override
	public void setIdDongHoNuoc(String _IdDongHoDien) {
		// TODO Auto-generated method stub
		this.IdDongHoDien=_IdDongHoDien;
	}

	@Override
	public String getTenDongHoNuoc() {
		// TODO Auto-generated method stub
		return this.TenDongHoDien;
	}

	@Override
	public void setTenDongHoNuoc(String _TenDongHoNuoc) {
		// TODO Auto-generated method stub
		this.TenDongHoDien=_TenDongHoNuoc;
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
