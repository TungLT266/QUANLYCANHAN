package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class SSPerformance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SSPerformance() {
        super();
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformance.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":(String) session.getAttribute("userTen"));
	    
   	 	obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))==null?"":util.antiSQLInspection(request.getParameter("nppId")));
   	 	
   	 	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId")));
   	 	
	   	 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId")));
	   	 
	   	 obj.setMonth(util.antiSQLInspection(request.getParameter("month"))==null? "":util.antiSQLInspection(request.getParameter("month")));
	   	 
	   	 obj.setYear(util.antiSQLInspection(request.getParameter("year"))==null? "":util.antiSQLInspection(request.getParameter("year")));	   	 
	 	 
	   	 obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId")));	   	 
	   	 
	   	 obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId")));	 
	   	 	   	    	
		 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId")));		 
		
		 obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId"))==null? "":util.antiSQLInspection(request.getParameter("gsbhId")));
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformance.jsp";		 
		try{
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=GSThucHienChiTieuTT.xlsm");
		        OutputStream out = response.getOutputStream();
		        CreatePivotTable(out,obj);
			}	else{
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());		
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
	
	}
	
	private String setQuery( IStockintransit obj) {
		
		String query="";
		long numofDay = 0;
		geso.traphaco.center.db.sql.dbutils db= new geso.traphaco.center.db.sql.dbutils();
		try{
			query = "SELECT CONVERT(VARCHAR(10), DATEADD( s, -1, DATEADD( mm, DATEDIFF( m, 0, '" + obj.getYear() + "-" + obj.getMonth() + "-01' ) + 1, 0 ) ),120) AS LASTDAY";
			System.out.println(query);
		
			ResultSet	rs = db.get(query);
			rs.next();
			numofDay = Integer.parseInt(rs.getString("LASTDAY").substring(8,10));
			System.out.println(numofDay);
			rs.close();
		
		}catch(Exception er){
			
		}
		db.shutDown();
		
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
		query = 	"SELECT	KBH.PK_SEQ AS KBHID, KBH.TEN AS CHANNEL, cast(GSBH.MANHANVIEN as nvarchar(10)) AS GSBHID, GSBH.TEN AS GSBH,	" +
					"DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS BU, "	+
					"V.TEN AS REGION, KV.TEN AS AREA, "	+
					"ISNULL(CTNPP.SONGAYLAMVIEC,0) AS  WORKING_DAY, " +		
					"ISNULL(CTGS.SODONHANG,0) AS  CTSODONHANG, " +
					"ISNULL(CTNPP.SONHANVIEN,0) AS  SONHANVIEN, " +
					"CAST(ISNULL(CTGS.SODONHANG, 0) AS FLOAT) AS SODH, " +					
					"CAST(ISNULL(CTNPP.SKU, 0) AS FLOAT) AS SOSKU,	" +	
					"ISNULL(CTPRI.CHITIEU,0) AS CHITIEUPRI,	" +		
					"ISNULL(DS_PRI.DS_PRI,0) AS THUCDATPRI, " +
					"CASE WHEN ISNULL(CTPRI.CHITIEU,0) > 0 THEN 100*CAST(ISNULL(DS_PRI.DS_PRI,0) AS FLOAT)/CTPRI.CHITIEU ELSE 0	END AS PHANTRAMMTDPRI, " +
					"ISNULL(CTNPP.CHITIEU,0) AS MONTHLY_TARGET, " + 
					"CONVERT(FLOAT, DS.DOANHSO) -  ISNULL (TRAVE.TIENTRAVE,0) AS ACHIEVED, " +					
					"CASE WHEN CTNPP.CHITIEU > 0 THEN 100*CAST(DS.DOANHSO AS FLOAT)/CTNPP.CHITIEU ELSE 0	END AS PHANTRAMMTD, " +					
					"CASE WHEN CTNPP.SONGAYLAMVIEC > 0 THEN CAST(DS.SODONHANG AS FLOAT) ELSE 0	END AS NUMBEROFORDER, " +	
					"CASE WHEN DS.SODONHANG > 0 THEN CAST(DS.TONGSKU AS FLOAT) ELSE 0 END AS NUMBEROFSKU,	" +				
					"CASE WHEN DS.SODONHANG > 0 THEN CAST(CAST(DS.DOANHSO AS FLOAT)/DS.SODONHANG AS FLOAT) ELSE 0 END AS AVGVALUEPERORDER, " +
					"CASE WHEN DS.TONGSKU > 0 THEN CAST(CAST(DS.DOANHSO AS FLOAT)/DS.TONGSKU AS FLOAT)	ELSE 0	END AS AVGVALUEPERSKU,	" +				
					"CASE WHEN CTGS.SODONHANG > 0 THEN CAST(100*CAST(DS.SODONHANG AS FLOAT)/(CTGS.SODONHANG*ISNULL(CTNPP.SONGAYLAMVIEC,0)) AS FLOAT)	ELSE 0	END AS PHANTRAMORDER, " + 
					"CASE WHEN CTNPP.SKU > 0 THEN CAST(100*CAST(DS.TONGSKU/CAST(DS.SODONHANG AS FLOAT) AS FLOAT)/CTNPP.SKU AS FLOAT)	ELSE 0	END AS PHANTRAMSKU, " +
					" ISNULL(DATSKUIN.SKUDAT ,0) AS SKUDAT , ISNULL(CTSKUIN.TONGSKU,0)AS TONGSKU ,  " +	
					" CASE WHEN ISNULL(DATSKUIN.SKUDAT,0) >0 THEN   CAST(DATSKUIN.SKUDAT AS FLOAT)/ CAST( CTSKUIN.TONGSKU  AS FLOAT) *100   ELSE 0 END AS PHANTRAMSKUIN, " +
					" isnull(CTGS.dophu, '0') as ChiTieuDoPhu, isnull(DOPHU.dophu, '0') as ThucDatDoPhu,  " +
					" case when isnull(CTGS.dophu, '0') > 0 then 100 * isnull(DOPHU.dophu, '0') / CTGS.dophu else 0	end as phantramDoPhu, " +
					" case when isnull(CTGS.dophu, '0') > 0 then 100 * tct.thuong * isnull(dophu.dophu, '0') / CTGS.dophu else 0	end as thuongDoPhu, CTNPP.tinhthanh as tinhthanh, CTNPP.daidienkinhdoanh as daidienkinhdoanh " +
					"FROM " +
					"(	" +				
					"	SELECT	CTNPP.KENH_FK AS KBH_FK, CTNPP.DVKD_FK, KV.PK_SEQ AS KVID, GSBH.PK_SEQ AS GSBHID,	CTNPP.THANG, CTNPP.NAM , " +   						
					"	SUM(CTNPP.SONGAYLAMVIEC) AS SONGAYLAMVIEC, SUM(CTNPP_DDKD.CHITIEU) AS CHITIEU, " + 
					"	 COUNT(CTNPP.SONGAYLAMVIEC) AS SONHANVIEN, SUM(CTNPP_DDKD.SKU) AS SKU, TT.TEN as tinhthanh,ddkd.ten as daidienkinhdoanh " +				
					"	FROM CHITIEUNPP CTNPP	" +					
					"	INNER JOIN CHITIEUNPP_DDKD CTNPP_DDKD ON CTNPP.PK_SEQ = CTNPP_DDKD.CHITIEUNPP_FK " +
					"	INNER JOIN DDKD_GSBH DDKD_GSBH ON DDKD_GSBH.DDKD_FK = CTNPP_DDKD.DDKD_FK " +
					"	inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = DDKD_GSBH.DDKD_FK "+
					"	INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DDKD_GSBH.GSBH_FK AND GSBH.DVKD_FK = CTNPP.DVKD_FK AND GSBH.TRANGTHAI='1' " + 	
					"	INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = CTNPP.NHAPP_FK AND NPP_GS.GSBH_FK = GSBH.PK_SEQ " +	
					"	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = NPP_GS.NPP_FK " +
					"	INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK " +
					"	inner join TINHTHANH TT on TT.PK_SEQ = NPP.TINHTHANH_FK "+
					"	WHERE CTNPP.TRANGTHAI=1 AND CTNPP_DDKD.CHITIEU >0   AND CTNPP.THANG = '" + obj.getMonth() + "' AND CTNPP.NAM ='" + obj.getYear() + "' AND SUBSTRING(NPP_GS.NGAYKETTHUC,1, 4)='2100' AND NPP_GS.NGAYBATDAU <= '" + obj.getYear() + "-" + obj.getMonth() + "-01' " +					
					"	AND NPP_GS.NPP_FK IN "+ ut.quyen_npp(obj.getuserId()) + " AND CTNPP.KENH_FK IN " + ut.quyen_kenh(obj.getuserId()) + " " +									
					"	GROUP BY  CTNPP.KENH_FK , CTNPP.DVKD_FK, KV.PK_SEQ, GSBH.PK_SEQ,  CTNPP.THANG, CTNPP.NAM, TT.TEN, ddkd.ten " +
					") CTNPP" +
					"  LEFT JOIN  (   " +
					" SELECT CT.thang,ct.nam,ct.dvkd_fk,ct.kenh_fk AS KBH_FK,ct_gs.dophu ,ct_Gs.GSBH_FK,ct_Gs.donhang AS SODONHANG,ct_gs.chitieu FROM CHITIEU_SEC ct "+ 
					" INNER JOIN CHITIEUSEC_GSBH CT_GS ON CT.PK_SEQ=CT_GS.CHITIEUSEC_FK WHERE CT.THANG='"+obj.getMonth()+"' and ct.nam="+obj.getYear()+" ) " +
					" AS CTGS ON CTNPP.KBH_FK = CTGS.KBH_FK AND CTGS.DVKD_FK = CTNPP.DVKD_FK AND CTNPP.GSBHID = CTGS.GSBH_FK 	  " +				
					"INNER JOIN " +
					"(	" +									
					"	SELECT DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.GSBH_FK AS GSBHID, " + 
					"	SUM(DH_SP.SOLUONG * DH_SP.GIAMUA*0.873) AS 'DOANHSO', COUNT(DH.PK_SEQ) AS 'SODONHANG' ,  COUNT(DH_SP.SANPHAM_FK) AS 'TONGSKU' " +	
					"	FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK " + 	
					"	INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = DH_SP.SANPHAM_FK	" +	
					"	WHERE SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' AND DH.TRANGTHAI ='1' " + 	
					"	GROUP BY DH.KBH_FK, SP2.DVKD_FK, DH.GSBH_FK " +
					" ) DS ON CTNPP.KBH_FK = DS.KBH_FK AND DS.DVKD_FK = CTNPP.DVKD_FK AND CTNPP.GSBHID = DS.GSBHID  " +	
					//THEM PHAN DON HANG TRA VE
					"  LEFT JOIN (" +
					"	SELECT  DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.GSBH_FK AS GSBHID ,  "+   	
					"		SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA*0.873) *    "+
					"		 ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS TIENTRAVE  "+    
						"		FROM  DONHANGTRAVE DH    "+
						"	LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   "+  	
						"	LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK   "+
						"	INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+   
						"	WHERE DH.TRANGTHAI = 3   "+
						"		AND SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "'   "+ 	
						"		GROUP BY 	 DH.KBH_FK, SP2.DVKD_FK, DH.GSBH_FK  ) TRAVE" +
						" ON CTNPP.KBH_FK = TRAVE.KBH_FK AND TRAVE.DVKD_FK = CTNPP.DVKD_FK AND CTNPP.GSBHID = TRAVE.GSBHID   "+
					"LEFT JOIN	" +
					"(	" +	
					"	SELECT NH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, NPP_GS.GSBH_FK AS GSBHID, SUM(NH_SP.SOLUONG * NH_SP.GIANET-ISNULL(0,CKTT)) AS 'DS_PRI' " +					
					"	FROM NHAPHANG NH INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ = NH_SP.NHAPHANG_FK	" +				
					"	INNER JOIN SANPHAM SP2 ON SP2.MA = NH_SP.SANPHAM_FK	" +				
					"	INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = NH.NPP_FK " +
					"	WHERE SUBSTRING(NH.NGAYCHUNGTU,1,7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' AND NH.TRANGTHAI ='1' AND NH.LOAIHOADON=0 AND PK_SEQ NOT IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG)    " +					
					"	GROUP BY NH.KBH_FK, SP2.DVKD_FK, NPP_GS.GSBH_FK  " +
					" ) DS_PRI ON CTNPP.KBH_FK = DS_PRI.KBH_FK AND DS_PRI.DVKD_FK = CTNPP.DVKD_FK AND CTNPP.GSBHID = DS_PRI.GSBHID  " +
							" LEFT JOIN " +
					" ( " +
					"  SELECT THANG,NAM,DVKD_FK,KBH_FK,MA AS GSBHID,CHITIEUPRI as CHITIEU FROM CHITIEUPRI CT WHERE CHUCVU='SS' AND CT.THANG='"+obj.getMonth()+"' and ct.nam='"+obj.getYear()+"' "+
					"  )CTPRI ON CTPRI.KBH_FK = DS.KBH_FK	AND CTPRI.DVKD_FK = CTNPP.DVKD_FK AND CTPRI.GSBHID = DS.GSBHID 	" +	
					"  LEFT JOIN  (" +
					"  SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,NPP_GS.GSBH_FK  ,COUNT (DISTINCT CT_SKU.SKU ) AS TONGSKU "+
					"  from CHITIEUSKUIN CT INNER JOIN "+
					" CHITIEUSKUIN_SKU CT_SKU ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
					" INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK=CT_SKU.NPP_FK  " +
					" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK AND CT.DVKD_FK=GSBH.DVKD_FK "+
					" WHERE CT.TRANGTHAI=1 AND CT.THANG='"+obj.getMonth()+"' and CT.NAM='"+obj.getYear()+"' AND NPP_GS.NGAYBATDAU <='"+obj.getYear()+"-"+obj.getMonth()+"-01'  "+ 
					" AND SUBSTRING(NPP_GS.NGAYKETTHUC,1,4)='2100'  "+
					" GROUP BY CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,NPP_GS.GSBH_FK ) " +
					" CTSKUIN  ON CTSKUIN.KBH_FK=DS.KBH_FK AND CTSKUIN.DVKD_FK=DS.DVKD_FK AND  CTSKUIN.GSBH_FK=DS.GSBHID  " +
					" LEFT JOIN  " +
					"  ( " +
					" SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,CT.GSBH_FK,SUM(COUNTS) AS SKUDAT FROM  "+
					" (  "+
					" SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,NPP_GS.GSBH_FK  ,COUNT (DISTINCT CT_SKU.SKU ) AS COUNTS "+ 
					" from CHITIEUSKUIN CT INNER JOIN  CHITIEUSKUIN_SKU CT_SKU ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK   "+
					" INNER JOIN NHAPP_GIAMSATBH NPP_GS  ON NPP_GS.NPP_FK=CT_SKU.NPP_FK   " +
					" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK AND GSBH.DVKD_FK=CT.DVKD_FK" +
					"  WHERE CT.TRANGTHAI=1 "+
					" AND NPP_GS.NGAYBATDAU <='"+obj.getYear()+"-"+obj.getMonth()+"-01' and  CT.THANG='"+obj.getMonth()+"' and ct.nam='"+obj.getYear()+"' "+
					" AND SUBSTRING(NPP_GS.NGAYKETTHUC,1,4)='2100'    "+
					" GROUP BY CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,NPP_GS.GSBH_FK,CT_SKU.SKU  HAVING "+ 
					" SUM ((CT_SKU.CHITIEU)*0.9) <=   "+
					" (   "+
					" SELECT ISNULL( SUM(CAST(SOLUONG AS INT ) * ISNULL( GIANET , 0 ) -ISNULL(CKTT,0) ),0) "+  
					" FROM   NHAPHANG DH INNER JOIN NHAPHANG_SP DH_SP  ON  DH_SP.NHAPHANG_FK=DH.PK_SEQ "+  
					" INNER JOIN SANPHAM SP ON SP.MA=DH_SP.SANPHAM_FK "+
					" WHERE SP.DVKD_FK=CT.DVKD_FK AND DH.KBH_FK=CT.KBH_FK  AND SUBSTRING(DH.NGAYCHUNGTU,1,7) "+
					" LIKE '" + obj.getYear() + "-" + obj.getMonth() + "%' AND CT_SKU.SKU=SP.PK_SEQ AND DH.TRANGTHAI=1  AND DH.GSBH_FK=NPP_GS.GSBH_FK AND NH.LOAIHOADON=0 AND PK_SEQ NOT IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG)   "+  
					" ) "+
					" )  CT GROUP BY CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,CT.GSBH_FK   " +
					"  ) DATSKUIN ON DATSKUIN.KBH_FK=DS.KBH_FK AND DATSKUIN.DVKD_FK=DS.DVKD_FK AND  DATSKUIN.GSBH_FK=DS.GSBHID  " +
					" LEFT JOIN  ( " +
					"		select dh.gsbh_fk, dh.kbh_fk, count(distinct khachhang_fk) as 'dophu', '6' as tieuchi					" +
					"		from donhang dh				" +
					"		where substring(dh.ngaynhap, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "'  " +
					"  and dh.trangthai ='1'					" +
					"		group by dh.kbh_fk, dh.gsbh_fk " +
					"	) dophu on dophu.kbh_fk = DS.kbh_fk and dophu.gsbh_fk = DS.GSBHID " +
					" LEFT JOIN ( " +
					"		select tu, den, thuong, tieuchi from TIEUCHITINHTHUONG_CHITIET  " +
					"		where tieuchi = '6' and tieuchitinhthuong_fk in (select pk_seq from TIEUCHITINHTHUONG where loai = '2' and thang = '" + obj.getMonth() + "' and nam = '" + obj.getYear() + "') " +
					"	) tct  on tct.tieuchi = dophu.tieuchi and tct.tu <=  ( 100 * dophu.dophu / CTGS.DOPHU ) and ( 100 * dophu.dophu / CTGS.DOPHU ) <= tct.den  "+
					" INNER JOIN KENHBANHANG KBH ON DS.KBH_FK = KBH.PK_SEQ " +			
					" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = CTNPP.DVKD_FK " + 
					" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = CTNPP.GSBHID " +
					" LEFT JOIN KHUVUC KV ON KV.PK_SEQ = CTNPP.KVID " +
  					" LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK " +
  					" WHERE 1=1 "; 
			if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and v.pk_seq = '"+obj.getvungId()+"'";
			if(obj.getdvkdId().length() > 0)
				query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			if(obj.getgsbhId().length() > 0)
				query += " and gsbh.pk_seq = '"+obj.getgsbhId()+"'";
		System.out.println("Query SS khong duoc luan chuyen  : " + query);
		
		return query;
	}

	
	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {   
		try{
			String chuoi=getServletContext().getInitParameter("path") + "\\SS_ThucHienChiTieuTT.xlsm";
		
			FileInputStream fstream = new FileInputStream(chuoi);
			
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateStaticHeader(workbook,obj);
			
			int row = 2;
			
			FillData(workbook, obj, row);
			
			workbook.save(out);
			fstream.close();
	     }catch(Exception ex){
	    	 throw new Exception(ex.getMessage());
	     }	    
   }
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO THƯỞNG SS");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	   
	    cell = cells.getCell("DA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("DB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("DC1"); cell.setValue("Mien");
	    cell = cells.getCell("DD1"); cell.setValue("Vung");
	    cell = cells.getCell("DE1"); cell.setValue("MaGSBH");
	    cell = cells.getCell("DF1");cell.setValue("GiamSatBanHang");
	    cell = cells.getCell("DG1"); cell.setValue("NgayLamViec");
	    cell = cells.getCell("DH1"); cell.setValue("ChiTieuSellsIn");
	    cell = cells.getCell("DI1"); cell.setValue("ThucDatSellsIn");
	    cell = cells.getCell("DJ1"); cell.setValue("%SellsIn");
	    cell = cells.getCell("DK1"); cell.setValue("ThuongDSSellsIn");
	    
	    cell = cells.getCell("DL1"); cell.setValue("ChiTieuSec");
	    cell = cells.getCell("DM1"); cell.setValue("ThucDatSec");
	    cell = cells.getCell("DN1"); cell.setValue("%Sec");
	    cell = cells.getCell("DO1"); cell.setValue("ThuongDSSec");

	    cell = cells.getCell("DP1"); cell.setValue("ChiTieuSoDonHang");
	    cell = cells.getCell("DQ1"); cell.setValue("ThucDatSoDonHang");	
	    cell = cells.getCell("DR1"); cell.setValue("%SoDonHang");	
	    cell = cells.getCell("DS1"); cell.setValue("ThuongSoDonHang");

	    cell = cells.getCell("DT1"); cell.setValue("ChiTieuSoSKU");	    
	    cell = cells.getCell("DU1"); cell.setValue("ThucDatSoSKU");
	    cell = cells.getCell("DV1"); cell.setValue("%SoSKU");	
	    cell = cells.getCell("DW1"); cell.setValue("ThuongSoSKU");	
	    cell = cells.getCell("DX1"); cell.setValue("ChiTieuSKUIN");	    
	    cell = cells.getCell("DY1"); cell.setValue("ThucDatSKUIN");
	    cell = cells.getCell("DZ1"); cell.setValue("%SoSKUIN");	
	    cell = cells.getCell("EA1"); cell.setValue("ThuongSKUIN");	
	    
	    
	    //Thuong DoPhu
	    cell = cells.getCell("EB1"); cell.setValue("ChiTieuDoPhu");	    
	    cell = cells.getCell("EC1"); cell.setValue("ThucDatDoPhu");
	    cell = cells.getCell("ED1"); cell.setValue("%DoPhu");	
	    cell = cells.getCell("EE1"); cell.setValue("ThuongDoPhu");	
	
	    
	    cell = cells.getCell("EF1"); cell.setValue("TongThuong");
	    cell = cells.getCell("EG1"); cell.setValue("tinhthanh");
	    cell = cells.getCell("EH1"); cell.setValue("daidienkinhdoanh");
	    
	}

	private void FillData(Workbook workbook,IStockintransit obj, int row)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
				
		dbutils db = new dbutils();	
		String query = setQuery( obj);
		
		System.out.println("1.Tieu Chi Thuong GSBH: " + query);
		db.update("SET DATEFORMAT ymd");
		ResultSet rs = db.get(query);	
		
		int indexRow = row;
		try 
			{				
				Cell cell = null;
				float phantramMTDPri = 0;
				float phantramMTD = 0;
				float phantramDH = 0;
				float phantramSKU = 0;
				
				float ThuongDoanhSo = 0;
				float ThuongDoanhSoPri = 0;
				float ChiTieuSoDonHang = 0;				
				float ThuongSoDonHang = 0;
				float ChiTieuSoSKU = 0;
				float ThuongSoSKU = 0;
				int tmp = 0;
				int level = 0;
				if(rs != null){
				while(rs.next())
				{ 				
					
				    cell = cells.getCell("DA" + Integer.toString(indexRow)); cell.setValue(rs.getString("Channel"));
				    cell = cells.getCell("DB" + Integer.toString(indexRow)); cell.setValue(rs.getString("BU"));
					cell = cells.getCell("DC" + Integer.toString(indexRow)); cell.setValue(rs.getString("Region"));
					cell = cells.getCell("DD" + Integer.toString(indexRow)); cell.setValue(rs.getString("Area"));					
					cell = cells.getCell("DE" + Integer.toString(indexRow)); cell.setValue(rs.getString("GSBHID"));
					cell = cells.getCell("DF" + Integer.toString(indexRow)); cell.setValue(rs.getString("GSBH"));
					
					if(rs.getFloat("SONHANVIEN") > 0){
						cell = cells.getCell("DG" + Integer.toString(indexRow)); cell.setValue(Math.round(rs.getFloat("Working_day")/rs.getFloat("SONHANVIEN")) );
					}else{
						cell = cells.getCell("DG" + Integer.toString(indexRow)); cell.setValue(0) ;
					}
					
					cell = cells.getCell("DH" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("ChitieuPri"));
					cell = cells.getCell("DI" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("THUCDATPRI"));
					cell = cells.getCell("DJ" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("PHANTRAMMTDPRI"));
					cell = cells.getCell("DK" + Integer.toString(indexRow)); cell.setValue(0);


					cell = cells.getCell("DL" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("Monthly_Target"));
					cell = cells.getCell("DM" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("Achieved"));
					
					//TB Phaiban/Ngay
					
					phantramMTDPri = rs.getFloat("phantramMTDPri");
					phantramMTD = rs.getFloat("phantramMTD");
					
					cell = cells.getCell("DN" + Integer.toString(indexRow)); cell.setValue(phantramMTD);
					
					String kbh_fk = rs.getString("kbhId");					
					System.out.println("KBH: " + kbh_fk);
					
					String dvkd_fk = rs.getString("dvkdId");
					System.out.println("DVKD: " + dvkd_fk);
					
					query = 	"SELECT TCT_CT.* FROM TIEUCHITINHTHUONG TCT INNER JOIN " +
									"TIEUCHITINHTHUONG_CHITIET TCT_CT ON TCT.PK_SEQ = TCT_CT.TIEUCHITINHTHUONG_FK " +
									"WHERE TCT.DVKD_FK='" + dvkd_fk + "' AND TCT.KBH_FK = '" + kbh_fk + "' AND " +
									"TCT.THANG ='" + obj.getMonth() + "' AND TCT.NAM ='" + obj.getYear() + "' AND TCT.LOAI='2' " +
									"ORDER BY TCT_CT.TIEUCHI, TCT_CT.STT";
					
					System.out.println("___TCT chi tiet: " + query);
					ResultSet tieuchi = db.get(query);

					tmp = 0;
					ThuongDoanhSoPri = 0;
					boolean exit;
					
					if(tieuchi.next()){
						
						// Thuong doanh so mua
						exit = false;
						level = 0;
						
						for (int i = 1; i <=5; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							if(!exit){
								if(tieuchi.getString("toantu").equals("<")){
									if(phantramMTDPri > tieuchi.getFloat("Tu") & phantramMTDPri < tieuchi.getFloat("Den")){
										if (i > 1)
											ThuongDoanhSoPri = (phantramMTDPri/100)*tieuchi.getFloat("Thuong");
										else
											ThuongDoanhSoPri = 0;
										
										System.out.println("Thuong doanh so: " +  ThuongDoanhSoPri);
									}else if(phantramMTDPri > tieuchi.getFloat("Tu") & i == 5){   	// Truong hop dat > 200%
										ThuongDoanhSoPri = (phantramMTDPri/100)*tieuchi.getFloat("Thuong");
									}
								}
							}
							tieuchi.next();
						}
					
						cell = cells.getCell("DK" + Integer.toString(indexRow)); cell.setValue(ThuongDoanhSoPri);
					
						// Thuong doanh so ban
						ThuongDoanhSo = 0;
						for (int i = 1; i <=5; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							if(!exit){
								if(tieuchi.getString("toantu").equals("<")){
									if(phantramMTD > tieuchi.getFloat("Tu") & phantramMTD < tieuchi.getFloat("Den")){
										if(i > 1)
											ThuongDoanhSo = (phantramMTD/100)*tieuchi.getFloat("Thuong");
										else
											ThuongDoanhSo = 0;
										
										// Ghi nhan muc(level) dat doanh so, neu duoc thuong thi exit la true, o duoc thuong thi exit la false
										level = i;
										if(i > 1) exit = true;
										
										System.out.println("Thuong doanh so: " +  ThuongDoanhSo);
									}else if(phantramMTD > tieuchi.getFloat("Tu") & i == 5){ 		// Truong hop dat > 200%
										level = i;
										exit = true;
										ThuongDoanhSo = (phantramMTD/100)*tieuchi.getFloat("Thuong");
									}
								}
							}
							tieuchi.next();
						}
					
						cell = cells.getCell("DO" + Integer.toString(indexRow)); cell.setValue(ThuongDoanhSo);
						if(rs.getFloat("SONHANVIEN") > 0){
							ChiTieuSoDonHang = rs.getFloat("Working_day")*rs.getFloat("CTSODONHANG")/rs.getFloat("SONHANVIEN");
						}else{
							ChiTieuSoDonHang = 0;
						}
						cell = cells.getCell("DP" + Integer.toString(indexRow)); cell.setValue(Math.round(ChiTieuSoDonHang));					
						cell = cells.getCell("DQ" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfOrder"));
					
						if (ChiTieuSoDonHang > 0)
							phantramDH = 100*rs.getFloat("NumberOfOrder")/ChiTieuSoDonHang;
						else
							phantramDH = 0;
						
						System.out.println("Phan Tram don hang : "+phantramDH);
						
						cell = cells.getCell("DR" + Integer.toString(indexRow)); cell.setValue(phantramDH);
					
						ThuongSoDonHang = 0;

						// Neu exit=true, nghia la secondary sales > 85%, thi KPI nay moi duoc xem xet

												
						// muc thuong cua KPI So don hang khong duoc vuot hon muc thuong Doanh so mua dat duoc
						for (int i = 1; i <= 5; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							System.out.println("Muc : "+level);
							if (i<=level){
								if(tieuchi.getString("toantu").equals("<")){
									if(phantramDH > tieuchi.getFloat("Tu") & phantramDH < tieuchi.getFloat("Den")){
										ThuongSoDonHang =phantramDH/100*  tieuchi.getFloat("Thuong");
									}else if(phantramDH > tieuchi.getFloat("Tu") & i == level){
										ThuongSoDonHang =phantramDH/100* tieuchi.getFloat("Thuong");
									}
									
									System.out.println("Thuong So Don Hang: " +  ThuongSoDonHang);
								}
								
							}
							tieuchi.next();						
							
						}
						
						cell = cells.getCell("DS" + Integer.toString(indexRow)); cell.setValue(ThuongSoDonHang);

						if(rs.getFloat("SONHANVIEN") > 0){
							ChiTieuSoSKU = (rs.getFloat("Working_day")*rs.getFloat("SOSKU")*rs.getFloat("CTSODONHANG")/rs.getFloat("SONHANVIEN"))/rs.getFloat("SONHANVIEN");
						}else{
							ChiTieuSoSKU = 0;
						}
						cell = cells.getCell("DT" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoSKU);					
						cell = cells.getCell("DU" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfSKU"));
						
						if(ChiTieuSoSKU >0){
							phantramSKU = 100*rs.getFloat("NumberOfSKU")/ChiTieuSoSKU;
						}else{
							phantramSKU = 0;
						}
						cell = cells.getCell("DV" + Integer.toString(indexRow)); cell.setValue(phantramSKU);
					
						ThuongSoSKU = 0;

						// Neu exit=true, nghia la secondary sales > 85%, thi KPI nay moi duoc xem xet
												
						for (int i = 1; i <= 5; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							if(i <= level){
								if(tieuchi.getString("toantu").equals("<")){
									if(phantramSKU > tieuchi.getFloat("Tu") & phantramSKU < tieuchi.getFloat("Den")){
										ThuongSoSKU =phantramSKU/100* tieuchi.getFloat("Thuong");
									}
									else if(phantramSKU > tieuchi.getFloat("Tu") & i == level){
										ThuongSoSKU = phantramSKU/100 *tieuchi.getFloat("Thuong");
									}
									System.out.println("Thuong SKU: " +  ThuongSoSKU);
								}								
							}
							tieuchi.next();						
						}
					
						cell = cells.getCell("DW" + Integer.toString(indexRow)); cell.setValue(ThuongSoSKU);
						
						//Thuong SKU IN
						float chitieuskuin = rs.getFloat("TONGSKU");
						cell = cells.getCell("DX" + Integer.toString(indexRow)); cell.setValue(chitieuskuin);
						
						float datSKUIn=rs.getFloat("SKUDAT");
						
						cell = cells.getCell("DY" + Integer.toString(indexRow)); cell.setValue( datSKUIn);
						
						float phantramskuin=rs.getFloat("PHANTRAMSKUIN");
						
						cell = cells.getCell("DZ" + Integer.toString(indexRow)); cell.setValue(phantramskuin);
						//1 diem là 50.000
						float ThuongSoSKUIn =0;
						for (int i = 1; i <= 3; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							
								if(tieuchi.getString("toantu").equals("<")){
									if(phantramskuin > tieuchi.getFloat("Tu") & phantramskuin < tieuchi.getFloat("Den")){
										ThuongSoSKUIn = Math.round(phantramskuin/100 * tieuchi.getFloat("Thuong"));
									}
									else if(phantramskuin > tieuchi.getFloat("Tu") && i==3){
										ThuongSoSKUIn =Math.round(tieuchi.getFloat("Thuong"));
									}
									System.out.println("Thuong SKUIN : " +  ThuongSoSKU);
								}								

							tieuchi.next();						
						}
						
						cell = cells.getCell("EA" + Integer.toString(indexRow)); cell.setValue(Math.round(ThuongSoSKUIn));
						
						
						//Thuong DoPhu
						float ThuongDoPhu = rs.getFloat("thuongDoPhu");	
						cell = cells.getCell("EB" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("ChiTieuDoPhu"));
						cell = cells.getCell("EC" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("ThucDatDoPhu"));
						cell = cells.getCell("ED" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("PhanTramDoPhu"));
						cell = cells.getCell("EE" + Integer.toString(indexRow)); cell.setValue(ThuongDoPhu);
						
						cell = cells.getCell("EF" + Integer.toString(indexRow)); cell.setValue(ThuongDoanhSoPri + ThuongDoanhSo + ThuongSoDonHang + ThuongSoSKU+ ThuongSoSKUIn + ThuongDoPhu);
					}
					else{
						cell = cells.getCell("DO" + Integer.toString(indexRow)); cell.setValue(0);

						if(rs.getFloat("SONHANVIEN") > 0){
							ChiTieuSoDonHang = rs.getFloat("Working_day")*rs.getFloat("CTSODONHANG")/rs.getFloat("SONHANVIEN");
						}else{
							ChiTieuSoDonHang = 0;
						}
						
						cell = cells.getCell("DP" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoDonHang);					
						cell = cells.getCell("DQ" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfOrder"));
					
						phantramDH = 100*rs.getFloat("NumberOfOrder")/ChiTieuSoDonHang;
						cell = cells.getCell("DR" + Integer.toString(indexRow)); cell.setValue(phantramDH);
						cell = cells.getCell("DS" + Integer.toString(indexRow)); cell.setValue(0);
						
						if(rs.getFloat("SONHANVIEN")>0){
							ChiTieuSoSKU = (rs.getFloat("Working_day")*rs.getFloat("SOSKU")*rs.getFloat("CTSODONHANG")/rs.getFloat("SONHANVIEN"))/rs.getFloat("SONHANVIEN");
						}else{
							ChiTieuSoSKU = 0;
						}
						cell = cells.getCell("DT" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoSKU);					
						cell = cells.getCell("DU" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfSKU"));

						if(ChiTieuSoSKU > 0){
							phantramSKU = 100*rs.getFloat("NumberOfSKU")/ChiTieuSoSKU;
						}else{
							phantramSKU = 0;
						}
						cell = cells.getCell("DV" + Integer.toString(indexRow)); cell.setValue(phantramSKU);										
						cell = cells.getCell("DW" + Integer.toString(indexRow)); cell.setValue(0);
						
						//thuong sku in
						float chitieuskuin = rs.getFloat("TONGSKU");
						cell = cells.getCell("DX" + Integer.toString(indexRow)); cell.setValue(chitieuskuin);
						
						float datSKUIn=rs.getFloat("SKUDAT");
						
						cell = cells.getCell("DY" + Integer.toString(indexRow)); cell.setValue(datSKUIn);
						
						float phantramskuin=rs.getFloat("PHANTRAMSKUIN");
						
						cell = cells.getCell("DZ" + Integer.toString(indexRow)); cell.setValue(phantramskuin);
						
						
						cell = cells.getCell("EA" + Integer.toString(indexRow)); cell.setValue(0);
						
						//Do phu
						cell = cells.getCell("EB" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("ChiTieuDoPhu"));
						cell = cells.getCell("EC" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("ThucDatDoPhu"));
						cell = cells.getCell("ED" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("PhanTramDoPhu"));
						cell = cells.getCell("EE" + Integer.toString(indexRow)); cell.setValue(0);
						
						cell = cells.getCell("EF" + Integer.toString(indexRow)); cell.setValue(0);
						cell = cells.getCell("EG" + Integer.toString(indexRow)); cell.setValue(rs.getString("tinhthanh"));						
						cell = cells.getCell("EH" + Integer.toString(indexRow)); cell.setValue(rs.getString("daidienkinhdoanh"));
						
						
					}
					
					indexRow++;
					if(tieuchi != null) 
						tieuchi.close();
				}
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}

		    	
			}catch(java.sql.SQLException err){
				System.out.println(err.toString());
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
	}


}
