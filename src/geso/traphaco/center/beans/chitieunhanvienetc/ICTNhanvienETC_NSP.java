package geso.traphaco.center.beans.chitieunhanvienetc;

import geso.traphaco.center.beans.chitieunhanvienetc.imp.CTNhanvienETC_NSP;

import java.sql.ResultSet;
import java.util.List;

public interface ICTNhanvienETC_NSP {

	public String getLoai() ;
	public void setLoai(String loai);
	public String getNsp_fk();
	public void setNsp_fk(String nsp_fk) ;
	public String getTennsp();
	public void setTennsp(String tennsp);
	public double getChitieu();
	public void setChitieu(double DoanhSo);
	public double getSoluong();
	public void setSoluong(double chitieu);

}
