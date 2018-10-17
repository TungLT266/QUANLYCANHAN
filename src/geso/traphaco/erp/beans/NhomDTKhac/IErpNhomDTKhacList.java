package geso.traphaco.erp.beans.NhomDTKhac;
import java.util.List;
public interface IErpNhomDTKhacList {
	
	public String getDiengiai();
	
	public void setDiengiai (String diengiai);
	
    
	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<IErpNhomDTKhac> getNccList();
	
	public void setNccList(List<IErpNhomDTKhac> Ncclist);
	
	public boolean getSearch();

	public void setSearch(boolean search);

	public String getMaNcc();
	public void setMaNcc(String maNcc);
	public void DBClose();
	
	public void setChixem(String chixem);
	public String getChixem();
	
}
