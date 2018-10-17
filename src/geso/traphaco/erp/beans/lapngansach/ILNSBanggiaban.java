package geso.traphaco.erp.beans.lapngansach;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface ILNSBanggiaban extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen(); 
	public void setUserTen(String userTen);	
	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getCtyTen();
	
	public void setCtyTen(String ctyTen);
	
	public String getId();
	public void setId(String id);	
	public String getTen();
	public void setTen(String ten);
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public String getDvkd();
	public void setDvkd(String dvkd);	
	public String getKenhId();	
	public void setKenhId(String kenhId);
	public String getKenh();
	public void setKenh(String kenh);
	public void setTrangthai(String trangthai);	
	public String getTrangthai();
	
	public void setBlock(String block); 

	public String getBlock(); 
	
	public String getNgaytao();	
	public void setNgaytao(String ngaytao);
	public String getNgaysua();	
	public void setNgaysua(String ngaysua);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();	
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	public ResultSet getDvkdIds();	
	public void setDvkdIds(ResultSet dvkdIds);
	public ResultSet getKenhIds();
	public void setKenhIds(ResultSet kenhIds);
	
	public String[] getSpIds(); 
	public void setSpIds(String[] spIds); 

	public String[] getMasp(); 
	public void setMasp(String[] masp);
	
	public String[] getTthai();
	public void setTthai(String[] tthai); 

	public String getMaspstr();
	public void setMaspstr(String maspstr);
	
	public String[] getTensp(); 
	public void setTensp(String[] tensp); 
	
	public String[] getTensp2(); 

	public void setTensp2(String[] tensp2); 
	
	public String[] getGiaban();
	public void setGiaban(String[] giaban);
	
	public String[] getGiamoi();
	
	public void setGiamoi(String[] giamoi);
	
	public String[] getDonvi(); 
	public void setDonvi(String[] dv); 
	
	public ResultSet getSanPhamList();
	public void setSanPhamList(ResultSet sanphamlist);
	public ResultSet getNewSanPhamList();
	public void setNewSanPhamList(ResultSet newsplist);
	public boolean CreateBgban(HttpServletRequest request); 
	public boolean UpdateBgban(HttpServletRequest request);
	public void createRS();
	public void init();
	public void Chot();
	public void closeDB();
	public boolean ChangeBgban(HttpServletRequest request);
	
	
	public String[][] getData();
	public void setData(String[][] data);
	
	public int getCount();
	public String getDateTime() ;
	public int getNam();	
	public void setNam(int nam) ;

}
