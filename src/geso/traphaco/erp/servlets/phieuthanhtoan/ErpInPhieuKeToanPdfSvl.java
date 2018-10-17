package geso.traphaco.erp.servlets.phieuthanhtoan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.phieuthanhtoan.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInPhieuKeToanPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpInPhieuKeToanPdfSvl() {
		super();

	}

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");

    float CONVERT = 28.346457f;  // =1cm
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
			
			try {
				
				
				
				System.out.println("Print");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=DeNghiThanhToan.pdf");
				
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();			
				
				this.inPDF(document, outstream, dmhBean);
				
				document.close();

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("tuBean", dmhBean);
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuThanhToanList.jsp";
				dmhBean.setMsg("Khong the tao bao cao..." + e.getMessage());
				response.sendRedirect(nextJSP);
			}
		}
	
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	

	private void inPDF(Document document, ServletOutputStream outstream, IErpDonmuahang_Giay obj) throws IOException {
		
		try
		{
			dbutils db = new dbutils();
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Font font12_n = new Font(bf, 12, Font.NORMAL);
			Font font12_b = new Font(bf, 12, Font.BOLD);
			Font font12_i = new Font(bf, 12, Font.ITALIC);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_HEADER_WIDTHS = {3.5f * CONVERT, 8.1f * CONVERT, 5.0f * CONVERT };
			float[] TABLE_MIDDLE_WIDTHS = {3.5f * CONVERT, 13.1f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT};			
			
			
			
			
			PdfPCell cell;
			
			
			//==========================header===================================//
			
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = { 33f, 34f, 33};
			Paragraph hd = new Paragraph();
			Chunk chunk = new Chunk();
			
			
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint") + "\\logo.gif");
			img.scalePercent(10);

			
			hd.add(new Chunk(img, 30, 0));
			hd.setAlignment(Element.ALIGN_LEFT);
			
			chunk = new Chunk("\nCông ty cổ phần Traphaco", font12_n);
			hd.add(chunk);
			
			//địa chỉ
			
			String query = "select DIACHI from ERP_CONGTY where PK_SEQ = '100000'";
			ResultSet rsdc = db.get(query);
			String diachi = "";
			if(rsdc!= null){
				try {
					if(rsdc.next()){
						diachi = rsdc.getString("DIACHI");
					}
					rsdc.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			chunk = new Chunk("\nĐịa chỉ: " + diachi, font12_n);
			hd.add(chunk);
			document.add(hd);
			
			
			//====================================================================//
			//========================BODY========================================//
			//============tua de ================================================//
			
			hd = new Paragraph("PHIẾU KẾ TOÁN", new Font(bf, 18, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			//hd.setSpacingAfter(20);
			hd.setSpacingBefore(30);
			document.add(hd);
			
			
			hd = new Paragraph("Ngày      tháng      năm", font12_n);
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			hd = new Paragraph("Số phiếu:                             ", font12_n);
			hd.setAlignment(Element.ALIGN_RIGHT);
			document.add(hd);

			//======================================cái bảng=========================//
			table1 = new PdfPTable(6);
			table1.setSpacingBefore(20);
			table1.setWidthPercentage(100);
			float[] with = {1f*CONVERT, 5f*CONVERT, 2f*CONVERT, 2f*CONVERT,2f*CONVERT, 2f*CONVERT };
			table1.setWidths(with);
			String[] tieude = {"STT","Nội dung","Tài khoản","Mã chi phí", "PS Nợ", "PS có"};
			
			for(int i =0; i< tieude.length; i++){
				cell = new PdfPCell(new Paragraph(tieude[i], font12_b));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				
				cell.setPaddingBottom(7);
				//cell.setBorder(1);
				table1.addCell(cell);
			}
			
			//tổng cộng bên dưới.
			cell = new PdfPCell( new Paragraph("CỘNG", font12_b));
			cell.setColspan(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(cell);
			
			cell = new PdfPCell( new Paragraph("21.0000", font12_b));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(cell);
			
			cell = new PdfPCell( new Paragraph("21.0000", font12_b));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1.addCell(cell);
			
			document.add(table1);
			
			//=================================phần tiền dưới====================
			hd = new Paragraph("         Bằng chữ:", font12_i);
			hd.setSpacingBefore(10);
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);
			
			
			//===============================FOOTER==============================//

			hd = new Paragraph("Ngày "+  getDateNow() +"           ", font12_i);
			hd.setSpacingBefore(50);
			hd.setAlignment(Element.ALIGN_RIGHT);
			document.add(hd);
			
			table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			
			
			cell = new PdfPCell();
			hd = new Paragraph("Kế toán trưởng", font12_b);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Người lập biểu", font12_b);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			for(int i=0; i< 2; i++){
				cell = new PdfPCell();
				hd = new Paragraph("(Ký, họ tên)", font12_n);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);
				table1.addCell(cell);
			}
			document.add(table1);

			
			document.close();
			
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		
	}
	private String getDateNow()
	{
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String d = dateFormat.format(date);
        String[] tach = d.split("/");
        
       
        //Lấy ngày hiện tai.
        //Get current date.
        String ngay = tach[2];
        String thang = tach[1];
        String nam = tach[0];
		return ngay + "  tháng  " + thang + "  Năm  " + nam;
	}
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
}
