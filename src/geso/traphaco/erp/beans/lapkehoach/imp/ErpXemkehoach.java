package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import geso.traphaco.erp.beans.lapkehoach.IErpXemkehoach;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpXemkehoach implements IErpXemkehoach
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
	
	dbutils db;
	
	public ErpXemkehoach()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
	
		this.khoId = "";
		this.spId = "";
		this.donvi = "";
		this.tondau = "";
		this.loaiId = "100005";
		
		this.ngayhientai = this.getDateTime();
		
		if(ngayhientai.substring(5, 7).equals("12"))
		{
			this.nam = Integer.toString( Integer.parseInt(ngayhientai.substring(0, 4)) + 1 );
			this.thang = "1";
		}
		else
		{
			this.nam = ngayhientai.substring(0, 4);
			this.thang = Integer.toString( Integer.parseInt(ngayhientai.substring(5, 7)) + 1 );
		}
		
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
		for( int i = 1; i <= 12; i++ )
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
		for(int i = nam - 3; i <= nam + 3; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 3)
			{
				query += " union all ";
			}
		}
		
		this.namRs = this.db.get(query);
		
		
		if(this.loaiId.equals("100005")){
			
			this.nhRs = this.db.get("select pk_seq, ten from nhanhang where trangthai = '1' and congty_fk = " + this.ctyId );

			query = "select pk_seq, ten from chungloai where trangthai = '1' and congty_fk = " + this.ctyId + " ";
			if(this.nhId.trim().length() > 0)
				query += " and pk_seq in ( select cl_fk from nhanhang_chungloai where nh_fk = '" + this.nhId + "' ) ";
				
			this.clRs = this.db.get(query);
			
			query = "select pk_seq as spId, ma + ten as ten from erp_sanpham where trangthai = 1 and loaisanpham_fk = '100005' and congty_fk = " + this.ctyId + " ";
		
			if(this.nhId.trim().length() > 0)
				query += " and nhanhang_fk = " + this.nhId + " ";
		
			if(this.clId.trim().length() > 0)
				query += " and chungloai_fk = " + this.clId + " ";
		
			this.spRs = this.db.get(query);
		
		}else{
			query = "select pk_seq as spId, ma + ten as ten from erp_sanpham where trangthai = 1 and congty_fk = " + this.ctyId + " ";
			if(this.loaiId.length() > 0){
				query = query + " and loaisanpham_fk = '" + this.loaiId + "' ";
			}
			this.spRs = this.db.get(query);
			
		}
		
		if(this.spId.length() > 0 & this.khoId.length() > 0){
			this.tondau = Tondau();
		}
		
	}
	
	private String Tondau(){
		String query =  "SELECT KHO_SP.AVAILABLE AS TONKHO, SANXUAT_SP.SANPHAM_FK AS SPID, KHO_SP.KHOTT_FK AS KHOID " +    
						"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " +   
						"WHERE KHO_SP.KHOTT_FK = " + this.khoId + " AND KHO_SP.SANPHAM_FK = '" + this.spId + "'";
		
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
	
	
	public ResultSet getData(String tungay, String denngay){
		String tmp = this.getDateTime();
		tmp = tmp.substring(5,6);
		
		String query = 	"SELECT * FROM ( " +
						"SELECT	'PLO-' + CONVERT(VARCHAR, LSXDK.PK_SEQ) AS ID, LSXDK.SANPHAM_FK AS SPID, LSXDK.SOLUONG AS QTY, NULL AS KHO, " +  
						"LSXDK.NAM + '-' + " +
						"(CASE WHEN LEN(CONVERT(VARCHAR, LSXDK.THANG)) = 1 " +
						"	THEN ('0' + CONVERT(VARCHAR, LSXDK.THANG)) ELSE CONVERT(VARCHAR, LSXDK.THANG) END ) + " +
						"(CASE WHEN (LSXDK.THANG = 2) THEN '-28' ELSE '-30' END) AS NGAY " +		
						"FROM ERP_LENHSANXUATDUKIEN LSXDK " +
						"WHERE LSXDK.SANPHAM_FK = '" + this.spId + "'  " +
						"AND (LSXDK.NAM = " + tungay.substring(0, 4) + " AND LSXDK.THANG >= " + Integer.parseInt(tungay.substring(5, 7)) + ") " +
						"AND (LSXDK.NAM = " + denngay.substring(0, 4) + " AND LSXDK.THANG <= " + Integer.parseInt(denngay.substring(5, 7)) + ") " +
						"UNION " +
						"(" +
						"	SELECT	'PRO-' + CONVERT(VARCHAR, LSX.PK_SEQ) AS ID, LSX.SANPHAM_FK AS SPID, LSX.SOLUONG AS QTY, NULL AS KHO, " + 
						"	LSX.NGAYDUKIENHT AS NGAY " +
						"	FROM ERP_LENHSANXUAT LSX " +
						"	WHERE LSX.SANPHAM_FK = '" + this.spId + "' " +
						"	AND LSX.NGAYDUKIENHT >= '" + tungay + "' AND LSX.NGAYDUKIENHT <= '" + denngay + "' " +
						") " +

						"UNION " + 
						"( " +
						"	SELECT '1INV-" + this.ngayhientai + "' AS ID,  SANXUAT_SP.SANPHAM_FK AS SPID, SUM(KHO_SP.AVAILABLE) AS QTY, NULL AS KHO, " +
						"	 '" + this.ngayhientai + "' AS NGAY   " +
						"	FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						"	INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " +   
						"	WHERE KHO_SP.SANPHAM_FK = '" + this.spId + "' " +
						"	GROUP BY SANXUAT_SP.SANPHAM_FK " +
						") " +

						"UNION " +
						"( " +
						"	SELECT  '2SAFE-" + this.ngayhientai + "' AS ID, DB_SP.SANPHAM_FK AS SPID, SUM(DB_SP.TONKHOANTOAN) AS QTY, NULL AS KHO, " +
						"			LTRIM(RTRIM(DB_SP.NAM))+'-'+ (CASE WHEN LEN(CONVERT(VARCHAR, DB_SP.THANG)) = 1 " + 
						"			THEN ('0' + CONVERT(VARCHAR,DB_SP.THANG)) ELSE CONVERT(VARCHAR,DB_SP.THANG) END ) +'-' + '01' AS NGAY " +
						"	FROM ERP_DUBAO DUBAO  " +
						"	INNER JOIN ERP_DUBAOSANPHAM DB_SP ON DB_SP.DUBAO_FK = DUBAO.PK_SEQ " + 
						"	INNER JOIN ERP_YEUCAUCUNGUNG YEUCAU ON YEUCAU.KHOTT_FK = DUBAO.KHO_FK  " +
						"	AND YEUCAU.SANPHAM_FK = DB_SP.SANPHAM_FK AND YEUCAU.THANG = DB_SP.THANG  " +
						"	AND YEUCAU.NAM = DB_SP.NAM AND YEUCAU.DUBAO_FK = DUBAO.PK_SEQ " +
						"	WHERE DB_SP.SANPHAM_FK = '" + this.spId + "' " + 
						"	AND DB_SP.NAM <= " + nam + " AND DB_SP.THANG <= " + thang + " " +
						"	GROUP BY DB_SP.SANPHAM_FK ,DB_SP.NAM, DB_SP.THANG " +	
						"   HAVING SUM(DB_SP.TONKHOANTOAN) > 0 " +
						")	" +
						"UNION " +
						"( " +
						"	SELECT  'SAFERE-" + this.ngayhientai + "' AS ID, DB_SP.SANPHAM_FK AS SPID, SUM(DB_SP.TONKHOANTOAN) AS QTY, NULL AS KHO, " +
						"			LTRIM(RTRIM(DB_SP.NAM))+'-'+ (CASE WHEN LEN(CONVERT(VARCHAR, DB_SP.THANG)) = 1 " + 
						"			THEN ('0' + CONVERT(VARCHAR,DB_SP.THANG)) ELSE CONVERT(VARCHAR,DB_SP.THANG) END ) +'-' + '22' AS NGAY " +
						"	FROM ERP_DUBAO DUBAO  " +
						"	INNER JOIN ERP_DUBAOSANPHAM DB_SP ON DB_SP.DUBAO_FK = DUBAO.PK_SEQ " + 
						"	INNER JOIN ERP_YEUCAUCUNGUNG YEUCAU ON YEUCAU.KHOTT_FK = DUBAO.KHO_FK  " +
						"	AND YEUCAU.SANPHAM_FK = DB_SP.SANPHAM_FK AND YEUCAU.THANG = DB_SP.THANG  " +
						"	AND YEUCAU.NAM = DB_SP.NAM AND YEUCAU.DUBAO_FK = DUBAO.PK_SEQ " +
						"	WHERE DB_SP.SANPHAM_FK = '" + this.spId + "' " + 
						"	AND DB_SP.NAM <= " + nam + " AND DB_SP.THANG <= " + thang + " AND DB_SP.THANG > " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " " +
						"	GROUP BY DB_SP.SANPHAM_FK ,DB_SP.NAM, DB_SP.THANG " +
						"   HAVING SUM(DB_SP.TONKHOANTOAN) > 0 " +
						"))A	"  +
						"WHERE NGAY >= '" + this.getDateTime() + "' ORDER BY NGAY, ID "	;
		
		System.out.println("Data: " + query);
		return this.db.get(query);
	}
	
	public ResultSet getDataNL(String tungay, String denngay){
		String tmp = this.getDateTime();
		int thang = Integer.parseInt(tmp.substring(5, 7));
		int tuthang = Integer.parseInt(tungay.substring(5, 7));
		int denthang = Integer.parseInt(denngay.substring(5, 7));
		int nam = Integer.parseInt(denngay.substring(0, 4));
		tmp = tmp.substring(5,6);
		String query =	"SELECT * FROM " +
					   	"( " +
					  	"	SELECT	'PLO-' + CONVERT(VARCHAR, MNLDK.SANPHAM_FK) AS ID, MNLDK.SANPHAM_FK AS SPID, " + 
						"	MNLDK.SOLUONG AS QTY, " +
						"	RTRIM(MNLDK.NAM) + '-' + " +
						"	(CASE WHEN LEN(CONVERT(VARCHAR, MNLDK.THANG)) = 1 " +
						"	THEN ('0' + RTRIM(CONVERT(VARCHAR, MNLDK.THANG))) ELSE RTRIM(CONVERT(VARCHAR, MNLDK.THANG)) END ) + " +
						"	'-01'  AS NGAY " +		
						
						"	FROM ERP_MUANGUYENLIEUDUKIEN MNLDK " +
						"	WHERE MNLDK.SANPHAM_FK = '"+ this.spId +"'  " +
						"	AND (MNLDK.NAM = " + tungay.substring(0, 4) + " AND MNLDK.THANG >= " + Integer.parseInt(tungay.substring(5, 7)) + ") " +
						"	AND (MNLDK.NAM = " + denngay.substring(0, 4) + " AND MNLDK.THANG <= " + Integer.parseInt(denngay.substring(5, 7)) + ") " +
						
						"UNION " +
						"(	" +
						"	SELECT	'PO-' + CONVERT(VARCHAR, MUAHANG.PK_SEQ) AS ID, MUAHANG.SANPHAM_FK AS SPID, MUAHANG.SOLUONG AS QTY, " + 	
						"	MUAHANG.NGAYNHAN AS NGAY " +	
						"	FROM ERP_MUAHANG_SP MUAHANG " +
						"	WHERE MUAHANG.SANPHAM_FK = '"+ this.spId +"'  " +  	
						"	AND MUAHANG.NGAYNHAN >= '" + tungay + "' AND MUAHANG.NGAYNHAN <= '" + denngay + "' " + 
						") " + 
						"UNION " + 
						"( 	" +
						"	SELECT '1INV-" + this.ngayhientai + "' AS ID,  SANXUAT_SP.SANPHAM_FK AS SPID, SUM(KHO_SP.AVAILABLE) AS QTY, " + 	 
						"	'" + this.ngayhientai + "' AS NGAY   " +
						"	FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						" 	INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK AND KHO.LOAI = 2 " +						
						"	INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 	
						"	WHERE KHO_SP.SANPHAM_FK = '"+ this.spId +"'	GROUP BY SANXUAT_SP.SANPHAM_FK " + 
						") " +						
						"UNION " +
						"( " + 	
						"	SELECT  '2SAFE-' AS ID, YCNL.NGUYENLIEU_FK AS SPID, YCNL.TONKHOANTOAN AS QTY, " +
						"	LSXDK.NAM + '-' + " +
						"	(CASE WHEN LEN(CONVERT(VARCHAR, LSXDK.THANG)) = 1 " +
						"	THEN ('0' + CONVERT(VARCHAR, LSXDK.THANG)) ELSE CONVERT(VARCHAR, LSXDK.THANG) END ) + " +
						"	'-01'  AS NGAY " +		
						"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YCNL" +
						"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YCNL.LENHSANXUATDUKIEN_FK " +
						"	WHERE YCNL.NGUYENLIEU_FK = '"+ this.spId +"' " +
						"	AND (LSXDK.NAM = " + tungay.substring(0, 4) + " AND LSXDK.THANG >= " + Integer.parseInt(tungay.substring(5, 7)) + ") " +
						"	AND (LSXDK.NAM = " + denngay.substring(0, 4) + " AND LSXDK.THANG <= " + Integer.parseInt(denngay.substring(5, 7)) + ") " +
						"	AND YCNL.TONKHOANTOAN > 0 " +
						")	" +
						")A	WHERE NGAY >= '" + this.getDateTime() + "' ORDER BY NGAY, ID "; 

		
		System.out.println("Data: " + query);
		return  this.db.get(query);
	}

	public void DbClose() 
	{
		
		try 
		{
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

	@Override
	public String getDvkdId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDvkdId(String dvkdId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet getDvkdRs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDvkdRs(ResultSet dvkd) {
		// TODO Auto-generated method stub
		
	}
}
