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

public class ErpBCTheodoihangnhapSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpBCTheodoihangnhapSvl()
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

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiHangNhap.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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


		String[] khoId = request.getParameterValues("khoids");
		String chuoikho="";
		if (khoId != null) {
			for(int i =0 ;i <khoId.length ;i++){
				chuoikho =chuoikho+"," + khoId[i];
			}
			if(chuoikho.length() >0){
				chuoikho = chuoikho.substring(1, chuoikho.length());
			}
		}
		obj.setKhoIds(chuoikho);

		String khonhan = request.getParameter("khonhanId");
		obj.setKhonhanIds(khonhan);

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
			obj.setSanPhamIds( spId);
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

		String[] ndnIds = request.getParameterValues("ndnIds");
		String ndnId = "";
		if (ndnIds != null)
		{
			for (int i = 0; i < ndnIds.length; i++)
				ndnId += ndnIds[i] + ",";
			if (ndnId.length() > 0)
				ndnId = ndnId.substring(0, ndnId.length() - 1);
			obj.setNdnhapIds(ndnId);
		}
		
		String[] nccIds = request.getParameterValues("nccIds");
		String nccId = "";
		if (nccIds != null)
		{
			for (int i = 0; i < nccIds.length; i++)
				nccId += nccIds[i] + ",";
			if (nccId.length() > 0)
				nccId = nccId.substring(0, nccId.length() - 1);
			obj.setNccIds(nccId);
		}
		
		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action); 
		
		
		String pivot = request.getParameter("pivot");
		if(pivot == null)
			pivot = "0";
		obj.setPivot(pivot);
		
		if (action.equals("search"))
		{
			obj.createRs();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangNhapKho.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ErpBCTheodoihangnhap.xlsm");
			obj.createRs();
			boolean isTrue = false;
			try
			{
				if( pivot.equals("1") )
					isTrue = CreatePivotTable(out, obj);
				else
					isTrue = CreateTable(out, obj);
			} 
			catch (Exception e)
			{
				obj.setMsg(e.getMessage());
				e.printStackTrace();
				isTrue = false;
			}

			if (isTrue==false)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiHangNhap.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private boolean CreatePivotTable(OutputStream out, IBaocao obj) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCTheodoihangnhap.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUserTen(),obj);
		
		boolean isTrue = CreateStaticData(workbook, obj);
		if (!isTrue)
		{
			obj.setMsg("Lỗi tạo dữ liệu báo cáo");
			return false;
		} 
		
		workbook.save(out);

		fstream.close();
		return true;
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName,IBaocao obj)
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

		String tieude = "BÁO CÁO THEO DÕI HÀNG NHẬP VÀ HOÁ ĐƠN";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày: " + dateFrom);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("B2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày: " + dateTo);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
		
		cell = cells.getCell("FA1");cell.setValue("SoPO");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FB1");cell.setValue("NgayPO");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FC1");cell.setValue("SoNhanHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FD1");cell.setValue("NgayNhanHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FE1");cell.setValue("MaHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FF1");cell.setValue("TenHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FG1");cell.setValue("SoLuongNhap");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FH1");cell.setValue("DonGiaNhap");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FI1");cell.setValue("ThanhTienNhap");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FJ1");cell.setValue("SoLuongHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FK1");cell.setValue("DonGiaHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FL1");cell.setValue("ThanhTienHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FM1");cell.setValue("SoLuongConLai");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FN1");cell.setValue("DonGia");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FO1");cell.setValue("ThanhTien");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FP1");cell.setValue("MaNhaCungCap");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FQ1");cell.setValue("TenNhaCungCap");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FR1");cell.setValue("LoaiSanPham");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FS1");cell.setValue("KhoNhan");ReportAPI.setCellHeader(cell);
					
	}

	private boolean CreateStaticData(Workbook workbook, IBaocao obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		String condition = "";
		if( obj.getTuNgay().trim().length() > 0 )
			condition += " AND NH.NGAYNHAN >= '" + obj.getTuNgay() + "' ";
		if( obj.getDenNgay().trim().length() > 0 )
			condition += " AND NH.NGAYNHAN <= '" + obj.getDenNgay() + "' ";
		if( obj.getLoaiSanPhamIds().trim().length() > 0 )
			condition += " AND SP.LOAISANPHAM_FK in ( " + obj.getLoaiSanPhamIds() + " ) ";
		if( obj.getNccIds().trim().length() > 0 )
			condition += " AND MH.NHACUNGCAP_FK in ( " + obj.getNccIds() + " ) ";
		if(obj.getKhonhanIds().trim().length() > 0){
			condition += " AND HD.KHOBIETTRU_FK in ( " + obj.getKhonhanIds() + " ) ";
		}
		
		String query =   "SELECT DISTINCT MH.SOPO, MH.NGAYMUA, NH.PK_SEQ AS NHID, NH.NGAYNHAN, SP.MA, SP.TEN,  "+
						 "	ISNULL(NH_SP.SOLUONGNHAN, 0) AS SOLUONG_NH, ISNULL(NH_SP.DONGIAVIET, 0) AS DONGIA_NH,  "+
						 "	ISNULL(NH_SP.SOLUONGNHAN * NH_SP.DONGIAVIET, 0) AS THANHTIENVIET_NH, "+
						 "	ISNULL(HD.SOLUONGNHAN, 0) AS SOLUONG_HD, ISNULL(HD.DONGIAVIET, 0) AS DONGIA_HD, ISNULL(HD.THANHTIENHD, 0) AS THANHTIEN_HD, "+
						 "	ISNULL(NH_SP.SOLUONGNHAN, 0) - ISNULL(HD.SOLUONGNHAN, 0) AS SOLUONG_CON, ISNULL(NH_SP.DONGIAVIET, 0) AS DONGIA_CON,  "+
						 "	(ISNULL(NH_SP.SOLUONGNHAN, 0) - ISNULL(HD.SOLUONGNHAN, 0))*ISNULL(NH_SP.DONGIAVIET, 0) AS THANHTIEN_CON, "+
						 "	NCC.MA as MANCC, NCC.TEN as TENNCC, isnull(KHO.TEN) KHOTEN, LSP.TEN as LOAISP "+
						 "FROM ERP_MUAHANG MH "+
						 "INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ "+
						 "INNER JOIN ERP_NHANHANG NH ON NH.MUAHANG_FK = MH.PK_SEQ "+
						 "INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ "+
						 "INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NH_SP.SANPHAM_FK "+
						 "LEFT JOIN ERP_LOAISANPHAM LSP on SP.LOAISANPHAM_FK = LSP.PK_SEQ "+
						 "LEFT JOIN ERP_NHACUNGCAP NCC on MH.NHACUNGCAP_FK = NCC.PK_SEQ "+
						 "LEFT JOIN( "+
						 "	SELECT HD_PN.PK_SEQ AS NHID, HD_PN.MUAHANG_FK AS MHID,  "+
						 "		NH_SP.SANPHAM_FK, NH_SP.SOLUONGNHAN,HD_NCC.KHOBIETTRU_FK, NH_SP.DONGIAVIET, NH_SP.SOLUONGNHAN* NH_SP.DONGIAVIET AS THANHTIENHD "+
						 "	FROM ERP_NHANHANG HD_PN "+
						 "		INNER JOIN ERP_HOADONNCC HD_NCC ON HD_NCC.PK_SEQ = HD_PN.hdNCC_fk 	 "+
						 "		INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = HD_PN.PK_SEQ 	 "+
						 "	WHERE NH_SP.SOLUONGNHAN* NH_SP.DONGIAVIET > 0 AND NH_SP.SANPHAM_FK IS NOT NULL "+
						 ")HD ON HD.NHID = NH.PK_SEQ AND HD.MHID = MH.PK_SEQ AND HD.SANPHAM_FK = NH_SP.SANPHAM_FK AND HD.SOLUONGNHAN = NH_SP.SOLUONGNHAN "+
						 " LEFT JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = HD.KHOBIETTRU_FK "+
						 "WHERE NH_SP.SOLUONGNHAN > 0 "+ condition +
						 "order by MH.NGAYMUA asc ";
		
		System.out.println("::: LAY BC: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		
		if (rs != null)
		{
			try
			{
				Cell cell = null;
				while (rs.next())
				{
					cell = cells.getCell("FA" + Integer.toString(i));cell.setValue(rs.getString("SOPO"));
					cell = cells.getCell("FB" + Integer.toString(i));cell.setValue(rs.getString("NGAYMUA"));
					cell = cells.getCell("FC" + Integer.toString(i));cell.setValue(rs.getString("NHID"));
					cell = cells.getCell("FD" + Integer.toString(i));cell.setValue(rs.getString("NGAYNHAN"));
					cell = cells.getCell("FE" + Integer.toString(i));cell.setValue(rs.getString("MA"));
					cell = cells.getCell("FF" + Integer.toString(i));cell.setValue(rs.getString("TEN") );
					
					cell = cells.getCell("FG" + Integer.toString(i));cell.setValue(rs.getDouble("SOLUONG_NH"));
					cell = cells.getCell("FH" + Integer.toString(i));cell.setValue(rs.getDouble("DONGIA_NH"));
					cell = cells.getCell("FI" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIENVIET_NH"));
					
					cell = cells.getCell("FJ" + Integer.toString(i));cell.setValue(rs.getDouble("SOLUONG_HD"));
					cell = cells.getCell("FK" + Integer.toString(i));cell.setValue(rs.getDouble("DONGIA_HD"));
					cell = cells.getCell("FL" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIEN_HD"));
					
					cell = cells.getCell("FM" + Integer.toString(i));cell.setValue(rs.getDouble("SOLUONG_CON"));
					cell = cells.getCell("FN" + Integer.toString(i));cell.setValue(rs.getDouble("DONGIA_CON"));
					cell = cells.getCell("FO" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIEN_CON"));
					
					cell = cells.getCell("FP" + Integer.toString(i));cell.setValue(rs.getString("MANCC"));
					cell = cells.getCell("FQ" + Integer.toString(i));cell.setValue(rs.getString("TENNCC"));
					cell = cells.getCell("FR" + Integer.toString(i));cell.setValue(rs.getString("LOAISP"));
					cell = cells.getCell("FS" + Integer.toString(i));cell.setValue(rs.getString("KHOTEN"));

					i++;
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (i == 2)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			} 
			catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
			return true;
		} else{
		
			if (db != null){
				db.shutDown();
			}	
			return false;		
		}
	}
	
	

	private boolean CreateTable(OutputStream out, IBaocao obj) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCTheodoihangnhap_khongPIVOT.xlsm");
		System.out.println(getServletContext().getInitParameter("path") + "\\ErpBCTheodoihangnhap_khongPIVOT.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader2(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUserTen(),obj);
		
		boolean isTrue = CreateStaticData2(workbook, obj);
		if (!isTrue)
		{
			obj.setMsg("Lỗi tạo dữ liệu báo cáo");
			return false;
		} 
		
		workbook.save(out);

		fstream.close();
		return true;
	}

	private void CreateStaticHeader2(Workbook workbook, String dateFrom, String dateTo, String UserName,IBaocao obj)
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

		String tieude = "BÁO CÁO THEO DÕI HÀNG NHẬP VÀ HOÁ ĐƠN";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày: " + dateFrom);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("B2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày: " + dateTo);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
		
		cell = cells.getCell("A6");cell.setValue("Số PO");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("B6");cell.setValue("Ngày PO");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("C6");cell.setValue("Số nhận hàng");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("D6");cell.setValue("Kho nhận");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("E6");cell.setValue("Ngày nhận hàng");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("F6");cell.setValue("Số lô");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("G6");cell.setValue("Mã hàng");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("H6");cell.setValue("Tên hàng");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("I6");cell.setValue("Bảng giá NCC");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("J6");cell.setValue("Số lượng nhập");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("K6");cell.setValue("Đơn giá nhập");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("L6");cell.setValue("Đơn vị nhập");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("M6");cell.setValue("Thành tiền nhập");//ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("N6");cell.setValue("Số hóa đơn");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("O6");cell.setValue("Ngày hóa đơn");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("P6");cell.setValue("Số lượng hóa đơn");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("Q6");cell.setValue("Đơn giá hóa đơn");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("R6");cell.setValue("Đơn vị hóa đơn");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("S6");cell.setValue("Thành tiền hóa đơn");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("T6");cell.setValue("Số lượng còn lại");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("U6");cell.setValue("Đơn giá");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("V6");cell.setValue("Thành tiền");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("W6");cell.setValue("Mã nhà cung cấp");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("X6");cell.setValue("Tên nhà cung cấp");//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("Y6");cell.setValue("Loại sản phẩm");//ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("V6");cell.setValue("Kho nhận");//ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("W6");cell.setValue("Ngày nhận hàng");//ReportAPI.setCellHeader(cell);
					
	}

	private boolean CreateStaticData2(Workbook workbook, IBaocao obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		String condition = "";
		String dieukien = "";
		if( obj.getTuNgay().trim().length() > 0 )
		{
			condition += " AND NH.NGAYNHAN >= '" + obj.getTuNgay() + "' ";
			dieukien += " AND NH.NGAYNHAN >= '" + obj.getTuNgay() + "' ";
		}
			
		if( obj.getDenNgay().trim().length() > 0 )
		{
			condition += " AND NH.NGAYNHAN <= '" + obj.getDenNgay() + "' ";
			dieukien += " AND NH.NGAYNHAN <= '" + obj.getDenNgay() + "' ";
		}
			
		if( obj.getLoaiSanPhamIds().trim().length() > 0 )
			condition += " AND SP.LOAISANPHAM_FK in ( " + obj.getLoaiSanPhamIds() + " ) ";
		if( obj.getNccIds().trim().length() > 0 )
			condition += " AND MH.NHACUNGCAP_FK in ( " + obj.getNccIds() + " ) ";
		if(obj.getKhonhanIds().trim().length() > 0){
			condition += " AND NH.KHONHAN_FK in ( " + obj.getKhonhanIds() + " ) ";
			dieukien += " AND NH.KHONHAN_FK in ( " + obj.getKhonhanIds() + " ) ";
		}
		/*String query =   "SELECT DISTINCT hd_dmh.SOLO, MH.SOPO, MH.NGAYMUA, NH.PK_SEQ AS NHID, NH.NGAYNHAN,KHONHAN.TEN as TenKhoNhan, SP.MA, SP.TEN,ISNULL(BGM_SP.GIAMUA,0) BANGGIANCC,  \n"+
						 "	ISNULL(hd_dmh.SOLUONG, 0) AS SOLUONG_NH, ISNULL(HD.DONGIAVIET, 0) AS DONGIA_NH,  \n"+
						 "	ISNULL(hd_dmh.SOLUONG * HD.DONGIAVIET, 0) AS THANHTIENVIET_NH, \n"+
						 "	ISNULL(HD.sohoadon, '') as sohoadon, ISNULL(HD.ngayhoadon, '' ) as ngayhoadon,	\n" +
						 "	ISNULL(hd_dmh.SOLUONG, 0) AS SOLUONG_HD, ISNULL(HD.DONGIAVIET, 0) AS DONGIA_HD, ISNULL(HD.THANHTIENHD, 0) AS THANHTIEN_HD, \n"+
						 "	ISNULL(NH_SP.SOLUONGNHAN, 0) - ISNULL(HD.SOLUONGNHAN, 0) AS SOLUONG_CON, ISNULL(HD.DONGIAVIET, 0) AS DONGIA_CON,  \n"+
						 "	(ISNULL(NH_SP.SOLUONGNHAN, 0) - ISNULL(HD.SOLUONGNHAN, 0))*ISNULL(HD.DONGIAVIET, 0) AS THANHTIEN_CON, \n"+
						 "	NCC.MA as MANCC, NCC.TEN as TENNCC, isnull(KHO.TEN, '') KHOTEN, LSP.TEN as LOAISP \n"+
						 "FROM ERP_NHANHANG NH \n"+
						 "INNER  JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n"+
						 "INNER JOIN ERP_HOADONNCC_DONMUAHANG hd_dmh on NH_SP.MUAHANG_FK = hd_dmh.MUAHANG_FK  and hd_dmh.SANPHAM_FK = NH_SP.SANPHAM_FK  and hd_dmh.HOADONNCC_FK = NH.hdNCC_fk \n"+
						 "INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NH_SP.SANPHAM_FK  \n"+
						 "INNER JOIN  ERP_MUAHANG MH  ON hd_dmh.MUAHANG_FK = MH.PK_SEQ \n"+
						 "INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ  \n"+
						 "LEFT JOIN ERP_LOAISANPHAM LSP on SP.LOAISANPHAM_FK = LSP.PK_SEQ \n"+
						 "LEFT JOIN ERP_NHACUNGCAP NCC on MH.NHACUNGCAP_FK = NCC.PK_SEQ \n"+
						 "LEFT JOIN ERP_BANGGIAMUANCC BGM_NCC ON BGM_NCC.NCC_FK = NCC.PK_SEQ \n"+
						 "LEFT JOIN ERP_BANGGIAMUANCC_SANPHAM BGM_SP ON BGM_SP.BANGGIAMUA_FK = BGM_NCC.PK_SEQ AND SP.PK_SEQ = BGM_SP.SANPHAM_FK \n"+
						 "LEFT JOIN( "+
						 "	SELECT DISTINCT HD_NCC.PK_SEQ as HDNCC_FK, HD_NCC.sohoadon, HD_NCC.ngayhoadon, HD_PN.PK_SEQ AS NHID, NH_SP.MUAHANG_FK AS MHID,  \n"+
						 "		NH_SP.SANPHAM_FK, NH_SP.SOLUONGNHAN,HD_NCC.KHOBIETTRU_FK, HD_DMH.DONGIA as DONGIAVIET, HD_DMH.THANHTIENHD AS THANHTIENHD \n"+
						 "	FROM ERP_NHANHANG HD_PN \n"+
						 "		INNER JOIN ERP_HOADONNCC HD_NCC ON HD_NCC.PK_SEQ = HD_PN.hdNCC_fk 	 \n"+
						 "		INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = HD_PN.PK_SEQ 	\n " +
						 "		INNER JOIN (select HOADONNCC_FK,SANPHAM_FK,MUAHANG_FK, DONGIA, DONGIA * sum(SOLUONG) as THANHTIENHD \n" +
						 "					from ERP_HOADONNCC_DONMUAHANG \n" +
						 "					group by HOADONNCC_FK,SANPHAM_FK,MUAHANG_FK, DONGIA \n" +
						 "					)HD_DMH ON HD_DMH.HOADONNCC_FK = HD_NCC.pk_seq and HD_DMH.SANPHAM_FK = NH_SP.SANPHAM_FK and HD_DMH.MUAHANG_FK= NH_SP.MUAHANG_FK \n"+
						 "	WHERE NH_SP.SOLUONGNHAN * NH_SP.DONGIAVIET > 0 AND NH_SP.SANPHAM_FK IS NOT NULL \n"+
						 ")HD ON HD.NHID = NH.PK_SEQ  AND HD.SANPHAM_FK = NH_SP.SANPHAM_FK AND HD.SOLUONGNHAN = NH_SP.SOLUONGNHAN \n" +
						 //" AND HD.MHID = MH.PK_SEQ \n"+
						 " LEFT JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = HD.KHOBIETTRU_FK \n"+
						 " LEFT JOIN ERP_KHOTT KHONHAN ON KHONHAN.PK_SEQ = NH.KHONHAN_Fk \n"+
						 "WHERE NH_SP.SOLUONGNHAN > 0 and NH.TRANGTHAI IN (1,2) "+ condition +
						 "order by MH.NGAYMUA asc ";*/
		String query =  "SELECT NH_CT.SOLO,MH.SOPO, MH.NGAYMUA, NH.PK_SEQ AS NHID, NH.NGAYNHAN,KHONHAN.TEN AS TENKHONHAN, SP.MA, SP.TEN,ISNULL(BGM_SP.GIAMUA,0) BANGGIANCC,  \n"
				+"ISNULL(NH_CT.SOLUONG, 0) AS SOLUONG_NH, ISNULL(NHSP.DONGIAVIET, 0) AS DONGIA_NH,   \n"
				+"ISNULL(NH_CT.SOLUONG * NHSP.DONGIAVIET, 0) AS THANHTIENVIET_NH,  \n"
				+"ISNULL(HD.SOHOADON, '') AS SOHOADON, ISNULL(HD.NGAYHOADON, '' ) AS NGAYHOADON,	 \n"
				+"ISNULL(HD.SOLUONG, 0) AS SOLUONG_HD, ISNULL(HD.DONGIAVIET, ISNULL(HD.DONGIA,0)) AS DONGIA_HD, ISNULL(HD.THANHTIENVIET, 0) AS THANHTIEN_HD,  \n"
				+"CONVERT(NUMERIC(18,3),ISNULL(HD.SOLUONG, 0)) -CONVERT(NUMERIC(18,3),ROUND(ISNULL(NH_CT.SOLUONG*ISNULL(TILEQUYDOI_DOLUONG,1), 0),3))  AS SOLUONG_CON, ISNULL(HD.DONGIAVIET, ISNULL(HD.DONGIA,0)) AS DONGIA_CON,   \n"
				+"(CONVERT(NUMERIC(18,3),ISNULL(HD.SOLUONG, 0))-CONVERT(NUMERIC(18,3),ROUND(ISNULL(NH_CT.SOLUONG*ISNULL(TILEQUYDOI_DOLUONG,1), 0),3)) ) *ISNULL(HD.DONGIAVIET,  ISNULL(HD.DONGIA,0)) AS THANHTIEN_CON,  \n"
				+"NCC.MA as MANCC, NCC.TEN as TENNCC, isnull(KHO.TEN, '') KHOTEN, LSP.TEN as LOAISP ,ISNULL(DVNH.DIENGIAI,'') AS DONVINHANHANG,ISNULL(DVHD.DIENGIAI,'') AS DONVIHOADON   \n"
				+"FROM   ERP_NHANHANG NH   \n"
				+"INNER  JOIN  \n"
				+"( \n"
				+"	SELECT NHANHANG_FK, SANPHAM_FK,MUAHANG_FK ,CASE WHEN ISNULL(GIATHEOLO,0) =0 THEN ISNULL(GIACHAYKT,0) ELSE ISNULL(GIATHEOLO,0) END GIATHEOLO,SOLO,SUM(SOLUONG) AS SOLUONG \n"
				+"	FROM ERP_NHANHANG_SP_CHITIET  WHERE EXISTS (SELECT PK_SEQ FROM ERP_NHANHANG NH WHERE NH.PK_SEQ = NHANHANG_FK AND  NH.TRANGTHAI IN (1,2) "+ dieukien +" ) \n"
				+"	GROUP BY NHANHANG_FK, SANPHAM_FK,MUAHANG_FK ,ISNULL(GIATHEOLO,0),SOLO,ISNULL(GIACHAYKT,0) \n"
				+") \n"
				+"NH_CT ON NH_CT.NHANHANG_FK = NH.PK_SEQ   \n"
				+"INNER JOIN \n"
				+"( \n"
				+"	SELECT DONVI,NHANHANG_FK, SANPHAM_FK,MUAHANG_FK,DONGIA,KHONHAN,TILEQUYDOI_DOLUONG,DONGIAVIET \n"
				+"	FROM ERP_NHANHANG_SANPHAM WHERE EXISTS (SELECT PK_SEQ FROM ERP_NHANHANG NH WHERE NH.PK_SEQ = NHANHANG_FK AND  NH.TRANGTHAI IN (1,2)  "+ dieukien +" ) \n"
				+") NHSP ON NHSP.NHANHANG_FK = NH_CT.NHANHANG_FK AND NH_CT.SANPHAM_FK = NHSP.SANPHAM_FK \n"
				+"AND NHSP.MUAHANG_FK = NH_CT.MUAHANG_FK AND NH_CT.GIATHEOLO = NHSP.DONGIA \n"
				+"INNER JOIN  ERP_MUAHANG MH  ON NH_CT.MUAHANG_FK = MH.PK_SEQ  \n"
				+"LEFT JOIN ERP_KHOTT KHONHAN ON KHONHAN.PK_SEQ = NH.KHONHAN_Fk  \n"
				+" \n"
				+"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NH_CT.SANPHAM_FK   \n"
				+"LEFT JOIN ERP_LOAISANPHAM LSP on SP.LOAISANPHAM_FK = LSP.PK_SEQ  \n"
				+"LEFT JOIN ERP_NHACUNGCAP NCC on MH.NHACUNGCAP_FK = NCC.PK_SEQ  \n"
				+"LEFT JOIN ERP_BANGGIAMUANCC BGM_NCC ON BGM_NCC.NCC_FK = NCC.PK_SEQ  \n"
				+"LEFT JOIN ERP_BANGGIAMUANCC_SANPHAM BGM_SP ON BGM_SP.BANGGIAMUA_FK = BGM_NCC.PK_SEQ AND SP.PK_SEQ = BGM_SP.SANPHAM_FK  \n"
				+"LEFT JOIN  \n"
				+"( \n"
				// XEM BÊN NHẬN HÀNG THÌ CHO SOLO ='1' KHI NULL HOẶC ''
				+"	SELECT DVT,HD.KHOBIETTRU_FK, ISNULL(LTRIM(RTRIM(A.SOLO)) ,'')  AS SOLO,A.HOADONNCC_FK,A.SANPHAM_FK,SUM(A.SOLUONG) AS SOLUONG, \n"
				+"	A.MUAHANG_FK,HD.SOHOADON,HD.NGAYHOADON,DONGIAVIET,DONGIA,SUM(ISNULL(DONGIAVIET,DONGIA) * A.SOLUONG) AS THANHTIENVIET \n"
				+"	FROM ERP_HOADONNCC  HD \n"
				+"	INNER JOIN ERP_HOADONNCC_DONMUAHANG A ON A.HOADONNCC_FK = HD.PK_SEQ \n"
				+"	GROUP BY  DVT,HD.KHOBIETTRU_FK, LTRIM(RTRIM(A.SOLO)) ,A.HOADONNCC_FK,A.SANPHAM_FK,A.MUAHANG_FK,HD.SOHOADON,HD.NGAYHOADON,DONGIAVIET,DONGIA \n"
				+") HD ON LTRIM(RTRIM(HD.SOLO)) =  LTRIM(RTRIM(NH_CT.SOLO)) AND NH_CT.MUAHANG_FK = HD.MUAHANG_FK AND NH_CT.SANPHAM_FK = HD.SANPHAM_FK \n"
				+" AND (CASE WHEN ISNULL(TILEQUYDOI_DOLUONG,1) = 1 AND NH_CT.GIATHEOLO >0 AND NH_CT.GIATHEOLO <1 THEN NH_CT.GIATHEOLO ELSE ROUND((NH_CT.GIATHEOLO/ISNULL(TILEQUYDOI_DOLUONG,1)),0) END =CASE WHEN ISNULL(TILEQUYDOI_DOLUONG,1) = 1 AND HD.DONGIA >0 AND HD.DONGIA <1 THEN NH_CT.GIATHEOLO ELSE  ROUND(HD.DONGIA,0)  END \n"
				+" OR CASE WHEN ISNULL(TILEQUYDOI_DOLUONG,1) = 1 AND NH_CT.GIATHEOLO >0 AND NH_CT.GIATHEOLO <1 THEN NH_CT.GIATHEOLO ELSE ROUND((NH_CT.GIATHEOLO/ROUND(ISNULL(TILEQUYDOI_DOLUONG,1),3)),3) END =CASE WHEN ISNULL(TILEQUYDOI_DOLUONG,1) = 1 AND HD.DONGIA >0 AND HD.DONGIA <1 THEN NH_CT.GIATHEOLO ELSE  ROUND(HD.DONGIA,0)  END) \n"
				+"AND HD.HOADONNCC_FK = NH.HDNCC_FK  \n"
				+"LEFT JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = HD.KHOBIETTRU_FK \n"
				+ "LEFT JOIN DONVIDOLUONG DVNH ON CONVERT(NVARCHAR,DVNH.PK_SEQ) = NHSP.DONVI  \n"
				+ "LEFT JOIN DONVIDOLUONG DVHD ON CONVERT(NVARCHAR,DVHD.PK_SEQ) = HD.DVT  \n"
				+"WHERE    NH.TRANGTHAI IN (1,2)   AND NH_CT.SOLUONG > 0   "+ condition +""
				+" \n order by MH.NGAYMUA asc  \n";
		
		System.out.println("::: LAY BC: " + query);
		ResultSet rs = db.get(query);
		int i = 7;
		
		if (rs != null)
		{
			try
			{
				Cell cell = null;
				while (rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i));cell.setValue(rs.getString("SOPO"));
					cell = cells.getCell("B" + Integer.toString(i));cell.setValue(rs.getString("NGAYMUA"));
					cell = cells.getCell("C" + Integer.toString(i));cell.setValue(rs.getString("NHID"));
					cell = cells.getCell("D" + Integer.toString(i));cell.setValue(rs.getString("TenKhoNhan"));
					cell = cells.getCell("E" + Integer.toString(i));cell.setValue(rs.getString("NGAYNHAN"));
					cell = cells.getCell("F" + Integer.toString(i));cell.setValue(rs.getString("SOLO"));
					cell = cells.getCell("G" + Integer.toString(i));cell.setValue(rs.getString("MA"));
					cell = cells.getCell("H" + Integer.toString(i));cell.setValue(rs.getString("TEN") );
					
					cell = cells.getCell("I" + Integer.toString(i));cell.setValue(rs.getDouble("BANGGIANCC") );
					cell = cells.getCell("J" + Integer.toString(i));cell.setValue(rs.getDouble("SOLUONG_NH"));
					cell = cells.getCell("K" + Integer.toString(i));cell.setValue(rs.getDouble("DONGIA_NH"));
					cell = cells.getCell("L" + Integer.toString(i));cell.setValue(rs.getString("DONVINHANHANG") );
					cell = cells.getCell("M" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIENVIET_NH"));
					
					cell = cells.getCell("N" + Integer.toString(i));cell.setValue(rs.getString("SOHOADON"));
					cell = cells.getCell("O" + Integer.toString(i));cell.setValue(rs.getString("NGAYHOADON"));
					cell = cells.getCell("P" + Integer.toString(i));cell.setValue(rs.getDouble("SOLUONG_HD"));
					cell = cells.getCell("Q" + Integer.toString(i));cell.setValue(rs.getDouble("DONGIA_HD"));
					cell = cells.getCell("R" + Integer.toString(i));cell.setValue(rs.getString("DONVIHOADON") );
					cell = cells.getCell("S" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIEN_HD"));
					
					cell = cells.getCell("T" + Integer.toString(i));cell.setValue(rs.getDouble("SOLUONG_CON"));
					cell = cells.getCell("U" + Integer.toString(i));cell.setValue(rs.getDouble("DONGIA_CON"));
					cell = cells.getCell("V" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIEN_CON"));
					
					cell = cells.getCell("W" + Integer.toString(i));cell.setValue(rs.getString("MANCC"));
					cell = cells.getCell("X" + Integer.toString(i));cell.setValue(rs.getString("TENNCC"));
					cell = cells.getCell("Y" + Integer.toString(i));cell.setValue(rs.getString("LOAISP"));
					i++;
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (i == 2)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			} 
			catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				db.shutDown();
				throw new Exception(e.getMessage());
			}
			return true;
		} else{
		
			if (db != null){
				db.shutDown();
			}	
			return false;		
		}
	}
	
	
	
}

