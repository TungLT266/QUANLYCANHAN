package geso.traphaco.erp.servlets.baocao;

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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;

public class BCTongHopTuoiNo_MucHanNo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BCTongHopTuoiNo_MucHanNo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String ctyId = (String)session.getAttribute("congtyId");
		
		
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
		String nextJSP = "/TraphacoHYERP/pages/Erp/BCTongHopTuoiNo_MucHanNo.jsp";
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
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		//obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		
		  String nhomkhachhang = request.getParameter("nhomkhachhang");
		    if (nhomkhachhang == null)
		    	nhomkhachhang = "";
		    obj.setNhomKhId(nhomkhachhang);
		//obj.setkenhId(util.antiSQLInspection(request.getParameter("kbhId"))!=null?
		//util.antiSQLInspection(request.getParameter("kbhId")):"");
		
	
		
		/*String[] nkhIds = request.getParameterValues("nkhIds");
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
		}*/
		
		
		obj.InitErp();
				
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/BCTongHopTuoiNo_MucHanNo.jsp";
		
		System.out.println("Action la: " + action);
	
		
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTongHopTuoiNo_MucHanNo.xlsm");

			try 
	        {
				if(!CreatePivotTable(out, response, request, obj, ""))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
			}
		}
		
		
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}
	

	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTongHopTuoiNo_MucHanNo.xlsm");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook, obj);
		//query = this.setQuery(obj);
		
		isFillData = FillData(workbook, obj, query);
   
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
	
	private void CreateHeader(Workbook workbook, IStockintransit obj) {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("BC Tuoi no KH");
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
		
		for (int i = 0; i < 1; i++) {
			worksheet = worksheets.getSheet(i);
			
			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ctyName);
		    
		    cells.setRowHeight(1, 20.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Địa chỉ: " + diachi); 
		    
		    cells.setRowHeight(2, 20.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế: " + masothue); 
		    
		    //cell = cells.getCell("D5"); cell.setValue("BÁO CÁO CÔNG NỢ TỔNG HỢP - TUỔI NỢ VÀ HẠN MỨC");

		    //cells.setRowHeight(2, 20.0f);
		    cell = cells.getCell("G8");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày: " ); 

		    cell = cells.getCell("H8");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getdenngay()); 
		}
		

		worksheet = worksheets.getSheet(1);
		Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("G3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày: " ); 

	    cell = cells.getCell("H3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getdenngay()); 
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
	
	private void setStyleColorGray(Cells cells ,Cell cell, String leftright,int number)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        	style.setNumber(number);
            cell.setStyle(style);
        }else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
        	style.setNumber(number);
            cell.setStyle(style);        	
        }
	}

	private void setStyleColorRed(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Z1");
		Style style;	
		 style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
	}
	
	/*private String getQuery(int num, String denngay, int isNPP, String ctyId){
		String query = 	"SELECT KHID, SUM(CONGNO) AS CONGNO \n " +
						"FROM ( \n " ;
//-- LẤY HÓA ĐƠN TÀI CHÍNH CÒN CHƯA THANH TOÁN HẾT
		if(isNPP == 0){ // KHÁCH HÀNG MUA TRỰC TIẾP
			query +=
						"SELECT HOADON.KHID, SUM(HOADON.TIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN , 0)) AS CONGNO \n " +
						"FROM (  \n " +
						"	SELECT 	KH.PK_SEQ AS KHID, HD.PK_SEQ AS HDID, HD.TONGTIENAVAT*(ISNULL(HD.TYGIA, 1)) AS TIENHD \n " +
						"	FROM 	ERP_HOADONNPP HD 	        		 \n " +
						"	INNER JOIN KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n " + 
//						"	LEFT JOIN ERP_HOADON_XUATKHO DDH ON DDH.HOADON_FK = HD.PK_SEQ  \n " +
//						"	LEFT JOIN ERP_XUATKHO XK ON DDH.XUATKHO_FK = XK.PK_SEQ   \n " +
						"	WHERE 	 \n " +
						"	HD.TRANGTHAI in (2, 4, 5) AND HD.LOAIHOADON = 0 \n " + //LOAIHOADON = 0: HÓA ĐƠN BÁN
						"	AND HD.CONGTY_FK IN (" + ctyId + ")  " ;
		}else{  // KHÁCH HÀNG LÀ NPP
			query +=
						"SELECT HOADON.KHID, SUM(HOADON.TIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN , 0)) AS CONGNO \n " +
						"FROM (  \n " +
						"	SELECT 	KH.PK_SEQ AS KHID, HD.PK_SEQ AS HDID, HD.TONGTIENAVAT*(ISNULL(HD.TYGIA, 1)) AS TIENHD \n " +
						"	FROM 	ERP_HOADONNPP HD 	        		 \n " +
						"	INNER JOIN NHAPHANPHOI KH ON HD.NPP_DAT_FK = KH.PK_SEQ \n " + 
//						"	LEFT JOIN ERP_HOADON_XUATKHO DDH ON DDH.HOADON_FK = HD.PK_SEQ  \n " +
//						"	LEFT JOIN ERP_XUATKHO XK ON DDH.XUATKHO_FK = XK.PK_SEQ   \n " +
						"	WHERE 	 \n " +
						"	HD.TRANGTHAI in (2, 4, 5) AND HD.LOAIHOADON = '0' \n " +
						"	AND HD.CONGTY_FK IN (" + ctyId + ")  " ;
		}			
		
		if(num > 0){
			query +=	"	AND HD.NGAYXUATHD >= convert(char(10) , DATEADD(day, " + (-1)*num + ", cast('" + denngay + "' as datetime)) , 120) \n " ;
			if(num != 90){
				query +=
						"	AND HD.NGAYXUATHD < convert(char(10), DATEADD(day,  " + ((-1)*num + 15) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;
			}else{
				query +=
						"	AND HD.NGAYXUATHD < convert(char(10), DATEADD(day,  " + ((-1)*num + 30) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;				
			}
		}else{
			query +=	"	AND HD.NGAYXUATHD < convert(char(10) , DATEADD(day, -90, cast('" + denngay + "' as datetime)) , 120) \n " ;
		}

					
		query +=
						") HOADON  \n " +
						"LEFT JOIN (  \n " +
						"	SELECT KHID, HDID, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n " + 
						"	FROM   \n " +
						"	( 	 \n " +
						"		SELECT TT.KHACHHANG_FK AS KHID, TTHD.HOADON_FK AS HDID, TTHD.SOTIENTT AS DATHANHTOAN \n " + 
						"		FROM ERP_XOAKHTRATRUOC_HOADON TTHD  \n " +
						"		INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n " + 
						"		WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0'  \n " +
						"		AND TTHD.LOAIHD = '0'  \n " +
						"		AND TT.CONGTY_FK IN (" + ctyId + ")  " +
					
						"		UNION ALL  \n " +
						"		SELECT TT.KHACHHANG_FK AS KHID, TTHD.HOADON_FK AS HDID, TTHD.SOTIENTT AS DATHANHTOAN \n " + 
						"		FROM ERP_THUTIEN_HOADON TTHD  \n " +
						"		INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n " + 
						"		INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = TTHD.HOADON_FK \n " + 
						"		WHERE HD.LOAIHOADON = 0 AND TT.TRANGTHAI NOT IN (2) \n " + 		
						"		AND TT.CONGTY_FK IN (" + ctyId + ")  " +
 			 		
						"		UNION ALL  \n " +
						"		SELECT BT.KH_CHUYENNO AS KHID, BT_KH.HOADON_FK AS HDID, BT_KH.XOANO AS SOTIENBUTRU   \n " + 
						"		FROM   ERP_BUTRUKHACHHANG BT  \n " + 
						"		INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +  
						"		WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0   \n " + 
						"		AND BT.KH_CHUYENNO IS NOT NULL \n " + 
						"		AND BT.CONGTY_FK IN (" + ctyId + ")  " +
 			
						"	) HOADONDATT   \n " + 
						"	GROUP BY KHID, HDID  \n " + 
						")DATHANHTOAN ON HOADON.HDID = DATHANHTOAN.HDID AND HOADON.KHID = DATHANHTOAN.KHID \n " + 
		
						"GROUP BY HOADON.KHID \n " + 
						"HAVING SUM(HOADON.TIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) > 0 \n " +  

//-- LÁY HÓA ĐƠN PHẾ LIỆU
						"UNION ALL   \n " ;
		if(isNPP == 0){ // KHÁCH HÀNG MUA TRỰC TIẾP
			query +=
						"SELECT HDPHELIEU.KHID, SUM(HDPHELIEU.SOTIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN,0)) AS CONGNO \n " +
    
						"FROM   \n " +
						"(  \n " +
						"	SELECT KH.PK_SEQ AS KHID, PL.PK_SEQ AS HDID, CAST( (PLSP.SOTIENVND + PLSP.SOTIENVND*PL.VAT/100) AS NUMERIC(18,0) ) AS SOTIENHD \n " +
		
						"	FROM ERP_HOADONPHELIEU PL      \n " +
						"	INNER JOIN KHACHHANG KH ON PL.KHACHHANG_FK = KH.PK_SEQ \n " + 
						"	INNER JOIN  \n " +
						"	( \n " +
						"		SELECT HOADONPHELIEU_FK, SUM(THANHTIEN)AS SOTIENVND \n " + 
						"		FROM ERP_HOADONPHELIEU_SANPHAM \n " + 
						"		GROUP BY HOADONPHELIEU_FK \n " +
						"	)AS PLSP  ON PL.PK_SEQ = PLSP.HOADONPHELIEU_FK \n " + 
						"	WHERE PL.TRANGTHAI = '1'   \n " +
						"	AND PL.CONGTY_FK IN (" + ctyId + ")  " ;

		}else{
			query +=
						"SELECT HDPHELIEU.KHID, SUM(HDPHELIEU.SOTIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN,0)) AS CONGNO \n " +

						"FROM   \n " +
						"(  \n " +
						"	SELECT KH.PK_SEQ AS KHID, PL.PK_SEQ AS HDID, " +
						"	CAST( (PLSP.SOTIENVND + PLSP.SOTIENVND*PL.VAT/100) AS NUMERIC(18,0) ) AS SOTIENHD \n " +
						
						"	FROM ERP_HOADONPHELIEU PL      \n " +
						"	INNER JOIN NHAPHANPHOI KH ON PL.KHACHHANG_FK = KH.PK_SEQ \n " + 
						"	INNER JOIN  \n " +
						"	( \n " +
						"		SELECT HOADONPHELIEU_FK, SUM(THANHTIEN)AS SOTIENVND \n " + 
						"		FROM ERP_HOADONPHELIEU_SANPHAM \n " + 
						"		GROUP BY HOADONPHELIEU_FK \n " +
						"	)AS PLSP  ON PL.PK_SEQ= PLSP.HOADONPHELIEU_FK \n " + 
						"	WHERE PL.TRANGTHAI = '1'   \n " +
						"	AND PL.CONGTY_FK IN (" + ctyId + ")  " ;
		}
						 
		if(num > 0){
			query +=	"	AND PL.NGAYGHINHAN >= convert(char(10) , DATEADD(day,  " + (-1)*num + ", cast('" + denngay + "' as datetime)) , 120) \n " ;
			
			if(num != 90){
				query += "	AND PL.NGAYGHINHAN < convert(char(10), DATEADD(day, " + ((-1)*num + 15) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;
			}else{
				query += "	AND PL.NGAYGHINHAN < convert(char(10), DATEADD(day, " + ((-1)*num + 30) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;
			}
				
						
		}else{
			query +=	"	AND PL.NGAYGHINHAN < convert(char(10) , DATEADD(day, -90, cast('" + denngay + "' as datetime)) , 120) \n " ;
			
		}

		query +=		")HDPHELIEU  \n " +
	
						"LEFT JOIN (  \n " +
						"	SELECT KHID, HDID, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n " + 
						"	FROM   \n " +
						"	( 	 \n " +
						"		SELECT TT.KHACHHANG_FK AS KHID, TTHD.HOADON_FK AS HDID, TTHD.SOTIENTT AS DATHANHTOAN \n " + 
						"		FROM ERP_XOAKHTRATRUOC_HOADON TTHD  \n " +
						"		INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n " + 
						"		WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n " + 		
						"		AND TTHD.LOAIHD = '1' AND TT.CONGTY_FK IN (" + ctyId + ")   \n " + 
                             
						"		UNION ALL    \n " +   
		
						"		SELECT TT.KHACHHANG_FK AS KHID, TTHD.HOADON_FK AS HDID, TTHD.SOTIENTT AS DATHANHTOAN \n " + 
						"		FROM ERP_THUTIEN_HOADON TTHD  \n " +
						"		INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n " + 
						"		WHERE TTHD.LOAIHOADON = '1' AND TT.TRANGTHAI NOT IN (2) AND TT.CONGTY_FK IN (" + ctyId + ")   \n " +
		
						"		UNION ALL \n " + 
						"		SELECT BT.KH_CHUYENNO AS KHID,BT_KH.HOADON_FK AS HDID, BT_KH.XOANO AS SOTIENBUTRU \n " +  
						"		FROM   ERP_BUTRUKHACHHANG BT  \n " +
						"		INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " + 
						"		WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 \n " +  
						"		AND BT.KH_CHUYENNO IS NOT NULL AND BT.CONGTY_FK IN (" + ctyId + ")   \n " +
	 		
						"	) HOADONDATT \n " +  
						"	GROUP BY KHID, HDID \n " +
						")DATHANHTOAN ON HDPHELIEU.KHID = DATHANHTOAN.KHID AND HDPHELIEU.HDID = DATHANHTOAN.HDID \n " +
						"GROUP BY HDPHELIEU.KHID \n " +
						"HAVING SUM(ISNULL(HDPHELIEU.SOTIENHD,0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) > 0 \n " +  

//-- LẤY BÚT TOÁN TỔNG HỢP
						"UNION ALL \n " +
						"SELECT BTTH.KHID, SUM(BTTH.SOTIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) AS CONGNO \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT 	BTTH_CT.KHACHHANG_FK AS KHID, BTTH.PK_SEQ AS HDID, (ISNULL(BTTH_CT.NO,0)) AS SOTIENHD \n " +
						"	FROM ERP_BUTTOANTONGHOP BTTH \n " +
						"	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTH_CT on BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n " + 
						"	WHERE TRANGTHAI = 1 AND BTTH_CT.NO > 0 " +
						
						"	AND BTTH_CT.TAIKHOANKT_FK  IN " +
						"					(SELECT PK_SEQ from ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + ctyId + ")   " +
						"					 AND SOHIEUTAIKHOAN IN ('13111000', '13680000') ) \n " +
						
 
						"	AND PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_XOAKHTRATRUOC_HOADON) \n " +
						"	AND BTTH_CT.isNPP = " + isNPP + " " ;
						

		if(num > 0){
			query +=	"	AND BTTH.NGAYBUTTOAN >= convert(char(10) , DATEADD(day," + (-1)*num + ", cast('" + denngay + "' as datetime)) , 120) \n " ;
			
			if(num != 90){
				query +=
						"	AND BTTH.NGAYBUTTOAN < convert(char(10), DATEADD(day, " + ((-1)*num + 15) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;
					
			}else{
				query +=
					"	AND BTTH.NGAYBUTTOAN < convert(char(10), DATEADD(day, " + ((-1)*num + 30) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;
				
			}
						
		}else{
			query +=	"	AND BTTH.NGAYBUTTOAN < convert(char(10) , DATEADD(day, -90, cast('" + denngay + "' as datetime)) , 120) \n " ;
		}

		query +=		")BTTH \n " +
						"LEFT JOIN ( \n " + 
						"	SELECT KHID, HDID, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN \n " +  
						"	FROM \n " +
						"	( \n " +  
						"		SELECT TT.KHACHHANG_FK AS KHID, TTHD.HOADON_FK AS HDID, TTHD.SOTIENTT as DATHANHTOAN \n " +    
						"		FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n " + 
						"		INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n " +  
						"		WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '2' AND TT.CONGTY_FK IN (" + ctyId + ")   \n " + 
								
						"		UNION ALL \n " +
						
						"		SELECT TT.KHACHHANG_FK AS KHID, TTHD.HOADON_FK, TTHD.SOTIENTT as DATHANHTOAN \n " +   
						"		FROM ERP_THUTIEN_HOADON TTHD \n " + 
						"		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n " +  
						"		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON= '2' AND TT.CONGTY_FK IN (" + ctyId + ")   \n " + 
						"	)HOADONDATT \n " +  
						"GROUP BY KHID, HDID \n " +
								
						")DATHANHTOAN ON BTTH.KHID = DATHANHTOAN.KHID AND BTTH.HDID = DATHANHTOAN.HDID \n " +
						"GROUP BY BTTH.KHID \n " +
						"HAVING ROUND(SUM(BTTH.SOTIENHD) - SUM(ISNULL(DATHANHTOAN.DATHANHTOAN, '0')), 0) <> 0 \n " + 

//-- LẤY BÙ TRỪ CÔNG NỢ KHÁCH HÀNG 
						"UNION ALL \n " + 
						"SELECT  BT.KH_NHANNO, (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) AS CONGNO \n " +
 						"FROM ERP_BUTRUKHACHHANG BT         \n " +
						"INNER JOIN ERP_KHACHHANG KH ON BT.KH_NHANNO = KH.PK_SEQ \n " +       
						"LEFT JOIN ( \n " +					
						"	SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n " + 
						"	FROM ERP_THUTIEN_HOADON TTHD \n " + 
						"	INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n " + 
						"	WHERE TTHD.LOAIHOADON= '4' AND TT.TRANGTHAI NOT IN (2) \n " + 
						"	GROUP BY TTHD.HOADON_FK  \n " +
						") DATHANHTOAN ON BT.PK_SEQ = DATHANHTOAN.HOADON_FK \n " +  
	
						"WHERE BT.TRANGTHAI = 1 AND (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) > 0 \n " +
						"AND BT.CONGTY_FK IN (" + ctyId + ")   \n " ;
						
		if(num > 0){
			query +=	"AND BT.NGAYBUTRU >= convert(char(10) , DATEADD(day, " + (-1)*num + ", cast('" + denngay + "' as datetime)) , 120) \n " ;
			
			if(num != 90){
				query +=
						"AND BT.NGAYBUTRU < convert(char(10), DATEADD(day, " + ((-1)*num + 15) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;
			}else{
				query +=
					"AND BT.NGAYBUTRU < convert(char(10), DATEADD(day, " + ((-1)*num + 30) + " , cast('" + denngay + "' as datetime)) , 120)  \n " ;				
			}
						
		}else{
			query +=	"AND BT.NGAYBUTRU < convert(char(10) , DATEADD(day, -90, cast('" + denngay + "' as datetime)) , 120) \n " ;
			
		}
		query +=	")DATA GROUP BY DATA.KHID";
	
		System.out.println("Get Sql : "+ query);
		 
		return query;
	}
	
	public String setQuery(IStockintransit obj)
	{
		 Utility util = new Utility();
		
		 String query = "";
		 
		 
		 query =" 	SELECT KH.PK_SEQ, MA,TEN,TAIKHOAN_FK,DIACHI, ISNULL(THOIHANNO, 0) AS THOIHANNO, ISNULL(HANMUCNO, 0) AS HANMUCNO," +
		 		"	ISNULL( NO15.CONGNO,0) AS NO15, \n " +
				"	ISNULL( NO30.CONGNO,0) AS NO30 ,ISNULL( NO45.CONGNO,0) AS NO45 ,ISNULL( NO60.CONGNO,0) AS  NO60,  \n " +
				"	ISNULL( NO90.CONGNO,0) AS NO90, ISNULL(OTHER.CONGNO, 0) AS OTHER  \n " +

		 		"	FROM KHACHHANG  KH  \n " +
		 		"	LEFT JOIN ( \n  " +
			
					getQuery(15, obj.getdenngay(), 0, obj.getErpCongtyId()) + "\n " +
			
				"	) NO15 ON NO15.KHID = KH.PK_SEQ  \n " +
				"	LEFT JOIN (  \n " +

					getQuery(30, obj.getdenngay(), 0, obj.getErpCongtyId()) + "\n " +

				"	) NO30 ON NO30.KHID = KH.PK_SEQ  \n " +
				"	LEFT JOIN (  \n " +
			 
					getQuery(45, obj.getdenngay(), 0, obj.getErpCongtyId()) + "\n " +

				"	) NO45 ON NO45.KHID = KH.PK_SEQ  \n " +

				"	LEFT JOIN (  \n " +

					getQuery(60, obj.getdenngay(), 0, obj.getErpCongtyId()) + "\n " +

				"	) NO60 ON NO60.KHID = KH.PK_SEQ  \n " +
				"	LEFT JOIN (  \n " +
					getQuery(90, obj.getdenngay(), 0, obj.getErpCongtyId()) + "\n " +

				"	) NO90 ON NO90.KHID = KH.PK_SEQ  \n " +
			
				"	LEFT JOIN (  \n " +
					getQuery(0, obj.getdenngay(), 0, obj.getErpCongtyId()) + "\n " +
			
				"	) OTHER ON OTHER.KHID = KH.PK_SEQ  \n "+
				" WHERE 1=1 " + util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() ) ;

		 if(obj.getNhomKhId().trim().length() >0){
			 query += " and  kh.nhomkhachhang_fk in ("+ obj.getNhomKhId() +") ";
		 }

		 if(obj.GetKhId().length() > 0)
			 query += " and KH.pk_seq in ( " + obj.GetKhId() +  " ) ";
		
		 query += " UNION ALL " +
		 
		 		" 	SELECT KH.PK_SEQ, MA,TEN,TAIKHOAN_FK,DIACHI, ISNULL(THOIHANNO, 0) AS THOIHANNO, ISNULL(HANMUCNO, 0) AS HANMUCNO," +
		 		"	ISNULL( NO15.CONGNO,0) AS NO15, \n " +
				"	ISNULL( NO30.CONGNO,0) AS NO30 ,ISNULL( NO45.CONGNO,0) AS NO45 ,ISNULL( NO60.CONGNO,0) AS  NO60,  \n " +
				"	ISNULL( NO90.CONGNO,0) AS NO90, ISNULL(OTHER.CONGNO, 0) AS OTHER  \n " +

				"	FROM NHAPHANPHOI  KH  \n " +
				"	LEFT JOIN ( \n  " +
		
				getQuery(15, obj.getdenngay(), 1, obj.getErpCongtyId()) + "\n " +
		
				"	) NO15 ON NO15.KHID = KH.PK_SEQ  \n " +
				"	LEFT JOIN (  \n " +

				getQuery(30, obj.getdenngay(), 1, obj.getErpCongtyId()) + "\n " +

				"	) NO30 ON NO30.KHID = KH.PK_SEQ  \n " +
				"	LEFT JOIN (  \n " +
		 
				getQuery(45, obj.getdenngay(), 1, obj.getErpCongtyId()) + "\n " +

				"	) NO45 ON NO45.KHID = KH.PK_SEQ  \n " +

				"	LEFT JOIN (  \n " +

				getQuery(60, obj.getdenngay(), 1, obj.getErpCongtyId()) + "\n " +

				"	) NO60 ON NO60.KHID = KH.PK_SEQ  \n " +
				"	LEFT JOIN (  \n " +
				getQuery(90, obj.getdenngay(), 1, obj.getErpCongtyId()) + "\n " +

				"	) NO90 ON NO90.KHID = KH.PK_SEQ  \n " +
		
				"	LEFT JOIN (  \n " +
				getQuery(0, obj.getdenngay(), 1, obj.getErpCongtyId()) + "\n " +
		
				"	) OTHER ON OTHER.KHID = KH.PK_SEQ  \n "+
				" WHERE 1=1 " ;

		 if(obj.getNhomKhId().trim().length() >0){
			 query += " and  kh.nhomkhachhang_fk in ("+ obj.getNhomKhId() +") ";
		 }

		 if(obj.GetKhId().length() > 0)
			 query += " and KH.pk_seq in ( " + obj.GetKhId() +  " ) ";
		 
		 System.out.println("Get Sql : "+ query);
		 return query;   
	}*/
	
	private ResultSet getSearchRs(String denNgay, String npp_fk, dbutils db, int loai,String nhomkhachhang) {
		/*
		 * loai 1: Tuoi no tong hop
		 * loai 0: Tuoi no chi tiet
		 */
//		int thangKyTruoc = 0, namKyTruoc = 0; 
//		int thangKyNay = 0, namKyNay = 0;
//		String ngayDauKyNay = "";
//		namKyNay = Integer.parseInt(denNgay.substring(0, 4));
//		thangKyNay = Integer.parseInt(denNgay.substring(5, 7));
//		namKyTruoc = namKyNay;
//		if(thangKyNay == 1) {
//			thangKyTruoc = 12;
//			namKyTruoc--;
//		} else {
//			thangKyTruoc = thangKyNay - 1;
//		}
//		ngayDauKyNay = denNgay.substring(0, 7) +"-01";
		int thangdauky ;
		int namdauky ;
		int lastmonth ;
		int lastyear ;
		lastyear = Integer.parseInt(denNgay.substring(0, 4)) - 1;
		if(Integer.parseInt(denNgay.substring(6, 7)) >1 )
			lastmonth = Integer.parseInt(denNgay.substring(6, 7)) - 1;
		else
			lastmonth = 12;
		if(lastmonth != 12){
			thangdauky = lastmonth;
			namdauky = Integer.parseInt(denNgay.substring(0, 4));
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
			}
		}
		if(thangKS >= thangdauky && namKS >= namdauky){
			
		}else{
			thangdauky = thangKS;
			namdauky = namKS;
		}
		
		String thangDauky = "0"+ thangdauky;
		thangDauky = thangDauky.substring(thangDauky.length() -2);
		String query = "";
		String querynkh_kh="";
		String querynkh_npp="";
		if(nhomkhachhang.length()>0)
		{
			querynkh_kh+= " and ( kh.pk_seq in (SELECT CAST(NPP_FK AS NVARCHAR) " +
					"				FROM LINK_ERP_THAT_NOIBO.TRAPHACOERP.DBO.NHOMKHACHHANGNPP_NPP NKHNPP \n" + 
					"			   	LEFT JOIN LINK_ERP_THAT_NOIBO.TRAPHACOERP.DBO.NHOMKHACHHANGNPP NKH ON NKHNPP.NHOMKHACHHANGNPP_FK= NKH.PK_SEQ \n" + 
					"			    WHERE NKHNPP.LOAINPP=3 AND CAST(NKH.PK_SEQ AS NVARCHAR(200))="+nhomkhachhang+") )" ;
			
			querynkh_npp+=	" and ( npp.pk_seq in (SELECT CAST(NPP_FK AS NVARCHAR)  \n" + 
					"				 FROM LINK_ERP_THAT_NOIBO.TRAPHACOERP.DBO.NHOMKHACHHANGNPP_NPP NKHNPP \n" + 
					"			   	 LEFT JOIN LINK_ERP_THAT_NOIBO.TRAPHACOERP.DBO.NHOMKHACHHANGNPP NKH ON NKHNPP.NHOMKHACHHANGNPP_FK= NKH.PK_SEQ \n"  + 
					"			     WHERE NKHNPP.LOAINPP in(1,2) AND CAST(NKH.PK_SEQ AS NVARCHAR(200))="+nhomkhachhang+") )" ;
		}
		if (loai == 1) {
			query = 
					"SELECT KH.*, ISNULL(NOCONLAI.THOIHANNO, 0) AS THOIHANNO, ISNULL(NOCONLAI.HANMUCNO, 0) HANMUCNO, \r\n" + /*
					"ISNULL(DKPS.DUNODAUKY, 0) DUNODAUKY, ISNULL(DKPS.DUCODAUKY, 0) DUCODAUKY, \r\n" + 
					"ISNULL(DKPS.PHATSINHNO, 0) PHATSINHNO, ISNULL(DKPS.PHATSINHCO, 0) PHATSINHCO, \r\n" + */
					"ISNULL(DKPS.DUNOCUOIKY, 0) DUNOCUOIKY, ISNULL(DKPS.DUCOCUOIKY, 0) DUCOCUOIKY,\r\n" + 
					"ISNULL(NOCONLAI.TRONGHAN, 0) TRONGHAN, ISNULL(TONGNOQUAHAN, 0) TONGNOQUAHAN, \r\n" + 
					"ISNULL(NOCONLAI.NO60, 0) NO60, ISNULL(NOCONLAI.NO120, 0) NO120, ISNULL(NOCONLAI.NO180, 0) NO180, \r\n" + 
					"ISNULL(NOCONLAI.NO181, 0) NO181, ISNULL(NOCONLAI.NOVUOTHANMUC, 0) AS NOVUOTHANMUC\r\n" + 
					"FROM\r\n" + 
					"(	--TINH DU NO DAU KY-PHAT SINH-CUOI KY\r\n" + 
					"	SELECT\r\n" + 
					"		CASE \r\n" + 
					"			WHEN ISNULL(DAUKY.ISNPP, PHATSINH.ISNPP) = 0 THEN 'KH'+CONVERT(VARCHAR, ISNULL(DAUKY.MADOITUONG, PHATSINH.MADOITUONG))\r\n" + 
					"			WHEN ISNULL(DAUKY.ISNPP, PHATSINH.ISNPP) = 1 THEN 'NPP'+CONVERT(VARCHAR, ISNULL(DAUKY.MADOITUONG, PHATSINH.MADOITUONG))\r\n" + 
					"		END AS KH_PK_SEQ,\r\n" + 
					/*"		SUM(ISNULL(DUNODAUKY, 0)) AS DUNODAUKY, SUM(ISNULL(DUCODAUKY, 0)) AS DUCODAUKY, \r\n" + 
					"		SUM(ISNULL(NO, 0)) AS PHATSINHNO, SUM(ISNULL(CO, 0)) AS PHATSINHCO,\r\n" + */
					"		CASE WHEN SUM(ISNULL(DUNODAUKY, 0)) - SUM(ISNULL(DUCODAUKY, 0)) + SUM(ISNULL(NO, 0)) - SUM(ISNULL(CO, 0)) > 0 \r\n" + 
					"			THEN SUM(ISNULL(DUNODAUKY, 0)) - SUM(ISNULL(DUCODAUKY, 0)) + SUM(ISNULL(NO, 0)) - SUM(ISNULL(CO, 0))\r\n" + 
					"			ELSE 0\r\n" + 
					"		END AS DUNOCUOIKY,\r\n" + 
					"		CASE WHEN SUM(ISNULL(DUCODAUKY, 0)) - SUM(ISNULL(DUNODAUKY, 0)) + SUM(ISNULL(CO, 0)) - SUM(ISNULL(NO, 0)) > 0 \r\n" + 
					"			THEN SUM(ISNULL(DUCODAUKY, 0)) - SUM(ISNULL(DUNODAUKY, 0)) + SUM(ISNULL(CO, 0)) - SUM(ISNULL(NO, 0))\r\n" + 
					"			ELSE 0\r\n" + 
					"		END AS DUCOCUOIKY\r\n" + 
					"	FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT MADOITUONG, ISNPP, SUM(DUNODAUKY) AS DUNODAUKY, SUM(DUCODAUKY) AS DUCODAUKY FROM (\r\n" + 
					"			SELECT MADOITUONG, ISNULL(ISNPP, 0) AS ISNPP, SUM(ROUND(GIATRICOVND, 0)) AS DUCODAUKY, SUM(ROUND(GIATRINOVND, 0)) AS DUNODAUKY\r\n" + 
					"			FROM ERP_TAIKHOAN_NOCO_KHOASO KS\r\n" + 
					"				WHERE KS.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK IN ("+npp_fk+"))\r\n" + 
					"				AND THANG = "+thangDauky+" AND NAM = "+namdauky+" AND DOITUONG = N'Khách hàng'\r\n" + 
					"			GROUP BY MADOITUONG, ISNULL(ISNPP, 0)" + 
					"		) DK_TEMP\r\n" + 
					"		GROUP BY MADOITUONG, ISNPP\r\n" + 
					"	) DAUKY\r\n" + 
					"	FULL OUTER JOIN\r\n" + 
					"	(\r\n" + 
					"		SELECT MADOITUONG, ISNPP, SUM(NO) AS NO, SUM(CO) AS CO\r\n" + 
					"		FROM (\r\n" + //LAY NGAY CUOI CUNG CUA THANG KHOA SO
					"			SELECT MADOITUONG, ISNULL(ISNPP, 0) AS ISNPP, SUM(ROUND(NO, 0)) AS NO, SUM(ROUND(CO, 0)) AS CO\r\n" + 
					"			FROM ERP_PHATSINHKETOAN PS\r\n" + 
					"			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK IN ("+npp_fk+"))\r\n" + 
					"			AND NGAYGHINHAN > '"+namdauky+"-"+thangDauky+"-32"+"' AND NGAYGHINHAN <= '"+denNgay+"' AND DOITUONG = N'Khách hàng' \r\n" + 
					"			GROUP BY MADOITUONG, ISNULL(ISNPP, 0)\r\n" + 
					"			UNION ALL\r\n" + 
					"			SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", 'SELECT MADOITUONG, ISNULL(ISNPP, 0) AS ISNPP, SUM(ROUND(NO, 0)) AS NO, SUM(ROUND(CO, 0)) AS CO\r\n" + 
					"			FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS\r\n" + 
					"			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM  " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE ''131%'' AND NPP_FK IN ("+npp_fk+"))\r\n" + 
					"			AND NGAYGHINHAN > ''"+namdauky+"-"+thangDauky+"-32"+"'' AND NGAYGHINHAN <= ''"+denNgay+"'' AND DOITUONG = N''Khách hàng'' \r\n" + 
					"			GROUP BY MADOITUONG, ISNULL(ISNPP, 0)')\r\n" + 
					"		) PS_TEMP\r\n" + 
					"		GROUP BY MADOITUONG, ISNPP\r\n" + 
					"	) PHATSINH ON DAUKY.MADOITUONG = PHATSINH.MADOITUONG AND DAUKY.ISNPP = PHATSINH.ISNPP\r\n" + 
					"	GROUP BY ISNULL(DAUKY.ISNPP, PHATSINH.ISNPP), ISNULL(DAUKY.MADOITUONG, PHATSINH.MADOITUONG)\r\n" + 
					") DKPS FULL OUTER JOIN\r\n" + 
					"(\r\n" + 
					"	SELECT  TEMP.KH_PK_SEQ, TEMP.THOIHANNO, TEMP.HANMUCNO,\r\n" + 
					"	SUM(TRONGHAN) AS TRONGHAN, SUM(NO60) AS NO60, SUM(NO120) AS NO120, SUM(NO180) AS NO180, SUM(NO181) AS NO181,\r\n" + 
					"		SUM(NO60) + SUM(NO120) + SUM(NO180) + SUM(NO181) AS TONGNOQUAHAN,\r\n" + 
					"		CASE WHEN (SUM(NO60) + SUM(NO120) + SUM(NO180) + SUM(NO181)) - HANMUCNO > 0 \r\n" + 
					"			THEN (SUM(NO60) + SUM(NO120) + SUM(NO180) + SUM(NO181)) - HANMUCNO \r\n" + 
					"			ELSE 0\r\n" + 
					"		END AS NOVUOTHANMUC\r\n" + 
					"	FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT TEMP.KH_PK_SEQ, TEMP.THOIHANNO, TEMP.HANMUCNO,\r\n" + 
					"			CASE WHEN SONGAYNO = 0 OR CONGNO < 0 THEN CONGNO ELSE 0 END AS TRONGHAN,\r\n" + 
					"			CASE WHEN SONGAYNO BETWEEN 1 AND 60 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO60,\r\n" + 
					"			CASE WHEN SONGAYNO BETWEEN 61 AND 120 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO120,\r\n" + 
					"			CASE WHEN SONGAYNO BETWEEN 121 AND 180 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO180,\r\n" + 
					"			CASE WHEN SONGAYNO > 180 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO181\r\n" + 
					"		FROM\r\n" + 
					"		(\r\n" + 
					"				SELECT KH_PK_SEQ, ISNULL(THOIHANNO, 0) THOIHANNO, ISNULL(HANMUCNO, 0) HANMUCNO, PK_SEQ, SONGAYNO, SUM(SOTIENTANGNO) - SUM(SOTIENGIAMNO) AS CONGNO\r\n" + 
					"				FROM\r\n" + 
					"				(\r\n" + 
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN VOI KHr\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN KH\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPP.PK_SEQ IS  NOT NULL THEN ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) ELSE ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ,\r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(KH.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(KH.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(KH.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "HOADON HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 0 AND LOAIHD in (N''HDCN_OTC'',N''HDQ_OTC'') \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "KHACHHANG KH \r\n" + 
					"							WHERE TRANGTHAI = 1						\r\n" +  querynkh_kh +
					"						) KH ON KH.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK AND ISNULL(NPP.CONGNOCHUNG,0)=1\r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADONNPP_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "ERP_THUTIENNPP_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADONNPP_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BTHD.HOADON_FK, SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) - SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "BUTRUCONGNO BT\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "BUTRUCONGNO_HOADON BTHD ON BT.PK_SEQ = BTHD.BTCN_FK\r\n" + 
					"										WHERE BT.TRANGTHAI = 1 AND BT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY BTHD.HOADON_FK, KHACHHANG_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO HOADON \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" + 
					
					///////////////////////////// ERP_HOADONNPP (ETC)
					
					"				UNION ALL \r\n" +
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN VOI KHr\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN KH\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPP.PK_SEQ IS  NOT NULL THEN ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) ELSE ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(KH.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(KH.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(KH.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "ERP_HOADONNPP HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 0 AND LOAIHD in (N''HDCN_ETC_MauHO'',N''HDQ_ETC'') \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "KHACHHANG KH \r\n" + 
					"							WHERE TRANGTHAI = 1						\r\n" + 
					"						) KH ON KH.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK AND ISNULL(NPP.CONGNOCHUNG,0)=1 \r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADONNPP_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "ERP_THUTIENNPP_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADONNPP_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BTHD.HOADON_FK, SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) - SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "BUTRUCONGNO BT\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "BUTRUCONGNO_HOADON BTHD ON BT.PK_SEQ = BTHD.BTCN_FK\r\n" + 
					"										WHERE BT.TRANGTHAI = 1 AND BT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY BTHD.HOADON_FK, KHACHHANG_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO HOADON \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" + 
					"					UNION ALL \r\n" + 
					//////////////////////////////////// ERP_HOADONNPP (ETC)- CN/DT NHA PHAN PHOI
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN VOI KHr\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN KH\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPPQUAY.PK_SEQ IS  NOT NULL THEN ''NPP''+CONVERT(VARCHAR, NPPQUAY.PK_SEQ) ELSE ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "ERP_HOADONNPP HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 1 AND LOAIHD in (N''HDCN_ETC_MauHO'',N''HDQ_ETC'') \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"							LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"							WHERE TRANGTHAI = 1								\r\n" +  querynkh_npp + 
					"						) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPPQUAY ON NPPQUAY.PK_SEQ = HD.NPP_FK AND ISNULL(NPPQUAY.CONGNOCHUNG,0)=1 \r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADONNPP_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "ERP_THUTIENNPP_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.NPP_FK, HOADONNPP_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BTHD.HOADON_FK, SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) - SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "BUTRUCONGNO BT\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "BUTRUCONGNO_HOADON BTHD ON BT.PK_SEQ = BTHD.BTCN_FK\r\n" + 
					"										WHERE BT.TRANGTHAI = 1 AND BT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY BTHD.HOADON_FK, KHACHHANG_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO HOADON \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" + 
					
					////////////////////////////////////////////
					"				UNION ALL \r\n" +
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI\r\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPPQUAY.PK_SEQ IS NOT NULL THEN ''NPP'' +CONVERT(VARCHAR, NPPQUAY.PK_SEQ)  ELSE ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "ERP_HOADON HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 1 AND LOAIHD=N''HDHO'' \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"							LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"							WHERE TRANGTHAI = 1						\r\n" + querynkh_npp+ 
					"						) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPPQUAY ON NPPQUAY.PK_SEQ = HD.NPP_FK AND ISNULL(NPPQUAY.CONGNOCHUNG,0)=1 \r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					
					//////////////////////// HÓA ĐƠN CỦA HO THÌ LÊN ERP THANH TOÁN
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  ERP_THUTIEN_HOADON TTHD\r\n" + 
					"										INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY  HOADON_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" +
					"				  UNION ALL \r\n" +

					"				--BEGIN Bút toán tổng hợp TANG HOAC GIAM NO\r\n" + 
					"					SELECT KH_PK_SEQ AS KH_PK_SEQ,\r\n" + 
					"							BTTH_CT.NGAYBUTTOAN AS NGAYCHUNGTU,\r\n" + 
					"							BTTH_CT.NGAYGHINHAN,\r\n" + 
					"							BTTH_CT.PK_SEQ,\r\n" + 
					"							SOTIENTANGNO,\r\n" + 
					"							SOTIENGIAMNO,\r\n" + 
					"							THOIHANNO, \r\n" + 
					"							HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(THOIHANNO,0),BTTH_CT.NGAYBUTTOAN),126), '"+denNgay+"')) SONGAYNO\r\n" + 
					"					FROM\r\n" + 
					"					(\r\n" + 
					"						SELECT  KHACHHANG.KH_PK_SEQ,\r\n" + 
					"								BTTH.PK_SEQ, BTTH.NGAYBUTTOAN, PS.NGAYGHINHAN, MADOITUONG, PS.ISNPP,\r\n" + 
					"								ROUND(ISNULL(PS.CO, 0), 0) AS SOTIENGIAMNO,\r\n" + 
					"								ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0)  AS SOTIENTANGNO,\r\n" + 
					"						THOIHANNO,\r\n" + 
					"						HANMUCNO\r\n" + 
					"						FROM ERP_BUTTOANTONGHOP BTTH \r\n" + 
					"						INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \r\n" + 
					"						LEFT JOIN ( \r\n" + 
					"							SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1\r\n" +  querynkh_npp+ 
					"								UNION ALL \r\n" + 
					"								SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS KH_PK_SEQ, KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO, 0 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" +
					"								WHERE TRANGTHAI = 1"+ querynkh_kh + "')\r\n" + 
					"						) KHACHHANG ON CONVERT(VARCHAR, KHACHHANG.PK_SEQ) = MADOITUONG AND ISNULL(PS.ISNPP, 0) = KHACHHANG.ISNPP" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM ERP_XOAKHTRATRUOC_HOADON TTHD\r\n" + 
					"										INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2)\r\n" + 
					"										AND TTHD.LOAIHD = 10\r\n" + 
					"										AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM ERP_THUTIEN_HOADON TTHD\r\n" + 
					"										INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = 10\r\n" + 
					"										AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"										GROUP BY  HOADON_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BT_KH.HOADON_FK, SUM(ROUND(BT_KH.XOANO, 0)) AS SOTIENBUTRU\r\n" + 
					"										FROM ERP_BUTRUKHACHHANG BT\r\n" + 
					"										INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK\r\n" + 
					"										WHERE BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 5\r\n" + 
					"										AND BT.KH_CHUYENNO IS NOT NULL AND BT.NGAYBUTRU < '"+denNgay+"'\r\n" + 
					"										GROUP BY BT_KH.HOADON_FK \r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON BTTH.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"						WHERE  LOAICHUNGTU = N'Bút toán tổng hợp' AND DOITUONG = N'Khách hàng' AND BTTH.TRANGTHAI = '1'\r\n" + 
					"																	AND BTTH.NGAYBUTTOAN < '"+denNgay+"'\r\n" + 
					"																	AND PS.TAIKHOAN_FK IN \r\n" + 
					"																	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK IN ("+npp_fk+"))\r\n" + 
					"								AND (ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0  OR ROUND(ISNULL(PS.CO, 0), 0) > 0)\r\n" + 
					"								--AND BTTH.PK_sEQ = 100124\r\n" + 
					"					) BTTH_CT\r\n" + 
					"					\r\n" + 
					"						UNION  ALL\r\n" + 
					"				--BU TRU CONG NO KHACHHANGNHANNO\r\n" + 
					"					SELECT KH.KH_PK_SEQ AS KH_PK_SEQ,\r\n" + 
					"					BT.NGAYBUTRU NGAYCHUNGTU,\r\n" + 
					"					PS.NGAYGHINHAN NGAYGHINHAN,\r\n" + 
					"					BT.PK_SEQ,\r\n" + 
					"					ROUND(ISNULL(PS.NO,0), 0) AS SOTIENTANGNO,\r\n" + 
					"					DATHANHTOAN.DATHANHTOAN AS SOTIENGIAMNO,\r\n" + 
					"					THOIHANNO,\r\n" + 
					"					HANMUCNO,\r\n" + 
					"					(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(THOIHANNO,0),BT.NGAYBUTRU),126), '"+denNgay+"')) SONGAYNO\r\n" + 
					"					FROM ERP_BUTRUKHACHHANG BT\r\n" + 
					"					LEFT JOIN\r\n" + 
					"					(\r\n" + 
					"					SELECT HOADON_FK,\r\n" + 
					"							SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"					FROM\r\n" + 
					"						(\r\n" + 
					"							--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD\r\n" + 
					"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ\r\n" + 
					"							WHERE TT.TRANGTHAI NOT IN (2)\r\n" + 
					"							AND TTHD.LOAIHD = '4'\r\n" + 
					"							AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"						UNION ALL \r\n" + 
					"						--THU TI?N\r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM ERP_THUTIEN_HOADON TTHD\r\n" + 
					"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"							WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = '4'\r\n" + 
					"							AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"					) HOADONDATT\r\n" + 
					"					GROUP BY HOADON_FK\r\n" + 
					"				)DATHANHTOAN ON BT.PK_SEQ = DATHANHTOAN.HOADON_FK\r\n" + 
					"					INNER JOIN ERP_PHATSINHKETOAN PS ON CAST ( PS.SOCHUNGTU AS NUMERIC(18,0)) = BT.PK_SEQ \r\n" + 
					"													AND BT.TRANGTHAI = 1  AND PS.NO > 0  \r\n" + 
					"													AND PS.LOAICHUNGTU = N'Bù trừ công nợ' \r\n" + 
					"													AND PS.NGAYGHINHAN < '"+denNgay+"'\r\n" + 
					"													AND ps.DOITUONG = N'Khách hàng'\r\n" + 
					"					INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ AND TK.NPP_FK IN ("+npp_fk+") \r\n" + 
					"					INNER JOIN (\r\n" + 
					"						SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1\r\n" + querynkh_npp + 
					"								UNION ALL \r\n" + 
					"								SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS KH_PK_SEQ, KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO, 0 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" + 
					"								WHERE TRANGTHAI = 1"+ querynkh_kh +"')\r\n" + 
					"					) KH ON (KH.KH_PK_SEQ = 'KH'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(ISNPPNHANNO, 0) = 0) OR \r\n" + 
					"					(KH.KH_PK_SEQ = 'NPP'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(ISNPPNHANNO, 0) = 1)\r\n" + 
					"				WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0\r\n" + 
					"				--END BU TRU CONG NO\r\n" + 
					"			UNION ALL \r\n" + 
					"				--BEGIN KHÁCH HÀNG TR? TR??C \r\n" + 
					"					SELECT  KH.KH_PK_SEQ AS KH_PK_SEQ,\r\n" + 
					"							TT.NGAYGHISO AS NGAYCHUNGTU, \r\n" + 
					"							TT.NGAYGHISO AS NGAYGHINHAN, \r\n" + 
					"							TT.PK_SEQ, \r\n" + 
					"							0 AS SOTIENTANGNO,\r\n" + 
					"							ROUND((TT.THUDUOC - ISNULL(DATHANHTOAN.DATHANHTOAN, 0)), 0) AS SOTIENGIAMNO, \r\n" + 
					"							ISNULL(KH.THOIHANNO,0) THOIHANNO,\r\n" + 
					"							ISNULL(KH.HANMUCNO, 0) HANMUCNO,\r\n" + 
					"							0 SONGAYNO\r\n" + 
					"					FROM ERP_THUTIEN TT   \r\n" + 
					"					LEFT JOIN  \r\n" + 
					"					(  \r\n" + 
					"						SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"						FROM\r\n" + 
					"							(\r\n" + 
					"									--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"								SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"									FROM ERP_XOAKHTRATRUOC_HOADON TTHD\r\n" + 
					"									INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ\r\n" + 
					"									WHERE TT.TRANGTHAI NOT IN (2)\r\n" + 
					"									AND TTHD.LOAIHD = '3'\r\n" + 
					"									AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"									GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"								UNION ALL \r\n" + 
					"								--THU TI?N\r\n" + 
					"								SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"									FROM ERP_THUTIEN_HOADON TTHD\r\n" + 
					"									INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"									WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = '3'\r\n" + 
					"									AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"									GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"							--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"								SELECT BT_KH.HOADON_FK, SUM(ROUND(BT_KH.XOANO, 0)) AS SOTIENBUTRU\r\n" + 
					"									FROM ERP_BUTRUKHACHHANG BT\r\n" + 
					"									INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK\r\n" + 
					"									WHERE BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3\r\n" + 
					"									AND BT.KH_CHUYENNO IS NOT NULL AND BT.NGAYBUTRU < '"+denNgay+"'\r\n" + 
					"									GROUP BY BT_KH.HOADON_FK \r\n" + 
					"							) HOADONDATT\r\n" + 
					"							GROUP BY HOADON_FK\r\n" + 
					"					) DATHANHTOAN ON TT.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					INNER JOIN ERP_PHATSINHKETOAN PS ON PS.SOCHUNGTU = TT.PK_SEQ AND LOAICHUNGTU LIKE N'Thu tiền KH trả trước'\r\n" + 
					"																				AND PS.DOITUONG = N'Khách hàng'\r\n" + 
					"																				AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN ("+npp_fk+") AND SOHIEUTAIKHOAN LIKE '131%')\r\n" + 
					"					INNER JOIN (\r\n" + 
					"						SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1\r\n" + querynkh_npp + 
					"								UNION ALL \r\n" + 
					"								SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS KH_PK_SEQ, KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO, 0 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" + 
					"								WHERE TRANGTHAI = 1" + querynkh_kh +"'  )\n"+ 
					"					) KH ON (KH.KH_PK_SEQ = 'KH'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(PS.ISNPP, 0) = 0) OR \r\n" + 
					"					(KH.KH_PK_SEQ = 'NPP'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(PS.ISNPP, 0) = 1)\r\n" + 
					"					WHERE TT.NOIDUNGTT_FK = '100001' AND TT.TRANGTHAI = '1'\r\n" + 
					"								AND (TT.THUDUOC - ISNULL(DATHANHTOAN.DATHANHTOAN, 0)) != 0  \r\n" + 
					"								AND TT.NGAYGHISO <   '"+denNgay+"'\r\n" + 
					"				--END KHÁCH HÀNG TR? TR??C \r\n" + 
					"			UNION ALL\r\n" + 
					"			SELECT \r\n" + 
					"					NPP.KH_PK_SEQ,\r\n" + 
					"					HD.NGAYHOADON AS NGAYCHUNGTU,\r\n" + 
					"					PS.NGAYGHINHAN,\r\n" + 
					"					HD.PK_SEQ,\r\n" + 
					"					ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"					ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"					ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"					ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"					(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYHOADON),126),  '"+denNgay+"')) SONGAYNO\r\n" + 
					"				FROM  ERP_HOADONKHAC HD\r\n" + 
					"				INNER JOIN (\r\n" + 
					"					SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N'Khách hàng' AND LOAICHUNGTU = N'Hóa đơn khác'\r\n" + 
					"																AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN ("+npp_fk+") AND SOHIEUTAIKHOAN LIKE '131%')\r\n" + 
					"																AND ISNULL(ISNPP, 0) = 1\r\n" + 
					"					GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"				) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"				INNER JOIN ( \r\n" + 
					"							SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1 "+querynkh_npp +" ')\r\n" + 
					"				) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"				LEFT JOIN  \r\n" + 
					"				(  \r\n" + 
					"					SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \r\n" + 
					"				FROM \r\n" + 
					"					( \r\n" + 
					"							--X+ANM-A KHÁCH HÀNG TR? TR??C \r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"								AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					"							AND TTHD.LOAIHD = '2' \r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK \r\n" + 
					"						UNION ALL  \r\n" + 
					"						--THU TI?N \r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_THUTIEN_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = '5' \r\n" + 
					"												AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK \r\n" + 
					"							UNION ALL  \r\n" + 
					"					--B+ANk- TR? KHÁCH HÀNG \r\n" + 
					"						SELECT BT_KH.HOADON_FK, SUM(ROUND(BT_KH.XOANO, 0)) AS SOTIENBUTRU \r\n" + 
					"							FROM ERP_BUTRUKHACHHANG BT \r\n" + 
					"							INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \r\n" + 
					"							WHERE BT.TRANGTHAI = 1 AND BT.NGAYBUTRU <  '"+denNgay+"' AND BT_KH.LOAIHD = 5 \r\n" + 
					"							AND BT.KH_CHUYENNO IS NOT NULL \r\n" + 
					"							GROUP BY BT_KH.HOADON_FK  \r\n" + 
					"					) HOADONDATT \r\n" + 
					"					GROUP BY HOADON_FK \r\n" + 
					"				) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"			--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI \r\n" + 
					"			WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYHOADON <  '"+denNgay+"'\r\n" + 
					"			UNION ALL\r\n" + 
					"			SELECT \r\n" + 
					"					KH_PK_SEQ,\r\n" + 
					"					HD.NGAYHOADON AS NGAYCHUNGTU,\r\n" + 
					"					PS.NGAYGHINHAN,\r\n" + 
					"					HD.PK_SEQ,\r\n" + 
					"					ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"					ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"					ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"					ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"					(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYHOADON),126),  '"+denNgay+"')) SONGAYNO\r\n" + 
					"				FROM  ERP_HOADONPHELIEU HD\r\n" + 
					"				INNER JOIN (\r\n" + 
					"					SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N'Khách hàng' AND LOAICHUNGTU = N'Hóa đơn thanh lý TSCĐ'\r\n" + 
					"																AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN ("+npp_fk+") AND SOHIEUTAIKHOAN LIKE '131%')\r\n" + 
					"																AND ISNULL(ISNPP, 0) = 1\r\n" + 
					"					GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"				) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"				INNER JOIN ( \n"
					+ "				SELECT * FROM OPENQUERY ("+Utility.prefixLinkDMS+", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1 "+ querynkh_npp +"')\r\n" + 
					"				) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"				LEFT JOIN  \r\n" + 
					"				(  \r\n" + 
					"					SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \r\n" + 
					"				FROM \r\n" + 
					"					( \r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TT.TRANGTHAI IN (1) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' 		AND TTHD.LOAIHD = '1' \r\n" + 
					"												AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					"							GROUP BY HOADON_FK \r\n" + 
					"						UNION ALL          \r\n" + 
					"						SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_THUTIEN_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TTHD.LOAIHOADON= '1' AND TT.TRANGTHAI NOT IN (2)\r\n" + 
					"												AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					" 							GROUP BY HOADON_FK \r\n" + 
					"						UNION ALL \r\n" + 
					" 						SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \r\n" + 
					"							FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \r\n" + 
					" 							WHERE  BT.TRANGTHAI = 1 AND BT.NGAYBUTRU <  '"+denNgay+"' AND BT_KH.LOAIHD = 1   \r\n" + 
					" 							GROUP BY BT_KH.HOADON_FK \r\n" + 
					"					) HOADONDATT \r\n" + 
					"					GROUP BY HOADON_FK \r\n" + 
					"				) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"			--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI \r\n" + 
					"			WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYHOADON <  '"+denNgay+"'\r\n" + 
					"\r\n" + 
					""+
					"				) TONGHOPCHUNGTU\r\n" + 
					"				GROUP BY KH_PK_SEQ,  ISNULL(THOIHANNO, 0),  ISNULL(HANMUCNO, 0), PK_SEQ, SONGAYNO\r\n" + 
					"		)TEMP\r\n" + 
					"		WHERE (CONGNO <> 0)\r\n" + 
					"	) TEMP\r\n" + 
					"	GROUP BY KH_PK_SEQ, THOIHANNO, HANMUCNO\r\n" + 
					") NOCONLAI ON NOCONLAI.KH_PK_SEQ = DKPS.KH_PK_SEQ\r\n" + 
					"LEFT JOIN (\r\n" + 
					"	SELECT * FROM OPENQUERY(" + Utility.prefixLinkDMS + ", 'SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS PK_SEQ, KH.MAFAST AS MA, KH.TEN, KH.TRANGTHAI \r\n" + 
					"	FROM  " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" + 
					"    WHERE KH.TRANGTHAI = 1\n"+ querynkh_kh +
					"					UNION ALL\r\n" + 
					"	SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS PK_SEQ, NPP.MAFAST, NPP.TEN, NPP.TRANGTHAI FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP WHERE NPP.TRANGTHAI = 1"+querynkh_npp +"')\r\n" + 
					") KH ON KH.PK_SEQ = ISNULL(DKPS.KH_PK_SEQ, NOCONLAI.KH_PK_SEQ) \r\n" + 
					"WHERE KH.TRANGTHAI = 1\r\n" +  
					"ORDER BY KH.TEN";
		} else if (loai == 0) {
			query = "SELECT  KH.*, TEMP.KH_PK_SEQ, TEMP.THOIHANNO, TEMP.HANMUCNO, TEMP.PK_SEQ, SOHOADON, NGAYCHUNGTU, NGAYGHINHAN, SOTIENTANGNO, SOTIENGIAMNO,\r\n" + 
					"	(TRONGHAN) AS TRONGHAN, (NO60) AS NO60, (NO120) AS NO120, (NO180) AS NO180, (NO181) AS NO181, CONGNO, \r\n" + 
					"		(NO60) + (NO120) + (NO180) + (NO181) AS TONGNOQUAHAN,\r\n" + 
					"		CASE WHEN ((NO60) + (NO120) + (NO180) + (NO181)) - HANMUCNO > 0 \r\n" + 
					"			THEN ((NO60) + (NO120) + (NO180) + (NO181)) - HANMUCNO \r\n" + 
					"			ELSE 0\r\n" + 
					"		END AS NOVUOTHANMUC\r\n" + 
					"	FROM\r\n" + 
					"	(\r\n" + 
					"		SELECT TEMP.KH_PK_SEQ, TEMP.THOIHANNO, TEMP.HANMUCNO, PK_SEQ, SOHOADON, NGAYCHUNGTU, NGAYGHINHAN, SOTIENTANGNO, SOTIENGIAMNO,\r\n" + 
					"			CASE WHEN SONGAYNO = 0 OR CONGNO < 0 THEN CONGNO ELSE 0 END AS TRONGHAN, CONGNO,\r\n" + 
					"			CASE WHEN SONGAYNO BETWEEN 1 AND 60 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO60,\r\n" + 
					"			CASE WHEN SONGAYNO BETWEEN 61 AND 120 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO120,\r\n" + 
					"			CASE WHEN SONGAYNO BETWEEN 121 AND 180 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO180,\r\n" + 
					"			CASE WHEN SONGAYNO > 180 AND CONGNO > 0 THEN CONGNO ELSE 0\r\n" + 
					"			END AS NO181\r\n" + 
					"		FROM\r\n" + 
					"		(\r\n" + 
					"				SELECT KH_PK_SEQ, ISNULL(THOIHANNO, 0) THOIHANNO, ISNULL(HANMUCNO, 0) HANMUCNO, PK_SEQ, SOHOADON, NGAYCHUNGTU, NGAYGHINHAN, SONGAYNO, SOTIENTANGNO, SOTIENGIAMNO, (SOTIENTANGNO) - (SOTIENGIAMNO) AS CONGNO\r\n" + 
					"				FROM\r\n" + 
					"				(\r\n" + 
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN VOI KHr\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN KH\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPP.PK_SEQ IS NOT NULL THEN ''NPP'' +CONVERT(VARCHAR, KH.PK_SEQ)  ELSE ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ,\r\n" + 
					"							HD.SOHOADON, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(KH.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(KH.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(KH.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "HOADON HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 0 AND LOAIHD in (N''HDCN_OTC'',N''HDQ_OTC'') \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "KHACHHANG KH \r\n" + 
					"							WHERE TRANGTHAI = 1						\r\n" + 
					"						) KH ON KH.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK AND ISNULL(NPP.CONGNOCHUNG,0)=1\r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADONNPP_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "ERP_THUTIENNPP_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADONNPP_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BTHD.HOADON_FK, SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) - SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "BUTRUCONGNO BT\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "BUTRUCONGNO_HOADON BTHD ON BT.PK_SEQ = BTHD.BTCN_FK\r\n" + 
					"										WHERE BT.TRANGTHAI = 1 AND BT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY BTHD.HOADON_FK, KHACHHANG_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO HOADON \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" + 
					
					///////////////////////////// ERP_HOADONNPP (ETC)
					
					"				UNION ALL \r\n" +
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN VOI KHr\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN KH\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPP.PK_SEQ IS NOT NULL THEN ''NPP'' +CONVERT(VARCHAR, KH.PK_SEQ)  ELSE ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ,\r\n" + 
					"							HD.SOHOADON, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(KH.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(KH.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(KH.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "ERP_HOADONNPP HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 0 AND LOAIHD in (N''HDCN_ETC_MauHO'',N''HDQ_ETC'') \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "KHACHHANG KH \r\n" + 
					"							WHERE TRANGTHAI = 1	" + querynkh_kh +"					\r\n" + 
					"						) KH ON KH.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK AND ISNULL(NPP.CONGNOCHUNG,0)=1\r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADONNPP_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "ERP_THUTIENNPP_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADONNPP_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BTHD.HOADON_FK, SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) - SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "BUTRUCONGNO BT\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "BUTRUCONGNO_HOADON BTHD ON BT.PK_SEQ = BTHD.BTCN_FK\r\n" + 
					"										WHERE BT.TRANGTHAI = 1 AND BT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY BTHD.HOADON_FK, KHACHHANG_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO HOADON \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" + 
					"					UNION ALL \r\n" + 
					//////////////////////////////////// ERP_HOADONNPP (ETC)- CN/DT NHA PHAN PHOI
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN VOI KHr\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ HOADON DUOC LEFT JOIN KH\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPPQUAY.PK_SEQ IS NOT NULL THEN ''NPP'' +CONVERT(VARCHAR, NPPQUAY.PK_SEQ)  ELSE ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ,\r\n" + 
					"							HD.SOHOADON, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "ERP_HOADONNPP HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 1 AND LOAIHD in (N''HDCN_ETC_MauHO'',N''HDQ_ETC'') \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"							LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"							WHERE TRANGTHAI = 1								\r\n" + querynkh_npp +
					"						) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPPQUAY ON NPPQUAY.PK_SEQ = HD.NPP_FK AND ISNULL(NPPQUAY.CONGNOCHUNG,0)=1\r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADONNPP_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "ERP_THUTIENNPP_HOADON TTHD\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.NPP_FK, HOADONNPP_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BTHD.HOADON_FK, SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) - SUM(ROUND(ISNULL(BTHD.GHINO,0), 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  " + Utility.prefixReportDMS + "BUTRUCONGNO BT\r\n" + 
					"										INNER JOIN  " + Utility.prefixReportDMS + "BUTRUCONGNO_HOADON BTHD ON BT.PK_SEQ = BTHD.BTCN_FK\r\n" + 
					"										WHERE BT.TRANGTHAI = 1 AND BT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY BTHD.HOADON_FK, KHACHHANG_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO HOADON \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" + 
					
					////////////////////////////////////////////
					"				UNION ALL \r\n" +
					"				--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI\r\n" + 
					"					--BEGIN HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI\r\n" + 
					"					SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"						'SELECT \r\n" + 
					"							CASE WHEN NPPQUAY.PK_SEQ IS NOT NULL THEN ''NPP'' +CONVERT(VARCHAR, NPPQUAY.PK_SEQ)  ELSE ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) END AS KH_PK_SEQ,\r\n" + 
					"							HD.NGAYXUATHD AS NGAYCHUNGTU,\r\n" + 
					"							PS.NGAYGHINHAN,\r\n" + 
					"							HD.PK_SEQ,\r\n" + 
					"							HD.SOHOADON, \r\n" + 
					"							ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"							ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"							ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"							ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYXUATHD),126), ''"+denNgay+"'')) SONGAYNO\r\n" + 
					"						FROM  " + Utility.prefixReportDMS + "ERP_HOADON HD\r\n" + 
					"						INNER JOIN (\r\n" + 
					"							SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  " + Utility.prefixReportDMS + "ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N''Khách hàng'' AND LOAICHUNGTU = N''Hóa đơn tài chính''\r\n" + 
					"																		AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + Utility.prefixReportDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE ''131%'')\r\n" + 
					"																		AND ISNULL(ISNPP, 0) = 1 AND LOAIHD=N''HDHO'' \r\n" + 
					"							GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"						) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"						LEFT JOIN (\r\n" + 
					"						\r\n" + 
					"							SELECT NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO \r\n" + 
					"							FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"							LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"							WHERE TRANGTHAI = 1						\r\n" + querynkh_npp+
					"						) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"						LEFT JOIN NHAPHANPHOI NPPQUAY ON NPPQUAY.PK_SEQ = HD.NPP_FK AND ISNULL(NPPQUAY.CONGNOCHUNG,0)=1\r\n" + 
					"						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					
					//////////////////////// HÓA ĐƠN CỦA HO THÌ LÊN ERP THANH TOÁN
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ISNULL(TTHD.SOTIENXOA, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  XOANOKHACHHANG_HOADON TTHD\r\n" + 
					"										INNER JOIN  XOANOKHACHHANG TT ON TTHD.XNKH_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"										AND TTHD.LOAIHD = 0\r\n" + 
					"										AND TT.NGAYCHUNGTU < ''"+denNgay+"''\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM  ERP_THUTIEN_HOADON TTHD\r\n" + 
					"										INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = 0\r\n" + 
					"										AND TT.NGAYGHISO < ''"+denNgay+"''\r\n" + 
					"										GROUP BY  HOADON_FK\r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI \r\n" + 
					"					WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYXUATHD < ''"+denNgay+"''')\r\n" +
					"				UNION ALL\r\n" + 
					"				--BEGIN Bút toán tổng hợp TANG HOAC GIAM NO\r\n" + 
					"					SELECT KH_PK_SEQ AS KH_PK_SEQ,\r\n" + 
					"							BTTH_CT.NGAYBUTTOAN AS NGAYCHUNGTU,\r\n" + 
					"							BTTH_CT.NGAYGHINHAN,\r\n" + 
					"							BTTH_CT.PK_SEQ,\r\n" + 
					"							BTTH_CT.MACHUNGTU,\r\n" + 
					"							SOTIENTANGNO,\r\n" + 
					"							SOTIENGIAMNO,\r\n" + 
					"							THOIHANNO, \r\n" + 
					"							HANMUCNO,\r\n" + 
					"							(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(THOIHANNO,0),BTTH_CT.NGAYBUTTOAN),126), '"+denNgay+"')) SONGAYNO\r\n" + 
					"					FROM\r\n" + 
					"					(\r\n" + 
					"						SELECT  KHACHHANG.KH_PK_SEQ, BTTH.MACHUNGTU,\r\n" + 
					"								BTTH.PK_SEQ, BTTH.NGAYBUTTOAN, PS.NGAYGHINHAN, MADOITUONG, PS.ISNPP,\r\n" + 
					"								ROUND(ISNULL(PS.CO, 0), 0) AS SOTIENGIAMNO,\r\n" + 
					"								ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0)  AS SOTIENTANGNO,\r\n" + 
					"						THOIHANNO,\r\n" + 
					"						HANMUCNO\r\n" + 
					"						FROM ERP_BUTTOANTONGHOP BTTH \r\n" + 
					"						INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \r\n" + 
					"						LEFT JOIN ( \r\n" + 
					"							SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1\r\n" +  querynkh_npp + 
					"								UNION ALL \r\n" + 
					"								SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS KH_PK_SEQ, KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO, 0 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" + 
					"								WHERE TRANGTHAI = 1"+ querynkh_kh +"')\r\n" + 
					"						) KHACHHANG ON CONVERT(VARCHAR, KHACHHANG.PK_SEQ) = MADOITUONG AND ISNULL(PS.ISNPP, 0) = KHACHHANG.ISNPP						LEFT JOIN  \r\n" + 
					"						(  \r\n" + 
					"							SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM\r\n" + 
					"								(\r\n" + 
					"										--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM ERP_XOAKHTRATRUOC_HOADON TTHD\r\n" + 
					"										INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2)\r\n" + 
					"										AND TTHD.LOAIHD = 10\r\n" + 
					"										AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"									--THU TI?N\r\n" + 
					"									SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"										FROM ERP_THUTIEN_HOADON TTHD\r\n" + 
					"										INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"										WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = 10\r\n" + 
					"										AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"										GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"										UNION ALL \r\n" + 
					"								--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"									SELECT BT_KH.HOADON_FK, SUM(ROUND(BT_KH.XOANO, 0)) AS SOTIENBUTRU\r\n" + 
					"										FROM ERP_BUTRUKHACHHANG BT\r\n" + 
					"										INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK\r\n" + 
					"										WHERE BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 5\r\n" + 
					"										AND BT.KH_CHUYENNO IS NOT NULL AND BT.NGAYBUTRU < '"+denNgay+"'\r\n" + 
					"										GROUP BY BT_KH.HOADON_FK \r\n" + 
					"								) HOADONDATT\r\n" + 
					"								GROUP BY HOADON_FK\r\n" + 
					"						) DATHANHTOAN ON BTTH.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"						WHERE  LOAICHUNGTU = N'Bút toán tổng hợp' AND DOITUONG = N'Khách hàng' AND BTTH.TRANGTHAI = '1'\r\n" + 
					"																	AND BTTH.NGAYBUTTOAN < '"+denNgay+"'\r\n" + 
					"																	AND PS.TAIKHOAN_FK IN \r\n" + 
					"																	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK IN (1))\r\n" + 
					"								AND (ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0  OR ROUND(ISNULL(PS.CO, 0), 0) > 0)\r\n" + 
					"								--AND BTTH.PK_sEQ = 100124\r\n" + 
					"					) BTTH_CT\r\n" + 
					"					\r\n" + 
					"						UNION  ALL\r\n" + 
					"				--BU TRU CONG NO KHACHHANGNHANNO\r\n" + 
					"					SELECT KH.KH_PK_SEQ AS KH_PK_SEQ,\r\n" + 
					"					BT.NGAYBUTRU NGAYCHUNGTU,\r\n" + 
					"					PS.NGAYGHINHAN NGAYGHINHAN,\r\n" + 
					"					BT.PK_SEQ, \r\n" + 
					"					BT.SOCHUNGTU,\r\n" + 
					"					ROUND(ISNULL(PS.NO,0), 0) AS SOTIENTANGNO,\r\n" + 
					"					DATHANHTOAN.DATHANHTOAN AS SOTIENGIAMNO,\r\n" + 
					"					THOIHANNO,\r\n" + 
					"					HANMUCNO,\r\n" + 
					"					(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(THOIHANNO,0),BT.NGAYBUTRU),126), '"+denNgay+"')) SONGAYNO\r\n" + 
					"					FROM ERP_BUTRUKHACHHANG BT\r\n" + 
					"					LEFT JOIN\r\n" + 
					"					(\r\n" + 
					"					SELECT HOADON_FK,\r\n" + 
					"							SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"					FROM\r\n" + 
					"						(\r\n" + 
					"							--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD\r\n" + 
					"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ\r\n" + 
					"							WHERE TT.TRANGTHAI NOT IN (2)\r\n" + 
					"							AND TTHD.LOAIHD = '4'\r\n" + 
					"							AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"						UNION ALL \r\n" + 
					"						--THU TI?N\r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"							FROM ERP_THUTIEN_HOADON TTHD\r\n" + 
					"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"							WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = '4'\r\n" + 
					"							AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"					) HOADONDATT\r\n" + 
					"					GROUP BY HOADON_FK\r\n" + 
					"				)DATHANHTOAN ON BT.PK_SEQ = DATHANHTOAN.HOADON_FK\r\n" + 
					"					INNER JOIN ERP_PHATSINHKETOAN PS ON CAST ( PS.SOCHUNGTU AS NUMERIC(18,0)) = BT.PK_SEQ \r\n" + 
					"													AND BT.TRANGTHAI = 1  AND PS.NO > 0  \r\n" + 
					"													AND PS.LOAICHUNGTU = N'Bù trừ công nợ' \r\n" + 
					"													AND PS.NGAYGHINHAN < '"+denNgay+"'\r\n" + 
					"													AND ps.DOITUONG = N'Khách hàng'\r\n" + 
					"					INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ AND TK.NPP_FK IN (1) \r\n" + 
					"					INNER JOIN (\r\n" + 
					"						SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1\r\n" + querynkh_npp + 
					"								UNION ALL \r\n" + 
					"								SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS KH_PK_SEQ, KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO, 0 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" + 
					"								WHERE TRANGTHAI = 1"+ querynkh_kh +"')\r\n" + 
					"					) KH ON (KH.KH_PK_SEQ = 'KH'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(ISNPPNHANNO, 0) = 0) OR \r\n" + 
					"					(KH.KH_PK_SEQ = 'NPP'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(ISNPPNHANNO, 0) = 1)\r\n" + 
					"				WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0\r\n" + 
					"				--END BU TRU CONG NO\r\n" + 
					"			UNION ALL \r\n" + 
					"				--BEGIN KHÁCH HÀNG TR? TR??C \r\n" + 
					"					SELECT  KH.KH_PK_SEQ AS KH_PK_SEQ,\r\n" + 
					"							TT.NGAYGHISO AS NGAYCHUNGTU, \r\n" + 
					"							TT.NGAYGHISO AS NGAYGHINHAN, \r\n" + 
					"							TT.PK_SEQ, \r\n" + 
					"							TT.MACHUNGTU,\r\n" + 
					"							0 AS SOTIENTANGNO,\r\n" + 
					"							ROUND((TT.THUDUOC - ISNULL(DATHANHTOAN.DATHANHTOAN, 0)), 0) AS SOTIENGIAMNO, \r\n" + 
					"							ISNULL(KH.THOIHANNO,0) THOIHANNO,\r\n" + 
					"							ISNULL(KH.HANMUCNO, 0) HANMUCNO,\r\n" + 
					"							0 SONGAYNO\r\n" + 
					"					FROM ERP_THUTIEN TT   \r\n" + 
					"					LEFT JOIN  \r\n" + 
					"					(  \r\n" + 
					"						SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN\r\n" + 
					"						FROM\r\n" + 
					"							(\r\n" + 
					"									--X+ANM-A KHÁCH HÀNG TR? TR??C\r\n" + 
					"								SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"									FROM ERP_XOAKHTRATRUOC_HOADON TTHD\r\n" + 
					"									INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ\r\n" + 
					"									WHERE TT.TRANGTHAI NOT IN (2)\r\n" + 
					"									AND TTHD.LOAIHD = '3'\r\n" + 
					"									AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"									GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"								UNION ALL \r\n" + 
					"								--THU TI?N\r\n" + 
					"								SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN\r\n" + 
					"									FROM ERP_THUTIEN_HOADON TTHD\r\n" + 
					"									INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ\r\n" + 
					"									WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = '3'\r\n" + 
					"									AND TT.NGAYGHISO < '"+denNgay+"'\r\n" + 
					"									GROUP BY TTHD.KHACHHANG_FK, HOADON_FK\r\n" + 
					"									UNION ALL \r\n" + 
					"							--B+ANk- TR? KHÁCH HÀNG\r\n" + 
					"								SELECT BT_KH.HOADON_FK, SUM(ROUND(BT_KH.XOANO, 0)) AS SOTIENBUTRU\r\n" + 
					"									FROM ERP_BUTRUKHACHHANG BT\r\n" + 
					"									INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK\r\n" + 
					"									WHERE BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3\r\n" + 
					"									AND BT.KH_CHUYENNO IS NOT NULL AND BT.NGAYBUTRU < '"+denNgay+"'\r\n" + 
					"									GROUP BY BT_KH.HOADON_FK \r\n" + 
					"							) HOADONDATT\r\n" + 
					"							GROUP BY HOADON_FK\r\n" + 
					"					) DATHANHTOAN ON TT.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"					INNER JOIN ERP_PHATSINHKETOAN PS ON PS.SOCHUNGTU = TT.PK_SEQ AND LOAICHUNGTU LIKE N'Thu tiền KH trả trước'\r\n" + 
					"																				AND PS.DOITUONG = N'Khách hàng'\r\n" + 
					"																				AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE '131%')\r\n" + 
					"					INNER JOIN (\r\n" + 
					"						SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1\r\n" + querynkh_npp + 
					"								UNION ALL \r\n" + 
					"								SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS KH_PK_SEQ, KH.PK_SEQ, 0 AS THOIHANNO, 0 AS HANMUCNO, 0 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "KHACHHANG KH\r\n" + 
					"								WHERE TRANGTHAI = 1"+ querynkh_kh +"')\r\n" + 
					"					) KH ON (KH.KH_PK_SEQ = 'KH'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(PS.ISNPP, 0) = 0) OR \r\n" + 
					"					(KH.KH_PK_SEQ = 'NPP'+CONVERT(VARCHAR, PS.MADOITUONG) AND ISNULL(PS.ISNPP, 0) = 1)\r\n" + 
					"					WHERE TT.NOIDUNGTT_FK = '100001' AND TT.TRANGTHAI = '1'\r\n" + 
					"								AND (TT.THUDUOC - ISNULL(DATHANHTOAN.DATHANHTOAN, 0)) != 0  \r\n" + 
					"								AND TT.NGAYGHISO <   '"+denNgay+"'\r\n" + 
					"				--END KHÁCH HÀNG TR? TR??C \r\n" + 
					"			UNION ALL\r\n" + 
					"			SELECT \r\n" + 
					"					NPP.KH_PK_SEQ,\r\n" + 
					"					HD.NGAYHOADON AS NGAYCHUNGTU,\r\n" + 
					"					PS.NGAYGHINHAN,\r\n" + 
					"					HD.PK_SEQ,\r\n" + 
					"					HD.SOHOADON,\r\n" + 
					"					ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"					ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"					ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"					ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"					(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYHOADON),126),  '"+denNgay+"')) SONGAYNO\r\n" + 
					"				FROM  ERP_HOADONKHAC HD\r\n" + 
					"				INNER JOIN (\r\n" + 
					"					SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N'Khách hàng' AND LOAICHUNGTU = N'Hóa đơn khác'\r\n" + 
					"																AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE '131%')\r\n" + 
					"																AND ISNULL(ISNPP, 0) = 1\r\n" + 
					"					GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"				) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"				INNER JOIN ( \r\n" + 
					"							SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1 "+ querynkh_npp +"')\r\n" + 
					"				) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"				LEFT JOIN  \r\n" + 
					"				(  \r\n" + 
					"					SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \r\n" + 
					"				FROM \r\n" + 
					"					( \r\n" + 
					"							--X+ANM-A KHÁCH HÀNG TR? TR??C \r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TT.TRANGTHAI IN (1)\r\n" + 
					"								AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					"							AND TTHD.LOAIHD = '2' \r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK \r\n" + 
					"						UNION ALL  \r\n" + 
					"						--THU TI?N \r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(ROUND(TTHD.SOTIENTT, 0)) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_THUTIEN_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHOADON = '5' \r\n" + 
					"												AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					"							GROUP BY TTHD.KHACHHANG_FK, HOADON_FK \r\n" + 
					"							UNION ALL  \r\n" + 
					"					--B+ANk- TR? KHÁCH HÀNG \r\n" + 
					"						SELECT BT_KH.HOADON_FK, SUM(ROUND(BT_KH.XOANO, 0)) AS SOTIENBUTRU \r\n" + 
					"							FROM ERP_BUTRUKHACHHANG BT \r\n" + 
					"							INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \r\n" + 
					"							WHERE BT.TRANGTHAI = 1 AND BT.NGAYBUTRU <  '"+denNgay+"' AND BT_KH.LOAIHD = 5 \r\n" + 
					"							AND BT.KH_CHUYENNO IS NOT NULL \r\n" + 
					"							GROUP BY BT_KH.HOADON_FK  \r\n" + 
					"					) HOADONDATT \r\n" + 
					"					GROUP BY HOADON_FK \r\n" + 
					"				) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"			--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI \r\n" + 
					"			WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYHOADON <  '"+denNgay+"'\r\n" + 
					"			UNION ALL\r\n" + 
					"			SELECT \r\n" + 
					"					KH_PK_SEQ,\r\n" + 
					"					HD.NGAYHOADON AS NGAYCHUNGTU,\r\n" + 
					"					PS.NGAYGHINHAN,\r\n" + 
					"					HD.PK_SEQ,\r\n" + 
					"					HD.SOHOADON,\r\n" + 
					"					ROUND(ISNULL(PS.NO, 0), 0) SOTIENTANGNO,\r\n" + 
					"					ISNULL(DATHANHTOAN.DATHANHTOAN, 0) SOTIENGIAMNO,\r\n" + 
					"					ISNULL(NPP.THOIHANNO, 0) AS THOIHANNO, \r\n" + 
					"					ISNULL(NPP.HANMUCNO, 0) AS HANMUCNO,\r\n" + 
					"					(DATEDIFF (DAY, convert(char(10), DATEADD(DAY,ISNULL(NPP.THOIHANNO,0),HD.NGAYHOADON),126),  '"+denNgay+"')) SONGAYNO\r\n" + 
					"				FROM  ERP_HOADONPHELIEU HD\r\n" + 
					"				INNER JOIN (\r\n" + 
					"					SELECT SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0) AS ISNPP, SUM(NO) AS NO, SUM(CO) AS CO FROM  ERP_PHATSINHKETOAN PS WHERE PS.DOITUONG = N'Khách hàng' AND LOAICHUNGTU = N'Hóa đơn thanh lý TSCĐ'\r\n" + 
					"																AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN (1) AND SOHIEUTAIKHOAN LIKE '131%')\r\n" + 
					"																AND ISNULL(ISNPP, 0) = 1\r\n" + 
					"					GROUP BY SOCHUNGTU, NGAYGHINHAN, MADOITUONG, ISNULL(PS.ISNPP, 0)\r\n" + 
					"				) PS ON PS.SOCHUNGTU = HD.PK_SEQ \r\n" + 
					"				INNER JOIN ( \r\n" + 
					"				SELECT * FROM OPENQUERY (" + Utility.prefixLinkDMS + ", \r\n" + 
					"								'SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS KH_PK_SEQ, NPP.PK_SEQ, ISNULL(CN.SONGAYNO, 0) AS THOIHANNO, ISNULL(CN.HANMUCNO, 0) AS HANMUCNO , 1 AS ISNPP\r\n" + 
					"								FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP \r\n" + 
					"								LEFT JOIN " + Utility.prefixReportDMS + "CONGNO_NPP CN ON CN.NPP_FK = NPP.PK_SEQ\r\n" + 
					"								WHERE TRANGTHAI = 1" + querynkh_npp + "')\r\n" + 
					"				) NPP ON NPP.PK_SEQ = PS.MADOITUONG\r\n" + 
					"				LEFT JOIN  \r\n" + 
					"				(  \r\n" + 
					"					SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \r\n" + 
					"				FROM \r\n" + 
					"					( \r\n" + 
					"						SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TT.TRANGTHAI IN (1) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' 		AND TTHD.LOAIHD = '1' \r\n" + 
					"												AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					"							GROUP BY HOADON_FK \r\n" + 
					"						UNION ALL          \r\n" + 
					"						SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \r\n" + 
					"							FROM ERP_THUTIEN_HOADON TTHD \r\n" + 
					"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \r\n" + 
					"							WHERE TTHD.LOAIHOADON= '1' AND TT.TRANGTHAI NOT IN (2)\r\n" + 
					"												AND TT.NGAYCHUNGTU <  '"+denNgay+"'\r\n" + 
					" 							GROUP BY HOADON_FK \r\n" + 
					"						UNION ALL \r\n" + 
					" 						SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \r\n" + 
					"							FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \r\n" + 
					" 							WHERE  BT.TRANGTHAI = 1 AND BT.NGAYBUTRU <  '"+denNgay+"' AND BT_KH.LOAIHD = 1   \r\n" + 
					" 							GROUP BY BT_KH.HOADON_FK \r\n" + 
					"					) HOADONDATT \r\n" + 
					"					GROUP BY HOADON_FK \r\n" + 
					"				) DATHANHTOAN ON HD.PK_SEQ = DATHANHTOAN.HOADON_FK \r\n" + 
					"			--END HOA DON TAI CHINH DMS DUOC HACH TOAN VAO HO/ ERP_HOADON DUOC LEFT JOIN VOI NHA PHAN PHOI \r\n" + 
					"			WHERE ROUND(ISNULL(PS.NO, 0), 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, 0) > 0 AND HD.NGAYHOADON <  '"+denNgay+"'\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"				) TONGHOPCHUNGTU\r\n" + 
					"		)TEMP\r\n" + 
					"		WHERE (CONGNO <> 0)\r\n" + 
					"	) TEMP\r\n" + 
					"LEFT JOIN (\r\n" + 
					"	SELECT * FROM OPENQUERY(" + Utility.prefixLinkDMS + ", 'SELECT ''KH''+CONVERT(VARCHAR, KH.PK_SEQ) AS PK_SEQ, KH.MAFAST AS MA, KH.TEN, KH.TRANGTHAI \r\n" + 
					"	FROM  " + Utility.prefixReportDMS + "KHACHHANG KH \n" + 
					"    WHERE KH.TRANGTHAI = 1 \n" + querynkh_kh + 
					"					UNION ALL\r\n" + 
					"	SELECT ''NPP''+CONVERT(VARCHAR, NPP.PK_SEQ) AS PK_SEQ, NPP.MAFAST, NPP.TEN, NPP.TRANGTHAI FROM " + Utility.prefixReportDMS + "NHAPHANPHOI NPP WHERE NPP.TRANGTHAI = 1  " +querynkh_npp +"')\r\n" + 
					") KH ON KH.PK_SEQ = TEMP.KH_PK_SEQ\r\n" + 
					"WHERE KH.TRANGTHAI = 1\r\n" +  
					"ORDER BY KH.TEN, NGAYGHINHAN \r\n" + 
					"";
		}
		System.out.println("Query lay tuoi no: \n"+query);
		ResultSet rs = db.get(query);
		return rs;
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		Style style;	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		double tongNoCK = 0, tongCoCK = 0, 
				tongTrongHan = 0, tongQuaHan = 0, tong60 = 0, tong120 = 0, tong180 = 0, tong181 = 0, tongVuotMuc = 0; 
		String denNgay = obj.getdenngay();
		ResultSet rs = null;
		//System.out.println("khach Hang : "+obj.GetKhId());
		//String [] param = new String [2];
		//param[0]=obj.getdenngay();
		//param[1]=obj.getErpCongtyId();
		
		//ResultSet rs = db.getRsByPro("TuoiNo_KH", param);
		rs = getSearchRs(obj.getdenngay(), "1", db, 1,obj.getNhomKhId());
		
		
		
