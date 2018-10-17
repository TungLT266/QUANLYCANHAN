package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import geso.traphaco.erp.beans.lapkehoach.IErpKiemtrakehoach;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpKiemtrakehoach implements IErpKiemtrakehoach
{
	String ctyId;
	String userId;
	String id;

	ResultSet thangRs;
	String thang;
	
	ResultSet namRs;
	String nam;
	
	ResultSet khoTTRs;
	String khoId;
	
	ResultSet nhRs;
	String nhId;

	ResultSet clRs;
	String clId;
	
	ResultSet spRs;
	String spId;
	
	String donvi;
	
	String tondau;
		
	String msg;
	
	String ngayhientai;
	
	String loaiId;
	
	String dvkdId;
	
	ResultSet rsDvkd;
	
	dbutils db;
	
	public ErpKiemtrakehoach()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.dvkdId = "";
		this.khoId = "";
		this.spId = "";
		this.donvi = "";
		this.tondau = "";
		this.loaiId = "100042";
		
		this.ngayhientai = this.getDateTime();
		
		this.nam = ngayhientai.substring(0, 4);
		this.thang = "" + Integer.parseInt(ngayhientai.substring(5, 7));
		
		this.nhId = "";
		this.clId = "";
		this.msg = "";
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
	
	public String getDvkdId()
	{

		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{

		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdRs()
	{

		return this.rsDvkd;
	}

	public void setDvkdRs(ResultSet rsDvkd)
	{

		this.rsDvkd = rsDvkd;
	}
	
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getNgayhientai() 
	{
		return this.ngayhientai;
	}

	public void setNgayhientai(String ngayhientai) 
	{
		this.ngayhientai = ngayhientai;
	}

	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;	
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public ResultSet getKhoTTRs()
	{
		return this.khoTTRs;
	}

	public void setKhoTTRs(ResultSet khoRs)
	{
		this.khoTTRs = khoRs;
	}

	public String getKhoId()
	{
		return this.khoId;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public String getDonvi() 
	{
		if(this.spId.length() > 0){
			String query = 	"SELECT DVDL.DONVI FROM ERP_SANPHAM SP " +
							"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
							"WHERE SP.PK_SEQ = " + this.spId ;
			
			ResultSet rs = this.db.get(query);

			try{
				rs.next();
				this.donvi = rs.getString("DONVI");
				rs.close();
			
			}catch(java.sql.SQLException e){}
			
		}
		return this.donvi;
	}

	public void setDonvi(String donvi)
	{
		this.donvi = donvi;
	}
	
	public String getLoaiId() 
	{
		return this.loaiId;
	}

	public void setLoaiId(String loaiId)
	{
		this.loaiId = loaiId;
	}

	public void createRs() 
	{
		String query = "";
		for( int i = Integer.parseInt((this.getDateTime().substring(5, 7))); i <= 12; i++ )
		{
			query += "select " + i + " as thang, N'Tháng " + i + "' as thangTen  ";
			if(i < 12)
			{
				query += " union all ";
			}
		}
		
		this.thangRs = db.get(query);
		
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		query = "";
		for(int i = nam ; i <= nam + 1 ; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 1)
			{
				query += " union all ";
			}
		}
		
		this.namRs = this.db.get(query);
		
		
		if(this.loaiId.equals("100002") || this.loaiId.equals("100003")){
						
			query = "select pk_seq, ten from chungloai where trangthai = '1' and congty_fk = " + this.ctyId + " \n " +
					"AND PK_SEQ IN (SELECT DISTINCT CHUNGLOAI_FK FROM ERP_SANPHAM WHERE LOAISANPHAM_FK IN (100002, 100003) )";
			this.clRs = this.db.get(query);
			
			query = "select pk_seq as spId, ma + ' - ' + ten as ten from erp_sanpham " +
					"where trangthai = 1 and loaisanpham_fk = '" + this.loaiId + "' and congty_fk = " + this.ctyId + " ";
			
			if(this.clId.trim().length() > 0)
				query += " and chungloai_fk = " + this.clId + " ";
			
			System.out.println("San pham: " + query);
			this.spRs = this.db.get(query);
		
		}else{
			query = "select pk_seq as spId, ma + ' - ' + ten as ten from erp_sanpham " +
					"where trangthai = 1 and congty_fk = " + this.ctyId + " ";
			if(this.loaiId.length() > 0){
				query = query + " and loaisanpham_fk = '" + this.loaiId + "' ";
			}
			
			System.out.println("San pham: " + query);
			this.spRs = this.db.get(query);
			
		}
		
		System.out.println("__Create Resual set....");
		if(this.spId.length() > 0 & this.khoId.length() > 0){
			this.tondau = Tondau();
		}
		
		
		//this.rsDvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.ctyId + " AND TRANGTHAI = '1' ");
	}
	
	private String Tondau(){
		String query =  "SELECT SUM(KHO_SP.AVAILABLE) AS TONKHO, SANXUAT_SP.SANPHAM_FK AS SPID " +    
						"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " +   
						"WHERE KHO_SP.KHOTT_FK = " + this.khoId + " AND KHO_SP.SANPHAM_FK = '" + this.spId + "' " +
						"AND KHO_SP.KHOTT_FK IN (100021, 100022) " +
						"GROUP BY SANXUAT_SP.SANPHAM_FK ";
		
		System.out.println("Ton dau: " + query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String tondau = rs.getString("TONKHO");
			rs.close();
			return tondau;
		}catch(java.sql.SQLException e){
			return "";
		}
	}
	
	public ResultSet getYeuCau(int thang, int nam){
		String query = 	"SELECT  DB_SP.TONKHOANTOAN, YEUCAU.SANXUAT AS YEUCAU,	" + 
						"DB_SP.SANPHAM_FK AS SPID, YEUCAU.KHOTT_FK AS KHOID, DB_SP.THANG, DB_SP.NAM " +
						"FROM ERP_DUBAO DUBAO  " +
						"INNER JOIN ERP_DUBAOSANPHAM DB_SP ON DB_SP.DUBAO_FK = DUBAO.PK_SEQ " + 
						"INNER JOIN ERP_YEUCAUCUNGUNG YEUCAU ON YEUCAU.KHOTT_FK = DUBAO.KHO_FK " +
						"AND YEUCAU.SANPHAM_FK = DB_SP.SANPHAM_FK AND YEUCAU.THANG = DB_SP.THANG " +
						"AND YEUCAU.NAM = DB_SP.NAM " +
						"WHERE DUBAO.KHO_FK = " + this.khoId + " AND DB_SP.SANPHAM_FK = '" + this.spId + "'" + 
						"AND DB_SP.THANG = " + thang + " AND DB_SP.NAM = " + nam + "" ;

		System.out.println("Yeu cau: " + query);
		return this.db.get(query);
	}
	
	
		
	public ResultSet getData(){
		String t0 = this.thang;
		String n0 = this.nam;
		double pt = 1;
		String query = "SELECT CONVERT(FLOAT, SUBSTRING('" + this.getDateTime() + "', 9, 2))/(SELECT datediff(dd, " + this.getDateTime() + ", dateadd(mm, 1, " + this.getDateTime() + "))) as pt";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			pt = 1.0 - rs.getDouble("pt");
			rs.close();
		}catch(java.sql.SQLException e){}
		String khoIds = "100021, 100022";

		query = 
					"SELECT * \n " +
					"FROM \n " +
					"( \n " +
					"	SELECT '-0' AS LOAI, SANPHAM_FK AS SPID, " + t0 + " AS THANG, " + n0 + " AS NAM, SUM(ISNULL(AVAILABLE, 0)) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_KHOTT_SANPHAM KHOSP \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK \n " +
					"	WHERE SP.LOAISANPHAM_FK IN (100002, 100003) \n " +
					"	AND KHOSP.KHOTT_FK IN (" + khoIds + ") \n " +
					"	GROUP BY SANPHAM_FK \n " +
 
					"   UNION ALL \n " +
					"	SELECT '-1' AS LOAI, SANPHAM_FK, " + t0 + " AS THANG, " + n0 + " AS NAM, SUM(ISNULL(TONANTOAN, 0)) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_KHOTT_SANPHAM KHOSP \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK \n " +
					"	WHERE SP.LOAISANPHAM_FK IN (100002, 100003) \n " +
					"	AND KHOSP.KHOTT_FK IN (" + khoIds + ") \n " +
					"	GROUP BY SANPHAM_FK \n " +
					
					" 	UNION ALL \n " +	
					" 	SELECT	'-2' AS LOAI, LSXSP.SANPHAM_FK, SUBSTRING(NGAYDUKIENHT, 6, 2), SUBSTRING(NGAYDUKIENHT, 1, 4), \n " +
					"   (ISNULL(LSXSP.SOLUONG,0) -  ISNULL(NH.NHANHANG, 0)), LSX.PK_SEQ AS ID \n " + 	
					"	FROM ERP_LENHSANXUAT_GIAY LSX \n " +
					"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ \n " + 
					"	LEFT JOIN( \n " +
					"		SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " + 
					"		FROM ERP_NHAPKHO NK  \n " +
					"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " + 
//					"		WHERE NK.TRANGTHAI = 1 \n " +
//					"		AND NK.KHONHAP IN(100005, 100006)   \n " +
					"		GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " +
					"	)NH ON NH.SANPHAM_FK = LSXSP.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX.PK_SEQ \n " + 
					"	WHERE \n " +
					"	((ISNULL(LSX.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)) > 0 AND LSX.TRANGTHAI < 6 \n " + 

					"	UNION ALL \n " +
					"	SELECT '-3' AS LOAI, SANPHAM_FK, THANG, NAM, SUM(DUKIENBAN)* " + pt + " AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_DUBAOSANPHAMMTS  \n " +
					"   WHERE THANG = " + t0 + " AND NAM = " + n0 + " \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"	GROUP BY SANPHAM_FK, NAM, THANG \n " +
					
					"	UNION ALL \n " +
					"	SELECT '-3' AS LOAI, SANPHAM_FK, THANG, NAM, SUM(DUKIENBAN) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_DUBAOSANPHAMMTS  \n " +
					"   WHERE CONVERT(VARCHAR, THANG) + '-' + CONVERT(VARCHAR, NAM) <> '" + t0 + "-" + n0 + "' \n " +
					"	AND DUBAO_FK = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO  WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"	GROUP BY SANPHAM_FK, NAM, THANG \n " +

					"	UNION ALL \n " +
					"	SELECT '-4' AS LOAI, LSXDK.SANPHAM_FK, \n " +
					"	CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) AS THANG, SUBSTRING(LSXDK.NGAYKETTHUC, 0, 5) AS NAM, \n " +
					"	(LSXDK.SOLUONG - ISNULL(CHUYEN_LSX.SOLUONG, 0)) AS SOLUONG, LSXDK.PK_SEQ AS ID \n " +
					"	FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
					"   LEFT JOIN ( \n " +
					" 		SELECT LSX.LENHSANXUATDUKIEN_FK AS LSXID, LSXSP.SANPHAM_FK, ISNULL(LSXSP.SOLUONG,0) AS SOLUONG \n " +
					" 		FROM ERP_LENHSANXUAT_GIAY LSX \n " + 
					"		INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ \n " + 
					"   )CHUYEN_LSX ON CHUYEN_LSX.SANPHAM_FK = LSXDK.SANPHAM_FK AND CHUYEN_LSX.LSXID = LSXDK.PK_SEQ \n " +
					"   WHERE (LSXDK.SOLUONG - ISNULL(CHUYEN_LSX.SOLUONG, 0)) > 0  \n " +
					
					")DATA  \n " +
					"WHERE SPID = " + this.spId + " AND (NAM > " + n0 + " OR (THANG >= " + t0 + " and NAM = " + n0 + "))  \n " +
					"ORDER BY NAM, THANG, LOAI " ;
		
		System.out.println(query);
		return this.db.get(query);
	}
	
	public ResultSet getDataNL(){
		String t0 = this.thang;
		String n0 = this.nam;
		
		String	query = 
					"SELECT * \n " +
					"FROM \n " +
					"( \n " +
					"	SELECT '-0' AS LOAI, SANPHAM_FK AS SPID, " + t0 + " AS THANG, " + n0 + " AS NAM, SUM(AVAILABLE) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_KHOTT_SANPHAM KHOSP \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK \n " +
					"	WHERE SP.LOAISANPHAM_FK IN (100000, 100008) \n " +
					"	AND KHOSP.KHOTT_FK IN (100016, 100017, 100018, 100019, 100020, 100049, 100051, 100049) \n " +
					"	GROUP BY SANPHAM_FK \n " +
 
					"   UNION ALL \n " +
					"	SELECT '-1' AS LOAI, SANPHAM_FK, " + t0 + " AS THANG, " + n0 + " AS NAM, SUM(ISNULL(TONANTOAN, 0)) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_KHOTT_SANPHAM KHOSP \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK \n " +
					"	WHERE SP.LOAISANPHAM_FK IN (100000, 100008) \n " +
					"	AND KHOSP.KHOTT_FK IN (100016, 100017, 100018, 100019, 100020, 100049, 100051, 100049) \n " +
					"	GROUP BY SANPHAM_FK \n " +
					
					"	UNION ALL	 \n " +								
					"	SELECT	'-2' AS LOAI, MHSP.SANPHAM_FK, CONVERT(VARCHAR, SUBSTRING(MHSP.NGAYNHAN, 6, 2)) AS THANG, SUBSTRING(MHSP.NGAYNHAN, 1, 4) AS NAM, \n " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS SOLUONG, MH.PK_SEQ AS ID  \n " + 			
					"	FROM ERP_MUAHANG MH   \n " + 
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
			
					"	WHERE MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, MHSP.NGAYNHAN, NH.NHANHANG, MH.PK_SEQ \n " +	
					"   HAVING (SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)) > 0 \n " +
					

					"	UNION ALL \n " +
					"	SELECT '-4' AS LOAI, SANPHAM_FK, CONVERT(INT, SUBSTRING(NGAYNHANHANG, 6, 2)) AS THANG, SUBSTRING(NGAYNHANHANG, 0, 5) AS NAM, SOLUONG, PK_SEQ AS ID \n " +
					"	FROM ERP_DONMUAHANGDUKIEN  \n " +
					
					"	UNION ALL \n " +
					"	SELECT '-3' AS LOAI, YC.NGUYENLIEU_FK AS SPID, " +
					"	CONVERT(INT, SUBSTRING(LSXDK.NGAYBATDAU, 6, 2)) AS THANG, " +
					"	CONVERT(INT, SUBSTRING(LSXDK.NGAYBATDAU, 1, 4)) AS NAM, " +
					"	SUM(ISNULL(YC.YEUCAU, 0)) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC \n " +
					"   INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
					"	GROUP BY YC.NGUYENLIEU_FK, CONVERT(INT, SUBSTRING(LSXDK.NGAYBATDAU, 1, 4)), CONVERT(INT, SUBSTRING(LSXDK.NGAYBATDAU, 6, 2)) \n " +					 

					")DATA  \n " +
					"WHERE SPID = " + this.spId + " AND (NAM > " + n0 + " OR (THANG >= " + t0 + " and NAM = " + n0 + "))  \n " +
					"ORDER BY NAM, THANG, LOAI " ;
		
		System.out.println(query);
		return this.db.get(query);
	}

	public void DbClose() 
	{
		
		try 
		{
			if(this.clRs != null) this.clRs.close();
			if(this.nhRs != null) this.nhRs.close();
			if(this.rsDvkd != null) this.rsDvkd.close();
			if(this.spRs != null) this.spRs.close();
			if(this.namRs != null) this.namRs.close();
			if(this.thangRs != null) this.thangRs.close();
			
			if(this.khoTTRs != null)
				this.khoTTRs.close();
			
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public static void main(String[] arg)
	{
		Calendar now = Calendar.getInstance();
		
		System.out.println("Tuan cua thang : " +
                now.get(Calendar.WEEK_OF_MONTH));
               
		System.out.println("Tuan cua nam : " +
                now.get(Calendar.WEEK_OF_YEAR));
		
		
		Calendar gregorianCalendar = new GregorianCalendar();  
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);  
        gregorianCalendar.setMinimalDaysInFirstWeek(4);  
          
        int numWeekofYear = 46;  //INPUT  
        int year = 2012;         //INPUT  
          
        gregorianCalendar.set(Calendar.YEAR , year);  
        gregorianCalendar.set(Calendar.WEEK_OF_YEAR , numWeekofYear);  
         
         		
	}

	
	public String getTondau()
	{
		return this.tondau;
	}

	public void setTondau(String tondau) 
	{
		this.tondau = tondau;
	}

	public ResultSet getThangRs() 
	{
		return this.thangRs;
	}

	public void setThangRs(ResultSet thangRs) 
	{
		this.thangRs = thangRs;
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public ResultSet getNamRs() 
	{
		return this.namRs;
	}

	public void setNamRs(ResultSet namRs) 
	{
		this.namRs = namRs;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public ResultSet getNhanhangRs() 
	{
		return this.nhRs;
	}

	public void setNhanhangRs(ResultSet nhRs) 
	{
		this.nhRs = nhRs;
	}

	public String getNhId() 
	{
		return this.nhId;
	}

	public void setNhId(String nhId) 
	{
		this.nhId = nhId;
	}

	public ResultSet getChungloaiRs() 
	{
		return this.clRs;
	}

	public void setChungloaiRs(ResultSet clRs) 
	{
		this.clRs = clRs;	
	}

	public String getClId()
	{
		return this.clId;
	}

	public void setClId(String clId) 
	{
		this.clId = clId;
	}
	
	public String getNgaycuoithang(String thang, String nam){
		int th = Integer.parseInt(thang);
		int n = Integer.parseInt(nam);
		
		if(th == 1 || th == 3 || th == 5 || th == 7 || th == 8 || th == 10 || th == 12)
			return "31";
		
		if(th == 4 || th == 6 || th == 9 || th == 11)
			return "31";
		
		if(th == 2){
			if( (n % 4) == 0) return "29";
			else return "28";
		}
		return "";
	}
}
