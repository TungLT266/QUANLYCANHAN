package geso.traphaco.erp.beans.vayvon.imp;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.vayvon.IThanhtoanlaivayList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThanhtoanlaivayList implements IThanhtoanlaivayList {
	String SoHD;
	String ngay;
	ResultSet ttnvRS;
	String userId;
	String msg;
	String ctyId;
	dbutils db;
	
	String tkvay;
	
	List<IThongTinHienThi> hienthiList;
	
	public ThanhtoanlaivayList()
	{   
		this.SoHD = "";
	    this.ctyId = "";
	    this.msg ="";
	    this.tkvay = "";
	    this.hienthiList = new ArrayList<IThongTinHienThi>();
	    db = new dbutils();
	}

	public String getTkvay() {
		return tkvay;
	}

	public void setTkvay(String tkvay) {
		this.tkvay = tkvay;
	}

	public void setSoHD(String SoHD) {
		this.SoHD = SoHD;
		
	}

	public String getSoHD() {
		return this.SoHD;
	}
	

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public ResultSet getTtnvRS() {
		
		return this.ttnvRS;
	}

	private String LayDuLieu(String id) {
		
		String laytk="";
		String query="";
		query =  "select  a.TIENTE_FK, isnull( ( select TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK = a.TIENTE_FK and TuNgay <= a.NGAY and a.NGAY <= DenNgay ), 0 ) as TYGIA,  " + 
		 "		a.TIENGOC, a.TIENLAI, a.TIENPHAT, a.PHIKHAC, a.NGAY,  " + 
		 "		( select nganhang_fk from ERP_NGANHANG_CONGTY where PK_SEQ = a.TKCONGTY_FK   )   nganhang_fk, " +
		 "		( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where PK_SEQ = a.TKCONGTY_FK   )   taikhoanCO, " + 
		 "		case when a.tiente_fk = '100000' and b.loai = 1 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '34113000') " +
		 "			 when a.tiente_fk = '100001' and b.loai = 1 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '34114000') " +
		 "           when a.tiente_fk = '100000' and b.loai = 2 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '34110000') " +
		 "           when a.tiente_fk = '100001' and b.loai = 2 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '34120000') " +
		 "           else '' end as TK_NO_TIENGOC,"+
		 "      (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63510000' ) as TK_NO_TIENLAI, "+
		 "      (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '81180000' ) as TK_NO_TIENPHAT, "+
		 "      (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '64250000' ) as TK_NO_PHIKHAC " + 
		 "from ERP_THANHTOANLAIVAY a " +
		 "inner join ERP_HOPDONGVAY b on a.HOPDONG_FK = b.PK_SEQ " + 
		 "where a.PK_SEQ = '" + id + "' ";
		
		int i = 1;
		ResultSet psktRs = db.get(query);
		try{
			if(psktRs != null)
			{
				while(psktRs.next())
				{
					double tygia = psktRs.getDouble("TYGIA");
					
					double tiengoc = Math.round(psktRs.getDouble("TIENGOC"));
					double tiengocViet = Math.round( tygia * tiengoc );
					
					String nam = psktRs.getString("NGAY").substring(0, 4);
					String thang = psktRs.getString("NGAY").substring(5, 7);
					String tiente_fk = psktRs.getString("TIENTE_FK");
					
					String nganhang_fk = psktRs.getString("nganhang_fk");
					
					if(tiengoc > 0)
					{
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_TIENGOC") == null ? "" : psktRs.getString("TK_NO_TIENGOC") ;
						
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+tiengoc+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+tiengoc+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
					}
					double tienlai = Math.round(psktRs.getDouble("TIENLAI"));
					double tienlaiViet = Math.round( tygia * tienlai );
					
					if(tienlai > 0)
					{
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_TIENLAI") == null ? "" : psktRs.getString("TK_NO_TIENLAI") ;
						
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+tienlai+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+tienlai+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
						
					}
					
					double tienphat = Math.round(psktRs.getDouble("TIENPHAT"));
					double tienphatViet = Math.round( tygia * tienphat );
					
					if(tienphat > 0)
					{
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_TIENPHAT") == null ? "" : psktRs.getString("TK_NO_TIENPHAT") ;
						
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+tienphat+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+tienphat+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
					}
					
					double phikhac = Math.round(psktRs.getDouble("PHIKHAC"));
					double phikhacViet = Math.round( tygia * phikhac );
					
					if(phikhac > 0)
					{
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_PHIKHAC") == null ? "" : psktRs.getString("TK_NO_PHIKHAC") ;
						
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+phikhac+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+phikhac+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
					}
				}
				psktRs.close();
			}
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
		
		if(laytk.trim().length()<=0 ){
			laytk = " SELECT '' NO_CO, HD.PK_SEQ, '' SOHIEUTAIKHOAN,  '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
					" FROM ERP_THANHTOANLAIVAY HD \n"+ 
					" WHERE HD.PK_SEQ = '"+id+"' \n";			
		}
				
		return laytk;
	}
	

	private String LayDuLieuOLD(String id) {
		
		String laytk="";
		String query="";
		query =  "select  a.TIENTE_FK, isnull( ( select TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK = a.TIENTE_FK and TuNgay <= a.NGAY and a.NGAY <= DenNgay ), 0 ) as TYGIA,  " + 
		 "		a.TIENGOC, a.TIENLAI, a.TIENPHAT, a.PHIKHAC, a.NGAY,  " + 
		 "		( select nganhang_fk from ERP_NGANHANG_CONGTY where PK_SEQ = a.TKCONGTY_FK   )   nganhang_fk, " +
		 "		( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where PK_SEQ = a.TKCONGTY_FK   )   taikhoanCO, " + 
		 "		case when a.tiente_fk = '100000' and b.loai = 1 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '31111000') " +
		 "			 when a.tiente_fk = '100001' and b.loai = 1 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '31120000') " +
		 "           when a.tiente_fk = '100000' and b.loai = 2 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '34110000') " +
		 "           when a.tiente_fk = '100001' and b.loai = 2 then  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '34120000') " +
		 "           else '' end as TK_NO_TIENGOC,"+
		 "      (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63510000' ) as TK_NO_TIENLAI, "+
		 "      (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '81180000' ) as TK_NO_TIENPHAT, "+
		 "      (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '64250000' ) as TK_NO_PHIKHAC " + 
		 "from ERP_THANHTOANLAIVAY a " +
		 "inner join ERP_HOPDONGVAY b on a.HOPDONG_FK = b.PK_SEQ " + 
		 "where a.PK_SEQ = '" + id + "' ";
		
		int i = 1;
		ResultSet psktRs = db.get(query);
		try{
			if(psktRs != null)
			{
				while(psktRs.next())
				{
					double tygia = psktRs.getDouble("TYGIA");
					
					double tiengoc = Math.round(psktRs.getDouble("TIENGOC"));
					double tiengocViet = Math.round( tygia * tiengoc );
					
					String nam = psktRs.getString("NGAY").substring(0, 4);
					String thang = psktRs.getString("NGAY").substring(5, 7);
					String tiente_fk = psktRs.getString("TIENTE_FK");
					
					String nganhang_fk = psktRs.getString("nganhang_fk");
					
					if(tiengoc > 0)
					{
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_TIENGOC") == null ? "" : psktRs.getString("TK_NO_TIENGOC") ;
						
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+tiengoc+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+tiengoc+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
					}
					double tienlai = Math.round(psktRs.getDouble("TIENLAI"));
					double tienlaiViet = Math.round( tygia * tienlai );
					
					if(tienlai > 0)
					{
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_TIENLAI") == null ? "" : psktRs.getString("TK_NO_TIENLAI") ;
						
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+tienlai+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+tienlai+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
						
					}
					
					double tienphat = Math.round(psktRs.getDouble("TIENPHAT"));
					double tienphatViet = Math.round( tygia * tienphat );
					
					if(tienphat > 0)
					{
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_TIENPHAT") == null ? "" : psktRs.getString("TK_NO_TIENPHAT") ;
						
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+tienphat+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+tienphat+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
					}
					
					double phikhac = Math.round(psktRs.getDouble("PHIKHAC"));
					double phikhacViet = Math.round( tygia * phikhac );
					
					if(phikhac > 0)
					{
						if(laytk.trim().length()>0) laytk += " UNION ALL ";
						
						String taikhoanCO = psktRs.getString("taikhoanCO") == null ? "" : psktRs.getString("taikhoanCO") ;
						String taikhoanNO = psktRs.getString("TK_NO_PHIKHAC") == null ? "" : psktRs.getString("TK_NO_PHIKHAC") ;
						
						laytk +=
							" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanNO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		 "+phikhac+" AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n"+
							 
							" UNION ALL \n"+
							 
							" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_TAIKHOANKT  WHERE PK_SEQ = "+taikhoanCO+") AS SOHIEUTAIKHOAN, \n"+ 
							"		"+phikhac+" AS SOTIEN, (SELECT TEN FROM ERP_NGANHANG WHERE PK_SEQ = "+nganhang_fk+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+ 
							" FROM ERP_THANHTOANLAIVAY HD \n"+ 
							" WHERE HD.PK_SEQ = '"+id+"' \n";
						i++;
					}
				}
				psktRs.close();
			}
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
		
		if(laytk.trim().length()<=0 ){
			laytk = " SELECT '' NO_CO, HD.PK_SEQ, '' SOHIEUTAIKHOAN,  '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
					" FROM ERP_THANHTOANLAIVAY HD \n"+ 
					" WHERE HD.PK_SEQ = '"+id+"' \n";			
		}
				
		return laytk;
	}
	
	public void init(String st) {
		
		String sql="";
		if(st.length()>0)
		{
			sql =	"SELECT	TTNV.PK_SEQ, HDV.SOHD, NTV.TKVAY, TTNV.NGAY, ISNULL(TTNV.TIENLAI, 0) AS TIENLAI, ISNULL(TTNV.TIENGOC, 0) AS TIENGOC, HDV.TIENTE_FK AS TIENTE, " +
					"ISNULL(TTNV.TIENPHAT, 0) AS TIENPHAT, ISNULL(TTNV.PHIKHAC, 0) AS PHIKHAC, TTNV.TRANGTHAI, TTNV.GHICHU " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_THANHTOANLAIVAY TTNV ON TTNV.HOPDONG_FK = HDV.PK_SEQ " +
					"INNER JOIN ERP_NHANTIENVAY NTV on NTV.PK_SEQ = TTNV.NHANTIENVAY_FK " +
					"WHERE TTNV.CONGTY_FK = " + this.ctyId + " " + st + " ORDER BY TTNV.NGAY DESC, HDV.SOHD ";
		}else
			sql =	"SELECT	TTNV.PK_SEQ, HDV.SOHD,  NTV.TKVAY, TTNV.NGAY, ISNULL(TTNV.TIENLAI, 0) AS TIENLAI, ISNULL(TTNV.TIENGOC, 0) AS TIENGOC, HDV.TIENTE_FK AS TIENTE, " +
					"ISNULL(TTNV.TIENPHAT, 0) AS TIENPHAT, ISNULL(TTNV.PHIKHAC, 0) AS PHIKHAC, TTNV.TRANGTHAI, TTNV.GHICHU " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_THANHTOANLAIVAY TTNV ON TTNV.HOPDONG_FK = HDV.PK_SEQ " +
					"INNER JOIN ERP_NHANTIENVAY NTV on NTV.PK_SEQ = TTNV.NHANTIENVAY_FK " +
					"WHERE TTNV.CONGTY_FK = " + this.ctyId + " ORDER BY TTNV.NGAY DESC, HDV.SOHD   ";
			
		System.out.println("chuoi: "+ sql);
		String query_init = sql;
		ResultSet rs = db.get(query_init);
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{
					
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("PK_SEQ"));
					System.out.println("chuỗi sai:" +dk);
					ResultSet rsKT = db.get(dk);
					
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"),"");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("PK_SEQ"));
						ht.setSohopdong(rs.getString("SOHD"));
						ht.setTKVAY(rs.getString("TKVAY"));
						ht.setNgayghinhan(rs.getString("NGAY"));
						ht.setTienlai(rs.getString("TIENLAI"));
						ht.setTiengoc(rs.getString("TIENGOC"));
						ht.setTienphat(rs.getString("TIENPHAT"));
						ht.setTiente(rs.getString("TIENTE"));
						ht.setPhikhac(rs.getString("PHIKHAC"));
						ht.setTrangthai(rs.getString("TRANGTHAI"));
						ht.setGhichu(rs.getString("GHICHU"));
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);																	
				}
				rs.close();
			}
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.hienthiList = htList;
	}
	
	public void Xoa(String Id){
		
	}
	
	public void setUserId(String userId) {
	    this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
		
	public void setMsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getMsg() {
		
		return this.msg;
	}
	
	public void DbClose() {
		
		try{
			if(ttnvRS !=null){
				ttnvRS.close();
			}
			db.shutDown();
		
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}
}
