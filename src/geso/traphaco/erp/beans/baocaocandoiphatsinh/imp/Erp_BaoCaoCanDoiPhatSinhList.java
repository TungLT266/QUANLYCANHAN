package geso.traphaco.erp.beans.baocaocandoiphatsinh.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocaocandoiphatsinh.IErp_BaoCaoCanDoiPhatSinhList;
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

public class Erp_BaoCaoCanDoiPhatSinhList  extends Phan_Trang implements IErp_BaoCaoCanDoiPhatSinhList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String congTyId;
	private String msg;
	
	private DKLocBaoCaoKeToan dieuKienLoc;
	final private List<String> colunmNameList = initColunmNameList();
	private List<PhatSinhKeToanItem> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	ResultSet cdpsRs;
	public Erp_BaoCaoCanDoiPhatSinhList()
	{
		this.congTyId = "";
		this.msg= ""; 
		
		this.dieuKienLoc = new DKLocBaoCaoKeToan();
		this.viewList = new ArrayList<PhatSinhKeToanItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	private List<String> initColunmNameList() {
		List<String> list = new ArrayList<String>();
		list.add("Số hiệu tài khoản");
		list.add("Tên tài khoản");
		list.add("Nợ đầu kì");
		list.add("Có đầu kì");
		list.add("Phát sinh nợ");
		list.add("Phát sinh có");
		list.add("Nợ cuối kì");
		list.add("Có cuối kì");
		list.add("Tác vụ");
		return list;
	}
	public void createRs(){
		this.dieuKienLoc.init(db);
	}

	public void init()
	{
			String query = "";
/*				"EXEC [REPORT_CANDOIPHATSINH] " +
				"'" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", " + (this.dieuKienLoc.getCongTyId().trim().length() == 0 ? "'100000'" : this.dieuKienLoc.getCongTyId());
*/			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");


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
			String thangNamDauKy = "0"+ Integer.toString(thangdauky);
			thangNamDauKy = thangNamDauKy.substring(thangNamDauKy.length() - 2);
			thangNamDauKy = Integer.toString(namdauky)+"-"+thangNamDauKy;
			System.out.println("Tháng-năm đầu kỳ" + thangNamDauKy);
			String thangNamPhatSinh = "0"+ Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 7));
			thangNamPhatSinh = thangNamPhatSinh.substring(thangNamPhatSinh.length()-2);
			thangNamPhatSinh = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,4))+"-"+thangNamPhatSinh ;
			System.out.println("Tháng-năm phát sinh" + thangNamPhatSinh);
			
			 query = " SELECT TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,TK.TENTAIKHOAN \n" +
			 "  ,ISNULL(DAUKY.GIATRINOVND, 0) AS DAUKYNOVND,ISNULL(DAUKY.GIATRICOVND, 0) AS DAUKYCOVND \n" +
			 "  ,ISNULL(PHATSINH.GIATRINOVND, 0) AS PHATSINHNOVND,ISNULL(PHATSINH.GIATRICOVND, 0) AS PHATSINHCOVND \n" +
			 "  ,ISNULL(CUOIKY.GIATRINOVND, 0) AS CUOIKYNO,ISNULL(CUOIKY.GIATRICOVND, 0) AS CUOIKYCO \n" +
			 " FROM ERP_TAIKHOANKT TK \n" +
			 " LEFT JOIN ( \n" +
			 "    SELECT TKID,CASE WHEN LOAITKBAOCAO= 1 THEN SUM(NO) - SUM(CO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 2 THEN 0   \n" + 
			 "      WHEN LOAITKBAOCAO= 3 THEN SUM(NO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) >0 THEN SUM(NO) - SUM(CO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) <=0 THEN 0 \n" + 
			 "      END AS GIATRINOVND   \n" + 
			 "     ,CASE WHEN LOAITKBAOCAO= 1 THEN 0   \n" + 
			 "      WHEN LOAITKBAOCAO = 2 THEN (- 1) * (SUM(NO) - SUM(CO))   \n" + 
			 "      WHEN LOAITKBAOCAO= 3 THEN SUM(CO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) <=0 THEN SUM(CO) -SUM(NO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) >0 THEN 0 \n" + 
			 "      END AS GIATRICOVND   \n" + 
			 "    FROM (   \n" + 
			 "     SELECT TKID,LOAITKBAOCAO,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG   \n" + 
			 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 AND LOAITKBAOCAO = 3 THEN SUM(GIATRINOVND) - SUM(GIATRICOVND)  \n" + 
			 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0 AND LOAITKBAOCAO = 3  THEN 0  \n" + 
			 "       ELSE SUM(GIATRINOVND) END AS NO  \n" + 
			 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0  AND LOAITKBAOCAO = 3 THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) * (- 1) \n" + 
			 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0  AND LOAITKBAOCAO = 3 THEN 0 \n" + 
			 "       ELSE SUM(GIATRICOVND)   \n" + 
			 "       END AS CO   \n" + 
			 "   FROM ( \n" +
			 "    SELECT KS.TAIKHOANKT_FK AS TKID,TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG \n" +
			 "     ,SUM(KS.GIATRINOVND) AS GIATRINOVND,SUM(KS.GIATRICOVND) AS GIATRICOVND \n" +
			 "    FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
			 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n" +
			 "    WHERE KS.THANG = "+thangdauky+" AND KS.NAM = "+namdauky+" AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
			 "    GROUP BY KS.TAIKHOANKT_FK,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG,TK.LOAITKBAOCAO \n" +
			 "    UNION ALL \n" +
				 "    SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
				 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
				 "    FROM ERP_PHATSINHKETOAN PS \n" +
				 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
				 "    INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
				 "     WHERE PS.NGAYGHINHAN >'"+thangNamDauKy+"-31"+"' \n" +
				 "	AND PS.NGAYGHINHAN < '" +this.dieuKienLoc.getTuNgay()+ "' \n" +
				 "    AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") AND TK1.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
				 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,TK.LOAITKBAOCAO \n" +
			"    ) DAUKY_PHATSINH_THEODOITUONG \n" +
			 "   GROUP BY DAUKY_PHATSINH_THEODOITUONG.TKID,DAUKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO,DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN \n" +
			 "    ,DAUKY_PHATSINH_THEODOITUONG.MADOITUONG,DAUKY_PHATSINH_THEODOITUONG.DOITUONG,DAUKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO \n" +
			 "   ) DAUKY_PHATSINH_THEOTAIKHOAN \n" +
			 "  GROUP BY TKID,SOHIEUTAIKHOAN,LOAITKBAOCAO \n" +
			 "  ) DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
			 " LEFT JOIN ( \n" +
			 "  SELECT TKID \n" +
			 "   ,SOHIEUTAIKHOAN \n" +
			 "   ,SUM(GIATRINOVND) AS GIATRINOVND \n" +
			 "   ,SUM(GIATRICOVND) AS GIATRICOVND \n" +
			 "  FROM ( \n" +
			 "   SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
			 "    ,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND \n" +
			 "   FROM ERP_PHATSINHKETOAN PS \n" +
			 "   INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
			 "   INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
			 "   WHERE PS.NGAYGHINHAN >= '"+this.dieuKienLoc.getTuNgay()+"' AND PS.NGAYGHINHAN <= '"+this.dieuKienLoc.getDenNgay()+"' \n" + 
			 " AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") AND TK1.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
			 "   GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,TK.LOAITKBAOCAO \n" +
			 " ) PS_THEODOITUONG \n" +
			 "  GROUP BY TKID,SOHIEUTAIKHOAN \n" +
			 "  ) PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
			 " LEFT JOIN ( \n" +
			 "    SELECT TKID,CASE WHEN LOAITKBAOCAO= 1 THEN SUM(NO) - SUM(CO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 2 THEN 0   \n" + 
			 "      WHEN LOAITKBAOCAO= 3 THEN SUM(NO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) >0 THEN SUM(NO) - SUM(CO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) <=0 THEN 0 \n" + 
			 "      END AS GIATRINOVND   \n" + 
			 "     ,CASE WHEN LOAITKBAOCAO= 1 THEN 0   \n" + 
			 "      WHEN LOAITKBAOCAO = 2 THEN (- 1) * (SUM(NO) - SUM(CO))   \n" + 
			 "      WHEN LOAITKBAOCAO= 3 THEN SUM(CO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) <=0 THEN SUM(CO) -SUM(NO)   \n" + 
			 "      WHEN LOAITKBAOCAO= 4 AND SUM(NO) - SUM(CO) >0 THEN 0 \n" + 
			 "      END AS GIATRICOVND   \n" + 
			 "    FROM (   \n" + 
			 "     SELECT TKID,LOAITKBAOCAO,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG   \n" + 
			 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 AND LOAITKBAOCAO = 3 THEN SUM(GIATRINOVND) - SUM(GIATRICOVND)  \n" + 
			 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0 AND LOAITKBAOCAO = 3  THEN 0  \n" + 
			 "       ELSE SUM(GIATRINOVND) END AS NO  \n" + 
			 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0  AND LOAITKBAOCAO = 3 THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) * (- 1) \n" + 
			 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0  AND LOAITKBAOCAO = 3 THEN 0 \n" + 
			 "       ELSE SUM(GIATRICOVND)   \n" + 
			 "       END AS CO   \n" +  
			 "   FROM ( \n" +
			 
			 //Dữ liệu đầu kỳ
			 "    SELECT KS.TAIKHOANKT_FK AS TKID,TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG \n" +
			 "     ,SUM(KS.GIATRINOVND) AS GIATRINOVND,SUM(KS.GIATRICOVND) AS GIATRICOVND \n" +
			 "    FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
			 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n" +
			 "    WHERE KS.THANG = "+thangdauky+" AND KS.NAM = "+namdauky+" AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
			 "    GROUP BY KS.TAIKHOANKT_FK,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG,TK.LOAITKBAOCAO \n" +
			 "    UNION ALL \n" +
			 "    SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
			"     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
			"    FROM ERP_PHATSINHKETOAN PS \n" +
			"    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
			"    INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
			"     WHERE PS.NGAYGHINHAN >'"+thangNamDauKy+"-31"+"' \n" +
			 "    AND PS.NGAYGHINHAN <= '"+this.dieuKienLoc.getDenNgay()+"' \n" +
			"    AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") AND TK1.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
			"    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,TK.LOAITKBAOCAO \n" +
			 //Có dấu = ở trên nên comment phần này lại
			 //Dữ liệu phát sinh
			/* "    UNION ALL \n" +
			 "    SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
			 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
			 "    FROM ERP_PHATSINHKETOAN PS \n" +
			 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
			 "    INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
			 "    WHERE month(PS.NGAYGHINHAN) = "+this.month+" AND YEAR(PS.NGAYGHINHAN) = "+this.year+" AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") AND TK1.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
			 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
			 "    UNION ALL \n" +
			 "	SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"]," +
			 "    'SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
			 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
			 "    FROM " + Utility.prefixLinkDMS + "ERP_PHATSINHKETOAN PS \n" +
			 "    INNER JOIN " + Utility.prefixLinkDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
			 "    INNER JOIN " + Utility.prefixLinkDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
			 "    WHERE month(PS.NGAYGHINHAN) = "+this.month+" AND YEAR(PS.NGAYGHINHAN) = "+this.year+" AND TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") AND TK1.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+") \n" +
			 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG') \n" +*/
			 "    ) CUOIKY_PHATSINH_THEODOITUONG \n" +
			 "   GROUP BY CUOIKY_PHATSINH_THEODOITUONG.TKID,CUOIKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO,CUOIKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN \n" +
			 "    ,CUOIKY_PHATSINH_THEODOITUONG.MADOITUONG,CUOIKY_PHATSINH_THEODOITUONG.DOITUONG,CUOIKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO \n" +
			 "   ) CUOIKY_PHATSINH_THEOTAIKHOAN \n" +
			 "  GROUP BY TKID,SOHIEUTAIKHOAN,LOAITKBAOCAO \n" +
			 "  ) CUOIKY ON CUOIKY.TKID = TK.PK_SEQ \n" +
			 " WHERE TK.NPP_FK IN ("+this.dieuKienLoc.getCongTyId()+")  AND ((DAUKY.GIATRICOVND <>0 OR DAUKY.GIATRINOVND <>0) OR (PHATSINH.GIATRICOVND<>0 OR PHATSINH.GIATRINOVND<>0)) \n" +
			 " ORDER BY SOHIEUTAIKHOAN ";
	
			
			System.out.println("Can doi phat sinh: " + query);
				  		
			this.cdpsRs = this.db.get(query);
				
