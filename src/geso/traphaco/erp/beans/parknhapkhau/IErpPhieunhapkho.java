package geso.traphaco.erp.beans.parknhapkhau;
public interface IErpPhieunhapkho  extends Comparable<IErpPhieunhapkho>
{
	public String getId();
	public void setId(String Id); 

	public String getPoId();
	public void setPoId(String poId);	
	
	public String getSopnk();
	public void setSopnk(String sopnk);	
	
	public String getNgaynhaphang();
	public void setNgaynhaphang(String ngaynhaphang);
	
	public String getNcc();
	public void setNcc(String ncc);
	
	public String getSotienpnk();
	public void setSotienpnk(String sotien);
	
	public String getTiente(); 
	public void setTiente(String tiente); 
	
	public String getSotienhd();
	public void setSotienhd(String sotienhd);
	public void setChon(String chon);	
	public String getChon();
	
	public void setHoanTat(String chon);	
	public String getHoanTat();
	
	public String getVAT();
	public void setVAT(String vat);	
	
	public String getSOPO();
	public void setSOPO(String sOPO);
	
}
