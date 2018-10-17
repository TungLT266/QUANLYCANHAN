package geso.traphaco.erp.servlets.donmuahangtrongnuoc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.nhanhang.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.servlets.denghimuahang.ErpDenghimuahangUpdateSvl;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInDonMuaHangVaGiaCongPdfSvl extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public ErpInDonMuaHangVaGiaCongPdfSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		//-------------------------//
		String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
			if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
			dmhBean.setCongtyId((String) session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init

			String canduyet = request.getParameter("duyet");
			if (canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);

				dmhBean.init();
				
				/*if(dmhBean.getIsGiaCong().equals("1") && dmhBean.getTrangthai().equals("2")){*/
				if(dmhBean.getIsGiaCong().equals("1")){
					//tien hanh in
					CreatePDF(request, response, dmhBean);
				}
				else{
					dmhBean.setMsg("Đơn mua hàng phải là đơn  gia công");
					session.setAttribute("nhacungcapNK", dmhBean.getNhacungcapNK());
					session.setAttribute("ngaymuahang", dmhBean.getNgaymuahang());
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocDisplay_Giay.jsp";
					session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
					session.setAttribute("lspId", dmhBean.getLoaispId());
					session.setAttribute("dmhBean", dmhBean);
					session.setAttribute("loaimh", dmhBean.getLoai());
					session.setAttribute("nccId", dmhBean.getNCC());
					response.sendRedirect(nextJSP);
				}

				
			}
	
	}
	private boolean deletefile(String file)
	{
		System.out.println(file);
		File f1 = new File(file);
		boolean success = f1.delete();
		if (!success)
		{
			return false;
		} else
		{
			return true;
		}
	}
	
