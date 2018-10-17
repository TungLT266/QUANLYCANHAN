package geso.traphaco.erp.servlets.donmuahangtrongnuoc;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanphamBom;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.servlets.baocao.ReportAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

import oracle.sql.LxMetaData;

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

public class ErpInDonHangVaGiaCongMau2PdfSvl extends HttpServlet{

	
	public ErpInDonHangVaGiaCongMau2PdfSvl() {
		super(); 
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String nextJSP;
		Utility util = new Utility();
		String querystring = request.getQueryString();
		HttpSession session = request.getSession();

		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId: " + userId);
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id:" + id);
		
		String action = request.getParameter("action");
		System.out.println("action: "+ action);

		//lay lenh san xuat
		IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
		dmhBean.setCongtyId((String) session.getAttribute("congtyId"));
		dmhBean.setUserId(userId); // phai co UserId truoc khi Init
		dmhBean.init();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
		" inline; filename=Traphaco_InDonGiaCongMau2.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 1.5f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT;
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT,
				PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			if(dmhBean.getIsGiaCong().equals("1")){
				//tien hanh in
			//	CreatePO_Training(document, outstream, response, request, db,dmhBean);
				
				
				if(action.equals("printExcel")){
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=InDonHangGiaCong_Lenhsanxuat.xlsm");
					try {
						
						CreateExcel_LSX(response, outstream ,request, db, dmhBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{
					//tien hanh in pdf
					CreatePO_Training(document, outstream, response, request, db,dmhBean);
					
				}
				
			}
			else{
				dmhBean.setMsg("Đơn mua hàng phải là đơn  gia công");
				session.setAttribute("nhacungcapNK", dmhBean.getNhacungcapNK());
				session.setAttribute("ngaymuahang", dmhBean.getNgaymuahang());
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocDisplay_Giay.jsp";
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				session.setAttribute("dmhBean", dmhBean);
				session.setAttribute("loaimh", dmhBean.getLoai());
				session.setAttribute("nccId", dmhBean.getNCC());
				response.sendRedirect(nextJSP);
			}
			
			
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	float CONVERT = 28.346457f;// 
	
	
	private void CreatePO_Training(Document document,ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db, IErpDonmuahang_Giay lsxBean) throws UnsupportedEncodingException {
		
		
	
		/*String donvisx=lsxBean.getNccTen();*/
		String donvisx=lsxBean.getTenncc();
		String tensp="";
		String masp="";
		String dangbaoche=""; //dang thuoc
		String soluonglosx=lsxBean.getSoluong();
		String madmvt=""; // dinh muc vat tu la  BOM
		String quytrinhdonggoi="";
		String sdk="";
		String solenh=lsxBean.getmaDMH();
		/*String ngaybatdau=lsxBean.getNgaymuahang();*/
		String ngaybatdau=lsxBean.getNgaybatdau();
		
		String canculenh=lsxBean.getmaDMH();
		
		
		// lay ngay giao = ngay thanh toan cuoi cung
		String  ngaygiaosp=""; 
		ResultSet thtt = lsxBean.getThttRs();
			try {
				if(thtt != null)
				{
					while (thtt.next())
					{
						ngaygiaosp=thtt.getString("NGAYTHANHTOAN")==null?"":thtt.getString("NGAYTHANHTOAN") ;
					}
				}
				thtt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
		
		String solo=""; 
		String ngaytao=lsxBean.getNgaymuahang();
		String donvi="";
		//lay ten san pham
		String songayhoanthanh="0";
		String idsp="";
		
		
		// tinhs ngay hoan thanh
		/*if(songayhoanthanh.length()>0)
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
		*/
		
		try {
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			///lay thong tin san pham gia cong
			List<ISanpham> spList = lsxBean.getSpList(); 
			if(spList.size() > 0) { 
				for(int i = 0; i < spList.size(); i++) { 
					ISanpham sp = spList.get(i);
					
					tensp=sp.getTensanpham();
					masp= sp.getMasanpham();
					soluonglosx=sp.getSoluong();
					donvi="";
					madmvt="";
					
					solo=lsxBean.getGhiChuGC();
					String dvtSQl=" select donvi from DONVIDOLUONG where PK_SEQ="+ sp.getDonvitinh();
					System.out.print("\n sql don vi tinh: "+ dvtSQl +"\n");
					ResultSet rs_dvt=db.get(dvtSQl);
					if(rs_dvt!=null)
					{ 
						try {
							while(rs_dvt.next())
							{
								donvi=rs_dvt.getString("donvi");
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					
					// lay don vi tinh  
					
					
					//lay dang bao che dang thuoc
					String qrdangthuoc="select b.ten as dangbaoche, a.ngayhoanthanh as ngayhoanthanh from erp_sanpham a inner join DANGBAOCHE b on a.dangbaoche = cast ( b.pk_seq as nvarchar (18)) where a.pk_seq="+ sp.getPK_SEQ();
					System.out.print("\n ql dang bao che: "+ qrdangthuoc +"\n");
					ResultSet rs_dangthuoc=db.get(qrdangthuoc);
					if(rs_dangthuoc!=null)
					{ 
						try {
							while(rs_dangthuoc.next())
							{
								dangbaoche=rs_dangthuoc.getString("dangbaoche");
								songayhoanthanh=rs_dangthuoc.getString("ngayhoanthanh");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					//lay ma dinh muc vat tu DMVT: TEN BOM
					ResultSet rsBom =  sp.getRsBom();
					if(rsBom!=null){
						try {
							while(rsBom.next()) {
								if(sp.getBomId().equals(rsBom.getString("pk_Seq")))
								{
									madmvt=rsBom.getString("TEN");
									quytrinhdonggoi=rsBom.getString("quycach");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println(" bom id: "+ sp.getBomId());
					System.out.println(" bom : "+ madmvt);
					System.out.println(" quy cach : "+ quytrinhdonggoi);
					
					//lay sdk
					
					String qr_sdk=" select top 1 sodangki, ngayketthuchieuluc from erp_sanpham_nhacungcap  where sanpham_fk =  "+ sp.getPK_SEQ();
					System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
					ResultSet rs_sdk=db.get(qr_sdk);
					if(rs_sdk!=null){
						try {
							while(rs_sdk.next()){
								
								sdk=rs_sdk.getString("sodangki")==null?"": rs_sdk.getString("sodangki");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					// MO PAGE IN - In rieng tung sp

						Create_sp(document, outstream,  response,request,  db,  lsxBean ,  sp,   solenh,  ngaytao,
								 donvisx,  tensp,masp,  solo, dangbaoche, soluonglosx, donvi, madmvt,
								 quytrinhdonggoi, sdk, ngaybatdau , canculenh,  ngaygiaosp );
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		document.close();
	}
	
	private void Create_sp(Document document,ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db, IErpDonmuahang_Giay lsxBean ,ISanpham sp,  String solenh, String ngaytao,
			String donvisx, String tensp,String idsp,String  solo,String dangbaoche,String soluonglosx , String donvi ,String madmvt,
			String quytrinhdonggoi,String sdk,String ngaybatdau ,String canculenh,String  ngaygiaosp ){
	/////IN RA

		try {
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,###.###########");
			
			NumberFormat formatter3 = new DecimalFormat("#,###,###.###"); 
			document.newPage();
			
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


			/*font11_normal.setColor(105, 105, 105);*/
			cell2 = new PdfPCell(new Phrase("CÔNG TY CP TRAPHACO", font11_normal));
			cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell2.setPadding(0.0f);
			cell2.setBorder(0);
			tab_Header.addCell(cell2);

			cell = new PdfPCell(new Paragraph(" ", font11_italic));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(0);
			tab_Header.addCell(cell);

			cell = new PdfPCell(new Paragraph("BH/SĐ:03/02/2017", font9_italic));
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

			cell = new PdfPCell(new Paragraph("Số lượng lô SX: " + formatter.format( Double.parseDouble(soluonglosx.replaceAll(",", "")) )   +" " + donvi, font12_normal));
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

			cell = new PdfPCell(new Paragraph("SĐK: " +sdk, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			if(ngaybatdau.trim().length()>0)
				cell = new PdfPCell(new Paragraph("Ngày bắt đầu: "+ formatngay (ngaybatdau) , font12_normal));
			else
				cell = new PdfPCell(new Paragraph("Ngày bắt đầu: " , font12_normal));
				
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Căn cứ làm lệnh: Mã ĐHM "+canculenh, font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);

			cell = new PdfPCell(new Paragraph("Ngày giao SP: " + formatngay (ngaygiaosp), font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tab_info.addCell(cell);




			document.add(tab_info);

			//___________BANG DU LIEU_______________

			/*float[] crDonHang = { 0.5f * CONVERT,1.0f * CONVERT, 3.0f * CONVERT,
					1.0f * CONVERT, 1.5f * CONVERT, 1.3f * CONVERT,
					1.3f * CONVERT, 1.0f * CONVERT};*/
			
		/*	float[] crDonHang = { 0.8f * CONVERT,2.0f * CONVERT, 5.5f * CONVERT,
					1.0f * CONVERT, 2.2f * CONVERT, 1.5f * CONVERT,
					1.5f * CONVERT, 2.5f * CONVERT};*/
			
			float[] crDonHang = { 0.8f * CONVERT,2.0f * CONVERT, 5.5f * CONVERT,
					1.0f * CONVERT, 2.2f * CONVERT, 1.5f * CONVERT,
					2.5f * CONVERT, 1.5f * CONVERT};


			PdfPTable tab_lenhsx = new PdfPTable(crDonHang.length);
			tab_lenhsx.setWidthPercentage(100);
			tab_lenhsx.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab_lenhsx.setWidths(crDonHang);
			tab_lenhsx.getDefaultCell().setBorder(0);
			tab_lenhsx.setSpacingAfter(8.0f);
			tab_lenhsx.setSpacingBefore(6);


			//in tieu de bang
			String[] spTitles = { "TT","MÃ VT","TÊN NGUYÊN LIỆU", "ĐƠN VỊ", "SỐ PKN","SỐ LƯỢNG","GHI CHÚ"};
			for (int z = 0; z < spTitles.length; z++) {
				if(z==5){
					cell = new PdfPCell(new Paragraph(spTitles[z], font10_bold));
					cell.setPadding(3.0f);
					cell.setColspan(2); //gop cot
					
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tab_lenhsx.addCell(cell);
				}
				else
				{
					cell = new PdfPCell(new Paragraph(spTitles[z], font10_bold));
					cell.setPadding(3.0f);
					cell.setRowspan(2); //gop dong
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					//cell.setBorderColorBottom();
					tab_lenhsx.addCell(cell);
				}
			}
			cell = new PdfPCell(new Paragraph("Đ.Mức", font10_bold));
			cell.setPadding(3.0f);

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_lenhsx.addCell(cell);

			cell = new PdfPCell(new Paragraph("Thực", font10_bold));
			cell.setPadding(3.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab_lenhsx.addCell(cell);


			//------------------------lay danh sach vat tu------------------------
					List<ISanphamBom> spConList = sp.getSpListBom();
					int kt=0; 
					int  j =0;  
					int sott = 1;
                 	String bomid="";
                 	if(spConList.size() > 0)
                 	{
                 		while (j< spConList.size())
                 		{
                 			System.out.println(" stt : " +sott);
                 			kt=0;
                 			ISanphamBom spCon = spConList.get(j);
                 			bomid=spCon.getDMVTId();
                 			
        					String sldinhmuc="";
        					String slthuc="";
        					String pkn="";
        					String ghichu="";
        					String maspvattu=spCon.getMasanpham();
        					String donvivt= spCon.getDonVi();
        					
        					// LAY NGUYEN LIEU GOC
        					String sql= " SELECT  MAPHIEU  FROM  ERP_MUAHANG_BOM_CHITIET" + 
        							 	"\n  WHERE ISYCCK <>0 AND  MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + spCon.getSpId() + "   AND  VATTU_FK = SANPHAM_FK";
        					System.out.println("\n ma phieu nguyen lieu goc: "+ sql);	
        					pkn=LayPkn(sql, db);
        					
        					
        					// lay so luong yeu cau =so luong 
        					String qr_dinhmuc="SELECT    SUM(SOLUONGQUYCHUANBOM) as soluongdinhmuc, SUM( CAST( soluong as numeric (18,3))) as soluongthuc FROM" + 
        					  "\n ERP_MUAHANG_BOM_CHITIET " + 
        					  "\n WHERE ISYCCK <>0 AND  MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" +spCon.getSpId()+ "   AND  VATTU_FK = SANPHAM_FK  having    SUM(SOLUONGQUYCHUANBOM) >0 and SUM(soluong) >0";
        			         System.out.println(" san pham goc: "+ qr_dinhmuc);
        			         
        					//LAY SO LUONG DINH MUC CUA VAT TU THEO SO LUONG YEU CAU
        					//NEU SO LUONG YEU CAU KHONG CO THI LAY SO LUONG TRONG BOM

        					System.out.println("\n lenh qr so luong dinh muc: "+ qr_dinhmuc);
        					ResultSet rs_dinhmuc=db.get(qr_dinhmuc);
        					String mavattu=spCon.getSpId();
        					System.out.println("\n ma pk_seq vat tu: "+ mavattu);
        					NumberFormat formatter_2sole = new DecimalFormat("#,###,###.###########");
        					NumberFormat formatter_3sole = new DecimalFormat("#,###,###.###");
        					
        					
        					
        					if(rs_dinhmuc!=null){
        						try {
        							while(rs_dinhmuc.next())
        							{
        								
        								System.out.println(" da vao nguyen lieu goc: "+ j);
        								kt=1; // vat tu da yeu cau nguyen lieu
        								sldinhmuc =spCon.getSoluongdenghi(); // lay so luong dinh muc cua nguyen lieu goc
        								slthuc =rs_dinhmuc.getString("soluongthuc")== null?"0": rs_dinhmuc.getString("soluongthuc");	 
        							
        								
        								System.out.println(" so luong dinh muc: "+ sldinhmuc);
        								System.out.println(" so luong slthuc : "+ Double.parseDouble(slthuc.replaceAll(",", "") ));
        								System.out.println(" so luong slthuc xx: "+ slthuc +" \n \n ");
        								 
        								if(slthuc.trim().equals("0") ||slthuc. trim().equals(""))
        								{
        									slthuc="";
        								}
        								else
        									if(  Double.parseDouble(slthuc.replaceAll(",", "")) ==  Double.parseDouble( sldinhmuc.replace(",", "") ) ){
        										slthuc="";
        									}
        									/*if(formatter_3sole.format(Double.parseDouble(slthuc.replaceAll(",", ""))).equals(formatter_3sole.format( Double.parseDouble( sldinhmuc.replace(",", ""))))    ){
        										slthuc="";
        									}*/
        								

        								if(sldinhmuc!=null && sldinhmuc!="")
        								{
        									sldinhmuc=formatter_2sole.format(Double.parseDouble(sldinhmuc));
        								}

        								if(slthuc!=null && slthuc!="")
        								{
        									slthuc=formatter_3sole.format(Double.parseDouble(slthuc));
        								}
        								
        							
        								slthuc=	formatSlthuc(slthuc);
        								
        								
        									
        							    String[] spTitles2 = {sott + "", spCon.getMasanpham(),spCon.gettensanpham(),donvivt,pkn,sldinhmuc,slthuc,ghichu};

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
        									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        									tab_lenhsx.addCell(cell);
        								}
        								sott++;
        							}
        							rs_dinhmuc.close();

        						} catch (Exception e) {
        							e.printStackTrace();
        						}
        					}
        					
        					System.out.println(" stt thuc te : "+ sott);
        			
        					
        					//==================LAY NGUYEN THAY THE=================
        					 sql= "select data1.MUAHANG_FK, data1.SANPHAM_FK,  b.TEN1 as tenvattu, b.MA as mavattu, data1.soluongthuc  from  " + 
        					 "\n (select MUAHANG_FK,SANPHAM_FK, soluongthuc from " + 
        					 "\n ( " + 
        					 "\n SELECT  MUAHANG_FK,SANPHAM_FK, SUM(SOLUONGQUYCHUANBOM) as soluongthuc  FROM  ERP_MUAHANG_BOM_CHITIET " + 
        					 "\n WHERE ISYCCK <>0 AND   MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" +  spCon.getSpId() + "  AND  VATTU_FK <> SANPHAM_FK " + 
        					 "\n GROUP BY MUAHANG_FK,SANPHAM_FK ) data ) data1 " + 
        					 "\n inner join ERP_SANPHAM b on b.PK_SEQ=data1.SANPHAM_FK" ;		 
        		
						System.out.println(" san pham thay the: "+ sql);
						ResultSet rs_nglieuthaythe=db.get(sql);
        			
        				if(rs_nglieuthaythe!=null){
        					try {
        					while(rs_nglieuthaythe.next()){
        						
        						System.out.println(" da vao nguyen lieu  thay the: "+ j);
        						kt =1;
        						
        						String idspthaythe=rs_nglieuthaythe.getString("SANPHAM_FK");
        						String maspthaythe=rs_nglieuthaythe.getString("mavattu");
        						String tenspthaythe=rs_nglieuthaythe.getString("tenvattu");
        						String sldinhmuc1="";
        						String slthuc1="";
        						String pkn1="";
        						
        						// tim so luong dinh muc tu ERP_DANHMUCVATTU_VATTU_THAYTHE
        						
        						String qr_dinhmucthaythe="select isnull (SOLUONGCHUAN_THAYTHE,0) as SOLUONGCHUAN_THAYTHE  from ERP_MUAHANG_BOM where MUAHANG_FK="+ lsxBean.getId()+"  and VATTU_FK="+ spCon.getSpId()  ;
        						System.out.println(" so luong sldinhmuc thay the: "+ qr_dinhmucthaythe +" \n \n ");
        						
        						ResultSet rs_dinhmucthaythe=db.get(qr_dinhmucthaythe);
        						if( rs_dinhmucthaythe != null){
        							try {
        								while (rs_dinhmucthaythe.next()){
        									sldinhmuc1=rs_dinhmucthaythe.getString("SOLUONGCHUAN_THAYTHE")== null?"0": rs_dinhmucthaythe.getString("SOLUONGCHUAN_THAYTHE");	
        								}
        							} catch (Exception e) {
        								e.printStackTrace();
        							}
        						}
        						rs_dinhmucthaythe.close();
        						slthuc1 =rs_nglieuthaythe.getString("soluongthuc")== null?"0": rs_nglieuthaythe.getString("soluongthuc");
        						
        						// lay phieu kiem nghiem
        						sql= " SELECT  MAPHIEU  FROM  ERP_MUAHANG_BOM_CHITIET" + 
        					 	"\n WHERE  ISYCCK <>0 AND MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" +  spCon.getSpId() + "    AND  SANPHAM_FK = "+idspthaythe;
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
        				    String [] spTitles3 = {sott + "", maspthaythe,tenspthaythe,donvivt,pkn1,sldinhmuc1,slthuc1, ghichu};

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
        						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        						tab_lenhsx.addCell(cell);
        					}
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
        				System.out.println(" da vao nguyen lieu  chua chuyen nguyen lieu: "+ j);
        				String[] spTitles2 = {sott + "", spCon.getMasanpham(),spCon.gettensanpham(),donvivt," ",formatter_2sole.format(Double.parseDouble( spCon.getSoluongdenghi().replaceAll(",", "")))   ,"", ghichu};
        			
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
	        					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        					tab_lenhsx.addCell(cell);
                 			
        					}
        					sott++;
        				
        				
        				}
        					
        					j++;
                 		}
                 		
                 	}
					
				
			
			document.add(tab_lenhsx);		



			//------------bang chu ki---------------------
			float[] wky = {9.0f*CONVERT, 8.8f*CONVERT, 6.6f*CONVERT,8.f*CONVERT };
			PdfPTable tbl_vat = new PdfPTable(wky.length);
			tbl_vat.setWidthPercentage(100);
			tbl_vat.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbl_vat.getDefaultCell().setBorder(0);
			tbl_vat.setWidths(wky);

			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			cellss.setPaddingLeft(0.3f*CONVERT);
			chunk =new Chunk("T/L TỔNG GIÁM ĐỐC \n        TP. Kế hoạch", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n  NGUYỄN VĂN NHƯỢNG", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);


			cellss = new PdfPCell(new Paragraph("", new Font(bf, 12, Font.BOLD)));
			chunk =new Chunk("           TP. ĐBCL \n ", new Font(bf, 12, Font.BOLD));
			cellss.addElement(chunk);
			chunk =new Chunk("\n \n \n  NGUYỄN THỊ VÂN ANH", new Font(bf, 10, Font.NORMAL));
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
			chunk =new Chunk("\n \n \n           ĐỖ THÚY NGA", new Font(bf, 10, Font.NORMAL));
			cellss.addElement(chunk);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setBorder(0);
			tbl_vat.addCell(cellss);
			document.add(tbl_vat);

			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}

	}
	
	

	String formatngay(String ngay){
		if(ngay.length()>0)
		return ngay.substring(8)+"/"+ngay.substring(5,7)+"/"+ngay.substring(0,4);
		else
			return " ";
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
	
	
	
	String formatSlthuc(String s){
		String so=s;
		if(s!=null){
			if(s.length()>0){
				String []tempt=s.split("\\.");
				//System.out.println(" tempt lengt: "+ tempt.length);
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


	
	
	private boolean CreateExcel_LSX(HttpServletResponse response,ServletOutputStream outstream, HttpServletRequest request, dbutils db, IErpDonmuahang_Giay lsxBean ) throws Exception {
		
		try {
			
			

			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\BCDonGiaCong_LenhSanXuat.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			
	
			
			// lây thông tin
			String donvisx=lsxBean.getTenncc();
			String tensp="";
			String masp="";
			String dangbaoche=""; //dang thuoc
			String soluonglosx=lsxBean.getSoluong();
			String madmvt=""; // dinh muc vat tu la  BOM
			String quytrinhdonggoi="";
			String sdk="";
			String solenh=lsxBean.getmaDMH();
			String ngaybatdau=lsxBean.getNgaymuahang();
			
			String canculenh=lsxBean.getmaDMH();
			
			
			// lay ngay giao = ngay thanh toan cuoi cung
			String  ngaygiaosp=""; 
			ResultSet thtt = lsxBean.getThttRs();
				try {
					if(thtt != null)
					{
						while (thtt.next())
						{
							ngaygiaosp=thtt.getString("NGAYTHANHTOAN")==null?"":thtt.getString("NGAYTHANHTOAN") ;
						}
					}
					thtt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		
			
			String solo=""; 
			String ngaytao=lsxBean.getNgaymuahang();
			String donvi="";
			//lay ten san pham
			String songayhoanthanh="0";
			String idsp="";
			
			///lay thong tin san pham gia cong
			
			
			List<ISanpham> spList = lsxBean.getSpList(); 
			if(spList.size() > 0) { 
				for(int i = 0; i < spList.size(); i++) { 
					ISanpham sp = spList.get(i);
					
					tensp=sp.getTensanpham();
					masp= sp.getMasanpham();
					soluonglosx=sp.getSoluong();
					donvi="";
					madmvt="";
					
					solo=lsxBean.getGhiChuGC();
					String dvtSQl=" select donvi from DONVIDOLUONG where PK_SEQ="+ sp.getDonvitinh();
					System.out.print("\n sql don vi tinh: "+ dvtSQl +"\n");
					ResultSet rs_dvt=db.get(dvtSQl);
					if(rs_dvt!=null)
					{ 
						try {
							while(rs_dvt.next())
							{
								donvi=rs_dvt.getString("donvi");
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					
					// lay don vi tinh  
					
					
					//lay dang bao che dang thuoc
					String qrdangthuoc="select b.ten as dangbaoche, a.ngayhoanthanh as ngayhoanthanh from erp_sanpham a inner join DANGBAOCHE b on a.dangbaoche = cast ( b.pk_seq as nvarchar (18)) where a.pk_seq="+ sp.getPK_SEQ();
					System.out.print("\n ql dang bao che: "+ qrdangthuoc +"\n");
					ResultSet rs_dangthuoc=db.get(qrdangthuoc);
					if(rs_dangthuoc!=null)
					{ 
						try {
							while(rs_dangthuoc.next())
							{
								dangbaoche=rs_dangthuoc.getString("dangbaoche");
								songayhoanthanh=rs_dangthuoc.getString("ngayhoanthanh");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					//lay ma dinh muc vat tu DMVT: TEN BOM
					ResultSet rsBom =  sp.getRsBom();
					if(rsBom!=null){
						try {
							while(rsBom.next()) {
								if(sp.getBomId().equals(rsBom.getString("pk_Seq")))
								{
									madmvt=rsBom.getString("TEN");
									quytrinhdonggoi=rsBom.getString("quycach");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println(" bom id: "+ sp.getBomId());
					System.out.println(" bom : "+ madmvt);
					System.out.println(" quy cach : "+ quytrinhdonggoi);
					
					//lay sdk
					
					String qr_sdk=" select top 1 sodangki, ngayketthuchieuluc from erp_sanpham_nhacungcap  where sanpham_fk =  "+ sp.getPK_SEQ();
					System.out.println("\n lay lenh so dk: "+qr_sdk+"\n");
					ResultSet rs_sdk=db.get(qr_sdk);
					if(rs_sdk!=null){
						try {
							while(rs_sdk.next()){
								
								sdk=rs_sdk.getString("sodangki")==null?"": rs_sdk.getString("sodangki");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					
					
					// các bước thực hiện: mỗi sp trong danh sách sẽ có 1 danh sách nguyen lieu== > mỗi sp sẽ được in trên 1 sheet 
					// b1: mẫu templete ở sheet (0)
					// bắt đầu duyệt danh sách sp gốc: mở từ sheet1 ---> sheet n
					// các bước ghi vào sheet: mở sheet(0) copy templete sang sheet n, đỗ data vào.---> đỗ hết danh sách thì remove sheet (0)
					
					// MỞ SHEET
					Worksheet ws0 = worksheets.getSheet(0);  // mở sheet (0)
					Worksheet worksheet = worksheets.getSheet(i+1); // mở sheet cần ghi
					Cells cells = worksheet.getCells();
					worksheet.copy(ws0); // copy từ sheet 0 qa
					
					// ghi dữ liệu vào sheet cần
					CreateExcel(worksheet, cells, response,  request,  db,  lsxBean ,  sp,   solenh,  ngaytao,
							 donvisx,  tensp,masp,  solo, dangbaoche, soluonglosx, donvi, madmvt,
							 quytrinhdonggoi, sdk, ngaybatdau , canculenh,  ngaygiaosp );
					// xoa sheet (0)
					workbook.removeSheet(ws0);
					workbook.save(outstream); // save lại
					fstream.close();
					
				}
			}
			
			
			
			
			// ----------------------gọi ham in tung sheet
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}


	}


	
	
	
	private boolean CreateExcel( Worksheet worksheet,Cells cells,  HttpServletResponse response, HttpServletRequest request,dbutils  db,  
			IErpDonmuahang_Giay	lsxBean , ISanpham  sp, String  solenh, String ngaytao,
			String donvisx,String tensp, String  masp, String solo, String dangbaoche,String soluonglosx,String donvi, String madmvt,
			String quytrinhdonggoi,String sdk, String ngaybatdau ,String  canculenh, String ngaygiaosp) throws Exception {
		

		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
		NumberFormat formatter2 = new DecimalFormat("#,###,###.###########");
		NumberFormat formatter3 = new DecimalFormat("#,###,###.###"); 
		
		try {
			
			Cell cell = null;
			Style style;
			
			String ngaythang="";
			if(ngaytao.length()>0){
				 ngaythang = "Ngày "+ ngaytao.substring(8)+" tháng "+ngaytao.substring(5,7)+" năm "+ngaytao.substring(0,4);
			}
					
			
			cell = cells.getCell("I8");
			this.getCellStyle1(cell, Color.BLACK, true,12, ngaythang, false);

			cell = cells.getCell("B5");
			this.getCellStyle1(cell, Color.BLACK, true,11, solenh, false);
			
			/*cell = cells.getCell("C10");
			this.getCellStyle(cell, Color.BLACK, false, 12, donvisx);
			
			cell = cells.getCell("H10");
			this.getCellStyle(cell,  Color.BLACK, false, 12,tensp);

			cell = cells.getCell("C11");
			this.getCellStyle(cell,  Color.BLACK, false, 12 , masp);

			cell = cells.getCell("H11");
			this.getCellStyle(cell,  Color.BLACK, false,12,solo);

			cell = cells.getCell("C12");
			this.getCellStyle(cell, Color.BLACK, false, 12,dangbaoche);
			
			cell = cells.getCell("H12");
			this.getCellStyle(cell,  Color.BLACK, false,12,soluonglosx +" " + donvi);


			cell = cells.getCell("C13");
			this.getCellStyle(cell,  Color.BLACK, false,12, madmvt);
			
			cell = cells.getCell("H13");
			this.getCellStyle(cell,  Color.BLACK, false, 12,quytrinhdonggoi);

			
			cell = cells.getCell("C14");
			this.getCellStyle(cell,  Color.BLACK, false,12, sdk);

			
			cell = cells.getCell("H14");
			this.getCellStyle(cell,  Color.BLACK, false, 12,formatngay (ngaybatdau));
			
			cell = cells.getCell("C15");
			this.getCellStyle(cell,  Color.BLACK, false, 12,canculenh);


			cell = cells.getCell("H15");
			this.getCellStyle(cell,  Color.BLACK, false, 12,formatngay (ngaygiaosp));*/
			
			
			cell = cells.getCell("A10");
			this.getCellStyle1(cell, Color.BLACK, false, 11,"Đơn vị SX: "+ donvisx, true);
			
			cell = cells.getCell("F10");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Tên sản phẩm: "+ tensp, true);

			cell = cells.getCell("A11");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Mã sản phẩm: "+ masp, true);

			cell = cells.getCell("F11");
			this.getCellStyle1(cell,  Color.BLACK, false, 11, "Số lô SX: "+ solo, true);

			cell = cells.getCell("A12");
			this.getCellStyle1(cell, Color.BLACK, false, 11, "Dạng bào chế: "+  dangbaoche, true);
			
			cell = cells.getCell("F12");
			this.getCellStyle1(cell,  Color.BLACK, false, 11,"Số lượng lô SX: "+   soluonglosx +" " + donvi, true);


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
			
			
			
			Color color_=Color.BLACK;
			
			List<ISanphamBom> spConList = sp.getSpListBom();
			int kt=0; 
			int  j =0;  
			int sott = 1;
			int countRow =  18;
         	String bomid="";
         	if(spConList.size() > 0)
         	{
         		while (j< spConList.size())
         		{
         			System.out.println(" stt: " +sott);
         			kt=0;
         			ISanphamBom spCon = spConList.get(j);
         			bomid=spCon.getDMVTId();
         			
					String sldinhmuc="";
					String slthuc="";
					String pkn="";
					String ghichu="";
					String maspvattu=spCon.getMasanpham();
					String donvivt= spCon.getDonVi();
					
					// LAY NGUYEN LIEU GOC
					String sql= " SELECT  MAPHIEU  FROM  ERP_MUAHANG_BOM_CHITIET" + 
							 	"\n  WHERE ISYCCK <>0 AND  MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" + spCon.getSpId() + "   AND  VATTU_FK = SANPHAM_FK";
					System.out.println("\n ma phieu nguyen lieu goc: "+ sql);	
					pkn=LayPkn(sql, db);
					
					
					// lay so luong yeu cau =so luong 
					String qr_dinhmuc="SELECT    SUM(SOLUONGQUYCHUANBOM) as soluongdinhmuc, SUM( CAST( soluong as numeric (18,3))) as soluongthuc FROM" + 
					  "\n ERP_MUAHANG_BOM_CHITIET " + 
					  "\n WHERE ISYCCK <>0 AND  MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" +spCon.getSpId()+ "   AND  VATTU_FK = SANPHAM_FK  having    SUM(SOLUONGQUYCHUANBOM) >0 and SUM(soluong) >0";
			         System.out.println(" san pham goc: "+ qr_dinhmuc);
			         
					//LAY SO LUONG DINH MUC CUA VAT TU THEO SO LUONG YEU CAU
					//NEU SO LUONG YEU CAU KHONG CO THI LAY SO LUONG TRONG BOM

					System.out.println("\n lenh qr so luong dinh muc: "+ qr_dinhmuc);
					ResultSet rs_dinhmuc=db.get(qr_dinhmuc);
					String mavattu=spCon.getSpId();
					System.out.println("\n ma pk_seq vat tu: "+ mavattu);
					NumberFormat formatter_2sole = new DecimalFormat("#,###,###.###########");
					NumberFormat formatter_3sole = new DecimalFormat("#,###,###.###");
					
					
					
					if(rs_dinhmuc!=null){
						try {
							while(rs_dinhmuc.next())
							{
								
								System.out.println(" da vao nguyen lieu goc: "+ j);
								kt=1; // vat tu da yeu cau nguyen lieu
								sldinhmuc =spCon.getSoluongdenghi(); // lay so luong dinh muc cua nguyen lieu goc
								slthuc =rs_dinhmuc.getString("soluongthuc")== null?"0": rs_dinhmuc.getString("soluongthuc");	 
							
								
								System.out.println(" so luong dinh muc: "+ sldinhmuc);
								System.out.println(" so luong slthuc : "+ Double.parseDouble(slthuc.replaceAll(",", "") ));
								System.out.println(" so luong slthuc xx: "+ slthuc +" \n \n ");
								 
								if(slthuc.trim().equals("0") ||slthuc. trim().equals(""))
								{
									slthuc="";
								}
								else
									if(  Double.parseDouble(slthuc.replaceAll(",", "")) ==  Double.parseDouble( sldinhmuc.replace(",", "") ) ){
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
								
								
									
							    String[] spTitles2 = {sott + "", spCon.getMasanpham(),spCon.gettensanpham()," "," ",donvivt,pkn,sldinhmuc,slthuc,ghichu};

							    for (int z = 0; z < spTitles2.length; z++) {
							    	
									cell = cells.getCell(countRow,z);
									// xác dịnh kieu du lieu
									/*if(z==5||z==6){
										ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 10,Double.parseDouble(spTitles2[z].replaceAll(",", "")) );
									}
									else*/
									
									
									ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 10, spTitles2[z]);
									
									
									
									// gop dong doi 
									if(z==2){
										z=z+2;
										cells.merge(countRow, 2, countRow, 4);
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
					
					System.out.println(" stt thuc te : "+ sott);
			
					
					//==================LAY NGUYEN THAY THE=================
					 sql= "select data1.MUAHANG_FK, data1.SANPHAM_FK,  b.TEN1 as tenvattu, b.MA as mavattu, data1.soluongthuc  from  " + 
					 "\n (select MUAHANG_FK,SANPHAM_FK, soluongthuc from " + 
					 "\n ( " + 
					 "\n SELECT  MUAHANG_FK,SANPHAM_FK, SUM(SOLUONGQUYCHUANBOM) as soluongthuc  FROM  ERP_MUAHANG_BOM_CHITIET " + 
					 "\n WHERE ISYCCK <>0 AND   MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" +  spCon.getSpId() + "  AND  VATTU_FK <> SANPHAM_FK " + 
					 "\n GROUP BY MUAHANG_FK,SANPHAM_FK ) data ) data1 " + 
					 "\n inner join ERP_SANPHAM b on b.PK_SEQ=data1.SANPHAM_FK" ;		 
		
				System.out.println(" san pham thay the: "+ sql);
				ResultSet rs_nglieuthaythe=db.get(sql);
			
				if(rs_nglieuthaythe!=null){
					try {
					while(rs_nglieuthaythe.next()){
						
						System.out.println(" da vao nguyen lieu  thay the: "+ j);
						kt =1;
						
						String idspthaythe=rs_nglieuthaythe.getString("SANPHAM_FK");
						String maspthaythe=rs_nglieuthaythe.getString("mavattu");
						String tenspthaythe=rs_nglieuthaythe.getString("tenvattu");
						String sldinhmuc1="";
						String slthuc1="";
						String pkn1="";
						
						// tim so luong dinh muc tu ERP_DANHMUCVATTU_VATTU_THAYTHE
						
						String qr_dinhmucthaythe="select isnull (SOLUONGCHUAN_THAYTHE,0) as SOLUONGCHUAN_THAYTHE  from ERP_MUAHANG_BOM where MUAHANG_FK="+ lsxBean.getId()+"  and VATTU_FK="+ spCon.getSpId()  ;
						System.out.println(" so luong sldinhmuc thay the: "+ qr_dinhmucthaythe +" \n \n ");
						
						ResultSet rs_dinhmucthaythe=db.get(qr_dinhmucthaythe);
						if( rs_dinhmucthaythe != null){
							try {
								while (rs_dinhmucthaythe.next()){
									sldinhmuc1=rs_dinhmucthaythe.getString("SOLUONGCHUAN_THAYTHE")== null?"0": rs_dinhmucthaythe.getString("SOLUONGCHUAN_THAYTHE");	
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						rs_dinhmucthaythe.close();
						slthuc1 =rs_nglieuthaythe.getString("soluongthuc")== null?"0": rs_nglieuthaythe.getString("soluongthuc");
						
						// lay phieu kiem nghiem
						sql= " SELECT  MAPHIEU  FROM  ERP_MUAHANG_BOM_CHITIET" + 
					 	"\n WHERE  ISYCCK <>0 AND MUAHANG_FK= " +lsxBean.getId()  + " AND VATTU_FK=" +  spCon.getSpId() + "    AND  SANPHAM_FK = "+idspthaythe;
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
				    String [] spTitles3 = {sott + "", maspthaythe,tenspthaythe ," "," ",donvivt,pkn1,sldinhmuc1,slthuc1, ghichu};

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
				System.out.println(" da vao nguyen lieu  chua chuyen nguyen lieu: "+ j);
				String[] spTitles2 = {sott + "", spCon.getMasanpham(),spCon.gettensanpham(), " "," ",donvivt," ",formatter_2sole.format(Double.parseDouble( spCon.getSoluongdenghi().replaceAll(",", "")))   ,"", ghichu};
			
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
         		
         	}
			
			
			
			
			countRow+=2;
			/*cell = cells.getCell("A"+countRow);
			this.getCellStyle(cell, Color.BLACK, true, 12,"T/L TỔNG GIÁM ĐỐC", HorizontalAlignmentType.LEFT);
			
			cell = cells.getCell("B"+ (countRow+1));
			this.getCellStyle(cell, Color.BLACK, true, 12,"TP. Kế hoạch" ,HorizontalAlignmentType.CENTRED);
			cell = cells.getCell("B"+ (countRow+3));
			this.getCellStyle(cell, Color.BLACK, false, 10,"NGUYỄN VĂN NHƯỢNG",HorizontalAlignmentType.CENTRED);
			
			
			
			
			cell = cells.getCell("D"+countRow);
			this.getCellStyle(cell, Color.BLACK, true, 12,"NGƯỜI NHẬN",HorizontalAlignmentType.LEFT);
			cell = cells.getCell("D"+(countRow+3));
			this.getCellStyle(cell, Color.BLACK, false, 10,"NGUYỄN THỊ VÂN ANH",HorizontalAlignmentType.CENTRED);

			
			cell = cells.getCell("G"+countRow);
			this.getCellStyle(cell, Color.BLACK, true, 12,"TP. ĐBCL",HorizontalAlignmentType.RIGHT);
			cell = cells.getCell("G"+ (countRow+3));
			this.getCellStyle(cell, Color.BLACK, true, 10," ");
			
			
			cell = cells.getCell("I"+countRow);
			this.getCellStyle(cell, Color.BLACK, true, 12,"NGƯỜI SOẠN THẢO",HorizontalAlignmentType.CENTRED);
			cell = cells.getCell("I"+(countRow+3));
			this.getCellStyle(cell, Color.BLACK, false, 10,"ĐỖ THÚY NGA",HorizontalAlignmentType.CENTRED);*/
			
			
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
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}


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
		style.setTextWrapped(true);
		style.setFont(font);
		
		cell.setStyle(style);
		cell.setValue(cellValue);
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

	
	
}
