package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;

import java.util.*;

public class SecondarySalesPIRTT extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public SecondarySalesPIRTT()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		obj.setuserId(userId);
		obj.setdiscount("0");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		// OutputStream out = response.getOutputStream();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		
		Utility util =new Utility();

		String nppId = request.getParameter("nppId");
		obj.setnppId(nppId);
		obj.setuserTen(userTen);

		String vungId = request.getParameter("vungId"); 
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = request.getParameter("khuvucId"); 
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String kenhId = request.getParameter("kenhId"); 
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String dvkdId = request.getParameter("dvkdId"); 
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);

		String nhanhangId = request.getParameter("nhanhangId"); 
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);

		String chungloaiId = request.getParameter("chungloaiId");
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		obj.setsanphamId(util.antiSQLInspection(request.getParameter("sanphamId"))!=null?util.antiSQLInspection(request.getParameter("sanphamId")):"");
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nganhhangId"))!=null?util.antiSQLInspection(request.getParameter("nganhhangId")):"");
		obj.setHoaDonKmDb(util.antiSQLInspection(request.getParameter("hdkmdb"))!=null?util.antiSQLInspection(request.getParameter("hdkmdb")):"");
		obj.setHangDiDuong(util.antiSQLInspection(request.getParameter("instransit"))!=null?util.antiSQLInspection(request.getParameter("instransit")):"");
		
		
		String tungay = request.getParameter("Sdays"); 
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = request.getParameter("Edays");
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String khoId = request.getParameter("khoId"); 
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);

		String vat = request.getParameter("vat"); 
		obj.setvat(vat);
		
		String discount = request.getParameter("discount"); 
		obj.setdiscount(discount);
		

		String giatinh = request.getParameter("giatinh");
		if (giatinh == null)
			giatinh = "1";
		obj.settype(giatinh);
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		
		System.out.println("GET TYPE : " + obj.gettype());
		if (!tungay.equals("") && !denngay.equals(""))
		{
			String action = request.getParameter("action");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				
				
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(TT)" + util.setTieuDe(obj) + ".xlsm");

				OutputStream out = response.getOutputStream();
				try
				{
					System.out.println("Minh Dung da o day.");
					CreatePivotTable(out, response, request, fieldsHien, obj); // Create
				} catch (Exception ex)
				{
					request.getSession().setAttribute("errors", ex.getMessage());
				}
				return;
				
			} else if (action.equals("toPdf"))
			{
				response.setContentType("application/pdf");
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				try
				{
					obj.XuatNhapTonPdf(document, outstream, obj, "", giatinh);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}

	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
	}

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTonTT(NPP).xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			FillData(workbook, fieldsHien, obj);
			workbook.save(out);
			fstream.close();
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void FillData(Workbook workbook, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
			   
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 10.0f);   
			}
			cells.setColumnWidth(3, 18.0f);
			cells.setColumnWidth(5, 18.0f);
		   
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN");
		   
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
			
			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
			
			cell = cells.getCell("P1");
			Style style1=cell.getStyle();
			
			cell = cells.getCell("O1");
			Style style2=cell.getStyle();
			
			dbutils db = new dbutils();
			ResultSet rs;
			String[] param = new String[4];
			param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
			param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
			
			String query = "delete kho_tmp";
			db.update(query);
			query = "delete npp_tmp";
			db.update(query);

			if(obj.getnppId().length() > 0)
				query = "insert into npp_tmp(npp_fk) select pk_seq from nhaphanphoi where pk_seq in (" + obj.getnppId() + ")";
			else
				query = "insert into npp_tmp(npp_fk) select pk_seq from nhaphanphoi where trangthai = 1";
			db.update(query);
			
			if(obj.getkhoId().length() > 0)
				query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
			else
				query = "insert into kho_tmp(kho_fk) select pk_seq from kho where trangthai = 1";
			db.update(query);
				
			rs = db.getRsByPro("REPORT_XUATNHAPTON_NHIEUKHO_TT", param);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int socottrongSql = rsmd.getColumnCount();
			   
			int countRow = 4;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				cell.setStyle(style2);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, rsmd.getColumnName(i));
    	
			}
			countRow ++;
	   
			NumberFormat formatter = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						cell.setStyle(style1);
						ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setStyle(style2);
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
	   
			if(rs!=null)rs.close();
			db.shutDown();

		}catch(Exception ex){
		
			ex.printStackTrace();
		}
	}
	
}

