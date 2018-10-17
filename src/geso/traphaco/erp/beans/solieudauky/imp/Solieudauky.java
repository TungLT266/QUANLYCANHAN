package geso.traphaco.erp.beans.solieudauky.imp;

import java.sql.SQLException;

import geso.traphaco.erp.beans.solieudauky.ISolieudauky;
import geso.traphaco.erp.db.sql.dbutils;

public class Solieudauky implements ISolieudauky  {
	private String userId;
	private String check;
	private String thang;
	private String nam;
	private String nppId;
	private String msg;
	private dbutils db;
	public Solieudauky(){
		this.userId = "";
		this.check = "";
		this.thang = "";
		this.nam = "";
		this.nppId = "";
		this.msg = "";
		db = new dbutils();
	}
	@Override
	public void setUserId(String userId){
		this.userId = userId;
	}
	@Override
	public String getUserId(){
		return this.userId;
	}
	@Override
	public void setCheck(String check){
		this.check = check;
	}
	@Override
	public String getCheck(){
		return this.check;
	}
	@Override
	public void init(){
		//DKSD : 30-09-2016 Chỉ chạy số liệu sau tháng 9 . Dữ liệu tháng 9 trong ERP_TAIKHOAN_NOCO_KHOASO không được xóa và chỉnh sửa 
		String thangKoTinh= "";
		for (int i = 10 ; i<=12;i++){
			this.setNam("2016");
			this.setThang(Integer.toString(i));
			if(!TinhDauKy()){
				thangKoTinh = thangKoTinh + Integer.toString(i) + " , ";
			}
		}
		if(thangKoTinh.length() >0){
			this.msg = "Những tháng chưa tính số liệu đầu kỳ :" + thangKoTinh;
		}else{
			this.msg= "Tạo số liệu đầu kỳ thành công .";
		}
		
	}
	@Override
	public boolean TinhDauKy(){

		String lastmonth = "";
		String lastyear = "";
		
		if(this.thang.equals("1")){
			lastmonth = "12";
			lastyear = "" + (Integer.parseInt(this.nam) - 1);
		}else{
			lastmonth = "" + (Integer.parseInt(this.thang) - 1);
			lastyear = this.nam;
		}
		try{
			db.getConnection().setAutoCommit(false);
			String query = "DELETE ERP_TAIKHOAN_NOCO_KHOASO WHERE THANG = " + this.thang + " AND NAM = " + this.nam + "";
			db.update(query);
			query = "INSERT INTO ERP_TAIKHOAN_NOCO_KHOASO(TAIKHOANKT_FK, MADOITUONG, DOITUONG, NGUYENTE_FK,ISNPP,GIATRINONGUYENTE,GIATRICONGUYENTE, GIATRINOVND, GIATRICOVND,THANG, NAM) " +
					" SELECT TKID, MADOITUONG, DOITUONG,NTID,ISNPP, \n " + 
					"CASE WHEN SUM(GIATRINONT) - SUM(GIATRICONT) > 0 AND   \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
					"THEN 	SUM(GIATRINONT) - SUM(GIATRICONT)  \n " + 
					"WHEN SUM(GIATRINONT) - SUM(GIATRICONT) <= 0  AND  \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%')  \n " + 
					"THEN 0  ELSE SUM(GIATRINONT) END AS GIATRINONT,  \n " + 
					"CASE WHEN SUM(GIATRINONT) - SUM(GIATRICONT) < 0 AND  \n " + 
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
					"THEN 0 ELSE	SUM(GIATRICOVND) END AS GIATRICOVND , " + this.thang + "," + this.nam +"   \n " + 
					"		FROM (  \n " + 
					" 			SELECT KS.TAIKHOANKT_FK AS TKID, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG, \n " + 
					"				KS.NGUYENTE_FK AS NTID,KS.ISNPP, SUM(KS.GIATRINONGUYENTE) AS GIATRINONT,SUM(KS.GIATRICONGUYENTE) AS GIATRICONT, \n " + 
					" 			SUM(KS.GIATRINOVND) AS GIATRINOVND, \n " + 
					"  			SUM(KS.GIATRICOVND) AS GIATRICOVND   \n " + 
					" 			FROM ERP_TAIKHOAN_NOCO_KHOASO KS  \n " + 
					"            INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  \n " + 
					" 			WHERE  KS.THANG >= 9 AND KS.NAM >= 2016 AND KS.THANG = "+lastmonth+" AND KS.NAM = "+lastyear+"   \n " ; 
			if(this.nppId.length() >0)
				query +="           AND TK.NPP_FK IN (" +this.nppId+" )  \n " ; 
			query+= " 			GROUP BY KS.TAIKHOANKT_FK, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG,KS.NGUYENTE_FK,KS.ISNPP \n " + 
					"  			UNION ALL  \n " + 
					"  			SELECT PS.TAIKHOAN_FK AS TKID, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG, \n " + 
					"				PS.TIENTEGOC_FK AS NTID,PS.ISNPP, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.NO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRINONT, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.CO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRICONT, \n " + 
					" 			SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n " + 
					"				FROM ERP_PHATSINHKETOAN PS    \n " + 
					"   			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " + 
					"        INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " + 
					" 			WHERE PS.NGAYGHINHAN > '2016-09-30' AND month(PS.NGAYGHINHAN) = "+this.thang+" and YEAR(PS.NGAYGHINHAN) = "+ this.nam +"  \n " ;
			if(this.nppId.length() >0)
				query +="           AND TK.NPP_FK IN (" +this.nppId+" )  \n " ; 
			query +=" 			GROUP BY PS.TAIKHOAN_FK, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.TIENTEGOC_FK,PS.ISNPP  \n " + 
					"   			UNION ALL  \n " + 
					"  			SELECT PS.TAIKHOAN_FK AS TKID, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG, \n " + 
					"				PS.TIENTEGOC_FK AS NTID,PS.ISNPP, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.NO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRINONT, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.CO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRICONT, \n " + 
					" 			SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n " + 
					"				FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS    \n " + 
					"   			INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " + 
					"        INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " + 
					" 			WHERE PS.NGAYGHINHAN > '2016-09-30' AND month(PS.NGAYGHINHAN) = "+this.thang+" and YEAR(PS.NGAYGHINHAN) = "+ this.nam +"  \n " ;
			if(this.nppId.length() >0)
				query +="           AND TK.NPP_FK IN (" +this.nppId+" )  \n " ; 
			query +=" 			GROUP BY PS.TAIKHOAN_FK, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.TIENTEGOC_FK,PS.ISNPP \n " + 
					"  		)DAUKY_PHATSINH_THEODOITUONG  \n " + 
					"GROUP BY DAUKY_PHATSINH_THEODOITUONG.TKID,DAUKY_PHATSINH_THEODOITUONG.MADOITUONG, \n " + 
					"DAUKY_PHATSINH_THEODOITUONG.DOITUONG,DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN, \n " + 
					"DAUKY_PHATSINH_THEODOITUONG.NTID,DAUKY_PHATSINH_THEODOITUONG.ISNPP";
			System.out.println("-----Chạy lại số liệu : " + this.thang + " - " + this.nam + " ------------");
			System.out.println(query);
			db.update(query);
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception ex){
			ex.printStackTrace();
			this.msg = "Lỗi : " + ex.toString();
			try{
				db.getConnection().rollback();
				
			}catch(SQLException ex1){
				ex1.printStackTrace();
				this.msg = "Lỗi : " + ex.toString();
			}
			
			return false;
		}
		return true;
	
	}
	@Override
	public void setThang(String thang) {		
		this.thang= thang;
	}
	@Override
	public String getThang() {		
		return this.thang;
	}
	@Override
	public void setNam(String nam) {		
		this.nam = nam;
	}
	@Override
	public String getNam() {	
		return this.nam;
	}
	@Override
	public void setNPPid(String nppId) {	
		this.nppId = nppId;
	}
	@Override
	public String getNPPid() {
		return this.nppId;
	}
	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String getMsg() {
		return this.msg;
	}
	@Override
	public void DBclose() 
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
}
