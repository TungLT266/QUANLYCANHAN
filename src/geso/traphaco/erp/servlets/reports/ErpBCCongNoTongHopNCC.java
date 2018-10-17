package geso.traphaco.erp.servlets.reports;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.erp.beans.stockintransit.*;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
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

public class ErpBCCongNoTongHopNCC extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCCongNoTongHopNCC() {
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
		obj.setnppdangnhap(util.getIdNhapp(util.getUserId(querystring)));
		
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcTongHopCongNoNCC.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		 
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		System.out.print("Công ty ID " + ctyId);
		obj.setErpCongtyId(ctyId);
		obj.setnppdangnhap(util.getIdNhapp(userId));
		
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.InitErp();
		String[] loainccIds = request.getParameterValues("nccIds");
		
		String str2 = "";
		String str="";
		if(loainccIds != null)
		{
			for(int i = 0; i < loainccIds.length; i++)
			{
				str += loainccIds[i] + ",";
			}
			if(str.length() > 0)
				str2 = str.substring(0, str.length() - 1);
			
			obj.setLoaiNCCIds(str2);
		}
		
		String[] ChonIdNcc = request.getParameterValues("ChonIdNcc");
		String strncc="0";
		if(ChonIdNcc != null)
		{
			for(int i = 0; i < ChonIdNcc.length; i++)
			{
				strncc += ChonIdNcc[i] + ",";
			}
	 
			obj.setErpNCCId(strncc.substring(0, strncc.length() - 1));
		}
			
		System.out.println("Ncc iD: " + obj.getErpNCCId());
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcTongHopCongNoNCC.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTongHopCongNoNCC.xlsm");
			
