package geso.traphaco.erp.beans.lapkehoach.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapkehoach.IErp_Yeucausanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Erp_Yeucausanxuat implements IErp_Yeucausanxuat 
{
	String ctyId;
	String dvkdId;
	String userId;
	String trangthai; 

	String diengiai;
	String msg;
	String nam;
	String thang;
	String clId;
	String khoId;
	String loaihh;
	ResultSet ycsxRs;
	ResultSet chungloaiRS;
	dbutils db;
	
	public Erp_Yeucausanxuat()
	{
		this.ctyId = "";
		this.dvkdId = "";
		this.userId = "";
		this.clId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)) );
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.khoId = "";
		this.loaihh = "";
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

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;	
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
			String query = 	"SELECT CL.PK_SEQ, CL.TEN " +
							"FROM CHUNGLOAI CL " +
							"WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId;

			this.chungloaiRS = this.db.get( query );
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
			
			
			double pt = 1;
			/*query = "SELECT CONVERT(FLOAT, SUBSTRING('" + this.getDateTime() + "', 9, 2))/(SELECT datediff(dd, " + this.getDateTime() + ", dateadd(mm, 1, " + this.getDateTime() + "))) as pt";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				pt = 1.0 - rs.getDouble("pt");
				rs.close();
			}catch(java.sql.SQLException e){}*/
						
			
			String khoIdTT = "100021, 100022";

			//Không tính lượng hàng đã nhập kho
			query = 		"SELECT  SP.PK_SEQ AS SPID, SP.MA, SP.TEN, DVDL.DONVI, \n " +
					
							"SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) AS TONHIENTAI,  \n " +
							"	isnull( ( select top(1) solo from ERP_KHOTT_SP_CHITIET where khott_fk = '100022' and sanpham_fk = SP.PK_SEQ and AVAILABLE > 0 order by AVAILABLE desc ), '') as SOLO,  	" +
							"SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) AS TONANTOAN,   \n " +
					
							"ISNULL(DB1.DUBAOBAN, 0)*" + pt + " AS DUBAOBAN_1,  \n " +
							
							"ISNULL(YC1.YEUCAU, 0) AS YCCU_THANG_1, \n " +

							"ISNULL(YCSX_THANG_1.YCSX, 0) AS YCSX_THANG_1,  \n " +
							
							"ISNULL(SX_THANG_1.QTY, 0)	AS SX_THANG_1,  \n " +
							
							"ISNULL(DNSX_THANG_1.DNSX, 0) AS DNSX_THANG_1, \n " +
							
														
							"ISNULL(DB2.DUBAOBAN, 0) AS DUBAOBAN_2,  \n " +

							"ISNULL(YC2.YEUCAU, 0) AS YCCU_THANG_2, \n " +
							
							"ISNULL(YCSX_THANG_2.YCSX, 0) AS YCSX_THANG_2,  \n " +

							"ISNULL(SX_THANG_2.QTY, 0) AS SX_THANG_2,  \n " +
													
							"ISNULL(DNSX_THANG_2.DNSX, 0) AS DNSX_THANG_2, \n " +
							
							
							"ISNULL(DB3.DUBAOBAN, 0) AS DUBAOBAN_3,  \n " +
							
							"ISNULL(YC3.YEUCAU, 0) AS YCCU_THANG_3, \n " +
							
							"ISNULL(YCSX_THANG_3.YCSX, 0) AS YCSX_THANG_3,  \n " +

							"ISNULL(SX_THANG_3.QTY, 0) AS SX_THANG_3,  \n " +
													
							"ISNULL(DNSX_THANG_3.DNSX, 0) AS DNSX_THANG_3 \n " +
							
									
							"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
							"LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ  \n " +
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SUM(DUKIENBAN) AS DUBAOBAN  \n " +
							"	FROM ERP_DUBAOSANPHAMMTS  \n " +
							"	WHERE THANG = " + t1 + " AND NAM = " + n1 + "  \n " +
							"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
							" 	GROUP BY SANPHAM_FK  \n " +
							")DB1 ON DB1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SUM(DUKIENBAN) AS DUBAOBAN   \n " +
							"	FROM ERP_DUBAOSANPHAMMTS  \n " +
							"	WHERE THANG = " + t2 + " AND NAM = " + n2 + "  \n " +
							
							"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
							" 	GROUP BY SANPHAM_FK  \n " +
							")DB2 ON DB2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SUM(DUKIENBAN) AS DUBAOBAN  \n  " +
							"	FROM ERP_DUBAOSANPHAMMTS  \n " +
							"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " \n  " +
							"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
							" 	GROUP BY SANPHAM_FK  \n " +
							")DB3 ON DB3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
							"	FROM ERP_YEUCAUCUNGUNG  \n " +
							"	WHERE THANG = " + t1 + " AND NAM = " + n1 + "  \n " +
