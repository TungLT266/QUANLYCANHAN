package geso.traphaco.erp.util;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 


import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

public class LibraryKS {
	public static void main(String args[]) {
		dbutils db = new dbutils();

		try {
				/*String query="SELECT PK_SEQ   FROM NHAPHANPHOI  where loaiNPP in ('0','1','2','3') and pk_seq=1  "; 
				ResultSet rs=db.get(query);
				while(rs.next()){
				 TinhSoDuKetChuyenCuoiThangKT(1, 2017,rs.getString("PK_SEQ"), "100000", "100004",db);
				}*/
			
			  
		 
		} catch (Exception er) {
			er.printStackTrace();
		}
		Tinhcuoiky(db,"1","2017","6");

		db.shutDown();

	}

	public static String TinhSoDuKetChuyenCuoiThangKT(int month, int year, String nppid, String ctyid,String Id_curent,Idbutils db) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Utility util = new Utility();
		String msg = "";
		try {
			System.out.println("TinhSoDuCuoiThangKT");
			
			// kết chuuyển
			int lastMonth = 0;
			int lastYear = 0;
			if (month == 1) {
				lastMonth = 12;
				lastYear = year - 1;
			} else {
				lastMonth = month - 1;
				lastYear = year;
			}
			String ngaythang =year+"-"+( (month) >9?month:"0"+month);
				// xóa kết chuyển cũ đi, chạy lại, nếu nhấn vào kết chuyển tiếp
			
			 
			String query="SELECT  CONVERT (char(10),DATEADD(DAY,-1,  DATEADD (MONTH, 1, '"+ngaythang+"-01"+"')) ,126) AS NGAY ";
			ResultSet rsngay=db.get(query);
			String ngaychot="";
			if(rsngay.next()){
				ngaychot =rsngay.getString("NGAY");
			}
			rsngay.close();
			
			
			String loaichungtu = "Kết chuyển cuối tháng kế toán";
			//Revert_KeToan(loaichungtu, Id_curent, "", month, year, db, msg);
			
		    query= 		    " SELECT a.*,tk.PK_SEQ as TAIKHOAN_FK FROM ERP_CAUHINH_KETCHUYENKETOAN a "+  
							" LEFT join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN=a.TAIKHOANKT_FK   "+
							" WHERE TK.NPP_FK= "+nppid+"   ORDER BY SOTT ";
			
			
			ResultSet rs=db.get(query);
			while(rs.next()){
		 
					String taikhoan_fk= rs.getString("TAIKHOAN_FK");
					String[] param = new String[6];
					 
					param[0] = lastMonth+"";
					param[1] = lastYear+"";
					param[2] = ngaythang;
					param[3]=  taikhoan_fk ;
					param[4]=  ctyid;
					param[5]=nppid;
				 
				 
					ResultSet rsKetChuyen = db.getRsByPro("GET_KETCHUYEN_TAIKHOAN_KT", param);
				 	
				
				if (rsKetChuyen.next()) {
					String taiKhoanNo = "";
					String taiKhoanCo = "";
					if (rsKetChuyen.getDouble("GIATRICUOIKY") > 0) {
						taiKhoanNo = rsKetChuyen.getString("TAIKHOANNO_FK");
						taiKhoanCo = rsKetChuyen.getString("TAIKHOANCO_FK");
					} else {
						taiKhoanNo = rsKetChuyen.getString("TAIKHOANCO_FK");
						taiKhoanCo = rsKetChuyen.getString("TAIKHOANNO_FK");
					}
					double giaTriKetChuyen = rsKetChuyen.getDouble("GIATRICUOIKY");
					String machungtu =Id_curent;
					
					if(giaTriKetChuyen<0){
						giaTriKetChuyen=giaTriKetChuyen* (-1);
					}
					if(giaTriKetChuyen!= 0){
						
						query = " INSERT INTO ERP_CHUNGTUKETCHUYENKETOAN(KHOASOKETOANLIST_FK,NPP_FK,TAIKHOANKT_FK,GIATRI,TAIKHOANNO_FK,TAIKHOANCO_FK) VALUES "
								+ " (" + machungtu + "," + nppid + "," + rsKetChuyen.getString("TAIKHOANKT_FK") + ","
								+ giaTriKetChuyen + "," + taiKhoanNo + "," + taiKhoanCo + ")";
						System.out.println(" phat sinh ket chuyen : "+query);
						if (!db.update(query)) {
							return "Không thể thực thi dòng lệnh, vui lòng báo lại admin để được trợ giúp: " + query;
						}

						// thực hiện phát sinh kế toán
	
						String sanpham_fk = "";
						String diengiai = "Kết chuyển cuối tháng kế toán";
						
						 
						String tiente_fk = "100000";
	
						String masp = "";
						String tensp = "";
						String dvt = "";

					// DINH KHOAN KE TOAN
					
						if (taiKhoanCo.trim().length() > 0 || taiKhoanNo.trim().length() > 0) {
							double tonggiatri = giaTriKetChuyen;
							String msg1= util.Update_TaiKhoan(db, Integer.toString(month),
									Integer.toString(year), ngaychot, ngaychot, loaichungtu, machungtu, taiKhoanNo,
									taiKhoanCo, "", Double.toString(tonggiatri), Double.toString(tonggiatri), "", "", "", "", "", "0", "0", "100000","0", "1", 
									Double.toString(tonggiatri), "0", diengiai);
							 	 
							if (msg1.trim().length() > 0) {
								return msg1;
							}
	
						}
					}

				}
				
			}
	  
		} catch (Exception er) {
			er.printStackTrace();
			return er.getMessage();
		}
		return "";

