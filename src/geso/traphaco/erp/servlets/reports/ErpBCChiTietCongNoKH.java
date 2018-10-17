package geso.traphaco.erp.servlets.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

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

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;

public class ErpBCChiTietCongNoKH extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCChiTietCongNoKH() {
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
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setErpCongtyId((String)session.getAttribute("congtyId"));
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		obj.setDoituongId(session.getAttribute("doituongId"));
		
		obj.initKhachHangRs();
		obj.initChiNhanhRs();
		
		//obj.InitErp();
		//session.setAttribute("kbhId", "");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietCongNoKH.jsp";
		
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
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		obj.setDoituongId(session.getAttribute("doituongId"));
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kbhId"))!=null?util.antiSQLInspection(request.getParameter("kbhId")):"");
				
		String khachhangId = util.antiSQLInspection(request.getParameter("khid"));
		if(khachhangId == null)
			khachhangId  = "";		
		obj.setErpKhachHangId(util.antiSQLInspection(request.getParameter("khid")));
				
		String nkhId = util.antiSQLInspection(request.getParameter("nkhId"));
		if(nkhId == null)
			nkhId = "";
		obj.setNhomKhId(nkhId);
		
		obj.initKhachHangRs();
		obj.initChiNhanhRs();
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietCongNoKH.jsp";
		
