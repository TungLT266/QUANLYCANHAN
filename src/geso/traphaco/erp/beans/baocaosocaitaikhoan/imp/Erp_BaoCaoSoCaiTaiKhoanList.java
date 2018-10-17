package geso.traphaco.erp.beans.baocaosocaitaikhoan.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocaosocaitaikhoan.IErp_BaoCaoSoCaiTaiKhoanList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Erp_BaoCaoSoCaiTaiKhoanList  extends Phan_Trang implements IErp_BaoCaoSoCaiTaiKhoanList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String congTyId;
	private String msg;
	
	private DKLocBaoCaoKeToan dieuKienLoc;
	private List<String> colunmNameList;
	private List<PhatSinhKeToanItem> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public Erp_BaoCaoSoCaiTaiKhoanList()
	{
		this.congTyId = "";
		this.msg= ""; 
		
		this.dieuKienLoc = new DKLocBaoCaoKeToan();
		this.colunmNameList = new ArrayList<String>();
		this.viewList = new ArrayList<PhatSinhKeToanItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		this.dieuKienLoc.init(db);
		
//		String [] param = {"x...."};
		//Bắt buộc nhập chọn tài khoản công nợ
		if (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0 || this.dieuKienLoc.getNhomTaiKhoanId().trim().length() > 0)
		{
			//Lấy pk_seq của tài khoản thuộc chi nhánh
			//String query = "select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + this.dieuKienLoc.getSoHieuTaiKhoanNo() + "' and TRANGTHAI = 1 and npp_fk = " + this.dieuKienLoc.getCongTyId();
			String query = " SELECT (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '"+this.dieuKienLoc.getSoHieuTaiKhoanNo()+"' and TRANGTHAI = 1 and npp_fk = "+this.dieuKienLoc.getCongTyId()+")  TK, "+
						   " (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '"+this.dieuKienLoc.getSoHieuTaiKhoanCo()+"' and TRANGTHAI = 1 and npp_fk = "+this.dieuKienLoc.getCongTyId()+")  TKDU ";	
			System.out.println("TK " + query);
			try {
			ResultSet rs = this.db.get(query);
			String taiKhoanId = "";
			String taiKhoanDoiUngId = "NULL";
			if (rs != null)
			{
				if (rs.next())
				{
					taiKhoanId = rs.getString("TK");
					taiKhoanDoiUngId = rs.getString("TKDU");
				}
				rs.close();
			}
			
			/*query =
				"EXEC [REPORT_SOCAITAIKHOAN_V2] " +
				"'" + taiKhoanId + "'" +
				",'" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", " + this.dieuKienLoc.getCongTyId();*/
			int thangdauky ;
			int namdauky ;
			int lastmonth ;
			int lastyear ;
			lastyear = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,4)) - 1;
			if(Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 7)) >1 )
				lastmonth = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 7)) - 1;
			else
				lastmonth = 12;
			if(lastmonth != 12){
				thangdauky = lastmonth;
				namdauky = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,4));
			}else{
				thangdauky = lastmonth;
				namdauky = lastyear;
			}
			int thangks ;
			int namks ;
			//Chọn tháng khóa sổ gần nhất
			String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
					 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
					 "WHERE TK.NPP_FK =  " + this.dieuKienLoc.getCongTyId() + 
					 "ORDER BY NAM DESC,THANG DESC";
			ResultSet rsKhoaSo = db.get(sqlKhoaSo);
			int thangKS = 0;
			int namKS = 0;
			if(rsKhoaSo!= null){
				try {
					while(rsKhoaSo.next()){
						thangKS = rsKhoaSo.getInt("THANG");
						namKS = rsKhoaSo.getInt("NAM");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					String error = "[ERROR 1.1] Không thể lấy tháng KS gần nhất : "+ e.toString();
					System.out.println(error);
					this.msg = error;
				}
			}
			if(thangKS >= thangdauky && namKS >= namdauky){
				
			}else{
				thangdauky = thangKS;
				namdauky = namKS;
			}
			
			query = "";
			String thangDauky = "0"+ thangdauky;
			thangDauky = thangDauky.substring(thangDauky.length() -2);
			
			query =
				"EXEC [REPORT_SOCAITAIKHOAN_NHOMDT] " +
				"" + taiKhoanId + "" +
				"," + taiKhoanDoiUngId + "" +
				",'" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", " + this.dieuKienLoc.getCongTyId()+" ,'"+this.dieuKienLoc.getNhomKhachHangId()+"'" + ",'"+this.dieuKienLoc.getNhomTaiKhoanId()+"' "+
				", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
				", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			
			
	//			rs = this.db.getRsByPro("MyPro...", param);
	//			rs = createSplittingData(50, 10, "NGAYTAO desc, NGAYSUA desc, PK_SEQ desc", query);
				rs = this.db.get(query);
				ResultSetMetaData rsmd = rs.getMetaData();
				int rowNumber = rsmd.getColumnCount();
				for (int i = 1; i < rowNumber; i++)
					this.colunmNameList.add(rsmd.getColumnLabel(i));
				
				if (rs != null)
				{
					int stt = 1;
					while (rs.next())
					{
						//N'Số chứng từ',a.NGAYGHINHAN N'Ngày ghi nhận',a.NGAYCHUNGTU N'Ngày chứng từ',a.DIENGIAI N'Diễn giải'
						//, b.sohieutaikhoan as  N'Số hiệu tài khoản đối ứng', a.PS_NO N'Phát sinh nợ', a.PS_CO N'Phát sinh có'
						String soChungTu = rs.getString(1);
						String ngayGhiNhan = rs.getString(2);
						String ngayChungTu = rs.getString(3);
						String dienGiai = rs.getString(4);
						String soHieuTaiKhoanDoiUng = rs.getString(5);
						double no = rs.getDouble(6);
						double co = rs.getDouble(7);
						String loaiChungTu = rs.getString(8);
						String maChungTu = rs.getString(9);
						String doiTuong = rs.getString(10);
						String doiTuongDoiUng = rs.getString(11);
						
				
						if (doiTuong == null || doiTuong.toLowerCase().equals("null"))
							doiTuong = "";
						

						if (doiTuongDoiUng == null || doiTuongDoiUng.toLowerCase().equals("null"))
							doiTuongDoiUng = "";
						
						
						
						PhatSinhKeToanItem item = new PhatSinhKeToanItem();
						item.setStt(stt);
						item.setSoChungTu(soChungTu);
						item.setNgayGhiNhan(ngayGhiNhan);
						item.setNgayChungTu(ngayChungTu);
						item.setDienGiai(dienGiai);
						item.setSoHieuTaiKhoanDoiUng(soHieuTaiKhoanDoiUng == null?"":soHieuTaiKhoanDoiUng);
						item.setNo(no);
						item.setCo(co);
						item.setLoaiChungTu(loaiChungTu);
						item.setMaChungTu(maChungTu);
						item.setDoiTuong(doiTuong);
						item.setDoiTuongDoiUng(doiTuongDoiUng);
						this.viewList.add(item);
						stt++;
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
			if (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0 || this.dieuKienLoc.getNhomTaiKhoanId().trim().length() > 0)
			{
				String SOHIEUTAIKHOAN="";
				String TENTAIKHOAN="";
				//Lấy pk_seq của tài khoản thuộc chi nhánh
				String query = "select PK_SEQ,SOHIEUTAIKHOAN,TENTAIKHOAN from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + this.dieuKienLoc.getSoHieuTaiKhoanNo() + "' and TRANGTHAI = 1 and npp_fk = " + this.dieuKienLoc.getCongTyId();
				ResultSet rs = this.db.get(query);
				String taiKhoanId = "";
				if (rs != null)
				{
					if (rs.next())
					{
						taiKhoanId = rs.getString(1);
						TENTAIKHOAN=rs.getString("TENTAIKHOAN");
						SOHIEUTAIKHOAN=rs.getString("SOHIEUTAIKHOAN");
					}
					rs.close();
				}
				
				query = " SELECT (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '"+this.dieuKienLoc.getSoHieuTaiKhoanNo()+"' and TRANGTHAI = 1 and npp_fk = "+this.dieuKienLoc.getCongTyId()+")  TK, "+
						   " (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '"+this.dieuKienLoc.getSoHieuTaiKhoanCo()+"' and TRANGTHAI = 1 and npp_fk = "+this.dieuKienLoc.getCongTyId()+")  TKDU ";	
				System.out.println("TK " + query);
				
					ResultSet rss = this.db.get(query);
					String taiKhoanDoiUngId = "NULL";
					if (rss != null)
					{
						if (rss.next())
						{
							taiKhoanDoiUngId = rss.getString("TKDU");
						}
						rss.close();
					}
				
				
				FileInputStream fstream = null;
				Workbook workbook = new Workbook();
				
				fstream = new FileInputStream(fileName);
				
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				worksheet.setName("Sheet1");
				
				Cells cells = worksheet.getCells();
				query =
					"EXEC [REPORT_SOCAITAIKHOAN_NHOMDT] " +
					"" + taiKhoanId + "" +
					"," + taiKhoanDoiUngId + "" +
					",'" + this.dieuKienLoc.getTuNgay() + "'" +
					", '" + this.dieuKienLoc.getDenNgay()  + "'" +
					", " + this.dieuKienLoc.getCongTyId()+" ,'"+this.dieuKienLoc.getNhomKhachHangId()+"'" + ",'"+this.dieuKienLoc.getNhomTaiKhoanId()+"' "+
					", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
					", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
				
//				int thangdauky ;
//				int namdauky ;
//				int lastmonth ;
//				int lastyear ;
//				lastyear = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,4)) - 1;
//				if(Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 7)) >1 )
//					lastmonth = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 7)) - 1;
//				else
//					lastmonth = 12;
//				if(lastmonth != 12){
//					thangdauky = lastmonth;
//					namdauky = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,4));
//				}else{
//					thangdauky = lastmonth;
//					namdauky = lastyear;
//				}
//				int thangks ;
//				int namks ;
//				//Chọn tháng khóa sổ gần nhất
//				String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
//						 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
//						 "WHERE TK.NPP_FK =  " + this.dieuKienLoc.getCongTyId() + 
//						 "ORDER BY NAM DESC,THANG DESC";
//				ResultSet rsKhoaSo = db.get(sqlKhoaSo);
//				int thangKS = 0;
//				int namKS = 0;
//				if(rsKhoaSo!= null){
//					try {
//						while(rsKhoaSo.next()){
//							thangKS = rsKhoaSo.getInt("THANG");
//							namKS = rsKhoaSo.getInt("NAM");
//						}
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						String error = "[ERROR 1.1] Không thể lấy tháng KS gần nhất : "+ e.toString();
//						System.out.println(error);
//						this.msg = error;
//					}
//				}
//				if(thangKS >= thangdauky && namKS >= namdauky){
//					
//				}else{
//					thangdauky = thangKS;
//					namdauky = namKS;
//				}
//				
//				query = "";
//				String thangDauky = "0"+ thangdauky;
//				thangDauky = thangDauky.substring(thangDauky.length() -2);
//				
//				query =  "  BEGIN \n" +
//						 "     create table #PSKT \n" +
//						 "     ( \n" +
//						 "  	[SS] [numeric](1, 0) NULL  default '', \n" +
//						 "  	[SOCHUNGTU] [numeric](18, 0) NULL  default '', \n" +
//						 "  	[NGAYGHINHAN] [NVARCHAR] (10) NULL  default '', \n" +
//						 "  	[NGAYCHUNGTU] [NVARCHAR] (10) NULL  default '', \n" +
//						 "  	[DIENGIAI]	[NVARCHAR] (200) NULL default '', \n" +
//						 "  	[TAIKHOANDOIUNG_FK] [numeric](18, 0) NULL  default '', \n" +
//						 "  	[PS_NO] [float] NULL default 0, \n" +
//						 "  	[PS_CO] [float] NULL default 0, \n" +
//						 "  	[LOAICHUNGTU] [nvarchar](50) default '', \n" +
//						 "  	[MACHUNGTU] [nvarchar](50) default '' \n" +
//						 "     ) \n" +
//						 "      INSERT INTO #PSKT \n" +
//						 "  	SELECT '1','0','','',N'Số dư đầu kỳ','0',SUM(DAUKY.PS_NO) AS PS_NO,SUM(DAUKY.PS_CO) AS PS_CO, \n" +
//						 "  	'' AS LOAICHUNGTU,'' AS MACHUNGTU  \n" +
//						 "  	FROM \n" +
//						 "  	( \n" +
//						 "  	SELECT ROUND(ISNULL(KS.GIATRINOVND,0),0) AS PS_NO,ROUND(ISNULL(KS.GIATRICOVND,0),0) AS PS_CO \n" +
//						 "  	FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
//						 "  	INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n" +
//						 "  	WHERE THANG = "+thangdauky+" AND NAM = "+namdauky+" AND TK.PK_SEQ = "+taiKhoanId+" \n" +
//						 "  	AND ("+this.dieuKienLoc.getCongTyId()+" = 0 or TK.NPP_FK = "+this.dieuKienLoc.getCongTyId()+") \n" +
//						 "  	UNION ALL \n" +
//						 "  	SELECT ROUND(ISNULL(PS.NO,0),0) AS PS_NO,ROUND(ISNULL(PS.CO,0),0) AS PS_CO \n" +
//						 "  	FROM ERP_PHATSINHKETOAN PS \n" +
//						/* "  	INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK AND  \n" +
//						 "  	("+this.dieuKienLoc.getCongTyId()+" = 0 or TK.NPP_FK = "+this.dieuKienLoc.getCongTyId()+") \n" +
//						 "  	INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND \n" +
//						 "  	("+this.dieuKienLoc.getCongTyId()+" = 0 or TK1.NPP_FK = "+this.dieuKienLoc.getCongTyId()+") \n" +*/
//						 "  	WHERE PS.NGAYGHINHAN >= '"+namdauky+"-"+ thangDauky +"-32'" +  " \n" +
//						 "  	AND PS.NGAYGHINHAN < '"+this.dieuKienLoc.getTuNgay()+"' AND TAIKHOAN_FK = "+taiKhoanId+" \n" +
//						 "  	UNION ALL \n" +
//						 "		SELECT ISNULL(PS_NO,0) AS PS_NO, ISNULL(PS_CO,0) AS PS_CO FROM OPENQUERY([LINK_DMS_THAT_NOIBO],'" +
//						 "  	SELECT ROUND(ISNULL(PS.NO,0),0) AS PS_NO,ROUND(ISNULL(PS.CO,0),0) AS PS_CO \n" +
//						 "  	FROM DATACENTER.DBO.ERP_PHATSINHKETOAN PS \n" +
//						 /*"  	INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK AND  \n" +
//						 "  	("+this.dieuKienLoc.getCongTyId()+" = 0 or TK.NPP_FK = "+this.dieuKienLoc.getCongTyId()+") \n" +
//						 "  	INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND \n" +
//						 "  	("+this.dieuKienLoc.getCongTyId()+" = 0 or TK1.NPP_FK = "+this.dieuKienLoc.getCongTyId()+") \n" +*/
//						 "  	WHERE PS.NGAYGHINHAN >= ''"+namdauky+"-"+ thangDauky +"-32''" +  " \n" +
//						 "  	AND PS.NGAYGHINHAN <''"+this.dieuKienLoc.getTuNgay()+"'' AND TAIKHOAN_FK = "+taiKhoanId+" ')\n" +
//						 "  	)DAUKY \n" +
//						 "  	   \n" +
//						 "  	                    \n" +
//						 "      --Lấy phát sinh \n" +
//						 "      INSERT INTO #PSKT  \n" +
//						 "      SELECT '2',SOCHUNGTU,NGAYGHINHAN,NGAYCHUNGTU, \n" +
//						 "      CASE WHEN DIENGIAI IS NULL THEN DIENGIAI_CT ELSE DIENGIAI END AS DIENGIAI , \n" +
//						 "      TAIKHOANDOIUNG_FK,ROUND(NO,0) AS PS_NO,ROUND(CO,0) AS PS_CO,LOAICHUNGTU,MACHUNGTU \n" +
//						 "  	FROM ERP_PHATSINHKETOAN  \n" +
//						 "      WHERE NGAYGHINHAN >= '"+this.dieuKienLoc.getTuNgay()+"'   AND NGAYGHINHAN <=  '"+this.dieuKienLoc.getDenNgay()+"' and TAIKHOAN_FK = "+taiKhoanId+"\n" +
//						 "		UNION ALL " +
//						 "		SELECT * FROM OPENQUERY([LINK_DMS_THAT_NOIBO],'" +
//						 "      SELECT ''2'',SOCHUNGTU,NGAYGHINHAN,NGAYCHUNGTU, \n" +
//						 "      CASE WHEN DIENGIAI IS NULL THEN DIENGIAI_CT ELSE DIENGIAI END AS DIENGIAI , \n" +
//						 "      TAIKHOANDOIUNG_FK,ROUND(NO,0) AS PS_NO,ROUND(CO,0) AS PS_CO,LOAICHUNGTU,MACHUNGTU \n" +
//						 "  	FROM DATACENTER.DBO.ERP_PHATSINHKETOAN  \n" +
//						 "      WHERE NGAYGHINHAN >= ''"+this.dieuKienLoc.getTuNgay()+"''   AND NGAYGHINHAN <=  ''"+this.dieuKienLoc.getDenNgay()+"'' and TAIKHOAN_FK = "+taiKhoanId+" ') \n" +
//						 "       \n" +
//						 "  	--Tính tổng phát sinh  \n" +
//						 "  	INSERT INTO #PSKT  \n" +
//						 "      SELECT '3','0','','',N'Tổng phát sinh','0',SUM(PS_NO) AS PS_NO,SUM(PS_CO) AS PS_CO, \n" +
//						 "  	'' AS LOAICHUNGTU,'' AS MACHUNGTU	           \n" +
//						 "      FROM #PSKT WHERE SS = 2 \n" +
//						 "  	 \n" +
//						 "  	--Tính giá trị cuối kỳ	 \n" +
//						 "  	INSERT INTO #PSKT  \n" +
//						 "      SELECT '4','0','','',N'Số dư cuối kỳ','0', \n" +
//						 "      CASE WHEN SUM(PS_NO-PS_CO)>0 THEN SUM(PS_NO-PS_CO) ELSE 0 END AS PS_NO, \n" +
//						 "  	CASE WHEN SUM(PS_CO-PS_NO)>0 THEN SUM(PS_CO-PS_NO) ELSE 0 END AS PS_CO, \n" +
//						 "  	'' AS LOAICHUNGTU,'' AS MACHUNGTU	           \n" +
//						 "      FROM #PSKT  WHERE SS <> 3 \n" +
//						 "   \n" +
//						 "    \n" +
//						 "  	SELECT CASE WHEN CONVERT(VARCHAR, ISNULL(PSKT.MACHUNGTU, PSKT.SOCHUNGTU)) = 'NULL'  \n" +
//						 "  	THEN CONVERT(VARCHAR, PSKT.SOCHUNGTU) ELSE ISNULL(PSKT.MACHUNGTU,PSKT.SOCHUNGTU) END AS N'Số chứng từ', \n" +
//						 "  	PSKT.NGAYGHINHAN N'Ngày ghi nhận',PSKT.NGAYCHUNGTU N'Ngày chứng từ', \n" +
//						 "  	ISNULL(PSKT.DIENGIAI,'') N'Diễn giải chứng từ', TK.SOHIEUTAIKHOAN AS N'Số hiệu tài khoản đối ứng', \n" +
//						 "  	PSKT.PS_NO AS N'Phát sinh nợ',PSKT.PS_CO AS N'Phát sinh có' ,PSKT.LOAICHUNGTU AS N'Loại chứng từ', \n" +
//						 "  	PSKT.SOCHUNGTU AS N'Mã chứng từ', \n" +
//						 "  	CASE WHEN PS_NO > 0 THEN 1 WHEN PS_CO >0 THEN 2 ELSE 3 END SAPXEP \n" +
//						 "  	FROM #PSKT PSKT  \n" +
//						 "  	LEFT JOIN ERP_TAIKHOANKT TK ON PSKT.TAIKHOANDOIUNG_FK = TK.PK_SEQ \n" +
//						 "  	WHERE PS_NO >0 OR PS_CO > 0  \n" +
//						 "  	ORDER BY PSKT.SS,PSKT.NGAYGHINHAN,PSKT.NGAYCHUNGTU,SAPXEP,PSKT.MACHUNGTU,PSKT.SOCHUNGTU,TK.SOHIEUTAIKHOAN \n" +
//						 "  END \n" ;
//				
				System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
				rs = this.db.get(query);
				
			    Cell cell = cells.getCell("A4");
			    
			    cell = cells.getCell("A5");
			    cell.setValue("Từ ngày: " + this.dieuKienLoc.getTuNgay() + " - " + this.dieuKienLoc.getDenNgay());
			    
			    
			    cell = cells.getCell("A4");
			    cell.setValue(" Tài khoản: " + SOHIEUTAIKHOAN + " - " + TENTAIKHOAN);
	             
				ResultSetMetaData rsmd = rs.getMetaData();
				int rowNumber = rsmd.getColumnCount();
				int countRow = 10;
							
				int stt = 0;
				while(rs.next())
				{
					stt++;
					for(int i = 1;i <rowNumber ; i ++)
					{
						cell = cells.getCell(countRow, i - 1);
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
						{
							cell.setValue(rs.getDouble(i));
							cell = ReportAPI.CreateBorderSetting2(cell,41);
						}
						else
						{
							cell.setValue(rs.getString(i));
							cell = ReportAPI.CreateBorderSetting2(cell);
						}
					}
					++countRow;
				}
				
				for(int i = 1; i < rowNumber ; i++)
				{
					ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i - 1));
				}
				
				workbook.save(out);
				fstream.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void DBClose()
	{
		if (this.db != null)
		{
			this.db.shutDown();
		}
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}
	public int[] getListPages() {
		return listPages;
	}
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}
	public int getCurrentPages() {
		return currentPages;
	}
	public void setCurrentPages(int currentPages) {
		this.currentPages = currentPages;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setDieuKienLoc(DKLocBaoCaoKeToan dieuKienLoc) {
		this.dieuKienLoc = dieuKienLoc;
	}

	public DKLocBaoCaoKeToan getDieuKienLoc() {
		return dieuKienLoc;
	}

	public void setViewList(List<PhatSinhKeToanItem> viewList) {
		this.viewList = viewList;
	}

	public List<PhatSinhKeToanItem> getViewList() {
		return viewList;
	}

	public void setColunmNameList(List<String> colunmNameList) {
		this.colunmNameList = colunmNameList;
	}

	public List<String> getColunmNameList() {
		return colunmNameList;
	}
	
	@Override
	public void getLoaiTaiKhoan() {
		if(this.getDieuKienLoc().getSoHieuTaiKhoanNo().trim().length() == 0)
			return;
		String query = "SELECT TOP 1 ISNULL(DUNGCHONCC,0) DUNGCHONCC, ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG , " +
				" ISNULL(DUNGCHONHANVIEN,0) DUNGCHONHANVIEN,  ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '" + this.getDieuKienLoc().getSoHieuTaiKhoanNo() + "'";
		System.out.println("lay tai khoan " + query);
		ResultSet rs = db.get(query);
		try {
			
			rs.next();
			if(rs.getString("DUNGCHONCC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("2");
			}
			
			if(rs.getString("DUNGCHOKHACHHANG").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("0");
			}
			
			if(rs.getString("DUNGCHONHANVIEN").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("1");
			}
		
			if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("3");
			}
			
			this.getDieuKienLoc().setNhomDoiTuongId("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}