		// TODO Auto-generated method stub

	}
	
	public static String Tinhcuoiky(Idbutils db,String npp_fk, String nam, String thang){
		try{
		String lastmonth = "";
		String lastyear = "";
		
		if(thang.equals("1")){
			lastmonth = "12";
			lastyear = "" + (Integer.parseInt(nam) - 1);
		}else{
			lastmonth = "" + (Integer.parseInt(thang) - 1);
			lastyear = nam;
		}
		String query = " DELETE ERP_TAIKHOAN_NOCO_KHOASO WHERE THANG = " + thang + " AND NAM = " + nam + "";
		db.update(query);
		query = "INSERT INTO ERP_TAIKHOAN_NOCO_KHOASO(TAIKHOANKT_FK, MADOITUONG, DOITUONG, NGUYENTE_FK,GIATRINONGUYENTE,GIATRICONGUYENTE, GIATRINOVND, GIATRICOVND,THANG, NAM) " +
				" SELECT TKID, MADOITUONG, DOITUONG,NTID, \n " + 
				"CASE WHEN SUM(GIATRINONT) - SUM(GIATRICONT) > 0 AND   \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
				"THEN 	SUM(GIATRINONT) - SUM(GIATRICONT)  \n " + 
				"WHEN SUM(GIATRINONT) - SUM(GIATRICONT) <= 0  AND  \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%')  \n " + 
				"THEN 0  ELSE SUM(GIATRINONT) END AS GIATRINONT,  \n " + 
				"CASE WHEN SUM(GIATRINONT) - SUM(GIATRICOVND) < 0 AND  \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' ) \n " + 
				"THEN (SUM(GIATRINONT) - SUM(GIATRICONT))*(-1)  \n " + 
				"WHEN SUM(GIATRINONT) - SUM(GIATRICONT) >= 0  AND \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
				"THEN 0 ELSE	SUM(GIATRICONT) END AS GIATRICONT, \n " + 
				" \n " + 
				" \n " + 
				"CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 AND   \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
				"THEN 	SUM(GIATRINOVND) - SUM(GIATRICOVND)  \n " + 
				"WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0  AND  \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%')  \n " + 
				"THEN 0  ELSE SUM(GIATRINOVND) END AS GIATRINOVND,  \n " + 
				"CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0 AND  \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' ) \n " + 
				"THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND))*(-1)  \n " + 
				"WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0  AND \n " + 
				"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
				"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
				"THEN 0 ELSE	SUM(GIATRICOVND) END AS GIATRICOVND , " + thang + "," + nam +"   \n " + 
				"		FROM (  \n " + 
				" 			SELECT KS.TAIKHOANKT_FK AS TKID, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG, \n " + 
				"				KS.NGUYENTE_FK AS NTID,SUM(KS.GIATRINONGUYENTE) AS GIATRINONT,SUM(KS.GIATRICONGUYENTE) AS GIATRICONT, \n " + 
				" 			SUM(KS.GIATRINOVND) AS GIATRINOVND, \n " + 
				"  			SUM(KS.GIATRICOVND) AS GIATRICOVND   \n " + 
				" 			FROM ERP_TAIKHOAN_NOCO_KHOASO KS  \n " + 
				"            INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  \n " + 
				" 			WHERE KS.THANG = "+lastmonth+" AND KS.NAM = "+lastyear+"   \n " + 
				"            AND TK.NPP_FK IN (" +npp_fk+" )  \n " + 
				" 			GROUP BY KS.TAIKHOANKT_FK, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG,KS.NGUYENTE_FK \n " + 
				"  			UNION ALL  \n " + 
				"  			SELECT PS.TAIKHOAN_FK AS TKID, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG, \n " + 
				"				PS.TIENTEGOC_FK AS NTID, \n " + 
				"				CASE WHEN SUM(ISNULL(PS.NO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRINONT, \n " + 
				"				CASE WHEN SUM(ISNULL(PS.CO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRICONT, \n " + 
				" 			SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n " + 
				"				FROM ERP_PHATSINHKETOAN PS    \n " + 
				"   			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " + 
				"        INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " + 
				" 			WHERE month(PS.NGAYGHINHAN) = "+thang+" and YEAR(PS.NGAYGHINHAN) = "+ nam +"  \n " + 
				"             AND TK.NPP_FK IN (" +npp_fk+" )  \n " + 
				" 			GROUP BY PS.TAIKHOAN_FK, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.TIENTEGOC_FK  \n " + 
				 
				"  		)DAUKY_PHATSINH_THEODOITUONG  \n " + 
				"GROUP BY DAUKY_PHATSINH_THEODOITUONG.TKID,DAUKY_PHATSINH_THEODOITUONG.MADOITUONG, \n " + 
				"DAUKY_PHATSINH_THEODOITUONG.DOITUONG,DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN, \n " + 
				"DAUKY_PHATSINH_THEODOITUONG.NTID";
		System.out.println("aaaaaaaaaaaaaaaaaa"+query);
		if(!db.update(query)){
			return "Không thể cập nhật tính tồn cuối tháng";
		}
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
			
		}
		return "";
	}

	private static boolean Revert_KeToan(String loaichungtu, String sochungtu, String idlog, int month, int year,
			Idbutils db, String msg) {
		try {

			// GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			String query = "  delete  ERP_PHATSINHKETOAN " + " where LOAICHUNGTU = N'" + loaichungtu.trim()
					+ "' and SOCHUNGTU = '" + sochungtu + "' ";

			 
				if (!db.update(query)) {
					msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
					return false;
				}
 

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}
}
