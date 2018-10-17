package geso.traphaco.erp.beans.parktrongnuoc.imp;
import java.util.List;
import geso.traphaco.erp.beans.parktrongnuoc.IErpPhieunhapkho;

public class ErpPhieunhapkho implements IErpPhieunhapkho
{

	String POID, ID, SOPNK,NGAYNHAPHANG,NCC,SOTIENPNK,SOTIENHD, CHON, hoantat, VAT, TIENTE , SOPO;

	List<IErpPhieunhapkho> pknList;
	public ErpPhieunhapkho()
	{
		this.ID = "";
		this.POID = "";
		this.NGAYNHAPHANG="";
		this.SOPNK="";
		this.NCC="";
		this.SOTIENPNK="";
		this.SOTIENHD="";
		this.CHON = "0";
		this.hoantat = "0";

		this.TIENTE = "";
		this.VAT = "";
		
		this.SOPO = "";
	}
	public ErpPhieunhapkho(String poId, String pnkId, String ngaynhaphang,String sopnk,String ncc,String sotienpnk,String dotienhd, String chon, String tiente)
	{
		this.ID = pnkId;
		this.POID=poId;
		this.NGAYNHAPHANG=ngaynhaphang;
		this.NCC=ncc;
		this.SOPNK=sopnk;
		this.SOTIENPNK=sotienpnk;
		this.SOTIENHD=dotienhd;
		this.CHON = chon;
		this.hoantat = "0";
		
		this.TIENTE = tiente;
		this.VAT  = "";
		
		this.SOPO = "";
	}
	public String getId() 
	{
		
		return this.ID;
	}

	
	public void setId(String Id) 
	{
		this.ID=Id;
		
	}

	public String getPoId() 
	{
		
		return this.POID;
	}

	
	public void setPoId(String PoId) 
	{
		this.POID=PoId;
		
	}

	
	public String getNgaynhaphang() 
	{
		
		return this.NGAYNHAPHANG;
	}

	
	public void setNgaynhaphang(String ngaynhaphang) 
	{
		this.NGAYNHAPHANG=ngaynhaphang;
		
	}

	
	public String getNcc() 
	{
		
		return this.NCC;
	}

	public void setNcc(String ncc) 
	{
		this.NCC=ncc;
		
	}
	
	public String getSotienpnk() 
	{
		
		return this.SOTIENPNK;
	}
	
	public void setSotienpnk(String sotien) 
	{
		this.SOTIENPNK=sotien;
		
	}
	
	public String getSotienhd() 
	{
		
		return this.SOTIENHD;
	}

	public void setSotienhd(String sotienhd) 
	{
		
		this.SOTIENHD=sotienhd;
	}
	
	public void setChon(String chon)
	{
		this.CHON = chon;
	}
	
	public String getChon()
	{
		return this.CHON;
	}

	public List<IErpPhieunhapkho> getPhieunhapkhoList() 
	{	
		return this.pknList;
	}
	public void close() 
	{
		
	}
	public String getSopnk() 
	{
		return this.SOPNK;
	}
	public void setSopnk(String sopnk) 
	{
		this.SOPNK=sopnk;
	}

	public String getTiente() 
	{
		return this.TIENTE;
	}
	public void setTiente(String tiente) 
	{
		this.TIENTE=tiente;
	}

	public int compareTo(IErpPhieunhapkho arg0) 
	{
		if(this.POID.length() > 0)
		{
			if(Integer.parseInt(this.POID) >= Integer.parseInt(arg0.getPoId()))
				return 1;
		}		
		return -1;
	}
	
	public String getVAT() {
		
		return this.VAT;
	}
	
	public void setVAT(String vat) {
		
		this.VAT = vat;
	}

	public void setHoanTat(String chon) {
		
		this.hoantat = chon;
	}

	public String getHoanTat() {

		return this.hoantat;
	}
	public String getSOPO() {
		return SOPO;
	}
	public void setSOPO(String sOPO) {
		SOPO = sOPO;
	}
	
	
}
