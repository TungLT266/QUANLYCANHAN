package geso.traphaco.erp.beans.timnhacc;

import java.sql.ResultSet;
import java.util.List;

public interface ISanpham
{
	public String getId();
	public void setId(String id);

	public String getMasanpham();
	public void setMasanpham(String masp);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getTensanpham();
	public void setTensanpham(String tensp);

	public String getNhomhang();
	public void setNhomhang(String nhomhang);

	public List<INhacungcap> getNhacungcap();
	public void setNhacungcap(List<INhacungcap> list);
	public void setRsNcc(ResultSet rsncc);
	public ResultSet getRsNcc();
	
	
	
	
}
