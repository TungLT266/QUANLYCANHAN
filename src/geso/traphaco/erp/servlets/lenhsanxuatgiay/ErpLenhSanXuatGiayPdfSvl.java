package geso.traphaco.erp.servlets.lenhsanxuatgiay;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
@WebServlet("/ErpTamUngPdfSvl")
public class ErpLenhSanXuatGiayPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpLenhSanXuatGiayPdfSvl() {
		super();

	}

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			
			
			Utility util = new Utility();
			String querystring = request.getQueryString();
			String ddhId = util.getId(querystring);
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			IErpLenhsanxuat obj =new ErpLenhsanxuat(ddhId);
			
			
			/*response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition","attachment; filename=DeNghiTamUng.xlsm");
			OutputStream out = response.getOutputStream();*/
			String nextJSP = "";
			try {
				
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=PhieuDeNghiTamUngTT.pdf");
				
				float CONVERT = 28.346457f;
				float PAGE_LEFT = 2.0f*CONVERT, PAGE_RIGHT = 2.0f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
				//Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
				Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
				
				ServletOutputStream outstream = response.getOutputStream();
				
				this.inPDF(document, outstream, obj);

			} catch (Exception e) {
				session.setAttribute("tuBean", obj);
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTamUngUpdate.jsp";
				obj.setMsg("Khong the tao bao cao..." + e.getMessage());
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void inPDF(Document document, ServletOutputStream outstream, IErpLenhsanxuat obj) throws IOException {
		
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
					
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9bold = new Font(bf, 9, Font.BOLD);
				Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
					
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_HEADER_WIDTHS = {3.5f * CONVERT, 8.1f * CONVERT, 5.0f * CONVERT };
			float[] TABLE_MIDDLE_WIDTHS = {3.5f * CONVERT, 13.1f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT};			
			
			PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			headerTable.setWidths(TABLE_HEADER_WIDTHS);
			headerTable.setWidthPercentage(100);
			
			PdfPCell cell;
			
			//header left
			PdfPTable headerLeftTable = new PdfPTable(1);
			headerLeftTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[0]});
			headerLeftTable.setWidthPercentage(100);
			
			String[] imageSources = {
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
				"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
				"D:\\project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png",
				"D:\\projects\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"
			}; 
			Image logoImage = null;
			
			for(int i = 0; i < imageSources.length; i++) {
				try {
					if(logoImage == null) {
						logoImage = Image.getInstance(imageSources[i]);
						
						break;
					}
				} catch (Exception e) {	}
			}
			if(logoImage != null) {
				
				logoImage.setBorder(0);
				logoImage.setAlignment(Element.ALIGN_CENTER);
				logoImage.scaleToFit(2.0f * CONVERT, 2.0f * CONVERT);
				logoImage.setAbsolutePosition(2.8f * CONVERT, PageSize.A4.getHeight() - 3.2f*CONVERT);
				document.add(logoImage);
			} else {
			}
			
			cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(2.5f * CONVERT);
			headerLeftTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("MPP: ", font9));
			cell.setBorderWidthTop(1);
			cell.setBorderWidthBottom(0);
			cell.setBorderWidthLeft(0);
			cell.setBorderWidthRight(0);
			cell.setFixedHeight(0.5f * CONVERT);
			headerLeftTable.addCell(cell);

			
			headerTable.addCell(headerLeftTable);
			
			//Header middle: PHIẾU ĐỀ NGHỊ TẠM ỨNG
			cell = new PdfPCell(new Paragraph("LỆNH SẢN XUẤT PRODUCTION ORDER", font15bold));
			cell.setFixedHeight(3.0f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerTable.addCell(cell);
			
			//Header right: Table
			PdfPTable headerRightTable = new PdfPTable(2);
			headerRightTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[2]*0.4f, TABLE_HEADER_WIDTHS[2]*0.6f});
			headerRightTable.setWidthPercentage(100);
			
			cell = new PdfPCell(new Paragraph("Loại", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": Biểu mẫu", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Mã số", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": BM-KTTC-005", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Soát xét", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": 01", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Điều chỉnh", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": 01", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Trang", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": 1/1", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			headerTable.addCell(headerRightTable);
			
			document.add(headerTable);
			
			
			//Số
			Paragraph par = new Paragraph("Số: " + obj.getId(), font9);
			par.setAlignment(Element.ALIGN_CENTER);
			par.setSpacingBefore(0.5f * CONVERT);
			par.setSpacingAfter(0.2f * CONVERT);
			document.add(par);
			
			//Middle Table
			PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
			middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
			middleTable.setWidthPercentage(100);
			
			cell = new PdfPCell(new Paragraph("Người yêu cầu", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " , font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Bộ phận", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": ", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số tiền đề nghị", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
		
	
			
			//System.out.println(lsotien + "");
			cell = new PdfPCell(new Paragraph("Bằng chữ", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
		

			
			cell = new PdfPCell(new Paragraph("Lý do tạm ứng", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
		
			cell = new PdfPCell(new Paragraph("Thời hạn thanh toán", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
		
			
			
			//TABLE FOOTER
			PdfPTable tableFooter = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(TABLE_FOOTER_WIDTHS);
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("NGƯỜI ĐỀ NGHỊ", font9bold));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell12 = new PdfPCell(new Paragraph("KẾ TOÁN TRƯỞNG", font9bold));
			cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell13 = new PdfPCell(new Paragraph("T.BỘ PHẬN", font9bold));
			cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell14 = new PdfPCell(new Paragraph("BAN TỔNG GIÁM ĐỐC", font9bold));
			cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			
			document.add(tableFooter);
			
			
			document.close();
			
			
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		
	}
}
