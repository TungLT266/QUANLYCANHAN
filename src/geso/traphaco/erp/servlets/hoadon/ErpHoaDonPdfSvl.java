package geso.traphaco.erp.servlets.hoadon;

import geso.traphaco.center.beans.doctien.DocTien;
 
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
 
import geso.traphaco.erp.beans.hoadon.IErpHoaDon;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoaDon;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;
 
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


public class ErpHoaDonPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final float CM = 28.3464f;

	public ErpHoaDonPdfSvl() {
		super();
	}
	 float CONVERT = 28.346457f; 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IErpHoaDon obj;
		String userId;
		Utility util = new Utility();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String ddhId = util.antiSQLInspection(request.getParameter("ddhId"));
		if (ddhId == null)
			ddhId = "";
		
		// HD tai chinh : 0 ; hoa don tra hang ncc :1
		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if (loaihd == null)
			loaihd = "";
		
		String inHd = util.antiSQLInspection(request.getParameter("inHd"));
		if (inHd == null)
			inHd = "";

		obj = new ErpHoaDon();
		obj.setUserId(userId);
		if(inHd.trim().length() <= 0)
		{
			obj.init(ddhId, true);
		}
		else
		{
			obj.initLSIN(inHd);
		}
		
		System.out.println("Print");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=HoaDonTraVeNCC.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();			
		
		this.CreateHdPdf(document, outstream,obj, ddhId);
		
		document.close();

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	private void CreateHdPdf(Document document, ServletOutputStream outstream, IErpHoaDon pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				" SELECT HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MASOTHUE, KH.DIACHI, HD.NGUOIMUA,\n"+ 
				"        KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI,  HD.VAT, HD.TONGTIENAVAT \n"+ 
				" FROM ERP_HOADON HD INNER JOIN ERP_NHACUNGCAP KH ON KH.PK_SEQ=HD.NCC_FK \n"+ 
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";
			
			System.out.println(query);
			
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
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("MASOTHUE");
					DIACHI = rs.getString("DIACHI");
					NGUOIMUA = rs.getString("NGUOIMUA");
					TENKH = rs.getString("TEN");
					//TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.0f * CONVERT);//CÁCH LỀ TRÊN
			cell.setPaddingLeft(3.0f * CONVERT);//CÁCH LỀ TRÁI
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			Paragraph hd = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.setSpacingAfter(2);
			cell.addElement(hd);

			tableheader.addCell(cell);
			document.add(tableheader);
			
			//THÔNG TIN KHÁCH HÀNG
			
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			
			
			// DONG 1-- NGUOI MUA HANG
			PdfPCell cell_nguoimua = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_nguoimua.addElement(hd);
			cell_nguoimua.setPaddingLeft(5.0f*CONVERT);
			cell_nguoimua.setBorder(0);	
			
			table1.addCell(cell_nguoimua);	
			
			document.add(table1);
			
			
			
			PdfPTable table2 =new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table2.setWidths(withs2);
									
			//DONG 2 -- DON VI
			
			PdfPCell cell_donvi = new PdfPCell();	
			hd = new Paragraph( TENKH , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_donvi.addElement(hd);
			cell_donvi.setPaddingLeft(2.0f*CONVERT);
			cell_donvi.setBorder(0);	
			
			table2.addCell(cell_donvi);	
			
			document.add(table2);
			
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
			
			//DONG 3 -- DIA CHI
			
			PdfPCell cell_diachi = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_diachi.addElement(hd);
			cell_diachi.setPaddingLeft(2.0f*CONVERT);
			cell_diachi.setBorder(0);	
			
			table3.addCell(cell_diachi);	

			document.add(table3);
			
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {50f,100f};
			table4.setWidths(withs4);
			
			//DONG 4 -- HINH THUC THANH TOAN
			
			PdfPCell cell_hinhthuctt = new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(4);
			cell_hinhthuctt.addElement(hd);
			cell_hinhthuctt.setPaddingLeft(2.0f*CONVERT);
			cell_hinhthuctt.setBorder(0);
			
			table4.addCell(cell_hinhthuctt);	
			
			PdfPCell cell_masothue = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(2);
			cell_masothue.addElement(hd);
			cell_masothue.setPaddingLeft(2.0f*CONVERT);
			cell_masothue.setBorder(0);	
			
			table4.addCell(cell_masothue);
			
			document.add(table4);
			
			//LẤY THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(95);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(2.0f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 2.0f, 20.0f, 7.0f, 7.0f, 10.0f, 9.0f, 14.0f };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();
			
			
			String INIT_SANPHAM = "	SELECT a.SANPHAM_FK, b.TEN TENSP,a.DONVITINH,a.SOLUONG, a.DONGIA, a.CHIETKHAU \n"+
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
					DONGIAGIAM = Math.round(DONGIA - DONGIA*CHIETKHAU/100);
					THANHTIENGIAM = SOLUONG*DONGIAGIAM;
					TONGTIENHANG+=THANHTIENGIAM;
									
					String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)),
												  DinhDangTraphacoERP(formatter.format(DONGIAGIAM)), DinhDangTraphacoERP(formatter.format(THANHTIENGIAM)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								}
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						cells.setFixedHeight(0.6f * CONVERT);
						//cells.setPaddingTop(2.5f);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 9-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
				
			
			document.add(sanpham);
						
			
			PdfPTable table5 =new PdfPTable(1);
			table5.setWidthPercentage(100);
			float[] withs5 = {100f};
			table5.setWidths(withs5);
						
			//DONG 4 -- TIỀN HÀNG
			
			PdfPCell cell_tienhang = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TONGTIENHANG)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(4);
			cell_tienhang.addElement(hd);
			cell_tienhang.setPaddingLeft(2.0f*CONVERT);
			cell_tienhang.setBorder(0);
			
			table5.addCell(cell_tienhang);	
			
			document.add(table5);		
			
			
			
			PdfPTable table6 =new PdfPTable(2);
			table6.setWidthPercentage(100);
			float[] withs6 = {665f,120f};
			table6.setWidths(withs6);
			
			//DONG 5 -- SOTIENCHIETKHAU			
						
			if(TIENCK==0){
				PdfPCell cell_tienck = new PdfPCell();	
				hd = new Paragraph( "" , new Font(bf, 10, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_RIGHT);
				hd.setSpacingAfter(2);
				cell_tienck.addElement(hd);
				cell_tienck.setPaddingLeft(2.0f*CONVERT);
				cell_tienck.setBorder(0);
				
				table6.addCell(cell_tienck);
			}
			else{
				PdfPCell cell_tienck = new PdfPCell();	
				hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENCK)) , new Font(bf, 10, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_RIGHT);
				hd.setSpacingAfter(2);
				cell_tienck.addElement(hd);
				cell_tienck.setPaddingLeft(2.0f*CONVERT);
				cell_tienck.setBorder(0);
				
				table6.addCell(cell_tienck);
			}
			
			//DONG 5 -- TIEN DA TRU CHIET KHAU
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			PdfPCell cell_tiensauck = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(TIENSAUCK)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(2);
			cell_tiensauck.addElement(hd);
			cell_tiensauck.setPaddingLeft(2.0f*CONVERT);
			cell_tiensauck.setBorder(0);	
			
			table6.addCell(cell_tiensauck);
			
			document.add(table6);
			
			
			
			PdfPTable table7 =new PdfPTable(2);
			table7.setWidthPercentage(100);
			float[] withs7 = {650f,120f};
			table7.setWidths(withs7);
			
			//THUẾ VAT
			PdfPCell cell_vat = new PdfPCell();	
			hd = new Paragraph( "10%" , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(2);
			cell_vat.addElement(hd);
			cell_vat.setPaddingLeft(2.0f*CONVERT);
			cell_vat.setBorder(0);
			
			table7.addCell(cell_vat);
			
			
			//TIỀN THUẾ VAT
		
			PdfPCell cell_tienthue = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter.format(VAT)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(2);
			cell_tienthue.addElement(hd);
			cell_tienthue.setPaddingLeft(2.0f*CONVERT);
			cell_tienthue.setBorder(0);
			
			table7.addCell(cell_tienthue);
			
			document.add(table7);
			
			
			
			PdfPTable table8 =new PdfPTable(1);
			table8.setWidthPercentage(100);
			float[] withs8 = {100f};
			table5.setWidths(withs8);
			
			//DONG 4 -- TỔNG TIỀN THANH TOÁN
			
			
			PdfPCell cell_sumthanhtoan = new PdfPCell();	
			hd = new Paragraph( DinhDangTraphacoERP(formatter1.format(TONGTIENAVAT)) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			hd.setSpacingAfter(4);
			cell_sumthanhtoan.addElement(hd);
			cell_sumthanhtoan.setPaddingLeft(2.0f*CONVERT);
			cell_sumthanhtoan.setBorder(0);
			
			table8.addCell(cell_sumthanhtoan);	
			
			document.add(table8);
			
			
			//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
					    //String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("            " + tien, new Font(bf, 10, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(12.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
			
/*			// Tien bang chu
			doctien doc = new doctienrachu();
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			String tien = doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",", "")));
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}*/
			
			
			/*PdfPTable table7 =new PdfPTable(2);
			table7.setWidthPercentage(100);
			float[] withs7 = {100f,100f};
			table7.setWidths(withs7);
			
			//DONG 6 -- THUE SUAT VAT
			
			PdfPCell cell_thuevat = new PdfPCell();	
			hd = new Paragraph( "10%" , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(2);
			cell_thuevat.addElement(hd);
			cell_thuevat.setPaddingLeft(2.0f*CONVERT);
			cell_thuevat.setBorder(0);
			
			table7.addCell(cell_tienck);	
			
			//DONG 5 -- TIEN DA TRU CHIET KHAU
			TIENSAUCK = TONGTIENHANG - TIENCK;
			
			PdfPCell cell_sauck = new PdfPCell();	
			hd = new Paragraph( formatter.format(TIENSAUCK) , new Font(bf, 10, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(2);
			cell_sauck.addElement(hd);
			cell_sauck.setPaddingLeft(2.0f*CONVERT);
			cell_sauck.setBorder(0);	
			
			table7.addCell(cell_sauck);
			
			document.add(table7);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Xử lý để in sản phẩm trong phần in hóa đơn trong nước
	 * @param hdBean
	 * @param _tenList
	 * @param sanpham
	 * @param spIndex
	 * @param tenSp
	 * @param qcSp
	 * @param sokytu1sp
	 * @param prev_tensp
	 * @param temp_tensp
	 * @param changeSpCore
	 * @return
	 */
	
	protected boolean xuLyTenList(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 ||  sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >=0  ||  sanpham.getMaSanPham().trim().equals("OE"));
		//System.out.println("xuLyTenList .isSpCone : "+sanpham.getMaSanPham());
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		if(!isSpCone || changeSpCore) {
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			//Insert quy cách group nếu là CONE
			if(isSpCone) {
				_tenList.add(sanpham.getQuyCachGroup());
			}
			
		} else {
			changeSpCore = false;
		}
		String sanpham_ghichu = hdBean.getSanphamGhiChu().get(sanpham.getIdSanPham());
				
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;
			
			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}
		


		// Xu ly quy cach
		int countSoDongQuyCach = 0;
		if(sanpham.getDvkdId().equals("100004")){
			// nối cái ghi chú liên tiếp vào,nếu là ống cone
			String ghichu_=(arrGhiChu.length >0?arrGhiChu[0]:"");
			qcSp=qcSp.trim()  +(ghichu_.trim().length() >0?": " +ghichu_:"");
		}
		words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xử lý khi 1 từ > 40 ký tự
			if (words[_i].length() > sokytu1sp) {
				if (_ten.trim().length() > 0) {
					_tenList.add(_ten); // Thêm dòng cũ
					countSoDongQuyCach++;
					
				}
				_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
				countSoDongQuyCach++;
				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_tenList.add(_ten);
					countSoDongQuyCach++;
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_tenList.add(_ten);
			countSoDongQuyCach++;
		}
		if(countSoDongQuyCach == 0 && !sanpham.getDvkdId().equals("100005")) {
			_tenList.add("Không màu");
		}

		
		for (int _j = (isSpCone || sanpham.getDvkdId().equals("100004")?1:0); _j < arrGhiChu.length; _j++) {
			
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > 45 ký tự
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return changeSpCore;
	}
	
	protected boolean xuLyTenList_NuocNgoai(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 ||  sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0 ||  sanpham.getMaSanPham().trim().equals("OE"));

		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		if(!isSpCone || changeSpCore) {
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			//Insert quy cách group nếu là CONE
			if(isSpCone) {
				_tenList.add(sanpham.getQuyCachGroup());
			}
			
		} else {
			changeSpCore = false;
		}
		// int soDongDanhChoTenSanPham = _tenList.size();

		// Xu ly quy cach
		int countSoDongQuyCach = 0;
		words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xử lý khi 1 từ > 40 ký tự
			if (words[_i].length() > sokytu1sp) {
				if (_ten.trim().length() > 0) {
					_tenList.add(_ten); // Thêm dòng cũ
					countSoDongQuyCach++;
					
				}
				_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
				countSoDongQuyCach++;
				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_tenList.add(_ten);
					countSoDongQuyCach++;
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_tenList.add(_ten);
			countSoDongQuyCach++;
		}
		if(countSoDongQuyCach == 0 && !sanpham.getDvkdId().equals("100005")) {
			_tenList.add("Không màu");
		}

		
		return changeSpCore;
	}
	
	protected boolean xuLyGhiChuList(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int sokytu1sp, boolean changeSpCore) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";

		String sanpham_ghichu = hdBean.getSanphamGhiChu().get(sanpham.getIdSanPham());
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;
			
			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}

		for (int _j = 0; _j < arrGhiChu.length; _j++) {
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > 45 ký tự
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return changeSpCore;
	}
	
	protected boolean xuLyGhiChu_HoaDon(IErpHoaDon hdBean, List<String> _ghichuList, String ghichu, int sokytu1sp, boolean ktra) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";

		words = ghichu.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xử lý khi 1 từ > 40 ký tự
			if (words[_i].length() > sokytu1sp)
			{
				if (_ten.trim().length() > 0) {
					_ghichuList.add(_ten); // Thêm dòng cũ
					
				}
				_ghichuList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới

				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_ghichuList.add(_ten);						
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_ghichuList.add(_ten);
			
		}
		
		return ktra;
	}
	
	
	/**
	 * Xử lý để in sản phẩm trong phần in hóa đơn nước ngoài
	 * @param hdBean
	 * @param _tenList
	 * @param sanpham
	 * @param spIndex
	 * @param tenSp
	 * @param qcSp
	 * @param sokytu1sp
	 * @param prev_tensp
	 * @param temp_tensp
	 * @param changeSpCore
	 * @return
	 */
	protected boolean xuLyTenNNList(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 || sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0 ||  sanpham.getMaSanPham().trim().equals("OE"));

		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		if(!isSpCone || changeSpCore) {
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
		} else {
			changeSpCore = false;
		}
		// int soDongDanhChoTenSanPham = _tenList.size();

		// Xu ly quy cach: cone ko cần hiện
		if(!isSpCone) {
			words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > 40 ký tự
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) {
						_tenList.add(_ten); // Thêm dòng cũ
						
					}
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
		}

		String sanpham_ghichu = hdBean.getSanphamGhiChu().get(sanpham.getIdSanPham());
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;

			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}

		for (int _j = 0; _j < arrGhiChu.length; _j++) {
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > 45 ký tự
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return changeSpCore;
	}

	/**
	 * In hóa đơn trong nước excel
	 * @param out
	 * @param hdBean
	 */
	private void HoaDonTrongNuocExcel(OutputStream out, IErpHoaDon hdBean) {/*

		NumberFormat formatter = new DecimalFormat("###,###,###,###,###.###");

		int TABLE_NUM_ROWS = hdBean.getSoDongSanPham(); // 6
		int S2_START_INDEX = 20;

		try {
			dbutils db = new dbutils();
			String sql = "";
			if(hdBean.getNppId().trim().length() > 0)
			{
				sql =	"select a.*, '' as GHICHU from Erp_KhachHang a where a.pk_seq = '" + hdBean.getNppId() + "'";
			}else
			{
				sql =	" select ten, diachi, masothue as MST, Ten_Nguoilienhe as NguoiLienhe," +
						" (select ISNULL(GHICHU,'') AS GHICHU " +
						" from ERP_MUAHANG" +
						" where PK_SEQ = (select DDH_FK from ERP_HOADON_DDH where HOADON_FK= "+ hdBean.getId() +" ) ) AS GHICHU " +
						" from Erp_Nhacungcap" +
						" where pk_seq = '" + hdBean.getNccId() + "'";
			}
			//System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf....] sql = " + sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";
			String ghichu_donTH = "";
			
			try {
				ResultSet rs = db.get(sql);
				if (rs.next()) {
					if (rs.getString("MST") != null) taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null) name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
					ghichu_donTH = rs.getString("GHICHU");
				}
				rs.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String ghichu = hdBean.getGhiChu();
			if (ghichu == null || ghichu.trim().length() == 0) {
				ResultSet rs_hopdong = hdBean.getHopdongRs();
				if (rs_hopdong != null) {
					try {
						while (rs_hopdong.next()) {
							if (rs_hopdong.getString("pk_seq").trim().equals(hdBean.getHopdongId())) {
								ghichu = rs_hopdong.getString("mahopdong");
							}
						}
						rs_hopdong.close();
					} catch (Exception e) {
					}
				}

				if (ghichu == null || ghichu.trim().length() == 0) {
					ghichu = hdBean.gethinhthuctt();
				}

				if (ghichu == null)
					ghichu = " ";
			}

			String thue = "" + hdBean.getVAT();
			//System.out.println("Thue la: " + thue);
 

			FileInputStream fstream;
			Cell cell = null;

			fstream = new FileInputStream(getServletContext().getInitParameter( "path") + "\\HoaDonTaiChinhTrongNuoc.xlsm");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			workbook.open(fstream);

			Worksheets worksheets = workbook.getWorksheets();

			// Sheet 1
			Worksheet worksheet1 = worksheets.getSheet("HOA DON CANH GIUA");
			Cells cells1 = worksheet1.getCells();

				Cell cell_style_10 = cells1.getCell("AB1");
			   Style style10;
			   style10 = cell_style_10.getStyle();
 
			   Cell cell_style_11 = cells1.getCell("AC1");
			   Style style11;
			   style11 = cell_style_11.getStyle();
  
			   Cell cell_style_12 = cells1.getCell("AD1");
			   Style style12;
			   style12 = cell_style_12.getStyle();
			   
			   Cell cell_style_13 = cells1.getCell("AE1");
			   Style style13;
			   style13 = cell_style_13.getStyle();
				
			cell = cells1.getCell("E12");
			cell.setValue(hdBean.getNguoiMuaHang()); // Người mua hàng
			   
			cell = cells1.getCell("E13");
			if(name_of_company.length()<63){
				cell.setStyle(style13);
			}else if(name_of_company.length()<71){
				cell.setStyle(style12);
			}else if(name_of_company.length()<81){
				cell.setStyle(style11);
			}else if(name_of_company.length()<150){
				cell.setStyle(style10);
			}
			cell.setValue(name_of_company); // Họ tên khách hàng
			
			cell = cells1.getCell("E14");
			if(address.length()<63){
				cell.setStyle(style13);
			}else if(address.length()<71){
				cell.setStyle(style12);
			}else if(address.length()<81){
				cell.setStyle(style11);
			}else if(address.length()<94){
				cell.setStyle(style10);
			}
			
			cell.setValue(address); // Địa chỉ
			
			cell = cells1.getCell("E15");
			cell.setValue(taxCode); // Mã số thuế
			cell = cells1.getCell("E16");
			
			if(hdBean.getNppId().trim().length() > 0) 
			{
				sql=" SELECT MAHOPDONG  FROM ERP_HOPDONG WHERE PK_SEQ IN "+
					" ( "+
					" SELECT HOPDONG_FK FROM ERP_DONDATHANG WHERE PK_SEQ IN "+ 
					" (   "+
					" SELECT A.DDH_FK FROM ERP_HOADON_DDH A WHERE A.HOADON_FK= "+hdBean.getId() +  
					" UNION "+
					" SELECT DONDATHANG_FK FROM ERP_XUATKHO XK INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.xuatkho_fk=XK.PK_SEQ "+
					" WHERE HDXK.hoadon_fk="+hdBean.getId()+" ))";
				 
				ResultSet rs=db.get(sql);
				String mahopdong="";
				if(rs.next()){
					mahopdong=rs.getString("MAHOPDONG");
				}
				rs.close();			
				if(mahopdong.length()>0) {
					cell.setValue("Theo HĐ: "+mahopdong);
				}else{
					cell.setValue(hdBean.gethinhthuctt()); // Hình thức thanh toán
				}
			}else // Đơn trả hàng NCC
			{
				cell.setValue("CK/TM"); // Hình thức thanh toán
			}

			// Sản phẩm
			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;
			double total_amount = 0;
			
			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0)
				sokytu1sp = 40;

			String prev_tensp = "", temp_tensp = "";
			boolean changeSpCore = false;
			int stt = 0;
			String tensp_bk="";
	
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) 
			{
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);
				temp_tensp = sanpham.getMaSanPham() + sanpham.getQuyCachGroup() + sanpham.getDonGia();

				double thanhtien = 0;
				try {
					thanhtien = Math.round(sanpham.getDonGia() * sanpham.getSoLuong());
					total_amount += sanpham.getDonGia() * sanpham.getSoLuong();
				} catch (Exception ex) { }
				
				//System.out.println("[ErpHoaDonPdf...] sanpham.getTenXuatHoaDon() = " + sanpham.getTenXuatHoaDon());

				// TÊN XUẤT HÓA ĐƠN } QUY CÁCH
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0].trim();
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words;
				String _ten = "", _ten2 = "";
				
				boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 || sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0  ||  sanpham.getMaSanPham().trim().equals("OE") ) ;

				 
				changeSpCore = isSpCone && !prev_tensp.equals(temp_tensp);
				prev_tensp = temp_tensp;
				  
				//System.out.println("hdBean.getDvkdId(): " +hdBean.getDvkdId());
				//Tạo _tenList
				xuLyTenList(hdBean, _tenList, sanpham, spIndex, tenSp, qcSp, sokytu1sp, prev_tensp, temp_tensp, changeSpCore);
				System.out.println("isSpCone : "+isSpCone);
				if (isSpCone) {
					// IN SẢN PHẨM CONE
					int beginIndex = 0;
					if (changeSpCore) {
						//Tìm thông tin sp cone trong listsanphamCone
						double sl = sanpham.getSoLuong();
						String key = sanpham.getMaSanPham() + sanpham.getQuyCachGroup();
						IErpHoaDon_SP _spTemp;
						for(int z = 0; z < hdBean.GetListSanPhamCone().size(); z++) {
							_spTemp =  hdBean.GetListSanPhamCone().get(z);
							if(_spTemp != null && _spTemp.getIdSanPham().equals(key)) {
								sl = _spTemp.getSoLuong();
							}
						}
						double _thanhtien = sl * sanpham.getDonGia(); 
						
						stt++;
						// Cột stt sản phẩm
						cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
						cell.setValue("" + (stt));
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
						// Cột Đơn vị tính
						cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonViInAn());
						// Cột Số lượng
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(sl)));
						// Cột Đơn giá
						cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(sanpham.getDonGia())));
						// Cột Thành tiền
						cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(_thanhtien)));
						rowIndex++;
						beginIndex++;

						// In cột quy cách
						if(_tenList.size() >= beginIndex) {
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList.get(beginIndex).trim());
							rowIndex++;
							beginIndex++;
						}
					}
					
					// In mẫu in + màu
					if(_tenList.size() >   beginIndex) {
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue("-" + _tenList.get(beginIndex));
						rowIndex++;
						beginIndex++;
					}

					// In cac dong con lai
					for (int z = beginIndex; z < _tenList.size(); z++) {
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue("   " + _tenList.get(z));
						// Cột Đơn vị tính
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						rowIndex++;
					}
					
				}else if(sanpham.getDvkdId().trim().equals("100004")){
					// các loại ống giấy còn lại
					//***************************************ỐNG LÕI KHÁC ỐNG CONE*************************************************
					stt++;	
					
					System.out.println("Đã vào đây2");
					ttsp = sanpham.getTenXuatHoaDon().split("}");

					String  tenSp1 = ttsp[0].trim();
					 
				 
						if(!tensp_bk.equals(tenSp1)){
							
							tensp_bk=tenSp1;
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(tenSp1);
							rowIndex++;
						}
						 String qcstr=ttsp.length > 1 ? ttsp[1].trim() : "";
						// In dong 1
						// Cột stt sản phẩm
						cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
						
						//System.out.println(S2_START_INDEX + rowIndex);
						cell.setValue("" + (stt));
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.size() > 1 ? _tenList.get(1) : "");
						// Cột Đơn vị tính
						cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonViInAn());
						// Cột Số lượng
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(sanpham.getSoLuong())));
						// Cột Đơn giá
						cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(sanpham.getDonGia())));
						// Cột Thành tiền
						cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(thanhtien)));
						rowIndex++;
						
						
					//**********************************************************************************************
				}
				else {
				
					// CÁC SẢN PHẨM CÒN LẠI
					stt++;

					// In dong 1
					// Cột stt sản phẩm
					cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
					
					//System.out.println(S2_START_INDEX + rowIndex);
					cell.setValue("" + (stt));
					// Cột tên sản phẩm
					cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					// Cột Đơn vị tính
					cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonViInAn());
					// Cột Số lượng
					cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(sanpham.getSoLuong())));
					// Cột Đơn giá
					cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(sanpham.getDonGia())));
					// Cột Thành tiền
					cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(thanhtien)));

					int bien=1;
						
					
					if(sanpham.getDvkdId().equals("100000")){
						//nếu không phải sản phẩm mới thì mới làm
							rowIndex++;
							
							String qd = sanpham.getQuyDoiStr();
							if (qd == null)
								qd = "";
							qd = qd.trim();
							if (qd.trim().length() > 0) {
								qd = "(" + qd + ")";
							}
							
							// In dong 2
							// Cột tên sản phẩm
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList.size() > 1 ? _tenList.get(1) : " ");
							// Cột Đơn vị tính
							cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
							
							
							Cell cell_style10 = cells1.getCell("AA1");
							   Style style;
							   style = cell_style10.getStyle();
							   
							    Font font = new Font();
							   // font.setName("Times New Roman");
							    font.setColor();//mau chu
							    font.setSize(10);// size chu
							   	font.setBold(true);
								cell.setValue(qd);
								cell.setStyle(style);
								if(_tenList.size() > 1){
									bien++;
								}
							 
							// Cột Số lượng
							cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Đơn giá
							cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
					}
					rowIndex++;

					// In cac dong ghi chu con lai
					for (int z = bien; z < _tenList.size(); z++) {
						
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.get(z));
						// Cột Đơn vị tính
						cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");

						rowIndex++;
					}
				}

				// NẾU LÀ HÓA ĐƠN TRẢ HÀNG : IN THÊM GHI CHÚ CỦA ĐƠN TRẢ HÀNG
				   boolean ktra = false;
				   if(hdBean.getNccId().trim().length() > 0)
					   ktra = true;
				   
				   List<String> _ghichuList = new ArrayList<String>();	
				   
				   xuLyGhiChu_HoaDon(hdBean, _ghichuList, ghichu_donTH, sokytu1sp, ktra) ;
				   if(ktra)
				   {				   
					   
					// In cac dong con lai
						 for (int z = 0; z < _ghichuList.size(); z++) {
							// Cột tên sản phẩm
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue("   " + _ghichuList.get(z));
							// Cột Đơn vị tính
							cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Số lượng
							cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Đơn giá
							cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							rowIndex++;
						}
				   }
				//END
				
				spIndex++;
			}
			if(hdBean.getSoPO().trim().length()>0){
				ArrayList<String>	_tenList = new ArrayList<String>();
				this.GetListStrDaXuLy(_tenList,hdBean.getSoPO().trim(),44) ;
 
				for (int z = 0; z < _tenList.size(); z++) {
					
					// Cột tên sản phẩm
					cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
					if(z==0){
						cell.setValue("Theo PO: "+_tenList.get(z));
					}else{
						cell.setValue(_tenList.get(z));
					}
					// Cột Đơn vị tính
					cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Số lượng
					cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Đơn giá
					cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
					cell.setValue("");

					rowIndex++;
				}
				
			}
			
			double chietkhau = 0;
			String chietkhau_ = "";
		
			if(hdBean.getNppId().trim().length() > 0)
			{
			// Lấy ra % chiết khấu
				sql =   " SELECT ddh.CHIETKHAU, 0 AS CHIETKHAU " +
						" FROM ERP_DONDATHANG ddh " +
						" WHERE ddh.PK_SEQ IN ( " +
						(hdBean.getHinhthucxuat().equals("0") 
							? " SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + hdBean.getId() + " "
							: " SELECT TOP 1 DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ( SELECT TOP 1 XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " + hdBean.getId() + " ) "
						) +
						" ) ";
				System.out.println("Lay % CK "+sql);
				ResultSet rsLayCK = db.get(sql);
	
				if(rsLayCK != null)
				{
					while(rsLayCK.next())
					{
						chietkhau = rsLayCK.getDouble("CHIETKHAU");
					}
					rsLayCK.close();
					db.shutDown();
				}	
			}
			else
			{
				chietkhau = hdBean.getChietkhau();
			}
			
			if(chietkhau > 0)
			{
				chietkhau_ = formatter.format(chietkhau) + "%";
			}
			
			if (hdBean.getTienChietKhau() > 0) {
				
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Chiết khấu hoa hồng " + chietkhau_ );
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(hdBean.getTienChietKhau())));
				rowIndex++;
			}

			if (hdBean.getTienkhuyenmai() > 0) {
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Tiền giảm giá ");
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(hdBean.getTienkhuyenmai())));
				rowIndex++;
			}

			if (hdBean.getTienVanChuyen() > 0) {
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Tiền vận chuyển ");
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(hdBean
						.getTienVanChuyen())));
				rowIndex++;
			}

			double tienCK = hdBean.getTienChietKhau() > 0 ? hdBean.getTienChietKhau() : total_amount * hdBean.getChietkhau() / 100;
			double tienKM = hdBean.getTienkhuyenmai();
			double tienBH = hdBean.getTienBaoHiem();
			double tienVC = hdBean.getTienVanChuyen();
			double tienSauCKKM = total_amount - tienCK - tienKM + tienVC;
			double vat = Math.round(hdBean.getVAT());
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT))
					+ "./.";
			String ngaythangnam = hdBean.getNgayxuathd();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);
		 

			cell = cells1.getCell("D42");
			cell.setValue(formatVN(formatter.format(vat)));
			
			NumberFormat formatter_vnd = new DecimalFormat("###,###,###,###,###");
			if(hdBean.getTienteId().equals("100000")){
			
				cell = cells1.getCell("L41");
				cell.setValue(formatVN(formatter_vnd.format(tienSauCKKM)));
				cell = cells1.getCell("L42");
				cell.setValue(formatVN(formatter_vnd.format(tienVAT)));
				cell = cells1.getCell("L43");
				cell.setValue(formatVN(formatter_vnd.format(tienSauVAT)));
				cell = cells1.getCell("E45");
			}else{
				cell = cells1.getCell("L41");
				cell.setValue(formatVN(formatter.format(tienSauCKKM)));
				cell = cells1.getCell("L42");
				cell.setValue(formatVN(formatter.format(tienVAT)));
				cell = cells1.getCell("L43");
				cell.setValue(formatVN(formatter.format(tienSauVAT)));
				cell = cells1.getCell("E45");
			}
			cell.setValue(tienBangChu);
			cell = cells1.getCell("K48");
			cell.setValue(ngay);
			cell = cells1.getCell("M48");
			cell.setValue(thang);
			cell = cells1.getCell("N48");
			cell.setValue(nam);

			workbook.save(out);
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	*/}
	protected boolean GetListStrDaXuLy(List<String> _tenList ,String tenSp,  int sokytu1sp) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			return true;
	}