/*				if (rs != null)
				{
					int stt = 1;
					while (rs.next())
					{
						//N'Số hiệu tài khoản',b.TENTAIKHOAN N'Tên tài khoản', no_dk N'Nợ đầu kỳ',co_dk N'Có đầu kỳ',ps_no N'Nợ'
						//,ps_co N'Có',no_ck N'Nợ cuối kỳ',co_ck N'Có cuối kỳ'
						String soHieuTaiKhoan = rs.getString("soHieuTaiKhoan");
						String loaiTaiKhoan = PhatSinhKeToanItem.getLoaiTaiKhoan(soHieuTaiKhoan);
						String tenTaiKhoan = rs.getString("tenTaiKhoan");
						double noDauKy = rs.getDouble("no_dk");
						double coDauKy = rs.getDouble("co_dk");
						double no = rs.getDouble("ps_no");
						double co = rs.getDouble("ps_co");
						double noCuoiKy = rs.getDouble("no_ck");
						double coCuoiKy = rs.getDouble("co_ck");
						
						PhatSinhKeToanItem item = new PhatSinhKeToanItem();
						item.setStt(stt);
						item.setSoHieuTaiKhoan(soHieuTaiKhoan);
						item.setTaiKhoan(loaiTaiKhoan);
						item.setTenTaiKhoan(tenTaiKhoan);
						item.setNoDauKy(noDauKy);
						item.setCoDauKy(coDauKy);
						item.setNo(no);
						item.setCo(co);
						item.setNoCuoiKy(noCuoiKy);
						item.setCoCuoiKy(coCuoiKy);
						
						this.viewList.add(item);
						stt++;
					}
					
					PhatSinhKeToanItem.getLoaiTaiKhoan(db, this.viewList, this.dieuKienLoc.getTaiKhoanCongNoList());
					rs.close();
*/	
			
	}
	
	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream(fileName);
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
/*			String query =
				"EXEC [REPORT_CANDOIPHATSINH] " +
				"'" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", " + this.dieuKienLoc.getCongTyId();
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			ResultSet rs = this.db.get(query);
*/			
		    Cell cell = cells.getCell("A4");
		    
		    cell = cells.getCell("A4");
		    cell.setValue("Từ ngày: " + this.dieuKienLoc.getTuNgay() + " - " + this.dieuKienLoc.getDenNgay());
		    
             
			ResultSetMetaData rsmd = this.cdpsRs.getMetaData();
			int rowNumber = rsmd.getColumnCount();
			int countRow = 12;
						
			int stt = 0;
			while(this.cdpsRs.next())
			{
				stt++;
				for(int i = 2;i <=rowNumber ; i ++)
				{
					cell = cells.getCell(countRow, i - 2);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(this.cdpsRs.getDouble(i));
						cell = ReportAPI.CreateBorderSetting2(cell);
					}
					else
					{
						cell.setValue(this.cdpsRs.getString(i));
						cell = ReportAPI.CreateBorderSetting2(cell);
					}
				}
				++countRow;
			}
			
			for(int i = 1; i <= rowNumber ; i++)
			{
				ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i - 1));
			}
			
			workbook.save(out);
			fstream.close();
			
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

	public List<String> getColunmNameList() {
		return colunmNameList;
	}
	
	public ResultSet getCdpsRs(){
		return this.cdpsRs;
	}
}