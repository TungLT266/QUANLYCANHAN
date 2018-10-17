package geso.traphaco.erp.servlets.phieuthanhtoan;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.phieuthanhtoan.imp.ErpDonmuahang_Giay;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class ErpInPhieuKeToanDNTTPdfSvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public ErpInPhieuKeToanDNTTPdfSvl() {
		super();

	}

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");

    float CONVERT = 28.346457f;  // =1cm
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
    		String nextJSP;
    		HttpSession session = request.getSession();
    		String userId = (String) session.getAttribute("userId");
    		String userTen = (String) session.getAttribute("userTen");
    		String sum = (String) session.getAttribute("sum");
    		String querystring = request.getQueryString();
    		Utility util = new Utility();
    		String id = util.getId(querystring);
    		System.out.println("Id để innnnnnnnnnnn: " + id);
    		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
    				.getAttribute("util");
    		if (!cutil.check(userId, userTen, sum)) {
    			response.sendRedirect("/TraphacoHYERP/index.jsp");
    		} else {
    			session.setMaxInactiveInterval(30000);

    		
    			
    			userId = util.getUserId(querystring);

    			if (userId.length() == 0)
    				userId = util.antiSQLInspection(request.getParameter("userId"));

    			
    			IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
    			
    			dmhBean.setUserId(userId);
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
    				
    					this.inPDF1(document, outstream, dmhBean, userId);
    					document.close();
    				

    			} catch (Exception e) {
    				e.printStackTrace();
    				session.setAttribute("tuBean", dmhBean);
    				nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuThanhToanDisplay.jsp";
    				dmhBean.setMsg("Khong the tao bao cao..." + e.getMessage());
    				response.sendRedirect(nextJSP);
    			}
    		}

		
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	//cach 1
	private void inPDF1(Document document, ServletOutputStream outstream, IErpDonmuahang_Giay obj, String userID) throws IOException {
		
		try
		{
			dbutils db = new dbutils();
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Font font12_n = new Font(bf, 12, Font.NORMAL);
			Font font12_b = new Font(bf, 12, Font.BOLD);
			Font font12_i = new Font(bf, 12, Font.ITALIC);
			Font font11_n = new Font(bf, 11, Font.NORMAL);
			Font font11_b = new Font(bf, 11, Font.BOLD);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			
			PdfPCell cell;
			//==========================header===================================//
			
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = { 33f, 34f, 33};
			Paragraph hd = new Paragraph();
			Chunk chunk = new Chunk();

			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint") + "\\logo.gif");
			img.scalePercent(10);

			hd.add(new Chunk(img, 30, 0));
			hd.setAlignment(Element.ALIGN_LEFT);
			
			chunk = new Chunk("\nCông ty cổ phần Traphaco", font12_n);
			hd.add(chunk);
			
			//địa chỉ
			String query = "select DIACHI from ERP_CONGTY where PK_SEQ = '100000'";
			ResultSet rsdc = db.get(query);
			String diachi = "";
			if(rsdc!= null){
				try {
					if(rsdc.next()){
						diachi = rsdc.getString("DIACHI");
					}
					rsdc.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			chunk = new Chunk("\nĐịa chỉ: " + diachi, font12_n);
			hd.add(chunk);
			document.add(hd);

			//====================================================================//
			//========================BODY========================================//
			//============tua de ================================================//
			
			hd = new Paragraph("PHIẾU KẾ TOÁN", new Font(bf, 18, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			//hd.setSpacingAfter(20);
			hd.setSpacingBefore(20);
			document.add(hd);
			
			String tachNgay[] = obj.getNgaymuahang().split("-");
			hd = new Paragraph("Ngày "+ tachNgay[2]+" tháng "+ tachNgay[1] + " năm " + tachNgay[0], font12_n);
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			hd = new Paragraph("Số phiếu: " + obj.getId() +"                         ", font12_n);
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingBefore(20);
			document.add(hd);

			//======================================cái bảng=========================//
			table1 = new PdfPTable(6);
			table1.setSpacingBefore(20);
			table1.setWidthPercentage(100);
			float[] with = {1.5f*CONVERT, 4.5f*CONVERT, 4.5f*CONVERT, 1.5f*CONVERT,2.5f*CONVERT, 2.5f*CONVERT };
			table1.setWidths(with);
			String[] tieude = {"Mã ĐT","Nội dung","Tài khoản","Mã chi phí", "PS Nợ", "PS có"};
		
			for(int i =0; i< tieude.length; i++){
				cell = new PdfPCell(new Paragraph(tieude[i], font12_b));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				
				cell.setPaddingBottom(7);
				//cell.setBorder(1);
				table1.addCell(cell);
			}
			//dien du lieu
			

			String tkco = "";
			String tkno = "";
			String doituongno ="";
			String doituongco = "";
			
			//=====================//
		
			int stt = 0;
			double tongco = 0;
			double tongno = 0;
			
			String chuoi = "select " +
					"case when b.pk_seq is null then  '' else b.ma end as doituong, " +
					"case when a.DIENGIAI is null then '' " +
					"else a.DIENGIAI end as diengiai, " +
					"case when c.pk_seq is null then '' else c.sohieutaikhoan + N' - ' + c.TENTAIKHOAN end as taikhoan, " +
					"case when d.PK_SEQ is null then '' else d.TEN end as maphi, " +
					"case when NO is null then 0 else no end as no, " +
					"case when CO is null then 0 else CO end as co " +
					"From ERP_PHATSINHKETOAN a " +
					"left join dmdoituong b on  a.MADOITUONG = CAST(b.pk_seq AS NVARCHAR(100)) and a.DOITUONG = b.loai " +
					"left join ERP_TAIKHOANKT c on a.TAIKHOAN_FK = c.PK_SEQ " +
					"left join ERP_NHOMCHIPHI d on a.KHOANMUCCHIPHI_FK = d.PK_SEQ " +
					"where a.SOCHUNGTU = '"+obj.getId()+"' and a.LOAICHUNGTU = N'Duyệt đề nghị thanh toán' " +
					"order by a.PK_SEQ desc";
			System.out.println("Loc lay du lieu: " + chuoi);
			ResultSet rs = db.get(chuoi);
			if(rs!= null){
				try {
					while(rs.next()){
						String[] tieudeNo = {rs.getString("doituong"),rs.getString("diengiai"), rs.getString("taikhoan"),rs.getString("maphi"), formatter.format(Double.parseDouble(rs.getString("no").replaceAll(",", ""))), formatter.format(Double.parseDouble(rs.getString("co").replaceAll(",", ""))) };
						
						tongco = tongco + Double.parseDouble(rs.getString("co").replaceAll(",", ""));
						tongno = tongno + Double.parseDouble(rs.getString("no").replaceAll(",", ""));
						for(int j = 0; j< tieudeNo.length; j++){
							cell = new PdfPCell(new Paragraph(tieudeNo[j], font11_n));
							if( j == 1 || j == 2){
								cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
							}
							else if( j == 4 || j == 5){
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							else{
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setPaddingBottom(5);
							table1.addCell(cell);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			//=============================================================//
			
			//============================================================//
			//tổng cộng bên dưới.
			cell = new PdfPCell( new Paragraph("CỘNG", font12_b));
			cell.setColspan(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(7);
			cell.setPaddingTop(5);
			table1.addCell(cell);
			
			cell = new PdfPCell( new Paragraph(formatter.format(tongno), font11_b));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingBottom(7);
			cell.setPaddingTop(5);
			table1.addCell(cell);
			
			cell = new PdfPCell( new Paragraph(formatter.format(tongco), font11_b));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingBottom(7);
			cell.setPaddingTop(5);
			table1.addCell(cell);
			
			document.add(table1);
			
			//=================================phần tiền dưới====================
			DocTien doctien = new DocTien();
			String tien = doctien.docTien((long) tongno);
			hd = new Paragraph("       Bằng chữ: " + tien, font12_i);
			hd.setSpacingBefore(10);
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);
			
			//===============================FOOTER==============================//

			hd = new Paragraph("Ngày "+  getDateNow() +"                ", font12_i);
			hd.setSpacingBefore(50);
			hd.setAlignment(Element.ALIGN_RIGHT);
			document.add(hd);
			
			table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			
			
			cell = new PdfPCell();
			hd = new Paragraph("Kế toán trưởng", font12_b);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Người lập biểu", font12_b);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			for(int i=0; i< 2; i++){
				cell = new PdfPCell();
				hd = new Paragraph("(Ký, họ tên)", font12_n);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);
				table1.addCell(cell);
			}
			document.add(table1);

			
			document.close();
			db.shutDown();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
			
		}
	}
	//lấy mã vụ việc
	
//cachs 2
	
	//lấy mã vụ việc
	public String maVuViec(ResultSet rsVuViec, String id){
		String maVuViec = "";
		try {
			if(rsVuViec != null){
				while(rsVuViec.next()){
					if(rsVuViec.getString("pk_seq").equals(id)){
						maVuViec = rsVuViec.getString("ten");
					}
				}
				rsVuViec.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maVuViec;
	}
	//lấy đối tượng
	public String layDoiTuong(String DoiTuong){
		String madoituong = "";
		
		String[] tach =  DoiTuong.split(",");
		if(tach.length == 2){
			madoituong = tach[1];
			
		}
		return madoituong;
	}
	
	//lấy tài khoản
	public String layTaiKhoan(String mataiKhoan, ResultSet rstaiKhoan){
		String matk = "";
		try {
			if(rstaiKhoan != null){
				while (rstaiKhoan.next()){
					if(rstaiKhoan.getString("pk_seq").equals(mataiKhoan)){
						matk =rstaiKhoan.getString("ten");
					}
					
				}
				rstaiKhoan.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return matk;
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
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
}
