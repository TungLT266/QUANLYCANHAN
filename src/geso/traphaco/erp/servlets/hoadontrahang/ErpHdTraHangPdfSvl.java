package geso.traphaco.erp.servlets.hoadontrahang;


import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang;
import geso.traphaco.erp.beans.hoadontrahang.imp.ErpHdTraHang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.BorderType;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Cell;

public class ErpHdTraHangPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpHdTraHangPdfSvl() {
        super();
    }

    float CONVERT = 28.346457f;  // =1cm
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		IErpHdTraHang obj;
		String userId;
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();	    
	    String hdId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpHdTraHang();
	    //obj.initdisplay(ddhId);
	    
		
		if(querystring.contains("print"))
		{
			System.out.println("Print");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoaDonTraHang.pdf");
			
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();			
			
			this.CreateHdPdf(document, outstream,obj, hdId);
			
			document.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreateHdPdf(Document document, ServletOutputStream outstream, IErpHdTraHang pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
			" SELECT HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MASOTHUE, KH.DIACHI, HD.NGUOIMUA, KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI," +
			"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT \n"+
			" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
			" WHERE HD.PK_SEQ ='"+hdId+"' \n";
			
			
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("MASOTHUE");
					DIACHI = rs.getString("DIACHI");
					NGUOIMUA = rs.getString("NGUOIMUA");
					TENKH = rs.getString("TEN");
					TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.0f * CONVERT);//CÁCH LỀ TRÊN
			cell.setPaddingLeft(3.0f * CONVERT);//CÁCH LỀ TRÁI
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			Paragraph hd = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.setSpacingAfter(2);
			cell.addElement(hd);

			tableheader.addCell(cell);
			document.add(tableheader);
			
			//THÔNG TIN KHÁCH HÀNG
			
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			
			
			// DONG 1-- NGUOI MUA HANG
			PdfPCell cell_nguoimua = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_nguoimua.addElement(hd);
			cell_nguoimua.setPaddingLeft(5.0f*CONVERT);
			cell_nguoimua.setBorder(0);	
			
			table1.addCell(cell_nguoimua);	
			
			document.add(table1);
			
			
			
			PdfPTable table2 =new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table2.setWidths(withs2);
									
			//DONG 2 -- DON VI
			
			PdfPCell cell_donvi = new PdfPCell();	
			hd = new Paragraph( TENKH , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_donvi.addElement(hd);
			cell_donvi.setPaddingLeft(2.0f*CONVERT);
			cell_donvi.setBorder(0);	
			
			table2.addCell(cell_donvi);	
			
			document.add(table2);
			
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
			
			//DONG 3 -- DIA CHI
			
			PdfPCell cell_diachi = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_diachi.addElement(hd);
			cell_diachi.setPaddingLeft(2.0f*CONVERT);
			cell_diachi.setBorder(0);	
			
			table3.addCell(cell_diachi);	

			document.add(table3);
			
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {50f,100f};
			table4.setWidths(withs4);
			
			//DONG 4 -- HINH THUC THANH TOAN
			
			PdfPCell cell_hinhthuctt = new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_hinhthuctt.addElement(hd);
			cell_hinhthuctt.setPaddingLeft(2.0f*CONVERT);
			cell_hinhthuctt.setBorder(0);
			
			table4.addCell(cell_hinhthuctt);	
			
			PdfPCell cell_masothue = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(2);
			cell_masothue.addElement(hd);
			cell_masothue.setPaddingLeft(2.0f*CONVERT);
			cell_masothue.setBorder(0);	
			
			table4.addCell(cell_masothue);
			
			document.add(table4);
			
			//LẤY THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(95);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(2.0f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 2.0f, 20.0f, 7.0f, 7.0f, 10.0f, 9.0f, 14.0f };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();
			
			
			String INIT_SANPHAM = "	SELECT a.SANPHAM_FK, b.TEN TENSP,a.DONVITINH,a.SOLUONG, a.DONGIA, a.CHIETKHAU \n"+
								  "	FROM ERP_HOADON_SP a INNER JOIN ERP_SANPHAM b ON a.SANPHAM_FK = b.PK_SEQ \n"+
								  " WHERE a.HOADON_FK ='"+hdId+"' \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					TENSP = rsSP.getString("TENSP");
					DONVITINH = rsSP.getString("DONVITINH");
					SOLUONG = rsSP.getDouble("SOLUONG");
					DONGIA = Math.round(rsSP.getDouble("DONGIA"));
					CHIETKHAU = rsSP.getDouble("CHIETKHAU");
					DONGIAGIAM = Math.round(DONGIA - DONGIA*CHIETKHAU/100);
					THANHTIENGIAM = SOLUONG*DONGIAGIAM;
					TONGTIENHANG+=THANHTIENGIAM;
									
					String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)),
							DinhDangTraphacoERP(formatter.format(DONGIAGIAM)), DinhDangTraphacoERP(formatter.format(THANHTIENGIAM)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								}
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						cells.setFixedHeight(0.6f * CONVERT);
						//cells.setPaddingTop(2.5f);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 9-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
				
			
			document.add(sanpham);
						
			
			PdfPTable table5 =new PdfPTable(1);
			table5.setWidthPercentage(100);
			float[] withs5 = {100f};
			table5.setWidths(withs5);
						
			//DONG 4 -- TIỀN HÀNG
			
			PdfPCell cell_tienhang = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENHANG)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(4);
			cell_tienhang.addElement(hd);
			cell_tienhang.setPaddingLeft(2.0f*CONVERT);
			cell_tienhang.setBorder(0);
			
			table5.addCell(cell_tienhang);	
			
			document.add(table5);		
			
			
			
			PdfPTable table6 =new PdfPTable(2);
			table6.setWidthPercentage(100);
			float[] withs6 = {665f,120f};
			table6.setWidths(withs6);
			
			//DONG 5 -- SOTIENCHIETKHAU			
						
			if(TIENCK==0){
				PdfPCell cell_tienck = new PdfPCell();	
				hd = new Paragraph( "" , new Font(bf, 10, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_RIGHT);
				hd.setSpacingAfter(2);
				cell_tienck.addElement(hd);
				cell_tienck.setPaddingLeft(2.0f*CONVERT);
				cell_tienck.setBorder(0);
				
				table6.addCell(cell_tienck);
			}
			else{
				PdfPCell cell_tienck = new PdfPCell();	
				hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENCK)) , new Font(bf, 10, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_RIGHT);
				hd.setSpacingAfter(2);
				cell_tienck.addElement(hd);
				cell_tienck.setPaddingLeft(2.0f*CONVERT);
				cell_tienck.setBorder(0);
				
				table6.addCell(cell_tienck);
			}
			
			//DONG 5 -- TIEN DA TRU CHIET KHAU
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			PdfPCell cell_tiensauck = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENSAUCK)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(2);
			cell_tiensauck.addElement(hd);
			cell_tiensauck.setPaddingLeft(2.0f*CONVERT);
			cell_tiensauck.setBorder(0);	
			
			table6.addCell(cell_tiensauck);
			
			document.add(table6);
			
			
			
			PdfPTable table7 =new PdfPTable(2);
			table7.setWidthPercentage(100);
			float[] withs7 = {650f,120f};
			table7.setWidths(withs7);
			
			//THUẾ VAT
			PdfPCell cell_vat = new PdfPCell();	
			hd = new Paragraph( "10%" , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(2);
			cell_vat.addElement(hd);
			cell_vat.setPaddingLeft(2.0f*CONVERT);
			cell_vat.setBorder(0);
			
			table7.addCell(cell_vat);
			
			
			//TIỀN THUẾ VAT
		
			PdfPCell cell_tienthue = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(VAT)), new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(2);
			cell_tienthue.addElement(hd);
			cell_tienthue.setPaddingLeft(2.0f*CONVERT);
			cell_tienthue.setBorder(0);
			
			table7.addCell(cell_tienthue);
			
			document.add(table7);
			
			
			
			PdfPTable table8 =new PdfPTable(1);
			table8.setWidthPercentage(100);
			float[] withs8 = {100f};
			table5.setWidths(withs8);
			
			//DONG 4 -- TỔNG TIỀN THANH TOÁN
			
			
			PdfPCell cell_sumthanhtoan = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter1.format(TONGTIENAVAT)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(4);
			cell_sumthanhtoan.addElement(hd);
			cell_sumthanhtoan.setPaddingLeft(2.0f*CONVERT);
			cell_sumthanhtoan.setBorder(0);
			
			table8.addCell(cell_sumthanhtoan);	
			
			document.add(table8);
			
			
			//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
					    //String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("            " + tien, new Font(bf, 10, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(12.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
			
/*			// Tien bang chu
			doctien doc = new doctienrachu();
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			String tien = doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",", "")));
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}*/
			
			
			/*PdfPTable table7 =new PdfPTable(2);
			table7.setWidthPercentage(100);
			float[] withs7 = {100f,100f};
			table7.setWidths(withs7);
			
			//DONG 6 -- THUE SUAT VAT
			
			PdfPCell cell_thuevat = new PdfPCell();	
			hd = new Paragraph( "10%" , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(2);
			cell_thuevat.addElement(hd);
			cell_thuevat.setPaddingLeft(2.0f*CONVERT);
			cell_thuevat.setBorder(0);
			
			table7.addCell(cell_tienck);	
			
			//DONG 5 -- TIEN DA TRU CHIET KHAU
			TIENSAUCK = TONGTIENHANG - TIENCK;
			
			PdfPCell cell_sauck = new PdfPCell();	
			hd = new Paragraph( formatter.format(TIENSAUCK) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(2);
			cell_sauck.addElement(hd);
			cell_sauck.setPaddingLeft(2.0f*CONVERT);
			cell_sauck.setBorder(0);	
			
			table7.addCell(cell_sauck);
			
			document.add(table7);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}

}



