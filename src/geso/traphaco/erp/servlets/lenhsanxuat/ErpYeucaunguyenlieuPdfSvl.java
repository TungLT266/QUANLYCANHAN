package geso.traphaco.erp.servlets.lenhsanxuat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.erp.beans.lenhsanxuat.*;
import geso.traphaco.erp.beans.lenhsanxuat.imp.*;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.db.sql.dbutils;

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

public class ErpYeucaunguyenlieuPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static float CM = 28.346457f;
  
    public ErpYeucaunguyenlieuPdfSvl() {
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
			
		IErpYeucaunguyenlieu ycnlBean = new ErpYeucaunguyenlieu(id);
		ycnlBean.setUserId(userId);
	    
	    String task = request.getParameter("task");
	    if(task.equals("chuyenNL"))
	    {
		    ycnlBean.initChuyenNLPdf();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=PhieuChuyenNguyenLieu.pdf");
			ServletOutputStream outstream = response.getOutputStream();		
			this.CreatePdf(outstream, ycnlBean);
			//this.CreatePhieuChuyenNL(document, outstream, ycnlBean);
	    }
	    else
	    {
	    	if(task.equals("yeucauNL"))
	    	{
	    		ycnlBean.initYeucauNLPdf();
			    
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=PhieuYeuCauNguyenLieu.pdf");
				
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				
				this.CreatePhieuYeuCauNL(document, outstream, ycnlBean);
	    	}
	    	else
	    	{
	    		if(task.equals("sxyeucaunl"))
	    		{
	    			 ycnlBean.initsxyeucaunlPdf();
	    			    
	    				response.setContentType("application/pdf");
	    				response.setHeader("Content-Disposition"," inline; filename=PhieuChuyenNguyenLieu.pdf");
	    				
	    				ServletOutputStream outstream = response.getOutputStream();
	    			
	    				this.CreatePdf(outstream, ycnlBean);
	    		}
	    	}
	    }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreatePhieuYeuCauNL(Document document, ServletOutputStream outstream, IErpYeucaunguyenlieu ycnlBean) throws IOException
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			 
			Paragraph pxk = new Paragraph("Số phiếu..: " + ycnlBean.getId(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("PHIẾU YÊU CẦU NGUYÊN LIỆU", font);
			pxk.setSpacingAfter(10);
			pxk.setSpacingBefore(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
		
			PdfPTable sanpham = new PdfPTable(8);
			sanpham.setKeepTogether(false);
			sanpham.setSplitLate(false);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = {35.0f, 10.0f, 15.0f, 15.0f, 15.0f, 15.0f, 12.0f, 10.0f};
			sanpham.setWidths(withsKM);
			
			String[] th = new String[]{"Sản phẩm", "Số lô", " ", "Kho nhận", "Khu", "Bean", "Số lượng", "Đơn vị"};
			PdfPCell[] cell = new PdfPCell[8];
			for(int i= 0; i < 8 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 9, Font.NORMAL)));
				cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				
				if(i >= 6)
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBorder(0);
				cell[i].setPadding(4);
				
				sanpham.addCell(cell[i]);	
			}
		    
			PdfPCell space = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			space.setColspan(8);
			space.setPadding(2.0f);
			space.setBorderWidthLeft(0);
			space.setBorderWidthRight(0);
			space.setBorderWidthTop(0);
			space.setBorderWidthBottom(0.8f);
			sanpham.addCell(space);
			
			space = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			space.setColspan(8);
			space.setPadding(2.0f);
			space.setBorder(0);
			sanpham.addCell(space);
			
			List<IYeucau> spList = ycnlBean.getSpList();
			PdfPCell cells = new PdfPCell();

			int stt = 0;
			for(int i = 0; i <= spList.size(); i++)
			{
				if(i < spList.size())
				{
					IYeucau sp = (IYeucau)spList.get(i);
					List<ISpDetail> spDetail = sp.getSpDetailList();
					for(int k = 0; k < spDetail.size(); k++)
					{
						ISpDetail detail = spDetail.get(k);
						
						String[] soluong = detail.getSoluong().split(" - "); //0 - soluong yeu cau, 1 - soluong nhap, 2 - con lai
						
						String[] arr = new String[]{sp.getMa(), detail.getSolo(), "Yêu cầu", " ", " ", " ", 
								formatter.format(Double.parseDouble(sp.getSoluongYC())), detail.getMa()};
						
						for(int j = 0; j < 8; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
							if(j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);
							cells.setBorder(0);
							
							sanpham.addCell(cells);
						}
						
						String[] arr2 = new String[]{sp.getTen(), "Nhận", detail.getVitriId(), detail.getKhu(), detail.getVitri(), formatter.format(Double.parseDouble(soluong[1])), " "};
						
						for(int j = 0; j < 7; j++)
						{
							cells = new PdfPCell(new Paragraph(arr2[j], new Font(bf, 8, Font.NORMAL)));
							if(j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							if(j == 0)
								cells.setColspan(2);
							
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);
							cells.setBorder(0);
							
							sanpham.addCell(cells);
						}
						
						String[] arr3 = new String[]{" ", "Còn lại", " ", " ", " ", formatter.format(Double.parseDouble(soluong[2])), " "};
						
						for(int j = 0; j < 7; j++)
						{
							cells = new PdfPCell(new Paragraph(arr3[j], new Font(bf, 8, Font.NORMAL)));
							if(j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							if(j == 0)
								cells.setColspan(2);
							
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);
							cells.setBorder(0);
							
							sanpham.addCell(cells);
						}
						
						cells = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
						cells.setColspan(8);
						cells.setPadding(2.0f);
						cells.setBorder(0);
						sanpham.addCell(cells);
						
						stt++;
					}
				}
			}	
			
			
			/*space = new PdfPCell(new Paragraph("Picker. . . . . . . . . . . .  ____________________________________", new Font(bf, 8, Font.NORMAL)));
			space.setColspan(4);
			space.setPaddingTop(60.0f);
			space.setBorder(0);
			sanpham.addCell(space);
			
			space = new PdfPCell(new Paragraph("Controlller.  _____________________________________________", new Font(bf, 8, Font.NORMAL)));
			space.setColspan(4);
			space.setPaddingTop(60.0f);
			space.setBorder(0);
			sanpham.addCell(space);*/
			
			document.add(sanpham);
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}

	
	private Image getLogoImage() {
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
					break;
				}
			} catch (Exception e) {	
				System.out.println("[ErpYeucaunguyenlieuPdfSvl.CreatePdf] error message = " + e.getMessage());
			}
		}
		return logoImage;
	}
	
	private void CreatePdf(ServletOutputStream outstream, IErpYeucaunguyenlieu ycnlBean) throws IOException {
		Rectangle pageSize = new Rectangle(PageSize.A3.getWidth(), PageSize.A3.getHeight()/2);
		float PAGE_LEFT = 1.25f*CM, PAGE_RIGHT = 1.25f*CM, PAGE_TOP = 0.75f*CM, PAGE_BOTTOM = 0.0f*CM; //cm
		Document document = new Document(pageSize, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		float imageWidth = 2.0f*CM;
		
		int currentSpId = 0;
		int currentPage = 0;
		int numSps = ycnlBean.getSpList().size();
		int NUM_SANPHAM_PER_PAGE = 3;
		int NUM_SPCT_PER_PAGE = 10;
		
		//Phụ liệu
		int numSpPl = ycnlBean.getSpChoNhapList().size();
		int NUM_SANPHAMPL_PER_PAGE = 10;
		
		long numPages = Math.round(Math.ceil((double)numSps/NUM_SANPHAM_PER_PAGE)) + Math.round(Math.ceil((double)numSpPl/NUM_SANPHAMPL_PER_PAGE));		
		
		try
		{
			
			dbutils db=new dbutils();
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font fontHeader = new Font(bf, 24, Font.BOLD);
			Font font = new Font(bf, 12, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font fontbold = new Font(bf, 12, Font.BOLD);
			Font fontSmall = new Font(bf, 10, Font.NORMAL);
			Font fontSmallBold = new Font(bf, 10, Font.BOLD);
			
			//SIZE 27
			float[] TABLE_HEADER_WIDTHS = {4.0f * CM, 17.0f * CM, 6.0f * CM};
			float[] TABLE_MIDDLE_WIDTHS = {4.0f * CM, 17.0f * CM, 6.0f * CM };
			float[] TABLE_SANPHAMMALON_WIDTHS = {8.0f * CM, 8.0f * CM, 6.0f * CM, 5.0f * CM,6.0f * CM };
			float[] TABLE_SANPHAMMACT_WIDTHS = {9.0f * CM, 9.0f * CM, 9.0f * CM, };
			float[] TABLE_SANPHAM_WIDTHS = {1.0f * CM, 3.2f * CM, 3.0f * CM, 1.8f * CM, };
			float[] TABLE_FOOTER_WIDTHS = {7.0f * CM, 7.5f * CM, 7.0f * CM, 7.5f * CM, };
			

			//Align
			int[] TABLE_SANPHAMMALON_ALIGNS = {Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER,Element.ALIGN_CENTER, };
			int[] TABLE_SANPHAM_ALIGNS = {Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_LEFT, };
			Font[] TABLE_SANPHAMMALON_FONTS = {fontbold, fontbold, fontbold, fontbold,fontbold, };
			
			//Ve cac trang pdf
			currentPage++;
		
			//VẼ khung header (Logo Picture | Header Titles)
			PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			headerTable.setWidths(TABLE_HEADER_WIDTHS);
			headerTable.setWidthPercentage(100);
			
			PdfPCell cell;
			
			Image logoImage = getLogoImage();
			if(logoImage != null) {
				logoImage.setBorder(0);
				logoImage.setAlignment(Element.ALIGN_CENTER);
				logoImage.scaleToFit(imageWidth, imageWidth);
				float paddingLeft = (TABLE_HEADER_WIDTHS[0] - imageWidth)/2;
				float paddingTop = (2.5f*CM - imageWidth)/2;
				logoImage.setAbsolutePosition(PAGE_LEFT + paddingLeft, pageSize.getHeight() - PAGE_TOP - imageWidth - paddingTop);
				
				document.add(logoImage);
			}
			
			cell = new PdfPCell(new Paragraph(" ", font));
			cell.setFixedHeight(2.5f*CM);
			headerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("PHIẾU YÊU CẦU\nXUẤT VẬT TƯ", fontHeader));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setRowspan(2);
			headerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Loại: Biểu mẫu\nMã số: BM-PXGN-026\nSoát xét: 02\nĐiều chỉnh: 00\nSố trang: " + currentPage + "/" + numPages, font));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(0.5f * CM);
			cell.setRowspan(2);
			headerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("MPP: ", font));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingLeft(0.2f * CM);
			headerTable.addCell(cell);
			
			document.add(headerTable);
			
			
			//MIDDLE
			PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
			middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
			middleTable.setWidthPercentage(100);
			middleTable.setSpacingBefore(0.5f * CM);
			
			cell = new PdfPCell(new Paragraph("Ngày: " + getVnDateTime(ycnlBean.getNgayyeucau()) + "\nSố phiếu: " + ycnlBean.getId(), font));
			cell.setBorderWidth(1);
			cell.setPadding(5.0f);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("     Bộ phận : " + ycnlBean.getKhoXuatId() + "\n     Đề nghị phòng: " + ycnlBean.getKhoNhapId() + " xuất vật tư như sau:", fontbold));
			cell.setBorder(0);
			cell.setPadding(5.0f);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số P/O:"+ycnlBean.getLydoyeucau()+"\nSố tờ: ...........", font));
			cell.setBorderWidth(1);
			cell.setPadding(5.0f);
			middleTable.addCell(cell);

			
			document.add(middleTable);
			
			//SANPHAM MÃ LỚN
			PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAMMALON_WIDTHS.length);
			sanPhamTable.setWidths(TABLE_SANPHAMMALON_WIDTHS);
			sanPhamTable.setWidthPercentage(100);
			sanPhamTable.setSpacingBefore(0.5f * CM);
			
			String[] arr = new String[] { 
				"NGUYÊN VẬT LIỆU", 
				"QUY CÁCH / SIZE",
				"NHÀ SẢN XUẤT",  
				"SỐ LƯỢNG YÊU CẦU",
				"GHI CHÚ"
			};
			for(int j = 0; j < arr.length; j++)
			{
				cell = new PdfPCell(new Paragraph(arr[j], TABLE_SANPHAMMALON_FONTS[j]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(5.0f);
				 
				sanPhamTable.addCell(cell);
			}
			
			String[] chiTietTitles = {"CHI TIẾT GIẤY", "CHI TIẾT NHÔM", "CÁC LOẠI KHÁC"};
			

			for(int i = 0; i < ycnlBean.getSpList().size(); i++)
			{
				IYeucau sp = (IYeucau)ycnlBean.getSpList().get(i);
				
				double slyc = 0;
				try { slyc = Double.parseDouble(sp.getSoluongYC()); } catch(Exception e) { }
				
				arr = new String[] { 
					sp.getTen().toUpperCase(), 
					sp.getQuyCach(), 
					sp.getNguonGoc(),  
					formatter.format(slyc) + sp.getDonViTinh(),
					sp.getDiengiai()
				};
				
				for(int j = 0; j < arr.length; j++)
				{
					cell = new PdfPCell(new Paragraph(arr[j], font11));
					cell.setHorizontalAlignment(TABLE_SANPHAMMALON_ALIGNS[j]);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(3.0f);
					
					sanPhamTable.addCell(cell);
				}
			}
			
			//Lý do
			arr = new String[] { "GHI CHÚ", ycnlBean.getLydoyeucau() };
			for(int j = 0; j < arr.length; j++)
			{
				cell = new PdfPCell(new Paragraph(arr[j], TABLE_SANPHAMMALON_FONTS[j]));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(3.0f);
				if(j == 1) cell.setColspan(3);
				sanPhamTable.addCell(cell);
			}
			
			document.add(sanPhamTable);
			
			//3 cột SANPHAM CT
			PdfPTable sanPhamCtTable = new PdfPTable(TABLE_SANPHAMMACT_WIDTHS.length);
			sanPhamCtTable.setWidths(TABLE_SANPHAMMACT_WIDTHS);
			sanPhamCtTable.setWidthPercentage(100);
			sanPhamCtTable.setSpacingBefore(0.5f * CM);
			
			PdfPCell cells;
			int soDongChiTiet = 12;
			
			for(int _z = 0; _z < chiTietTitles.length; _z++) {
				//int soDongChiTiet = 12 - ycnlBean.getSpList().size();
				
				cells = new PdfPCell();
				cells.setBorder(0);
				cells.setPaddingLeft(5.0f);
				cells.setPaddingRight(5.0f);
				
				sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				
				cell = new PdfPCell(new Paragraph(chiTietTitles[_z], fontbold));
				cell.setColspan(TABLE_SANPHAM_WIDTHS.length);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				sanPhamTable.addCell(cell);
			
				
				String[] spTitles = {"STT", "MÃ SỐ", "TRỌNG LƯỢNG", "GHI CHÚ"};
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], fontSmallBold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CM);
					sanPhamTable.addCell(cell);
				}
				
				
			/*	
			 * Bỏ phẩn in ra chi tiết
			 * 
			    String query="";
				if(_z==0){
					query=" SELECT ISNULL(MASO,'') AS MASO, SOLUONG,ISNULL(GHICHU,'') AS GHICHU FROM  ERP_PXKNL_GHICHU CT "+
						  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CT.SANPHAM_FK "+
						  " WHERE PXK_FK="+ycnlBean.getId()+" AND SP.LOAISANPHAM_FK=100013";
				}else if(_z==1){
					query=" SELECT ISNULL(MASO,'') AS MASO, SOLUONG,ISNULL(GHICHU,'') AS GHICHU FROM  ERP_PXKNL_GHICHU CT "+
					  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CT.SANPHAM_FK "+
					  " WHERE PXK_FK="+ycnlBean.getId()+" AND SP.LOAISANPHAM_FK=100014";
				}else{
					query=" SELECT ISNULL(MASO,'') AS MASO, SOLUONG,ISNULL(GHICHU,'') AS GHICHU FROM  ERP_PXKNL_GHICHU CT "+
					  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CT.SANPHAM_FK "+
					  " WHERE PXK_FK="+ycnlBean.getId()+" AND SP.LOAISANPHAM_FK not in (100014,100013)";
				}
				int bien_tmp=0;
				double tongsoluong=0;
				ResultSet rsct=db.get(query);
				try{
						while (rsct.next()){
							spTitles = new String[] {"",rsct.getString("maso"),formatter.format(rsct.getDouble("SOLUONG")), rsct.getString("ghichu")};
							bien_tmp++;
							tongsoluong=tongsoluong+ rsct.getDouble("SOLUONG");
							for(int z= 0; z < spTitles.length ; z++)
							{
								cell = new PdfPCell(new Paragraph(spTitles[z], fontSmall));
								cell.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[z]);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setPadding(0.15f * CM);
								sanPhamTable.addCell(cell);
							}
						}
				}catch(Exception er){
					er.printStackTrace();
				}
				soDongChiTiet=soDongChiTiet-bien_tmp;*/
				//Các ô sản phẩm
				for(int ict = 0; ict < soDongChiTiet; ict++) {
					spTitles = new String[] {" ", "", "", ""};

					for(int z= 0; z < spTitles.length ; z++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[z], fontSmall));
						cell.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[z]);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CM);
						sanPhamTable.addCell(cell);
					}
				}
				
				//Ô tổng cộng
				spTitles = new String[] {"CỘNG ","", " "};
				
				for(int z = 0; z < spTitles.length ; z++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[z], fontSmallBold));
					cell.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[z+1]);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CM);
					if(z==0) { cell.setColspan(2); }
					sanPhamTable.addCell(cell);
				}
				
				cells.addElement(sanPhamTable);
				sanPhamCtTable.addCell(cells);
			}
			
			document.add(sanPhamCtTable);
			
			
			
			//FOOTER
			PdfPTable footerTable = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
			footerTable.setWidths(TABLE_FOOTER_WIDTHS);
			footerTable.setWidthPercentage(100);
			footerTable.setSpacingBefore(0.5f*CM);
			
			cell = new PdfPCell(new Paragraph("Người lập phiếu", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("P. Tổng Giám Đốc 2", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Thủ Kho NVL", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Người Nhận", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);

			document.add(footerTable);
 
			document.close();
			
			ycnlBean.DBclose();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}


	private void CreatePhieuChuyenNL(Document document, ServletOutputStream outstream, IErpYeucaunguyenlieu ycnlBean) throws IOException
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			 
			Paragraph pxk = new Paragraph("Số phiếu..: " + ycnlBean.getId(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("PHIẾU CHUYỂN NGUYÊN LIỆU", font);
			pxk.setSpacingAfter(10);
			pxk.setSpacingBefore(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
		
			PdfPTable sanpham = new PdfPTable(8);
			sanpham.setKeepTogether(false);
			sanpham.setSplitLate(false);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = {35.0f, 10.0f, 15.0f, 15.0f, 15.0f, 15.0f, 12.0f, 10.0f};
			sanpham.setWidths(withsKM);
			
			String[] th = new String[]{"Sản phẩm", "Số lô", " ", "Kho chuyển", "Khu", "Bean", "Số lượng", "Đơn vị"};
			PdfPCell[] cell = new PdfPCell[8];
			for(int i= 0; i < 8 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 9, Font.NORMAL)));
				cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				
				if(i >= 6)
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBorder(0);
				cell[i].setPadding(4);
				
				sanpham.addCell(cell[i]);	
			}
		    
			PdfPCell space = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			space.setColspan(8);
			space.setPadding(2.0f);
			space.setBorderWidthLeft(0);
			space.setBorderWidthRight(0);
			space.setBorderWidthTop(0);
			space.setBorderWidthBottom(0.8f);
			sanpham.addCell(space);
			
			space = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			space.setColspan(8);
			space.setPadding(2.0f);
			space.setBorder(0);
			sanpham.addCell(space);
			
			List<IYeucau> spList = ycnlBean.getSpList();
			PdfPCell cells = new PdfPCell();

			int stt = 0;
			for(int i = 0; i <= spList.size(); i++)
			{
				if(i < spList.size())
				{
					IYeucau sp = (IYeucau)spList.get(i);
					List<ISpDetail> spDetail = sp.getSpDetailList();
					for(int k = 0; k < spDetail.size(); k++)
					{
						ISpDetail detail = spDetail.get(k);
						
						String[] soluong = detail.getSoluong().split(" - "); //0 - soluong yeu cau, 1 - soluong nhap, 2 - con lai
						
						String[] arr = new String[]{sp.getMa(), detail.getSolo(), "Yêu cầu", " ", " ", " ", 
								formatter.format(Double.parseDouble(sp.getSoluongYC())), detail.getMa()};
						
						for(int j = 0; j < 8; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
							if(j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);
							cells.setBorder(0);
							
							sanpham.addCell(cells);
						}
						
						String[] arr2 = new String[]{sp.getTen(), "Chuyển", detail.getVitriId(), detail.getKhu(), detail.getVitri(), formatter.format(Double.parseDouble(soluong[1])), " "};
						
						for(int j = 0; j < 7; j++)
						{
							cells = new PdfPCell(new Paragraph(arr2[j], new Font(bf, 8, Font.NORMAL)));
							if(j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							if(j == 0)
								cells.setColspan(2);
							
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);
							cells.setBorder(0);
							
							sanpham.addCell(cells);
						}
						
						String[] arr3 = new String[]{" ", "Còn lại", " ", " ", " ", formatter.format(Double.parseDouble(soluong[2])), " "};
						
						for(int j = 0; j < 7; j++)
						{
							cells = new PdfPCell(new Paragraph(arr3[j], new Font(bf, 8, Font.NORMAL)));
							if(j >= 5)
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							if(j == 0)
								cells.setColspan(2);
							
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);
							cells.setBorder(0);
							
							sanpham.addCell(cells);
						}
										
						cells = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
						cells.setColspan(8);
						cells.setPadding(2.0f);
						cells.setBorder(0);
						sanpham.addCell(cells);
						
						stt++;
					}
				}
			}	
			
			document.add(sanpham);
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}

	
	private String getVnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "/" + thang + "/" + nam;
		} else {
			return "";
		}
	}

}
