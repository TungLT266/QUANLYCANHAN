package geso.traphaco.center.servlets.thutien;

import geso.traphaco.center.beans.doctien.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.traphaco.center.beans.cauhinhinhoadon.imp.ErpCauHinhInHoaDon;
import geso.traphaco.center.beans.thutien.IErpThutien;
import geso.traphaco.center.beans.thutien.imp.ErpThutien;
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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpThutienPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpThutienPdfSvl() {
        super();
    }
    float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("id");
		
		IErpThutien tthdBean = new ErpThutien(id);
	    tthdBean.setUserId(userId);
	    
    	tthdBean.initPdf();
    	
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		
		this.CreatePhieuThu(document, outstream, tthdBean);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	private void CreatePhieuThu(Document document, ServletOutputStream outstream, IErpThutien tthdBean) 
	{
		try
		{						
			dbutils db = new dbutils();
           
			   
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(95);
			float[] withsheader = { 350.0f, 50.0f, 50.0f};
			tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableheader.setWidths(withsheader); 			
			
		// DÒNG 1	
			PdfPCell celltenCT= new PdfPCell();
			celltenCT.setHorizontalAlignment(Element.ALIGN_LEFT);
			celltenCT.setVerticalAlignment(Element.ALIGN_TOP);
						
			Paragraph pxk = new Paragraph("CÔNG TY CP TRAPHACO",  new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltenCT.addElement(pxk);
			celltenCT.setBorder(0);
						
			tableheader.addCell(celltenCT);
			
			PdfPCell cellSophieu = new PdfPCell();
			cellSophieu.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSophieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph("Số phiếu:", new Font(bf, 8, Font.NORMAL));
			cellSophieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellSophieu.setBorder(0);
						
			tableheader.addCell(cellSophieu);
			
			PdfPCell cellGTphieu = new PdfPCell();
			cellGTphieu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTphieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph(tthdBean.getSoin(), new Font(bf, 8, Font.NORMAL));
			cellGTphieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTphieu.setBorder(0);
						
			tableheader.addCell(cellGTphieu);
			

			
	   // DONG 2		
			
			PdfPCell cellDiachi= new PdfPCell();
			cellDiachi.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDiachi.setVerticalAlignment(Element.ALIGN_TOP);						
		    pxk = new Paragraph("75 PHỐ YÊN NINH, PHƯỜNG QUÁN THÁNH, QUẬN BA ĐÌNH, THÀNH PHỐ HÀ NỘI, VIỆT NAM",  new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellDiachi.addElement(pxk);
			cellDiachi.setBorder(0);
						
			tableheader.addCell(cellDiachi);
			
			PdfPCell cellTK = new PdfPCell();
			cellTK.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTK.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph("Tài khoản:", new Font(bf, 8, Font.NORMAL));
			cellTK.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellTK.setBorder(0);
						
			tableheader.addCell(cellTK);
			
			String taikhoan="";
			if(tthdBean.getHtttId().equals("100001")) //TIEN MAT
			{
				taikhoan = "1111";
			}else{
				taikhoan = "1112";
			}
			
			PdfPCell cellGTtaikh = new PdfPCell();
			cellGTtaikh.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTtaikh.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(taikhoan, new Font(bf, 8, Font.NORMAL));
			cellGTtaikh.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTtaikh.setBorder(0);
						
			tableheader.addCell(cellGTtaikh);
		
	// DONG 3
			PdfPCell cell= new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP)	;				
		    pxk = new Paragraph("",  new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(pxk);
			cell.setBorder(0);
						
			tableheader.addCell(cell);
			
		
			
			PdfPCell cell1 = new PdfPCell();
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);					
			pxk = new Paragraph("Tài khoản dư:", new Font(bf, 8, Font.NORMAL));
			cell1.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell1.setBorder(0);
						
			tableheader.addCell(cell1);
			
			String tkd ="13111";
			
			PdfPCell cell2 = new PdfPCell();
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(tkd, new Font(bf, 8, Font.NORMAL));
			cell2.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
						
			tableheader.addCell(cell2);
			
			document.add(tableheader);	
			
	// Tiêu đề		
	
			pxk = new Paragraph("PHIẾU THU" , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			document.add(pxk);
			
			String [] ngayHD = tthdBean.getNgaychungtu().split("-");
			pxk = new Paragraph("Ngày " +ngayHD[2] +"/" +ngayHD[1] +  "/"+ngayHD[0] , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			document.add(pxk);
		

			
   // Thông tin Phiếu thu
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(95);
			float[] withs1 = {50.0f,350.0f};
			table1.setWidths(withs1);
			
			PdfPCell cell3 = new PdfPCell();			
			pxk = new Paragraph("Người nộp: " , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell3.addElement(pxk);
			cell3.setBorder(0);						
			table1.addCell(cell3);	
			
			PdfPCell cell4= new PdfPCell();			
			pxk = new Paragraph(tthdBean.getNguoinoptien() , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell4.addElement(pxk);
			cell4.setBorder(0);						
			table1.addCell(cell4);
			
			PdfPCell cell3a = new PdfPCell();			
			pxk = new Paragraph("Địa chỉ: " , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell3a.addElement(pxk);
			cell3a.setBorder(0);						
			table1.addCell(cell3a);	
			
			PdfPCell cell4a= new PdfPCell();			
			pxk = new Paragraph("", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell4a.addElement(pxk);
			cell4a.setBorder(0);						
			table1.addCell(cell4a);
			
			PdfPCell cell5 = new PdfPCell();			
			pxk = new Paragraph("Về khoản: " , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell5.addElement(pxk);
			cell5.setBorder(0);						
			table1.addCell(cell5);	
			
			PdfPCell cell6= new PdfPCell();			
			pxk = new Paragraph("Thu tiền hàng" , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell6.addElement(pxk);
			cell6.setBorder(0);						
			table1.addCell(cell6);
			
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph("Số tiền: " , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);	
			
			PdfPCell cell8= new PdfPCell();			
			pxk = new Paragraph(formatter.format(Double.parseDouble(tthdBean.getThuduoc())) , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);
			
			
			
			// DOC TIEN BANG CHU
			doctienrachu doctien = new doctienrachu();		    
		    String tien = doctien.tranlate(Math.round(Double.parseDouble(tthdBean.getThuduoc())) + "");	
			//  Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph("Bằng chữ: " , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			PdfPCell cell0= new PdfPCell();			
			pxk = new Paragraph( TienIN , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell0.addElement(pxk);
			cell0.setBorder(0);						
			table1.addCell(cell0);
			
			PdfPCell cell11 = new PdfPCell();			
			pxk = new Paragraph("Kèm theo: " , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			PdfPCell cell12= new PdfPCell();			
			pxk = new Paragraph("    chứng từ gốc" , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell12.addElement(pxk);
			cell12.setBorder(0);						
			table1.addCell(cell12);
			
			document.add(table1);
			
			
			
			PdfPTable table3 =new PdfPTable(2);
			table3.setWidthPercentage(100);
			float[] withs3 = {200.0f,250.0f};
			table3.setWidths(withs3);
			
			PdfPCell cell13 = new PdfPCell();						
			pxk = new Paragraph("KẾ TOÁN TRƯỞNG", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell13.addElement(pxk);
			cell13.setBorder(0);						
			table3.addCell(cell13);	
						
			PdfPCell cell14 = new PdfPCell();						
			pxk = new Paragraph("KẾ TOÁN THANH TOÁN", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table3.addCell(cell14);	
						
			document.add(table3);
					
			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			document.add(pxk);

			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			document.add(pxk);
			
			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			document.add(pxk);
			
			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			document.add(pxk);
			
	// KHÁCH HÀNG XÁC NHẬN TIỀN		
	
			PdfPTable table5 =new PdfPTable(1);
			table5.setWidthPercentage(95);
			float[] withs5 = {500.0f};
			table5.setWidths(withs5);
			
			PdfPCell cell15= new PdfPCell();			
			pxk = new Paragraph("Đã nhận đủ số tiền (viết bằng chữ):................................................................................................................................................................" 
				, new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell15.addElement(pxk);
			cell15.setBorder(0);						
			table5.addCell(cell15);
			
			PdfPCell cell16= new PdfPCell();			
			pxk = new Paragraph(".........................................................................................................................................................................................................................." 
				, new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell16.addElement(pxk);
			cell16.setBorder(0);						
			table5.addCell(cell16);
			
			document.add(table5);
			
			
	// FOOTER
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200.0f,250.0f};
			table3.setWidths(withs4);
			
			PdfPCell cell17 = new PdfPCell();						
			pxk = new Paragraph("", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table4.addCell(cell17);	
						
			
			PdfPCell cell18 = new PdfPCell();						
			pxk = new Paragraph("Ngày......tháng.......năm.......", new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell18.addElement(pxk);
			cell18.setBorder(0);						
			table4.addCell(cell18);	
			
			PdfPCell cell19 = new PdfPCell();						
			pxk = new Paragraph("NGUỜI NỘP", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell19.addElement(pxk);
			cell19.setBorder(0);						
			table4.addCell(cell19);	
						
			PdfPCell cell20 = new PdfPCell();						
			pxk = new Paragraph("THỦ QUỸ", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell20.addElement(pxk);
			cell20.setBorder(0);						
			table4.addCell(cell20);	
						
			document.add(table4);
			
			document.close();
		
			
		
			
		}
		catch(Exception e)
		{
			System.out.println("Exception In Phieu Thu: " + e.getMessage());
		}
	}

	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return ngay + "/" + thang + "/" + nam;
	}
	
	
}