		System.out.println("Action la: " + action);
		
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietCongNoKH.xlsm");
			
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
	        	session.setAttribute("obj", obj);
	    		response.sendRedirect(nextJSP);
	    		e.printStackTrace();
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
			}
		}
		
		
		return;
	}
	
	public String getQuery(IStockintransit obj)
	{		
		String query = "";
			query = " SELECT PS.LOAICHUNGTU AS LOAI, ISNULL(PS.SOHOADON,'') SOHOADON, PS.SOCHUNGTU ID, isnull(PS.NO,0) TANGTRONGKY , ISNULL(PS.CO, 0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, \n"+
					" PS.MADOITUONG, PS.DOITUONG, PS.NGAYGHINHAN NGAY, PS.MADOITUONG \n"+
					" FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
					" INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
					" INNER JOIN NHAPHANPHOI NCC ON PS.MADOITUONG = CAST( NCC.PK_SEQ AS NVARCHAR(50)) \n"+
					" WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN (  '13111000', '13112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
					" AND DOITUONG = N'Khách hàng' \n"+
					" AND PS.NGAYGHINHAN >='"+obj.gettungay()+"' AND PS.NGAYGHINHAN <='"+obj.getdenngay()+"'  \n"+
					" AND PS.MADOITUONG = '" + obj.getErpKhachHangId() +"'" ;
			
			//Tich hop DMS
			/*query += " UNION ALL \n"+
			
					" SELECT PS.LOAICHUNGTU AS LOAI, ISNULL(PS.SOHOADON,'') SOHOADON, PS.SOCHUNGTU ID, isnull(PS.NO,0) TANGTRONGKY , ISNULL(PS.CO, 0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, \n"+
					" PS.MADOITUONG, PS.DOITUONG, PS.NGAYGHINHAN NGAY, PS.MADOITUONG \n"+
					" FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
					" INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "NHAPHANPHOI NCC ON PS.MADOITUONG = CAST( NCC.PK_SEQ AS NVARCHAR(50)) \n"+
					" WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN (  '13111000', '13112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
					" AND DOITUONG = N'Khách hàng' \n"+
					" AND PS.NGAYGHINHAN >='"+obj.gettungay()+"' AND PS.NGAYGHINHAN <='"+obj.getdenngay()+"'  \n"+
					" AND PS.MADOITUONG = '" + obj.getErpKhachHangId() +"'" ;*/
			
		System.out.println(query);
	    return query;   
	}
	
	public String getDauky(IStockintransit obj)
	{					
//		Utility util = new Utility();
		//CÂU ĐẦU KỲ GIỐNG BÁO CÁO TỔNG HỢP KHÁCH HÀNG - NẾU CÓ SỬA THÌ SỬA CẢ 2 BÁO CÁO
		
		String query =	"	SELECT KHID, ISNULL(SUM(TANGTRONGKY - GIAMTRONGKY), 0) AS DAUKY \n" +
						"	FROM ( \n" +		
						"			SELECT PS.LOAICHUNGTU AS LOAI, ISNULL(PS.SOHOADON,'') SOHOADON, PS.SOCHUNGTU, isnull(PS.NO,0) TANGTRONGKY , ISNULL(PS.CO, 0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
						"			PS.MADOITUONG KHID, PS.DOITUONG, PS.NGAYGHINHAN \n"+
						"			FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						"			INNER JOIN NHAPHANPHOI KH ON PS.MADOITUONG = CAST( KH.PK_SEQ AS NVARCHAR(50)) \n"+
						"			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '13111000', '13112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
						"			AND DOITUONG = N'Khách hàng' \n"+
						"   		AND PS.NGAYGHINHAN < '"+ obj.gettungay() +"' \n"+
						//Tinh dau ky Dms
						/*"          UNION ALL \n"+
						"			SELECT PS.LOAICHUNGTU AS LOAI, ISNULL(PS.SOHOADON,'') SOHOADON, PS.SOCHUNGTU, isnull(PS.NO,0) TANGTRONGKY , ISNULL(PS.CO, 0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
						"			PS.MADOITUONG KHID, PS.DOITUONG, PS.NGAYGHINHAN \n"+
						"			FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						"			INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "NHAPHANPHOI KH ON PS.MADOITUONG = CAST( KH.PK_SEQ AS NVARCHAR(50)) \n"+
						"			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '13111000', '13112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
						"			AND DOITUONG = N'Khách hàng' \n"+
						"   		AND PS.NGAYGHINHAN < '"+ obj.gettungay() +"' \n"+*/
						" 	)DAUKY \n"+
						"  WHERE 1 = 1 "+
						"  AND  DAUKY.KHID IN (" + obj.getErpKhachHangId() + ") "+
						" GROUP BY DAUKY.KHID \n";
		
		System.out.println("SQL DAUKY : "+ query);
	    return query;   
	}

	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoChiTietCongNoKH.xlsm");
						
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
		
		dbutils db = new dbutils();
		
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
		
		//LẤY TÊN VÀ MÃ KHÁCH HÀNG
		
		String query = " ";
		query = "	SELECT PK_SEQ, MA, TEN FROM (\r\n" + 
				"				SELECT 'KH'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM KHACHHANG \r\n" + 
				"				UNION\r\n" + 
				"				SELECT 'NPP'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM NHAPHANPHOI \r\n" + 
				"				UNION\r\n" + 
				"				SELECT 'NV'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM ERP_NHANVIEN \r\n" + 
				"			) TEMP\r\n" + 
				"			WHERE PK_SEQ = '"+obj.getErpKhachHangId()+"'";
		
		
		System.out.println(query);
		
		ResultSet khRs = db.get(query);
		
		String tenkh = "";
		
		try{
			if(khRs != null){
				khRs.next();
			
				tenkh = khRs.getString("TEN");
				
				khRs.close();
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
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế: " + masothue); 
	    

	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO CHI TIẾT CÔNG NỢ KHÁCH HÀNG");

	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay());
	    
	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());
	    	    
	    cell = cells.getCell("A9"); 
	    if(tenkh.length() > 0){
	    	ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã và tên Khách hàng: "  + tenkh); 
	    }
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
		Cell cell1 = cells.getCell("Y1");
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
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 8; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 0)
	    		cells.setColumnWidth(i, 5.0f);
	    	else if(i==1)
	    		cells.setColumnWidth(i, 25.0f);
	    	else if(i==2)
	    		cells.setColumnWidth(i, 25.0f);
	    	else if(i==3)
	    		cells.setColumnWidth(i, 25.0f);
	    	else if(i==7)
	    		cells.setColumnWidth(i, 25.0f);
	    }	
		
		double totaltangtrongky=0;
		double totalgiamtrongky=0;
		double totalnocodauky=0;

		// Lay so du dau ky
		String [] param = new String [5];
		param[0]=obj.gettungay();
		param[1]=obj.getdenngay();
		param[2]=obj.getErpKhachHangId();
		int thangKyTruoc = 0, namKyTruoc = 0, thangKyNay = 0, namKyNay = 0;
		namKyNay = Integer.parseInt(param[0].substring(0, 4));
		thangKyNay = Integer.parseInt(param[0].substring(5, 7));
		namKyTruoc = namKyNay;
		if(thangKyNay == 1) {
			thangKyTruoc = 12;
			namKyTruoc--;
		} else {
			thangKyTruoc = thangKyNay - 1;
		}
		
		String query = "SELECT * FROM (\r\n" + 
				"				SELECT PK_SEQ, MA, TEN FROM (\r\n" + 
				"					SELECT 'KH'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MAFAST MA, TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG WHERE TRANGTHAI = 1 AND NPP_FK IS NOT NULL \r\n" + 
				"					UNION ALL \r\n" + 
				"					SELECT 'NPP'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM NHAPHANPHOI WHERE TRANGTHAI = 1 \r\n" + 
				"					AND PK_SEQ != 1"
				+ "					UNION ALL \r\n" + 
				"					SELECT 'NV'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM ERP_NHANVIEN WHERE TRANGTHAI = 1 \r\n" + 
				"				) TEMP\r\n" + 
				"				WHERE PK_SEQ = '"+param[2]+"'\r\n" + 
				"		) DANHMUC\r\n" + 
				"		INNER JOIN \r\n" + 
				"		(\r\n" + 
				"			SELECT \r\n" + 
				"				CASE WHEN ISNULL(KS.isNPP, 0) = 0 THEN 'KH'+KS.MADOITUONG\r\n" + 
				"					WHEN ISNULL(KS.isNPP, 0) = 1 THEN 'NPP'+KS.MADOITUONG\r\n" + 
				"					WHEN ISNULL(KS.isNPP, 0) = 2 THEN 'NV'+KS.MADOITUONG \r\n" + 
				"				END AS MADOITUONG, \r\n" + 
				"				ISNULL(SUM(GIATRINOVND) - SUM(GIATRICOVND), 0) AS DAUKY\r\n" + 
				"			FROM ERP_TAIKHOAN_NOCO_KHOASO KS\r\n" + 
				"			WHERE THANG = "+thangKyTruoc+" AND NAM = "+namKyTruoc+" AND DOITUONG = N'KHÁCH HÀNG'\r\n" + 
				"			GROUP BY MADOITUONG, ISNPP\r\n" + 
				"		) DAUKY ON DAUKY.MADOITUONG = DANHMUC.PK_SEQ";
		System.out.println("DAUKY: "+query);
		ResultSet rs = db.get(query);
		if(rs != null){
			if(rs.next()){
				totalnocodauky = rs.getDouble("DAUKY");
			}
		}else{
			totalnocodauky = 0;
		}
		query = "		SELECT * FROM (\r\n" + 
				"			SELECT PK_SEQ, MA, TEN FROM (\r\n" + 
				"				SELECT 'KH'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ,  MAFAST MA, TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG WHERE TRANGTHAI = 1 AND NPP_FK IS NOT NULL \r\n" + 
				"				UNION ALL \r\n" + 
				"				SELECT 'NPP'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM NHAPHANPHOI WHERE TRANGTHAI = 1 \r\n" + 
				"				AND PK_SEQ != 1"
				+ "				UNION ALL \r\n" + 
				"				SELECT 'NV'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM ERP_NHANVIEN WHERE TRANGTHAI = 1 \r\n" + 
				"			) TEMP\r\n" + 
				"				WHERE PK_SEQ = '"+param[2]+"'\r\n" + 
				"		) DANHMUC\r\n" + 
				"		INNER JOIN (\r\n" + 
				"	--BEGIN CAC CHUNG TU KHONG PHAI HOA DON\r\n" + 
				"		SELECT \r\n" + 
				"		PS.LOAICHUNGTU AS LOAI, \r\n" + 
				"		CASE WHEN MACHUNGTU IS NOT NULL THEN MACHUNGTU ELSE CONVERT(VARCHAR,PS.SOCHUNGTU) END AS ID, \r\n" + 
				"		ISNULL(SOHOADON, '') SOHOADON, \r\n" + 
				"		isnull(PS.NO,0) TANGTRONGKY , ISNULL(PS.CO, 0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, \r\n" + 
				"		PS.DOITUONG, PS.NGAYGHINHAN NGAY, PS.LOAICHUNGTU, ISNULL(PS.isNPP, 0) AS ISNPP,\r\n" + 
				"		CASE WHEN ISNULL(PS.isNPP, 0) = 0 THEN 'KH'+PS.MADOITUONG\r\n" + 
				"			WHEN ISNULL(PS.isNPP, 0) = 1 THEN 'NPP'+PS.MADOITUONG\r\n" + 
				"			WHEN ISNULL(PS.isNPP, 0) = 2 THEN 'NV'+PS.MADOITUONG\r\n" + 
				"		END AS MADOITUONG\r\n" + 
				"		FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \r\n" + 
				"		INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \r\n" + 
				"		WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK IN (1) ) \r\n" + 
				"		AND DOITUONG = N'Khách hàng' AND PS.NGAYGHINHAN BETWEEN '"+param[0]+"' \r\n" + 
				"		AND '"+param[1]+"'\r\n" + 
				"		UNION ALL\r\n" + 
				"	--BEGIN CAC CHUNG TU KHONG PHAI HOA DON DMS\r\n" + 
				"		SELECT \r\n" + 
				"		PS.LOAICHUNGTU AS LOAI, \r\n" + 
				"		CASE WHEN MACHUNGTU IS NOT NULL THEN MACHUNGTU ELSE CONVERT(VARCHAR,PS.SOCHUNGTU) END AS ID, \r\n" + 
				"		ISNULL(SOHOADON, '') SOHOADON, \r\n" + 
				"		isnull(PS.NO,0) TANGTRONGKY , ISNULL(PS.CO, 0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, \r\n" + 
				"		PS.DOITUONG, PS.NGAYGHINHAN NGAY, PS.LOAICHUNGTU, ISNULL(PS.isNPP, 0) AS ISNPP,\r\n" + 
				"		CASE WHEN ISNULL(PS.isNPP, 0) = 0 THEN 'KH'+PS.MADOITUONG\r\n" + 
				"			WHEN ISNULL(PS.isNPP, 0) = 1 THEN 'NPP'+PS.MADOITUONG\r\n" + 
				"			WHEN ISNULL(PS.isNPP, 0) = 2 THEN 'NV'+PS.MADOITUONG\r\n" + 
				"		END AS MADOITUONG\r\n" + 
				"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \r\n" + 
				"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \r\n" + 
				"		WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '131%' AND NPP_FK IN (1) ) \r\n" + 
				"		AND DOITUONG = N'Khách hàng' AND PS.NGAYGHINHAN BETWEEN '"+param[0]+"' \r\n" + 
				"		AND '"+param[1]+"'\r\n" + 
				"	) PHATSINH	ON DANHMUC.PK_SEQ = PHATSINH.MADOITUONG";

		rs = db.get(query);
		System.out.println("\n PHATSINH: "+query);
		Cell cell = null;
		cell = cells.getCell("K11"); cell.setValue(totalnocodauky);
		this.setStyleColorGray(cells, cell, "1");

		int index = 12;
		
		if (rs != null) 
		{
			try 
			{
				cell = null;
				while (rs.next())
				{		
					
					totalgiamtrongky=totalgiamtrongky+rs.getDouble("GIAMTRONGKY");
					totaltangtrongky=totaltangtrongky +rs.getDouble("TANGTRONGKY");
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-11);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("LOAI"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("SOHIEUTAIKHOAN"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("SOHIEUTAIKHOAN_DU"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("ID"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));
					this.setStyleColorNormar(cells, cell);					
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("NGAY"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("KHOANMUC"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY"));
					this.setStyleColorNormar(cells, cell);					
					cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(0);
					this.setStyleColorNormar(cells, cell);	
					
					
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
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");				
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(totaltangtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(totalgiamtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(totalnocodauky+totaltangtrongky-totalgiamtrongky);	//Nhan hang   	6
				this.setStyleColorGray(cells, cell, "1");
								
				index=index+3;
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
				
				cell = cells.getCell("F" + String.valueOf(index));cell.setValue("Giám đốc");
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
				
				index=index+5;
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");

				rs.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			
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