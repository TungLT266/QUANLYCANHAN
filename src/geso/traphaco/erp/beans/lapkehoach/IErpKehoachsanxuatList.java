package geso.traphaco.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpKehoachsanxuatList 
{
		public String getCtyId(); 
		public void setCtyId(String ctyId); 
	
		public String getUserId();
		public void setUserId(String userId);
	
		public String getTrangthai();
		public void setTrangthai(String trangthai);
		
		public String getDiengiai();
		public void setDiengiai(String diengiai);
		public String getMsg();
		public void setMsg(String msg);
		
		
		public ResultSet getKehoachSXRs();
		public void setKehoachSXRs(ResultSet khsxRs);
		
		public void init();
		
		public void DbClose();
	
		public String getNam(); 
	
		public void setNam(String nam); 
		
		public String getThang(); 
	
		public void setThang(String thang); 
		public String getDateTime();
		
		public ResultSet getChungloaiRS();
		
		public void setChungloaiRS(ResultSet clRS);
		
		public String getClId();
		
		public void setClId(String clId);
		
		public String getSpId();
	
		public void setSpId(String spId) ;
		
		public ResultSet getSanphamRS();
		
		public void setSanphamRS(ResultSet spRS);

		public ResultSet getKehoachSX_Ngay(String ngay, String dtsxId);
		
		public ResultSet getDaytruyenSX();
		public ResultSet getDaytruyenSX_2();
		public String getDtsxId();
		public void setDtsxId(String dtsxId);
		
		public void TaoKeHoachSanXuat();
		public int getSongay();
	}