			String query = setQuery(obj); 
			
	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query))
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
		dbutils db = new dbutils();
		String month = obj.gettungay().substring(5, 7);
		String year = obj.gettungay().substring(0, 4);	
		//Chọn tháng khóa sổ gần nhất
		String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
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
		
		if (thangKS != 0 && namKS != 0){
			if(thangKS >= thangdauky && namKS >= namdauky){
				
			}
			else {
				thangdauky = thangKS;
				namdauky = namKS;
			}
		}
		String thangDauKy = "0"+Integer.toString(thangdauky);
		thangDauKy.substring(thangDauKy.length() - 1, thangDauKy.length());
		
		String query=	" SELECT PK_SEQ, MA, TAIKHOAN_FK, TEN , ISNULL(DAUKY.DAUKY,0) AS DUNODAUKY,  ISNULL(PSTRONGKY.TANGTRONGKY,0) TANGTRONGKY , ISNULL(PSTRONGKY.GIAMTRONGKY,0) GIAMTRONGKY  \n" +
						" FROM ERP_NHACUNGCAP NCC  \n" +
						" LEFT JOIN ( \n" +
						"	SELECT NCCID, ISNULL(SUM(TANGTRONGKY - GIAMTRONGKY), 0) AS DAUKY \n" +
						"	FROM ( \n" +
						" 			SELECT '' AS LOAI,'' AS SOHOADON,'' AS SOCHUNGTU, ROUND(ISNULL(KS.GIATRICOVND,0),0) AS TANGTRONGKY,ROUND(ISNULL(KS.GIATRINOVND,0),0) AS GIAMTRONGKY,'' AS KHOANMUC,'' AS SOHIEUTAIKHOAN, \n " + 
						" 			KS.MADOITUONG AS NCCID,KS.DOITUONG,'' AS NGAYGHINHAN \n " + 
						" 			FROM ERP_TAIKHOAN_NOCO_KHOASO KS INNER JOIN ERP_TAIKHOANKT TK ON KS.TAIKHOANKT_FK = TK.PK_SEQ \n " + 
						" 			INNER JOIN ERP_NHACUNGCAP NCC ON KS.MADOITUONG = CAST(NCC.PK_SEQ AS NVARCHAR(50)) \n " + 
						" 			WHERE KS.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '33111000', '33112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") )  \n " + 
						" 			AND KS.THANG = "+thangdauky+"  AND KS.NAM = "+namdauky+" \n " + 
						" 			UNION ALL \n " + 
						"			SELECT PS.LOAICHUNGTU AS LOAI, ISNULL(PS.SOHOADON,'') SOHOADON, PS.SOCHUNGTU, ROUND(ISNULL(PS.CO,0),0) TANGTRONGKY , ROUND(ISNULL(PS.NO, 0),0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
						"			PS.MADOITUONG NCCID, PS.DOITUONG, PS.NGAYGHINHAN \n"+
						"			FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						"			INNER JOIN ERP_NHACUNGCAP NCC ON PS.MADOITUONG = CAST( NCC.PK_SEQ AS NVARCHAR(50)) \n"+
						"			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '33111000', '33112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
						//"			AND DOITUONG = N'Nhà cung cấp' \n"+
						"   		AND PS.NGAYGHINHAN >'"+namdauky+"-"+ thangDauKy +"-32' AND PS.NGAYGHINHAN < '"+ obj.gettungay() +"' \n"+
						"	)SUM GROUP BY SUM.NCCID \n" +
						")DAUKY ON DAUKY.NCCID = NCC.PK_SEQ \n" +
						"LEFT JOIN ( \n" +
						"	SELECT NCCID, ISNULL(SUM(TANGTRONGKY),0) TANGTRONGKY,  ISNULL(SUM(GIAMTRONGKY),0) GIAMTRONGKY \n" +
						"	FROM ( \n" +
						" 			SELECT PS.LOAICHUNGTU AS LOAI, ISNULL(PS.SOHOADON,'') SOHOADON, PS.SOCHUNGTU ID, ROUND(ISNULL(PS.CO,0),0) TANGTRONGKY , ROUND(ISNULL(PS.NO, 0),0) GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, \n"+
						" 			PS.MADOITUONG, PS.DOITUONG, PS.NGAYGHINHAN NGAY, PS.MADOITUONG NCCID \n"+
						" 			FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						" 			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" 			INNER JOIN ERP_NHACUNGCAP NCC ON PS.MADOITUONG = CAST( NCC.PK_SEQ AS NVARCHAR(50)) \n"+
						" 			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '33111000', '33112000' )  AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
						//" 			AND DOITUONG = N'Nhà cung cấp' \n"+
						" 			AND PS.NGAYGHINHAN >='"+obj.gettungay()+"' AND PS.NGAYGHINHAN <='"+obj.getdenngay()+"'  \n"+						
						"	)SUM  GROUP BY SUM.NCCID \n" +
						" ) PSTRONGKY ON PSTRONGKY.NCCID = NCC.PK_SEQ \n" +
						" WHERE (ISNULL(DAUKY.DAUKY,0) != 0 OR ISNULL(PSTRONGKY.TANGTRONGKY,0) >0 OR ISNULL(PSTRONGKY.GIAMTRONGKY,0) >0) \n" ;
			
		if( obj.getLoaiNCCIds().length() > 0){
					query+=" AND  LOAINCC IN (" + obj.getLoaiNCCIds() + ") \n";
		}
		if(obj.getErpNCCId().length() >0){
			query+=" AND  NCC.PK_SEQ IN (" + obj.getErpNCCId() + ") ";
		}
		System.out.println("Get Sql : "+ query);   
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\BaoCaoTongHopCongNoNCC.xlsm");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
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
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế: " + masothue); 
	    
	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO TỔNG HỢP CÔNG NỢ NHÀ CUNG CẤP");
	    
	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: ");
	    
	    cell = cells.getCell("B8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.gettungay());

	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());

	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Loại nhà cung cấp: ");
	    
	    cell = cells.getCell("C9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getLoaincc());
	    
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
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);        
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
				
		ResultSet rs = db.get(query);
		int index = 11;
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
					if(rs.getDouble("GIAMTRONGKY") != 0 || rs.getDouble("TANGTRONGKY") != 0 || rs.getDouble("DUNODAUKY") != 0){
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-10);	
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("ma"));	
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("ten"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getDouble("DUNODAUKY"));	
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("DUNODAUKY") +rs.getDouble("TANGTRONGKY")-rs.getDouble("GIAMTRONGKY"));	//Nhan hang   	6
						this.setStyleColorNormar(cells, cell);
						
						index++;					
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
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(totalnocodauky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totaltangtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalgiamtrongky);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(totalnocodauky+totaltangtrongky-totalgiamtrongky);	//Nhan hang   	6
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
		
		    	cells.setColumnWidth(0, 8.0f);
		    	cells.setColumnWidth(1, 15.0f);
		    	cells.setColumnWidth(2, 66.0f);
		    	cells.setColumnWidth(3, 15.0f);
		    	cells.setColumnWidth(4, 21.0f);
		    	cells.setColumnWidth(5, 21.0f);
		    	cells.setColumnWidth(6, 15.0f);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
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
