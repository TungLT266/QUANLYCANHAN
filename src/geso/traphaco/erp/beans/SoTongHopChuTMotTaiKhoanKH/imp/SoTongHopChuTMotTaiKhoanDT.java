package geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.imp;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.ISoTongHopChuTMotTaiKhoanDT;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.ISoTongHopChuTMotTaiKhoanKH;
import geso.traphaco.erp.db.sql.dbutils;

public class SoTongHopChuTMotTaiKhoanDT implements ISoTongHopChuTMotTaiKhoanDT{
	private String tuNgay, denNgay, taiKhoan, chiNhanhTen, congTyTen, congTy,Khachhang,chinhanhdoitac; 
	private String chiNhanh,nhomKhachHang;
	private dbutils db;
	private ResultSet chiNhanhRs, taiKhoanRs, phatSinhRs,khachhangRs,nhomKhachHangRs;
	private Double dauKyNo, dauKyCo, tongPSNo, tongPSCo, cuoiKyNo, cuoiKyCo;
	private String loaiDoiTuong;
	private String querykh;
	private DKLocBaoCaoKeToan dieuKienLoc;
	public SoTongHopChuTMotTaiKhoanDT() {
		super();
		this.tuNgay = "";
		this.denNgay = "";
		this.taiKhoan = "";
		this.chiNhanh = "1";
		this.congTyTen = "";
		this.congTy = "";
		this.chiNhanhTen = "";
		this.querykh ="";
		this.dauKyNo = 0.0;
		this.dauKyCo = 0.0;
		this.cuoiKyNo = 0.0;
		this.cuoiKyCo = 0.0;
		this.tongPSNo = 0.0;
		this.tongPSCo = 0.0;
		
		this.chinhanhdoitac="";
		this.loaiDoiTuong ="";
		this.Khachhang="";
		this.nhomKhachHang="";
		this.dieuKienLoc = new DKLocBaoCaoKeToan();

		this.db = new dbutils();
	}

	public SoTongHopChuTMotTaiKhoanDT(String tuNgay, String denNgay,
			String taiKhoan, String chiNhanh, String congTy) {
		super();
		this.tuNgay = tuNgay;
		this.denNgay = denNgay;
		this.taiKhoan = taiKhoan;
		this.chiNhanh = chiNhanh;
		this.congTy = congTy;
		this.congTyTen = "";
		this.querykh ="";
		this.chiNhanhTen = "";
		this.dauKyNo = 0.0;
		this.dauKyCo = 0.0;
		this.cuoiKyNo = 0.0;
		this.cuoiKyCo = 0.0;
		this.tongPSNo = 0.0;
		this.tongPSCo = 0.0;
		this.chinhanhdoitac="";
		this.loaiDoiTuong ="";
		this.Khachhang="";
		this.dieuKienLoc = new DKLocBaoCaoKeToan();

		this.db = new dbutils();
	}
	@Override
	public String getTuNgay() {
		return tuNgay;
	}

