package geso.traphaco.erp.servlets.huychungtu;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.*;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhang;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtubanhang;

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

public class ErpHuyChungTuBanHangPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpHuyChungTuBanHangPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; //
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		Utility util = new Utility();
		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(request.getParameter("pdf"));
		
		String print = util.antiSQLInspection(request.getParameter("print"));
		
		IErpHuychungtubanhang hdBean = new ErpHuychungtubanhang(id);
		hdBean.setUserId(userId);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		String task = request.getParameter("task");
		if (querystring.indexOf("pdf") > 0)
		{
			//hdBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename= HuyChungTuBanHangPdf.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();			
		   
		    this.CreateHd(document, outstream, hdBean );
						
		} 
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreateHd(Document document, ServletOutputStream outstream, IErpHuychungtubanhang hdBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
			
			String query = 
			   " select sochungtu, isnull(sodonhang, '') as sodonhang, isnull(sophieuxuatkho, '') as sophieuxuatkho," +
		       "       isnull(sohoadon, '') as sohoadon, loaichungtu, isnull(sobienban, '') sobienban, isnull(ngaybienban, '') ngaybienban, isnull(benA_bb, '') benA_bb, isnull(diachiA_bb, '') diachiA_bb," +
		       "  	   isnull(dienthoaiA_bb,'')  as dienthoaiA_bb, isnull(mstA_bb, '') mstA_bb, isnull(mstB_bb, '') mstB_bb, isnull(OngbaA_bb, '') OngbaA_bb, isnull(chucvuA_bb, '') chucvuA_bb, isnull(benB_bb,'') benB_bb," +
		       "	   isnull(diachiB_bb, '') diachiB_bb, isnull(dienthoaiB_bb, '') dienthoaiB_bb, isnull(OngbaB_bb, '') OngbaB_bb, isnull(chucvuB_bb, '') chucvuB_bb, " +
		       "	   isnull(sohoadon1_bb, '') sohoadon1_bb, isnull(kyhieu1_bb, '') kyhieu1_bb, isnull(sohoadon2_bb, '') sohoadon2_bb, " +
		       "	   isnull(ngayhoadon1_bb, '') ngayhoadon1_bb, isnull(sohoadon3_bb, '')  sohoadon3_bb, isnull(kyhieu2_bb, '') kyhieu2_bb, "+
		       "       isnull(sohoadon4_bb, '') sohoadon4_bb, isnull(ngayhoadon2_bb,'') ngayhoadon2_bb, isnull(lydothuhoi_bb, '') lydothuhoi_bb, isnull(ngaybb, '') ngaybb "+
			   " from erp_huychungtubanhang" +
			   " where pk_seq = '" + hdBean.getId() + "'";
			   
		   ResultSet rsLayKH= db.get(query);
		   
		   String sochungtu = "";
		   String sodonhang = "";
		   String sophieuxuatkho = "";
		   String loaichungtu = "";
		   String sobienban = "";
		   String ngaybienban = "";
		   String benA_bb = "";
		   String diachiA_bb = "";
		   String dienthoaiA_bb = "";
		   String mstA_bb = "";
		   String mstB_bb = "";
		   String OngbaA_bb = "";
		   String chucvuA_bb = "";
		   String benB_bb = "";
		   String diachiB_bb = "";
		   String dienthoaiB_bb = "";
		   String OngbaB_bb = "";
		   String chucvuB_bb = "";
		   String sohoadon1_bb = "";
		   String kyhieu1_bb = "";
		   String sohoadon2_bb = "";
		   String ngayhoadon1_bb = "";
		   String sohoadon3_bb = "";
		   String kyhieu2_bb = "";
		   String sohoadon4_bb = "";
		   String ngayhoadon2_bb = "";
		   String lydothuhoi_bb = "";
		   String ngaybb = "";
		   
		   if(rsLayKH.next())
		   {
			   sochungtu = rsLayKH.getString("sochungtu");
			   sodonhang = rsLayKH.getString("sodonhang");
			   sophieuxuatkho = rsLayKH.getString("sophieuxuatkho");
			   loaichungtu = rsLayKH.getString("loaichungtu");
			   sobienban = rsLayKH.getString("sobienban");
			   ngaybienban = rsLayKH.getString("ngaybienban");
			   benA_bb = rsLayKH.getString("benA_bb");
			   diachiA_bb = rsLayKH.getString("diachiA_bb");
			   dienthoaiA_bb = rsLayKH.getString("dienthoaiA_bb");
			   mstA_bb = rsLayKH.getString("mstA_bb");
			   mstB_bb = rsLayKH.getString("mstB_bb");
			   OngbaA_bb = rsLayKH.getString("OngbaA_bb");
			   chucvuA_bb = rsLayKH.getString("chucvuA_bb");
			   benB_bb = rsLayKH.getString("benB_bb");
			   diachiB_bb = rsLayKH.getString("diachiB_bb");
			   dienthoaiB_bb = rsLayKH.getString("dienthoaiB_bb");
			   OngbaB_bb = rsLayKH.getString("OngbaB_bb");
			   chucvuB_bb = rsLayKH.getString("chucvuB_bb");
			   sohoadon1_bb = rsLayKH.getString("sohoadon1_bb");
			   kyhieu1_bb = rsLayKH.getString("kyhieu1_bb");
			   sohoadon2_bb = rsLayKH.getString("sohoadon2_bb");
			   ngayhoadon1_bb = rsLayKH.getString("ngayhoadon1_bb");
			   sohoadon3_bb = rsLayKH.getString("sohoadon3_bb");
			   kyhieu2_bb = rsLayKH.getString("kyhieu2_bb");
			   sohoadon4_bb = rsLayKH.getString("sohoadon4_bb");
			   ngayhoadon2_bb = rsLayKH.getString("ngayhoadon2_bb");
			   lydothuhoi_bb = rsLayKH.getString("lydothuhoi_bb");
			   ngaybb = rsLayKH.getString("ngaybb");
			   
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			//document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logoPhanam.png");
			hinhanh.scalePercent(50);
			hinhanh.setAbsolutePosition(2.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
			document.add(hinhanh);
			
			Paragraph pxk = new Paragraph();
			
			// Thông tin CÔNG TY
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									
			
			
			// DONG 1-- THÔNG TIN CÔNG TY
			PdfPCell cell_nguoimua = new PdfPCell();	
			pxk = new Paragraph("Công Ty Cổ Phần Dược Pha Nam "  , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingBefore(1.0f*CONVERT);
			cell_nguoimua.addElement(pxk);
			cell_nguoimua.setBorder(0);						
			table1.addCell(cell_nguoimua);	
			
			PdfPCell cell_nguoimua1 = new PdfPCell();
			pxk = new Paragraph("Cộng Hòa Xã Hội Chủ Nghĩa Việt Nam", new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell_nguoimua1.addElement(pxk);
			cell_nguoimua1.setBorder(0);						
			table1.addCell(cell_nguoimua1);
			
			// DONG 2-- THÔNG TIN CÔNG TY
			PdfPCell cell8 = new PdfPCell();	
			pxk = new Paragraph("Số:    /BBĐCHĐ/PHANAM"  , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);	
			
			PdfPCell cell8a = new PdfPCell();
			pxk = new Paragraph("Độc Lập – Tự Do – Hạnh Phúc", new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- THÔNG TIN CÔNG TY
			PdfPCell cell10 = new PdfPCell();
			pxk = new Paragraph(" ", new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell10.addElement(pxk);
			cell10.setBorder(0);						
			table1.addCell(cell10);
							
			if(ngaybb.trim().length() > 0)
			{
			String [] ngay = ngaybb.split("-");
			
			PdfPCell cell14 = new PdfPCell();
			pxk = new Paragraph("TPHCM, Ngày "+ngay[2]+" Tháng "+ngay[1]+"  Năm "+ngay[0], new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);	
			}
			else
			{
				PdfPCell cell14 = new PdfPCell();
				pxk = new Paragraph("TPHCM, Ngày     Tháng   Năm     ", new Font(bf, 13, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_CENTER);
				cell14.addElement(pxk);
				cell14.setBorder(0);						
				table1.addCell(cell14);
			}
			
			
			//DONG 4 --- BIÊN BẢN THU HỒI HOÁ ĐƠN  ĐÃ LẬP
			PdfPCell cell10a = new PdfPCell();
			pxk = new Paragraph("BIÊN BẢN THU HỒI HOÁ ĐƠN  ĐÃ LẬP ", new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell10a.addElement(pxk);
			cell10a.setBorder(0);	
			cell10a.setColspan(2);
			table1.addCell(cell10a);
						
			
			PdfPCell cell14a = new PdfPCell();
			pxk = new Paragraph("Số:"+sobienban+ "/BBTHHĐ", new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell14a.addElement(pxk);
			cell14a.setBorder(0);	
			cell14a.setColspan(2);
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			pxk = new Paragraph("- Căn cứ Thông tư 39/2014/TT-BTC ngày 31/3/2014 của Bộ tài chính hướng dẫn thi hành Nghị định số 51/2010/NĐ-CP.", new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			cell17.setColspan(2);
			table1.addCell(cell17);	
			
			PdfPCell cell18 = new PdfPCell();
			pxk = new Paragraph( "- Căn cứ vào khoàn 7 điều 2 thông tư 26/2015/TT-BTC của Bộ tài chính hướng dẫn thuế giá trị gia tăng; quản lý thuế và hóa đơn. ", new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell18.addElement(pxk);
			cell18.setColspan(2);
			cell18.setBorder(0);				
			table1.addCell(cell18);       
			
			PdfPCell cell19 = null;
			if(ngaybienban.trim().length() > 0)
			{
				String [] ngayBB = ngaybienban.split("-");
				cell19 = new PdfPCell();
				pxk = new Paragraph( " Hôm nay, ngày "+ngayBB[2]+"/"+ ngayBB[1]+ "/"+ ngayBB[0]+", đại diện hai bên gồm có: ", new Font(bf, 13, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell19.addElement(pxk);
				cell19.setColspan(2);
				cell19.setBorder(0);				
				table1.addCell(cell19);  
			}
			else
			{
				cell19 = new PdfPCell();
				pxk = new Paragraph( " Hôm nay, ngày    /    /    , đại diện hai bên gồm có: ", new Font(bf, 13, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell19.addElement(pxk);
				cell19.setColspan(2);
				cell19.setBorder(0);				
				table1.addCell(cell19);  
			}
			
			PdfPCell cell20 = new PdfPCell();
			pxk = new Paragraph("");
			pxk.add(new Chunk("BÊN A : ", new Font(bf, 13, Font.BOLD))); 	
			pxk.add(new Chunk(benA_bb, new Font(bf, 13, Font.NORMAL))); 			
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell20.addElement(pxk);
			cell20.setBorder(0);	
			cell20.setColspan(2);
			table1.addCell(cell20); 
			
			PdfPCell cell21 = new PdfPCell();			
			pxk = new Paragraph( " Địa chỉ : " + diachiA_bb, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell21.addElement(pxk);
			cell21.setBorder(0);	
			cell21.setColspan(2);
			table1.addCell(cell21); 
			
			PdfPCell cell22 = new PdfPCell();
			pxk = new Paragraph( " Điện thoại : " + dienthoaiA_bb + " \t \t \t \t \t MST: "+mstA_bb, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell22.addElement(pxk);
			cell22.setBorder(0);	
			cell22.setColspan(2);
			table1.addCell(cell22); 
			
			PdfPCell cell23 = new PdfPCell();
			pxk = new Paragraph( " Do Ông (Bà) : " + OngbaA_bb + " \t \t \t \t \t Chức vụ:"+chucvuA_bb, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			cell23.setColspan(2);
			table1.addCell(cell23); 
			
			cell20 = new PdfPCell();
			pxk = new Paragraph("");
			pxk.add(new Chunk("BÊN B : ", new Font(bf, 13, Font.BOLD))); 	
			pxk.add(new Chunk(benB_bb, new Font(bf, 13, Font.NORMAL))); 
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell20.addElement(pxk);
			cell20.setBorder(0);	
			cell20.setColspan(2);
			table1.addCell(cell20);  
			
			cell21 = new PdfPCell();
			pxk = new Paragraph( " Địa chỉ : " + diachiB_bb, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell21.addElement(pxk);
			cell21.setBorder(0);	
			cell21.setColspan(2);
			table1.addCell(cell21); 

			cell22 = new PdfPCell();
			pxk = new Paragraph( " Điện thoại : " + dienthoaiB_bb + " \t \t \t \t \t MST: "+mstB_bb, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell22.addElement(pxk);
			cell22.setBorder(0);	
			cell22.setColspan(2);
			table1.addCell(cell22); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " Do Ông (Bà) : " + OngbaB_bb + " \t \t \t \t \t Chức vụ:"+chucvuB_bb, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			cell23.setColspan(2);
			table1.addCell(cell23); 
			
			String [] ngayHD1 = ngayhoadon1_bb.split("-");
			String [] ngayHD2 = ngayhoadon2_bb.split("-");
			
			cell19 = new PdfPCell();
			
			String abc1 = "";
			String abc2 = "";
			if(ngayhoadon1_bb.trim().length()>0)
				abc1 = " ngày " + ngayHD1[2]+"/"+ ngayHD1[1]+ "/"+ ngayHD1[0];
			if(ngayhoadon2_bb.trim().length()>0)
				abc2 = " ngày " + ngayHD2[2]+"/"+ ngayHD2[1]+ "/"+ ngayHD2[0];
				
			pxk = new Paragraph( " Hai bên, thống nhất lập biên thu hồi (Liên 2) hóa đơn " + sohoadon1_bb + " đã lập, có ký hiệu:" + kyhieu1_bb + " số " + sohoadon2_bb +abc1 +
								 " để xóa bỏ theo quy định, và sẽ xuất thay thế bằng hóa đơn số "+ sohoadon3_bb + ", ký hiệu: " + kyhieu2_bb + " số " + sohoadon4_bb +abc2, new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell19.addElement(pxk);
			cell19.setColspan(2);
			cell19.setBorder(0);				
			table1.addCell(cell19);  
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " Lý do thu hồi: " + lydothuhoi_bb , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			cell23.setColspan(2);
			table1.addCell(cell23); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " Chúng tôi cam kết và hoàn toàn chịu trách nhiệm về việc thu hồi và xóa bỏ hóa đơn này. " , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			cell23.setColspan(2);
			table1.addCell(cell23); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " Biên bản này lập thành 02 bản, Bên A giữ 01 bản, Bên B giữ 01 bản. " , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			cell23.setColspan(2);
			table1.addCell(cell23); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " ĐẠI DIỆN BÊN A " , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			table1.addCell(cell23); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " ĐẠI DIỆN BÊN B " , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			table1.addCell(cell23); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " Ký tên, đóng dấu  " , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			table1.addCell(cell23); 
			
			cell23 = new PdfPCell();
			pxk = new Paragraph( " Ký tên, đóng dấu " , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell23.addElement(pxk);
			cell23.setBorder(0);	
			table1.addCell(cell23); 
			
			document.add(table1);				
			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param document
	 * @param outstream
	 * @param hdBean
	 * @throws IOException
	 */
	private void CreateHd_QUANGNGAI(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) throws IOException
	{
		try
		{			
			dbutils db = new dbutils();
				
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "";
			String ngayxuatHD ="";
			String chucuahieu="";
			double chietkhauDH = 0;
			
			String sql =
			"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";

			System.out.println("[INIT_DONHANG]"+sql);
			
			ResultSet rsCheck = db.get(sql);					
								
			if(rsCheck.next())
			{
				npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
				khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
				ddh = rsCheck.getString("PK_SEQ");
				chucuahieu = rsCheck.getString("nguoimua");
				kbh = rsCheck.getString("KBH_FK");
				khoxuat = rsCheck.getString("KHO");
				hinhthucTT = rsCheck.getString("HTTT");
				ngayxuatHD = rsCheck.getString("ngayxuathd");
				chietkhauDH = rsCheck.getDouble("chietkhauDH");
				rsCheck.close();
			}
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ hdBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
		   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
		/*	  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
		/*	sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   } 
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.####");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	
		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(1.0f * CONVERT);
		cell.setPaddingLeft(1.9f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 9, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		/****   DONG 1 ***********/
		PdfPCell cell8 = new PdfPCell();	
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8.setPaddingLeft(4.0f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8a.setPaddingLeft(5.4f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);	
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8a);
		/**** END DONG 1 ************/

		/****   DONG 2 ***********/
		cell8 = new PdfPCell();	
		cell8.setPaddingTop(-0.1f * CONVERT);
		cell8.setPaddingLeft(2.0f * CONVERT);
		pxk = new Paragraph(" " , new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingTop(-0.1f * CONVERT);
		cell10.setPaddingLeft(3.3f * CONVERT);	
		pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell10.addElement(pxk);
		cell10.setBorder(0);	
		//cell10.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell10);
		/**** END DONG 1 ************/
				
		
		/****   DONG 3 ***********/
		PdfPCell cell14 = new PdfPCell();
		cell8a.setPaddingTop(-0.5f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);	
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		PdfPCell cell17 = new PdfPCell();	
		cell8a.setPaddingTop(-0.5f * CONVERT);
		cell17.setPaddingLeft(3.4f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 3 ************/
		
		/****   DONG 4 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.1f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		cell17 = new PdfPCell();
		cell17.setPaddingTop(-0.1f * CONVERT);
		cell17.setPaddingLeft(3.0f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 4 ************/
		
		/****   DONG 5 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.15f * CONVERT);
		cell14.setPaddingLeft(2.4f * CONVERT);
		pxk = new Paragraph(" "+khoxuat, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingTop(-0.15f * CONVERT);
		cell18.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell18.addElement(pxk);
		cell18.setBorder(0);	
		//cell18.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell18);    
		/**** END DONG 5 ************/
					
		document.add(table1);
			
		// Table Content
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(47.8f); //45
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 10.0f, 55f, 12.0f, 15.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = "";
			
			if(SoNgay(ngayxuatHD)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query =
					 "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
						"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
						"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
						"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
						"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, " +
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
						"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
						"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
						"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
						"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
						"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
						"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
						"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}

			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double vatCK = 0;
			double totalCK = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			double chenhlech =0;
			
			while(rsSP.next())
			{

				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuatHD)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					thanhtien =Math.round(soLUONG*dongia);
					thueGTGT = Math.round(thanhtien *vat/100);
					sotientt = thanhtien + (thanhtien*vat/100) ;
					
					vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuatHD)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + '-' +rsSP.getString("TEN"), rsSP.getString("SoLo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangCANFOCO(formatter1.format(soLUONG)), DinhDangCANFOCO(formatter.format(dongia)),
						DinhDangCANFOCO(formatter1.format(thanhtien)),DinhDangCANFOCO(formatter1.format(rsSP.getDouble("thuevat"))),
						DinhDangCANFOCO(formatter1.format(thueGTGT)),DinhDangCANFOCO(formatter1.format(sotientt)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					
					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.6f*CONVERT);
					if( j== 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if( j== 3)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if( j== 4)
						cells.setPaddingLeft(-0.2f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(-0.7f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(-0.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(-0.2f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
		
		// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
													
					
					sanpham.addCell(cells);
				}
				
				kk++;
			}
				totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," ", DinhDangCANFOCO(formatter1.format(totalTienTruocVAT -totalCK )),
					                      " ",DinhDangCANFOCO( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangCANFOCO(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
		
	
		for (int j = 0; j < arr.length; j++)
			{
			cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
			if(j <=4 )
			{
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			}
			else
			{
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
			
			if(j == 0)
				cells.setPaddingLeft(0.6f*CONVERT);
			if( j== 1)
				cells.setPaddingLeft(0.2f*CONVERT);
			if( j== 3)
				cells.setPaddingLeft(-0.6f*CONVERT);
			if( j== 4)
				cells.setPaddingLeft(-0.2f*CONVERT);
			if(j == 6)
				cells.setPaddingRight(-0.7f*CONVERT);
			if(j == 7)
				cells.setPaddingRight(-0.6f*CONVERT);
			if(j == 8)
				cells.setPaddingRight(-0.6f*CONVERT);
			if(j == 9)
				cells.setPaddingRight(-0.2f*CONVERT);
			if(j == 10)
				cells.setPaddingRight(-0.2f*CONVERT);
			
			cells.setVerticalAlignment(Element.ALIGN_TOP);
			cells.setPaddingLeft(0.8f * CONVERT);
			cells.setPaddingTop(-8.4f);
			cells.setBorder(0);
			cells.setFixedHeight(0.6f*CONVERT);
			sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			   String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-5.6f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	

/*	public static void main(String[] arg)
	{
		ErpHoadontaichinhNPPPdfSvl hd = new ErpHoadontaichinhNPPPdfSvl();
		
		System.out.println(hd.DinhDangCANFOCO("12,000.56"));
	}*/
	
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
	
	
	private double roundNumer(double num, int dec)
	{
		double result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	private boolean SoNgay (String ngayxuathd){
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2014-01-09', '"+ngayxuathd+"') songay";
		ResultSet checkngay = db.get(layngay);
		
		try{
			if(checkngay.next())
			{
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if(songay >=0 ) kt = true;
		}
		catch (Exception e){
			e.printStackTrace();
			kt = false;
		}
		
		return kt;
		
	}
	
	private String DinhDangCANFOCO(String sotien)
	 {
	  sotien = sotien.replaceAll("\\.", "_");
	  sotien = sotien.replaceAll(",", "\\.");
	  sotien = sotien.replaceAll("_", ",");
	  
	  return sotien;
	 }
	
}
