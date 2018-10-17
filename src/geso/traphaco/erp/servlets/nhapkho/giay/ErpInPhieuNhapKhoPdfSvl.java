package geso.traphaco.erp.servlets.nhapkho.giay;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.nhanhangnhapkhau.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangnhapkhau.ISanpham;
import geso.traphaco.erp.beans.nhanhangnhapkhau.ISpDetail;
import geso.traphaco.erp.beans.nhanhangnhapkhau.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapkho;
import geso.traphaco.erp.servlets.denghimuahang.ErpDenghimuahangUpdateSvl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.sun.net.httpserver.HttpServer;

public class ErpInPhieuNhapKhoPdfSvl extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private PrintWriter out;

	public ErpInPhieuNhapKhoPdfSvl () 
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);

			//this.out = response.getWriter();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpNhapkho nkBean = new ErpNhapkho(id);
			nkBean.setCongtyId((String) session.getAttribute("congtyId"));
			nkBean.setUserId(userId); // phai co UserId truoc khi Init
			nkBean.init();
			CreatePDF(request, response, nkBean);
			/*String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoGiayDisplay.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoGiayUpdate.jsp";
			}

			session.setAttribute("nkBean", nkBean);
			
			response.sendRedirect(nextJSP);*/
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
	
private void CreatePDF( HttpServletRequest request,HttpServletResponse response, IErpNhapkho nhBean) {
		
	
	//định nghĩa kiểu trả về cho response là pdf
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition"," inline; filename=InPhieuNhapKho.pdf");
	
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
			HttpServletResponse response, HttpServletRequest request, dbutils db, IErpNhapkho nkBean){
		Utility util = new Utility();
		try
		{
			
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			/*Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font9_bold = new Font(bf,9,Font.BOLD);*/
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			font9_italic.setColor(79, 79, 79);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font13_bold = new Font(bf, 12, Font.BOLD);
			Font font13_normal = new Font(bf, 12, Font.NORMAL);
			//Font font13_bold_underline = new Font(bf, 13, Font.UNDERLINE);
			Font font13_bold_italic = new Font(bf, 12, Font.BOLDITALIC);
			Font font13_italic = new Font(bf, 12, Font.ITALIC);
			
			
			//-----TAO HEADER-----//
			
			//--chen hinh logo
			PdfPTable tab_Header = new PdfPTable(1);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell2;
			cell2 = new PdfPCell(new Phrase("Công ty Cổ phần Traphaco", font11_bold));
			
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_Header.addCell(cell2);
			
			cell2 = new PdfPCell(new Phrase("75  Yên  Ninh, Quán Thánh, Ba Đình, Hà Nội", font10));
		
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_Header.addCell(cell2);
			
			document.add(tab_Header);
			
			//----Add tua de-------------------//
			Paragraph tuade = new Paragraph("PHIẾU NHẬP KHO", new Font(bf, 18, Font.BOLD));
			tuade.setSpacingBefore(30);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);
			
			
			//----------add noi dung---------------//
			
			//-------------add ngay va so--------//
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			
			
			table.setSpacingBefore(10.0f);
			float[] tblWitdh = { 800f };
			//table.setWidths(tblWitdh);\
			
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			 
			PdfPCell cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			String ngayNhapKho = nkBean.getNgaynhapkho();
			String[] tach = ngayNhapKho.split("-");
			cell = new PdfPCell(new Paragraph("Ngày "+ tach[2] +" tháng "+ tach [1]+" năm " + tach[0], font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số: 	" + nkBean.getId(), font10));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			table.addCell(cell);
			
			document.add(table);

			//------add thong tin---------------//
			float[] size = {5.0f, 20.0f};
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(size);
			table.setSpacingBefore(10.0f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER); 
			cell = new PdfPCell(new Paragraph("Người  giao  hàng:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Đơn vị:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Địa chỉ:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số hóa đơn:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Nội  dung:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			String query = "select TEN from erp_Noidungnhap where pk_seq = '" + nkBean.getNdnId() + "'";
			String ndn = "";
			ResultSet rsnd = db.get(query);
			try {
				if(rsnd != null){
					if(rsnd.next()){
					
						ndn = rsnd.getString("TEN"); 
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			cell = new PdfPCell(new Paragraph(ndn, font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tài  khoản  có:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			String queryKhoNhap = "select MA + '-' + TEN as 'makho' from ERP_KHOTT where PK_Seq = '" + nkBean.getKhoId() + "'";
			String maKho = "";
			ResultSet rsKho = db.get(queryKhoNhap);
			try {
				if(rsKho.next()){
					maKho = rsKho.getString("makho");
				}
				rsKho.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			cell = new PdfPCell(new Paragraph("Nhập tại kho:", font10_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph(maKho, font10));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table.addCell(cell);
			
			document.add(table);


			float[] crDonHang = { 7.0f * CONVERT, 3.0f * CONVERT,2.0f * CONVERT, 3.0f * CONVERT, 3.0f * CONVERT , 3.0f * CONVERT, 3.0f * CONVERT};

			PdfPTable tab_sp = new PdfPTable(7);
			tab_sp.setWidthPercentage(100);
			tab_sp.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_sp.setWidths(crDonHang);//set kich thuoc cho cac cột
			tab_sp.getDefaultCell().setBorder(0);
			tab_sp.setSpacingAfter(8.0f);
			tab_sp.setSpacingBefore(5.0f);
			
			String[] spTitles = { "Mã kho", "Tên  vật  tư", "Tk", "Đvt", "Số lượng", "Đơn giá", "Thành  tiền"};
			PdfPCell[] celltable = new PdfPCell[7];
			for(int i=0; i < 7 ; i++)
			{
				celltable[i] = new PdfPCell(new Paragraph(spTitles[i], font10_bold));
				celltable[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				celltable[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				celltable[i].setPaddingBottom(5.0f);
				tab_sp.addCell(celltable[i]);			
			}
			//---------add danh sachs san pham--------------------------------//
			
			List<geso.traphaco.erp.beans.nhapkho.giay.ISanpham> spList = nkBean.getSpList();
			
			for(int i=0; i< spList.size(); i++){
				geso.traphaco.erp.beans.nhapkho.giay.ISanpham sp = spList.get(i);
				
				String[] dulieuSp = {maKho,sp.getDiengiai(),"",sp.getDVT(),sp.getSoluongnhapkho(),"",""};
				
				for(int j = 0; j < 7; j++){
					
						cell = new PdfPCell(new Paragraph(dulieuSp[j], font10));//đổ dữ liệu vào cell
					
					if(j == 4){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else{
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(5.0f);
					
					tab_sp.addCell(cell);
				}
			}
			//---------add tong tien, chi phi,  thue gia tri gia tang---------//
			cell = new PdfPCell(new Paragraph("Tổng cộng tiền hàng", font10));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			cell.setBorderWidthBottom(0);
			tab_sp.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("0", font10));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			cell.setBorderWidthBottom(0);
			tab_sp.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Chi phí ", font10));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			cell.setBorderWidthBottom(0);
			cell.setBorderWidthTop(0);
			tab_sp.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("0", font10));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			cell.setBorderWidthBottom(0);
			cell.setBorderWidthTop(0);
			tab_sp.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Thuế giá trị  gia tăng", font10));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
		
			cell.setBorderWidthTop(0);
			tab_sp.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("0", font10));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			cell.setBorderWidthTop(0);
			tab_sp.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Tổng cộng tiền thanh toán", font10));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			tab_sp.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("0", font10));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingBottom(5.0f);
			tab_sp.addCell(cell);
			
			document.add(tab_sp);
			
			//------------------bang chu--------------------//
			Paragraph para = new Paragraph("Bằng chữ:", font13_bold);
			para.setSpacingBefore(2.0f);
			para.setSpacingAfter(1);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para);
			
			table = new PdfPTable(5);
			table.setWidthPercentage(100);
			//table.setWidths(size1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingBefore(5.0f);
			String[] title = {"Phó tổng giám đốc","T/L Kế toán trưởng","Người lập phiếu","Thủ  kho","Phụ trách cung tiêu"};
			for(int i=0 ;i < 5; i++){
				
				cell = new PdfPCell(new Paragraph(title[i], font10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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
