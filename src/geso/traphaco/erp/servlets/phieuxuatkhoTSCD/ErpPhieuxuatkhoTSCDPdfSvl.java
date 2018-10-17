package geso.traphaco.erp.servlets.phieuxuatkhoTSCD;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.*;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp.*;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ErpPhieuxuatkhoTSCDPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public ErpPhieuxuatkhoTSCDPdfSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("id");
			
		IErpPhieuxuatkhoTSCD pxkBean = new ErpPhieuxuatkhoTSCD(id);
	    pxkBean.setUserId(userId);
	    
	    String task = request.getParameter("task");
	    if(task.equals("xuatkho"))
	    {
		    pxkBean.initPdf();
		    
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=PhieuXuatKhoTT.pdf");
			
			float CONVERT = 28.346457f;
			float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
			Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
			Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
			
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreatePxk(document, outstream, pxkBean);
	    }

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream, IErpPhieuxuatkhoTSCD pxkBean) throws IOException
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,###.00"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			//total width = 17.5cm
			float[] TABLE_HEADER_WIDTHS = {2.8f * CONVERT, 15.7f * CONVERT};
			float[] TABLE_MIDDLE_WIDTHS = {4.25f * CONVERT, 5.75f * CONVERT, 3.65f * CONVERT, 4.85f * CONVERT};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT, 8.4f * CONVERT, 1.3f * CONVERT, 2.3f * CONVERT, 5.6f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.3f * CONVERT, 7.8f * CONVERT, 7.4f * CONVERT};
			int SANPHAM_NUM_ROWS = 4;
			
			int numSPs = pxkBean.getSpList().size();
			int numPages = (int) Math.ceil((float)numSPs/SANPHAM_NUM_ROWS);
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] splist.size = " + numSPs);
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] num of pages = " + numPages);
			
			int currentSpId = 0;

			double totalTrongLuong = 0;
			double totalTheTich = 0;
			int totalSoluong=0;
			int totalthung=0;
			int totalle=0;
			
			for(int pageIndex = 0; pageIndex < numPages; pageIndex++) {
			
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(2);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				headerTable.getDefaultCell().setBorder(0);
				
				PdfPCell cell;
				
				String[] imageSources = {
					"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
					"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
					"D:\\project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"
				}; 
				Image logoImage = null;
				
				for(int i = 0; i < imageSources.length; i++) {
					try {
						if(logoImage == null) {
							logoImage = Image.getInstance(imageSources[i]);
							System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = " + imageSources[i]);
							break;
						}
					} catch (Exception e) {	}
				}
				if(logoImage != null) {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Load Images Logo Thanh Cong....");
					logoImage.setBorder(Rectangle.NO_BORDER);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					headerTable.addCell(logoImage);
					/*cell = new PdfPCell(logoImage);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(2.8f * CONVERT);*/
				} else {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Khong load duoc hinh anh logo");
					cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(2.0f * CONVERT);
					headerTable.addCell(cell);
				}
				
				
				
				//Header Titles
				PdfPTable headerTitlesTable = new PdfPTable(1);
				headerTitlesTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[1]});
				headerTitlesTable.setWidthPercentage(100);
				headerTitlesTable.getDefaultCell().setBorder(0);
				
				cell = new PdfPCell(new Paragraph("CÔNG TY TNHH BAO BÌ GIẤY NHÔM NEW TOYO", font13bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setPaddingLeft(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				headerTitlesTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD.", font11bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.5f * CONVERT);
				headerTitlesTable.addCell(cell);
				
				//Empty Row
				cell = new PdfPCell(new Paragraph(" ", font10bold));
				cell.setBorder(0);
				cell.setFixedHeight(0.3f * CONVERT);
				headerTitlesTable.addCell(cell);			
				
				PdfPTable headerTitles2Table = new PdfPTable(3);
				float[] headerTitles2Widths = {TABLE_HEADER_WIDTHS[1]*1.8f/3.0f, TABLE_HEADER_WIDTHS[1]*0.6f/3.0f, TABLE_HEADER_WIDTHS[1]*0.6f/3.0f};
				headerTitles2Table.setWidths(headerTitles2Widths);
				headerTitles2Table.setWidthPercentage(100);
				
				PdfPCell cell2;
				cell2 = new PdfPCell(new Paragraph("PHIẾU XUẤT TRẢ TÀI SẢN", font16bold));
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Số phiếu/Ref No", font11));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(": " + pxkBean.getId() + " /XXN", font11));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("(GOODS ISSUED NOTES)", font12bold));	
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày/Date", font11));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(": " + getVnDateTime(pxkBean.getNgayxuatkho(),"/"), font11));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				headerTitles2Table.addCell(cell2);
				
				headerTitlesTable.addCell(headerTitles2Table);			
				headerTable.addCell(headerTitlesTable);
	
				document.add(headerTable);
				
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
				middleTable.setWidthPercentage(100);
				
				cell = new PdfPCell(new Paragraph("Tên người nhận/Receiver:", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(pxkBean.getNguoinhanhang(), font10bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Bộ phận/Department", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": ", font10)); //Bộ phận (ko có)
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Lý do xuất/For reason", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": ", font11bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
			cell = new PdfPCell(new Paragraph("", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" " , font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				document.add(middleTable);
				
				//SANPHAM
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.2f * CONVERT);
				
				String[] spTitles = {"STT\nNo", "Tên tài sản\nName of material ", "ĐVT\nUnit", "Số lượng\nQuantity",  "Ghi chú\nRemarks"};			
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], font12bold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CONVERT);
					sanPhamTable.addCell(cell);			
				}
				
				PdfPCell cells = new PdfPCell();
				
				
				//int maxLocalNumSp = numSPs - SANPHAM_NUM_ROWS >= 0 ? SANPHAM_NUM_ROWS : numSPs - SANPHAM_NUM_ROWS*pageIndex;
				int maxLocalNumSp = numSPs - currentSpId >= SANPHAM_NUM_ROWS ? SANPHAM_NUM_ROWS : numSPs - currentSpId;
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] maxLocalNumSp = " + maxLocalNumSp);
				
				for(int i = 0; i < maxLocalNumSp; i++)
				{
					/*if(i < pxkBean.getSpList().size())
					{*/
						ISanpham sp = (ISanpham)pxkBean.getSpList().get(currentSpId);
						
						//double trongluong = Float.parseFloat(sp.getTrongluong().trim());
						//double khoiluong = Float.parseFloat(sp.getTrongluong().trim()) * Float.parseFloat(sp.getSoluong());
						//double tt = Float.parseFloat( sp.getThetich().trim() ) * Float.parseFloat(sp.getSoluong());
						//double tt = Integer.parseInt(sp.getSoluong());
						
						//String ghichu = currentSpId == 0 ? pxkBean.getGhichu() : " ";
						
						String[] arr = new String[]{
							Integer.toString(currentSpId+1),//STT 
							sp.getDiengiai(), //San pham
							sp.getDVT(), //Don vi tinh
							formatter3.format(Float.parseFloat(sp.getSoluong())), // So luong 
							//formatter3.format((trongluong)),  //Trong luong
							sp.getGhiChu() //Ghi chu
						};
						
						totalSoluong += Float.parseFloat(sp.getSoluong());
						
						/*if(sp.getThung().length() > 0)
							totalthung += Float.parseFloat(sp.getThung());
						if(sp.getLe().length() > 0)
							totalle += Float.parseFloat(sp.getLe());*/
						
						/*if(sp.getTrongluong().length() > 0)
							totalTrongLuong += trongluong;*/
						/*if(sp.getThetich().length() > 0)
							totalTheTich += Float.parseFloat(sp.getThetich()) * Float.parseFloat(sp.getSoluong());*/
						
						for(int j = 0; j < spTitles.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], font11));
							if(j == 1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								if( j >= 5)
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(3.0f);
							
							sanPhamTable.addCell(cells);
						}
						currentSpId++;
					/*} else {
						for(int j = 0; j < 6; j++)
						{
							cells = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
							cells.setPadding(3.0f);
							cells.setFixedHeight(0.4f*CONVERT);
							
							sanPhamTable.addCell(cells);
						}
					}*/
				}	
				
				if(pageIndex == numPages-1) {
					//TOTAL
					String[] arr = new String[]{"", "Tổng cộng/Total", "", formatter3.format(totalSoluong),  "" };
					for(int j = 0; j < arr.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font11bold));
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setFixedHeight(0.6f * CONVERT);
						//cells.setPaddingRight(0.5f * CONVERT);
						
						sanPhamTable.addCell(cells);
					}
				}
				
				document.add(sanPhamTable);
				
				//Table Footer			
				PdfPTable tableFooter = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				tableFooter.setWidthPercentage(100);
				tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableFooter.setWidths(TABLE_FOOTER_WIDTHS);
				tableFooter.setSpacingBefore(0.2f * CONVERT);
				
				PdfPCell cell11 = new PdfPCell(new Paragraph("Người nhận hàng", font12));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", font12));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ trưởng đơn vị", font12));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				
				cell11 = new PdfPCell(new Paragraph("(Receive by)", font11));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("(Store Keeper)", font11));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("(Management Board)", font11));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				
				document.add(tableFooter);
				
				//Them trang moi neu con san pham chua in
				if(pageIndex < numPages-1) {
					document.newPage();
				}
			
			}
			
			document.close(); 
			
			
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}


	
	
	private String getSTT(int stt)
	{
		if(stt < 10)
			return "000" + Integer.toString(stt);
		if(stt < 100)
			return "00" + Integer.toString(stt);
		if(stt < 1000)
			return "0" + Integer.toString(stt);
		return Integer.toString(stt);
	}
	
	
	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return "Ngày  " + ngay + "  tháng  " + thang + "  Năm  " + nam;
	}
	
	private String getVnDateTime(String date, String split) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + split + thang + split + nam;
		} else {
			return "";
		}
	}
	
	private String getVnDateTime(String date) {
		return getVnDateTime(date, "-");
	}

	
}
