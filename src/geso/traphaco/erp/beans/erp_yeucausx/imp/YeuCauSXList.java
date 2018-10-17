package geso.traphaco.erp.beans.erp_yeucausx.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_yeucausx.IYeuCauSXList;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
   
public class YeuCauSXList extends Phan_Trang implements IYeuCauSXList {
	private String ctyId;
	private String khoId;
	
	private String nam;
	private String thang;
	
	private String view;
	
	private String clId;
	private ResultSet chungloaiRS;
	
	private ResultSet data;
	private int sohang;
	private int socot;
	private String LOAI;
	
	private String theothang;
	private dbutils db;
	
	public YeuCauSXList()
	{
		this.ctyId = "";
		this.khoId = "";
		this.nam = "";		
		this.view = "";
		this.clId = "";
		this.socot = 0;
		this.sohang = 0;
		this.LOAI = "1";
		this.nam = this.getDateTime().substring(0, 4);
		
		if(this.getDateTime().substring(5, 7).equals("12")){
			this.nam = "" + (Integer.parseInt(this.getDateTime().substring(0, 4)) + 1);
		}
		
		this.theothang = "1";
		
		this.khoId = "100023";
		this.db = new dbutils();
		this.thang = this.getDateTime().substring(5, 7);
		this.nam = this.getDateTime().substring(0, 4);
		System.out.println("Tháng: " + this.thang);
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getKhoId(){
		return this.khoId;
	}
	
	public void setKhoId(String Id){
		this.khoId = Id;
	}
	
	public String getNam(){
		return this.nam;
	}
	
	public void setNam(String nam){
		this.nam = nam;
	}

	public String getThang(){
		return this.thang;
	}
	
	public void setThang(String thang){
		this.thang = thang;
	}
	
	public String getView(){
		return this.view;
	}
	
	public void setView(String view){
		this.view = view;
	}

	public String getClId(){
		return this.clId;
	}
	
	public void setClId(String clId){
		this.clId = clId;
	}

	public int getSohang(){
		return this.sohang;
	}
	
	public void setSohang(int sohang){
		this.sohang = sohang;
	}

	public int getSocot(){
		return this.socot;
	}
	
	public void setSocot(int socot){
		this.socot = socot;
	}

	public String getTheothang(){
		return this.theothang;
	}
	
	public void setTheothang(String theothang){
		this.theothang = theothang;
	}

	public ResultSet getChungloaiRS(){
		return this.chungloaiRS;
	}
	
	public void setChungloaiRS(ResultSet clRS){
		this.chungloaiRS = clRS;
	}

	public ResultSet getData(){
		return this.data;
	}
	
	public void setData(ResultSet data){
		this.data = data;
	}

	public void createRs() 
	{
		this.LOAI = "1";
		String	query = "SELECT CL.PK_SEQ, CL.TEN " +
						"from CHUNGLOAI CL " +
						"WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId;
	
			
		this.chungloaiRS = this.db.get( query );
		
		//System.out.println("___Init Data.......");
		createData();
	}
	
	private void createData()
	{
		try
		{
			
			ResultSet rs;

			String query = "";
			String t1 = "", t2 = "", t3 = "", t4 = "", t5 = "";
			String n1 = "", n2 = "", n3 = "", n4 = "", n5 = "";
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
			
			if(t4.equals("12")){
				t5 = "1";
				n5 = "" + (Integer.parseInt(n4) + 1);
			}else{
				t5 = "" + (Integer.parseInt(t4) + 1);
				n5 = n4;
			}
			

			//System.out.println("_____THANG LA: " + t);
			String khoIdTT = "";

			
/*			if(this.khoId.equals("100023")) {
				khoIds = "100023, 100024, 100025, 100026, 100027, 100028, 100029, 100030, 100031, 100032, 100033";
			}

			if(this.khoId.equals("100041")) {
				
				khoIds = "100041, 100042, 100043, 100044";
			}
			
			if(this.khoId.equals("100034")) {
				khoIds = "100034,100035, 100036, 100037, 100038, 100039, 100040";
			}

			if(this.khoId.length() == 0){
				this.khoId = "100023, 100041, 100034";
				khoIds = "100023, 100024, 100025, 100026, 100027, 100028, 100029, 100030, 100031, 100032, 100033, 100041, 100042, 100043, 100044, 100034,100035, 100036, 100037, 100038, 100039, 100040";
			}
*/
			khoIdTT = "100021, 100022";
			
			double pt = 1;
			/*query = "SELECT CONVERT(FLOAT, SUBSTRING('" + this.getDateTime() + "', 9, 2))/(SELECT datediff(dd, " + this.getDateTime() + ", dateadd(mm, 1, " + this.getDateTime() + "))) as pt";
			System.out.println(query);
			rs = this.db.get(query);
			try{
				rs.next();
				pt = 1.0 - rs.getDouble("pt");
				rs.close();
			}catch(java.sql.SQLException e){}*/
			
			query = "SELECT  SP.PK_SEQ AS SPID, SP.TEN, SP.MA, DVDL.DONVI, \n " +
					"SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) AS TONHIENTAI, SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) AS TONANTOAN,  \n  " +
					"ISNULL(THANG_1.DUKIENBAN, 0)*" + pt + " AS DUBAO_THANG_1,  \n " +
					"YC1.YEUCAU AS YCCU_THANG_1,  \n " +

					"ISNULL(THANG_2.DUKIENBAN, 0) AS DUBAO_THANG_2,  \n " +
	
					"YC2.YEUCAU AS YCCU_THANG_2,  \n " +

					"THANG_3.DUKIENBAN AS DUBAO_THANG_3,  \n " +
				
					"YC3.YEUCAU AS YCCU_THANG_3,  \n " +

					"THANG_4.DUKIENBAN AS DUBAO_THANG_4,  \n " +
				
					"YC4.YEUCAU AS YCCU_THANG_4,  \n " +
		

					"THANG_5.DUKIENBAN AS DUBAO_THANG_5,  \n " +
				
					"YC5.YEUCAU AS YCCU_THANG_5  \n " +
		
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU   \n " +
					"	FROM ERP_YEUCAUCUNGUNG  \n " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + "  \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   GROUP BY SANPHAM_FK  \n " +
					")YC1 ON YC1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG  \n " +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + "  \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   GROUP BY SANPHAM_FK \n  " +
					")YC2 ON YC2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG  \n " +
					"	WHERE THANG = " + t3 + " AND NAM = " + n3 + "  \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   GROUP BY SANPHAM_FK  \n " +
					")YC3 ON YC3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG  \n " +
					"	WHERE THANG = " + t4 + " AND NAM = " + n4 + " \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   GROUP BY SANPHAM_FK  \n " +
					")YC4 ON YC4.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
						
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT SANPHAM_FK, SUM(YEUCAU) AS YEUCAU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG  \n " +
					"	WHERE THANG = " + t5 + " AND NAM = " + n5 + "  \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC) \n  " +
					"   GROUP BY SANPHAM_FK  \n " +
					")YC5 ON YC5.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
										
					"LEFT JOIN " +
					"( " +
					"	SELECT DBSP.SANPHAM_FK, ISNULL(SUM(DBSP.DUKIENBAN), 0) AS DUKIENBAN  \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1  \n " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"	GROUP BY DBSP.SANPHAM_FK " +
					")THANG_1 ON THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, ISNULL(SUM(DBSP.DUKIENBAN), 0) AS DUKIENBAN  \n" +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP  \n" +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK  \n" +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " AND DB.HIEULUC = 1  \n" +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n" +
					" 	GROUP BY DBSP.SANPHAM_FK  \n" +
					")THANG_2 ON THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n" +

					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, ISNULL(SUM(DBSP.DUKIENBAN), 0) AS DUKIENBAN  \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK  \n " +
					"	WHERE THANG = " + t3 + " AND NAM = " + n3 + "  AND DB.HIEULUC = 1  \n " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					" 	GROUP BY DBSP.SANPHAM_FK  \n " +
					")THANG_3 ON THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
	
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, ISNULL(SUM(DBSP.DUKIENBAN), 0) AS DUKIENBAN  \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK  \n " +
					"	WHERE THANG = " + t4 + " AND NAM = " + n4 + "  AND DB.HIEULUC = 1  \n " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					" 	GROUP BY DBSP.SANPHAM_FK  \n " +
					")THANG_4 ON THANG_4.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +

					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, ISNULL(SUM(DBSP.DUKIENBAN), 0) AS DUKIENBAN  \n " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK  \n " +
					"	WHERE THANG = " + t5 + " AND NAM = " + n5 + "  AND DB.HIEULUC = 1  \n " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC) \n  " +
					" 	GROUP BY DBSP.SANPHAM_FK  \n " +
					")THANG_5 ON THANG_5.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
	
					"WHERE 1 = 1 AND KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ") \n " ;
			
				if(this.clId.length() > 0){
					query += " AND SP.CHUNGLOAI_FK = " + this.clId + "";
				}
					
				query += " GROUP BY SP.PK_SEQ, SP.TEN, DVDL.DONVI, " +
						 " THANG_1.DUKIENBAN, YC1.YEUCAU, THANG_2.DUKIENBAN, YC2.YEUCAU, THANG_3.DUKIENBAN,  YC3.YEUCAU, \n " +
						 " THANG_4.DUKIENBAN, YC4.YEUCAU, THANG_5.DUKIENBAN, YC5.YEUCAU, SP.MA \n " +
						 " HAVING (ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_3.DUKIENBAN, 0) + ISNULL(THANG_4.DUKIENBAN, 0) + ISNULL(THANG_5.DUKIENBAN, 0)) > 0 \n " + 
						 " ORDER BY  SP.MA ASC ";
			
			System.out.println(query);
			
			this.data = this.db.get(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception YC: " + e.getMessage() );
		}
	}
	
	public String getLoai() {
		
		return this.LOAI;
	}

	
	public void setLoai(String loai) {
		
		this.LOAI=loai;
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try{
			if(chungloaiRS != null) chungloaiRS.close();
			if (this.data != null) this.data.close();
			if (this.db != null) this.db.shutDown();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}
}
