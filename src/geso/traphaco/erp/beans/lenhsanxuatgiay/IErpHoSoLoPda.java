package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHoSoLoPda extends Serializable, IPhan_Trang {

	public String getCongtyId() ;
	public void setCongtyId(String congtyId);
	public String getNppId() ;
	public void setNppId(String nppId);
	public String getUserId() ;
	public void setUserId(String userId) ;
	public String getLenhsanxuat() ;
	public void setLenhsanxuat(String lenhsanxuat) ;
	public String getNgayin() ;
	public void setNgayin(String ngayin) ;
	public String getCa() ;
	public void setCa(String ca) ;
	public String getCongdoan() ;
	public void setCongdoan(String congdoan);
	public String getSmg();
	public void setSms(String smg);
	public ResultSet getCongdoanRs() ;
	public void setCongdoanRs(ResultSet congdoanRs) ;
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs) ;
	
	public String getHoso_fk();

	public void setHoso_fk(String hoso_fk);
	
	public void DBclose();
	public void createRs ();

}