/*	
	private void HoaDonTrongNuocPdf(Document document,
			ServletOutputStream outstream, IErpHoaDon hdBean)
			throws IOException {
		try {
			
			 * IErpCauHinhInHoaDon soNumber = new ErpCauHinhInHoaDon();
			 * soNumber.initWithName("SO");
			 

			IErpCauHinhInHoaDon khachhang_config = new ErpCauHinhInHoaDon();
			khachhang_config.initWithName("CUSTOMMER");
			khachhang_config.dbClose();

			IErpCauHinhInHoaDon dh_config = new ErpCauHinhInHoaDon();
			dh_config.initWithName("DETAILS");
			dh_config.dbClose();

			IErpCauHinhInHoaDon footer_config = new ErpCauHinhInHoaDon();
			footer_config.initWithName("FOOTER");
			footer_config.dbClose();

			// Kích thước theo đơn vị cm
			float CONVERT = 28.346457f; // = 1cm
			// Header
			float[] TABLE_HEADER_WIDTHS = { 18.0f * CONVERT };
			float TABLE_HEADER_LEFT = khachhang_config.getMarginLeft() * CONVERT, TABLE_HEADER_TOP = khachhang_config
					.getMarginTop() * CONVERT, TABLE_HEADER_BOTTOM = khachhang_config
					.getMarginBottom() * CONVERT;
			// float TABLE_HEADER_LEFT = 7.0f*CONVERT, TABLE_HEADER_TOP =
			// 0.0f*CONVERT, TABLE_HEADER_BOTTOM = 1.6f*CONVERT;
			int BORDER_WIDTH = Rectangle.NO_BORDER;

			// Products
			int TABLE_NUM_ROWS = 16;// dh_config.getNumberOfRow(); //6
			float TABLE_LEFT = 0.0f * CONVERT, TABLE_RIGHT = 0.0f * CONVERT, TABLE_TOP = 0.0f * CONVERT, TABLE_BOTTOM = 0.0f * CONVERT;
			float TABLE_HEIGHT = dh_config.getTableHeight() * CONVERT, TABLE_ROW_HEIGHT = TABLE_HEIGHT
					/ TABLE_NUM_ROWS;
			// float TABLE_HEIGHT = 9.0f*CONVERT, TABLE_ROW_HEIGHT =
			// TABLE_HEIGHT/TABLE_NUM_ROWS;
			float[] TABLE_COLUMN_WIDTHS = { dh_config.getNoColumn() * CONVERT,
					dh_config.getProductColumn() * CONVERT,
					dh_config.getUnitColumn() * CONVERT,
					dh_config.getQuantityColumn() * CONVERT,
					dh_config.getUniPriceColumn() * CONVERT,
					dh_config.getAmoutColumn() * CONVERT };
			// float[] TABLE_COLUMN_WIDTHS = {1.0f*CONVERT, 6.7f*CONVERT,
			// 2.3f*CONVERT, 2.1f*CONVERT, 2.1f*CONVERT, 3.8f*CONVERT};
			float[] TABLE_COLUMN_PADDING_LEFTS = { 0.1f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT };
			int[] TABLE_COLUMN_ALIGNS = { Element.ALIGN_LEFT,
					Element.ALIGN_LEFT, Element.ALIGN_CENTER,
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
					Element.ALIGN_RIGHT };

			// Amount
			float TABLE_AMOUNT_LEFT_WIDTH = 7.0f * CONVERT;
			float TABLE_AMOUNT_RIGHT_WIDTH = 11.0f * CONVERT;
			float[] TABLE_AMOUNT_ROW_HEIGHTS = {
					footer_config.getMarginLeft() * CONVERT,
					footer_config.getMarginRight() * CONVERT,
					footer_config.getMarginTop() * CONVERT,
					footer_config.getMarginBottom() * CONVERT,
					footer_config.getPaddingLeft() * CONVERT };
			float[] TABLE_AMOUNT_LEFT_PADDING_LEFTS = { 0.0f * CONVERT,
					4.8f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT };
			float[] TABLE_AMOUNT_RIGHT_PADDING_RIGHTS = { 0.5f * CONVERT,
					0.5f * CONVERT, 0.5f * CONVERT, 0.0f * CONVERT,
					0.0f * CONVERT };

			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
			NumberFormat formatter3 = new DecimalFormat("#,###,###");
			PdfWriter.getInstance(document, outstream);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font headerFont = new Font(bf, khachhang_config.getFontSize(),
					Font.BOLD);
			Font donhangFont = new Font(bf, 11, Font.NORMAL);
			Font productFont = new Font(bf, dh_config.getLineSpacing(),
					Font.NORMAL);
			// Font font10bold = new Font(bf, 10, Font.BOLD);

			
			 * Font font = new Font(bf, 13, Font.BOLD); Font font2 = new
			 * Font(bf, 8, Font.BOLD); Font font9bold = new Font(bf, 9,
			 * Font.BOLD); Font font11bold = new Font(bf, 11, Font.BOLD); Font
			 * font12bold = new Font(bf, 12, Font.BOLD); Font font13bold = new
			 * Font(bf, 13, Font.BOLD); Font font14bold = new Font(bf, 14,
			 * Font.BOLD); Font font15bold = new Font(bf, 15, Font.BOLD); Font
			 * font16bold = new Font(bf, 16, Font.BOLD); Font font9 = new
			 * Font(bf, 9, Font.NORMAL); Font font10 = new Font(bf, 10,
			 * Font.NORMAL); Font font11 = new Font(bf, 11, Font.NORMAL); Font
			 * font12 = new Font(bf, 12, Font.NORMAL); Font font13 = new
			 * Font(bf, 13, Font.NORMAL); Font font14 = new Font(bf, 14,
			 * Font.NORMAL);
			 

			dbutils db = new dbutils();
			String sql = "select * from Erp_KhachHang where pk_seq = '"
					+ hdBean.getNppId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] sql = "
					+ sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try {
				ResultSet rs = db.get(sql);
				if (rs.next()) {
					if (rs.getString("MST") != null)
						taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null)
						name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				db.shutDown();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String ghichu = hdBean.getGhiChu();
			if (ghichu == null || ghichu.trim().length() == 0) {
				ResultSet rs_hopdong = hdBean.getHopdongRs();
				if (rs_hopdong != null) {
					try {
						while (rs_hopdong.next()) {
							if (rs_hopdong.getString("pk_seq").trim()
									.equals(hdBean.getHopdongId())) {
								ghichu = rs_hopdong.getString("mahopdong");
							}
						}
					} catch (Exception e) {
					}
				}

				if (ghichu == null || ghichu.trim().length() == 0) {
					ghichu = hdBean.gethinhthuctt();
				}

				if (ghichu == null)
					ghichu = " ";
			}

			String thue = "" + hdBean.getVAT();
			System.out.println("Thue la: " + thue);

			// Vẽ HEADER
			PdfPTable table = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_HEADER_WIDTHS);
			table.setWidthPercentage(100.0f);

			// Họ và tên người mua hàng
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(hdBean.getNguoiMuaHang(),
					headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// name_of_company =
			// "CÔNG TY TNHH SẢN XUẤT THƯƠNG MẠI VÀ DỊCH VỤ BAO BÌ TĂNG PHÚ TÂN";//

			// Tên đơn vị
			float _fontSize = khachhang_config.getFontSize()
					- (name_of_company.length() > 50 ? (float) (name_of_company
							.length() - 50) / 6 : 0);
			Font headerFontKh = new Font(bf, _fontSize, Font.BOLD);

			// System.out.println("FONT SIZE = " + _fontSize);

			cell = new PdfPCell(new Paragraph(name_of_company.toUpperCase(),
					headerFontKh));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// address =
			// "Số 10 đường Nguyễn Sinh Sắc, ấp Phú Long, xã Tân Phú Đông,Thành phố SaĐéc, tỉnh Đồng Tháp";
			_fontSize = address.length() > 100 ? 8.0f
					: address.length() > 95 ? 8.1f
							: address.length() > 90 ? 8.2f
									: address.length() > 80 ? 8.3f : 10.0f;
			Font headerFontDc = new Font(bf, _fontSize, Font.BOLD);

			// Địa chỉ
			cell = new PdfPCell(new Paragraph(address, headerFontDc));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// Mã số thuế
			cell = new PdfPCell(new Paragraph(taxCode, headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// Hình thức thanh toán
			cell = new PdfPCell(new Paragraph(ghichu, headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// Khoảng trống
			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_HEADER_BOTTOM);
			table.addCell(cell);

			document.add(table);

			// IN SẢN PHẨM
			table = new PdfPTable(TABLE_COLUMN_WIDTHS.length);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_COLUMN_WIDTHS);
			table.setWidthPercentage(100.0f);

			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();
			double total_amount = 0;
			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;

			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_NUM_ROWS = " + TABLE_NUM_ROWS);
			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_ROW_HEIGHT = " + TABLE_ROW_HEIGHT);

			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0)
				sokytu1sp = 30;

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) {
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);

				// Tên + quy cách
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");
				// System.out.println("TEN XUAT HOA DON = " +
				// sanpham.getTenXuatHoaDon());

				String tenSp = ttsp[0];
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat
																				// ca
																				// cac
																				// tu
																				// trong
																				// ten
																				// san
																				// pham
				String _ten = "", _ten2 = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xử lý khi 1 từ > 30 ký tự
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // Thêm dòng cũ
						_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng
													// mới
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);

				// Xu ly quy cach
				words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca
																		// cac
																		// tu
																		// trong
																		// quy
																		// cach
				_ten = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xử lý khi 1 từ > 30 ký tự
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // Thêm dòng cũ
						_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng
													// mới
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);

				// Xu ly ghi chu
				String sanpham_ghichu = hdBean.getSanphamGhiChu().get(
						sanpham.getIdSanPham());
				int countGhiChu = 0;
				String[] arrGhiChu = new String[] {};
				if (sanpham_ghichu != null
						&& sanpham_ghichu.trim().length() > 0) {
					arrGhiChu = sanpham_ghichu.split("__");
					countGhiChu = arrGhiChu.length;

					for (int j = 0; j < arrGhiChu.length; j++) {
						if (arrGhiChu[j].equals("NA"))
							arrGhiChu[j] = "";
					}

					if (countGhiChu > 3)
						countGhiChu = 3;
				}

				for (int _j = 0; _j < arrGhiChu.length; _j++) {
					words = arrGhiChu[_j].trim().replaceAll("  ", " ")
							.split(" "); // Tat ca cac tu trong ghi chu
					_ten = "";
					for (int _i = 0; _i < words.length; _i++) {
						// Xử lý khi 1 từ > 30 ký tự
						if (words[_i].length() > sokytu1sp) {
							if (_ten.trim().length() > 0)
								_tenList.add(_ten); // Thêm dòng cũ
							_tenList.add(words[_i]); // Thêm từ dài đó vào 1
														// dòng mới
							_ten = ""; // reset _ten
						} else {
							_ten2 = _ten
									+ (_ten.length() == 0 ? words[_i] : " "
											+ words[_i]);
							if (_ten2.length() > sokytu1sp) {
								_tenList.add(_ten);
								_ten = words[_i];
							} else {
								_ten = _ten2;
							}
						}
					}
					if (_ten.trim().length() > 0)
						_tenList.add(_ten);
				}
				soDongSp += _tenList.size();

				
				 * for(int _i = 0; _i < _tenList.size(); _i++) {
				 * System.out.println("ErpHoaDonPdf.in Dòng [" + _i + "] " +
				 * _tenList.get(_i)); }
				 

				double thanhtien = 0;

				try {
					thanhtien = Math.round(sanpham.getDonGia()
							* sanpham.getSoLuong());
					total_amount += sanpham.getDonGia() * sanpham.getSoLuong();
				} catch (Exception ex) {
				}

				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Thanh tien["
				// + spIndex + "] = " + total_amount);
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Quy doi["
				// + spIndex + "] = " + sanpham.getQuyDoiStr());

				String dg = " ";
				if (sanpham.getDonGia() > 0)
					dg = formatter.format(sanpham.getDonGia());

				String tt = " ";
				if (thanhtien > 0)
					tt = formatter.format(thanhtien);

				// In khi có thể hiện toàn bộ thông tin của sản phẩm (1 dòng tên
				// sp, 1 dòng quy cách, các dòng ghi chú)

				// In dong 1
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dòng 1 sp " + spIndex);
				String[] arr = new String[] { Integer.toString(spIndex + 1),
						_tenList.size() > 0 ? _tenList.get(0) : "",
						sanpham.getDonViTinh(),
						formatter2.format(sanpham.getSoLuong()), dg, tt };

				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
				rowIndex++;

				// In dong2
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dòng 2 sp " + spIndex);
				arr = new String[] { " ",
						_tenList.size() > 1 ? _tenList.get(1) : "",
						"(" + sanpham.getQuyDoiStr() + ")", " ", " " };

				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length - 1; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					if (j == 2) {
						cell.setColspan(2);
					}
					table.addCell(cell);
				}
				rowIndex++;

				// In cac dong ghi chu con lai
				for (int z = 2; z < _tenList.size(); z++) {
					arr = new String[] { " ", _tenList.get(z), " ", " ", " ",
							" " };
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
						cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						cell.setVerticalAlignment(Element.ALIGN_TOP);
						cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
						cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
						cell.setBorder(BORDER_WIDTH);
						table.addCell(cell);
					}
					rowIndex++;
				}

				spIndex++;
			}

			// In dong rong

			for (int i = rowIndex; i < TABLE_NUM_ROWS; i++) {
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// i +", In dòng rỗng");
				String[] arr = new String[] { " ", " ", " ", " ", " ", " " };
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
			}

			
			 * for (int i = 0; i < TABLE_NUM_ROWS; i++) { if(i < spList.size())
			 * { IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(i); long
			 * thanhtien = 0;
			 * 
			 * try { thanhtien = Math.round(sanpham.getDonGia() *
			 * sanpham.getSoLuong()); } catch (Exception ex){ }
			 * 
			 * total_amount = total_amount + thanhtien;
			 * System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Thanh tien["
			 * + count + "] = " + total_amount);
			 * System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Quy doi["
			 * + count + "] = " + sanpham.getQuyDoiStr());
			 * 
			 * String dg = " "; if (sanpham.getDonGia() > 0) dg =
			 * FormatNumber(sanpham.getDonGia(), 0);
			 * 
			 * String tt = " "; if (thanhtien > 0) tt = FormatNumber(thanhtien,
			 * 0);
			 * 
			 * String[] arr = new String[] { Integer.toString(count),
			 * sanpham.getTenXuatHoaDon(), sanpham.getDonViTinh() + "\n(" +
			 * sanpham.getQuyDoiStr() + ")", FormatNumber((double)
			 * sanpham.getSoLuong(), 0), dg, tt };
			 * 
			 * for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) { cell = new
			 * PdfPCell(new Paragraph(arr[j], donhangFont));
			 * cell.setFixedHeight(TABLE_ROW_HEIGHT);
			 * cell.setVerticalAlignment(Element.ALIGN_TOP);
			 * cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
			 * cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
			 * cell.setBorder(BORDER_WIDTH); table.addCell(cell); } count++; }
			 * else { for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
			 * cell = new PdfPCell(new Paragraph("  ", donhangFont));
			 * cell.setBorder(BORDER_WIDTH);
			 * cell.setFixedHeight(TABLE_ROW_HEIGHT); table.addCell(cell); } } }
			 
			document.add(table);

			double tienCK = hdBean.getTienChietKhau() > 0 ? hdBean.getTienChietKhau() : total_amount * hdBean.getChietkhau() / 100;
			double tienKM = hdBean.getTienkhuyenmai();
			double tienSauCKKM = total_amount - tienCK - tienKM;

			// AMOUNTS TABLE
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { TABLE_AMOUNT_LEFT_WIDTH,
					TABLE_AMOUNT_RIGHT_WIDTH });

			// Left0: Trống
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[0]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[0]);
			table.addCell(cell);
			// Right0: Cộng tiền hàng
			cell = new PdfPCell(new Paragraph(formatter3.format(tienSauCKKM),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[0]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[0]);
			table.addCell(cell);

			// Left1: VAT
			double vat = Math.round(hdBean.getVAT());
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT))
					+ "./.";

			cell = new PdfPCell(new Paragraph(formatter3.format(vat),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[1]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[1]);
			table.addCell(cell);
			// Right1: Tiền thuế GTGT
			cell = new PdfPCell(new Paragraph(formatter3.format(tienVAT),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[1]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[1]);
			table.addCell(cell);

			// Left2: Trống
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[2]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[2]);
			table.addCell(cell);
			// Right2: Tổng cộng tiền thanh toán
			cell = new PdfPCell(new Paragraph(formatter3.format(tienSauVAT),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[2]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[2]);
			table.addCell(cell);

			// Left3: Trống
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[3]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[3]);
			table.addCell(cell);
			// Right3: Số tiền viết bằng chữ
			cell = new PdfPCell(new Paragraph(tienBangChu, donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[3]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[3]);
			table.addCell(cell);

			document.add(table);

			String ngaythangnam = hdBean.getNgayxuathd();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);

			Paragraph p = new Paragraph(ngay + "                  " + thang
					+ "               " + nam, donhangFont);
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setSpacingBefore(TABLE_AMOUNT_ROW_HEIGHTS[4]);
			document.add(p);

			// CLOSE DOCUMENT
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Loi Trong Qua Trinh In: "
							+ e.getMessage());
		}
	}
*/
	/**
	 * In hóa đơn nước ngoài excel
	 * @param out
	 * @param hdBean
	 */
	private void HoaDonNuocNgoaiExcel(OutputStream out, IErpHoaDon hdBean) 
	{/*


		NumberFormat formatter = new DecimalFormat("###,###,###,###,###");
		NumberFormat formatter1 = new DecimalFormat("###,###,###,###,###.##");
		NumberFormat formatter2 = new DecimalFormat("###,###,###,###,###.00");

		int TABLE_NUM_ROWS = hdBean.getSoDongSanPham(); // 6
		int S2_START_INDEX = 20;

		try {
			dbutils db = new dbutils();
			String sql = "select * from Erp_KhachHang where pk_seq = '" + hdBean.getNppId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] sql = " + sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try {
				ResultSet rs = db.get(sql);
				if (rs.next()) {
					if (rs.getString("MST") != null) taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null) name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String ghichu = hdBean.getGhiChu();
			if (ghichu == null || ghichu.trim().length() == 0) {
				ResultSet rs_hopdong = hdBean.getHopdongRs();
				if (rs_hopdong != null) {
					try {
						while (rs_hopdong.next()) {
							if (rs_hopdong.getString("pk_seq").trim().equals(hdBean.getHopdongId())) {
								ghichu = rs_hopdong.getString("mahopdong");
							}
						}
						rs_hopdong.close();
					} catch (Exception e) {
					}
				}

				if (ghichu == null || ghichu.trim().length() == 0) {
					ghichu = hdBean.gethinhthuctt();
				}

				if (ghichu == null)
					ghichu = " ";
			}

			String thue = "" + hdBean.getVAT();
 

			FileInputStream fstream;
			Cell cell = null;

			fstream = new FileInputStream(getServletContext().getInitParameter( "path") + "\\HoaDonTaiChinhTrongNuoc.xlsm");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			workbook.open(fstream);

			Worksheets worksheets = workbook.getWorksheets();

			// Sheet 1
			Worksheet worksheet1 = worksheets.getSheet("HOA DON CANH GIUA");
			Cells cells1 = worksheet1.getCells();

			cell = cells1.getCell("E12");
			cell.setValue(hdBean.getNguoiMuaHang()); // Người mua hàng
			cell = cells1.getCell("E13");
			cell.setValue(name_of_company); // Họ tên khách hàng
			cell = cells1.getCell("E14");
			cell.setValue(address); // Địa chỉ
			cell = cells1.getCell("E15");
			cell.setValue(taxCode); // Mã số thuế
			cell = cells1.getCell("E16");
			
			sql=" SELECT MAHOPDONG  FROM ERP_HOPDONG WHERE PK_SEQ IN "+
				" ( "+
				" SELECT HOPDONG_FK FROM ERP_DONDATHANG WHERE PK_SEQ IN "+ 
				" (   "+
				" SELECT A.DDH_FK FROM ERP_HOADON_DDH A WHERE A.HOADON_FK= "+hdBean.getId() +  
				" UNION "+
				" SELECT DONDATHANG_FK FROM ERP_XUATKHO XK INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.xuatkho_fk=XK.PK_SEQ "+
				" WHERE HDXK.hoadon_fk="+hdBean.getId()+" ))";
			
			
			
			
			
			ResultSet rs=db.get(sql);
			String mahopdong="";
			if(rs.next()){
				mahopdong=rs.getString("MAHOPDONG");
			}
			rs.close();
			db.shutDown();
			if(mahopdong.length()>0) {
				cell.setValue("Theo HĐ: "+mahopdong);
			}else{
				cell.setValue(hdBean.gethinhthuctt()); // Hình thức thanh toán
			}

			// Sản phẩm
			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;
			double total_amount = 0;
			double total_amountVND = 0;
			
			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0)
				sokytu1sp = 40;

			String prev_tensp = "", temp_tensp = "";
			boolean changeSpCore = false;
			int stt = 0;
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) 
			{
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);
				temp_tensp = sanpham.getMaSanPham() + sanpham.getQuyCachGroup() + sanpham.getDonGia();

				double thanhtien = 0;
				try {
					thanhtien = sanpham.getDonGia() * sanpham.getSoLuong();
					total_amount += sanpham.getDonGia() * sanpham.getSoLuong();
					total_amountVND += (sanpham.getDonGia() * sanpham.getSoLuong())* Double.parseDouble(hdBean.getTyGiaQuyDoi());
					
				} catch (Exception ex) { }
				
				//System.out.println("[ErpHoaDonPdf...] sanpham.getTenXuatHoaDon() = " + sanpham.getTenXuatHoaDon());

				// TÊN XUẤT HÓA ĐƠN } QUY CÁCH
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0].trim();
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";
			

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				
				// Xu ly ten ghi chú dòng san pham
				List<String> _tenList_GC = new ArrayList<String>();
				
				boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 || sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0 ||  sanpham.getMaSanPham().trim().equals("OE"));

				 
				changeSpCore = isSpCone && !prev_tensp.equals(temp_tensp);
				prev_tensp = temp_tensp;
				 
				
				//Tạo _tenList
				xuLyTenList_NuocNgoai(hdBean, _tenList, sanpham, spIndex, tenSp, qcSp, sokytu1sp, prev_tensp, temp_tensp, changeSpCore);
				
				//Xử lý ghi chú
				xuLyGhiChuList(hdBean, _tenList_GC, sanpham, sokytu1sp, changeSpCore);
				
				if (isSpCone) {
					// IN SẢN PHẨM CONE

					int beginIndex = 0;
					if (changeSpCore) {
						//Tìm thông tin sp cone trong listsanphamCone
						double sl = sanpham.getSoLuong();
						String key = sanpham.getMaSanPham() + sanpham.getQuyCachGroup();
						IErpHoaDon_SP _spTemp;
						for(int z = 0; z < hdBean.GetListSanPhamCone().size(); z++) {
							_spTemp =  hdBean.GetListSanPhamCone().get(z);
							if(_spTemp != null && _spTemp.getIdSanPham().equals(key)) {
								sl = _spTemp.getSoLuong();
							}
						}
						double _thanhtien = sl * sanpham.getDonGia();
						double _thanhtienVND = sl * sanpham.getDonGia()* Double.parseDouble(hdBean.getTyGiaQuyDoi()); 
						
						stt++;
						// Cột stt sản phẩm
						cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
						cell.setValue("" + (stt));
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
						// Cột Đơn vị tính
						cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonViInAn());
						// Cột Số lượng
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue((formatVN(formatter2.format(sl))));
						// Cột Đơn giá
						cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter1.format(sanpham.getDonGia()*Double.parseDouble(hdBean.getTyGiaQuyDoi()) )));
						// Cột Thành tiền
						cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(_thanhtienVND)));
						rowIndex++;
						beginIndex++;

						// In cột quy cách
						if(_tenList.size() >= beginIndex) {
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList.get(beginIndex).trim());
							rowIndex++;
							beginIndex++;
						}
						
						// In cột tiền NT
						if(_thanhtien >= 0) {
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue("(" +formatter1.format(_thanhtien) + " " + hdBean.getTienteTen() +")");
							rowIndex++;
							beginIndex++;
						}
						
						int bieny=0;
						// In các dòng ghi chú
						for (int y = bieny; y < _tenList_GC.size(); y++) {
							
							// Cột tên sản phẩm
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList_GC.get(y));
							// Cột Đơn vị tính
							cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Số lượng
							cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Đơn giá
							cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
							cell.setValue("");

							rowIndex++;
							beginIndex++;
						}
						
					}
					


				} else {
					// CÁC SẢN PHẨM CÒN LẠI
					stt++;

					// In dong 1
					// Cột stt sản phẩm
					cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
					
					//System.out.println(S2_START_INDEX + rowIndex);
					cell.setValue("" + (stt));
					// Cột tên sản phẩm
					cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					// Cột Đơn vị tính
					cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonViInAn());
					// Cột Số lượng
					cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter2.format(sanpham.getSoLuong())));
					// Cột Đơn giá
					cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter1.format(sanpham.getDonGia()*Double.parseDouble(hdBean.getTyGiaQuyDoi()) )));
					// Cột Thành tiền
					cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(thanhtien*Double.parseDouble(hdBean.getTyGiaQuyDoi()))));

					
					int bien=1;
						
					rowIndex++;

					
					
						for (int z = bien; z < _tenList.size(); z++) {
							
							// Cột tên sản phẩm
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList.get(z));
							// Cột Đơn vị tính
							cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Số lượng
							cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Đơn giá
							cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
							cell.setValue("");

							rowIndex++;
						}
						
						// In cột tiền NT
						if(thanhtien >= 0) {
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue("(" +formatter1.format(thanhtien) + " " + hdBean.getTienteTen() +")");
							rowIndex++;
						}
						
						int bieny=0;
						// In các dòng ghi chú
						for (int y = bieny; y < _tenList_GC.size(); y++) {
							
							// Cột tên sản phẩm
							cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList_GC.get(y));
							// Cột Đơn vị tính
							cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Số lượng
							cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
							cell.setValue("");
							// Cột Đơn giá
							cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
							cell.setValue("");

							rowIndex++;
						}
					
					
					
					
				}

				
				
				spIndex++;
			}

			// TIỀN CK, KM, VC, BH
			double tienCK = Math.round(hdBean.getTienChietKhau()*Double.parseDouble(hdBean.getTyGiaQuyDoi()));
			System.out.println(hdBean.getTienChietKhau());
			
			double tienKM = Math.round(hdBean.getTienkhuyenmai()*Double.parseDouble(hdBean.getTyGiaQuyDoi()));
			double tienBH = Math.round(hdBean.getTienBaoHiem()*Double.parseDouble(hdBean.getTyGiaQuyDoi()));
			double tienVC = Math.round(hdBean.getTienVanChuyen()*Double.parseDouble(hdBean.getTyGiaQuyDoi()));
			
			
			if (hdBean.getTienChietKhau() > 0) {
				stt++;
				// Cột stt sản phẩm
				cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));				
				cell.setValue("" + (stt));
				
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Chiết khấu "+ hdBean.getChietkhau() + "%:(" +formatter1.format(hdBean.getTienChietKhau()) + hdBean.getTienteTen() + ")" );
				
				
				// Cột Đơn vị tính
				cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Số lượng
				cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Đơn giá
				cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Thành tiền
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(tienCK)));
				
				rowIndex++;
			}

			if (hdBean.getTienkhuyenmai() > 0) {
				stt++;
				// Cột stt sản phẩm
				cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));				
				cell.setValue("" + (stt));
				
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Tiền giảm giá: "+ formatter1.format(hdBean.getTienkhuyenmai()));
				
				// Cột Đơn vị tính
				cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Số lượng
				cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Đơn giá
				cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Thành tiền
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(tienKM)));
				
				
				rowIndex++;
			}

			if (hdBean.getTienVanChuyen() > 0) {
				stt++;
				// Cột stt sản phẩm
				cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));				
				cell.setValue("" + (stt));
				
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Tiền vận chuyển: (" +formatter1.format(hdBean.getTienVanChuyen()) + " " + hdBean.getTienteTen() + ")");
				
				// Cột Đơn vị tính
				cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Số lượng
				cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Đơn giá
				cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Thành tiền
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(tienVC)));
				
				
				rowIndex++;
			}
			
			if (hdBean.getTienBaoHiem() > 0) {
				stt++;
				// Cột stt sản phẩm
				cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));				
				cell.setValue("" + (stt));
				
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Phí bảo hiểm: ("+ formatter1.format(hdBean.getTienBaoHiem()) + " " + hdBean.getTienteTen() + ")");
				
				// Cột Đơn vị tính
				cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Số lượng
				cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Đơn giá
				cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
				cell.setValue("");
				// Cột Thành tiền
				cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(tienBH)));
				
				
				rowIndex++;
			}

			if (Double.parseDouble(hdBean.getTyGiaQuyDoi()) > 0) {
				
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("*Tỷ giá NHNT:" + formatVN(formatter.format(Double.parseDouble(hdBean.getTyGiaQuyDoi()))) + " VND/" + hdBean.getTienteTen());
				
			}

			
			double tienSauCKKM = Math.round(total_amountVND) - tienCK - tienKM + tienBH + tienVC;
			
			double vat = Math.round(hdBean.getVAT());
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT))
					+ "./.";
			String ngaythangnam = hdBean.getNgayxuathd();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);
			System.out.println("total_amount = " + total_amount);
			System.out.println("TienCK = " + tienCK);
			System.out.println("tienKM = " + tienKM);
			System.out.println("tienSauCKKM = " + tienSauCKKM);
			System.out.println("tienVAT = " + tienVAT);
			System.out.println("tienSauVAT = " + tienSauVAT);

			cell = cells1.getCell("D42");
			cell.setValue(formatVN(formatter.format(vat)));
			
			NumberFormat formatter_vnd = new DecimalFormat("###,###,###,###,###");
			if(hdBean.getTienteId().equals("100000")){
			
				cell = cells1.getCell("L41");
				cell.setValue(formatVN(formatter.format(tienSauCKKM)));
				cell = cells1.getCell("L42");
				cell.setValue(formatVN(formatter.format(tienVAT)));
				cell = cells1.getCell("L43");
				cell.setValue(formatVN(formatter.format(tienSauVAT)));
				cell = cells1.getCell("E45");
			}else{
				cell = cells1.getCell("L41");
				cell.setValue(formatVN(formatter.format(tienSauCKKM)));
				cell = cells1.getCell("L42");
				cell.setValue(formatVN(formatter.format(tienVAT)));
				cell = cells1.getCell("L43");
				cell.setValue(formatVN(formatter.format(tienSauVAT)));
				cell = cells1.getCell("E45");
			}
			cell.setValue(tienBangChu);
			cell = cells1.getCell("K48");
			cell.setValue(ngay);
			cell = cells1.getCell("M48");
			cell.setValue(thang);
			cell = cells1.getCell("N48");
			cell.setValue(nam);

			workbook.save(out);
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	
	*/}

