package geso.traphaco.erp.servlets.thanhtoanhoadon;

import geso.traphaco.center.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.traphaco.center.beans.cauhinhinhoadon.imp.ErpCauHinhInHoaDon;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpTTHoaDonPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpTTHoaDonPdfSvl() {
        super();
    }
    dbutils db = new dbutils();
    float CONVERT = 28.346457f;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("id");
		String httt = request.getParameter("httt");
		if(httt == null)
			httt = "";
			
		IErpThanhtoanhoadon tthdBean = new ErpThanhtoanhoadon(id);
		tthdBean.setHtttId(httt);
	    tthdBean.setUserId(userId);
	    
	    
	    String nppId=(String) session.getAttribute("nppId");

	    float CONVERT = 28.346457f;
		float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		
		ServletOutputStream outstream = response.getOutputStream();		
		
		this.CreatePCPdf(document, outstream,tthdBean, id,nppId );
		
		document.close();
	   
		
	}
	private void createBody(Document document, BaseFont bf,  String nguoiNop, String doiTuong, String diaChi, String khoanThu,
			String soTien,String docTienRaChu, int size) {
		try {
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = { 20f,80f };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			cell = new PdfPCell();
			hd = new Paragraph("Người nhận tiền: " , new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph( nguoiNop, new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			//----
			cell = new PdfPCell();
			hd = new Paragraph("Địa chỉ: " , new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph( diaChi, new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			///-------------

			cell = new PdfPCell();
			hd = new Paragraph("Về khoản: " , new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph( khoanThu, new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			//-----
			cell = new PdfPCell();
			hd = new Paragraph("Số tiền: " , new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph(soTien+" VNĐ", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			//----
			cell = new PdfPCell();
			hd = new Paragraph("Bằng chữ :" , new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph( docTienRaChu, new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			//----
			
			cell = new PdfPCell();
			hd = new Paragraph("Kèm theo: ..... chứng từ gốc ", new Font(bf, size, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			cell.setColspan(2);

			table1.addCell(cell);

			table1.setSpacingAfter(2f);//10
			
			document.add(table1);
			
			// Phần kế toán trưởng và đã nhận đủ tiền
			table1 = new PdfPTable(3);
			float[] withs2 = { 150f, 150f,150f };
			table1.setWidths(withs2);
			
			cell = new PdfPCell();
			hd = new Paragraph("GIÁM ĐỐC CÔNG TY" , new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph("KẾ TOÁN TRƯỞNG ", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph("KẾ TOÁN THANH TOÁN ", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			table1.setSpacingAfter(40f);//30
			
			document.add(table1);
			
			hd = new Paragraph("Đã nhận đủ số tiền ( viết bằng chữ): ............................................................................" +
					"......................................................\n .................................................................................." +
					"......................................................................................................... ", new Font(bf, size, Font.NORMAL));
			hd.setSpacingAfter(2f);
			document.add(hd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createFooter(Document document, BaseFont bf, int size) {
		try {
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = { 100f, 100f};
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			//-1
			cell = new PdfPCell();
			hd = new Paragraph(" ", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Ngày ... tháng ... năm ..... ", new Font(bf, size, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			//2
			cell = new PdfPCell();
			hd = new Paragraph("THỦ QUỸ", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(-0.1f);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph(" NGƯỜI NHẬN TIỀN  ", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(-0.1f);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			//--3
			cell = new PdfPCell();
			hd = new Paragraph("", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(-0.1f);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("(Ghi rõ họ, tên) ", new Font(bf, size, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(-0.1f);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			table1.setSpacingAfter(45f);//30
			document.add(table1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createHeader(Document document, BaseFont bf, String id, int size, IErpThanhtoanhoadon tthdBean,String nppId) {
		try {
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = { 170f, 80f };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

//			int stt = Integer.parseInt(id) - 100000;
			
			
			//b1. lấy được mã chứng từ ra
			String machungtu = "";
			String sochungtu = "";
			String ngaytao = "";
			String query = "select MACHUNGTU, isnull(SOCHUNGTU, '') as SOCHUNGTU, NGAYTAO from  ERP_THANHTOANHOADON WHERE PK_SEQ = " + id;
			
			System.out.println("sochungtu:" + query);
			ResultSet rssochungtu = db.get(query);
			if(rssochungtu != null){
				try {
					if(rssochungtu.next()){
						machungtu = rssochungtu.getString("MACHUNGTU");
						sochungtu = rssochungtu.getString("SOCHUNGTU");
						ngaytao = rssochungtu.getString("NGAYTAO");
					}
					rssochungtu.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			//b2. lấy tách chuỗi lấy tháng vs năm
			String thangnam = machungtu.substring(4,10);
			System.out.println("thangnam: " + thangnam);
			String nam = ngaytao.substring(0,4);
			String thang = ngaytao.substring(5,7);
			//bước 3 lọc lấy danh sách theo tháng năm đó và sắp xếp theo ngày và theo mã
			query = "select ROW_NUMBER() over (order by (select 1)) as STT, PK_SEQ, MACHUNGTU, SOCHUNGTU, NGAYTAO " +
					"from  ERP_THANHTOANHOADON " +
					" WHERE MONTH(NGAYTAO) = "+thang+" AND YEAR(NGAYTAO) = "+nam+" " +
					"ORDER BY NGAYTAO, PK_SEQ";
			
			System.out.println("querystt " + query);
			
			String stts = "";
			ResultSet rsstt = db.get(query);
			int dem = 0;
			if(rsstt!= null){
				try {
					while(rsstt.next()){
						dem ++;
						if(rsstt.getString("PK_SEQ").equals(id)){
							stts = "" + dem;
						}
					}
					rsstt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(stts.length() < 2){
				stts = "0" + stts;
			}
			//sau đó lấy số thứ tự ra
			
			/*String chuoistt = machungtu.substring(0,11) + stts;
			System.out.println("catchuoi: " + chuoistt);
			*/
			
			String sql =" SELECT  ISNULL( CT.TEN,NPP.TEN) AS TEN,   ISNULL( CT.DIACHI,NPP.DIACHIXHD)  AS DIACHI   "
					+ " \n FROM NHAPHANPHOI NPP LEFT JOIN ERP_CONGTY CT ON NPP.congty_fk=CT.PK_SEQ WHERE NPP.PK_SEQ ="+nppId+" ";
			System.out.println(" lay ten chi nhanh: "+sql );
			ResultSet rs1=db.get(sql);
			String tencongty="";
			String diachi="Tân Quang, Văn Lâm, Hưng Yên";
			try
			{
			if(rs1.next())
			{
				tencongty=rs1.getString("TEN");
				
			}
			rs1.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			/*cell = new PdfPCell();
			hd = new Paragraph("CÔNG TY CỔ PHẦN TRAPHACO \n75 Yên Ninh", new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);*/
			cell = new PdfPCell();
			hd = new Paragraph(tencongty+ "\n"+ diachi, new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			
			cell = new PdfPCell();
			hd = new Paragraph(/*"Số thứ tự: "+ stts+*/"\nSố phiếu  " + sochungtu, new Font(bf, size, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			table1.setSpacingAfter(15f);
			document.add(table1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void CreatePCPdf(Document document, ServletOutputStream outstream, IErpThanhtoanhoadon tthdBean, String Id,String nppId) throws IOException
	{

		dbutils db = new dbutils();
		try{
			//LẤY THÔNG TIN
			
			String query = " select hd.prefix,hd.nhanvien_fk, ISNULL(hd.NGUOINHANTIEN,'') AS NGUOINHANTIEN " +
			", hd.ngayghinhan, hd.ncc_fk , hd.httt_fk, hd.nganhang_fk," +
			" hd.noidungtt, hd.sotientt ,ncc.ma as mancc,isnull(ncc.ten,'') as tenncc, nv.ma as manv, isnull(nv.ten,'') as tennv," +
			" isnull(ncc.diachi,'') as dcncc,  isnull(nv.diachi,'') as dcnv, isnull(kh.diachi,'') as dckhachhang, " +
			" hd.khachhang_fk, kh.ma as makh, isnull(kh.ten,'') as tenkh, " +
			" isnull(hd.NHOMNCCCN,'0') nhomncccn , nc.DIENGIAI as tennhomncc , tk.SOHIEUTAIKHOAN +' -- '+ tk.TENTAIKHOAN as dinhkhoanno , " +
			" hd.doituongdinhkhoan , hd.MADOITUONGDINHKHOAN,isnull(hd.THANHTOANTUTIENVAY,0) as THANHTOANTUTIENVAY , " +
			" hd.ghichu, hd.loaithanhtoan, hd.sotienHD, hd.sotienHDNT, hd.sotientt, hd.sotienttNT, hd.phinganhang, hd.phinganhangNT, " +
			" hd.vat, hd.vatNT, hd.sotienttNT, isnull(hd.chenhlechVND,0) as chenhlechVND , trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, " +
			" PNH.mahoadon , PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, isnull(PNH.TIENHANG,0) as TIENHANG, " +
			" isnull(PNH.TIENTHUE,0) as TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK, hd.TIENTE_FK, hd.TIGIA, " +
			" isnull(hd.ctkemtheo, '') chungtukemtheo,  " +
			" hd.dvth_fk, dvth.pk_seq as dvthId, dvth.ma as dvthMa, isnull(dvth.Ten,'') as dvthTen \n"+
			
			" from ERP_THANHTOANHOADON hd  " +
			" left join ERP_THANHTOANHOADON_PHINGANHANG PNH on PNH.THANHTOANHD_FK = hd.PK_SEQ " +
			
			" left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk " +						
			" left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  " +
			" left join khachhang kh on kh.pk_seq = hd.khachhang_fk " +
			" left join NHOMNHACUNGCAPCN nc on nc.PK_SEQ = hd.Nhomncccn " +
			" left join erp_donvithuchien dvth on dvth.pk_seq = nv.dvth_fk " +
			" left join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN = hd.dinhkhoanno " +
			"where hd.pk_seq = '" + Id + "'";
			
			System.out.println("in pdf : " + query);
			NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
			String nguoiNop = "";
			String doiTuong = "";
			String diaChi = "";
			String khoanThu = "";
			String soTien = "";
			String ngayChungTu = "";
			long soTienN = 0;
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						nguoiNop = rs.getString("tennv") + rs.getString("tenncc") +rs.getString("tenkh") + rs.getString("NGUOINHANTIEN");
						diaChi = rs.getString("dvthTen") + rs.getString("dckhachhang") + rs.getString("dcncc");
						/*doiTuong = rs.getString("nppTen")+ rs.getString("nccTen")+ rs.getString("nvTen");*/
						khoanThu = rs.getString("noidungtt");
						soTien = formatter.format(rs.getDouble("sotientt"));
						soTienN = rs.getLong("sotientt");
						ngayChungTu = rs.getString("ngayghinhan");
					}
					rs.close(); 
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		
			/*nguoiNop = nguoiNop+ " - " + doiTuong;*/
			
			DocTien doctien = new DocTien();
			String docTienRaChu = doctien.docTien(soTienN);
			
			
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 0.2f*CONVERT, 0.2f*CONVERT); // T - 1, R, B-1, L
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			Font font = new Font(bf, 13, Font.BOLD);
//			Font font2 = new Font(bf, 8, Font.BOLD);
			
			//GIẤY ĐỀ NGHỊ THANH TOÁN
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

//			PdfPCell cell = new PdfPCell();		
			
			Paragraph hd = new Paragraph();
			
			
			// liên 1
			// tạo header
			createHeader(document, bf,Id,11, tthdBean,nppId);
			
			hd.add(new Chunk(" PHIẾU CHI ", new Font(bf, 18, Font.BOLD)));//16
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			hd = new Paragraph(); 
			hd.add(new Chunk(" Ngày: " + this.getDate(ngayChungTu), 
					new Font(bf, 11, Font.NORMAL)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			createBody(document, bf, nguoiNop, doiTuong, diaChi, khoanThu, soTien,docTienRaChu,11 );
			createFooter(document, bf, 11);
			
			
			hd = new Paragraph(".................................................................................." +
					"................................................................................................ ", 
					new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			// liên 2
			// tạo header
			createHeader(document, bf,Id,11, tthdBean,nppId);
			hd = new Paragraph(); 
			hd.add(new Chunk(" PHIẾU CHI", new Font(bf, 18, Font.BOLD)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			hd = new Paragraph(); 
			hd.add(new Chunk(" Ngày:" + this.getDate(ngayChungTu), 
					new Font(bf, 11, Font.NORMAL)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			createBody(document, bf, nguoiNop, doiTuong, diaChi, khoanThu, soTien,docTienRaChu,11 );
			createFooter(document, bf,11);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} finally{
			db.shutDown();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	private void CreateUyNhiemChiPdf(Document document, ServletOutputStream outstream, IErpThanhtoanhoadon tthdBean) 
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
//			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
//			String soTk = "";
			String sotientt = "";
			long sotien = 0;
//			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanvien_fk;

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100005), '') as congty, " +
							" 	isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, " +
							" 	isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, " +
							" 	tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, " +
							" 	tthd.NGAYGHINHAN as NGAYGHINHAN, " +
							"	tthd.nhanvien_fk  " +
							" from ERP_THANHTOANHOADON tthd " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  " +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  " +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq " +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  " +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  " +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  " +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  " +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' ";
			System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ngayghinhan = rs.getString("NGAYGHINHAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						//traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
//							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
						} else {
							nhanTen = rs.getString("nccTen");
//							nhanDiaChi = rs.getString("nccTen");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			db.shutDown();
			
			
			
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			Font font = new Font(bf, 13, Font.BOLD);
//			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font8bold = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
//			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
//			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font7italic = new Font(bf, 7, Font.ITALIC);
//			Font font9italic = new Font(bf, 9, Font.ITALIC);
			Font font10italic = new Font(bf, 10, Font.ITALIC);
//			Font font12italic = new Font(bf, 12, Font.ITALIC);
//			Font font9underline = new Font(bf, 9, Font.UNDERLINE);
			Font font9bu = new Font(bf, 9, Font.BOLD); font9bu.setStyle("bold,underline");
			Font font10bi = new Font(bf, 10, Font.BOLDITALIC);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
//			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
//			Font font13 = new Font(bf, 13, Font.NORMAL);
//			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_WIDTHS = {18.0f * CONVERT};

			float[] TABLE_HEADER_WIDTHS = new float[] {7.0f * CONVERT, 11.0f * CONVERT};
			float[] TABLE_CONTENT_WIDTHS = new float[] {8.0f * CONVERT, 10.0f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS = new float[] {TABLE_CONTENT_WIDTHS[0]*2/5, TABLE_CONTENT_WIDTHS[0]*3/5};
			float[] TABLE_FOOTER_WIDTHS = new float[] {8.0f * CONVERT, 10.0f * CONVERT};
			
			float[] TABLE_ACB_WIDTHS = new float[] {12.0f * CONVERT, 1.75f * CONVERT,  3.25f * CONVERT,  1.0f * CONVERT};
			
			PdfPCell cell, cell2, cell3, cell4;
			
			if(traNganHangMa.trim().equals("VCB")) 
			{
				//Chữ Form quay góc 90 độ
				PdfContentByte cb = writer.getDirectContent();
				cb.beginText();
		        cb.setFontAndSize(bf, 6);
		        cb.setTextMatrix(0, 1, -1, 0, 0.9f*CONVERT, 4.4f * CONVERT);
		        cb.showText("Form NHBL 04 - 06/99, Liên 1: Lưu, Liên 2: KH");
		        cb.endText();
		        
		        //Logo Vietcombank
		        Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/vietcombank.png");
				hinhanh.scalePercent(75);
				hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				cell = new PdfPCell(new Paragraph("(Hóa đơn được ban hành kèm theo Quyết định số 153 ngày 06/04/2011 cua TGĐ VCB)", font7italic)); //Header
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				table.addCell(cell);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(1);
				
				PdfPTable table2 = new PdfPTable(2);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_HEADER_WIDTHS);
				table2.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell(new Paragraph("CHỨNG TỪ GIAO DỊCH", font9bold));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(2);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("ỦY NHIỆM CHI-PAYMENT ORDER", font12bold));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(2);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(traChiNhanh, font10));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày (Date)   " + getDate(ngayghinhan), font10bi));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Địa chỉ: ", font8));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(" ", font10));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Mã VAT: ", font8));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Số Hóa Đơn-Invoice No: ", font10));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				table2.addCell(cell2);
				
				cell.addElement(table2);
				table.addCell(cell);
				
				//CONTENT
				cell = new PdfPCell(new Paragraph("", font10bold));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(1);
				//cell.setFixedHeight(2 * CONVERT);
				
				table2 = new PdfPTable(2);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_CONTENT_WIDTHS);
				table2.setWidthPercentage(100.0f);
				
				//Left
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				cell2.setPaddingLeft(0.3f*CONVERT);
				cell2.setPaddingRight(0.2f*CONVERT);
				//cell2.setFixedHeight(2 * CONVERT);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {TABLE_CONTENT_WIDTHS[0]});
				table3.setWidthPercentage(100.0f);
				
				//Đề nghị ghi nợ tài khoản
				cell3 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ĐỀ NGHỊ GHI NỢ TÀI KHOẢN", font9bold)); 
				p.add(new Chunk(" (Please Debit account)", font9));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				
				//Số tài khoản
				cell3 = new PdfPCell();
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				
				PdfPTable table4 = new PdfPTable(2);	
				table4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
				table4.setWidthPercentage(100.0f);
				
				cell4 = new PdfPCell(new Paragraph("SỐ TK (A/C No.):", font9));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(0.7f * CONVERT);
				cell4.setBorder(0);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(traStk, font9bold));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				cell4.setFixedHeight(0.7f * CONVERT);
				table4.addCell(cell4);

				cell3.addElement(table4);
				table3.addCell(cell3);
				
				//Tên TK + Địa chỉ
				cell3 = new PdfPCell();
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				
				table4 = new PdfPTable(2);
				table4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
				table4.setWidthPercentage(100.0f);
				
				cell4 = new PdfPCell(new Paragraph("TÊN TK (A/C name.): \n \nĐỊA CHỈ (Address):", font9));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				cell4.setFixedHeight(1.2f * CONVERT);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(traTen, font9bold));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				cell4.setFixedHeight(1.2f * CONVERT);
				table4.addCell(cell4);

				cell3.addElement(table4);
				table3.addCell(cell3);
				
				//Tại NH
				cell3 = new PdfPCell();
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				
				table4 = new PdfPTable(2);	
				table4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
				table4.setWidthPercentage(100.0f);
				
				cell4 = new PdfPCell(new Paragraph("Tại NH (With Bank):", font9));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				cell4.setFixedHeight(0.7f * CONVERT);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(traNganHang, font8bold));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				cell4.setFixedHeight(0.8f * CONVERT);
				table4.addCell(cell4);

				cell3.addElement(table4);
				table3.addCell(cell3);
				
				//Ghi có tài khoản
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("& GHI CÓ TÀI KHOẢN", font9bold)); 
				p.add(new Chunk(" (& Credit account:)", font9));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Số tài khoản
				cell3 = new PdfPCell();
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				
				table4 = new PdfPTable(2);	
				table4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
				table4.setWidthPercentage(100.0f);
				
				cell4 = new PdfPCell(new Paragraph("SỐ TK (A/C No.):", font9));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				cell4.setFixedHeight(0.7f * CONVERT);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(nhanStk, font9bold));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				table4.addCell(cell4);

				cell3.addElement(table4);
				table3.addCell(cell3);
				
				//Tên TK + Địa chỉ
				cell3 = new PdfPCell();
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				
				table4 = new PdfPTable(2);	
				table4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
				table4.setWidthPercentage(100.0f);
				
				cell4 = new PdfPCell(new Paragraph("TÊN TK (A/C name.): \n \nĐỊA CHỈ (Address):", font9));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setFixedHeight(1.2f * CONVERT);
				cell4.setBorder(0);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(nhanTen, font9bold));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				cell4.setFixedHeight(1.2f * CONVERT);
				table4.addCell(cell4);

				cell3.addElement(table4);
				table3.addCell(cell3);
				
				//Tại NH
				cell3 = new PdfPCell();
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				
				table4 = new PdfPTable(2);	
				table4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
				table4.setWidthPercentage(100.0f);
				
				cell4 = new PdfPCell(new Paragraph("Tại NH (With Bank):", font9));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(0.7f * CONVERT);
				cell4.setBorder(0);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(nhanNganHang, font8bold));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setFixedHeight(0.8f * CONVERT);
				cell4.setBorder(0);
				table4.addCell(cell4);

				cell3.addElement(table4);
				table3.addCell(cell3);

				cell2.addElement(table3);
				table2.addCell(cell2);
				
				//Right
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				cell2.setPaddingLeft(0.2f*CONVERT);
				cell2.setPaddingRight(0.4f*CONVERT);
				//cell2.setFixedHeight(2 * CONVERT);
				
				//Row1
				table3 = new PdfPTable(2);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {TABLE_CONTENT_WIDTHS[1]/2, TABLE_CONTENT_WIDTHS[1]/2});
				table3.setWidthPercentage(100.0f);
				
				//Số tiền
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("SỐ TIỀN", font9bold)); 
				p.add(new Chunk(" (With amount)", font9));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				
				//Số tiền
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("PHÍ NH", font9bold)); 
				p.add(new Chunk(" (Bank charges)", font9)); 
				p.setAlignment(Element.ALIGN_RIGHT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				
				cell2.addElement(table3);
				
				//Row 2, 3
				
				table3 = new PdfPTable(5);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {TABLE_CONTENT_WIDTHS[1]*4/10, TABLE_CONTENT_WIDTHS[1]*2/10, TABLE_CONTENT_WIDTHS[1]*1.5f/10, TABLE_CONTENT_WIDTHS[1]*1.5f/10, TABLE_CONTENT_WIDTHS[1]*1/10});
				table3.setWidthPercentage(100.0f);
				
				//Row 2
				//Bằng số
				cell3 = new PdfPCell(new Paragraph("BẰNG SỐ (In figures):", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidthLeft(1);
				cell3.setBorderWidthTop(1);
				cell3.setBorderWidthBottom(1);
				cell3.setBorderWidthRight(0);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//Số tiền bằng số
				cell3 = new PdfPCell(new Paragraph(sotientt, font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthTop(1);
				cell3.setBorderWidthBottom(1);
				cell3.setBorderWidthRight(1);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//VND
				cell3 = new PdfPCell(new Paragraph("VNĐ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthTop(1);
				cell3.setBorderWidthBottom(1);
				cell3.setBorderWidthRight(1);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//Phí trong
				cell3 = new PdfPCell(new Paragraph("Phí trong\nIncluding", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//
				cell3 = new PdfPCell(new Paragraph(" ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				
				//Row 3
				//BẰNG CHỮ (In words): 3cell
				cell3 = new PdfPCell(new Paragraph("BẰNG CHỮ (In words):", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(3);
				cell3.setBorderWidthLeft(1);
				cell3.setBorderWidthTop(0);
				cell3.setBorderWidthBottom(0);
				cell3.setBorderWidthRight(1);
				cell3.setFixedHeight(0.5f * CONVERT);
				table3.addCell(cell3);
				
				// 2cell
				cell3 = new PdfPCell(new Paragraph(" ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(2);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				//Row 4
				//Nội dung tiền bằng chữ
				cell3 = new PdfPCell(new Paragraph(tienbangchu, font9));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(3);
				cell3.setBorderWidthLeft(1);
				cell3.setBorderWidthTop(0);
				cell3.setBorderWidthBottom(1);
				cell3.setBorderWidthRight(1);
				cell3.setRowspan(2);
				cell3.setFixedHeight(1.25f * CONVERT);
				table3.addCell(cell3);
				
				//Phí ngoài
				cell3 = new PdfPCell(new Paragraph("Phí ngoài\nExcluding", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//
				cell3 = new PdfPCell(new Paragraph(" ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(1);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(" ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(2);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				//Row 5
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("NỘI DUNG", font9bold)); 
				p.add(new Chunk(" (Details of Payment):", font9));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidth(0);
				cell3.setColspan(5);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				
				//Row6: Nội dung
				cell3 = new PdfPCell(new Paragraph(noidungtt, font10));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setColspan(5);
				cell3.setBorderWidth(0);
				cell3.setMinimumHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Row 7
				cell3 = new PdfPCell(new Paragraph("KẾ TOÁN TRƯỞNG", font9bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(1);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("CHỦ TÀI KHOẢN KÝ & ĐÓNG DẤU", font9bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(4);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				//Row 8
				cell3 = new PdfPCell(new Paragraph("Chief accountant", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setColspan(1);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Acc. Holder & Stamp", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setColspan(4);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				cell2.addElement(table3);
				
				table2.addCell(cell2);
				
				cell.addElement(table2);
				table.addCell(cell);
				
				//FOOTER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(1);
				
				table2 = new PdfPTable(2);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_FOOTER_WIDTHS);
				table2.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell(new Paragraph("DÀNH CHO NGÂN HÀNG (For Bank's Use only)", font9bu));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setFixedHeight(0.5f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Mã VAT", font9bold));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setFixedHeight(0.5f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(" ", font9));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setFixedHeight(0.5f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				//cell2.setFixedHeight(0.5f * CONVERT);
				
				table3 = new PdfPTable(3);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {TABLE_FOOTER_WIDTHS[1]/3, TABLE_FOOTER_WIDTHS[1]/3, TABLE_FOOTER_WIDTHS[1]/3});
				table3.setWidthPercentage(100.0f);
				
				cell3 = new PdfPCell(new Paragraph("Thanh toán viên", font9bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Kiểm soát", font9bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Giám đốc", font9bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);

				cell2.addElement(table3);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(" ", font9));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setColspan(2);
				cell2.setFixedHeight(1.2f * CONVERT);
				table2.addCell(cell2);

				cell.addElement(table2);
				table.addCell(cell);
				
				document.add(table);
				//END VIETCOMBANK
			} 
			else if(traNganHangMa.equals("ACB")) 
			{
				//Logo ACB
		        Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/acb.png");
				hinhanh.scalePercent(75);
				hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER + CONTENT
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(1);
				
				PdfPTable table2 = new PdfPTable(TABLE_ACB_WIDTHS.length);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_ACB_WIDTHS);
				table2.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI", font14bold)); 
				p.add(new Chunk("/PAYMENT ORDER", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ngày", font12bold)); 
				p.add(new Chunk("/Date", font10italic));
				p.add(new Chunk("  " + ngayghinhan, font10));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ĐƠN VỊ TRẢ TIỀN", font12bold)); 
				p.add(new Chunk("/APPLICANT", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthTop(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tên tài khoản", font12bold)); 
				p.add(new Chunk("/Acct's Name", font10italic));
				p.add(new Chunk("     " + traTen, font12bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản", font12bold)); 
				p.add(new Chunk("/Acct No.", font10italic));
				p.add(new Chunk("  " + traStk, font12bold));
				p.add(new Chunk("       Tại ngân hàng Á Châu _ chi nhánh", font12bold)); 
				p.add(new Chunk("/At ACB _ Branch", font10italic)); 
				//p.add(new Chunk(traChiNhanh, font12bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ĐƠN VỊ THỤ HƯỞNG", font12bold)); 
				p.add(new Chunk("/BENEFICIARY", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthTop(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Đơn vị thụ hưởng", font12bold)); 
				p.add(new Chunk("/Beneficiary", font10italic));
				p.add(new Chunk("     " + nhanTen, font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản", font12bold)); 
				p.add(new Chunk("/Acct No.", font10italic));
				p.add(new Chunk("     " + nhanStk, font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				cell2.setPaddingLeft(0.5f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("CMND/Hộ chiếu", font12bold)); 
				p.add(new Chunk("/ID CARD/PP No.", font10italic));
				p.add(new Chunk("            ", font10bold));
				p.add(new Chunk("Ngày cấp", font10bold));
				p.add(new Chunk("/Date", font10italic));
				p.add(new Chunk("            ", font10bold));
				p.add(new Chunk("Nơi cấp", font10bold));
				p.add(new Chunk("/Place", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				cell2.setPaddingLeft(0.5f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tại Ngân hàng", font12bold)); 
				p.add(new Chunk("/Beneficiary's Bank ", font10italic));
				p.add(new Chunk(nhanNganHang, font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tỉnh, TP", font12bold)); 
				p.add(new Chunk("/Province, City ", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthBottom(1);
				cell2.setColspan(TABLE_ACB_WIDTHS.length-1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tiền bằng chữ", font12bold)); 
				p.add(new Chunk("/Amount in words", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Bằng số:", font12bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(sotientt, font12bold));
				p.setAlignment(Element.ALIGN_RIGHT);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setPaddingRight(0.2f * CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);
				cell2.setRowspan(2);
				cell2.setBackgroundColor(new BaseColor(219, 229, 241));
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("VNĐ", font9bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorder(0);
				cell2.addElement(p);
				cell2.setRowspan(2);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(tienbangchu, font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("In figures", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorder(0);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Nội dung", font12bold)); 
				p.add(new Chunk("/Details     ", font10italic));
				p.add(new Chunk(noidungtt, font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell.addElement(table2);
				table.addCell(cell);
				
				//FOOTER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(1);
				
				table2 = new PdfPTable(3);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				float columnSize = 18.0f / 3 * CONVERT;
				table2.setWidths(new float[] {columnSize, columnSize, columnSize});
				table2.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ĐƠN VỊ TRẢ TIỀN", font12bold)); 
				p.add(new Chunk("/Application", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthRight(1);
				cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("NGÂN HÀNG Á CHÂU", font12bold)); 
				p.add(new Chunk("/ACB", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthRight(1);
				cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("NGÂN HÀNG B", font12bold)); 
				p.add(new Chunk("/B Bank", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				//Column1 
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthRight(1);
				
				PdfPTable table3 = new PdfPTable(2);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {columnSize/2, columnSize/2});
				table3.setWidthPercentage(100.0f);
				
				cell3 = new PdfPCell(new Paragraph("Kế toán trưởng", font12bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Chủ tài khoản", font12bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Chief Accountant", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Account Holder", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell2.addElement(table3);
				table2.addCell(cell2);
				
				//Column2
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthRight(1);
				
				table3 = new PdfPTable(2);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {columnSize/2, columnSize/2});
				table3.setWidthPercentage(100.0f);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ghi sổ ngày", font12bold)); 
				p.add(new Chunk("/Post date.............", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setColspan(2);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Giao dịch viên", font12bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Kiểm soát viên", font12bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Teller", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Suppervisor", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell2.addElement(table3);
				table2.addCell(cell2);
				

				//Column3
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorder(0);
				
				table3 = new PdfPTable(2);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {columnSize/2, columnSize/2});
				table3.setWidthPercentage(100.0f);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ghi sổ ngày", font12bold)); 
				p.add(new Chunk("/Post date.............", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setColspan(2);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Giao dịch viên", font12bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Trưởng đơn vị", font12bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Teller", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Manager", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setFixedHeight(2.0f * CONVERT);
				table3.addCell(cell3);
				
				cell2.addElement(table3);
				table2.addCell(cell2);
				
				cell.addElement(table2);
				
				
				table.addCell(cell);
				
				document.add(table);
				
			}
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private boolean CreateUyNhiemChi(OutputStream out, IErpThanhtoanhoadon obj)  throws Exception
	{
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
//			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
//			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "";

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100005), '') as congty, " +
							"	isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, " +
							" 	isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotknganhang as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, " +
							" 	isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, " +
							" 	tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, " +
							" 	tthd.NGAYGHINHAN as NGAYGHINHAN " +
							" from ERP_THANHTOANHOADON tthd " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  " +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  " +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq " +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  " +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  " +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  " +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  " +
							" where tthd.pk_seq = '"+obj.getId()+"' ";
			System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ngayghinhan = rs.getString("NGAYGHINHAN");
						loaitt = rs.getInt("LOAITHANHTOAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						//traDiaChi = rs.getString("traDiaChi");
						
						if(loaitt == 1) {
							nhanTen = rs.getString("nvTen");
//							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
						} else {
							nhanTen = rs.getString("nccTen");
//							nhanDiaChi = rs.getString("nccTen");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			db.shutDown();
			
			FileInputStream fstream;

			
			Cell cell = null;
			
			if(traNganHangMa.trim().equals("VIETCOMBANK")) 
			{
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\UNC_VCB.xlsm");
				Workbook workbook = new Workbook();
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				workbook.open(fstream);
				
				Worksheets worksheets = workbook.getWorksheets();
			    Worksheet worksheet = worksheets.getSheet(0);
			    Cells cells = worksheet.getCells();
				
				//Cột bên trái
			    cell = cells.getCell("C5"); cell.setValue(traChiNhanh); //Tên chi nhánh ngân hàng bên trả
				cell = cells.getCell("F11"); cell.setValue(traStk); //Số tài khoản bên trả
				cell = cells.getCell("G13"); cell.setValue(traTen); //Tên tài khoản trả
				cell = cells.getCell("G17"); cell.setValue(traNganHang); //Tên ngân hàng trả
				
				cell = cells.getCell("G22"); cell.setValue(nhanStk); //Số tài khoản nhận
				cell = cells.getCell("G24"); cell.setValue(nhanTen); //Tên tài khoản nhận
				cell = cells.getCell("G28"); cell.setValue(nhanNganHang); //Ngân hàng tài khoản nhận
				
				//Cột bên phải
				cell = cells.getCell("O5"); cell.setValue(getDate(ngayghinhan));
				cell = cells.getCell("S11"); cell.setValue(sotientt);
				cell = cells.getCell("N14"); cell.setValue(tienbangchu);
				cell = cells.getCell("N20"); cell.setValue(noidungtt);
				
				workbook.save(out);
				fstream.close();
			}
			else if(traNganHangMa.trim().equals("ACB")) 
			{
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\UNC_ACB.xlsm");
				Workbook workbook = new Workbook();
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				workbook.open(fstream);
				
				Worksheets worksheets = workbook.getWorksheets();
			    Worksheet worksheet = worksheets.getSheet(0);
			    Cells cells = worksheet.getCells();
				
				//Cột bên trái
				cell = cells.getCell("F7"); cell.setValue(traStk);
				cell = cells.getCell("H6"); cell.setValue(traTen);
				cell = cells.getCell("K7"); cell.setValue(traNganHang + " - " + traChiNhanh);
				cell = cells.getCell("G12"); cell.setValue(nhanStk);
				cell = cells.getCell("I10"); cell.setValue(nhanTen);
				cell = cells.getCell("I16"); cell.setValue(nhanNganHang);
				
				//Cột bên phải
				cell = cells.getCell("L3"); cell.setValue(getDate(ngayghinhan));
				cell = cells.getCell("W18"); cell.setValue(sotientt);
				cell = cells.getCell("A19"); cell.setValue(tienbangchu);
				cell = cells.getCell("E21"); cell.setValue(noidungtt);
				
				workbook.save(out);
				fstream.close();
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void CreatePhieuChi(Document document, ServletOutputStream outstream, IErpThanhtoanhoadon tthdBean) 
	{
		try
		{			

			NumberFormat formatter = new DecimalFormat("#,###,###"); 
//			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
//			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			IErpCauHinhInHoaDon config = new ErpCauHinhInHoaDon();
			config.initWithName("PHIEUTHU");
			
			IErpCauHinhInHoaDon configp = new ErpCauHinhInHoaDon();
			configp.initWithName("PHIEUTHUP");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			Font font = new Font(bf, 13, Font.BOLD);
//			Font font2 = new Font(bf, 8, Font.BOLD);
//			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
//			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
//			Font font13bold = new Font(bf, 13, Font.BOLD);
//			Font font14bold = new Font(bf, 14, Font.BOLD);
//			Font font9 = new Font(bf, 9, Font.NORMAL);
//			Font font10 = new Font(bf, 10, Font.NORMAL);
//			Font font11 = new Font(bf, 11, Font.NORMAL);
//			Font font12 = new Font(bf, 12, Font.NORMAL);
//			Font font13 = new Font(bf, 13, Font.NORMAL);
//			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_WIDTHS = {18.0f * CONVERT};

			float PADDING_LEFT_3 = configp.getMarginLeft() * CONVERT;
			float PADDING_LEFT_0 = configp.getMarginRight() * CONVERT;
			float PADDING_LEFT_1 = configp.getMarginTop() * CONVERT;
			float PADDING_LEFT_2 = configp.getMarginBottom() * CONVERT;
			//float PADDING_LEFT_4 = configp.getPaddingLeft() * CONVERT;
			
			/*float PADDING_LEFT_0 = 10.0f * CONVERT;
			float PADDING_LEFT_1 = 7.0f * CONVERT;
			float PADDING_LEFT_2 = 1.0f * CONVERT;
			float PADDING_LEFT_3 = 13.2f * CONVERT;*/
			
			//Độ rộng mỗi dòng
			float row1w = config.getPaddingLeft() * CONVERT; //0.5f*CONVERT pk_seq
			float row2w = config.getPaddingRight() * CONVERT; //0.75f*CONVERT rỗng
			float row3w = config.getPaddingTop() * CONVERT; //0.75f*CONVERT ngày ghi sổ
			float row4w = config.getPaddingBottom() * CONVERT; //1.2f*CONVERT người nộp tiền
			float row5w = config.getNoColumn() * CONVERT; //0.9f*CONVERT địa chỉ
			float row6w = config.getUnitColumn() * CONVERT; //0.9f*CONVERT lý do thu
			float row7w = config.getQuantityColumn() * CONVERT; //0.9f*CONVERT tiền
			float row8w = config.getUniPriceColumn() * CONVERT; //0.9f*CONVERT viết bằng chữ
			//float row9w = 0.9f * CONVERT; //0.9f*CONVERT ghi chú
			
			int BORDER_WIDTH = 0;
	        	        
	        PdfPTable table = new PdfPTable(1);	
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_WIDTHS);
			table.setWidthPercentage(100.0f);
			
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(tthdBean.getId(), font10bold)); //PK_SEQ
			cell.setPaddingLeft(PADDING_LEFT_3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row1w);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(" ", font10bold)); //Rỗng
			cell.setPaddingLeft(PADDING_LEFT_3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row2w);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(tthdBean.getNgayghinhan(), font12bold)); //Ngày ghi sổ
			cell.setPaddingLeft(PADDING_LEFT_0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row3w);
			table.addCell(cell);
			
			System.out.println("[ErpTTHoadonPdfSvl.CreatePhieuChi] nccId = " + tthdBean.getNccId());
			cell = new PdfPCell(new Paragraph(tthdBean.getNccId(), font12bold)); //Người nhận
			cell.setPaddingLeft(PADDING_LEFT_1);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row4w);
			table.addCell(cell);

			System.out.println("[ErpTTHoadonPdfSvl.CreatePhieuChi] diachi = " + tthdBean.getDiachi());
			cell = new PdfPCell(new Paragraph(tthdBean.getDiachi(), font12bold)); //Địa chỉ
			cell.setPaddingLeft(PADDING_LEFT_1);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row5w);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(tthdBean.getNoidungtt(), font12bold)); //Nội dung thanh toán
			cell.setPaddingLeft(PADDING_LEFT_1);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row6w);
			table.addCell(cell);
			
			//Số tiền thanh toán
			float fsotien = 0.0f;
			try {
				fsotien = Float.parseFloat(tthdBean.getSotientt());
				cell = new PdfPCell(new Paragraph(formatter.format(fsotien) + "đ", font12bold)); 
			} catch (Exception e) {
				System.out.println("[ErpTTHoaDonPdfSvl.CreatePhieuChi] so tien thanh toan exception message= " + e.getMessage());
				cell = new PdfPCell(new Paragraph(tthdBean.getSotientt() + "đ", font12bold));
			}
			cell.setPaddingLeft(PADDING_LEFT_1);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row7w);
			table.addCell(cell);
			
			//Viết bằng chữ
			try {
				NumberFormat _format = new DecimalFormat("############");
				String sotienfm = _format.format(fsotien);
				long lsotien = Long.parseLong(sotienfm);
				cell = new PdfPCell(new Paragraph(DocTien.docTien(lsotien), font12bold)); 
			}
			catch (Exception e) {
				e.printStackTrace();
				cell = new PdfPCell(new Paragraph(" ", font12bold));
			}
			cell.setPaddingLeft(PADDING_LEFT_2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(row8w);
			table.addCell(cell);
			
			document.add(table);
			
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return ngay + "/" + thang + "/" + nam;
	}
}