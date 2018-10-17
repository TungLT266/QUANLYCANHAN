package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

 import java.text.DecimalFormat;
 import java.text.NumberFormat;

public class Erp_Chitietnguyenvatlieu extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public Erp_Chitietnguyenvatlieu() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setErpCongtyId(ctyId);
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiTietNVL.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");

		IStockintransit obj = new Stockintransit();
		obj.setErpCongtyId(ctyId);
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
				
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		String nhamayid= request.getParameter("nhamayid"); 
		obj.setNhamayId(nhamayid);
		
		
		String baocaomoi= request.getParameter("baocaomoi"); 
		if(baocaomoi==null){
			baocaomoi="";
		}
		obj.setPivot(baocaomoi);
		
		
		String thang = request.getParameter("thang").length()< 2 ? ("0"+request.getParameter("thang")) :request.getParameter("thang") ;
		obj.setMonth(thang);
		
		obj.setYear(request.getParameter("nam"));

		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiTietNVL.jsp";
		obj.InitErp();
		//System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ChitietNVL_XuongNhom.xlsm");
			
			String query = setQuery(obj); 

	        try 
	        {
	        	// --- tạo báo cáo sản xuất giấy nhôm --- //
	        	if(obj.getNhamayId().equals("100000")){
					if(!CreatePivotTable(out, response, request, obj, query))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Xin loi khong co bao cao trong thoi gian nay");
					    writer.close();
					}
	        	}
	        	
	        	// --- tạo báo cáo sản xuất lõi--- //
	        	if(obj.getNhamayId().equals("100001")){
	        		if(!CreatePivotTable_XL(out, response, request, obj, query))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Xin loi khong co bao cao trong thoi gian nay");
					    writer.close();
					}
	        	}
			} 
	        catch (Exception e) 
	         { 
	        	e.printStackTrace();
	        	obj.init();
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
	}else{		
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
		}
	}
	
	public String setQuery(IStockintransit obj)
	{		
		//System.out.println("Query: "+query);
		String nam_thang = ""; 
		if(obj.getMonth().length() > 0 & obj.getYear().length() > 0){
			nam_thang = obj.getYear() + "-" + obj.getMonth();
		}else{
			return "";
		}
		
		// -- SẢN XUẤT GIẤY NHÔM ---//
		
		String query="";
		
		if(obj.getNhamayId().equals("100000")){
		 query = 	" SELECT	LSX.PK_SEQ AS LSXID, SP.MA AS TP_MA,  MAKT.MA AS TP_MAKT,  " +
						" SP.DINHLUONG , SP.DVDL_DINHLUONG , SP.RONG , SP.DVDL_RONG , SP.DAI , SP.DVDL_DAI , SP.MAU,  " + 
						"  DVDL.PK_SEQ AS DVDLID, DVDL.DONVI AS TP_DONVI, ISNULL(TPSX.SOLUONG, 0) AS SOLUONG_TP, " +
						" CASE WHEN DVDL.DONVI = 'Kg' THEN TPSX.SOLUONG " +
						" ELSE ISNULL(ISNULL(TPSX.SOLUONG, 0)*(SELECT SOLUONG2 FROM QUYCACH WHERE DVDL1_FK = DVDL.PK_SEQ AND DVDL2_FK = 100003 AND SANPHAM_FK = SP.PK_SEQ), 0) " +
						" END AS TRONGLUONG_TP, " +
						
						" ISNULL(TPSXA.SOLUONG, 0) AS SOLUONG_LSX, " +
						" CASE WHEN DVDL.DONVI = 'Kg' THEN TPSXA.SOLUONG " +
						" ELSE ISNULL(ISNULL(TPSXA.SOLUONG, 0)*(SELECT SOLUONG2 FROM QUYCACH WHERE DVDL1_FK = DVDL.PK_SEQ AND DVDL2_FK = 100003 AND SANPHAM_FK = SP.PK_SEQ), 0) " +
						" END AS TRONGLUONG_LSX, "+
						" ISNULL(BTPSX.MAKT_BTP_SX, '') AS MAKT_BTP_SX, " + 
						" CASE WHEN ISNULL(BTPSX.LSXID, 0) = 0 THEN '' ELSE ISNULL(CONVERT(VARCHAR, BTPSX.LSXID), '') END AS BTP_LSXID_SX, " +
						" CASE WHEN BTPSX.BTP_DONVI = 'Kg' THEN ISNULL(BTPSX.SOLUONG, 0) " +
						" ELSE ISNULL(ISNULL(BTPSX.SOLUONG, 0)*(SELECT SOLUONG2 FROM QUYCACH WHERE DVDL1_FK = BTPSX.DVDLID AND DVDL2_FK = 100003 AND SANPHAM_FK = BTPSX.BTPID), 0) " +
						" END AS TRONGLUONG_BTP_SX, " +
						" ISNULL(BTPTH.MAKT_BTP_TH, '') AS MAKT_BTP_TH, " + 
						" CASE WHEN ISNULL(BTPTH.LSXID, 0) = 0 THEN '' ELSE CONVERT(VARCHAR, BTPTH.LSXID) END AS BTP_LSXID_TH, " +
						" CASE WHEN BTPTH.BTP_DONVI = 'Kg' THEN ISNULL(BTPTH.SOLUONG, 0) " +
						" ELSE ISNULL(ISNULL(BTPTH.SOLUONG, 0)*(SELECT SOLUONG2 FROM QUYCACH WHERE DVDL1_FK = BTPSX.DVDLID AND DVDL2_FK = 100003 AND SANPHAM_FK = BTPTH.BTPID), 0) " +
						" END AS TRONGLUONG_BTP_TH, " +
				
						" ISNULL(NVL_DM.LSP, '') AS LSP, ISNULL(NVL_DM.MA_NVL, '') AS MA_NVL, ISNULL(NVL_DM.DINHLUONG, '') AS NVL_DINHLUONG, ISNULL(NVL_DM.DAI, '') AS NVL_DODAI, " + 
						" ISNULL(NVL_DM.SOLUONG_NVL_DM, 0) AS TLUONG_NVL_DM,	" +		

						" ISNULL(NVL_TH.SOLUONG_NVL_TH, 0) AS TLUONG_NVL_TH " +			
						" FROM ERP_LENHSANXUAT_GIAY LSX " +
						" INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX_SP.LENHSANXUAT_FK = LSX.PK_SEQ " + 
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSX_SP.SANPHAM_FK " +
						" INNER JOIN ERP_MAKETOAN MAKT ON MAKT.PK_SEQ = SP.MAKETOAN_FK " +
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						" INNER JOIN " +
						"( " +
						//-- LAY NHUNG LENH SAN XUAT, DA THUC HIEN NHAP KHO TP HOAC CHUA THUC HIEN NHUNG DA HOAN TAT
							"SELECT LSX.PK_SEQ AS LSXID, SP.PK_SEQ AS SPID, SP.MA AS TP_MA, MAKT.MA AS TP_MAKT, SUM(ISNULL(NK_SP.SOLUONGNHAP,0)) AS SOLUONG " +
							"FROM ERP_LENHSANXUAT_GIAY LSX " +
							"LEFT JOIN ERP_NHAPKHO NK ON LSX.PK_SEQ = NK.SOLENHSANXUAT " +
							"LEFT JOIN ERP_NHAPKHO_SANPHAM NK_SP ON NK_SP.SONHAPKHO_FK = NK.PK_SEQ " +
							"LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NK_SP.SANPHAM_FK " +
							"LEFT JOIN ERP_MAKETOAN MAKT ON MAKT.PK_SEQ = SP.MAKETOAN_FK " +
							"WHERE SP.LOAISANPHAM_FK = 100005 AND ((NK.TRANGTHAI IN (1) AND NK.NGAYNHAPKHO LIKE '" + nam_thang + "%' ) OR (LSX.TRANGTHAI = 7 AND LSX.NGAYSUA LIKE '" + nam_thang + "%' ) ) " +
							"GROUP BY LSX.PK_SEQ, MAKT.MA, SP.MA, SP.PK_SEQ " +
						")TPSX ON TPSX.LSXID = LSX.PK_SEQ AND TPSX.TP_MAKT = MAKT.MA AND SP.MA = TPSX.TP_MA AND SP.PK_SEQ = TPSX.SPID " +

						"LEFT JOIN " +
						"( " +
						"	SELECT  LSX.PK_SEQ AS LSXID, NK_SP.SANPHAM_FK AS BTPID, MAKT.MA AS MAKT_BTP_SX, " + 
						"	SP.MA AS MA_BTP_SX, SUM(ISNULL(NK_SP.SOLUONGNHAP,0)) AS SOLUONG, " +
						"	DVDL.PK_SEQ AS DVDLID, DVDL.DONVI AS BTP_DONVI " +
						"	FROM ERP_NHAPKHO NK " +
						"	INNER JOIN ERP_NHAPKHO_SANPHAM NK_SP ON NK_SP.SONHAPKHO_FK = NK.PK_SEQ " +
						"	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.SOLENHSANXUAT " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NK_SP.SANPHAM_FK " +
						"	INNER JOIN ERP_MAKETOAN MAKT ON MAKT.PK_SEQ = SP.MAKETOAN_FK " +
						"	INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"	WHERE SP.LOAISANPHAM_FK = 100011 AND NK.TRANGTHAI IN (1 ) AND NK.NGAYNHAPKHO LIKE '" + nam_thang + "%' " + 
						"	GROUP BY LSX.PK_SEQ, NK_SP.SANPHAM_FK, SP.MA, MAKT.MA, DVDL.PK_SEQ, DVDL.DONVI " +
						"	HAVING SUM(ISNULL(NK_SP.SOLUONGNHAP,0)) > 0 " +
						" )BTPSX ON BTPSX.LSXID = LSX.PK_SEQ " +

						" LEFT JOIN " +
						" ( " +
						"	SELECT	LSX.PK_SEQ AS LSXID, LSX_TH.SANPHAM_FK AS BTPID, MAKT.MA AS MAKT_BTP_TH, " +
						"	SP.MA AS MA_BTP_TH, SUM(ISNULL(LSX_TH.SOLUONG, 0)) AS SOLUONG, " +
						"	DVDL.PK_SEQ AS DVDLID, DVDL.DONVI AS BTP_DONVI " +
						"	FROM ERP_TIEUHAONGUYENLIEU TH " +
						"	INNER JOIN ERP_LENHSANXUAT_TIEUHAO LSX_TH ON LSX_TH.TIEUHAONGUYENLIEU_FK = TH.PK_SEQ " +
						"	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = TH.LENHSANXUAT_FK " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSX_TH.SANPHAM_FK " +
						"	INNER JOIN ERP_MAKETOAN MAKT ON MAKT.PK_SEQ = SP.MAKETOAN_FK " +
						"	INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"	WHERE SP.LOAISANPHAM_FK = 100011 AND TH.TRANGTHAI = 1 AND TH.NGAYTIEUHAO LIKE '" + nam_thang + "%' " + 
						"	GROUP BY LSX.PK_SEQ, LSX_TH.SANPHAM_FK, MAKT.MA, SP.MA, DVDL.PK_SEQ, DVDL.DONVI " +
						"	HAVING SUM(ISNULL(LSX_TH.SOLUONG,0)) > 0 " +
						")BTPTH ON BTPTH.LSXID = LSX.PK_SEQ AND BTPTH.MAKT_BTP_TH = BTPSX.MAKT_BTP_SX " +
						"LEFT JOIN " +
						"( " +
						"	SELECT LSX.PK_SEQ AS LSXID, LSP.TEN AS LSP, SP.MA AS MA_NVL, SP.MA AS MA_NVL, SP.DINHLUONG, SP.DAI, SUM(LSX_BOM.SOLUONG) AS SOLUONG_NVL_DM " + 
						"	FROM ERP_LENHSANXUAT_GIAY LSX " +
						"	INNER JOIN ERP_LENHSANXUAT_BOM_GIAY LSX_BOM ON LSX_BOM.LENHSANXUAT_FK = LSX.PK_SEQ " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSX_BOM.VATTU_FK AND SP.LOAISANPHAM_FK NOT IN (100011, 100005) " +
						"	INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"	GROUP BY LSX.PK_SEQ, LSP.TEN, SP.MA, SP.MA , SP.DINHLUONG, SP.DAI " +

						")NVL_DM ON NVL_DM.LSXID = LSX.PK_SEQ  " +
						"LEFT JOIN " +
						"( " +
						"	SELECT LSX.PK_SEQ AS LSXID, LSP.TEN AS LSP, SP.MA AS MA_NVL, SP.DINHLUONG, SP.DAI, SUM(ISNULL(LSX_TH.SOLUONG, 0)) AS SOLUONG_NVL_TH " +
						"	FROM ERP_TIEUHAONGUYENLIEU TH " +
						"	INNER JOIN ERP_LENHSANXUAT_TIEUHAO LSX_TH ON LSX_TH.TIEUHAONGUYENLIEU_FK = TH.PK_SEQ " +
						"	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = TH.LENHSANXUAT_FK " +
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX_SP.LENHSANXUAT_FK = LSX.PK_SEQ " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSX_TH.SANPHAM_FK " +
						"	INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"	WHERE SP.LOAISANPHAM_FK NOT IN (100011, 100005) AND TH.TRANGTHAI = 1 AND TH.NGAYTIEUHAO LIKE '" + nam_thang + "%' " +
						"	GROUP BY LSX.PK_SEQ, LSP.TEN, SP.MA, SP.DINHLUONG, SP.DAI " +

						")NVL_TH ON NVL_TH.LSXID = LSX.PK_SEQ AND NVL_TH.LSP = NVL_DM.LSP AND NVL_DM.MA_NVL = NVL_TH.MA_NVL AND NVL_DM.DINHLUONG = NVL_TH.DINHLUONG AND NVL_DM.DAI = NVL_TH.DAI " +

						"LEFT JOIN " +
						"(  " +
						"	SELECT LSX.PK_SEQ AS LSXID, SP.PK_SEQ AS SPID, SP.MA AS TP_MA, MAKT.MA AS TP_MAKT, SUM(ISNULL(LSXSP.SOLUONG,0)) AS SOLUONG " +
						"	FROM ERP_LENHSANXUAT_GIAY LSX  " +
						"	left join ERP_LENHSANXUAT_SANPHAM LSXSP on LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ " +
						"	LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP.SANPHAM_FK  " +
						"	LEFT JOIN ERP_MAKETOAN MAKT ON MAKT.PK_SEQ = SP.MAKETOAN_FK  " +
						"	WHERE SP.LOAISANPHAM_FK = 100005 " +
						"	GROUP BY LSX.PK_SEQ, MAKT.MA, SP.MA, SP.PK_SEQ  " +
						")TPSXA ON TPSXA.LSXID = LSX.PK_SEQ AND TPSXA.TP_MAKT = MAKT.MA AND SP.MA = TPSXA.TP_MA AND SP.PK_SEQ = TPSXA.SPID " +

						"WHERE LSX.CONGTY_FK = " + obj.getErpCongtyId() + " AND SP.DVKD_FK = 100000 " +
						
						" union all  " +

						" select 0 as LSXID, '' as TP_MA, '' as TP_MAKT, 0 as DINHLUONG,'' as	DVDL_DINHLUONG,0 as RONG,'' as	DVDL_RONG,  " +
						" 0 as	DAI,'' as DVDL_DAI, '' as MAU,0 as	DVDLID, '' as	TP_DONVI,0 as	SOLUONG_TP,0 as TRONGLUONG_TP,	" +
						" 0 as SOLUONG_LSX,0 as TRONGLUONG_LSX,'' as MAKT_BTP_SX, 0 as BTP_LSXID_SX,0 as	TRONGLUONG_BTP_SX	,   " +
						" '' as MAKT_BTP_TH, 0 as	BTP_LSXID_TH, 0 as	TRONGLUONG_BTP_TH,   " +
						" LSP.TEN as LSP,	SP.MA as	MA_NVL, SP.DINHLUONG as	NVL_DINHLUONG, SP.DAI as NVL_DODAI, SUM(ck_sp.SOLUONGXUAT) as TLUONG_NVL_DM, 0 as TLUONG_NVL_TH  " +
						" from ERP_CHUYENKHO ck " +
						" inner join ERP_CHUYENKHO_SANPHAM ck_sp on ck.PK_SEQ = ck_sp.CHUYENKHO_FK  " +
						" inner join ERP_SANPHAM SP ON SP.PK_SEQ = ck_sp.SANPHAM_FK AND SP.LOAISANPHAM_FK NOT IN (100011, 100005)  " +
						" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK  " +
						" where ck.TRANGTHAI = 3 and ck.NOIDUNGXUAT_FK in (100009, 100021) and NGAYCHUYEN LIKE '" + nam_thang + "%'  " +
						" group by  LSP.TEN ,SP.MA , SP.DINHLUONG , SP.DAI ";
		}
		
		//--- SẢN XUẤT LÕI ---//
		
		if(obj.getNhamayId().equals("100001")){
			
			query=	" SELECT TH.MATP ,TH.MA,TH.DINHLUONG,TH.RONG ,SUM(TRONGLUONG) AS TRONGLUONG,SUM(TH.SOLUONG) AS SOLUONG  FROM ( " +
					" SELECT (SELECT TOP 1  SP.MA FROM ERP_LENHSANXUAT_SANPHAM LSXSP  " +
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSXSP.SANPHAM_FK  " +
					" WHERE LSXSP.LENHSANXUAT_FK=LSX.PK_SEQ) AS MATP,  SP.MA,  SP.DINHLUONG,SP.RONG,THSP.SOLUONG, " +
					" case when sp.loaisanpham_fk =100005  " +
					" then   THSP.SOLUONG *  CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE CAST( QC.SOLUONG2 AS FLOAT) " +  
					" / CAST( QC.SOLUONG1 AS FLOAT)  END     else   THSP.SOLUONG end AS TRONGLUONG " +    
					" FROM  ERP_TIEUHAONGUYENLIEU TH " +        
					" INNER JOIN ERP_LENHSANXUAT_TIEUHAO THSP ON TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK  "  +
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=THSP.SANPHAM_FK  " +     
					" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=TH.LENHSANXUAT_FK " +    
					" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 " +    
					" WHERE LSX.NHAMAY_FK IN (100001) AND  SUBSTRING(TH.ngaytieuhao,1,7) ='"+nam_thang +"'   and TH.TRANGTHAI=1 " + 
					" ) AS TH " + 
					" GROUP BY TH.MATP ,TH.MA,TH.DINHLUONG,TH.RONG " +
					" ORDER BY MATP " ;

			
		}
		
		System.out.println(obj.getNhamayId()+": "+query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ChitietNVL_XuongNhom.xlsm");
		if(obj.getPivot().equals("1")){
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ChitietNVL_XuongNhom_V1.xlsm");
		}
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateHeader(workbook,obj);
		
		if(obj.getPivot().equals("1")){
			isFillData = FillData_v1(workbook, obj, query);
		}else{
			isFillData = FillData(workbook, obj, query);
		}
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
	
	private boolean CreatePivotTable_XL(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ChitietNVL_XuongLoi.xlsm");
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader_XL(workbook,obj);
		isFillData = FillData_XL(workbook, obj, query);
   
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
	
	
	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
	{	
 		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
	    Style style;
	    Font font = new Font();
	    font.setName("Times New Roman");
	    Cell cell = cells.getCell("A102");
	    	    
    	ReportAPI.getCellStyle(cell,Color.BLACK,true, 12, "THÁNG " + obj.getMonth() + " NĂM " + obj.getYear() );
	  	    		
	}
	
	private void CreateHeader_XL(Workbook workbook, IStockintransit obj)throws Exception
	{	
 		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
	    Style style;
	    Font font = new Font();
	    font.setName("Times New Roman");
	    Cell cell = cells.getCell("A4");
	    	    
    	ReportAPI.getCellStyle(cell,Color.BLACK,true, 12, "THÁNG " + obj.getMonth() + " NĂM " + obj.getYear() );
	  	    		
	}
	private Hashtable< Integer, String > htbValueCell ()
	{
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"A");htb.put(2,"B");htb.put(3,"C");htb.put(4,"D");htb.put(5,"E");
		htb.put(6,"F");htb.put(7,"G");htb.put(8,"H");htb.put(9,"I");htb.put(10,"J");
		htb.put(11,"K");htb.put(12,"L");htb.put(13,"M");htb.put(14,"N");htb.put(15,"O");
		htb.put(16,"P");htb.put(17,"Q");htb.put(18,"R");htb.put(19,"S");htb.put(20,"T");
		htb.put(21,"U");htb.put(22,"V");htb.put(23,"W");
		return htb; 
	}
	
	private boolean FillData_v1(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(2);
		Hashtable<Integer, String> htb = this.htbValueCell();
		Cells cells = worksheet.getCells();	
		
		Cell cell = cells.getCell("A102");
	    cell.setValue("THÁNG " + obj.getMonth() + " NĂM " + obj.getYear());	
		
		Font font = new Font();

	/*	ResultSet rs = db.get(query);
	 * */
		
		 
	 
		    //System.out.println("Tong hop NXT: " + sql);
		    String[] param = new String[2];
		    
		    param[0] =obj.getYear() + "-" + obj.getMonth();
		    param[1]=obj.getNhamayId(); 
		    ResultSet   rs = db.getRsByPro("REPORT_NHAPKHO_TIEUHAO_LSX", param);
		   String lsxid="";
		   String lsxid_bk="";
		     
		int index = 7;
		try 
		{
			cell = null;
			Style style = null;
			double dinhmuc = 0;
			double thucte = 0;
			double tltp = 0;
			double tlsx = 0;
			double tlsd = 0;
			double banthanhphamcuoiky=0;
			double tongtrongluongnhap=0;
			double DINHLUONG_NK=0;
			double banthanhphamdk=0;
			double dinhluongbtp_dk=0;
			double banthanhpham_ck=0;
			double dinhluongbtp_ck=0;
			String COSUDUNGHC="";
			String TP_MAKT="";
			
			while (rs.next())
			{					
				if(index > 105){
					// Ve khung cho cac cell
					for(int i = 1; i < 24; i++){
						cell = cells.getCell(htb.get(i) + String.valueOf(index));		cell.setValue("");	
						cell = CreateBorderSetting(cell);
					}
				}
				
				lsxid = rs.getString("LSXID");
				
				if(!lsxid.equals(lsxid_bk) && !lsxid.equals("0")){
					lsxid_bk=lsxid;
					//set tổng trọng lượng nhập của TP
					tongtrongluongnhap =rs.getDouble("TONGTRONGLUONG_NHAP");
					DINHLUONG_NK=rs.getDouble("DINHLUONG_NK")/1000;
					//banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_DK")+ rs.getDouble("SOLUONG_SANXUAT_BTP") - rs.getDouble("SOLUONG_SUDUNG_BTP");
					banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_NHAPTHEM");
					
					banthanhphamdk=rs.getDouble("SOLUONG_BTP_DK");
					dinhluongbtp_dk=rs.getDouble("DINHLUONG_BTP_DK")/1000;
					dinhluongbtp_ck=rs.getDouble("DINHLUONG_BTP_CK")/1000;
					if(rs.getString("COSUDUNGHC").equals("1")){
						COSUDUNGHC="Hóa chất";
					}else{
						COSUDUNGHC="";
					}
					TP_MAKT=rs.getString("TP_MAKT");
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(rs.getDouble("LSXID"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
						
				/*cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("TP_MA"));	  						
				cell = CreateBorderSetting(cell);
						
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TP_MAKT"));	 						
				cell = CreateBorderSetting(cell);
				*/
				// QUY CACH : Dinh Luong, Dai , Rong , Mau   D E F G
				String dinhluong1= rs.getString("DINHLUONG");
				if(dinhluong1==null)
					dinhluong1="";
				if(dinhluong1.indexOf(" ")>=0){
				
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(dinhluong1.substring(0,dinhluong1.indexOf(" ")));
				}else{
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(0);
				}
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
			/*	
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("RONG") );	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("DAI")  );	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);*/
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("MAU"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("TP");	 
				
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("TP_DONVI")); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(""); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				String dinhluong=rs.getString("RONG")+rs.getString("DAI")+rs.getString("DINHLUONG");
				
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(""); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(dinhluong); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_NHAP_TP")); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_NHAP_TP")+rs.getDouble("SOLUONG_BTP_NHAPTHEM")); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_NHAP_TP")); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
						
			/*	cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_DUTINH"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
						
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_DUTINH"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				tltp = tltp + rs.getDouble("TRONGLUONG_NHAP_TP");
				*/
				//them so luong + trong luong
				
		/*		cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONGNHAP_TP"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
						
				cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_NHAP_TP"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				 */
				
				
			/*	cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(rs.getString("SOLUONG_BTP_DK"));	
				cell = CreateBorderSetting(cell);
				
				cell = cells.getCell("N" + String.valueOf(index));		cell.setValue(rs.getString("MAKT_BTP"));	
				cell = CreateBorderSetting(cell);

				cell = cells.getCell("O" + String.valueOf(index));		cell.setValue(rs.getString("LSXID"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
					
				cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_SANXUAT_BTP"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();		
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				tlsx = tlsx + rs.getDouble("SOLUONG_SANXUAT_BTP");
				
				cell = cells.getCell("Q" + String.valueOf(index));		cell.setValue(rs.getString("MAKT_BTP"));	
				cell = CreateBorderSetting(cell);
					
				cell = cells.getCell("R" + String.valueOf(index));		cell.setValue(rs.getString("LSXID"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
					
				cell = cells.getCell("S" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_SUDUNG_BTP"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();		
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
 */
				// CUỐI KỲ
			/*	if(rs.getString("MAKT_BTP").length() >0){
									//banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_DK")+ rs.getDouble("SOLUONG_SANXUAT_BTP") - rs.getDouble("SOLUONG_SUDUNG_BTP");
									banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_NHAPTHEM");
									cell = cells.getCell("T" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_BTP_NHAPTHEM"));	
				}
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();		
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				tlsd = tlsd + rs.getDouble("SOLUONG_SUDUNG_BTP");
				*/
			 
				
				cell = cells.getCell("R" + String.valueOf(index));		cell.setValue(rs.getString("LOAISANPHAM"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
			/*	cell = cells.getCell("V" + String.valueOf(index));		cell.setValue(rs.getString("MA_NL"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);*/

				cell = cells.getCell("S" + String.valueOf(index));		cell.setValue(rs.getDouble("DINHLUONG_NL"));
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);

				cell = cells.getCell("T" + String.valueOf(index));		cell.setValue(rs.getDouble("RONG_NL"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);

				cell = cells.getCell("U" + String.valueOf(index));		cell.setValue(rs.getDouble("DAI_NL"));
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
			/*	cell = cells.getCell("Z" + String.valueOf(index));		cell.setValue(rs.getString("QUYCACH_NGUONGOC_NL"));
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);*/
				
				 
					
				/*cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_NVL_DM"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);*/
			 
				cell = cells.getCell("V" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_NVL_TH"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				/*
				 * Trường hợp 1: không có BTP đầu kỳ và cuối kỳ							
					Công thức tính hao hụt ( kg)- cột 7			: Lưu ý: định lượng NVL và Thành Phẩm ta chia cho 1000 để đổi thành kg/m2				
					+ Giấy:	(Tổng TL NVL/DL NVL giấy -Tổng TP thu về / DL T.Phẩm) x DL NVL giấy						
					+ Nhôm:	((Tổng TL NVL/DL NVL nhômx 2.714) -Tổng TP thu về / DL T.Phẩm) x (DL NVL nhôm x 2.714)						
					+ Film(pet):	((Tổng TL NVL/DL NVL film x 1.4) -Tổng TP thu về / DL T.Phẩm) x (DL NVL film x 1.4)						

				 */
				double haohut=0;
				
				/*System.out.println("banthanhphamdk: "+banthanhphamdk);
				System.out.println("banthanhphamcuoiky: "+banthanhphamcuoiky);
				System.out.println("tongtrongluongnhap: "+tongtrongluongnhap);
				System.out.println("DINHLUONG_NL: "+rs.getDouble("DINHLUONG_NL"));
				System.out.println("DINHLUONG_NK: "+DINHLUONG_NK);
				System.out.println("dinhluongbtp_ck: "+dinhluongbtp_ck);
				System.out.println("tongtrongluongnhap: "+rs.getDouble("TRONGLUONG_NVL_TH_TOTAL"));*/
				double 	haohuttong=0;
					if(rs.getString("MALOAISANPHAM").trim().equals("PAPER")){
						
						  	haohuttong=( (rs.getDouble("DINHLUONG_NL") >0?  rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")/(rs.getDouble("DINHLUONG_NL")/1000) :0 ) +  ( dinhluongbtp_dk >0 ? banthanhphamdk/dinhluongbtp_dk: 0 )  - (DINHLUONG_NK>0? tongtrongluongnhap/DINHLUONG_NK : 0) - (dinhluongbtp_ck>0 ? banthanhphamcuoiky/dinhluongbtp_ck: 0 ))*  (rs.getDouble("DINHLUONG_NL")/1000);
						 
							
					}else if(rs.getString("MALOAISANPHAM").trim().equals("FOIL")){
						if(rs.getString("MA_NL").contains("PET") || rs.getString("MA_NL").contains("PET")){
							  	haohuttong=(( rs.getDouble("DINHLUONG_NL") >0 ? rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")/(rs.getDouble("DINHLUONG_NL")/1000* 1.40) :0 ) + ( dinhluongbtp_dk >0 ? banthanhphamdk/dinhluongbtp_dk: 0 ) -(DINHLUONG_NK>0 ?tongtrongluongnhap/ DINHLUONG_NK :0) - (dinhluongbtp_ck>0? banthanhphamcuoiky/dinhluongbtp_ck:0) ) *  (rs.getDouble("DINHLUONG_NL")/1000*1.4);
						}else{
							  	haohuttong=(( rs.getDouble("DINHLUONG_NL") >0 ? rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")/(rs.getDouble("DINHLUONG_NL")/1000*  2.714) :0 ) + ( dinhluongbtp_dk >0 ? banthanhphamdk/dinhluongbtp_dk: 0 ) - (DINHLUONG_NK>0 ?tongtrongluongnhap/ DINHLUONG_NK:0) - (dinhluongbtp_ck>0? banthanhphamcuoiky/dinhluongbtp_ck:0) ) * ( rs.getDouble("DINHLUONG_NL")/1000* 2.714);
						}
						
					}	
					if(rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")>0){
						haohut=	 haohuttong* rs.getDouble("TRONGLUONG_NVL_TH")/rs.getDouble("TRONGLUONG_NVL_TH_TOTAL") ;
					}
					
					cell = cells.getCell("W" + String.valueOf(index));		cell.setValue(haohut);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("X" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_NVL_TH")>0? haohut*100 /rs.getDouble("SOLUONG_NVL_TH"):0);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(TP_MAKT);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(COSUDUNGHC);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					
					
					cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("SOPO_CU"));	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
				index++;
				
				
			}	
			for(int i = 1; i < 24; i++){
				cell = cells.getCell(htb.get(i) + String.valueOf(index));		cell.setValue("");	
				cell = CreateBorderSetting(cell);
			}

			font.setBold(true);
			font.setName("Times New Roman");
			font.setSize(12);
 
			cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(tltp);	
			cell = CreateBorderSetting(cell);
			style = cell.getStyle();
			style.setNumber(4);
			style.setHAlignment(TextAlignmentType.RIGHT);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(tlsx);	
			cell = CreateBorderSetting(cell);
			style = cell.getStyle();
			style.setNumber(4);
			style.setHAlignment(TextAlignmentType.RIGHT);
			style.setFont(font);			
			cell.setStyle(style);

			cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tlsd);	
			cell = CreateBorderSetting(cell);
			style = cell.getStyle();
			style.setNumber(4);
			style.setHAlignment(TextAlignmentType.RIGHT);
			style.setFont(font);			
			cell.setStyle(style);

			index = index + 2;
			
			cells.merge(index, 0, index, 3);
			cell = cells.getCell("A" + String.valueOf(index + 1));
			cell.setValue("NGƯỜI LẬP");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);

			cells.merge(index, 12, index, 16);
			cell = cells.getCell("M" + String.valueOf(index + 1));
			cell.setValue("TRƯỞNG PHÒNG ĐHSX");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);

			index = index + 3;
			cells.merge(index, 0, index, 3);
			cell = cells.getCell("A" + String.valueOf(index + 1));
			cell.setValue("LIÊN NGỌC QUÍ");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);


			cells.merge(index, 12, index, 16);
			cell = cells.getCell("M" + String.valueOf(index + 1));
			cell.setValue("NGUYỄN NGỌC TRANG ĐÀI");			
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);

			if (rs != null)
				rs.close();
			
			if(db != null)
				db.shutDown();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
		return true;
	}	
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(2);
		Hashtable<Integer, String> htb = this.htbValueCell();
		Cells cells = worksheet.getCells();	
		
		Cell cell = cells.getCell("A102");
	    cell.setValue("THÁNG " + obj.getMonth() + " NĂM " + obj.getYear());	
		
		Font font = new Font();

	/*	ResultSet rs = db.get(query);
	 * */
		
		 
	 
		    //System.out.println("Tong hop NXT: " + sql);
		    String[] param = new String[2];
		    
		    param[0] =obj.getYear() + "-" + obj.getMonth();
		    param[1]=obj.getNhamayId(); 
		    ResultSet   rs = db.getRsByPro("REPORT_NHAPKHO_TIEUHAO_LSX", param);
		   String lsxid="";
		   String lsxid_bk="";
		     
		int index = 106;
		try 
		{
			cell = null;
			Style style = null;
			double dinhmuc = 0;
			double thucte = 0;
			double tltp = 0;
			double tlsx = 0;
			double tlsd = 0;
			double banthanhphamcuoiky=0;
			double tongtrongluongnhap=0;
			double DINHLUONG_NK=0;
			double banthanhphamdk=0;
			double dinhluongbtp_dk=0;
			double banthanhpham_ck=0;
			double dinhluongbtp_ck=0;
			
			while (rs.next())
			{					
				if(index > 105){
					// Ve khung cho cac cell
					for(int i = 1; i < 24; i++){
						cell = cells.getCell(htb.get(i) + String.valueOf(index));		cell.setValue("");	
						cell = CreateBorderSetting(cell);
					}
				}
				
				lsxid = rs.getString("LSXID");
				
				if(!lsxid.equals(lsxid_bk) && !lsxid.equals("0")){
					lsxid_bk=lsxid;
					//set tổng trọng lượng nhập của TP
					tongtrongluongnhap =rs.getDouble("TONGTRONGLUONG_NHAP");
					DINHLUONG_NK=rs.getDouble("DINHLUONG_NK")/1000;
					//banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_DK")+ rs.getDouble("SOLUONG_SANXUAT_BTP") - rs.getDouble("SOLUONG_SUDUNG_BTP");
					banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_NHAPTHEM");
					
					banthanhphamdk=rs.getDouble("SOLUONG_BTP_DK");
					dinhluongbtp_dk=rs.getDouble("DINHLUONG_BTP_DK")/1000;
					dinhluongbtp_ck=rs.getDouble("DINHLUONG_BTP_CK")/1000;
 
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(rs.getDouble("LSXID"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
						
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("TP_MA"));	  						
				cell = CreateBorderSetting(cell);
						
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TP_MAKT"));	 						
				cell = CreateBorderSetting(cell);
				
				// QUY CACH : Dinh Luong, Dai , Rong , Mau   D E F G
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("DINHLUONG")  );	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("RONG") );	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("DAI")  );	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("MAU"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("TP_DONVI")); 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
						
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_DUTINH"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
						
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_DUTINH"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				tltp = tltp + rs.getDouble("TRONGLUONG_NHAP_TP");
				
				//them so luong + trong luong
				
				cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONGNHAP_TP"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
						
				cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("TRONGLUONG_NHAP_TP"));	 
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				 
				
				
				cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(rs.getString("SOLUONG_BTP_DK"));	
				cell = CreateBorderSetting(cell);
				
				cell = cells.getCell("N" + String.valueOf(index));		cell.setValue(rs.getString("MAKT_BTP"));	
				cell = CreateBorderSetting(cell);

				cell = cells.getCell("O" + String.valueOf(index));		cell.setValue(rs.getString("LSXID"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
					
				cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_SANXUAT_BTP"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();		
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				tlsx = tlsx + rs.getDouble("SOLUONG_SANXUAT_BTP");
				
				cell = cells.getCell("Q" + String.valueOf(index));		cell.setValue(rs.getString("MAKT_BTP"));	
				cell = CreateBorderSetting(cell);
					
				cell = cells.getCell("R" + String.valueOf(index));		cell.setValue(rs.getString("LSXID"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
					
				cell = cells.getCell("S" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_SUDUNG_BTP"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();		
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
 
				// CUỐI KỲ
				if(rs.getString("MAKT_BTP").length() >0){
									//banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_DK")+ rs.getDouble("SOLUONG_SANXUAT_BTP") - rs.getDouble("SOLUONG_SUDUNG_BTP");
									banthanhphamcuoiky=rs.getDouble("SOLUONG_BTP_NHAPTHEM");
									cell = cells.getCell("T" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_BTP_NHAPTHEM"));	
				}
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();		
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				tlsd = tlsd + rs.getDouble("SOLUONG_SUDUNG_BTP");
				
			 
				
				cell = cells.getCell("U" + String.valueOf(index));		cell.setValue(rs.getString("LOAISANPHAM"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("V" + String.valueOf(index));		cell.setValue(rs.getString("MA_NL"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);

				cell = cells.getCell("W" + String.valueOf(index));		cell.setValue(rs.getDouble("DINHLUONG_NL"));
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);

				cell = cells.getCell("X" + String.valueOf(index));		cell.setValue(rs.getDouble("RONG_NL"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);

				cell = cells.getCell("Y" + String.valueOf(index));		cell.setValue(rs.getDouble("DAI_NL"));
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("Z" + String.valueOf(index));		cell.setValue(rs.getString("QUYCACH_NGUONGOC_NL"));
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				 
					
				cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_NVL_DM"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
			 
				cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_NVL_TH"));	
				cell = CreateBorderSetting(cell);
				style = cell.getStyle();
				style.setNumber(4);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				/*
				 *  Trường hợp 1: không có BTP đầu kỳ và cuối kỳ							
					Công thức tính hao hụt ( kg)- cột 7			: Lưu ý: định lượng NVL và Thành Phẩm ta chia cho 1000 để đổi thành kg/m2				
					+ Giấy:	(Tổng TL NVL/DL NVL giấy -Tổng TP thu về / DL T.Phẩm) x DL NVL giấy						
					+ Nhôm:	((Tổng TL NVL/DL NVL nhômx 2.714) -Tổng TP thu về / DL T.Phẩm) x (DL NVL nhôm x 2.714)						
					+ Film(pet):	((Tổng TL NVL/DL NVL film x 1.4) -Tổng TP thu về / DL T.Phẩm) x (DL NVL film x 1.4)						

				 */
				double haohut=0;
				
				/*System.out.println("banthanhphamdk: "+banthanhphamdk);
				System.out.println("banthanhphamcuoiky: "+banthanhphamcuoiky);
				System.out.println("tongtrongluongnhap: "+tongtrongluongnhap);
				System.out.println("DINHLUONG_NL: "+rs.getDouble("DINHLUONG_NL"));
				System.out.println("DINHLUONG_NK: "+DINHLUONG_NK);
				System.out.println("dinhluongbtp_ck: "+dinhluongbtp_ck);
				System.out.println("tongtrongluongnhap: "+rs.getDouble("TRONGLUONG_NVL_TH_TOTAL"));*/
				double 	haohuttong=0;
					if(rs.getString("MALOAISANPHAM").trim().equals("PAPER")){
						
						  	haohuttong=( (rs.getDouble("DINHLUONG_NL") >0?  rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")/(rs.getDouble("DINHLUONG_NL")/1000) :0 ) +  ( dinhluongbtp_dk >0 ? banthanhphamdk/dinhluongbtp_dk: 0 )  - (DINHLUONG_NK>0? tongtrongluongnhap/DINHLUONG_NK : 0) - (dinhluongbtp_ck>0 ? banthanhphamcuoiky/dinhluongbtp_ck: 0 ))*  (rs.getDouble("DINHLUONG_NL")/1000);
						 
							
					}else if(rs.getString("MALOAISANPHAM").trim().equals("FOIL")){
						if(rs.getString("MA_NL").contains("PET") || rs.getString("MA_NL").contains("PET")){
							  	haohuttong=(( rs.getDouble("DINHLUONG_NL") >0 ? rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")/(rs.getDouble("DINHLUONG_NL")/1000* 1.40) :0 ) + ( dinhluongbtp_dk >0 ? banthanhphamdk/dinhluongbtp_dk: 0 ) -(DINHLUONG_NK>0 ?tongtrongluongnhap/ DINHLUONG_NK :0) - (dinhluongbtp_ck>0? banthanhphamcuoiky/dinhluongbtp_ck:0) ) *  (rs.getDouble("DINHLUONG_NL")/1000*1.4);
						}else{
							  	haohuttong=(( rs.getDouble("DINHLUONG_NL") >0 ? rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")/(rs.getDouble("DINHLUONG_NL")/1000*  2.714) :0 ) + ( dinhluongbtp_dk >0 ? banthanhphamdk/dinhluongbtp_dk: 0 ) - (DINHLUONG_NK>0 ?tongtrongluongnhap/ DINHLUONG_NK:0) - (dinhluongbtp_ck>0? banthanhphamcuoiky/dinhluongbtp_ck:0) ) * ( rs.getDouble("DINHLUONG_NL")/1000* 2.714);
						}
						
					}	
					if(rs.getDouble("TRONGLUONG_NVL_TH_TOTAL")>0){
						haohut=	 haohuttong* rs.getDouble("TRONGLUONG_NVL_TH")/rs.getDouble("TRONGLUONG_NVL_TH_TOTAL") ;
					}
					
					cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(haohut);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONG_NVL_TH")>0? haohut*100 /rs.getDouble("SOLUONG_NVL_TH"):0);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
				
					cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("SOPO_CU"));	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
				index++;
				
				
			}	
			for(int i = 1; i < 24; i++){
				cell = cells.getCell(htb.get(i) + String.valueOf(index));		cell.setValue("");	
				cell = CreateBorderSetting(cell);
			}

			font.setBold(true);
			font.setName("Times New Roman");
			font.setSize(12);
 
			cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(tltp);	
			cell = CreateBorderSetting(cell);
			style = cell.getStyle();
			style.setNumber(4);
			style.setHAlignment(TextAlignmentType.RIGHT);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(tlsx);	
			cell = CreateBorderSetting(cell);
			style = cell.getStyle();
			style.setNumber(4);
			style.setHAlignment(TextAlignmentType.RIGHT);
			style.setFont(font);			
			cell.setStyle(style);

			cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tlsd);	
			cell = CreateBorderSetting(cell);
			style = cell.getStyle();
			style.setNumber(4);
			style.setHAlignment(TextAlignmentType.RIGHT);
			style.setFont(font);			
			cell.setStyle(style);

			index = index + 2;
			
			cells.merge(index, 0, index, 3);
			cell = cells.getCell("A" + String.valueOf(index + 1));
			cell.setValue("NGƯỜI LẬP");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);

			cells.merge(index, 12, index, 16);
			cell = cells.getCell("M" + String.valueOf(index + 1));
			cell.setValue("TRƯỞNG PHÒNG ĐHSX");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);

			index = index + 3;
			cells.merge(index, 0, index, 3);
			cell = cells.getCell("A" + String.valueOf(index + 1));
			cell.setValue("LIÊN NGỌC QUÍ");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);


			cells.merge(index, 12, index, 16);
			cell = cells.getCell("M" + String.valueOf(index + 1));
			cell.setValue("NGUYỄN NGỌC TRANG ĐÀI");			
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setFont(font);
			cell.setStyle(style);

			if (rs != null)
				rs.close();
			
			if(db != null)
				db.shutDown();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
		return true;
	}	
	
	private boolean FillData_XL(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.###");
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(2);
		/*Hashtable<Integer, String> htb = this.htbValueCell();*/
		Cells cells = worksheet.getCells();	
		
		Cell cell = cells.getCell("A4");
	    cell.setValue("THÁNG " + obj.getMonth() + " NĂM " + obj.getYear());	
		
	    
	    
		Font font = new Font();
		
		
		
	/*	ResultSet rs = db.get(query);
	 * */
		
		    //System.out.println("Tong hop NXT: " + sql);
		    String[] param = new String[2];
		    
		    param[0] =obj.getYear() + "-" + obj.getMonth();
		    param[1]=obj.getNhamayId(); 
		    
		    String 	sxl		=" SELECT TH.MATP ,TH.MA,TH.DINHLUONG,TH.RONG ,SUM(TRONGLUONG) AS TRONGLUONG,SUM(TH.SOLUONG) AS SOLUONG  FROM ( " +
							" SELECT (SELECT TOP 1  SP.MA FROM ERP_LENHSANXUAT_SANPHAM LSXSP  " +
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSXSP.SANPHAM_FK  " +
							" WHERE LSXSP.LENHSANXUAT_FK=LSX.PK_SEQ) AS MATP,  SP.MA,  SP.DINHLUONG,SP.RONG,THSP.SOLUONG, " +
							" case when sp.loaisanpham_fk =100005  " +
							" then   THSP.SOLUONG *  CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE CAST( QC.SOLUONG2 AS FLOAT) " +  
							" / CAST( QC.SOLUONG1 AS FLOAT)  END     else   THSP.SOLUONG end AS TRONGLUONG " +    
							" FROM  ERP_TIEUHAONGUYENLIEU TH " +        
							" INNER JOIN ERP_LENHSANXUAT_TIEUHAO THSP ON TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK  "  +
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=THSP.SANPHAM_FK  " +     
							" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=TH.LENHSANXUAT_FK " +    
							" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 " +    
							" WHERE LSX.NHAMAY_FK IN (100001) AND  SUBSTRING(TH.ngaytieuhao,1,7) ='"+param[0]+"'   and TH.TRANGTHAI=1 " + 
							" ) AS TH " + 
							" GROUP BY TH.MATP ,TH.MA,TH.DINHLUONG,TH.RONG " +
							" ORDER BY MATP " ;
		    
		    ResultSet   rs = db.get(sxl);
		    
		   
		  
		     
		int index = 8;
		try 
		{

			String tmp ="";
			
			double tong=0;
			double daitong=0;
			
			int stt=1;
			cell = null;
			Style style = null;
			
				while (rs.next())
				{		
					String loaitp= rs.getString("MATP");
					String gsm = rs.getString("DINHLUONG");
					String kho = rs.getString("RONG");
					String cuon= rs.getString("RONG");
				
					double trongluong = rs.getDouble("TRONGLUONG");
					String ghichu =rs.getString("MA");
					
					
					daitong=daitong+ trongluong;
					
					if(tmp==""){
						tmp=rs.getString("MATP");
					}
					
					
					
					tong= tong + trongluong;
					
					cell = cells.getCell("A" + String.valueOf(index));		
					cell.setValue(stt);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
							
					cell = cells.getCell("B" + String.valueOf(index));		
					cell.setValue(loaitp);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
							
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(gsm);	
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					
					cell = cells.getCell("D" + String.valueOf(index));		
					cell.setValue(kho);	 
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("E" + String.valueOf(index));		
					cell.setValue(" ");	 
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("F" + String.valueOf(index));		
					cell.setValue(" ");	 
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("G" + String.valueOf(index));		
					cell.setValue(trongluong);	 
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("H" + String.valueOf(index));		
					cell.setValue(ghichu);	 
					cell = CreateBorderSetting(cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					stt++;
					index++;
					
					if(!loaitp.equals(tmp)){
						
						cells.merge(index-1, 2, index-1, 4);
						cell = cells.getCell("C" + String.valueOf(index));
						cell.setValue("Giấy sử dụng cho "+tmp);
						cell = CreateBorderSetting(cell);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						
	
						cell = cells.getCell("G" + String.valueOf(index));
						cell.setValue(tong);			
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell = CreateBorderSetting(cell);		
						cell.setStyle(style);
						
						cell = cells.getCell("H" + String.valueOf(index));
						cell = CreateBorderSetting(cell);
						cell.setStyle(style);
						
						tmp= loaitp;
						tong= 0;
						index++;
						stt =1;
					
						
					}
							
				 
					
					
			}
					
			//---- ô cuối cùng	---//
				
				cell = cells.getCell("A" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
				cell = cells.getCell("B" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
 
				cell = cells.getCell("F" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
 
 
				
				cells.merge(index-1, 2, index-1, 4);
				cell = cells.getCell("C" + String.valueOf(index));
				cell.setValue("Giấy sử dụng cho "+tmp);
				cell = CreateBorderSetting(cell);
				style.setHAlignment(TextAlignmentType.LEFT);

				cell.setStyle(style);
				

				cell = cells.getCell("G" + String.valueOf(index));
				cell.setValue(tong);			
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell = CreateBorderSetting(cell);
			
				cell.setStyle(style);
				
				cell = cells.getCell("H" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
 
				index++;
				
				
				
				cells.merge(index-1, 0, index-1, 4);
				cell = cells.getCell("A" + String.valueOf(index));
				cell.setValue("Tổng cộng");			
				cell = CreateBorderSetting(cell);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				
				cell = cells.getCell("B" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
 
				cell = cells.getCell("C" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
				
				cell = cells.getCell("D" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
				cell = cells.getCell("E" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
				cell = cells.getCell("F" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
				cell = cells.getCell("G" + String.valueOf(index ));
				cell.setValue(daitong);			
				cell = CreateBorderSetting(cell);
				style.setHAlignment(TextAlignmentType.RIGHT);
				style.setFont(font);
				cell.setStyle(style);
				cell = cells.getCell("H" + String.valueOf(index));
				cell = CreateBorderSetting(cell);
				cell.setStyle(style);
				
				
					
					
					if (rs != null)
						rs.close();
					
					if(db != null)
						db.shutDown();
				
			}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
		return true;
	}	
	
	
	 public Cell CreateBorderSetting(Cell cell) throws IOException
	    {
		
	        Style style;
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

	        cell.setStyle(style);
	        return cell;
	    }


}