//		String denNgay = obj.getdenngay();
//		double tongNoDK = 0, tongCoDK = 0, tongNoPS = 0, tongCoPS = 0;
				
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				//Bat dau sheet BC tuoi no KH
				worksheet = worksheets.getSheet(0);
				cells = worksheet.getCells();	
				
				for(int i = 0;i < 7; ++i)
				{
			    	cells.setColumnWidth(i, 15.0f);
			    	if(i == 4)
			    		cells.setColumnWidth(i, 30.0f);
			    }	
				
				int index2 = 13;
				int i = 1;
//				String formula = "";
				//index dau tien khi do du lieu
//				int firstIndex2 = index2;
				if (rs != null) 
				{
						while (rs.next())
						{	
							//STT
							cell = cells.getCell("A" + String.valueOf(index2));	cell.setValue(i);	CreateBorderSetting(cell);	
							i++;
							//MAKH
							cell = cells.getCell("B" + String.valueOf(index2));	cell.setValue(rs.getString("MA"));	CreateBorderSetting(cell);
							cell = cells.getCell("C" + String.valueOf(index2));	cell.setValue(rs.getString("TEN"));	CreateBorderSetting(cell);
							cell = cells.getCell("D" + String.valueOf(index2));	cell.setValue(rs.getInt("THOIHANNO"));	CreateBorderSetting(cell);
							cell = cells.getCell("E" + String.valueOf(index2));	cell.setValue(rs.getDouble("HANMUCNO"));	CreateBorderSetting(cell);
							
							/*cell = cells.getCell("F" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							style.setHAlignment(TextAlignmentType.LEFT);	
							cell.setValue(rs.getDouble("DUNODAUKY"));	
							CreateBorderSetting(cell);
							tongNoDK += rs.getDouble("DUNODAUKY");
							
							cell = cells.getCell("G" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUCODAUKY"));	CreateBorderSetting(cell);
							tongCoDK += rs.getDouble("DUCODAUKY");
							

							cell = cells.getCell("H" + String.valueOf(index2));
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("PHATSINHNO"));	
							CreateBorderSetting(cell);
							tongNoPS += rs.getDouble("PHATSINHNO");

							cell = cells.getCell("I" + String.valueOf(index2));
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("PHATSINHCO"));
							CreateBorderSetting(cell);
							tongCoPS += rs.getDouble("PHATSINHCO");*/
													
						
							cell = cells.getCell("F" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(rs.getDouble("DUNOCUOIKY"));	
							CreateBorderSetting(cell);
							tongNoCK += rs.getDouble("DUNOCUOIKY");
							
							cell = cells.getCell("G" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("DUCOCUOIKY"));	
							CreateBorderSetting(cell);
							tongCoCK += rs.getDouble("DUCOCUOIKY");
							
							cell = cells.getCell("H" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("TRONGHAN"));	
							CreateBorderSetting(cell);
							tongTrongHan += rs.getDouble("TRONGHAN");

							Double noQuaHan = 0.0 + rs.getDouble("NO60") + rs.getDouble("NO120") + rs.getDouble("NO180") + rs.getDouble("NO181");
							cell = cells.getCell("I" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(noQuaHan);	
							CreateBorderSetting(cell);
							tongQuaHan += noQuaHan;
							

							cell = cells.getCell("J" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO60"));	
							CreateBorderSetting(cell);
							tong60 += rs.getDouble("NO60");
						

							cell = cells.getCell("K" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO120"));	
							CreateBorderSetting(cell);
							tong120 += rs.getDouble("NO120");
							

							cell = cells.getCell("L" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO180"));	
							CreateBorderSetting(cell);
							tong180 += rs.getDouble("NO180");
			

							cell = cells.getCell("M" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO181"));	
							CreateBorderSetting(cell);
							tong181 += rs.getDouble("NO181");
							
							cell = cells.getCell("N" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NOVUOTHANMUC"));	
							CreateBorderSetting(cell);
							tongVuotMuc += rs.getDouble("NOVUOTHANMUC");
							
							index2++;
						}
						
						/*//TONG DUNODAUKY
						cell = cells.getCell("F12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongNoDK);
						
						//TONG DUCODAUKY
						cell = cells.getCell("G12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongCoDK);

						//TONG PHATSINHNO
						cell = cells.getCell("H12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongNoPS);
						
						//TONG PHATSINHCO
						cell = cells.getCell("I12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongCoPS);*/
						
						//TONG DUNOCUOIKY
						cell = cells.getCell("F12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongNoCK);

						//TONG DUCOCUOIKY
						cell = cells.getCell("G12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongCoCK);

						//TONG TRONGHAN
						cell = cells.getCell("H12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongTrongHan);

						//TONG NOQUAHAN
						cell = cells.getCell("I12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongQuaHan);

						//TONG NO30
						cell = cells.getCell("J12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong60);

						//TONG NO60
						cell = cells.getCell("K12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong120);

						//TONG NO90
						cell = cells.getCell("L12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong180);

						//TONG NO91
						cell = cells.getCell("M12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong181);

						//TONG NOVUOITHANMUC
						cell = cells.getCell("N12");
						style = cell.getStyle();
						cell.setStyle(style);	
						cell.setValue(tongVuotMuc);
						
						index2=index2+3;
						//this.setStyleColorGray(cells, cell, "1", 3);
						
						cell = cells.getCell("F" + String.valueOf(index2));
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
						
						cell = cells.getCell("J" + String.valueOf(index2));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
						index2=index2+5;
						cell = cells.getCell("F" + String.valueOf(index2));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
						
						cell = cells.getCell("J" + String.valueOf(index2));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
						
						cells.setColumnWidth(0, 5.0f);
						cells.setColumnWidth(1, 15.0f);
						cells.setColumnWidth(2, 40.0f);
						cells.setColumnWidth(3, 15.0f);
						cells.setColumnWidth(4, 10.0f);
						cells.setColumnWidth(5, 25.0f);
						cells.setColumnWidth(6, 25.0f);
						cells.setColumnWidth(7, 20.0f);
						cells.setColumnWidth(8, 20.0f);
						cells.setColumnWidth(9, 20.0f);
						cells.setColumnWidth(10, 20.0f);
						cells.setColumnWidth(11, 20.0f);
						cells.setColumnWidth(12, 20.0f);
						cells.setColumnWidth(13, 20.0f);
						cells.setColumnWidth(14, 20.0f);
						cells.setColumnWidth(15, 20.0f);
						cells.setColumnWidth(16, 20.0f);
						cells.setColumnWidth(17, 20.0f);			
				}
				//rs.close();
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

		rs = getSearchRs(obj.getdenngay(), "1", db, 0,obj.getNhomKhId());
		double soTienTangNo = 0, soTienGiamNo = 0, tongNoQuaHan = 0;
		tong60 = 0; tong120 = 0; tong180 = 0; tong181 = 0;
		tongTrongHan = 0;
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				//Bat dau sheet Kiem tra tuoi no KH
				worksheet = worksheets.getSheet(1);
				worksheet.setName("Kiem tra tuoi no KH");
				cells = worksheet.getCells();	
				
				/*for(int i = 0;i < 7; ++i)
				{
			    	cells.setColumnWidth(i, 15.0f);
			    	if(i == 4)
			    		cells.setColumnWidth(i, 30.0f);
			    }*/	
				
				int index2 = 8;
				int i = 1;
//				String formula = "";
				//index dau tien khi do du lieu
//				int firstIndex2 = index2;
				if (rs != null) 
				{
						while (rs.next())
						{	
							//STT
							//cell = cells.getCell("A" + String.valueOf(index2));	cell.setValue(i);	CreateBorderSetting(cell);	
							i++;
							//MAKH
							cell = cells.getCell("A" + String.valueOf(index2));	cell.setValue(rs.getString("MA"));	CreateBorderSetting(cell);
							cell = cells.getCell("B" + String.valueOf(index2));	cell.setValue(rs.getString("TEN"));	CreateBorderSetting(cell);
							cell = cells.getCell("C" + String.valueOf(index2));	cell.setValue(rs.getString("NGAYCHUNGTU"));	CreateBorderSetting(cell);
							cell = cells.getCell("D" + String.valueOf(index2));	cell.setValue(rs.getString("NGAYGHINHAN"));	CreateBorderSetting(cell);
							cell = cells.getCell("E" + String.valueOf(index2));	cell.setValue(rs.getString("SOHOADON"));	CreateBorderSetting(cell);
							cell = cells.getCell("F" + String.valueOf(index2));	cell.setValue(rs.getInt("THOIHANNO"));	CreateBorderSetting(cell);
							//cell = cells.getCell("E" + String.valueOf(index2));	cell.setValue(rs.getDouble("HANMUCNO"));	CreateBorderSetting(cell);
							
							cell = cells.getCell("G" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SOTIENTANGNO"));	
							CreateBorderSetting(cell);
							soTienTangNo += rs.getDouble("SOTIENTANGNO");
							
							cell = cells.getCell("H" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("SOTIENGIAMNO"));	
							CreateBorderSetting(cell);
							soTienGiamNo += rs.getDouble("SOTIENGIAMNO");


							cell = cells.getCell("I" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(rs.getDouble("CONGNO"));	
							CreateBorderSetting(cell);
							
							
							Double trongHan = rs.getDouble("TRONGHAN");
							cell = cells.getCell("J" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(trongHan);	
							CreateBorderSetting(cell);
							tongTrongHan += trongHan;
							

							cell = cells.getCell("K" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);
							cell.setValue(rs.getDouble("TONGNOQUAHAN"));	
							CreateBorderSetting(cell);
							tongNoQuaHan += rs.getDouble("TONGNOQUAHAN");
							

							cell = cells.getCell("L" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO60"));	
							CreateBorderSetting(cell);
							tong60 += rs.getDouble("NO60");
						

							cell = cells.getCell("M" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO120"));	
							CreateBorderSetting(cell);
							tong120 += rs.getDouble("NO120");
							

							cell = cells.getCell("N" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO180"));	
							CreateBorderSetting(cell);
							tong180 += rs.getDouble("NO180");
			

							cell = cells.getCell("O" + String.valueOf(index2));	
							style = cell.getStyle();
							style.setNumber(3);	
							cell.setStyle(style);	
							cell.setValue(rs.getDouble("NO181"));	
							CreateBorderSetting(cell);
							tong181 += rs.getDouble("NO181");
							
							index2++;
						}
						
						/*//TONG DUNODAUKY
						cell = cells.getCell("F12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongNoDK);
						
						//TONG DUCODAUKY
						cell = cells.getCell("G12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongCoDK);

						//TONG PHATSINHNO
						cell = cells.getCell("H12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongNoPS);
						
						//TONG PHATSINHCO
						cell = cells.getCell("I12");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongCoPS);*/

						//TONG TANG NO
						cell = cells.getCell("G7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(soTienTangNo);

						//TONG GIAM NO
						cell = cells.getCell("H7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(soTienGiamNo);
						
						//TONG TRONGHAN
						cell = cells.getCell("J7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongTrongHan);

						//TONG NOQUAHAN
						cell = cells.getCell("K7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tongNoQuaHan);

						//TONG NO60
						cell = cells.getCell("L7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong60);

						//TONG NO60
						cell = cells.getCell("M7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong120);

						//TONG NO90
						cell = cells.getCell("N7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong180);

						//TONG NO91
						cell = cells.getCell("O7");
						style = cell.getStyle();
						style.setNumber(3);	
						cell.setStyle(style);	
						cell.setValue(tong181);
						
						index2=index2+3;
						//this.setStyleColorGray(cells, cell, "1", 3);
						
						cell = cells.getCell("F" + String.valueOf(index2));
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
						
						cell = cells.getCell("J" + String.valueOf(index2));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
						index2=index2+5;
						cell = cells.getCell("F" + String.valueOf(index2));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
						
						cell = cells.getCell("J" + String.valueOf(index2));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
						
						cells.setColumnWidth(0, 15.0f);
						cells.setColumnWidth(1, 45.0f);
						cells.setColumnWidth(2, 15.0f);
						cells.setColumnWidth(3, 15.0f);
						cells.setColumnWidth(4, 20.0f);
						cells.setColumnWidth(5, 20.0f);
						cells.setColumnWidth(6, 20.0f);
						cells.setColumnWidth(7, 20.0f);
						cells.setColumnWidth(8, 20.0f);
						cells.setColumnWidth(9, 20.0f);
						cells.setColumnWidth(10, 20.0f);
						cells.setColumnWidth(11, 20.0f);
						cells.setColumnWidth(12, 20.0f);
						cells.setColumnWidth(13, 20.0f);
						cells.setColumnWidth(14, 20.0f);
						cells.setColumnWidth(15, 20.0f);
						cells.setColumnWidth(16, 20.0f);
						cells.setColumnWidth(17, 20.0f);			
				}
				rs.close();
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
		
		/*int index = 11;
		double totalNo15=0;
		double totalNo30=0;
		double totalNo45=0;
		double totalNo60=0;
		double totalNo90=0;
		double totalNoOther=0;
		double tong=0;
		double totaltong=0;
		int THOIHANNO=0;
		double totalvuothanmuc=0;
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{		
					tong=0;
					totalNo15= totalNo15 + rs.getDouble("No15");
					totalNo30= totalNo30 + rs.getDouble("No30");
					totalNo45= totalNo45 + rs.getDouble("No45");
					totalNo60= totalNo60 + rs.getDouble("No60");
					totalNo90= totalNo90 + rs.getDouble("No90");
					totalNoOther= totalNoOther + rs.getDouble("OTHER");
					try{
						THOIHANNO=rs.getInt("THOIHANNO");
						
					}catch(Exception er){
						
					}
					
					if(THOIHANNO <= 0){

						tong = rs.getDouble("NO15")+rs.getDouble("NO30")+rs.getDouble("NO45")+rs.getDouble("NO60")+rs.getDouble("NO90") + rs.getDouble("OTHER");

						if(tong > 0){
							cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-10);	
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("ma"));
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("ten"));	

							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(THOIHANNO);	
							this.setStyleColorNormar(cells, cell);

							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("NO15"));

							if(rs.getDouble("NO15") >0 ){
								this.setStyleColorRed(cells, cell);
								
							}else{
								this.setStyleColorNormar(cells, cell);
							}
					
							cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("NO30"));	
							if(rs.getDouble("NO30")>0 ){
								this.setStyleColorRed(cells, cell);
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("NO45"));
							if(rs.getDouble("NO45") > 0){
								this.setStyleColorRed(cells, cell);		
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("NO60"));
							if(rs.getDouble("NO60") > 0){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("NO90"));
							if(rs.getDouble("NO90") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}

							cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("OTHER"));
							if(rs.getDouble("OTHER") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							totaltong = totaltong + tong;
						
							cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(tong);
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("HANMUCNO"));
							this.setStyleColorNormar(cells, cell);
						
							if(rs.getDouble("HANMUCNO") >0 && (tong-rs.getDouble("HANMUCNO")) >0 ){
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tong-rs.getDouble("HANMUCNO"));
								totalvuothanmuc=totalvuothanmuc+ (tong-rs.getDouble("HANMUCNO"));
								this.setStyleColorRed(cells, cell);
							}else{
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(0);
								this.setStyleColorNormar(cells, cell);
							}
							index++;
						}
					}
					
					if(THOIHANNO <= 15 & THOIHANNO > 0){
						tong= rs.getDouble("NO30")+rs.getDouble("NO45")+rs.getDouble("NO60")+rs.getDouble("NO90") + rs.getDouble("OTHER");
						
						if(tong > 0){

							cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-10);	
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("B" + String.valueOf(index));	"E	cell.setValue(rs.getString("ma"));
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("ten"));	

							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(THOIHANNO);	
							this.setStyleColorNormar(cells, cell);

							
							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("NO15"));

							this.setStyleColorNormar(cells, cell);
					
							cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("NO30"));	
							if(rs.getDouble("NO30")>0 ){
								this.setStyleColorRed(cells, cell);
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("NO45"));	
							if(rs.getDouble("NO45") > 0){
								this.setStyleColorRed(cells, cell);		
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("NO60"));
							if(rs.getDouble("NO60") > 0){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("NO90"));
							if(rs.getDouble("NO90") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}

							cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("OTHER"));
							if(rs.getDouble("OTHER") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							totaltong = totaltong + tong;
						
							cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(tong);
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("HANMUCNO"));
							this.setStyleColorNormar(cells, cell);
						
							if(rs.getDouble("HANMUCNO") >0 && (tong-rs.getDouble("HANMUCNO")) >0 ){
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tong-rs.getDouble("HANMUCNO"));
								totalvuothanmuc=totalvuothanmuc+ (tong-rs.getDouble("HANMUCNO"));
								this.setStyleColorRed(cells, cell);
							}else{
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(0);
								this.setStyleColorNormar(cells, cell);
							}
							
							index++;
						}
					}

					if(THOIHANNO <= 30 & THOIHANNO > 15){
						tong= rs.getDouble("NO45")+rs.getDouble("NO60")+rs.getDouble("NO90") + rs.getDouble("OTHER");
						
						if(tong > 0){

							cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-10);	
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("ma"));
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("ten"));	

							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(THOIHANNO);	
							this.setStyleColorNormar(cells, cell);

							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("NO15"));

							this.setStyleColorNormar(cells, cell);
					
							cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("NO30"));	
							this.setStyleColorNormar(cells, cell);
						
							cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("NO45"));	//Nhan hang   	6
							if(rs.getDouble("NO45") > 0){
								this.setStyleColorRed(cells, cell);		
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("NO60"));
							if(rs.getDouble("NO60") > 0){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("NO90"));
							if(rs.getDouble("NO90") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("OTHER"));
							if(rs.getDouble("OTHER") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							totaltong = totaltong + tong;
						
							cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(tong);
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("HANMUCNO"));
							this.setStyleColorNormar(cells, cell);
						
							if(rs.getDouble("HANMUCNO") >0 && (tong-rs.getDouble("HANMUCNO")) >0 ){
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tong-rs.getDouble("HANMUCNO"));
								totalvuothanmuc=totalvuothanmuc+ (tong-rs.getDouble("HANMUCNO"));
								this.setStyleColorRed(cells, cell);
							}else{
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(0);
								this.setStyleColorNormar(cells, cell);
							}
							
							index++;
						}
					}
					
					if(THOIHANNO <= 45 & THOIHANNO > 30){
						tong=  rs.getDouble("NO60")+rs.getDouble("NO90") + rs.getDouble("OTHER");
						
						if(tong > 0){

							cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-10);	
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("ma"));
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("ten"));	

							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(THOIHANNO);	
							this.setStyleColorNormar(cells, cell);

							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("NO15"));

							this.setStyleColorNormar(cells, cell);
					
							cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("NO30"));	
							this.setStyleColorNormar(cells, cell);
						
							cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("NO45"));	//Nhan hang   	6
							this.setStyleColorNormar(cells, cell);
						
							cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("NO60"));
							if(rs.getDouble("NO60") > 0){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
						
							cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("NO90"));
							if(rs.getDouble("NO90") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
														
							cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("OTHER"));
							if(rs.getDouble("OTHER") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							totaltong = totaltong + tong;
						
							cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(tong);
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("HANMUCNO"));
							this.setStyleColorNormar(cells, cell);
						
							if(rs.getDouble("HANMUCNO") >0 && (tong-rs.getDouble("HANMUCNO")) >0 ){
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tong-rs.getDouble("HANMUCNO"));
								totalvuothanmuc=totalvuothanmuc+ (tong-rs.getDouble("HANMUCNO"));
								this.setStyleColorRed(cells, cell);
							}else{
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(0);
								this.setStyleColorNormar(cells, cell);
							}
							index++;
						}
					}
					
					if(THOIHANNO <= 60 & THOIHANNO > 45){
						tong=  rs.getDouble("NO90") + rs.getDouble("OTHER");
						
						if(tong > 0){

							cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-10);	
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("ma"));
							
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("ten"));	

							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(THOIHANNO);	
							this.setStyleColorNormar(cells, cell);

							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("NO15"));

							this.setStyleColorNormar(cells, cell);
					
							cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("NO30"));	
							this.setStyleColorNormar(cells, cell);
						
							cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("NO45"));
							this.setStyleColorNormar(cells, cell);
						
							cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("NO60"));
							this.setStyleColorNormar(cells, cell);
						
							cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("NO90"));

							if(rs.getDouble("NO90") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("OTHER"));
							if(rs.getDouble("OTHER") > 0 ){
								this.setStyleColorRed(cells, cell);	
							}else{
								this.setStyleColorNormar(cells, cell);
							}
							
							totaltong = totaltong + tong;
						
							cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(tong);
							this.setStyleColorNormar(cells, cell);
							cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("HANMUCNO"));
							this.setStyleColorNormar(cells, cell);
						
							if(rs.getDouble("HANMUCNO") >0 && (tong-rs.getDouble("HANMUCNO")) >0 ){
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(tong-rs.getDouble("HANMUCNO"));
								totalvuothanmuc=totalvuothanmuc+ (tong-rs.getDouble("HANMUCNO"));
								this.setStyleColorRed(cells, cell);
							}else{
								cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(0);
								this.setStyleColorNormar(cells, cell);
							}
							index++;
						}
						
					}
										
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
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totalNo15);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalNo30);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(totalNo45);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(totalNo60);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(totalNo90);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(totalNoOther);
				this.setStyleColorGray(cells, cell, "1");
				

				cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(totaltong);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(0);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(totalvuothanmuc);
				index=index+3;
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("B" + String.valueOf(index));
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
				
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
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 20.0f);
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 20.0f);
				cells.setColumnWidth(7, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(9, 20.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				
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
		return true;*/
	}	
}