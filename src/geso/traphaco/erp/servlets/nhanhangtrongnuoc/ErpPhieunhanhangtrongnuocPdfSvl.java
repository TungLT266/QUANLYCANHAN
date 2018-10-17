package geso.traphaco.erp.servlets.nhanhangtrongnuoc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.imp.ErpCongTy;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.*;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.*;

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

public class ErpPhieunhanhangtrongnuocPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpPhieunhanhangtrongnuocPdfSvl()
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
		
		IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay(id);
		nhBean.setUserId(userId);
		nhBean.setLydo(id);
		
	
		
		
		nhBean.initPdf(spId);
		
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=PhieuNhanHangTT.pdf");
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
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
	
	private void CreateNhanhang(Document document, ServletOutputStream outstream, IErpNhanhang_Giay nhBean)
	throws IOException
	{
		
		dbutils db = new dbutils();
		String lydonhan="";
		String dvkdId = "";
		try
		{	
			
			String query="";
			query= " select ten, " +
					"      (select TOP 1 dvkd.PK_SEQ " +
					"       from ERP_NHANHANG_SANPHAM nhsp inner join ERP_SANPHAM sp on nhsp.SANPHAM_FK = sp.PK_SEQ " +
					"                                      inner join DONVIKINHDOANH dvkd on sp.DVKD_FK = dvkd.PK_SEQ " +
					"       where nhsp.NHANHANG_FK = "+ nhBean.getId() +") as dvkdId " +
				   " from ERP_NOIDUNGNHAP where pk_seq =(select noidungnhan_fk from erp_nhanhang where pk_seq='"+nhBean.getLydonhan()+"')  ";
			System.out.println(" query ly do nhan :" + query);
			ResultSet kt= db.get(query);
			if(kt.next()){  //-------  Mua hàng/ nhận hàng ---> lý do
				
					lydonhan = kt.getString("ten");
					dvkdId = kt.getString("dvkdId");
				
			}else{//------- bán hàng / nhận hàng   -----> lý do
			
				lydonhan="Nhận hàng trả về từ KH/NPP";
			}
			
			System.out.println(" ly do nhan :" + lydonhan);
			
			
			
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
			
			boolean isVattu= nhBean.getLoaispId().equals("100000");
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			//total width = 17.5cm
			float[] TABLE_HEADER_WIDTHS = {2.8f * CONVERT, 15.7f * CONVERT};
			float[] TABLE_MIDDLE_WIDTHS = {4.5f * CONVERT, 8.5f * CONVERT, 3.7f * CONVERT, 2.8f * CONVERT};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT, 7.5f * CONVERT, 1.3f * CONVERT, 2.3f * CONVERT, 2.3f * CONVERT, 4.2f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.0f * CONVERT, 7.5f * CONVERT, 7.0f * CONVERT};
			int SANPHAM_NUM_ROWS;
			SANPHAM_NUM_ROWS= isVattu?10:5;
			int numSps = nhBean.getSpList().size();
			int numPages = (int) Math.ceil((float)numSps/SANPHAM_NUM_ROWS);
			if(isVattu) {
				TABLE_SANPHAM_WIDTHS = new float[] {1.2f * CONVERT, 8.5f * CONVERT, 2.3f * CONVERT, 2.3f * CONVERT, 0.0f * CONVERT, 4.2f * CONVERT};
			}
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
				
				cell = new PdfPCell(new Paragraph("Công Ty Cổ Phần Đồ Hộp Hạ Long", font12bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setPaddingLeft(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				headerTitlesTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Số 71, Lê Lai, Phường Máy Chai, Quận Ngô Quyền, TP. Hải Phòng.             Page " + currentPage + " of " + numPages, font10bold));
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
				cell2 = new PdfPCell(new Paragraph("PHIẾU NHẬP KHO", font15bold));
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
				
				String Kyhieu = "";
				if(dvkdId.equals("100004"))
				{
					Kyhieu = "/NXL";
				}
				else
				{
					Kyhieu = "/NXN";
				}
				cell2 = new PdfPCell(new Paragraph(": " + nhBean.getId() + Kyhieu, font10));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("(GOODS RECEIVED NOTES)", font11bold));	
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày/Date", font10));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.6f * CONVERT);
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
				
				cell = new PdfPCell(new Paragraph("Số HĐ/Invoice No. : ", font10)); //Bỏ trống
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(nhBean.getSohoadon(), font10bold)); //Bỏ trống
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
				
				cell = new PdfPCell(new Paragraph("NTVN" +"   - "+ lydonhan, font10bold)); //Link đến kho nhận
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
			 
				String[] spTitles = {"STT\nNo", "Tên nhãn hiệu & quy cách\nName of material & size", "ĐVT\nUnit", "Số lượng ", "Trọng lượng ", "Ghi chú\nRemarks"};
				
				 
				
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
					ISanpham  sp= new Sanpham();
					sp = nhBean.getSpList().get(currentSpId);
					
					
					 
					double khoiluong=0;
					double soluong=0;
					double tt=0;
					//if(sl > 0) {
						if(sp.getDvdl().equals("Kg")){
							khoiluong= Float.parseFloat(sp.getSoluongnhan());
						}
						else{
							soluong= Float.parseFloat(sp.getSoluongnhan());
						}
					
						if(isVattu){
							soluong= Float.parseFloat(sp.getSoluongnhan());
						}

						double trongluong = Double.parseDouble(sp.getTrongluong().trim()) +0.0001;
						
							String tl = ""; // Nhom: trong luong kg ko in ra	
							String sl=	"";
							if( !sp.getDvdl().equals("Kg")|| isVattu)
							{
								sl=(Float.parseFloat(sp.getSoluongnhan()) >0 ?  formatter3.format(Float.parseFloat(sp.getSoluongnhan())):"" );
							}

						 
								tl = formatter3.format((trongluong));
					 
								String[] arr = new String[]{
									Integer.toString(stt), 
									sp.getDiengiai(), 
		 
									sp.getDvdl(), 
		 
									sl, 
									tl, 
		 
									" "
							};
								 
						if( !sp.getDvdl().equals("Kg") || isVattu)
						{
						totalSoluong += soluong;
						totalTrongLuong += khoiluong;
						}
						
						if(sp.getTrongluong().length() > 0)
							totalTrongLuong += trongluong;
						 
 
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
				
				PdfPCell cell12 = new PdfPCell(new Paragraph("Người nhận hàng", isVattu?font10:font11));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho",isVattu?font10: font11));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell14 = new PdfPCell(new Paragraph("Trưởng bộ phận",isVattu?font10: font11));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				
				cell12 = new PdfPCell(new Paragraph("(Received By)", isVattu?font9:font10));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("(Store Keeper)", isVattu?font9:font10));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("(Head of Dept)",isVattu?font9: font10));
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
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
 
	 
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
