package geso.traphaco.center.servlets.reports;


import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;

import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

@WebServlet("/XuatNhapTonStoreSvl")
public class XuatNhapTonStoreSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public XuatNhapTonStoreSvl()
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
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/XuatNhapTonStore.jsp";
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

		obj.setuserId(userId != null ? userId : "");

		obj.setkenhId(request.getParameter("kbhId") != null ? util.antiSQLInspection(request.getParameter("kbhId")) : "");

		obj.setvungId(request.getParameter("vungId") != null ? util.antiSQLInspection(request.getParameter("vungId")) : "");

		obj.setkhuvucId(request.getParameter("khuvucId") != null ? util.antiSQLInspection(request.getParameter("khuvucId")) : "");

		obj.setgsbhId(request.getParameter("gsbhId") != null ? util.antiSQLInspection(request.getParameter("gsbhId")) : "");

		obj.setnppId(request.getParameter("nppId") != null ? util.antiSQLInspection(request.getParameter("nppId")) : "");

		obj.setdvkdId(request.getParameter("dvkdId") != null ? util.antiSQLInspection(request.getParameter("dvkdId")) : "");
		
		obj.setsanphamId(request.getParameter("spId") != null ? util.antiSQLInspection(request.getParameter("spId")) : "");
		
		obj.setnhanhangId(request.getParameter("nhanhangId") != null ? util.antiSQLInspection(request.getParameter("nhanhangId")) : "");
		
		obj.setchungloaiId(request.getParameter("chungloaiId") != null ? util.antiSQLInspection(request.getParameter("chungloaiId")) : "");
		
		String fromMonth = request.getParameter("fromMonth").length() < 2 ? ("0" + request.getParameter("fromMonth")) : request.getParameter("fromMonth");
		obj.setFromMonth(fromMonth);

		String toMonth = request.getParameter("toMonth").length() < 2 ? ("0" + request.getParameter("toMonth")) : request.getParameter("toMonth");		
		obj.setToMonth(toMonth);
		
		obj.setFromYear(request.getParameter("fromYear"));
		obj.setToYear(request.getParameter("toYear"));		

		String action = request.getParameter("action") != null ? util.antiSQLInspection(request.getParameter("action")) : "";
		String nextJSP = "/TraphacoHYERP/pages/Center/XuatNhapTonStore.jsp";

		if (action.equals("Excel"))
		{
			try
			{

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuatNhapTonStore_"+util.setTieuDe(obj)+".xlsm");
				if (!CreatePivotTable(out, obj))
				{
					obj.setMsg("Không có báo cáo với điều kiện đã chọn ! ");
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
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
		String chuoi = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTonStore.xlsm";
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
			cell.setValue("BÁO CÁO XUẤT NHẬP TỒN ");

			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 10);
			cell.setValue("Từ tháng: " + obj.getFromYear() + "-" + obj.getFromMonth());
		 
			

			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("B3");
			getCellStyle(workbook, "B3", Color.NAVY, true, 9);			
			cell.setValue("Đến tháng: " + obj.getToYear() + "-" + obj.getToMonth());
			
			cells.setRowHeight(4, 18.0f);
			cell = cells.getCell("A4");
			getCellStyle(workbook, "A4", Color.NAVY, true, 9);
			cell.setValue("Ngày báo cáo: " + this.getDateTime());

			cells.setRowHeight(5, 18.0f);
			cell = cells.getCell("A5");
			getCellStyle(workbook, "A5", Color.NAVY, true, 9);
			

			cell = cells.getCell("FA1");cell.setValue("KenhBanHang");
			cell = cells.getCell("FB1");cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("FC1");cell.setValue("Vung");
			cell = cells.getCell("FD1");cell.setValue("KhuVuc");
			cell = cells.getCell("FE1");cell.setValue("NhanHang");
			cell = cells.getCell("FF1");cell.setValue("Chungloai");
			cell = cells.getCell("FG1");cell.setValue("MaSanPham");
			cell = cells.getCell("FH1");cell.setValue("TenSanPham");
			
			cell = cells.getCell("FI1");cell.setValue("TongThauMa");
			cell = cells.getCell("FJ1");cell.setValue("TongThauTen");
			
			cell = cells.getCell("FK1");cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("FL1");cell.setValue("TenNhaPhanPhoi");
			cell = cells.getCell("FM1");cell.setValue("ThoiGian");
			cell = cells.getCell("FN1");cell.setValue("TonDau");
			cell = cells.getCell("FO1");cell.setValue("Nhap");
			cell = cells.getCell("FP1");cell.setValue("Ban");
			cell = cells.getCell("FQ1");cell.setValue("TonCuoi");

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
		String fromDate=obj.getFromYear()+"-"+obj.getFromMonth()+"-01";
		if(obj.getFromMonth().length()<2)
			fromDate=obj.getFromYear()+"-0"+obj.getFromMonth()+"-01";
		
		String toDate=obj.getToYear()+"-"+obj.getToMonth()+"-01";
		if(obj.getToMonth().length()<2)
			toDate=obj.getToYear()+"-0"+obj.getToYear()+"-01";
		
		
		String query=
		"	select kbh.TEN as KenhBanHang,dvkd.DONVIKINHDOANH,isnull(kv.TEN,N'Chưa xác định') as KhuVuc, "+
		"		isnull(v.TEN,N'Chưa xác định') as Vung,isnull(nh.TEN,N'Chưa xác định') as NhanHang,isnull(cl.TEN,N'Chưa xác định') as ChungLoai, "+
		"		a.PK_SEQ as TonKhoId,d.MA as spMa,d.TEN as spTen,tt.TEN as TongThauTen,tt.MA as TongThauMa ,c.MA as nppMa,c.TEN as nppTen,a.ThoiGian,b.TonDau,b.Nhap,b.Ban,b.TonCuoi "+ 
		"	from TonKho a inner join TonKho_NPP b on b.TonKho_FK=a.PK_SEQ "+    
		"		 	inner join nhaphanphoi c  on c.pk_seq=b.npp_fk   "+
		"		 	left join nhaphanphoi tt on tt.pk_seq=c.tongthau_fk "+
		"		  	inner join sanpham d on d.pk_seq=b.sanpham_fk  "+
		"		  	left join nhanhang nh on nh.pk_seq=d.nhanhang_fk  "+
		"		  	left join chungloai cl on cl.pk_seq=d.chungloai_fk  "+
		"		  	inner join donvikinhdoanh dvkd on dvkd.pk_seq=d.dvkd_fk  "+
		"		  	inner join nhapp_kbh e on e.npp_fk=c.pk_seq and e.kbh_fk=100052 "+ 
		"		  	inner join kenhbanhang kbh on kbh.pk_seq=e.kbh_fk "+ 
		"		  	left join khuvuc kv on kv.pk_seq=c.khuvuc_fk  "+
		"		  	left join vung v on v.pk_seq=kv.vung_fk    "+ 
		"where a.ThoiGian>=	'"+fromDate+"' and a.ThoiGian<='"+toDate+"'  "; 
		System.out.println("[XuatNhapTonStoreSvl]"+query);
		ResultSet rs ;
		rs=db.get(query);
		
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
				
					cell = cells.getCell("FA" + Integer.toString(i));cell.setValue(rs.getString("KenhBanHang"));
					
					cell = cells.getCell("FB" + Integer.toString(i));cell.setValue(rs.getString("DonViKinhDoanh"));
					
					cell = cells.getCell("FC" + Integer.toString(i));cell.setValue(rs.getString("Vung"));

					cell = cells.getCell("FD" + Integer.toString(i));cell.setValue(rs.getString("KhuVuc"));
					
					cell = cells.getCell("FE" + Integer.toString(i));cell.setValue(rs.getString("NhanHang"));
					
					cell = cells.getCell("FF" + Integer.toString(i));cell.setValue(rs.getString("Chungloai"));
					
					cell = cells.getCell("FG" + Integer.toString(i));cell.setValue(rs.getString("spMa"));
					
					cell = cells.getCell("FH" + Integer.toString(i));cell.setValue(rs.getString("spTen"));
					
					cell = cells.getCell("FI" + Integer.toString(i));cell.setValue(rs.getString("tongthauMa"));
					
					cell = cells.getCell("FJ" + Integer.toString(i));cell.setValue(rs.getString("tongthauTen"));
					
					cell = cells.getCell("FK" + Integer.toString(i));cell.setValue(rs.getString("nppMa"));
					
					cell = cells.getCell("FL" + Integer.toString(i));cell.setValue(rs.getString("nppTen"));
					
					cell = cells.getCell("FM" + Integer.toString(i));cell.setValue(rs.getString("ThoiGian"));
					
					cell = cells.getCell("FN" + Integer.toString(i));cell.setValue(rs.getLong("Tondau"));
					
					cell = cells.getCell("FO" + Integer.toString(i));cell.setValue(rs.getLong("Nhap"));
					
					cell = cells.getCell("FP" + Integer.toString(i));cell.setValue(rs.getLong("Ban"));
					
					cell = cells.getCell("FQ" + Integer.toString(i));cell.setValue(rs.getLong("TonCuoi"));style = cell.getStyle();style.setNumber(2);cell.setStyle(style);
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

				setHidden(workbook, 49);

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

	private void setHidden(Workbook workbook, int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 26; j <= i; j++)
		{
			cells.hideColumn(j);
		}

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}

