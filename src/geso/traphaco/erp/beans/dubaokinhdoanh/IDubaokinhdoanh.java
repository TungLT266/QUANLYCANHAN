package geso.traphaco.erp.beans.dubaokinhdoanh;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IDubaokinhdoanh 
{
	public String getCtyId(); 
	
	public void setCtyId(String ctyId); 
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNgaydubao();
	public void setNgaydubao(String ngaydubao);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getNhanhang();
	public void setNhanhang(String nhanhang);
	
	public String getChungloai();
	public void setChungloai(String Chungloai);
	
	public String getKho();
	public void setKho(String kho);
	
	public String getSothangdubao();
	public void setSothangdubao(String sothangdubao);
	
	
	
	public String getPhuongphap();
	public void setPhuongphap(String phuongphap);
	
	
	public ResultSet getKhoList();
	public void setKhoList(ResultSet kholist);
	///////
	public ResultSet getNhanhangList();
	public void setNhanhangList(ResultSet nhanhanglist);
	
	public ResultSet getChungloaiList();
	public void setChungloaiList(ResultSet chungloailist);
	
	/////
	public ResultSet getSovoi();
	public void setSovoi(ResultSet sovoi);
	
	public ResultSet getSanPhamRs();
	public void setSanPhamRs(ResultSet sanpham);
	
	public String[] getSanPhamIds();
	public void setSanPhamIds(String[] spIds);
	
	public String getTenPhamIds();
	public void setTenPhamIds(String rensanpham);
	
	public String[] getSelectedSpIds();
	
	public void setSelectedSpIds(String[] selectedSpIds);
	
	public String[][] getData();
	
	public void setData(String[][] data);
	
	public ResultSet getPhuongphapList();
	
	public void setPhuongphapList(ResultSet phuongphaplist);
	
	public void createRs();
	
	public void createRs_New();
	
	public void init();
		
	public void close();
	
	public int getCount();
	
	public void setCount(int count);
	
	public String getMaspstr();
	
	public void setMaspstr(String maspstr);
	
	public boolean update(HttpServletRequest request)  throws ServletException, IOException;
	
	public boolean CreateDubao(HttpServletRequest request)  throws ServletException, IOException;
	
	public String getNgaytonkho();
	
	public void setNgaytonkho(String ngaytonkho); 
	
	public String[] getNgayThang();

	public ResultSet getKhRS();
	
	public void setKhRS(ResultSet khRS);
	
}
