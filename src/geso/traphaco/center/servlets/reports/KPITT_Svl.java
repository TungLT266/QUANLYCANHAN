package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KPITT_Svl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public KPITT_Svl()
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
		String querystring = request.getQueryString();

		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId(util.getIdNhapp(obj.getuserId()));

		obj.init();
		obj.settype("0");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Center/KPITT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));

		obj.setuserId(userId != null ? userId : "");
		obj.setuserTen(userTen != null ? userTen : "");
		obj.setkenhId(request.getParameter("kenhId") != null ? util.antiSQLInspection(request.getParameter("kenhId")) : "");

		obj.setvungId(request.getParameter("vungId") != null ? util.antiSQLInspection(request.getParameter("vungId")) : "");

		obj.setkhuvucId(request.getParameter("khuvucId") != null ? util.antiSQLInspection(request.getParameter("khuvucId")) : "");
		obj.setTtId(util.antiSQLInspection(request.getParameter("ttId")) != null ? util.antiSQLInspection(request.getParameter("ttId")) : "");
		obj.setgsbhId(request.getParameter("gsbhs") != null ? util.antiSQLInspection(request.getParameter("gsbhs")) : "");

		obj.setnppId(request.getParameter("nppId") != null ? util.antiSQLInspection(request.getParameter("nppId")) : "");

		obj.setdvkdId(request.getParameter("dvkdId") != null ? util.antiSQLInspection(request.getParameter("dvkdId")) : "");

		obj.setDdkd(request.getParameter("ddkdId") != null ? util.antiSQLInspection(request.getParameter("ddkdId")) : "");
		
		String tuthang = request.getParameter("tuthang").length() < 2 ? ("0" + request.getParameter("tuthang")) : request.getParameter("tuthang");
		String toithang = request.getParameter("denthang").length() < 2 ? ("0" + request.getParameter("denthang")) : request.getParameter("denthang");
		obj.setFromMonth(tuthang);

		obj.setToMonth(toithang);
		obj.setToYear(request.getParameter("dennam"));
		obj.setFromYear(request.getParameter("tunam"));

		obj.settype(request.getParameter("typeid"));

		String action = request.getParameter("action") != null ? util.antiSQLInspection(request.getParameter("action")) : "";
		String nextJSP = "/TraphacoHYERP/pages/Center/KPITT.jsp";

		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ChiSoThanhTich" + util.setTieuDe(obj) + ".xlsm");
				if (!CreatePivotTable(out, obj))
				{

				}
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		} else
		{

		}
		obj.init();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\KPITT.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);

		if (!isFill)
		{
			fstream.close();
			return false;
		} else
		{
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		try
		{

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			cell.setValue("CHỈ SỐ THÀNH TÍCH CỦA NHÂN VIÊN BÁN HÀNG");

			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 10);
			if (obj.gettype().equals("1"))
			{
				cell.setValue("Từ tháng: " + obj.getFromYear() + "-" + obj.getFromMonth());
			} else
			{
				if (obj.gettype().equals("0"))
					cell.setValue("Từ ngày: " + obj.gettungay());
				else
					cell.setValue("Từ tuần: " + obj.gettungay());
			}

			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("B3");
			getCellStyle(workbook, "B3", Color.NAVY, true, 9);

			if (obj.gettype().equals("1"))
			{
				cell.setValue("Đến tháng: " + obj.getToYear() + "-" + obj.getToMonth());
			} else
			{
				if (obj.gettype().equals("0"))
					cell.setValue("Đến ngày: " + obj.getdenngay());
				else
					cell.setValue("Đến tuần: " + obj.getdenngay());
			}
			cells.setRowHeight(4, 18.0f);
			cell = cells.getCell("A4");
			getCellStyle(workbook, "A4", Color.NAVY, true, 9);
			cell.setValue("Ngày báo cáo: " + this.getDateTime());

			cells.setRowHeight(5, 18.0f);
			cell = cells.getCell("A5");
			getCellStyle(workbook, "A5", Color.NAVY, true, 9);
			cell.setValue("Được tạo bởi Nhà phân phối:  " + obj.getuserTen());

			cell = cells.getCell("FA1");cell.setValue("KenhBanHang");
			cell = cells.getCell("FB1");cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("FC1");cell.setValue("ChiNhanh");
			cell = cells.getCell("FD1");cell.setValue("TINHTHANH");
			cell = cells.getCell("FE1");cell.setValue("GiamSat");
			cell = cells.getCell("FF1");cell.setValue("MaCN/DT");
			cell = cells.getCell("FG1");cell.setValue("ChiNhanh/DoiTac");
			cell = cells.getCell("FH1");cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("FI1");cell.setValue("CuaHieuQuanLy");
			cell = cells.getCell("FJ1");cell.setValue("CuaHieuCoDoanhSo");
			cell = cells.getCell("FK1");cell.setValue("SoDonHang");
			cell = cells.getCell("FL1");cell.setValue("MatHangBanDuoc");
			cell = cells.getCell("FM1");cell.setValue("MatHangPhanPhoi(SKU)");
			cell = cells.getCell("FN1");cell.setValue("DoanhSo");
			cell = cells.getCell("FO1");cell.setValue("SoLanVT");
			cell = cells.getCell("FP1");cell.setValue("MaDaiDienKinhDoanh");
			cell = cells.getCell("FQ1");cell.setValue("MaGiamSatBanHang");
			cell = cells.getCell("FR1");cell.setValue("LoaiCuaHang");
			cell = cells.getCell("FS1");cell.setValue("DoanhSoTB/DH");
			cell = cells.getCell("FT1");cell.setValue("TrungBinhSoSKU/DH");
			

		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		ResultSet rs;
		String[] param = new String[11];
		param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
		param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
		param[2] = obj.getDdkd().equals("") ? null : obj.getDdkd();
		param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
		param[4] = obj.getvungId().equals("") ? null : obj.getvungId();
		param[5] = obj.getTtId().equals("") ? null : obj.getTtId();
		param[6] = obj.getgsbhId().equals("") ? null : obj.getgsbhId();
		param[7] = obj.getkenhId().equals("") ? null : obj.getkenhId();
		param[8] = obj.getuserId().equals("") ? null : obj.getuserId();
		param[9] = "1";
		param[10] = obj.getdvkdId().equals("") ? null : obj.getdvkdId();
		rs = db.getRsByPro("getRsKpi_report", param);

		int i = 2;

		try
		{
			cells.setColumnWidth(0, 15.0f);
			cells.setColumnWidth(1, 15.0f);
			cells.setColumnWidth(2, 15.0f);
			cells.setColumnWidth(3, 15.0f);
			cells.setColumnWidth(4, 15.0f);
			cells.setColumnWidth(5, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(10, 15.0f);
			cells.setColumnWidth(11, 15.0f);
			cells.setColumnWidth(12, 15.0f);
			cells.setColumnWidth(13, 15.0f);
			cells.setColumnWidth(14, 15.0f);
			cells.setColumnWidth(15, 15.0f);
			cells.setColumnWidth(16, 15.0f);
			cells.setColumnWidth(17, 15.0f);
			cells.setColumnWidth(18, 15.0f);
			cells.setColumnWidth(19, 15.0f);
			cells.setColumnWidth(20, 15.0f);

			i = 2;
			if (rs != null)
			{
				Cell cell = null;
				Style style;
				while (rs.next())// lap den cuoi bang du lieu
				{

					// lay tu co so du lieu, gan bien
					String Channel = rs.getString("KENH");

					String Region = rs.getString("VUNG");

					String Area = rs.getString("TINHTHANH");

					String Distributor = rs.getString("NPPTEN");

					String SalesRep = rs.getString("DDKDTEN");

					String BusinessUnit = rs.getString("DONVIKINHDOANH");

					String Sitecode = rs.getString("NPPMA");

					String Salessup = rs.getString("GSBHTEN");

					// lay tu co so du lieu, gan bien

					double SKU = 0;

					SKU = rs.getDouble("SOSKU");

					// DOANH SO
					double Volume = rs.getDouble("DOANHSO");

					double Outlet = 0;

					Outlet = rs.getDouble("SOCUAHIEU");

					double OutletHaveVolume = 0;

					OutletHaveVolume = rs.getDouble("SOCHCODS");

					double Order = 0;

					Order = rs.getDouble("SODH");

					double SoldSKU = rs.getDouble("TONGLUONG");
					
					double AVG_SKU_PER_ORDER = SoldSKU/Order;
					double AVG_VOLUME_PER_ORDER = Volume/Order;

					cell = cells.getCell("FA" + Integer.toString(i));				cell.setValue(Channel); 
					cell = cells.getCell("FB" + Integer.toString(i));cell.setValue(BusinessUnit);
					cell = cells.getCell("FC" + Integer.toString(i));			cell.setValue(Region);
					cell = cells.getCell("FD" + Integer.toString(i));
					cell.setValue(Area);
					cell = cells.getCell("FE" + Integer.toString(i));
					cell.setValue(Salessup);
					cell = cells.getCell("FF" + Integer.toString(i));
					cell.setValue(Sitecode);
					cell = cells.getCell("FG" + Integer.toString(i));
					cell.setValue(Distributor);
					cell = cells.getCell("FH" + Integer.toString(i));
					cell.setValue(SalesRep);
					cell = cells.getCell("FI" + Integer.toString(i));
					cell.setValue(Outlet); 
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FJ" + Integer.toString(i));
					cell.setValue(OutletHaveVolume);
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FK" + Integer.toString(i));
					cell.setValue(Order);
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FL" + Integer.toString(i));
					cell.setValue(SoldSKU);
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FM" + Integer.toString(i));
					cell.setValue(SKU);// 13
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FN" + Integer.toString(i));
					cell.setValue(Volume);// 14
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FO" + Integer.toString(i));
					cell.setValue(rs.getDouble("solanvt"));
					style = cell.getStyle();
					style.setNumber(41);
					cell.setStyle(style);

					cell = cells.getCell("FP" + Integer.toString(i));
					cell.setValue(rs.getString("DDKDMA")); 
					cell = cells.getCell("FQ" + Integer.toString(i));cell.setValue(rs.getString("GSBHMA")); 
					
					cell = cells.getCell("FR" + Integer.toString(i));cell.setValue(rs.getString("LoaiCuaHang")==null?" ":rs.getString("LoaiCuaHang"));
			

					cell = cells.getCell("FS" + Integer.toString(i));cell.setValue(AVG_VOLUME_PER_ORDER);style = cell.getStyle();style.setNumber(41);cell.setStyle(style);
					cell = cells.getCell("FT" + Integer.toString(i));cell.setValue(AVG_SKU_PER_ORDER);style = cell.getStyle();style.setNumber(41);cell.setStyle(style);
					
					
					i++;
				}

				if (rs != null)
				{
					rs.close();
				}

				if (db != null)
				{
					db.shutDown();
				}

				if (i == 2)
				{
					obj.setMsg("Khong co bao cao trong thoi gian nay");
					return false;
				}
				return true;
			} else
			{
				obj.setMsg("Khong co bao cao trong thoi gian nay");
				return false;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			obj.setMsg("Khong The Tao Duoc Bao Cao :" + ex.toString());
			return false;
		}
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

	/*
	 * private void setHidden(Workbook workbook,int i) { Worksheets worksheets =
	 * workbook.getWorksheets(); Worksheet worksheet = worksheets.getSheet(0);
	 * 
	 * Cells cells = worksheet.getCells(); for(int j = 26; j <= i; j++) {
	 * cells.hideColumn(j); }
	 * 
	 * }
	 */
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
