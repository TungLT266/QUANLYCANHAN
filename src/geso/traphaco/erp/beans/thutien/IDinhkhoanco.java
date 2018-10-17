package geso.traphaco.erp.beans.thutien;

import java.sql.ResultSet;
import java.util.List;

public interface IDinhkhoanco
{
	
	public String getTaikhoanId();
	
	public void setTaikhoanId(String taikhoanId);
	

	public String getDoituongId();

	public void setDoituongId(String doituongId);

	public String getDoituongdinhkhoan();

	public void setDoituongdinhkhoan(String doituongdinhkhoan);
	
	public String getSotien();

	public void setSotien(String sotien);
	
	public String getSotienNT();

	public void setSotienNT(String sotienNT);
	
	public String CheckDoiTuongDinhKhoan(String dinhkhoanco) ;
	
	public ResultSet getDoiTuong();
	

	
}
