package geso.traphaco.erp.servlets.nhapkho.giay;

import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapkho;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

public class ErpPhieunhapkhoPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieunhapkhoPdfSvl() {
        super();
    }
 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	
		//IErpCongTy c = getCongTy();
		
		if (userId.length() == 0) {
	    	userId = request.getParameter("userId");
		}
			   
		String id = request.getParameter("id");
			
		IErpNhapkho pnkBean = new ErpNhapkho(id);
	    pnkBean.setUserId(userId);
	    
	    pnkBean.initPdf();
	    
	    response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=PhieuNhapKhoTT.pdf");
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
		Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		
		ServletOutputStream outstream = response.getOutputStream();
		
		this.CreatePxk(document, outstream, pnkBean);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpNhapkho pnkBean) throws IOException
	{
		try
		{		
			//NumberFormat formatter = new DecimalFormat("#,###,###"); 
			//NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.00"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//Font font = new Font(bf, 13, Font.BOLD);
			//Font font2 = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			//Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			//Font font12 = new Font(bf, 12, Font.NORMAL);
			//Font font13 = new Font(bf, 13, Font.NORMAL);
			//Font font14 = new Font(bf, 14, Font.NORMAL);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_HEADER_WIDTHS = {2.8f * CONVERT, 15.7f * CONVERT};
			float[] TABLE_MIDDLE_WIDTHS = {4.5f * CONVERT, 5.0f * CONVERT, 4.2f * CONVERT, 3.8f * CONVERT};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT, 7.5f * CONVERT, 1.3f * CONVERT, 2.3f * CONVERT, 2.8f * CONVERT, 4.2f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.0f * CONVERT, 7.5f * CONVERT, 7.0f * CONVERT};
			int SANPHAM_NUM_ROWS = 8;
			int numSps = pnkBean.getSpList().size();
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] splist.size = " + numSps);
			
			int currentSpId = 0;
			int currentPage = 0;
			int stt = 1;
			double totalTrongLuong = 0;
			//double totalTheTich = 0;
			int totalSoluong=0;
			int totalthung=0;
			int totalle=0;
			
			//Ve cac trang pdf
			do {
				currentPage++;
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Draw page " + currentPage + "...");
			
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
					} catch (Exception e) {	
						System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] error message = " + e.getMessage());
					}
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
					cell.setFixedHeight(2.8f * CONVERT);
					headerTable.addCell(cell);
				}
				
				
				//Header Titles
				PdfPTable headerTitlesTable = new PdfPTable(1);
				headerTitlesTable.setWidths(new float[] {13.8f * CONVERT});
				headerTitlesTable.setWidthPercentage(100);
				headerTitlesTable.getDefaultCell().setBorder(0);
				
				cell = new PdfPCell(new Paragraph("CÔNG TY TNHH BAO BÌ GIẤY NHÔM NEW TOYO", font12bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setPaddingLeft(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				headerTitlesTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD.", font10bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.5f * CONVERT);
				headerTitlesTable.addCell(cell);
				
				//Empty Row
				cell = new PdfPCell(new Paragraph(" ", font10bold));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				headerTitlesTable.addCell(cell);			
				
				PdfPTable headerTitles2Table = new PdfPTable(2);
				float[] headerTitles2Widths = {TABLE_HEADER_WIDTHS[1]/2, TABLE_HEADER_WIDTHS[1]/2};
				headerTitles2Table.setWidths(headerTitles2Widths);
				headerTitles2Table.setWidthPercentage(100);
				
				PdfPCell cell2;
				cell2 = new PdfPCell(new Paragraph("PHIẾU NHẬP KHO", font15bold));
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Số phiếu/Ref No: " + pnkBean.getId() + "", font10));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("(GOODS RECEIVED NOTES)", font11bold));	
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày/Date: " + pnkBean.getNgaychotNV(), font10));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				headerTitlesTable.addCell(headerTitles2Table);			
				headerTable.addCell(headerTitlesTable);
	
				document.add(headerTable);
				
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
				middleTable.setWidthPercentage(100);
				
				cell = new PdfPCell(new Paragraph("Đơn vị bán/Supplier:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font9bold)); //Để trống
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font10)); //Trống
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font9)); //Trống
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Chứng từ/Voucher No.:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);			
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(pnkBean.getSoLenhsx(), font11bold));
				cell.setBorder(Rectangle.NO_BORDER);	
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Ngày CT/Voucher Date:", font10));
				cell.setBorder(Rectangle.NO_BORDER);	
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(pnkBean.getNgaychotNV(), font9));
				cell.setBorder(Rectangle.NO_BORDER);			
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Nhập vào kho/Kept at store:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				cell.setBorderWidthLeft(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(pnkBean.getKhoId(), font9));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);			
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Ngày nhập/Received Date:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(pnkBean.getNgaynhapkho(), font9));
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
				
				String[] spTitles = {"STT", "Tên nhãn hiệu & quy cách\nName of material & size", "ĐVT\nUnit", "Số lượng\nQuantity", "Trọng lượng\nWeight", "Ghi chú\nRemarks"};
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], font11bold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CONVERT);
					sanPhamTable.addCell(cell);			
				}
			 
				
				PdfPCell cells = new PdfPCell();

				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] currentSpId = " + currentSpId);
				
				int currentLocalSpId = 0;
				
				while(currentSpId < numSps && currentLocalSpId < SANPHAM_NUM_ROWS)
				{

					/*if(i < pxkBean.getSpList().size())
					{*/
					ISanpham sp = (ISanpham)pnkBean.getSpList().get(currentSpId);
						//để làm tròn lên trong trường hợp 5.555-->5.6
						
						double trongluong = Double.parseDouble(sp.getTrongluong().trim()) +0.0001;
						
					 
						String tl = ""; // Nhom: trong luong kg ko in ra	
						String sl=	"";
						if( !sp.getDVT().equals("Kg"))
						{
							sl=(Float.parseFloat(sp.getSoluongnhapkho()) >0 ?  formatter3.format(Float.parseFloat(sp.getSoluongnhapkho())):"" );
						}
						if(!pnkBean.getDvdkId().equals("100004")){
							tl = formatter3.format((trongluong));
						}
					
						 
						String[] arr = new String[]{
								
							(Float.parseFloat(sp.getSoluongnhapkho()) >0? Integer.toString(stt+1):""),//STT 
							sp.getDiengiai(), //San pham
							sp.getDVT(), //Don vi tinh
							(sl), // So luong 
							( Double.parseDouble(sp.getTrongluong().trim()) >0?tl:""),  //Trong luong
							""//Ghi chu
						};
						if( !sp.getDVT().equals("Kg"))
						{
							totalSoluong += Float.parseFloat(formatter3.format(Float.parseFloat(sp.getSoluongnhapkho())).replace(",",""));
						}
						
						if(!pnkBean.getDvdkId().equals("100004")){
							totalTrongLuong += Double.parseDouble(formatter3.format(Double.parseDouble(sp.getTrongluong().trim())).replace(",",""));
						}
						
						
						
						
						for(int j = 0; j < spTitles.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], font11));
							if(j == 1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								if( j >= 5)
									cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(3.0f);
							
							sanPhamTable.addCell(cells);
						}
						if(Float.parseFloat(sp.getSoluongnhapkho()) >0){
							stt++;
						}
						currentLocalSpId++;
						currentSpId++;
				
					/*
					ISanpham sp = (ISanpham)pnkBean.getSpList().get(currentSpId);
					float sl = Float.parseFloat(sp.getSoluongnhapkho());
					
					if(sl > 0) {
						
						double khoiluong = Float.parseFloat(sp.getTrongluong().trim()) * Float.parseFloat(sp.getSoluongnhapkho());
						//double tt = 0;//Float.parseFloat( sp.getThetich().trim() ) * Float.parseFloat(sp.getSoluongnhapkho());
						//double tt = Integer.parseInt(sp.getSoluong());
						
						String[] arr = new String[]{
							Integer.toString(stt), 
							sp.getDiengiai(), 
							sp.getDVT(), 
							formatter3.format(Float.parseFloat(sp.getSoluongnhapkho())), 
							formatter3.format((khoiluong)), 
							" "
						};
						
						totalSoluong += Float.parseFloat(sp.getSoluongnhapkho());
						
						if(sp.getThung().length() > 0)
							totalthung += Float.parseFloat(sp.getThung());
						if(sp.getLe().length() > 0)
							totalle += Float.parseFloat(sp.getLe());
						
						if(sp.getTrongluong().length() > 0)
							totalTrongLuong += khoiluong;
						//if(sp.getThetich().length() > 0)
							//totalTheTich += Float.parseFloat(sp.getThetich()) * Float.parseFloat(sp.getSoluongnhapkho());
						
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
	
						currentLocalSpId++;
						stt++;
					}
					currentSpId++;
				*/}	
				
				
				if(currentSpId >= numSps) {
					//TOTAL
					String[] arr = new String[]{"", "Tổng cộng/Total", "", formatter3.format(totalSoluong), formatter3.format(totalTrongLuong), "" };
					for(int j = 0; j < arr.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font10bold));
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
				
				PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", font11));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph(" ", font11));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell14 = new PdfPCell(new Paragraph("Trưởng bộ phận ", font11));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				
				cell12 = new PdfPCell(new Paragraph("(Store Keeper)", font10));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph(" ", font10));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("(Head of Dept)", font10));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				
				document.add(tableFooter);
				
				//Them trang moi neu con san pham chua in
				if(currentSpId < numSps) {
					document.newPage();
				}
			} while(currentSpId < numSps);

			document.close(); 
			
			/*IErpCongTy c=getCongTy();
			NumberFormat formatter = new DecimalFormat("###,###.##"); 
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("Đơn vị : "+c.getTEN(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Địa chỉ : "+c.getDIACHI(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("PHIẾU NHẬP KHO", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Số: " + pnkBean.getId(), new Font(bf, 7, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			pxk = new Paragraph(getDate(pnkBean.getNgaynhapkho()),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Họ tên người giao hàng: " + pnkBean.getNguoigiaohang(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Địa chỉ: " + pnkBean.getDiachi(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Lý do nhập: " + pnkBean.getNdnId(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Nhập tại kho: " + pnkBean.getKhoId(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Ghi chú: ",  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			//Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {95.0f, 100.0f};
			root.setWidths(cr);
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 13.0f};
	        table.setWidths(withs);
	        
	        Font font4 = new Font(bf, 9, Font.BOLD);
		
			PdfPTable sanpham = new PdfPTable(10);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = {10.0f, 50.0f, 30.0f, 15.0f, 15.0f, 15.0f, 20.0f, 15.0f, 15.0f, 30.0f};
			sanpham.setWidths(withsKM);
		    
		    String[] th = new String[]{"STT", "Tên, nhãn hiệu, quy cách, phẩm chất vật tư (sản phẩm, hàng hóa)", "Mã số", "ĐVT", "Lô", "Quy cách", "Số lượng", "Thùng", "Lẻ", "Ghi chú"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i= 0; i < 10 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				if(i == 1)
					cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				cell[i].setPadding(5);
				sanpham.addCell(cell[i]);			
			}
			
			
			List<ISanpham> spList = pnkBean.getSpList();
			PdfPCell cells = new PdfPCell();
			float totalTrongLuong = 0;
			float totalTheTich = 0;
			int totalSoluong=0;
			int totalthung=0;
			int totalle=0;
			for(int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = (ISanpham)spList.get(i);
				String[] arr = new String[]{Integer.toString(i+1), sp.getDiengiai(), sp.getMa(), sp.getDVT(), sp.getSolo(), sp.getQuycach(), 
						formatter.format(Float.parseFloat(sp.getSoluongnhapkho())), formatter.format(Float.parseFloat(sp.getThung())), 
						formatter.format(Float.parseFloat(sp.getLe())), ""};
				
				totalSoluong += Float.parseFloat(sp.getSoluongnhapkho());
				
				if(sp.getThung().length() > 0)
					totalthung += Float.parseFloat(sp.getThung());
				if(sp.getLe().length() > 0)
					totalle += Float.parseFloat(sp.getLe());
				
				if(sp.getTrongluong().length() > 0)
					totalTrongLuong += Float.parseFloat(sp.getTrongluong());
				if(sp.getThetich().length() > 0)
					totalTheTich += Float.parseFloat(sp.getThetich());
				
				for(int j = 0; j < 10; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.NORMAL)));
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
					
					sanpham.addCell(cells);
				}
			}	
			
			document.add(sanpham);
			
			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(40);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with = {25.0f, 20.0f, 10.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 9, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(Float.toString(totalTheTich), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));
			
			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
	
			cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 9, Font.NORMAL)));
			cell2 = new PdfPCell(new Paragraph(Float.toString(totalTrongLuong), font2));
			cell3 = new PdfPCell(new Paragraph("KG", font2));
			
			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			
			document.add(tableHead);
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]{30.0f, 30.0f, 30.0f, 30.0f, 30.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Tài xế", new Font(bf, 9, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 9, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 9, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Kế toán", new Font(bf, 9, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			document.add(tableFooter);
			document.close(); */
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}


	/*private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return "Ngày  " + ngay + "  tháng  " + thang + "  Năm  " + nam;
	}*/

}
