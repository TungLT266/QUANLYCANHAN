package geso.traphaco.erp.beans.nhomnhacungcapcn;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;;

public interface INhomnhacungcapcnList extends Serializable {
	
	public String getDiengiai();
	
	public void setDiengiai (String diengiai);
	
    
	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<INhomnhacungcapcn> getNccList();
	
	public void setNccList(List<INhomnhacungcapcn> Ncclist);
	
	public boolean getSearch();

	public void setSearch(boolean search);

	public String getMaNcc();
	public void setMaNcc(String maNcc);
	public void DBClose();
	
	public void setChixem(String chixem);
	public String getChixem();
	public String getTen();
	public void setTen(String ten);
	
}

