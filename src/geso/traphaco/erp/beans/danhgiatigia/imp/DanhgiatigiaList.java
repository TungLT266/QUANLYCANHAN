package geso.traphaco.erp.beans.danhgiatigia.imp;

import geso.traphaco.erp.beans.danhgiatigia.IDanhgiatigiaList;
import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.util.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class DanhgiatigiaList implements IDanhgiatigiaList 
{
	String userId;
	String Id = "";
	String ctyId;
	String nam;
	ResultSet namlist;	
	String dienGiaiCT;
	String quy;
	ResultSet quylist;	
	ResultSet dgtigialist;
	String ghidao;
	ResultSet tienteRS;
	String ngaychungtu;
	String trangthai = "";
	String[] ttIds;
	String[] tigia;
	Hashtable<String, String> ht;
	String msg;
	String action = "";
	dbutils db;
	public DanhgiatigiaList(){
		this.userId = "";
		this.ctyId = "";
		this.nam = "";
		this.quy = "";
		this.msg = "";
		this.ghidao = "";
		this.ngaychungtu = "";
		this.db = new dbutils();
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}

	public void setNam(String nam){
		this.nam = nam;
	}
	
	public String getNam(){
		return this.nam;
	}
	
	public void setNamRS(ResultSet namlist){
		this.namlist = namlist;
	}
	
	public ResultSet getNamRS(){
		return this.namlist;
	}

	public void setQuyRS(ResultSet quyRS){
		this.quylist = quyRS;
	}
	
	public ResultSet getQuyRS(){
		return this.quylist;
	}

	public void setTienteRS(ResultSet tienteRS){
		this.tienteRS = tienteRS;
	}
	
	public ResultSet getTienteRS(){
		return this.tienteRS;
	}

	public void setDanhgiaRS(ResultSet dgtigialist){
		this.dgtigialist = dgtigialist;
	}
	
	public ResultSet getDanhgiaRS(){
		return this.dgtigialist;
	}

	public void setQuy(String quy){
		this.quy = quy;
	}
	
	public String getQuy(){
		return this.quy;
	}

	public void setAction(String action){
		this.action = action;
	}
	
	public String getAction(){
		return this.action;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return this.msg;
	}

	private void createNamList(){
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		String query = "";
		if(nam == 2016){
			for(int i = nam; i <= nam + 1; i++)
			{
				query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
				if(i < nam + 1)
				{
					query += " union all ";
				}
			}
		}else{
			for(int i = nam - 1; i <= nam + 1; i++)
			{
				query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
				if(i < nam + 1)
				{
					query += " union all ";
				}
			}			
		}
		System.out.println(query);
		this.namlist = this.db.get(query);

	}

	private void createQuylist(){
				
		String query = "";
		for(int i = 1; i <= 4; i++)
		{
			query += "select " + i + " as quy, N'Quý " + i + "' as quyTen ";
			if(i < 4)
				query += " union all ";
		}
		System.out.println(query);
		
		this.quylist = this.db.get(query);

	}
	
	public void setGhidao(String ghidao){
		this.ghidao = ghidao;
	}
	
	public String getGhidao(){
		return this.ghidao;
	}

	public void setNgaychungtu(String ngaychungtu){
		this.ngaychungtu = ngaychungtu;
	}
	
	public String getNgaychungtu(){
		return this.ngaychungtu;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	public String getTrangthai(){
		return this.trangthai;
	}

	public void setTienteIds(String[] ttIds){
		this.ttIds = ttIds;
	}
	
	public String[] getTienteIds(){
		return this.ttIds;
	}

	public void setTigia(String[] tigia){
		this.tigia = tigia;
	}
	
	public String[] getTigia(){
		return this.tigia;
	}

	public void setTigiaHashtable(Hashtable<String, String> ht){
		this.ht = ht;
	}
	
	public Hashtable<String, String> getTigiaHashtable(){
		return this.ht;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void Chot(){
		String query = "";
		Utility util = new Utility();
		// CHEN VAO BANG ERP_PHATSINHKETOAN
		String ngayghinhan = "";
		String ngayghinhan_dao = "";
		String thang = "";
		try{
			this.db.getConnection().setAutoCommit(false);
			if(this.nam.length() > 0 & this.quy.length() > 0){
				if(quy.equals("1")){
					ngayghinhan = nam + "-03-31";
					ngayghinhan_dao = nam + "-04-01";
					thang = "3";
				}

				if(quy.equals("2")){
					ngayghinhan = nam + "-06-30";
					ngayghinhan_dao = nam + "-07-01";
					thang = "6";
				}

				if(quy.equals("3")){
					ngayghinhan = nam + "-09-30";
					ngayghinhan_dao = nam + "-10-01";
					thang = "9";
				}

				if(quy.equals("4")){
					ngayghinhan = nam + "-12-31";
					ngayghinhan_dao = "" + (Integer.parseInt(nam) + 1) + "-01-01";
					thang = "12";
				}
				
				query = "DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Đánh giá tỉ giá' AND NGAYGHINHAN = '" + ngayghinhan + "' " +
						"AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK = " + this.ctyId + ") ";

				this.db.update(query);
				
				query = "SELECT DGTG.PK_SEQ AS ID, DGTG.NGAYCHUNGTU, DGTG.NAM, DGTG.QUY, N'Đánh giá tỉ giá' AS LOAICHUNGTU, \n " +
						"DGTG_CT.MADOITUONG, DGTG_CT.DOITUONG, DGTG_CT.DOITUONG_PSKT, DGTG_CT.TAIKHOAN_FK, DGTG_CT.TIENTE_FK, DGTG_CT.CHENHLECH, \n " +
						"CASE WHEN CHENHLECH > 0  \n " +
						"	THEN DGTG_CT.TAIKHOAN_FK  \n " +
						"	ELSE (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63580000' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"END AS TK_NO, \n " +
						
						"CASE WHEN CHENHLECH > 0 \n " + 
						"	THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51530000' AND CONGTY_FK = 100000) \n " +
						"	ELSE DGTG_CT.TAIKHOAN_FK  \n " +
						"END AS TK_CO, \n " +
						
						"(SELECT TOP 1 TIGIADANHGIA FROM ERP_DANHGIATIGIA_TIGIA " +
						" WHERE DANHGIATIGIA_FK = DGTG.PK_SEQ AND TIENTE_FK = DGTG_CT.TIENTE_FK) AS TIGIA \n " +
						
						"FROM ERP_DANHGIATIGIA DGTG \n " +
						"INNER JOIN ERP_DANHGIATIGIA_CHITIET DGTG_CT ON DGTG.PK_SEQ = DGTG_CT.DANHGIATIGIA_FK \n " +
						"WHERE DGTG.NAM = " + this.nam + " AND QUY = " + this.quy + " \n " ;
				
				ResultSet rs = this.db.get(query);
				if(rs != null){
					while(rs.next()){
						String loaichungtu = rs.getString("LOAICHUNGTU");
						String sochungtu = rs.getString("ID");
						String taikhoanNO_fk = rs.getString("TK_NO");
						String taikhoanCO_fk = rs.getString("TK_CO");
						String sotien = rs.getString("CHENHLECH");
						String DOITUONG_NO = rs.getString("DOITUONG_PSKT");
						String MADOITUONG_NO = rs.getString("MADOITUONG");
						String DOITUONG_CO = rs.getString("DOITUONG_PSKT");
						String MADOITUONG_CO = rs.getString("MADOITUONG");
						String TIENTEGOC_FK = rs.getString("TIENTE_FK");
						String TIGIA_FK = rs.getString("TIGIA");
						String TONGGIATRI = sotien;
						
						util.Update_TaiKhoan_KeToan(db, thang, this.nam, ngayghinhan, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
								"NULL", sotien, sotien, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, "0", "NULL", "NULL", 
								TIENTEGOC_FK, "NULL", TIGIA_FK, TONGGIATRI, "0", "NULL", "0", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL");
						
						if(this.ghidao.equals("1")){
							util.Update_TaiKhoan_KeToan(db, thang, this.nam, ngayghinhan_dao, ngayghinhan_dao, loaichungtu, sochungtu, taikhoanCO_fk, taikhoanNO_fk, 
									"NULL", sotien, sotien, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, "0", "NULL", "NULL", 
									TIENTEGOC_FK, "NULL", TIGIA_FK, TONGGIATRI, "0", "NULL", "0", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL");
							
						}
					}
				}
				
			}
			
			query = "UPDATE ERP_DANHGIATIGIA SET TRANGTHAI = 1 WHERE NAM = " + this.nam + " AND QUY = " + this.quy ;
			this.db.update(query);

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}
	
	public void init(){
		
		String query = "";
		this.createNamList();
		this.createQuylist();
		this.Id = "";
		
		if(this.nam.length() > 0 & this.quy.length() > 0){
			query = "SELECT PK_SEQ AS ID, NAM, QUY, GHIDAO, NGAYCHUNGTU, TRANGTHAI,ISNULL(DIENGIAI,'') AS DIENGIAI \n " +
					"FROM ERP_DANHGIATIGIA  \n " +
					"WHERE NAM = " + nam + " AND QUY = " + quy + " AND CONGTY_FK = " + this.ctyId + "";
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
					if(rs.next()){
						this.Id = rs.getString("ID");
						this.ghidao = rs.getString("GHIDAO");
						this.ngaychungtu = rs.getString("NGAYCHUNGTU");
						this.trangthai = rs.getString("TRANGTHAI");
						this.dienGiaiCT = rs.getString("DIENGIAI");
						rs.close();
					}
				}
				
				
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		
		}else{
			query = "SELECT TOP 1 PK_SEQ AS ID, NAM, QUY, GHIDAO, NGAYCHUNGTU, TRANGTHAI,ISNULL(DIENGIAI,'') AS DIENGIAI \n " +
					"FROM ERP_DANHGIATIGIA  \n " +
					"ORDER BY NAM + QUY DESC";
			System.out.println("query: " + query);
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
					if (rs.next()) {
						this.Id = rs.getString("ID");
						this.ghidao = rs.getString("GHIDAO");
						this.ngaychungtu = rs.getString("NGAYCHUNGTU");
						this.quy = rs.getString("QUY");
						this.nam = rs.getString("NAM");
						this.trangthai = rs.getString("TRANGTHAI");
						this.dienGiaiCT = rs.getString("DIENGIAI");
					}
					rs.close();
				}
				
				
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
			
		}

		if(this.Id.length() > 0){
			query = "SELECT DISTINCT TT.PK_SEQ AS ID, TT.MA + ' - ' + TT.TEN AS TIENTE, TG.TIGIADANHGIA AS TIGIA \n " +
					"FROM ERP_DANHGIATIGIA_TIGIA TG \n " +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = TG.TIENTE_FK \n " +
					"WHERE DANHGIATIGIA_FK = " + this.Id + " " +
					"ORDER BY TIENTE ";
			this.tienteRS = this.db.get(query);
		
			query = "SELECT SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3) AS SOHIEU, TT.MA AS TIENTE, SUM(SODUNGOAITE) AS SODUNGOAITE, \n " +
					"SUM(SODU_TRUOC_DG) AS SODU_TRUOC_DG, SUM(SODU_SAU_DG) AS SODU_SAU_DG, SUM(CHENHLECH) AS CHENHLECH \n " +
					"FROM ERP_DANHGIATIGIA_CHITIET DGTG_CT \n " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = DGTG_CT.TAIKHOAN_FK \n " +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = DGTG_CT.TIENTE_FK \n " +
					"WHERE DGTG_CT.DANHGIATIGIA_FK = " + this.Id + " \n " +
					"GROUP BY SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3), TT.MA \n " +
					"ORDER BY SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3)";
			System.out.println("Query đánh giá tỉ giá :" + query);
			this.dgtigialist = this.db.get(query);
		}else{
		
			init_new();
		}
	}
	
	public boolean checkTigia(){
		boolean kq = true;
		if(this.tigia != null){
			for (int i = 0; i < this.tigia.length; i++){
				if(this.tigia[i].trim().length() == 0){
					kq = false;
					this.msg = "Vui lòng nhập tỉ giá cho tất cả ngoại tệ .";
				}
			}
		}else{
			kq = false;
		}
		return kq;
	}
	
	public void init_new(){
		String query = 	"";
		this.createNamList();
		this.createQuylist();
		this.ht = this.createTigiaHashtable();
		
		if(this.ttIds == null){
			query = "SELECT DISTINCT PK_SEQ AS ID, MA + ' - ' + TEN AS TIENTE, '' AS TIGIA \n " +
					"FROM ERP_TIENTE  \n " +
					"WHERE PK_SEQ <> 100000 \n " +
					"ORDER BY MA + ' - ' + TEN";
		}else{
			query = "";
			for(int i = 0; i < this.ttIds.length; i++){
				if(query.length() == 0){
					query = "SELECT " + this.ttIds[i] + " AS ID, " +
							"(SELECT MA + ' - ' + TEN FROM ERP_TIENTE WHERE PK_SEQ = " + this.ttIds[i] + ") AS TIENTE, \n " +
							"" + this.tigia[i].replaceAll(",", "") + " AS TIGIA \n " ;
				}else{
					query += "UNION ALL \n " +
							"SELECT " + this.ttIds[i] + " AS ID, (SELECT MA + ' - ' + TEN FROM ERP_TIENTE WHERE PK_SEQ = " + this.ttIds[i] + ") AS TIENTE, \n " +
							"" + this.tigia[i].replaceAll(",", "") + " AS TIGIA \n " ;
				}
			}
		}
		System.out.println("tiền tệ query:"+ query);
		this.tienteRS = this.db.get(query);

		if(this.quy.length() > 0 & this.nam.length() > 0){
			String thangdk = "";
			String namdk = "";
			String ngaybd = "";
			String ngaykt = "";
			
			if(quy.equals("1")){
				thangdk = "12";
				namdk = "" + (Integer.parseInt(nam) - 1);
				ngaybd = nam + "-01-01";
				ngaykt = nam + "-03-31";
			}

			if(quy.equals("2")){
				thangdk = "3";
				namdk = nam;
				ngaybd = nam + "-04-01";
				ngaykt = nam + "-06-30";
			}

			if(quy.equals("3")){
				thangdk = "6";
				namdk = nam;
				ngaybd = nam + "-07-01";
				ngaykt = nam + "-09-31";
			}

			if(quy.equals("4")){
				thangdk = "9";
				namdk = nam;
				ngaybd = nam + "-10-01";
				ngaykt = nam + "-12-31";
			}
			
			if(this.checkTigia()){
				query = 	"SELECT SOHIEU, TIENTE, TTID, \n " + 
							"CASE WHEN (SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE) > 0) THEN (SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE)) \n " +
							"ELSE (-1)*(SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE)) END AS SODUNGOAITE, \n " +
					
							"CASE WHEN (SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0) THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) \n " +
							"ELSE (-1)*(SUM(GIATRINOVND) - SUM(GIATRICOVND)) END AS SODUVND_TRUOCDG " +
	
							"FROM \n " +
							"( \n " +
					
							"	SELECT SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3) AS SOHIEU, MADOITUONG, \n " + 
							"	CASE WHEN DOITUONG = N'Sản phẩm' THEN (SELECT TEN FROM ERP_SANPHAM WHERE CONVERT(VARCHAR, PK_SEQ) = MADOITUONG) \n " +
							"	ELSE CASE WHEN DOITUONG = N'Nhà cung cấp' THEN (SELECT TEN FROM ERP_NHACUNGCAP WHERE CONVERT(VARCHAR, PK_SEQ) = MADOITUONG) \n " +
							"	ELSE DOITUONG END END AS DOITUONG, \n " +
					
							"	(SELECT MA FROM ERP_TIENTE WHERE PK_SEQ = TTID) AS TIENTE, TTID, \n " + 
									 
							"	CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0  \n " +
							"	THEN SUM(GIATRINOVND) - SUM(GIATRICOVND) \n " +
							"	ELSE 0 END AS GIATRINOVND,  \n " +
									 
							"	CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0 \n " + 
							"	THEN SUM(GIATRICOVND) - SUM(GIATRINOVND) \n " +
							"	ELSE 0 END AS GIATRICOVND,  \n " +
					
							"	CASE WHEN SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE) > 0 \n " + 
							"	THEN SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE) \n " +
							"	ELSE 0 END AS GIATRINONGUYENTE,  \n " +
									 
							"	CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICONGUYENTE) < 0 \n " + 
							"	THEN SUM(GIATRICONGUYENTE) - SUM(GIATRINONGUYENTE) \n " +
							"	ELSE 0 END AS GIATRICONGUYENTE  \n " +
							"	FROM    \n " +
							"	(   \n " +
							"		SELECT TAIKHOANKT_FK AS TKID, NGUYENTE_FK AS TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +   
							"		GIATRINOVND, GIATRICOVND, GIATRINONGUYENTE, GIATRICONGUYENTE   \n " +
							"		FROM ERP_TAIKHOAN_NOCO_KHOASO   \n " +
							"		WHERE THANG =  " + thangdk + "  AND NAM =  " + namdk + "  \n " +
							" 	    AND (GIATRINONGUYENTE > 0 OR GIATRICONGUYENTE > 0) AND NGUYENTE_FK <> 100000 \n " +
							 
							"		UNION ALL   \n " +
													
							"		SELECT TKID, TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +   
					
							"		CASE WHEN SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0)) > 0   \n " +
							"		THEN SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0))   \n " +
							"		ELSE 0 END AS NO,   \n " +
									 
							"		CASE WHEN SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0)) < 0 \n " +  
							"		THEN (-1)*(SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0)))   \n " +
							"		ELSE 0 END AS CO,   \n " +
									
							"		CASE WHEN SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0)) > 0 \n " +  
							"		THEN SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0))   \n " +
							"		ELSE 0 END AS NONT,   \n " +
									 
							"		CASE WHEN SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0)) < 0 \n " +  
							"		THEN (-1)*(SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0)))   \n " +
							"		ELSE 0 END AS CONT   \n " +
					
							"		FROM (   \n " +
							"			SELECT TAIKHOAN_FK AS TKID, TIENTEGOC_FK AS TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +  
							"			NO, CO,   \n " +
							"			CASE WHEN NO > 0 THEN TONGGIATRINT ELSE 0 END AS NONT, \n " +  
							"			CASE WHEN CO > 0 THEN TONGGIATRINT ELSE 0 END AS CONT   \n " +
					
							"			FROM ERP_PHATSINHKETOAN    \n " +
							"			WHERE   \n " +
							"			NGAYGHINHAN >= '" + ngaybd + "' \n " +  
							"			AND NGAYGHINHAN <= '" + ngaykt + "'  \n " +
							" 			AND TONGGIATRINT > 0 AND TIENTEGOC_FK <> 100000 " +
							 
							"			UNION ALL \n " +
							"			SELECT TAIKHOAN_FK AS TKID, TIENTEGOC_FK AS TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +  
							"			NO, CO,   \n " +
							"			CASE WHEN NO > 0 THEN TONGGIATRINT ELSE 0 END AS NONT, \n " +  
							"			CASE WHEN CO > 0 THEN TONGGIATRINT ELSE 0 END AS CONT   \n " +
					
							"			FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN    \n " +
							"			WHERE   \n " +
							"			NGAYGHINHAN >= '" + ngaybd + "' \n " +  
							"			AND NGAYGHINHAN <= '" + ngaykt + "'  \n " +
							" 			AND TONGGIATRINT > 0 AND TIENTEGOC_FK <> 100000 " +
							
					  		"		)TEMP   \n " +
							"		GROUP BY TKID, TTID, MADOITUONG, DOITUONG \n " +  
						
					 		"	)DATA   \n " +
							"	INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = DATA.TKID \n " +
							"	WHERE TTID <> 100000 \n " +
							" AND ( SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '138%' \n "+
							" OR SOHIEUTAIKHOAN LIKE '244%' OR SOHIEUTAIKHOAN LIKE '331%' \n "+
							" OR SOHIEUTAIKHOAN LIKE '338%') \n" +
							" AND ( SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '138%' \n "+
							" OR SOHIEUTAIKHOAN LIKE '244%' OR SOHIEUTAIKHOAN LIKE '331%' \n "+
							" OR SOHIEUTAIKHOAN LIKE '338%') \n" +
							"	GROUP BY MADOITUONG, DOITUONG, SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3), TTID \n " + 
					 
							")FINAL \n " +
							"GROUP BY SOHIEU, TIENTE, TTID \n " +
							"ORDER BY SOHIEU, TIENTE \n " ;
				System.out.println(query);
				this.dgtigialist = this.db.get(query);
			}
		}
	}
		
	private Hashtable<String, String> createTigiaHashtable(){
		Hashtable<String, String> ht = new Hashtable<String, String>();
		if(this.ttIds != null){
			for(int i = 0; i < this.ttIds.length; i++){
				if(this.tigia[i] != null){
					if(this.tigia[i].length() > 0){
						
						ht.put(this.ttIds[i], this.tigia[i].replaceAll(",", ""));
						
					}else{
						
						ht.put(this.ttIds[i], "1");
					}
				}else{
					ht.put(this.ttIds[i], "1");
				}
			}
		}
		return ht;
	}
	
	public boolean Save(){
		String query = "";
		String Id = "";
		
		this.ht = this.createTigiaHashtable();
		if(this.ctyId.length() > 0 & this.nam.length() > 0 & this.quy.length() > 0){
			try{
				this.db.getConnection().setAutoCommit(false);

				query = "SELECT PK_SEQ AS ID FROM ERP_DANHGIATIGIA WHERE NAM = " + this.nam + " AND QUY = " + this.quy + " AND CONGTY_FK = " + this.ctyId;
				ResultSet rs = this.db.get(query);
				if(rs != null){
					if(rs.next()){
						Id = rs.getString("ID");
					}
					rs.close();
				}
				
				if(Id.length() == 0){	
					query = "INSERT INTO [ERP_DANHGIATIGIA] " +
					        "([NAM],[QUY],[GHIDAO],[NGAYCHUNGTU],[NGAYTAO],[NGAYSUA],[NGUOITAO],[NGUOISUA],[TRANGTHAI],[CONGTY_FK],[DIENGIAI]) " +
					        "VALUES(" + this.nam + ", " + this.quy + ", " + this.ghidao + ", '" + this.ngaychungtu + "', " +
					        "'" + this.getDateTime() + "', '" + this.getDateTime() + "', " + this.userId + ", " + this.userId + ", 0, " + this.ctyId + ",N'"+this.dienGiaiCT+"')";
					
					System.out.println(query);
					this.db.update(query);
					
					query = "SELECT SCOPE_IDENTITY() AS ID ";
					rs = this.db.get(query);
					rs.next();
					Id = rs.getString("ID");
					rs.close();
					
				}else{
					query = "UPDATE ERP_DANHGIATIGIA SET GHIDAO = " + this.ghidao + ", NGAYCHUNGTU = '" + this.ngaychungtu + "', " +
							"NGAYTAO = '" + this.getDateTime() + "', NGAYSUA = '" + this.getDateTime() + "',DIENGIAI = N'"+this.dienGiaiCT+"' " +
							"WHERE NAM = " + this.nam + " AND QUY = " + this.quy + " AND CONGTY_FK = " + this.ctyId + "";
					this.db.update(query);
					
				}	

				query = "DELETE ERP_DANHGIATIGIA_TIGIA WHERE DANHGIATIGIA_FK = " + Id + "";
				this.db.update(query);
				
				query = "DELETE ERP_DANHGIATIGIA_CHITIET WHERE DANHGIATIGIA_FK = " + Id + "";
				this.db.update(query);

				query = "";
				for(int i = 0; i < this.ttIds.length; i++){
					query += "INSERT INTO ERP_DANHGIATIGIA_TIGIA(DANHGIATIGIA_FK, TIENTE_FK, TIGIADANHGIA) " +
							"VALUES(" + Id + ", " + this.ttIds[i] + ", " + this.tigia[i] + ") ";
					
				}
				this.db.update(query);
				Danhgiatigia_chitiet();
				
				if(this.dgtigialist != null){
					query = "";
					while(this.dgtigialist.next()){
						query += "INSERT INTO [ERP_DANHGIATIGIA_CHITIET] " +
						        "([DANHGIATIGIA_FK],[TAIKHOAN_FK],[MADOITUONG],[DOITUONG], [DOITUONG_PSKT], [ISNPP],[TIENTE_FK],[SODUNGOAITE], " +
						        " [SODU_TRUOC_DG],[SODU_SAU_DG],[CHENHLECH]) " +
						        
						        " VALUES(" + Id + ", " +  this.dgtigialist.getString("TKID") + ", '" +  this.dgtigialist.getString("MADOITUONG") + "', "  +
						        "N'" + this.dgtigialist.getString("DOITUONG") + "', N'" + this.dgtigialist.getString("DOITUONG_PSKT") + "', NULL, " +  this.dgtigialist.getString("TTID") + ", "  +
						        ""  +  this.dgtigialist.getString("SODUNGOAITE") + ", " +  this.dgtigialist.getString("SODUVND_TRUOCDG") + ", " +
						        "" +  this.dgtigialist.getDouble("SODUNGOAITE")*Double.parseDouble((String)ht.get(this.dgtigialist.getString("TTID"))) + ", " + 
						        "" + (this.dgtigialist.getDouble("SODUNGOAITE")*Double.parseDouble((String)ht.get(this.dgtigialist.getString("TTID"))) - this.dgtigialist.getDouble("SODUVND_TRUOCDG")) + ") ";
						        
					}
					System.out.println(query);
					this.db.update(query);
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);

			}catch(java.sql.SQLException e){
				this.msg = "Kết quả của đánh giá tỉ giá đã được lưu không thành công!";
			}
		}
		this.msg = "Kết quả của đánh giá tỉ giá đã được lưu thành công!";
		return true;
	}

	private void Danhgiatigia_chitiet(){
		String thangdk = "";
		String namdk = "";
		String ngaybd = "";
		String ngaykt = "";
		String query = "";
		
		if(this.quy.equals("1")){
			thangdk = "12";
			namdk = "" + (Integer.parseInt(nam) - 1);
			ngaybd = nam + "-01-01";
			ngaykt = nam + "-03-31";
		}

		if(this.quy.equals("2")){
			thangdk = "3";
			namdk = nam;
			ngaybd = nam + "-04-01";
			ngaykt = nam + "-06-30";
		}

		if(this.quy.equals("3")){
			thangdk = "6";
			namdk = nam;
			ngaybd = nam + "-07-01";
			ngaykt = nam + "-09-31";
		}

		if(this.quy.equals("4")){
			thangdk = "9";
			namdk = nam;
			ngaybd = nam + "-10-01";
			ngaykt = nam + "-12-31";
		}


		query = 	"SELECT TKID, TIENTE, TTID, MADOITUONG, DOITUONG, DOITUONG_PSKT, \n " + 
					"CASE WHEN (SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE) > 0) THEN (SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE)) \n " +
					"ELSE (-1)*(SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE)) END AS SODUNGOAITE, \n " +
			
					"CASE WHEN (SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0) THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND)) \n " +
					"ELSE (-1)*(SUM(GIATRINOVND) - SUM(GIATRICOVND)) END AS SODUVND_TRUOCDG " +

					"FROM \n " +
					"( \n " +
			
					"	SELECT TKID, MADOITUONG, DOITUONG AS DOITUONG_PSKT, \n " + 
					"	CASE WHEN DOITUONG = N'Sản phẩm' THEN (SELECT TEN FROM ERP_SANPHAM WHERE CONVERT(VARCHAR, PK_SEQ) = MADOITUONG) \n " +
					"	ELSE CASE WHEN DOITUONG = N'Nhà cung cấp' THEN (SELECT TEN FROM ERP_NHACUNGCAP WHERE CONVERT(VARCHAR, PK_SEQ) = MADOITUONG) \n " +
					"	ELSE DOITUONG END END AS DOITUONG, \n " +
			
					"	(SELECT MA FROM ERP_TIENTE WHERE PK_SEQ = TTID) AS TIENTE, TTID, \n " + 
							 
					"	CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0  \n " +
					"	THEN SUM(GIATRINOVND) - SUM(GIATRICOVND) \n " +
					"	ELSE 0 END AS GIATRINOVND,  \n " +
							 
					"	CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0 \n " + 
					"	THEN SUM(GIATRICOVND) - SUM(GIATRINOVND) \n " +
					"	ELSE 0 END AS GIATRICOVND,  \n " +
			
					"	CASE WHEN SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE) > 0 \n " + 
					"	THEN SUM(GIATRINONGUYENTE) - SUM(GIATRICONGUYENTE) \n " +
					"	ELSE 0 END AS GIATRINONGUYENTE,  \n " +
							 
					"	CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICONGUYENTE) < 0 \n " + 
					"	THEN SUM(GIATRICONGUYENTE) - SUM(GIATRINONGUYENTE) \n " +
					"	ELSE 0 END AS GIATRICONGUYENTE  \n " +
					"	FROM    \n " +
					"	(   \n " +
					"		SELECT TAIKHOANKT_FK AS TKID, NGUYENTE_FK AS TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +   
					"		GIATRINOVND, GIATRICOVND, GIATRINONGUYENTE, GIATRICONGUYENTE   \n " +
					"		FROM ERP_TAIKHOAN_NOCO_KHOASO   \n " +
					"		WHERE THANG =  " + thangdk + "  AND NAM =  " + namdk + "  \n " +
					" 	    AND (GIATRINONGUYENTE > 0 OR GIATRICONGUYENTE > 0) AND NGUYENTE_FK <> 100000 \n " +
					 
					"		UNION ALL   \n " +
											
					"		SELECT TKID, TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +   
			
					"		CASE WHEN SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0)) > 0   \n " +
					"		THEN SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0))   \n " +
					"		ELSE 0 END AS NO,   \n " +
							 
					"		CASE WHEN SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0)) < 0 \n " +  
					"		THEN (-1)*(SUM(ROUND(ISNULL(NO, 0), 0)) - SUM(ROUND(ISNULL(CO, 0), 0)))   \n " +
					"		ELSE 0 END AS CO,   \n " +
							
					"		CASE WHEN SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0)) > 0 \n " +  
					"		THEN SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0))   \n " +
					"		ELSE 0 END AS NONT,   \n " +
							 
					"		CASE WHEN SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0)) < 0 \n " +  
					"		THEN (-1)*(SUM(ROUND(ISNULL(NONT, 0), 0)) - SUM(ROUND(ISNULL(CONT, 0), 0)))   \n " +
					"		ELSE 0 END AS CONT   \n " +
			
					"		FROM (   \n " +
					"			SELECT TAIKHOAN_FK AS TKID, TIENTEGOC_FK AS TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +  
					"			NO, CO,   \n " +
					"			CASE WHEN NO > 0 THEN TONGGIATRINT ELSE 0 END AS NONT, \n " +  
					"			CASE WHEN CO > 0 THEN TONGGIATRINT ELSE 0 END AS CONT   \n " +
			
					"			FROM ERP_PHATSINHKETOAN    \n " +
					"			WHERE   \n " +
					"			NGAYGHINHAN >= '" + ngaybd + "' \n " +  
					"			AND NGAYGHINHAN <= '" + ngaykt + "'  \n " +
					" 			AND TONGGIATRINT > 0 AND TIENTEGOC_FK <> 100000 " +
					 
					"			UNION ALL \n " +
					"			SELECT TAIKHOAN_FK AS TKID, TIENTEGOC_FK AS TTID, ISNULL(MADOITUONG, '') AS MADOITUONG, ISNULL(DOITUONG, '') AS DOITUONG, \n " +  
					"			NO, CO,   \n " +
					"			CASE WHEN NO > 0 THEN TONGGIATRINT ELSE 0 END AS NONT, \n " +  
					"			CASE WHEN CO > 0 THEN TONGGIATRINT ELSE 0 END AS CONT   \n " +
			
					"			FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN    \n " +
					"			WHERE   \n " +
					"			NGAYGHINHAN >= '" + ngaybd + "' \n " +  
					"			AND NGAYGHINHAN <= '" + ngaykt + "'  \n " +
					" 			AND TONGGIATRINT > 0 AND TIENTEGOC_FK <> 100000 " +
					
			  		"		)TEMP   \n " +
					"		GROUP BY TKID, TTID, MADOITUONG, DOITUONG \n " +  
				
			 		"	)DATA   \n " +
					"	INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = DATA.TKID \n " +
					"	WHERE TTID <> 100000 \n " +
					" AND ( SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '138%' \n "+
					" OR SOHIEUTAIKHOAN LIKE '244%' OR SOHIEUTAIKHOAN LIKE '331%' \n "+
					" OR SOHIEUTAIKHOAN LIKE '338%') \n" +
					"	GROUP BY MADOITUONG, DOITUONG, TKID, TTID \n " + 
			 
					")FINAL \n " +
					"GROUP BY TKID, TIENTE, TTID, MADOITUONG, DOITUONG, DOITUONG_PSKT \n " +
					"ORDER BY TKID, TIENTE \n " ;
		System.out.println(query);
		this.dgtigialist = this.db.get(query);
		
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		try{
			if(this.dgtigialist != null) this.dgtigialist.close();
			if(this.namlist != null) this.namlist.close();
			if(this.quylist != null) this.quylist.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}
}
