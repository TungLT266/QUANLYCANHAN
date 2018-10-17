package geso.traphaco.erp.servlets.congtytrahang;

import geso.traphaco.center.beans.doctien.DocTien;
 
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.*;
 
import geso.traphaco.erp.beans.congtytrahang.*;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.congtytrahang.imp.ErpCongtytrahang;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoaDon;

 
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
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

public class Erp_CongtytrahangPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final float CM = 28.3464f;

	public Erp_CongtytrahangPdfSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IErpCongtytrahang obj;
		String userId;
		Utility util = new Utility();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String dthId = util.antiSQLInspection(request.getParameter("dthId"));
		if (dthId == null)
			dthId = "";
		
		System.out.println("Id là : "+dthId);
		
		String inHd = util.antiSQLInspection(request.getParameter("inHd"));
		if (inHd == null)
			inHd = "";

		obj = new ErpCongtytrahang();
		obj.setUserId(userId);
		obj.setId(dthId);
				
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=DonTraHangNCC.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();		
		
		dbutils db = new dbutils();
		
		CreatePO(document, outstream, response, request, db, dthId, obj );
				
		document.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
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
	
	
	private void CreatePO(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db, String id, IErpCongtytrahang obj) 
	{
		HttpSession session = request.getSession();
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 2.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
				
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font11italic = new Font(bf, 11, Font.ITALIC);
			Font font11boldItalic = new Font(bf, 11, Font.BOLDITALIC);
			Font font12boldItalic = new Font(bf, 12, Font.BOLDITALIC);
			Font font11underline = new Font(bf, 11, Font.UNDERLINE);
			
			
			/********************** INFO CONGTY *******************************/
						
			
			PdfPTable tbHeader = new PdfPTable(1);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = {400f};
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logoTraphacoERP.png");
			hinhanh.scalePercent(70);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell _celllogo = new PdfPCell(hinhanh);
			_celllogo.setBorder(0);
			_celllogo.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//_cell.setPadding(10.0f);
			tbHeader.addCell(_celllogo);
			
			PdfPCell cell = null;			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			
			
			/********************** INFO NHA CUNG CAP *******************************/
			String query =
				 " SELECT a.PK_SEQ as dmhId, isnull(a.NguonGocHH ,'') as NguonGocHH, isnull(a.TienTe_FK, '100000') as TienTe_FK, \n"+         
				 "		  c.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, a.NGAYMUA, isnull(a.GhiChu,'') as GhiChu, \n"+         
				 "		  a.DONVITHUCHIEN_FK as dvthId, a.LOAIHANGHOA_FK, a.LOAISANPHAM_FK,  isnull(b.loainhacungcap_fk,0) as nccLoai, \n"+ 
				 "		  b.pk_seq as nccId, b.ma + ', ' + b.TEN as nccTen,         isnull(a.TONGTIENAVAT, '0') as TONGTIENAVAT, \n"+ 
				 "		  isnull(a.VAT, '0') as VAT, isnull(a.TONGTIENBVAT, 0) as TONGTIENBVAT, isnull(a.Dungsai, '0') as dungsai, \n"+ 
				 "		  a.TRANGTHAI, isnull(b.loainhacungcap_fk,0) as loainhacungcap_fk, b.khoNL_Nhan_GC, isnull(a.lydotrahang,'') lydotrahang, c.TEN TENBOPHAN, " +
				 "		  b.DIACHI, b.DIENTHOAI, b.TEN_NGUOILIENHE \n"+  
				 " FROM	  ERP_MUAHANG a inner join ERP_NHACUNGCAP b on a.NHACUNGCAP_FK = b.PK_SEQ \n"+                     
				 "		  inner join ERP_DONVITHUCHIEN c on c.PK_SEQ = a.DONVITHUCHIEN_FK   \n"+
				 " WHERE  a.pk_seq = '"+id+"'  ";
			
			String NgayTraHang = "";
			String Sochungtu = "";
			String Bophan = "";
			String Lydotrahang = "";
			String Nhacungcap = "";
			String Diachi = "";
			String Sodienthoai = "";
			String Nguoilienhe = "";
			double TONGTIENBVAT = 0;
			int VAT = 0;
			double TONGTIENAVAT = 0;
			ResultSet nccRs = db.get(query);
			if(nccRs!=null){
				while(nccRs.next()){
					NgayTraHang = nccRs.getString("NGAYMUA");
					Sochungtu = nccRs.getString("SOCHUNGTU");
					Bophan = nccRs.getString("TENBOPHAN");
					Lydotrahang = nccRs.getString("LYDOTRAHANG");
					Nhacungcap = nccRs.getString("nccTen");
					Diachi = nccRs.getString("DIACHI");
					Sodienthoai = nccRs.getString("DIENTHOAI");
					Nguoilienhe = nccRs.getString("TEN_NGUOILIENHE");
					TONGTIENBVAT = nccRs.getDouble("TONGTIENBVAT");
					TONGTIENAVAT = nccRs.getDouble("TONGTIENAVAT");
					VAT = nccRs.getInt("VAT");
				}
			}
			
			PdfPTable tbNCC = new PdfPTable(2);
			tbNCC.setWidthPercentage(100);
			tbNCC.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crNcc = {50.0f, 200.0f};
			tbNCC.setWidths(crNcc);
			tbNCC.setSpacingBefore(10.0f);
			
			cell= new PdfPCell();	
			Paragraph hd = new Paragraph( "ĐƠN TRẢ HÀNG NHÀ CUNG CẤP" , new Font(bf, 14, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setColspan(2);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Ngày trả hàng: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			
			cell= new PdfPCell();	
			hd = new Paragraph( NgayTraHang , new Font(bf, 12, Font.NORMAL)); // NGÀY TRẢ HÀNG
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Số chứng từ: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( Sochungtu , new Font(bf, 12, Font.NORMAL)); // SỐ ĐƠN TRẢ HÀNG
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Bộ phận: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( Bophan , new Font(bf, 12, Font.NORMAL)); // BỘ PHẬN
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Lý do trả hàng: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( Lydotrahang , new Font(bf, 12, Font.NORMAL)); // LÝ DO TRẢ HÀNG
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Nhà cung cấp: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( Nhacungcap , new Font(bf, 12, Font.NORMAL)); // NHÀ CUNG CẤP
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Địa chỉ: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( Diachi , new Font(bf, 12, Font.NORMAL)); // ĐỊA CHỈ
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Số điện thoại: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( Sodienthoai , new Font(bf, 12, Font.NORMAL)); // SỐ ĐIỆN THOẠI
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			// --------------------------------------* * *--------------------------------- //
			
			cell= new PdfPCell();	
			hd = new Paragraph( "Người liên hệ: " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph(Nguoilienhe , new Font(bf, 12, Font.NORMAL)); // NGƯỜI LIÊN HỆ
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph( " " , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);			
			cell.addElement(hd);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			cell= new PdfPCell();	
			hd = new Paragraph("" , new Font(bf, 12, Font.NORMAL)); // NGƯỜI LIÊN HỆ
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setBorder(0);
			
			tbNCC.addCell(cell);
			
			document.add(tbNCC);
					
			
			/********************INFO SAN PHAM **********************/
			
			
			float[] crSanpham = {1.2f * CONVERT, 9.0f * CONVERT, 1.9f * CONVERT, 1.8f * CONVERT, 2.4f * CONVERT, 2.8f * CONVERT};//, 2.2f * CONVERT};
 
			PdfPTable tbSanPham = new PdfPTable(crSanpham.length);
			tbSanPham.setWidthPercentage(100);
			tbSanPham.setHorizontalAlignment(Element.ALIGN_LEFT);			
			tbSanPham.setWidths(crSanpham);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingAfter(8.0f);
			
			String[] spTitles = {"STT", "Tên hàng hóa", "Đơn vị", "Số lượng", "Đơn giá", "Thành tiền\n(VNĐ)"};//, "Ngày giao" };
			
			for(int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tbSanPham.addCell(cell);
			}
			
			double tongtien = 0;
			//San Pham
			query =  
				" SELECT isnull(b.pk_seq,0) as spid, isnull(b.ma, '') as spMa, isnull(b.dvkd_fk,0) as spDvkd, \n"+  
		        " 		 isnull(b.ten1, b.ten)  as spTen, \n"+ 
		        " 		 isnull(b.ten1, b.ten) as spTen2, 'NA' as spNh, \n"+ 
		        " 		 isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh, \n"+  
		        " 		 isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, \n"+ 
		        " 		 isnull(a.donvi, '') as donvi, a.soluong, isnull(a.dongia, '0') as dongia, \n"+ 
		        " 		 isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, ngaynhan, a.khott_fk, dungsai, \n"+  
		        " 		 isnull(muanguyenlieudukien_fk, 0) as mnlId , '1' as inraHd \n"+ 
		        " 		 ,isnull(a.tenhd, '') as tenhd \n"+  
				" FROM 	 erp_muahang_sp a left join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq \n"+   
				"		 left join erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq \n"+  
				"		 left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq \n"+   
				"		 left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq \n"+ 
				"		 left join erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq \n"+  
				" WHERE    muahang_fk = '"+id+"' \n";

			
			System.out.println("rsSOSP = " + query);
			
			ResultSet rsSOSP = db.get(query);
			
			int STT = 0;
			String TENSP = "";
			String DONVITINH = "";
			double SOLUONG = 0;
			double DONGIA = 0;
			double THANHTIEN = 0;
			double TOTAL_THANHTIEN = 0;
			
			while( rsSOSP.next())
			{
				//soSPCount = soSPCount +1;
				STT++; 
				TENSP = rsSOSP.getString("SPMA")+rsSOSP.getString("SPTEN");
				DONVITINH = rsSOSP.getString("DONVI");
				SOLUONG = rsSOSP.getDouble("SOLUONG");
				DONGIA = rsSOSP.getDouble("DONGIA");
				THANHTIEN = rsSOSP.getDouble("THANHTIEN");
				
				TOTAL_THANHTIEN += THANHTIEN;
				
				String[] arr = new String[] { Integer.toString(STT), TENSP , DONVITINH ,DinhDangTraphacoERP(formatter1.format(SOLUONG)) , DinhDangTraphacoERP(formatter.format(DONGIA)), DinhDangTraphacoERP(formatter.format(THANHTIEN)) };
				
				for (int j = 0; j < spTitles.length; j++)
				{
					cell = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
					if (j == 0 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						//cells.setPaddingLeft(-0.5f*CONVERT);
					}
					else{	
							if(j==1){
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							else if(j == 2 )//ĐƠN VỊ TÍNH
							{
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							}								
							else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								
							}
					}
					
					cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthBottom(0);
					cell.setBorderWidthTop(0);
					cell.setFixedHeight(0.8f * CONVERT);	
					tbSanPham.addCell(cell);
				}
			}
			rsSOSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 8-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < spTitles.length; j++)
				{
					cell = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setFixedHeight(0.8f*CONVERT);
					cell.setBorderWidthLeft(0.5f);
					cell.setBorderWidthTop(0);
					cell.setBorderWidthBottom(0);
					//cell.setBorderWidthRight(1);
					tbSanPham.addCell(cell);
				}
				
				kk++;
			}
			
			cell = new PdfPCell(new Paragraph("Cộng tiền hàng", font12boldItalic));
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(DinhDangTraphacoERP(formatter.format(TONGTIENBVAT)), font12boldItalic));
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
					
			cell = new PdfPCell(new Paragraph("Tiền thuế GTGT " + VAT + "%", font12boldItalic));
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(DinhDangTraphacoERP(formatter.format(TONGTIENAVAT-TONGTIENBVAT)), font12boldItalic));
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tổng cộng", font12boldItalic));
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(DinhDangTraphacoERP(formatter.format(TONGTIENAVAT)), font12boldItalic));
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
			
			document.add(tbSanPham);
			
			/********************END INFO NCC ***********************/
						
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(5);
			
			float[] footerWidths = new float[5];
			float width = 19.0f * CONVERT / 5;
			for(int i = 0; i < footerWidths.length; i++) {
				footerWidths[i] = width;
			}
			
			//float[] footerWidths
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingBefore(5.0f);
			tableFooter.setWidths(footerWidths);
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Xác nhận nhà cung cấp", font11boldItalic));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				
			
			PdfPCell cell12 = new PdfPCell(new Paragraph("Phòng kế hoạch - cung ứng", font11boldItalic));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12.setColspan(2);
			
			
			PdfPCell cell13 = new PdfPCell(new Paragraph("Phòng kế toán", font11boldItalic));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell14 = new PdfPCell(new Paragraph("Ban tổng giám đốc", font11boldItalic));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorderWidth(0);
			cell11.setBorderWidthLeft(0.5f);
			cell11.setBorderWidthRight(0.5f);
			cell11.setBorderWidthTop(0.5f);
			cell11.setBorderWidthBottom(0.5f);
			
			cell12.setBorderWidth(0.5f);
			cell12.setBorderWidthLeft(0);
			
			cell13.setBorderWidth(0.5f);
			cell13.setBorderWidthLeft(0);
			
			cell14.setBorderWidth(0.5f);
			cell14.setBorderWidthLeft(0);

			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			
			
			cell11 = new PdfPCell(new Paragraph("", font11));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
						
			cell12 = new PdfPCell(new Paragraph("Người lập", font12));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
						
			cell13 = new PdfPCell(new Paragraph("Trưởng bộ phận", font12));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell14 = new PdfPCell(new Paragraph("", font12));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
						
			PdfPCell cell15 = new PdfPCell(new Paragraph(" ", font12));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			cell11.setBorderWidthLeft(0.5f);
			cell11.setBorderWidthRight(0.5f);
			
			cell12.setBorderWidthRight(0.5f);
			cell12.setBorderWidthBottom(0.5f);
			
			cell13.setBorderWidthRight(0.5f);
			cell13.setBorderWidthBottom(0.5f);
			
			cell14.setBorderWidthRight(0.5f);
			cell15.setBorderWidthRight(0.5f);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			String[] footerStr = null;						
			footerStr = new String[] {" ", "", "", "Nguyễn Hồng Nam", "Nguyễn Văn Bình"};
			
			String[] footerStr2 = null;
			footerStr2 = new String[] {" ", "", "", "Nguyễn Hồng Nam", "Nguyễn Văn Bình"};
			
			int numFooterRows = 3;
			
			PdfPCell cellFooter;
			//Them khoang trang
			for(int i = 0; i < numFooterRows; i++)
			{
				for(int j = 0; j < footerStr.length; j++)
				{
					if(i<2){
						cellFooter = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.NORMAL)));
					}
					else{
						cellFooter = new PdfPCell(new Paragraph(footerStr[j], new Font(bf, 12, Font.NORMAL)));
					}
					if (j == 0 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
						cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
						//cells.setPaddingLeft(-0.5f*CONVERT);
					}
					else{	
							if(j==1){
								cellFooter.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							else if(j == 2 )//ĐƠN VỊ TÍNH
							{
								cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
							}								
							else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
								cellFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
								
							}
					}
										
					cellFooter.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cellFooter.setBorderWidthLeft(0.5f);
					
					cellFooter.setFixedHeight(0.8f * CONVERT);	
					if(i<2){
						cellFooter.setBorderWidthBottom(0);
						cellFooter.setBorderWidthTop(0);
					}
					else{
						cellFooter.setBorderWidthTop(0);
						cellFooter.setBorderWidthBottom(0.5f);
					}
					
					tableFooter.addCell(cellFooter);
				}
			}
			
			document.add(tableFooter);
			
		/*	//Add Pragrp
			PdfPTable tbLast = new PdfPTable(1);
			tbLast.setWidthPercentage(95);
			tbLast.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] crLast = {600.0f};
			tbLast.setWidths(crLast);
			tbLast.getDefaultCell().setBorder(0);
			
			PdfPCell cellLast = new PdfPCell(new Paragraph("Vui lòng ký xác nhận và phúc đáp cho phòng Cung Ứng qua số fax 3729 1767 hoặc điện thoại 3729 1786 (số nội bộ 131,141) trong vòng 1 ngày kể từ ngày nhận đơn hàng", new Font(bf, 11, Font.NORMAL)));
			cellLast.setBorder(0);
			tbLast.addCell(cellLast);
			
			//tbLast.addCell("Vui lòng ký xác nhận và phúc đáp cho phòng Cung Ứng qua số fax 3729 1767 hoặc điện thoại 3729 1786 (số nội bộ 131,141) trong vòng 1 ngày kể từ ngày nhận đơn hàng");
			
			document.add(tbLast);*/
			
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
	
	protected boolean xuLyTenList(IErpCongtytrahang hdBean, List<String> _tenList, ISanpham sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkd() != null && sanpham.getDvkd().equals("100004") && sanpham.getMasanpham().trim().toUpperCase().indexOf("CONE") >= 0;

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
				_tenList.add(sanpham.getQuycachsanpham());
			}
			
		} else {
			changeSpCore = false;
		}		
		


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
		if(countSoDongQuyCach == 0 && !sanpham.getDvkd().equals("100005")) {
			_tenList.add("Không màu");
		}

		
		return changeSpCore;
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

	/**
	 * In hóa đơn trong nước excel
	 * @param out
	 * @param hdBean
	 */
	/*private void HoaDonTrongNuocExcel(OutputStream out, IErpCongtytrahang hdBean) {

		NumberFormat formatter = new DecimalFormat("###,###,###,###,###.###");

		int TABLE_NUM_ROWS = ErpHoaDon.getSoDongSanPham("100000");
		int S2_START_INDEX = 20;

		try {
			dbutils db = new dbutils();
			String sql = "select * from Erp_Nhacungcap where pk_seq = '" + hdBean.getNCC() + "'";
			//System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] sql = " + sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try {
				ResultSet rs = db.get(sql);
				if (rs.next()) {
					if (rs.getString("MASOTHUE") != null) taxCode = rs.getString("MASOTHUE");
					address = rs.getString("DiaChi");
					if (rs.getString("TEN_NguoiLienhe") != null) name_of_buyer = rs.getString("TEN_NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}


			String thue = "" + hdBean.getVat();
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
				
				
			cell = cells1.getCell("E13");
			cell.setValue(name_of_company); // Họ tên khách hàng
			
			cell = cells1.getCell("E14");			
			cell.setValue(address); // Địa chỉ
			
			cell = cells1.getCell("E15");
			cell.setValue(taxCode); // Mã số thuế
			
			cell = cells1.getCell("E16");						
			cell.setValue(""); // Hình thức thanh toán


			// Sản phẩm
			List<ISanpham> spList = hdBean.getSpList();			

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getInraHd().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;
			double total_amount = 0;

			int sokytu1sp = ErpHoaDon.getSoKyTu1DongSanPham("100000");
			if (sokytu1sp <= 0)
				sokytu1sp = 40;

			String prev_tensp = "", temp_tensp = "";
			boolean changeSpCore = false;
			int stt = 0;
			String tensp_bk="";
			
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) 
			{
				ISanpham sanpham = (ISanpham) spList.get(spIndex);
				temp_tensp = sanpham.getMasanpham() + sanpham.getQuycachsanpham() + sanpham.getDongia();

				double thanhtien = 0;
				try {
					thanhtien = Math.round(Double.parseDouble(sanpham.getDongia().replace(",", "")) * Double.parseDouble(sanpham.getSoluong().replace(",", "")));
					total_amount += Double.parseDouble(sanpham.getDongia().replace(",", "")) * Double.parseDouble(sanpham.getSoluong().replace(",", ""));
				} catch (Exception ex) { }
				
				//System.out.println("[ErpHoaDonPdf...] sanpham.getTenXuatHoaDon() = " + sanpham.getTenXuatHoaDon());

				// TÊN XUẤT HÓA ĐƠN } QUY CÁCH
				String[] ttsp = sanpham.getTenXHD().split("}");

				String tenSp = ttsp[0].trim();
				
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words;
				String _ten = "", _ten2 = "";
				
				boolean isSpCone = sanpham.getDvkd().trim().length() > 0 && sanpham.getDvkd() != null && sanpham.getDvkd().equals("100004") && sanpham.getMasanpham().trim().toUpperCase().indexOf("CONE") >= 0;

				 
				changeSpCore = isSpCone && !prev_tensp.equals(temp_tensp);
				prev_tensp = temp_tensp;
				  
				//System.out.println("hdBean.getDvkdId(): " +hdBean.getDvkdId());
				//Tạo _tenList
				xuLyTenList(hdBean, _tenList, sanpham, spIndex, tenSp, qcSp, sokytu1sp, prev_tensp, temp_tensp, changeSpCore);
				
				if (isSpCone) {
					// IN SẢN PHẨM CONE

					int beginIndex = 0;
					if (changeSpCore) {
						//Tìm thông tin sp cone trong listsanphamCone
						double sl = Double.parseDouble(sanpham.getSoluong().replace(",", ""));
						String key = sanpham.getMasanpham() + sanpham.getQuycachsanpham();
						ISanpham _spTemp;
						for(int z = 0; z < hdBean.GetListSanPhamCone().size(); z++) {
							_spTemp =  hdBean.GetListSanPhamCone().get(z);
							if(_spTemp != null && _spTemp.getId().equals(key)) {
								sl = Double.parseDouble(_spTemp.getSoluong().replace(",", ""));
							}
						}
						double _thanhtien = sl * Double.parseDouble(sanpham.getDongia().replace(",", "")); 
						
						stt++;
						// Cột stt sản phẩm
						cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
						cell.setValue("" + (stt));
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
						// Cột Đơn vị tính
						cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonvitinh());
						// Cột Số lượng
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(sl)));
						// Cột Đơn giá
						cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(Double.parseDouble(sanpham.getDongia().replace(",", "")))));
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
					if(_tenList.size() >= beginIndex) {
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

				}else if(sanpham.getDvkd().trim().equals("100004")){
					// các loại ống giấy còn lại
					//***************************************ỐNG LÕI KHÁC ỐNG CONE*************************************************
					stt++;	
					
					ttsp = sanpham.getTenXHD().split("}");

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
						cell.setValue(sanpham.getDonvitinh());
						// Cột Số lượng
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(Double.parseDouble(sanpham.getSoluong().replace(",", "")))));
						// Cột Đơn giá
						cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(Double.parseDouble(sanpham.getDongia().replace(",", "")))));
						// Cột Thành tiền
						cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
						cell.setValue(formatVN(formatter.format(thanhtien)));
						rowIndex++;
						
						
					//**********************************************************************************************
				}
				else {
					// CÁC SẢN PHẨM CÒN LẠI / TSCD / CHI PHI
					stt++;

					// In dong 1
					// Cột stt sản phẩm
					cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
					
					//System.out.println(S2_START_INDEX + rowIndex);
					cell.setValue("" + (stt));
					// Cột tên sản phẩm
					cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
					
					if(sanpham.getTenHD().equals("")){
						cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					}else{
						cell.setValue(sanpham.getTenHD());
					}
					
					// Cột Đơn vị tính
					cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonvitinh());
					// Cột Số lượng
					cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(Double.parseDouble(sanpham.getSoluong().replace(",", "")))));
					// Cột Đơn giá
					cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(Double.parseDouble(sanpham.getDongia().replace(",", "")))));
					// Cột Thành tiền
					cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(thanhtien)));

					int bien=1;
						
					
					if(sanpham.getDvkd().equals("100000")){
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

				spIndex++;
			}
			
						
			double tienSauCKKM = total_amount ;
			double vat = Math.round(Double.parseDouble(hdBean.getVat()));
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT))
					+ "./.";
			String ngaythangnam = hdBean.getNgaymuahang();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);
		 

			cell = cells1.getCell("D42");
			cell.setValue(formatVN(formatter.format(vat)));
			
			NumberFormat formatter_vnd = new DecimalFormat("###,###,###,###,###");
			if(hdBean.getTienTe_FK().equals("100000")){
			
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

	}
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
	}*/

	public static String formatVN(String so) {

		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");

		return result;

	}

	/*public static void main(String[] arg) throws DocumentException, IOException {
		String so = "100,100,100.213123";
		String a = formatVN(so);
		System.out.println(a);
	}*/

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
	
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
	
}
