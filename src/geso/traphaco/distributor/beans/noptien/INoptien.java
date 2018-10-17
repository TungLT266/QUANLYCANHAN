package geso.traphaco.distributor.beans.noptien;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface INoptien extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);

	public String getNgaynop();
	public void setNgaynop(String ngaynop);
	
	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getDoituongId();
	public void setDoituongId(String doituongId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public ResultSet getNvbhRs();
	public void setNvbhRs(ResultSet NvbhRs);
	public String getNvbhId();
	public void setNvbhId(String NvbhId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet KhRs);
	public String getKhId();
	public void setKhId(String KhId);
	
	public ResultSet getDtRs();
	public void setDtRs(ResultSet DtRs);
	public String getDtId();
	public void setDtId(String DtId);

	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	

	
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	public String getNvgnId();
	public void setNvgnId(String nvgnId);

	public Double getSotien();
	public void setSotien(Double sotien);
	
	public String getVekhoan();
	public void setVekhoan(String vekhoan);
	
	public String getSoin();
	public void setSoin(String soin);
	
	public String getSoCTgoc();
	public void setSoCTgoc(String soCTgoc);
	
	public String getNguoinop();
	public void setNguoinop(String nguoinop);
	
	public boolean CreateKh();
	public boolean UpdateKh();
	public void createRS();
	public void init();
	public void DBclose();
	public void initPdf();
	
	
}
