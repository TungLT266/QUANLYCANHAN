package geso.traphaco.erp.servlets.hoadonphelieu;

import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.center.beans.doctien.doctienrachu;

import geso.traphaco.erp.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.erp.beans.hoadonphelieu.IErpHoadonphelieu;

import geso.traphaco.erp.beans.hoadonphelieu.imp.ErpHoadonphelieu;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpHoaDonPheLieuPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final float CONVERT = 28.3464f;

	// PrintWriter out;
	public ErpHoaDonPheLieuPdfSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		IErpHoadonphelieu csxBean;

		// this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		// out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String userTen = (String) session.getAttribute("userTen");
		String id = util.getId(querystring);

		csxBean = new ErpHoadonphelieu(id);
		String ctyId = (String) session.getAttribute("congtyId");

		csxBean.setCongtyId(ctyId);
		csxBean.setId(id);
		csxBean.setUserId(userId);

		csxBean.init();

		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					" inline; filename=HoaDonTaiChinh.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();

			// this.CreateHdPdf_HaNoi(document, outstream, obj, ddhId);
			this.CreatePxk_hanoi(document, outstream, csxBean, userTen);
			System.out.println(" da vao dayyyyyyyyyyyyyyyyyyyyyyyyy ");

			document.close();
		} catch (Exception e) {
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * private void CreateHdPdf_HaNoi(Document document, ServletOutputStream
	 * outstream, IErpHoadonphelieu pxkBean, String hdId) throws IOException {
	 * System.out.println(" da vao ha noi cu "); try{ dbutils db = new
	 * dbutils();
	 * 
	 * //LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI) String query =
	 * "	SELECT a.pk_seq,a.sohoadon, a.kyhieuhoadon KYHIEU, a.ngayhoadon ,isnull(b.TenXuatHD,'') TEN,'' NGUOIMUAHANG, b.MST MASOTHUE, b.DiaChi,a.vat, a.avat, a.HANGHOADICHVU, isnull(b.TenXuatHD, '') DONVI, a.HINHTHUCTHANHTOAN, a.DIENGIAI, a.doanhthu_fk, \n"
	 * +
	 * "		 isnull(a.DONVI,'') DONVIVL, isnull(a.NGUOIMUAHANG, '') NGUOIMUAHANGVL, isnull(a.NGUOIMUAHANG,'')  TENVL , isnull(a.MASOTHUE,'') MASOTHUEVL, isnull(a.DiaChi,'') diachivl, a.KHACHHANG_FK, a.LAMTRONVAT "
	 * +
	 * "	FROM erp_hoadonphelieu a INNER JOIN ERP_KHACHHANG b ON a.khachhang_fk = b.PK_SEQ \n"
	 * + "	WHERE a.pk_seq = '"+ hdId +"' \n";
	 * 
	 * System.out.println(query);
	 * 
	 * String SOHOADON=""; String KYHIEU=""; String NGAYXUATHD =""; String
	 * HINHTHUCTT=""; String MASOTHUE=""; String DIACHI=""; String NGUOIMUA="";
	 * String TENKH=""; String HANGHOADICHVU = ""; String TENHANGHOADV = "";
	 * String DONVI = ""; String KHACHHANG_FK = ""; String doanhthu =""; String
	 * SOCHUNGTU = "";
	 * 
	 * double TIENCK=0; double VAT=0; double TONGTIENAVAT=0; int lamtronvat = 0;
	 * 
	 * System.out.println("THONGTINNPP__:"+query); ResultSet rs = db.get(query);
	 * if(rs!=null){ while(rs.next()){ SOCHUNGTU = rs.getString("pk_seq");
	 * KHACHHANG_FK = rs.getString("KHACHHANG_FK");
	 * if(KHACHHANG_FK.equals("107385"
	 * )||KHACHHANG_FK.equals("107573")||KHACHHANG_FK
	 * .equals("107689")||KHACHHANG_FK.equals("107695")){ MASOTHUE =
	 * rs.getString("MASOTHUEVL"); DIACHI = rs.getString("DIACHIVL"); TENKH =
	 * rs.getString("TENVL"); NGUOIMUA = rs.getString("NGUOIMUAHANGVL"); DONVI =
	 * rs.getString("DONVIVL");
	 * if(MASOTHUE.trim().length()<=0&&DIACHI.trim().length
	 * ()<=0&&NGUOIMUA.trim().length()<=0&&DONVI.trim().length()<=0){ MASOTHUE =
	 * rs.getString("MASOTHUE"); DIACHI = rs.getString("DIACHI"); TENKH =
	 * rs.getString("TEN"); NGUOIMUA = rs.getString("NGUOIMUAHANG"); DONVI =
	 * rs.getString("DONVI"); } } else{ MASOTHUE = rs.getString("MASOTHUE");
	 * DIACHI = rs.getString("DIACHI"); TENKH = rs.getString("TEN"); NGUOIMUA =
	 * rs.getString("NGUOIMUAHANG"); DONVI = rs.getString("DONVI"); } SOHOADON =
	 * rs.getString("SOHOADON"); KYHIEU = rs.getString("KYHIEU"); NGAYXUATHD =
	 * rs.getString("ngayhoadon"); VAT = rs.getDouble("VAT"); TONGTIENAVAT =
	 * rs.getDouble("avat"); HANGHOADICHVU = rs.getString("DIENGIAI");
	 * TENHANGHOADV = rs.getString("HANGHOADICHVU"); HINHTHUCTT = rs.getString
	 * ("HINHTHUCTHANHTOAN"); doanhthu = rs.getString ("DOANHTHU_FK");
	 * lamtronvat = rs.getInt("LAMTRONVAT"); }
	 * 
	 * 
	 * }
	 * 
	 * if(TONGTIENAVAT<0) TONGTIENAVAT = TONGTIENAVAT*(-1);
	 * 
	 * NumberFormat formatter = new DecimalFormat("#,###,###.###"); NumberFormat
	 * formatter1 = new DecimalFormat("#,###,###");
	 * //document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
	 * document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 0f*CONVERT,
	 * 2.0f*CONVERT); // L,R, T, B PdfWriter writer =
	 * PdfWriter.getInstance(document, outstream);
	 * 
	 * document.open(); //document.setPageSize(new Rectangle(100.0f, 100.0f));
	 * //document.setPageSize(PageSize.A4.rotate());
	 * 
	 * 
	 * BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
	 * BaseFont.IDENTITY_H, BaseFont.EMBEDDED); Font font = new Font(bf, 13,
	 * Font.BOLD); Font font2 = new Font(bf, 8, Font.BOLD);
	 * 
	 * //SO CHUNG TU PdfPTable tablepkseq =new PdfPTable(1);
	 * tablepkseq.setWidthPercentage(100);
	 * 
	 * PdfPCell cell = new PdfPCell();
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * 
	 * Paragraph hd = new Paragraph(SOCHUNGTU , new Font(bf, 12, Font.BOLD));
	 * hd.setAlignment(Element.ALIGN_RIGHT); cell.setFixedHeight(1.6f*CONVERT);
	 * cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
	 * cell.setPaddingLeft(1.8f*CONVERT); cell.setPaddingTop(0.85f*CONVERT);
	 * cell.setBorder(0); cell.addElement(hd);
	 * 
	 * tablepkseq.addCell(cell); document.add(tablepkseq);
	 * 
	 * 
	 * //NGÀY THÁNG NĂM PdfPTable tableheader =new PdfPTable(1);
	 * tableheader.setSpacingBefore(3.2f*CONVERT);
	 * tableheader.setWidthPercentage(100);
	 * 
	 * cell = new PdfPCell(); cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * 
	 * String [] ngayHD = NGAYXUATHD.split("-"); hd = new Paragraph(ngayHD[2] +
	 * "         thu test        " + ngayHD[1] + "             " + ngayHD[0] ,
	 * new Font(bf, 12, Font.BOLD)); hd.setAlignment(Element.ALIGN_CENTER);
	 * cell.setFixedHeight(1.50f*CONVERT);
	 * cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
	 * cell.setPaddingLeft(2.5f*CONVERT); cell.setPaddingTop(0.75f*CONVERT);
	 * cell.setBorder(0); cell.addElement(hd);
	 * 
	 * tableheader.addCell(cell); document.add(tableheader);
	 * 
	 * //NGƯỜI MUA HÀNG
	 * 
	 * PdfPTable table1 = new PdfPTable(1); table1.setWidthPercentage(100);
	 * float[] withs1 = {100f}; table1.setWidths(withs1);
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph(NGUOIMUA , new Font(bf, 12,
	 * Font.BOLD));//NGUOI MUA hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); //hd.setSpacingAfter(4);
	 * cell.setPaddingLeft(6.0f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0);
	 * 
	 * table1.addCell(cell); document.add(table1);
	 * 
	 * //ĐƠN VỊ
	 * 
	 * PdfPTable table2 = new PdfPTable(1); table2.setWidthPercentage(100);
	 * float[] withs2 = {100f}; table1.setWidths(withs2);
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph(DONVI , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); //hd.setSpacingAfter(4);
	 * cell.setPaddingLeft(2.0f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0);
	 * 
	 * table2.addCell(cell); document.add(table2);
	 * 
	 * 
	 * //ĐỊA CHỈ
	 * 
	 * PdfPTable table3 =new PdfPTable(1); table3.setWidthPercentage(100);
	 * float[] withs3 = {100f}; table3.setWidths(withs3);
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph( DIACHI , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingLeft(2.0f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0);
	 * 
	 * table3.addCell(cell); document.add(table3);
	 * 
	 * //HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
	 * 
	 * PdfPTable table4 =new PdfPTable(2); table4.setWidthPercentage(100);
	 * float[] withs4 = {200f,200f}; table4.setWidths(withs4);
	 * 
	 * cell= new PdfPCell(); hd = new Paragraph( HINHTHUCTT , new Font(bf, 12,
	 * Font.BOLD)); HINH THUC THANH TOAN hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.addElement(hd);
	 * cell.setPaddingLeft(4.3f*CONVERT); cell.setBorder(0);
	 * 
	 * table4.addCell(cell);
	 * 
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph( MASOTHUE , new Font(bf, 12,
	 * Font.BOLD)); MÃ SỐ THUẾ hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.addElement(hd);
	 * cell.setPaddingLeft(2.9f*CONVERT); cell.setBorder(0);
	 * 
	 * table4.addCell(cell);
	 * 
	 * document.add(table4);
	 * 
	 * //THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
	 * 
	 * PdfPTable root = new PdfPTable(2); root.setKeepTogether(false);
	 * root.setSplitLate(false); root.setWidthPercentage(100);
	 * root.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * root.getDefaultCell().setBorder(0); float[] cr = { 95.0f, 100.0f };
	 * root.setWidths(cr);
	 * 
	 * String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
	 * 
	 * PdfPTable sanpham = new PdfPTable(th.length);
	 * sanpham.setSpacingBefore(1.5f*CONVERT); sanpham.setWidthPercentage(100);
	 * sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * 
	 * float[] withsKM = { 1.0f*CONVERT,9.5f*CONVERT, 3.3f*CONVERT,
	 * 2.2f*CONVERT, 2.5f*CONVERT, 3.5f*CONVERT, 3.0f*CONVERT };
	 * sanpham.setWidths(withsKM);
	 * 
	 * PdfPCell cells = new PdfPCell();
	 * 
	 * 
	 * String INIT_SANPHAM =
	 * " select diengiai, ISNULL(donvitinh, '') DONVITINH, soluong, dongia, isnull(dongiack, dongia) dongiack, thanhtien  \n"
	 * + " from erp_hoadonphelieu_sanpham "+
	 * " where hoadonphelieu_fk ='"+hdId+"'";
	 * 
	 * System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
	 * 
	 * String TENSP=""; String DONVITINH=""; int STT=0; int dong=0; double
	 * SOLUONG=0; double DONGIA=0; double DONGIADAGIAM =0; //double DONGIAGIAM
	 * =0; //double CHIETKHAU =0;
	 * 
	 * double THANHTIENGIAM = 0; double THANHTIEN = 0;
	 * 
	 * double TIENSAUCK = 0;
	 * 
	 * double TONGTIEN = 0;
	 * 
	 * if(doanhthu.equals("400004")||doanhthu.equals("400005")){ STT++; dong++;
	 * 
	 * TONGTIEN += THANHTIEN;
	 * 
	 * int vitri= 0; int dodaichuoi = TENSP.length(); int sodong_nguyen =
	 * dodaichuoi/38;
	 * 
	 * int sodong_du = dodaichuoi%38; if(sodong_du>0) sodong_nguyen++;
	 * 
	 * String chuoibd = TENSP; String chuoithu = ""; String chuoitiep = "";
	 * String chuoiin = ""; for (int i = 0; i<sodong_nguyen;i++){
	 * 
	 * dong++; dodaichuoi = chuoibd.length();
	 * 
	 * if(dodaichuoi>=38) { chuoithu = chuoibd.substring(0, 38); vitri =
	 * chuoithu.lastIndexOf(" "); chuoiin = chuoithu.substring(0,vitri);
	 * chuoitiep = chuoibd.substring(vitri + 1,dodaichuoi ); //chuoiin =
	 * chuoitiep; }
	 * 
	 * else{ chuoiin = chuoibd; } String[] arr = null; if(sodong_nguyen<=0){ arr
	 * = new String[] { Integer.toString(STT), TENHANGHOADV , "" , "" , "","" ,
	 * "" }; } else if(sodong_nguyen >=1){ if(i ==0){ arr = new String[] {
	 * Integer.toString(STT), chuoiin , "" , "" , "","" , "" }; } else{ arr =
	 * new String[] { "", chuoiin , "" , "" , "","" , "" }; } }
	 * 
	 * for (int j = 0; j < th.length; j++) { System.out.println(arr[j]); cells =
	 * new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD))); if (j
	 * <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
	 * cells.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * cells.setPaddingLeft(-0.1f*CONVERT); } else{ if(j == 2 )//ĐƠN VỊ TÍNH {
	 * cells.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * cells.setPaddingLeft(1.2f*CONVERT); } else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ
	 * GIẢM GIÁ, THÀNH TIỀN cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * if(j == 3 )//SỐ LƯỢNG { //cells.setPaddingRight(0.2f*CONVERT); }
	 * 
	 * if(j == 4 )//ĐƠN GIÁ { //cells.setPaddingRight(0.5f*CONVERT); }
	 * 
	 * if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM { cells.setPaddingRight(0.6f*CONVERT); }
	 * if(j == 6 )//THÀNH TIỀN { cells.setPaddingRight(-0.25f*CONVERT); } } }
	 * 
	 * cells.setVerticalAlignment(Element.ALIGN_BOTTOM); cells.setBorder(0);
	 * //cells.setBorderWidth(1); cells.setFixedHeight(0.8f * CONVERT);
	 * sanpham.addCell(cells);
	 * 
	 * chuoibd = chuoitiep; } }
	 * 
	 * } else if(!doanhthu.equals("400004")&&!doanhthu.equals("400005")){
	 * ResultSet rsSP= db.get(INIT_SANPHAM);
	 * 
	 * if(rsSP!=null){ while(rsSP.next()){ STT++; TENSP =
	 * rsSP.getString("diengiai"); DONVITINH = rsSP.getString("DONVITINH");
	 * SOLUONG = rsSP.getDouble("SOLUONG"); DONGIA =
	 * Math.round(rsSP.getDouble("DONGIA")); DONGIADAGIAM =
	 * Math.round(rsSP.getDouble("dongiack"));
	 * THANHTIEN=Math.round(rsSP.getDouble("thanhtien"));
	 * 
	 * TONGTIEN += THANHTIEN; if(DONGIA<0) DONGIA = DONGIA*(-1);
	 * if(DONGIADAGIAM<0) DONGIADAGIAM = DONGIADAGIAM*(-1); if(THANHTIEN<0)
	 * THANHTIEN = THANHTIEN*(-1);
	 * 
	 * int vitri= 0; int dodaichuoi = TENSP.length(); int sodong_nguyen =
	 * dodaichuoi/38; int sodong_du = dodaichuoi%38; if(sodong_du>0)
	 * sodong_nguyen++;
	 * 
	 * String chuoibd = TENSP; String chuoithu = ""; String chuoitiep = "";
	 * String chuoiin = "";
	 * 
	 * 
	 * 
	 * for (int i = 0; i<sodong_nguyen;i++){ dong++; dodaichuoi =
	 * chuoibd.length();
	 * 
	 * if(dodaichuoi>=38) { chuoithu = chuoibd.substring(0, 38); vitri =
	 * chuoithu.lastIndexOf(" "); chuoiin = chuoithu.substring(0,vitri);
	 * chuoitiep = chuoibd.substring(vitri + 1,dodaichuoi ); //chuoiin =
	 * chuoitiep; }
	 * 
	 * else{ chuoiin = chuoibd; } String[] arr = null; if(sodong_nguyen<=0){ arr
	 * = new String[] { Integer.toString(STT), TENSP , DONVITINH ,
	 * DinhDangCANFOCO(formatter1.format(SOLUONG)) ,
	 * DinhDangCANFOCO(formatter.format(DONGIA)),
	 * DinhDangCANFOCO(formatter.format(DONGIADAGIAM)) ,
	 * DinhDangCANFOCO(formatter.format(THANHTIEN)) }; } else if(sodong_nguyen
	 * >=1){ if(i ==0){ arr = new String[] { Integer.toString(STT), chuoiin ,
	 * DONVITINH , DinhDangCANFOCO(formatter1.format(SOLUONG)) ,
	 * DinhDangCANFOCO(formatter.format(DONGIA)),
	 * DinhDangCANFOCO(formatter.format(DONGIADAGIAM)) ,
	 * DinhDangCANFOCO(formatter.format(THANHTIEN)) }; } else{ arr = new
	 * String[] { "", chuoiin , "" , "" , "","" , "" }; } }
	 * 
	 * for (int j = 0; j < th.length; j++) { System.out.println(arr[j]); cells =
	 * new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD))); if (j
	 * <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
	 * cells.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * cells.setPaddingLeft(-0.1f*CONVERT); } else{ if(j == 2 )//ĐƠN VỊ TÍNH {
	 * cells.setHorizontalAlignment(Element.ALIGN_LEFT);
	 * cells.setPaddingLeft(1.2f*CONVERT); } else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ
	 * GIẢM GIÁ, THÀNH TIỀN cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 * 
	 * if(j == 3 )//SỐ LƯỢNG { //cells.setPaddingRight(0.2f*CONVERT); }
	 * 
	 * if(j == 4 )//ĐƠN GIÁ { //cells.setPaddingRight(0.5f*CONVERT); }
	 * 
	 * if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM { cells.setPaddingRight(0.6f*CONVERT); }
	 * if(j == 6 )//THÀNH TIỀN { cells.setPaddingRight(-0.25f*CONVERT); } } }
	 * 
	 * cells.setVerticalAlignment(Element.ALIGN_BOTTOM); cells.setBorder(0);
	 * //cells.setBorderWidth(1); cells.setFixedHeight(0.8f * CONVERT);
	 * sanpham.addCell(cells); chuoibd = chuoitiep; }
	 * 
	 * }
	 * 
	 * } } rsSP.close(); }
	 * 
	 * // DONG TRONG int kk=0; while(kk < 13-dong) { String[] arr_bosung = new
	 * String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	 * 
	 * for (int j = 0; j < th.length; j++) { cells = new PdfPCell(new
	 * Paragraph(arr_bosung[j], new Font(bf, 12, Font.BOLD)));
	 * cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
	 * cells.setHorizontalAlignment(Element.ALIGN_CENTER);
	 * cells.setFixedHeight(0.8f*CONVERT); cells.setBorder(0);
	 * 
	 * sanpham.addCell(cells); }
	 * 
	 * kk++; }
	 * 
	 * 
	 * document.add(sanpham);
	 * 
	 * //TỔNG TIỀN HÀNG
	 * 
	 * PdfPTable footter = new PdfPTable(2);
	 * //footter.setSpacingBefore(1.2f*CONVERT);
	 * footter.setWidthPercentage(100); footter.setSpacingBefore(0.5f*CONVERT);
	 * 
	 * float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
	 * footter.setWidths(withsfooter);
	 * 
	 * cell = new PdfPCell();
	 * 
	 * hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.BOLD));
	 * hd.setAlignment(Element.ALIGN_LEFT); cell.setFixedHeight(0.8f*CONVERT);
	 * cell.setPaddingLeft(1.0f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0);
	 * 
	 * footter.addCell(cell);
	 * 
	 * if(doanhthu.equals("400004")||doanhthu.equals("400005")){ cell = new
	 * PdfPCell(); hd = new Paragraph( "" , new Font(bf, 12, Font.BOLD));
	 * hd.setAlignment(Element.ALIGN_RIGHT); cell.setFixedHeight(0.8f*CONVERT);
	 * cell.setPaddingRight(-0.25f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0); footter.addCell(cell);
	 * 
	 * // TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph("" , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingLeft(5.0f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell);
	 * 
	 * TIENSAUCK = TONGTIEN - TIENCK;
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph( "" , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_RIGHT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingRight(-0.25f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell); } else
	 * if(!doanhthu.equals("400004")&&!doanhthu.equals("400005")){
	 * if(TONGTIEN<0) TONGTIEN = TONGTIEN*(-1);
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph(
	 * DinhDangCANFOCO(formatter.format(TONGTIEN)) , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_RIGHT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingRight(-0.25f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell);
	 * 
	 * // TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
	 * 
	 * if(TIENCK<0) TIENCK = TIENCK*(-1);
	 * 
	 * cell = new PdfPCell(); hd = new
	 * Paragraph(DinhDangCANFOCO(formatter.format(TIENCK)) , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingLeft(5.0f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell);
	 * 
	 * TIENSAUCK = TONGTIEN - TIENCK;
	 * 
	 * if(TIENSAUCK<0) TIENSAUCK = TIENSAUCK*(-1);
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph(
	 * DinhDangCANFOCO(formatter.format(TIENSAUCK)) , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_RIGHT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingRight(-0.25f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell); }
	 * 
	 * 
	 * 
	 * //VAT - TIỀN THUẾ
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph( VAT +"%", new Font(bf, 10,
	 * Font.BOLD)); hd = new Paragraph( DinhDangCANFOCO(formatter.format(VAT)) ,
	 * new Font(bf, 12, Font.BOLD)); hd.setAlignment(Element.ALIGN_LEFT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingLeft(5.0f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell);
	 * 
	 * double TIENTHUE =0; TIENTHUE = TONGTIEN*VAT/100;
	 * 
	 * if(doanhthu.equals("400004")||doanhthu.equals("400005")){//ĐIỀU CHỈNH
	 * THUẾ TIENTHUE = TONGTIENAVAT;
	 * 
	 * cell = new PdfPCell(); if(lamtronvat==0) hd = new Paragraph(
	 * DinhDangCANFOCO(formatter.format(TIENTHUE)) , new Font(bf, 12,
	 * Font.BOLD)); else hd = new Paragraph(
	 * DinhDangCANFOCO(formatter1.format(TIENTHUE)) , new Font(bf, 12,
	 * Font.BOLD));
	 * 
	 * hd.setAlignment(Element.ALIGN_RIGHT); cell.setFixedHeight(0.8f*CONVERT);
	 * cell.setPaddingRight(-0.25f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0); footter.addCell(cell); } else
	 * if(!doanhthu.equals("400004")&&!doanhthu.equals("400005")){
	 * if(TIENTHUE<0) TIENTHUE = TIENTHUE*(-1);
	 * 
	 * cell = new PdfPCell(); if(lamtronvat==0) hd = new Paragraph(
	 * DinhDangCANFOCO(formatter.format(TIENTHUE)) , new Font(bf, 12,
	 * Font.BOLD)); else hd = new Paragraph(
	 * DinhDangCANFOCO(formatter1.format(TIENTHUE)) , new Font(bf, 12,
	 * Font.BOLD));
	 * 
	 * hd.setAlignment(Element.ALIGN_RIGHT); cell.setFixedHeight(0.8f*CONVERT);
	 * cell.setPaddingRight(-0.25f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0); footter.addCell(cell); }
	 * 
	 * //TỔNG TIỀN THANH TOÁN
	 * 
	 * cell = new PdfPCell(); hd = new Paragraph( "" , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_RIGHT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0); footter.addCell(cell);
	 * 
	 * if(doanhthu.equals("400004")||doanhthu.equals("400005")){ cell = new
	 * PdfPCell(); hd = new Paragraph( "" , new Font(bf, 12, Font.BOLD));
	 * hd.setAlignment(Element.ALIGN_RIGHT); cell.setFixedHeight(0.8f*CONVERT);
	 * cell.setPaddingRight(-0.25f*CONVERT); cell.setPaddingTop(-0.1f*CONVERT);
	 * cell.addElement(hd); cell.setBorder(0); footter.addCell(cell); } else
	 * if(!doanhthu.equals("400004")&&!doanhthu.equals("400005")){ cell = new
	 * PdfPCell(); hd = new Paragraph(
	 * DinhDangCANFOCO(formatter1.format(TONGTIENAVAT)) , new Font(bf, 12,
	 * Font.BOLD)); hd.setAlignment(Element.ALIGN_RIGHT);
	 * cell.setFixedHeight(0.8f*CONVERT); cell.setPaddingRight(-0.25f*CONVERT);
	 * cell.setPaddingTop(-0.1f*CONVERT); cell.addElement(hd);
	 * cell.setBorder(0); footter.addCell(cell); } document.add(footter);
	 * 
	 * 
	 * //ĐỌC TIỀN RA CHỮ
	 * 
	 * DocTien doctien = new DocTien();
	 * 
	 * String tien = doctien.docTien(Math.round(TONGTIENAVAT));
	 * 
	 * //String tien = doctien.tranlate(tongtiencovat + ""); tien =
	 * tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
	 * if(tien.equals("Đồng")) tien="Không Đồng";
	 * System.out.println(" Tien là :"+tien);
	 * 
	 * Paragraph paradoctien = new Paragraph("            " + tien, new Font(bf,
	 * 12, Font.BOLD)); //paradoctien.setSpacingBefore(12.0f);
	 * paradoctien.setSpacingBefore(0.9f);
	 * paradoctien.setIndentationLeft(140.575f);
	 * 
	 * document.add(paradoctien);
	 * 
	 * 
	 * // Tien bang chu doctien doc = new doctienrachu(); //String tien =
	 * doctien.docTien(Math.round(totalSotienTT - totalTienCK)); String tien =
	 * doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",",
	 * ""))); //Viết hoa ký tự đầu tiên String TienIN =
	 * (tien.substring(0,1)).toUpperCase() + tien.substring(1);
	 * 
	 * String[] arr1 = new String[]
	 * {"                                           " + TienIN}; for (int j = 0;
	 * j < arr1.length; j++) { cells = new PdfPCell(new Paragraph(arr1[j], new
	 * Font(bf, 10, Font.BOLDITALIC))); if (j == 0) {
	 * cells.setHorizontalAlignment(Element.ALIGN_LEFT); cells.setColspan(12); }
	 * cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 * cells.setPaddingLeft(0.8f * CONVERT); cells.setPaddingTop(5.0f);
	 * cells.setBorder(0); cells.setFixedHeight(0.6f*CONVERT);
	 * sanpham.addCell(cells); }
	 * 
	 * 
	 * PdfPTable table7 =new PdfPTable(2); table7.setWidthPercentage(100);
	 * float[] withs7 = {100f,100f}; table7.setWidths(withs7);
	 * 
	 * //DONG 6 -- THUE SUAT VAT
	 * 
	 * PdfPCell cell_thuevat = new PdfPCell(); hd = new Paragraph( "10%" , new
	 * Font(bf, 10, Font.BOLD)); hd.setAlignment(Element.ALIGN_LEFT);
	 * hd.setSpacingAfter(2); cell_thuevat.addElement(hd);
	 * cell_thuevat.setPaddingLeft(2.0f*CONVERT); cell_thuevat.setBorder(0);
	 * 
	 * table7.addCell(cell_tienck);
	 * 
	 * //DONG 5 -- TIEN DA TRU CHIET KHAU TIENSAUCK = TONGTIENHANG - TIENCK;
	 * 
	 * PdfPCell cell_sauck = new PdfPCell(); hd = new Paragraph(
	 * formatter.format(TIENSAUCK) , new Font(bf, 10, Font.BOLD));
	 * hd.setAlignment(Element.ALIGN_LEFT); hd.setSpacingAfter(2);
	 * cell_sauck.addElement(hd); cell_sauck.setPaddingLeft(2.0f*CONVERT);
	 * cell_sauck.setBorder(0);
	 * 
	 * table7.addCell(cell_sauck);
	 * 
	 * document.add(table7);
	 * 
	 * } catch (Exception e) { System.out.println("115.Exception: " +
	 * e.getMessage()); e.printStackTrace(); }
	 * 
	 * }
	 */

	private void CreatePxk_hanoi(Document document,
			ServletOutputStream outstream, IErpHoadonphelieu obj, String userTen)
			throws IOException {
		System.out.println("vao ha noi");
		try {
			dbutils db = new dbutils();
			String nguoimuahang = " ";
			String khoxuat = " ";
			String thukho = " ";
			String thutruongdonvi = " ";
			String sql = "";

			// LAY THONG TIN KHACHHANG

			String Donvi = "";
			String kh_MST = "";
			String kh_Diachi = "";
			String hinhthucTT = "";
			String ngayxuatHD = obj.getNgayhoadon();
			String chucuahieu = "";

			// LẤY THEO DỮ LIỆU MỚI
			sql = "SELECT PK_SEQ  AS PK_SEQ, TEN AS TEN,ISNULL( DIACHI,'') AS DIACHI, ISNULL(MASOTHUE,'') AS MASOTHUE,  "
					+ "\n  TENNGUOIDAIDIEN AS TENNGUOIDAIDIEN, HINHTHUCTHANHTOAN AS HINHTHUCTHANHTOAN  "
					+ "\n  from NHAPHANPHOI WHERE TRANGTHAI = '1' AND PK_SEQ="
					+ obj.getkhId();

			System.out.println("TT KH " + sql);
			ResultSet rsLayKH = db.get(sql);
			if (rsLayKH.next()) {
				Donvi = rsLayKH.getString("TEN");
				kh_MST = rsLayKH.getString("MASOTHUE");
				kh_Diachi = rsLayKH.getString("DIACHI");
				chucuahieu = rsLayKH.getString("TENNGUOIDAIDIEN");
				hinhthucTT = rsLayKH.getString("HINHTHUCTHANHTOAN");
				rsLayKH.close();

			}

			NumberFormat formatter = new DecimalFormat("#,###,###.####");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f * CONVERT, 1.5f * CONVERT, 1.7f * CONVERT,
					0.0f * CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			PdfPTable tableheader = new PdfPTable(1);
			tableheader.setWidthPercentage(100);

			PdfPCell cell = new PdfPCell();

			cell.setBorder(0);
			cell.setPaddingTop(0.67f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			String[] ngayHD = ngayxuatHD.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2]
					+ "                        " + ngayHD[1]
					+ "                             " + ngayHD[0], new Font(bf,
					8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			// Thông tin Khach Hang
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = { 15.0f * CONVERT, 15.0f * CONVERT };
			table1.setWidths(withs1);

			// DONG 1-- NGUOI MUA HANG
			cell = new PdfPCell();
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			cell = new PdfPCell();
			cell.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			// DONG 2-- DON VI
			cell = new PdfPCell();
			cell.setPaddingLeft(2.7f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			cell = new PdfPCell();
			cell.setPaddingTop(-0.19f * CONVERT);
			cell.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			// DONG 3 ---- DIA CHI
			cell = new PdfPCell();
			cell.setPaddingLeft(2.5f * CONVERT);
			cell.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			String chuoi_ = "";
			String chuoi1 = kh_Diachi;
			String chuoi2 = "";
			int vitri = 0;
			int dodaichuoi = kh_Diachi.length();
			if (dodaichuoi >= 70) {
				chuoi_ = kh_Diachi.substring(0, 70);
				vitri = chuoi_.lastIndexOf(" ");
				chuoi1 = chuoi_.substring(0, vitri);
				chuoi2 = kh_Diachi.substring(vitri + 1, dodaichuoi);
			}

			cell = new PdfPCell();
			cell.setPaddingTop(-0.1f * CONVERT);
			cell.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			// DONG 4 --- DIA CHI : dai se xuong dong
			cell = new PdfPCell();
			cell.setPaddingTop(-0.2f * CONVERT);
			cell.setPaddingLeft(2.5f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			cell = new PdfPCell();
			cell.setPaddingTop(-0.2f * CONVERT);
			cell.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			// DONG 5 ----KHO XUAT
			cell = new PdfPCell();
			cell.setPaddingLeft(2.9f * CONVERT);
			cell.setPaddingTop(-0.2f * CONVERT);
			pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			if (kh_MST.trim().length() <= 0) {
				kh_MST = "                             ";
			}

			cell = new PdfPCell();
			cell.setPaddingLeft(5.0f * CONVERT);
			cell.setPaddingTop(-0.2f * CONVERT);
			pxk = new Paragraph(kh_MST
					+ "                                                "
					+ hinhthucTT, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);
			cell.setBorder(0);
			table1.addCell(cell);

			document.add(table1);

			// Table Content

			String[] th = new String[] { " ", " ", " ", "  ", " ", " ", " ",
					" ", " ", " ", " ", " " };
			PdfPTable tblsanpham = new PdfPTable(th.length);
			tblsanpham.setSpacingBefore(52.4f - (float) (0.5 * CONVERT));
			tblsanpham.setWidthPercentage(100);
			tblsanpham.setHorizontalAlignment(Element.ALIGN_LEFT);

			float[] withsKM = { 7.0f, 15.0f, 60f,
					17f - (float) (0.2 * CONVERT), 17f, 8.0f, 14.0f, 20f,
					24.0f, 8.0f, 23f, 25f };
			tblsanpham.setWidths(withsKM);

			PdfPCell cells;
			int stt = 1;
			int dem = -1;
			int sodongthem = 0;
			double pttthue = Double.parseDouble(obj.getVat());
			double tongtientruoc_Vat = Double.parseDouble(obj.getBvat()
					.replaceAll(",", ""));
			double tongtien_Vat = pttthue * tongtientruoc_Vat;
			double tongtiensau_Vat = Double.parseDouble(obj.getAvat()
					.replaceAll(",", ""));

			// Lay thong tin bang
			List<IErpHoaDonPL_SP> splist = obj.GetSanPhamList();
			if (splist != null) {
				IErpHoaDonPL_SP sanpham;
				for (int i = 0; i < splist.size(); i++) {
					sanpham = splist.get(i);
					if (sanpham.getTenSanPham().trim().length() > 0) {

						Double tienthue = Double.valueOf(sanpham.getThanhTien()
								.replaceAll(",", "")) * pttthue / 100;
						Double tienthanhtoan = tienthue
								+ Double.valueOf(sanpham.getThanhTien()
										.replaceAll(",", ""));

						int vitri1 = 0;
						dodaichuoi = sanpham.getTenSanPham().length();
						String chuoicd = sanpham.getTenSanPham();
						chuoi1 = sanpham.getTenSanPham();
						chuoi2 = "";
						int boiso = ((dodaichuoi % 29) > 0 ? (int) (dodaichuoi / 29) + 1
								: (int) (dodaichuoi / 29));

						if (dodaichuoi >= 29) {
							sodongthem += boiso;
							chuoi1 = sanpham.getTenSanPham().substring(0, 29);
							vitri1 = chuoi1.lastIndexOf(" ");
							chuoicd = chuoi1.substring(0, vitri1);
							chuoi2 = sanpham.getTenSanPham().substring(
									vitri1 + 1, dodaichuoi);
						}

						String[] arr = new String[] { stt + "", " ", chuoicd,
								" ", " ", sanpham.getDonViTinh(),
								sanpham.getSoLuong(), sanpham.getDonGia(),
								sanpham.getThanhTien(),
								formatter1.format(pttthue),
								formatter1.format(tienthue),
								formatter1.format(tienthanhtoan) };

						for (int j = 0; j < th.length; j++) {
							cells = new PdfPCell(new Paragraph(arr[j],
									new Font(bf, 10, Font.NORMAL)));
							cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							cells.setBorder(0);
							cells.setFixedHeight(0.6f * CONVERT);

							if (j <= 2 || j == 4) {
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							} else {
								if (j == 3) {
									cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								} else {
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}
							}

							cells.setVerticalAlignment(Element.ALIGN_BOTTOM);

							if (j == 4) {
								cells.setPaddingLeft(0.1f * CONVERT);
							}
							if (j >= 10) {
								cells.setPaddingRight(0.3f * CONVERT);
							}

							tblsanpham.addCell(cells);
						}

						if (chuoi2.length() > 0) {
							String[] arrSP = new String[] { "", "", chuoi2, "",
									"", "", "", "", "", "", "", "" };
							for (int j = 0; j < th.length; j++) {
								cells = new PdfPCell(new Paragraph(arr[j],
										new Font(bf, 10, Font.NORMAL)));
								cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								cells.setBorder(0);
								cells.setFixedHeight(0.6f * CONVERT);

								if (j <= 2 || j == 4) {
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								} else {
									if (j == 3) {
										cells.setHorizontalAlignment(Element.ALIGN_CENTER);
									} else {
										cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
								}

								cells.setVerticalAlignment(Element.ALIGN_BOTTOM);

								if (j == 4) {
									cells.setPaddingLeft(0.1f * CONVERT);
								}
								if (j >= 10) {
									cells.setPaddingRight(0.3f * CONVERT);
								}

								tblsanpham.addCell(cells);
							}
							dem++;
						}
						stt++;
					}
				}
			}
			// DONG TRONG
			int kk = 0;
			while (kk < 13 - stt - dem) {

				for (int j = 0; j < th.length; j++) {
					cells = new PdfPCell(new Paragraph(th[j], new Font(bf, 10,
							Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);

					tblsanpham.addCell(cells);
				}

				kk++;
			}

			String[] arr = new String[] { " ", " ", " ", " ", " ", " ", " ",
					" ", formatter1.format(tongtientruoc_Vat), " ",
					formatter1.format(tongtien_Vat),
					formatter1.format(tongtiensau_Vat) };

			for (int j = 0; j < arr.length; j++) {
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10,
						Font.NORMAL)));
				if (j <= 2 || j == 4) {
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				} else {
					if (j == 3) {
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					} else {
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
				}

				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);

				if (j == 4) {
					cells.setPaddingLeft(0.1f * CONVERT);
				}
				if (j >= 10) {
					cells.setPaddingRight(0.3f * CONVERT);
				}

				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setPaddingLeft(0.8f * CONVERT);
				// cells.setPaddingTop(-8.4f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f * CONVERT);
				tblsanpham.addCell(cells);
			}

			// Tien bang chu'
			doctienrachu doctien = new doctienrachu();

			String tien = doctien.docTien(Math.round(tongtiensau_Vat));

			// Viết hoa ký tự đầu tiên
			String TienIN = (tien.substring(0, 1)).toUpperCase()
					+ tien.substring(1);

			cells = new PdfPCell(new Paragraph(
					"                                           " + TienIN,
					new Font(bf, 10, Font.BOLDITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setColspan(12);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPaddingLeft(0.8f * CONVERT);
			cells.setPaddingTop(-5.6f);
			cells.setBorder(0);
			tblsanpham.addCell(cells);

			document.add(tblsanpham);

			// Thông tin Khach Hang
			PdfPTable table_footer = new PdfPTable(3);
			table_footer.setWidthPercentage(100);
			float[] withsfooter = { 8.0f * CONVERT, 11.3f * CONVERT,
					14.5f * CONVERT };
			table_footer.setWidths(withsfooter);

			//

			PdfPCell cellfooter12 = new PdfPCell();
			pxk = new Paragraph("Bán Hàng Qua Điện Thoại", new Font(bf, 11,
					Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cellfooter12.setPaddingTop(2.2f * CONVERT);
			cellfooter12.setPaddingLeft(2.5f * CONVERT);
			cellfooter12.addElement(pxk);
			cellfooter12.setBorder(0);
			// cellfooter12.setBorderWidth(1);
			table_footer.addCell(cellfooter12);

			// DONG 1-- NGUOI MUA HANG
			PdfPCell cellfooter = new PdfPCell();
			pxk = new Paragraph(userTen, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cellfooter.setPaddingTop(2.2f * CONVERT);
			cellfooter.setPaddingLeft(1.5f * CONVERT);
			cellfooter.addElement(pxk);
			cellfooter.setBorder(0);
			table_footer.addCell(cellfooter);

			PdfPCell cellfooter2 = new PdfPCell();
			pxk = new Paragraph(thukho, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cellfooter2.setPaddingTop(2.2f * CONVERT);
			cellfooter2.addElement(pxk);
			cellfooter2.setBorder(0);
			table_footer.addCell(cellfooter2);

			document.add(table_footer);

		} catch (Exception e) {
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	protected boolean xuLyTenList(IErpHoadonphelieu hdBean,
			List<String> _tenList, String tenSp, int sokytu1sp, boolean changeSp) {

		String[] words = new String[0];
		String _ten = "", _ten2 = "";

		words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu
																// trong ten san
																// pham
		_ten = "";
		_ten2 = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xử lý khi 1 từ > số ký tự 1 dòng
			if (words[_i].length() > sokytu1sp) {
				if (_ten.trim().length() > 0)
					_tenList.add(_ten); // Thêm dòng cũ
				_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten
						+ (_ten.length() == 0 ? words[_i] : " " + words[_i]);
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
		return changeSp;
	}

	public static String formatVN(String so) {

		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");

		return result;

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
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
	}

	public static void main(String[] arg) throws DocumentException, IOException {

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

	private String DinhDangCANFOCO(String sotien) {
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");

		return sotien;
	}

	private String DinhDangTRAPHACO(String sotien) {
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");

		return sotien;
	}

	private boolean SoNgay(String ngayxuathd) {
		boolean kt = false;
		int songay = 0;
		// Náº¾U NGÃ€Y XUáº¤T HÃ“A Ä�Æ N > '2014-12-08' THÃŒ Ä�Æ¯A Vá»€ Ä�á»ŠNH
		// Dáº NG Má»šI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2014-12-08', '" + ngayxuathd
				+ "') songay";
		ResultSet checkngay = db.get(layngay);

		try {
			if (checkngay.next()) {
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if (songay >= 0)
				kt = true;
		} catch (Exception e) {
			e.printStackTrace();
			kt = false;
		}

		return kt;

	}

}
