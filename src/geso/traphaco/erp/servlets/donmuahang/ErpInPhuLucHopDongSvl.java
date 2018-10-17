package geso.traphaco.erp.servlets.donmuahang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.IErpCongTyList;
import geso.traphaco.erp.beans.congty.imp.ErpCongTy;
import geso.traphaco.erp.beans.congty.imp.ErpCongTyList;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.indondathang.IDonDatHang;
import geso.traphaco.erp.beans.indondathang.imp.DonDatHang;
import geso.traphaco.erp.beans.nganhang.IErpNganHang;
import geso.traphaco.erp.beans.nhacungcap.IErpNhaCungCap;
import geso.traphaco.erp.beans.nhacungcap.imp.ErpNhaCungCap;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInPhuLucHopDongSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpInPhuLucHopDongSvl() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		System.out.println("Action = " + action);
		Create_PO_PDF(response, request);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void Create_PO_PDF(HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=Traphaco_InPhuLucHopDong.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 2.0f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT;
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT,
				PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			CreatePO_Training(document, outstream, response, request, db);
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}

	}

	private void CreatePO_Training(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db)
			throws UnsupportedEncodingException {

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
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null)
			loai = "";
		
		
		IDonDatHang dhNK = new DonDatHang(id);
		dhNK.setCongTyId(congTyId);
		dhNK.setUserId(userId);
		dhNK.setLoai(loai);
		dhNK.init();
		
		
		
		//lay sohopdong tu id don hang
		IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
		dmhBean.init();
		String sohopdong=dmhBean.getSohopdong();
		if(sohopdong==null)
			sohopdong="";
		System.out.println("\n so hop dong:" + sohopdong);
		
		
		String ngaymua=dmhBean.getNgaymuahang();
		//tach lay ngay thang nam
		
		String ngay=ngaymua.substring(8);
		String thang=ngaymua.substring(5,7);
		String nam=ngaymua.substring(0,4);
		
		
		//lay thong tin cong ty tai khoan va ngan hang cua don vi dat hang traphaco
		IErpCongTy ctBean;
		ctBean = new ErpCongTy(congTyId);
		ctBean.init();
		String stk_donvidat=ctBean.getSoTaiKhoan();
		String idbank=ctBean.getNganHang_FK();
		String bank_donvidat="";
		String query = "Select PK_SEQ,Ma,Ten From Erp_NganHang Where TrangThai=1 where PK_SEQ="+idbank;
		db = new dbutils();
		ResultSet rsNganHang = db.get(query);
		if (rsNganHang != null)
		{
			try
			{
				while (rsNganHang.next())
				{
					bank_donvidat = rsNganHang.getString("Ten");
				}
				rsNganHang.close();
				db.shutDown();
			}
			catch (SQLException e)
			{
				System.out.println("Loi lay ngan hang ErpNganHang");
			}
		}
		
		
		//String bank_donvidat=ctBean.getNganHang(idbank);
		//don vi dat hang la tra pha co
		//congTyDatHang  nha cung cap
		
		
		String congTyDatHang = dhNK.getCongTyDatHang();
		String donViDatHang = dhNK.getDonViDatHang();
		String diaChi = dhNK.getDiaChi();
		String dienThoai = dhNK.getDienThoai();
		String fax = dhNK.getFax();
		String maSoThue = dhNK.getMaSoThue();
		String hinhThucThanhToan = dhNK.getHinhThucThanhToan();
		String tienTe = dhNK.getTienTe();
		ResultSet thongTinDatHang = dhNK.getThongTinDatHang();
		long tongCong = dhNK.getTongCong();
		System.out.println("Tổng cộng đơn hàng đặt:" + tongCong);
		String tongCongBangChu = dhNK.getTongCongBangChu();
		
		//---------------------thong tin phia nha cung cap cong ty dat hang tu don mua hang 
		String diachi_ncc="";
		String dienthoai_ncc="";
		String taikhoa_ncc="";
		String masothue_ncc="";
		String bankfk_ncc="";
		String bank_ncc="";
	    //lay id nha cung cap tu don mua hang
		String ncc_fk=dmhBean.getNCC();
		//lay thong tin ncc
		IErpNhaCungCap nccBean = new ErpNhaCungCap(ncc_fk);
		nccBean.setCongTy(ncc_fk);
		nccBean.init();
		nccBean.createaRs();
		diachi_ncc=nccBean.getDiaChi_NCC();
		dienthoai_ncc=nccBean.getDienThoai_NCC();
		masothue_ncc=nccBean.getMST();
		taikhoa_ncc=nccBean.getSoTaiKhoan();
		bankfk_ncc=nccBean.getNganHang();
		String qr = "Select PK_SEQ,Ma,Ten From Erp_NganHang Where TrangThai=1 where PK_SEQ="+bankfk_ncc;
		db = new dbutils();
		ResultSet rsBank_ncc = db.get(qr);
		if (rsBank_ncc != null)
		{
			try
			{
				while (rsBank_ncc.next())
				{
					bank_ncc = rsBank_ncc.getString("Ten");
				}
				rsBank_ncc.close();
				db.shutDown();
			}
			catch (SQLException e)
			{
				System.out.println("Loi lay ngan hang ErpNganHang");
			}
		}
		
		
		
		
		// ___________________________________________

		NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter = new DecimalFormat("#,###,###");

		try {
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font9_bold = new Font(bf, 9, Font.BOLD);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);

			Font font12_bold = new Font(bf, 12, Font.BOLD);
			
			Font font14_italic = new Font(bf, 12, Font.ITALIC);
			Font font14_bold = new Font(bf, 12, Font.BOLD);
			Font font14 = new Font(bf, 12, Font.NORMAL);

			PdfPTable tab_Header = new PdfPTable(1);
			tab_Header.setWidthPercentage(100);
			tab_Header.setSpacingAfter(6);
			tab_Header.setHorizontalAlignment(Element.ALIGN_RIGHT);
			float[] crtbl_header = { 7f };
			tab_Header.setWidths(crtbl_header);

			// Header 1
			PdfPCell cell = new PdfPCell(new Paragraph("BM29/08", new Font(bf,10, Font.ITALIC)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph("BH/SĐ: 19/12/14", new Font(bf,
					10, Font.ITALIC)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);
			document.add(tab_Header);
			// Header 2

			PdfPTable table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(new float[] {8.5f,4.0f, 14.0f });
			table.setSpacingBefore(3.0f);
			table.setSpacingAfter(4.0f);
			PdfPCell cell2;
			cell2 = new PdfPCell(new Phrase("", font11_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setColspan(6);
			cell2.setPadding(1.0f);
			cell2.setBorder(0);
			table.addCell(cell2);

			// insert logo traphaco
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint")+ "\\logo.gif");
			img.scalePercent(10);

			cell2 = new PdfPCell();
			cell2.addElement(new Chunk(img, 35, 0));
			cell2.setBorder(0);
			cell2.setBorderColor(BaseColor.WHITE);
			table.addCell(cell2);

			/*
			 * cell2 = new PdfPCell(new Phrase("TRAPHACO", font10_bold));
			 * cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell2.setBorder(0); table.addCell(cell2);
			 */
			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);
			
			

			cell2 = new PdfPCell(new Phrase("CÔNG TY CP TRAPHACO", new Font(bf,10, Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setPadding(1.0f);
			cell2.setBorder(0);
			table.addCell(cell2);
			
			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase("Độc lập - Tự do - Hạnh phúc",font14));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(
					"Số: ... ĐĐH/ TRAPHACO - ..../ 201...", new Font(bf,10, Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);
			
			
			cell2 = new PdfPCell(new Phrase(" ", font12_bold));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			cell2 = new PdfPCell(new Phrase(
					"Hà Nội, Ngày ...  Tháng ...  Năm ...    ", font14_italic));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBorder(0);
			table.addCell(cell2);

			document.add(table);

			// Header 3
			Paragraph donDH = new Paragraph("PHỤ LỤC HỢP ĐỒNG SỐ: "+ sohopdong,new Font(bf, 16, Font.BOLD));
			donDH.setSpacingBefore(10);
			donDH.setSpacingAfter(1);
			donDH.setAlignment(Element.ALIGN_CENTER);
			document.add(donDH);

			Paragraph para_nam = new Paragraph("Năm "+nam, font14_bold);
			para_nam.setSpacingBefore(0);
			para_nam.setSpacingAfter(6);
			para_nam.setAlignment(Element.ALIGN_CENTER);
			document.add(para_nam);

			/* thong tin traphaco */
			Paragraph pr = new Paragraph(
					"Căn cứ theo hợp đồng kinh tế năm "+nam+" số: "+ sohopdong+" ngày "+ngay+" tháng "+ thang +" năm "+nam  +   ", giữa:", font14);
			
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			//Paragraph para = new Paragraph("Bên mua: "+ donViDatHang + " (BÊN A) ", font14_bold);
			Paragraph para = new Paragraph("", font14_bold);
			Chunk chunk=new Chunk("Bên mua:");
			chunk.setUnderline(1f, -2f);
			para.add(chunk);
			chunk=new Chunk( " "+donViDatHang + " (BÊN A) ");
			para.add(chunk);
			para.setSpacingBefore(3);
			para.setSpacingAfter(3);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			//------------ been a 
			/*PdfPTable tbl_bena = new PdfPTable(2);
			float [] tblwithd={2.5f,20.0f};
			tbl_bena.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl_bena.setWidths(tblwithd);
			tbl_bena.setWidthPercentage(100);
			
			Paragraph para_bena = new Paragraph("Bên mua: ",new Font(bf,12,Font.UNDERLINE));
			para_bena.setSpacingBefore(3);
			para_bena.setSpacingAfter(3);
			para_bena.setAlignment(Element.ALIGN_LEFT);
			cell=new PdfPCell();
			cell.setBorder(0);
			cell.addElement(para_bena);
			tbl_bena.addCell(cell);
			
			Paragraph para_bena1 = new Paragraph(donViDatHang +" (BÊN A)",new Font(bf,12,Font.BOLD));
			para_bena1.setSpacingBefore(3);
			para_bena1.setSpacingAfter(3);
			para_bena1.setAlignment(Element.ALIGN_LEFT);
			cell=new PdfPCell();
			cell.setBorder(0);
			cell.addElement(para_bena1);
			tbl_bena.addCell(cell);
			
			document.add(tbl_bena);*/
			//------------------
			
			

			Paragraph para1 = new Paragraph(
					"Đại diện:                                                 Chức vụ:",
					font14);
			para1.setSpacingBefore(3);
			para1.setSpacingAfter(3);
			para1.setAlignment(Element.ALIGN_LEFT);
			document.add(para1);

			Paragraph para2 = new Paragraph("Địa chỉ: "+diaChi, font14);
			para2.setSpacingBefore(3);
			para2.setSpacingAfter(3);
			para2.setAlignment(Element.ALIGN_LEFT);
			document.add(para2);

			Paragraph para3 = new Paragraph("Điện thoại: "+dienThoai, font14);
			para3.setSpacingBefore(3);
			para3.setSpacingAfter(3);
			para3.setAlignment(Element.ALIGN_LEFT);
			document.add(para3);

			Paragraph para4 = new Paragraph(
					"Tài khoản: "+stk_donvidat +" "+ bank_donvidat,font14);
			para4.setSpacingBefore(3);
			para4.setSpacingAfter(3);
			para4.setAlignment(Element.ALIGN_LEFT);
			document.add(para4);

			Paragraph para5 = new Paragraph("Mã số thuế: "+ maSoThue, font14);
			para5.setSpacingBefore(3);
			para5.setSpacingAfter(3);
			para5.setAlignment(Element.ALIGN_LEFT);
			document.add(para5);

			/* thong tin nha cung cap */

			//Paragraph para6 = new Paragraph("Bên bán:  " + congTyDatHang+ " (BÊN B)", font14_bold);
			Paragraph para6 = new Paragraph("", font14_bold);
			chunk=new Chunk("Bên bán:");
			chunk.setUnderline(1f, -2f);
			para6.add(chunk);
			chunk=new Chunk( " "+congTyDatHang + " (BÊN B) ");
			para6.add(chunk);
			para6.setSpacingBefore(3);
			para6.setSpacingAfter(3);
			para6.setAlignment(Element.ALIGN_LEFT);
			document.add(para6);
			
			//------------ been a 
			/*PdfPTable tbl_benb = new PdfPTable(2);
			
			tbl_benb.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl_benb.setWidths(tblwithd);
			tbl_benb.setWidthPercentage(100);
			
			Paragraph para_benb = new Paragraph("Bên bán: ",new Font(bf,12,Font.UNDERLINE));
			para_benb.setSpacingBefore(3);
			para_benb.setSpacingAfter(3);
			para_benb.setAlignment(Element.ALIGN_LEFT);
			cell=new PdfPCell();
			cell.setBorder(0);
			cell.addElement(para_benb);
			tbl_benb.addCell(cell);
			
			Paragraph para_benb1 = new Paragraph(congTyDatHang +" (BÊN B)",new Font(bf,12,Font.BOLD));
			para_benb1.setSpacingBefore(3);
			para_benb1.setSpacingAfter(3);
			para_benb1.setAlignment(Element.ALIGN_LEFT);
			cell=new PdfPCell();
			cell.setBorder(0);
			cell.addElement(para_benb1);
			tbl_benb.addCell(cell);
			
			document.add(tbl_benb);*/
			//------------------
			
			

			Paragraph para7 = new Paragraph("Đại diện: ", font14);
			para7.setSpacingBefore(3);
			para7.setSpacingAfter(3);
			para7.setAlignment(Element.ALIGN_LEFT);
			document.add(para7);

			Paragraph para8 = new Paragraph("Địa chỉ: "+ diachi_ncc , font14);
			para8.setSpacingBefore(3);
			para8.setSpacingAfter(3);
			para8.setAlignment(Element.ALIGN_LEFT);
			document.add(para8);

			Paragraph para9 = new Paragraph("Điện thoại: "+dienthoai_ncc ,font14);
			para9.setSpacingBefore(3);
			para9.setSpacingAfter(3);
			para9.setAlignment(Element.ALIGN_LEFT);
			document.add(para9);

			Paragraph para10 = new Paragraph("Tài khoản: "+taikhoa_ncc +" "+bank_ncc, font14);
			para10.setSpacingBefore(3);
			para10.setSpacingAfter(3);
			para10.setAlignment(Element.ALIGN_LEFT);
			document.add(para10);

			Paragraph para11 = new Paragraph("Mã số thuế: "+masothue_ncc ,font14);
			para11.setSpacingBefore(3);
			para11.setSpacingAfter(3);
			para11.setAlignment(Element.ALIGN_LEFT);
			document.add(para11);

			Paragraph para12 = new Paragraph(
					"Hai bên thỏa thuận ký kết phụ lục hợp đồng kinh tế với nội dung sau: Bên B bán cho bên A:",font14);
			para12.setSpacingBefore(3);
			para12.setSpacingAfter(12);
			para12.setAlignment(Element.ALIGN_LEFT);
			document.add(para12);

			float[] crDonHang = { 0.5f * CONVERT,1.0f * CONVERT, 2.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 0.7f * CONVERT, 1.0f * CONVERT,
					1.5f * CONVERT, 1.3f * CONVERT };

			PdfPTable tab_DonHang = new PdfPTable(crDonHang.length);
			tab_DonHang.setWidthPercentage(100);
			tab_DonHang.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_DonHang.setWidths(crDonHang);
			tab_DonHang.getDefaultCell().setBorder(0);
			tab_DonHang.setSpacingAfter(8.0f);

			String[] spTitles = { "TT","Mã VT","Tên hàng, qui cách", "TCKT", "Đ.vị",
					"S.lượng", "Đ.giá (" + tienTe + ")", "VAT",
					"Thành tiền (" + tienTe + ")", "Thời gian giao hàng",
					"Địa điểm nhận hàng" };

			for (int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11_bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab_DonHang.addCell(cell);
			}

			int sott = 1;
			if (thongTinDatHang != null) {
				while (thongTinDatHang.next()) {
					String[] spTitles2 = {
							sott + "",thongTinDatHang.getString("SPID"),
							thongTinDatHang.getString("SPTEN") + " "
									+ thongTinDatHang.getString("QUYCACH"),
							thongTinDatHang.getString("TCKT"),
							thongTinDatHang.getString("DONVI"),
							thongTinDatHang.getString("SOLUONG"),
							String.valueOf(formatter.format(thongTinDatHang
									.getFloat("DONGIA"))),
							thongTinDatHang.getString("VAT"),
							String.valueOf(formatter.format(thongTinDatHang
									.getFloat("THANHTIEN"))),
							thongTinDatHang.getString("THOIGIANGIAOHANG"),
							thongTinDatHang.getString("DIADIEMGIAOHANG") };

					for (int z = 0; z < spTitles2.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles2[z], font10));
						cell.setPadding(3.0f);
						if (z == 4 || z == 5 || z == 7) {
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						tab_DonHang.addCell(cell);
					}
					sott++;
				}
			}
			for (int z = 0; z < spTitles.length; z++) {
				if (z == 2) {
					cell = new PdfPCell(new Paragraph("Tổng cộng", font10));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				} else if (z == 8) {
					cell = new PdfPCell(new Paragraph(
							formatter.format(tongCong), font10));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				} else {
					cell = new PdfPCell(new Paragraph("", font10));
					cell.setPadding(3.0f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_DonHang.addCell(cell);
				}

			}
			// Chỗ này không hiển thị
			cell = new PdfPCell(new Paragraph("Tổng cộng", font11_bold));
			cell.setColspan(3);
			cell.setPadding(3.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_DonHang.addCell(cell);
			// tinh tong gia tri cho cac cot trong table
			cell = new PdfPCell(new Paragraph(formatter.format(tongCong),
					font10_bold));
			cell.setPadding(3.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_DonHang.addCell(cell);
			// .....
			document.add(tab_DonHang);

			// tong tien bang chu
			Paragraph para17 = new Paragraph("Tổng số tiền viết bằng chữ: "
					+ tongCongBangChu, new Font(bf, 12, Font.BOLD));
			para17.setSpacingBefore(3);
			para17.setSpacingAfter(5);
			para17.setAlignment(Element.ALIGN_LEFT);
			document.add(para17);

			/*
			 * PdfPTable table3 = new PdfPTable(2);
			 * table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * table3.setWidths(new int[]{ 6, 6}); table3.setSpacingAfter(5.0f);
			 * PdfPCell cell4; cell4 = new PdfPCell(new Phrase("",
			 * font11_bold));
			 * cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell4.setColspan(8); cell4.setPadding(1.0f); cell4.setBorder(0);
			 * table3.addCell(cell4);
			 * 
			 * cell4 = new PdfPCell(new Phrase("Tổng tiền viết bằng chữ: ",
			 * font11_bold)); cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * cell4.setBorder(0); table3.addCell(cell4);
			 * 
			 * cell4 = new PdfPCell(new Phrase("Đọc theo nguyên tệ gốc", new
			 * Font(bf, 11, Font.NORMAL)));
			 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * cell4.setBorder(0); table3.addCell(cell4); document.add(table3);
			 */
			// ___________________;

			/*if (hinhThucThanhToan == "" ||hinhThucThanhToan == null)
				hinhThucThanhToan = "                                                  ";
			Paragraph para16 = new Paragraph("Hình thức thanh toán: "
					+ hinhThucThanhToan + "                  Theo hợp đồng kinh tế… ", font14_bold);
			para16.setSpacingBefore(3);
			para16.setSpacingAfter(10);
			para16.setAlignment(Element.ALIGN_LEFT);
			document.add(para16);*/
			
			///hinh thuc thanh toan
			
			PdfPTable table3 = new PdfPTable(3);
			table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.setWidths(new float[]{4.89f,7.0f, 10.0f});
			table3.setWidthPercentage(100);
			table3.getDefaultCell().setBorder(0);
			table3.setSpacingAfter(3.0f);
			
			cell = new PdfPCell(new Paragraph("Hình thức thanh toán: ",font14_bold));
			cell.setPadding(0.0f);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(hinhThucThanhToan,font14_italic));
			cell.setPadding(0.0f);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table3.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Theo hợp đồng kinh tế… ",font14_italic));
			cell.setPadding(0.0f);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table3.addCell(cell);
			
			document.add(table3);
			
			
			
			
			//----------------------
			Paragraph pxk = new Paragraph(
					"               ĐẠI DIỆN BÊN B                         "
							+ "                                    "
							+ "ĐẠI DIỆN BÊN A", font12_bold);
			pxk.setSpacingAfter(15);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
	}

}
