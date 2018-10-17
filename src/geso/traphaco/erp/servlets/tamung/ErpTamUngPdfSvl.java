package geso.traphaco.erp.servlets.tamung;

import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tamung.IErpTamUng;
import geso.traphaco.erp.beans.tamung.imp.ErpTamUng;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

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
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class ErpTamUngPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpTamUngPdfSvl() {
		super();

	}

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");
	
	float CONVERT = 28.346457f;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			IErpTamUng obj;
			Utility util = new Utility();
			String querystring = request.getQueryString();
			String ddhId = util.getId(querystring);
			String doituongId = util.getLoai(querystring);
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			obj = new ErpTamUng(ddhId);
			obj.setUserId(userId);
			String ctyId = (String)session.getAttribute("congtyId");
			obj.setCongtyId(ctyId);
			String nextJSP = "";
			try {
				
				System.out.println("Print");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=DeNghiTamUng.pdf");
				
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				if(doituongId.equals("1")){
					obj.InitPDFNhanVien();
					this.CreateDNTUPdfNhanVien(document, outstream,obj, ddhId);
				}else if(doituongId.equals("2")){
					obj.InitPDFNhaCungCap();
					this.CreateDNTUPdfNhaCungCap(document, outstream, obj, ddhId, userTen);
				}
				
				document.close();

			} catch (Exception e) {
				session.setAttribute("tuBean", obj);
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTamUngUpdate.jsp";
				obj.setMsg("Khong the tao bao cao..." + e.getMessage());
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
//	private void CreatePXCPdf_NCC(Document document, ServletOutputStream outstream, IErpTamUng pxkBean, String Id) throws IOException
//	{
//		try{
//			dbutils db = new dbutils();
//			
//			//LẤY THÔNG TIN
//			
//			String query = 	"SELECT NGAYTAMUNG,TT.MA AS MATIENTE,TT.PK_SEQ AS TIENTEID, CASE WHEN TU.NCC_FK IS NOT NULL THEN NCC.TEN \n "+ 
//							"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN NV.TEN  END TEN, \n "+
//							"		isnull(TU.NCC_FK,0) NCC_FK,SOTIENTAMUNG,LYDOTAMUNG,THOIGIANHOANUNG,HINHTHUCHOANUNG, \n "+ 	
//							"		CASE WHEN TU.NHANVIEN_FK IS NOT NULL THEN 1 ELSE 2 END DOITUONGTAMUNG , \n"+
//							"		isnull(n.TEN, '') as nguoitao, muahang_fk dmhId, BP.TEN TENBOPHAN \n "+
//							"FROM	ERP_TAMUNG TU 	LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=TU.NCC_FK \n "+	
//							"		LEFT JOIN ERP_NhanVien NV ON NV.pk_seq=TU.NHANVIEN_FK  	\n"+
//							"		LEFT JOIN NHANVIEN n ON n.PK_SEQ = TU.NGUOITAO 	\n"+
//							"		LEFT JOIN ERP_DONVITHUCHIEN BP ON NV.DVTH_FK = BP.PK_SEQ \n"+
//							"		LEFT JOIN ERP_TIENTE TT ON TU.TIENTE_FK= TT.PK_SEQ \n"+
//							"WHERE	TU.PK_SEQ='"+Id+"'\n";
//			
//			System.out.println("in pdf : " + query);
//			String NGUOIDENGHI = "";
//			String BOPHAN = "";
//			String NGAYDENGHI ="";
//			String SOPHIEU ="";
//			double TONGTIENAVAT= 0;
//			String tienTeId="";
//			String maTienTe="";
//			ResultSet rs = db.get(query);
//			if (rs != null)
//			{
//				try
//				{
//					while (rs.next())
//					{
//						BOPHAN = rs.getString("TENBOPHAN");
//						NGAYDENGHI = rs.getString("NGAYTAMUNG");
//						SOPHIEU = Id;
//						tienTeId = rs.getString("TIENTEID");
//						maTienTe  = rs.getString("MATIENTE");
//					}
//					rs.close(); 
//				}
//				catch (Exception e)
//				{
//					System.out.println("__Exception: " + e.getMessage());
//				}
//			}
//		
//			NumberFormat formatter = new DecimalFormat("#,###,###.###");
//			NumberFormat formatter1 = new DecimalFormat("#,###,###");
//			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
//			document.setMargins(1.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
//			PdfWriter writer = PdfWriter.getInstance(document, outstream);
//			
//			document.open();
//			//document.setPageSize(new Rectangle(100.0f, 100.0f));
//			//document.setPageSize(PageSize.A4.rotate());
//		
//
//			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			Font font = new Font(bf, 13, Font.BOLD);
//			Font font2 = new Font(bf, 8, Font.BOLD);
//			
//			
//			//GIẤY ĐỀ NGHỊ THANH TOÁN
//			PdfPTable tableheader =new PdfPTable(1);
//			tableheader.setWidthPercentage(100);			
//
//			PdfPCell cell = new PdfPCell();		
//			
//			Paragraph hd = new Paragraph();
//			hd.add(new Chunk("GIẤY ĐỀ NGHỊ CHUYỂN TIỀN", new Font(bf, 16, Font.ITALIC)));
//			hd.setAlignment(Element.ALIGN_CENTER);	
//			cell.setBorderWidthBottom(0);
//			cell.addElement(hd);
//			
//			tableheader.addCell(cell);
//			
//			cell = new PdfPCell();	
//			hd = new Paragraph();
//			hd.add(new Chunk("Số: "+ Id, new Font(bf, 10, Font.ITALIC)));
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setBorderWidthTop(0);
//			cell.addElement(hd);
//			
//			tableheader.addCell(cell);
//				
//			document.add(tableheader);
//			
//			//CÔNG TY
//			
//			PdfPTable table1 = new PdfPTable(2);
//			table1.setWidthPercentage(100);
//			float[] withs1 = {60f,100f};
//			table1.setWidths(withs1);
//					
//			cell = new PdfPCell();	
//			hd = new Paragraph("CÔNG TY " , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
//			cell.setPaddingLeft(2.0f*CONVERT);
//			cell.addElement(hd);
//			cell.setBorder(0);	
//			
//			table1.addCell(cell);
//			
//			cell = new PdfPCell();	
//			hd = new Paragraph("CỔ PHẦN ĐỒ HỘP HẠ LONG " , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(0.8f*CONVERT);
//			cell.addElement(hd);
//			cell.setBorder(0);	
//			
//			table1.addCell(cell);
//			
//			document.add(table1);
//			
//			//BỘ PHẬN
//			
//			PdfPTable table2 = new PdfPTable(2);
//			table2.setWidthPercentage(100);
//			float[] withs2 = {60f,100f};
//			table2.setWidths(withs2);
//					
//			cell = new PdfPCell();	
//			hd = new Paragraph( "BỘ PHẬN" , new Font(bf, 12, Font.BOLD));
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(0.8f*CONVERT);
//			cell.setPaddingLeft(2.0f*CONVERT);
//			cell.addElement(hd);
//			cell.setBorder(0);	
//			
//			table2.addCell(cell);
//			
//			cell = new PdfPCell();	
//			hd = new Paragraph( "Phòng Kế Hoạch Cung Ứng", new Font(bf, 12, Font.NORMAL));
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(0.8f*CONVERT);
//			cell.addElement(hd);
//			cell.setBorder(0);	
//			
//			table2.addCell(cell);
//			
//			//NGÀY ĐỀ NGHỊ
//			
//			cell = new PdfPCell();	
//			hd = new Paragraph( "NGÀY" , new Font(bf, 12, Font.BOLD));
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(0.8f*CONVERT);
//			cell.setPaddingLeft(2.0f*CONVERT);
//			cell.addElement(hd);
//			cell.setBorder(0);	
//			
//			table2.addCell(cell);
//			
//			String [] ngayHD = NGAYDENGHI.split("-");
//			cell = new PdfPCell();	
//			hd = new Paragraph( ngayHD[2] + "/" + ngayHD[1] +  "/" + ngayHD[0], new Font(bf, 10, Font.NORMAL));
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(0.8f*CONVERT);
//			cell.addElement(hd);
//			cell.setBorder(0);			
//			
//			table2.addCell(cell);
//			
//			document.add(table2);
//			
//			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
//			
//			String nd = "SELECT NGAYTAMUNG NGAYNHAN, LYDOTAMUNG DIENGIAI, SOTIENTAMUNG THANHTIEN FROM ERP_TAMUNG WHERE PK_SEQ ='"+Id+"'";
//			
//			System.out.println("in nd : " + query);
//			String NGAYNHAN = "";
//			String DIENGIAI = "";
//			double THANHTIENVIET =0;
//			
//			int i =0;
//			
//			
//			PdfPTable table4 =new PdfPTable(4);
//			table4.setWidthPercentage(100);
//			float[] withs4 = {100f,200f,100f, 100f};
//			table4.setWidths(withs4);
//			table4.setSpacingAfter(0.2f*CONVERT);
//						
//			cell= new PdfPCell();	
//			hd = new Paragraph( "Ngày" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph( "Chi tiết chuyển tiền \n (Nội dung chuyển tiền)" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph( "Số tiền cả VAT" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph( "Ghi chú" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			DocTien doctien = new DocTien();
//			
//			rs = db.get(nd);
//			if (rs != null)
//			{
//				try
//				{
//					while (rs.next())
//					{
//						NGAYNHAN = rs.getString("NGAYNHAN");
//						DIENGIAI = rs.getString("DIENGIAI");
//						THANHTIENVIET = rs.getDouble("THANHTIEN");	
//						
//						cell= new PdfPCell();					
//						hd = new Paragraph( ngayHD[2] + "/" + ngayHD[1] +  "/" + ngayHD[0] , new Font(bf, 10, Font.NORMAL)); 
//						hd.setAlignment(Element.ALIGN_CENTER);
//						cell.addElement(hd);
//						
//						table4.addCell(cell);
//						
//						cell= new PdfPCell();	
//						hd = new Paragraph( DIENGIAI , new Font(bf, 10, Font.NORMAL)); 
//						hd.setAlignment(Element.ALIGN_LEFT);
//						cell.addElement(hd);
//						
//						table4.addCell(cell);
//						
//						cell= new PdfPCell();
//						String sotienbangchu ="";
//						if (!maTienTe.equals("VND"))
//						{
//							sotienbangchu = doctien.docTienNgoaiTe(Math.round(THANHTIENVIET), 0, maTienTe, maTienTe);
//						}else
//						{
//							
//						}
//						System.out.println("sotien:"+sotienbangchu);
//						
//						hd = new Paragraph(DinhDangTraphacoERP(formatter.format( THANHTIENVIET))+ " \n "+ sotienbangchu  , new Font(bf, 10, Font.NORMAL)); 
//						hd.setAlignment(Element.ALIGN_CENTER);
//						cell.addElement(hd);
//						table4.addCell(cell);
//						
//						cell= new PdfPCell();	
//						hd = new Paragraph( "" , new Font(bf, 10, Font.NORMAL)); 
//						hd.setAlignment(Element.ALIGN_LEFT);
//						cell.addElement(hd);
//						
//						table4.addCell(cell);
//						
//						i++;
//					}
//					rs.close(); 
//				}
//				catch (Exception e)
//				{
//					System.out.println("__Exception: " + e.getMessage());
//				}
//			}
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph( "" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(0.5f*CONVERT);
//			cell.setBorder(0);	
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph( "" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(0.5f*CONVERT);
//			cell.setBorder(0);	
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph("" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(0.5f*CONVERT);
//			cell.setBorder(0);	
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			cell= new PdfPCell();	
//			hd = new Paragraph( "" , new Font(bf, 10, Font.BOLD)); 
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(0.5f*CONVERT);
//			cell.setBorder(0);	
//			cell.addElement(hd);
//			
//			table4.addCell(cell);
//			
//			document.add(table4);
//						
//			
//			PdfPTable table7 = new PdfPTable(2);
//			table7.setWidthPercentage(100);
//			float[] withs7 = {100f, 100f};
//			table7.setWidths(withs7);
//			
//			cell = new PdfPCell();
//			hd = new Paragraph("NGƯỜI ĐỀ NGHỊ" , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			cell = new PdfPCell();
//			hd = new Paragraph("" , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			cell = new PdfPCell();
//			hd = new Paragraph("PHỤ TRÁCH BỘ PHẬN" , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			cell = new PdfPCell();
//			hd = new Paragraph("" , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			cell = new PdfPCell();
//			hd = new Paragraph("KẾ TOÁN TRƯỞNG" , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			//LẤY KẾ TOÁN TRƯỞNG
//			query = "select b.TEN from ERP_CHUCDANH a inner join NHANVIEN b on a.NHANVIEN_FK = b.PK_SEQ where a.PK_SEQ ='100001'";
//			
//			String ketoantruong = "";
//			
//			rs = db.get(query);
//			try{
//				if(rs != null){
//					rs.next();
//						ketoantruong = rs.getString("TEN");
//					rs.close();
//				}
//			}catch(java.sql.SQLException e){
//				System.out.println(e.toString());
//			}
//			
//			cell = new PdfPCell();
//			hd = new Paragraph(ketoantruong, new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_RIGHT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			cell = new PdfPCell();
//			hd = new Paragraph("TỔNG GIÁM ĐỐC" , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_LEFT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//			
//			table7.addCell(cell);
//			
//			//LẤY KẾ TOÁN TRƯỞNG
//			query = "select b.TEN from ERP_CHUCDANH a inner join NHANVIEN b on a.NHANVIEN_FK = b.PK_SEQ where a.PK_SEQ ='100000'";
//			
//			String tonggiamdoc = "";
//			
//			rs = db.get(query);
//			try{
//				if(rs != null){
//					rs.next();
//						tonggiamdoc = rs.getString("TEN");
//					rs.close();
//				}
//			}catch(java.sql.SQLException e){
//				System.out.println(e.toString());
//			}
//			
//			cell = new PdfPCell();
//			hd = new Paragraph(tonggiamdoc , new Font(bf, 12, Font.BOLD));//NGUOI MUA
//			hd.setAlignment(Element.ALIGN_RIGHT);
//			cell.setFixedHeight(1.0f*CONVERT);
//			cell.addElement(hd);	
//					
//			table7.addCell(cell);
//			
//			document.add(table7);
//							
//			
//		}
//		catch (Exception e)
//		{
//			System.out.println("115.Exception: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
	
	private void createBody(Document document, BaseFont bf, String nguoiDeNghi, String boPhan, String lyDo, String thoiHanThanhToan,
			String soTien,String docTienRaChu) {
		try {
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = { 100f };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			cell = new PdfPCell();
			hd = new Paragraph("Tên tôi là: " + nguoiDeNghi, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Địa chỉ: " + boPhan, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Đề nghị cho tạm ứng số tiền: " + soTien +" ( Viết bằng chữ) "+ docTienRaChu, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Lý do tạm ứng: " + lyDo, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Thời hạn thanh toán: " + thoiHanThanhToan, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			table1.setSpacingAfter(30f);
			document.add(table1);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void createFooter(Document document, BaseFont bf) {
		try {
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = { 33f, 34f, 33 };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			cell = new PdfPCell();
			hd = new Paragraph("Kế toán trưởng \n (Ký, họ tên)", new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph(" Phụ trách bộ phận \n (Ký, họ tên)", new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph(" Người đề nghị tạm ứng \n (Ký, họ tên)", new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			table1.setSpacingAfter(30f);
			document.add(table1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void createHeader(Document document, BaseFont bf, String boPhan, String ngayTamUng) {
		try {
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = { 35f, 32f, 33 };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			cell = new PdfPCell();
			hd = new Paragraph("Công ty TNHH Traphaco Hưng Yên\nTân Quang, Văn Lâm, Hưng Yên" , new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			
			// tách ngày
			String ngay = ngayTamUng.substring(8, 10);
	        String thang = ngayTamUng.substring(5, 7);
	        String nam = ngayTamUng.substring(0, 4);
			ngayTamUng= "Ngày "+ ngay + " tháng " + thang + " năm " + nam;
			
			cell = new PdfPCell();
			hd = new Paragraph(" GIẤY ĐỀ NGHỊ TẠM ỨNG \n "+ngayTamUng+" ", new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph(" Mẫu số 05 - TT \n Theo QĐ số: 15/2006/QĐ/BTC \n Ngày 20/03/2006 của Bộ trưởng \n BTC ", new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			table1.setSpacingAfter(30f);
			document.add(table1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	private void CreateDNTUPdfNhanVien(Document document, ServletOutputStream outstream, IErpTamUng pxkBean, String Id) throws IOException
	{
		dbutils db = new dbutils();
		try{
			//LẤY THÔNG TIN
			
			String query = 
				" SELECT TU.NGAYTAMUNG, TU.SOTIENTAMUNGNT, "+
				" 	isnull((CASE "+ 
				"		WHEN TU.NCC_FK IS NOT NULL THEN n.TEN "+ 
				"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN NV.TEN "+ 
				" 	END),'') as TENHIENTHI, " +
				"	isnull(TU.NCC_FK,0) NCC_FK, TU.NHANVIEN_FK, isnull(SOTIENTAMUNG, 0) as SOTIENTAMUNG, " +
				"   isnull(LYDOTAMUNG, '') as LYDOTAMUNG, TU.TIENTE_FK, THOIGIANHOANUNG, HINHTHUCHOANUNG, " +
				" 	CASE WHEN TU.NHANVIEN_FK IS NOT NULL THEN 1 ELSE 2 END as DOITUONGTAMUNG, " +
				
			 	"	isnull((CASE  " +  
				"		WHEN TU.NCC_FK IS NOT NULL THEN DVTH2.TEN  " +  
				"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN ISNULL(DVTH2.TEN, '')  " + 
				" 	END),'') as BOPHAN,  " +


				" isnull(n.TEN, '') as nguoitao, " +
			 	" isnull((CASE  " +  
				"		WHEN TU.NCC_FK IS NOT NULL THEN DVTH2.MA  " +  
				"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN ISNULL(DVTH2.MA, '')  " + 
				" END),'') as MABOPHAN , " +
				" TT.MA AS MATIENTE "+
				" FROM ERP_TAMUNG TU "+
				" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TU.NCC_FK "+
				" LEFT JOIN ERP_NhanVien NV ON NV.pk_seq = TU.NHANVIEN_FK  " +
				
				
				" LEFT JOIN NHANVIEN n on n.PK_SEQ = TU.NGUOITAO " +
				" LEFT JOIN ERP_TIENTE TT ON TU.TIENTE_FK = TT.PK_SEQ   " +
				" LEFT JOIN ERP_DONVITHUCHIEN DVTH2 ON n.PHONGBAN_FK=DVTH2.PK_SEQ "+

				" WHERE TU.PK_SEQ = "+Id+" ";
			
			System.out.println("in pdf : " + query);
			NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
			String nguoiDeNghi = "";
			String ngayTamUng = "";
			String boPhan = "";
			String maBoPhan = "";
			String lyDo = "";
			String thoiHanThanhToan = "";
			String soTien = "";
			String soTienNT="";
			long soTienN = 0;
			String maTienTe="";
			String sotienNT=""; 
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						nguoiDeNghi = rs.getString("TENHIENTHI");
						boPhan = rs.getString("BOPHAN");
						maBoPhan = rs.getString("MABOPHAN");
						lyDo = rs.getString("LYDOTAMUNG");
						thoiHanThanhToan = rs.getString("THOIGIANHOANUNG");
						soTien = formatter.format(rs.getDouble("SOTIENTAMUNG"));
						soTienNT = formatter.format(rs.getDouble("SOTIENTAMUNGNT"));
						soTienN = rs.getLong("SOTIENTAMUNG");
						sotienNT=rs.getString("SOTIENTAMUNGNT");
						ngayTamUng = rs.getString("NGAYTAMUNG");
						maTienTe =rs.getString("MATIENTE");
					}
					rs.close(); 
				}
				catch (Exception e)
				{
					System.out.println("__Exception: " + e.getMessage());
				}
				
				SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

				try {

					thoiHanThanhToan = myFormat.format(fromUser.parse(thoiHanThanhToan));
				} catch (ParseException e) {
				    e.printStackTrace();
				}
			}
		
			DocTien doctien = new DocTien();
//			String docTienRaChu = doctien.docTien(soTienN);
			String soTienTamUng="";
			String tienchu="";
			System.out.println("MATIENTE" +maTienTe);
			
			if (!maTienTe.equals("VND"))
			{
				System.out.println("tien ngoai te");
				//tienchu = doctien.docTienNgoaiTe_New(Double.parseDouble(sotienNT), maTienTe);
				soTienTamUng=soTienNT;
								
				tienchu = doctien.docTien_New_DLN(Double.parseDouble(sotienNT.replaceAll(",","")), maTienTe);
			}else
			{
				tienchu = doctien.docTien(soTienN);
				soTienTamUng=soTien;
			}
			
			
			
			
			
			
			
			
			document.setMargins(1.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 11, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//GIẤY ĐỀ NGHỊ THANH TOÁN
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();		
			
			Paragraph hd = new Paragraph();
			
			// liên 1
			// tạo header
			createHeader(document, bf, maBoPhan, ngayTamUng);
			hd.setTabSettings(new TabSettings(170f));
			hd.add(Chunk.TABBING);
			hd.add(new Chunk("Kính gửi:  				Phòng Tổng Hợp", 
					new Font(bf, 12, Font.BOLD)));
			
			document.add(hd);
			
			// body
			createBody(document, bf, nguoiDeNghi, boPhan, lyDo, thoiHanThanhToan, soTienTamUng,tienchu );
			// footer
			createFooter(document, bf);
			
			// dấu ngăn cách
			/*ArrayList<TabStop> tabList = new ArrayList<TabStop>();
			tabList.add(new TabStop(1, new VerticalPositionMark()));
			tabList.add(new TabStop(30, new DottedLineSeparator()));

			Paragraph paragraph = new Paragraph();
			paragraph.setTabSettings(new TabSettings(tabList));
			paragraph.add(Chunk.TABBING);
			document.add(paragraph);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		} finally{
			db.shutDown();
		}
		
	}
	private void CreateDNTUPdfNhaCungCap(Document document, ServletOutputStream outstream, IErpTamUng obj, String Id,String userTen) throws IOException{


		try {
			
			geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2so = new DecimalFormat("#,###,###.##");
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000");
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
/*			Font font = new Font(bf, 12, Font.BOLD);
			Font font8_bold = new Font(bf, 8, Font.BOLD);
			Font font8_i = new Font(bf, 8, Font.ITALIC);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);*/

			// SIZE
			float CONVERT = 28.346457f; // = 1cm

			PdfPCell cell;

			// ==========================header===================================//

			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = {8*CONVERT , 9f*CONVERT, 8*CONVERT };
			table1.setWidths(withs1);
			Paragraph hd = new Paragraph();
			
			
			String donvi = "";
			/*ResultSet rsdv = obj.getDvthList();
			
			if (rsdv != null) {
				try {
					while (rsdv.next()) {
						if (rsdv.getString("pk_seq").equals(obj.getDvthId())) {
							donvi = rsdv.getString("ten");
						}

					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}*/
			
			

			String boPhan = "";
			String maBoPhan = "";
			
			String queryDVTH = "SELECT  ISNULL(DVTH.Ma,'') as MABOPHAN,ISNULL (DVTH.Ten,'') as BOPHAN "+
				    " from Erp_TamUng TU " +
					" LEFT JOIN NHANVIEN NV on TU.NGUOITAO=NV.PK_SEQ" +
					" LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NV.PHONGBAN_FK=DVTH.PK_SEQ \n" +
					" WHERE TU.PK_SEQ = " +Id ;

			ResultSet rsbp = db.get(queryDVTH);
			if (rsbp!= null)
			{		
				while (rsbp.next())
				{
							
					boPhan = rsbp.getString("BOPHAN");
					maBoPhan = rsbp.getString("MABOPHAN");
				}
			}
			
			Chunk chunk = new Chunk("Đơn vị: Công ty TNHH Traphaco Hưng Yên\nBộ phận: " +maBoPhan + "-"		
					+ boPhan, new Font(bf, 9, Font.BOLD));
			chunk.setTextRise(2.0f);
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.add(chunk);
			
			cell = new PdfPCell();
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph();
			chunk = new Chunk("GIẤY ĐỀ NGHỊ THANH TOÁN", new Font(bf, 12,
					Font.BOLD));
			chunk.setTextRise(2.0f);
			hd.add(chunk);

			String[] tachNgay = obj.getNgayTamUng().split("-");

			chunk = new Chunk("\nNgày  " + tachNgay[2] + "  tháng  "
					+ tachNgay[1] + "  năm  " + tachNgay[0], new Font(bf, 12,
					Font.ITALIC));
			chunk.setTextRise(-20.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph();
			chunk = new Chunk("Mẫu số 05 - TT", new Font(bf, 10, Font.BOLD));
			chunk.setTextRise(2.0f);
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.add(chunk);

			chunk = new Chunk("\nTheo QĐ số 15/2016/QĐ/BTC", new Font(
					bf, 9, Font.ITALIC));
			chunk.setTextRise(5.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_CENTER);

			chunk = new Chunk("\nNgày 20/03/2006 của Bộ trưởng BTC", new Font(bf, 9,
					Font.ITALIC));
			chunk.setTextRise(7.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_CENTER);

			cell.addElement(hd);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			table1.addCell(cell);
			table1.setSpacingAfter(20);
			document.add(table1);
			// ====================================================================//
			// ========================BODY========================================//
			// ============KÍNH GỬI
			// ================================================//

			table1 = new PdfPTable(2);
			table1.setSpacingBefore(15);
			table1.setWidthPercentage(100);

			float[] withstable = { 20f, 30f };
			table1.setWidths(withstable);

			// cell kính gửi
			cell = new PdfPCell();
			cell.setBorder(0);
			chunk = new Chunk("Kính gửi:", new Font(bf, 12, Font.BOLDITALIC));
			chunk.setTextRise(2.0f);
			chunk.setUnderline(1, (float) -0.2);
			hd = new Paragraph();
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.addElement(hd);
			cell.setPaddingTop(0);
			table1.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			hd = new Paragraph();
			chunk = new Chunk("     -   Lãnh đạo công ty", new Font(bf, 12,
					Font.NORMAL));
			chunk.setTextRise(2.0f);
			hd.add(chunk);
			chunk = new Chunk("\n     -   Phòng Tổng Hợp", new Font(
					bf, 12, Font.NORMAL));
			chunk.setTextRise(2.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setPaddingTop(0);

			table1.addCell(cell);
			document.add(table1);

			// ==============================điền thông
			// tin====================//
			hd = new Paragraph("Họ và tên người đề nghị tạm ứng: "
					+ userTen, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingBefore(20);
			document.add(hd);
			
			
			

			hd = new Paragraph("Bộ phận :  " + boPhan, new Font(bf,
					12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			hd = new Paragraph("Nội dung tạm ứng: " + obj.getLyDoTamUng(), new Font(bf, 12,
					Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);
			ResultSet rshttt = obj.getHtttRs();
			
			String hinhthuc = "";
			if (rshttt != null) {
				try {
					while (rshttt.next()) {
						if (rshttt.getString("pk_seq").equals(obj.getHtttId())) {
							hinhthuc = rshttt.getString("ten");
						
						}
					}
					rshttt.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			hd = new Paragraph("Hình thức tạm ứng: " + hinhthuc, new Font(
					bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);
			
			
			String tienchu="";
			String maTienTe="";
			String soTienTamUng="";
			DocTien doctien = new DocTien();
			String soTienTU="";
			
			String query = " select MA AS MATIENTE from erp_tiente \n" + 
			" where pk_seq="+obj.getTienTeId()+" ";	
			ResultSet rs=db.get(query);			
			if(rs.next())
			{
				maTienTe  = rs.getString("MATIENTE");
			}
			System.out.println("MATIENTE" +maTienTe);
			
			if (!maTienTe.equals("VND"))
			{
				System.out.println("tien ngoai te");
				soTienTamUng=obj.getSoTienNT();
				soTienTU=formatter2so.format(Double.parseDouble(soTienTamUng));
				
				tienchu = doctien.docTien_New_DLN(Double.parseDouble(soTienTU.replaceAll(",","")), maTienTe);
			}else
			{
				soTienTamUng=obj.getSoTienTamUng();
				soTienTU=formatter.format(Double.parseDouble(soTienTamUng));
				tienchu = doctien.docTien(Long.parseLong(soTienTamUng));
				
			}

			hd = new Paragraph();
			hd.setAlignment(Element.ALIGN_LEFT);
			chunk = new Chunk("       Số tiền tạm ứng: ", new Font(bf, 12,
					Font.NORMAL));
			chunk.setTextRise(0);
			hd.add(chunk);
//			System.out.println("Số tiền tạm ứng :" + obj.getSoTienTamUng());
			chunk = new Chunk(soTienTU, new Font(bf, 12, Font.BOLD));
			chunk.setTextRise(0);
			hd.add(chunk);
			
			System.out.println("Tiền chữ :" + tienchu);
			chunk = new Chunk(" (" + tienchu + ").", new Font(bf, 12,
					Font.ITALIC));
			chunk.setTextRise(0);
			hd.add(chunk);
			
			document.add(hd);

			hd = new Paragraph("( Kèm theo .......  chứng từ gốc).", new Font(
					bf, 12, Font.NORMAL));
			hd.setSpacingBefore(5);
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			if (obj.getHtttId().equals("100001")) {
				String chuTaiKhoan = "";
				String maSoThue = "";
				String soTaiKhoan = "";
				String tenNganHang = "";
				String tenChiNhanh = "";
				
				String queryNH = "";
				//NHÀ CUNG CẤP
					queryNH = "select ncc.TEN, ncc.SOTAIKHOAN, ncc.MASOTHUE, nh.TEN  as TENNGANHANG,isnull(CN.TEN,'')  AS TENCHINHANH "
							+ "from ERP_NHACUNGCAP ncc " +
					"LEFT JOIN ERP_NGANHANG nh on nh.PK_SEQ = ncc.NGANHANG_FK " +
					"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = ncc.ChiNhanh_FK \n" +
					"WHERE ncc.PK_SEQ = " + obj.getnccId();
				System.out.println("----Query tai khoan: "  + queryNH);
				ResultSet rsncc = db.get(queryNH);
				if(rsncc != null){
					try {
						if(rsncc.next()){
							chuTaiKhoan = rsncc.getString("TEN");
							maSoThue = rsncc.getString("MASOTHUE");
							soTaiKhoan = rsncc.getString("SOTAIKHOAN");
							tenNganHang = rsncc.getString("TENNGANHANG").equals("null")?"":rsncc.getString("TENNGANHANG");
							tenChiNhanh = rsncc.getString("TENCHINHANH");
						}
						rsncc.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				hd = new Paragraph();
				hd.setSpacingBefore(5);
				hd.setAlignment(Element.ALIGN_LEFT);
				chunk = new Chunk("Chủ tài khoản: ", new Font(bf, 12,
						Font.NORMAL));
				chunk.setTextRise(0);
				hd.add(chunk);

				chunk = new Chunk(chuTaiKhoan,
						new Font(bf, 12, Font.BOLD));
				chunk.setTextRise(0);
				hd.add(chunk);
				document.add(hd);

				hd = new Paragraph("Mã số thuế: " + maSoThue, new Font(bf, 12,
						Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);

				hd = new Paragraph("Tài khoản: " + soTaiKhoan, new Font(bf, 12,
						Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);

				hd = new Paragraph(
						"Tại: " + tenNganHang,
						new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);
				
				hd = new Paragraph(
						"Chi nhánh: " + tenChiNhanh,
						new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);
				

			}
			//tạo footer ký tên
			table1 = new PdfPTable(3);
			table1.setSpacingBefore(50);
			table1.setWidthPercentage(100);
			footer(hd, cell, 2, table1, userTen, bf);
			document.add(table1);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	
	}
	public void footer( Paragraph hd, PdfPCell cell, int n, PdfPTable table1, String tenNhanVien, BaseFont bf){
		hd = new Paragraph();

		cell = new PdfPCell();
		if(n == 3){
			hd = new Paragraph("LÃNH ĐẠO CÔNG TY", new Font(bf, 12, Font.BOLD));
		}else{
			hd = new Paragraph("", new Font(bf, 12, Font.BOLD));
		}
		
		hd.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(hd);
		cell.setBorder(0);

		table1.addCell(cell);

		cell = new PdfPCell();
		hd = new Paragraph("PHỤ TRÁCH BỘ PHẬN", new Font(bf, 12, Font.BOLD));
		hd.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(hd);
		cell.setBorder(0);

		table1.addCell(cell);

		cell = new PdfPCell();
		hd = new Paragraph("NGƯỜI ĐỀ NGHỊ", new Font(bf, 12, Font.BOLD));
		hd.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(hd);
		cell.setBorder(0);

		table1.addCell(cell);

		for (int i = 0; i < 3; i++) {
			cell = new PdfPCell();
			if(n ==2){
				hd = new Paragraph("", new Font(bf, 12, Font.ITALIC));
			}else{
				hd = new Paragraph("(Chữ ký)", new Font(bf, 12, Font.ITALIC));
			}
			
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);
			table1.addCell(cell);
		}
		for (int i = 0; i < 3; i++) {
			cell = new PdfPCell();
			if (i < 2) {
				hd = new Paragraph("", new Font(bf, 12, Font.ITALIC));
			} else {
				hd = new Paragraph("\n\n\n" + tenNhanVien, new Font(bf,
						12, Font.ITALIC));
			}
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			hd.setSpacingBefore(30);
			cell.setBorder(0);
			table1.addCell(cell);
		}
	}
	
	
	private void inPDF(Document document, ServletOutputStream outstream, IErpTamUng obj) throws IOException {
		
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_HEADER_WIDTHS = {3.5f * CONVERT, 8.1f * CONVERT, 5.0f * CONVERT };
			float[] TABLE_MIDDLE_WIDTHS = {3.5f * CONVERT, 13.1f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT, 4.15f * CONVERT};			
			
			//VẼ khung header (Logo Picture | Header Titles)
			PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			
			headerTable.setWidths(TABLE_HEADER_WIDTHS);
			headerTable.setWidthPercentage(100);
			
			PdfPCell cell;
			
			//header left
			PdfPTable headerLeftTable = new PdfPTable(1);
			headerLeftTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[0]});
			headerLeftTable.setWidthPercentage(100);
			
			String[] imageSources = {
					"C:\\Program Files\\Apache Software Foundation\\8888\\webapps\\TraphacoERP\\pages\\images\\logoTraphacoERP.jpg",
					"C:\\Program Files (x86)\\Apache Software Foundation\\8888\\webapps\\TraphacoERP\\pages\\images\\logoTraphacoERP.jpg",
					"D:\\TOMCAT WEB SOURCE 2\\webapps\\TraphacoERP\\pages\\images\\logoTraphacoERP.jpg",
					"D:\\Project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoTraphacoERP.jpg"
			}; 
			Image logoImage = null;
			
			for(int i = 0; i < imageSources.length; i++) {
				try {
					if(logoImage == null) {
						logoImage = Image.getInstance(imageSources[i]);
						System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = " + imageSources[i]);
						break;
					}
				} catch (Exception e) {	}
			}
			if(logoImage != null) {
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Load Images Logo Thanh Cong....");
				logoImage.setBorder(0);
				logoImage.setAlignment(Element.ALIGN_CENTER);
				logoImage.scaleToFit(2.0f * CONVERT, 2.0f * CONVERT);
				//headerLeftTable.addCell(logoImage);
				logoImage.setAbsolutePosition(2.8f * CONVERT, PageSize.A4.getHeight() - 3.2f*CONVERT);
				
				document.add(logoImage);
				/**/
			} else {
				
			}
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Khong load duoc hinh anh logo");
			cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(2.5f * CONVERT);
			headerLeftTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("MPP: ", font9));
			cell.setBorderWidthTop(1);
			cell.setBorderWidthBottom(0);
			cell.setBorderWidthLeft(0);
			cell.setBorderWidthRight(0);
			cell.setFixedHeight(0.5f * CONVERT);
			headerLeftTable.addCell(cell);

			
			headerTable.addCell(headerLeftTable);
			
			//Header middle: PHIẾU ĐỀ NGHỊ TẠM ỨNG
			
			String tieude="";
			if(obj.getDoiTuongTamUng().equals("1"))
			{
				tieude = "PHIẾU ĐỀ NGHỊ TẠM ỨNG";
			}
			else 
			{
				tieude = "PHIẾU ĐỀ NGHỊ \n" +
						"THANH TOÁN TRƯỚC";
			}
			
			cell = new PdfPCell(new Paragraph(tieude, font15bold));
			cell.setFixedHeight(3.0f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerTable.addCell(cell);
			
			//Header right: Table
			PdfPTable headerRightTable = new PdfPTable(2);
			headerRightTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[2]*0.4f, TABLE_HEADER_WIDTHS[2]*0.6f});
			headerRightTable.setWidthPercentage(100);
			
			cell = new PdfPCell(new Paragraph("Loại", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": Biểu mẫu", font10));
			
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Mã số", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			String maso="";
			if(obj.getDoiTuongTamUng().equals("1"))
			{
				maso = ": BM-KTTC-005";
			}
			else 
			{
				maso = ": BM-KTTC-012";
			}
			cell = new PdfPCell(new Paragraph(maso, font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Soát xét", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": 01", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Điều chỉnh", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": 01", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Trang", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": 1/1", font10));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerRightTable.addCell(cell);
			
			headerTable.addCell(headerRightTable);
			
			document.add(headerTable);
			
			
			//Số
			Paragraph par = new Paragraph("Số: " + obj.getId(), font9);
			par.setAlignment(Element.ALIGN_CENTER);
			par.setSpacingBefore(0.5f * CONVERT);
			par.setSpacingAfter(0.2f * CONVERT);
			document.add(par);
			
			//Middle Table
			PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
			middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
			middleTable.setWidthPercentage(100);
			
			
			cell = new PdfPCell(new Paragraph("Họ và tên", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			String hovaten="";
			if(obj.getDoiTuongTamUng().equals("1"))
			{
				hovaten = obj.getTenHienThi();
			}
			else 
			{
				hovaten = obj.getNguoitao();
			}
			
			cell = new PdfPCell(new Paragraph(": " + hovaten, font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Bộ phận", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + obj.getBoPhan(), font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số tiền đề nghị", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			//cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			String tienchu = "";
			try {		
				float sotien = Float.parseFloat(obj.getSoTienTamUng().replaceAll(",", ""));			
				NumberFormat nf = new DecimalFormat("##########");
				long ltien = Long.parseLong(nf.format(sotien));
				tienchu = DocTien.docTien(ltien);
			} catch (Exception e) {
				System.out.println("Exception = " + e.getMessage());
			}
			
			cell = new PdfPCell(new Paragraph(": " + formatter.format(Float.parseFloat(obj.getSoTienTamUng())) + "   Bằng chữ: " + tienchu, font11));
			cell.setBorder(Rectangle.NO_BORDER);
			//cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			
			
			 
		/*	cell = new PdfPCell(new Paragraph("Bằng chữ", font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
		
			cell = new PdfPCell(new Paragraph(": " + tienchu, font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);*/
			
			String lydo="";
			if(obj.getDoiTuongTamUng().equals("1"))
			{
				lydo = "Lý do tạm ứng";
			}
			else 
			{
				lydo = "Lý do thanh toán";
			}
			
			cell = new PdfPCell(new Paragraph(lydo, font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + obj.getLyDoTamUng(), font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			String ngay ="";
			String thang ="";
			String nam ="";
			if(obj.getDoiTuongTamUng().equals("1"))
			{
				cell = new PdfPCell(new Paragraph("Thời hạn thanh toán: ", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable.addCell(cell);
				
				ngay = obj.getThoiGianHoanUng().substring(8,10);
				thang = obj.getThoiGianHoanUng().substring(5,7);
				nam = obj.getThoiGianHoanUng().substring(0,4);
				cell = new PdfPCell(new Paragraph(": " + ngay + "-"+thang+"-" + nam, font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable.addCell(cell);
			}
			else 
			{
				cell = new PdfPCell(new Paragraph("Nhà cung cấp", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": " + obj.getTenHienThi(), font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable.addCell(cell);
			}
			
			
			String duyet="";
			if(obj.getDoiTuongTamUng().equals("1"))
			{
				duyet = "Duyệt tạm ứng";
			}
			else 
			{
				duyet = "Duyệt";
			}
			
			cell = new PdfPCell(new Paragraph(duyet, font11));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": ", font11));// Để trống
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setFixedHeight(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable.addCell(cell);
			
			document.add(middleTable);
			

			//Ngày tháng năm
			ngay = obj.getNgayTamUng().substring(8,10);
			thang = obj.getNgayTamUng().substring(5,7);
			nam = obj.getNgayTamUng().substring(0,4);
			
			par = new Paragraph("Ngày "+ngay+" tháng "+thang+" năm " + nam, font11);
			par.setAlignment(Element.ALIGN_RIGHT);
			par.setSpacingBefore(0.3f * CONVERT);
			par.setSpacingAfter(0.2f * CONVERT);
			document.add(par);
			
			//TABLE FOOTER
			PdfPTable tableFooter = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(TABLE_FOOTER_WIDTHS);
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("NGƯỜI ĐỀ NGHỊ", font9bold));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell12 = new PdfPCell(new Paragraph("KẾ TOÁN TRƯỞNG", font9bold));
			cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell13 = new PdfPCell(new Paragraph("T.BỘ PHẬN", font9bold));
			cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell14 = new PdfPCell(new Paragraph("BAN TỔNG GIÁM ĐỐC", font9bold));
			cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			
			
			PdfPCell cell15 = new PdfPCell(new Paragraph( hovaten, font9));
			cell15.setBorder(Rectangle.NO_BORDER);
			cell15.setFixedHeight(2.0f * CONVERT);
			cell15.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell15.setColspan(4);
			tableFooter.addCell(cell15);
					
			
			
			document.add(tableFooter);
			document.close();
			
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
}
