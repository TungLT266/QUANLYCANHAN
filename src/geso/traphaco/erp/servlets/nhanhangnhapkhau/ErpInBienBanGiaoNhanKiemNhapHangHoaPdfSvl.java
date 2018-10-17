
package geso.traphaco.erp.servlets.nhanhangnhapkhau;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;


import geso.traphaco.erp.servlets.denghimuahang.ErpDenghimuahangUpdateSvl;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import geso.traphaco.erp.beans.nhanhangnhapkhau.*;
import geso.traphaco.erp.beans.nhanhangnhapkhau.imp.ErpNhanhang_Giay;

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

public class ErpInBienBanGiaoNhanKiemNhapHangHoaPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpInBienBanGiaoNhanKiemNhapHangHoaPdfSvl () 
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		//--------------------------------//
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			
			//this.out = response.getWriter();
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			//out.println(userId);	
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String loaimh = util.antiSQLInspection(request.getParameter("loai"));
			if(loaimh == null) loaimh = "1";
			nhBean.setLoaimh(loaimh);
			System.out.println("Loai: " + loaimh);
			nhBean.init();
			
		//---neu đã chốt hoặc hoàn tất thì in-----//
			if(nhBean.getTrangthai().equals("1") || nhBean.equals("2")){
				CreatePDF(request, response, nhBean);
			}
			else{
				String nextJSP;
				
			
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";	
				nhBean.setMsg("Phiếu nhận hàng chưa được chốt");
				
				session.setAttribute("nhBean", nhBean);			
				response.sendRedirect(nextJSP);
			}
			}
			
	
		
		//-------------------------------//

		

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
	
	private void FillData(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db, IErpNhanhang_Giay nhBean){
		Utility util = new Utility();
		try
		{
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font9_italic = new Font(bf,10,Font.ITALIC);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			font9_italic.setColor(79, 79, 79);
			/*Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_normal = new Font(bf, 12, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);*/
			Font font13_bold = new Font(bf, 12, Font.BOLD);
			Font font13_normal = new Font(bf, 11, Font.NORMAL);
			//Font font13_bold_underline = new Font(bf, 13, Font.UNDERLINE);
			Font font13_bold_italic = new Font(bf, 12, Font.BOLDITALIC);
			Font font13_italic = new Font(bf, 12, Font.ITALIC);
			
			//----Add tua de-------------------//
			Paragraph tuade = new Paragraph("                                                ", new Font(bf, 14, Font.BOLD));
			//tuade.setSpacingBefore();
			//donDH.setSpacingAfter(1);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);
			
			tuade = new Paragraph("BIÊN BẢN GIAO NHẬN VÀ KIỂM NHẬP HÀNG HÓA", new Font(bf, 14, Font.BOLD));
			tuade.setSpacingBefore(30);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);

			//----------add noi dung---------------//
			Paragraph text = new Paragraph("(Chỉ lập biên bản với vật tư đã có Hợp đồng, Đơn đặt hàng)", font13_italic);
			text.setSpacingBefore(10);
			text.setSpacingAfter(1);
			text.setAlignment(Element.ALIGN_CENTER);
			document.add(text);

			String soPO = nhBean.getSopo_Id();
			String[] tach = nhBean.getNgaynhanhang().split("-");
			String thang = tach[1];
			String nam = tach[0];
			String ngay = tach[2];
			
			
			Paragraph para = new Paragraph("Căn cứ vào HĐ số: "+ soPO +" ngày " + ngay + " tháng "+ thang +" năm " + nam + " của công ty", font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			
			para = new Paragraph("I/ THÀNH PHẦN GIAO NHẬN VÀ KIỂM NHẬP" + "", font13_bold);
			
			para.setSpacingBefore(10);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			
			
			table.setSpacingBefore(10.0f);
			float[] tblWitdh = { 800f };
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			
					 
			PdfPCell cell = new PdfPCell(new Paragraph("Cán bộ ĐBCL: ..............................................................", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Thủ kho:..........................................................................", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Cán bộ XNK:.................................................................", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Người giao hàng:............................................................", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			//para.add(table)table;
			document.add(table);

			
			para = new Paragraph("II/ NỘI DUNG GIAO NHẬN VÀ KIỂM NHẬP HÀNG " + "", font13_bold);
			para.setSpacingBefore(10);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			String khoChoXl = nhBean.getKhoChoXuLy();
			String query = "select PK_SEQ, MA, TEN as TEN from ERP_BIN Where KHOTT_FK = "+ khoChoXl;
			db = new dbutils();
			ResultSet rs = db.get(query);
			String tenKho = "";
			try {
				while(rs.next()){
					tenKho = rs.getString("TEN");
				}
				rs.close();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
			
			para = new Paragraph("Nhập tại kho: " + tenKho, font13_normal);
			para.setSpacingBefore(10);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setSpacingBefore(7.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell = new PdfPCell(new Paragraph("Nhà sản xuất: ................................................................", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Địa chỉ: ..........................................................................", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			//-------------nha cung cap-------------//
			String tenNCC = ".....................................................";
			String diaChi = "..........................................................................";

			ResultSet rs1 = nhBean.getNccList();

			try {

				while(rs1.next()){
					if(rs1.getString("pk_seq").equals(nhBean.getNccId())){
					
						tenNCC = rs1.getString("ten");
						diaChi = rs1.getString("DIACHI");
					}
				}
				rs1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			cell = new PdfPCell(new Paragraph("Nhà phân phối: " + tenNCC, font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Địa chỉ: " + diaChi, font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			document.add(table);
			
			//KHAI BÁO KÍCH THƯỚC CHO BẢNG SẢN PHẨM
			float[] crDonHang = { 3.5f * CONVERT, 3.0f * CONVERT, 2.0f * CONVERT, 3.0f * CONVERT, 3.0f * CONVERT,
					1.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT };

			PdfPTable tab_sp = new PdfPTable(8);
			tab_sp.setWidthPercentage(100);
			tab_sp.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_sp.setWidths(crDonHang);
			tab_sp.getDefaultCell().setBorder(0);
			tab_sp.setSpacingAfter(8.0f);
			tab_sp.setSpacingBefore(5.0f);
			
			String[] spTitles = { "Tên hàng hóa - Nồng độ, HL", "Quy cách  đóng gói", "Tổng số", "Ngày SX/ Số lô", "Hạn dùng/ Ngày KT lại", "Đơn vị","Chứng từ","Thực nhập"};
			PdfPCell[] celltable = new PdfPCell[8];
			for(int i=0; i < 5 ; i++)
			{
				celltable[i] = new PdfPCell(new Paragraph(spTitles[i], font13_bold));
				celltable[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				celltable[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				celltable[i].setPaddingBottom(5.0f);
				celltable[i].setRowspan(2);
				
				tab_sp.addCell(celltable[i]);			
			}
			cell = new PdfPCell(new Paragraph("Số lượng", font13_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			cell.setColspan(3);
			tab_sp.addCell(cell);
			
			for (int i=5; i<8; i++){
				cell = new PdfPCell(new Paragraph(spTitles[i], font13_italic));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingBottom(5.0f);
				tab_sp.addCell(cell);
			}
			//--------------add san pham-------------//
			List<ISanpham> spList = nhBean.getSpList();
			for (int i = 0; i < spList.size(); i++){
				ISanpham sp = spList.get(i);
				List<ISpDetail> spDetailList = sp.getSpDetail();
				String ngaySX = "";
				String ngayHetHan = "";
				String tongso = "";
				//---------------------------------//
				if(spDetailList.size() > 0)
            	{
            		for(int sd = 0; sd < spDetailList.size(); sd++)
            		{
            			ISpDetail spDetail = spDetailList.get(sd);
            		
					if(nhBean.getLoaimh().trim().equals("1") || nhBean.getLoaimh().trim().equals("0")){ 
						ngaySX = spDetail.getNgaySx() ;
					    ngayHetHan = spDetail.getNgayHethan();
					    tongso = spDetail.getSoluong();
					    
					}
            		}
            	} 
				//------------------add 3 dòng cuối của bảng------------------//
				String[] spTitles2 = {sp.getDiengiai(), "", "", ngaySX, ngayHetHan, 
						sp.getDvdl() , sp.getSoluongdat(), sp.getSoluongDaNhan()} ;
				for(int j = 0; j<8; j++){
					celltable[j] = new PdfPCell(new Paragraph(spTitles2[j], font10));
					if(j == 6 || j == 7 || j == 2){
						celltable[j].setHorizontalAlignment(Element.ALIGN_RIGHT);
					}else{
						celltable[j].setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					cell.setBorderWidth(0);
					celltable[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
					celltable[j].setPaddingBottom(5.0f);
					tab_sp.addCell(celltable[j]);
				}
			}
			document.add(tab_sp);
			
			//------------------cac tai lieu khac--------------------//
			para = new Paragraph("Các tài liệu khác: ", font13_bold);
			para.setSpacingBefore(5.0f);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
		/*	String[] check = new String[3];*/
			float[] size1 = { 17.0f * CONVERT, 1.0f* CONVERT, 12.0f * CONVERT };
			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(size1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(5.0f);
			
			for(int i=0 ;i < 3; i++){
				if(i==0){
					cell = new PdfPCell(new Paragraph("1. Tờ khai hải quan", font13_normal));
				}
				if(i==1){
					cell = new PdfPCell(new Paragraph("2. Cetificate of origin (CO - giấy chứng nhận nguồn gốc)", font13_normal));
				}
				if(i==2){
					cell = new PdfPCell(new Paragraph("3. Certificate of Analysis (COA - PKN gốc của NSX)", font13_normal));
				}
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				//-----------lam o vuong --------//
				PdfPTable tab_sp1 = new PdfPTable(1);
				tab_sp1.setWidthPercentage(100);
				tab_sp1.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] s = {1.0f};
				tab_sp1.setWidths(s);
				tab_sp1.getDefaultCell().setBorder(2);
				PdfPCell cell1 = new PdfPCell(new Paragraph(" ", font13_bold));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(2);
				cell1.setBorderWidth(2);
				cell1.setBorderWidthBottom(2);
				cell1.setBorderWidthLeft(2);
				cell1.setBorderWidthRight(2);
				cell1.setBorderWidthTop(2);
				tab_sp1.addCell(cell1);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.addElement(tab_sp1);
				table.addCell(cell);
				if(i == 0 || i == 1){
					if(i==0){
						String query1 = "select th.SOCHUNGTU as 'SOTOKHAI', th.NGAYCHUNGTU as 'ngay' " +
								"from ERP_THUENHAPKHAU_HOADONNCC thhd " +
								"left join ERP_THUENHAPKHAU th on th.PK_SEQ = thhd.THUENHAPKHAU_FK " +
								"left join ERP_HOADONNCC hdncc on hdncc.pk_seq = thhd.HOADONNCC_FK " +
								"Where hdncc.pk_seq = '" + nhBean.getHdNccId() +"'";
						System.out.println("queryToKhai:" + query1);
						db = new dbutils();
						String sotokhai = "";
						String ngaySoToKhai = "";
						ResultSet rsToKhai = db.get(query1);
						try {
							if(rsToKhai.next()){
								sotokhai = rsToKhai.getString("SOTOKHAI");
								ngaySoToKhai = rsToKhai.getString("ngay");
							}
							rsToKhai.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						String[] tachngay = ngaySoToKhai.split("-");
						if(ngaySoToKhai.equals("")){
							cell = new PdfPCell(new Paragraph(" Số....................ngày......tháng......năm.........", font13_normal));
						}
						else{
								cell = new PdfPCell(new Paragraph(" Số " + sotokhai + " ngày "+ tachngay[2]+" tháng " + tachngay[1]+ " năm " + tachngay[0], font13_normal));
						}
					}
					else{
						cell = new PdfPCell(new Paragraph(" Số....................ngày......tháng......năm.........", font13_normal));
					}
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					table.addCell(cell);
				}
				else{
					cell = new PdfPCell(new Paragraph("  ", font13_normal));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorder(0);
					table.addCell(cell);
				}
				
				
			}
			document.add(table);
			
			para = new Paragraph("Điều kiện bảo quản hàng:..........................................................." + "", font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			float[] size = { 15.0f * CONVERT, 8.0f * CONVERT, 4.0f * CONVERT };

			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(size);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(1.0f);
			
			cell = new PdfPCell(new Paragraph("Điều kiện bảo quản trong thùng/ xe khi giao nhận: ", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Nhiệt độ:...............", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(" Độ ẩm:..........", font13_normal));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			table.addCell(cell);
			
			document.add(table);
			
			para = new Paragraph("III/ NHẬN XÉT CẢM QUAN CỦA CÁN BỘ ĐẢM BẢO CHẤT LƯỢNG" + "", font13_bold);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			para = new Paragraph("..........................................................................." +
					"............................................................................" +
					"............................................................................" +
					"..........................................................................................." +
					"..........................................................................................." +
					"......................................................................................" +
					"............................................." + "", font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			para = new Paragraph("IV/ YÊU CẦU XỬ LÝ" + "", font13_bold);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			para = new Paragraph("..........................................................................." +
					"............................................................................" +
					"............................................................................" +
					"..........................................................................................." +
					"..........................................................................................." +
					"......................................................................................" +
					"............................................." + "", font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			
			para = new Paragraph(" Ngày " + getDateNow(), font13_normal);
			para.setSpacingBefore(1);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_RIGHT);
			document.add(para);
			
			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(30.0f);
			String[] title = {"Cán bộ ĐBCL", "Cán bộ XNK-CƯVT", "Thủ kho", "Người giao hàng"};
			
			for(int i = 0 ; i< 4; i++){
				cell = new PdfPCell(new Paragraph( title[i], font13_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
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
