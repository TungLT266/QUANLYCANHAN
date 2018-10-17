package geso.traphaco.erp.beans.tonkhoantoan.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.tonkhoantoan.IErpTonkhoantoan;

public class ErpTonkhoantoan implements IErpTonkhoantoan
{
	String userId;
	String congtyId;
	String clId;
	ResultSet clRs;
	
	ResultSet tkanRs_1;
	ResultSet tkanRs_2;
	ResultSet tkanRs_3;
	ResultSet tkanRs_4;
	
	
	String[] tkan_1;
	String[] tkan_2;
	String[] tkan_3;
	String[] tkan_4;
	
	String[] sp_1;
	String[] sp_2;
	String[] sp_3;
	String[] sp_4;
	String msg;
	
	dbutils db;
	
	public ErpTonkhoantoan()
	{
		this.userId = "";
		this.clId = "";
		this.msg = "";
		this.db = new dbutils();
		
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

	public String[] getSp_1() 
	{
		return this.sp_1;
	}

	public void setSp_1(String[] sp_1) 
	{
		this.sp_1 = sp_1;
	}

	public String[] getSp_2() 
	{
		return this.sp_2;
	}

	public void setSp_2(String[] sp_2) 
	{
		this.sp_2 = sp_2;
	}

	public String[] getSp_3() 
	{
		return this.sp_3;
	}

	public void setSp_3(String[] sp_3) 
	{
		this.sp_3 = sp_3;
	}

	public String[] getSp_4() 
	{
		return this.sp_4;
	}

	public void setSp_4(String[] sp_4) 
	{
		this.sp_4 = sp_4;
	}

	public String[] getTkat_1() 
	{
		return this.tkan_1;
	}

	public void setTkat_1(String[] tkat_1) 
	{
		this.tkan_1 = tkat_1;
	}

	public String[] getTkat_2() 
	{
		return this.tkan_2;
	}

	public void setTkat_2(String[] tkat_2) 
	{
		this.tkan_2 = tkat_2;
	}

	public String[] getTkat_3() 
	{
		return this.tkan_3;
	}

	public void setTkat_3(String[] tkat_3) 
	{
		this.tkan_3 = tkat_3;
	}

	public String[] getTkat_4() 
	{
		return this.tkan_4;
	}

	public void setTkat_4(String[] tkat_4) 
	{
		this.tkan_4 = tkat_4;
	}

	public ResultSet getTkatRs_1() 
	{
		return this.tkanRs_1;
	}

	public void setTkatRs_1(ResultSet tkanRs_1) 
	{
		this.tkanRs_1 = tkanRs_1;
	}

	public ResultSet getTkatRs_2() 
	{
		return this.tkanRs_2;
	}

	public void setTkatRs_2(ResultSet tkanRs_2) 
	{
		this.tkanRs_2 = tkanRs_2;
	}

	public ResultSet getTkatRs_3() 
	{
		return this.tkanRs_3;
	}

	public void setTkatRs_3(ResultSet tkanRs_3) 
	{
		this.tkanRs_3 = tkanRs_3;
	}

	public ResultSet getTkatRs_4() 
	{
		return this.tkanRs_4;
	}

	public void setTkatRs_4(ResultSet tkanRs_4) 
	{
		this.tkanRs_4 = tkanRs_4;
	}

	public void init() 
	{
		try
		{
			//String query = "SELECT PK_SEQ, TEN AS TEN FROM CHUNGLOAI WHERE TRANGTHAI = 1";
			//this.clRs  =  this.db.get(query);
			
			String query = "select PK_SEQ, MA + ', ' + TEN as TEN from ERP_KHOTT order by pk_seq asc";
			this.clRs  =  this.db.get(query);

			/*String ngaytao = this.getDateTime();
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytao + "'), 0)) AS NGAY";
			ResultSet rs = this.db.get(query);			
			rs.next();

			String  ngaytd = rs.getString("NGAY").substring(0, 10);
			rs.close();

			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')-3, 0)) AS NGAY";
			rs = this.db.get(query);
			rs.next();

			String  ngaydsd3M = rs.getString("NGAY").substring(0, 10);
			rs.close();

			query = "SELECT SP.PK_SEQ AS SPID, SP.MA, SP.TEN, CL.TEN AS CHUNGLOAI, DVDL.DONVI, ISNULL(TONANTOAN, 0)  AS TKAT, \n " +
					"ROUND(ISNULL(AVG3M.AVG3M_PRI, 0), 0) AS AVG3M, 0 AS LASTYEAR, 1 NGAYTK  \n " +
					"FROM ERP_KHOTT_SANPHAM KHOSP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK  \n " +
					"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK  \n " +
					"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK  \n " +
					"LEFT JOIN   \n " +
					"(   \n " +
					"SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI   \n " +
					"FROM ERP_XUATKHO XK  \n " +
					"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK   \n   " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK   \n " +
					"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 \n  " +
					"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "'  \n " +
					"AND XK.KHO_FK IN (100023, 100024, 100025, 100026, 100027, 100028, 100029, 100030, 100031, 100032, 100033) " +
					"GROUP BY SP.PK_SEQ   " +
					") " +
					"AVG3M ON AVG3M.SPID = SP.PK_SEQ  " +
					"LEFT JOIN  " +
					"(  " +
					"SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) ) AS LASTYEAR    " +
					"FROM ERP_XUATKHO XK " +
					"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
					"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
					"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >=  '" + (Integer.parseInt(ngaytd.substring(0, 4)) - 1) + "-" + ngaytd.substring(5, 7) + "-01' " +
					"AND XK.NGAYCHOT <= '" + (Integer.parseInt(ngaytd.substring(0, 4)) - 1) + "-" + ngaytd.substring(5, 10) + "' " +
					"AND XK.KHO_FK IN (100023, 100024, 100025, 100026, 100027, 100028, 100029, 100030, 100031, 100032, 100033) " +
					"GROUP BY SP.PK_SEQ   " +
					") " +
					"LASTYEAR ON AVG3M.SPID = SP.PK_SEQ  " +


					"WHERE KHOTT_FK IN (100023) ";
			if(this.clId.length() > 0){
				query += " AND SP.CHUNGLOAI_FK = " + this.clId + " ";
			}
			query += " ORDER BY CL.PK_SEQ ";
			System.out.println(query);
			this.tkanRs_1 = this.db.get(query);


			query = "SELECT SP.PK_SEQ AS SPID, SP.MA, SP.TEN, CL.TEN AS CHUNGLOAI, DVDL.DONVI, ISNULL(TONANTOAN, 0)  AS TKAT, ROUND(ISNULL(AVG3M.AVG3M_PRI, 0), 0) AS AVG3M, 0 AS LASTYEAR, 1 NGAYTK " +
					"FROM ERP_KHOTT_SANPHAM KHOSP " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK " +
					"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
					"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
					"LEFT JOIN  " +
					"(  " +
					"	SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI    " +
					"	FROM ERP_XUATKHO XK " +
					"	INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
					"	INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
					"	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' " +
					"	AND XK.KHO_FK IN (100041, 100042, 100043, 100044) " +
					"	GROUP BY SP.PK_SEQ   " +
					") " +
					"AVG3M ON AVG3M.SPID = SP.PK_SEQ  " +
					"LEFT JOIN  " +
					"(  " +
					"	SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) ) AS LASTYEAR    " +
					"	FROM ERP_XUATKHO XK " +
					"	INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
					"	INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
					"	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >=  '" + (Integer.parseInt(ngaytd.substring(0, 4)) - 1) + "-" + ngaytd.substring(5, 7) + "-01' " +
					"	AND XK.NGAYCHOT <= '" + (Integer.parseInt(ngaytd.substring(0, 4)) - 1) + "-" + ngaytd.substring(5, 10) + "' " +
					"	AND XK.KHO_FK IN (100041, 100042, 100043, 100044) " +
					"	GROUP BY SP.PK_SEQ   " +
					") " +
					"LASTYEAR ON AVG3M.SPID = SP.PK_SEQ  " +


					"WHERE KHOTT_FK IN (100041) " ;
			if(this.clId.length() > 0){
				query += " AND SP.CHUNGLOAI_FK = " + this.clId + " ";
			}
			query += " ORDER BY CL.PK_SEQ ";
			this.tkanRs_2 = this.db.get(query);

			query = "SELECT SP.PK_SEQ AS SPID, SP.MA, SP.TEN, CL.TEN AS CHUNGLOAI, DVDL.DONVI, ISNULL(TONANTOAN, 0)  AS TKAT, ROUND(ISNULL(AVG3M.AVG3M_PRI, 0), 0) AS AVG3M, 0 AS LASTYEAR, 1 NGAYTK " +
					"FROM ERP_KHOTT_SANPHAM KHOSP " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK " +
					"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
					"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
					"LEFT JOIN  " +
					"(  " +
					"	SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI    " +
					"	FROM ERP_XUATKHO XK " +
					"	INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
					"	INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
					"	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' " +
					"	AND XK.KHO_FK IN (100034,100035, 100036, 100037, 100038, 100039, 100040) " +
					"	GROUP BY SP.PK_SEQ   " +
					") " +
					"AVG3M ON AVG3M.SPID = SP.PK_SEQ  " +
					"LEFT JOIN  " +
					"(  " +
					"	SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) ) AS LASTYEAR    " +
					"	FROM ERP_XUATKHO XK " +
					"	INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
					"	INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
					"	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >=  '" + (Integer.parseInt(ngaytd.substring(0, 4)) - 1) + "-" + ngaytd.substring(5, 7) + "-01' " +
					"	AND XK.NGAYCHOT <= '" + (Integer.parseInt(ngaytd.substring(0, 4)) - 1) + "-" + ngaytd.substring(5, 10) + "' " +
					"	AND XK.KHO_FK IN (100034,100035, 100036, 100037, 100038, 100039, 100040) " +
					"	GROUP BY SP.PK_SEQ   " +
					") " +
					"LASTYEAR ON AVG3M.SPID = SP.PK_SEQ  " +


					"WHERE KHOTT_FK IN (100034) " ;
			if(this.clId.length() > 0){
				query += " AND SP.CHUNGLOAI_FK = " + this.clId + " ";
			}
			query += " ORDER BY CL.PK_SEQ ";
			this.tkanRs_3 = this.db.get(query);*/
			
			
			if( this.clId.trim().length() > 0 )
			{
				query = "  select a.PK_SEQ, a.MA, a.TEN, b.DONVI,  " + 
						"  	isnull( ( select AVG(tonantoan) from ERP_KHOTT_SANPHAM where SANPHAM_FK = a.PK_SEQ and KHOTT_FK in ( " + this.clId + " ) ), 0 ) as tonantoan " + 
						"  from ERP_SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
						"  where a.PK_SEQ in ( select SANPHAM_FK from ERP_KHOTT_SANPHAM where KHOTT_FK in ( " + this.clId + " ) ) " + 
						"  order by a.TEN asc ";
				
				System.out.println("::: LAY SP: " + query);
				this.tkanRs_1 = db.get(query);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public boolean save() 
	{
		//Bên dự báo thì chỉ đang tính tồn an toàn của 2 kho: 100021, 100022 ( Kho thành phẩm Văn Lâm, Kho thành phẩm Kế Hoạch Hoàng Liệt )
		String query = "";
		/*if(this.sp_1 != null){
			for(int i = 0; i < this.sp_1.length; i++){
				query = "UPDATE ERP_KHOTT_SANPHAM SET TONANTOAN = " + this.tkan_1[i].replaceAll(",", "") + " " +
						"WHERE KHOTT_FK = 100023 AND SANPHAM_FK = " + this.sp_1[i] + " ";
				System.out.println(query);
				this.db.update(query);
			}
		}*/
		
		/*if(this.sp_2 != null){
			for(int i = 0; i < this.sp_2.length; i++){
				query = "UPDATE ERP_KHOTT_SANPHAM SET TONANTOAN = " + this.tkan_2[i].replaceAll(",", "") + " " +
						"WHERE KHOTT_FK = 100041 AND SANPHAM_FK = " + this.sp_2[i] + " ";
				this.db.update(query);
			}
		}
		
		if(this.sp_3 != null){
			for(int i = 0; i < this.sp_3.length; i++){
				query = "UPDATE ERP_KHOTT_SANPHAM SET TONANTOAN = " + this.tkan_3[i].replaceAll(",", "") + " " +
						"WHERE KHOTT_FK = 100034 AND SANPHAM_FK = " + this.sp_3[i] + " ";
				this.db.update(query);
			}
		}*/
		
		//Còn tồn an toàn của nguyên liệu?
	
		
		if(this.sp_1 != null && this.clId.trim().length() > 0 )
		{
			for(int i = 0; i < this.sp_1.length; i++)
			{
				query = "UPDATE ERP_KHOTT_SANPHAM SET TONANTOAN = " + this.tkan_1[i].replaceAll(",", "") + " " +
						"WHERE KHOTT_FK in ( " + this.clId + " ) AND SANPHAM_FK = " + this.sp_1[i] + " ";
				
				System.out.println("::: CAP NHAT TON AN TOAN: " + query);
				this.db.update(query);
			}
		}
		
		return true;
	}
	

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getClId() {
		
		return this.clId;
	}

	
	public void setClId(String clId) {
		
		this.clId = clId;
	}

	
	public ResultSet getClRs() {
		
		return this.clRs;
	}

	
	public void setClRs(ResultSet clRs) {
		
		this.clRs = clRs;
	}

	
	public void createRs()
	{
		
	}
	
	public void DbClose() 
	{
		try 
		{
			if(this.clRs != null) this.clRs.close();
			if(this.tkanRs_1 != null) this.tkanRs_1.close();
			if(this.tkanRs_2 != null) this.tkanRs_2.close();
			if(this.tkanRs_3 != null) this.tkanRs_3.close();
			if(this.tkanRs_4 != null) this.tkanRs_4.close();
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}

}
