package geso.traphaco.erp.beans.tienguinganhang.imp;
import geso.traphaco.erp.beans.tienguinganhang.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class Sotienguinganhang implements ISotienguinganhang {
	String userId;
	String ctyId;	
	String ctyTen;
	String tungay;
	String denngay;
	String diachi;
	String masothue;	
	String sodudau;
	String nganhang;
	String sotaikhoan;
	String tiente;
	String chiNhanh;
	String chiNhanhTen;
	String chiNhanhMaSoThue;
	String chiNhanhDiaChi;
	
	ResultSet rs;
	ResultSet dauky;
	ResultSet tk;
	
	String tkId;
	String msg;
	dbutils db;
	ResultSet congtyRs;
	String[] ctyIds;
	ResultSet chiNhanhRs;
	
	String ErpCongTyId;
	String view;
	
	public Sotienguinganhang() {
		this.userId = "";
		this.ctyId = "100005";
		this.ctyTen = "";
		this.sodudau = "0";
		this.denngay = "";		
		this.tungay = "";
		this.tkId = "";
		this.nganhang = "";
		this.sotaikhoan = "";
		this.tiente = "";	
		
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
	
	public String getNganhang() {

		return this.nganhang;
	}

	public String getSoTaikhoan() {

		return this.sotaikhoan;
	}

	public String getTiente() {

		return this.tiente;
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

	public void setTkId(String tkId) {

		this.tkId = tkId;
	}

	public String getTkId() {

		return this.tkId;
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

	public ResultSet getTaikhoan(){
		return this.tk;
	}
	
	public void init_0(){
		/*if(this.ctyIds != null){
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
		this.congtyRs = db.get(sql);*/	

		String query = "SELECT PK_SEQ, TEN\r\n" + 
				"FROM NHAPHANPHOI \r\n" + 
				"WHERE ISKHACHHANG = '0'\r\n" + 
				"  AND LOAINPP IN (0, 1, 2, 3)";
		System.out.println(query);
		this.chiNhanhRs = this.db.get(query);
	}

	public void init(){
		init_0();
		String query;
		query = "SELECT TK.PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN, NH_CTY.SOTAIKHOAN, NH.TEN " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
				"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = NH_CTY.TAIKHOAN_FK " +
				"WHERE TK.NPP_FK = "+this.chiNhanh+""+
				//"WHERE NH_CTY.CONGTY_FK IN ( " + this.ctyId + " )" +
				"ORDER BY SOHIEUTAIKHOAN";
		System.out.println(query);
		this.tk = this.db.get(query);
		
		if(this.tkId.length() > 0){
			query = "SELECT TK.PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN, NH_CTY.SOTAIKHOAN, NH.TEN AS NGANHANG, TT.TEN AS TIENTE " +
					"FROM ERP_NGANHANG_CONGTY NH_CTY " +
					"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = NH_CTY.TAIKHOAN_FK " +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " +
					"WHERE TK.PK_SEQ = " + this.tkId;
			System.out.println(query);
			ResultSet tmp = this.db.get(query);			
			try{
				if(tmp != null) {
					tmp.next();
					this.nganhang = tmp.getString("NGANHANG");
					this.sotaikhoan = tmp.getString("SOTAIKHOAN");						
					this.tiente = tmp.getString("TIENTE");
				
					tmp.close();
				}
				
			}catch(java.sql.SQLException e){}
			
		}
		

		ResultSet rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		System.out.println("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
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
		
		
		if(this.tungay.length() > 0 & this.denngay.length() > 0 & this.tkId.length() > 0){

			try{
				query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ this.tungay + "'), 0)) AS NGAY";
				rs = this.db.get(query);			
				rs.next();
				Utility util = new Utility();
				String ngayDuaSoDu=util.getNgayKhoaSo(db, this.tungay);
				String  thang = "" + (Integer.parseInt(rs.getString("NGAY").substring(5, 7)));
				String nam = "" + (Integer.parseInt(rs.getString("NGAY").substring(0, 4)));
				rs.close();
				/*query = 	"SELECT SUM(DAUKY) AS DAUKY  \n" +
							"FROM (  \n" +
							"	SELECT SUM(ISNULL(GIATRINOVND, 0) - ISNULL(GIATRICOVND, 0)) AS DAUKY \n" +
							"	FROM ERP_TAIKHOAN_NOCO \n" +
							"	WHERE THANG = '" + thang + "' AND NAM = '" + nam + "' AND \n" +
							"	TAIKHOANKT_FK = " + this.tkId + " \n" + 
		
							"	UNION \n" +
		
							"	SELECT ISNULL(SUM(ISNULL(TONGGIATRI, 0)), 0) AS DAUKY \n" + 
							"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
							"	WHERE TAIKHOAN_FK = " + this.tkId + " \n" +  
							"	AND PSTK.NGAYGHINHAN >= '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "' \n" +
		
							"	UNION  \n" +
		
							"	SELECT (-1)*ISNULL(SUM(ISNULL(TONGGIATRI, 0)), 0) AS DAUKY \n" +
							"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
							"	WHERE TAIKHOAN_FK = " + this.tkId + " \n" +  
							"	AND PSTK.NGAYGHINHAN >=  '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "'  \n" +
							")DAUKY ";*/
				
				query = "SELECT SUM( ISNULL(DATA.DAUKY,0)) AS DAUKY FROM ( \r\n" + 
						"SELECT GIATRINOVND - GIATRICOVND AS DAUKY \r\n" + 
						"FROM ERP_TAIKHOAN_NOCO_KHOASO KS\r\n" + 
						"WHERE TAIKHOANKT_FK = "+this.tkId+"\r\n" + 
						"AND THANG = Month('"+ngayDuaSoDu+"') AND NAM = YEAR('"+ngayDuaSoDu+"') ";
				
				query+=	" UNION ALL " +
						" SELECT SUM(NO) - SUM(CO) DAUKY FROM ERP_PHATSINHKETOAN WHERE TAIKHOAN_FK =  \n" + 
						"	"+this.tkId+"  " +
						" AND NGAYGHINHAN <'"+this.tungay+"'  " +
						" AND NGAYGHINHAN > '"+ngayDuaSoDu+"' " +
				
						" UNION ALL " +
						" SELECT SUM(NO) - SUM(CO) DAUKY FROM "+util.prefixDMS+"ERP_PHATSINHKETOAN WHERE TAIKHOAN_FK =  \n" + 
						"	"+this.tkId+"  " +
						" AND NGAYGHINHAN <'"+this.tungay+"'  " +
						" AND NGAYGHINHAN > '"+ngayDuaSoDu+"' " ;
				query+= " ) data " ;

				System.out.println("dau ky:" + query);
				rs = this.db.get(query);
				rs.next();
				this.sodudau = rs.getString("DAUKY");
				rs.close();

			}catch(java.sql.SQLException e){e.printStackTrace();}
			
			query = " SELECT * \n"+
					" FROM ( \n"+
					" SELECT  TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN,PHATSINH.NGAYCHUNGTU NGAY,  \n"+
					" ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU)  SOCHUNGTU_THU, \n"+
					" '' SOCHUNGTU_CHI, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI_THU, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI_CHI,  \n"+
					" TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI,1 AS STT \n"+
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 AND (PHATSINH.NO > 0 OR PHATSINH.CO > 0) \n"+
					//" AND TAIKHOAN.CONGTY_FK IN ("+ this.ctyId + ")  \n"+
					//" AND TAIKHOAN_2.CONGTY_FK IN ("+ this.ctyId + ")  \n"+
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK = "+ this.tkId +
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN "+
	
					" UNION ALL \n"+
	
					" SELECT  TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN,PHATSINH.NGAYCHUNGTU NGAY, \n"+  
					" '' SOCHUNGTU_THU, ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) SOCHUNGTU_CHI, '' DIENGIAI_THU, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI_CHI, \n"+
					" TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI,2 AS STT \n"+
					 
					" FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 AND (PHATSINH.NO > 0 OR PHATSINH.CO > 0) \n"+
					//" AND TAIKHOAN.CONGTY_FK IN ("+ this.ctyId + ")  \n"+
					//" AND TAIKHOAN_2.CONGTY_FK IN ("+ this.ctyId + ") \n"+  
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK = "+ this.tkId +
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN "+
					" ) A "+
					" ORDER BY A.NGAY,A.STT ASC,A.SOCHUNGTU_THU,A.SOCHUNGTU_CHI \n";
				
		
			/*query = " SELECT  PHATSINH.NGAYCHUNGTU NGAY,  \n"+
					" CASE WHEN PHATSINH.NO > 0 THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE NULL END AS SOCHUNGTU_THU, \n"+
					" CASE WHEN PHATSINH.CO > 0 THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE NULL END AS SOCHUNGTU_CHI, \n"+
					" CASE WHEN PHATSINH.NO > 0 THEN PHATSINH.DIENGIAI ELSE NULL END AS DIENGIAI_THU, \n"+
					" CASE WHEN PHATSINH.CO > 0 THEN PHATSINH.DIENGIAI END AS DIENGIAI_CHI, \n"+
				    " TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, ISNULL(PHATSINH.NO, 0) AS THU, ISNULL(PHATSINH.CO, 0) AS CHI \n"+
			 		 
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 \n"+
					" AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ") \n"+ 
					" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") \n"+ 
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK = " + this.tkId + " \n" +
					" ORDER BY PHATSINH.NGAYCHUNGTU \n";*/

			System.out.println("bao cao "+query);
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
		try{
			if(tk != null) tk.close();
			if(db != null) db.shutDown();
		}catch(java.sql.SQLException e){}
		
	}

	public String getChiNhanh() {
		return chiNhanh;
	}

	public void setChiNhanh(String chiNhanh) {
		this.chiNhanh = chiNhanh;
	}

	public String getChiNhanhTen() {
		return chiNhanhTen;
	}

	public void setChiNhanhTen(String chiNhanhTen) {
		this.chiNhanhTen = chiNhanhTen;
	}

	public String getChiNhanhMaSoThue() {
		return chiNhanhMaSoThue;
	}

	public void setChiNhanhMaSoThue(String chiNhanhMaSoThue) {
		this.chiNhanhMaSoThue = chiNhanhMaSoThue;
	}

	public String getChiNhanhDiaChi() {
		return chiNhanhDiaChi;
	}

	public void setChiNhanhDiaChi(String chiNhanhDiaChi) {
		this.chiNhanhDiaChi = chiNhanhDiaChi;
	}

	public ResultSet getChiNhanhRs() {
		return chiNhanhRs;
	}

	public void setChiNhanhRs(ResultSet chiNhanhRs) {
		this.chiNhanhRs = chiNhanhRs;
	}
}