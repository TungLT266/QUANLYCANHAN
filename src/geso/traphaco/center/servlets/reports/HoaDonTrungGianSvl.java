package geso.traphaco.center.servlets.reports;


import geso.traphaco.center.db.sql.dbutils_syn;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.report.Ireport;
import geso.traphaco.distributor.beans.report.imp.Report;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

@WebServlet("/HoaDonTrungGianSvl")
public class HoaDonTrungGianSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public HoaDonTrungGianSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/HoaDonTrungGian.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);

		String nppId = "";
		nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String vungId = request.getParameter("vungId");
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);

		String khuvucId = request.getParameter("khuvucId");
		if (khuvucId == null)
			khuvucId = "";
		obj.setKvId(khuvucId);

		String tungay = util.antiSQLInspection(request.getParameter("Sdays"));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("Edays"));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String action = request.getParameter("action");
		if (action.equals("tao"))
		{

			request.setCharacterEncoding("utf-8");

			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=HoaDonTrungGian.xlsm");

			OutputStream out = response.getOutputStream();

			String query =
			"	SELECT	( SELECT TOP(1) SOHOADONMOI FROM TBL_HOADONTT WHERE SOHOADONMOI IS NOT NULL AND LEN(ISNULL(STT_REC,'')) >0  AND STT_REC=HD.STT_REC_HD	ORDER BY NGAY DESC  ) AS HOADONDUNG ,* "+
			"	FROM TBL_INVOICE HD "+ 
			"	WHERE  1=1 ";
			
			if(tungay.length()>0)
			{
				query += " AND CONVERT(varchar(10), NGAYHOADON, 120) >= '"+tungay+"' ";
			}
			if(denngay.length()>0)
			{
				query += " AND CONVERT(varchar(10), NGAYHOADON, 120) <= '"+denngay+"' ";
			}
			if(nppId.length()>0)
			{
				query+=" AND MaKhachHang  ='"+nppId+"'";
			}
			try
			{
				if (!CreatePivotTable(out, obj, query))
				{
					response.setContentType("text/html");
					PrintWriter writer = new PrintWriter(out);
					writer.print("Xin loi khong co bao cao trong thoi gian nay");
					writer.close();
				}
			} catch (Exception e)
			{
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
			}
			
		}
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/HoaDonTrungGian.jsp";
			response.sendRedirect(nextJSP);
			return;
	}

	private boolean CreatePivotTable(OutputStream out, Ireport obj, String query) throws Exception
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HoaDonTrungGian.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		CreateHeader(workbook, obj);

		isFillData = FillData(workbook, query, obj);
		if (!isFillData)
		{
			if (fstream != null)
				fstream.close();
			return false;
		}
		workbook.save(out);
		fstream.close();
		return true;
	}

	private void CreateHeader(Workbook workbook, Ireport obj)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 16, "HÓA ĐƠN TRUNG GIAN");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Người tạo : " + obj.getuserTen());

		cell = cells.getCell("FA1");cell.setValue("NgayHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FB1");cell.setValue("SoHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FC1");cell.setValue("MaKhachHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FD1");cell.setValue("LoaiHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FE1");cell.setValue("SoDonDatHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FF1");cell.setValue("MaSanPham");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FG1");cell.setValue("SoLuong");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FH1");cell.setValue("DonViTinh");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FI1");cell.setValue("GiaNet");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FJ1");cell.setValue("TienTruocThue");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FK1");cell.setValue("Thue");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FL1");cell.setValue("TienSauThue");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FM1");cell.setValue("GiaGross");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FN1");cell.setValue("ChietKhauTrucTiep");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FO1");cell.setValue("ChietKhauThuongMai");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FP1");cell.setValue("ChietKhauDongHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FQ1");cell.setValue("Scheme");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FR1");cell.setValue("SoLo");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FS1");cell.setValue("Date");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FT1");cell.setValue("Huy");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FU1");cell.setValue("DaNhanHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FV1");cell.setValue("HoaDonThayThe");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FW1");cell.setValue("NgayGioTao");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FX1");cell.setValue("FAST_ID_HD");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FY1");cell.setValue("SoLuongGoi");ReportAPI.setCellHeader(cell);
		
		
		
	}

	private boolean FillData(Workbook workbook, String query, Ireport obj) throws Exception
	{
		ResultSet rs = null;
		dbutils_syn db = new dbutils_syn();

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		for (int i = 0; i < 4; ++i)
		{
			cells.setColumnWidth(i, 15.0f);
		}
		rs = db.get(query);
		int index = 2;
		if (rs != null)
		{
			try
			{
				Cell cell = null;
				while (rs.next())
				{					
					cell = cells.getCell("FA" + String.valueOf(index));cell.setValue(rs.getString("NgayHoaDon"));
					cell = cells.getCell("FB" + String.valueOf(index));cell.setValue(rs.getString("HoaDonDung")!=null?rs.getString("HoaDonDung"):rs.getString("SoHoaDon"));
					cell = cells.getCell("FC" + String.valueOf(index));cell.setValue(rs.getString("MaKhachHang"));
					cell = cells.getCell("FD" + String.valueOf(index));cell.setValue(rs.getString("LoaiHoaDon").equals("0")?"BÁN":"KM");
					cell = cells.getCell("FE" + String.valueOf(index));cell.setValue(rs.getString("SO"));
					cell = cells.getCell("FF" + String.valueOf(index));cell.setValue(rs.getString("MASANPHAM"));
					cell = cells.getCell("FG" + String.valueOf(index));cell.setValue(rs.getDouble("SoLuong"));
					cell = cells.getCell("FH" + String.valueOf(index));cell.setValue(rs.getString("DonViTinh"));
					cell = cells.getCell("FI" + String.valueOf(index));cell.setValue(rs.getDouble("GiaNet"));
					cell = cells.getCell("FJ" + String.valueOf(index));cell.setValue(rs.getDouble("TienTruocThue"));
					cell = cells.getCell("FK" + String.valueOf(index));cell.setValue(rs.getDouble("Thue"));
					cell = cells.getCell("FL" + String.valueOf(index));cell.setValue(rs.getDouble("TienSauThue"));
					cell = cells.getCell("FM" + String.valueOf(index));cell.setValue(rs.getDouble("GiaGross"));
					cell = cells.getCell("FN" + String.valueOf(index));cell.setValue(rs.getDouble("CKTT"));
					cell = cells.getCell("FO" + String.valueOf(index));cell.setValue(rs.getDouble("CKTM"));
					cell = cells.getCell("FP" + String.valueOf(index));cell.setValue(rs.getDouble("CKDH"));
					cell = cells.getCell("FQ" + String.valueOf(index));cell.setValue(rs.getString("Scheme"));
					cell = cells.getCell("FR" + String.valueOf(index));cell.setValue(rs.getString("Lo"));
					cell = cells.getCell("FS" + String.valueOf(index));cell.setValue(rs.getString("Date"));
					cell = cells.getCell("FT" + String.valueOf(index));cell.setValue(rs.getString("Huy"));
					cell = cells.getCell("FU" + String.valueOf(index));cell.setValue(rs.getString("IsDaNhan"));
					cell = cells.getCell("FV" + String.valueOf(index));cell.setValue(rs.getString("IsThayThe"));
					
					cell = cells.getCell("FW" + String.valueOf(index));cell.setValue(rs.getString("Created_date"));
					cell = cells.getCell("FX" + String.valueOf(index));cell.setValue(rs.getString("Stt_rec_hd"));
					cell = cells.getCell("FY" + String.valueOf(index));cell.setValue(rs.getDouble("SoLuongGoi"));
					index++;
				}

				if (rs != null)
					rs.close();

				if(index ==2)
				{
					throw new Exception("Không có báo cáo với điều kiện đã chọn !");
				}
				if (db != null)
					db.shutDown();
			} catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		} else
		{
			return false;
		}
		return true;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
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

}
