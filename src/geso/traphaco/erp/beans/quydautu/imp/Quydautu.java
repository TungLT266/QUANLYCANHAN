package geso.traphaco.erp.beans.quydautu.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.quydautu.IQuydautu;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Quydautu implements IQuydautu{
	String userId;
	String ctyId;	
	String ctyTen;
	String tungay;
	String denngay;
	String diachi;
	String masothue;	
	String sodudau;

	ResultSet rs;
	ResultSet dauky;
	
	String msg;
	dbutils db;
	
	ResultSet congtyRs;
	String[] ctyIds;
	
	String ErpCongTyId;
	String view;
	
	public Quydautu() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.sodudau = "0";
		this.denngay = "";		
		this.tungay = "";

		this.msg = "";
		this.db = new dbutils();
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}
	
	public void setCtyRs(ResultSet ctyRs) {

		this.congtyRs = ctyRs;
	}

	public ResultSet getCtyRs() {

		return this.congtyRs;
	}
	
	public void setCtyIds(String[] ctyIds) {

		this.ctyIds = ctyIds;
	}

	public String[] getCtyIds() {

		return this.ctyIds;
	}
	
	public String getErpCongtyId() {
		
		return this.ErpCongTyId;
	}

	
	public void setErpCongtyId(String id) {
		
		this.ErpCongTyId=id;
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
	
	public void setTungay(String tungay) {

		this.tungay = tungay;
	}

	public String getTungay() {
		return this.tungay;	
	}

	public String getDenngay() {
		return this.denngay;			
	}

	public void setDenngay(String denngay) {

		this.denngay = denngay;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public String getSodudau() {

		return this.sodudau;
	}


	public ResultSet getData(){
		return this.rs;
	}
	
	public ResultSet getDauky(){
		return this.dauky;
	}

	public void init_0(){
		if(this.ctyIds != null){
			String tmp = "";
			for(int i = 0; i < this.ctyIds.length; i++){
				tmp += this.ctyIds[i] + ",";
			}
			this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
			
			System.out.println("Công ty: " + this.ErpCongTyId);
		}else{
			
			String tmp = "";
			ResultSet rs = this.db.get("SELECT PK_SEQ, TEN FROM ERP_CONGTY WHERE isTongCongTy = 0 AND TRANGTHAI = 1");
			try{
				while(rs.next()){
					tmp += rs.getString("PK_SEQ") + ",";
				}
				
				this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
				
			}catch(java.sql.SQLException e){}
		}
		
		String sql = "SELECT PK_SEQ, TEN FROM ERP_CONGTY WHERE isTongCongTy = 0 AND TRANGTHAI = 1";
		this.congtyRs = db.get(sql);	
	}


	public void init(){
		init_0();
		String query;
		ResultSet rs;
		try{
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ this.tungay + "'), 0)) AS NGAY";
			rs = this.db.get(query);			
			rs.next();
	
			String  thang = "" + (Integer.parseInt(rs.getString("NGAY").substring(5, 7)));
			String nam = "" + (Integer.parseInt(rs.getString("NGAY").substring(0, 4)));
			rs.close();
			query = 	"SELECT SUM(DAUKY) AS DAUKY  \n" +
						"FROM (  \n" +
						"	SELECT SUM(GIATRINOVND-GIATRICOVND) AS DAUKY \n" +
						"	FROM ERP_TAIKHOAN_NOCO \n" +
						"	WHERE THANG = '" + thang + "' AND NAM = '" + nam + "' AND \n" +
						"	TAIKHOANKT_FK IN \n" +
											
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +
				
						"   AND TAIKHOANKT_FK IN \n" +
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ErpCongTyId + ")  ) \n" +

						
						"	UNION \n" +
				
						"	SELECT ISNULL(SUM(TONGGIATRI), 0) AS DAUKY \n" + 
						"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
						"	WHERE TAIKHOAN_FK IN \n" +
						"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +  
						
						"   AND TAIKHOAN_FK IN \n" +
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ErpCongTyId + ")  ) \n" +

						"	AND PSTK.NGAYGHINHAN >= '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "' \n" +
						"	AND PSTK.LOAICHUNGTU LIKE N'Thu tiền%' \n" +  
				
						"	UNION  \n" +
				
						"	SELECT (-1)*ISNULL(SUM(TONGGIATRI), 0) AS DAUKY \n" +
						"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
						"	WHERE TAIKHOAN_FK IN \n" +
						"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +  
						
						"   AND TAIKHOAN_FK IN \n" +
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ErpCongTyId + ")  ) \n" +
						
						"	AND PSTK.NGAYGHINHAN >=  '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "'  \n" +
						"	AND PSTK.LOAICHUNGTU LIKE N'Thanh toán%' \n" +  
						
						"	UNION  \n" +

						"	SELECT (-1)*ISNULL(SUM(TONGGIATRI), 0) AS DAUKY \n" +
						"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
						"	WHERE TAIKHOAN_FK IN \n" +
						"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +  
						
						"   AND TAIKHOAN_FK IN \n" +
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ErpCongTyId + ")  ) \n" +
						
						"	AND PSTK.NGAYGHINHAN >=  '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "'  \n" +
						"	AND PSTK.LOAICHUNGTU LIKE N'Trả khác%' \n" +  
						
						")DAUKY \n";
		
			System.out.println("dau ky:" + query);
			rs = this.db.get(query);
			rs.next();
			this.sodudau = rs.getString("DAUKY");
			rs.close();
			
/*			this.dauky = this.db.get("SELECT DAUKY_THU - DAUKY_CHI AS DAUKY " +
								 	 "FROM ( " +
								 	 "	SELECT ISNULL(SUM(TONGGIATRI), 0) AS DAUKY_THU, 0 AS DAUKY_CHI " +
								 	 "	FROM ERP_PHATSINHKETOAN PSTK " +
								 	 "	WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%') " +
								 	 "  AND PSTK.NGAYGHINHAN < '" + this.tungay + "' AND PSTK.LOAICHUNGTU LIKE N'Thu tiền%' " +
								 	 "UNION " +
								 	 "SELECT 0 AS DAUKYTHU, ISNULL(SUM(TONGGIATRI), 0) AS DAUKY_CHI " +
								 	 "FROM ERP_PHATSINHKETOAN PSTK " +
								 	 "WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%') " +
								 	 "AND PSTK.NGAYGHINHAN < '" + this.tungay + "' AND PSTK.LOAICHUNGTU LIKE N'Thanh toán%' " +
								 	 ")DAUKY ");*/
		
			rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
			System.out.println("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		
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
		
		
		if(this.tungay.length() > 0 & this.denngay.length() > 0){
						
			
			query = " SELECT  PHATSINH.NGAYCHUNGTU NGAY,  \n"+
					" CASE WHEN PHATSINH.NO > 0 THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE NULL END AS SOCHUNGTU_THU, \n"+
					" CASE WHEN PHATSINH.CO > 0 THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE NULL END AS SOCHUNGTU_CHI, \n"+
					" CASE WHEN PHATSINH.NO > 0 THEN PHATSINH.DIENGIAI ELSE NULL END AS DIENGIAI_THU, \n"+
					" CASE WHEN PHATSINH.CO > 0 THEN PHATSINH.DIENGIAI END AS DIENGIAI_CHI, \n"+
				    " TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, ISNULL(PHATSINH.NO, 0) AS THU, ISNULL(PHATSINH.CO, 0) AS CHI \n"+
			 		 
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 \n"+
					" AND TAIKHOAN.CONGTY_FK IN (" + this.ErpCongTyId + ") \n"+ 
					" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ErpCongTyId + ") \n"+ 
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND CONGTY_FK IN ("+ErpCongTyId+" )) \n"+
					" ORDER BY PHATSINH.NGAYCHUNGTU \n";
			
			
			System.out.println(query);
			this.rs = this.db.get(query);
		}
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) db.shutDown();
	}

}
