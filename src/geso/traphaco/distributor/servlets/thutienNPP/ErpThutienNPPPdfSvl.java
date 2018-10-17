package geso.traphaco.distributor.servlets.thutienNPP;

import geso.traphaco.center.beans.doctien.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.traphaco.center.beans.cauhinhinhoadon.imp.ErpCauHinhInHoaDon;
import geso.traphaco.distributor.beans.thutienNPP.IErpThutienNPP;
import geso.traphaco.distributor.beans.thutienNPP.imp.ErpThutienNPP;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import geso.traphaco.distributor.util.Utility;

public class ErpThutienNPPPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpThutienNPPPdfSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		Utility util=new Utility();
		String querystring=request.getQueryString();
		String id=util.getId(querystring);
		
		IErpThutienNPP tthdBean = new ErpThutienNPP(id);
	    tthdBean.setUserId(userId);
	    
	    if(querystring.contains("inBangke"))
	    {
	    	tthdBean.initPdf();   	
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=Bangkethutien.pdf");
	
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreateBangKeTT(document, outstream, tthdBean);
	    }
	    else if (querystring.contains("in"))
	    {
	    	tthdBean.initPdf();   	
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=Phieuthutien.pdf");
	
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreatePhieuThu(document, outstream, tthdBean);
	    }
		
	}

	private void CreateBangKeTT(Document document,ServletOutputStream outstream, IErpThutienNPP tthdBean) 
	{
		try
		{	
			dbutils db = new dbutils();
           
			// LAY THONG TIN CHI NHANH/DOITAC
			
			String ten="";
			String diachi ="";
			
			String query ="SELECT TEN, DIACHI FROM NHAPHANPHOI WHERE PK_SEQ= '"+ tthdBean.getNppId() +"' ";
			 ResultSet  rss = db.get(query) ;
			 if(rss!= null)
			 {
				 while(rss.next())
				 {
					 ten = rss.getString("TEN");
					 diachi = rss.getString("DIACHI");
				 }rss.close();
			 }
			
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			//document.setMargins(10.0f, -5.0f, 5.0f,0.0f);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(95);
			float[] withsheader = { 350.0f, 60.0f, 40.0f};
			tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableheader.setWidths(withsheader); 			
			
		// DÒNG 1	
			PdfPCell celltenCT= new PdfPCell();
			celltenCT.setHorizontalAlignment(Element.ALIGN_LEFT);
			celltenCT.setVerticalAlignment(Element.ALIGN_TOP);
						
			Paragraph pxk = new Paragraph(ten.toUpperCase(),  new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltenCT.addElement(pxk);
			celltenCT.setBorder(0);
						
			tableheader.addCell(celltenCT);
			
			PdfPCell cellSophieu = new PdfPCell();
			cellSophieu.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSophieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph("Số phiếu:", new Font(bf, 10, Font.NORMAL));
			cellSophieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellSophieu.setBorder(0);
						
			tableheader.addCell(cellSophieu);
			
			PdfPCell cellGTphieu = new PdfPCell();
			cellGTphieu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTphieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph(tthdBean.getSoin(), new Font(bf, 10, Font.NORMAL));
			cellGTphieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTphieu.setBorder(0);
						
			tableheader.addCell(cellGTphieu);
			

			
	   // DONG 2		
			
			PdfPCell cellDiachi= new PdfPCell();
			cellDiachi.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDiachi.setVerticalAlignment(Element.ALIGN_TOP);						
		    pxk = new Paragraph(diachi.toUpperCase(),  new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellDiachi.addElement(pxk);
			cellDiachi.setBorder(0);
						
			tableheader.addCell(cellDiachi);
			
			PdfPCell cellTK = new PdfPCell();
			cellTK.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTK.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			cellTK.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellTK.setBorder(0);
						
			tableheader.addCell(cellTK);
			
			
			PdfPCell cellGTtaikh = new PdfPCell();
			cellGTtaikh.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTtaikh.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			cellGTtaikh.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTtaikh.setBorder(0);
						
			tableheader.addCell(cellGTtaikh);
			
			document.add(tableheader);	
			
	// Tiêu đề		
	
			pxk = new Paragraph("BẢNG KÊ THU TIỀN" , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			String [] ngayHD = tthdBean.getNgaychungtu().split("-");
			pxk = new Paragraph("Ngày " +ngayHD[2] +"/" +ngayHD[1] +  "/"+ngayHD[0] , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
		

			
   // Thông tin Phiếu thu
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(95);
			float[] withs1 = {100.0f,300.0f};
			table1.setWidths(withs1);
			
			PdfPCell cell3 = new PdfPCell();			
			pxk = new Paragraph("Ngày chứng từ: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell3.addElement(pxk);
			cell3.setBorder(0);						
			table1.addCell(cell3);	
			
			PdfPCell cell4= new PdfPCell();			
			pxk = new Paragraph(tthdBean.getNgaychungtu() , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell4.addElement(pxk);
			cell4.setBorder(0);						
			table1.addCell(cell4);
			
			PdfPCell cell3a = new PdfPCell();			
			pxk = new Paragraph("Người nộp tiền: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell3a.addElement(pxk);
			cell3a.setBorder(0);						
			table1.addCell(cell3a);	
			
			PdfPCell cell4a= new PdfPCell();			
			pxk = new Paragraph(tthdBean.getNguoinoptien(), new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell4a.addElement(pxk);
			cell4a.setBorder(0);						
			table1.addCell(cell4a);
			
			document.add(table1);
			
			String[] th = new String[]{ "STT", "Khách hàng/ Đối tác", "Số chứng từ", "Ký hiệu hóa đơn", "Số hóa đơn", "Ngày hóa đơn", "Tổng tiền thanh toán" };
			
			PdfPTable table = new PdfPTable(th.length);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10.0f);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =  { 8.0f, 40.0f, 15.0f, 15.0f, 15.0f, 15.0f, 25.0f  };
			table.setWidths(withs);
			
			

			PdfPCell[] cell1s = new PdfPCell[th.length];
			for (int i = 0; i < th.length ; i++)
			{
					cell1s[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 10, Font.NORMAL)));
					cell1s[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell1s[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell1s[i].setPadding(3.0f);
					table.addCell(cell1s[i]);		
			}
			
			 query =
				" select (case  when b.KHACHHANG_FK is not null then (select MAFAST + '-' + TEN from KHACHHANG where PK_SEQ= b.KHACHHANG_FK) "+
	            " else (select MAFAST + '-' + TEN from NHAPHANPHOI where PK_SEQ= b.NPP_FK) end) as doituong  , "+
	            "  b.hoadonnpp_fk as sochungtu, (case when hd.ngayxuathd is not null then hd.ngayxuathd else hdnpp.ngayxuathd end) as ngayhoadon,  "+ 
	            " (case when hd.sohoadon is not null then hd.sohoadon else hdnpp.sohoadon end) as sohoadon ,  " +
	            " (case when hd.kyhieu is not null then hd.kyhieu else hdnpp.kyhieu end) as kyhieuhoadon , b.sotientt  	"+
	            "from ERP_THUTIENNPP a   inner join ERP_THUTIENNPP_HOADON b on a.pk_seq=b.THUTIENNPP_FK  "+
	            "           left join HOADON hd on b.hoadonnpp_fk = hd.pk_seq and b.khachhang_fk in (select pk_seq from KHACHHANG where KBH_FK=100025) "+   
				"		    left join ERP_HOADONNPP hdnpp on b.hoadonnpp_fk= hdnpp.pk_seq "+
	            "                      and (hdnpp.npp_fk is not null or hdnpp.KHACHHANG_FK in(select pk_seq from KHACHHANG where KBH_FK=100052)) "+
	            "            left join DUNO_KHACHHANG dn on b.HOADONNPP_FK = dn.pk_seq  						     "+
	            "where a.PK_SEQ =" + tthdBean.getId() +" ";
				
				
				System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
				
				ResultSet rsSP = db.get(query);
				
				int stt = 1;
				while(rsSP.next())
				{
					double sotientt = rsSP.getDouble("sotientt");
					
					String tendoituong= rsSP.getString("doituong");
					String kyhieuhoadon= rsSP.getString("kyhieuhoadon") == null ? "" : rsSP.getString("kyhieuhoadon");
					String sohoadon= rsSP.getString("sohoadon") == null ? "" : rsSP.getString("sohoadon") ;
					String ngayhoadon= rsSP.getString("ngayhoadon") == null ? "" : rsSP.getString("ngayhoadon") ;
					String sochungtu= rsSP.getString("sochungtu");
									
					String[] arr = new String[] { Integer.toString(stt),tendoituong , sochungtu, kyhieuhoadon, sohoadon, ngayhoadon, formatter.format(sotientt) };


					for (int j = 0; j < th.length; j++)
					{
						PdfPCell cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.NORMAL)));
						if (j == 5 ){
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else{
							if(j==1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						
						
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2.0f);	
											
						table.addCell(cells);
					}
					
					stt++;
					
				}
			
				String[] arr = new String[] { "Tổng ", formatter.format(Double.parseDouble(tthdBean.getThuduoc())) };


				for (int j = 0; j < arr.length; j++)
				{
					PdfPCell cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.NORMAL)));
					if (j == 0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(6);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
										
					table.addCell(cells);
				}
				
			// DOC TIEN BANG CHU
			doctienrachu doctien = new doctienrachu();		    
		    String tien = doctien.docTien(Math.round(Double.parseDouble(tthdBean.getThuduoc())));	
			//  Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    String[] arr1 = new String[] { "Bằng chữ:  " + TienIN};
 

			for (int j = 0; j < arr1.length; j++)
			{
				PdfPCell cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.NORMAL)));
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(6);	
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(2.0f);	
									
				table.addCell(cells);
			}
						
			
			document.add(table);
			

			
			
	// FOOTER
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200.0f,250.0f};
			table4.setWidths(withs4);
			
			PdfPCell cell17 = new PdfPCell();						
			pxk = new Paragraph("", new Font(bf,10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table4.addCell(cell17);	
						
			
			PdfPCell cell18 = new PdfPCell();						
			pxk = new Paragraph("Ngày......tháng.......năm.......", new Font(bf, 10, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell18.addElement(pxk);
			cell18.setBorder(0);						
			table4.addCell(cell18);	
			
			PdfPCell cell19 = new PdfPCell();						
			pxk = new Paragraph("NGUỜI NỘP", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell19.addElement(pxk);
			cell19.setBorder(0);
		    pxk.setSpacingAfter(14.0f);
			table4.addCell(cell19);	
						
			PdfPCell cell20 = new PdfPCell();						
			pxk = new Paragraph("THỦ QUỸ", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell20.addElement(pxk);
			cell20.setBorder(0);	
			pxk.setSpacingAfter(14.0f);
			table4.addCell(cell20);	
			
		
						
			
						
			document.add(table4);
				
		
			document.close();
		
			
		
			
		}
		catch(Exception e)
		{
			System.out.println("Exception In Phieu Thu: " + e.getMessage());
		}
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	private void CreatePhieuThu(Document document, ServletOutputStream outstream, IErpThutienNPP tthdBean) 
	{
		try
		{	
			dbutils db = new dbutils();
           
			// LAY THONG TIN CHI NHANH/DOITAC
			
			String ten="";
			String diachi ="";
			
			
			String query ="SELECT TEN, DIACHI FROM NHAPHANPHOI WHERE PK_SEQ= '"+ tthdBean.getNppId() +"' ";
			 ResultSet  rss = db.get(query) ;
			 if(rss!= null)
			 {
				 while(rss.next())
				 {
					 ten = rss.getString("TEN");
					 diachi = rss.getString("DIACHI");
				 }rss.close();
			 }
			
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			//document.setMargins(10.0f, -5.0f, 5.0f,0.0f);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			
		// IN LÀM 2 MẪU GIỐNG NHAU TRÊN 1 TRANG
		for(int i=0; i < 2 ; i++)
		{
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(95);
			float[] withsheader = { 350.0f, 60.0f, 40.0f};
			tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableheader.setWidths(withsheader); 			
			
		// DÒNG 1	
			PdfPCell celltenCT= new PdfPCell();
			celltenCT.setHorizontalAlignment(Element.ALIGN_LEFT);
			celltenCT.setVerticalAlignment(Element.ALIGN_TOP);
						
			Paragraph pxk = new Paragraph(ten.toUpperCase(),  new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltenCT.addElement(pxk);
			celltenCT.setBorder(0);
						
			tableheader.addCell(celltenCT);
			
			PdfPCell cellSophieu = new PdfPCell();
			cellSophieu.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSophieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph("Số phiếu:", new Font(bf, 10, Font.NORMAL));
			cellSophieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellSophieu.setBorder(0);
						
			tableheader.addCell(cellSophieu);
			
			PdfPCell cellGTphieu = new PdfPCell();
			cellGTphieu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTphieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph(tthdBean.getSoin(), new Font(bf, 10, Font.NORMAL));
			cellGTphieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTphieu.setBorder(0);
						
			tableheader.addCell(cellGTphieu);
			

			
	   // DONG 2		
			
			PdfPCell cellDiachi= new PdfPCell();
			cellDiachi.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDiachi.setVerticalAlignment(Element.ALIGN_TOP);						
		    pxk = new Paragraph(diachi.toUpperCase(),  new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellDiachi.addElement(pxk);
			cellDiachi.setBorder(0);
						
			tableheader.addCell(cellDiachi);
			
			PdfPCell cellTK = new PdfPCell();
			cellTK.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTK.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph("Tài khoản:", new Font(bf, 10, Font.NORMAL));
			cellTK.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellTK.setBorder(0);
						
			tableheader.addCell(cellTK);
			
			
			PdfPCell cellGTtaikh = new PdfPCell();
			cellGTtaikh.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTtaikh.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph("1111", new Font(bf, 10, Font.NORMAL));
			cellGTtaikh.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTtaikh.setBorder(0);
						
			tableheader.addCell(cellGTtaikh);
		
	// DONG 3
			PdfPCell cell= new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP)	;				
		    pxk = new Paragraph("",  new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(pxk);
			cell.setBorder(0);
						
			tableheader.addCell(cell);
			
		
			
			PdfPCell cell1 = new PdfPCell();
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);					
			pxk = new Paragraph("Tài khoản dư:", new Font(bf, 10, Font.NORMAL));
			cell1.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell1.setBorder(0);
						
			tableheader.addCell(cell1);
			
			String tkd ="13111";
			
			PdfPCell cell2 = new PdfPCell();
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(tkd, new Font(bf, 10, Font.NORMAL));
			cell2.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
						
			tableheader.addCell(cell2);
			
			document.add(tableheader);	
			
	// Tiêu đề		
	
			pxk = new Paragraph("PHIẾU THU" , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			String [] ngayHD = tthdBean.getNgaychungtu().split("-");
			pxk = new Paragraph("Ngày " +ngayHD[2] +"/" +ngayHD[1] +  "/"+ngayHD[0] , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
		

			
   // Thông tin Phiếu thu
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(95);
			float[] withs1 = {50.0f,350.0f};
			table1.setWidths(withs1);
			
			PdfPCell cell3 = new PdfPCell();			
			pxk = new Paragraph("Người nộp: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell3.addElement(pxk);
			cell3.setBorder(0);						
			table1.addCell(cell3);	
			
			PdfPCell cell4= new PdfPCell();			
			pxk = new Paragraph(tthdBean.getNguoinoptien() , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell4.addElement(pxk);
			cell4.setBorder(0);						
			table1.addCell(cell4);
			
			PdfPCell cell3a = new PdfPCell();			
			pxk = new Paragraph("Địa chỉ: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell3a.addElement(pxk);
			cell3a.setBorder(0);						
			table1.addCell(cell3a);	
			
			PdfPCell cell4a= new PdfPCell();			
			pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell4a.addElement(pxk);
			cell4a.setBorder(0);						
			table1.addCell(cell4a);
			
			PdfPCell cell5 = new PdfPCell();			
			pxk = new Paragraph("Về khoản: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell5.addElement(pxk);
			cell5.setBorder(0);						
			table1.addCell(cell5);	
			
			PdfPCell cell6= new PdfPCell();			
			pxk = new Paragraph("Thu tiền hàng" , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell6.addElement(pxk);
			cell6.setBorder(0);						
			table1.addCell(cell6);
			
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph("Số tiền: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);	
			
			PdfPCell cell8= new PdfPCell();			
			pxk = new Paragraph(formatter.format(Double.parseDouble(tthdBean.getThuduoc())) , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);
			
			
			
			// DOC TIEN BANG CHU
			doctienrachu doctien = new doctienrachu();		    
		    String tien = doctien.docTien(Math.round(Double.parseDouble(tthdBean.getThuduoc())));	
			//  Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph("Bằng chữ: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			PdfPCell cell0= new PdfPCell();			
			pxk = new Paragraph( TienIN , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell0.addElement(pxk);
			cell0.setBorder(0);						
			table1.addCell(cell0);
			
			PdfPCell cell11 = new PdfPCell();			
			pxk = new Paragraph("Kèm theo: " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			PdfPCell cell12= new PdfPCell();			
			pxk = new Paragraph("    chứng từ gốc" , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell12.addElement(pxk);
			cell12.setBorder(0);						
			table1.addCell(cell12);
			
			document.add(table1);
			
			
			
			PdfPTable table3 =new PdfPTable(2);
			table3.setWidthPercentage(100);
			float[] withs3 = {200.0f,250.0f};
			table3.setWidths(withs3);
			
			PdfPCell cell13 = new PdfPCell();						
			pxk = new Paragraph("KẾ TOÁN TRƯỞNG", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell13.addElement(pxk);
			cell13.setBorder(0);						
			table3.addCell(cell13);	
						
			PdfPCell cell14 = new PdfPCell();						
			pxk = new Paragraph("KẾ TOÁN THANH TOÁN", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table3.addCell(cell14);	
						
			document.add(table3);
					
			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph(" ", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
	// KHÁCH HÀNG XÁC NHẬN TIỀN		
	
			PdfPTable table5 =new PdfPTable(1);
			table5.setWidthPercentage(95);
			float[] withs5 = {500.0f};
			table5.setWidths(withs5);
			
			PdfPCell cell15= new PdfPCell();			
			pxk = new Paragraph("Đã nhận đủ số tiền (viết bằng chữ):..........................................................................................................." 
				, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell15.addElement(pxk);
			cell15.setBorder(0);						
			table5.addCell(cell15);
			
			PdfPCell cell16= new PdfPCell();			
			pxk = new Paragraph(".............................................................................................................................................................." 
				, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell16.addElement(pxk);
			cell16.setBorder(0);						
			table5.addCell(cell16);
			
			document.add(table5);
			
			
	// FOOTER
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200.0f,250.0f};
			table4.setWidths(withs4);
			
			PdfPCell cell17 = new PdfPCell();						
			pxk = new Paragraph("", new Font(bf,10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table4.addCell(cell17);	
						
			
			PdfPCell cell18 = new PdfPCell();						
			pxk = new Paragraph("Ngày......tháng.......năm.......", new Font(bf, 10, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell18.addElement(pxk);
			cell18.setBorder(0);						
			table4.addCell(cell18);	
			
			PdfPCell cell19 = new PdfPCell();						
			pxk = new Paragraph("NGUỜI NỘP", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell19.addElement(pxk);
			cell19.setBorder(0);						
			table4.addCell(cell19);	
						
			PdfPCell cell20 = new PdfPCell();						
			pxk = new Paragraph("THỦ QUỸ", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell20.addElement(pxk);
			cell20.setBorder(0);						
			table4.addCell(cell20);	
			
			PdfPCell cell21 = new PdfPCell();						
			pxk = new Paragraph(" ", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell21.addElement(pxk);
			cell21.setBorder(0);						
			table4.addCell(cell21);	
						
			
			PdfPCell cell22 = new PdfPCell();						
			pxk = new Paragraph(" ", new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell22.addElement(pxk);
			cell22.setBorder(0);						
			table4.addCell(cell22);	
			
			PdfPCell cell23 = new PdfPCell();						
			pxk = new Paragraph(" ", new Font(bf, 8, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell23.addElement(pxk);
			cell23.setBorder(0);						
			table4.addCell(cell23);	
						
			
			PdfPCell cell24 = new PdfPCell();						
			pxk = new Paragraph(" ", new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell24.addElement(pxk);
			cell24.setBorder(0);						
			table4.addCell(cell24);	
						
			
						
			document.add(table4);
				
		}
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
