package geso.traphaco.erp.servlets.hoadontaichinh;

import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadontaichinh.imp.ErpHoaDonTaiChinh;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.BorderType;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class ErpHoaDonTaiChinhPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpHoaDonTaiChinhPdfSvl() {
        super();
    }

    float CONVERT = 28.346457f;  // =1cm
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		IErpHoaDonTaiChinh obj;
		String userId;
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();	    
	    String hdId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpHoaDonTaiChinh();
	    //obj.initdisplay(ddhId);
	    
		
		if(querystring.contains("print"))
		{		
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoaDonTaiChinh.pdf");
			
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();			
			
			if(userId.equals("100472")) //HẢI PHÒNG
			{
				this.CreateHdPdf_HaiPhong(document, outstream,obj, hdId);
			}
			else if (userId.equals("100503")|| userId.equals("100504")){// HÀ NỘI -- hongbui
				this.CreateHdPdf_HANOI(document, outstream,obj, hdId);
			}
			else if(userId.equals("100514")){// ĐÀ NẴNG
				this.CreateHdPdf_DANANG(document, outstream,obj, hdId);
			}
			else{
				this.CreateHdPdf_HCM(document, outstream,obj, hdId);
			}
			
			document.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreateHdPdf_HaiPhong(Document document, ServletOutputStream outstream, IErpHoaDonTaiChinh pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
			" SELECT HD.PK_SEQ,HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, isnull(KH.TenXuatHD,'') TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI, \n" +
			"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG, \n"+
			"	 isnull(HD.DONVI,'') DONVIVL, isnull(HD.DIACHI,'') AS DIACHIVL, isnull(HD.MASOTHUE,'') MASOTHUEVL, isnull(HD.NGUOIMUAHANG,'') NGUOIMUAHANGVL, HD.KHACHHANG_FK "+
			" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
			" WHERE HD.PK_SEQ ='"+hdId+"' \n";
			
			
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			String KHACHHANG_FK = "";
			String SOCHUNGTU = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOCHUNGTU = rs.getString("PK_SEQ");
					KHACHHANG_FK = rs.getString("KHACHHANG_FK");
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					if(KHACHHANG_FK.equals("107385")||KHACHHANG_FK.equals("107573")||KHACHHANG_FK.equals("107689")){//KHÁCH HÀNG VÃNG LAI// KHÁCH HÀNG KHÔNG THU TIỀN						
						MASOTHUE = rs.getString("MASOTHUEVL");
						DIACHI = rs.getString("DIACHIVL");
						NGUOIMUA = rs.getString("NGUOIMUAHANGVL");
						TENKH = rs.getString("DONVIVL");
						if(MASOTHUE.trim().length()<=0&&DIACHI.trim().length()<=0&&NGUOIMUA.trim().length()<=0&&TENKH.trim().length()<=0)
						{
							MASOTHUE = rs.getString("MASOTHUE");
							DIACHI = rs.getString("DIACHI");
							NGUOIMUA = rs.getString("NGUOIMUA");
							TENKH = rs.getString("TEN");
						}
					}
					else{
						MASOTHUE = rs.getString("MASOTHUE");
						DIACHI = rs.getString("DIACHI");
						NGUOIMUA = rs.getString("NGUOIMUA");
						TENKH = rs.getString("TEN");
					}
					TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
					HANGHOADICHVU = rs.getString("GHICHU");
					DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
					
					
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(0.0f*CONVERT, 1.0f*CONVERT, 0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			//SO CHUNG TU
			PdfPTable tablepkseq =new PdfPTable(1);
			tablepkseq.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			
			Paragraph hd = new Paragraph(SOCHUNGTU , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablepkseq.addCell(cell);
			document.add(tablepkseq);
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setSpacingBefore(3.2f*CONVERT);
			tableheader.setWidthPercentage(100);			

			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			hd = new Paragraph(ngayHD[2] + "                 " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA + " " +DIACHIGIAOHANG, new Font(bf, 12, Font.BOLD));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.BOLD)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.BOLD)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.9f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.0f*CONVERT,9.0f*CONVERT, 3.0f*CONVERT, 2.0f*CONVERT, 3.2f*CONVERT, 3.2f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = "	SELECT a.SANPHAM_FK, b.TEN TENSP,a.DONVITINH,a.SOLUONG, a.DONGIA, a.CHIETKHAU, a.DONGIACK \n"+
								  "	FROM ERP_HOADON_SP a INNER JOIN ERP_SANPHAM b ON a.SANPHAM_FK = b.PK_SEQ \n"+
								  " WHERE a.HOADON_FK ='"+hdId+"' \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					TENSP = rsSP.getString("TENSP");
					DONVITINH = rsSP.getString("DONVITINH");
					SOLUONG = rsSP.getDouble("SOLUONG");
					DONGIA = Math.round(rsSP.getDouble("DONGIA"));
					CHIETKHAU = rsSP.getDouble("CHIETKHAU");
					DONGIAGIAM = Math.round(rsSP.getDouble("DONGIACK"));
					THANHTIENGIAM = SOLUONG*DONGIAGIAM;
					TONGTIENHANG+=THANHTIENGIAM;
									
					String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)),
							DinhDangTraphacoERP(formatter.format(DONGIAGIAM)), DinhDangTraphacoERP(formatter.format(THANHTIENGIAM)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.BOLD)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									cells.setPaddingLeft(1.4f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										cells.setPaddingRight(0.1f*CONVERT);
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.4f*CONVERT);
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.7f*CONVERT);
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.6f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 16-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.BOLD)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
			
			for (int j = 0; j < th.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 11, Font.BOLD)));
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
									
				sanpham.addCell(cells);
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENHANG)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph(DinhDangTraphacoERP(formatter.format(TIENCK)) , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENSAUCK)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( "10" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(VAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);			
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(3.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreateHdPdf_HCM(Document document, ServletOutputStream outstream, IErpHoaDonTaiChinh pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				" SELECT HD.PK_SEQ,HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, ISNULL(KH.TenXuatHD, '') TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI, \n" +
				"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG, \n"+
				"	 isnull(HD.DONVI,'') DONVIVL, isnull(HD.DIACHI,'') AS DIACHIVL, isnull(HD.MASOTHUE,'') MASOTHUEVL, isnull(HD.NGUOIMUAHANG,'') NGUOIMUAHANGVL, HD.KHACHHANG_FK "+
				" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";
				
				
				String SOHOADON="";
				String KYHIEU="";
				String NGAYXUATHD ="";
				String HINHTHUCTT="";
				String MASOTHUE="";
				String DIACHI="";
				String NGUOIMUA="";
				String TENKH="";
				double TIENCK=0;
				double VAT=0;
				double TONGTIENAVAT=0;
				String HANGHOADICHVU = "";
				String DIACHIGIAOHANG = "";
				String KHACHHANG_FK = "";
				String SOCHUNGTU = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOCHUNGTU = rs.getString("PK_SEQ");
					KHACHHANG_FK = rs.getString("KHACHHANG_FK");
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					if(KHACHHANG_FK.equals("107385")||KHACHHANG_FK.equals("107573")||KHACHHANG_FK.equals("107689")){//KHÁCH HÀNG VÃNG LAI// KHÁCH HÀNG KHÔNG THU TIỀN						
						MASOTHUE = rs.getString("MASOTHUEVL");
						DIACHI = rs.getString("DIACHIVL");
						NGUOIMUA = rs.getString("NGUOIMUAHANGVL");
						TENKH = rs.getString("DONVIVL");
						if(MASOTHUE.trim().length()<=0&&DIACHI.trim().length()<=0&&NGUOIMUA.trim().length()<=0&&TENKH.trim().length()<=0)
						{
							MASOTHUE = rs.getString("MASOTHUE");
							DIACHI = rs.getString("DIACHI");
							NGUOIMUA = rs.getString("NGUOIMUA");
							TENKH = rs.getString("TEN");
						}
					}
					else{
						MASOTHUE = rs.getString("MASOTHUE");
						DIACHI = rs.getString("DIACHI");
						NGUOIMUA = rs.getString("NGUOIMUA");
						TENKH = rs.getString("TEN");
					}
					TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
					HANGHOADICHVU = rs.getString("GHICHU");
					DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
					
					
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(1.8f*CONVERT, 0.0f*CONVERT, 0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			//SO CHUNG TU
			PdfPTable tablepkseq =new PdfPTable(1);
			tablepkseq.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			
			Paragraph hd = new Paragraph(SOCHUNGTU , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablepkseq.addCell(cell);
			document.add(tablepkseq);
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setSpacingBefore(2.9f*CONVERT);
			tableheader.setWidthPercentage(100);			

			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			hd = new Paragraph(ngayHD[2] + "               " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.5f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA +" "+ DIACHIGIAOHANG, new Font(bf, 12, Font.BOLD));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.BOLD)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.BOLD)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.2f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.3f*CONVERT,12.0f*CONVERT, 2.2f*CONVERT, 2.3f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT, 4.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = "	SELECT a.SANPHAM_FK, b.TEN TENSP,a.DONVITINH,a.SOLUONG, a.DONGIA, a.CHIETKHAU,a.DONGIACK \n"+
								  "	FROM ERP_HOADON_SP a INNER JOIN ERP_SANPHAM b ON a.SANPHAM_FK = b.PK_SEQ \n"+
								  " WHERE a.HOADON_FK ='"+hdId+"' \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					TENSP = rsSP.getString("TENSP");
					DONVITINH = rsSP.getString("DONVITINH");
					SOLUONG = rsSP.getDouble("SOLUONG");
					DONGIA = Math.round(rsSP.getDouble("DONGIA"));
					CHIETKHAU = rsSP.getDouble("CHIETKHAU");
					DONGIAGIAM = Math.round(rsSP.getDouble("DONGIACK"));
					THANHTIENGIAM = SOLUONG*DONGIAGIAM;
					TONGTIENHANG+=THANHTIENGIAM;
									
					String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)),
							DinhDangTraphacoERP(formatter.format(DONGIAGIAM)), DinhDangTraphacoERP(formatter.format(THANHTIENGIAM)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.BOLD)));
						
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ							
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							cells.setPaddingLeft(0.2f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									//cells.setPaddingLeft(1.1f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										cells.setPaddingRight(0.5f*CONVERT);
										//cells.setPaddingRight(0.3f*CONVERT); -- khớp với cột - sát mép
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.5f*CONVERT);
										//cells.setPaddingRight(0.3f*CONVERT); -- khớp với cột - sát mép
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.7f*CONVERT);
										//cells.setPaddingRight(0.3f*CONVERT); -- khớp với cột - sát mép
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 12-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.BOLD)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.8f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.5f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENHANG)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph(DinhDangTraphacoERP(formatter.format(TIENCK)) , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENSAUCK)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( "10" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(VAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);	
			cell.setPaddingTop(-0.4f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.4f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(-13.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	private void CreateHdPdf_HANOI(Document document, ServletOutputStream outstream, IErpHoaDonTaiChinh pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				" SELECT HD.PK_SEQ,HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, isnull(KH.TenXuatHD,'') TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI, \n" +
				"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG, \n"+
				"	 isnull(HD.DONVI,'') DONVIVL, isnull(HD.DIACHI,'') AS DIACHIVL, isnull(HD.MASOTHUE,'') MASOTHUEVL, isnull(HD.NGUOIMUAHANG,'') NGUOIMUAHANGVL, HD.KHACHHANG_FK "+
				" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";
			
			
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			String KHACHHANG_FK= "";
			String SOCHUNGTU = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOCHUNGTU = rs.getString("PK_SEQ");
					KHACHHANG_FK = rs.getString("KHACHHANG_FK");
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					if(KHACHHANG_FK.equals("107385")||KHACHHANG_FK.equals("107573")||KHACHHANG_FK.equals("107689")){//KHÁCH HÀNG VÃNG LAI// KHÁCH HÀNG KHÔNG THU TIỀN						
						MASOTHUE = rs.getString("MASOTHUEVL");
						DIACHI = rs.getString("DIACHIVL");
						NGUOIMUA = rs.getString("NGUOIMUAHANGVL");
						TENKH = rs.getString("DONVIVL");
						if(MASOTHUE.trim().length()<=0&&DIACHI.trim().length()<=0&&NGUOIMUA.trim().length()<=0&&TENKH.trim().length()<=0)
						{
							MASOTHUE = rs.getString("MASOTHUE");
							DIACHI = rs.getString("DIACHI");
							NGUOIMUA = rs.getString("NGUOIMUA");
							TENKH = rs.getString("TEN");
						}
					}
					else{
						MASOTHUE = rs.getString("MASOTHUE");
						DIACHI = rs.getString("DIACHI");
						NGUOIMUA = rs.getString("NGUOIMUA");
						TENKH = rs.getString("TEN");
					}
					TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
					HANGHOADICHVU = rs.getString("GHICHU");
					DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(0.8f*CONVERT, 1.0f*CONVERT, 0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			//SO CHUNG TU
			PdfPTable tablepkseq =new PdfPTable(1);
			tablepkseq.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			
			Paragraph hd = new Paragraph(SOCHUNGTU , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablepkseq.addCell(cell);
			document.add(tablepkseq);
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setSpacingBefore(3.3f*CONVERT);
			tableheader.setWidthPercentage(100);			

			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			hd = new Paragraph(ngayHD[2] + "                 " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.50f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(2.3f*CONVERT);
			cell.setPaddingTop(0.75f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(-0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA+" "+DIACHIGIAOHANG , new Font(bf, 12, Font.BOLD));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.setPaddingTop(-0.05f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.05f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.05f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.BOLD)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setPaddingTop(-0.05f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.BOLD)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.9f*CONVERT);
			cell.setPaddingTop(-0.05f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.8f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.0f*CONVERT,9.5f*CONVERT, 3.3f*CONVERT, 2.2f*CONVERT, 2.5f*CONVERT, 3.5f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = "	SELECT a.SANPHAM_FK, b.TEN TENSP,a.DONVITINH,a.SOLUONG, a.DONGIA, a.CHIETKHAU, a.DONGIACK \n"+
			  "	FROM ERP_HOADON_SP a INNER JOIN ERP_SANPHAM b ON a.SANPHAM_FK = b.PK_SEQ \n"+
			  " WHERE a.HOADON_FK ='"+hdId+"' \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					TENSP = rsSP.getString("TENSP");
					DONVITINH = rsSP.getString("DONVITINH");
					SOLUONG = rsSP.getDouble("SOLUONG");
					DONGIA = Math.round(rsSP.getDouble("DONGIA"));
					CHIETKHAU = rsSP.getDouble("CHIETKHAU");
					DONGIAGIAM = Math.round(rsSP.getDouble("DONGIACK"));
					THANHTIENGIAM = SOLUONG*DONGIAGIAM;
					TONGTIENHANG+=THANHTIENGIAM;
									
					String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)),
							DinhDangTraphacoERP(formatter.format(DONGIAGIAM)), DinhDangTraphacoERP(formatter.format(THANHTIENGIAM)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.BOLD)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							cells.setPaddingLeft(-0.1f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									cells.setPaddingLeft(1.2f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										//cells.setPaddingRight(0.2f*CONVERT);
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										//cells.setPaddingRight(0.5f*CONVERT);
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.6f*CONVERT);
									}
									if(j == 6 )//THÀNH TIỀN
									{
										cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.6f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 16-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 11, Font.BOLD)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
			
			for (int j = 0; j < th.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 11, Font.BOLD)));
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setFixedHeight(0.3f*CONVERT);
				cells.setBorder(0);
									
				sanpham.addCell(cells);
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(1.0f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENHANG)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph(DinhDangTraphacoERP(formatter.format(TIENCK)) , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENSAUCK)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( "10" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(VAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(-0.3f*CONVERT);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreateHdPdf_DANANG(Document document, ServletOutputStream outstream, IErpHoaDonTaiChinh pxkBean, String hdId) throws IOException
	{
		
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				" SELECT HD.PK_SEQ,HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, isnull(KH.TenXuatHD, '') TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI, \n" +
				"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG, \n"+
				"	 isnull(HD.DONVI,'') DONVIVL, isnull(HD.DIACHI,'') AS DIACHIVL, isnull(HD.MASOTHUE,'') MASOTHUEVL, isnull(HD.NGUOIMUAHANG,'') NGUOIMUAHANGVL, HD.KHACHHANG_FK "+
				" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";
			
			
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			String KHACHHANG_FK = "";
			String SOCHUNGTU = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOCHUNGTU = rs.getString("PK_SEQ");
					KHACHHANG_FK = rs.getString("KHACHHANG_FK");
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					if(KHACHHANG_FK.equals("107385")||KHACHHANG_FK.equals("107573")||KHACHHANG_FK.equals("107689")){//KHÁCH HÀNG VÃNG LAI// KHÁCH HÀNG KHÔNG THU TIỀN						
						MASOTHUE = rs.getString("MASOTHUEVL");
						DIACHI = rs.getString("DIACHIVL");
						NGUOIMUA = rs.getString("NGUOIMUAHANGVL");
						TENKH = rs.getString("DONVIVL");
						if(MASOTHUE.trim().length()<=0&&DIACHI.trim().length()<=0&&NGUOIMUA.trim().length()<=0&&TENKH.trim().length()<=0)
						{
							MASOTHUE = rs.getString("MASOTHUE");
							DIACHI = rs.getString("DIACHI");
							NGUOIMUA = rs.getString("NGUOIMUA");
							TENKH = rs.getString("TEN");
						}
					}
					else{
						MASOTHUE = rs.getString("MASOTHUE");
						DIACHI = rs.getString("DIACHI");
						NGUOIMUA = rs.getString("NGUOIMUA");
						TENKH = rs.getString("TEN");
					}
					TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
					HANGHOADICHVU = rs.getString("GHICHU");
					DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			//SO CHUNG TU
			PdfPTable tablepkseq =new PdfPTable(1);
			tablepkseq.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			
			Paragraph hd = new Paragraph(SOCHUNGTU , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablepkseq.addCell(cell);
			document.add(tablepkseq);
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setSpacingBefore(2.7f*CONVERT);
			tableheader.setWidthPercentage(100);			

			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			hd = new Paragraph(ngayHD[2] + "              " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.5f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(-0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA + " " +DIACHIGIAOHANG, new Font(bf, 12, Font.BOLD));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			//cell.setPaddingTop(-0.2f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.BOLD)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.BOLD)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.9f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.0f*CONVERT,10.0f*CONVERT, 2.3f*CONVERT, 1.7f*CONVERT, 3.2f*CONVERT, 3.2f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = "	SELECT a.SANPHAM_FK, b.TEN TENSP,a.DONVITINH,a.SOLUONG, a.DONGIA, a.CHIETKHAU, a.DONGIACK \n"+
			  "	FROM ERP_HOADON_SP a INNER JOIN ERP_SANPHAM b ON a.SANPHAM_FK = b.PK_SEQ \n"+
			  " WHERE a.HOADON_FK ='"+hdId+"' \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					TENSP = rsSP.getString("TENSP");
					DONVITINH = rsSP.getString("DONVITINH");
					SOLUONG = rsSP.getDouble("SOLUONG");
					DONGIA = Math.round(rsSP.getDouble("DONGIA"));
					CHIETKHAU = rsSP.getDouble("CHIETKHAU");
					DONGIAGIAM = Math.round(rsSP.getDouble("DONGIACK"));
					THANHTIENGIAM = SOLUONG*DONGIAGIAM;
					TONGTIENHANG+=THANHTIENGIAM;
									
					String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)),
							DinhDangTraphacoERP(formatter.format(DONGIAGIAM)), DinhDangTraphacoERP(formatter.format(THANHTIENGIAM)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.BOLD)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									cells.setPaddingLeft(0.5f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										//cells.setPaddingRight(0.1f*CONVERT);
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.4f*CONVERT);
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.9f*CONVERT);
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.BOLD)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.8f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			//footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENHANG)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph(DinhDangTraphacoERP(formatter.format(TIENCK)) , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENSAUCK)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( "10" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.4f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(VAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.4f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);			
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.6f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    //paradoctien.setSpacingBefore(3.0f);
			 paradoctien.setSpacingBefore(-15.0f);		
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
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



