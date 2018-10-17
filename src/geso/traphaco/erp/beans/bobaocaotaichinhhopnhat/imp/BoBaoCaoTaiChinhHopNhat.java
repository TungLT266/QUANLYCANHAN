package geso.traphaco.erp.beans.bobaocaotaichinhhopnhat.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.bobaocaotaichinhhopnhat.IBoBaoCaoTaiChinhHopNhat;

public class BoBaoCaoTaiChinhHopNhat  implements IBoBaoCaoTaiChinhHopNhat  {
	String userId;
	String ctyId;
	String nppId;
	String ctyTen;
	String year;
	String month;
	String diachi;
	String masothue; 
	ResultSet rs;
	ResultSet rsCDPS;
	ResultSet rsCDKT;
	ResultSet rsHDKD;
	String msg;
	dbutils db;
	
	ResultSet congtyRs;
	ResultSet nppRs;
	String ErpCongTyId;
	String view;
	
	public BoBaoCaoTaiChinhHopNhat () {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.nppId ="";
		this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));
		System.out.println(this.month);
		
		this.year = getDate().substring(0, 4);

		this.msg = "";
		this.db = new dbutils();
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

	public void setCtyRs(ResultSet ctyRs) {

		this.congtyRs = ctyRs;
	}

	public ResultSet getCtyRs() {

		return this.congtyRs;
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
		
		this.ErpCongTyId=id;
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
		if(this.month.length() >0){
			return this.month;	
		}else{
			return this.getDate().substring(5, 7);
		}
		
	}
	
	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if(this.year.length()>0){
			return this.year;	
		}else{
			return this.getDate().substring(0, 4);
		}
	}


	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public ResultSet getData(){
		return this.rs;
	}
	
	
	public void init(){
		String query ;
		this.ctyTen = "";
		this.diachi = "";
		this.masothue = "";
		query = "SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ IN (" + this.ErpCongTyId + ") ";
		ResultSet rs = this.db.get(query);
			
			try{
				if(rs != null) {
					rs.next();
					this.ctyTen = rs.getString("TEN");
					this.diachi = rs.getString("DIACHI");
					System.out.println(this.diachi);
					
					this.masothue = rs.getString("MASOTHUE");
					System.out.println(this.masothue);
					
					rs.close();
				}
				
			}catch(java.sql.SQLException e){}
		if(this.year.length() > 0 & this.month.length() > 0){
			//Chọn tháng khóa sổ gần nhất
			String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
					 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
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
			String sql = ""; 
			
			sql = 	" BEGIN \n" +
					"  \n" +
					" 	CREATE TABLE #CANDOIPHATSINH \n" +
					" 	( \n" +
					" 	SOHIEUTAIKHOAN NVARCHAR(50), \n" +
					" 	TENTAIKHOAN NVARCHAR(100), \n" +
					" 	DAUKYNO FLOAT DEFAULT 0, \n" +
					" 	DAUKYCO FLOAT DEFAULT 0, \n" +
					" 	PHATSINHNOVND FLOAT DEFAULT 0, \n" +
					" 	PHATSINHCOVND FLOAT DEFAULT 0, \n" +
					" 	CUOIKYNO FLOAT DEFAULT 0, \n" +
					" 	CUOIKYCO FLOAT DEFAULT 0,  \n" +
					" 	) \n" +
					" 	 \n" +
					" 	INSERT INTO #CANDOIPHATSINH \n" + 
					" SELECT DATA.SOHIEUTAIKHOAN,DATA.TKTEN , \n" + 
					 "  	ISNULL(DAUKY.GIATRINOVND, 0) AS NO_DK,ISNULL(DAUKY.GIATRICOVND, 0) AS CO_DK,   \n" + 
					 "  	ISNULL(PHATSINH.GIATRINOVND,0) AS PS_NO,ISNULL(PHATSINH.GIATRICOVND,0) AS PS_CO,  \n" + 
					 "  	ISNULL(CUOIKY.GIATRINOVND,0) AS NO_CK, ISNULL(CUOIKY.GIATRICOVND,0) AS CO_CK  \n" + 
					 "  	FROM \n" +
					 "		( SELECT TK.SOHIEUTAIKHOAN,TK.TENTAIKHOAN TKTEN  \n" +
					 "		FROM ERP_TAIKHOANKT TK WHERE TK.NPP_FK=1\n" + 
					 "		) DATA  \n" + 
					 "  	LEFT JOIN  \n" + 
					 "  	(  \n" + 
					 "  		SELECT SOHIEUTAIKHOAN,CASE WHEN LOAITKBAOCAO= 1 THEN SUM(NO) - SUM(CO)  \n" + 
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
					 "     SELECT LOAITKBAOCAO,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG,ISNPP  \n" + 
					 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 AND LOAITKBAOCAO = 3 THEN SUM(GIATRINOVND) - SUM(GIATRICOVND) \n" + 
					 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0 AND LOAITKBAOCAO = 3  THEN 0  \n" + 
					 "       ELSE SUM(GIATRINOVND) END AS NO   \n" + 
					 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0  AND LOAITKBAOCAO = 3 THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) * (- 1) \n" + 
					 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0  AND LOAITKBAOCAO = 3 THEN 0 \n" + 
					 "       ELSE SUM(GIATRICOVND)   \n" + 
					 "       END AS CO   \n" +  
					 "     FROM (  \n" + 
					 "  		 SELECT TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG ,KS.ISNPP  \n" + 
					 "       ,SUM(ROUND(ISNULL(KS.GIATRINOVND, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(KS.GIATRICOVND, 0), 0)) AS GIATRICOVND   \n" +
					"     FROM ERP_TAIKHOAN_NOCO_KHOASO KS  \n" +
					"     INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  \n" +
					"     WHERE KS.THANG = "+thangdauky+" AND KS.NAM = "+namdauky+"  \n" +
					"			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					 "			106170,106171,106172,106174, \n" +
					 "			106179,106182,106191,106210, \n" +
					 "			106368,106367,106277,106279, \n" +
					 "			106280,106224,106225,106226, \n" +
					 "			106227,106231,106249,106250, \n" +
					 "			106251,106275,106211) \n" +
					"     GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG,KS.ISNPP  \n" +
					 " UNION ALL \n " +	
					 "  		 SELECT TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP   \n" + 
					 "       ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND   \n" + 
					 "  		FROM ERP_PHATSINHKETOAN PS   \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
					 "     WHERE PS.NGAYGHINHAN >'"+thangNamDauKy+"-31"+"' \n" +
					"     AND PS.NGAYGHINHAN <'"+thangNamPhatSinh+"-01'  \n" +
					 "			AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
					 //CHO SET CỨNG HK INNER JOIN VỚI BẢNG NHAPHANPHOI CHO BÁO CÁO NHANH 
						//Đang set cứng 20 chi nhánh, 4 quầy 1HO return 26 giá trị
						 /*
						  SELECT PK_SEQ,MA,MAFAST,TEN FROM NHAPHANPHOI WHERE LOAINPP = 0 OR LOAINPP = 1 OR
						(MA = '100048' AND TEN = N'Chi Nhánh Công ty cổ phần Traphaco (CH số 1)') OR CONGNOCHUNG = 1
						OR LOAINPP = 0 OR PK_SEQ = 1
						  */
					 "			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					 "			106170,106171,106172,106174, \n" +
					 "			106179,106182,106191,106210, \n" +
					 "			106368,106367,106277,106279, \n" +
					 "			106280,106224,106225,106226, \n" +
					 "			106227,106231,106249,106250, \n" +
					 "			106251,106275,106211) \n" +
					 " 			AND PS.PK_SEQ NOT IN ( \n" +
					 "			SELECT PS.PK_SEQ FROM ERP_PHATSINHKETOAN PS  \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 " 			WHERE PS.LOAICHUNGTU =N'Kết chuyển cuối tháng kế toán' AND PS.NGAYGHINHAN <'"+thangNamPhatSinh+ "-01' AND  TK.SOHIEUTAIKHOAN IN ('91100000')  \n" +
					 " 			AND TKDU.SOHIEUTAIKHOAN IN ('51123000','63260000','52160000') )    \n" +	
					 
					 "   		GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP  \n" +	
					 "			UNION ALL \n" +
					 "			SELECT * FROM OPENQUERY(LINK_DMS_THAT_NOIBO,'"+
					 "  		 SELECT TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP   \n" + 
					 "       	,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND   \n" + 
					 "  		FROM ERP_PHATSINHKETOAN PS   \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
					 "     WHERE PS.NGAYGHINHAN >''"+thangNamDauKy+"-31"+"'' \n" +
					 "     AND PS.NGAYGHINHAN <''"+thangNamPhatSinh+"-01''  \n" +
					 "			AND TK.SOHIEUTAIKHOAN NOT IN (''51123000'',''63260000'',''52160000'')   \n" +
					 //CHO SET CỨNG HK INNER JOIN VỚI BẢNG NHAPHANPHOI CHO BÁO CÁO NHANH 
					//Đang set cứng 20 chi nhánh, 4 quầy 1HO return 26 giá trị
					 /*
					  SELECT PK_SEQ,MA,MAFAST,TEN FROM NHAPHANPHOI WHERE LOAINPP = 0 OR LOAINPP = 1 OR
					(MA = '100048' AND TEN = N'Chi Nhánh Công ty cổ phần Traphaco (CH số 1)') OR CONGNOCHUNG = 1
					OR LOAINPP = 0 OR PK_SEQ = 1
					  */
					 "			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					 "			106170,106171,106172,106174, \n" +
					 "			106179,106182,106191,106210, \n" +
					 "			106368,106367,106277,106279, \n" +
					 "			106280,106224,106225,106226, \n" +
					 "			106227,106231,106249,106250, \n" +
					 "			106251,106275,106211) \n" +
					 " 			AND PS.PK_SEQ NOT IN ( \n" +
					 "			SELECT PS.PK_SEQ FROM ERP_PHATSINHKETOAN PS  \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 " 			WHERE PS.LOAICHUNGTU =N''Kết chuyển cuối tháng kế toán'' AND PS.NGAYGHINHAN <''"+thangNamPhatSinh+ "-01'' AND  TK.SOHIEUTAIKHOAN IN (''91100000'')  \n" +
					 " 			AND TKDU.SOHIEUTAIKHOAN IN (''51123000'',''63260000'',''52160000'') )   \n" + 
					 
	 			 	 "   		GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP ')   \n" ;	
					 
			sql+=	 "  	) DAUKY_PHATSINH_THEODOITUONG   \n" + 
					 "     GROUP BY DAUKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO,DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN ,DAUKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO    \n" + 
					 "      ,DAUKY_PHATSINH_THEODOITUONG.MADOITUONG,DAUKY_PHATSINH_THEODOITUONG.DOITUONG ,DAUKY_PHATSINH_THEODOITUONG.ISNPP  \n" + 
					 "     ) DAUKY_PHATSINH_THEOTAIKHOAN   \n" + 
					 "    GROUP BY LOAITKBAOCAO,SOHIEUTAIKHOAN   \n" + 
					 "    ) DAUKY ON DAUKY.SOHIEUTAIKHOAN = DATA.SOHIEUTAIKHOAN   \n" + 
					 "  	LEFT JOIN  \n" + 
					 "  	(  \n" + 
					 "  		SELECT SOHIEUTAIKHOAN   \n" + 
					 "     ,SUM(GIATRINOVND) AS GIATRINOVND   \n" + 
					 "     ,SUM(GIATRICOVND) AS GIATRICOVND   \n" + 
					 "    FROM (   \n" + 
					 "  		SELECT TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP    \n" + 
					 "  		 ,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND   \n" + 
					 "  		FROM ERP_PHATSINHKETOAN PS  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
					 "  		WHERE PS.NGAYGHINHAN >= '"+thangNamPhatSinh+ "-01' AND PS.NGAYGHINHAN <= '"+thangNamPhatSinh+ "-31'   \n"  +
					 "			AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
					//CHO SET CỨNG HK INNER JOIN VỚI BẢNG NHAPHANPHOI CHO BÁO CÁO NHANH 
					//Đang set cứng 20 chi nhánh, 4 quầy 1HO return 26 giá trị
					 /*
					  SELECT PK_SEQ,MA,MAFAST,TEN FROM NHAPHANPHOI WHERE LOAINPP = 0 OR LOAINPP = 1 OR
					(MA = '100048' AND TEN = N'Chi Nhánh Công ty cổ phần Traphaco (CH số 1)') OR CONGNOCHUNG = 1
					OR LOAINPP = 0 OR PK_SEQ = 1
					  */
					  "			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					  "			106170,106171,106172,106174, \n" +
					  "			106179,106182,106191,106210, \n" +
					  "			106368,106367,106277,106279, \n" +
					  "			106280,106224,106225,106226, \n" +
					  "			106227,106231,106249,106250, \n" +
					  "			106251,106275,106211) \n" +
					 " 			AND PS.PK_SEQ NOT IN ( \n" +
					 "			SELECT PS.PK_SEQ FROM ERP_PHATSINHKETOAN PS  \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 " 			WHERE PS.LOAICHUNGTU =N'Kết chuyển cuối tháng kế toán' AND PS.NGAYGHINHAN >= '"+thangNamPhatSinh+ "-01' AND PS.NGAYGHINHAN <= '"+thangNamPhatSinh+ "-31' AND  TK.SOHIEUTAIKHOAN IN ('91100000')  \n" +
					 " 			AND TKDU.SOHIEUTAIKHOAN IN ('51123000','63260000','52160000') )   \n" +
					 
					 "   		GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP  \n" +
					 "			UNION ALL \n" +
					 "			SELECT * FROM OPENQUERY(LINK_DMS_THAT_NOIBO,'"+
					 "  		SELECT TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG ,PS.ISNPP  \n" + 
					 "  		 ,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND   \n" + 
					 "  		FROM ERP_PHATSINHKETOAN PS  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
					 "  		WHERE PS.NGAYGHINHAN >= ''"+thangNamPhatSinh+ "-01'' AND PS.NGAYGHINHAN <= ''"+thangNamPhatSinh+ "-31''   \n"  +
					 "			AND TK.SOHIEUTAIKHOAN NOT IN (''51123000'',''63260000'',''52160000'') \n"  +
					//CHO SET CỨNG HK INNER JOIN VỚI BẢNG NHAPHANPHOI CHO BÁO CÁO NHANH 
					//Đang set cứng 20 chi nhánh, 4 quầy 1HO return 26 giá trị
					 /*
					  SELECT PK_SEQ,MA,MAFAST,TEN FROM NHAPHANPHOI WHERE LOAINPP = 0 OR LOAINPP = 1 OR
					(MA = '100048' AND TEN = N'Chi Nhánh Công ty cổ phần Traphaco (CH số 1)') OR CONGNOCHUNG = 1
					OR LOAINPP = 0 OR PK_SEQ = 1
					  */
					  "			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					  "			106170,106171,106172,106174, \n" +
					  "			106179,106182,106191,106210, \n" +
					  "			106368,106367,106277,106279, \n" +
					  "			106280,106224,106225,106226, \n" +
					  "			106227,106231,106249,106250, \n" +
					  "			106251,106275,106211) \n" +
					 " 			AND PS.PK_SEQ NOT IN ( \n" +
					 "			SELECT PS.PK_SEQ FROM ERP_PHATSINHKETOAN PS  \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 " 			WHERE PS.LOAICHUNGTU =N''Kết chuyển cuối tháng kế toán'' AND PS.NGAYGHINHAN >= ''"+thangNamPhatSinh+ "-01'' AND PS.NGAYGHINHAN <= ''"+thangNamPhatSinh+ "-31'' AND  TK.SOHIEUTAIKHOAN IN (''91100000'')  \n" +
					 " 			AND TKDU.SOHIEUTAIKHOAN IN (''51123000'',''63260000'',''52160000'') )   \n" +
					 "   		GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP ')   \n" ;				 
				
				sql+="     ) PS_THEODOITUONG   \n" + 
					 "  	GROUP BY SOHIEUTAIKHOAN   \n" + 
					 "    ) PHATSINH ON PHATSINH.SOHIEUTAIKHOAN = DATA.SOHIEUTAIKHOAN    \n" + 
					 "    LEFT JOIN (   \n" + 
					 "  		SELECT  SOHIEUTAIKHOAN,CASE WHEN LOAITKBAOCAO= 1 THEN SUM(NO) - SUM(CO)  \n" + 
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
					 "     SELECT LOAITKBAOCAO,SOHIEUTAIKHOAN,MADOITUONG,DOITUONG,ISNPP   \n" + 
					 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 AND LOAITKBAOCAO = 3 THEN SUM(GIATRINOVND) - SUM(GIATRICOVND) \n" + 
					 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0 AND LOAITKBAOCAO = 3  THEN 0  \n" + 
					 "       ELSE SUM(GIATRINOVND) END AS NO   \n" + 
					 "      ,CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0  AND LOAITKBAOCAO = 3 THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) * (- 1) \n" + 
					 "       WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0  AND LOAITKBAOCAO = 3 THEN 0 \n" + 
					 "       ELSE SUM(GIATRICOVND)   \n" + 
					 "       END AS CO   \n" +  
					 "     FROM (  \n" +
					 "  		 SELECT TK.LOAITKBAOCAO, TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG,KS.ISNPP   \n" + 
					 "       ,SUM(ROUND(ISNULL(KS.GIATRINOVND, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(KS.GIATRICOVND, 0), 0)) AS GIATRICOVND   \n" +
					"     FROM ERP_TAIKHOAN_NOCO_KHOASO KS  \n" +
					"     INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  \n" +
					"     WHERE KS.THANG = "+thangdauky+" AND KS.NAM = "+namdauky+"  \n" +
					"			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					 "			106170,106171,106172,106174, \n" +
					 "			106179,106182,106191,106210, \n" +
					 "			106368,106367,106277,106279, \n" +
					 "			106280,106224,106225,106226, \n" +
					 "			106227,106231,106249,106250, \n" +
					 "			106251,106275,106211) \n" +
					 "     GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,KS.MADOITUONG,KS.DOITUONG,KS.ISNPP  \n" +
					 " UNION ALL \n" +
					 "  		SELECT TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP   \n" + 
					 "  		 ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND   \n" + 
					 "  		FROM ERP_PHATSINHKETOAN PS  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" +
					 "     WHERE PS.NGAYGHINHAN >'"+thangNamDauKy+"-31"+"' \n" +
					"     AND PS.NGAYGHINHAN <='"+thangNamPhatSinh+"-31'  \n" +
					 "			AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
					//CHO SET CỨNG HK INNER JOIN VỚI BẢNG NHAPHANPHOI CHO BÁO CÁO NHANH 
					//Đang set cứng 20 chi nhánh, 4 quầy 1HO return 26 giá trị
					 /*
					  SELECT PK_SEQ,MA,MAFAST,TEN FROM NHAPHANPHOI WHERE LOAINPP = 0 OR LOAINPP = 1 OR
					(MA = '100048' AND TEN = N'Chi Nhánh Công ty cổ phần Traphaco (CH số 1)') OR CONGNOCHUNG = 1
					OR LOAINPP = 0 OR PK_SEQ = 1
					  */
					  "			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					  "			106170,106171,106172,106174, \n" +
					  "			106179,106182,106191,106210, \n" +
					  "			106368,106367,106277,106279, \n" +
					  "			106280,106224,106225,106226, \n" +
					  "			106227,106231,106249,106250, \n" +
					  "			106251,106275,106211) \n" +
					 " 			AND PS.PK_SEQ NOT IN ( \n" +
					 " 			SELECT PS.PK_SEQ FROM ERP_PHATSINHKETOAN PS  \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 " 			WHERE PS.LOAICHUNGTU =N'Kết chuyển cuối tháng kế toán' AND PS.NGAYGHINHAN > '"+thangNamDauKy+"-31' AND PS.NGAYGHINHAN <= '"+thangNamPhatSinh+ "-31' AND  TK.SOHIEUTAIKHOAN IN ('91100000')  \n" +
					 " 			AND TKDU.SOHIEUTAIKHOAN IN ('51123000','63260000','52160000') ) \n" +
					 "			GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP \n" +
					 "			UNION ALL  \n" +
					 "			SELECT * FROM OPENQUERY(LINK_DMS_THAT_NOIBO,'"+
					 "  		SELECT TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP   \n" + 
					 "  		 ,SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND,SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND   \n" + 
					 "  		FROM ERP_PHATSINHKETOAN PS  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
					 "  		INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" +
					 "     WHERE PS.NGAYGHINHAN >''"+thangNamDauKy+"-31"+"'' \n" +
					"     AND PS.NGAYGHINHAN <=''"+thangNamPhatSinh+"-31''  \n" +
					 "			AND TK.SOHIEUTAIKHOAN NOT IN (''51123000'',''63260000'',''52160000'') \n" +
					//CHO SET CỨNG HK INNER JOIN VỚI BẢNG NHAPHANPHOI CHO BÁO CÁO NHANH 
					//Đang set cứng 20 chi nhánh, 4 quầy 1HO return 26 giá trị
					 /*
					  SELECT PK_SEQ,MA,MAFAST,TEN FROM NHAPHANPHOI WHERE LOAINPP = 0 OR LOAINPP = 1 OR
					(MA = '100048' AND TEN = N'Chi Nhánh Công ty cổ phần Traphaco (CH số 1)') OR CONGNOCHUNG = 1
					OR LOAINPP = 0 OR PK_SEQ = 1
					  */
					  "			AND TK.NPP_FK IN(1,100002,100010,106162, \n" +
					  "			106170,106171,106172,106174, \n" +
					  "			106179,106182,106191,106210, \n" +
					  "			106368,106367,106277,106279, \n" +
					  "			106280,106224,106225,106226, \n" +
					  "			106227,106231,106249,106250, \n" +
					  "			106251,106275,106211) \n" + 
					 " 			AND PS.PK_SEQ NOT IN ( \n" +
					 " 			SELECT PS.PK_SEQ FROM ERP_PHATSINHKETOAN PS  \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n" +
					 " 			INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n" +
					 " 			WHERE PS.LOAICHUNGTU =N''Kết chuyển cuối tháng kế toán'' AND PS.NGAYGHINHAN > ''"+thangNamDauKy+ "-31'' AND PS.NGAYGHINHAN <= ''"+thangNamPhatSinh+ "-31'' AND  TK.SOHIEUTAIKHOAN IN (''91100000'')  \n" +
					 " 			AND TKDU.SOHIEUTAIKHOAN IN (''51123000'',''63260000'',''52160000'') )  \n" +
					 "   		GROUP BY TK.LOAITKBAOCAO,TK.SOHIEUTAIKHOAN,PS.MADOITUONG,PS.DOITUONG,PS.ISNPP ')   \n" ;
					
				sql+="  	) CUOIKY_PHATSINH_THEODOITUONG   \n" + 
					 "     GROUP BY CUOIKY_PHATSINH_THEODOITUONG.LOAITKBAOCAO,CUOIKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN   \n" + 
					 "      ,CUOIKY_PHATSINH_THEODOITUONG.MADOITUONG,CUOIKY_PHATSINH_THEODOITUONG.DOITUONG,CUOIKY_PHATSINH_THEODOITUONG.ISNPP   \n" + 
					 "     ) CUOIKY_PHATSINH_THEOTAIKHOAN   \n" + 
					 "    GROUP BY LOAITKBAOCAO,SOHIEUTAIKHOAN   \n" + 
					 "    ) CUOIKY ON CUOIKY.SOHIEUTAIKHOAN = DATA.SOHIEUTAIKHOAN  " +
					"  WHERE ( ISNULL(DAUKY.GIATRINOVND, 0) <> 0 OR ISNULL(DAUKY.GIATRICOVND, 0)<>0 OR ISNULL(PHATSINH.GIATRINOVND, 0)<>0 OR ISNULL(PHATSINH.GIATRICOVND, 0) <> 0 )\n" ;
			
				sql+="  ORDER BY SOHIEUTAIKHOAN \n" +
					"  \n" +
					"  --SHEET CAN DOI KE TOAN  \n" +
					"  \n" +
					" 	CREATE TABLE #CANDOIKETOAN \n" +
					" 	( \n" +
					" 	MASO NVARCHAR(10), \n" +
					" 	CUOIKYNAY FLOAT DEFAULT 0 NOT NULL, \n" +
					" 	CUOIKYTRUOC FLOAT DEFAULT 0 NOT NULL \n" +
					" 	) \n" +
					"  \n" +
					" 	 \n" +
					"  \n" +
					" 	 \n" +
					"  \n" +
					" 	--Mã số 111 : NỢ TK (111,112,113) \n" +
					" 	INSERT INTO #CANDOIKETOAN  \n" +
					" 	SELECT '111' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '111%' OR SOHIEUTAIKHOAN LIKE '112%' OR SOHIEUTAIKHOAN LIKE '113%' \n" +
					"  \n" +
					" 	--MÃ SỐ 112 : NỢ TK (12811000) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '112' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY, ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '12811000%' \n" +
					"  \n" +
					" 	--Mã số 110 : MÃ SỐ 111 + MÃ SỐ 112 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '110' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('111','112') \n" +
					"  \n" +
					" 	 \n" +
					"  \n" +
					" 	--MÃ SỐ 121 : NỢ TK (121) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '121' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '121%' \n" +
					"  \n" +
					" 	--MÃ SỐ 122 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK (2291) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '122' AS MASO,- ABS(ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS(ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2291%' \n" +
					"  \n" +
					" 	--MÃ SỐ 123 : NỢ TK (1288) + NỢ TK (1282) + NỢ TK (1281) - Mã số 112 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '123' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY, ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '1288%' OR SOHIEUTAIKHOAN LIKE '1282%' OR SOHIEUTAIKHOAN LIKE '1281%' \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNAY,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.CUOIKYTRUOC,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('112') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('123') \n" +
					"  \n" +
					" 	--MÃ SỐ 120 : MÃ SỐ 121 + MÃ SỐ 122 + MÃ SỐ 123 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '120' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('121','122','123') \n" +
					" 	 \n" +
					" 	--MÃ SỐ 131 :NỢ TK (131) - MÃ SỐ 211 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '131' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '131%' \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNAY,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.CUOIKYTRUOC,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('211') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('131') \n" +
					"  \n" +
					" 	--MÃ SỐ 132 : NỢ TK (331) - MÃ SỐ 212 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '132' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '331%' \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNAY,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.CUOIKYTRUOC,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('212') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('132') \n" +
					"  \n" +
					" 	--MÃ SỐ 133 : NỢ TK (136) - CÓ TK (136)  \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '133' AS MASO,ISNULL(SUM(CUOIKYNO - CUOIKYCO),0) AS CUOIKYNAY, ISNULL(SUM(DAUKYNO - DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '136%' \n" +
					"  \n" +
					"  \n" +
					" 	--MÃ SỐ 134 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '134' AS MASO, 0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					" 	 \n" +
					" 	--MÃ SỐ 135 : NỢ TK (1283) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '135' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY, ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '1283%' \n" +
					"  \n" +
					" 	--MÃ SỐ 136 :NỢ TK (13881, 1387, 334,335, 338, 141, 24411, 24421) - CÓ TK 141 \n" +
					"  \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '136' AS MASO,ISNULL(SUM(CUOIKYNO),0) \n" +
					"   - (SELECT ISNULL(SUM(CUOIKYCO),0) FROM #CANDOIPHATSINH WHERE SOHIEUTAIKHOAN LIKE '141%' ) AS CUOIKYNAY,\n" +
					"   ISNULL(SUM(DAUKYNO),0) \n"+
					"   - (SELECT ISNULL(SUM(DAUKYCO),0) FROM #CANDOIPHATSINH WHERE SOHIEUTAIKHOAN LIKE '141%') AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '13881%' OR SOHIEUTAIKHOAN LIKE '1387%' OR \n" +
					" 	SOHIEUTAIKHOAN LIKE '334%' OR SOHIEUTAIKHOAN LIKE '338%' OR \n" +
					" 	SOHIEUTAIKHOAN LIKE '141%' OR SOHIEUTAIKHOAN LIKE '24411%' OR \n" +
					" 	SOHIEUTAIKHOAN LIKE '24421%' OR SOHIEUTAIKHOAN LIKE '335%' \n" +
					" 	 \n" +
					" 	--MÃ SỐ 137 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK (22931) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '137' AS MASO,- ABS (ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS (ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '22931%' \n" +
					"  \n" +
					"  \n" +
					" 	--MÃ SỐ 139 : NỢ TK (1381) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '139' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY, ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '1381%' \n" +
					"  \n" +
					" 	--MÃ SỐ 130 : MÃ SỐ 131 + MÃ SỐ 132 + MÃ SỐ 133 + MÃ SỐ 134 + MÃ SỐ 135 + MÃ SỐ 136  \n" +
					" 	--		+ MÃ SỐ 137 + MÃ SỐ 139 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '130' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('131','132','133','134','135','136','137','139') \n" +
					"  \n" +
					"  \n" +
					" 	--MÃ SỐ 141 : NỢ - CÓ TK (151, 152, 153, 155, 156, 157, 158) - MÃ SỐ 263 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '141' AS MASO,ISNULL(SUM(CUOIKYNO),0) - ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY, ISNULL(SUM(DAUKYNO),0) -ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '151%' OR SOHIEUTAIKHOAN LIKE '152%' OR \n" +
					" 	SOHIEUTAIKHOAN LIKE '153%' OR SOHIEUTAIKHOAN LIKE '155%' OR  \n" +
					" 	SOHIEUTAIKHOAN LIKE '156%' OR SOHIEUTAIKHOAN LIKE '157%' OR  \n" +
					" 	SOHIEUTAIKHOAN LIKE '158%' \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNAY,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.CUOIKYTRUOC,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('263') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('141') \n" +
					"  \n" +
					" 	--MÃ SỐ 149: - GIÁ TRỊ TUYỆT ĐỐI CÓ TK 2294 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '149' AS MASO,- ABS(ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS(ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2294%'  \n" +
					"  \n" +
					" 	--MÃ SỐ 140: MÃ SỐ 141 + MÃ SỐ 149 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '140' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('141','149') \n" +
					"  \n" +
					" 	 \n" +
					"  \n" +
					" 	--MÃ SỐ 151: NỢ TK 2421  \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '151' AS MASO, ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2421%' \n" +
					"  \n" +
					" 	--MÃ SỐ 152: NỢ TK 133 - CÓ TK 133 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '152' AS MASO, ISNULL(SUM(CUOIKYNO - CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO  - DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '133%' \n" +
					"  \n" +
					" 	--MÃ SỐ 153: NỢ TK 333 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '153' AS MASO, ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '333%' \n" +
					"  \n" +
					" 	--MÃ SỐ 154 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '154' AS MASO, 0 AS CUOIKYNAY , 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 155 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '155' AS MASO, 0 AS CUOIKYNAY , 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 150: MÃ SỐ 151 + MÃ SỐ 152 + MÃ SỐ 153 + MÃ SỐ 154 + MÃ SỐ 155 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '150' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('151','152','153','154','155') \n" +
					"  \n" +
					" 	--Mã số 100 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '100' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN  \n" +
					" 	WHERE MASO IN ('110','120','130','140','150') \n" +
					"  \n" +
					" 	--MÃ SỐ 211 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '211' AS MASO, 0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 212 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '212' AS MASO, 0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 213 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '213' AS MASO, 0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 214 : 0  \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '214' AS MASO, 0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 215 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '215' AS MASO, 0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 216 :NỢ TK (1385, 13882, 24412, 24422) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '216' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '1385%' OR SOHIEUTAIKHOAN LIKE '13882%' OR  \n" +
					" 	SOHIEUTAIKHOAN LIKE '24412%' OR SOHIEUTAIKHOAN LIKE '24422%' \n" +
					"  \n" +
					" 	--MÃ SỐ 219 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK (22932) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '219' AS MASO,- ABS (ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS (ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '22932%' \n" +
					"  \n" +
					" 	--MÃ SỐ 210 : MÃ SỐ 211 + MÃ SỐ 212 + MÃ SỐ 213 + MÃ SỐ 214 + MÃ SỐ 215 + MÃ SỐ 216 + MÃ SỐ 219 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '210' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('211','212','213','214','215','216','219') \n" +
					" 	 \n" +
					" 	--MÃ SỐ 222 : NỢ TK (211) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '222' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '211%' \n" +
					" 	 \n" +
					" 	--MÃ SỐ 223 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK 2141 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '223' AS MASO, - ABS(ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,-ABS(ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2141%' \n" +
					"  \n" +
					" 	--MÃ SỐ 221 : MÃ SỐ 222 + MÃ SỐ 223 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '221' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('222','223') \n" +
					"  \n" +
					" 	--MÃ SỐ 225 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '225' AS MASO,0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 226 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '226' AS MASO,0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 224 : MÃ SỐ 225 + MÃ SỐ 226 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '224' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('225','226') \n" +
					"  \n" +
					" 	--MÃ SỐ 228 : NỢ TK (213) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '228' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '213%'  \n" +
					" 	 \n" +
					" 	--MÃ SỐ 229 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK (2143) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '229' AS MASO,- ABS(ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS(ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE'2143%' \n" +
					"  \n" +
					" 	--MÃ SỐ 227 :MÃ SỐ 228 + MÃ SỐ 229 \n" +
					" 	INSERT INTO #CANDOIKETOAN  \n" +
					" 	SELECT '227' AS  MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('228','229') \n" +
					"  \n" +
					" 	--MÃ SỐ 220 : MÃ SỐ 221 + MÃ SỐ 224 + MÃ SỐ 227 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '220' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('221','224','227') \n" +
					"  \n" +
					" 	--MÃ SỐ 231 : NỢ TK (217) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '231' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '217%' \n" +
					"  \n" +
					"  \n" +
					" 	--MÃ SỐ 232 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK (2147) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '232' AS MASO,- ABS(ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS(ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2147%' \n" +
					"  \n" +
					" 	--MÃ SỐ 230 : MÃ SỐ 231 + MÃ SỐ 232 \n" +
					" 	INSERT INTO #CANDOIKETOAN  \n" +
					" 	SELECT '230' AS  MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('231','232') \n" +
					"  \n" +
					" 	--MÃ SỐ 241 : NỢ TK (154) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '241' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '154%' \n" +
					"  \n" +
					" 	--MÃ SỐ 242 : NỢ TK (241) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '242' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '241%' \n" +
					"  \n" +
					" 	--MÃ SỐ 240 : MÃ SỐ 241 + MÃ SỐ 242 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '240' AS MASO, ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('241','242') \n" +
					"  \n" +
					" 	 \n" +
					" 	--MÃ SỐ 251 : NỢ TK (221) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '251' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '221%' \n" +
					"  \n" +
					" 	--MÃ SỐ 252 : NỢ TK (222) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '252' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '222%' \n" +
					"  \n" +
					" 	--MÃ SỐ 253 : NỢ TK (2281, 2282) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '253' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2281%' OR SOHIEUTAIKHOAN LIKE '2282%' \n" +
					"  \n" +
					" 	--MÃ SỐ 254 : - GIÁ TRỊ TUYỆT ĐỐI CÓ TK (2292) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '254' AS MASO,- ABS(ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY,- ABS(ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2292%' \n" +
					"  \n" +
					" 	--MÃ SỐ 255 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '255' AS MASO,0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 250 : MÃ SỐ 251 + MÃ SỐ 252 + MÃ SỐ 253 + MÃ SỐ 254 + MÃ SỐ 255 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '250' AS MASO, ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('251','252','253','254','255') \n" +
					"  \n" +
					" 	--MÃ SỐ 261 : NỢ TK (2422) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '261' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2422%'  \n" +
					"  \n" +
					" 	--MÃ SỐ 262 : NỢ TK (243) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '262' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '243%' \n" +
					"  \n" +
					" 	--MÃ SỐ 263 : 0  \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '263' AS MASO, 0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					" 	 \n" +
					" 	--MÃ SỐ 268 : NỢ TK 2288 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '268' AS MASO,ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '2288%' \n" +
					"  \n" +
					" 	--MÃ SỐ 260 : MÃ SỐ 261 + MÃ SỐ 262 + MÃ SỐ 263 + MÃ SỐ 268 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '260' AS MASO, ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('261','262','263','268') \n" +
					"  \n" +
					" 	--MÃ SỐ 200 : MÃ SỐ 210 + MÃ SỐ 220 + MÃ SỐ 230 + MÃ SỐ 240 + MÃ SỐ 250 + MÃ SỐ 260 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '200' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('210','220','230','240','250','260') \n" +
					" 	 \n" +
					" 	--MÃ SỐ 270 : MÃ SỐ 100 + MÃ SỐ 200 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '270' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('100','200') \n" +
					"  \n" +
					" 	--MÃ SỐ 311 : CÓ TK (331) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '311' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '331%' \n" +
					"  \n" +
					" 	--MÃ SỐ 312 : CÓ TK (131) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '312' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '131%' \n" +
					"  \n" +
					" 	--MÃ SỐ 313 : CÓ TK (333) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '313' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '333%' \n" +
					"  \n" +
					" 	--MÃ SỐ 314 : CÓ TK (334) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '313' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '334%' \n" +
					"  \n" +
					" 	--MÃ SỐ 315 : CÓ TK (335) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '315' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '335%' \n" +
					"  \n" +
					" 	--MÃ SỐ 316 : CÓ TK (336) - NỢ TK (336) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '316' AS MASO,ISNULL(SUM(CUOIKYCO - CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO - DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '336%' \n" +
					"  \n" +
					" 	--MÃ SỐ 317 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '317' AS MASO,0 AS CUOIKYNAY, 0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 318 : CÓ TK (3387) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '318' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '3387%' OR SOHIEUTAIKHOAN LIKE '3387%' \n" +
					"  \n" +
					" 	--MÃ SỐ 319 : CÓ TK (338, 138, 3441,224) - CÓ TK (3387) - MÃ SỐ 337 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '319' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '338%' OR SOHIEUTAIKHOAN LIKE '138%' OR \n" +
					" 	SOHIEUTAIKHOAN LIKE '3441%' OR SOHIEUTAIKHOAN LIKE '224%' \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYCO,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.DAUKYCO,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYCO),0) AS CUOIKYCO,ISNULL(SUM(DAUKYCO),0) AS DAUKYCO FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE ('3387%') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('319') \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNAY,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.CUOIKYTRUOC,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('337') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('319') \n" +
					"  \n" +
					" 	--MÃ SỐ 320 : CÓ TK (34111, 34112, 34121, 34122) - MÃ SỐ 338 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '320' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '34111%' OR SOHIEUTAIKHOAN LIKE '34112%' OR \n" +
					" 	SOHIEUTAIKHOAN LIKE '34121%' OR SOHIEUTAIKHOAN LIKE '34122%' \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNAY,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.CUOIKYTRUOC,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY,ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('338') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('320') \n" +
					"  \n" +
					" 	--MÃ SỐ 321 :CÓ TK (352) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '321' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '352%' \n" +
					"  \n" +
					" 	--MÃ SỐ 322 :CÓ TK (353) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '322' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '353%' \n" +
					"  \n" +
					" 	--MÃ SỐ 323 : CÓ TK (357) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '323' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '357%' \n" +
					"  \n" +
					" 	--MÃ SỐ 324 : CÓ TK (171) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '324' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '171%' \n" +
					"  \n" +
					" 	--MÃ SỐ 310 : Mã số 311 + Mã số 312 + Mã số 313 + Mã số 314 + Mã số 315 + Mã số 316 + Mã số 317 +  \n" +
					" 	--			Mã số 318 + Mã số 319 + Mã số 320 + Mã số 321 + Mã số 322 + Mã số 323 + Mã số 324 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '310' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('311','312','313','314','315','316','317','318','319', \n" +
					" 					'320','321','322','323','324') \n" +
					"  \n" +
					" 	-- MÃ SỐ 331 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '331' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					" 	 \n" +
					" 	-- MÃ SỐ 332 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '332' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 333 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '333' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 334 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '334' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 335 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '335' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 336 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '336' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 337 : CÓ TK (3442) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '337' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '3442%' \n" +
					"  \n" +
					" 	-- MÃ SỐ 338 : CÓ TK (34113,34114) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '338' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '34113%' OR SOHIEUTAIKHOAN LIKE '34114%' \n" +
					"  \n" +
					" 	-- MÃ SỐ 339 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '339' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 340 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '340' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	-- MÃ SỐ 341 : CÓ TK (347) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '341' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '347%' \n" +
					"  \n" +
					" 	--MÃ SỐ 342 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '342' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 343 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '343' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--	MÃ SỐ 330 : MÃ SỐ 331 + MÃ SỐ 332 + MÃ SỐ 333 + MÃ SỐ 334 + MÃ SỐ 335 + MÃ SỐ 336  \n" +
					" 	--	+ MÃ SỐ 337 + MÃ SỐ 338 + MÃ SỐ 339 + MÃ SỐ 340 + MÃ SỐ 341 + MÃ SỐ 342 + MÃ SỐ 343 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '330' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('331','332','333','334','335','336','337','338','339', \n" +
					" 					'340','341','342','343') \n" +
					" 	 \n" +
					" 	--MÃ SỐ 300 : MÃ SỐ 310 + MÃ SỐ 330 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '300' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('310','330') \n" +
					"  \n" +
					" 	--MÃ SỐ 411a : CÓ TK (4111) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '411a' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '4111%' \n" +
					"  \n" +
					" 	--MÃ SỐ 411b : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '411b' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 411 : MÃ SỐ 411a + MÃ SỐ 411b		 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '411' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('411a','411b') \n" +
					"  \n" +
					" 	--MÃ SỐ 412 : CÓ TK (4112) - NỢ TK 4112 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '412' AS MASO,ISNULL(SUM(CUOIKYCO - CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO - CUOIKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '4112%' \n" +
					"  \n" +
					" 	--MÃ SỐ 413 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '413' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 414 : CÓ TK 4118 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '414' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '4118%' \n" +
					"  \n" +
					" 	--MÃ SỐ 415 : - GIÁ TRỊ TUYỆT ĐỐI (NỢ TK (419) - CÓ TK (419)) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '415' AS MASO,- ABS(ISNULL(SUM(CUOIKYNO),0) -ISNULL(SUM(CUOIKYCO),0)) AS CUOIKYNAY, \n" +
					" 	- ABS(ISNULL(SUM(DAUKYNO),0) -ISNULL(SUM(DAUKYCO),0)) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '419%' \n" +
					"  \n" +
					" 	--MÃ SỐ 416 : CÓ TK (412) - NỢ TK(412) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '416' AS MASO,ISNULL(SUM(CUOIKYCO),0) -ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY, \n" +
					" 	ISNULL(SUM(DAUKYCO),0) - ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '412%' \n" +
					"  \n" +
					" 	--MÃ SỐ 417 : CÓ TK (413) - NỢ TK(413) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '417' AS MASO,ISNULL(SUM(CUOIKYCO),0) -ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNAY, \n" +
					" 	ISNULL(SUM(DAUKYCO),0) -ISNULL(SUM(DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '413%' \n" +
					"  \n" +
					" 	--MÃ SỐ 418 : CÓ TK (414) - NỢ TK 414 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '418' AS MASO,ISNULL(SUM(CUOIKYCO - CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO - DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '414%' \n" +
					"  \n" +
					" 	--MÃ SỐ 419 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '419' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 420 : CÓ TK (418) - NỢ TK 418 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '420' AS MASO,ISNULL(SUM(CUOIKYCO - CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO - DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '418%' \n" +
					"  \n" +
					" 	 \n" +
					"  \n" +
					" 	--MÃ SỐ 421a : CÓ TK 4211 - NỢ TK 4211 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '421a' AS MASO,ISNULL(SUM(CUOIKYCO - CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO - DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '4211%' \n" +
					"  \n" +
					" 	--MÃ SỐ 421b : CÓ TK 4212  - NỢ TK 4212  \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '421b' AS MASO,ISNULL(SUM(CUOIKYCO - CUOIKYNO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO - DAUKYNO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '4212%' \n" +
					"  \n" +
					" 	--MÃ SỐ 421 : MÃ SỐ 421a + MÃ SỐ 421b \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '421' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('421a','421b') \n" +
					"  \n" +
					" 	--MÃ SỐ 422 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '422' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 410	: MÃ SỐ 411 + MÃ SỐ 412 + MÃ SỐ 413 + MÃ SỐ 414 + MÃ SỐ 415 + MÃ SỐ 416 + \n" +
					" 	--		MÃ SỐ 417 + MÃ SỐ 418 + MÃ SỐ 419 + MÃ SỐ 420 + MÃ SỐ 421 + MÃ SỐ 422 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '410' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('411','412','413','414','415','416','417','418','419','420', \n" +
					" 			'421','422') \n" +
					"  \n" +
					" 	 \n" +
					"  \n" +
					" 	--MÃ SỐ 431 : CÓ TK (461) - NỢ TK (161) \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '431' AS MASO,ISNULL(SUM(CUOIKYCO),0) AS CUOIKYNAY,ISNULL(SUM(DAUKYCO),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE '461%' \n" +
					"  \n" +
					" 	UPDATE #CANDOIKETOAN \n" +
					" 	SET CUOIKYNAY = #CANDOIKETOAN.CUOIKYNAY - ISNULL(DATA.CUOIKYNO,0), \n" +
					" 	CUOIKYTRUOC = #CANDOIKETOAN.CUOIKYTRUOC - ISNULL(DATA.DAUKYNO,0) \n" +
					" 	FROM  \n" +
					" 	( \n" +
					" 	SELECT ISNULL(SUM(CUOIKYNO),0) AS CUOIKYNO,ISNULL(SUM(DAUKYNO),0) AS DAUKYNO FROM #CANDOIPHATSINH \n" +
					" 	WHERE SOHIEUTAIKHOAN LIKE ('161%') \n" +
					" 	)DATA  \n" +
					" 	WHERE MASO IN ('431') \n" +
					"  \n" +
					" 	--MÃ SỐ 432 : 0 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '432' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					"  \n" +
					" 	--MÃ SỐ 430 : MÃ SỐ 431 + MÃ SỐ 432 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '430' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('431','432') \n" +
					"  \n" +
					" 	--MÃ SỐ 400	: MÃ SỐ 410 + MÃ SỐ 430 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '400' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('410','430')	 \n" +
					"  \n" +
					" 	--Mã số 440 : MÃ SỐ 300 + MÃ SỐ 400 \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '440' AS MASO,ISNULL(SUM(CUOIKYNAY),0) AS CUOIKYNAY, ISNULL(SUM(CUOIKYTRUOC),0) AS CUOIKYTRUOC \n" +
					" 	FROM #CANDOIKETOAN \n" +
					" 	WHERE MASO IN ('300','400') \n" +
					"  \n" +
					" 	--TẠO DÒNG NGUỒN VỐN \n" +
					" 	INSERT INTO #CANDOIKETOAN \n" +
					" 	SELECT '270TEMP' AS MASO,0 AS CUOIKYNAY,0 AS CUOIKYTRUOC \n" +
					" END \n" ;
			System.out.println("Khởi tạo dữ liệu báo cáo : \n "+sql);
			this.db.update(sql);
			
			//Get Dữ liệu
			this.rsCDPS = this.db.get("SELECT * FROM #CANDOIPHATSINH WHERE DAUKYNO <>0 OR DAUKYCO <>0 OR PHATSINHNOVND<>0 OR PHATSINHCOVND<>0 ORDER BY SOHIEUTAIKHOAN");
			sql = "SELECT MASO,CASE WHEN MASO = '270TEMP' THEN '' \n" +
					" ELSE CUOIKYNAY END AS CUOIKYNAY, \n" +
					" CASE WHEN MASO = '270TEMP' THEN '' \n" +
					" ELSE CUOIKYTRUOC END AS CUOIKYTRUOC \n" +
					" FROM #CANDOIKETOAN ORDER BY MASO \n" ;
			this.rsCDKT = this.db.get(sql);
			this.rsHDKD = this.db.get(this.getQueryHDKD());
				
		}
	}
	public String getQueryHDKD(){
		String query = "";
		int month = Integer.parseInt(this.month);
		int year = Integer.parseInt(this.year);
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
		query ="SELECT MASO,SUM(KYNAY) AS KYNAY,SUM(KYTRUOC) AS KYTRUOC  \n" + 
				 "  FROM(  \n" + 
				 "  SELECT 1 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 2 MASO,ABS(SUM(CO)-SUM(NO)) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '511%' AND TKDU.SOHIEUTAIKHOAN LIKE '521%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 2 MASO,0 AS KYNAY,ABS(SUM(CO)-SUM(NO)) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '511%' AND TKDU.SOHIEUTAIKHOAN LIKE '521%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 10 MASO,SUM(NO)-SUM(CO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '511%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 10 MASO,0 AS KYNAY,SUM(NO)-SUM(CO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '511%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 11 MASO,SUM(CO)-SUM(NO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '632%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 11 MASO,0 AS KYNAY,SUM(CO)-SUM(NO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '632%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 20 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 21 MASO,SUM(NO)-SUM(CO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '515%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 21 MASO,0 AS KYNAY,SUM(NO)-SUM(CO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '515%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 22 MASO,SUM(CO)-SUM(NO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '635%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 22 MASO,0 AS KYNAY,SUM(CO)-SUM(NO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '635%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 23 MASO,SUM(CO)-SUM(NO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '6351%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 23 MASO,0 AS KYNAY,SUM(CO)-SUM(NO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '6351%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 25 MASO,SUM(CO)-SUM(NO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '641%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 25 MASO,0 AS KYNAY,SUM(CO)-SUM(NO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '641%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 "  --KY NAY   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 26 MASO,SUM(CO)-SUM(NO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '642%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 26 MASO,0 AS KYNAY,SUM(CO)-SUM(NO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '642%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 30 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 31 MASO,SUM(NO)-SUM(CO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '711%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 31 MASO,0 AS KYNAY,SUM(NO)-SUM(CO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '711%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 
				 "  UNION ALL  \n" + 
				 "  --KY NAY   \n" + 
				 "  SELECT 32 MASO,SUM(CO)-SUM(NO) AS KYNAY,0 AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán'  AND TK.SOHIEUTAIKHOAN LIKE '811%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" + 
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + month + " AND YEAR(NGAYGHINHAN) = " + year + " \n" +
				 
				 "  UNION ALL  \n" + 
				 "  --KY TRUOC  \n" + 
				 "  SELECT 32 MASO,0 AS KYNAY,SUM(CO)-SUM(NO) AS KYTRUOC FROM ERP_PHATSINHKETOAN PS  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n" + 
				 "  INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n" + 
				 "  WHERE  LOAICHUNGTU = N'Kết chuyển cuối tháng kế toán' AND TK.SOHIEUTAIKHOAN LIKE '811%' AND TKDU.SOHIEUTAIKHOAN LIKE '911%'  \n" +
				 "  AND TK.SOHIEUTAIKHOAN NOT IN ('51123000','63260000','52160000') \n" +
				 "  AND MONTH(NGAYGHINHAN) = " + thangdauky + " AND YEAR(NGAYGHINHAN) = " + namdauky + " \n" +
				 
				 "  UNION ALL  \n" + 
				 "  SELECT 40 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 50 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 51 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 52 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 60 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 70 MASO,0 AS KYNAY,0 AS KYTRUOC   \n" + 
				 "  UNION ALL  \n" + 
				 "  SELECT 71 MASO,0 AS KYNAY,0 AS KYTRUOC )DATA  \n" + 
				 "  GROUP BY MASO  \n" + 
				 "  ORDER BY MASO ";
		System.out.println("HOAT DONG KINH DOANH :" +query);
		return query;
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){

		try {
			if (rs != null) rs.close();
			if(rsCDKT != null) rsCDKT.close();
			if(rsCDPS != null) rsCDPS.close();
			if(rsHDKD != null) rsHDKD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (db != null)
			db.shutDown();
	}


/*	public Double getNoTruCo(String sohieu , boolean dauki) {
		int lastmonth=0;
		int lastyear=0;
		
		lastyear = Integer.parseInt(this.year) - 1;
		lastmonth = 0;
				
		if (Integer.parseInt(this.month) > 1){
			lastmonth = Integer.parseInt(this.month) - 1;
		}else{
			lastmonth = 12;
		}
		
		
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i].trim() +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan truoc" + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "( " + sohieutk.substring(0, index) + ")";
		
		System.out.print("Chuoi sohieutaikhoan sau" + sohieutk);
		
		try{
			String query ="";
			if(dauki==true)
			{
				if(lastmonth != 12){
					query = "select SUM(GIATRINOVND) - SUM(GIATRICOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO_KHOASO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +"  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) )";
				}
				else
				{
					query = "select SUM(GIATRINOVND) - SUM(GIATRICOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO_KHOASO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +"  AND CONGTY_FK IN (" + this.ErpCongTyId + " ))";
				}				
			}
			else{
				if(lastmonth != 12){
					query = 
					"SELECT SUM(ISNULL(DAUKY.GIATRINOVND, 0) - ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) -  ISNULL(PHATSINH.GIATRICOVND, 0)) AS giatri 						\n" +
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
					"	SELECT TAIKHOANKT_FK AS TKID, SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n" +
					"	FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
					" 	WHERE thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
					"	GROUP BY TAIKHOANKT_FK \n" +
					")DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"LEFT JOIN( \n" +
					"	SELECT TAIKHOAN_FK AS TKID, SUM(ROUND(ISNULL(NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(CO, 0), 0)) AS GIATRICOVND \n" +
					"	FROM ERP_PHATSINHKETOAN \n" +
					" where month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"	GROUP BY TAIKHOAN_FK \n" +
					")PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}
				else
				{
					query = 
					"SELECT SUM(ISNULL(DAUKY.GIATRINOVND, 0) - ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) -  ISNULL(PHATSINH.GIATRICOVND, 0)) AS giatri 						\n" +
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
					"	SELECT TAIKHOANKT_FK AS TKID, SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n" +
					"	FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
					" 	WHERE thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
					"	GROUP BY TAIKHOANKT_FK \n" +
					")DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"LEFT JOIN( \n" +
					"	SELECT TAIKHOAN_FK AS TKID, SUM(ROUND(ISNULL(NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(CO, 0), 0)) AS GIATRICOVND \n" +
					"	FROM ERP_PHATSINHKETOAN \n" +
					" 	WHERE month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"	GROUP BY TAIKHOAN_FK \n" +
					")PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					" where " + sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) " ;
				}
				
			}

			System.out.println("GET No tru co: " + query);
			ResultSet rs=db.get(query);
			if(rs.next()){
				giatri = rs.getDouble("giatri");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}*/


	public Double getNoTruCo_TheoDoiTuong(String sohieu , boolean kytruoc) {
	
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i].trim() +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan truoc" + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "( " + sohieutk.substring(0, index) + ")";
		
		System.out.print("Chuoi sohieutaikhoan sau" + sohieutk);
		int thismonth_kynay = Integer.parseInt(this.month);
		int thisyear_kynay = Integer.parseInt(this.year);
		
		try{
			String query ="";
			if(kytruoc == true)
			{
				int lastmonth_kytruoc = 0;
				int lastyear_kytruoc = 0;
				int thismonth_kytruoc = 0;
				int thisyear_kytruoc = 0;
				
				if (thismonth_kynay > 1){
					thismonth_kytruoc = thismonth_kynay - 1;
					thisyear_kytruoc = thisyear_kynay;
				}else{
					thismonth_kytruoc = 12;
					thisyear_kytruoc = thisyear_kynay - 1;
				}

				if(thismonth_kytruoc > 1){
					lastmonth_kytruoc = thismonth_kytruoc - 1;
					lastyear_kytruoc = thisyear_kytruoc;
				}else{
					lastmonth_kytruoc = 12;
					lastyear_kytruoc = thisyear_kytruoc - 1;				
				}
			if(sohieutk.indexOf("131") >= 0 || sohieutk.indexOf("136") >= 0 || sohieutk.indexOf("138") >= 0 || sohieutk.indexOf("331") >= 0 || sohieutk.indexOf("336") >= 0 || sohieutk.indexOf("338") >= 0){
				query = 
				"SELECT SUM(ISNULL(NOVND, 0)) AS GIATRI 						\n " +
				"FROM ERP_TAIKHOANKT TK \n" +
				"LEFT JOIN ( \n" +
					// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
				"  SELECT TKID, MADOITUONG, DOITUONG, " +
				"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
				"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
				"  ELSE " +
				"		 0 " +
				"  END AS NOVND, \n " +
					
				"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
				"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
				"  ELSE " +
				"		 0 " +
				"  END AS COVND \n " +
				"  FROM ( \n " +
				
				// LẤY ĐẦU KỲ
				"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, \n " +
				"		SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
				"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
				"	 	WHERE thang = '" + lastmonth_kytruoc + "' and NAM = '" + lastyear_kytruoc + "' " +
				"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
				" 		UNION ALL \n " +
				
				// LẤY PHÁT SINH TRONG KỲ
				"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, \n " +
				"		SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
					   
				"		FROM( \n " +
				"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
				"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
				"			THEN \n " +
				"				SUM(CO) - SUM(NO) \n " +
				"			ELSE  \n " +
				"				0 \n " +
				"			END AS CO,  \n " +
					
				"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
				"			THEN \n " +
				"				SUM(NO) - SUM(CO) \n " +
				"			ELSE  \n " +
				"				0 \n " +
				"			END AS NO \n " +
											
				"			FROM( \n " +
				"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
				"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
				"				FROM ERP_PHATSINHKETOAN \n " +
				"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'   \n " + 
				
				//#Begin Tích hợp phát sinh DMS
				/*
				" 			UNION ALL "+
				"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
				"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
				"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
				"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'   \n " + 
				*/
				//#End Tích hợp phát sinh DMS
				
				"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
				"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG \n " +
				"	)DAUKY_PHATSINH " +
				"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
				")DATA ON DATA.TKID = TK.PK_SEQ \n" +
				" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
			}else{

				query = 
					"SELECT SUM(ISNULL(NOVND, 0)) - SUM(ISNULL(COVND, 0)) AS GIATRI \n " +
				
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
						// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
					"  SELECT TKID, MADOITUONG, DOITUONG, " +
					"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
					"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
					"  ELSE " +
					"		 0 " +
					"  END AS NOVND, \n " +
						
					"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
					"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
					"  ELSE " +
					"		 0 " +
					"  END AS COVND \n " +
					"  FROM ( \n " +
					
					// LẤY ĐẦU KỲ
					"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, \n " +
					"		SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
					"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
					"	 	WHERE thang = '" + lastmonth_kytruoc + "' and NAM = '" + lastyear_kytruoc + "' " +
					"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
					" 		UNION ALL \n " +
					
					// LẤY PHÁT SINH TRONG KỲ
					"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, \n " +
					"		SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
						   
					"		FROM( \n " +
					"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
					"			THEN \n " +
					"				SUM(CO) - SUM(NO) \n " +
					"			ELSE  \n " +
					"				0 \n " +
					"			END AS CO,  \n " +
						
					"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
					"			THEN \n " +
					"				SUM(NO) - SUM(CO) \n " +
					"			ELSE  \n " +
					"				0 \n " +
					"			END AS NO \n " +
												
					"			FROM( \n " +
					"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
					"				FROM ERP_PHATSINHKETOAN \n " +
					"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'   \n " + 
					
					//#Begin Tích hợp phát sinh DMS
					/*
					" 			UNION ALL "+
					"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
					"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
					"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'   \n " + 
					*/
					//#End Tích hợp phát sinh DMS
					
					"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
					"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG \n " +
					"	)DAUKY_PHATSINH " +
					"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
					")DATA ON DATA.TKID = TK.PK_SEQ \n" +
					" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
			} 
			
		}
		else{
// Lấy kỳ này
				int lastmonth_kynay =0;
				int lastyear_kynay=0;
										
				if (Integer.parseInt(this.month) > 1){
					lastmonth_kynay = Integer.parseInt(this.month) - 1;
					lastyear_kynay = Integer.parseInt(this.year) ;
				}else{
					lastmonth_kynay = 12;
					lastyear_kynay = Integer.parseInt(this.year) - 1;
				}
			if(sohieutk.indexOf("131") >= 0 || sohieutk.indexOf("136") >= 0 || sohieutk.indexOf("138") >= 0 || sohieutk.indexOf("331") >= 0 || sohieutk.indexOf("336") >= 0 || sohieutk.indexOf("338") >= 0){
				
				query = 
					"SELECT SUM(ISNULL(NOVND, 0)) AS GIATRI 						\n " +
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
						// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
					"  SELECT TKID, MADOITUONG, DOITUONG, " +
					"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
					"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
					"  ELSE " +
					"		 0 " +
					"  END AS NOVND, \n " +
						
					"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
					"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
					"  ELSE " +
					"		 0 " +
					"  END AS COVND \n " +
					"  FROM ( \n " +
					
					// LẤY ĐẦU KỲ
					"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, \n " +
					"		SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
					"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
					"	 	WHERE thang = '" + lastmonth_kynay + "' and NAM = '" + lastyear_kynay + "' " +
					"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
					" 		UNION ALL \n " +
					
					// LẤY PHÁT SINH TRONG KỲ
					"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, \n " +
					"		SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
						   
					"		FROM( \n " +
					"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
					"			THEN \n " +
					"				SUM(CO) - SUM(NO) \n " +
					"			ELSE  \n " +
					"				0 \n " +
					"			END AS CO,  \n " +
						
					"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
					"			THEN \n " +
					"				SUM(NO) - SUM(CO) \n " +
					"			ELSE  \n " +
					"				0 \n " +
					"			END AS NO \n " +
												
					"			FROM( \n " +
					"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
					"				FROM ERP_PHATSINHKETOAN \n " +					
					"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
					
					//#Begin Tích hợp phát sinh DMS
					/*
					" 			UNION ALL "+
					"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
					"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
					"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
					*/
					//#End Tích hợp phát sinh DMS
					
					"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
					"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG \n " +
					"	)DAUKY_PHATSINH " +
					"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
					")DATA ON DATA.TKID = TK.PK_SEQ \n" +
					" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}else{ 
						query = 
							"SELECT SUM(ISNULL(NOVND, 0)) - SUM(ISNULL(COVND, 0)) AS GIATRI 						\n " +
							"FROM ERP_TAIKHOANKT TK \n" +
							"LEFT JOIN ( \n" +
								// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
							"  SELECT TKID, MADOITUONG, DOITUONG, " +
							"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
							"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
							"  ELSE " +
							"		 0 " +
							"  END AS NOVND, \n " +
								
							"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
							"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
							"  ELSE " +
							"		 0 " +
							"  END AS COVND \n " +
							"  FROM ( \n " +
							
							// LẤY ĐẦU KỲ
							"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, \n " +
							"		SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
							"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
							"	 	WHERE thang = '" + lastmonth_kynay + "' and NAM = '" + lastyear_kynay + "' " +
							"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
							" 		UNION ALL \n " +
							
							// LẤY PHÁT SINH TRONG KỲ
							"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, \n " +
							"		SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
								   
							"		FROM( \n " +
							"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
							"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
							"			THEN \n " +
							"				SUM(CO) - SUM(NO) \n " +
							"			ELSE  \n " +
							"				0 \n " +
							"			END AS CO,  \n " +
								
							"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
							"			THEN \n " +
							"				SUM(NO) - SUM(CO) \n " +
							"			ELSE  \n " +
							"				0 \n " +
							"			END AS NO \n " +
														
							"			FROM( \n " +
							"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
							"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
							"				FROM ERP_PHATSINHKETOAN \n " +					
							"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
							
							//#Begin Tích hợp phát sinh DMS
							/*
							" 			UNION ALL "+
							"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
							"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
							"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
							"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
							*/
							//#End Tích hợp phát sinh DMS
							
							"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
							"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG \n " +
							"	)DAUKY_PHATSINH " +
							"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
							")DATA ON DATA.TKID = TK.PK_SEQ \n" +
							" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
					
				}
			}

			System.out.println("\n\nGET No tru co theo tung doi tuong: " + query);
			ResultSet rs=db.get(query);
			if(rs.next()){
				giatri = rs.getDouble("giatri");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}
	@Override
	public Double getNo_New(String soHieu, boolean kyTruoc){
		double giaTri = 0;
		String[] soHieus = soHieu.split(",");
		int thismonth_kynay = Integer.parseInt(this.month);
		int thisyear_kynay = Integer.parseInt(this.year);
		String congTy = this.getErpCongtyId();
		if (congTy.trim().endsWith(",")){
			congTy = congTy.substring(0,congTy.length() -1);
		}
		try{
			if(kyTruoc ==true){
				int lastmonth_kytruoc = 0;
				int lastyear_kytruoc = 0;
				int thismonth_kytruoc = 0;
				int thisyear_kytruoc = 0;
				
				if (thismonth_kynay > 1){
					thismonth_kytruoc = thismonth_kynay - 1;
					thisyear_kytruoc = thisyear_kynay;
				}else{
					thismonth_kytruoc = 12;
					thisyear_kytruoc = thisyear_kynay - 1;
				}

				if(thismonth_kytruoc > 1){
					lastmonth_kytruoc = thismonth_kytruoc - 1;
					lastyear_kytruoc = thisyear_kytruoc;
				}else{
					lastmonth_kytruoc = 12;
					lastyear_kytruoc = thisyear_kytruoc - 1;				
				}
				String sql = "";
				String query = "SELECT TOP (1) thang, nam from ERP_TAIKHOAN_NOCO_KHOASO \n"
						+" ORDER BY NAM DESC,THANG DESC " ;
				for (int i =0 ; i<soHieus.length;i++){
					soHieus[i]=soHieus[i].trim();
					if(soHieus[i].startsWith("131") || soHieus[i].startsWith("136") || soHieus[i].startsWith("138")
							|| soHieus[i].startsWith("331") || soHieus[i].startsWith("336") 
							|| soHieus[i].startsWith("338")){
						sql = " SELECT SUM (NO) AS GIATRI "
								+ " FROM "
								+ "(SELECT TKID AS TKID , MADOITUONG ,DOITUONG , "
								+ " CASE WHEN SUM(NO) - SUM(CO) > 0 THEN SUM(NO) - SUM(CO) ELSE 0 END AS NO, "
								+ " CASE WHEN SUM(NO) - SUM(CO) < 0 THEN (SUM(NO) -SUM(CO)) *(-1) ELSE 0 END CO "
								+ " FROM "
								+ " (SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0), 0) AS NO,ISNULL(ROUND(CO, 0), 0) AS CO "
								+ " FROM ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								" ' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")"
								+ " UNION ALL \n"
								//Tích hợp DMS
								+ " SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								" ' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")"
								//Tích hợp DMS
								+ " UNION ALL "
								+ " SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
								+ " WHERE THANG ='"+lastmonth_kytruoc+"' AND NAM='"+lastyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +")" 
								+ " )CK_DT "
								+ " GROUP BY TKID,MADOITUONG,DOITUONG)CK_TK ";
						//Kiếm tra tháng đó có trước đó có khóa sổ chưa - NẾU CÓ LẤY DỮ LIỆU CỦA THÁNG
						//KS ĐÓ LUÔN
						ResultSet rsThangKS = db.get(query);
						if (rsThangKS != null){
							while (rsThangKS.next()){
								if(Integer.parseInt(rsThangKS.getString("THANG"))==thismonth_kytruoc 
										&& Integer.parseInt(rsThangKS.getString("NAM")) ==thisyear_kytruoc){
									sql = " SELECT SUM (NO) AS GIATRI "
											+ " FROM "
											+ "(SELECT TKID AS TKID , MADOITUONG ,DOITUONG , "
											+ " CASE WHEN SUM(NO) - SUM(CO) > 0 THEN SUM(NO) - SUM(CO) ELSE 0 END AS NO, "
											+ " CASE WHEN SUM(NO) - SUM(CO) < 0 THEN (SUM(NO) -SUM(CO)) *(-1) ELSE 0 END CO "
											+ " FROM "
											+ " ( SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
											+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
											+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
											+ " WHERE THANG ='"+thismonth_kytruoc+"' AND NAM='"+thisyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +")" 
											+ " )CK_DT "
											+ " GROUP BY TKID,MADOITUONG,DOITUONG)CK_TK ";
								}
							}
						}
						
					}
					else if((soHieus[i].startsWith("1") ||soHieus[i].startsWith("2") ||
							soHieus[i].startsWith("6")||soHieus[i].startsWith("8") ||
							soHieus[i].startsWith("521") || soHieus[i].startsWith("531") ||
							soHieus[i].startsWith("532")) && !soHieus[i].startsWith("214")
							&& !soHieus[i].startsWith("229")){
						sql=" SELECT SUM(NO) - SUM(CO) AS GIATRI "
								+ " FROM "
								+ "(SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOAN_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								" ' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								+" UNION ALL \n"
								//Tích hợp DMS
								+ " SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								//Tích hợp DMS
						
								+ " UNION ALL "
								+ " SELECT TAIKHOANKT_FK,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  "
								+ " WHERE THANG ='"+lastmonth_kytruoc+"' AND NAM='"+lastyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +"))CK ";
						
						//Kiếm tra tháng đó có trước đó có khóa sổ chưa - NẾU CÓ LẤY DỮ LIỆU CỦA THÁNG
						//KS ĐÓ LUÔN
						ResultSet rsThangKS = db.get(query);
						if (rsThangKS != null){
							while (rsThangKS.next()){
								if(Integer.parseInt(rsThangKS.getString("THANG"))==thismonth_kytruoc 
										&& Integer.parseInt(rsThangKS.getString("NAM")) ==thisyear_kytruoc){
									sql=" SELECT SUM(NO) - SUM(CO) AS GIATRI "
											+ " FROM "
											+ " ( SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
											+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
											+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
											+ " WHERE THANG ='"+thismonth_kytruoc+"' AND NAM='"+thisyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +")" 
											+ " )CK_DT ";
								}
							}
						}
					}else{
						sql = "SELECT 0 AS GIATRI";
					}
					System.out.println ("\n\n SQL cân đối kỳ trước "+soHieu +": "+sql );
					ResultSet rs=db.get(sql);
					if(rs.next()){
						giaTri = giaTri + rs.getDouble("GIATRI");
					}
					rs.close();
				}
			}else {
				int lastmonth_kynay =0;
				int lastyear_kynay=0;
										
				if (Integer.parseInt(this.month) > 1){
					lastmonth_kynay = Integer.parseInt(this.month) - 1;
					lastyear_kynay = Integer.parseInt(this.year) ;
				}else{
					lastmonth_kynay = 12;
					lastyear_kynay = Integer.parseInt(this.year) - 1;
				}
				String sql = "";
				
				for (int i =0 ; i<soHieus.length;i++){
					soHieus[i]=soHieus[i].trim();
					if(soHieus[i].startsWith("131") || soHieus[i].startsWith("136") || soHieus[i].startsWith("138")
							|| soHieus[i].startsWith("331") || soHieus[i].startsWith("336") 
							|| soHieus[i].startsWith("338")){
						sql = " SELECT SUM (NO) AS GIATRI "
								+ " FROM "
								+ " (SELECT TKID AS TKID , MADOITUONG ,DOITUONG , "
								+ " CASE WHEN SUM(NO) - SUM(CO) > 0 THEN SUM(NO) - SUM(CO) ELSE 0 END AS NO, "
								+ " CASE WHEN SUM(NO) - SUM(CO) < 0 THEN (SUM(NO) -SUM(CO)) *(-1) ELSE 0 END CO "
								+ " FROM "
								+ "(SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0)AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								" ' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								//Tích hợp DMS
								+" UNION ALL "
								+ " SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0)AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								" ' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								//Tích hợp DMS
								+ " UNION ALL "
								+ " SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
								+ " WHERE THANG ='"+lastmonth_kynay+"' AND NAM='"+lastyear_kynay+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								+ " )CK_DT "
								+ " GROUP BY TKID,MADOITUONG,DOITUONG)CK_TK ";
					}
					else if((soHieus[i].startsWith("1") ||soHieus[i].startsWith("2") ||
							soHieus[i].startsWith("6")||soHieus[i].startsWith("8") || 
							soHieus[i].startsWith("521") || soHieus[i].startsWith("531") ||
							soHieus[i].startsWith("532")) && !soHieus[i].startsWith("214")
							&& !soHieus[i].startsWith("229")){
						sql=" SELECT SUM(NO) - SUM(CO) AS GIATRI "
								+ " FROM "
								+ "(SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								
								//Tích hợp DMS
								+" UNION ALL "
								+ " SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ " FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ " WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")"
								//Tích hợp DMS
								
								+ " UNION ALL "
								+ " SELECT TAIKHOANKT_FK,ISNULL(ROUND(GIATRINOVND, 0), 0) AS NO,ISNULL(ROUND(GIATRICOVND, 0), 0) AS CO "
								+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  "
								+ " WHERE THANG ='"+lastmonth_kynay+"' AND NAM='"+lastyear_kynay+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in(" + congTy +"))CK ";
						
					}else{	
						sql = "SELECT 0 AS GIATRI";
					}
					System.out.println ("\n\n SQL cân đối "+soHieu +": "+sql );
					ResultSet rs=db.get(sql);
					if(rs.next()){
						giaTri = giaTri + rs.getDouble("GIATRI");
					}
					rs.close();
				
			}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return giaTri;
	}
	
	@Override
	public Double getCo_New(String soHieu, boolean kyTruoc){
		double giaTri = 0;
		String[] soHieus = soHieu.split(",");
		String congTy = this.getErpCongtyId();
		if (congTy.trim().endsWith(",")){
			congTy = congTy.substring(0,congTy.length() -1);
		}
		int thismonth_kynay = Integer.parseInt(this.month);
		int thisyear_kynay = Integer.parseInt(this.year);
		try{
			if(kyTruoc ==true){
				int lastmonth_kytruoc = 0;
				int lastyear_kytruoc = 0;
				int thismonth_kytruoc = 0;
				int thisyear_kytruoc = 0;
				
				if (thismonth_kynay > 1){
					thismonth_kytruoc = thismonth_kynay - 1;
					thisyear_kytruoc = thisyear_kynay;
				}else{
					thismonth_kytruoc = 12;
					thisyear_kytruoc = thisyear_kynay - 1;
				}

				if(thismonth_kytruoc > 1){
					lastmonth_kytruoc = thismonth_kytruoc - 1;
					lastyear_kytruoc = thisyear_kytruoc;
				}else{
					lastmonth_kytruoc = 12;
					lastyear_kytruoc = thisyear_kytruoc - 1;				
				}
				String sql = "";
				String query = "SELECT TOP (1) thang, nam from ERP_TAIKHOAN_NOCO_KHOASO \n"
						+" ORDER BY NAM DESC,THANG DESC " ;
				for (int i =0 ; i<soHieus.length;i++){
					soHieus[i]=soHieus[i].trim();
					if(soHieus[i].startsWith("131") || soHieus[i].startsWith("136") || soHieus[i].startsWith("138")
							|| soHieus[i].startsWith("331") || soHieus[i].startsWith("336") 
							|| soHieus[i].startsWith("338")){
						sql = "SELECT SUM (CO) AS GIATRI "
								+ "FROM "
								+ "(SELECT TKID AS TKID , MADOITUONG ,DOITUONG , "
								+ "CASE WHEN SUM(NO) - SUM(CO) > 0 THEN SUM(NO) - SUM(CO) ELSE 0 END AS NO, "
								+ "CASE WHEN SUM(NO) - SUM(CO) < 0 THEN (SUM(NO) -SUM(CO)) *(-1) ELSE 0 END CO "
								+ "FROM "
								+ "(SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")" 
								
								//Tích hợp DMS
								+ " UNION ALL \n"
								+ "SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy +")" 
								//Tích hợp DMS
								
								+ " UNION ALL "
								+ "SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ "FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
								+ "WHERE THANG ='"+lastmonth_kytruoc+"' AND NAM='"+lastyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND NPP_FK in ( " + congTy + ")" 
								+ ")CK_DT "
								+ "GROUP BY TKID,MADOITUONG,DOITUONG)CK_TK ";
						//Kiếm tra tháng đó có trước đó có khóa sổ chưa - NẾU CÓ LẤY DỮ LIỆU CỦA THÁNG
						//KS ĐÓ LUÔN
						ResultSet rsThangKS = db.get(query);
						if (rsThangKS != null){
							while (rsThangKS.next()){
								if(Integer.parseInt(rsThangKS.getString("THANG"))==thismonth_kytruoc 
										&& Integer.parseInt(rsThangKS.getString("NAM")) ==thisyear_kytruoc){
									sql = " SELECT SUM (CO) AS GIATRI "
											+ " FROM "
											+ "(SELECT TKID AS TKID , MADOITUONG ,DOITUONG , "
											+ " CASE WHEN SUM(NO) - SUM(CO) > 0 THEN SUM(NO) - SUM(CO) ELSE 0 END AS NO, "
											+ " CASE WHEN SUM(NO) - SUM(CO) < 0 THEN (SUM(NO) -SUM(CO)) *(-1) ELSE 0 END CO "
											+ " FROM "
											+ " ( SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
											+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
											+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
											+ " WHERE THANG ='"+thismonth_kytruoc+"' AND NAM='"+thisyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +")" 
											+ " )CK_DT "
											+ " GROUP BY TKID,MADOITUONG,DOITUONG)CK_TK ";
								}
							}
						}
						
					}
					else if((soHieus[i].startsWith("1") ||soHieus[i].startsWith("2") ||
							soHieus[i].startsWith("6")||soHieus[i].startsWith("8") ||
							soHieus[i].startsWith("521") || soHieus[i].startsWith("531") ||
							soHieus[i].startsWith("532")) && !soHieus[i].startsWith("214")
							&& !soHieus[i].startsWith("229")){
						sql = "SELECT 0 AS GIATRI";
					}else{
						sql="SELECT SUM(CO) - SUM(NO) AS GIATRI "
								+ "FROM "
								+ "(SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")"   
								
								//Tích hợp DMS
								+ "UNION ALL \n"
								+ "SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")"   
								//Tích hợp DMS
								+ " UNION ALL "
								+ "SELECT TAIKHOANKT_FK,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ "FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  "
								+ "WHERE THANG ='"+lastmonth_kytruoc+"' AND NAM='"+lastyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +" ))CK ";
						
						//Kiếm tra tháng đó có trước đó có khóa sổ chưa - NẾU CÓ LẤY DỮ LIỆU CỦA THÁNG
						//KS ĐÓ LUÔN
						ResultSet rsThangKS = db.get(query);
						if (rsThangKS != null){
							while (rsThangKS.next()){
								if(Integer.parseInt(rsThangKS.getString("THANG"))==thismonth_kytruoc 
										&& Integer.parseInt(rsThangKS.getString("NAM")) ==thisyear_kytruoc){
									sql=" SELECT SUM(CO) - SUM(NO) AS GIATRI "
											+ " FROM "
											+ " ( SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
											+ " FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
											+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
											+ " WHERE THANG ='"+thismonth_kytruoc+"' AND NAM='"+thisyear_kytruoc+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +")" 
											+ " )CK_DT ";
								}
							}
						}
					}
					System.out.println ("\n\n SQL cân đối kỳ trước "+soHieu +": "+sql );
					ResultSet rs=db.get(sql);
					if(rs.next()){
						giaTri = giaTri + rs.getDouble("GIATRI");
					}
					rs.close();
				}
			}else {
				int lastmonth_kynay =0;
				int lastyear_kynay=0;
										
				if (Integer.parseInt(this.month) > 1){
					lastmonth_kynay = Integer.parseInt(this.month) - 1;
					lastyear_kynay = Integer.parseInt(this.year) ;
				}else{
					lastmonth_kynay = 12;
					lastyear_kynay = Integer.parseInt(this.year) - 1;
				}
				String sql = "";
				for (int i =0 ; i<soHieus.length;i++){
					soHieus[i]=soHieus[i].trim();
					if(soHieus[i].startsWith("131") || soHieus[i].startsWith("136") || soHieus[i].startsWith("138")
							|| soHieus[i].startsWith("331") || soHieus[i].startsWith("336") 
							|| soHieus[i].startsWith("338")){
						sql = "SELECT SUM (CO) AS GIATRI "
								+ "FROM "
								+ "(SELECT TKID AS TKID , MADOITUONG ,DOITUONG , "
								+ "CASE WHEN SUM(NO) - SUM(CO) > 0 THEN SUM(NO) - SUM(CO) ELSE 0 END AS NO, "
								+ "CASE WHEN SUM(NO) - SUM(CO) < 0 THEN (SUM(NO) -SUM(CO)) *(-1) ELSE 0 END CO "
								+ "FROM "
								+ "(SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")" 
								//Tích hợp DMS
								+"UNION ALL"
								+  " SELECT TAIKHOAN_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")" 
								//Tích hợp DMS
								+ " UNION ALL "
								+ "SELECT TAIKHOANKT_FK AS TKID,MADOITUONG,DOITUONG,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ "FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK "
								+ "WHERE THANG ='"+lastmonth_kynay+"' AND NAM='"+lastyear_kynay+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " + congTy + ")"
								+ " )CK_DT "
								+ "GROUP BY TKID,MADOITUONG,DOITUONG)CK_TK ";
					}
					else if((soHieus[i].startsWith("1") ||soHieus[i].startsWith("2") ||
							soHieus[i].startsWith("6")||soHieus[i].startsWith("8") ||
							soHieus[i].startsWith("521") || soHieus[i].startsWith("531") ||
							soHieus[i].startsWith("532")) && !soHieus[i].startsWith("214")
							&& !soHieus[i].startsWith("229")){
						sql = "SELECT 0 AS GIATRI";
					}else{
						sql="SELECT SUM(CO) - SUM(NO) AS GIATRI "
								+ "FROM "
								+ "(SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " +congTy + ")"
								
								//Tích hợp DMS 
								+" UNION ALL \n"
								+ " SELECT TAIKHOAN_FK,ISNULL(ROUND(NO, 0),0) AS NO,ISNULL(ROUND(CO, 0),0) AS CO "
								+ "FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS "
								+ "INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK "
								+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK "
								+ "WHERE SUBSTRING(NGAYGHINHAN,1,7) = '"+ thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) +
								"' AND TK.SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in ( " +congTy + ")"
								//Tích hợp DMS
								
								+ " UNION ALL "
								+ "SELECT TAIKHOANKT_FK,ISNULL(ROUND(GIATRINOVND, 0),0) AS NO,ISNULL(ROUND(GIATRICOVND, 0),0) AS CO "
								+ "FROM ERP_TAIKHOAN_NOCO_KHOASO KS "
								+ "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  "
								+ "WHERE THANG ='"+lastmonth_kynay+"' AND NAM='"+lastyear_kynay+"' AND SOHIEUTAIKHOAN LIKE '"+soHieus[i]+"%' AND TK.NPP_FK in (" + congTy +" ))CK ";
					}
					System.out.println ("\n\n SQL cân đối "+soHieu +": "+sql );
					ResultSet rs=db.get(sql);
					if(rs.next()){
						giaTri = giaTri + rs.getDouble("GIATRI");
					}
					rs.close();
				
			}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return giaTri;
	}

	public Double getCoTruNo_TheoDoiTuong(String sohieu , boolean kytruoc) {
		
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i].trim() +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan truoc" + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "(" + sohieutk.substring(0, index) + ")";
		
		System.out.print("Chuoi sohieutaikhoan sau" + sohieutk);
		int thismonth_kynay = Integer.parseInt(this.month);
		int thisyear_kynay = Integer.parseInt(this.year);
		
		try{
			String query ="";
			if(kytruoc == true)
			{
				int lastmonth_kytruoc = 0;
				int lastyear_kytruoc = 0;
				int thismonth_kytruoc = 0;
				int thisyear_kytruoc = 0;
				
				if (thismonth_kynay > 1){
					thismonth_kytruoc = thismonth_kynay - 1;
					thisyear_kytruoc = thisyear_kynay;
				}else{
					thismonth_kytruoc = 12;
					thisyear_kytruoc = thisyear_kynay - 1;
				}

				if(thismonth_kytruoc > 1){
					lastmonth_kytruoc = thismonth_kytruoc - 1;
					lastyear_kytruoc = thisyear_kytruoc;
				}else{
					lastmonth_kytruoc = 12;
					lastyear_kytruoc = thisyear_kytruoc - 1;				
				}
				
				if(sohieutk.indexOf("131") >= 0 || sohieutk.indexOf("136") >= 0 || sohieutk.indexOf("138") >= 0 || sohieutk.indexOf("331") >= 0 || sohieutk.indexOf("336") >= 0 || sohieutk.indexOf("338") >= 0){
				
					query = 
						"SELECT SUM(ISNULL(COVND, 0)) AS GIATRI 						\n " +
						"FROM ERP_TAIKHOANKT TK \n" +
						"LEFT JOIN ( \n" +
						// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
						"  SELECT TKID, MADOITUONG, DOITUONG, " +
						"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
						"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
						"  ELSE " +
						"		 0 " +
						"  END AS NOVND, \n " +
				
						"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
						"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
						"  ELSE " +
						"		 0 " +
						"  END AS COVND \n " +
				
						"  FROM ( \n " +
				
						// LẤY ĐẦU KỲ
						"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
						"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
						"	 	WHERE thang = '" + lastmonth_kytruoc + "' and NAM = '" + lastyear_kytruoc + "' " +
						"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
						" 		UNION ALL \n " +
				
						// LẤY PHÁT SINH TRONG KỲ
						"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
					   
						"		FROM( \n " +
						"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
						"			THEN \n " +
						"				SUM(CO) - SUM(NO) \n " +
						"			ELSE  \n " +
						"				0 \n " +
						"			END AS CO,  \n " +
					
						"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
						"			THEN \n " +
						"				SUM(NO) - SUM(CO) \n " +
						"			ELSE  \n " +
						"				0 \n " +
						"			END AS NO \n " +
											
						"			FROM( \n " +
						"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
						"				FROM ERP_PHATSINHKETOAN \n " +				
						"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'  \n " +
						
						//#Begin Tích hợp phát sinh DMS
						/*
						" 			UNION ALL "+
						"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
						"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
						"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'  \n " +
						*/
						//#End Tích hợp phát sinh DMS
						
						"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
						"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG  \n " +
						"	)DAUKY_PHATSINH " +
						"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
						")DATA ON DATA.TKID = TK.PK_SEQ \n" +
						" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}else{
					query = 
						"SELECT " +
						"CASE WHEN SUM(ISNULL(NOVND, 0)) - SUM(ISNULL(COVND, 0)) >0" +
						"THEN" +
						"	0 " +
						"ELSE" +
						"	(SUM(ISNULL(NOVND, 0)) - SUM(ISNULL(COVND, 0)))*(-1)" +
						"END AS GIATRI 						\n " +
						"FROM ERP_TAIKHOANKT TK \n" +
						"LEFT JOIN ( \n" +
						// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
						"  SELECT TKID, MADOITUONG, DOITUONG, " +
						"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
						"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
						"  ELSE " +
						"		 0 " +
						"  END AS NOVND, \n " +
				
						"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
						"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
						"  ELSE " +
						"		 0 " +
						"  END AS COVND \n " +
				
						"  FROM ( \n " +
				
						// LẤY ĐẦU KỲ
						"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
						"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
						"	 	WHERE thang = '" + lastmonth_kytruoc + "' and NAM = '" + lastyear_kytruoc + "' " +
						"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
						" 		UNION ALL \n " +
				
						// LẤY PHÁT SINH TRONG KỲ
						"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
					   
						"		FROM( \n " +
						"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
						"			THEN \n " +
						"				SUM(CO) - SUM(NO) \n " +
						"			ELSE  \n " +
						"				0 \n " +
						"			END AS CO,  \n " +
					
						"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
						"			THEN \n " +
						"				SUM(NO) - SUM(CO) \n " +
						"			ELSE  \n " +
						"				0 \n " +
						"			END AS NO \n " +
											
						"			FROM( \n " +
						"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
						"				FROM ERP_PHATSINHKETOAN \n " +
						"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'  \n " +
						
						//#Begin Tích hợp phát sinh DMS
						/*
						" 			UNION ALL "+
						"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
						"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
						"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kytruoc + "-" + (thismonth_kytruoc < 10?"0" + thismonth_kytruoc:thismonth_kytruoc) + "'  \n " +
						*/
						//#End Tích hợp phát sinh DMS
						
						"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
						"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG  \n " +
						"	)DAUKY_PHATSINH " +
						"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
						")DATA ON DATA.TKID = TK.PK_SEQ \n" +
						" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
					
				}
			}
			else{
// Lấy kỳ này
				int lastmonth_kynay =0;
				int lastyear_kynay=0;
										
				if (Integer.parseInt(this.month) > 1){
					lastmonth_kynay = Integer.parseInt(this.month) - 1;
					lastyear_kynay = Integer.parseInt(this.year) ;
				}else{
					lastmonth_kynay = 12;
					lastyear_kynay = Integer.parseInt(this.year) - 1;
				}

				if(sohieutk.indexOf("131") >= 0 || sohieutk.indexOf("136") >= 0 || sohieutk.indexOf("138") >= 0 || sohieutk.indexOf("331") >= 0 || sohieutk.indexOf("336") >= 0 || sohieutk.indexOf("338") >= 0){
				
				query = 
					"SELECT SUM(ISNULL(COVND, 0)) AS GIATRI 						\n " +
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
					// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
					"  SELECT TKID, MADOITUONG, DOITUONG, " +
					"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
					"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
					"  ELSE " +
					"		 0 " +
					"  END AS NOVND, \n " +
					
					"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
					"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
					"  ELSE " +
					"		 0 " +
					"  END AS COVND \n " +
					"  FROM ( \n " +
					
					// LẤY ĐẦU KỲ
					"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
					"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
					"	 	WHERE thang = '" + lastmonth_kynay + "' and NAM = '" + lastyear_kynay + "' " +
					"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
					" 		UNION ALL \n " +
					
					// LẤY PHÁT SINH TRONG KỲ
					"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
						   
					"		FROM( \n " +
					"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
					"			THEN \n " +
					"				SUM(CO) - SUM(NO) \n " +
					"			ELSE  \n " +
					"				0 \n " +
					"			END AS CO,  \n " +
						
					"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
					"			THEN \n " +
					"				SUM(NO) - SUM(CO) \n " +
					"			ELSE  \n " +
					"				0 \n " +
					"			END AS NO \n " +
												
					"			FROM( \n " +
					"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
					"				FROM ERP_PHATSINHKETOAN \n " +
					"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
					
						//#Begin Tích hợp phát sinh DMS
						/*
					" 			UNION ALL "+
					"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
					"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
					"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
					"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
						*/
						//#End Tích hợp phát sinh DMS
					
					"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
					"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG  \n " +
					"	)DAUKY_PHATSINH " +
					"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
					")DATA ON DATA.TKID = TK.PK_SEQ \n" +
					" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}else{
					query = 
						"SELECT " +
						"CASE WHEN SUM(ISNULL(NOVND, 0)) - SUM(ISNULL(COVND, 0)) >0" +
						"THEN" +
						"	0 " +
						"ELSE" +
						"	(SUM(ISNULL(NOVND, 0)) - SUM(ISNULL(COVND, 0)))*(-1)" +
						"END AS GIATRI 						\n " +
						"FROM ERP_TAIKHOANKT TK \n" +
						"LEFT JOIN ( \n" +
						// CỘNG ĐẦU KỲ VÀ PHÁT SINH TRONG KỲ THEO ĐỐI TƯỢNG VÀ TỔNG NỢ TRỪ ĐI TỔNG CÓ THEO TỪNG ĐỐI TƯỢNG
						"  SELECT TKID, MADOITUONG, DOITUONG, " +
						"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) > 0 THEN" +
						"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) \n " +
						"  ELSE " +
						"		 0 " +
						"  END AS NOVND, \n " +
						
						"  CASE WHEN (SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0))) < 0 THEN" +
						"		(SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)))*(-1) \n " +
						"  ELSE " +
						"		 0 " +
						"  END AS COVND \n " +
						"  FROM ( \n " +
						
						// LẤY ĐẦU KỲ
						"		SELECT TAIKHOANKT_FK AS TKID, MADOITUONG, DOITUONG, SUM(ROUND(GIATRINOVND, 0)) AS GIATRINOVND, SUM(ROUND(GIATRICOVND, 0)) AS GIATRICOVND \n " +
						"  		FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
						"	 	WHERE thang = '" + lastmonth_kynay + "' and NAM = '" + lastyear_kynay + "' " +
						"		GROUP BY TAIKHOANKT_FK, MADOITUONG, DOITUONG \n" +
						" 		UNION ALL \n " +
						
						// LẤY PHÁT SINH TRONG KỲ
						"		SELECT TAIKHOAN_FK AS TKID, MADOITUONG, DOITUONG, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n " +
							   
						"		FROM( \n " +
						"			SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"			CASE WHEN SUM(CO) - SUM(NO) > 0  \n " +
						"			THEN \n " +
						"				SUM(CO) - SUM(NO) \n " +
						"			ELSE  \n " +
						"				0 \n " +
						"			END AS CO,  \n " +
							
						"			CASE WHEN SUM(NO) - SUM(CO) > 0  \n " +
						"			THEN \n " +
						"				SUM(NO) - SUM(CO) \n " +
						"			ELSE  \n " +
						"				0 \n " +
						"			END AS NO \n " +
													
						"			FROM( \n " +
						"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
						"				FROM ERP_PHATSINHKETOAN \n " +
						"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
						
						//#Begin Tích hợp phát sinh DMS
						/*
						" 			UNION ALL "+
						"				SELECT TAIKHOAN_FK, MADOITUONG, DOITUONG,  \n " +
						"				ROUND(ISNULL(CO, 0), 0) AS CO, ROUND(ISNULL(NO, 0), 0) AS NO \n " +
						"				FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " +
						"				WHERE SUBSTRING(NGAYGHINHAN, 1, 7) = '" + thisyear_kynay + "-" + (thismonth_kynay < 10?"0" + thismonth_kynay:thismonth_kynay) + "'  \n " + 
						*/
						//#End Tích hợp phát sinh DMS
						
						"			)NHOMDOITUONG GROUP BY NHOMDOITUONG.TAIKHOAN_FK, NHOMDOITUONG.MADOITUONG, NHOMDOITUONG.DOITUONG \n " +
						"		)NHOMTAIKHOAN GROUP BY NHOMTAIKHOAN.TAIKHOAN_FK, NHOMTAIKHOAN.MADOITUONG, NHOMTAIKHOAN.DOITUONG  \n " +
						"	)DAUKY_PHATSINH " +
						"   GROUP BY DAUKY_PHATSINH.TKID, DAUKY_PHATSINH.MADOITUONG, DAUKY_PHATSINH.DOITUONG \n " +
						")DATA ON DATA.TKID = TK.PK_SEQ \n" +
						" where "+ sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
					
				}
			}

			System.out.println("GET CO tru NO theo tung doi tuong: " + query);
			ResultSet rs=db.get(query);
			if(rs.next()){
				giatri = rs.getDouble("giatri");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}

	public Double getNoTruCoCuoiKy(String sohieu, String ctyId) {
		int lastmonth=0;
		int lastyear=0;
		
		lastyear = Integer.parseInt(this.year) - 1;
		lastmonth = 0;
				
		if (Integer.parseInt(this.month) > 1){
			lastmonth = Integer.parseInt(this.month) - 1;
		}else{
			lastmonth = 12;
		}
		
		
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i].trim() +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan truoc" + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "(" + sohieutk.substring(0, index) + ")";
		
		System.out.print("Chuoi sohieutaikhoan sau" + sohieutk);
		
		try{
			String query ="";
															
			if(lastmonth == 12){
				query = "SELECT SUM(GIATRI) AS GIATRI " +
						"FROM " +
						"( " +
						"SELECT ROUND ( ISNULL(DAUKY.GIATRINOVND, 0) - ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) -  ISNULL(PHATSINH.GIATRICOVND, 0)), 0) AS giatri 						\n" +
						"FROM ERP_TAIKHOANKT TK \n" +
						"LEFT JOIN ( \n" +
						"	SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
						"	FROM ERP_TAIKHOAN_NOCO\n" +
						" where thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
						"	GROUP BY TAIKHOANKT_FK \n" +
						")DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
						"LEFT JOIN( \n" +
						"	SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
						"	FROM ERP_PHATSINHKETOAN \n" +
						" where month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
						"	GROUP BY TAIKHOAN_FK \n" +
						")PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
						
						"where "+ sohieutk + " AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) " +
						")A " ;
				
			}else{
				query = "SELECT SUM(GIATRI) AS GIATRI " +
						"FROM " +
						"( " +
					
						"SELECT ROUND ( ISNULL(DAUKY.GIATRINOVND, 0) - ISNULL(DAUKY.GIATRICOVND, 0) " +
						"				+ ISNULL(PHATSINH.GIATRINOVND, 0) -  ISNULL(PHATSINH.GIATRICOVND, 0), 0) AS giatri 						\n" +
						"FROM ERP_TAIKHOANKT TK \n" +
						"LEFT JOIN ( \n" +
						"	SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
						"	FROM ERP_TAIKHOAN_NOCO\n" +
						" where thang = '" + lastmonth  + "' and NAM = '" + this.year  + "' " +
						"	GROUP BY TAIKHOANKT_FK \n" +
						")DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
						"LEFT JOIN( \n" +
						"	SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
						"	FROM ERP_PHATSINHKETOAN \n" +
						" 	where month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
						"	GROUP BY TAIKHOAN_FK \n" +
						")PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
						"where "+ sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) " +
						")A ";
			}

			System.out.println("GET No tru co cuoi ky: " + query);
			ResultSet rs=db.get(query);
			if(rs.next()){
				giatri = rs.getDouble("giatri");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}

	@Override
	public Double getCoTruNo(String sohieu , boolean dauki) {
		
		int lastmonth=0;
		int lastyear=0;
		
		lastyear = Integer.parseInt(this.year) - 1;
		lastmonth = 0;
				
		if (Integer.parseInt(this.month) > 1){
			lastmonth = Integer.parseInt(this.month) - 1;
		}else{
			lastmonth = 12;
		}
		
		
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i] +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan " + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = sohieutk.substring(0, index);
		
		System.out.print("Chuoi sohieutaikhoan " + sohieutk);
		
		try{
			String query ="";
			if(dauki==true)
			{
				if(lastmonth != 12){
					query = "select SUM(GIATRICOVND) - SUM(GIATRINOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +"  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) )";
				}
				else
				{
					query = "select SUM(GIATRICOVND) - SUM(GIATRINOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +"  AND CONGTY_FK IN (" + this.ErpCongTyId + " ))";
				}				
			}
			else{
				/*query = "select SUM(GIATRICOVND) - SUM(GIATRINOVND) as giatri " +
			 			"from ERP_TAIKHOAN_NOCO " +
			 			"where thang = '" + this.month + "' and NAM = '" + this.year + "' " +
			 			"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 			"where "+ sohieutk +" )";*/
				if(lastmonth != 12){
					query = "SELECT ROUND (SUM(ISNULL(DAUKY.GIATRICOVND, 0) - ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) -  ISNULL(PHATSINH.GIATRINOVND, 0)), 0) AS giatri 						\n" +
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
					"	SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
					"	FROM ERP_TAIKHOAN_NOCO\n" +
					" where thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
					"	GROUP BY TAIKHOANKT_FK \n" +
					")DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"LEFT JOIN( \n" +
					"	SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
					"	FROM ERP_PHATSINHKETOAN \n" +
					" where month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"	GROUP BY TAIKHOAN_FK \n" +
					")PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					" where "+ sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}
				else
				{
					query = "SELECT ROUND (SUM(ISNULL(DAUKY.GIATRICOVND, 0) - ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) -  ISNULL(PHATSINH.GIATRINOVND, 0)), 0) AS giatri 						\n" +
					"FROM ERP_TAIKHOANKT TK \n" +
					"LEFT JOIN ( \n" +
					"	SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
					"	FROM ERP_TAIKHOAN_NOCO\n" +
					" where thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
					"	GROUP BY TAIKHOANKT_FK \n" +
					")DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"LEFT JOIN( \n" +
					"	SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
					"	FROM ERP_PHATSINHKETOAN \n" +
					" where month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"	GROUP BY TAIKHOAN_FK \n" +
					")PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					" where " + sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}
				
			}

		System.out.println("GET DOANH SO: " + query);
		ResultSet rs=db.get(query);
		if(rs.next()){
			giatri = rs.getDouble("giatri");
		}
		rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}

	@Override
	public Double getNo(String sohieu , boolean dauki, String ctyId) {
		int lastmonth=0;
		int lastyear=0;
		
		lastyear = Integer.parseInt(this.year) - 1;
		lastmonth = 0;
			
		if (Integer.parseInt(this.month) > 1){
			lastmonth = Integer.parseInt(this.month) - 1;
		}else{
			lastmonth = 12;
		}
		
		
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i] +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan " + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = sohieutk.substring(0, index);
		
		System.out.print("Chuoi sohieutaikhoan " + sohieutk);
		
		try{
			String query ="";
			if(dauki==true)
			{
				if(lastmonth != 12){
					query = "select SUM(GIATRINOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +"  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}
				else
				{
					query = "select SUM(GIATRINOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where " + sohieutk + "  AND CONGTY_FK IN (" + this.ErpCongTyId + " ) ";
				}				
			}
			else{
				if(lastmonth != 12)
				{
					query = 
					"SELECT SUM((ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) )) as giatri						\n" +
					"FROM 	ERP_TAIKHOANKT TK \n" +
					"	  	LEFT JOIN ( \n" +
					"						SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
					"						FROM ERP_TAIKHOAN_NOCO \n" +
					"  						WHERE thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
					"						GROUP BY TAIKHOANKT_FK \n" +
					"				   )DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"		LEFT JOIN ( \n" +
					"						SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
					"						FROM ERP_PHATSINHKETOAN \n" +
					"  						WHERE month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"						GROUP BY TAIKHOAN_FK \n" +
					"					)PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					"WHERE "+ sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) " +
					"HAVING SUM (( ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) )) > 0 \n "; 
				}
				else
				{
					query = 
					"SELECT SUM((ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) )) as giatri						\n" +
					"FROM 	ERP_TAIKHOANKT TK \n" +
					"		LEFT JOIN ( \n" +
					"					SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
					"					FROM ERP_TAIKHOAN_NOCO\n" +
					"  					WHERE thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
					"					GROUP BY TAIKHOANKT_FK \n" +
					"				  )DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"		LEFT JOIN( \n" +
					"					SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
					"					FROM ERP_PHATSINHKETOAN \n" +
					"  					WHERE month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"					GROUP BY TAIKHOAN_FK \n" +
					"				)PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					"WHERE "+ sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) " +
					"HAVING SUM(( ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) )) > 0\n ";
					
				}
			}

		System.out.println("GET DOANH SO: " + query);
		ResultSet rs=db.get(query);
		if(rs.next()){
			giatri = rs.getDouble("giatri");
		}
		rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}

	
	
	
	
	
	
	// loai = 0: Tổng phát sinh Nợ
	// loai = 1: Tổng phát sinh Có
	public double getTongPhatSinh(int loai, String Sohieu, String SohieuDoiung){
		double result = 0;
		String query = "";
		String sohieutk = "";
		String[] Sohieutk = Sohieu.split(",");
		
		for(int i = 0 ; i < Sohieutk.length ; i ++)
		{
			sohieutk += "TK.SOHIEUTAIKHOAN like '"+ Sohieutk[i].trim() +"%' or ";
		}
		
		
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "( " + sohieutk.substring(0, index) + ")";
		System.out.println("Chuỗi số hiệu TK :" + sohieutk);
		String sohieutkdu = "";
		//String tmp = "PS.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + congTy + ") AND ( ";
		
		if(SohieuDoiung.length() > 0){
			String[] Sohieutkdu = SohieuDoiung.split(",");
		
			for(int i = 0 ; i < Sohieutkdu.length ; i ++)
			{
				sohieutkdu += "TK1.SOHIEUTAIKHOAN like '"+ Sohieutkdu[i].trim() +"%' or ";
			}
		
			
			index = sohieutkdu.lastIndexOf("or");
			sohieutkdu ="("+ sohieutkdu.substring(0, index) + ")";
			System.out.println("Chuỗi số hiệu TK đối ứng : " + sohieutkdu);
		}
		if(loai == 0){
			query = "SELECT SUM(ISNULL(GIATRI,0)) AS GIATRI \n"+
					"FROM ( \n"+
					"SELECT SUM(ROUND(ISNULL(PS.NO, 0),0)) AS GIATRI \n " +
					"FROM ERP_PHATSINHKETOAN PS \n " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " +
					"INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " +
					"WHERE LOAICHUNGTU != N'Đăng ký số dư' AND MONTH(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "   \n " +
					"AND PS.CONGTY_FK IN (" + this.ctyId + ") AND " + sohieutk + " \n" ;
			if(sohieutkdu.length() > 0){
				query += "AND " + sohieutkdu + " \n ";
			}
			query = query + " )PHATSINH\n ";
			
		}else{
			
			query = "SELECT SUM(ISNULL(GIATRI,0)) AS GIATRI \n"+
					"FROM ( \n"+
					"SELECT SUM(ROUND(ISNULL(PS.CO, 0),0)) AS GIATRI \n " +
					"FROM ERP_PHATSINHKETOAN PS \n " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " +
					"INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " +
					"WHERE LOAICHUNGTU != N'Đăng ký số dư' AND MONTH(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "   \n " +
					"AND PS.CONGTY_FK IN (" + this.ctyId + ") AND " + sohieutk + " \n" ;
			if(sohieutkdu.length() > 0){
				query += "AND " + sohieutkdu + " \n ";
			}
			query =  query +  " )PHATSINH\n ";
			
		}
			
		System.out.println(query);
		System.out.println("============================");
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				if(rs.next()){
					result = rs.getDouble("GIATRI");
				}
				rs.close();
			}
		}catch(java.sql.SQLException e){}
		
		return result;
	}

	// loai = 0: Tổng phát sinh Nợ
	// loai = 1: Tổng phát sinh Có
	public double getTongPhatSinh_Kytruoc(int loai, String Sohieu, String SohieuDoiung){
		int lastmonth = 0;
		int lastyear = 0;
		
		if(Integer.parseInt(this.month) == 1){
			lastmonth = 12;
			lastyear = Integer.parseInt(this.year) - 1;
		}else{
			lastmonth = Integer.parseInt(this.month) - 1;
			lastyear = Integer.parseInt(this.year);
		}
		
		double result = 0;
		String query = "";
		String sohieutk = "";
		String[] Sohieutk = Sohieu.split(",");
		
		for(int i = 0 ; i < Sohieutk.length ; i ++)
		{
			sohieutk += "TK.SOHIEUTAIKHOAN like '"+ Sohieutk[i].trim() +"%' or ";
		}
		
		System.out.println("Chuỗi số hiệu TK : " + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "(" + sohieutk.substring(0, index) + ")";
		
		String sohieutkdu = "";		
		if(SohieuDoiung.length() > 0){
			String[] Sohieutkdu = SohieuDoiung.split(",");
		
			for(int i = 0 ; i < Sohieutkdu.length ; i ++)
			{
				sohieutkdu += "TK1.SOHIEUTAIKHOAN like '"+ Sohieutkdu[i].trim() +"%' or ";
			}
		
			System.out.println("Chuỗi số hiệu TK đối ứng : " + sohieutkdu);
			index = sohieutkdu.lastIndexOf("or");
			sohieutkdu ="(" + sohieutkdu.substring(0, index) + ")";
		}
		if(loai == 0){
			
			query = "SELECT SUM(ISNULL(GIATRI,0)) AS GIATRI \n"+
					"FROM ( \n"+
					"SELECT SUM(ISNULL(ROUND(PS.NO, 0), 0)) AS GIATRI \n " +
					"FROM ERP_PHATSINHKETOAN PS \n " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " +
					"INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " +
					"WHERE LOAICHUNGTU != N'Đăng ký số dư' AND  MONTH(NGAYGHINHAN) = " + lastmonth + " and YEAR(NGAYGHINHAN) = " + lastyear + "   \n " +
					"AND PS.CONGTY_FK IN (" + this.ctyId + ") AND " + sohieutk + " \n" ;
			if(sohieutkdu.length() > 0){
				query += "AND " + sohieutkdu + " \n ";
			}
			query =  query +  " )PHATSINH\n ";
			
		}else{
			
			query = "SELECT SUM(ISNULL(GIATRI,0)) AS GIATRI \n"+
					"FROM ( \n"+
					"SELECT SUM(ISNULL(ROUND(PS.CO, 0), 0)) AS GIATRI \n " +
					"FROM ERP_PHATSINHKETOAN PS \n " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " +
					"INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " +
					"WHERE LOAICHUNGTU != N'Đăng ký số dư' AND MONTH(NGAYGHINHAN) = " + lastmonth + " and YEAR(NGAYGHINHAN) = " + lastyear + "   \n " +
					"AND PS.CONGTY_FK IN (" + this.ctyId + ") AND " + sohieutk + " \n" ;
			if(sohieutkdu.length() > 0){
				query += "AND " + sohieutkdu + " \n ";
			}
			query =  query +  " )PHATSINH\n ";
			
		}
			
		System.out.println(query);
		System.out.println("============================");
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				if(rs.next()){
					result = rs.getDouble("GIATRI");
				}
				rs.close();
			}
		}catch(java.sql.SQLException e){}
		
		return result;
	}

	@Override
	public Double getCo(String sohieu , boolean dauki, String ctyId) {
		
		int lastmonth=0;
		int lastyear=0;
		
		lastyear = Integer.parseInt(this.year) - 1;
		lastmonth = 0;
			
		if (Integer.parseInt(this.month) > 1){
			lastmonth = Integer.parseInt(this.month) - 1;
		}else{
			lastmonth = 12;
		}
		
		
		double giatri=0;
		String sohieutk="";
		String[] sohieus = sohieu.split(",");
		
		for(int i = 0 ; i < sohieus.length ; i ++)
		{
			sohieutk += "SOHIEUTAIKHOAN like '"+ sohieus[i].trim() +"%' or ";
		}
		
		System.out.print("Chuoi sohieutaikhoan " + sohieutk);
		int index = sohieutk.lastIndexOf("or");
		sohieutk = "(" + sohieutk.substring(0, index) + ")";
		
		System.out.print("Chuoi sohieutaikhoan " + sohieutk);
		
		try{
			String query ="";
			if(dauki==true)
			{
				if(lastmonth != 12){
					query = "select SUM(GIATRICOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +"  AND CONGTY_FK IN (" + this.ErpCongTyId + " )) " ;
				}
				else
				{
					query = "select SUM(GIATRICOVND) as giatri " +
			 				"from ERP_TAIKHOAN_NOCO " +
			 				"where thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
			 				"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 				"where "+ sohieutk +" AND CONGTY_FK IN (" + this.ErpCongTyId + " )) " ;
				}				
			}
			else{
				/*query = "select SUM(GIATRICOVND) as giatri " +
			 			"from ERP_TAIKHOAN_NOCO " +
			 			"where thang = '" + this.month + "' and NAM = '" + this.year + "' " +
			 			"and TAIKHOANKT_FK in ( select PK_SEQ from ERP_TAIKHOANKT " +
			 			"where "+ sohieutk +" )";*/
				if(lastmonth != 12)
				{
					query =
					"SELECT SUM((ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0)) ) as giatri						\n " +
					"FROM 	ERP_TAIKHOANKT TK \n " +
					"		LEFT JOIN ( \n " +
					"					SELECT TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
					"					FROM ERP_TAIKHOAN_NOCO\n" +
					"  					WHERE thang = '" + lastmonth + "' and NAM = '" + this.year + "' " +
					"					GROUP BY TAIKHOANKT_FK \n" +
					"				)DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"		LEFT JOIN( \n" +
					"					SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
					"					FROM ERP_PHATSINHKETOAN \n" +
					"  					WHERE month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"					GROUP BY TAIKHOAN_FK \n" +
					"				)PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					"where "+ sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) " +
					" HAVING SUM(( ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0)) ) < 0\n " ;
				}
				else
				{
					query = 
					"SELECT SUM((ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) )) as giatri						\n" +
					"FROM 	ERP_TAIKHOANKT TK \n" +
					"		LEFT JOIN ( \n" +
					"					SELECT 	TAIKHOANKT_FK AS TKID, SUM(GIATRINOVND) AS GIATRINOVND, SUM(GIATRICOVND) AS GIATRICOVND \n" +
					"					FROM 	ERP_TAIKHOAN_NOCO\n" +
					"  					WHERE 	thang = '" + lastmonth + "' and NAM = '" + lastyear + "' " +
					"					GROUP BY TAIKHOANKT_FK \n" +
					"				  )DAUKY ON DAUKY.TKID = TK.PK_SEQ \n" +
					"		LEFT JOIN( \n" +
					"					SELECT TAIKHOAN_FK AS TKID, SUM(NO) AS GIATRINOVND, SUM(CO) AS GIATRICOVND \n" +
					"					FROM ERP_PHATSINHKETOAN \n" +
					"  					WHERE month(NGAYGHINHAN) = " + this.month + " and YEAR(NGAYGHINHAN) = " + this.year + "  " +
					"					GROUP BY TAIKHOAN_FK \n" +
					"				)PHATSINH ON PHATSINH.TKID = TK.PK_SEQ \n" +
					"where "+ sohieutk + "  AND TK.CONGTY_FK IN (" + this.ErpCongTyId + " ) " +
					"HAVING SUM(( ISNULL(DAUKY.GIATRINOVND, 0) + ISNULL(PHATSINH.GIATRINOVND, 0) ) - ( ISNULL(DAUKY.GIATRICOVND, 0) + ISNULL(PHATSINH.GIATRICOVND, 0) )) < 0\n ";
				}
				
			}

		System.out.println("GET DOANH SO: " + query);
		ResultSet rs=db.get(query);
		if(rs.next()){
			giatri = rs.getDouble("giatri");
			if(giatri < 0)
			{
				giatri = giatri * (-1);
				
			}
		}
		rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return giatri;
	}


	@Override
	public String getNppId() {
		// TODO Auto-generated method stub
		return this.nppId;
	}


	@Override
	public void setNppId(String nppId) {
		// TODO Auto-generated method stub
		this.nppId = nppId;
	}


	@Override
	public void createRs() {
		// TODO Auto-generated method stub
		String sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE TRANGTHAI = 1";
		this.nppRs = db.get(sql);
	}


	@Override
	public ResultSet getNppRs() {
		// TODO Auto-generated method stub
		return this.nppRs;
	}


	@Override
	public void setNppRs(ResultSet nppRs) {
		// TODO Auto-generated method stub
		this.nppRs = nppRs;
	}


	@Override
	public ResultSet getRsCDPS() {
		// TODO Auto-generated method stub
		return this.rsCDPS;
	}


	@Override
	public ResultSet getRsCDKT() {
		// TODO Auto-generated method stub
		return this.rsCDKT;
	}
	public ResultSet getRsHDKD() {
		return rsHDKD;
	}


	public void setRsHDKD(ResultSet rsHDKD) {
		this.rsHDKD = rsHDKD;
	}
	

}
