package geso.traphaco.erp.beans.lapkehoach.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapkehoach.IErp_YeucaumuaNL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Erp_YeucaumuaNL implements IErp_YeucaumuaNL 
{
	String ctyId;
	String dvkdId;
	String userId;
	String trangthai; 

	String diengiai;
	String msg;
	String nam;
	String thang;
	String nmId;
	String khoId;
	ResultSet ycNLRs;
	ResultSet nmRS;
	dbutils db;
	
	public Erp_YeucaumuaNL()
	{
		this.ctyId = "";
		this.dvkdId = "";
		this.userId = "";
		this.nmId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)) );
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.khoId = "";
		this.db = new dbutils();
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
	}


	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;	
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;	
	}
	
	public ResultSet getNhamayRS(){
		return this.nmRS;
	}
	
	public void setNhamayRS(ResultSet nmRS){
		this.nmRS = nmRS;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getNhamayId(){
		return this.nmId;
	}
	
	public void setNhamayId(String nmId){
		this.nmId = nmId;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		try
		{
			String query = 	"SELECT PK_SEQ AS NMID, MANHAMAY + ' - ' + TENNHAMAY AS TEN \n " +
							"FROM ERP_NHAMAYTONG WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.ctyId + " ";
			
			this.nmRS = this.db.get( query );

			String t1 = "", t2 = "", t3 = "", t4 = "", n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;
			
			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}
			
			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}
			
			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}

			//System.out.println("_____THANG LA: " + t);
			
			query = 		"SELECT  SP.PK_SEQ AS SPID, SP.MA, SP.TEN,  \n " +
			
							"SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) AS TONHIENTAI,  \n " +
							
							"SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) AS TONANTOAN,   \n " +
					
							"ISNULL(YC1.YEUCAU, 0) AS YCNL_THANG_1,  \n " +

							"ISNULL(YCCU_THANG_1.YCCU, 0) AS YCCU_THANG_1,  \n " +
							
							"ISNULL(MH_THANG_1.QTY, 0)	AS MH_THANG_1,  \n " +
							
							"ISNULL(DNMH_THANG_1.DNMH, 0) AS DNMH_THANG_1, \n " +
							
														
							"ISNULL(YC2.YEUCAU, 0) AS YCNL_THANG_2,  \n " +
							
							"ISNULL(YCCU_THANG_2.YCCU, 0) AS YCCU_THANG_2,  \n " +

							"ISNULL(MH_THANG_2.QTY, 0) AS MH_THANG_2,  \n " +
													
							"ISNULL(DNMH_THANG_2.DNMH, 0) AS DNMH_THANG_2, \n " +
							
							
							"ISNULL(YC3.YEUCAU, 0) AS YCNL_THANG_3,  \n " +
							
							"ISNULL(YCCU_THANG_3.YCCU, 0) AS YCCU_THANG_3,  \n " +

							"ISNULL(MH_THANG_3.QTY, 0) AS MH_THANG_3,  \n " +
													
							"ISNULL(DNMH_THANG_3.DNMH, 0) AS DNMH_THANG_3, \n " +
							

							"ISNULL(YC4.YEUCAU, 0) AS YCNL_THANG_4,  \n " +
							
							"ISNULL(YCCU_THANG_4.YCCU, 0) AS YCCU_THANG_4,  \n " +

							"ISNULL(MH_THANG_4.QTY, 0) AS MH_THANG_4,  \n " +
													
							"ISNULL(DNMH_THANG_4.DNMH, 0) AS DNMH_THANG_4 \n " +
									
							"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
