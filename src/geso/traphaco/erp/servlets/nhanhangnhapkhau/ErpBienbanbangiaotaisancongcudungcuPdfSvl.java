package geso.traphaco.erp.servlets.nhanhangnhapkhau;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.denghimuahang.IErpDenghimuahang;
import geso.traphaco.erp.beans.denghimuahang.IErpDenghimuahangList;
import geso.traphaco.erp.beans.denghimuahang.imp.ErpDenghimuahang;
import geso.traphaco.erp.beans.denghimuahang.imp.ErpDenghimuahangList;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.imp.Sanpham;
import geso.traphaco.erp.beans.nhanhang.*;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.servlets.denghimuahang.ErpDenghimuahangUpdateSvl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.oreilly.servlet.MultipartRequest;

public class ErpBienbanbangiaotaisancongcudungcuPdfSvl extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public ErpBienbanbangiaotaisancongcudungcuPdfSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		String nextJSP;
		HttpSession session = request.getSession();
		
		String userTen = (String) session.getAttribute("userTen");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");

			session.setMaxInactiveInterval(30000);

			
			String querystring = request.getQueryString();
			String userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			System.out.println("id :" + id );
			IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay(id);
			nhBean.setUserId(userId);
			
			String loaimh = util.antiSQLInspection(request.getParameter("loai"));
			if(loaimh == null) loaimh = "1";
			nhBean.setLoaimh(loaimh);
			System.out.println("Loai: " + loaimh);
			
			nhBean.init();
			
			System.out.print("Loai hang hoa: " + nhBean.getLoaihanghoa());
			if(nhBean.getLoaihanghoa().equals("1")){
				CreatePDF(request, response, nhBean);
				
				
			}
			else{
		
				
				nhBean.setLoaimh(loaimh);
				nhBean.createRs();
				nhBean.setMsg("Chỉ in loại tài sản cố định");
				session.setAttribute("nhBean", nhBean);
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangDisplay_Giay.jsp";
				response.sendRedirect(nextJSP);
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
	
private void CreatePDF( HttpServletRequest request,HttpServletResponse response, IErpNhanhang_Giay nhBean) {
		
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
			HttpServletResponse response, HttpServletRequest request, dbutils db, IErpNhanhang_Giay nhBean){
		Utility util = new Utility();
		try
		{
			
			//-----TAO HEADER-----//
			
			//--chen hinh logo

			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			/*Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font9_bold = new Font(bf,9,Font.BOLD);*/
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			font9_italic.setColor(79, 79, 79);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_normal = new Font(bf, 12, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);
			Font font13_bold = new Font(bf, 13, Font.BOLD);
			Font font13_normal = new Font(bf, 13, Font.NORMAL);
			//Font font13_bold_underline = new Font(bf, 13, Font.UNDERLINE);
			Font font13_bold_italic = new Font(bf, 13, Font.BOLDITALIC);
			Font font13_italic = new Font(bf, 13, Font.ITALIC);
			
			PdfPTable tab_Header = new PdfPTable(3);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_CENTER);
			//float[] crtbl_header = { 1000f };
			//tab_Header.setWidths(crtbl_header[0]);

			// Header 1
			
			PdfPCell cell2;
			cell2 = new PdfPCell(new Phrase("", font11_bold));
			// insert logo traphaco
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
			
			cell = new PdfPCell(new Paragraph("BM15/01", font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);
			
			font11_normal.setColor(105, 105, 105);
			cell2 = new PdfPCell(new Phrase("CÔNG TY CP TRAPHACO", font11_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(1.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);
			
			
			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);
		

			String date = "03/09/14";
			cell = new PdfPCell(new Paragraph("Ngày BH:" + date, font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);
		
			document.add(tab_Header);
	
			
			
			//----Add tua de-------------------//
			Paragraph tuade = new Paragraph("BIÊN BẢN BÀN GIAO TÀI SẢN – CÔNG CỤ DỤNG CỤ", new Font(bf, 14, Font.BOLD));
			tuade.setSpacingBefore(30);
			//donDH.setSpacingAfter(1);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);
			
			
			//----------add noi dung---------------//
			Paragraph datetext = new Paragraph("Hôm nay, ngày " + getDateNow(), font13_normal);
			datetext.setSpacingBefore(30);
			datetext.setSpacingAfter(1);
			datetext.setAlignment(Element.ALIGN_LEFT);
			document.add(datetext);
			
			//----------lay thong tin don vi thuc hien------//
			
			String tenDonVi = nhBean.getDvthId();
			String query = "select dv.TEN as TenDonVi, ct.DIACHI from ERP_DONVITHUCHIEN dv left join ERP_Congty ct " +
					"On dv.CONGTY_FK = ct.PK_SEQ " +
					"WHERE dv.PK_SEQ =" + nhBean.getDvthId();
			db = new dbutils();
			String tendv = "";
			String diachi = "";
			ResultSet rs = db.get(query);
			try {
				
				
				while (rs.next()) {
					tendv = rs.getString("TenDonVi");
					diachi = rs.getString("DIACHI");
					
				}
				rs.close();
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("ten don vi: " + tenDonVi);
			Paragraph tencongty = new Paragraph("Tại (công ty hoặc bộ phận): " + tendv, font13_normal);
			tencongty.setSpacingBefore(1);
			tencongty.setSpacingAfter(1);
			tencongty.setAlignment(Element.ALIGN_LEFT);
			document.add(tencongty);
			
			Paragraph para = new Paragraph("Địa chỉ: " + diachi, font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			para = new Paragraph("Chúng tôi gồm:" + "", font13_normal);
			
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			
		/*	font13_bold_underline.setStyle(Font.BOLD);
			para = new Paragraph("Bên bàn giao:" + "", font13_bold_underline);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);*/
			font13_bold_italic.setColor(28, 28, 28);
			Chunk underline = new Chunk("Bên bàn giao:",font13_bold_italic );
			underline.setUnderline(0.2f, -2f); //0.1 thick, -2 y-location
			document.add(underline);
			
			para = new Paragraph("Địa chỉ : " + "", font13_bold_italic);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			
			
			table.setSpacingBefore(10.0f);
			float[] tblWitdh = { 800f };
			//table.setWidths(tblWitdh);\
			
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			cell = new PdfPCell(new Paragraph("               Ông (bà):", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("           - Phụ trách bộ phận :", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("               Ông (bà):", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("           - Bộ phận công tác :", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			//para.add(table)table;
			document.add(table);
			
			para = new Paragraph("Bên nhận bàn giao : " + "", font13_bold_italic);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			para = new Paragraph("Địa chỉ : " + "", font13_bold_italic);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setSpacingBefore(7.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			//float[] tblWitdh = { 5*CONVERT };
			//table.setWidths(tblWitdh);

			cell = new PdfPCell(new Paragraph("               Ông (bà):", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("           - Phụ trách bộ phận :", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("               Ông (bà):	", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("           - Bộ phận công tác :", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			document.add(table);
			
			para = new Paragraph("Tiến hành bàn giao tài sản, công cụ dụng cụ chi tiết như sau:" + "", font13_normal);
			para.setSpacingBefore(10.0f);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			
			float[] crDonHang = { 1.0f * CONVERT, 3.5f * CONVERT, 2.0f * CONVERT, 2.0f * CONVERT, 3.0f * CONVERT,
					2.0f * CONVERT};

			PdfPTable tab_sp = new PdfPTable(6);
			tab_sp.setWidthPercentage(100);
			tab_sp.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_sp.setWidths(crDonHang);
			tab_sp.getDefaultCell().setBorder(0);
			tab_sp.setSpacingAfter(8.0f);
			tab_sp.setSpacingBefore(5.0f);
			
			String[] spTitles = { "STT", "Tên tài sản, ccdc", "Mã số", "Số lượng", "Đơn vị tính", "Tình trạng"};
			PdfPCell[] celltable = new PdfPCell[6];
			for(int i=0; i < 6 ; i++)
			{
				celltable[i] = new PdfPCell(new Paragraph(spTitles[i], font13_bold));
				celltable[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				celltable[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				celltable[i].setPaddingBottom(5.0f);
				//celltable[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
				tab_sp.addCell(celltable[i]);			
			}
			//--------------add san pham-------------//
			List<geso.traphaco.erp.beans.nhanhang.ISanpham> spList = nhBean.getSpList();
			for (int i = 0; i < spList.size(); i++){
				geso.traphaco.erp.beans.nhanhang.ISanpham sp = spList.get(i);
				int stt = i + 1;
				String[] spTitles2 = {""+ stt, sp.getDiengiai(), sp.getMa(),sp.getSoluongDaNhan(),
						sp.getDvdl() , ""} ;
				for(int j = 0; j<6; j++){
					celltable[j] = new PdfPCell(new Paragraph(spTitles2[j], font13_normal));
					if(j == 0 || j == 1){
						celltable[j].setHorizontalAlignment(Element.ALIGN_LEFT);
					}else{
						celltable[j].setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					//cell.setBorderWidthRight(0);
					cell.setBorderWidth(0);
					//cell.setBorderWidthBottom(1);
					//cell.setBorderWidthLeft(1);
					celltable[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
					celltable[j].setPaddingBottom(5.0f);
					//celltable[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
					tab_sp.addCell(celltable[j]);
				}
			}
			document.add(tab_sp);
			
			
			para = new Paragraph("Kèm theo hồ sơ thiết bị (nếu có):" + nhBean.getGhichu(), font13_italic);
			para.setSpacingBefore(5.0f);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			para = new Paragraph("Biên bản này được lập thành 03 bản (Mỗi bên giữ 01bản , kế toán công ty 01 bản ) có trị trị pháp lý như nhau." + "", font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(30.0f);
			//float[] tblWitdh = { 5*CONVERT };
			//table.setWidths(tblWitdh);
			
			cell = new PdfPCell(new Paragraph("BÊN BÀN GIAO", font13_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BÊN NHẬN BÀN GIAO", font13_bold));
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
