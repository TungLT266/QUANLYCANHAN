package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCHansudungSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpBCHansudungSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new Baocao();
		obj.setUserId(userId);
		obj.createRs();
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHanSuDung.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException
	{
		OutputStream out = response.getOutputStream();
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String userTen = (String) session.getAttribute("userTen");
		String userId = request.getParameter("userId");

		obj = new Baocao();
		obj.setUserId(userId);
		obj.setUserTen(userTen);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String loaisp = request.getParameter("loaisanpham");
		if (loaisp == null)
			loaisp = "";
		obj.setLoaiSanPhamIds(loaisp);

		String khoId = request.getParameter("khoId");
		if (khoId == null)
			khoId = "";
		obj.setKhoIds(khoId);

		String khoTen = request.getParameter("khoTen");
		if (khoTen == null)
			khoTen = "";
		obj.setKhoTen(khoTen);

		String flag = request.getParameter("flag");
		if (flag == null)
			flag = "0";
		obj.setFlag(flag);

		String[] spIds = request.getParameterValues("spIds");
		String spId = "";
		if (spIds != null)
		{
			for (int i = 0; i < spIds.length; i++)
				spId += spIds[i] + ",";
			if (spId.length() > 0)
				spId = spId.substring(0, spId.length() - 1);
			obj.setSanPhamIds(spId);
		}

		String[] clIds = request.getParameterValues("clIds");
		String clId = "";
		if (clIds != null)
		{
			for (int i = 0; i < clIds.length; i++)
				clId += clIds[i] + ",";
			if (clId.length() > 0)
				clId = clId.substring(0, clId.length() - 1);
			obj.setChungloaiIds(clId);
		}

		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action);

		if (action.equals("search"))
		{
			obj.createRs();
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHanSuDung.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=HanSuDungSanPham.xlsm");

			boolean isTrue = false;
			try
			{
				isTrue = CreatePivotTable(out, obj, "");
			} catch (Exception e)
			{
				e.printStackTrace();
				isTrue = false;
			}

			if (!isTrue)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				obj.setMsg("Không thể tạo báo cáo");

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHanSuDung.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHansudung.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUserTen());
		boolean isTrue = CreateStaticData(workbook, obj, condition);
		if (!isTrue)
		{
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName)
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

		String tieude = "BÁO CÁO HẠN SỬ DỤNG";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);

		// tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell("A6");
		cell.setValue("MaSP");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("B6");
		cell.setValue("TenSP");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("C6");
		cell.setValue("DVT");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("D6");
		cell.setValue("SoLo");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("E6");
		cell.setValue("SoLuong");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("F6");
		cell.setValue("NgaySanXuat");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("G6");
		cell.setValue("NgayHetHan");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("H6");
		cell.setValue("HanSuDung");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("I6");
		cell.setValue("NgayNhapKho");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("J6");
		cell.setValue("SoNgayLuuKho");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("K6");
		cell.setValue("SoNgayConLai");
		ReportAPI.setCellHeader(cell);
	}

	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		if (obj.getTuNgay().equals("") || obj.getTuNgay().trim().length() == 0 || obj.getTuNgay() == null)
			obj.setTuNgay(getDateTime());
		String ngayHienTai = getDateTime();

		String query = " SELECT KHOTT_FK, SP.MA AS SPMA, SP.TEN AS SPTEN, DVDL.DONVI, KHOTT_CHITIET.SOLO,  NHAPKHO.NGAYNHAPKHO ," +
			" NHAPKHO.NGAYSANXUAT ,NHAPKHO.NGAYHETHAN, " +
			" DATEDIFF (DD, NHAPKHO.NGAYNHAPKHO, '" +obj.getTuNgay() +"') AS SoNgayLuuKho, " +
			" DATEDIFF (DAY, NHAPKHO.NGAYSANXUAT, NHAPKHO.NGAYHETHAN) AS HANSUDUNG," +
			" DATEDIFF (DAY,'" +ngayHienTai + "',NHAPKHO.NGAYHETHAN) SONGAYCONLAI ," +
			" SUM(DISTINCT KHOTT_CHITIET.SOLUONG) AS SOLUONG " +
			" FROM ERP_KHOTT_SP_CHITIET KHOTT_CHITIET  " +
			" INNER JOIN  ( SELECT B.NGAYSANXUAT,A.NGAYCHOT AS NGAYNHAPKHO, " +
			" B.NGAYHETHAN,  B.SOLO, B.SANPHAM_FK  AS SPID  " +
			" FROM ERP_NHAPKHO A " +
			" INNER JOIN ERP_NHAPKHO_SANPHAM B ON A.PK_SEQ = B.SONHAPKHO_FK " +
			" WHERE A.TRANGTHAI = '1'  ) NHAPKHO  ON KHOTT_CHITIET.SANPHAM_FK= NHAPKHO.SPID and LTRIM(RTRIM(NHAPKHO.solo))=LTRIM(RTRIM(KHOTT_CHITIET.solo)) " +
			" INNER JOIN ERP_SANPHAM SP ON NHAPKHO.SPID = SP.PK_SEQ " +
			" INNER JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ " + " WHERE KHOTT_CHITIET.SOLUONG > 0";
		if (obj.getKhoIds().length() > 0)
			query += " and khott_chitiet.KhoTT_fk = '" + obj.getKhoIds() + "' ";

		if (obj.getChungloaiIds().length() > 0)
			query += " and sp.chungloai_fk in (" + obj.getChungloaiIds() + ") ";

		if (obj.getLoaiSanPhamIds().length() > 0)
			query += " and sp.loaisanpham_fk = '" + obj.getLoaiSanPhamIds() + "' ";

		if (obj.getSanPhamIds().length() > 0)
			query += " and sp.pk_seq in (" + obj.getSanPhamIds() + ") ";

		query += " GROUP BY KHOTT_FK, SP.MA, SP.TEN,DVDL.DONVI, KHOTT_CHITIET.SOLO, "
			+ " NHAPKHO.NGAYHETHAN, NHAPKHO.NGAYSANXUAT,NHAPKHO.NGAYNHAPKHO, SP.HANSUDUNG "
			+ " ORDER BY NHAPKHO.NGAYNHAPKHO DESC ";

		System.out.println("1.Bao cao han su dung: " + query);
		ResultSet rs = db.get(query);

		int i = 7;
		if (rs != null)
		{
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
				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					String maSp = rs.getString("spMa");
					String tenSp = rs.getString("spTen");
					String donvi = rs.getString("donvi");
					String solo = rs.getString("SOLO");
					String ngaysanxuat = rs.getString("ngaysanxuat");
					String ngayhethan = rs.getString("ngayhethan");
					String ngaynhapkho = rs.getString("ngaynhapkho");

					float SoNgayLuuKho = rs.getFloat("SoNgayLuuKho");
					float handudung = rs.getFloat("hansudung");

					float soluong = rs.getFloat("SoLuong");
					float SoNgayConLai = rs.getFloat("SoNgayConLai");

					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(maSp);

					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(tenSp);

					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(donvi);

					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(solo);

					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(soluong);

					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(ngaysanxuat);

					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(ngayhethan);

					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(handudung);

					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(ngaynhapkho);

					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(SoNgayLuuKho);

					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(SoNgayConLai);
					i++;
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		} else
		{
			if (db != null)
				db.shutDown();
			return false;
		}

		if (db != null)
			db.shutDown();
		return true;

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