/*	private void HoaDonNuocNgoaiPdf(Document document,
			ServletOutputStream outstream, IErpHoaDon hdBean)
			throws IOException {
		try {
			IErpCauHinhInHoaDon soNumber = new ErpCauHinhInHoaDon();
			soNumber.initWithName("SO_NN");
			soNumber.dbClose();

			IErpCauHinhInHoaDon khachhang_config = new ErpCauHinhInHoaDon();
			khachhang_config.initWithName("CUSTOMER_NN");
			khachhang_config.dbClose();

			IErpCauHinhInHoaDon dh_config = new ErpCauHinhInHoaDon();
			dh_config.initWithName("DETAILS_NN");
			dh_config.dbClose();

			float CONVERT = 28.346457f; // = 1cm
			int BORDER_WIDTH = 0;
			float[] TOP_WIDTHS = { 13f, 6.5f };
			float CUSTOMER_LEFT = khachhang_config.getMarginLeft() * CONVERT, CUSTOMER_RIGHT = khachhang_config
					.getMarginRight() * CONVERT, CUSTOMER_TOP = khachhang_config
					.getMarginTop() * CONVERT, CUSTOMER_BOTTOM = khachhang_config
					.getMarginBottom() * CONVERT;
			// float CUSTOMER_LEFT = 0.9f*CONVERT, CUSTOMER_RIGHT =
			// 0.1f*CONVERT, CUSTOMER_TOP = 0.0f*CONVERT, CUSTOMER_BOTTOM =
			// 1.6f*CONVERT;
			float CUSTOMER_NAME_LEFT = 3.0f * CONVERT + CUSTOMER_LEFT;
			float CUSTOMER_ADDRESS_LEFT = 0.5f * CONVERT + CUSTOMER_LEFT;
			float CUSTOMER_FAX_LEFT = 0.5f * CONVERT + CUSTOMER_LEFT;
			float SO_LEFT = soNumber.getMarginLeft() * CONVERT;
			// float SO_LEFT = 2.7f*CONVERT;

			int TABLE_NUM_ROWS = 16; // 6
			// int TABLE_NUM_ROWS = 5;
			float TABLE_LEFT = 0.0f * CONVERT, TABLE_RIGHT = 0.0f * CONVERT, TABLE_TOP = 0.0f * CONVERT, TABLE_BOTTOM = 0.0f * CONVERT;
			float TABLE_HEIGHT = dh_config.getTableHeight() * CONVERT, TABLE_ROW_HEIGHT = TABLE_HEIGHT
					/ TABLE_NUM_ROWS;
			// float TABLE_HEIGHT = 8.7f*CONVERT, TABLE_ROW_HEIGHT =
			// TABLE_HEIGHT/TABLE_NUM_ROWS;
			float[] TABLE_COLUMN_WIDTHS = { 1.0f * CONVERT, 8.7f * CONVERT,
					2.1f * CONVERT, 2.1f * CONVERT, 2.1f * CONVERT,
					3.5f * CONVERT };
			// float[] TABLE_COLUMN_WIDTHS = {dh_config.getNoColumn()*CONVERT,
			// dh_config.getProductColumn()*CONVERT,
			// dh_config.getUnitColumn()*CONVERT,
			// dh_config.getQuantityColumn()*CONVERT,
			// dh_config.getUniPriceColumn()*CONVERT,
			// dh_config.getAmoutColumn()*CONVERT};
			float[] TABLE_COLUMN_PADDING_LEFTS = { 0.1f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT };
			int[] TABLE_COLUMN_ALIGNS = { Element.ALIGN_LEFT,
					Element.ALIGN_LEFT, Element.ALIGN_CENTER,
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
					Element.ALIGN_RIGHT };

			float TOTAL_HEIGHT = 1.9f * CONVERT;
			float TOTAL_IN_WORDS_HEIGHT = 0.8f * CONVERT;
			float REMARKS_HEIGHT = 3.3f * CONVERT;
			// Kích thước theo đơn vị cm

			System.out .println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] TABLE_ROW_HEIGHT = " + TABLE_ROW_HEIGHT / CONVERT + "cm");

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font headerFont = new Font(bf, khachhang_config.getFontSize(),
					Font.BOLD);
			Font donhangFont = new Font(bf, 11, Font.NORMAL);
			Font donhangFontB = new Font(bf, 11, Font.BOLD);
			Font productFont = new Font(bf, dh_config.getLineSpacing(),
					Font.NORMAL);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font10 = new Font(bf, 10, Font.NORMAL);

			
			 * Font font = new Font(bf, 13, Font.BOLD); Font font2 = new
			 * Font(bf, 8, Font.BOLD); Font font9bold = new Font(bf, 9,
			 * Font.BOLD); Font font11bold = new Font(bf, 11, Font.BOLD); Font
			 * font12bold = new Font(bf, 12, Font.BOLD); Font font13bold = new
			 * Font(bf, 13, Font.BOLD); Font font14bold = new Font(bf, 14,
			 * Font.BOLD); Font font15bold = new Font(bf, 15, Font.BOLD); Font
			 * font16bold = new Font(bf, 16, Font.BOLD); Font font9 = new
			 * Font(bf, 9, Font.NORMAL); Font font11 = new Font(bf, 11,
			 * Font.NORMAL); Font font12 = new Font(bf, 12, Font.NORMAL); Font
			 * font13 = new Font(bf, 13, Font.NORMAL); Font font14 = new
			 * Font(bf, 14, Font.NORMAL);
			 

			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00");
			PdfWriter.getInstance(document, outstream);
			document.open();

			dbutils db = new dbutils();
			String sql = " select kh.PK_SEQ as khId , kh.MA as khMa, kh.TEN as khTen, isnull(kh.DIACHI,'') as khDiachi, isnull(kh.DIENTHOAI, '') as khDienthoai, isnull(kh.FAX,'') as khFax, isnull(kh.NguoiLienHe,'') as khNguoiLienHe, isnull(kh.Ten,'') as khTen, isnull(hd.HINHTHUCTT,'') as hinhthuctt, isnull(tt.MA,'') as tiente, isnull(e.mahopdong, '') as sohopdong, isnull(hd.ghichu, '') as ghichu, "
					+ "     ISNULL(E.PAYMENTTERMS,'') AS PAYMENTTERMS, ISNULL(E.DELIVERYTERMS, '') AS DELIVERYTERMS, ISNULL(E.ETD, '') AS ETD, ISNULL(E.REMARKS, '') AS REMARKS "
					+ " from ERP_HOADON hd "
					+ " inner join ERP_TIENTE tt on hd.tiente_fk = tt.PK_SEQ "
					+ " inner join ERP_KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "
					+ " inner join ERP_HOADON_XUATKHO a on a.hoadon_fk = hd.PK_SEQ "
					+ " inner join ERP_XUATKHO c on a.xuatkho_fk = c.PK_SEQ "
					+ " inner join ERP_DONDATHANG d on c.DONDATHANG_FK = d.PK_SEQ "
					+ " left join ERP_HOPDONG e on hd.hopdong_fk = e.pk_seq "
					+ " WHERE hd.PK_SEQ = '" + hdBean.getId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] sql = "
					+ sql);
			String address = "";
			// String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";
			String fax = "";
			String phone_number = "";
			String tiente = "";
			String ghichu = "";
			String sohopdong = "";

			ResultSet rs = db.get(sql);
			try {
				if (rs.next()) {
					name_of_buyer = rs.getString("khNguoiLienHe");
					name_of_company = rs.getString("khTen");
					address = rs.getString("khDiachi");
					fax = rs.getString("khFax");
					phone_number = rs.getString("khDienthoai");
					tiente = rs.getString("tiente");
					ghichu = rs.getString("ghichu");
					sohopdong = rs.getString("sohopdong");

					if (ghichu == null || ghichu.trim().length() == 0) {
						ghichu = rs.getString("hinhthuctt");
					}
				}
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			db.shutDown();

			// Vẽ HEADER gồm CUSTOMER và SO
			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TOP_WIDTHS);
			table.setWidthPercentage(100.0f);

			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(name_of_company.toUpperCase(),
					headerFont)); // CUSTOMER
			cell.setPaddingLeft(CUSTOMER_NAME_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.7f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph( getEnDateTime(hdBean.getNgayxuathd()), headerFont)); // Inv date
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.7f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(address, headerFont));
			cell.setPaddingLeft(CUSTOMER_ADDRESS_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("", headerFont)); // Customer's PO
																// //hdBean.getDonDatHang()
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("Tel: " + phone_number + "   " + "Fax: " + fax, headerFont));
			cell.setPaddingLeft(CUSTOMER_FAX_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(sohopdong, headerFont)); // Contract
																		// No.
																		// (Số
																		// Hợp
																		// đồng)
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setPaddingLeft(CUSTOMER_FAX_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(tiente, headerFont)); // Curency
																	// used
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			// Hình thức thanh toán: lấy 1 trong 3 ô ghi chú, hợp đồng hoặc hình
			// thức thanh toán
			cell = new PdfPCell(new Paragraph(" ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(hdBean.getPaymentTerms(),
					headerFont)); // Payment terms
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("                         "
					+ hdBean.getETD(), headerFont)); // ETD
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(hdBean.getDeliveryTerms(),
					headerFont)); // Delivery terms
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			// Khoảng trống HEADER TABLE BÊN DƯỚI
			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(CUSTOMER_BOTTOM);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(" ", headerFont));
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(CUSTOMER_BOTTOM);
			table.addCell(cell);

			// table.setSpacingBefore(7f * CONVERT);
			document.add(table);

			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();
			float lineSpacing = 0;

			String noidungck = "";
			String thue = "";

			noidungck = hdBean.getNoidungCK();
			thue = "" + hdBean.getVAT();
			System.out.println("Thue la: " + thue);

			// Table Content
			table = new PdfPTable(6);
			// table.setSpacingBefore(1.78f * CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(TABLE_COLUMN_WIDTHS);

			PdfPCell cells = new PdfPCell();
			cells.setBorder(0);

			double total_amount = 0;
			double total_quantity = 0;
			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;

			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_NUM_ROWS = "
							+ TABLE_NUM_ROWS);
			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_ROW_HEIGHT = "
							+ TABLE_ROW_HEIGHT);

			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0)
				sokytu1sp = 30;

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) {
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);

				// Tên + quy cách
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0];
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat
																				// ca
																				// cac
																				// tu
																				// trong
																				// ten
																				// san
																				// pham
				String _ten = "", _ten2 = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xử lý khi 1 từ > 30 ký tự
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // Thêm dòng cũ
						_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng
													// mới
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);
				int soDongDanhChoTenSanPham = _tenList.size();

				// Xu ly quy cach
				words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca
																		// cac
																		// tu
																		// trong
																		// quy
																		// cach
				_ten = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xử lý khi 1 từ > 30 ký tự
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // Thêm dòng cũ
						_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng
													// mới
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);

				String sanpham_ghichu = hdBean.getSanphamGhiChu().get(
						sanpham.getIdSanPham());
				int countGhiChu = 0;
				String[] arrGhiChu = new String[] {};
				if (sanpham_ghichu != null
						&& sanpham_ghichu.trim().length() > 0) {
					arrGhiChu = sanpham_ghichu.split("__");
					countGhiChu = arrGhiChu.length;

					for (int j = 0; j < arrGhiChu.length; j++) {
						if (arrGhiChu[j].equals("NA"))
							arrGhiChu[j] = "";
					}

					if (countGhiChu > 3)
						countGhiChu = 3;
				}

				for (int _j = 0; _j < arrGhiChu.length; _j++) {
					words = arrGhiChu[_j].trim().replaceAll("  ", " ")
							.split(" "); // Tat ca cac tu trong ghi chu
					_ten = "";
					for (int _i = 0; _i < words.length; _i++) {
						// Xử lý khi 1 từ > 30 ký tự
						if (words[_i].length() > sokytu1sp) {
							if (_ten.trim().length() > 0)
								_tenList.add(_ten); // Thêm dòng cũ
							_tenList.add(words[_i]); // Thêm từ dài đó vào 1
														// dòng mới
							_ten = ""; // reset _ten
						} else {
							_ten2 = _ten
									+ (_ten.length() == 0 ? words[_i] : " "
											+ words[_i]);
							if (_ten2.length() > sokytu1sp) {
								_tenList.add(_ten);
								_ten = words[_i];
							} else {
								_ten = _ten2;
							}
						}
					}
					if (_ten.trim().length() > 0)
						_tenList.add(_ten);
				}
				soDongSp += _tenList.size();

				double thanhtien = 0;
				try {
					thanhtien = Math.round(sanpham.getDonGia()
							* sanpham.getSoLuong());
					total_amount = total_amount + sanpham.getDonGia()
							* sanpham.getSoLuong();
				} catch (Exception ex) {
				}

				// System.out.println("Thanh tien: " + total_amount);
				total_quantity += sanpham.getSoLuong();

				String dg = " ";
				if (sanpham.getDonGia() > 0)
					dg = formatter2.format(sanpham.getDonGia());

				String tt = " ";
				if (thanhtien > 0)
					tt = formatter2.format(thanhtien);

				// In khi có thể hiện toàn bộ thông tin của sản phẩm (1 dòng tên
				// sp, 1 dòng quy cách, các dòng ghi chú)

				// In dong 1
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dòng 1 sp " + spIndex);
				String[] arr = new String[] { Integer.toString(spIndex + 1),
						_tenList.size() > 0 ? _tenList.get(0) : "",
						sanpham.getDonViTinhEng(),
						formatter.format((double) sanpham.getSoLuong()), dg, tt };

				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					Font font = j == 1 ? donhangFontB : donhangFont;

					cell = new PdfPCell(new Paragraph(arr[j], font));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
				rowIndex++;

				// In dong2
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dòng 2 sp " + spIndex);
				arr = new String[] { " ",
						_tenList.size() > 1 ? _tenList.get(1) : " ",
						"(" + sanpham.getQuyDoiStr() + ")", " ", " " };
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length - 1; j++) {
					cell = new PdfPCell( new Paragraph(arr[j], soDongDanhChoTenSanPham > 1 && j == 1 ? donhangFontB : donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					if (j == 2) {
						cell.setColspan(2);
					}
					table.addCell(cell);
				}
				rowIndex++;

				// In cac dong ghi chu con lai
				for (int z = 2; z < _tenList.size(); z++) {
					// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
					// rowIndex +", In các dòng ghi chú sp " + spIndex);
					arr = new String[] { " ", _tenList.get(z), " ", " ", " ",
							" " };
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
						cell = new PdfPCell(
								new Paragraph(
										arr[j],
										soDongDanhChoTenSanPham > z && j == 1 ? donhangFontB
												: donhangFont));
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						cell.setVerticalAlignment(Element.ALIGN_TOP);
						cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
						cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
						cell.setBorder(BORDER_WIDTH);
						table.addCell(cell);
					}
					rowIndex++;
				}

				spIndex++;
			}

			for (int i = rowIndex; i < TABLE_NUM_ROWS; i++) {
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// i +", In dòng rỗng");
				String[] arr = new String[] { " ", " ", " ", " ", " ", " " };
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
			}

			
			 * double total_amount = 0; double total_quantity = 0; int count =
			 * 0; for (int i = 0; i < TABLE_NUM_ROWS; i++) { if(i <
			 * spList.size()) { ++count; IErpHoaDon_SP sanpham = (IErpHoaDon_SP)
			 * spList.get(i); long thanhtien = 0; try { thanhtien =
			 * Math.round(sanpham.getDonGia() * sanpham.getSoLuong()); } catch
			 * (Exception ex) {}
			 * 
			 * total_amount = total_amount + sanpham.getDonGia() *
			 * sanpham.getSoLuong(); System.out.println("Thanh tien: " +
			 * total_amount); total_quantity += sanpham.getSoLuong();
			 * 
			 * String dg = " "; if (sanpham.getDonGia() > 0) dg =
			 * formatter.format(sanpham.getDonGia());
			 * 
			 * String tt = " "; if (thanhtien > 0) tt =
			 * formatter.format(thanhtien); String[] arr = new String[] {
			 * Integer.toString(count), //0 sanpham.getTenXuatHoaDon(), //1
			 * sanpham.getDonViTinhEng(), //2 formatter.format((double)
			 * sanpham.getSoLuong()), //3 dg, //4 tt //5 };
			 * 
			 * for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) { cells =
			 * new PdfPCell(); Paragraph para; if(j==1) { para = new
			 * Paragraph(arr[j], productFont);
			 * para.setAlignment(Element.ALIGN_LEFT);
			 * cells.setPaddingLeft(0.2f*CONVERT); } else { para = new
			 * Paragraph(arr[j], donhangFont);
			 * para.setAlignment(Element.ALIGN_CENTER); }
			 * cells.addElement(para);
			 * 
			 * cells.setFixedHeight(TABLE_ROW_HEIGHT); cells.setPadding(0);
			 * cells.setBorder(BORDER_WIDTH);
			 * cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * table.addCell(cells); } } else { for (int j = 0; j <
			 * TABLE_COLUMN_WIDTHS.length; j++) { cells = new PdfPCell();
			 * Paragraph para = new Paragraph(" ", donhangFont);
			 * cells.addElement(para); cells.setFixedHeight(TABLE_ROW_HEIGHT);
			 * cells.setPadding(0); cells.setBorder(BORDER_WIDTH);
			 * cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * table.addCell(cells); } } }
			 * 
			 * System.out.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] count = "
			 * + count);
			 

			double tienCK = hdBean.getTienChietKhau() > 0 ? hdBean
					.getTienChietKhau() : total_amount * hdBean.getChietkhau()
					/ 100;
			double tienKM = hdBean.getTienkhuyenmai();
			double tienSauCKKM = Math.round(total_amount - tienCK - tienKM);

			// TOTAL
			for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
				if (j == 3) {
					cells = new PdfPCell(new Paragraph(""
							+ formatter.format(total_quantity), donhangFont));
				} else if (j == 5) {
					cells = new PdfPCell(new Paragraph(
							formatter2.format(tienSauCKKM), donhangFont));
				} else {
					cells = new PdfPCell(new Paragraph("  ", donhangFont));
				}
				if (j == 1) {
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				} else {
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setBorder(BORDER_WIDTH);
				cells.setFixedHeight(TOTAL_HEIGHT);
				table.addCell(cells);
			}
			document.add(table);

			// TOTAL IN WORDS
			String tien = DocTienEN.convert(Math.round(tienSauCKKM));

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			float[] totalInWordsWidths = { 18.0f * CONVERT };
			table.setWidths(totalInWordsWidths);

			cells = new PdfPCell(
					new Paragraph("US Dollars " + tien, font10bold));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setPaddingLeft(3.5f * CONVERT);
			cells.setFixedHeight(TOTAL_IN_WORDS_HEIGHT);
			cells.setBorder(BORDER_WIDTH);
			table.addCell(cells);

			// REMARKS (3 ghi chú)
			cells = new PdfPCell(new Paragraph("                            "
					+ hdBean.getRemarks().replaceAll("\n", "\n"), font10));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setPaddingLeft(1.0f * CONVERT);
			cells.setBorder(BORDER_WIDTH);
			table.addCell(cells);

			document.add(table);

			// CLOSE DOCUMENT
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] Loi Trong Qua Trinh In :"
							+ e.getMessage());
		}
	}

	*/
	private static String FormatNumber(double number, int round) {
		// System.out.println("Truoc kho Format: " + number);
		String format = "#,###,###";
		if (round >= 1)
			format += ".";

		for (int i = 0; i < round; i++)
			format += "0";

		// System.out.println("Chuoi Format: " + format);

		DecimalFormat df = new DecimalFormat(format);
		String result = df.format(number);

		if (number > 999) {
			// result = result.replaceAll(".", "+");
			result = result.replaceAll(",", ".");
			if (round > 0)
				result = result.substring(0, result.length() - (round + 1))
						+ "," + result.substring(result.length() - round);
			// result = result.replaceFirst("-", ",");
		} else
			result = result.replaceAll(",", ".");
		
		// System.out.println("ket qua: " + result);
		return result;
	}

	public static String formatVN(String so) {

		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");

		return result;

	}

	public static void main(String[] arg) throws DocumentException, IOException {
		String so = "100,100,100.213123";
		String a = formatVN(so);
		System.out.println(a);
	}

	private String getEnDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);

			thang = thang.equals("01") ? "Jan" : thang.equals("02") ? "Feb"
					: thang.equals("03") ? "Mar" : thang.equals("04") ? "Apr"
							: thang.equals("05") ? "May"
									: thang.equals("06") ? "Jun" : thang
											.equals("07") ? "Jul" : thang
											.equals("08") ? "Aug" : thang
											.equals("09") ? "Sep" : thang
											.equals("10") ? "Oct" : thang
											.equals("11") ? "Nov" : thang
											.equals("12") ? "Dec" : " ";
			return thang + " " + ngay + ", " + nam;
		} else {
			return "";
		}
	}

	private String getEnNewDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "/" + thang + "/" + nam;
		} else {
			return date;
		}
	}

	private String getVnDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "-" + thang + "-" + nam;
		} else {
			return "";
		}
	}
	
	private void HoaDonNuocNgoaiExcel_BK(OutputStream out, IErpHoaDon hdBean) 
	{/*
		NumberFormat formatter = new DecimalFormat("###,###,###,###,###");
		NumberFormat formatter2 = new DecimalFormat("###################");
		NumberFormat formatter3 = new DecimalFormat("###,###,###,###,##0.000");
		NumberFormat formatter4 = new DecimalFormat("############0.#####");
		int TABLE_NUM_ROWS = 18; // 6
		int S2_START_INDEX = 20;
		int S1_START_INDEX = 2;
		int REMARK_START_INDEX = 48;

		try {
			dbutils db = new dbutils();
			String sql = " select kh.PK_SEQ as khId , isnull(kh.MA, '') as khMa, isnull(kh.TEN, '') as khTen, isnull(kh.DIACHI,'') as khDiachi, isnull(kh.DIENTHOAI, '') as khDienthoai, isnull(kh.FAX,'') as khFax, isnull(kh.NguoiLienHe,'') as khNguoiLienHe, isnull(kh.Ten,'') as khTen, isnull(hd.HINHTHUCTT,'') as hinhthuctt, isnull(tt.MA,'') as tiente, isnull(e.mahopdong, '') as sohopdong, isnull(hd.ghichu, '') as ghichu, "
					+ "     ISNULL(E.PAYMENTTERMS,'') AS PAYMENTTERMS, ISNULL(E.DELIVERYTERMS, '') AS DELIVERYTERMS, ISNULL(E.ETD, '') AS ETD, ISNULL(E.REMARKS, '') AS REMARKS "
					+ " from ERP_HOADON hd "
					+ " inner join ERP_TIENTE tt on hd.tiente_fk = tt.PK_SEQ "
					+ " inner join ERP_KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "
					+ " left join ERP_HOADON_XUATKHO a on a.hoadon_fk = hd.PK_SEQ "
					+ " left join ERP_XUATKHO c on a.xuatkho_fk = c.PK_SEQ "
					+ " left join ERP_DONDATHANG d on c.DONDATHANG_FK = d.PK_SEQ "
					+ " left join ERP_HOPDONG e on hd.hopdong_fk = e.pk_seq "
					+ " WHERE hd.PK_SEQ = '" + hdBean.getId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] sql = "
					+ sql);
			String address = "";
			// String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";
			String fax = "";
			String phone_number = "";
			String tiente = "";
			String ghichu = "";
			String sohopdong = "";

			ResultSet rs = db.get(sql);
			try {
				if (rs.next()) {
					name_of_buyer = rs.getString("khNguoiLienHe").trim();
					name_of_company = rs.getString("khTen").trim().toUpperCase();
					address = rs.getString("khDiachi").trim();
					fax = rs.getString("khFax").trim();
					phone_number = rs.getString("khDienthoai").trim();
					tiente = rs.getString("tiente").trim();
					ghichu = rs.getString("ghichu").trim();
					sohopdong = rs.getString("sohopdong").trim();

					if (ghichu == null || ghichu.trim().length() == 0) {
						ghichu = rs.getString("hinhthuctt").trim();
					}
				}
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			db.shutDown();

			System.out.println("Khach Hang: " + name_of_company);
			System.out.println("Dia Chi: " + address);
			System.out.println("So Dien Thoai: " + phone_number);
			System.out.println("Fax: " + fax);

			FileInputStream fstream;
			Cell cell = null;

			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HoaDonTaiChinhNuocNgoai.xlsm");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			workbook.open(fstream);

			Worksheets worksheets = workbook.getWorksheets();

			// Sheet 1
			Worksheet worksheet1 = worksheets.getSheet("DS Khach Hang");
			Cells cells1 = worksheet1.getCells();

			cell = cells1.getCell("A2");
			cell.setValue(name_of_company); // CUSTOMER
			cell = cells1.getCell("B2");
			cell.setValue(address); //
			cell = cells1.getCell("C2");
			cell.setValue(""); //
			cell = cells1.getCell("D2");
			cell.setValue(phone_number); //
			cell = cells1.getCell("E2");
			cell.setValue(fax); //
			cell = cells1.getCell("K2");
			cell.setValue(tiente); //
			cell = cells1.getCell("M2");
			cell.setValue(hdBean.gethinhthuctt()); // Payment terms

			cell = cells1.getCell("Z1");
			cell.setValue(getEnNewDateTime(hdBean.getNgayxuathd())); // Ngày xuất hóa đơn

			// Sheet 2
			Worksheet worksheet2 = worksheets.getSheet("HoaDon");
			Cells cells2 = worksheet2.getCells();

			cell = cells2.getCell("D10");
			cell.setValue(name_of_company); // CUSTOMER
			cell = cells2.getCell("D16");
			cell.setValue(hdBean.getETD()); // ETD
			cell = cells2.getCell("N10");
			cell.setValue(hdBean.getNgayxuathd()); // Inv date
			cell = cells2.getCell("N12");
			cell.setValue(hdBean.getCustomersPO()); // Customer's PO
			cell = cells2.getCell("N13");
			cell.setValue(sohopdong); // Constract No.
			cell = cells2.getCell("N14");
			cell.setValue(tiente); // Currency used
			cell = cells2.getCell("N15");
			cell.setValue(hdBean.gethinhthuctt()); // Payment terms
			cell = cells2.getCell("N16");
			cell.setValue(hdBean.getDeliveryTerms()); // Delivery terms

			double ck = hdBean.getTienChietKhau() * 100 / hdBean.getTongtienchuaVat(); // Tính lại chiết khấu để ra
																						// được tiền chiết khấu đúng
																						// khi in hóa đơn
			cell = cells2.getCell("O41");
			cell.setValue(ck); // Chiết khấu
			cell = cells2.getCell("O42");
			cell.setValue(hdBean.getTienBaoHiem()); // Bảo hiểm

			// Sản phẩm
			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			int spIndex = 0;
			int rowIndex = 0, rowDvIndex = 0;
			int soDongSp = 0;

			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0) {
				sokytu1sp = 40;
			}

			String prev_tensp = "", temp_tensp = "";
			boolean changeSpCore = false;
			int stt = 0;
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) {
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);
				temp_tensp = sanpham.getMaSanPham() + sanpham.getQuyCachGroup();

				// Tên + quy cách
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0];
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
				String _ten = "", _ten2 = "";
				
				boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0|| sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0 ||  sanpham.getMaSanPham().trim().equals("OE"));
				
				if(isSpCone) {
					tenSp += " " + sanpham.getQuyCachGroup().trim();
				}
				
				System.out.println("[ErpHoaDonPdfSvl.xuLyTenList]"+spIndex+" prev_tensp = " + prev_tensp);
				System.out.println("[ErpHoaDonPdfSvl.xuLyTenList]"+spIndex+" temp_tensp = " + temp_tensp);
				changeSpCore = isSpCone && !prev_tensp.equals(temp_tensp);
				prev_tensp = temp_tensp;
				System.out.println("[ErpHoaDonPdfSvl.xuLyTenList]"+spIndex+" changeSpCore = " + changeSpCore);
				
				//Tạo _tenList
				xuLyTenNNList(hdBean, _tenList, sanpham, spIndex, tenSp, qcSp, sokytu1sp, prev_tensp, temp_tensp, changeSpCore);

				if (isSpCone) {
					// IN SẢN PHẨM CONE

					int beginIndex = 0;
					if (changeSpCore) {
						//Tìm thông tin sp cone trong listsanphamCone
						double sl = sanpham.getSoLuong();
						String key = sanpham.getMaSanPham() + sanpham.getQuyCachGroup();
						IErpHoaDon_SP _spTemp;
						for(int z = 0; z < hdBean.GetListSanPhamCone().size(); z++) {
							_spTemp =  hdBean.GetListSanPhamCone().get(z);
							if(_spTemp != null && _spTemp.getIdSanPham().equals(key)) {
								sl = _spTemp.getSoLuong();
							}
						}
						//double _thanhtien = sl * sanpham.getDonGia();
						
						stt++;
						// In dong 1
						// Cột tên sản phẩm
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
						// Cột Đơn vị tính
						cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonViTinhEng());
						// Cột Số lượng
						cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(sl);
						// Cột Đơn giá
						cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonGia());
						rowIndex++;
						beginIndex++;

						// In cột quy cách
						if(_tenList.size() >= beginIndex) {
							cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList.get(beginIndex).trim());
							rowIndex++;
							beginIndex++;
						}
					}
					
					// In mẫu in + màu
					if(_tenList.size() >= beginIndex) {
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue("-" + _tenList.get(beginIndex));
						rowIndex++;
						beginIndex++;
					}

					// In cac dong con lai
					for (int z = beginIndex; z < _tenList.size(); z++) {
						// Cột tên sản phẩm
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.get(z));
						// Cột Đơn vị tính
						cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");

						rowIndex++;
					}

				} else {
					// In dong 1
					// Cột tên sản phẩm
					cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					// Cột Đơn vị tính
					cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonViTinhEng());
					// Cột Số lượng
					cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getSoLuong());
					// Cột Đơn giá
					cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonGia());

					rowIndex++;

					cell = cells1.getCell("I" + (S1_START_INDEX + rowDvIndex));
					cell.setValue(sanpham.getDonViTinhEng());
					rowDvIndex++;

					// In dong 2
					// Cột tên sản phẩm
					cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 1 ? _tenList.get(1) : " ");
					// Cột Đơn vị tính
					cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Số lượng
					cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Đơn giá
					cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
					cell.setValue("");

					rowIndex++;

					// In cac dong ghi chu con lai
					for (int z = 2; z < _tenList.size(); z++) {

						// Cột tên sản phẩm
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.get(z));
						// Cột Đơn vị tính
						cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");

						rowIndex++;
					}
				}

				spIndex++;
			}

			// Remarks
			String[] remarks = hdBean.getRemarks().split("\n");
			if (remarks.length > 0) {
				cell = cells2.getCell("D48");
				cell.setValue(remarks[0].trim());
			}
			for (int i = 1; i < remarks.length; i++) {
				cell = cells2.getCell("C" + (REMARK_START_INDEX + i));
				cell.setValue(remarks[i].trim());
			}

			workbook.save(out);
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	*/}
	
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
	
}
