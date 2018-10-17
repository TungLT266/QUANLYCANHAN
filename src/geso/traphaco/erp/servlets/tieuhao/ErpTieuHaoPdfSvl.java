package geso.salesup.erp.servlets.tieuhao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.salesup.erp.db.sql.dbutils;
import geso.salesup.erp.beans.congty.IErpCongTy;
import geso.salesup.erp.beans.congty.imp.ErpCongTy;
import geso.salesup.erp.beans.tieuhao.*;
import geso.salesup.erp.beans.tieuhao.imp.*;

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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ErpTieuHaoPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpTieuHaoPdfSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		if (userId.length() == 0)
			userId = request.getParameter("userId");
		
		String id = request.getParameter("nhId");
		if(id == null) id = "";
		else id = id.trim();
		
		String spId = request.getParameter("spIds");
		if(spId == null) spId = "";
		else spId = spId.trim();
		
		System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] userId =  " + userId);
		System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] nhId =  " + id);
		System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] spIds =  " + spId);
		
		IErpTieuHao nhBean = new ErpTieuHao(id);
		nhBean.setUserId(userId);
		
		nhBean.initPdf(spId);
		
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=PhieuNhanHangTT.pdf");
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 2.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
		Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		
		ServletOutputStream outstream = response.getOutputStream();
		
		this.CreateNhanhang(document, outstream, nhBean);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException
	{
		this.doGet(request, response);
	}
	
	private IErpCongTy getCongTy()
	{
		dbutils db = new dbutils();
		String query = "Select Ma,Ten,MaSoThue,DiaChi,DienThoai,Fax From Erp_CongTy Where PK_SEQ =100000 ";
		ResultSet rs = db.get(query);
		IErpCongTy c = new ErpCongTy();
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					c.setTEN(rs.getString("Ten"));
					c.setDIACHI(rs.getString("DiaChi"));
					c.setDIENTHOAI(rs.getString("DienThoai"));
					c.setFAX(rs.getString("Fax"));
					c.setMASOTHUE(rs.getString("MaSoThue"));
					
				}
			}
			catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		return c;
		
	}
	
	private void CreateNhanhang(Document document, ServletOutputStream outstream, IErpTieuHao nhBean)
	throws IOException
	{
		try
		{/*			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.00"); 
			
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
			float[] TABLE_HEADER_WIDTHS = {2.8f * CONVERT, 14.7f * CONVERT};
			float[] TABLE_MIDDLE_WIDTHS = {4.5f * CONVERT, 9.0f * CONVERT, 2.2f * CONVERT, 2.8f * CONVERT};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT, 6.5f * CONVERT, 1.3f * CONVERT, 2.3f * CONVERT, 2.3f * CONVERT, 4.2f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.0f * CONVERT, 7.5f * CONVERT, 7.0f * CONVERT};
			int SANPHAM_NUM_ROWS = 4;
			int numSps = nhBean.getSpList().size();
			int numPages = (int) Math.ceil((float)numSps/SANPHAM_NUM_ROWS);
			
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] splist.size = " + numSps);
			
			int currentSpId = 0;
			int currentPage = 0;
			int stt = 1;
			double totalTrongLuong = 0;
			double totalTheTich = 0;
			int totalSoluong=0;
			
			
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
						getServletContext().getInitParameter("projectPath") + "\\pages\\images\\logoProvence.png"
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
					cell = new PdfPCell(logoImage);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(2.8f * CONVERT);
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
				
				cell = new PdfPCell(new Paragraph("CONG TY CO PHAN HANG TIEU DUNG PROVENCE", font12bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setPaddingLeft(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				headerTitlesTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font10bold));
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
				cell2 = new PdfPCell(new Paragraph("PHIẾU NHẬP KHO", font14bold));
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Số phiếu/Ref No", font10));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(": " + nhBean.getId() + "/NXN", font10));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("(GOODS RECEIVED NOTES)", font10bold));	
				cell2.setFixedHeight(0.5f * CONVERT);
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày/Date", font10));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.5f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				//Ngày chứng từ = ngày tạo phiếu = được lưu trong biến ngaychot
				String ngay = nhBean.getNgaychot().substring(8, 10);
				String thang = nhBean.getNgaychot().substring(5, 7);
				String nam = nhBean.getNgaychot().substring(0, 4);
				
				cell2 = new PdfPCell(new Paragraph(": " + ngay + "/" + thang + "/" + nam, font10));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.5f * CONVERT);
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
				
				cell = new PdfPCell(new Paragraph(nhBean.getDvthId(), font10bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font10)); //Bỏ trống
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font9)); //Bỏ trống
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Chứng từ/Voucher No:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(nhBean.getDonmuahangId(), font10bold));
				cell.setBorder(Rectangle.NO_BORDER);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Ngày/Date:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(ngay + "/" + thang + "/" + nam, font9));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				

				
				cell = new PdfPCell(new Paragraph("Nhập vào kho/Kept at store:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NTVN", font10bold)); //Link đến kho nhận
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Ngày/Date:", font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				ngay = nhBean.getNgaynhanhang().substring(8, 10);
				thang = nhBean.getNgaynhanhang().substring(5, 7);
				nam = nhBean.getNgaynhanhang().substring(0, 4);
				
				cell = new PdfPCell(new Paragraph(ngay + "/" + thang + "/" + nam, font9));
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
				
				String[] spTitles = {"STT\nNo", "Tên nhãn hiệu & quy cách\nName of material & size", "ĐVT\nUnit", "Số lượng\nQuantity", "Trọng lượng\nWeight", "Ghi chú\nRemarks"};			
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], font10bold));
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
					ISanpham sp = (ISanpham)nhBean.getSpList().get(currentSpId);
					float sl = Float.parseFloat(sp.getSoluongnhan());
					
					//if(sl > 0) {
						double khoiluong = Float.parseFloat(sp.getTrongluong().trim()) * Float.parseFloat(sp.getSoluongDaNhan());
						double tt = Float.parseFloat( sp.getThetich().trim() ) * Float.parseFloat(sp.getSoluongDaNhan());
						
						String[] arr = new String[]{
							Integer.toString(stt), 
							sp.getDiengiai(), 
							//sp.getMa(), 
							sp.getDvdl(), 
							//sp.getQuycach(), 
							formatter3.format(Float.parseFloat(sp.getSoluongnhan())), 
							//formatter.format(Long.parseLong(sp.getThung())), 
							//formatter.format(Long.parseLong(sp.getLe())), 
							formatter3.format((khoiluong)), 
							" "
						};
						
						totalSoluong += Float.parseFloat(sp.getSoluongnhan());
						
						
						if(sp.getTrongluong().length() > 0)
							totalTrongLuong += khoiluong;
						if(sp.getThetich().length() > 0)
							totalTheTich += Float.parseFloat(sp.getThetich()) * Float.parseFloat(sp.getSoluongDaNhan());
						
						for(int j = 0; j < spTitles.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], font9));
							if(j == 1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else if( j == 3 || j == 4) {
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							} else {
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(3.0f);
							
							sanPhamTable.addCell(cells);
						}
	
						currentLocalSpId++;
						stt++;
					//}
					currentSpId++;
				}
				
				if(currentSpId >= numSps) {
					//TOTAL
					String[] arr = new String[]{"", "Tổng cộng/Total", "", formatter3.format(totalSoluong), formatter3.format(totalTrongLuong), "" };
					for(int j = 0; j < arr.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font11bold));
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
				
				PdfPCell cell12 = new PdfPCell(new Paragraph("Người nhận hàng", font10));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", font10));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell14 = new PdfPCell(new Paragraph("Thủ trưởng đơn vị", font10));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				
				cell12 = new PdfPCell(new Paragraph("(Received By)", font9));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("(Store Keeper)", font9));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("(Management Board)", font9));
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
		*/}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		/*
		try
		{
			
			
			
			
			
			
			
			
			IErpCongTy c = getCongTy();
			NumberFormat formatter = new DecimalFormat("#,###,###");
			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			
			Paragraph pxk =
			new Paragraph("Tên DN : "+c.getTEN(), new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk =
			new Paragraph("Địa chỉ : "+c.getDIACHI() , new Font(bf, 9,
			Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("MST: "+c.getMASOTHUE() , new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("PHIẾU NHẬN HÀNG", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("No. : 120" + nhBean.getId(), new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			pxk = new Paragraph(getDate(nhBean.getNgaynhanhang()), new Font(bf, 10, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Mã và tên NCC/KH: " + nhBean.getDvthId(), new Font(bf, 10, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Số PO: " + nhBean.getDonmuahangId(), new Font(bf, 10, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Nội dung: " + nhBean.getDiengiai(), new Font(bf, 10, Font.NORMAL));
			pxk.setSpacingAfter(15);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			/*
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr =
			{ 95.0f, 100.0f };
			root.setWidths(cr);
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =
			{ 7.0f, 22.0f, 40.0f, 13.0f, 13.0f };
			table.setWidths(withs);
			
			Font font4 = new Font(bf, 10, Font.BOLD);
			
			PdfPTable sanpham = new PdfPTable(9);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM =
			{ 10.0f, 20.0f, 45.0f, 10.0f, 15.0f, 16.0f, 16.0f, 16.0f, 37.0f };
			sanpham.setWidths(withsKM);
			
			String[] th = new String[]
			{ "STT", "Mã hàng", "Tên hàng", "ĐVT", "Số lượng", "Ghi chú" };
			PdfPCell[] cell = new PdfPCell[9];
			for (int i = 0; i < 6; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				
				if (i == 4)
					cell[i].setColspan(4);
				else
					cell[i].setRowspan(2);
				
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				if (i >= 1)
					cell[i].setBorderWidthLeft(0);
				cell[i].setPadding(4);
				// cell[i].setBorderWidthBottom(0);
				// cell[i].setBackgroundColor(BaseColor.GRAY);
				sanpham.addCell(cell[i]);
			}
			
			th = new String[]
			{ "Đặt / Trả", "Thực nhập", "Đạt", "Không đạt" };
			for (int i = 0; i < 4; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				cell[i].setPadding(4);
				cell[i].setBorderWidthTop(0);
				cell[i].setBorderWidthLeft(0);
				// cell[i].setBackgroundColor(BaseColor.GRAY);
				sanpham.addCell(cell[i]);
			}
			/*
			 * th = new String[]{"No.", "Material Code", "Material Description",
			 * "UoM", "PO Qty", "Actual Qty", "Accepted Qty", "Not Acc. Qty",
			 * "Notes"}; for(int i= 0; i < 9; i++) { cell[i] = new PdfPCell(new
			 * Paragraph(th[i], new Font(bf, 10, Font.ITALIC)));
			 * cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * cell[i].setPadding(2); if(i >= 1) cell[i].setBorderWidthLeft(0);
			 * cell[i].setBorderWidthTop(0);
			 * cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * sanpham.addCell(cell[i]); }
			 */
			/*List<ISanpham> spList = nhBean.getSpList();
			PdfPCell cells = new PdfPCell();
			
			System.out.println("So san pham: " + spList.size());
			
			long totalDat = 0;
			long totalNhan = 0;
			for (int i = 0; i <= spList.size(); i++)
			{
				if (i < spList.size())
				{
					ISanpham sp = (ISanpham) spList.get(i);
					String[] arr =
					new String[]
					{ this.getSTT(i + 1), sp.getMa(), sp.getDiengiai(), sp.getId(),
					formatter.format( Float.parseFloat(sp.getSoluongdat())),
					formatter.format( Float.parseFloat(sp.getSoluongnhan())), "", "", "" };
					
					if (sp.getSoluongdat().length() > 0)
						totalDat += Float.parseFloat(sp.getSoluongdat());
					if (sp.getSoluongnhan().length() > 0)
						totalNhan +=Float.parseFloat(sp.getSoluongnhan());
					
					for (int j = 0; j < 9; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.NORMAL)));
						if (j == 2)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							if (j > 3)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						
						if (j >= 1)
							cells.setBorderWidthLeft(0);
						
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setBorderWidthTop(0);
						cells.setBorderWidthBottom(0);
						cells.setPadding(2.0f);
						
						sanpham.addCell(cells);
					}
				}
				else
				{
					for (int j = 0; j < 9; j++)
					{
						cells = new PdfPCell(new Paragraph(" ", new Font(bf, 12, Font.BOLD)));
						
						if (j >= 1)
							cells.setBorderWidthLeft(0);
						cells.setBorderWidthTop(0);
						cells.setBorderWidthBottom(0);
						sanpham.addCell(cells);
					}
					
					String[] arr = new String[]
					{ "Tổng cộng", "", formatter.format(totalDat), formatter.format(totalNhan), "", "", "" };
					
					for (int j = 0; j < 7; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.BOLD)));
						if (j == 0)
						{
							cells.setColspan(3);
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						// cells.setBackgroundColor(BaseColor.GRAY);
						cells.setPadding(4.0f);
						
						sanpham.addCell(cells);
					}
				}
			}
			document.add(sanpham);
			
			pxk = new Paragraph("Số chứng từ kèm theo: ", new Font(bf, 10, Font.NORMAL));
			pxk.setSpacingAfter(20);
			pxk.setSpacingBefore(15);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			// Table Footer
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 30.0f, 30.0f, 30.0f, 40.0f });
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Người giao hàng", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 9, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 9, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Kiểm tra chất lượng (QA)", new Font(bf, 9, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			
			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.NORMAL)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.NORMAL)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
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
		catch (DocumentException e)
		{
			System.out.println("Exception: " + e.getMessage());
		}*/
	}
	
	private String getSTT(int stt)
	{
		if (stt < 10)
			return "000" + Integer.toString(stt);
		if (stt < 100)
			return "00" + Integer.toString(stt);
		if (stt < 1000)
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
	
}
