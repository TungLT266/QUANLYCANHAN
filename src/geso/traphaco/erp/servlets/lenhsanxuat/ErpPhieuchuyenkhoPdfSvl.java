package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.IYeucau;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

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

public class ErpPhieuchuyenkhoPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public ErpPhieuchuyenkhoPdfSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("print");
			
		 IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX(id);
		 lsxBean.setUserId(userId);
	 
	    	lsxBean.initXuatkhoPdf();
		    
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=PhieuXuatKhoTT.pdf");
			
//			float CONVERT = 28.346457f;
			//float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
			//Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
			//Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
			Document document = new Document(PageSize.LETTER);
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreatePxk2(document, outstream, lsxBean);
			//CreatePxk2(Document document, ServletOutputStream outstream, IErpPhieuxuatkho pxkBean)
			lsxBean.DBclose();
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream, IErpChuyenkhoSX lsxBean) throws IOException
	{
		try
		{			
			//NumberFormat formatter = new DecimalFormat("#,###,###"); 
			//NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			Font font = new Font(bf, 13, Font.BOLD);
//			Font font2 = new Font(bf, 8, Font.BOLD);
//			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
//			Font font14bold = new Font(bf, 14, Font.BOLD);
//			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
//			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
//			Font font13 = new Font(bf, 13, Font.NORMAL);
//			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			//total width = 17.5cm
			float[] TABLE_HEADER_WIDTHS = {2.8f * CONVERT, 15.7f * CONVERT};
			float[] TABLE_MIDDLE_WIDTHS = {4.25f * CONVERT, 5.75f * CONVERT, 3.65f * CONVERT, 4.85f * CONVERT};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT, 7.0f * CONVERT, 1.3f * CONVERT, 2.3f * CONVERT, 2.8f * CONVERT, 4.2f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.3f * CONVERT, 7.8f * CONVERT, 7.4f * CONVERT};
			int SANPHAM_NUM_ROWS = 4;
			
			int numSPs = lsxBean.getSpList().size();
			int numPages = (int) Math.ceil((float)numSPs/SANPHAM_NUM_ROWS);
			if(numPages==0){
				numPages=1;
			}
			/*System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] splist.size = " + numSPs);
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] num of pages = " + numPages);
*/			
			int currentSpId = 0;

			double totalTrongLuong = 0;
//			double totalTheTich = 0;
			double totalSoluong=0;
//			int totalthung=0;
//			int totalle=0;
//			String tongtrongluong = "";
			
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
					"D:\\Projects\\Newtoyo\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"
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
				cell2 = new PdfPCell(new Paragraph("PHIẾU XUẤT KHO", font16bold));
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
				
				if(lsxBean.getDvkd().equals("100004"))
				{
					cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getId() + " /XXL", font11));	
				}
				else
				{
					cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getId() + " /XXN", font11));	
				}
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
				
				cell2 = new PdfPCell(new Paragraph(": " + getVnDateTime(lsxBean.getNgayyeucau(),"/"), font11));	
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
				
				cell = new PdfPCell(new Paragraph(lsxBean.getNguoinhan(), font10bold));
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
				
				cell = new PdfPCell(new Paragraph(lsxBean.getLydoyeucau(), font11bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Xuất tại kho/Issued at", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": " + lsxBean.getKhoXuatTen(), font10));
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
						IYeucau sp = (IYeucau)lsxBean.getSpList().get(currentSpId);
						//để làm tròn lên trong trường hợp 5.555-->5.6
						
						double trongluong = 0;
						try{
							trongluong = Double.parseDouble(sp.getTrongluong().trim()) +0.0001;
						}catch(Exception err){
							
						}
						 
						String tl = ""; // Nhom: trong luong kg ko in ra	
						if(!sp.getDonViTinh().equals("Kg"))
						{
							tl = formatter3.format((trongluong));
						}
						float soluongyc=0;
						try{
							soluongyc=Float.parseFloat(sp.getSoluongYC());
						}catch(Exception er){
							
						}
						String[] arr = new String[]{
							Integer.toString(currentSpId+1),//STT 
							sp.getDiengiai(), //San pham
							sp.getDonViTinh(), //Don vi tinh
							formatter3.format(soluongyc), // So luong 
							tl,  //Trong luong
							sp.getGhiChu() //Ghi chu
						};
						
						totalSoluong += Float.parseFloat(formatter3.format(soluongyc).replace(",",""));
						totalTrongLuong+= Float.parseFloat(formatter3.format(trongluong).replace(",",""));
						
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
					 
				}	
				
				if(pageIndex == numPages-1) {
					//TOTAL
					String[] arr = new String[]{"", "Tổng cộng/Total", "", formatter3.format(totalSoluong),  formatter3.format(totalTrongLuong), "" };
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
				PdfPCell cell13 = new PdfPCell(new Paragraph("Trưởng bộ phận", font12));
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
				cell13 = new PdfPCell(new Paragraph("(Head of Dept)", font11));
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

	public static void main ( String args [  ]  )   {
		/*IThongtinsanphamgiay thongtin=new Thongtinsanphamgiay();
		String[] mang=new String[]{"109669"
				};
		//String[] mang=new String[]{"103106", "103107"};
		
		for(int i=0;i<mang.length;i++){
			thongtin.setId(mang[i]);
		if(thongtin.deleteQc(mang[i])){
			
			System.out.println(" Thanh cong "+mang[i]);
		}else{
			System.out.println("Khong Thanh cong "+mang[i]);
			System.out.println(thongtin.getMessage());
		}
		}*/
		NumberFormat formatter = new DecimalFormat("#,###,###.00"); 
		double so= 3000.785;
		so=so+0.00002;
		
		System.out.println(formatter.format(so));
		
   }
	
	private void CreatePxk2(Document document, ServletOutputStream outstream, IErpChuyenkhoSX pxkBean) throws IOException
	{
		dbutils db = new dbutils();
		try
		{
//			NumberFormat formatter = new DecimalFormat("#,###,###");
//			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000");
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");

			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			// font2.setColor(BaseColor.GREEN);
			
			String 
			query = " SELECT ten, diachi, masothue, dienthoai, fax FROM ERP_CONGTY WHERE PK_SEQ = 100005 ";
			ResultSet rsCt = db.get(query);
//			String ctyTen = "";
			String ctyDiachi = "";
//			String ctyMasothue = "";
//			String ctyDienthoai = "";
//			String ctyFax = "";
			try {
				rsCt.next();
//				ctyTen = rsCt.getString("ten");
				ctyDiachi = rsCt.getString("diachi");
//				ctyMasothue = rsCt.getString("masothue");
//				ctyDienthoai = rsCt.getString("dienthoai");
//				ctyFax = rsCt.getString("fax");
				rsCt.close();
			} catch(Exception e) {
//				ctyTen = "HALONG TraphacoERP";
				ctyDiachi = "Lầu 8 161 Võ Văn Tần, Phường 6, Quận 3, Tp.Hồ Chí Minh";
//				ctyMasothue = "0 3 1 0 7 7 6 0 7 1";
//				ctyDienthoai = "(08) 62905560";
//				ctyFax = "(08) 62905104";
			}

			Paragraph pxk = new Paragraph("Đơn vị: HALONG TraphacoERP", new Font(bf, 6, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("Địa chỉ: " + ctyDiachi, new Font(bf, 6,
				Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("PHIẾU XUẤT KHO", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Số: " + pxkBean.getId(), new Font(bf, 7, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);

			pxk = new Paragraph(getDate(pxkBean.getNgayyeucau()), new Font(bf, 6, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Họ tên người nhận hàng: " + pxkBean.getKhoNhapTen(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			/*pxk = new Paragraph("Địa chỉ: " + pxkBean.getDiachi(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);*/

			pxk = new Paragraph("Lý do xuất: " + pxkBean.getLydoyeucau(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("Xuất tại kho: " + pxkBean.getKhoXuatTen(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			/*pxk = new Paragraph("Ghi chú:  " + pxkBean.getGhichu(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
*/
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

			Font font4 = new Font(bf, 8, Font.BOLD);

			PdfPTable sanpham = new PdfPTable(9);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM =
			{ 10.0f, 45.0f, 20.0f, 15.0f, 15.0f, 18.0f, 15.0f, 15.0f, 15.0f};
			sanpham.setWidths(withsKM);

			/*String[] th = new String[]
			{ "STT", "Tên, nhãn hiệu, quy cách, phẩm chất vật tư (sản phẩm, hàng hóa)", "Mã số", "ĐVT", "Quy cách",
				"Số lượng", "Thùng", "Lẻ", "T.Lượng", "T.Tích" };*/
			String[] th = new String[]
					{ "STT", "Tên, nhãn hiệu, quy cách, phẩm chất vật tư (sản phẩm, hàng hóa)", "Mã số", "ĐVT", "Quy cách",
						"Số lượng", "Thùng", "Lẻ", "T.Lượng" };
			PdfPCell[] cell = new PdfPCell[9];
			for (int i = 0; i < 9; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				if (i == 1)
					cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);

				cell[i].setPadding(4);
				sanpham.addCell(cell[i]);
			}

			List<IYeucau> spList = pxkBean.getSpList();
			PdfPCell cells = new PdfPCell();
			double totalTrongLuong = 0;
//			double totalTheTich = 0;
			double totalSoluong = 0;
//			double totalthung = 0;
//			double totalle = 0;
			
			
			
			System.out.println("CÓ: "+spList.size()+" SP");
			
			for (int i = 0; i <= spList.size(); i++)
			{
				if (i < spList.size())
				{
					IYeucau sp = spList.get(i);

					double khoiluong = Float.parseFloat(sp.getTrongluong().trim());
					String TLuong = "";
					if(khoiluong > 0 )
						TLuong = formatter3.format((khoiluong));
					
					
					sp.setSoluongchuyen(sp.getSoluongchuyen().replaceAll(",", ""));
					//double tt = Float.parseFloat(sp.getThetich().trim()) * Integer.parseInt(sp.getSoluong());

					/*String[] arr = new String[]
					{ Integer.toString(i + 1), sp.getDiengiai(), sp.getMa(), sp.getDVT(), sp.getQuycach(),
						formatter3.format(Long.parseLong(sp.getSoluong())), 
						formatter.format(Float.parseFloat(sp.getThung())),
						formatter3.format(Float.parseFloat(sp.getLe())), 
						formatter3.format((khoiluong)),
						formatter2.format((tt)) };*/
					String[] arr = new String[]
							{ Integer.toString(i + 1), sp.getTen(), sp.getMa(), sp.getDonViTinh(), sp.getQuyCach(),
								formatter3.format(Float.parseFloat(sp.getSoluongchuyen().replaceAll(",", ""))), 
								"",
								"", 
								TLuong };

					totalSoluong += Float.parseFloat(sp.getSoluongchuyen());

					/*if (sp.getThung().length() > 0)
						totalthung += Float.parseFloat(sp.getThung());
					if (sp.getLe().length() > 0)
						totalle += Float.parseFloat(sp.getLe());*/

					if (sp.getTrongluong().length() > 0)
						totalTrongLuong += Float.parseFloat(sp.getTrongluong());
					/*if (sp.getThetich().length() > 0)
						totalTheTich += Float.parseFloat(sp.getThetich()) * Integer.parseInt(sp.getSoluong());
					*/
					for (int j = 0; j < 9; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
						if (j == 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							if (j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(3.0f);

						sanpham.addCell(cells);
					}
				} else
				{
					String[] arr = new String[]
					{ "", "Total", "", "", "", formatter3.format(totalSoluong), "",
							"", ""};
					for (int j = 0; j < 9; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if (j == 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2.0f);

						sanpham.addCell(cells);
					}
				}
			}

			document.add(sanpham);

			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with =
			{ 35.0f, 40.0f, 10.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			/*PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 8, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(formatter3.format(totalTheTich), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);*/

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 8, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(formatter3.format(totalTrongLuong), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("KG", font2));

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

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 30.0f, 30.0f, 30.0f, 30.0f, 30.0f });

			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 7, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Tài xế", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Kế toán", new Font(bf, 7, Font.BOLD)));
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

			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
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
			
			System.out.println("TABLE FOOTER");
			document.close(); 
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		db.shutDown();
	}

	 
	 
	
	 
	private String getVnDateTime(String date, String split) {
		return date;
	 
	}
	
	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = "";
		String thang = "";
		String ngay = "";
		if(arr.length > 2) {
			nam = arr[0];
			thang = arr[1];
			ngay = arr[2];
		}

		return "Ngày  " + ngay + "  tháng  " + thang + "  Năm  " + nam;
	}
	
 

	
}