	@Override
	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
	}

	@Override
	public String getDenNgay() {
		return denNgay;
	}

	@Override
	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
	}

	@Override
	public String getTaiKhoan() {
		return taiKhoan;
	}

	@Override
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	@Override
	public String getChiNhanh() {
		return chiNhanh;
	}

	@Override
	public void setChiNhanh(String chiNhanh) {
		this.chiNhanh = chiNhanh;
	}


	@Override
	public ResultSet getPhatSinhRs() {
		return phatSinhRs;
	}

	@Override
	public void setPhatSinhRs(ResultSet phatSinhRs) {
		this.phatSinhRs = phatSinhRs;
	}

	@Override
	public Double getDauKyNo() {
		return dauKyNo;
	}

	@Override
	public void setDauKyNo(Double dauKyNo) {
		this.dauKyNo = dauKyNo;
	}
	
	

	@Override
	public Double getDauKyCo() {
		return dauKyCo;
	}

	@Override
	public void setDauKyCo(Double dauKyCo) {
		this.dauKyCo = dauKyCo;
	}


	@Override
	public Double getTongPSNo() {
		return tongPSNo;
	}

	@Override
	public void setTongPSNo(Double tongPSNo) {
		this.tongPSNo = tongPSNo;
	}

	@Override
	public Double getTongPSCo() {
		return tongPSCo;
	}

	@Override
	public void setTongPSCo(Double tongPSCo) {
		this.tongPSCo = tongPSCo;
	}

	@Override
	public Double getCuoiKyNo() {
		return cuoiKyNo;
	}

	@Override
	public void setCuoiKyNo(Double cuoiKyNo) {
		this.cuoiKyNo = cuoiKyNo;
	}

	@Override
	public Double getCuoiKyCo() {
		return cuoiKyCo;
	}

	@Override
	public void setCuoiKyCo(Double cuoiKyCo) {
		this.cuoiKyCo = cuoiKyCo;
	}

	@Override
	public ResultSet getChiNhanhRs() {
		return chiNhanhRs;
	}

	@Override
	public void setChiNhanhRs(ResultSet chiNhanhRs) {
		this.chiNhanhRs = chiNhanhRs;
	}


	@Override
	public ResultSet getTaiKhoanRs() {
		return taiKhoanRs;
	}

	@Override
	public void setTaiKhoanRs(ResultSet taiKhoanRs) {
		this.taiKhoanRs = taiKhoanRs;
	}

	
	private String getQueryPhatSinh() {
		String chiNhanhQuery = "";
		if (chiNhanh.equals("All")) {
			chiNhanhQuery =  "SELECT PK_SEQ FROM NHAPHANPHOI WHERE ISKHACHHANG = 0 AND TRANGTHAI = 1";
		} else {
			chiNhanhQuery = this.chiNhanh;
		}
		String query = "SELECT TK.SOHIEUTAIKHOAN AS TAIKHOANDOIUNG, TK.TENTAIKHOAN, SUM(NO) AS NO, SUM(CO) AS CO \n" + 
				"FROM ERP_PHATSINHKETOAN PS \n" + 
				"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" + 
				"WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '"+this.taiKhoan+"' AND NPP_FK IN ("+chiNhanhQuery+")) \n" +
				"AND PS.NGAYGHINHAN BETWEEN '"+this.tuNgay+"' AND '"+this.denNgay+"' \n"+
				""+querykh+""+
				"GROUP BY TK.SOHIEUTAIKHOAN, TK.TENTAIKHOAN \n";
		/*query += "UNION ALL \n";
		query += "SELECT TK.SOHIEUTAIKHOAN AS TAIKHOANDOIUNG, TK. TENTAIKHOAN, SUM(NO) AS NO, SUM(CO) AS CO \n" + 
				"FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS \n" + 
				"INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" + 
				"WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '"+this.taiKhoan+"' AND NPP_FK IN ("+chiNhanhQuery+")) \n" + 
				"AND PS.NGAYGHINHAN BETWEEN '"+this.tuNgay+"' AND '"+this.denNgay+"' \n"+
				""+querykh+""+
				"GROUP BY TK.SOHIEUTAIKHOAN, TK.TENTAIKHOAN \n";*/
		System.out.println("Query: -----\n"+query);
		
	
		return query;
		
	}
	@Override
	public void getSoPhatSinh() {
		String query = getQueryPhatSinh();
		String query1 = "SELECT DATA.TAIKHOANDOIUNG,DATA.TENTAIKHOAN, SUM(NO) AS NO, SUM(CO) AS CO FROM (" ;
		query1 += query + ") DATA ";
		query1 += " GROUP BY DATA.TAIKHOANDOIUNG, DATA.TENTAIKHOAN \n";
		query1 += " ORDER BY DATA.TAIKHOANDOIUNG \n";
		this.phatSinhRs = db.get(query1);
	}

	@Override
	public ResultSet getTongSoPhatSinh() {
		String query = 
				"SELECT SUM(NO) AS NO, SUM(CO) AS CO \n" + 
				"FROM \n" + 
				"( \n";
		query += getQueryPhatSinh();
		query += ") PHATSINH ";
		System.out.println("Query getTongSoPhatSinh: -----\n"+query);
		ResultSet rs = db.get(query);
		return rs;
	}

	@Override
	public ResultSet getQuery(int loaiQuery) {
		/*
		 * loaiQuery = 0 - DAUKY
		 * loaiQuery = 0 - CUOIKY
		 */
		String chiNhanhQuery = "";
		chiNhanhQuery = this.chiNhanh;
		if (chiNhanh.equals("All")) {
			chiNhanhQuery =  "SELECT PK_SEQ FROM NHAPHANPHOI WHERE ISKHACHHANG = 0 AND TRANGTHAI = 1";
		} 
		
		
		
		System.out.println("Chi nhanh doi tac"+chinhanhdoitac);
		

		//Chọn tháng khóa sổ gần nhất
		String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
				 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
				 "WHERE TK.NPP_FK IN (  " + chiNhanhQuery + " ) \n"+
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
			}
		}
		
		
		int lastyear = Integer.parseInt(this.tuNgay.substring(0, 4)) - 1;
		int lastmonth = 0;
		
		if (Integer.parseInt(this.tuNgay.substring(5, 7)) > 1){
			lastmonth = Integer.parseInt(this.tuNgay.substring(5, 7)) - 1;
		}else{
			lastmonth = 12;
		}
			
		int thangdauky = 0;
		int namdauky = 0;
		if(lastmonth != 12){
			thangdauky = lastmonth;
			namdauky = Integer.parseInt(this.tuNgay.substring(0, 4));
		}else{
			thangdauky = lastmonth;
			namdauky = lastyear;
		}
		
		
		if (thangKS != 0 && namKS != 0){
			if(thangKS >= thangdauky && namKS >= namdauky){
				
			}
			else {
				thangdauky = thangKS;
				namdauky = namKS;
			}
		}
		
		Utility util = new Utility();
		String ngayKhoaSo=util.getNgayKhoaSo(db, this.tuNgay);
		
		String query = "SELECT GIATRINOVND AS NO, GIATRICOVND AS CO FROM   \n" + 
				"( \n" + 
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
				"			FROM \n" + 
				"				( \n" + 
				"					SELECT TKID AS TKID,LOAITKBAOCAO,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG,SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND " +
				"					FROM \n" +
				"					(\n" +
				"					--BEGIN LAY DU LIEU DAU KY \n" + 
				"					SELECT KS.TAIKHOANKT_FK AS TKID,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, \n" + 
				"					CASE WHEN KS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng' THEN KS.QUAY_FK ELSE KS.MADOITUONG END MADOITUONG, \n" + 
				"					CASE WHEN ((KS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng') OR (KS.DOITUONG=N'Khách hàng' AND ISNULL(ISNPP,0)=1)) THEN N'Chi nhánh/Đối tác' ELSE KS.DOITUONG END AS DOITUONG, \n" + 
				"						SUM (KS.GIATRINOVND) AS GIATRINOVND, \n" + 
				"						SUM (KS.GIATRICOVND) AS GIATRICOVND \n" + 
				"					FROM \n" + 
				"						ERP_TAIKHOAN_NOCO_KHOASO KS \n" + 
				"					INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n" + 
				"					WHERE KS.THANG = MONTH('"+ngayKhoaSo+"') \n" + 
				"					AND KS.NAM = YEAR('"+ngayKhoaSo+"')  \n" + 
				"								AND TK.NPP_FK IN ("+chiNhanhQuery+") AND TK.SOHIEUTAIKHOAN IN ("+this.taiKhoan+") \n" + 
				" "+querykh+" " +
				"					GROUP BY KS.TAIKHOANKT_FK,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG,KS.QUAY_FK,KS.ISNPP \n";
			query += 
					"						UNION ALL \n" + 
					"					SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, \n" + 
					"					CASE WHEN PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng' THEN PS.QUAY_FK ELSE PS.MADOITUONG END MADOITUONG, \n" + 
					"					CASE WHEN ((PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng') OR (PS.DOITUONG=N'Khách hàng' AND ISNULL(ISNPP,0)=1)) THEN N'Chi nhánh/Đối tác' ELSE PS.DOITUONG END AS DOITUONG, \n" + 
					"						SUM (ROUND(ISNULL(PS. NO, 0), 0)) AS GIATRINOVND, \n" + 
					"						SUM (ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" + 
					"					FROM ERP_PHATSINHKETOAN PS \n" + 
					"					INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" + 
					"					INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" + 
					"					WHERE PS.NGAYGHINHAN > '"+ngayKhoaSo+"' AND PS.NGAYGHINHAN < '"+this.tuNgay+"' \n" + 
					"								AND TK.NPP_FK IN ("+chiNhanhQuery+") AND TK.SOHIEUTAIKHOAN IN ("+this.taiKhoan+") \n" + 
					" "+querykh+" " +
					"					GROUP BY PS.TAIKHOAN_FK,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.QUAY_FK,PS.ISNPP \n";
			/*query += 
					"						UNION ALL \n" + 
					"					SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, " +
					"					CASE WHEN PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng' THEN PS.QUAY_FK ELSE PS.MADOITUONG END MADOITUONG, \n" + 
					"					CASE WHEN ((PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng') OR (PS.DOITUONG=N'Khách hàng' AND ISNULL(ISNPP,0)=1)) THEN N'Chi nhánh/Đối tác' ELSE PS.DOITUONG END AS DOITUONG, \n" + 
					"						SUM (ROUND(ISNULL(PS. NO, 0), 0)) AS GIATRINOVND, \n" + 
					"						SUM (ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" + 
					"					FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS \n" + 
					"					INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" + 
					"					INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" + 
					"					WHERE PS.NGAYGHINHAN > '"+ngayKhoaSo+"' AND PS.NGAYGHINHAN < '"+this.tuNgay+"' \n" + 
					"								AND TK.NPP_FK IN ("+chiNhanhQuery+") AND TK.SOHIEUTAIKHOAN IN ("+this.taiKhoan+") \n" + 
					"					 " +querykh +" "+
					"					GROUP BY PS.TAIKHOAN_FK,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.QUAY_FK ,PS.ISNPP \n" + 
					"					--END LAY DU LIEU DAU KY \n ";*/
		if (loaiQuery == 1) {
			query += 
				"						UNION ALL \n" + 
				"					SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, " +
				"					CASE WHEN PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng' THEN PS.QUAY_FK ELSE PS.MADOITUONG END MADOITUONG, \n" + 
				"					CASE WHEN ((PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng') OR (PS.DOITUONG=N'Khách hàng' AND ISNULL(ISNPP,0)=1)) THEN N'Chi nhánh/Đối tác' ELSE PS.DOITUONG END AS DOITUONG, \n" + 
				"						SUM (ROUND(ISNULL(PS. NO, 0), 0)) AS GIATRINOVND, \n" + 
				"						SUM (ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" + 
				"					FROM ERP_PHATSINHKETOAN PS \n" + 
				"					INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" + 
				"					INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" + 
				"					WHERE PS.NGAYGHINHAN BETWEEN '"+this.tuNgay+"' AND '"+this.denNgay+"' \n"+
				"								AND TK.NPP_FK IN ("+chiNhanhQuery+") AND TK.SOHIEUTAIKHOAN IN ("+this.taiKhoan+") \n" + 
				"					 " +querykh +" "+
				"					GROUP BY PS.TAIKHOAN_FK,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG ,PS.QUAY_FK ,PS.ISNPP \n" + 
				"					--Begin Tích hợp phát sinh DMS \n" ; 
				/*"						UNION ALL \n" + 
				"					SELECT PS.TAIKHOAN_FK AS TKID,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, " +
				"					CASE WHEN PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng' THEN PS.QUAY_FK ELSE PS.MADOITUONG END MADOITUONG, \n" + 
				"					CASE WHEN ((PS.QUAY_FK IS NOT NULL AND DOITUONG= N'Khách hàng') OR (PS.DOITUONG=N'Khách hàng' AND ISNULL(ISNPP,0)=1)) THEN N'Chi nhánh/Đối tác' ELSE PS.DOITUONG END AS DOITUONG, \n" + 
				"						SUM (ROUND(ISNULL(PS. NO, 0), 0)) AS GIATRINOVND, \n" + 
				"						SUM (ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" + 
				"					FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS \n" + 
				"					INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" + 
				"					INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" + 
				"					WHERE PS.NGAYGHINHAN BETWEEN '"+this.tuNgay+"' AND '"+this.denNgay+"' \n"+
				"					 " +querykh +" "+
				"								AND TK.NPP_FK IN ("+chiNhanhQuery+") AND TK.SOHIEUTAIKHOAN IN ("+this.taiKhoan+") \n" + 
				"					GROUP BY PS.TAIKHOAN_FK,TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG ,PS.QUAY_FK ,PS.ISNPP \n" + 
				"				--End Tích hợp phát sinh DMS \n";*/
		}
		query +=
				"               )DATA \n"+
				"					GROUP BY TKID,LOAITKBAOCAO, SOHIEUTAIKHOAN, MADOITUONG,DOITUONG  \n" +
				
				"				) DAUKY_PHATSINH_THEODOITUONG \n" + 
				"			GROUP BY \n" + 
				"				DAUKY_PHATSINH_THEODOITUONG.TKID, \n" + 
				"				DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN, \n" + 
				"				DAUKY_PHATSINH_THEODOITUONG.MADOITUONG, \n" + 
				"				DAUKY_PHATSINH_THEODOITUONG.DOITUONG,     \n" + 
				"				DAUKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO   \n" + 
				"	) DAUKY_PHATSINH_THEOTAIKHOAN \n" + 
				"	GROUP BY TKID,LOAITKBAOCAO	 \n" + 
				") FINAL_TEMP";
		System.out.println("Query: -----\n"+query);
		ResultSet rs = db.get(query);
		return rs;
	}
	
	@Override
	public void initRs() {
		this.dieuKienLoc.setCongTyId(this.chiNhanh);
		this.dieuKienLoc.init(db);
		
		String query = "SELECT PK_SEQ, MA, TEN FROM NHAPHANPHOI WHERE ISKHACHHANG = 0 AND TRANGTHAI = 1";
		this.chiNhanhRs = db.get(query);
		query = "SELECT PK_SEQ, SOHIEUTAIKHOAN, TENTAIKHOAN FROM ERP_TAIKHOANKT WHERE TRANGTHAI = 1 AND NPP_FK = 1  ";
		this.taiKhoanRs = db.get(query);
		query = "SELECT PK_SEQ, DIENGIAI FROM NHOMKHACHHANGNPP WHERE TRANGTHAI = 1";
		this.nhomKhachHangRs = db.get(query);

		
			
	}

	@Override
	public void initBC() {
		if (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0)
		{
			//Lấy pk_seq của tài khoản thuộc chi nhánh
			String query = "select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + this.dieuKienLoc.getSoHieuTaiKhoanNo() + "' and TRANGTHAI = 1 and npp_fk = " + this.dieuKienLoc.getCongTyId();
			try {
				ResultSet rs = this.db.get(query);
				String taiKhoanId = "";
				if (rs != null)
				{
					if (rs.next())
					{
						taiKhoanId = rs.getString(1);
					}
					rs.close();
				}
				System.out.println("Chi nhanh doi tac"+chinhanhdoitac);
			    query =
						"SELECT * FROM  [FN_REPORT_SOCHUTKHACHHANG_NHOMDT] " +
						"('" + this.tuNgay + "'" +
						", '" + this.denNgay  + "'" +
						", '" + taiKhoanId + "'" +
						", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
						", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
						", " + this.dieuKienLoc.getCongTyId() + 
						", '" +this.nhomKhachHang+"' " + 
						", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
						", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' "+
						")" ;
				System.out.println("query "+query);
				this.phatSinhRs=db.getScrol(query);
		    }catch(Exception ex)
			{
		    	ex.printStackTrace();
		    }
		}
	}
	public void layThongTinKhac() {
		String query = "SELECT TEN FROM ERP_CONGTY WHERE PK_SEQ = "+this.congTy;
		ResultSet rs = this.db.get(query);
		try {
			if (rs.next()){
				if(this.congTyTen.length() == 0)
					this.congTyTen = rs.getString("TEN");
			}	
			if (chiNhanh.equals("All")) {
				chiNhanhTen =  "Tất cả chi nhánh";
			} else {
				query = "SELECT TEN, DIACHI, MASOTHUE FROM NHAPHANPHOI WHERE PK_SEQ IN (" + chiNhanh + ") ";
				rs = this.db.get(query);
				if (rs.next()){
					if(this.chiNhanhTen.length() == 0)
						this.chiNhanhTen = rs.getString("TEN");
				}
			}
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean xuatExcel (ServletOutputStream out, String fileName) {
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
			Style style;
			cells.setColumnWidth(0, 12f);
			cells.setColumnWidth(1, 60f);
			cells.setColumnWidth(2, 16f);
			cells.setColumnWidth(3, 16f);
			
			this.layThongTinKhac();
			
		    Cell cell = cells.getCell("A1");
		    cell.setValue("Công ty: "+this.congTyTen);
		    
		    cell = cells.getCell("A2");
		    cell.setValue("Chi nhánh: "+this.chiNhanhTen);
			
		    cell = cells.getCell("B5");
		    cell.setValue("Tài khoản: "+this.taiKhoan);
			
		    cell = cells.getCell("B6");
		    cell.setValue("Từ ngày: " + this.tuNgay + " đến ngày: " + this.denNgay);

			this.initBC();

				
			

			ResultSetMetaData rsmd = phatSinhRs.getMetaData();
			int rowNumber = rsmd.getColumnCount();
			int index = 11;
			double noDauKy=0;
			double coDauKy=0;
			double noPhatSinh=0;
			double coPhatSinh=0;
			double noCuoiKy=0;
			double coCuoiKy=0;
						

			while(phatSinhRs.next())
			{

				
				if(phatSinhRs.getString("LOAI").equals("2"))
				{
					cell = cells.getCell("A"+index);
					style = cell.getStyle();
					style.setNumber(0);		
					cell.setStyle(style);
					cell.setValue(phatSinhRs.getString("SOHIEUTAIKHOAN"));
					cell = ReportAPI.CreateBorderSetting3(cell);
					
					
					cell = cells.getCell("B"+index);
					style = cell.getStyle();
					style.setNumber(0);		
					cell.setStyle(style);
					cell.setValue(phatSinhRs.getString("TENTAIKHOAN"));
					cell = ReportAPI.CreateBorderSetting3(cell);
					
					
					cell = cells.getCell("C"+index);
					style = cell.getStyle();
					style.setNumber(3);		
					cell.setStyle(style);
					cell.setValue(phatSinhRs.getDouble("NOPHATSINH"));
					cell = ReportAPI.CreateBorderSetting3(cell);
					
					
					cell = cells.getCell("D"+index);
					style = cell.getStyle();
					style.setNumber(3);		
					cell.setStyle(style);
					cell.setValue(phatSinhRs.getDouble("COPHATSINH"));
					cell = ReportAPI.CreateBorderSetting3(cell);
					
					index++;
					
				}else if(phatSinhRs.getString("LOAI").equals("3"))
				{
					noDauKy=phatSinhRs.getDouble("NODAUKY");
					coDauKy=phatSinhRs.getDouble("CODAUKY");
					noPhatSinh=phatSinhRs.getDouble("NOPHATSINH");
					coPhatSinh=phatSinhRs.getDouble("COPHATSINH");
					noCuoiKy=phatSinhRs.getDouble("NOCUOIKY");
					coCuoiKy=phatSinhRs.getDouble("COCUOIKY");			
				}
				
				
			}

//			for(int i = 1; i <= rowNumber ; i++)
//			{
//				ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i - 1));
//			}
//			countRow++;
		    
			
		    cell = cells.getCell("B8");
		    cell.setValue("Số dư nợ đầu kỳ: ");
		    cell = cells.getCell("C8");
		    cell.setValue(noDauKy);
			style = cell.getStyle();
			style.setNumber(3);		
			cell.setStyle(style);
			
			
			cell = cells.getCell("D8");
		    cell.setValue(coDauKy);
			style = cell.getStyle();
			style.setNumber(3);		
			cell.setStyle(style);
			
			
			
			cell = cells.getCell("B"+(index));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);		
			cell.setStyle(style);
		    cell.setValue("Phát sinh trong kỳ: ");
		    cell = cells.getCell("C"+(index));
		    cell.setValue(noPhatSinh);
			style = cell.getStyle();
			style.setNumber(3);		
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
		    cell = cells.getCell("D"+(index));
		    
		    cell.setValue(coPhatSinh);
			style = cell.getStyle();
			style.setNumber(3);		
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			index++;
		    
		    cell = cells.getCell("B"+(index));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);		
			cell.setStyle(style);
		    cell.setValue("Số dư cuối kỳ: ");
		    cell = cells.getCell("C"+(index));
		    cell.setValue(noCuoiKy);
			style = cell.getStyle();
			style.setNumber(3);		
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
		    cell = cells.getCell("D"+(index));
		    
		    cell.setValue(coCuoiKy);
			style = cell.getStyle();
			style.setNumber(3);		
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			
			workbook.save(out);
			fstream.close();
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void DBClose()
	{
		try {
			if (this.chiNhanhRs != null){
				this.chiNhanhRs.close();
			}
			if (this.taiKhoanRs != null){
				this.taiKhoanRs.close();
			}
			if (this.phatSinhRs != null){
				this.phatSinhRs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (this.db != null)
		{
			this.db.shutDown();
		}
	}

	public ResultSet getKhachhangRs() {
		return khachhangRs;
	}

	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}

	public String getKhachhang() {
		return Khachhang;
	}

	public void setKhachhang(String khachhang) {
		Khachhang = khachhang;
	}

	@Override
	public void setKhachhangRs() {
		// TODO Auto-generated method stub
		
	}

	public String getChinhanhdoitac() {
		return chinhanhdoitac;
	}

	public void setChinhanhdoitac(String chinhanhdoitac) {
		this.chinhanhdoitac = chinhanhdoitac;
	}

	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	public String getQuerykh() {
		return querykh;
	}

	public void setQuerykh(String querykh) {
		this.querykh = querykh;
	}

	public ResultSet getNhomKhachHangRs() {
		return nhomKhachHangRs;
	}

	public void setNhomKhachHangRs(ResultSet nhomKhachHangRs) {
		this.nhomKhachHangRs = nhomKhachHangRs;
	}

	public String getNhomKhachHang() {
		return nhomKhachHang;
	}

	public void setNhomKhachHang(String nhomKhachHang) {
		this.nhomKhachHang = nhomKhachHang;
	}

	public DKLocBaoCaoKeToan getDieuKienLoc() {
		return dieuKienLoc;
	}

	public void setDieuKienLoc(DKLocBaoCaoKeToan dieuKienLoc) {
		this.dieuKienLoc = dieuKienLoc;
	}

	@Override
	public void getLoaiTaiKhoan() {
		if(this.getDieuKienLoc().getSoHieuTaiKhoanNo().trim().length() == 0){
			this.getDieuKienLoc().setLoaiNhom("");
			return;
		}
			
		String query = "SELECT TOP 1 ISNULL(DUNGCHONCC,0) DUNGCHONCC, ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG , " +
				" ISNULL(DUNGCHONHANVIEN,0) DUNGCHONHANVIEN,  ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '" + this.getDieuKienLoc().getSoHieuTaiKhoanNo() + "'";
		System.out.println("lay tai khoan " + query);
		ResultSet rs = db.get(query);
		try {
			
			rs.next();
			if(rs.getString("DUNGCHONCC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("2");
			}
			
			else if(rs.getString("DUNGCHOKHACHHANG").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("0");
			}
			
			else if(rs.getString("DUNGCHONHANVIEN").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("1");
			}
		
			else if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("3");
			}
			else {
				this.getDieuKienLoc().setLoaiNhom("");
			}
			this.getDieuKienLoc().setNhomDoiTuongId("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}