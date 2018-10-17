package geso.traphaco.erp.beans.chiphikhac;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IErpChiphikhac
{
	public String getId(); 

	public void setId(String Id); 
	
	public String getUserId(); 

	public void setUserId(String userId); 

	public String getMsg(); 

	public void setMsg(String msg); 

	public String getLoai(); 

	public void setLoai(String loai); 

	public String getNccId(); 

	public void setNccId(String nccId); 

	public String getNccTen(); 

	public void setNccTen(String nccTen); 

	public String getNvId(); 

	public void setNvId(String nvId); 

	public String getNvTen(); 

	public void setNvTen(String nvTen); 

	public String getCongtyId(); 

	public void setCongtyId(String congtyId); 
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getNgaynhap();
	
	public void setNgaynhap(String tungay);

	public String[] getChiphi(); 

	public void setChiphi(String[] chiphi); 
	
	public String[] getDiengiaicp();
	
	public void setDiengiaicp(String[] diengiaicp); 

	public String[] getTongtien();
	
	public void setTongtien(String[] tongtien);

	public String[] getTongthue();
	
	public void setTongthue(String[] tongthue);
	
	public String[] getGhichu();
	
	public void setGhichu(String[] ghichu);
	
	public String[] getMaHD();
	public void setMaHD(String[] maHD);
	
	public String[] getMausoHD();
	public void setMausoHD(String[] mausoHD);
	
	public String[] getKyhieu();
	public void setKyhieu(String[] kyhieu);
	
	
	public String[] getSohd();
	
	public void setSohd(String[] sohd);
	
	public String[] getNgayhd();
	
	public void setNgayhd(String[] ngayhd);
	
	public String[] getTenNcc();
	
	public void setTenNcc(String[] tenNcc);

	public String[] getMST();
	
	public void setMst(String[] mst);
	
	public String[] getTienhang();
	
	public void setTienhang(String[] tienhang);
	
	public String[] getThuesuat();
	
	public void setThuesuat(String[] thuesuat);
	
	public String[] getTienthue();
	
	public void setTienthue(String[] tienthue);
	
	public ResultSet getTienteRs();
	
	public void setTienteRs(ResultSet tienteRs);

	public String getTienteId();
	
	public void setTienteId(String ttId);	
	
	public boolean Create(HttpServletRequest request);
	
	public boolean Update(HttpServletRequest request);
		
	public void getData(String hmcpId);
	
	public void init();
	
	public void DbClose();
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
}
