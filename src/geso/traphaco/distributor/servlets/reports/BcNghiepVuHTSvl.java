package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBcNghiepVuHT;
import geso.traphaco.distributor.beans.reports.imp.BcNghiepVuHT;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
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

@WebServlet("/BcNghiepVuHTSvl")
public class BcNghiepVuHTSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcNghiepVuHTSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcNghiepVuHT obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String loaihoadon = request.getParameter("loaihoadon");
		if (loaihoadon == null)
			loaihoadon = "0";
		
		obj = new BcNghiepVuHT();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		obj.init("");
		
		nextJSP = "/TraphacoHYERP/pages/Distributor/BcNghiepVuHT.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		HttpSession session = request.getSession();
		
		OutputStream out = response.getOutputStream();
		
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		
		IBcNghiepVuHT obj = new BcNghiepVuHT();
		obj.setUserId(userId);
		
		obj.setView("TT");
		
		String khoId = request.getParameter("khoId");
	    if(khoId == null)
	    	khoId = "";
	    obj.setKhoId(khoId);
	    
	    String[] lspIds = request.getParameterValues("loaisanpham");
	    String lspId = "";
	    if(lspIds != null)
	    {
	    	for(int i = 0; i < lspIds.length; i++)
	    		lspId += lspIds[i] + ",";
	    	if(lspId.length() > 0)
	    		lspId = lspId.substring(0, lspId.length() - 1);
	    	obj.setLoaiSpId(lspId);
	    }
	    
	    String[] spIds = request.getParameterValues("spIds");
	    String spId = "";
	    if(spIds != null)
	    {
	    	for(int i = 0; i < spIds.length; i++)
	    		spId += spIds[i] + ",";
	    	if(spId.length() > 0)
	    		spId = spId.substring(0, spId.length() - 1);
	    	obj.setSpId(spId);
	    }
		
		obj.setAction(action);
		
		System.out.println("___ATION " + action);
		
		if (action.equals("excel"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcNghiepVuHT.xlsm");
				FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcNghiepVuHT.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				obj.init("");
				CreateStaticHeader(workbook, obj);
				obj.setUserId(userId);
				String query = obj.getQueryHd();
				FillData(workbook, obj, query);
				workbook.save(out);
				fstream.close();
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMsg("Khong the tao pivot.");
			}
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Distributor/BcNghiepVuHT.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			obj.setUserId(userId);
			obj.init("");
			
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Distributor/BcNghiepVuHT.jsp");
		}
		else if (action.equals("search"))
		{
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			obj.init("");
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Distributor/BcNghiepVuHT.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Distributor/BcNghiepVuHT.jsp";
			obj.init("");
			response.sendRedirect(nextJSP);
		}		
	}
	
	private boolean FillData(Workbook workbook, IBcNghiepVuHT obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();
		ResultSet hdRs = db.get(query);

		int i = 3;
		int SoTt = 1;
		if (hdRs != null)
		{
			try
			{
				Cell cell = null;
				
				while (hdRs.next())
				{
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(SoTt++);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(hdRs.getString("loaichungtu"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(hdRs.getString("sochungtu"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(hdRs.getString("ngaychungtu"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(hdRs.getString("kho"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(hdRs.getString("spma"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(hdRs.getString("spten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(hdRs.getString("vitri"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(hdRs.getString("solo"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(hdRs.getString("ngayhethan"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(hdRs.getString("ngaynhapkho"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(hdRs.getString("mame"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("M" + Integer.toString(i));
					cell.setValue(hdRs.getString("mathung"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("N" + Integer.toString(i));
					cell.setValue(hdRs.getString("maphieu"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("O" + Integer.toString(i));
					cell.setValue(hdRs.getString("phieudt"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("P" + Integer.toString(i));
					cell.setValue(hdRs.getString("phieueo"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("Q" + Integer.toString(i));
					cell.setValue(hdRs.getString("MARQ"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("R" + Integer.toString(i));
					cell.setValue(hdRs.getString("hamam"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("S" + Integer.toString(i));
					cell.setValue(hdRs.getString("hamluong"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("T" + Integer.toString(i));
					cell.setValue(hdRs.getDouble("BOOKED"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					++i;
				}
				
				if (hdRs != null)
					hdRs.close();
				
				if (db != null)
					db.shutDown();
				
				if (i == 3)
				{
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
			} catch (Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		} else
		{
			return false;
		}
		return true;
		
	}
	
	private void CreateStaticHeader(Workbook workbook, IBcNghiepVuHT obj)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
		Style style;
		Font font = new Font();
		font.setColor(Color.RED);// mau chu
		font.setSize(16);// size chu
		font.setBold(true);
		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		
		String tieude = "BC NGHIỆP VỤ CHƯA HOÀN TẤT";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);
		ReportAPI.mergeCells(worksheet,6,7,0,0);
		
		cell = cells.getCell("A2");
		cell.setValue("STT");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);		
		
		cell = cells.getCell("B2");
		cell.setValue("Nghiệp vụ");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);		
		
		cell = cells.getCell("C2");
		cell.setValue("Số chứng từ");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);		
		
		cell = cells.getCell("D2");
		cell.setValue("Ngày chứng từ");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);		

		cell = cells.getCell("E2");
		cell.setValue("Kho");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);		
		
		cell = cells.getCell("F2");
		cell.setValue("Mã sản phẩm");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("G2");
		cell.setValue("Tên sản phẩm");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("H2");
		cell.setValue("Vị trí");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("I2");
		cell.setValue("Số lô");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("J2");
		cell.setValue("Ngày hết hạn");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("K2");
		cell.setValue("Ngày nhập kho");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("L2");
		cell.setValue("Mã mẻ");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("M2");
		cell.setValue("Mã thùng");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("N2");
		cell.setValue("Mã phiếu");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("O2");
		cell.setValue("Phiếu định tính");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("P2");
		cell.setValue("Phiếu EO");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("Q2");
		cell.setValue("MARQ");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("R2");
		cell.setValue("Hàm ẩm");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("S2");
		cell.setValue("Hàm lượng");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
		cell = cells.getCell("T2");
		cell.setValue("Số Lượng");
		ReportAPI.setCellBackground_Font(cell, Color.BLACK, BorderLineType.THIN, true, 0, Color.WHITE);
		
	}
	
}