private void CreatePDF( HttpServletRequest request,HttpServletResponse response, IErpDonmuahang_Giay nhBean) {
		
		//định nghĩa kiểu trả về cho response là pdf
		response.setContentType("application/pdf");
		//đặt tên cho file của bạn
		response.setHeader("Content-Disposition"," inline; filename=DonBanHangInPdf_Trinh.pdf");
		
		float CONVERT = 28.346457f;//1 cm 
		
		//chuyển về cm
		float PAGE_LEFT = 2.0f*CONVERT;
		float PAGE_RIGHT = 1.5f*CONVERT;
		float PAGE_TOP = 0.5f*CONVERT ;
		float PAGE_BOTTOM = 0.0f*CONVERT; 
		
		//định dạng khổ giấy
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;
		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			 
			this.FillData(document, outstream, response, request, db, nhBean);
			db.shutDown();
		
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("___Exception Print: " + e.getMessage());
		}
	}
	
	private void FillData(Document document, ServletOutputStream outstream, 
			HttpServletResponse response, HttpServletRequest request, dbutils db, IErpDonmuahang_Giay dmhBean){
		Utility util = new Utility();
		try
		{
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font9_bold = new Font(bf,9,Font.BOLD);
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			font9_italic.setColor(79, 79, 79);
			//Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_normal = new Font(bf, 12, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);
			Font font13_bold = new Font(bf, 12, Font.BOLD);
			Font font13_normal = new Font(bf, 11, Font.NORMAL);
			//Font font13_bold_underline = new Font(bf, 13, Font.UNDERLINE);
			Font font13_bold_italic = new Font(bf, 12, Font.BOLDITALIC);
			Font font13_italic = new Font(bf, 12, Font.ITALIC);
			
			
			
			//-----TAO HEADER-----//
			
		
			PdfPTable tab_Header = new PdfPTable(3);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_CENTER);

			// Header 1
			
			PdfPCell cell2;
			cell2 = new PdfPCell(new Phrase("", font11_bold));
			// insert logo traphaco
			//--chen hinh logo
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint") + "\\logo.gif");
			img.scalePercent(10);

			cell2 = new PdfPCell();
			cell2.addElement(new Chunk(img, 30, 0));
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_Header.addCell(cell2);
			//table.addCell(cell2);

			PdfPCell cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BM28/04", font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingBottom(1);
			cell.setBorder(0);
			tab_Header.addCell(cell);
			
			font11_normal.setColor(105, 105, 105);
			cell2 = new PdfPCell(new Phrase("   CÔNG TY CP TRAPHACO", font10_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(1.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);
			
			
			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);
		

			String date = "03/09/14";
			cell = new PdfPCell(new Paragraph("Ngày BH/SĐ:" + date, font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(1);
			cell.setBorder(0);
			tab_Header.addCell(cell);
		
			document.add(tab_Header);
	
			//----Add tua de-------------------//
			Paragraph tuade = new Paragraph("ĐƠN MUA HÀNG & GIA CÔNG", new Font(bf, 15, Font.BOLD));
			tuade.setSpacingBefore(30);
			//donDH.setSpacingAfter(1);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);
			
			
			//-------------lay thang cua don hang------------//
			String ngaymuahang = dmhBean.getNgaymuahang();
			String[] tachChuoi = ngaymuahang.split("-");
			
			String thang = tachChuoi[1];
			String nam = tachChuoi[0];
			
			tuade = new Paragraph("THÁNG "+ thang +" NĂM " + nam, new Font(bf, 15, Font.BOLD));
			tuade.setSpacingBefore(2);
			//donDH.setSpacingAfter(1);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);

			//----------add noi dung---------------//
			
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10.0f);
			float[] tblWitdh = { 150.0f, 80.0f,500.0f };
			table.setWidths(tblWitdh);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell = new PdfPCell(new Paragraph(" ", font13_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			Chunk underline = new Chunk("Kính gửi: ",font13_italic );
			underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			
			cell.addElement(underline);
			table.addCell(cell);
			
			
			underline = new Chunk("Công ty cổ phần công nghệ cao TRAPHACO",font13_italic );
			//underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
			cell = new PdfPCell();
			//cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			
			cell.addElement(underline);
			table.addCell(cell);
			/*cell = new PdfPCell(new Paragraph("Công ty cổ phần công nghệ cao TRAPHACO", font13_italic));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);*/
			table.addCell(cell);
			
			document.add(table);
			
			Paragraph text = new Paragraph("    - Căn cứ vào nhu cầu thị trường, tồn kho & Kế hoạch tháng ..../20.....", font13_normal);
			text.setSpacingBefore(5);
			text.setSpacingAfter(1);
			text.setAlignment(Element.ALIGN_LEFT);
			document.add(text);
			
			text = new Paragraph("    - Công ty chúng tôi gửi đơn mua hàng & gia công tháng "+thang+" năm " + nam +" như sau:", font13_normal);
			text.setSpacingBefore(2);
			text.setSpacingAfter(1);
			text.setAlignment(Element.ALIGN_LEFT);
			document.add(text);
			
			//----------lay thong tin don vi thuc hien------//
			float[] crDonHang = { 1.0f * CONVERT, 3.5f * CONVERT, 2.0f * CONVERT, 2.0f * CONVERT, 3.0f * CONVERT,
					2.0f * CONVERT};

			PdfPTable tab_sp = new PdfPTable(6);
			tab_sp.setWidthPercentage(100);
			tab_sp.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_sp.setWidths(crDonHang);
			tab_sp.getDefaultCell().setBorder(0);
			tab_sp.setSpacingAfter(8.0f);
			tab_sp.setSpacingBefore(5.0f);
			
			String[] spTitles = { "STT", "Tên sản phẩm", "Đơn vị", "Số lượng", "Số lô sản xuất", "Ghi chú"};
			PdfPCell[] celltable = new PdfPCell[6];
			for(int i=0; i < 6 ; i++)
			{
				if(i == 4){
					
					celltable[i] = new PdfPCell();
					text = new Paragraph("Số lô", font13_bold);
					text.setSpacingAfter(1);
					text.setAlignment(Element.ALIGN_CENTER);
					celltable[i].addElement(text);
					
					
					text = new Paragraph("sản xuất", font13_bold);
					text.setSpacingBefore(1);
					text.setSpacingAfter(1);
					text.setAlignment(Element.ALIGN_CENTER);
					celltable[i].addElement(text);
					
					celltable[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					celltable[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					celltable[i].setPaddingBottom(5.0f);
					//celltable[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
					tab_sp.addCell(celltable[i]);
					
				}
				else{
						celltable[i] = new PdfPCell(new Paragraph(spTitles[i], font13_bold));
						celltable[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						celltable[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						celltable[i].setPaddingBottom(5.0f);
						//celltable[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
						tab_sp.addCell(celltable[i]);	
				}
			}
			//--------------add san pham-------------//
			List<ISanpham> spList = dmhBean.getSpList();
			for (int i = 0; i < spList.size(); i++){
				ISanpham sp = spList.get(i);
				int stt = i + 1;
				String[] spTitles2 = {""+ stt, sp.getTensanpham(), sp.getDonvitinh(),sp.getSoluong(),
					"" , "" } ;
				for(int j = 0; j<6; j++){
					celltable[j] = new PdfPCell(new Paragraph(spTitles2[j], font13_normal));
					if(j == 2){
						celltable[j].setHorizontalAlignment(Element.ALIGN_CENTER);
					}else
					if(j == 3 || j == 4){
						celltable[j].setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else{
						celltable[j].setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					cell.setBorderWidth(0);
					celltable[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
					celltable[j].setPaddingBottom(5.0f);		
					tab_sp.addCell(celltable[j]);
				}
			}
			document.add(tab_sp);
			
			//---------------- ngay thang----------------------//
			
			text = new Paragraph("Hà Nội, ngày " + getDateNow(), font13_normal);
			text.setSpacingBefore(10);
			text.setSpacingAfter(1);
			text.setAlignment(Element.ALIGN_RIGHT);
			document.add(text);
			
			
			//-----------------------Ky ten---------------------//
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(30.0f);
			
			cell = new PdfPCell(new Paragraph("P. TỔNG GIÁM ĐỐC TRAPHACO", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("PHÒNG KẾ HOẠCH TRAPHACO", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			document.add(table);
			
			document.close(); 
	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
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
	
	private String getDateNow()
	{
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String d = dateFormat.format(date);
        String[] tach = d.split("/");
        
       
        //Lấy ngày hiện tai.
        //Get current date.
        String ngay = tach[2];
        String thang = tach[1];
        String nam = tach[0];
		return ngay + "  tháng  " + thang + "  Năm  " + nam;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		
	}
	

	
	public static void main(String[] arg)
	{
		ErpDenghimuahangUpdateSvl dmh = new ErpDenghimuahangUpdateSvl();
		dmh.removeFONT("");
	}
	
}
