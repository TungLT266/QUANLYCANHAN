package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.ILichSuGdKho;
import geso.traphaco.erp.beans.baocao.imp.LichSuGdKho;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBcLichSuGdKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");

	public ErpBcLichSuGdKhoSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ILichSuGdKho bcLSKho;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		bcLSKho = new LichSuGdKho();
		bcLSKho.setUserId(userId);
		bcLSKho.createRs();
		session.setAttribute("bcLSKho", bcLSKho);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCLichSuGdKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream();
		ILichSuGdKho bcLSKho;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String userId = request.getParameter("userId");

		bcLSKho = new LichSuGdKho();
		bcLSKho.setUserId(userId);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		bcLSKho.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		bcLSKho.setDenNgay(denngay);

		String[] loaiSpIds = request.getParameterValues("loaiSpIds");
		String loaiSpId = "";
		if (loaiSpIds != null)
		{
			for (int i = 0; i < loaiSpIds.length; i++)
				loaiSpId += loaiSpIds[i] + ",";
			if (loaiSpId.length() > 0)
				loaiSpId = loaiSpId.substring(0, loaiSpId.length() - 1);
			bcLSKho.setLoaiSanPhamIds(loaiSpId);
		}
		
		String[] malonsanphamIds = request.getParameterValues("malonsanphamIds");
		String malonsanphamId = "";
		if (malonsanphamIds != null)
		{
			for (int i = 0; i < malonsanphamIds.length; i++)
				malonsanphamId += "'"+malonsanphamIds[i] + "',";
			if (malonsanphamId.length() > 0)
				malonsanphamId = malonsanphamId.substring(0, malonsanphamId.length() - 1);
			bcLSKho.setMalonsanphamIds(malonsanphamId);
		}
		
		String khoId = request.getParameter("khoId");
		if (khoId == null)
			khoId = "";
		bcLSKho.setKhoIds(khoId);

		String khoTen = request.getParameter("khoTen");
		if (khoTen == null)
			khoTen = "";
		bcLSKho.setKhoTen(khoTen);

		String[] spIds = request.getParameterValues("spIds");
		String spId = "";
		if (spIds != null)
		{
			for (int i = 0; i < spIds.length; i++)
				spId += spIds[i] + ",";
			if (spId.length() > 0)
				spId = spId.substring(0, spId.length() - 1);
			bcLSKho.setSanPhamIds(spId);
		}

		// Loai Chung Tu
		String[] loaiCtIds = request.getParameterValues("loaiCtIds");
		String loaiCtId = "";
		if (loaiCtIds != null)
		{
			for (int i = 0; i < loaiCtIds.length; i++)
				loaiCtId += loaiCtIds[i] + ",";
			if (loaiCtId.length() > 0)
				loaiCtId = loaiCtId.substring(0, loaiCtId.length() - 1);
			bcLSKho.setLoaiCtIds(loaiCtId);
		}
		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action);
		if (action.equals("search"))
		{
			bcLSKho.createRs();
			session.setAttribute("bcLSKho", bcLSKho);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCLichSuGdKho.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ErpLichSuGdKho.xlsm");

			String query = bcLSKho.getQuery();

			try
			{
				BcTongHop(out, bcLSKho, query);
			} 
			catch (Exception e)
			{
				bcLSKho.createRs();
				session.setAttribute("bcLSKho", bcLSKho);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCLichSuGdKho.jsp";
				bcLSKho.setMsg("Khong the tao bao cao..." + e.getMessage());
				response.sendRedirect(nextJSP);
			}
		}

	}

	private boolean BcTongHop(OutputStream out, ILichSuGdKho bcLSKho, String query) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpLichSuGdKho.xlsm");

		workbook.open(fstream);

		boolean isTrue = BaoCaoLichSuGdKho(workbook, bcLSKho, query);
		if (!isTrue)
		{
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}

	private boolean BaoCaoLichSuGdKho(Workbook workbook, ILichSuGdKho bcLSKho, String sql) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		Style style;
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.RED);// mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		Cell cell = cells.getCell("A1");
		style = cell.getStyle();

		style.setFont(font);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu

		String tieude = "Tên DN: ";
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
		cells.merge(0, 0, 0, 8);

		cell = cells.getCell("A2");
		tieude = "Địa chỉ: ";
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
		cells.merge(1, 0, 0, 8);

		cell = cells.getCell("A3");
		tieude = "MST: 0 3 1 0 7 7 6 0 7 1";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(2, 0, 0, 8);

		if (bcLSKho.getKhoIds().trim().length() > 0)
		{
			cell = cells.getCell("A4");
			tieude = "Kho: " + bcLSKho.getKhoTen();
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
			cells.merge(2, 0, 0, 8);
		}

		cells.setRowHeight(4, 30.0f);
		cell = cells.getCell("A5");
		tieude = "BÁO CÁO LỊCH SỬ GIAO DỊCH KHO";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
		cells.merge(4, 3, 0, 10);

		cell = cells.getCell("A6");
		tieude = "Từ ngày " + bcLSKho.getTuNgay() + " đến ngày " + bcLSKho.getDenNgay();
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
		cells.merge(5, 4, 0, 10);

		cells.setRowHeight(8, 30.0f);

		cell = cells.getCell("A9");
		tieude = "STT";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		// cells.merge(7, 1, 2, 2);

		cell = cells.getCell("B9");
		tieude = "Số chứng từ";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		// cells.merge(7, 1, 2, 2);

		cell = cells.getCell("C9");
		tieude = "Loại chứng từ";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		// cells.merge(7, 3, 2, 6);

		cell = cells.getCell("D9");
		tieude = "Ngày chốt";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		// cells.merge(7, 9, 2, 2);

		cell = cells.getCell("E9");
		tieude = "Mã sản phẩm ";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		// cells.merge(7, 11, 0, 3);

		cell = cells.getCell("F9");
		tieude = "Tên sản phẩm";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		// cells.merge(7, 6, 0, 3);

		cell = cells.getCell("G9");
		tieude = "Mã đối tượng";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		cell = cells.getCell("H9");
		tieude = "Tên đối tượng";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		// cells.merge(7, 17, 0, 3);

		cell = cells.getCell("I9");
		tieude = "Số lượng";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		cell = cells.getCell("J9");
		tieude = "Kho";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		cell = cells.getCell("K9");
		tieude = "Người thực hiện";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		cell = cells.getCell("L9");
		tieude = "Trạng thái";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		// create data
		dbutils db = new dbutils();

		// System.out.println("Tong hop NXT: " + sql);
		ResultSet rs = db.get(sql);
		int index = 10;
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					String stt = Integer.toString(index - 9);
					String soCt = rs.getString("SoCT");
					String loaiCt = rs.getString("loaiCt");
					String ngayChot = rs.getString("ngayChot");
					String maSp = rs.getString("maSp");
					String tenSp = rs.getString("TenSp");
					String maDt = rs.getString("maDt");
					String tenDt = rs.getString("tenDt");
					double soLuong = rs.getDouble("soLuong");
					String kho = rs.getString("Kho");
					String nguoiTao = rs.getString("nguoiTao");
					String trangThai = rs.getString("trangThai");

					cell = cells.getCell("A" + Integer.toString(index));
					cell.setValue(stt);
					cell = cells.getCell("B" + Integer.toString(index));
					cell.setValue(soCt);
					cell = cells.getCell("C" + Integer.toString(index));
					cell.setValue(loaiCt);
					cell = cells.getCell("D" + Integer.toString(index));
					cell.setValue(ngayChot);
					cell = cells.getCell("E" + Integer.toString(index));
					cell.setValue(maSp);
					cell = cells.getCell("F" + Integer.toString(index));
					cell.setValue(tenSp);
					cell = cells.getCell("G" + Integer.toString(index));
					cell.setValue(maDt);
					cell = cells.getCell("H" + Integer.toString(index));
					cell.setValue(tenDt);
					cell = cells.getCell("I" + Integer.toString(index));
					cell.setValue(formatter.format(soLuong));
					cell = cells.getCell("J" + Integer.toString(index));
					cell.setValue(kho);
					cell = cells.getCell("K" + Integer.toString(index));
					cell.setValue(nguoiTao);
					cell = cells.getCell("L" + Integer.toString(index));
					cell.setValue(trangThai);
					index++;
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (index == 10)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
					
				}

			} catch (SQLException e)
			{
				bcLSKho.setMsg("Khong the tao bao cao");
				System.out.println("Exception2: " + e.getMessage());
				return false;
			}
		} else
		{
			throw new Exception("Khong co bao cao trong thoi gian nay...");
		}
		return true;
	}

	public String getDate(String date, int songay)
	{
		String[] arr = date.split("-");
		if (arr[2].equals("01"))
			return date;

		Calendar lich = Calendar.getInstance();
		lich.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
		lich.add(Calendar.DATE, songay);

		String thang = Integer.toString(lich.get(Calendar.MONTH) + 1);
		if ((lich.get(Calendar.MONTH) + 1) < 10)
			thang = "0" + thang;

		String ngay = Integer.toString(lich.get(Calendar.DATE));
		if (lich.get(Calendar.DATE) < 10)
			ngay = "0" + ngay;

		String kq = Integer.toString(lich.get(Calendar.YEAR)) + "-" + thang + "-" + ngay;
		return kq;
	}

	public String getMonth(String date, int sothang)
	{
		String[] arr = date.split("-");

		Calendar lich = Calendar.getInstance();
		lich.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
		lich.add(Calendar.MONTH, sothang);

		String thang = Integer.toString(lich.get(Calendar.MONTH) + 1);
		if ((lich.get(Calendar.MONTH) + 1) < 10)
			thang = "0" + thang;

		String ngay = Integer.toString(lich.get(Calendar.DATE));
		if (lich.get(Calendar.DATE) < 10)
			ngay = "0" + ngay;

		String kq = Integer.toString(lich.get(Calendar.YEAR)) + "-" + thang + "-" + ngay;
		return kq;
	}

}
