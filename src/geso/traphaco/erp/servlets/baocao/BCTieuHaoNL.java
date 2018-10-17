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

public class BCTieuHaoNL extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCTieuHaoNL()
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

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTieuHaoNL.jsp";
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

		String pivot = request.getParameter("pivot");
		if (pivot == null)
			pivot = "0";
		obj.setPivot(pivot);
		String hodaphanbo = request.getParameter("hodaphanbo");
		if (hodaphanbo == null)
			hodaphanbo = "0";
		obj.setHoDaPhanBo(hodaphanbo);
		
		
		String nhamayid = request.getParameter("nhamayid");
		if (nhamayid == null)
			nhamayid = "0";
		obj.setNhamayId(nhamayid);
		
		
		
		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action);

		if (action.equals("search"))
		{
			obj.createRs();
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTieuHaoNL.jsp";
			response.sendRedirect(nextJSP);
		} 
		else 
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTieuHaoNL.xlsm");
			
			boolean isTrue = false;
			try
			{
				isTrue = CreatePivotTable(out, obj, "");
			} 
			catch (Exception e)
			{
				isTrue = false;
				e.printStackTrace();
			}

			if (!isTrue)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				obj.setMsg("Không thể tạo báo cáo");
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTieuHaoNL.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCTieuHaoNL.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUserTen(), obj.getPivot().equals("1"));
		boolean isTrue = CreateStaticData(workbook, obj, condition, obj.getPivot().equals("1"));
		if (!isTrue)
		{
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, boolean pivot)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		String col = pivot ? "D" : "";
		String row = pivot ? "1" : "8";

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

		String tieude = "BÁO CÁO TIÊU HAO NGUYÊN LIỆU";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);

		// tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell(col+"A"+row);
		cell.setValue("NhaMay");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"B"+row);
		cell.setValue("PO");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"C"+row);
		cell.setValue("DienGiai");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"D"+row);
		cell.setValue("CongDoan");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"E"+row);
		cell.setValue("NgayBatDau");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"F"+row);
		cell.setValue("NgayDuKienHT");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"G"+row);
		cell.setValue("LoaiSanPham");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"H"+row);
		cell.setValue("NhanHang");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"I"+row);
		cell.setValue("ChungLoai");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"J"+row);
		cell.setValue("Ma");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"K"+row);
		cell.setValue("Ten");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"L"+row);
		cell.setValue("QuyCach");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"M"+row);
		cell.setValue("Dai");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"N"+row);
		cell.setValue("Rong");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"O"+row);
		cell.setValue("DinhLuong");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"P"+row);
		cell.setValue("SoLuong");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"Q"+row);
		cell.setValue("DonVi");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell(col+"R"+row);
		cell.setValue("TrongLuong");
		ReportAPI.setCellHeader(cell);
	}

	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition, boolean pivot) throws Exception
	{
		String col = pivot ? "D" : "";
		int row = pivot ? 2 : 9;
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		String query = "";
		
		query = " SELECT NHAMAY.TENNHAMAY AS NHAMAY, LSX.PK_SEQ AS PO,ISNULL(LSX.DIENGIAI,'') AS DIENGIAI,CD.DIENGIAI AS CONGDOAN,LSX.NGAYBATDAU,LSX.NGAYDUKIENHT " +
				" ,LSP.TEN AS LOAISANPHAM,NH.TEN AS NHANHANG,CL.TEN AS CHUNGLOAI, " +
				" SP.MA,SP.TEN,SP.QUYCACH,SP.DAI,SP.RONG,SP.DINHLUONG ,THSP.SOLUONG ,DV.DONVI,THSP.SOLUONG* CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE " +
				" ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  AS TRONGLUONG  " +
				" FROM ERP_TIEUHAONGUYENLIEU TH " +
				" INNER JOIN ERP_LENHSANXUAT_TIEUHAO THSP ON THSP.TIEUHAONGUYENLIEU_FK=TH.PK_SEQ  " +
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=THSP.SANPHAM_FK  " +
				" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 " +
				" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK  " +
				" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=TH.LENHSANXUAT_FK  " +
				" INNER JOIN ERP_NHAMAY NHAMAY ON NHAMAY.PK_SEQ=LSX.NHAMAY_FK  " +
				" LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK  " +
				" LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK  " +
				" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK  " +
				" LEFT JOIN ERP_CONGDOANSANXUAT_GIAY CD ON CD.PK_SEQ=CONGDOAN_FK " +
				" WHERE TH.TRANGTHAI = 1 AND LSX.TRANGTHAI <> 8  " ;
				if(obj.getHoPhanBo().endsWith("1")){
					query = query+	" and iskeoho='0'  ";
				}
				
					query = query+	" AND TH.NGAYTIEUHAO >= '" + obj.getTuNgay() + "'   AND TH.NGAYTIEUHAO <= '" + obj.getDenNgay() + "'  " ;
					
					if(obj.getNhamayId().length()>0){
						query = query+	" and LSX.NHAMAY_FK="+obj.getNhamayId();
					}		  
					
				if(obj.getHoPhanBo().endsWith("1")){
					query = query+  " UNION ALL " +
								    " SELECT NHAMAY.TENNHAMAY AS NHAMAY, LSX.PK_SEQ AS PO,ISNULL(LSX.DIENGIAI,'') AS DIENGIAI,N'Công đoạn ghép - NM Nhôm' AS CONGDOAN,LSX.NGAYBATDAU,LSX.NGAYDUKIENHT " + 
								    " ,LSP.TEN AS LOAISANPHAM,NH.TEN AS NHANHANG,CL.TEN AS CHUNGLOAI,  " +
								    " SP.MA,SP.TEN,SP.QUYCACH,SP.DAI,SP.RONG,SP.DINHLUONG ,THSP.SOLUONG ,DV.DONVI,THSP.SOLUONG* CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE " + 
								    " ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  AS TRONGLUONG " +
								    " FROM ERP_TIEUHAOKEOHO_PHANBO THSP " +
								    " INNER JOIN ERP_TIEUHAOKEOHO TH ON TH.PK_SEQ=THSP.TIEUHAOKEOHO_FK " +
								    " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=THSP.LENHSANXUAT_FK " +
								    " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=THSP.SANPHAM_FK " +
								    " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 " +
								    " INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK  " +
								    " INNER JOIN ERP_NHAMAY NHAMAY ON NHAMAY.PK_SEQ=LSX.NHAMAY_FK    " +
								    " LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK   " +
								    " LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK   " +
								    " LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK " +
								    " WHERE  TH.TRANGTHAI=1	 AND TH.NGAYCHUNGTU   >= '" + obj.getTuNgay() + "' AND TH.NGAYCHUNGTU   <= '" + obj.getDenNgay() + "'    " ;
					
					if(obj.getNhamayId().length()>0){
						query = query+	" and LSX.NHAMAY_FK="+obj.getNhamayId();
					}		    
								  
				}  
				
				query=query+ " ORDER BY  DIENGIAI ";
		Utility util = new Utility();
		
		//query += " and THSP.SANPHAM_FK in " + util.quyen_sanpham(obj.getUserId() ) + " ";
		
		System.out.println("BCTieuHaoNL query = " + query);
		ResultSet rs = db.get(query);

		int i = row;
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

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					cell = cells.getCell(col+"A" + Integer.toString(i));
					cell.setValue(rs.getString("NHAMAY"));
					cell = cells.getCell(col+"B" + Integer.toString(i));
					cell.setValue(rs.getString("PO"));
					cell = cells.getCell(col+"C" + Integer.toString(i));
					cell.setValue(rs.getString("DIENGIAI"));
					cell = cells.getCell(col+"D" + Integer.toString(i));
					cell.setValue(rs.getString("CONGDOAN"));
					cell = cells.getCell(col+"E" + Integer.toString(i));
					cell.setValue(rs.getString("NGAYBATDAU"));
					cell = cells.getCell(col+"F" + Integer.toString(i));
					cell.setValue(rs.getString("NGAYDUKIENHT"));
					cell = cells.getCell(col+"G" + Integer.toString(i));
					cell.setValue(rs.getString("LOAISANPHAM"));
					cell = cells.getCell(col+"H" + Integer.toString(i));
					cell.setValue(rs.getString("NHANHANG"));
					cell = cells.getCell(col+"I" + Integer.toString(i));
					cell.setValue(rs.getString("CHUNGLOAI"));
					cell = cells.getCell(col+"J" + Integer.toString(i));
					cell.setValue(rs.getString("MA"));
					cell = cells.getCell(col+"K" + Integer.toString(i));
					cell.setValue(rs.getString("TEN"));
					cell = cells.getCell(col+"L" + Integer.toString(i));
					cell.setValue(rs.getString("QUYCACH"));
					cell = cells.getCell(col+"M" + Integer.toString(i));
					cell.setValue(rs.getString("DAI"));
					cell = cells.getCell(col+"N" + Integer.toString(i));
					cell.setValue(rs.getString("RONG"));
					cell = cells.getCell(col+"O" + Integer.toString(i));
					cell.setValue(rs.getString("DINHLUONG"));
					cell = cells.getCell(col+"P" + Integer.toString(i));
					cell.setValue(rs.getDouble("SOLUONG"));
					cell = cells.getCell(col+"Q" + Integer.toString(i));
					cell.setValue(rs.getString("DONVI"));
					cell = cells.getCell(col+"R" + Integer.toString(i));
					cell.setValue(rs.getDouble("TRONGLUONG"));
					i++;
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (i == row)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}

			}
			catch (Exception e)
			{
				throw new Exception(e.getMessage());
			}
		} 
		else
		{
			if (db != null)
				db.shutDown();
			return false;
		}

		if (db != null)
			db.shutDown();
		return true;

	}

}
