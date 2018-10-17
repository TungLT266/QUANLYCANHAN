package geso.traphaco.center.servlets.donmuahang;

import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
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

@WebServlet("/DonMuaHangExcel")
public class DonMuaHangExcel extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DonMuaHangExcel()
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
			

			Utility util = new Utility();

			String querystring = request.getQueryString();
			if (querystring != null)
			{
				userId = util.getUserId(querystring);

				if (userId.length() == 0)
					userId = util.antiSQLInspection(request.getParameter("userId"));

				if (querystring.indexOf("excel") > 0)
				{
					IERP_DonDatHang ddhBean = new ERP_DonDatHang();
					String id = util.antiSQLInspection(request.getParameter("excel"));
					ddhBean.setId(id);
					ddhBean.setUserTen(userTen);
					ddhBean.initExcel();
					request.setCharacterEncoding("utf-8");
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=DonDatHang_"+id +"_"+ util.replaceAEIOU(ddhBean.getNppTen()) + ".xlsm");
					
					System.out.println("[NppTen]"+ddhBean.getNppTen());
					OutputStream out = response.getOutputStream();

					String query = setQuery(request,id );

					try
					{
						if (!CreatePivotTable(out, ddhBean, query))
						{
							response.setContentType("text/html");
							PrintWriter writer = new PrintWriter(out);
							writer.print("Xin loi khong co bao cao trong thoi gian nay");
							writer.close();
						}
					} catch (Exception e)
					{
						e.printStackTrace();
						ddhBean.setMessage("Khong the tao bao cao " + e.getMessage());
					}
					
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	private String setQuery(HttpServletRequest request,String ddhId)
	{
		String query = 
		"	SELECT ISNULL(THONGTIN,'') AS THONGTIN, ISNULL(ND.TEN,'') AS NGUOIDUYET ,  \n"+ 
		"		ISNULL( DDH.DATEDUYET,'')  AS DATEDUYET, DDH.PK_SEQ AS MADDH,ISNULL(DDH.SOID,'NA') AS SOID, \n"+      
		"		DDH.NGAYDAT AS NGAYDAT,ISNULL(DDH.NGAYDENGHIGH,'') AS NGAYDENGHIGIAOHANG,      \n"+
		"	CASE DDH.TRANGTHAI  \n"+
		"		WHEN '0' THEN N'NPP ĐẶT HÀNG' \n"+   
		"		WHEN '1' THEN N'CHỜ TT XỬ LÝ'   \n"+
		"		WHEN '2' THEN N'TT DUYỆT'   \n"+
		"		WHEN '3' THEN N'ERP DUYỆT'   \n"+
		"		WHEN '4' THEN N'ĐÃ XUẤT HĐ'    \n"+
		"		WHEN '6' THEN N'ĐÃ HỦY ĐĐH'    \n"+
		"		WHEN '7' THEN N'NPP HỦY HĐ' \n"+
		"	END AS TRANGTHAI,NPP.MA AS MANPP, NPP.TEN AS TENNPP, KV.TEN AS VUNG,V.TEN   AS MIEN , GSBH.TEN AS TENGSBH,SP.MA AS MASP, \n"+  
		"		SP.TEN AS TENSP,DVDL.DONVI ,ISNULL( (SELECT SUM(SOLUONG) AS THUCXUAT  FROM NHAPHANG_SP NHSP INNER JOIN NHAPHANG NH ON NH.PK_SEQ=NHSP.NHAPHANG_FK   WHERE NH.TRANGTHAI!=2 AND NH.SOID=DDH.SOID AND SANPHAM_FK=SP.MA) ,0) AS THUCXUAT, ISNULL( DH_SP.SOLUONG,0) AS SLDAT, \n"+ 
		"		ISNULL(DH_SP.SOLUONGDUYET,0) AS SLDUYET,(DH_SP.DONGIA*DH_SP.SOLUONGDUYET) AS TONGTIEN,(DH_SP.DONGIA* ISNULL(DH_SP.SOLUONG,0)) AS TONGTIENDAT , \n"+
		"		QC.SOLUONG1 AS QC1,QC.SOLUONG2 AS QC2 ,ISNULL(DDH.SOID,0) AS SODONDATHANG \n"+
		"	FROM \n"+     
		"	( \n"+  
		"		SELECT * FROM \n"+  
		"		DONDATHANG DDH \n"+    
		"		WHERE DDH.PK_SEQ='"+ddhId+"' \n"+    
		"	)DDH   \n"+
		"	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DDH.NPP_FK \n"+   
		"	LEFT JOIN NHANVIEN ND ON ND.PK_SEQ=DDH.NGUOIDUYET \n"+     
		"	LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK     \n"+
		"	LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK    \n"+
		"	INNER JOIN DONDATHANG_SP DH_SP ON DH_SP.DONDATHANG_FK = DDH.PK_SEQ \n"+    
		"	INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=DH_SP.dvdl_duyet_fk \n"+  
		"	INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK \n"+    
		"	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DDH.GSBH_FK     \n"+
		"	INNER JOIN QUYCACH QC ON QC.SANPHAM_FK = DH_SP.SANPHAM_FK AND QC.DVDL2_FK=100018 \n"+     
		"	AND (DH_SP.SOLUONG > 0 OR ISNULL(DH_SP.SOLUONGDUYET,0) > 0  ) \n"+   
		"	WHERE 1=1 " ;	
		System.out.println("BC Don Dat Hang NPP : " + query);
		return query;
	}

	private boolean CreatePivotTable(OutputStream out, IERP_DonDatHang obj, String query) throws Exception
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DonDatHang.xlsm");
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

	private void CreateHeader(Workbook workbook, IERP_DonDatHang obj)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 16, "Báo Cáo Đơn Đặt Hàng Nhà Phân Phối "+obj.getNppTen());
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày đặt hàng: " + obj.getNgaygiaodich() +"");
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Người tạo : " + obj.getUserten());

		cell = cells.getCell("AA1");
		cell.setValue("Ma DDH");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");
		cell.setValue("SO Id");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");
		cell.setValue("Ngay Dat");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");
		cell.setValue("Ngay De Nghi Giao Hang");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");
		cell.setValue("Trang Thai");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");
		cell.setValue("Ten NPP");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");
		cell.setValue("Vung");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");
		cell.setValue("Mien");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");
		cell.setValue("Ten GSBH");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");
		cell.setValue("Ma san pham");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");
		cell.setValue("Ten san pham");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");
		cell.setValue("Don Vi");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");
		cell.setValue("So luong dat(le)");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");
		cell.setValue("So luong dat(Thung)");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");
		cell.setValue("So luong duyet(le)");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");
		cell.setValue("So luong duyet(Thung)");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");
		cell.setValue("Tong tien");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1");
		cell.setValue("NguoiDuyet");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1");
		cell.setValue("NgayGioDuyet");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1");
		cell.setValue("TongTienDat");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AU1");
		cell.setValue("Ma NPP");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AV1");
		cell.setValue("ThongTinDonHang");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AW1");
		cell.setValue("ThucXuat(le)");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AX1");
		cell.setValue("ThucXuat(Thung)");
		ReportAPI.setCellHeader(cell);
	}

	private boolean FillData(Workbook workbook, String query, IERP_DonDatHang obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();

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

					int qc1 = rs.getInt("qc1") == 0 ? 1 : rs.getInt("qc1");
					int qc2 = rs.getInt("qc2") == 0 ? 1 : rs.getInt("qc2");
					double soluong = rs.getDouble("sldat");
					double slduyet_thg = rs.getDouble("slduyet") / qc1 / qc2;
					double thucxuat_thg = rs.getDouble("thucxuat") / qc1 / qc2;

					double sldat_thg = soluong / qc1 / qc2;

					cell = cells.getCell("AA" + String.valueOf(index));
					cell.setValue(rs.getString("MaDDH"));
					cell = cells.getCell("AB" + String.valueOf(index));
					cell.setValue(rs.getString("SOId"));
					cell = cells.getCell("AC" + String.valueOf(index));
					cell.setValue(rs.getString("NgayDat"));
					cell = cells.getCell("AD" + String.valueOf(index));
					cell.setValue(rs.getString("NgayDeNghiGiaoHang"));
					cell = cells.getCell("AE" + String.valueOf(index));
					cell.setValue(rs.getString("TrangThai"));
					cell = cells.getCell("AF" + String.valueOf(index));
					cell.setValue(rs.getString("TenNPP"));
					cell = cells.getCell("AG" + String.valueOf(index));
					cell.setValue(rs.getString("Vung"));
					cell = cells.getCell("AH" + String.valueOf(index));
					cell.setValue(rs.getString("Mien"));

					cell = cells.getCell("AI" + String.valueOf(index));
					cell.setValue(rs.getString("TenGSBH"));
					cell = cells.getCell("AJ" + String.valueOf(index));
					cell.setValue(rs.getString("MaSP"));
					cell = cells.getCell("AK" + String.valueOf(index));
					cell.setValue(rs.getString("TenSP"));
					cell = cells.getCell("AL" + String.valueOf(index));
					cell.setValue(rs.getString("DonVi"));

					cell = cells.getCell("AM" + String.valueOf(index));
					cell.setValue(rs.getFloat("SlDat"));
					cell = cells.getCell("AN" + String.valueOf(index));
					cell.setValue(sldat_thg);

					cell = cells.getCell("AO" + String.valueOf(index));
					cell.setValue(rs.getFloat("SlDuyet"));
					cell = cells.getCell("AP" + String.valueOf(index));
					cell.setValue(slduyet_thg);

					cell = cells.getCell("AQ" + String.valueOf(index));
					cell.setValue(rs.getDouble("TongTien"));
					cell = cells.getCell("AR" + String.valueOf(index));
					cell.setValue(rs.getString("nguoiduyet"));
					cell = cells.getCell("AS" + String.valueOf(index));
					cell.setValue(rs.getString("dateduyet"));
					cell = cells.getCell("AT" + String.valueOf(index));
					cell.setValue(rs.getDouble("TongTienDat"));
					cell = cells.getCell("AU" + String.valueOf(index));
					cell.setValue(rs.getString("MaNPP"));
					cell = cells.getCell("AV" + String.valueOf(index));
					cell.setValue(rs.getString("thongtin"));
					cell = cells.getCell("AW" + String.valueOf(index));
					cell.setValue(rs.getDouble("thucxuat"));
					cell = cells.getCell("AX" + String.valueOf(index));
					cell.setValue(thucxuat_thg);

					index++;
				}

				if (rs != null)
					rs.close();

				if (index == 2)
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
