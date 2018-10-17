package geso.traphaco.erp.servlets.lenhsanxuatgiay;

/*import geso.traphaco.center.servlets.report.ReportAPI;*/
import geso.traphaco.erp.servlets.baocao.ReportAPI;
import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.traphaco.center.beans.thongtinsanpham.imp.Thongtinsanpham;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.imp.ErpCongTy;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.indondathang.IDonDatHang;
import geso.traphaco.erp.beans.indondathang.imp.DonDatHang;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.nhacungcap.IErpNhaCungCap;
import geso.traphaco.erp.beans.nhacungcap.imp.ErpNhaCungCap;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapKhoLsx;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapKhoLsx;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Thongtinsanphamgiay;
import geso.traphaco.erp.beans.timnhacc.imp.Sanpham;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Line;
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
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;

public class ErpInLenhSanXuatPdfMau2Svl extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public ErpInLenhSanXuatPdfMau2Svl() {
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
			Create_PO_PDF(response, request);
		}
	
		
		if(action.equals("printExcel")){
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=INLenhsanxuat.xlsm");
			try {
				CreateExcel(response,request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			Create_PO_PDF(response, request);
		}
		System.out.println("Action = " + action);

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
		" inline; filename=Traphaco_InLenhSanXuatMau2.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 1.5f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT;
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
/*
	private void CreatePO_Training(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db)
	throws UnsupportedEncodingException {
		float CONVERT = 28.346457f;// 


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

		//lay lenh san xuat
		IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(id);
		lsxBean.setUserId(userId); //phai co UserId truoc khi Init
		lsxBean.init();


		String donvisx="";
		String tensp="";
		String masp="";
		String dangbaoche=""; //dang thuoc
		String soluonglosx=lsxBean.getSoluong();
		String madmvt=""; // dinh muc vat tu la  BOM
		String quytrinhdonggoi="";
		String sdk="";
		String solenh=lsxBean.getId();
		String ngaybatdau=lsxBean.getNgaybatdau_new()==null?"":lsxBean.getNgaybatdau_new();
		String canculenh= "";
		if(lsxBean.getCanCuLamLenh()!=null){
			canculenh= lsxBean.getCanCuLamLenh();
		}
		String ngaygiaosp=""; // cong thuc ngay giao
		
		
		String solo=lsxBean.getghichu(); // lay ghi chu 11_11_2016
		String ngaytao=lsxBean.getNgaytao();

		//lay thong tin solenh, so dang ki, can cu lenh
		String qr_sdk="select isnull(SOLENHSANXUAT,'') as solenhsanxuat,isnull(SDK,'') as sdk,isnull(CANCULAMLENH,'') as canculenh "+
		" from ERP_LENHSANXUAT_HOSOLO where LENHSANXUAT_FK ="+lsxBean.getId();

		System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
		ResultSet rs_sdk=db.get(qr_sdk);
		if(rs_sdk!=null){
			try {
				while(rs_sdk.next()){
					//solenh=rs_sdk.getString("solenhsanxuat");
					sdk=rs_sdk.getString("sdk");
					//canculenh=rs_sdk.getString("canculenh");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String ngayketthuchieuluc="";
		String qr_sdk=" select top 1 sodangki, ngayketthuchieuluc from erp_sanpham_nhacungcap  where sanpham_fk =  "+ lsxBean.getSpId();
		

		System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
		ResultSet rs_sdk=db.get(qr_sdk);
		if(rs_sdk!=null){
			try {
				while(rs_sdk.next()){
					
					sdk=rs_sdk.getString("sodangki")==null?"": rs_sdk.getString("sodangki");
					ngayketthuchieuluc=rs_sdk.getString("ngayketthuchieuluc")==null?"":rs_sdk.getString("ngayketthuchieuluc");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		//lay ten san pham
		String songayhoanthanh="0";
		String donvi="";
		String idsp=lsxBean.getSpma();
		System.out.print("ma san pham: "+ idsp);
		ResultSet rsTenSP = lsxBean.getSpRs();
		if (rsTenSP != null)
		{
			try
			{
				while (rsTenSP.next())
				{
					if (rsTenSP.getString("ma").equals(lsxBean.getSpma())){
						tensp = rsTenSP.getString("tentrenform");
						System.out.print("ten san pham: "+ tensp);
						if(rsTenSP.getString("NGAYHOANTHANH") !=null)
							songayhoanthanh=rsTenSP.getString("NGAYHOANTHANH") ;
					
						donvi= rsTenSP.getString("donvi");
						
					}
				}
				rsTenSP.close();
			}
			catch (SQLException e)
			{
				System.out.println("Loi");
			}
		}

		
		// tinhs ngay hoan thanh
		if(songayhoanthanh.length()>0)
		if(Integer.valueOf(songayhoanthanh) >0 )
		{
			ngaygiaosp = ngaybatdau;  // Start date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(ngaygiaosp));
				c.add(Calendar.DATE,Integer.valueOf(songayhoanthanh));  // number of days to add
				ngaygiaosp = sdf.format(c.getTime());  // dt is now the new date
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		


		

		//lay don vi san xuat = phan xuong
		ResultSet nhamayRs = lsxBean.getNhamayRs();
		if(nhamayRs != null)
		{
			try
			{
				while(nhamayRs.next())
				{  
					if( nhamayRs.getString("pk_seq").equals(lsxBean.getNhamayId()))
					{ 
						donvisx=nhamayRs.getString("tennhamay");
						nhamayRs.close();
					}
				}
			} catch(SQLException ex){}
		}	

		//lay ma dinh muc vat tu DMVT: TEN BOM
		String qr_bom="select TENBOM, isnull(quycach,'') as quycach FROM ERP_DANHMUCVATTU WHERE PK_SEQ="+lsxBean.getBomId();
		ResultSet rsBom = db.get(qr_bom);
		System.out.println(" lay ten bom :"+ qr_bom);
		if(rsBom!=null){
			try {
				while(rsBom.next()) {
					madmvt=rsBom.getString("TENBOM");
					quytrinhdonggoi=rsBom.getString("quycach");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//lay QUY CACH DONG GOI 
		ReportAPI report= new ReportAPI();
		quytrinhdonggoi=report.getQuycachSp(db, lsxBean.getSpma());


		//lay dang bao che dang thuoc
		String qrdangthuoc="select b.ten as dangbaoche from erp_sanpham a inner join DANGBAOCHE b on a.dangbaoche=b.pk_seq where a.pk_seq="+ lsxBean.getSpId();
		System.out.print("\n ql dang bao che: "+ qrdangthuoc +"\n");
		ResultSet rs_dangthuoc=db.get(qrdangthuoc);
		if(rs_dangthuoc!=null)
		{ 
			try {
				while(rs_dangthuoc.next())
				{
					dangbaoche=rs_dangthuoc.getString("dangbaoche");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		

		/////IN RA

		try {
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 

			PdfWriter.getInstance(document, outstream);
			document.open();
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			//dinh dang font
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			font9_italic.setColor(79, 79, 79);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
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


			//TAO HEADER 1
			PdfPTable tab_Header = new PdfPTable(3);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell2,cellss;
			Chunk chunk;

			cell2 = new PdfPCell(new Phrase("", font11_bold));
			// insert logo traphaco
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint")+ "\\logo.gif");
			img.scalePercent(10);
			cell2 = new PdfPCell();
			cell2.addElement(new Chunk(img, 30, 0));
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_Header.addCell(cell2);

			PdfPCell cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph("BM 28/05", font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);


			font11_normal.setColor(105, 105, 105);
			cell2 = new PdfPCell(new Phrase("CÔNG TY CP TRAPHACO", font11_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(0.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph("BH/SĐ:01/01/2017", font9_italic));
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			font11_normal.setColor(105, 105, 105);
			cell2 = new PdfPCell(new Phrase("         Số: "+solenh, font11_italic));
			cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(2.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			document.add(tab_Header);

			//TUA DE
			Paragraph tuade = new Paragraph("LỆNH SẢN XUẤT", new Font(bf, 20, Font.BOLD));
			tuade.setSpacingBefore(10);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);

			if(ngaytao.length()>0){
			Paragraph ngaythang = new Paragraph("Ngày "+ ngaytao.substring(8)+" tháng "+ngaytao.substring(5,7)+" năm "+ngaytao.substring(0,4), new Font(bf, 12, Font.ITALIC));
			ngaythang.setSpacingBefore(3);
			ngaythang.setSpacingAfter(3);
			ngaythang.setAlignment(Element.ALIGN_RIGHT);
			document.add(ngaythang);
			}
			else{
				Paragraph ngaythang = new Paragraph("Ngày            tháng               năm ", new Font(bf, 12, Font.ITALIC));
				ngaythang.setSpacingBefore(3);
				ngaythang.setSpacingAfter(3);
				ngaythang.setAlignment(Element.ALIGN_RIGHT);
				document.add(ngaythang);
			}
			
			//NOI DUNG
			PdfPTable tab_info = new PdfPTable(2);
			tab_info.setWidthPercentage(100);
			tab_info.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_info.setSpacingAfter(10);
			tab_info.setSpacingBefore(5);
			tab_info.setPaddingTop(10);

			cell = new PdfPCell(new Paragraph("Đơn vị SX: "+donvisx, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Tên sản phẩm: "+tensp, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Mã sản phẩm: "+idsp, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Số lô SX: " +solo, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Dạng bào chế: "+dangbaoche, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Số lượng lô SX: " + formatter.format( Double.parseDouble(soluonglosx) ) +" " + lsxBean.getDvtBOM(), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);


			cell = new PdfPCell(new Paragraph("Mã ĐMVT: " +madmvt, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("QC đóng gói: " +quytrinhdonggoi, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Hiệu lực: " +  formatngay (ngaybatdau) +" - " + formatngay (ngayketthuchieuluc), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph(" " , font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("SĐK: " +sdk, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Ngày bắt đầu: "+ formatngay (ngaybatdau) , font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Căn cứ  làm lệnh: "+canculenh, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Ngày giao SP: " + formatngay (ngaygiaosp), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);




			document.add(tab_info);

			//___________BANG DU LIEU_______________

			float[] crDonHang = { 0.5f * CONVERT,1.0f * CONVERT, 3.0f * CONVERT,
					1.0f * CONVERT, 1.4f * CONVERT,0.7f*CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT};
			
			float[] crDonHang = { 0.5f * CONVERT,0.9f * CONVERT, 4.0f * CONVERT,
					0.7f * CONVERT, 1.4f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT};
			
			float[] crDonHang = { 0.8f * CONVERT,2.0f * CONVERT, 5.5f * CONVERT,
					1.0f * CONVERT, 2.2f * CONVERT, 1.5f * CONVERT,
					1.5f * CONVERT, 2.5f * CONVERT};

			PdfPTable tab_lenhsx = new PdfPTable(crDonHang.length);
			tab_lenhsx.setWidthPercentage(100);
			tab_lenhsx.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_lenhsx.setWidths(crDonHang);
			tab_lenhsx.getDefaultCell().setBorder(0);
			tab_lenhsx.setSpacingAfter(8.0f);
			tab_lenhsx.setSpacingBefore(6);


			//in tieu de bang
			String[] spTitles = { "TT","MÃ VT","TÊN NGUYÊN LIỆU", "ĐƠN VỊ", "SỐ PKN" ,"SỐ LƯỢNG","GHI CHÚ"};
			for (int z = 0; z < spTitles.length; z++) {
				if(z==5){
					cell = new PdfPCell(new Paragraph(spTitles[z], font10_bold));
					cell.setPadding(3.0f);
					cell.setColspan(2); //gop cot
					//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_lenhsx.addCell(cell);
				}
				else
				{
					cell = new PdfPCell(new Paragraph(spTitles[z], font10_bold));
					cell.setPadding(3.0f);
					cell.setRowspan(2); //gop dong
					//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					//cell.setBorderColorBottom();
					tab_lenhsx.addCell(cell);
				}
			}
			cell = new PdfPCell(new Paragraph("Đ.Mức", font10_bold));
			cell.setPadding(3.0f);
			//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_lenhsx.addCell(cell);

			cell = new PdfPCell(new Paragraph("Thực", font10_bold));
			cell.setPadding(3.0f);
			//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_lenhsx.addCell(cell);


			//------------------------lay danh sach vat tu------------------------
			List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>)lsxBean.getListDanhMuc();
			int  j =0;  
			int sott = 1;
			if(vattuList!=null)
				while (j< vattuList.size()) { 
					IDanhmucvattu_SP vt=vattuList.get(j);
					String sldinhmuc="";
					String slthuc="";
					String pkn="";
					String ghichu=vt.getGhichu();
					String hamluong=vt.getIsTinhHL();
					//lay phieu kiem nghiem

					List<ISpSanxuatChitiet> list_ct=vt.getListCT_DaYCCK();				
					if(list_ct!=null)							
					{
						String pkn_ar[]= new String [list_ct.size()];
						for(int t=0;t<list_ct.size();t++)
						{
							ISpSanxuatChitiet sp=list_ct.get(t);
							if(sp.getMAPHIEU()!=null)
							{
								pkn_ar[t]=sp.getMAPHIEU().trim();
							}

						}

						//loai bo pt giong nhau
						for (int i = 0; i < list_ct.size(); i++)
						{
							for (int k = i+1; k <list_ct.size(); k++) 
							{
								if(pkn_ar[i].equals(pkn_ar[k]))
								{
									pkn_ar[k]="";
								}
							}
						}
						//lay ra chuoi
						for (int i = 0; i < list_ct.size(); i++)
						{
							if(pkn_ar[i].length()>0)
								pkn+=pkn_ar[i]+"; ";
						}											
					}									

					pkn=pkn.trim(); // neu cuoi la ',' thi cat bo
					if(pkn.endsWith(";")){
						pkn=pkn.substring(0,pkn.length()-1);
					}


					//LAY SO LUONG DINH MUC CUA VAT TU THEO SO LUONG YEU CAU
					//NEU SO LUONG YEU CAU KHONG CO THI LAY SO LUONG TRONG BOM
					String qr="SELECT  SANPHAM_FK as masp, SUM(SOLUONG) as soluongdinhmuc FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET "+  
					"	where LENHSANXUAT_FK="+ id  +" GROUP BY SANPHAM_FK";
					String slyeucau="";
					String slbom="";
					String qr_dinhmuc=" select  VATTU_FK as masp, SOLUONGCHUAN as soluongdinhmuc, SOLUONG AS SOLUONGYEUCAU  from ERP_LENHSANXUAT_BOM_GIAY  where LENHSANXUAT_FK="+id ;
					
					System.out.println("\n lenh qr so luong dinh muc: "+ qr_dinhmuc);
					ResultSet rs_dinhmuc=db.get(qr_dinhmuc);
					String mavattu=vt.getIdVT();
					System.out.println("\n ma pk_seq vat tu: "+ mavattu);

					if(rs_dinhmuc!=null){
						try {
							while(rs_dinhmuc.next())
							{
								System.out.println("\n ma vat tu: "+ rs_dinhmuc.getString("masp"));
								if(rs_dinhmuc.getString("masp").equals(mavattu))
								{
									sldinhmuc=rs_dinhmuc.getString("soluongdinhmuc")==null? "": rs_dinhmuc.getString("soluongdinhmuc");
									System.out.println("\n so luong dinh muc: "+ sldinhmuc);
								   // slyeucau=rs_dinhmuc.getString("SOLUONGYEUCAU") ==null?"0": rs_dinhmuc.getString("SOLUONGYEUCAU");
								    slbom=rs_dinhmuc.getString("soluongdinhmuc")== null?"0": rs_dinhmuc.getString("soluongdinhmuc");
							    	
								    if(Double.parseDouble(slyeucau)>0){
							    		sldinhmuc=slyeucau;
							    	}
								    else{
								    	sldinhmuc=slbom;
								    }
								    
								    sldinhmuc=slbom;
								    
								}
							}
							rs_dinhmuc.close();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					
					// lay lai so luong yeu cau
					
					String sql="SELECT SUM(SOLUONG) as SOLUONG FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET   where LENHSANXUAT_FK=" +id +"   and vattu_fk="+ vt.getIdVT() ;
					System.out.println(" soluong yc: "+ sql);
					ResultSet slRS=db.get(sql);
					if(slRS!= null){
						try {
							while(slRS.next()){
								slyeucau=slRS.getString("SOLUONG")==null?"":slRS.getString("SOLUONG");
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					slRS.close();
					
					//-----------------------------------------------

					//---------LAY SO LUONG THUC ( SO LUONG TIEU HAO )
					String qr_sltieuhao="SELECT sp.PK_SEQ as pk_seq, SP.MA as masp, SP.TEN as tensp, LSX_TH.SOLUONG as soluongtieuhao, TH.LENHSANXUAT_FK as lenhsx  "+ 
					"	FROM ERP_TIEUHAONGUYENLIEU TH  "+ 
					"	INNER JOIN ERP_LENHSANXUAT_TIEUHAO LSX_TH ON TH.PK_SEQ=LSX_TH.TIEUHAONGUYENLIEU_FK "+ 
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=LSX_TH.SANPHAM_FK "+ 
					"WHERE TH.LENHSANXUAT_FK="+ id;
					System.out.println("\n lenh qr so luong thuc (tieu hao): "+ qr_sltieuhao);
					ResultSet rs_tieuhao=db.get(qr_sltieuhao);
					if(rs_tieuhao!=null){
						try {
							while(rs_tieuhao.next())
							{
								System.out.println("\n ma vat tu: "+ rs_tieuhao.getString("pk_seq"));
								if(rs_tieuhao.getString("pk_seq").equals(mavattu))
								{
									slthuc=rs_tieuhao.getString("soluongtieuhao");
									System.out.println("\n so luong tieu hao: "+ slthuc);
								}
							}
							rs_tieuhao.close();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					// yeu cau so luong thuc : 
					//+ neu sl dinh muc= sl yeu cau (bom
					
					System.out.println(" so luong yeu cau: "+ slyeucau);
					System.out.println(" so luong bom: "+ slbom +" \n \n ");
					 
					if(slyeucau.equals("0") ||slyeucau.equals("") )
					{
						slthuc="";
					}
					else
						if( Double.parseDouble(slyeucau.replaceAll(",", "")) ==  Double.parseDouble( slbom.replace(",", "") ) ){
							slthuc="";
						}
						else{
							slthuc=slyeucau;
						}

					NumberFormat formatter_2sole = new DecimalFormat("#,###,###.###");

					if(sldinhmuc!=null && sldinhmuc!="")
					{
						sldinhmuc=formatter_2sole.format(Double.parseDouble(sldinhmuc));
					}

					if(slthuc!=null && slthuc!="")
					{
						slthuc=formatter_2sole.format(Double.parseDouble(slthuc));
					}
					
					

					//-----------------------in ra------------------------------------
					String[] spTitles2 = {sott + "", vt.getMaVatTu(),vt.getTenVatTu(),vt.getDvtVT(),pkn,sldinhmuc,slthuc, vt.getGhichu()};

					
					System.out.println(" vat tu thay the: " + vt.getIdVatTuThayThe());
					String[] spTitles2 = {sott + "", "ABCDEFDH",vt.getTenVatTu(),vt.getDvtVT(),pkn,sldinhmuc,slthuc, vt.getGhichu()};

					
					for (int z = 0; z < spTitles2.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles2[z],new Font(bf, 10,Font.NORMAL)));
						cell.setPadding(3.0f);
						if(z==1||z==2){
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else if(z==5 ||z==6){
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab_lenhsx.addCell(cell);
					}
					sott++;
					j++;
				}
			document.add(tab_lenhsx);		



			//------------bang chu ki---------------------
			float[] wky = {9.0f*CONVERT, 8.8f*CONVERT, 8.8f*CONVERT,6.0f*CONVERT };
			PdfPTable tbl_vat = new PdfPTable(wky.length);
			tbl_vat.setWidthPercentage(100);
			tbl_vat.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbl_vat.getDefaultCell().setBorder(0);
			tbl_vat.setWidths(wky);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			cellss.setPaddingLeft(0.3f*CONVERT);
			chunk =new Chunk("P. TỔNG GIÁM ĐỐC", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n \n  NGUYỄN THỊ LAN", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);


			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("           TP. ĐBCL", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n \n NGUYỄN THỊ VÂN ANH", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("      TP. KẾ HOẠCH", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n \n NGUYỄN VĂN NHƯỢNG", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("NGƯỜI NHẬN", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			document.add(tbl_vat);

			// FOOTER
			Paragraph para_footer;
		para_footer= new Paragraph("* Ghi chú:", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("-Số lượng định mức: Theo ĐMVT", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("-Số lượng thực (Số lượng đã qui đổi): Số lượng đã bù hàm lượng, hoặc hàm ẩm (nếu có)", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("* Đề nghị :", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("- Cần theo dõi tổng số LSX heo sản phẩm (Viên nén, Nang mềm…)", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("- Cần đặt công thức tính ngày giao SP = ngày bắt đầu + số ngày cần thiết để hoàn thành một SP cụ thể", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("* Một số công thức tính nguyên liệu:", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		para_footer= new Paragraph("1. Tratrison: Tính Gentamicinsulfat  ", font12_italic);
		para_footer.setSpacingBefore(2);
		para_footer.setSpacingAfter(2);
		para_footer.setAlignment(Element.ALIGN_LEFT);
		document.add(para_footer);

		//cong thuc 
		PdfPTable congthuc = new PdfPTable(2); //2 cot add 4 cell
		congthuc.setWidthPercentage(100);
		float[] withsheader = {30.0f, 65.0f};//SET DO RONG CAC COT
		congthuc.setWidths(withsheader);
		congthuc.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell=new PdfPCell();
		Paragraph pr22 = new Paragraph(" ",font12_italic);

		Chunk n2 = new Chunk("        đơn vị gam = "); 
		n2.setTextRise(-5.0f);
	    pr22.add(n2);

		Chunk n1 = new Chunk("1932 x 10");
	    n1.setUnderline(1f, -2f); //gach chan(withd,vi tri)
	    pr22.add(n1);

		Chunk n3 = new Chunk("4");
	    n3.setTextRise(5.0f);//so mu  (vi tri)
	    pr22.add(n3);
	    cell.addElement(pr22);

		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(0);
		congthuc.addCell(cell);

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(0);
		Paragraph pr = new Paragraph("Trong đó: x là hàm ẩm của nguyên liệu theo PKN",font12_italic);
		pr.setSpacingBefore(2);
		//pr.setSpacingAfter(2);
		pr.setAlignment(Element.ALIGN_LEFT);
		cell.addElement(pr);
		congthuc.addCell(cell);

		cell = new PdfPCell(new Paragraph("                         y(100-x)",font12_italic));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBorder(0);
	    congthuc.addCell(cell);

	    Paragraph pr1 = new Paragraph("y là hàm lượng Gentamicin tính theo chế  phẩm khan(IU/mg)",font12_italic);
		//pr1.setSpacingBefore(2);
		pr1.setSpacingAfter(2);
		pr1.setAlignment(Element.ALIGN_LEFT);
		cell.addElement(pr1);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(0);
		congthuc.addCell(cell);


		document.add(congthuc);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}

	}

	*/
	
	private boolean CreateExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
		OutputStream out = response.getOutputStream();

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

		//lay lenh san xuat
		IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(id);
		lsxBean.setUserId(userId); //phai co UserId truoc khi Init
		lsxBean.init();
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\BCLenhSanXuat.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			dbutils db = new dbutils();



			// ___________________________________________

			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
			NumberFormat formatter_1sole = new DecimalFormat("#,###,###.#");
			NumberFormat formatter = new DecimalFormat("#,###,###");

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			worksheet.setName("Sheet1");
			Style style;


//			String query = "";
//			
//			// header
//			// thong tin khách hang	
//			ResultSet NLlist = (ResultSet)obj.getNLlist();
//			String ngaytao="";
//			String ngaysua="";
//			String diengiai="";
//			String nguoitao="";
//			String nguoisua="";
//			
//			while (NLlist.next()){
//				 
//				diengiai=NLlist.getString("diengiai") ;
//				 ngaytao=NLlist.getString("ngaytao") ;
//				 NLlist.getString("nguoitao") ;
//				 ngaysua=NLlist.getString("ngaysua") ;
//				 nguoisua=NLlist.getString("nguoisua") ;
//			}
			String donvisx="";
			String tensp="";
			String masp="";
			String dangbaoche=""; //dang thuoc
			String soluonglosx=lsxBean.getSoluong();
			String madmvt=""; // dinh muc vat tu la  BOM
			String quytrinhdonggoi="";
			String sdk="";
			String solenh=lsxBean.getId();
			String ngaybatdau=lsxBean.getNgaybatdau_new()==null?"":lsxBean.getNgaybatdau_new();
			String canculenh= "";
			if(lsxBean.getCanCuLamLenh()!=null){
				canculenh= lsxBean.getCanCuLamLenh();
			}
			String ngaygiaosp=""; // cong thuc ngay giao
			
			
			String solo=lsxBean.getghichu(); // lay ghi chu 11_11_2016
			String ngaytao=lsxBean.getNgaytao();
			String ngayketthuchieuluc="";
			String qr_sdk=" select top 1 sodangki, ngayketthuchieuluc from erp_sanpham_nhacungcap  where sanpham_fk =  "+ lsxBean.getSpId();
			

			System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
			ResultSet rs_sdk=db.get(qr_sdk);
			if(rs_sdk!=null){
				try {
					while(rs_sdk.next()){
						
						sdk=rs_sdk.getString("sodangki")==null?"": rs_sdk.getString("sodangki");
						ngayketthuchieuluc=rs_sdk.getString("ngayketthuchieuluc")==null?"":rs_sdk.getString("ngayketthuchieuluc");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String songayhoanthanh="0";
			String donvi="";
			String idsp=lsxBean.getSpma();
			System.out.print("ma san pham: "+ idsp);
			ResultSet rsTenSP = lsxBean.getSpRs();
			if (rsTenSP != null)
			{
				try
				{
					while (rsTenSP.next())
					{
						if (rsTenSP.getString("ma").equals(lsxBean.getSpma())){
							tensp = rsTenSP.getString("tentrenform");
							System.out.print("ten san pham: "+ tensp);
							if(rsTenSP.getString("NGAYHOANTHANH") !=null)
								songayhoanthanh=rsTenSP.getString("NGAYHOANTHANH") ;
						
							donvi= rsTenSP.getString("donvi");
							
						}
					}
					rsTenSP.close();
				}
				catch (SQLException e)
				{
					System.out.println("Loi");
				}
			}

			
			// tinhs ngay hoan thanh
			if(songayhoanthanh.length()>0)
			if(Integer.valueOf(songayhoanthanh) >0 )
			{
				ngaygiaosp = ngaybatdau;  // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(sdf.parse(ngaygiaosp));
					c.add(Calendar.DATE,Integer.valueOf(songayhoanthanh));  // number of days to add
					ngaygiaosp = sdf.format(c.getTime());  // dt is now the new date
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			


			

			//lay don vi san xuat = phan xuong
			ResultSet nhamayRs = lsxBean.getNhamayRs();
			if(nhamayRs != null)
			{
				try
				{
					while(nhamayRs.next())
					{  
						if( nhamayRs.getString("pk_seq").equals(lsxBean.getNhamayId()))
						{ 
							donvisx=nhamayRs.getString("tennhamay");
							nhamayRs.close();
						}
					}
				} catch(SQLException ex){}
			}	

			//lay ma dinh muc vat tu DMVT: TEN BOM
			String qr_bom="select TENBOM, isnull(quycach,'') as quycach FROM ERP_DANHMUCVATTU WHERE PK_SEQ="+lsxBean.getBomId();
			ResultSet rsBom = db.get(qr_bom);
			System.out.println(" lay ten bom :"+ qr_bom);
			if(rsBom!=null){
				try {
					while(rsBom.next()) {
						madmvt=rsBom.getString("TENBOM");
						quytrinhdonggoi=rsBom.getString("quycach");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			//lay QUY CACH DONG GOI 
			/*ReportAPI report= new ReportAPI();
			quytrinhdonggoi=report.getQuycachSp(db, lsxBean.getSpma());*/


			//lay dang bao che dang thuoc
			String qrdangthuoc="select b.ten as dangbaoche from erp_sanpham a inner join DANGBAOCHE b on a.dangbaoche=b.pk_seq where a.pk_seq="+ lsxBean.getSpId();
			System.out.print("\n ql dang bao che: "+ qrdangthuoc +"\n");
			ResultSet rs_dangthuoc=db.get(qrdangthuoc);
			if(rs_dangthuoc!=null)
			{ 
				try {
					while(rs_dangthuoc.next())
					{
						dangbaoche=rs_dangthuoc.getString("dangbaoche");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			String ngaythang="";
			if(ngaytao.length()>0){
				 ngaythang = "Ngày "+ ngaytao.substring(8)+" tháng "+ngaytao.substring(5,7)+" năm "+ngaytao.substring(0,4);
			}
			
			cell = cells.getCell("J8");
			this.getCellStyle1(cell, Color.BLACK, true,12, ngaythang, false);
			
			cell = cells.getCell("B5");
			this.getCellStyle1(cell, Color.BLACK, true, 11, solenh, true);
			
			cell = cells.getCell("A10");
			this.getCellStyle1(cell, Color.BLACK, false, 11,"Đơn vị SX: "+ donvisx, true);
			
			cell = cells.getCell("F10");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Tên sản phẩm: "+ tensp, true);

			cell = cells.getCell("A11");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Mã sản phẩm: "+ idsp, true);

			cell = cells.getCell("F11");
			this.getCellStyle1(cell,  Color.BLACK, false, 11, "Số lô SX: "+ solo, true);

			cell = cells.getCell("A12");
			this.getCellStyle1(cell, Color.BLACK, false, 11, "Dạng bào chế: "+  dangbaoche, true);
			
			cell = cells.getCell("F12");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Số lượng lô SX: "+   formatter.format( Double.parseDouble(soluonglosx) ) +" " + lsxBean.getDvtBOM(), true);


			cell = cells.getCell("A13");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Mã ĐMVT: "+ madmvt, true);
			
			cell = cells.getCell("F13");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"QC đóng gói: "+  quytrinhdonggoi, true);

			
			cell = cells.getCell("A14");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"SĐK: "+  sdk, true);

			
			cell = cells.getCell("F14");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Ngày bắt đầu: "+  formatngay (ngaybatdau), true);
			
			cell = cells.getCell("A15");
			this.getCellStyle1(cell,  Color.BLACK, false, 11, "Căn cứ làm lệnh: "+ canculenh, true);


			cell = cells.getCell("F15");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Ngày giao SP: "+  formatngay (ngaygiaosp), true);
			
			List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>)lsxBean.getListDanhMuc();
			int  j =0;  
			int sott = 1;
			int countRow =  18;
			int kt=0; // kiem tra nguyen lieu da yeu cau
			
			//--------------------------------
			Color color_=Color.BLACK;
			if(vattuList!=null)
				while (j< vattuList.size()) {
					
					kt=0;
					IDanhmucvattu_SP vt=vattuList.get(j);
					String sldinhmuc="";
					String slthuc="";
					String pkn="";
					String ghichu=vt.getGhichu();
					String hamluong=vt.getIsTinhHL();
					String maspvattu=vt.getMaVatTu();
					String donvivt=vt.getDvtVT();
					System.out.print("sdasdsadsadsads "+vt.getMaVatTu() );
					// LAY NGUYEN LIEU GOC
					String sql= " SELECT  MAPHIEU  FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET" + 
							 	"\n  WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "   AND  VATTU_FK = SANPHAM_FK";
						
					pkn=LayPkn(sql, db);
					
					
					// lay so luong yeu cau =so luong 
					String qr_dinhmuc="SELECT   SUM(soluongchuan) as soluongdinhmuc, SUM(soluong) as soluongthuc FROM" + 
					  "\n ERP_LENHSANXUAT_BOM_GIAY_CHITIET " + 
					  "\n WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "   AND  VATTU_FK = SANPHAM_FK  having  SUM(soluongchuan) >0 and SUM(soluong) >0";
			         System.out.println(" san pham goc: "+ qr_dinhmuc);
					//LAY SO LUONG DINH MUC CUA VAT TU THEO SO LUONG YEU CAU
					//NEU SO LUONG YEU CAU KHONG CO THI LAY SO LUONG TRONG BOM

					System.out.println("\n lenh qr so luong dinh muc: "+ qr_dinhmuc);
					ResultSet rs_dinhmuc=db.get(qr_dinhmuc);
					String mavattu=vt.getIdVT();
					System.out.println("\n ma pk_seq vat tu: "+ mavattu);
					formatter_2sole = new DecimalFormat("#,###,###.###########");
					NumberFormat formatter_3sole = new DecimalFormat("#,###,###.###");
					

					if(rs_dinhmuc!=null){
						try {
							while(rs_dinhmuc.next())
							{
								kt=1; // vat tu da yeu cau nguyen lieu
								sldinhmuc=	vt.getSoLuongChuan(); // lay so luong dinh muc cua nguyen lieu goc
								slthuc =rs_dinhmuc.getString("soluongthuc")== null?"0": rs_dinhmuc.getString("soluongthuc");	 
							
								
								System.out.println(" so luong dinh muc: "+ sldinhmuc);
								System.out.println(" so luong slthuc: "+ slthuc +" \n \n ");
								 
								if(slthuc.trim().equals("0") ||slthuc. trim().equals(""))
								{
									slthuc="";
								}
								else
									if( Double.parseDouble(slthuc.replaceAll(",", "")) ==  Double.parseDouble( sldinhmuc.replace(",", "") ) ){
										slthuc="";
									}
									
								

								if(sldinhmuc!=null && sldinhmuc!="")
								{
									sldinhmuc=formatter_2sole.format(Double.parseDouble(sldinhmuc));
								}

								if(slthuc!=null && slthuc!="")
								{
									slthuc=formatter_3sole.format(Double.parseDouble(slthuc));
								}
								
							
								slthuc=	formatSlthuc(slthuc);
								
								
									
							    String[] spTitles2 = {sott + "", vt.getMaVatTu(),vt.getTenVatTu(),"","",vt.getDvtVT(),pkn,sldinhmuc,slthuc, vt.getGhichu()};
							    
								for (int z = 0; z < spTitles2.length; z++) {
									cell = cells.getCell(countRow,z);
									System.out.print("hellloo "+countRow +" "+z);
									ReportAPI.getCellStyle(cell, "Arial", color_, false, 9, spTitles2[z]);
									if(z==2){
										cells.merge(countRow, 2, countRow, 4);
										z=z+2;
										for (int i = 2; i < 5; i++) {
											cell = cells.getCell(countRow,i);
											setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);	
										}
									}
								}
								countRow++;
								sott++;
							}
							
							rs_dinhmuc.close();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					//==================LAY NGUYEN THAY THE=================
					 sql="select DV.DONVI,DV.DIENGIAI AS DIENGIAIDV, data1.LENHSANXUAT_FK, data1.SANPHAM_FK,  b.TEN1 as tenvattu, b.MA as mavattu, data1.soluongthuc  from " + 
						  "\n (select LENHSANXUAT_FK,SANPHAM_FK, soluongthuc from" + 
						  "\n (" + 
						  "\n SELECT  LENHSANXUAT_FK,SANPHAM_FK, SUM(soluong) as soluongthuc  FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET" + 
						  "\n WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "   AND  VATTU_FK <> SANPHAM_FK" + 
						  "\n GROUP BY LENHSANXUAT_FK,SANPHAM_FK ) data ) data1" + 
						  "\n inner join ERP_SANPHAM b on b.PK_SEQ=data1.SANPHAM_FK \n  LEFT join DONVIDOLUONG DV ON DV.PK_SEQ= b.DVDL_FK";
					
					 
					    System.out.println(" san pham thay the: "+ sql);
						ResultSet rs_nglieuthaythe=db.get(sql);
			
						if(rs_nglieuthaythe!=null){
							try {
							while(rs_nglieuthaythe.next()){
								
								kt =1;
								
								String idspthaythe=rs_nglieuthaythe.getString("SANPHAM_FK");
								String maspthaythe=rs_nglieuthaythe.getString("mavattu");
								String tenspthaythe=rs_nglieuthaythe.getString("tenvattu");
								String donvispthaythe=rs_nglieuthaythe.getString("DONVI");
								String sldinhmuc1="";
								String slthuc1="";
								String pkn1="";
								
								// tim so luong dinh muc tu ERP_DANHMUCVATTU_VATTU_THAYTHE
								
								String qr_dinhmucthaythe="select top 1 SOLUONGDINHMUC from ERP_LENHSANXUAT_BOM_GIAY_CHITIET where LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT()+"  AND SANPHAM_FK="+idspthaythe ;
								System.out.println(" so luong sldinhmuc thay the: "+ qr_dinhmucthaythe +" \n \n ");
								
								ResultSet rs_dinhmucthaythe=db.get(qr_dinhmucthaythe);
								if( rs_dinhmucthaythe != null){
									try {
										while (rs_dinhmucthaythe.next()){
											sldinhmuc1=rs_dinhmucthaythe.getString("SOLUONGDINHMUC")== null?"0": rs_dinhmucthaythe.getString("SOLUONGDINHMUC");	
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								rs_dinhmucthaythe.close();
								/*sldinhmuc1=String.valueOf(rs_nglieuthaythe.getDouble("soluongdinhmuc"));*/
								slthuc1 =rs_nglieuthaythe.getString("soluongthuc")== null?"0": rs_nglieuthaythe.getString("soluongthuc");
								
								// lay phieu kiem nghiem
								sql= " SELECT  MAPHIEU  FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET" + 
							 	"\n WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "    AND  SANPHAM_FK = "+idspthaythe;
								pkn1= LayPkn(sql, db);
							
								
								// lay so luong chuan, dih muc
								System.out.println(" so luong dinh muc thay the: "+ sldinhmuc1);
								System.out.println(" so luong slthuc thay the: "+ slthuc1 +" \n \n ");
								if(sldinhmuc1==null || sldinhmuc1==""){
									sldinhmuc1="0.0";
								}
								
								if(slthuc1.trim().equals("0") ||slthuc1. trim().equals(""))
								{
									slthuc1="";
								}
								else
									if( Double.parseDouble(slthuc1.replaceAll(",", "")) ==  Double.parseDouble( sldinhmuc1.replace(",", "") ) ){
										slthuc1="";
									}
								
								if(sldinhmuc1!=null && sldinhmuc1!="")
								{
									sldinhmuc1=formatter_2sole.format(Double.parseDouble(sldinhmuc1));
								}
		
								if(slthuc1!=null && slthuc1!="")
								{
									slthuc1=formatter_3sole.format(Double.parseDouble(slthuc1));
								}
								slthuc1=formatSlthuc(slthuc1);
								
								System.out.println(" so luong dinh muc thay the: "+ sldinhmuc1);
						    // in sp thay the
						  //  String [] spTitles3 = {sott + "", maspthaythe,tenspthaythe,vt.getDvtVT(),pkn1,sldinhmuc1,slthuc1, vt.getGhichu()};
						    String [] spTitles3 = {sott + "", maspthaythe,tenspthaythe ," "," ",donvispthaythe,pkn1,sldinhmuc1,slthuc1, ghichu};

						    for (int z = 0; z < spTitles3.length; z++) {
								cell = cells.getCell(countRow,z);
								System.out.print("hellloo "+countRow +" "+z);
								ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 10, spTitles3[z]);
								
							   
								
								if(z==2){
									cells.merge(countRow, 2, countRow, 4);
									z=z+2;
									for (int i = 2; i < 5; i++) {
										cell = cells.getCell(countRow,i);
										setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);	
									}
								}
								
								
							}
						    countRow++;
							sott++;
							
							sott++;
							}
							
							rs_nglieuthaythe.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
						
					System.out.println(" stt thay the : "+ sott);
		
						
						//------------------- NEU CHUA YEU CAU NGUYEN LIEU
					if(kt==0)
						{
						String[] spTitles2 = {sott + "", vt.getMaVatTu(),vt.getTenVatTu()," "," ",vt.getDvtVT(),"",formatter_2sole.format(Double.parseDouble( vt.getSoLuongChuan().replaceAll(",", "")))   ,"", vt.getGhichu()};
						 for (int z = 0; z < spTitles2.length; z++) {
								cell = cells.getCell(countRow,z);
								System.out.print("hellloo "+countRow +" "+z);
								ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 10, spTitles2[z]);
								
								
								if(z==2){
									cells.merge(countRow, 2, countRow, 4);
									z=z+2;
									for (int i = 2; i < 5; i++) {
										cell = cells.getCell(countRow,i);
										setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);	
									}
								}
								
								
							}
							countRow++;
							sott++;
						}
						
							
							j++;
						}

			


			countRow+=2;
			
			
	
			
			cell = cells.getCell("A"+countRow);
			cells.merge( (countRow -1),0, (countRow -1) , 2);
			this.getCellStyle(cell, Color.BLACK, true, 10,"T/L TỔNG GIÁM ĐỐC", HorizontalAlignmentType.CENTRED, false);
			
			
			
			cell = cells.getCell("A"+ (countRow+1));
			cells.merge( (countRow), 0,(countRow), 2);
			this.getCellStyle(cell, Color.BLACK, true, 10,"TP. Kế hoạch" ,HorizontalAlignmentType.CENTRED, false);
			
			
			
			cell = cells.getCell("A"+ (countRow+3));
			cells.merge( (countRow+2),0, (countRow+2), 2);
			this.getCellStyle(cell, Color.BLACK, false, 10,"NGUYỄN VĂN NHƯỢNG",HorizontalAlignmentType.CENTRED, false);
			
			
			
			
			cell = cells.getCell("D"+countRow);
			cells.merge( (countRow -1),3, (countRow -1), 5);
			this.getCellStyle(cell, Color.BLACK, true, 10,"NGƯỜI NHẬN",HorizontalAlignmentType.CENTRED, false);
			
			
			cell = cells.getCell("D"+(countRow +3));
			cells.merge( (countRow +2),3, (countRow +2) , 5);
			this.getCellStyle(cell, Color.BLACK, false, 10,"NGUYỄN THỊ VÂN ANH",HorizontalAlignmentType.CENTRED, false);

		
			cell = cells.getCell("G"+countRow);
			this.getCellStyle(cell, Color.BLACK, true, 12,"TP. ĐBCL",HorizontalAlignmentType.CENTRED, false);
			cell = cells.getCell("G"+ (countRow+3));
			this.getCellStyle1(cell, Color.BLACK, true, 10," ", false);
			
			
			cell = cells.getCell("H"+countRow);
			cells.merge( (countRow -1),7, (countRow -1), 9);
			this.getCellStyle(cell, Color.BLACK, true, 12,"NGƯỜI SOẠN THẢO",HorizontalAlignmentType.CENTRED, false);
			
			
			cell = cells.getCell("H"+(countRow+3));
			cells.merge( countRow +2,7, countRow +2, 9);
			this.getCellStyle(cell, Color.BLACK, false, 10,"ĐỖ THÚY NGA",HorizontalAlignmentType.CENTRED, false);

			// --- đổ dữ liệu ---//
		


			workbook.save(out);

			fstream.close();
			return true;
		} catch (Exception e) {
			return false;
		}


	}


	public static void getCellStyle1(Cell cell,Color color, Boolean bold, int size,String cellValue,Boolean textwraped ){		
		com.aspose.cells.Font font= new com.aspose.cells.Font();
		Style style;
		style = cell.getStyle();		
		font.setColor(color);
		font.setItalic(bold);
		font.setSize(size);
		font.setName("Times New Roman");
		style.setFont(font);
		style.setTextWrapped(textwraped);
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}
		private void setCellBorderStyle(Cell cell, short align,boolean kt) {
			Style style = cell.getStyle();
			style.setHAlignment(align);
			style.setBorderLine(BorderType.TOP, 1);
			style.setBorderLine(BorderType.RIGHT, 1);
			style.setBorderLine(BorderType.BOTTOM, 1);
			style.setBorderLine(BorderType.LEFT, 1);
			style.setTextWrapped(true);
			if(kt)
			{
				com.aspose.cells.Font font2 = new com.aspose.cells.Font(); 
				font2.setName("Calibri");
				font2.setColor(Color.BLACK);
				font2.setSize(11);
				style.setFont(font2);
				style.setColor(Color.SILVER);
			}
			else
				style.setColor(Color.WHITE);

			cell.setStyle(style);
		}
	
		
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue, short align){		   
		com.aspose.cells.Font font= new com.aspose.cells.Font();
		Style style;
		style = cell.getStyle();	
		style.setHAlignment(align);
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName("Times New Roman");
		
		style.setFont(font);
		
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}	
	
	
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue, short align, Boolean waptexted){		   
		com.aspose.cells.Font font= new com.aspose.cells.Font();
		Style style;
		style = cell.getStyle();	
		style.setHAlignment(align);
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName("Times New Roman");
		style.setTextWrapped(waptexted);
		style.setFont(font);
		
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}	
		
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue){		
		com.aspose.cells.Font font= new com.aspose.cells.Font();
		Style style;
		style = cell.getStyle();		
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName("Times New Roman");
		style.setFont(font);
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	

	private void CreatePO_Training(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db)
	throws UnsupportedEncodingException {
		float CONVERT = 28.346457f;// 


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

		//lay lenh san xuat
		IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(id);
		lsxBean.setUserId(userId); //phai co UserId truoc khi Init
		lsxBean.init();


		String donvisx="";
		String tensp="";
		String masp="";
		String dangbaoche=""; //dang thuoc
		String soluonglosx=lsxBean.getSoluong();
		String madmvt=""; // dinh muc vat tu la  BOM
		String quytrinhsx="";
		String quytrinhdonggoi="";
		String sdk="";
		String solenh=lsxBean.getId();
		String ngaybatdau=lsxBean.getNgaybatdau_new()==null?"":lsxBean.getNgaybatdau_new();
		String canculenh= "";
		if(lsxBean.getCanCuLamLenh()!=null){
			canculenh= lsxBean.getCanCuLamLenh();
		}
		String ngaygiaosp=""; // cong thuc ngay giao
		
		
		String solo=lsxBean.getghichu(); // lay ghi chu 11_11_2016
		String ngaytao=lsxBean.getNgaytao();

		//lay thong tin solenh, so dang ki, can cu lenh
		/*String qr_sdk="select isnull(SOLENHSANXUAT,'') as solenhsanxuat,isnull(SDK,'') as sdk,isnull(CANCULAMLENH,'') as canculenh "+
		" from ERP_LENHSANXUAT_HOSOLO where LENHSANXUAT_FK ="+lsxBean.getId();

		System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
		ResultSet rs_sdk=db.get(qr_sdk);
		if(rs_sdk!=null){
			try {
				while(rs_sdk.next()){
					//solenh=rs_sdk.getString("solenhsanxuat");
					sdk=rs_sdk.getString("sdk");
					//canculenh=rs_sdk.getString("canculenh");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/

		String ngayketthuchieuluc="";
		String qr_sdk=" select top 1 sodangki, ngayketthuchieuluc from erp_sanpham_nhacungcap  where sanpham_fk =  "+ lsxBean.getSpId();
		

		System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
		ResultSet rs_sdk=db.get(qr_sdk);
		if(rs_sdk!=null){
			try {
				while(rs_sdk.next()){
					
					sdk=rs_sdk.getString("sodangki")==null?"": rs_sdk.getString("sodangki");
					ngayketthuchieuluc=rs_sdk.getString("ngayketthuchieuluc")==null?"":rs_sdk.getString("ngayketthuchieuluc");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		//lay ten san pham
		String songayhoanthanh="0";
		String donvi="";
		String idsp=lsxBean.getSpma();
		System.out.print("ma san pham: "+ idsp);
		ResultSet rsTenSP = lsxBean.getSpRs();
		if (rsTenSP != null)
		{
			try
			{
				while (rsTenSP.next())
				{
					if (rsTenSP.getString("ma").equals(lsxBean.getSpma())){
						tensp = rsTenSP.getString("tentrenform");
						System.out.print("ten san pham: "+ tensp);
						if(rsTenSP.getString("NGAYHOANTHANH") !=null)
							songayhoanthanh=rsTenSP.getString("NGAYHOANTHANH") ;
					
						donvi= rsTenSP.getString("donvi");
						
					}
				}
				rsTenSP.close();
			}
			catch (SQLException e)
			{
				System.out.println("Loi");
			}
		}

		
		// tinhs ngay hoan thanh
		if(songayhoanthanh.length()>0)
		if(Integer.valueOf(songayhoanthanh) >0 )
		{
			ngaygiaosp = ngaybatdau;  // Start date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(ngaygiaosp));
				c.add(Calendar.DATE,Integer.valueOf(songayhoanthanh));  // number of days to add
				ngaygiaosp = sdf.format(c.getTime());  // dt is now the new date
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		


		

		//lay don vi san xuat = phan xuong
		ResultSet nhamayRs = lsxBean.getNhamayRs();
		if(nhamayRs != null)
		{
			try
			{
				while(nhamayRs.next())
				{  
					if( nhamayRs.getString("pk_seq").equals(lsxBean.getNhamayId()))
					{ 
						donvisx=nhamayRs.getString("tennhamay");
						nhamayRs.close();
					}
				}
			} catch(SQLException ex){}
		}	

		//lay ma dinh muc vat tu DMVT: TEN BOM
		String qr_bom="select TENBOM, isnull(quycach,'') as quycach, isnull( QUYTRINHSANXUAT,'') AS QUYTRINHSANXUAT  FROM ERP_DANHMUCVATTU WHERE PK_SEQ="+lsxBean.getBomId();
		ResultSet rsBom = db.get(qr_bom);
		System.out.println(" lay ten bom :"+ qr_bom);
		if(rsBom!=null){
			try {
				while(rsBom.next()) {
					madmvt=rsBom.getString("TENBOM");
					quytrinhdonggoi=rsBom.getString("quycach");
					quytrinhsx =rsBom.getString("QUYTRINHSANXUAT");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//lay QUY CACH DONG GOI 
		/*ReportAPI report= new ReportAPI();
		quytrinhdonggoi=report.getQuycachSp(db, lsxBean.getSpma());*/


		//lay dang bao che dang thuoc
		String qrdangthuoc="select b.ten as dangbaoche from erp_sanpham a inner join DANGBAOCHE b on a.dangbaoche=b.pk_seq where a.pk_seq="+ lsxBean.getSpId();
		System.out.print("\n ql dang bao che: "+ qrdangthuoc +"\n");
		ResultSet rs_dangthuoc=db.get(qrdangthuoc);
		if(rs_dangthuoc!=null)
		{ 
			try {
				while(rs_dangthuoc.next())
				{
					dangbaoche=rs_dangthuoc.getString("dangbaoche");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		

		/////IN RA

		try {
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 

			PdfWriter.getInstance(document, outstream);
			document.open();
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			//dinh dang font
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			font9_italic.setColor(79, 79, 79);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
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


			//TAO HEADER 1
			PdfPTable tab_Header = new PdfPTable(3);
			float []withd= {3.2f,2f,2f};
			tab_Header.setWidths(withd);
			tab_Header.setWidthPercentage(100);
			tab_Header.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell2,cellss;
			Chunk chunk;

			cell2 = new PdfPCell(new Phrase("", font11_bold));
			// insert logo traphaco
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint")+ "\\logo.gif");
			img.scalePercent(10);
			cell2 = new PdfPCell();
			cell2.addElement(new Chunk(img, 75, 0));
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_Header.addCell(cell2);

			PdfPCell cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph("BM 51/04", font9_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);


			/*font11_normal.setColor(105, 105, 105);*/
			cell2 = new PdfPCell(new Phrase("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font11_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(0.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph("BH/SĐ:01/06/2018", font9_italic));
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			font11_normal.setColor(105, 105, 105);
			cell2 = new PdfPCell(new Phrase("Số: "+solenh, font11_italic));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(2.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			document.add(tab_Header);

			//TUA DE
			Paragraph tuade = new Paragraph("LỆNH SẢN XUẤT", new Font(bf, 20, Font.BOLD));
			tuade.setSpacingBefore(10);
			tuade.setAlignment(Element.ALIGN_CENTER);
			document.add(tuade);

			if(ngaytao.length()>0){
			Paragraph ngaythang = new Paragraph("Ngày "+ ngaytao.substring(8)+" tháng "+ngaytao.substring(5,7)+" năm "+ngaytao.substring(0,4), new Font(bf, 12, Font.ITALIC));
			ngaythang.setSpacingBefore(3);
			ngaythang.setSpacingAfter(3);
			ngaythang.setAlignment(Element.ALIGN_RIGHT);
			document.add(ngaythang);
			}
			else{
				Paragraph ngaythang = new Paragraph("Ngày            tháng               năm ", new Font(bf, 12, Font.ITALIC));
				ngaythang.setSpacingBefore(3);
				ngaythang.setSpacingAfter(3);
				ngaythang.setAlignment(Element.ALIGN_RIGHT);
				document.add(ngaythang);
			}
			
			//NOI DUNG
			PdfPTable tab_info = new PdfPTable(2);
			tab_info.setWidthPercentage(100);
			tab_info.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_info.setSpacingAfter(10);
			tab_info.setSpacingBefore(5);
			tab_info.setPaddingTop(10);

			cell = new PdfPCell(new Paragraph("Đơn vị SX: "+donvisx, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Tên sản phẩm: "+tensp, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Mã sản phẩm: "+idsp, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Số lô SX: " +solo, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Dạng bào chế: "+dangbaoche, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Số lượng lô SX: " + formatter.format( Double.parseDouble(soluonglosx) ) +" " + lsxBean.getDvtBOM(), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);


			cell = new PdfPCell(new Paragraph("Mã ĐMVT: " +madmvt, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("QC đóng gói: " +quytrinhdonggoi, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("QTSX số: " +quytrinhsx, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			/*cell = new PdfPCell(new Paragraph("Hiệu lực: " +  formatngay (ngaybatdau) +" - " + formatngay (ngayketthuchieuluc), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph(" " , font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);*/

			

			cell = new PdfPCell(new Paragraph("Ngày bắt đầu: "+ formatngay (ngaybatdau) , font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("SĐK: " +sdk, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Ngày giao SP: " + formatngay (ngaygiaosp), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Căn cứ  làm lệnh: "+canculenh, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(2);
			tab_info.addCell(cell);

			




			document.add(tab_info);

			//___________BANG DU LIEU_______________

			
			float[] crDonHang = { 0.8f * CONVERT,2.0f * CONVERT, 5.5f * CONVERT,
					1.0f * CONVERT, 2.8f * CONVERT, 1.7f * CONVERT,
					1.7f * CONVERT, 1.5f * CONVERT};

			PdfPTable tab_lenhsx = new PdfPTable(crDonHang.length);
			tab_lenhsx.setWidthPercentage(100);
			tab_lenhsx.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_lenhsx.setWidths(crDonHang);
			tab_lenhsx.getDefaultCell().setBorder(0);
			tab_lenhsx.setSpacingAfter(8.0f);
			tab_lenhsx.setSpacingBefore(6);


			//in tieu de bang
			String[] spTitles = { "TT","MÃ VT","TÊN NGUYÊN LIỆU", "ĐƠN VỊ", "SỐ PKN" ,"SỐ LƯỢNG","GHI CHÚ"};
			for (int z = 0; z < spTitles.length; z++) {
				if(z==5){
					cell = new PdfPCell(new Paragraph(spTitles[z], font10_bold));
					cell.setPadding(3.0f);
					cell.setColspan(2); //gop cot
					//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tab_lenhsx.addCell(cell);
				}
				else
				{
					cell = new PdfPCell(new Paragraph(spTitles[z], font10_bold));
					cell.setPadding(3.0f);
					cell.setRowspan(2); //gop dong
					//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					//cell.setBorderColorBottom();
					tab_lenhsx.addCell(cell);
				}
			}
			cell = new PdfPCell(new Paragraph("Đ.Mức", font10_bold));
			cell.setPadding(3.0f);
			//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_lenhsx.addCell(cell);

			cell = new PdfPCell(new Paragraph("Thực", font10_bold));
			cell.setPadding(3.0f);
			//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_lenhsx.addCell(cell);


			//------------------------lay danh sach vat tu------------------------
			List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>)lsxBean.getListDanhMuc();
			int  j =0;  
			int sott = 1;
			int kt=0; // kiem tra nguyen lieu da yeu cau
			if(vattuList!=null)
				while (j< vattuList.size()) {
					kt=0;
					IDanhmucvattu_SP vt=vattuList.get(j);
					String sldinhmuc="";
					String slthuc="";
					String pkn="";
					String ghichu=vt.getGhichu();
					String hamluong=vt.getIsTinhHL();
					String maspvattu=vt.getMaVatTu();
					String donvivt=vt.getDvtVT();
					
					// LAY NGUYEN LIEU GOC
					String sql= " SELECT  MAPHIEU  FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET" + 
							 	"\n  WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "   AND  VATTU_FK = SANPHAM_FK";
						
					pkn=LayPkn(sql, db);
					
					
					// lay so luong yeu cau =so luong 
					String qr_dinhmuc="SELECT   SUM(soluongchuan) as soluongdinhmuc, SUM(soluong) as soluongthuc FROM" + 
					  "\n ERP_LENHSANXUAT_BOM_GIAY_CHITIET " + 
					  "\n WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "   AND  VATTU_FK = SANPHAM_FK  having  SUM(soluongchuan) >0 and SUM(soluong) >0";
			         System.out.println(" san pham goc: "+ qr_dinhmuc);
					//LAY SO LUONG DINH MUC CUA VAT TU THEO SO LUONG YEU CAU
					//NEU SO LUONG YEU CAU KHONG CO THI LAY SO LUONG TRONG BOM

					System.out.println("\n lenh qr so luong dinh muc: "+ qr_dinhmuc);
					ResultSet rs_dinhmuc=db.get(qr_dinhmuc);
					String mavattu=vt.getIdVT();
					System.out.println("\n ma pk_seq vat tu: "+ mavattu);
					NumberFormat formatter_2sole = new DecimalFormat("#,###,###.###########");
					NumberFormat formatter_3sole = new DecimalFormat("#,###,###.###");
					
					
					
					if(rs_dinhmuc!=null){
						try {
							while(rs_dinhmuc.next())
							{
								kt=1; // vat tu da yeu cau nguyen lieu
								sldinhmuc=	vt.getSoLuongChuan(); // lay so luong dinh muc cua nguyen lieu goc
								slthuc =rs_dinhmuc.getString("soluongthuc")== null?"0": rs_dinhmuc.getString("soluongthuc");	 
							
								
								System.out.println(" so luong dinh muc: "+ sldinhmuc);
								System.out.println(" so luong slthuc: "+ slthuc +" \n \n ");
								 
								if(slthuc.trim().equals("0") ||slthuc. trim().equals(""))
								{
									slthuc="";
								}
								else
									if( Double.parseDouble(slthuc.replaceAll(",", "")) ==  Double.parseDouble( sldinhmuc.replace(",", "") ) ){
										slthuc="";
									}
									
								

								if(sldinhmuc!=null && sldinhmuc!="")
								{
									sldinhmuc=formatter_2sole.format(Double.parseDouble(sldinhmuc));
								}

								if(slthuc!=null && slthuc!="")
								{
									slthuc=formatter_3sole.format(Double.parseDouble(slthuc));
								}
								
							
								slthuc=	formatSlthuc(slthuc);
								
								
									
							    String[] spTitles2 = {sott + "", vt.getMaVatTu(),vt.getTenVatTu(),vt.getDvtVT(),pkn,sldinhmuc,slthuc, vt.getGhichu()};

								for (int z = 0; z < spTitles2.length; z++) {
									cell = new PdfPCell(new Paragraph(spTitles2[z],new Font(bf, 10,Font.NORMAL)));
									cell.setPadding(3.0f);
									if(z==1||z==2){
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else if(z==5 ||z==6){
										cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
									else
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									tab_lenhsx.addCell(cell);
								}
								sott++;
							}
							rs_dinhmuc.close();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
			
					
					//==================LAY NGUYEN THAY THE=================
					 sql="select DV.DONVI,DV.DIENGIAI AS DIENGIAIDV, data1.LENHSANXUAT_FK, data1.SANPHAM_FK,  b.TEN1 as tenvattu, b.MA as mavattu, data1.soluongthuc  from " + 
						  "\n (select LENHSANXUAT_FK,SANPHAM_FK, soluongthuc from" + 
						  "\n (" + 
						  "\n SELECT  LENHSANXUAT_FK,SANPHAM_FK, SUM(soluong) as soluongthuc  FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET" + 
						  "\n WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "   AND  VATTU_FK <> SANPHAM_FK" + 
						  "\n GROUP BY LENHSANXUAT_FK,SANPHAM_FK ) data ) data1" + 
						  "\n inner join ERP_SANPHAM b on b.PK_SEQ=data1.SANPHAM_FK "+
						  "\n  LEFT join DONVIDOLUONG DV ON DV.PK_SEQ= b.DVDL_FK ";
					
					
					
					 
		    System.out.println(" san pham thay the: "+ sql);
			ResultSet rs_nglieuthaythe=db.get(sql);
			
				if(rs_nglieuthaythe!=null){
					try {
					while(rs_nglieuthaythe.next()){
						
						kt =1;
						
						String idspthaythe=rs_nglieuthaythe.getString("SANPHAM_FK");
						String maspthaythe=rs_nglieuthaythe.getString("mavattu");
						String tenspthaythe=rs_nglieuthaythe.getString("tenvattu");
						String donvispthaythe=rs_nglieuthaythe.getString("DONVI");
						String sldinhmuc1="";
						String slthuc1="";
						String pkn1="";
						
						// tim so luong dinh muc tu ERP_DANHMUCVATTU_VATTU_THAYTHE
						
						String qr_dinhmucthaythe="select top 1 SOLUONGDINHMUC from ERP_LENHSANXUAT_BOM_GIAY_CHITIET where LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT()+"  AND SANPHAM_FK="+idspthaythe ;
						System.out.println(" so luong sldinhmuc thay the: "+ qr_dinhmucthaythe +" \n \n ");
						
						ResultSet rs_dinhmucthaythe=db.get(qr_dinhmucthaythe);
						if( rs_dinhmucthaythe != null){
							try {
								while (rs_dinhmucthaythe.next()){
									sldinhmuc1=rs_dinhmucthaythe.getString("SOLUONGDINHMUC")== null?"0": rs_dinhmucthaythe.getString("SOLUONGDINHMUC");	
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						rs_dinhmucthaythe.close();
						/*sldinhmuc1=String.valueOf(rs_nglieuthaythe.getDouble("soluongdinhmuc"));*/
						slthuc1 =rs_nglieuthaythe.getString("soluongthuc")== null?"0": rs_nglieuthaythe.getString("soluongthuc");
						
						// lay phieu kiem nghiem
						sql= " SELECT  MAPHIEU  FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET" + 
					 	"\n WHERE LENHSANXUAT_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + vt.getIdVT() + "    AND  SANPHAM_FK = "+idspthaythe;
						pkn1= LayPkn(sql, db);
					
						
						// lay so luong chuan, dih muc
						System.out.println(" so luong dinh muc thay the: "+ sldinhmuc1);
						System.out.println(" so luong slthuc thay the: "+ slthuc1 +" \n \n ");
						if(sldinhmuc1==null || sldinhmuc1==""){
							sldinhmuc1="0.0";
						}
						
						if(slthuc1.trim().equals("0") ||slthuc1. trim().equals(""))
						{
							slthuc1="";
						}
						else
							if( Double.parseDouble(slthuc1.replaceAll(",", "")) ==  Double.parseDouble( sldinhmuc1.replace(",", "") ) ){
								slthuc1="";
							}
						
						if(sldinhmuc1!=null && sldinhmuc1!="")
						{
							sldinhmuc1=formatter_2sole.format(Double.parseDouble(sldinhmuc1));
						}

						if(slthuc1!=null && slthuc1!="")
						{
							slthuc1=formatter_3sole.format(Double.parseDouble(slthuc1));
						}
						slthuc1=formatSlthuc(slthuc1);
						
						System.out.println(" so luong dinh muc thay the: "+ sldinhmuc1);
				    // in sp thay the
				    String [] spTitles3 = {sott + "", maspthaythe,tenspthaythe,donvispthaythe,pkn1,sldinhmuc1,slthuc1, vt.getGhichu()};

					for (int z = 0; z < spTitles3.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles3[z],new Font(bf, 10,Font.NORMAL)));
						cell.setPadding(3.0f);
						if(z==1||z==2){
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else if(z==5 ||z==6){
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab_lenhsx.addCell(cell);
					}
					sott++;
					}
					
					rs_nglieuthaythe.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

				
				//------------------- NEU CHUA YEU CAU NGUYEN LIEU
			if(kt==0)
				{String[] spTitles2 = {sott + "", vt.getMaVatTu(),vt.getTenVatTu(),vt.getDvtVT(),"",formatter_2sole.format(Double.parseDouble( vt.getSoLuongChuan()))   ,"", vt.getGhichu()};
				for (int z = 0; z < spTitles2.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles2[z],new Font(bf, 10,Font.NORMAL)));
					cell.setPadding(3.0f);
					if(z==1||z==2){
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else if(z==5 ||z==6){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab_lenhsx.addCell(cell);
				}
				sott++;
				}
				
					
					j++;
				}
			document.add(tab_lenhsx);		



			//------------bang chu ki---------------------
			/*float[] wky = {9.0f*CONVERT, 8.8f*CONVERT, 6.6f*CONVERT,8.f*CONVERT };
			PdfPTable tbl_vat = new PdfPTable(wky.length);
			tbl_vat.setWidthPercentage(100);
			tbl_vat.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbl_vat.getDefaultCell().setBorder(0);
			tbl_vat.setWidths(wky);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			cellss.setPaddingLeft(0.3f*CONVERT);
			chunk =new Chunk("PHÓ GIÁM ĐỐC \n       ", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n  Phạm Thị Thanh Duyên", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);


			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("           PP. QLCL \n ", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n  Vũ Văn Tuấn", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("NGƯỜI NHẬN", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n \n ", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("NGƯỜI SOẠN THẢO \n  ", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n           Đỗ Thị Thanh Tâm", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			document.add(tbl_vat);*/

			// viet lai chu ki
			
			float[] with = {9.0f*CONVERT, 8.8f*CONVERT, 6.6f*CONVERT,8.f*CONVERT };
			PdfPTable	tbl_vat = new PdfPTable(with.length);
			tbl_vat.setWidthPercentage(100);
			tbl_vat.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbl_vat.getDefaultCell().setBorder(0);
			tbl_vat.setWidths(with);
			
			// dong 1 
			cellss = new PdfPCell(new Paragraph("GIÁM ĐỐC", new Font(bf, 12, Font.BOLD)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			
			cellss = new PdfPCell(new Paragraph("PP. QLCL", new Font(bf, 12, Font.BOLD)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			
			cellss = new PdfPCell(new Paragraph("NGƯỜI NHẬN", new Font(bf, 12, Font.BOLD)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			
			cellss = new PdfPCell(new Paragraph("NGƯỜI SOẠN THẢO", new Font(bf, 12, Font.BOLD)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			
			
			// dong 2
			cellss = new PdfPCell(new Paragraph("Phạm Thị Thanh Duyên", new Font(bf, 12, Font.NORMAL)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			cellss.setPaddingTop(2f*CONVERT);
			tbl_vat.addCell(cellss);
			
			cellss = new PdfPCell(new Paragraph("Vũ Văn Tuấn", new Font(bf, 12, Font.NORMAL)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			cellss.setPaddingTop(2f*CONVERT);
			tbl_vat.addCell(cellss);
			
			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.NORMAL)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			cellss.setPaddingTop(2f*CONVERT);
			tbl_vat.addCell(cellss);
			
			cellss = new PdfPCell(new Paragraph("Đỗ Thị Thanh Tâm", new Font(bf, 12, Font.NORMAL)));
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellss.setBorder(0);
			cellss.setPaddingTop(2f*CONVERT);
			tbl_vat.addCell(cellss);
			
			document.add(tbl_vat);
			
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}

	}

	String LayPkn(String qr, dbutils db){
		String pkn="";
		String chuoipkn="";
		ResultSet rs= db.get(qr);
		if(rs!=null){
			try {
				while(rs.next()){
					chuoipkn+= rs.getString("MAPHIEU").trim() +"__";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
				
		if(chuoipkn.length()>0)							
		{
			String pkn_ar[]= chuoipkn.split("__");
			
			//loai bo pt giong nhau
			for (int i = 0; i < pkn_ar.length; i++)
			{
				for (int k = i+1; k <pkn_ar.length; k++) 
				{
					if(pkn_ar[i].equals(pkn_ar[k]))
					{
						pkn_ar[k]="";
					}
				}
			}
			//lay ra chuoi
			for (int i = 0; i < pkn_ar.length; i++)
			{
				if(pkn_ar[i].length()>0)
					pkn+=pkn_ar[i]+"; ";
			}			
		}
		
		return pkn;
	}
	
	
	String formatngay(String ngay){
		
		if(ngay!=null){
		if(ngay.length()>0)
		return ngay.substring(8)+"/"+ngay.substring(5,7)+"/"+ngay.substring(0,4);
		else
			return " ";
		}
		else
			return " ";
	}
	
	String formatSlthuc(String s){
		String so=s;
		if(s!=null){
			if(s.length()>0){
				String []tempt=s.split("\\.");
				System.out.println(" tempt lengt: "+ tempt.length);
				if(tempt.length>=2){
					if(tempt[1].length()==2){
						tempt[1]=tempt[1]+"0";
						so=tempt[0]+"."+ tempt[1];
					}
					
					if(tempt[1].length()==1){
						tempt[1]=tempt[1]+"00";
						so=tempt[0]+"."+ tempt[1];
					}
				}
				
			}
		}
		return so;
	}
	
	
	
	public void  capnhat_soluongdinhmuc () {
		
		
		
	}
	
	

	
}
