package geso.traphaco.erp.servlets.phieuthanhtoan;

import geso.traphaco.center.util.*;

import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.phieuthanhtoan.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.phieuthanhtoan.IPhieuchitamung;

import java.io.IOException;
import geso.traphaco.erp.beans.doctien.DocTien;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.TabSettings;


public class ErpPhieuThanhToanPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpPhieuThanhToanPdfSvl() {
		super();

	}

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");

    float CONVERT = 28.346457f;  // =1cm
	dbutils db = new dbutils();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	String nextJSP;
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

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String task = request.getParameter("task");

			String nccLoai = util.antiSQLInspection(request.getParameter("nccLoai"));
			if (nccLoai == null)
				nccLoai = "";
			session.setAttribute("nccLoai", nccLoai);
			dmhBean.setNccLOai(nccLoai);
			
			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "2";
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);

			dmhBean.init();
				
			try {
				
				System.out.println("Print");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						" inline; filename=DeNghiThanhToan.pdf");

				float PAGE_LEFT = 2.0f*CONVERT;
				float PAGE_RIGHT = 2.0f*CONVERT;
				float PAGE_TOP = 1.5f*CONVERT ;
				float PAGE_BOTTOM = 2.0f*CONVERT; 
				
				//định dạng khổ giấy
				Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
				ServletOutputStream outstream = response.getOutputStream();
				this.inPDF(document, outstream, dmhBean);
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuThanhToanDisplay.jsp";
				
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("khoanMucChi", dmhBean.getKhoanChiPhiId());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
				
		}
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	

	private void inPDF(Document document, ServletOutputStream outstream, IErpDonmuahang_Giay obj) throws IOException {
		
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);

			Font font16bold = new Font(bf, 16, Font.BOLD);

			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10i = new Font(bf, 10, Font.ITALIC);

			Font font12 = new Font(bf, 12, Font.NORMAL);
		
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_HEADER_WIDTHS = {3.5f * CONVERT, 8.1f * CONVERT, 5.0f * CONVERT };
			float[] TABLE_MIDDLE_WIDTHS = {3.5f * CONVERT, 13.1f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT};			
			
			//Váº¼ khung header (Logo Picture | Header Titles)
			PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			headerTable.setWidths(TABLE_HEADER_WIDTHS);
			headerTable.setWidthPercentage(100);
			
			PdfPCell cell;
			
			//header left
			PdfPTable table = new PdfPTable(2);
			table.setWidths(new float[] {150f, 80f});
			table.setWidthPercentage(100);
			
			String sql=" select pk_seq, ten from ERP_DONVITHUCHIEN " +
			   " where trangthai = '1' and pk_seq = " + obj.getDvthId(); 
			
			ResultSet rsdv = db.get(sql);
			String phongban = "";
			if(rsdv != null){
				try {
					if(rsdv.next()){
						phongban = rsdv.getString("ten");
					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			cell = new PdfPCell(new Paragraph("CÔNG TY CỔ PHẦN TRAPHACO\nBộ phận: " + phongban, font10));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("Mẫu số : 04-TT\nTheo Thông tư 200/2014/TT-BTC", font10));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			document.add(table);

			Paragraph p = new Paragraph("PHIẾU THANH TOÁN TẠM ỨNG",font16bold);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			String[] tachngay = obj.getNgaymuahang().split("-");
			p = new Paragraph("Ngày "+ tachngay[2]+" tháng "+ tachngay[1]+" năm "+ tachngay[0]+"",font12);
			p.setAlignment(Element.ALIGN_CENTER);
			p.setSpacingAfter(20);
			document.add(p);
			
			//số phiếu
			
			table = new PdfPTable(2);
			
			cell = new PdfPCell(new Paragraph(""));
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số phiếu " + obj.getId(), font12));
			cell.setBorder(0);
			table.addCell(cell);
			document.add(table);
			
			//điền thông tin
			String tennhanvien = "";
			if(obj.getNvTen().indexOf(",")>0){
				tennhanvien = obj.getNvTen().split(",")[1];
			}
			else tennhanvien = obj.getNvTen();
			p = new Paragraph("Họ và tên: " + tennhanvien , font12);
			document.add(p);
			
			p = new Paragraph("Địa chỉ: " + phongban , font12);
			document.add(p);
			
			p = new Paragraph("Số tiền tạm ứng được thanh toán theo bảng dưới đây:" , font12);
			p.setSpacingAfter(10);
			document.add(p);
			
			
			//bảng dữ liệu
			table = new PdfPTable(2);
			table.setWidths(new float[] {150f, 80f});
			table.setWidthPercentage(100);
			table.setSpacingBefore(10);
			String[] tieude = {"Diễn giải","Số tiền"};
			for(int i = 0; i< tieude.length; i++){
				cell = new PdfPCell(new Paragraph(tieude[i], font12bold));
				cell.setBorderWidth(1);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingBottom(5);
				cell.setPaddingTop(5);
				table.addCell(cell);

			}
			//1. số dư công nợ tạm ứng
			cell = new PdfPCell(new Paragraph("1. Số dư công nợ tạm ứng ",font12bold));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			//tiền:
			String query = "SELECT ISNULL(SUM(NO) - SUM(CO),0) AS DAUKY FROM ERP_PHATSINHKETOAN " +
					"WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND CONGTY_FK = 100000) " +
					"AND DOITUONG = N'Nhân viên' AND MADOITUONG = '"+obj.getNvId()+"' AND NGAYGHINHAN <= '"+obj.getNgaymuahang()+"'";
			System.out.println("Số dư đầu kỳ : "+ query);
			ResultSet rs = db.get(query);
			double tien = 0;
			if(rs != null){
				try {
					if(rs.next()){
						tien = rs.getDouble("DAUKY");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			cell = new PdfPCell(new Paragraph(formatter.format(tien),font12bold));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//1.1 Số công nợ tạm ứng thanh toán lần này 
			List<IPhieuchitamung> phieuchiList = obj.getPhieuchiTURs();
			
			cell = new PdfPCell(new Paragraph("1.1 Số công nợ tạm ứng thanh toán lần này",font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(this.tongtien(phieuchiList)),font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//chi tiết
			String diengiai = "";
			String sotien = "";
			for(int i = 0 ;i < phieuchiList.size(); i++ ){
				 IPhieuchitamung pcl = phieuchiList.get(i);	
				 if(pcl.getChon().equals("1")){
					 diengiai = diengiai + "\n + Phiếu chi số " + pcl.getSochungtu() + "-" + pcl.getNgaychungtu();
					 sotien = sotien + "\n " + pcl.getSotienAvat();
				 }
			}
			//----ô thông tin ngày - số chứng từ
			cell = new PdfPCell(new Paragraph("Chi tiết Phiếu tạm ứng: " + diengiai,font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			//--- ô tiền
			cell = new PdfPCell(new Paragraph("" + sotien,font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			//2. Số tiền đã chi
			cell = new PdfPCell(new Paragraph("2. Số tiền đã chi:",font12bold));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph( obj.getTongtiensauVat(),font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//3  Chênh lệch: 
			String chenhlech = "";
			String tien31 = "";
			String tien32 = "";
			double tien11 = this.tongtien(phieuchiList);
			double tienchi =Double.parseDouble( obj.getTongtiensauVat().replaceAll(",", ""));
			if(tien11 == tienchi){
				chenhlech = "";
			}else{
				if(tien11 > tienchi){
					double tru = tien11 - tienchi;
					tien31 = formatter.format(tru);
					chenhlech = tien31;
				}else{
					double tru = tienchi - tien11;
					tien32 = formatter.format(tru);
					chenhlech = tien32;
				}
			}
			cell = new PdfPCell(new Paragraph("3. Chênh lệch: ",font12bold));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(chenhlech,font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//3.1	Số tạm ứng chi không hết:
			cell = new PdfPCell(new Paragraph("3.1. Số tạm ứng chi không hết: ",font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(tien31,font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//3.2	Chi quá số tạm ứng:
			cell = new PdfPCell(new Paragraph("3.2. Chi quá số tạm ứng:",font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(tien32,font12));
			cell.setBorderWidth(1);
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			table.setSpacingAfter(20);
			document.add(table);

			//footer//----------------------------------------------------------------//
			table = new PdfPTable(4);
			table.setWidths(new float[] {50f, 40f, 40f, 50f});
			table.setWidthPercentage(100);
			String ten = "";
			for(int i = 0; i< 4; i++){
				if(i ==0){
					ten = "T/L Thủ trưởng đơn vị";
				}else if(i == 1){
					ten = "Kế toán trưởng";
				}else if (i ==2){
					ten = "Kế toán thanh toán";

				}else if( i == 3){
					ten = "Người đề nghị thanh toán";
				}
				cell = new PdfPCell(new Paragraph(ten, font11bold));
				cell.setBorder(0);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			for(int i =0; i<4; i++){
				cell = new PdfPCell(new Paragraph("(Ký, họ tên)", font10i));
				cell.setBorder(0);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			document.add(table);

			document.close();
			
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public double tongtien(List<IPhieuchitamung> phieuchiList){
		double tong =0;
		for(int i= 0; i< phieuchiList.size(); i++){
			 IPhieuchitamung pcl = phieuchiList.get(i);	
			 if(pcl.getChon().equals("1")){
				 tong = tong + Double.parseDouble(pcl.getSotienAvat().replaceAll(",", ""));
			 }
		}
		return tong;
	}
	
	
	private String getDateNow()
	{
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = dateFormat.format(date);
        return d;
	}
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
}
