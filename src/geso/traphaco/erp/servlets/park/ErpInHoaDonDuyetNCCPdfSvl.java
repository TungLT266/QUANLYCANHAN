package geso.traphaco.erp.servlets.park;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.park.imp.ErpPark;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.BorderLineStyle;

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
import com.cete.dynamicpdf.pageelements.CellBorderStyle;
/*import com.aspose.cells.Font;*/

import com.google.gson.annotations.Until;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;





public class ErpInHoaDonDuyetNCCPdfSvl extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
    public ErpInHoaDonDuyetNCCPdfSvl() {
        super();
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
			session.setMaxInactiveInterval(30000);
		
			/*this.out = response.getWriter();*/
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 	
		    
		    
		    String id = util.getId(querystring); 
		    if (id.length()==0)
		    	id = util.antiSQLInspection(request.getParameter("id")); 	
		    
		    String type= util.antiSQLInspection(request.getParameter("type")); 	
		    String loaihd= util.antiSQLInspection(request.getParameter("loaihd"));
		    
			IErpPark pBean = new ErpPark(id);
	        pBean.setUserId(userId); //phai co UserId truoc khi Init
	    	pBean.setCtyId((String)session.getAttribute("congtyId"));
	    	pBean.setnppdangnhap(util.getIdNhapp(userId));
	    	
	    	if(type.equals("3")){
	    		// xuat excel
	    		try {
		    		response.setContentType("application/vnd.ms-excel");
			        response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");
		    		CreateExcel( response,pBean, type, loaihd) ;
				} catch (Exception e) {
					e.printStackTrace();
					pBean.setMsg("Không thể tạo báo cáo - " + e.getMessage());
				}
	    	}
	    	else{
	    		// xuat pdf
	    	create_PNK_PDF(response, request, pBean, type, loaihd);
	    	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	
	private void create_PNK_PDF(HttpServletResponse response,
			HttpServletRequest request, IErpPark pBean, String type ,String loaihd ) {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=Traphaco_PhieuNhapThanhPham.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 1.5f * CONVERT, PAGE_RIGHT = 1.0f * CONVERT, PAGE_TOP = 1.0f * CONVERT, PAGE_BOTTOM = 1.0f * CONVERT;
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT,
				PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			pBean.init();
			CreatePO_Training(document, outstream, response, request, db, pBean, type,loaihd );
			
			document.close();
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}
		
	}

	private void CreatePO_Training(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db, IErpPark pBean, String type, String loaihd) {
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

		//________________________________________________

		NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter_4sole = new DecimalFormat("#,###,###.####");
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formater = new DecimalFormat("##,###,###.##");
		NumberFormat formatersl = new DecimalFormat("##,###,###.####");

		try {
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10v1 = new Font(bf, 8, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font10_ita = new Font(bf, 10, Font.ITALIC);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_ita = new Font(bf, 11, Font.ITALIC);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font14_bold = new Font(bf, 14, Font.BOLD);
			
			Font font9_ita = new Font(bf, 9, Font.ITALIC);
			Font font9_bold = new Font(bf, 9, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			
			PdfPCell cellss;
			Chunk chunk;

			//Header 2
			Paragraph pr;

					PdfPTable table = new PdfPTable(2);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setWidthPercentage(100);
					table.setSpacingBefore(3.0f);
					table.setSpacingAfter(4.0f);
					
					// insert logo
					Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint")+ "\\logo.gif");
					img.scalePercent(13);
				/*	img.setAbsolutePosition(3f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);*/
//					img.setAbsolutePosition(1.9f * CONVERT, document.getPageSize().getHeight() - 1.9f * CONVERT);
					img.setAbsolutePosition(2.3f * CONVERT, document.getPageSize().getHeight() - 1.9f * CONVERT);
					document.add(img);

					PdfPCell cell;
					cell = new PdfPCell();
					Paragraph p = new Paragraph();
					p = new Paragraph();
					p.add("\n");
					cell.setBorder(0);

	//				p.add(new Chunk("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font10)); 
					p.add(new Chunk("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font10v1)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.addElement(p);
					table.addCell(cell);

					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("", font11_ita)); 
					p.setAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.addElement(p);
					table.addCell(cell);

					document.add(table);

					pr = new Paragraph("PHIẾU NHẬP KHO", new Font(bf, 22, Font.BOLD));
					pr.setSpacingBefore(5);
					pr.setSpacingAfter(3);
					pr.setAlignment(Element.ALIGN_CENTER);
					document.add(pr);

					
					//-- ngay + so chung tu
					/*String[] thongTin = pBean.initPNK_StringArray();
					pr = new Paragraph("                   Ngày "+thongTin[0].substring(8,10)+" tháng "+thongTin[0].substring(5,7)+"   năm   "+thongTin[0].substring(0,4)+"          Số: "+(thongTin[1].equals("0") ? "" : thongTin[1]) , new Font(bf, 9, Font.BOLD));
					pr.setSpacingBefore(0);
					pr.setSpacingAfter(10);
					pr.setAlignment(Element.ALIGN_CENTER);
					document.add(pr);*/
					
					table=new PdfPTable(2);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setWidthPercentage(100);
					float arr[]={10.8f*CONVERT, 7.2f*CONVERT};
					table.setWidths(arr);
					
					String[] thongTin = pBean.initPNK_StringArray();
					cell = new PdfPCell( new Paragraph("           Ngày  "+thongTin[0].substring(8,10)+"  tháng  "+thongTin[0].substring(5,7)+"  năm  "+thongTin[0].substring(0,4), font9_ita));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setBorder(0);
					table.addCell(cell);
					
					cell = new PdfPCell( new Paragraph("Số:       "+(thongTin[1].equals("0") ? "   " : thongTin[1]), font9_bold));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setBorder(0);
					table.addCell(cell);
					
					document.add(table);
					
					
					
					table=new PdfPTable(2);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					float arr1[]={4.3f*CONVERT, 14.7f*CONVERT};
					table.setWidths(arr1);
					table.setWidthPercentage(100);
					
					// dong 1
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.addElement(new Paragraph("Họ và tên người mua hàng:", font9_bold));
					table.addCell(cell);
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.addElement(new Paragraph(thongTin[2].trim(), font9));
					table.addCell(cell);
					
					// dong 2
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph("Tên NCC:", font9_bold));
					table.addCell(cell);
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph(thongTin[3].trim(), font9));
					table.addCell(cell);
					
					
					//dong 3
					
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph("Địa chỉ:", font9_bold));
					table.addCell(cell);
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph(thongTin[4].trim(), font9));
					table.addCell(cell);
					
					
					
					// dong 4
					
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph("Số hóa đơn:", font9_bold));
					table.addCell(cell);
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(-0.1f*CONVERT);
					pr = new Paragraph();
					String ngayHoaDon = thongTin[7].substring(8,10)+"/"+thongTin[7].substring(5,7)+"/"+thongTin[7].substring(0,4);
					chunk =new Chunk(thongTin[5].trim(), font9);
					pr.add(chunk);
					
					chunk =new Chunk("                             Seri:  ", font9_bold);
					pr.add(chunk);
					chunk =new Chunk(thongTin[6].trim(), font9);
					pr.add(chunk);
					
					chunk =new Chunk("               Ngày:    ", font9_bold);
					pr.add(chunk);
					chunk =new Chunk(ngayHoaDon.trim(), font9);
					pr.add(chunk);
					cell.addElement(pr);	
					cell.setBorder(0);
					table.addCell(cell);
					
					//dong 5
					
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph("Nội dung:", font9_bold));
					table.addCell(cell);
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph(thongTin[8].trim(), font9));
					table.addCell(cell);
					
					

					
					//dong 6
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph("Nhập tại kho:", font9_bold));
					table.addCell(cell);
					
					cell = new PdfPCell( );
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setPaddingTop(-0.1f*CONVERT);
					cell.addElement(new Paragraph(thongTin[10].trim(), font9));
					table.addCell(cell);
					
					document.add(table);
					
					
					
					
					
					
					
					
		
					// Data table => Dựa vào loại kho mà chọn cặp crPhieuNhap-spTitles phù hợp

					// 1. Kho phụ liệu cấp 1
					float[] crPhieuNhap1 = { 1.25f * CONVERT, 3.75f * CONVERT, 1.25f * CONVERT,
							1.25f * CONVERT, 1.25f * CONVERT, 1.75f * CONVERT, 2.0f * CONVERT};

					int socot = 0;
					socot = crPhieuNhap1.length;


					table = new PdfPTable(socot);
					table.setWidthPercentage(100);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setSpacingBefore(0.5f*CONVERT);
					
					table.setSpacingAfter(8.0f);
					



					String[] spTitles1 = { "Mã vật tư", "Tên vật tư","TK", "ĐVT", "Số lượng", "Đơn giá",
					"Thành tiền"};

					table.setWidths(crPhieuNhap1);
					//in tieu de
					for (int z = 0; z < spTitles1.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles1[z], font9_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						//cell.setBorderWidth(0.5f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						
						
						cell.setBorderWidthBottom(0.0f);
				        if(z==0)
				        {
					        cell.setBorderWidthTop(0.5f);
					        cell.setBorderWidthLeft(0.5f);
					        cell.setBorderWidthRight(0.0f);
				        }
					        else
				        	if(z== spTitles1.length-1){
						        cell.setBorderWidthTop(0.5f);
						        cell.setBorderWidthLeft(0.5f);
						        cell.setBorderWidthRight(0.5f);
				        	}
				        	 else 
						        	{
								        cell.setBorderWidthTop(0.5f);
								        cell.setBorderWidthLeft(0.5f);
								        cell.setBorderWidthRight(0.0f);
							        }
						table.addCell(cell);
					}
					
					
					
					Double tongTienHang = 0.0;
					Double tongVAT = 0.0;
					Double tongTienThanhToan = 0.0;
					NumberFormat formatter1 = new DecimalFormat("#,###,###");
					List<ISanpham> spList = pBean.initPNK_HD_SP();	
					if (spList.size() > 0) {
						for (ISanpham sp : spList) {
							String maKho=sp.getMa();
							String spTen=sp.getDiengiai();
							String donVi="";
							String taiKhoan=sp.getTaiKhoan();
							double soLuong=sp.getSoLuong2();
							Double donGia = Double.parseDouble(sp.getDongiaViet());
							System.out.println("Don gia: "+donGia);
							Double thanhTien= Double.parseDouble(sp.getThanhtienVND());
							System.out.println("Don gia1: "+thanhTien);
							tongTienHang += thanhTien;
							tongVAT += Double.parseDouble(sp.getThanhtienVATVND());
							
							System.out.println(" loai hd: "+ loaihd);
							// nhập khẩu và trog nươc dung don vi pk_seq
							if(  !loaihd.equals("0")  && !loaihd.equals("1") ){
								donVi=sp.getDvdl();
							}
							else {
									String sql="SELECT DONVI FROM DONVIDOLUONG WHERE PK_SEQ="+ sp.getDvdl();
									ResultSet rs=db.get(sql);
									if(rs!=null){
										try {
											while(rs.next()){
												donVi=rs.getString("DONVI")==null?"": rs.getString("DONVI");
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
							}
							
							String[] sp_data1={maKho, spTen, taiKhoan, donVi, formatersl.format(soLuong), formatter_4sole.format(donGia), formatter1.format(thanhTien)};
					
							for (int z = 0; z < sp_data1.length; z++) {
								cell = new PdfPCell(new Paragraph(sp_data1[z],new Font(bf, 9,Font.NORMAL)));
								cell.setPadding(3.0f);
								cell.setPaddingBottom(7);

								if(z==0 || z==1){
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								}
								else
									if(z>=4){
										cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
									else
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								//cell.setBorderWidth(0.5f);
								
								
								
								cell.setBorderWidthBottom(0.0f);
							
								
								if(z==0)
						        {
							        cell.setBorderWidthTop(0.5f);
							        cell.setBorderWidthLeft(0.5f);
							        cell.setBorderWidthRight(0.0f);
							    	
						        }
							        else
						        	if(z== spTitles1.length-1){
								        cell.setBorderWidthTop(0.5f);
								        cell.setBorderWidthLeft(0.5f);
								        cell.setBorderWidthRight(0.5f);
								       
						        	}
						        	 else 
								        	{
										        cell.setBorderWidthTop(0.5f);
										        cell.setBorderWidthLeft(0.5f);
										        cell.setBorderWidthRight(0.0f);
									        }
								

								table.addCell(cell);
							}
							
						}
						
						
						
						
						if(loaihd.equals("1")){ // neu la trong nuoc thi lay tien trong du lieu CHO GIONG DMS (dong bo)
							String qr ="select ISNULL(sotienBVAT,0)sotienBVAT,ISNULL(sotienAVAT,0)sotienAVAT, ISNULL(VAT,0)VAT from ERP_HOADONNCC WHERE  PARK_FK='"+pBean.getId() +"'";
							System.out.println(" XXX : "+qr);
							ResultSet rs = db.get(qr);
							if(rs!= null){
								while (rs.next()) {
									 tongTienHang =rs.getDouble("sotienBVAT");
									 tongVAT = rs.getDouble("VAT");
									 tongTienThanhToan =rs.getDouble("sotienAVAT");
								}
							}
						}
						else
						{
							tongTienThanhToan = tongTienHang + tongVAT;
						}
						
						
						Double soTien = 0.0;
						//Tong cong
						String[] spTongCong = {"Tổng cộng tiền hàng", "Chi phí", "Thuế giá trị gia tăng"};
						for (int z = 0; z < spTongCong.length; z++) {
							if (z == 0) {
								soTien = tongTienHang;
							}else if (z == 2){
								soTien = tongVAT;
							} else {
								soTien = 0.0;
								
							}
						
							cell = new PdfPCell(new Paragraph(spTongCong[z], font9));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        cell.setColspan(6);
					        cell.setBorderWidthBottom(0.0f);
					        cell.setBorderWidthLeft(0.5f);
					        cell.setBorderWidthRight(0.0f);
					          cell.setBorderWidthTop(0.0f);
					        if(z==2){
					        	 cell.setBorderWidthBottom(0.5f);
					        }
					        if( z==0){
					        	  cell.setBorderWidthTop(0.5f);
					        }
							table.addCell(cell);
							
							cell = new PdfPCell(new Paragraph(formatter1.format(soTien), font9));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorderWidthBottom(0.0f);
						    cell.setBorderWidthTop(0.0f);
						    cell.setBorderWidthLeft(0.5f);
					        cell.setBorderWidthRight(0.5f);
					        if(z==2){
					        	 cell.setBorderWidthBottom(0.5f);
					        }
					        if( z==0){
					        	  cell.setBorderWidthTop(0.5f);
					        }
							table.addCell(cell);
						}
						cell.setBorderColorTop(BaseColor.BLACK);
						cell = new PdfPCell(new Paragraph("Tổng cộng tiền thanh toán", font9_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        cell.setColspan(6);
				       // cell.setBorderWidth(0.5f);	
				        cell.setBorderWidthTop(0.0f);
				        cell.setBorderWidthLeft(0.5f);
				        cell.setBorderWidthRight(0.0f);
				        cell.setBorderWidthBottom(0.5f);
						table.addCell(cell);
						
						cell = new PdfPCell(new Paragraph(formatter1.format(tongTienThanhToan), font9_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						/*cell.setBorderWidth(0.5f);*/
						cell.setBorderWidthBottom(0.5f);
					    cell.setBorderWidthTop(0.0f);
					    cell.setBorderWidthLeft(0.5f);
				        cell.setBorderWidthRight(0.5f);
						
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

						
						
						DocTien docTien = new DocTien();
						String tienchu = docTien.docTien((long)tongTienThanhToan.doubleValue());
						
						cell = new PdfPCell(new Paragraph("Bằng chữ: "+ tienchu, font9_ita));
						cell.setPadding(3.0f);
						cell.setColspan(7);
						cell.setBorder(0);
						cell.setBorderWidth(0.5f);
						cell.setPaddingBottom(7);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);
					//table.getDefaultCell().setBorder(1);
					document.add(table);

					
				
					float[] wky = {9.5f*CONVERT, 9.0f*CONVERT, 8.6f*CONVERT,5.3f*CONVERT };
					PdfPTable tbl_vat = new PdfPTable(wky.length);
					tbl_vat.setWidthPercentage(100);
					tbl_vat.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbl_vat.getDefaultCell().setBorder(0);
					tbl_vat.setWidths(wky);

					cellss = new PdfPCell(new Paragraph("", font9_bold));
					cellss.setPaddingLeft(0.3f*CONVERT);
					
					if(type.equals("1")){
					chunk =new Chunk("        T/L Giám đốc",font9_bold);
					}
					else{
						chunk =new Chunk("    T/L Giám đốc", font9_bold);
						
					}
					cellss.addElement(chunk);
					cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellss.setBorder(0);
					tbl_vat.addCell(cellss);


					cellss = new PdfPCell(new Paragraph("", font9_bold));
					chunk =new Chunk("Kế toán trưởng", font9_bold);
					cellss.addElement(chunk);
					cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellss.setBorder(0);
					tbl_vat.addCell(cellss);

					cellss = new PdfPCell(new Paragraph("", font9_bold));
					chunk =new Chunk("Người lập phiếu", font9_bold);
					cellss.addElement(chunk);
					cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellss.setBorder(0);
					tbl_vat.addCell(cellss);

					cellss = new PdfPCell(new Paragraph("",font9_bold));
					chunk =new Chunk("Thủ kho", font9_bold);
					cellss.addElement(chunk);
					cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellss.setBorder(0);
					tbl_vat.addCell(cellss);
					document.add(tbl_vat);
					
				}
			


		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	
	


	private void CreateExcel(HttpServletResponse response,IErpPark pBean, String type, String loaihd) throws Exception {
		 OutputStream out = response.getOutputStream();
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\PHIEUNHAPKHO_DUYETHOADONNCC.xlsm");

			
			dbutils db = new dbutils();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Font font = new Font();
			
			worksheet.setName("PHIEUNHAPKHO");
			
			Cells cells = worksheet.getCells();
			
			//dien thong tin 
		/*	cells.setRowHeight(3, 18.0f);*/
			
			
			String[] thongTin = pBean.initPNK_StringArray();
			//ngay
			Cell cell = cells.getCell("A5");
			getCellStyle(cell,HorizontalAlignmentType.CENTRED, Color.BLACK,false, true, 9,"                             Ngày "+thongTin[0].substring(8,10)+" tháng "+thongTin[0].substring(5,7)+"   năm   "+thongTin[0].substring(0,4),0 );
	

			//so
			cell = cells.getCell("J5");
			getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, true,false, 9, "Số: "+(thongTin[1].equals("0") ? "" : thongTin[1]),0);
			
			//seri
			cell = cells.getCell("F10");
			getCellStyle(cell,HorizontalAlignmentType.LEFT, Color.BLACK, false,false, 9, thongTin[6] ,0);
			
			//ngayso
			String ngayHoaDon = thongTin[7].substring(8,10)+"/"+thongTin[7].substring(5,7)+"/"+thongTin[7].substring(0,4);
			cell = cells.getCell("H10");
			getCellStyle(cell,HorizontalAlignmentType.LEFT, Color.BLACK, false,false, 9, ngayHoaDon ,0);
			
			int noidung=2;
			// thong tin day D
			for (int i = 7; i <= 10; i++) {
				cell = cells.getCell("D"+ i);
				getCellStyle(cell,HorizontalAlignmentType.LEFT, Color.BLACK,false,false, 9, thongTin[noidung],0);
				noidung++;
			}
			noidung=8;
			for (int i = 11; i <= 13; i++) {
				cell = cells.getCell("D"+ i);
				getCellStyle(cell,HorizontalAlignmentType.LEFT, Color.BLACK, false,false, 9, thongTin[noidung],0);
				noidung++;
			}
			
			
			
			Double tongTienHang = 0.0;
			Double tongVAT = 0.0;
			Double tongTienThanhToan = 0.0;
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter_4sole = new DecimalFormat("#,###,###.####");
			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formater = new DecimalFormat("##,###,###.##");
			int countRow = 15;
			int column = 0;
			try {
			
			List<ISanpham> spList = pBean.initPNK_HD_SP();	
			
			if (spList.size() > 0) {
				for (ISanpham sp : spList) {
					
					column = 0;
					
					String maKho=sp.getMa();
					String spTen=sp.getDiengiai();
					String donVi="";
					String taiKhoan=sp.getTaiKhoan();
					double soLuong=sp.getSoLuong2();
					Double donGia = Double.parseDouble(sp.getDongiaViet());
					Double thanhTien= Double.parseDouble(sp.getThanhtienVND());
					tongTienHang += thanhTien;
					tongVAT += Double.parseDouble(sp.getThanhtienVATVND());
					
					
					if(  !loaihd.equals("0")  && !loaihd.equals("1") ){
						donVi=sp.getDvdl();
					}
					else {		
							String sql="SELECT DONVI FROM DONVIDOLUONG WHERE PK_SEQ="+ sp.getDvdl();
							ResultSet rs=db.get(sql);
							if(rs!=null){
								try {
									while(rs.next()){
										donVi=rs.getString("DONVI")==null?"": rs.getString("DONVI");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
					}
					
					
					String[] sp_data1={maKho, spTen, taiKhoan, donVi, formatter_4sole.format(soLuong), formatter_4sole.format(donGia), formatter1.format(thanhTien)};
			
					
							
					for(int i =0;i <sp_data1.length ; i ++)
					{
						cell = cells.getCell(countRow,column + i );
					    
						if(i==1){
							cells.merge(countRow,column + i,countRow,column + i+2);
							
							for (int j = column + i; j <= column + i+2; j++) {
								cell = cells.getCell(countRow,j );
								setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false,false, 1,1,1,1);
								cell.setValue(sp_data1[i]);
							}
							
							column=column+2;
						}
						
						if(i==5 || i==6){
							cells.merge(countRow,column + i,countRow,column + i+1);
							
							for (int j = column + i; j <= column + i+1; j++) {
								cell = cells.getCell(countRow,j );
								setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,false,1,1,1,1);
								cell.setValue(sp_data1[i]);
							}
							
							column=column+1;
							
						}
						
						
						cell.setValue(sp_data1[i]);
						if(i<=1){
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false,false,1,1,1,1);
							
						}else 
							if(i>=4){
								setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,false,1,1,1,1);
								
							} else{
							setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false,false,1,1,1,1);
						}							
					}
					countRow++;
					
					
				}
			}
			
			// in them 4 dong trang
			// them 4 dong
			int k=0;
			
			while(k<4){
				String[] sp_data1={" ", " ", " ", " ", " ", " ", " "};
				column=0;
				for(int i =0;i <sp_data1.length ; i ++)
				{
					cell = cells.getCell(countRow,column + i );
				    
					if(i==1){
						cells.merge(countRow,column + i,countRow,column + i+2);
						
						for (int j = column + i; j <= column + i+2; j++) {
							cell = cells.getCell(countRow,j );
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false,false,1,1,1,1);
							cell.setValue(sp_data1[i]);
						}
						
						column=column+2;
					}
					
					if(i==5 || i==6){
						cells.merge(countRow,column + i,countRow,column + i+1);
						
						for (int j = column + i; j <= column + i+1; j++) {
							cell = cells.getCell(countRow,j );
							setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,false,1,1,1,1);
							cell.setValue(sp_data1[i]);
						}
						
						column=column+1;
						
					}
					
					
					cell.setValue(sp_data1[i]);
					if(i<=1){
						setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false,false,1,1,1,1);
						
					}else 
						if(i>=4){
							setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,false,1,1,1,1);
							
						} else{
						setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false,false,1,1,1,1);
					}							
				}
				countRow++;
				k++;
			}
			
			
			if(loaihd.equals("1")){ // neu la trong nuoc thi lay tien trong du lieu CHO GIONG DMS (dong bo)
				String qr ="select ISNULL(sotienBVAT,0)sotienBVAT,ISNULL(sotienAVAT,0)sotienAVAT, ISNULL(VAT,0)VAT from ERP_HOADONNCC WHERE  PARK_FK='"+pBean.getId() +"'";
				System.out.println(" XXX : "+qr);
				ResultSet rs = db.get(qr);
				if(rs!= null){
					while (rs.next()) {
						 tongTienHang =rs.getDouble("sotienBVAT");
						 tongVAT = rs.getDouble("VAT");
						 tongTienThanhToan =rs.getDouble("sotienAVAT");
					}
				}
			}
			else
			{
				tongTienThanhToan = tongTienHang + tongVAT;
			}
			Double soTien = 0.0;
			//Tong cong
			String[] spTongCong = {"Tổng cộng tiền hàng", "Chi phí", "Thuế giá trị gia tăng", "Tổng cộng tiền thanh toán" };
			for (int z = 0; z < spTongCong.length; z++) {
				if (z == 0) {
					soTien = tongTienHang;
					
				}else if (z == 2){
					soTien = tongVAT;
					
				} else {
					soTien = 0.0;
					
				}
				
				
				if(z==3){ // in tien tong
					
					cells.setRowHeight(countRow +z, 17.25); 
					
					cell = cells.getCell(countRow +z, 0);
				    cells.merge(countRow +z, 0, countRow +z,8 );
				    for (int j =0; j <= 8; j++) {
						cell = cells.getCell(countRow +z,j );
						/*getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, true, 9,spTongCong[z], 1);*/
						
						setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,true,false,1,1,1,1);
						cell.setValue(spTongCong[z]);
					}
				    
					cell = cells.getCell(countRow +z, 9);
					cells.merge(countRow +z, 9, countRow +z,10 );
					/*getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9, formatter1.format(tongTienThanhToan) , 1);
					*/
					
					for (int j =9; j <= 10; j++) {
						cell = cells.getCell(countRow +z,j );
						//getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9, formatter1.format(tongTienThanhToan) , 1);
						setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,true,false,1,1,1,1);
						cell.setValue(formatter1.format(tongTienThanhToan));
						
					}
				}
				else { // in tien tung dong
					cells.setRowHeight(countRow +z, 17.25); 
					
					cell = cells.getCell(countRow +z, 0);
					cells.merge(countRow +z, 0, countRow +z,8 );
					 /* getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9,spTongCong[z], 1);*/
					 for (int j =0; j <= 8; j++) {
							cell = cells.getCell(countRow +z,j );
							//getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9,spTongCong[z], 1);
							setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,false,1,1,1,1);
							cell.setValue(spTongCong[z]);
					}
					
					
					
					cell = cells.getCell(countRow +z, 9);
					cells.merge(countRow +z, 9, countRow +z,10 );
					/*getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9, formatter1.format(soTien), 1);*/
					
					for (int j =9; j <= 10; j++) {
						cell = cells.getCell(countRow +z,j );
						//getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9, formatter1.format(soTien), 1);
						
						setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,false,1,1,1,1);
						cell.setValue(formatter1.format(soTien));
					}
				}
				
				
				
			}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			countRow =countRow +4;
			
			
			// doc tien 
			System.out.println(" count row: "+ countRow);
			DocTien docTien = new DocTien();
			String tienchu = docTien.docTien((long)tongTienThanhToan.doubleValue());
			cells.setRowHeight(countRow, 17.25); 
			cell = cells.getCell(countRow , 0);
		    cells.merge(countRow , 0, countRow ,10 );
		    
		    for (int j =0; j <= 10; j++) {
				cell = cells.getCell(countRow,j );
				//getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9, formatter1.format(tongTienThanhToan) , 1);
				setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false,true,1,1,1,1);
				cell.setValue("Bằng chữ: "+ tienchu);
				
			}
		    
			/*getCellStyle(cell,HorizontalAlignmentType.RIGHT, Color.BLACK, false, 9, "Bằng chữ: "+ tienchu, 1);*/
			
			
			
			
			// bang chu ký
													
			countRow =countRow+4;	
			System.out.println(" count row: "+ countRow);
			
			String[] chuki_tbl = {"T/L Giám đốc", "Kế toán trưởng", "Người lập phiếu", "Người lập phiếu"};
			
			cells.setRowHeight(countRow , 17.25); 
			cell = cells.getCell(countRow , 0);
		    cells.merge(countRow , 0, countRow,2);
			getCellStyle(cell,HorizontalAlignmentType.CENTRED, Color.BLACK, true,false, 9,"T/L Giám đốc", 0);
			
			
			cell = cells.getCell(countRow , 3);
		    cells.merge(countRow , 3, countRow,4);
			getCellStyle(cell,HorizontalAlignmentType.CENTRED, Color.BLACK, true,false, 9,"Kế toán trưởng", 0);
			
			
			cell = cells.getCell(countRow , 5);
		    cells.merge(countRow , 5, countRow,7);
			getCellStyle(cell,HorizontalAlignmentType.CENTRED, Color.BLACK, true,false, 9,"Người lập phiếu", 0);
			
			
			cell = cells.getCell(countRow , 8);
		    cells.merge(countRow , 8, countRow,10);
			getCellStyle(cell,HorizontalAlignmentType.CENTRED, Color.BLACK, true,false, 9,"Thủ kho", 0);
			
			countRow++;
			cells.setRowHeight(countRow , 17.25); 
			cell = cells.getCell(countRow , 0);
		    cells.merge(countRow , 0, countRow,2);
			getCellStyle(cell,HorizontalAlignmentType.CENTRED, Color.BLACK, true,false, 9,"", 0);
			
			//---
			workbook.save(out);
			fstream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
	}

	
	private void  getCellStyle(Cell cell,short align, Color color, Boolean bold,Boolean ita, int size,String cellValue , int border){		   
		Style style;
		style = cell.getStyle();		
		com.aspose.cells.Font font = new com.aspose.cells.Font();
		font.setColor(color);
		font.setBold(bold);
		font.setItalic(ita);
		font.setSize(size);
		font.setName("Arial");
		style.setFont(font);
		style.setHAlignment(align);
		
		// border
		style.setBorderLine(BorderType.TOP, border);
		style.setBorderLine(BorderType.RIGHT,border);
		style.setBorderLine(BorderType.BOTTOM, border);
		style.setBorderLine(BorderType.LEFT, border);
		
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	
	
	private void setCellBorderStyle(Cell cell, short align,boolean bold,boolean ita, int top,int bottom,  int LEFT , int RIGHT ) {
		
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, top);
		style.setBorderLine(BorderType.RIGHT, RIGHT);
		style.setBorderLine(BorderType.BOTTOM, bottom);
		style.setBorderLine(BorderType.LEFT, LEFT);
		
		
		// cho mau gray tai phan cach
		/*style.setBorderColor(BorderType.TOP, Color.GRAY);
		style.setBorderColor(BorderType.BOTTOM, Color.GRAY);*/
		
		/*if(kt)
		{
			com.aspose.cells.Font font2= new com.aspose.cells.Font();
			font2.setBold(kt);
			font2.setName("Arial");
			font2.setColor(Color.RED);
			font2.setSize(9);
			style.setFont(font2);
			
		}
		else {
			com.aspose.cells.Font font2= new com.aspose.cells.Font();
			font2.setItalic(true);
			font2.setName("Arial");
			font2.setSize(9);
			style.setFont(font2);
			
		}*/
		
		com.aspose.cells.Font font2= new com.aspose.cells.Font();
		font2.setBold(bold);
		font2.setItalic(ita);
		font2.setName("Arial");
		font2.setSize(9);
		style.setFont(font2);
		
		style.setColor(Color.WHITE);
		
		cell.setStyle(style);
	}

private void setCellBorderStyle(Cell cell, short align,boolean kt ) {
		
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			com.aspose.cells.Font font2= new com.aspose.cells.Font();
			/*Font font2 = new Font(); */
			font2.setName("Arial");
			font2.setColor(Color.RED);
			style.setFont(font2);
			
		}
		
		style.setColor(Color.WHITE);
		
		cell.setStyle(style);
	}

	

}




