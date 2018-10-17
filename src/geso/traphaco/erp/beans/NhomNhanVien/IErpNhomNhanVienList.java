package geso.traphaco.erp.beans.NhomNhanVien;

import java.util.List;

public interface IErpNhomNhanVienList {
	
	public String getDiengiai();
	
	public void setDiengiai (String diengiai);
	
    
	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<IErpNhomNhanVien> getNccList();
	
	public void setNccList(List<IErpNhomNhanVien> Ncclist);
	
	public boolean getSearch();

	public void setSearch(boolean search);

	public String getMaNcc();
	public void setMaNcc(String maNcc);
	public void DBClose();
	
	public void setChixem(String chixem);
	public String getChixem();
	
}