//							"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT NGUYENLIEU_FK AS SPID, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU  YC \n " +
							"   INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
							"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t1 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n1 + "  \n " +						
							" 	GROUP BY NGUYENLIEU_FK  \n " +
							")YC1 ON YC1.SPID = KHOTT_SP.SANPHAM_FK  \n " +
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT NGUYENLIEU_FK AS SPID, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU  YC \n " +
							"   INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
							"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t2 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n2 + "  \n " +						
							" 	GROUP BY NGUYENLIEU_FK  \n " +
							")YC2 ON YC2.SPID = KHOTT_SP.SANPHAM_FK  \n " +

							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT NGUYENLIEU_FK AS SPID, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU  YC \n " +
							"   INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
							"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t3 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n3 + "  \n " +						
							" 	GROUP BY NGUYENLIEU_FK  \n " +
							")YC3 ON YC3.SPID = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT NGUYENLIEU_FK AS SPID, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU  YC \n " +
							"   INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
							"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t4 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n4 + "  \n " +						
							" 	GROUP BY NGUYENLIEU_FK  \n " +
							")YC4 ON YC4.SPID = KHOTT_SP.SANPHAM_FK  \n " +

							"LEFT JOIN(	 \n " +								
							"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
							"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " + 			
							"	FROM ERP_MUAHANG MH   \n " + 
							"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
							"	LEFT JOIN( \n " +
							"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
							"		FROM ERP_NHANHANG NH \n " +
							"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
							"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
							"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
					
							"	WHERE MHSP.NGAYNHAN >=  '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-01' \n  " +
							"	AND MHSP.NGAYNHAN <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND MH.TRANGTHAI < 2 \n " +
							"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
							"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +	
							")MH_THANG_1 ON MH_THANG_1.SPID = KHOTT_SP.SANPHAM_FK \n  " +
							
							"LEFT JOIN(	 \n " +								
							"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
							"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " + 			
							"	FROM ERP_MUAHANG MH   \n " + 
							"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
							"	LEFT JOIN( \n " +
							"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
							"		FROM ERP_NHANHANG NH \n " +
							"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
							"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
							"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
					
							"	WHERE MHSP.NGAYNHAN >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01' \n  " +
							"	AND MHSP.NGAYNHAN <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31'  AND MH.TRANGTHAI < 2 \n " +
							"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
							"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +	
							")MH_THANG_2 ON MH_THANG_2.SPID = KHOTT_SP.SANPHAM_FK \n  " +
																					
							"LEFT JOIN(	 \n " +								
							"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
							"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " + 			
							"	FROM ERP_MUAHANG MH   \n " + 
							"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
							"	LEFT JOIN( \n " +
							"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
							"		FROM ERP_NHANHANG NH \n " +
							"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
							"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
							"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
					
							"	WHERE MHSP.NGAYNHAN >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01' \n  " +
							"	AND MHSP.NGAYNHAN <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31'  AND MH.TRANGTHAI < 2 \n " +
							"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
							"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +	
							")MH_THANG_3 ON MH_THANG_3.SPID = KHOTT_SP.SANPHAM_FK \n  " +

							"LEFT JOIN(	 \n " +								
							"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
							"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " + 			
							"	FROM ERP_MUAHANG MH   \n " + 
							"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
							"	LEFT JOIN( \n " +
							"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
							"		FROM ERP_NHANHANG NH \n " +
							"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
							"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
							"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
					
							"	WHERE MHSP.NGAYNHAN >=  '" + n4 + "-" + (t4.length()== 1? "0" + t4 : t4) + "-01' \n  " +
							"	AND MHSP.NGAYNHAN <= '" + n4 + "-" + (t4.length()== 1? "0" + t4 : t4) + "-31'  AND MH.TRANGTHAI < 2 \n " +
							"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
							"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +	
							")MH_THANG_4 ON MH_THANG_4.SPID = KHOTT_SP.SANPHAM_FK \n  " +

							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCCU  \n " +
							"	FROM ERP_YEUCAUNHANNGUYENLIEU   \n " +
							"	WHERE NAM = " + n1 + " AND THANG = " + t1 + "  \n " +
							
							")YCCU_THANG_1 ON YCCU_THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCCU   \n " +
							"	FROM ERP_YEUCAUNHANNGUYENLIEU  \n  " +
							"	WHERE NAM = " + n2 + " AND THANG = " + t2 + "  \n " +
							
							")YCCU_THANG_2 ON YCCU_THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCCU   \n " +
							"	FROM ERP_YEUCAUNHANNGUYENLIEU   \n " +
							"	WHERE NAM = " + n3 + " AND THANG = " + t3 + "  \n " +
							
							")YCCU_THANG_3 ON YCCU_THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCCU   \n " +
							"	FROM ERP_YEUCAUNHANNGUYENLIEU   \n " +
							"	WHERE NAM = " + n4 + " AND THANG = " + t4 + "  \n " +
							
							")YCCU_THANG_4 ON YCCU_THANG_4.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +

							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT DMHDK.SANPHAM_FK, SUM(DMHDK.SOLUONG - DMHDK.DATMUA) AS DNMH   \n " +
							"	FROM ERP_DONMUAHANGDUKIEN DMHDK   \n " +
							"	WHERE SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNMH_THANG_1 ON DNMH_THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT DMHDK.SANPHAM_FK, SUM(DMHDK.SOLUONG - DMHDK.DATMUA) AS DNMH   \n " +
							"	FROM ERP_DONMUAHANGDUKIEN DMHDK   \n " +
							"	WHERE SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNMH_THANG_2 ON DNMH_THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT DMHDK.SANPHAM_FK, SUM(DMHDK.SOLUONG - DMHDK.DATMUA) AS DNMH   \n " +
							"	FROM ERP_DONMUAHANGDUKIEN DMHDK   \n " +
							"	WHERE SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNMH_THANG_3 ON DNMH_THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT DMHDK.SANPHAM_FK, SUM(DMHDK.SOLUONG - DMHDK.DATMUA) AS DNMH   \n " +
							"	FROM ERP_DONMUAHANGDUKIEN DMHDK   \n " +
							"	WHERE SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNMH_THANG_4 ON DNMH_THANG_4.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +

							"WHERE SP.TRANGTHAI = 1 AND SP.LOAISANPHAM_FK IN (100000, 100008, 100013)  \n " +
							"AND KHOTT_SP.KHOTT_FK IN (100014, 100016, 100017, 100018, 100019, 100020, 100049)  \n " ;

			if(this.nmId.length() > 0){
				//query += " AND SP.PK_SEQ IN ( SELECT DISTINCT NGUYENLIEU_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NHAMAY_FK = " + this.nmId + ") \n ";
				query += " AND SP.PK_SEQ IN ( SELECT DISTINCT NGUYENLIEU_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NHAMAY_FK in ( select pk_seq from ERP_NHAMAY where NHAMAYTONG_FK = '" + this.nmId + "' ) ) \n ";
			}
			
			query +=		"GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, \n " + //CL.PK_SEQ,  \n " +
							"YC1.YEUCAU, MH_THANG_1.QTY, YCCU_THANG_1.YCCU, DNMH_THANG_1.DNMH, \n  " +
							"YC2.YEUCAU, MH_THANG_2.QTY, YCCU_THANG_2.YCCU, DNMH_THANG_2.DNMH,  \n  " +
							"YC3.YEUCAU, MH_THANG_3.QTY, YCCU_THANG_3.YCCU,  DNMH_THANG_3.DNMH, \n " +
							"YC4.YEUCAU, MH_THANG_4.QTY, YCCU_THANG_4.YCCU,  DNMH_THANG_4.DNMH \n " +
							"HAVING ISNULL(YC1.YEUCAU, 0) + ISNULL(YC2.YEUCAU, 0) + ISNULL(YC3.YEUCAU, 0)+ ISNULL(YC4.YEUCAU, 0) > 0 \n " +

							"ORDER BY SP.MA ASC ";
			
			System.out.println("::: INIT DE NGHI MUA NL: " + query );
			this.ycNLRs = this.db.get(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception YC: " + e.getMessage() );
		}
	}

	public void delete(String id ){
		String query = "DELETE FROM ERP_LENHSANXUATDUKIEN WHERE PK_SEQ = '" + id + "'";
		this.db.update(query);
	}

	
	public void DbClose() 
	{
		try 
		{
			if(this.nmRS != null) this.nmRS.close();
			if(this.ycNLRs != null) this.ycNLRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public ResultSet getYeucaumuaNLRs() 
	{
		return this.ycNLRs;
	}

	public void setYeucaumuaNLRs(ResultSet ycNLRs) 
	{
		this.ycNLRs = ycNLRs;
	}

	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
