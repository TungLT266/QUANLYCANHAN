package geso.traphaco.center.servlets.dieuchinhtonkho;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.beans.dieuchinhtonkho.*;
import geso.traphaco.center.beans.dieuchinhtonkho.imp.*;
import geso.traphaco.center.db.sql.dbutils;

import java.io.IOException;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfContentByte;


import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ErpChuyenkhoTTPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpChuyenkhoTTPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String id = request.getParameter("id");
		IErpChuyenkhoTT pxkBean = new ErpChuyenkhoTT(id);
		pxkBean.setUserId(userId);

		String task = request.getParameter("task");
		if (task.equals("chuyenkho"))
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();

			this.CreatePxk(document, outstream, pxkBean);
		} 
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpChuyenkhoTT pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
										
			
			String  sql = " select PK_SEQ, TEN ,DIACHI,'' AS DIENTHOAI,'' AS FAX,MASOTHUE, '' as SOTAIKHOAN "+
				        " from NHACUNGCAP ";				  
				   
			   
			   System.out.println("Lấy TT CTY "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   ctyTen = rsINFO.getString("TEN");
				   cty_MST = rsINFO.getString("MASOTHUE");
				   cty_Diachi = rsINFO.getString("DIACHI");
				   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
				   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
				   cty_Fax = rsINFO.getString("FAX");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen="";
			   String khoxuat_fk="";
			   String tenNPP="";
			   
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaydieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			   
			   sql = " select NGAYCHUYEN,C.TEN AS KHOXUAT ,B.TEN AS TENNPP ," +
			   		 "    lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong," +
			   		 "    ngayhopdong, nguoivanchuyen, ptvanchuyen " +
		        	 " from  ERP_CHUYENKHO A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 "       INNER JOIN ERP_KHOTT C ON A.KHOXUAT_FK=C.PK_SEQ " +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   khoxuat_fk = rskho.getString("KHOXUAT");
					   tenNPP = rskho.getString("TENNPP");
					   
					    lenhdieudong =rskho.getString("lenhdieudong");
					    lddcua =rskho.getString("lddcua");
					    lddveviec =rskho.getString("lddveviec");
					    ngaydieudong =rskho.getString("ngaydieudong");
					    sohopdong =rskho.getString("sohopdong");
					    ngayhopdong =rskho.getString("ngayhopdong");
					    nguoivanchuyen =rskho.getString("nguoivanchuyen");
					    ptvanchuyen =rskho.getString("ptvanchuyen");

				   rskho.close();
					   
				   }
		
		
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		document.open();
	   for(int sotrang = 0;sotrang < 3; sotrang++)
		{ 	   
			document.newPage();

			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
   //
			//Chữ Form quay góc 90 độ
			PdfContentByte cb = writer.getDirectContent();
			cb.beginText();
	        cb.setFontAndSize(bf, 6);
	        cb.setTextMatrix(0, 1, -1, 0, 20.1f*CONVERT, 15.0f * CONVERT);
	        cb.showText("In tại Công ty TNHH Một thành viên In Tài chính, Mã số thuế: 0100111225");
	        cb.endText();

			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);
			float[] withsheader = {100.0f, 250.0f,100.0f};
			tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableheader.setWidths(withsheader); 
			
			//LOGO
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/logo.gif");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			hinhanh.setWidthPercentage(60.0f);
			hinhanh.scalePercent(70);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellslogo.setVerticalAlignment(Element.ALIGN_TOP);
			cellslogo.setPadding(1);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			/*PdfPCell cella = new PdfPCell();
			cella.setHorizontalAlignment(Element.ALIGN_CENTER);
			cella.setVerticalAlignment(Element.ALIGN_TOP);
			
			
			Paragraph pxk = new Paragraph("LOGO", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cella.addElement(pxk);
			cella.setBorder(0);
			
			tableheader.addCell(cella);*/
			
			PdfPCell celltieude = new PdfPCell();
			celltieude.setHorizontalAlignment(Element.ALIGN_CENTER);
			celltieude.setVerticalAlignment(Element.ALIGN_TOP);
						
			Paragraph pxk = new Paragraph("PHIẾU XUẤT KHO",  new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(pxk);
			celltieude.setBorder(0);
						
			tableheader.addCell(celltieude);
			
			PdfPCell cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph("Mẫu số: 03XKNB3/001", new Font(bf, 8, Font.NORMAL));
			cell.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
						
			tableheader.addCell(cell);
			
			document.add(tableheader);	
			
			
			
			PdfPTable tableheader1=new PdfPTable(3);
			tableheader1.setWidthPercentage(100);
			float[] withsheader1 ={100.0f, 250.0f,100.0f};
			tableheader1.setWidths(withsheader1); 
			
			PdfPCell cell1 = new PdfPCell();			
			pxk = new Paragraph("Website: www.traphaco.com.vn", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingBefore(-25);
			pxk.setSpacingAfter(2);
			cell1.addElement(pxk);
			cell1.setBorder(0);
			tableheader1.addCell(cell1);
			
			
			PdfPCell cell2 = new PdfPCell();						
		    pxk = new Paragraph("KIÊM VẬN CHUYỂN NỘI BỘ", new Font(bf, 11, Font.BOLD));
		    pxk.setAlignment(Element.ALIGN_CENTER);
		    pxk.setSpacingBefore(-25);
			pxk.setSpacingAfter(2);
			cell2.addElement(pxk);
			cell2.setBorder(0);
						
			tableheader1.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell();			
			pxk = new Paragraph("Ký hiệu: TR/14P", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingBefore(-25);
			pxk.setSpacingAfter(2);
			cell3.addElement(pxk);
			cell3.setBorder(0);
						
			tableheader1.addCell(cell3);
			
			document.add(tableheader1);
			
			
			PdfPTable tableheader2=new PdfPTable(3);
			tableheader2.setWidthPercentage(100);
			float[] withsheader2 = {100.0f, 250.0f,100.0f};
			tableheader2.setWidths(withsheader2); 
			
			PdfPCell cell4 = new PdfPCell();			
			pxk = new Paragraph("", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell4.addElement(pxk);
			cell4.setBorder(0);
			tableheader2.addCell(cell4);
			
			
			PdfPCell cell5 = new PdfPCell();						

			if(sotrang==0)
				pxk = new Paragraph("Liên 1: Lưu", new Font(bf, 8, Font.NORMAL));
			if(sotrang==1)
				pxk = new Paragraph("Liên 2: Dùng để vận chuyển hàng", new Font(bf, 8, Font.NORMAL));
			if(sotrang==2)
				pxk = new Paragraph("Liên 3: Nội bộ", new Font(bf, 8, Font.NORMAL));
			
		    pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell5.addElement(pxk);
			cell5.setBorder(0);						
			tableheader2.addCell(cell5);
			
			PdfPCell cell6 = new PdfPCell();			
			pxk = new Paragraph("Số:", new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell6.addElement(pxk);
			cell6.setBorder(0);						
			tableheader2.addCell(cell6);
			
			document.add(tableheader2);
			
			String [] ngayin = ngaychuyen.split("-");
			pxk = new Paragraph("Ngày " +ngayin[2] +" tháng " +ngayin[1] +  " năm "+ngayin[0] , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			document.add(pxk);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {450.0f};
			table1.setWidths(withs1);
			
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph("Tên tổ chức, cá nhân: CÔNG TY CỔ PHẦN TRAPHACO" , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph("Mã số thuế: 0100108656 "  , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph("Địa chỉ: 75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam" , new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			String [] ngaydd = ngaydieudong.split("-");
			PdfPCell cell13 = new PdfPCell();			
			pxk = new Paragraph("Căn cứ Lệnh điều động số:" + lenhdieudong +" ngày "+ ngaydd[2] +" tháng "+ ngaydd[1] +" năm "+ ngaydd[0] +" của " +lddcua+ " về việc " +lddveviec, new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell13.addElement(pxk);
			cell13.setBorder(0);						
			table1.addCell(cell13);				
			
			
			PdfPCell cell15 = new PdfPCell();			
			pxk = new Paragraph("Họ tên người vận chuyển: "+ nguoivanchuyen +" .Số hợp đồng: "+ sohopdong +" .Ngày hợp đồng: "+ ngayhopdong +" .Phương tiện vận chuyển: "+ptvanchuyen, new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell15.addElement(pxk);
			cell15.setBorder(0);						
			table1.addCell(cell15);					
						
			document.add(table1);
			
			PdfPTable table2 =new PdfPTable(2);
			table2.setWidthPercentage(100);
			float[] withs2 = {250.0f,200.0f};
			table2.setWidths(withs2);
			
			
			PdfPCell cell17 = new PdfPCell();			
			pxk = new Paragraph("Xuất tại kho: "+khoxuat_fk, new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table2.addCell(cell17);		
			
			
			PdfPCell cell18 = new PdfPCell();			
			pxk = new Paragraph("Nhập tại kho: Kho của "+tenNPP, new Font(bf, 8, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell18.addElement(pxk);
			cell18.setBorder(0);						
			table2.addCell(cell18);		
			
			document.add(table2);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);


			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font5 = new Font(bf, 8, Font.BOLDITALIC);

			String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Số lượng","Đơn giá", "Thành tiền"};
			
			PdfPTable table = new PdfPTable(10);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =  { 10.0f, 35.0f, 15.0f, 13.0f, 10.0f,10.0f, 13.0f, 13.0f,20.0f,20.0f  };
			table.setWidths(withs);
			

			PdfPCell[] cell1s = new PdfPCell[10];
			for (int i = 0; i < 9; i++)
			{
				if(i==6)
				{
					cell1s[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 7, Font.NORMAL)));
					cell1s[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell1s[i].setVerticalAlignment(Element.ALIGN_TOP);
					cell1s[i].setVerticalAlignment(-1000);
					cell1s[i].setColspan(2);
					cell1s[i].setPaddingBottom(3.0f);
					table.addCell(cell1s[i]);	
				}
				else
				{
					cell1s[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 7, Font.NORMAL)));
					cell1s[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell1s[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell1s[i].setPadding(3.0f);
					cell1s[i].setRowspan(2);
					table.addCell(cell1s[i]);		
				}
	
				
			}
			String[] thxuat_nhan = new String[]{ "Thực xuất","Thực nhận"};
			for(int j=0; j <  2; j++)
			{
				cell1s[j] = new PdfPCell(new Paragraph(thxuat_nhan[j], new Font(bf, 7, Font.NORMAL)));
				cell1s[j].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1s[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1s[j].setPaddingBottom(3.0f);
				table.addCell(cell1s[j]);			
			}

			
			PdfPCell cellSTT = new PdfPCell();
			String[] arrSTT = new String[] {"1", "2","3","4","5","6","7","8","9","10"};
			for (int j = 0; j < 10; j++)
			{
				cellSTT = new PdfPCell(new Paragraph(arrSTT[j], new Font(bf, 7, Font.NORMAL)));
				cellSTT.setHorizontalAlignment(Element.ALIGN_CENTER);									
				cellSTT.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cellSTT.setPadding(0.1f * CONVERT);
				table.addCell(cellSTT);
			}

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP,c.solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHO a " +
			"   inner join ERP_CHUYENKHO_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHO_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
				tongslnhan +=soluongNhan;
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), rsSP.getString("handung"), "",
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), formatter.format(soluongNhan),formatter.format(dongia), formatter.format(thanhtien) };


				for (int j = 0; j < 10; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 7, Font.NORMAL)));
					if (j == 8 || j==9 ){
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
						
			
		String[] arr = new String[] {"", "Cộng","","","","",formatter.format(tongslchuyen), formatter.format(tongslnhan),"", formatter.format(totalTienTruocVAT)};
			for (int j = 0; j < 10; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 7, Font.BOLD)));
				if (j == 6 || j==7 || j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(0.1f * CONVERT);
				table.addCell(cells);
			}
			
					
																			
			document.add(table);
			
			//Table thời gian nhập xuất
			PdfPTable tableTg = new PdfPTable(3);
			tableTg.setWidthPercentage(90);
			tableTg.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTg.setWidths(new float[]
			{ 150.0f, 100.0f, 150.0f });

			PdfPCell cella = new PdfPCell(new Paragraph("Xuất, ngày          tháng          năm", new Font(bf, 8, Font.NORMAL)));
			cella.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cellb = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.BOLD)));
			cellb.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cellc = new PdfPCell(new Paragraph("Nhập, ngày          tháng          năm", new Font(bf, 8, Font.NORMAL)));
			cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cella.setBorder(0);
			cellb.setBorder(0);
			cellc.setBorder(0);
			
			tableTg.addCell(cella);
			tableTg.addCell(cellb);
			tableTg.addCell(cellc);

			document.add(tableTg);
			
			// Table Footer
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 40.0f, 30.0f, 30.0f, 40.0f });

			PdfPCell a = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 7, Font.BOLD)));
			a.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell b = new PdfPCell(new Paragraph("Thủ kho xuất", new Font(bf, 7, Font.BOLD)));
			b.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell c = new PdfPCell(new Paragraph("Người vận chuyển", new Font(bf, 7, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell d = new PdfPCell(new Paragraph("Thủ kho nhập", new Font(bf, 7, Font.BOLD)));
			d.setHorizontalAlignment(Element.ALIGN_CENTER);
		

			a.setBorder(0);
			b.setBorder(0);
			c.setBorder(0);
			d.setBorder(0);
			

			tableFooter.addCell(a);
			tableFooter.addCell(b);
			tableFooter.addCell(c);
			tableFooter.addCell(d);
			

			a = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			a.setHorizontalAlignment(Element.ALIGN_CENTER);
			b = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			b.setHorizontalAlignment(Element.ALIGN_CENTER);
			c = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			d = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			d.setHorizontalAlignment(Element.ALIGN_CENTER);
			

			a.setBorder(0);
			b.setBorder(0);
			c.setBorder(0);
			d.setBorder(0);
			

			tableFooter.addCell(a);
			tableFooter.addCell(b);
			tableFooter.addCell(c);
			tableFooter.addCell(d);
			

			for (int k = 0; k < 15; k++)
			{
				a = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				a.setHorizontalAlignment(Element.ALIGN_CENTER);
				b = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				b.setHorizontalAlignment(Element.ALIGN_CENTER);
				c = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				c.setHorizontalAlignment(Element.ALIGN_CENTER);
				d = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				d.setHorizontalAlignment(Element.ALIGN_CENTER);
				

				a.setBorder(0);
				b.setBorder(0);
				c.setBorder(0);
				d.setBorder(0);
				d.setBorder(0);

				tableFooter.addCell(a);
				tableFooter.addCell(b);
				tableFooter.addCell(c);
				tableFooter.addCell(d);
				

			}

			tableFooter.addCell(a);
			tableFooter.addCell(b);
			tableFooter.addCell(c);
			tableFooter.addCell(d);
											
			document.add(tableFooter);
			
			//GHI CHU
			
			PdfPTable tableGhichu =new PdfPTable(1);
			tableGhichu.setWidthPercentage(100);
			float[] withsGC = {450.0f};
			tableGhichu.setWidths(withsGC);
			
			PdfPCell cellGC = new PdfPCell();			
			pxk = new Paragraph("Ghi chú:          - Liên 1: Lưu          - Liên 2: Dùng để vận chuyển hàng         - Liên 3: Nội bộ" , new Font(bf, 6, Font.ITALIC));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cellGC.addElement(pxk);
			cellGC.setBorder(0);						
			tableGhichu.addCell(cellGC);	
			
			document.add(tableGhichu);
			
		}	
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
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

}
