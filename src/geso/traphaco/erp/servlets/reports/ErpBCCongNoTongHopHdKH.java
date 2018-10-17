package geso.traphaco.erp.servlets.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;

public class ErpBCCongNoTongHopHdKH extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCCongNoTongHopHdKH() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		obj.setErpCongtyId(ctyId);
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppdangnhap(util.getUserId(querystring));
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTongHopCongNoHdKH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		String userId = (String) session.getAttribute("userId");

		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		IStockintransit obj = new Stockintransit();
		obj.setErpCongtyId(ctyId);
		obj.setuserId(userId);
		obj.setnppdangnhap(userId);
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kbhid")));
		 
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")));
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String kh_khongcosolieu= request.getParameter("kh_khongcosolieu");
		if(kh_khongcosolieu==null){
			kh_khongcosolieu="0";
		}
		
		obj.settype(kh_khongcosolieu);
		
		
		String[] nkhIds = request.getParameterValues("nkhIds");
		String str2_ = "";
		String str_="";
		if(nkhIds != null)
		{
			for(int i = 0; i < nkhIds.length; i++)
			{
				str_ += nkhIds[i] + ",";
			}
			if(str_.length() > 0)
				str2_ = str_.substring(0, str_.length() - 1);
			
			obj.setNhomKhId(str2_);
		}
		
		String[] khIds = request.getParameterValues("khIds");
		String str2 = "";
		String str="";
		if(khIds != null)
		{
			for(int i = 0; i < khIds.length; i++)
			{
				str += khIds[i] + ",";
			}
			if(str.length() > 0)
				str2 = str.substring(0, str.length() - 1);
			
			obj.SetKHid(str2);
		}

		obj.InitErp();
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTongHopCongNoHdKH.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTongHopCongNoKH.xlsm");
			

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
	        	e.printStackTrace();
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}else{
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}

		
	}
	
	/*public String getQuery(IStockintransit obj)
	{
//		Utility util = new Utility();
		
//		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
		
		
		String month = obj.gettungay().substring(5, 7);
		String year = obj.gettungay().substring(0, 4);
		int lastyear = Integer.parseInt(year) - 1;
		int lastmonth = 0;
		
		if (Integer.parseInt(month) > 1){
			lastmonth = Integer.parseInt(month) - 1;
		}else{
			lastmonth = 12;
		}
			
		int thangdauky = 0;
		int namdauky = 0;
		if(lastmonth != 12){
			thangdauky = lastmonth;
			namdauky = Integer.parseInt(year);
		}else{
			thangdauky = lastmonth;
			namdauky = lastyear;
		}
		

		String query = 
				"SELECT ISNULL(PK_SEQ, '') AS PK_SEQ, ISNULL(MA, '') AS MA, ISNULL(TEN, '') AS TEN, DUNODAUKY, TANGTRONGKY, GIAMTRONGKY, ISNULL((DUNODAUKY + TANGTRONGKY - GIAMTRONGKY), 0) AS DUNOCUOIKY\n" + 
				"FROM\n" + 
				"(\n" + 
				"	SELECT PK_SEQ, MA, TEN, ISNULL(NODAUKY, 0) AS DUNODAUKY, ISNULL(TANGTRONGKY, 0) AS TANGTRONGKY, ISNULL(GIAMTRONGKY, 0) AS GIAMTRONGKY\n" + 
				"\n" + 
				"FROM\n" + 
				"(\n" + 
				"	SELECT 'KH'+CAST(PK_SEQ AS VARCHAR(50)) AS PK_SEQ, MAFAST AS MA, TEN\n" + 
				"	FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG \n" + 
				"	UNION\n" + 
				"	SELECT 'NPP'+CAST(PK_SEQ AS VARCHAR(50)) AS PK_SEQ, ISNULL(MA, ''), TEN\n" + 
				"	FROM NHAPHANPHOI \n" + 
				"	WHERE PK_SEQ != 1\n" + 
				"	UNION\n" + 
				"	SELECT 'NV'+CAST(PK_SEQ AS VARCHAR(50)) AS PK_SEQ, '', TEN\n" + 
				"	FROM NHANVIEN \n" + 
				") DANHMUC\n" + 
				"LEFT JOIN \n" + 
				"(\n" + 
				
				"	SELECT \n" + 
				"		CASE \n" + 
				"			WHEN ISNPP = 0 THEN 'KH'+CAST(MDT AS VARCHAR(50))\n" + 
				"			WHEN ISNPP = 1 THEN 'NPP'+CAST(MDT AS VARCHAR(50))\n" + 
				"			WHEN ISNPP = 2 THEN 'NV'+CAST(MDT AS VARCHAR(50))\n" + 
				"		END AS MADOITUONG , ISNULL(NODAUKY, 0) AS NODAUKY\n" + 
				"	FROM\n" + 
				"	(\n" +
				" 		SELECT MDT,ISNPP,SUM(NODAUKY) AS NODAUKY \n " + 
				" 		FROM ( \n " + 
				"		SELECT TAIKHOANKT_FK, DOITUONG, MADOITUONG AS MDT, ISNULL(ISNULL(GIATRINOVND, 0) - ISNULL(GIATRICOVND, 0), 0) AS NODAUKY, ISNULL(ISNPP, 0) AS ISNPP\n" + 
				"		FROM ERP_TAIKHOAN_NOCO_KHOASO\n" + 
				"		WHERE THANG = "+thangdauky+" AND NAM = "+namdauky+"\n" + 
				"		AND TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1)\n" + 
				"		AND DOITUONG = N'Khách hàng'\n" + 
				" 		UNION ALL \n " + 
				" 		SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ROUND(ISNULL(NO, 0),0)) - SUM(ROUND(ISNULL(CO, 0),0)) AS NODAUKY, ISNULL(ISNPP, 0) AS ISNPP \n " + 
				" 		FROM ERP_PHATSINHKETOAN \n " + 
				" 		WHERE NGAYGHINHAN >='"+year+"-"+month+"-01' AND NGAYGHINHAN < '"+ obj.gettungay() +"' \n " + 
				" 		AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1) \n " + 
				" 		AND DOITUONG = N'Khách hàng'  \n " + 
				" 		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP \n " + 
				" 		UNION ALL \n " + 
				" 		SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ROUND(ISNULL(NO, 0),0)) - SUM(ROUND(ISNULL(CO, 0),0)) AS NODAUKY, ISNULL(ISNPP, 0) AS ISNPP \n " + 
				" 		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN \n " + 
				" 		WHERE NGAYGHINHAN >='"+year+"-"+month+"-01' AND NGAYGHINHAN < '"+ obj.gettungay() +"' \n " + 
				" 		AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1) \n " + 
				" 		AND DOITUONG = N'Khách hàng'  \n " + 
				" 		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP \n " + 
				" 		)DK \n " + 
				" 		GROUP BY MDT,ISNPP \n " + 
				"	) TEMP\n" + 
				") DAUKY ON DAUKY.MADOITUONG = DANHMUC.PK_SEQ\n" + 
				"LEFT JOIN\n" + 
				"(\n" + 
				"	SELECT \n" + 
				"		CASE \n" + 
				"			WHEN ISNPP = 0 THEN 'KH'+CAST(MDT AS VARCHAR(50))\n" + 
				"			WHEN ISNPP = 1 THEN 'NPP'+CAST(MDT AS VARCHAR(50))\n" + 
				"			WHEN ISNPP = 2 THEN 'NV'+CAST(MDT AS VARCHAR(50))\n" + 
				"		END AS MADOITUONG , TANGTRONGKY, GIAMTRONGKY\n" + 
				"	FROM\n" + 
				"	(\n" + 
				"		SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ROUND(ISNULL(NO, 0),0)) AS TANGTRONGKY, SUM(ROUND(ISNULL(CO, 0),0)) AS GIAMTRONGKY, ISNULL(ISNPP, 0) AS ISNPP\n" + 
				"		FROM ERP_PHATSINHKETOAN\n" + 
				"		WHERE NGAYGHINHAN BETWEEN '"+obj.gettungay()+"' AND '"+obj.getdenngay()+"'\n" + 
				"		AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1)\n" + 
				"		AND DOITUONG = N'Khách hàng' \n" + 
				"		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP\n" + 
				"	UNION ALL \n "+
				"		SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ISNULL(NO, 0)) AS TANGTRONGKY, SUM(ISNULL(CO, 0)) AS GIAMTRONGKY, ISNULL(ISNPP, 0) AS ISNPP\n" + 
				"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN\n" + 
				"		WHERE NGAYGHINHAN BETWEEN '"+obj.gettungay()+"' AND '"+obj.getdenngay()+"'\n" + 
				"		AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1)\n" + 
				"		AND DOITUONG = N'Khách hàng' \n" + 
				"		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP\n" + 

				"	) TEMP\n" + 
				") PHATSINH ON PHATSINH.MADOITUONG = DANHMUC.PK_SEQ\n" + 
				")A\n" + 
				"WHERE (DUNODAUKY != 0 OR TANGTRONGKY != 0 OR GIAMTRONGKY != 0)" ;
		System.out.println(query);
	    return query;   
	}*/
	public String getQueryBangTam(IStockintransit obj, geso.traphaco.erp.db.sql.dbutils db) {
		//Tao bang tam dau ky va phat sinh
		int thangdauky ;
		int namdauky ;
		int lastmonth ;
		int lastyear ;
		lastyear = Integer.parseInt(obj.gettungay().substring(0,3)) - 1;
		if(Integer.parseInt(obj.gettungay().substring(5, 6)) >1 )
			lastmonth = Integer.parseInt(obj.gettungay().substring(5, 6)) - 1;
		else
			lastmonth = 12;
		if(lastmonth != 12){
			thangdauky = lastmonth;
			namdauky = Integer.parseInt(obj.gettungay().substring(0,3));
		}else{
			thangdauky = lastmonth;
			namdauky = lastyear;
		}
		int thangks ;
		int namks ;
		//Chọn tháng khóa sổ gần nhất
		String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
				 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
				 "WHERE TK.NPP_FK =  1" + 
				 "ORDER BY NAM DESC,THANG DESC";
		ResultSet rsKhoaSo = db.get(sqlKhoaSo);
		int thangKS = 0;
		int namKS = 0;
		if(rsKhoaSo!= null){
			try {
				while(rsKhoaSo.next()){
					thangKS = rsKhoaSo.getInt("THANG");
					namKS = rsKhoaSo.getInt("NAM");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String error = "[ERROR 1.1] Không thể lấy tháng KS gần nhất : "+ e.toString();
				System.out.println(error);
				obj.setMsg(error);
			}
		}
		if(thangKS >= thangdauky && namKS >= namdauky){
			
		}else{
			thangdauky = thangKS;
			namdauky = namKS;
		}
		
		String thangDauky = "0"+ thangdauky;
		thangDauky = thangDauky.substring(thangDauky.length() -2);
		String query = 
				
				"IF OBJECT_ID('tempdb.dbo.#DAUKY') IS NOT NULL DROP TABLE #DAUKY\r\n" + 
				"\r\n" + 
				"SELECT \r\n" + 
				"		CASE \r\n" + 
				"			WHEN ISNPP = 0 THEN 'KH'+CAST(MDT AS VARCHAR(50))\r\n" + 
				"			WHEN ISNPP = 1 THEN 'NPP'+CAST(MDT AS VARCHAR(50))\r\n" + 
				"			WHEN ISNPP = 2 THEN 'NV'+CAST(MDT AS VARCHAR(50))\r\n" + 
				"		END AS MADOITUONG , SUM(ISNULL(NODAUKY, 0)) AS NODAUKY\r\n" + 
				"INTO #DAUKY\r\n" + 
				"FROM \r\n" + 
				"(\r\n" + 
				"	SELECT TAIKHOANKT_FK, DOITUONG, MADOITUONG AS MDT, SUM(ISNULL(GIATRINOVND, 0)) - SUM(ISNULL(GIATRICOVND, 0)) AS NODAUKY, ISNULL(ISNPP, 0) AS ISNPP\r\n" + 
				"		FROM ERP_TAIKHOAN_NOCO_KHOASO\r\n" + 
				"		WHERE THANG = "+thangdauky+" AND NAM = "+namdauky+"\r\n" + 
				"		AND TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1)\r\n" + 
				"		AND DOITUONG = N'Khách hàng'\r\n" + 
				"		GROUP BY TAIKHOANKT_FK, DOITUONG, MADOITUONG, ISNPP\r\n" + 
				"	UNION ALL\r\n" + 
				"	SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ISNULL(NO, 0)) - SUM(ISNULL(CO, 0)) AS NODAUKY, ISNULL(ISNPP, 0) AS ISNPP\r\n" + 
				"	FROM ERP_PHATSINHKETOAN PS\r\n" + 
				"	WHERE PS.NGAYGHINHAN > (SELECT DATEADD(MONTH, (("+namdauky+" - 1900) * 12) + "+thangdauky+", -1)) AND PS.NGAYGHINHAN <  '"+obj.gettungay()+"' AND\r\n" + 
				"	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1)\r\n" + 
				"	AND DOITUONG = N'Khách hàng' \r\n" + 
				"		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP\r\n" + 
				"	UNION ALL\r\n" + 
				"	SELECT * FROM OPENQUERY("+Utility.prefixLinkDMS+", 'SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ISNULL(NO, 0)) - SUM(ISNULL(CO, 0)) AS NODAUKY, ISNULL(ISNPP, 0) AS ISNPP\r\n" + 
				"	FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN PS\r\n" + 
				"	WHERE PS.NGAYGHINHAN > (SELECT DATEADD(MONTH, (("+namdauky+" - 1900) * 12) + "+thangdauky+", -1)) AND PS.NGAYGHINHAN  < ''"+obj.gettungay()+"'' AND\r\n" + 
				"	TAIKHOAN_FK IN (SELECT PK_SEQ FROM "+Utility.prefixReportDMS+"ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE ''131%'' AND NPP_FK = 1)\r\n" + 
				"	AND DOITUONG = N''Khách hàng'' \r\n" + 
				"	GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP')\r\n" + 
				") DAUKY\r\n" + 
				"GROUP BY MDT, ISNPP\r\n" + 
				"\r\n" + 
				"IF OBJECT_ID('tempdb.dbo.#PHATSINH') IS NOT NULL DROP TABLE #PHATSINH\r\n" + 
				"SELECT * INTO #PHATSINH\r\n" + 
				"FROM\r\n" + 
				"(\r\n" + 
				"	SELECT \r\n" + 
				"		CASE \r\n" + 
				"			WHEN ISNPP = 0 THEN 'KH'+CAST(MDT AS VARCHAR(50))\r\n" + 
				"			WHEN ISNPP = 1 THEN 'NPP'+CAST(MDT AS VARCHAR(50))\r\n" + 
				"			WHEN ISNPP = 2 THEN 'NV'+CAST(MDT AS VARCHAR(50))\r\n" + 
				"		END AS MADOITUONG , SUM(TANGTRONGKY) TANGTRONGKY, SUM(GIAMTRONGKY) GIAMTRONGKY\r\n" + 
				"	FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ROUND(ISNULL(NO, 0),0)) AS TANGTRONGKY, SUM(ROUND(ISNULL(CO, 0),0)) AS GIAMTRONGKY, ISNULL(ISNPP, 0) AS ISNPP\r\n" + 
				"		FROM ERP_PHATSINHKETOAN\r\n" + 
				"		WHERE NGAYGHINHAN BETWEEN '"+obj.gettungay()+"' AND '"+obj.getdenngay()+"'\r\n" + 
				"		AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK = 1)\r\n" + 
				"		AND DOITUONG = N'Khách hàng' \r\n" + 
				"		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP\r\n" + 
				"	UNION ALL \r\n" + 
				" 		SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", 'SELECT TAIKHOAN_FK, DOITUONG, MADOITUONG AS MDT, SUM(ROUND(ISNULL(NO, 0),0)) AS TANGTRONGKY, SUM(ROUND(ISNULL(CO, 0),0)) AS GIAMTRONGKY, ISNULL(ISNPP, 0) AS ISNPP\r\n" + 
				"		FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN\r\n" + 
				"		WHERE NGAYGHINHAN BETWEEN ''"+obj.gettungay()+"'' AND ''"+obj.getdenngay()+"''\r\n" + 
				"		AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM "+Utility.prefixReportDMS+"ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE ''131%'' AND NPP_FK = 1)\r\n" + 
				"		AND DOITUONG = N''Khách hàng'' \r\n" + 
				"		GROUP BY TAIKHOAN_FK, DOITUONG, MADOITUONG, ISNPP')\r\n" + 
				"	) TEMP\r\n" + 
				"	GROUP BY MDT, ISNPP\r\n" + 
				") PHATSINH \r\n";
		System.out.println("Query bang tam: "+query);
		return query;
	}
	
	public String getQueryBaoCao() {
		String query = "SELECT ISNULL(PK_SEQ, '') AS PK_SEQ, ISNULL(MA, '') AS MA, ISNULL(TEN, '') AS TEN, \r\n" + 
				"DUNODAUKY, TANGTRONGKY, GIAMTRONGKY, ISNULL((DUNODAUKY + TANGTRONGKY - GIAMTRONGKY), 0) AS DUNOCUOIKY\r\n" + 
				"FROM\r\n" + 
				"(\r\n" + 
				"	SELECT PK_SEQ, MA, TEN, ISNULL(NODAUKY, 0) AS DUNODAUKY, ISNULL(TANGTRONGKY, 0) AS TANGTRONGKY, ISNULL(GIAMTRONGKY, 0) AS GIAMTRONGKY\r\n" + 
				"	FROM\r\n" + 
				"	(\r\n" + 
				"		SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", 'SELECT ''KH''+CAST(PK_SEQ AS VARCHAR(50)) AS PK_SEQ, MAFAST AS MA, TEN\r\n" + 
				"		FROM "+Utility.prefixReportDMS+"KHACHHANG')\r\n" + 
				"		UNION ALL\r\n" + 
				"		SELECT 'NPP'+CAST(PK_SEQ AS VARCHAR(50)) AS PK_SEQ, ISNULL(MAFAST, '') AS MA, TEN\r\n" + 
				"		FROM NHAPHANPHOI \r\n" + 
				"		WHERE PK_SEQ != 1\r\n" + 
				"		UNION\r\n" + 
				"		SELECT 'NV'+CAST(PK_SEQ AS VARCHAR(50)) AS PK_SEQ, '', TEN\r\n" + 
				"		FROM NHANVIEN \r\n" + 
				"	) DANHMUC\r\n" + 
				"	LEFT JOIN #DAUKY ON #DAUKY.MADOITUONG = DANHMUC.PK_SEQ\r\n" + 
				"	LEFT JOIN #PHATSINH ON #PHATSINH.MADOITUONG = DANHMUC.PK_SEQ\r\n" + 
				"	WHERE (ISNULL(NODAUKY, 0) != 0 OR ISNULL(TANGTRONGKY, 0) != 0 OR ISNULL(GIAMTRONGKY, 0) != 0)\r\n" + 
				")A\r\n" + 
				"ORDER BY TEN ASC";
		System.out.println("Query bao cao: "+query);
		return query;
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoTongHopCongNoKH.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook, obj);
		isFillData = FillData(workbook, obj);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeader(Workbook workbook, IStockintransit obj) {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		ResultSet ctyRs = obj.getRsErpCongty();
		String ctyName = "";
		String diachi = "";
		String masothue = "";
		
		try{
			if(ctyRs != null){
				ctyRs.next();
			
				ctyName = ctyRs.getString("TEN");
				diachi =  ctyRs.getString("DIACHI");
				masothue =  ctyRs.getString("MASOTHUE");
				
				ctyRs.close();
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
		}
		
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ctyName);
	    
	    cells.setRowHeight(1, 20.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Địa chỉ: " + diachi); 
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuê: " + masothue); 
	    
	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO TỔNG HỢP CÔNG NỢ KHÁCH HÀNG");

	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay());
	    
	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "đến ngày: " + obj.getdenngay());
	    
	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Kênh bán hàng: " + obj.getKenhBH());

	}
	
	private void setStyleColorGray(Cells cells ,Cell cell, String leftright)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
	}

	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		String query = "";
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		db.getConnection().setAutoCommit(false);
		
		query = getQueryBangTam(obj, db);
		//db.update(query);
		if(!db.update(query))
		{
			obj.setMsg("Không tạo được bảng tạm");
			db.getConnection().rollback();
			return false;
		}
		query = getQueryBaoCao();
		ResultSet rs = db.get(query);
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		
		int index = 12;
		double totaltangtrongky=0;
		double totalgiamtrongky=0;
		double totalnocodauky=0;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{		
					totalnocodauky= totalnocodauky + rs.getDouble("DUNODAUKY");
					totalgiamtrongky=totalgiamtrongky+rs.getDouble("GIAMTRONGKY");
					totaltangtrongky=totaltangtrongky +rs.getDouble("TANGTRONGKY");
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-11);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("MA"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TEN"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getDouble("DUNODAUKY"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("DUNOCUOIKY"));	//Nhan hang   	6
					this.setStyleColorNormar(cells, cell);
					
					/*cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("TAIKHOAN"));	
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getString("MAFAST"));	
					this.setStyleColorNormar(cells, cell);*/
					
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tổng cộng");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(totalnocodauky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totaltangtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalgiamtrongky);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(totalnocodauky+totaltangtrongky-totalgiamtrongky);
				this.setStyleColorGray(cells, cell, "1");
							
			/*	cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");*/
			
				
				index=index+3;
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập báo cáo");
				
				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
				
				index=index+5;
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
				cells.setColumnWidth(0, 10.0f);
				cells.setColumnWidth(1, 20.0f);
				cells.setColumnWidth(2, 40.0f);
				cells.setColumnWidth(3, 25.0f);
				cells.setColumnWidth(4, 25.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 25.0f);
				
			}

			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}	
		if(db != null)
			db.shutDown();
		return true;
	}	
}