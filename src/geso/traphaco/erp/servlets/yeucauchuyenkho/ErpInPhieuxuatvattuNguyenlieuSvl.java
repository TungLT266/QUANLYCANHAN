package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;
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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInPhieuxuatvattuNguyenlieuSvl  extends HttpServlet{
	
	List<IYeucau> danhsach_sp;
	
	public ErpInPhieuxuatvattuNguyenlieuSvl() {
        super(); 
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
		response.setHeader("Content-Disposition",
				" inline; filename=Traphaco_InPhieuxuatvattuNguyenlieu.pdf");

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
		
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId: " + userId);
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id:" + id);
		
		//lay phieu chuyen kho
		IErpChuyenkho lsxBean = new ErpChuyenkho(id);
	    lsxBean.setUserId(userId);
	    lsxBean.init();
		
		//lay danh sach vat tu
	    danhsach_sp = (List<IYeucau>)lsxBean.getSpList(); 
		Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	    //lay thong tin tung vat tu
		
		
//lay kho xuat
		
		String tenkhoxuat="";
		ResultSet khoxuatRs=lsxBean.getKhoXuatRs();
		if(khoxuatRs != null)
		{
			try {
				while(khoxuatRs.next()){
					if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){
						tenkhoxuat=khoxuatRs.getString("ten");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//noi dung form
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			//dinh dang font chu
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
			
			Font font12_normal = new Font(bf, 12, Font.NORMAL);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12_italic = new Font(bf, 12, Font.ITALIC);
			
			float CONVERT = 28.346457f;// 1 cm
		//	float[] TABLE_HEADER_WIDTHS = new float[] {9.25f * CONVERT ,11.25f * CONVERT };
			PdfPCell cell;
			
			
			//=---------------------------tao header1
			PdfPTable table2 = new PdfPTable(2);	
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("pathPrint")+"\\logo.gif");
			hinhanh.scalePercent(10);
			hinhanh.setAbsolutePosition(3f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);
			
			cell = new PdfPCell();
			Paragraph p = new Paragraph();
			p = new Paragraph();
			p.add("\n");
			cell.setBorder(0);
			
			p.add(new Chunk("CÔNG TY CP TRAPHACO", font12_normal)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table2.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("BM 28/07 \n", font11_italic )); 
			p.add(new Chunk("BH/SĐ: 22/10/15 \n", font11_italic )); 
			p.setAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table2.addCell(cell);
			
			//----------------------tua de
			cell = new PdfPCell();
			p = new Paragraph();
			p.setSpacingBefore(15);
			p.add(new Chunk("\n  PHIẾU XUẤT VẬT TƯ\n", new Font(bf, 18, Font.BOLD)));
			p.add(new Chunk("Số:", font12_normal)); 
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.addElement(p);
			table2.addCell(cell);
			
			// ----------------------thong tin
			
			/*PdfPTable table3 = new PdfPTable(1);	
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.setSpacingBefore(10);
			p.add(new Chunk("Họ và tên người nhận hàng:\n", font12_normal));
			p.add(new Chunk("Địa chỉ:\n", font12_normal));
			p.add(new Chunk("Lý do xuất:\n", font12_normal));
			p.add(new Chunk("Xuất tại kho: Kho phụ liệu cấp 2\n", font12_normal));
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
		
			cell.addElement(p);
			table3.addCell(cell);*/
			
			PdfPTable tbl = new PdfPTable(1);
			tbl.setWidthPercentage(100);
			tbl.setSpacingBefore(0);
			tbl.getDefaultCell().setBorder(0);
			
			cell = new PdfPCell(new Paragraph("Họ và tên người nhận hàng: ", font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Địa chỉ: ", font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Lý do xuất: "+lsxBean.getLydoyeucau(), font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Xuất tại kho: "+tenkhoxuat, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			
			//bang du lieu
			float[] vattu_width = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.2f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT,1.0f * CONVERT,1.0f * CONVERT};
			PdfPTable tbl_vattu = new PdfPTable(vattu_width.length);
			tbl_vattu.setWidthPercentage(100);
			tbl_vattu.setSpacingBefore(10);
			tbl_vattu.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl_vattu.setWidths(vattu_width);
			tbl_vattu.getDefaultCell().setBorder(0);
			tbl_vattu.setSpacingAfter(8.0f);

			String[] spTitles = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Hàm ẩm","Hàm lượng","Phiếu định tính","Thùng ", "Số lô NSX","Vị trí" };
			//in tieu de
			for (int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11_bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.WHITE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tbl_vattu.addCell(cell);
			}
			
			
//in danh sach san pham 
			
			int  j =0;  
			int sott = 1;
			if(danhsach_sp!=null)
	       		while (j< danhsach_sp.size()) { 

	       			IYeucau sanpham=danhsach_sp.get(j);
	       			String Masp=sanpham.getMa();
	       			String Tensp=sanpham.getTen();
	       			String Donvi=sanpham.getDonViTinh();
	       			
	       			System.out.println("ma san pham:" + Masp);
	       			
	       			//duyet trong thong tin cua sanpham_soluong
	       			Enumeration<String> keys = sanpham_soluong.keys();
	       			
	       			while( keys.hasMoreElements() )
                	{
                		String key = keys.nextElement();
                		System.out.println("key trong hastable:" + key);
                		if(key.startsWith(sanpham.getMa()))
                		{
                			String[] arr = key.split("__");
                			String temID = sanpham.getMa() + "__ ";
                			
                			String soluongXUAT = sanpham_soluong.get(key);
                			String solo=arr[2];
                			String ngayhh=arr[3];
                			String ngaynk=arr[4];
                			String me=arr[5];
                			String thung=arr[6];
                			String vitri=arr[7];
                			String maphieu=arr[8];
                			String phieudt=arr[9];
                			String phieueo=arr[10];
                			String marquete=arr[11];
                			String hamluong=arr[12];
                			String hamam=arr[13];
                			String pkn="";
                			String phieudangki="";
                			System.out.println("thong tin san pham so lo:" + solo);
                			//day la thong cua san pham
                			String[] spTitles2={sott+" ",Masp,Tensp,Donvi,soluongXUAT,pkn,hamam,hamluong,phieudt,thung,solo,vitri};
                			
                			for (int z = 0; z < spTitles2.length; z++) {
        						cell = new PdfPCell(new Paragraph(spTitles2[z],new Font(bf, 10,Font.NORMAL)));
        						cell.setPadding(3.0f);
        						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        						tbl_vattu.addCell(cell);
        						}
        						sott++;
                		}
                	}	
	       			j++;
	       		}
			
			//--------------------- footer
			float[] footer_width = { 5.0f * CONVERT, 3.0f * CONVERT,3.5f * CONVERT,3.0f * CONVERT, 2.5f * CONVERT};
			PdfPTable footer = new PdfPTable(5);
			footer.setWidthPercentage(100);
			footer.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.setWidths(footer_width);
			footer.getDefaultCell().setBorder(0);
			footer.setSpacingAfter(15.0f);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("PHÓ TỔNG GIÁM ĐỐC",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("QĐPX",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("P. KẾ HOẠCH",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("THỦ KHO",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("LẬP PHIẾU",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			//--------------------
			
			
			document.add(hinhanh);
			document.add(table2); //header + tieu de
			/*document.add(table3);*/
			document.add(tbl);	 //thong tin
			document.add(tbl_vattu); //bang du lieu
			document.add(footer);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
		
	}

}
