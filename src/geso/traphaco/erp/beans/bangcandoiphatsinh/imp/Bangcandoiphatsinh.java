package geso.traphaco.erp.beans.bangcandoiphatsinh.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.bangcandoiphatsinh.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bangcandoiphatsinh implements IBangcandoiphatsinh {
	String userId;
	String ctyId;
	String ctyTen;
	String year;
	String month;
	String diachi;
	String masothue;
	ResultSet rs;

	String msg;
	dbutils db;

	ResultSet nppRs;
	String nppId;
	String ErpCongTyId;
	String view;

	public Bangcandoiphatsinh() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.year = "";
		this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));
		this.year = getDate().substring(0, 4);
		this.diachi = "";
		this.masothue = "";
		this.view = "0";
		this.msg = "";
		this.nppId = "";
		this.db = new dbutils();
	}

	public void setNppId(String nppId){
		this.nppId = nppId;
	}
	public String getNppId(){
		return this.nppId;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}

	public String getErpCongtyId() {

		return this.ErpCongTyId;
	}

	public void setErpCongtyId(String id) {

		this.ErpCongTyId = id;
	}

	public void setNPPRs(ResultSet nppRs) {

		this.nppRs = nppRs;
	}

	public ResultSet getNPPRs() {

		return this.nppRs;
	}


	public void setuserId(String userId) {

		this.userId = userId;
	}

	public String getuserId() {

		return this.userId;
	}

	public void setCtyId(String ctyId) {

		this.ctyId = ctyId;
	}

	public String getCtyId() {

		return this.ctyId;
	}

	public String getCtyTen() {
		return this.ctyTen;
	}

	public String getDiachi() {

		return this.diachi;
	}

	public String getMasothue() {

		return this.masothue;
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public String getMonth() {
		if (this.month.length() > 0) {
			return this.month;
		} else {
			return this.getDate().substring(5, 7);
		}

	}

	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if (this.year.length() > 0) {
			return this.year;
		} else {
			return this.getDate().substring(0, 4);
		}
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public ResultSet getData() {
		return this.rs;
	}

	public void init() {
		String sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND TRANGTHAI = 1 AND PK_SEQ =1";
		System.out.println(sql);
		this.nppRs = db.get(sql);

		ResultSet rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		System.out.println("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		try {
			if (rs != null) {
				rs.next();
				this.ctyTen = rs.getString("TEN");
				this.diachi = rs.getString("DIACHI");
				System.out.println(this.diachi);

				this.masothue = rs.getString("MASOTHUE");
				System.out.println(this.masothue);

				rs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void createRs(){
		String sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND TRANGTHAI = 1";
		System.out.println("GET ResultSet NHAPHANPHOI : "+sql);
		this.nppRs = db.get(sql);
	}
	public void getQuery() {
		if(this.year.length() > 0 & this.month.length() > 0){
			//Chọn tháng khóa sổ gần nhất
			String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
					 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
					 "WHERE TK.NPP_FK =  " + this.nppId + 
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
			
			
			int lastyear = Integer.parseInt(this.year) - 1;
			int lastmonth = 0;
			
			if (Integer.parseInt(this.month) > 1){
				lastmonth = Integer.parseInt(this.month) - 1;
			}else{
				lastmonth = 12;
			}
				
			int thangdauky = 0;
			int namdauky = 0;
			if(lastmonth != 12){
				thangdauky = lastmonth;
				namdauky = Integer.parseInt(this.year);
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
			String thangNamDauKy = "0"+ Integer.toString(thangdauky);
			thangNamDauKy = thangNamDauKy.substring(thangNamDauKy.length() - 2);
			thangNamDauKy = Integer.toString(namdauky)+"-"+thangNamDauKy;
			System.out.println("Tháng-năm đầu kỳ" + thangNamDauKy);
			String thangNamPhatSinh = "0"+ this.month;
			thangNamPhatSinh = thangNamPhatSinh.substring(thangNamPhatSinh.length()-2);
			thangNamPhatSinh = this.year+"-"+thangNamPhatSinh ;
			System.out.println("Tháng-năm phát sinh" + thangNamPhatSinh);
			
			String query="";
			 query = " SELECT TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,TK.TENTAIKHOAN \n" +
					 "  ,ISNULL(DAUKY.GIATRINOVND, 0) AS DAUKYNOVND,ISNULL(DAUKY.GIATRICOVND, 0) AS DAUKYCOVND \n" +
					 "  ,ISNULL(PHATSINH.GIATRINOVND, 0) AS PHATSINHNOVND,ISNULL(PHATSINH.GIATRICOVND, 0) AS PHATSINHCOVND \n" +
					 "  ,ISNULL(CUOIKY.GIATRINOVND, 0) AS CUOIKYNO,ISNULL(CUOIKY.GIATRICOVND, 0) AS CUOIKYCO \n" +
					 " FROM ERP_TAIKHOANKT TK \n" +
					 " LEFT JOIN ( \n" +
					 "  SELECT TKID,CASE WHEN TKID IN ( \n" +
					 "      SELECT PK_SEQ \n" +
					 "      FROM ERP_TAIKHOANKT \n" +
					 "      WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '214%' AND SOHIEUTAIKHOAN NOT LIKE '229%' \n" +
					 "       AND ( \n" +
					 "        SOHIEUTAIKHOAN LIKE '521%' OR SOHIEUTAIKHOAN LIKE '531%' OR SOHIEUTAIKHOAN LIKE '532%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '1%' OR SOHIEUTAIKHOAN LIKE '2%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '6%' OR SOHIEUTAIKHOAN LIKE '8%' \n" +
					 "        ) \n" +
					 "      )THEN SUM(NO) - SUM(CO) \n" +
					 "    ELSE CASE  \n" +
					 "      WHEN TKID IN ( \n" +
					 "        SELECT PK_SEQ \n" +
					 "        FROM ERP_TAIKHOANKT \n" +
					 "        WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '521%' AND SOHIEUTAIKHOAN NOT LIKE '531%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '532%' \n" +
					 "         AND ( \n" +
					 "          SOHIEUTAIKHOAN LIKE '214%' OR SOHIEUTAIKHOAN LIKE '229%' \n" +
					 "          OR SOHIEUTAIKHOAN LIKE '3%' OR SOHIEUTAIKHOAN LIKE '4%' \n" +
					 "          OR SOHIEUTAIKHOAN LIKE '5%' OR SOHIEUTAIKHOAN LIKE '7%' \n" +
					 "          ) \n" +
					 "        )THEN 0 \n" +
					 "      ELSE CASE  \n" +
					 "        WHEN TKID IN ( \n" +
					 "          SELECT PK_SEQ \n" +
					 "          FROM ERP_TAIKHOANKT \n" +
					 "          WHERE SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' \n" +
					 "           OR SOHIEUTAIKHOAN LIKE '138%' OR SOHIEUTAIKHOAN LIKE '331%' \n" +
					 "           OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "          )THEN SUM(NO) \n" +
					 "        END \n" +
					 "      END \n" +
					 "    END AS GIATRINOVND \n" +
					 "   ,CASE  \n" +
					 "    WHEN TKID IN ( \n" +
					 "      SELECT PK_SEQ \n" +
					 "      FROM ERP_TAIKHOANKT \n" +
					 "      WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '214%' AND SOHIEUTAIKHOAN NOT LIKE '229%' \n" +
					 "       AND ( \n" +
					 "        SOHIEUTAIKHOAN LIKE '521%' OR SOHIEUTAIKHOAN LIKE '531%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '532%' OR SOHIEUTAIKHOAN LIKE '1%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '2%' OR SOHIEUTAIKHOAN LIKE '6%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '8%' \n" +
					 "        ) \n" +
					 "      ) THEN 0 \n" +
					 "    ELSE CASE  \n" +
					 "      WHEN TKID IN ( \n" +
					 "        SELECT PK_SEQ \n" +
					 "        FROM ERP_TAIKHOANKT \n" +
					 "        WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '521%' AND SOHIEUTAIKHOAN NOT LIKE '531%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '532%' \n" +
					 "         AND ( \n" +
					 "          SOHIEUTAIKHOAN LIKE '214%' OR SOHIEUTAIKHOAN LIKE '229%' \n" +
					 "          OR SOHIEUTAIKHOAN LIKE '3%' OR SOHIEUTAIKHOAN LIKE '4%' \n" +
					 "          OR SOHIEUTAIKHOAN LIKE '5%' OR SOHIEUTAIKHOAN LIKE '7%' \n" +
					 "          ) \n" +
					 "        ) THEN (- 1) * (SUM(NO) - SUM(CO)) \n" +
					 "      ELSE CASE  \n" +
					 "        WHEN TKID IN ( \n" +
					 "          SELECT PK_SEQ \n" +
					 "          FROM ERP_TAIKHOANKT \n" +
					 "          WHERE SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' \n" +
					 "           OR SOHIEUTAIKHOAN LIKE '138%' OR SOHIEUTAIKHOAN LIKE '331%' \n" +
					 "           OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "          ) THEN SUM(CO) \n" +
					 "        END \n" +
					 "      END \n" +
					 "    END AS GIATRICOVND \n" +
					 "  FROM ( \n" +
					 "   SELECT TKID,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG \n" +
					 "    ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 \n" +
					 "      AND (SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '138%'OR SOHIEUTAIKHOAN LIKE '331%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '336%'OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN SUM(GIATRINOVND) - SUM(GIATRICOVND) \n" +
					 "     WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0 \n" +
					 "      AND (SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '138%'OR SOHIEUTAIKHOAN LIKE '331%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '336%'OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN 0 \n" +
					 "     ELSE SUM(GIATRINOVND) \n" +
					 "     END AS NO \n" +
					 "    ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0 \n" +
					 "      AND (SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '138%'OR SOHIEUTAIKHOAN LIKE '331%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) * (- 1) \n" +
					 "     WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0 \n" +
					 "      AND ( \n" +
					 "       SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '138%' OR SOHIEUTAIKHOAN LIKE '331%' \n" +
					 "       OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN 0 \n" +
					 "     ELSE SUM(GIATRICOVND) \n" +
					 "     END AS CO \n" +
					 "   FROM ( \n" +
					 "    SELECT KS.TAIKHOANKT_FK AS TKID,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG \n" +
					 "     ,SUM(KS.GIATRINOVND) AS GIATRINOVND,SUM(KS.GIATRICOVND) AS GIATRICOVND \n" +
					 "    FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
					 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n" +
					 "    WHERE KS.THANG = "+thangdauky+" AND KS.NAM = "+namdauky+" AND TK.NPP_FK IN ("+this.nppId+") \n" +
					 "    GROUP BY KS.TAIKHOANKT_FK,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG \n" +
					 "    UNION ALL \n" +
 					 "    SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
 					 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
 					 "    FROM ERP_PHATSINHKETOAN PS \n" +
 					 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
 					 "    INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
 					"     WHERE PS.NGAYGHINHAN >'"+thangNamDauKy+"-31"+"' \n" +
					"     AND PS.NGAYGHINHAN <'"+thangNamPhatSinh+"-01'  \n" +
 					 "    AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
 					 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
 					 "    UNION ALL \n" +
 					 "	SELECT * FROM OPENQUERY([LINK_DMS_THAT_NOIBO]," +
 					 "    'SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
 					 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
 					 "    FROM " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS \n" +
 					 "    INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
 					 "    INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
 					"     WHERE PS.NGAYGHINHAN >''"+thangNamDauKy+"-31"+"'' \n" +
					"     AND PS.NGAYGHINHAN <''"+thangNamPhatSinh+"-01''  \n" +
					 "    AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
					 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG') \n" +
					 "    ) DAUKY_PHATSINH_THEODOITUONG \n" +
					 "   GROUP BY DAUKY_PHATSINH_THEODOITUONG.TKID,DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN \n" +
					 "    ,DAUKY_PHATSINH_THEODOITUONG.MADOITUONG,DAUKY_PHATSINH_THEODOITUONG.DOITUONG \n" +
					 "   ) DAUKY_PHATSINH_THEOTAIKHOAN \n" +
					 "  GROUP BY TKID,SOHIEUTAIKHOAN \n" +
					 "  ) DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					 " LEFT JOIN ( \n" +
					 "  SELECT TKID \n" +
					 "   ,SOHIEUTAIKHOAN \n" +
					 "   ,SUM(GIATRINOVND) AS GIATRINOVND \n" +
					 "   ,SUM(GIATRICOVND) AS GIATRICOVND \n" +
					 "  FROM ( \n" +
					 "   SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
					 "    ,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND \n" +
					 "   FROM ERP_PHATSINHKETOAN PS \n" +
					 "   INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 "   INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 "   WHERE MONTH(PS.NGAYGHINHAN) = "+this.month+" AND YEAR(PS.NGAYGHINHAN) = "+this.year+" AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
					 "   GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
					 "   UNION ALL \n" +
					 "	SELECT * FROM OPENQUERY([LINK_DMS_THAT_NOIBO]," +
					 "   'SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
					 "    ,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND \n" +
					 "   FROM " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS \n" +
					 "   INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 "   INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 "   WHERE MONTH(PS.NGAYGHINHAN) = "+this.month+" AND YEAR(PS.NGAYGHINHAN) = "+this.year+" AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
					 "   GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG') \n" +
					 "   ) PS_THEODOITUONG \n" +
					 "  GROUP BY TKID,SOHIEUTAIKHOAN \n" +
					 "  ) PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					 " LEFT JOIN ( \n" +
					 "  SELECT TKID,CASE WHEN TKID IN ( \n" +
					 "      SELECT PK_SEQ \n" +
					 "      FROM ERP_TAIKHOANKT \n" +
					 "      WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '214%' AND SOHIEUTAIKHOAN NOT LIKE '229%' \n" +
					 "       AND ( \n" +
					 "        SOHIEUTAIKHOAN LIKE '521%' OR SOHIEUTAIKHOAN LIKE '531%' OR SOHIEUTAIKHOAN LIKE '532%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '1%' OR SOHIEUTAIKHOAN LIKE '2%' OR SOHIEUTAIKHOAN LIKE '6%' \n" +
					 " 	   OR SOHIEUTAIKHOAN LIKE '8%' \n" +
					 "        ) \n" +
					 "      )THEN SUM(NO) - SUM(CO) \n" +
					 "    ELSE CASE WHEN TKID IN ( \n" +
					 "        SELECT PK_SEQ \n" +
					 "        FROM ERP_TAIKHOANKT \n" +
					 "        WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '521%' AND SOHIEUTAIKHOAN NOT LIKE '531%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '532%' \n" +
					 "         AND ( \n" +
					 "          SOHIEUTAIKHOAN LIKE '214%' OR SOHIEUTAIKHOAN LIKE '229%' OR SOHIEUTAIKHOAN LIKE '3%' \n" +
					 " 		 OR SOHIEUTAIKHOAN LIKE '4%' OR SOHIEUTAIKHOAN LIKE '5%' OR SOHIEUTAIKHOAN LIKE '7%' \n" +
					 "          ) \n" +
					 "        )THEN 0 \n" +
					 "      ELSE CASE WHEN TKID IN ( \n" +
					 "          SELECT PK_SEQ \n" +
					 "          FROM ERP_TAIKHOANKT \n" +
					 "          WHERE SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n" +
					 " 		 OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "          )THEN SUM(NO) \n" +
					 "        END \n" +
					 "      END \n" +
					 "    END AS GIATRINOVND \n" +
					 "   ,CASE WHEN TKID IN ( \n" +
					 "      SELECT PK_SEQ \n" +
					 "      FROM ERP_TAIKHOANKT \n" +
					 "      WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "       AND SOHIEUTAIKHOAN NOT LIKE '214%' AND SOHIEUTAIKHOAN NOT LIKE '229%' \n" +
					 "       AND ( \n" +
					 "        SOHIEUTAIKHOAN LIKE '521%' OR SOHIEUTAIKHOAN LIKE '531%' OR SOHIEUTAIKHOAN LIKE '532%' \n" +
					 " 	   OR SOHIEUTAIKHOAN LIKE '1%' OR SOHIEUTAIKHOAN LIKE '2%' OR SOHIEUTAIKHOAN LIKE '6%' \n" +
					 "        OR SOHIEUTAIKHOAN LIKE '8%' \n" +
					 "        ) \n" +
					 "      ) THEN 0 \n" +
					 "    ELSE CASE WHEN TKID IN ( \n" +
					 "        SELECT PK_SEQ \n" +
					 "        FROM ERP_TAIKHOANKT \n" +
					 "        WHERE SOHIEUTAIKHOAN NOT LIKE '131%' AND SOHIEUTAIKHOAN NOT LIKE '136%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '138%' AND SOHIEUTAIKHOAN NOT LIKE '331%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '336%' AND SOHIEUTAIKHOAN NOT LIKE '338%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '521%' AND SOHIEUTAIKHOAN NOT LIKE '531%' \n" +
					 "         AND SOHIEUTAIKHOAN NOT LIKE '532%' \n" +
					 "         AND ( \n" +
					 "          SOHIEUTAIKHOAN LIKE '214%' OR SOHIEUTAIKHOAN LIKE '229%' OR SOHIEUTAIKHOAN LIKE '3%' \n" +
					 " 		 OR SOHIEUTAIKHOAN LIKE '4%' OR SOHIEUTAIKHOAN LIKE '5%' OR SOHIEUTAIKHOAN LIKE '7%' \n" +
					 "          ) \n" +
					 "        ) THEN (- 1) * (SUM(NO) - SUM(CO)) \n" +
					 "      ELSE CASE  \n" +
					 "        WHEN TKID IN ( \n" +
					 "          SELECT PK_SEQ \n" +
					 "          FROM ERP_TAIKHOANKT \n" +
					 "          WHERE SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n" +
					 " 		  OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "          ) THEN SUM(CO) \n" +
					 "        END \n" +
					 "      END \n" +
					 "    END AS GIATRICOVND \n" +
					 "  FROM ( \n" +
					 "   SELECT TKID,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG \n" +
					 "    ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 \n" +
					 "      AND (SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n" +
					 " 	  OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%'OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN SUM(GIATRINOVND) - SUM(GIATRICOVND) \n" +
					 "     WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0 \n" +
					 "      AND (SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n" +
					 " 	  OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%'OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN 0 \n" +
					 "     ELSE SUM(GIATRINOVND) \n" +
					 "     END AS NO \n" +
					 "    ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0 \n" +
					 "      AND (SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'OR SOHIEUTAIKHOAN LIKE '138%' \n" +
					 " 	 OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) * (- 1) \n" +
					 "     WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0 \n" +
					 "      AND ( \n" +
					 "       SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%'  \n" +
					 " 	  OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' \n" +
					 "       )THEN 0 \n" +
					 "     ELSE SUM(GIATRICOVND) \n" +
					 "     END AS CO \n" +
					 "   FROM ( \n" +
					 
					 //Dữ liệu đầu kỳ
					 "    SELECT KS.TAIKHOANKT_FK AS TKID,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG \n" +
					 "     ,SUM(KS.GIATRINOVND) AS GIATRINOVND,SUM(KS.GIATRICOVND) AS GIATRICOVND \n" +
					 "    FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
					 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n" +
					 "    WHERE KS.THANG = "+thangdauky+" AND KS.NAM = "+namdauky+" AND TK.NPP_FK IN ("+this.nppId+") \n" +
					 "    GROUP BY KS.TAIKHOANKT_FK,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG \n" +
					 "    UNION ALL \n" +
 					 "    SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
 					 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
 					 "    FROM ERP_PHATSINHKETOAN PS \n" +
 					 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
 					 "    INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
 					"     WHERE PS.NGAYGHINHAN >'"+thangNamDauKy+"-31"+"' \n" +
					"     AND PS.NGAYGHINHAN <='"+thangNamPhatSinh+"-31'  \n" +
 					 "    AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
 					 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
 					 "    UNION ALL \n" +
 					 "	SELECT * FROM OPENQUERY([LINK_DMS_THAT_NOIBO]," +
 					 "    'SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
 					 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
 					 "    FROM " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS \n" +
 					 "    INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
 					 "    INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
 					"     WHERE PS.NGAYGHINHAN >''"+thangNamDauKy+"-31"+"'' \n" +
					"     AND PS.NGAYGHINHAN <=''"+thangNamPhatSinh+"-31''  \n" +
					 "    AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
					 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG') \n" +
					 
					 //Có dấu bằng ở phần trên nên comment phần này lại 
					/* //Dữ liệu phát sinh
					 "    UNION ALL \n" +
					 "    SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
					 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
					 "    FROM ERP_PHATSINHKETOAN PS \n" +
					 "    INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 "    INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 "    WHERE month(PS.NGAYGHINHAN) = "+this.month+" AND YEAR(PS.NGAYGHINHAN) = "+this.year+" AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
					 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
					 "    UNION ALL \n" +
					 "	SELECT * FROM OPENQUERY([LINK_DMS_THAT_NOIBO]," +
					 "    'SELECT PS.TAIKHOAN_FK AS TKID,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG \n" +
					 "     ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n" +
					 "    FROM " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS \n" +
					 "    INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 "    INNER JOIN " + Utility.prefixReportDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 "    WHERE month(PS.NGAYGHINHAN) = "+this.month+" AND YEAR(PS.NGAYGHINHAN) = "+this.year+" AND TK.NPP_FK IN ("+this.nppId+") AND TK1.NPP_FK IN ("+this.nppId+") \n" +
					 "    GROUP BY PS.TAIKHOAN_FK,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG') \n" +*/
					 "    ) CUOIKY_PHATSINH_THEODOITUONG \n" +
					 "   GROUP BY CUOIKY_PHATSINH_THEODOITUONG.TKID,CUOIKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN \n" +
					 "    ,CUOIKY_PHATSINH_THEODOITUONG.MADOITUONG,CUOIKY_PHATSINH_THEODOITUONG.DOITUONG \n" +
					 "   ) CUOIKY_PHATSINH_THEOTAIKHOAN \n" +
					 "  GROUP BY TKID,SOHIEUTAIKHOAN \n" +
					 "  ) CUOIKY ON CUOIKY.TKID = TK.PK_SEQ \n" +
					 " WHERE TK.NPP_FK IN ("+this.nppId+")  \n" +
					 "	AND( ISNULL(DAUKY.GIATRINOVND, 0) <> 0 OR ISNULL(DAUKY.GIATRICOVND, 0)<>0 OR ISNULL(PHATSINH.GIATRINOVND, 0)<>0 OR ISNULL(PHATSINH.GIATRICOVND, 0) <> 0 )\n" +
					 " ORDER BY SOHIEUTAIKHOAN ";
			
			System.out.println("Can doi phat sinh: " + query);
			this.rs = this.db.get(query);
		}
	}

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBClose() {
		if (db != null)
			db.shutDown();
	}
}
