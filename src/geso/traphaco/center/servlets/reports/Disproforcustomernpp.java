package geso.traphaco.center.servlets.reports;

import geso.traphaco.distributor.beans.reports.imp.Reports;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Disproforcustomernpp extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Disproforcustomernpp()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String nppId = util.getIdNhapp(userId);
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			Reports obj = new Reports();
			obj.setNppId(nppId);
			obj.CreateRsCTTB();
			obj.setcttbid("");

			session.setAttribute("report", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/Dispforcustomernpp.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
		Reports obj = new Reports();

		boolean bfasle = true;
		Utility util = new Utility();

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setUser(userId);

		String[] cttbid = request.getParameterValues("cttbid");
		String str3 = "";
		if (cttbid != null)
		{
			for (int i = 0; i < cttbid.length; i++)
				str3 += cttbid[i] + ",";
			if (str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		obj.setcttbid(str3);

		session.setAttribute("userId", userId);
		try
		{
			// response.setContentType("application/vnd.ms-excel");
			// response.setHeader("Content-Disposition",
			// "attachment; filename=DISPLAYPROGRAMAPPLYFORCUSTOMER.xls");

			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTrungBay.xlsm");

			CreatePivotTable(out, response, request, obj, bfasle);
		} catch (Exception ex)
		{
			ex.printStackTrace();

			// say sorry
			response.setContentType("text/html");
			PrintWriter writer = new PrintWriter(out);

			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>sorry</title>");
			writer.println("</head>");
			writer.println("<body>");
			writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
			ex.printStackTrace(writer);
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
	}

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, Reports obj,
		boolean bfasle) throws IOException
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\BaoCaoTrungBay.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUser());

		bfasle = CreateStaticData(workbook, obj, bfasle);
		if (bfasle == false)
		{
			String loi = "chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
			obj.setLoi(loi);
			obj.CreateRsCTTB();
			HttpSession session = request.getSession();
			String nextJSP = "/TraphacoHYERP/pages/Center/Dispforcustomernpp.jsp";
			session.setAttribute("report", obj);

			response.sendRedirect(nextJSP);
			fstream.close();
		} else
		{
			fstream.close();
			workbook.save(out);
		}
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		Style style;
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO TRƯNG BÀY");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

		int i = 3;
		if (!dateFrom.equals(""))
		{
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 9);
			cell.setValue("Từ ngày: " + dateFrom);
		}

		if (!dateTo.equals(""))
		{
			cell = cells.getCell("B3");
			getCellStyle(workbook, "B3", Color.NAVY, true, 9);
			cell.setValue("Đến ngày: " + dateTo);
		}

		i = 4;
		cell = cells.getCell("A" + i);
		getCellStyle(workbook, "A" + i, Color.NAVY, true, 9);
		cell.setValue("Ngày tạo báo cáo: " + this.getDateTime());
		i++;

		cell = cells.getCell("A" + i);
		getCellStyle(workbook, "A" + i, Color.NAVY, true, 9);
		cell.setValue("Được tạo bởi:  " + UserName);

		cell = cells.getCell("AB1");
		cell.setValue("Scheme");
		cell = cells.getCell("AC1");
		cell.setValue("Dien giai");
		cell = cells.getCell("AD1");
		cell.setValue("Ma khach hang");
		cell = cells.getCell("AE1");
		cell.setValue("Ten khach hang");
		cell = cells.getCell("AF1");
		cell.setValue("Dia chi");
		cell = cells.getCell("AG1");
		cell.setValue("Lan tra");
		cell = cells.getCell("AH1");
		cell.setValue("Dang ky");
		cell = cells.getCell("AI1");
		cell.setValue("Dat");
		cell = cells.getCell("AJ1");
		cell.setValue("De nghi");
		cell = cells.getCell("AK1");
		cell.setValue("Duyet");

	}

	private boolean CreateStaticData(Workbook workbook, Reports obj, boolean bfasle)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		// khoi tao ket noi csdl
		dbutils db = new dbutils();
		Utility Utl = new Utility();
		String manpp = "";
		manpp = Utl.getIdNhapp(obj.getUser());

		String dk = "";
		if (obj.getTuNgay() != "")
			dk += " and cb.ngaytrungbay>='" + obj.getTuNgay() + "'";
		if (obj.getDenNgay() != "")
			dk += " and cb.ngaytrungbay<='" + obj.getDenNgay() + "'";
		if (!obj.getcttbid().equals(""))
		{
			dk += " and cb.pk_seq in(" + obj.getcttbid() + ")";
		}

		String sql = 
			"select distinct cb.scheme as programid,cb.diengiai,kh.smartid as CustomerKey,kh.ten as CustomerName, " +
			" kh.diachi as Address," +
			" isnull(dntb.lanthanhtoan,1) as PayTime,tbkh.dangky as Allocation ,isnull(dntbkh.xuatdenghi,0) as Request_pay," +
			" isnull(dntbkh.xuatduyet,0)  as Approval, isnull(tbkh.dat, 0) as Dat " +
			" from cttrungbay cb" +
			" INNER JOIN NHOMCTTRUNGBAY NCB ON NCB.PK_SEQ=CB.NHOMCTTB_FK  "+
			"  INNER JOIN NHOMCTTRUNGBAY_NPP PB ON PB.NHOMCTTRUNGBAY_FK = NCB.PK_SEQ "+  
			" inner join dangkytrungbay dktb on dktb.cttrungbay_fk = cb.pk_seq and dktb.npp_fk = pb.npp_fk" +
			" left join dktrungbay_khachhang tbkh on tbkh.dktrungbay_fk = dktb.pk_seq" +
			" left join denghitratrungbay dntb on dntb.cttrungbay_fk = cb.pk_seq and dntb.npp_fk =dktb.npp_fk  " +
			" left join DENGHITRATB_KHACHHANG dntbkh on dntbkh.denghitratb_fk = dntb.pk_seq  and dntbkh.khachhang_fk = tbkh.khachhang_fk" +
			" inner join khachhang kh on kh.pk_seq = tbkh.khachhang_fk" + " where  dktb.npp_fk ='" + manpp + "'" + dk;
		sql = sql + " order by isnull(dntb.lanthanhtoan,1) ";

		System.out.print("1.Bao cao trung bay:" + sql);
		ResultSet rs = db.get(sql);

		int i = 2;
		if (rs != null)
		{
			try
			{
				cells.setColumnWidth(0, 20.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 25.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 25.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					String Program_ID = rs.getString("programid");
					String Program_Des = rs.getString("diengiai");

					String CustomerKey = rs.getString("CustomerKey");
					String CustomerName = rs.getString("CustomerName");
					String Address = rs.getString("Address");
					String PayTime = rs.getString("PayTime");
					String Allocation = rs.getString("Allocation");
					String Request_pay = rs.getString("Request_pay");
					String Approval_pay = rs.getString("Approval");
					String Dat = rs.getString("Dat");

					cell = cells.getCell("AB" + Integer.toString(i));
					cell.setValue(Program_ID);
					cell = cells.getCell("AC" + Integer.toString(i));
					cell.setValue(Program_Des);
					cell = cells.getCell("AD" + Integer.toString(i));
					cell.setValue(CustomerKey);
					cell = cells.getCell("AE" + Integer.toString(i));
					cell.setValue(CustomerName);
					cell = cells.getCell("AF" + Integer.toString(i));
					cell.setValue(Address);
					cell = cells.getCell("AG" + Integer.toString(i));
					cell.setValue(PayTime);
					cell = cells.getCell("AH" + Integer.toString(i));
					cell.setValue(Float.parseFloat(Allocation));
					cell = cells.getCell("AI" + Integer.toString(i));
					cell.setValue(Float.parseFloat(Dat));
					cell = cells.getCell("AJ" + Integer.toString(i));
					cell.setValue(Float.parseFloat(Request_pay));
					cell = cells.getCell("AK" + Integer.toString(i));
					cell.setValue(Float.parseFloat(Approval_pay));
					i++;
				}
				bfasle = true;
				if (rs != null)
					rs.close();
				if (db != null)
				{
					db.shutDown();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				return bfasle;
			}
		} else
		{
			bfasle = false;
			return bfasle;
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
}