//							"   AND KHOTT_FK IN (100023, 100034, 100041) " +
							"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
							" 	GROUP BY SANPHAM_FK  \n " +
							")YC1 ON YC1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +

							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU   \n " +
							"	FROM ERP_YEUCAUCUNGUNG  \n " +
							"	WHERE THANG = " + t2 + " AND NAM = " + n2 + "  \n " +
//							"   AND KHOTT_FK IN (100023, 100034, 100041) " +
							"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
							" 	GROUP BY SANPHAM_FK  \n " +
							")YC2 ON YC2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +

							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n  " +
							"	FROM ERP_YEUCAUCUNGUNG  \n " +
							"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " \n  " +
//							"   AND KHOTT_FK IN (100023, 100034, 100041) " +
							"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
							" 	GROUP BY SANPHAM_FK  \n " +
							")YC3 ON YC3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN(  \n " +
							"	SELECT SPID, SUM(QTY) AS QTY \n " +
							"	FROM(  \n " +		
								"	SELECT	LSXSP.SANPHAM_FK AS SPID,  \n " + 
								"			(SUM(ISNULL(LSXSP.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP.sanpham_fk, null, LSXSP.dvdl_fk ), 1 )  AS QTY  \n " +  			
								"	FROM ERP_LENHSANXUAT_GIAY LSX     \n " +
								"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ  \n " +  
					
								"	WHERE LSX.NGAYDUKIENHT >=  '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-01'  \n  " +
								"	AND LSX.NGAYDUKIENHT <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND LSX.TRANGTHAI < 6  \n " +  // TRANG THAI CHUA HOAN TAT
								"	GROUP BY LSXSP.SANPHAM_FK, LSXSP.dvdl_fk  \n " +
							"	)SX GROUP BY SPID  \n " +
							")SX_THANG_1 ON SX_THANG_1.SPID = KHOTT_SP.SANPHAM_FK  \n " + 


							"LEFT JOIN(  \n " +
							"	SELECT SPID, SUM(QTY) AS QTY \n " +
							"	FROM(  \n " +		

								"	SELECT	LSXSP.SANPHAM_FK AS SPID,  \n " + 
								"			(SUM(ISNULL(LSXSP.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP.sanpham_fk, null, LSXSP.dvdl_fk ), 1 )  AS QTY  \n " +  			
								"	FROM ERP_LENHSANXUAT_GIAY LSX  \n " +   
								"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ  \n " +  
								
								"	WHERE LSX.NGAYDUKIENHT >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01'  \n " + 
								"	AND LSX.NGAYDUKIENHT <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31'  AND LSX.TRANGTHAI < 6  \n " + 
								"	GROUP BY LSXSP.SANPHAM_FK, LSXSP.dvdl_fk  \n " +
							"	)SX GROUP BY SPID  \n " +
			
							")SX_THANG_2 ON SX_THANG_2.SPID = KHOTT_SP.SANPHAM_FK  \n " +
							
							
							"LEFT JOIN(  \n " +
							"	SELECT SPID, SUM(QTY) AS QTY \n " +
							"	FROM(  \n " +		

								"	SELECT	LSXSP.SANPHAM_FK AS SPID,  \n " + 
								"			(SUM(ISNULL(LSXSP.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP.sanpham_fk, null, LSXSP.dvdl_fk ), 1 )  AS QTY  \n " +  			
								"	FROM ERP_LENHSANXUAT_GIAY LSX   \n   " +
								"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ  \n " +  
								
								"	WHERE LSX.NGAYDUKIENHT >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01'   \n " +
								"	AND LSX.NGAYDUKIENHT <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31'  AND LSX.TRANGTHAI < 6  \n " + 
								"	GROUP BY LSXSP.SANPHAM_FK, LSXSP.dvdl_fk  \n " +
							"	)SX GROUP BY SPID  \n " +
			
							")SX_THANG_3 ON SX_THANG_3.SPID = KHOTT_SP.SANPHAM_FK  \n " +

							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCSX  \n " +
							"	FROM ERP_YEUCAUSANXUAT   \n " +
							"	WHERE NAM = " + n1 + " AND THANG = " + t1 + "  \n " +
							
							")YCSX_THANG_1 ON YCSX_THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCSX   \n " +
							"	FROM ERP_YEUCAUSANXUAT  \n  " +
							"	WHERE NAM = " + n2 + " AND THANG = " + t2 + "  \n " +
							
							")YCSX_THANG_2 ON YCSX_THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT SANPHAM_FK, SOLUONG AS YCSX   \n " +
							"	FROM ERP_YEUCAUSANXUAT   \n " +
							"	WHERE NAM = " + n3 + " AND THANG = " + t3 + "  \n " +
							
							")YCSX_THANG_3 ON YCSX_THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT LSXDK.SANPHAM_FK, SUM(LSXDK.SOLUONG - LSXDK.SANXUAT) AS DNSX   \n " +
							"	FROM ERP_LENHSANXUATDUKIEN LSXDK   \n " +
							"	WHERE SUBSTRING(NGAYKETTHUC, 0, 8) = '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNSX_THANG_1 ON DNSX_THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT LSXDK.SANPHAM_FK, SUM(LSXDK.SOLUONG - LSXDK.SANXUAT) AS DNSX   \n " +
							"	FROM ERP_LENHSANXUATDUKIEN LSXDK   \n " +
							"	WHERE SUBSTRING(NGAYKETTHUC, 0, 8) = '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNSX_THANG_2 ON DNSX_THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							
							"LEFT JOIN  \n " +
							"(  \n " +
							"	SELECT LSXDK.SANPHAM_FK, SUM(LSXDK.SOLUONG - LSXDK.SANXUAT) AS DNSX   \n " +
							"	FROM ERP_LENHSANXUATDUKIEN LSXDK   \n " +
							"	WHERE SUBSTRING(NGAYKETTHUC, 0, 8) = '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "'  \n " +
							"   GROUP BY SANPHAM_FK \n " +
							")DNSX_THANG_3 ON DNSX_THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
							
							"WHERE SP.TRANGTHAI = 1 AND SP.LOAISANPHAM_FK IN (100002, 100003)  \n " +
							"AND KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ")  \n " ;
			
			if(this.clId.length() > 0){
				query += " AND SP.CHUNGLOAI_FK = " + this.clId + "";
			}
			
			if( this.loaihh.trim().length() > 0 )
				query += " and SP.loaihanghoa = '" + this.loaihh + "' ";
			
			query +=		"GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVDL.DONVI, \n " +
							"DB1.DUBAOBAN, YC1.YEUCAU, SX_THANG_1.QTY, YCSX_THANG_1.YCSX, DNSX_THANG_1.DNSX, \n  " +
							"DB2.DUBAOBAN, YC2.YEUCAU, SX_THANG_2.QTY, YCSX_THANG_2.YCSX, DNSX_THANG_2.DNSX,  \n  " +
							"DB3.DUBAOBAN, YC3.YEUCAU, SX_THANG_3.QTY, YCSX_THANG_3.YCSX,  DNSX_THANG_3.DNSX \n " +
							
							"HAVING ISNULL(DB1.DUBAOBAN, 0) + ISNULL(DB2.DUBAOBAN, 0) + ISNULL(DB3.DUBAOBAN, 0) > 0 \n " +

							"ORDER BY SP.MA ASC ";
			
			System.out.println(":::: YEU CAU SX: " + query);
			this.ycsxRs = this.db.get(query);
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

	public ResultSet getChungloaiRS(){
		return this.chungloaiRS;
	}
	
	public void setChungloaiRS(ResultSet clRS){
		this.chungloaiRS = clRS;
	}
	
	public String getClId(){
		return this.clId;
	}
	
	public void setClId(String clId){
		this.clId = clId;
	}

	
	public void DbClose() 
	{
		try 
		{
			if(this.chungloaiRS != null) this.chungloaiRS.close();
			if(this.ycsxRs != null) this.ycsxRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public ResultSet getYeucausanxuatRs() 
	{
		return this.ycsxRs;
	}

	public void setYeucausanxuatRs(ResultSet ycsxRs) 
	{
		this.ycsxRs = ycsxRs;
	}
	
	public String getLoaihh() {

		return this.loaihh;
	}


	public void setLoaihh(String loaihh) {

		this.loaihh = loaihh;
	}

	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
