package geso.traphaco.center.servlets.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

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

public class DailyStocknpp extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4137948157929406688L;

	public DailyStocknpp()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		obj.setuserTen(userTen);
		obj.setuserId(userId);
		obj.setMsg("");
		obj.settungay("");
		obj.setdenngay("");

		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/rp_DailyStocknpp.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IStockintransit obj = new Stockintransit();
		request.setCharacterEncoding("utf-8");
		String nextJSP = "/TraphacoHYERP/pages/Center/rp_DailyStocknpp.jsp";
		Utility util = new Utility();
		try
		{
			HttpSession session = request.getSession();
			obj.setuserTen((String) session.getAttribute("userTen") != null ? (String) session.getAttribute("userTen") : "");

			obj.settungay(util.antiSQLInspection(request.getParameter("tungay")) != null ? util.antiSQLInspection(request
				.getParameter("tungay")) : "");

			obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")) != null ? util.antiSQLInspection(request
				.getParameter("denngay")) : "");

			obj.setuserId(util.antiSQLInspection(request.getParameter("userId")) != null ? util.antiSQLInspection(request
				.getParameter("userId")) : "");


			String tuthang = request.getParameter("tuthang");
			if(tuthang == null)
				tuthang = "";
			
			obj.setFromMonth( (tuthang.length()>1? tuthang:"0"+tuthang ));
		
			
			String denthang = request.getParameter("denthang");
			if(denthang == null)
				denthang = "";
			obj.setToMonth((denthang.length()>1? denthang:"0"+denthang ));
		
			
			String tunam = request.getParameter("tunam");
			if(tunam == null)
				tunam = "";
			obj.setFromYear(tunam);
			
			String dennam = request.getParameter("dennam");
			if(dennam == null)
				dennam = "";
			obj.setToYear(dennam);
			
			
			/*
			 * response.setContentType("application/vnd.ms-excel");
			 * response.setHeader("Content-Disposition",
			 * "attachment; filename=TonKhoTheoNgay.xls");
			 */
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DailyStock(NPP).xlsm");

			boolean isTrue = CreatePivotTable(out, obj);
			if (!isTrue)
			{

			} else
			{
				PrintWriter writer = new PrintWriter(out);
				writer.write("Xin loi. Khong tao duoc bao cao trong thoi gian nay..!!");
				writer.close();
			}
		} catch (Exception ex)
		{
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception
	{

		FileInputStream fstream;
		String chuoi = getServletContext().getInitParameter("path") + "\\DailyStock(NPP).xlsm";
		fstream = new FileInputStream(chuoi);
		// fstream = new
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		// Buoc2 tao khung
		// ham tao khu du lieu
		CreateStaticHeader(workbook, obj.gettungay(), obj.getdenngay(), obj.getuserTen(),obj);
		// Buoc3
		// day du lieu vao
		boolean isTrue = CreateStaticData(workbook, obj);
		if (!isTrue)
		{
			return false;
		}
		workbook.save(out);
		fstream.close();
		return true;
	}

	private void CreateStaticHeader(Workbook workbook, String tungay, String denngay, String UserName,IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("TỒN KHO THÁNG");   	
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,9);	    
	    cell.setValue("Từ tháng: " + obj.getFromMonth());
	    
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến tháng: " + obj.getToMonth());    
		
		
		getCellStyle(workbook, "A4", Color.NAVY, true, 9);
		cell.setValue("Ngày Tạo: " + this.getDateTime());
		cell = cells.getCell("A5");
		getCellStyle(workbook, "A5", Color.NAVY, true, 9);
		cell.setValue("Tạo Bởi:  " + UserName);

		// tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell("EA1");
		cell.setValue("KenhBanHang");
		cell = cells.getCell("EB1");
		cell.setValue("TenSanPham");
		cell = cells.getCell("EC1");
		cell.setValue("SoLuong");
		cell = cells.getCell("ED1");
		cell.setValue("Ngay");
		cell = cells.getCell("EE1");
		cell.setValue("MaNhaPhanPhoi");
		cell = cells.getCell("EF1");
		cell.setValue("MaSanPham");
		cell = cells.getCell("EG1");
		cell.setValue("SoLuongKien");
		cell = cells.getCell("EH1");
		cell.setValue("Kho");
		cell = cells.getCell("EI1");
		cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("EJ1");
		cell.setValue("ChungLoai");
		cell = cells.getCell("EK1");
		cell.setValue("NhanHang");
		cell = cells.getCell("EL1");
		cell.setValue("SoTien");

		worksheet.setName("Sheet1");
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		Utility util = new Utility();

		String sql = 
			" 	SELECT ISNULL(D.TEN, 'CHUA XAC DINH') AS CHANEL, F.TEN AS REGION, H.TEN AS AREA, E.TEN AS DISTRIBUTOR," +
			"		E.SITECODE AS DISTCODE, B.TEN AS SKU, B.MA AS SKUCODE, cast (TKN.nam as varchar(4)) +'-' + cast( TKN.thang  as varchar(2))  AS DATE, C.TEN AS WAREHOUSE," +
			"		G.TEN AS PROVINCE, I.DONVIKINHDOANH AS BUSINESSUNIT, K.TEN AS BRANDS, J.TEN AS CATEGORY, TKN.SOLUONG AS PIECE," +
			"		CASE" +
			"			WHEN TKN.SOLUONG IS NULL THEN 0 ELSE TKN.SOLUONG/QC.SOLUONG1 END AS QUATITY," +
			"		CASE" +
			"			WHEN TKN.SOLUONG * NPPK.GIAMUA IS NULL THEN 0 ELSE TKN.SOLUONG * NPPK.GIAMUA END AS AMOUNT" +
			"	FROM (SELECT * FROM TonKhoThang WHERE NPP_FK = '" +util.getIdNhapp(obj.getuserId()) +"' and THANG >='" + obj.getFromMonth() + "' and Thang <= '" + obj.getToMonth() + "' and Nam >='"+obj.getFromYear()+"'  and nam  <='"+obj.getToYear()+"'  " ;
			sql+="	) TKN	INNER JOIN SANPHAM B ON TKN.SANPHAM_FK = B.PK_SEQ" +
			"		INNER JOIN KHO C ON TKN.KHO_FK = C.PK_SEQ" +
			"		LEFT JOIN KENHBANHANG D ON TKN.KBH_FK = D.PK_SEQ" +
			"		INNER JOIN NHAPHANPHOI E ON TKN.NPP_FK = E.PK_SEQ" +
			"		LEFT JOIN KHUVUC F ON E.KHUVUC_FK = F.PK_SEQ" +
			"		LEFT JOIN TINHTHANH G ON E.TINHTHANH_FK = G.PK_SEQ" +
			"		LEFT JOIN VUNG H ON F.VUNG_FK = H.PK_SEQ" +
			"		INNER JOIN DONVIKINHDOANH I ON B.DVKD_FK = I.PK_SEQ" +
			"		LEFT JOIN CHUNGLOAI J ON B.CHUNGLOAI_FK = J.PK_SEQ" +
			"		LEFT JOIN NHANHANG K ON B.NHANHANG_FK = K.PK_SEQ" +
			 " LEFT JOIN" +
			 " (  "+
			 " 	SELECT B.KENH_FK,C.NPP_FK  ,D.SANPHAM_FK, D.GIAMUANPP *0.97 AS GIAMUA  "+
			 " 	FROM BANGGIAMUANPP B   "+
			 " 		INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK    "+
			 " 		INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK  "+
			 " 	WHERE  B.TUNGAY <='" + obj.gettungay() + "' AND   "+
			 " 	B.PK_SEQ =    "+
			 " 	(     "+
			 " 		SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG  "+
			 " 		INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK    "+
			 " 		WHERE BG.TUNGAY <= '" + obj.gettungay() + "' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK "+
			 " 		ORDER BY TUNGAY DESC   "+
			 " 	)  "+
			 " )NPPK ON  NPPK.SANPHAM_FK=B.PK_SEQ  AND NPPK.NPP_FK =TKN.NPP_FK   AND TKN.KBH_FK=NPPK.KENH_FK "+
			"		LEFT JOIN QUYCACH QC ON QC.DVDL1_FK = B.DVDL_FK AND TKN.SANPHAM_FK = QC.SANPHAM_FK AND QC.DVDL2_FK=100018 " +
			" ORDER BY DATE,WAREHOUSE,SOLUONG";
		System.out.println("Lay Du Lieu :" + sql);

		ResultSet rs = db.get(sql);
		int i = 2;
		if (rs != null)
		{
			try
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);

				// set do rong cho dong

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{

					// lay tu co so du lieu, gtknn bien
					String Channel = rs.getString("Chanel");
					String SKU = rs.getString("SKU");
					String Piece = rs.getString("Piece");
					String Date = rs.getString("Date");
					String DistributorCode = rs.getString("Distributor");
					String SkuCode = rs.getString("SKUcode");
					double Quantily = rs.getDouble("Quatity");
					String Warehouse = rs.getString("Warehouse");
					String BusinessUnit = rs.getString("BusinessUnit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brands");
					String Amount = rs.getString("Amount");

					cell = cells.getCell("EA" + Integer.toString(i));
					cell.setValue(Channel);
					cell = cells.getCell("EB" + Integer.toString(i));
					cell.setValue(SKU);
					cell = cells.getCell("EC" + Integer.toString(i));
					cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("ED" + Integer.toString(i));
					cell.setValue(Date);
					cell = cells.getCell("EE" + Integer.toString(i));
					cell.setValue(DistributorCode);
					cell = cells.getCell("EF" + Integer.toString(i));
					cell.setValue(SkuCode);
					cell = cells.getCell("EG" + Integer.toString(i));
					cell.setValue(Quantily);
					cell = cells.getCell("EH" + Integer.toString(i));
					cell.setValue(Warehouse);
					cell = cells.getCell("EI" + Integer.toString(i));
					cell.setValue(BusinessUnit);
					cell = cells.getCell("EJ" + Integer.toString(i));
					cell.setValue(Category);
					cell = cells.getCell("EK" + Integer.toString(i));
					cell.setValue(Brands);
					cell = cells.getCell("EL" + Integer.toString(i));
					cell.setValue(Float.parseFloat(Amount));

					i++;

				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();

				if (i == 2)
					throw new Exception("Khong co bao cao trong thoi gian nay...!!!");


			} catch (Exception e)
			{
				throw new Exception(e.getMessage());
			}
			finally
			{
				db.shutDown();
			}
		} else
		{
			return false;
		}
		return true;

	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a);
		style = cell.getStyle();
		Font font1 = new Font();
		font1.setColor(mau);
		font1.setBold(dam);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void main(String[] arg)
	{
		String str="2014-08-01";
		System.out.println(str.split("-")[1]);
	}
	
}
