package geso.traphaco.erp.servlets.indondathang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
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
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList;
import geso.traphaco.erp.beans.indondathang.IDonDatHang;
import geso.traphaco.erp.beans.indondathang.imp.DonDatHang;

/**
 * Servlet implementation class InDonDatHangNhapKhau
 */
@WebServlet("/InDonDatHang")
public class InDonDatHang extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public InDonDatHang() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		System.out.println("Action = " + action);

		String loai= util.antiSQLInspection(request.getParameter("loai"));

		if(loai.equals("indonhang")){
			Create_PO_PDF(response, request);
		}
		else

			if(loai.equals("inhopdong"))
			{
				Create_PO_PDF_PHULUCHOPDONG(response, request);
			}
			else
				if(loai.equals("inexcell"))	// xuat exell
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=INDONHANG.xlsm");
					try {
						CreateExcel(response, request,out);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}


	}

	private boolean CreateExcel(HttpServletResponse response, HttpServletRequest request, OutputStream out) throws Exception {

		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\INDONHANG.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			dbutils db = new dbutils();
			// lay thong tin don hang

			HttpSession session = request.getSession();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			String congTyId = (String) session.getAttribute("congtyId");
			System.out.println("congTyId" + congTyId);
			String userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			System.out.println("userId" + userId);
			String id = util.getId(querystring);
			if (id.length() == 0)
				id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("id" + id);
			String loai = util.antiSQLInspection(request.getParameter("loai"));
			if (loai == null)
				loai = "";

			IDonDatHang dhNK = new DonDatHang(id);
			dhNK.setCongTyId(congTyId);
			dhNK.setUserId(userId);
			dhNK.setLoai(loai);
			dhNK.init();
			String congTyDatHang = dhNK.getCongTyDatHang();
			String donViDatHang = dhNK.getDonViDatHang();
			String diaChi = dhNK.getDiaChi();
			String dienThoai = dhNK.getDienThoai();
			String fax = dhNK.getFax();
			String maSoThue = dhNK.getMaSoThue();

			String diaChincc = dhNK.getDiachincc();
			String dienThoaincc = dhNK.getDienthoaincc();
			String faxncc = dhNK.getFaxncc();
			String maSoThuencc = dhNK.getMasothuencc();


			String hinhThucThanhToan = dhNK.getHinhThucThanhToan();
			String tienTe = dhNK.getTienTe();
			/*ResultSet thongTinDatHang = dhNK.getThongTinDatHang();*/
			long tongCong = dhNK.getTongCong();
			System.out.println("Tổng cộng đơn hàng đặt :" + tongCong);
			String tongCongBangChu = dhNK.getTongCongBangChu();
			// ___________________________________________

			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
			NumberFormat formatter_1sole = new DecimalFormat("#,###,###.#");
			NumberFormat formatter = new DecimalFormat("#,###,###");


			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			worksheet.setName("Sheet1");
			
			Style style;

			Font font = new Font();
			String query = "";

			// header
			// thong tin khách hang
			cell = cells.getCell("C13");
			ReportAPI.getCellStyle(cell, Color.BLACK, false, 11,  congTyDatHang);

			cell = cells.getCell("C14");
			ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, diaChincc);


			cell = cells.getCell("C15");
			ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, dienThoaincc);

			cell = cells.getCell("F15");
			ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, faxncc);

			cell = cells.getCell("C16");
			ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, maSoThuencc);

			// thong tin ben nguoi dat
			cell = cells.getCell("C17");
			ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, donViDatHang);

			cell = cells.getCell("C18");
			ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, diaChi);


			cell = cells.getCell("C19");
			ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, dienThoai);

			cell = cells.getCell("F19");
			ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, fax);

			cell = cells.getCell("C20");
			ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, maSoThue);
			
			
			// tien te
			cell = cells.getCell("I23");
			getCellStyle(cell, Color.BLACK, true, 11, "Đ.giá \n ("+ tienTe +")");

			cell = cells.getCell("K23");
			getCellStyle(cell, Color.BLACK, true, 11, "Thành tiền \n ("+ tienTe +")");

			// --- đổ dữ liệu ---//
			String sql = "SELECT ROW_NUMBER() OVER(ORDER BY b.ma ASC) AS Row, isnull(b.ma,'') as SPMA,   ISNULL(B.TEN, '') + ' ' +ISNULL(B.QUYCACH, '') AS SPTEN ,'' as ten1, '' as ten2 ,"
				+ " ' ' AS TCKT, ISNULL((SELECT DONVI FROM DONVIDOLUONG WHERE PK_SEQ=A.DONVI), '') AS DONVI,A.SOLUONG,ISNULL(A.DONGIA, '0') AS DONGIA,"
				+ " ISNULL(A.THUEXUAT, '0') AS VAT,"
				+ " ISNULL(A.THANHTIEN, '0') + ISNULL(A.THANHTIEN, '0') * ISNULL(A.THUEXUAT, '0') / 100 AS THANHTIEN,"
				+ " CASE"
				+ " WHEN A.NGAYNHAN = NULL OR A.NGAYNHAN = ''"
				+ " THEN '' "
				+ " ELSE CONVERT(VARCHAR(10),RIGHT(A.NGAYNHAN,2)+ '/' +SUBSTRING(A.NGAYNHAN,6,2) +'/'+LEFT(A.NGAYNHAN,4))"
				+ " END AS THOIGIANGIAOHANG,"
				+ " ISNULL(MH.DIADIEMGIAOHANG,'') AS DIADIEMGIAOHANG"

				+ " FROM ( SELECT AVG (PK_SEQ) AS PK_SEQ,MUAHANG_FK,SANPHAM_FK,CHIPHI_FK,TAISAN_FK,DIENGIAI,"
				+ " SUM (SOLUONG) AS SOLUONG,DONGIA,SUM (SOLUONG_NEW) AS SOLUONG_NEW,DONGIA_NEW,TIENTE_FK,"
				+ " SUM (THANHTIEN) AS THANHTIEN,PHANTRAMTHUE,THUENHAPKHAU,DONGIAVIET,SUM (THANHTIENVIET) AS THANHTIENVIET,"
				+ " DONVI,KHOTT_FK,DUNGSAI,MUANGUYENLIEUDUKIEN_FK,CCDC_FK,TENHD,HOADONNCC_FK,THUEXUAT,VAT,IDMARQUETTE,"
				+ " NGAYNHAN FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + dhNK.getId() + "' GROUP BY MUAHANG_FK,SANPHAM_FK,"
				+ " CHIPHI_FK,TAISAN_FK,DIENGIAI,DONGIA,DONGIA_NEW,TIENTE_FK,PHANTRAMTHUE,THUENHAPKHAU,DONGIAVIET,"
				+ " DONVI,KHOTT_FK,DUNGSAI,MUANGUYENLIEUDUKIEN_FK,CCDC_FK,TENHD,HOADONNCC_FK,THUEXUAT,VAT,"
				+ " IDMARQUETTE,NGAYNHAN) A" + " LEFT JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ"
				+ " LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ"
				+ " LEFT JOIN ERP_NHOMTAISAN NTS ON TSCD.NHOMTAISAN_FK = NTS.PK_SEQ"
				+ " LEFT JOIN ERP_CONGCUDUNGCU CCDC ON A.CCDC_FK = CCDC.PK_SEQ"
				+ " LEFT JOIN MARQUETTE M ON M.SANPHAM_FK = B.PK_SEQ" + " AND M.PK_SEQ = A.IDMARQUETTE"
				+ " LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ"
				+ " LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON NCP.TTCHIPHI_FK = TTCP.PK_SEQ"
				+ " LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ = A.MUAHANG_FK" + " WHERE MUAHANG_FK = '" + dhNK.getId()
				+ "' ORDER BY A.PK_SEQ ASC";


			System.out.println(" noi dung: "+ sql);
			ResultSet thongTinDatHang = db.get(sql);
			int index = 23;
			try {


				/* for(int i=0;i<30;i++){
					   cells.setColumnWidth(i, 20.0f);   
				   }*/

				/*  Cells cells = worksheet.getCells();*/

				/*for(int i=0;i<30;i++){
					   cells.setColumnWidth(i, 20.0f);   
				   }

				    cells.setRowHeight(0, 50.0f);
				    Cell cell = cells.getCell("A1");
				    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);*/


				ResultSetMetaData rsmd = thongTinDatHang.getMetaData();
				int socottrongSql = rsmd.getColumnCount();
				int countRow =  23;

				/*for( int i =1 ; i <=socottrongSql ; i ++  )
				   {
				    cell = cells.getCell(countRow,i-1);

			    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);

			    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));

				   }
				   countRow ++;*/

				Color color_=Color.BLACK;
				while(thongTinDatHang.next())
				{


					for(int i = 1; i <= socottrongSql; i ++)
					{

						
						cell = cells.getCell(countRow,i-1);
						style=cell.getStyle();
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
						{
							
							cell.setStyle(style);
							ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 9,  thongTinDatHang.getDouble(i));

						}
						else
						{
							ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 9, thongTinDatHang.getString(i));

						}
						// gop dong cho ten
						if(i==3){
							cells.merge(countRow, 2, countRow, 4);
							i=i+2;
							//ReportAPI.setBorder_Style_MergerCell(cells, countRow, countRow, 2, 4, BorderLineType.THIN, Color.BLACK, style);
							for (int j = 2; j < 5; j++) {
								cell = cells.getCell(countRow,j);
								setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);	
							}

						}


					}
					++countRow;
				}

				if(thongTinDatHang!=null)thongTinDatHang.close();



				// in dong tong
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1);
					

						if(i==3){
							ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, "Tổng cộng");
						}
						else
							if(i==11)
							{
								ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 11,  tongCong);
							}
							else
							{
								ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, "");
							}
						
						if(i==3){
							cells.merge(countRow, 2, countRow, 4);
							i=i+2;
							for (int j = 2; j < 5; j++) {
								cell = cells.getCell(countRow,j);
								setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
							}

					}
				}
				++countRow;

				++countRow;
				
				// đọc tiền
				cell = cells.getCell("A"+countRow);
				ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, "Tổng số tiền viết bằng chữ: " +  dhNK.getTongCongBangChu());
				++countRow;

				cell = cells.getCell("A"+countRow);
				ReportAPI.getCellStyle(cell,  Color.BLACK, false, 11, "II. Hình thức thanh toán: Theo hợp đồng kinh tế…………………………… Hoặc tiền mặt");
				++countRow;
				++countRow;
				
				cell = cells.getCell("C"+countRow);
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "XÁC NHẬN CỦA BÊN BÁN");

				cell = cells.getCell("I"+countRow);
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "XÁC NHẬN CỦA BÊN MUA");


			} catch (Exception e) {
				e.printStackTrace();
			}


			workbook.save(out);

			fstream.close();
			return true;
		} catch (Exception e) {
			return false;
		}


	}


	
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue){		   
		Style style;
		style = cell.getStyle();		
		com.aspose.cells.Font font = new com.aspose.cells.Font();
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName("Calibri");
		style.setFont(font);
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}



	private void Create_PO_PDF_PHULUCHOPDONG(HttpServletResponse response, HttpServletRequest request)
	throws UnsupportedEncodingException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=Traphaco_DonDatHang.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 1.5f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 1.0f * CONVERT,
		PAGE_BOTTOM = 1.0f * CONVERT; //L R T B
		Document document = new Document(PageSize.A4.rotate(), PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			Create_PHULUCHOPDONG(document, outstream, response, request, db);
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}

	}






	private void Create_PO_PDF(HttpServletResponse response, HttpServletRequest request)
	throws UnsupportedEncodingException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=Traphaco_DonDatHang.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 1.5f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 1.0f * CONVERT,
		PAGE_BOTTOM = 1.0f * CONVERT; //L R T B
		Document document = new Document(PageSize.A4.rotate(), PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			CreatePO_Training(document, outstream, response, request, db);
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}

	}

	private void CreatePO_Training(Document document, ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db) throws UnsupportedEncodingException {

		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String congTyId = (String) session.getAttribute("congtyId");
		System.out.println("congTyId" + congTyId);
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId" + userId);
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id" + id);
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null)
			loai = "";

		IDonDatHang dhNK = new DonDatHang(id);
		dhNK.setCongTyId(congTyId);
		dhNK.setUserId(userId);
		dhNK.setLoai(loai);
		dhNK.init();
		String congTyDatHang = dhNK.getCongTyDatHang();
		String donViDatHang = dhNK.getDonViDatHang();
		String diaChi = dhNK.getDiaChi();
		String dienThoai = dhNK.getDienThoai();
		String fax = dhNK.getFax();
		String maSoThue = dhNK.getMaSoThue();

		String diaChincc = dhNK.getDiachincc();
		String dienThoaincc = dhNK.getDienthoaincc();
		String faxncc = dhNK.getFaxncc();
		String maSoThuencc = dhNK.getMasothuencc();


		String hinhThucThanhToan = dhNK.getHinhThucThanhToan();
		String tienTe = dhNK.getTienTe();
		ResultSet thongTinDatHang = dhNK.getThongTinDatHang();
		long tongCong = dhNK.getTongCong();
		System.out.println("Tổng cộng đơn hàng đặt :" + tongCong);
		String tongCongBangChu = dhNK.getTongCongBangChu();
		// ___________________________________________

		//NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter_1sole = new DecimalFormat("#,###,###.#");
		NumberFormat formatter = new DecimalFormat("#,###,###");

		try {
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font9_normal = new Font(bf,9,Font.NORMAL);
			Font font9_bold = new Font(bf, 9, Font.BOLD);
			Font font9_italic = new Font(bf, 9, Font.ITALIC);


			Font font10_normal = new Font(bf,10,Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font10_italic = new Font(bf, 10, Font.ITALIC);


			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);

			Font font12_normal = new Font(bf,12,Font.NORMAL);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12_italic = new Font(bf, 12, Font.ITALIC);

			Font font14_normal = new Font(bf,14,Font.NORMAL);
			Font font14_bold = new Font(bf, 14, Font.BOLD);
			Font font14_italic = new Font(bf, 14, Font.ITALIC);


			PdfPTable tab_Header = new PdfPTable(1);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_RIGHT);
			float[] crtbl_header = { 7f };
			tab_Header.setWidths(crtbl_header);

			// Header 1
			PdfPCell cell = new PdfPCell(new Paragraph("BM29/09", font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(-0.5f*CONVERT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			String date = "22/07/16";
			cell = new PdfPCell(new Paragraph("BH/SĐ: " + date, font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.setBorder(0);
			tab_Header.addCell(cell);
			document.add(tab_Header);
			// Header 2


			PdfPTable table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(new int[] {8,8, 8});
			table.setSpacingAfter(5.0f);

			PdfPCell cell2;


			// insert logo traphaco
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint") + "\\logo.gif");
			img.scalePercent(10);

			cell2 = new PdfPCell();
			cell2.addElement(new Chunk(img, 80, 0));
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Phrase("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setPaddingTop(0.3f*CONVERT);
			cell2.setBorder(0);
			table.addCell(cell2);


			//--dong 2

			cell2 = new PdfPCell(new Phrase("CÔNG TY CP TRAPHACO", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			/*cell2.setPadding(1.0f);*/
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(" ", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase("Độc lập - Tự do - Hạnh phúc", font10_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			//--dong 3

			cell2 = new PdfPCell(new Phrase("Số: ... ĐĐH/ TRAPHACO - ..../ 20...", font10_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			cell2.setColspan(2);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			cell2.setColspan(2);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase("Hà Nội, ngày        tháng        năm       ", font10_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);



			cell2 = new PdfPCell(new Phrase("ĐƠN ĐẶT HÀNG", new Font(bf, 18, Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setColspan(3);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Phrase("Năm  ......   ", new Font(bf, 12, Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setColspan(3);
			cell2.setBorder(0);
			table.addCell(cell2);

			document.add(table);




			//-- bang thong tin
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);


			cell2 = new PdfPCell();
			cell2.setPaddingLeft(0.5f*CONVERT);
			Paragraph p = new Paragraph();

			Chunk chunk =new Chunk("Kính gửi: ", font12_bold);
			chunk.setUnderline(0.2f, -2f);
			//cell2.addElement(chunk);
			p.add(chunk);

			chunk =new Chunk(congTyDatHang, font12_bold);
			//cell2.addElement(chunk);
			p.add(chunk);

			cell2.addElement(p);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);



			cell2 = new PdfPCell(new Paragraph("Địa chỉ: " + diaChincc,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Số điện thoại: " + dienThoaincc + "                                   Fax: " + faxncc,font12_normal));

			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell( new Paragraph("Mã số thuế: " + maSoThuencc, font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);




			cell2 = new PdfPCell(new Paragraph("Đơn vị đặt hàng: " + donViDatHang, font12_bold));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Paragraph("Địa chỉ: " + diaChi,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Số điện thoại: " + dienThoai + "                                   Fax: " + fax,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell( new Paragraph("Mã số thuế: " + maSoThue, font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("I. Nội dung đặt hàng: ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);


			document.add(table);


			//------------


			float[] crDonHang = { 0.4f * CONVERT,1.0f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.3f * CONVERT, 0.5f * CONVERT, 1.5f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT };

			PdfPTable tab_DonHang = new PdfPTable(crDonHang.length);
			tab_DonHang.setWidthPercentage(100);
			tab_DonHang.setSpacingBefore(0.3f*CONVERT);
			tab_DonHang.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_DonHang.setWidths(crDonHang);
			tab_DonHang.getDefaultCell().setBorder(0);
			tab_DonHang.setSpacingAfter(8.0f);

			String[] spTitles = { "TT", "Mã VT", "Tên hàng, qui cách", "TCKT", "Đ.vị", "S.lượng", "Đ.giá \n (" + tienTe + ")",
					"VAT", "Thành tiền \n (" + tienTe + ")", "Thời gian giao hàng", "Địa điểm nhận hàng" };

			for (int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11_bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab_DonHang.addCell(cell);
			}





			int sott = 1;
			if (thongTinDatHang != null) {
				while (thongTinDatHang.next()) {

					double soluong= Double.parseDouble(   thongTinDatHang.getString("SOLUONG")) ;
					double vat= Double.parseDouble(   thongTinDatHang.getString("VAT") );

					String sl="";
					String vat1="";
					if(soluong%1 >0){
						sl=DinhDang.dinhdangkho(soluong);
					}
					else {
						sl=formatter.format(soluong);
					}
					System.out.println(" soluong: "+ soluong%1 );

					if(vat%1 >0){
						vat1=formatter_1sole.format(vat);
					}
					else {
						vat1=formatter.format(vat);
					}
					System.out.println(" soluong: "+ vat);


					String[] spTitles2 = { sott + "", thongTinDatHang.getString("SPMA"),
							thongTinDatHang.getString("SPTEN") + " " + thongTinDatHang.getString("QUYCACH"),
							thongTinDatHang.getString("TCKT"), thongTinDatHang.getString("DONVI"),
							sl  ,
							String.valueOf(formatter.format(thongTinDatHang.getFloat("DONGIA"))),
							vat1  ,
							String.valueOf(formatter.format(thongTinDatHang.getFloat("THANHTIEN"))),
							thongTinDatHang.getString("THOIGIANGIAOHANG"),
							thongTinDatHang.getString("DIADIEMGIAOHANG") };

					for (int z = 0; z < spTitles2.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles2[z], font10_normal));
						cell.setPadding(3.0f);
						cell.setFixedHeight(0.6f*CONVERT);
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						/*if (z == 4 || z == 5 || z == 7) {
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}*/

						if(z==0 || z==4){
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else if(z==2 ||z==1){
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}



						tab_DonHang.addCell(cell);
					}
					sott++;
				}
			}

			//			String [] tong={"Tổng cộng", formatter.format(tongCong), };

			for (int z = 0; z < spTitles.length; z++) {
				if (z == 2) {
					cell = new PdfPCell(new Paragraph("Tổng cộng", font10_bold));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				} else if (z == 8) {
					cell = new PdfPCell(new Paragraph(formatter.format(tongCong), font10_normal));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab_DonHang.addCell(cell);
				} else {
					cell = new PdfPCell(new Paragraph("", font10_bold));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				}

			}

			document.add(tab_DonHang);


			//------bang tong tien 

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);

			cell = new PdfPCell(new Paragraph("Tổng số tiền viết bằng chữ: " + tongCongBangChu,new Font(bf, 12, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell();
			p=new Paragraph();
			chunk =new Chunk("II. Hình thức thanh toán: ", font12_bold);
			p.add(chunk);

			chunk =new Chunk("Theo hợp đồng kinh tế…………………………… Hoặc tiền mặt", font12_normal);
			p.add(chunk);
			cell.addElement(p);

			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);

			document.add(table);




			//----bang chu ki
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(0.5f*CONVERT);
			table.setWidths(new int[] {12,12});
			table.setWidthPercentage(100);

			cell = new PdfPCell(new Paragraph(" XÁC NHẬN CỦA BÊN BÁN" ,new Font(bf, 12, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("XÁC NHẬN CỦA BÊN MUA",new Font(bf, 12, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);

			document.add(table);





			/*// Chỗ này không hiển thị
			cell = new PdfPCell(new Paragraph("Tổng cộng", font11_bold));
			cell.setColspan(2);
			cell.setPadding(3.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_DonHang.addCell(cell);
			// tinh tong gia tri cho cac cot trong table
			cell = new PdfPCell(new Paragraph(formatter.format(tongCong), font10_bold));
			cell.setPadding(3.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_DonHang.addCell(cell);
			// .....
			document.add(tab_DonHang);

			// tong tien bang chu
			Paragraph para7 = new Paragraph("Tổng số tiền viết bằng chữ: " + tongCongBangChu,new Font(bf, 12, Font.BOLD));
			para7.setSpacingBefore(3);
			para7.setSpacingAfter(5);
			para7.setAlignment(Element.ALIGN_LEFT);
			document.add(para7);


			 * PdfPTable table3 = new PdfPTable(2);
			 * table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * table3.setWidths(new int[]{ 6, 6}); table3.setSpacingAfter(5.0f);
			 * PdfPCell cell4; cell4 = new PdfPCell(new Phrase("",
			 * font11_bold));
			 * cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell4.setColspan(8); cell4.setPadding(1.0f); cell4.setBorder(0);
			 * table3.addCell(cell4);
			 * 
			 * cell4 = new PdfPCell(new Phrase("Tổng tiền viết bằng chữ: ",
			 * font11_bold)); cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * cell4.setBorder(0); table3.addCell(cell4);
			 * 
			 * cell4 = new PdfPCell(new Phrase("Đọc theo nguyên tệ gốc", new
			 * Font(bf, 11, Font.NORMAL)));
			 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * cell4.setBorder(0); table3.addCell(cell4); document.add(table3);

			// ___________________

			Paragraph para6 = new Paragraph("II. Hình thức thanh toán: " + hinhThucThanhToan,
					new Font(bf, 12, Font.BOLD));
			para6.setSpacingBefore(3);
			para6.setSpacingAfter(10);
			para6.setAlignment(Element.ALIGN_LEFT);
			document.add(para6);

			Paragraph pxk = new Paragraph("            XÁC NHẬN CỦA BÊN BÁN" + "                                    "
					+ "TRƯỞNG PHÒNG XNK-CƯVT", new Font(bf, 12, Font.BOLD));
			pxk.setSpacingAfter(15);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			 */


			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
	}



	private void Create_PHULUCHOPDONG(Document document, ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db) throws UnsupportedEncodingException {

		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String congTyId = (String) session.getAttribute("congtyId");
		System.out.println("congTyId" + congTyId);
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId" + userId);
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id" + id);
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null)
			loai = "";

		IDonDatHang dhNK = new DonDatHang(id);
		dhNK.setCongTyId(congTyId);
		dhNK.setUserId(userId);
		dhNK.setLoai(loai);
		dhNK.init();
		String congTyDatHang = dhNK.getCongTyDatHang();
		String donViDatHang = dhNK.getDonViDatHang();
		String diaChi = dhNK.getDiaChi();
		String dienThoai = dhNK.getDienThoai();
		String fax = dhNK.getFax();
		String maSoThue = dhNK.getMaSoThue();
		String nganhang = dhNK.getNganhang();
		String sotaikhoan= dhNK.getSotaikhoan();


		String diaChincc = dhNK.getDiachincc();
		String dienThoaincc = dhNK.getDienthoaincc();
		String faxncc = dhNK.getFaxncc();
		String maSoThuencc = dhNK.getMasothuencc();

		String nganhangncc = dhNK.getNganhangncc();
		String sotaikhoanncc = dhNK.getSotaikhoanncc();


		String hinhThucThanhToan = dhNK.getHinhThucThanhToan();
		String tienTe = dhNK.getTienTe();
		ResultSet thongTinDatHang = dhNK.getThongTinDatHang();
		long tongCong = dhNK.getTongCong();
		System.out.println("Tổng cộng đơn hàng đặt :" + tongCong);
		String tongCongBangChu = dhNK.getTongCongBangChu();
		// ___________________________________________

		NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter_1sole = new DecimalFormat("#,###,###.#");
		NumberFormat formatter = new DecimalFormat("#,###,###");

		try {
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font9_normal = new Font(bf,9,Font.NORMAL);
			Font font9_bold = new Font(bf, 9, Font.BOLD);
			Font font9_italic = new Font(bf, 9, Font.ITALIC);


			Font font10_normal = new Font(bf,10,Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font10_italic = new Font(bf, 10, Font.ITALIC);


			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);

			Font font12_normal = new Font(bf,12,Font.NORMAL);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12_italic = new Font(bf, 12, Font.ITALIC);

			Font font14_normal = new Font(bf,14,Font.NORMAL);
			Font font14_bold = new Font(bf, 14, Font.BOLD);
			Font font14_italic = new Font(bf, 14, Font.ITALIC);


			PdfPTable tab_Header = new PdfPTable(1);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_RIGHT);
			float[] crtbl_header = { 7f };
			tab_Header.setWidths(crtbl_header);

			// Header 1
			PdfPCell cell = new PdfPCell(new Paragraph("BM29/08", font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(-0.5f*CONVERT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			String date = "22/07/16";
			cell = new PdfPCell(new Paragraph("BH/SĐ: " + date, font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.setBorder(0);
			tab_Header.addCell(cell);
			document.add(tab_Header);
			// Header 2


			PdfPTable table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(new int[] {8,8, 8});
			table.setSpacingAfter(5.0f);

			PdfPCell cell2;


			// insert logo traphaco
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint") + "\\logo.gif");
			img.scalePercent(10);

			cell2 = new PdfPCell();
			cell2.addElement(new Chunk(img, 80, 0));
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Phrase("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setPaddingTop(0.3f*CONVERT);
			cell2.setBorder(0);
			table.addCell(cell2);


			//--dong 2

			cell2 = new PdfPCell(new Phrase("CÔNG TY CP TRAPHACO", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			/*cell2.setPadding(1.0f);*/
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(" ", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase("Độc lập - Tự do - Hạnh phúc", font10_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			//--dong 3

			cell2 = new PdfPCell(new Phrase("Số: ... ĐĐH/ TRAPHACO - ..../ 20...", font10_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			cell2.setColspan(2);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			cell2.setColspan(2);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase("Hà Nội, ngày        tháng        năm       ", font10_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);



			cell2 = new PdfPCell(new Phrase("PHỤ LỤC HỢP ĐỒNG SỐ: …...", new Font(bf, 18, Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setColspan(3);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Phrase("Năm  ......   ", new Font(bf, 12, Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setColspan(3);
			cell2.setBorder(0);
			table.addCell(cell2);

			document.add(table);




			//-- bang thong tin
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);



			cell2 = new PdfPCell(new Paragraph("Căn cứ theo hợp đồng kinh tế năm ...    số:             ngày     tháng     năm ...…, giữa: ",font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			//---- mua

			cell2 = new PdfPCell();
			cell2.setPaddingLeft(0.5f*CONVERT);

			Paragraph p = new Paragraph();
			Chunk chunk =new Chunk("Bên mua", font12_bold);
			chunk.setUnderline(0.2f, -2f);
			p.add(chunk);

			chunk =new Chunk( " : " +donViDatHang +"            (BÊN A)", font12_bold);
			p.add(chunk);
			cell2.addElement(p);

			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Paragraph("Đại Diện:"  + "                                      Chức vụ: ",font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Địa chỉ: " + diaChi,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Số điện thoại: " + dienThoai,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Tài khoản: " +sotaikhoan +"    "+  nganhang,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell( new Paragraph("Mã số thuế: " + maSoThue, font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);



			//-------------
			cell2 = new PdfPCell();
			cell2.setPaddingLeft(0.5f*CONVERT);
			p = new Paragraph();
			chunk =new Chunk("Bên bán", font12_bold);
			chunk.setUnderline(0.2f, -2f);
			p.add(chunk);

			chunk =new Chunk( " : " +congTyDatHang +"            (BÊN B)", font12_bold);
			p.add(chunk);

			cell2.addElement(p);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);


			cell2 = new PdfPCell(new Paragraph("Đại Diện:"  + "                                       Chức vụ: ",font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);



			cell2 = new PdfPCell(new Paragraph("Địa chỉ: " + diaChincc,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Số điện thoại: " + dienThoaincc ,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Paragraph("Tài khoản: " +sotaikhoanncc +"    "+  nganhangncc ,font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell( new Paragraph("Mã số thuế: " + maSoThuencc, font12_normal));
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			table.addCell(cell2);






			cell2 = new PdfPCell(new Paragraph("Hai bên thỏa thuận ký kết phụ lục hợp đồng kinh tế với nội dung sau:   Bên B bán cho bên A:", font12_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setPaddingLeft(0.5f*CONVERT);
			cell2.setBorder(0);
			table.addCell(cell2);


			document.add(table);


			//------------


			float[] crDonHang = { 0.4f * CONVERT,1.0f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.3f * CONVERT, 0.5f * CONVERT, 1.5f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT };

			PdfPTable tab_DonHang = new PdfPTable(crDonHang.length);
			tab_DonHang.setWidthPercentage(100);
			tab_DonHang.setSpacingBefore(0.3f*CONVERT);
			tab_DonHang.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_DonHang.setWidths(crDonHang);
			tab_DonHang.getDefaultCell().setBorder(0);
			tab_DonHang.setSpacingAfter(8.0f);

			String[] spTitles = { "TT", "Mã VT", "Tên hàng, qui cách", "TCKT", "Đ.vị", "S.lượng", "Đ.giá \n (" + tienTe + ")",
					"VAT", "Thành tiền \n (" + tienTe + ")", "Thời gian giao hàng", "Địa điểm nhận hàng" };

			for (int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11_bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab_DonHang.addCell(cell);
			}





			int sott = 1;
			if (thongTinDatHang != null) {
				while (thongTinDatHang.next()) {

					double soluong= Double.parseDouble(   thongTinDatHang.getString("SOLUONG")) ;
					double vat= Double.parseDouble(   thongTinDatHang.getString("VAT") );

					String sl="";
					String vat1="";
					if(soluong%1 >0){
						sl=formatter_2sole.format(soluong);
					}
					else {
						sl=formatter.format(soluong);
					}
					System.out.println(" soluong: "+ soluong%1 );

					if(vat%1 >0){
						vat1=formatter_1sole.format(vat);
					}
					else {
						vat1=formatter.format(vat);
					}
					System.out.println(" soluong: "+ vat);


					String[] spTitles2 = { sott + "", thongTinDatHang.getString("SPMA"),
							thongTinDatHang.getString("SPTEN") + " " + thongTinDatHang.getString("QUYCACH"),
							thongTinDatHang.getString("TCKT"), thongTinDatHang.getString("DONVI"),
							sl  ,
							String.valueOf(formatter.format(thongTinDatHang.getFloat("DONGIA"))),
							vat1  ,
							String.valueOf(formatter.format(thongTinDatHang.getFloat("THANHTIEN"))),
							thongTinDatHang.getString("THOIGIANGIAOHANG"),
							thongTinDatHang.getString("DIADIEMGIAOHANG") };

					for (int z = 0; z < spTitles2.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles2[z], font10_normal));
						cell.setPadding(3.0f);
						cell.setFixedHeight(0.6f*CONVERT);
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						/*if (z == 4 || z == 5 || z == 7) {
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}*/

						if(z==0 || z==4){
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else if(z==2 ||z==1){
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}



						tab_DonHang.addCell(cell);
					}
					sott++;
				}
			}

			//			String [] tong={"Tổng cộng", formatter.format(tongCong), };

			for (int z = 0; z < spTitles.length; z++) {
				if (z == 2) {
					cell = new PdfPCell(new Paragraph("Tổng cộng", font10_bold));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				} else if (z == 8) {
					cell = new PdfPCell(new Paragraph(formatter.format(tongCong), font10_normal));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab_DonHang.addCell(cell);
				} else {
					cell = new PdfPCell(new Paragraph("", font10_bold));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				}

			}

			document.add(tab_DonHang);


			//------bang tong tien 

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);

			cell = new PdfPCell(new Paragraph("Tổng số tiền viết bằng chữ: " + tongCongBangChu,new Font(bf, 12, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell();
			p=new Paragraph();
			chunk =new Chunk("Hình thức thanh toán:" , font12_bold);
			p.add(chunk);

			chunk =new Chunk( hinhThucThanhToan +"             "+"Theo hợp đồng kinh tế…………………………… ", font12_normal);
			p.add(chunk);
			cell.addElement(p);

			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);

			document.add(table);




			//----bang chu ki
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(0.5f*CONVERT);
			table.setWidths(new int[] {12,12});
			table.setWidthPercentage(100);

			cell = new PdfPCell(new Paragraph(" ĐẠI DIỆN BÊN A" ,new Font(bf, 12, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("ĐẠI DIỆN BÊN B",new Font(bf, 12, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);

			document.add(table);


			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
	}


	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			com.aspose.cells.Font font2 = new com.aspose.cells.Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		}
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}

}
