package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.reports.IBCDoanhSoTheoKH;
import geso.traphaco.distributor.beans.reports.imp.BCDoanhSoTheoKH;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCDoanhSoTheoKHSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	
	public BCDoanhSoTheoKHSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBCDoanhSoTheoKH obj = new BCDoanhSoTheoKH();
		
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String nppID = util.getIdNhapp(userId);
		
		String loai = request.getParameter("loai");
		String tt = request.getParameter("tt");
		if (tt == null)
			tt = "";
		
		obj.setUserId(userId);
		obj.setNppID(nppID);
		obj.setLoai(loai);
		obj.setTt(tt);
		
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("nppId", nppID);
		session.setAttribute("loai", loai);
		session.setAttribute("loi", "");
		session.setAttribute("tt", tt);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BCDoanhSoTheoKH.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");
		
		OutputStream out = response.getOutputStream();
		String userTen = (String) session.getAttribute("userTen");
		
		if (action.equals("taomoi"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCDoanhSoTheoKH.xlsm");
		}
		
		BCDoanhSoTheoKH obj = new BCDoanhSoTheoKH();
		
		String userId = (String) session.getAttribute("userId");
		obj.setUserId(userId);
		
		String nppId = (String) session.getAttribute("nppId");
		obj.setNppID(nppId);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
		{
			tungay = "";
		}
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
		{
			denngay = "";
		}
		obj.setDenngay(denngay);
		
		String tdvId = util.antiSQLInspection(request.getParameter("tdvId"));
		if (tdvId == null)
		{
			tdvId = "";
		}
		obj.setTdvId(tdvId);
		
		
		System.out.println("_____________"+tdvId);
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null)
		{
			loai = "";
		}
		obj.setLoai(loai);
		
		String tt = util.antiSQLInspection(request.getParameter("tt"));
		if (tt == null)
		{
			tt = "";
		}
		obj.setTt(tt);
		
		String chinhanhId = util.antiSQLInspection(request.getParameter("chinhanhId"));
		if (chinhanhId == null)
		{
			chinhanhId = "";
		}
		obj.setChinhanhId(chinhanhId);
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
		String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
		obj.setVungId(vungId);
		obj.setKhuvucId(khuvucId);
		obj.setTinhthanhId(tinhthanhId);
		
		if (action.equals("taomoi"))
		{
			if (obj.getLoai().equals("KH"))
			{
				response.setHeader("Content-Disposition", "attachment; filename=BCDoanhSoTheoKH.xlsm");
			} else if (obj.getLoai().equals("LKH"))
			{
				response.setHeader("Content-Disposition", "attachment; filename=BCDoanhSoTheoLKH.xlsm");
			}
			
		}
		
		
	obj.setMucCN_DT(util.antiSQLInspection(request.getParameter("cndt")) != null ? util.antiSQLInspection(request.getParameter("cndt")) : "");
	obj.setMuc_KhachHang(util.antiSQLInspection(request.getParameter("kh")) != null ? util.antiSQLInspection(request.getParameter("kh")) : "");
		
		
		
		obj.init();
		
		if (action.equals("taomoi"))
		{
			CreatePivotTable(out, response, request, userId, userTen, tungay, denngay, obj.getNppTen(), obj.getTdvTen(), obj);
		} else
		{
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BCDoanhSoTheoKH.jsp";
			response.sendRedirect(nextJSP);
		}
		
		// }
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String userId, String userTen, String tungay, String denngay, String nppTen, String tdvTen, IBCDoanhSoTheoKH obj)
	    throws IOException
	{
		
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BCDoanhSoTheoKH.xlsm";
			if (obj.getLoai().equals("KH"))
			{
				chuoi = getServletContext().getInitParameter("path") + "\\BCDoanhSoTheoKH.xlsm";
			} else if (obj.getLoai().equals("LKH"))
			{
				chuoi = getServletContext().getInitParameter("path") + "\\BCDoanhSoTheoLKH.xlsm";
			}
			
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			if (obj.getLoai().equals("KH"))
			{
				CreateStaticHeaderKH(workbook, userTen, tungay, denngay, nppTen);
				CreateStaticDataKH(workbook, userId, tungay, denngay, nppTen, obj);
			} else if (obj.getLoai().equals("LKH"))
			{
				CreateStaticHeaderLKH(workbook, userTen, tungay, denngay, nppTen);
				CreateStaticDataLKH(workbook, userId, tungay, denngay, nppTen, obj);
			}
			
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			
		}
		
	}
	
	private void CreateStaticHeaderKH(Workbook workbook, String userTen, String tungay, String denngay, String nppTen)
	{
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();
		
		Cell cell = cells.getCell("A4");
		cell.setValue("Đại lý Traphaco tại: " + nppTen);
		
		cell = cells.getCell("H4");
		cell.setValue("Từ ngày: " + tungay + "  Đến ngày: " + denngay);
		ReportAPI.mergeCells(worksheet, 3, 3, 7, 10);
		
		worksheet.setName("Báo cáo doanh số theo Nhóm Hàng");
	}
	

	
	private void CreateStaticDataKH(Workbook workbook, String userId, String tungay, String denngay, String nppTen, IBCDoanhSoTheoKH obj)
	{
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		ResultSet rs = obj.getRsBCDoanhSoTheoKH();
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		if (rs != null)
		{
			try
			{
				
				int i = 10;
				Cell cell = null;
				
				double tongds = 0;
				double tongthanhtien = 0;
				double tongdsboga = 0;
				double tongdsxanh = 0;
				double tongdskhac = 0;
				
				int so = 1;
				Font f = new Font();
				Style style;
				dbutils db=new dbutils();
				while (rs.next())
				{
					String tenkh = rs.getString("ten");
					String makh = rs.getString("mafast");
					String tennpp = rs.getString("nppten");
					
					double doanhsoboga = 0;
					try
					{
						doanhsoboga = rs.getDouble("dsnhomhh");
						
					} catch (Exception er)
					{
						
					}
					
					double doanhsoxanh = 0;
					try
					{
						doanhsoxanh = rs.getDouble("dsnhomxanh");
						
					} catch (Exception er)
					{
						
					}
					
					double doanhsokhac = 0;
					try
					{
						doanhsokhac = rs.getDouble("dsnhomkhac");
						
					} catch (Exception er)
					{
						
					}
					
					double doanhso = doanhsoboga + doanhsoxanh + doanhsokhac;
					
			/*		double tileboga =Double.parseDouble( df2.format(doanhsoboga / doanhso * 100 - 0.5 + 0.05));
					
					double tilexanh =Double.parseDouble( df2.format(doanhsoxanh / doanhso * 100 - 0.5 + 0.05));
					
			*/		
					
					double tileboga =doanhsoboga / doanhso * 100 ;
					
					double tilexanh =doanhsoxanh / doanhso * 100 ;
				
					//System.out.println("_________________________"+Math.floor(tileboga + 0.1));
					double tilekhac = 100 - tileboga - tilexanh;
					
					ResultSet rs1=db.get("select (Cast(Round("+tileboga+",2,1) as numeric(18,2))) as tileboga,(Cast(Round("+tilekhac+",2,1) as numeric(18,2))) as tilekhac,(Cast(Round("+tilexanh+",2,1) as numeric(18,2))) as tilexanh ");
					if(rs1.next()){
						tileboga=rs1.getDouble("tileboga");
						tilekhac=rs1.getDouble("tilekhac");
						tilexanh=rs1.getDouble("tilexanh");
					}
					rs1.close();
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(so);
					style = cell.getStyle();
					f.setColor(Color.BLACK);
					style.setFont(f);
					cell.setStyle(style);
					
					CreateBorderSetting(workbook, "A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(tennpp);
					CreateBorderSetting(workbook, "B" + Integer.toString(i));
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(makh);
					CreateBorderSetting(workbook, "C" + Integer.toString(i));
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(tenkh);
					CreateBorderSetting(workbook, "D" + Integer.toString(i));
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(doanhsoxanh);
					CreateBorderSetting(workbook, "E" + Integer.toString(i));
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(tilexanh);
					CreateBorderSetting(workbook, "F" + Integer.toString(i));
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(doanhsoboga);
					CreateBorderSetting(workbook, "G" + Integer.toString(i));
					
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(tileboga);
					CreateBorderSetting(workbook, "H" + Integer.toString(i));
					
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(doanhsokhac);
					CreateBorderSetting(workbook, "I" + Integer.toString(i));
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(tilekhac );
					CreateBorderSetting(workbook, "J" + Integer.toString(i));
					
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(doanhso);
					CreateBorderSetting(workbook, "K" + Integer.toString(i));
					
					tongds += doanhso;
					tongdsboga += doanhsoboga;
					tongdsxanh += doanhsoxanh;
					tongdskhac += doanhsokhac;
					
					so++;
					
					i++;
				}
				
				cell = cells.getCell("A" + Integer.toString(i));
				cell.setValue("");
				CreateBorderSetting(workbook, "A" + Integer.toString(i));
				
				cell = cells.getCell("B" + Integer.toString(i));
				cell.setValue("");
				CreateBorderSetting(workbook, "B" + Integer.toString(i));
				
				cell = cells.getCell("F" + Integer.toString(i));
				cell.setValue(tongdsxanh / tongds * 100);
				CreateBorderSetting(workbook, "F" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("H" + Integer.toString(i));
				cell.setValue(tongdsboga / tongds * 100);
				CreateBorderSetting(workbook, "H" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("J" + Integer.toString(i));
				cell.setValue(tongdskhac / tongds * 100);
				CreateBorderSetting(workbook, "J" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				CreateBorderSetting(workbook, "C" + Integer.toString(i));
				
				cell = cells.getCell("D" + Integer.toString(i));
				cell.setValue("Tổng");
				CreateBorderSetting(workbook, "D" + Integer.toString(i));
				style = cell.getStyle();
				f.setBold(true);
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("K" + Integer.toString(i));
				cell.setValue(tongds);
				CreateBorderSetting(workbook, "K" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("E" + Integer.toString(i));
				cell.setValue(tongdsxanh);
				CreateBorderSetting(workbook, "E" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("G" + Integer.toString(i));
				cell.setValue(tongdsboga);
				CreateBorderSetting(workbook, "G" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("I" + Integer.toString(i));
				cell.setValue(tongdskhac);
				CreateBorderSetting(workbook, "I" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
			} catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Lỗi dữ liệu");
			}
		}
		
	}
	
	public static void main(String[] arg)
	{
		
	}
	
	private void CreateStaticHeaderLKH(Workbook workbook, String userTen, String tungay, String denngay, String nppTen)
	{
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();
		
		Cell cell = cells.getCell("A4");
		cell.setValue("Đại lý Traphaco tại: " + nppTen);
		
		cell = cells.getCell("J4");
		cell.setValue("Từ ngày: " + tungay + "  Đến ngày: " + denngay);
		
		worksheet.setName("BC DS theo loại KH");
	}
	
	private void CreateStaticDataLKH(Workbook workbook, String userId, String tungay, String denngay, String nppTen, IBCDoanhSoTheoKH obj)
	{
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		ResultSet rs = obj.getRsBCDoanhSoTheoKH();
		
		if (rs != null)
		{
			try
			{
				
				int i = 10;
				Cell cell = null;
				
				double tongds = 0;
				double tongthanhtien = 0;
				double tongdsbl = 0;
				double tongdsbb = 0;
				double tongdsblbb = 0;
				double tongdsbbbl = 0;
				
				int so = 1;
				Font f = new Font();
				Style style;
				
				while (rs.next())
				{
					String tennpp = rs.getString("ten");
					double doanhsobl = rs.getDouble("bl");
					double doanhsobb = rs.getDouble("bb");
					double doanhsobbbl = rs.getDouble("bbbl") + rs.getDouble("blbb");
					double doanhso = doanhsobl + doanhsobb + doanhsobbbl;
					
					double tilebl = doanhsobl / doanhso * 100;
					double tilebb = doanhsobb / doanhso * 100;
					double tilebbbl = doanhsobbbl / doanhso * 100;
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(so);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(tennpp);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(doanhsobl);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(tilebl);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(doanhsobb);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(tilebb);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(doanhsobbbl);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(tilebbbl);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(doanhso);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					tongds += doanhso;
					tongdsbl += doanhsobl;
					tongdsbb += doanhsobb;
					tongdsbbbl += doanhsobbbl;
					
					so++;
					
					i++;
				}
				
				cell = cells.getCell("A" + Integer.toString(i));
				cell.setValue("");
				CreateBorderSetting(workbook, "A" + Integer.toString(i));
				
				cell = cells.getCell("C" + Integer.toString(i));
				cell.setValue("Tổng");
				CreateBorderSetting(workbook, "C" + Integer.toString(i));
				style = cell.getStyle();
				f.setBold(true);
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("L" + Integer.toString(i));
				cell.setValue(tongds);
				CreateBorderSetting(workbook, "L" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("D" + Integer.toString(i));
				cell.setValue(tongdsbl);
				CreateBorderSetting(workbook, "D" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("E" + Integer.toString(i));
				cell.setValue(tongdsbl / tongds * 100);
				CreateBorderSetting(workbook, "E" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("F" + Integer.toString(i));
				cell.setValue(tongdsbb);
				CreateBorderSetting(workbook, "F" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("G" + Integer.toString(i));
				cell.setValue(tongdsbb / tongds * 100);
				CreateBorderSetting(workbook, "G" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
			
				
				cell = cells.getCell("J" + Integer.toString(i));
				cell.setValue(tongdsbbbl);
				CreateBorderSetting(workbook, "J" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
				cell = cells.getCell("K" + Integer.toString(i));
				cell.setValue(tongdsbbbl / tongds * 100);
				CreateBorderSetting(workbook, "K" + Integer.toString(i));
				style = cell.getStyle();
				f.setColor(Color.BLACK);
				style.setFont(f);
				cell.setStyle(style);
				
			} catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Lỗi dữ liệu");
			}
		}
		
	}
	
	public void CreateBorderSetting(Workbook workbook, String fileName) throws IOException
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		Cell cell;
		Style style;
		
		cell = cells.getCell(fileName);
		style = cell.getStyle();
		
		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);
		// style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
		// style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);
		
		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
		// style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);
		
		cell.setStyle(style);
		
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
}
