package geso.traphaco.erp.servlets.uynhiemchi;

import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchi;
import geso.traphaco.erp.beans.thutien.IErpThutien;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;

public class ErpUynhiemchiPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpUynhiemchiPdfSvl() {
        super();
    }

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
		String inchitiet = request.getParameter("inchitiet");
		String ctyId = (String)session.getAttribute("congtyId");
		 
		System.out.println("inchitiet:"+inchitiet);
		if(httt == null)
			httt = "";
			
		IErpUynhiemchi tthdBean = new ErpUynhiemchi(id);
		tthdBean.setHtttId(httt);
	    tthdBean.setUserId(userId);
	    tthdBean.setCtyId(ctyId);

		OutputStream out = response.getOutputStream();
	    
	    if(tthdBean.getHtttId().equals("100001"))
	    {
	    	tthdBean.DBclose();
	    	
	    	if(inchitiet.equals("0")){
		    	//KIỂM TRA NẾU MÃ NGÂN HÀNG TRẢ LÀ IVB THÌ CANH CHIỀU DỌC
		    	String query = 
					"	SELECT 	tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa \n"+ 
					"	FROM  	ERP_THANHTOANHOADON tthd  left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq \n"+   
					"   WHERE   tthd.pk_seq = '"+tthdBean.getId()+"' ";
					
					System.out.println("MA NGAN HANG__:"+query);
					dbutils db = new dbutils();
					ResultSet rs = db.get(query);
					
					String manganhangtra = "";
					try{
						if(rs!=null){
							while(rs.next()){
								manganhangtra = rs.getString("traNganHangMa");
							}
						}
					}
					catch (Exception e){
						e.printStackTrace();
					}
		    	
		    	response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=UyNhiemChi"+tthdBean.getId()+".pdf");
				
				
				ServletOutputStream outstream = response.getOutputStream();
				
				/*if(manganhangtra.equals("IVB")){
					Document document = new Document();
					//document.setPageSize(PageSize.A4.rotate()); //CANH HÓA ĐƠN THEO CHIỀU DỌC
					document.setMargins(2.0f*CONVERT, 2.0f*CONVERT, 0f*CONVERT, 0f*CONVERT); // L,R, T, B
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
					
				} else if (manganhangtra.equals("ACB"))
				{
					float PAGE_LEFT = 1*CONVERT, PAGE_RIGHT = 1*CONVERT, PAGE_TOP = 0.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
					Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);//CANH THEO CHIỀU NGANG
					Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
					
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
				}
				else if (manganhangtra.equals("SACOMBANK")) 
				{
					float PAGE_LEFT = 0.2f*CONVERT, PAGE_RIGHT = 0.2f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.5f*CONVERT; //cm
					Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);//CANH THEO CHIỀU NGANG
					Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
					
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
				}
				else if (manganhangtra.equals("VIETCOMBANK")) 
				{
					float PAGE_LEFT = 0.2f*CONVERT, PAGE_RIGHT = 0.2f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.5f*CONVERT; //cm
					Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);//CANH THEO CHIỀU NGANG
					Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
					
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
				}
				else if (manganhangtra.equals("ANBINH")) 
				{
					float PAGE_LEFT = 0.2f*CONVERT, PAGE_RIGHT = 0.2f*CONVERT, PAGE_TOP = 0.0f*CONVERT, PAGE_BOTTOM = 0.2f*CONVERT; //cm
					Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);//CANH THEO CHIỀU NGANG
					Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
					
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
				}
				else if (manganhangtra.equals("VIETINBANK")) 
				{
					float PAGE_LEFT = 0.2f*CONVERT, PAGE_RIGHT = 0.2f*CONVERT, PAGE_TOP = 0.2f*CONVERT, PAGE_BOTTOM = 0.2f*CONVERT; //cm
					Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);//CANH THEO CHIỀU NGANG
					Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
					
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
				}
				else{
					float PAGE_LEFT = 1*CONVERT, PAGE_RIGHT = 1*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.5f*CONVERT; //cm
					Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);//CANH THEO CHIỀU NGANG
					Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
					
					this.CreateUyNhiemChiPdf(document, outstream, tthdBean);
					
					document.close();
				}*/
			
				System.out.println("manganhangtra "+manganhangtra);
				if (manganhangtra.equals("VIETIN")) 
				{
					CREATE_UNC_VIETTINBANK_JASPER( outstream,  request,  response, tthdBean);
				}
				else
				{
					// aler error
					CREATE_UNC_ERROR( outstream,  request,  response, tthdBean);
				}
				
	    	}
	    	else if(inchitiet.equals("1")){
	    		response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=UyNhiemChiChiTiet"+tthdBean.getId()+".pdf");
				
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();	
				try {
				PdfWriter writer =	PdfWriter.getInstance(document, outstream);
				
				writer.setPageEvent(new HeaderAndFooter(tthdBean.getId()));
				 
				this.CreateUyNhiemChiBPPdf(document, outstream,tthdBean, id);// SỬ DỤNG CHO IN BỘ PHẬN CHI TIẾT
				} catch (DocumentException e) {
					
					e.printStackTrace();
				}
				document.close();
				
				
	    	} else{
	    		// Điều chuyển tiền
	    		response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=DieuChuyenTien"+tthdBean.getId()+".pdf");
				
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();	
				try {
				PdfWriter writer =	PdfWriter.getInstance(document, outstream);
				
				/*writer.setPageEvent(new HeaderAndFooter(tthdBean.getId()));*/
				 
				this.CreateDieuChuyenTien(document, outstream,tthdBean, id);// SỬ DỤNG CHO IN BỘ PHẬN CHI TIẾT
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				document.close();
	    	}
	    }
	    else
	    {
	    	
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Thanhtoan"+tthdBean.getSohoadon()+".xlsm");
	        try {
	        	OutputStream outstream = response.getOutputStream();
				this.CreatePhieuChiExcel(outstream, tthdBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
	}
	
	private void CreatePhieuChiExcel(OutputStream outstream,
			IErpUynhiemchi tthdBean) {
		// TODO Auto-generated method stub
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk, donvithuchien = "" , pk_seq ="";

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100005), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai \n"+  
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
			
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
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						pk_seq = rs.getString("pk_seq");
						 
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccTen");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
					
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			db.shutDown();
			
			
			FileInputStream fstream;
			Cell cell = null;
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ThanhToanTienMat.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet("inchi");
		    Cells cells1 = worksheet1.getCells();
	
		    cell = cells1.getCell("G1"); cell.setValue(pk_seq); 
		    cell = cells1.getCell("E4"); cell.setValue(getDate(ngayghinhan)); 
		    cell = cells1.getCell("E6"); cell.setValue(nhanTen); 
		    cell = cells1.getCell("E7"); cell.setValue(donvithuchien); 
		    cell = cells1.getCell("D8"); cell.setValue(noidungtt); 
		    cell = cells1.getCell("E9"); cell.setValue(sotien); 
		    cell = cells1.getCell("C10"); cell.setValue(tienbangchu + "./."); 
		    
			workbook.save(outstream);
			fstream.close();
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	
	
	
	private void CreateUyNhiemChiPdf(Document document, ServletOutputStream outstream, IErpUynhiemchi tthdBean) 
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "", traDienthoai = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk ="";

			dbutils db = new dbutils();
			String query =  " SELECT tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = "+tthdBean.getCtyId()+"), '') as congty, \n" +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, isnull(ncc.sotaikhoan,'') as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n" +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, isnull(nv.sotaikhoan,'') as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n" +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n" +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk, isnull((select DIENTHOAI from erp_congty where pk_seq = "+tthdBean.getCtyId()+"), '') as tradienthoai   \n " +
							" FROM 	 ERP_THANHTOANHOADON tthd \n" +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq \n"+  
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq \n"+  
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n"+ 
							" left join ERP_NGANHANG nccnh on nccnh.PK_SEQ = ncc.NGANHANG_FK \n"+
							"  left join ERP_CHINHANH ncccn on ncccn.PK_SEQ = ncc.CHINHANH_FK \n"+  
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n"+ 
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq \n"+  
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq \n"+  
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
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
						traDienthoai = rs.getString("traDienthoai");
						//traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
							nhanChiNhanh = rs.getString("nvChiNhanh");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccTen");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
							nhanChiNhanh = rs.getString("nccChiNhanh");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
					
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			db.shutDown();
			
			
			
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font7bold = new Font(bf, Font.BOLD);
			Font font8bold = new Font(bf, 8, Font.BOLD);
			Font font8italic = new Font(bf, 8, Font.ITALIC);
			Font font8bi = new Font(bf, 8, Font.BOLDITALIC);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font11bi = new Font(bf, 11, Font.BOLDITALIC);
			Font font11italic = new Font(bf, 11, Font.ITALIC);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font7italic = new Font(bf, 7, Font.ITALIC);
			Font font9italic = new Font(bf, 9, Font.ITALIC);
			Font font10italic = new Font(bf, 10, Font.ITALIC);
			Font font12italic = new Font(bf, 12, Font.ITALIC);
			Font font9underline = new Font(bf, 9, Font.UNDERLINE);
			Font font9bu = new Font(bf, 9, Font.BOLD); font9bu.setStyle("bold,underline");
			Font font10bi = new Font(bf, 10, Font.BOLDITALIC);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font6italic = new Font(bf, 6, Font.ITALIC);
			Font font7 = new Font(bf, 7, Font.NORMAL);
			
			Font font9bi = new Font(bf, 9, Font.BOLD);
			font9bi.setStyle("bold,italic");
			
			Font font9bui = new Font(bf, 9, Font.BOLD); font9bui.setStyle("bold,underline,italic");
			
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_WIDTHS = {18.0f * CONVERT};

			float[] TABLE_HEADER_WIDTHS = new float[] {7.0f * CONVERT, 11.0f * CONVERT};
			float[] TABLE_HEADER_WIDTHS_VIB = new float[] {11.0f * CONVERT, 14.0f * CONVERT, 2.0f*CONVERT};
			float[] TABLE_CONTENT_WIDTHS = new float[] {8.0f * CONVERT, 10.0f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS = new float[] {TABLE_CONTENT_WIDTHS[0]*2/5, TABLE_CONTENT_WIDTHS[0]*3/5};
			float[] TABLE_FOOTER_WIDTHS = new float[] {8.0f * CONVERT, 10.0f * CONVERT};
			
			float[] TABLE_ACB_WIDTHS = new float[] {12.0f * CONVERT, 1.75f * CONVERT,  3.25f * CONVERT,  1.0f * CONVERT};
			
			float[] TABLE_EIB_WIDTHS = new float[] {15.0f * CONVERT,0.5f * CONVERT,6.0f * CONVERT};
			
			float[] TABLE_SCBANK_WIDTHS = new float[] {15.0f * CONVERT,0.5f * CONVERT,6.0f * CONVERT};
			
			float[] TABLE_ABBBANK_WIDTHS = new float[] {15.0f * CONVERT,0.2f * CONVERT,7.0f * CONVERT};
			
			float[] TABLE_DABANK_WIDTHS = new float[] {15.0f * CONVERT,0.5f * CONVERT,6.0f * CONVERT};
			
			float[] TABLE_VIETINKBANK_WIDTHS = new float[] {15.0f * CONVERT,0.5f * CONVERT,6.0f * CONVERT};
			
			float[] TABLE_AGB_WIDTHS = new float[] {5.0f * CONVERT, 8.0f * CONVERT,  4.0f * CONVERT,  4.0f * CONVERT};
			
			PdfPCell cell,cell1 ,cell2, cell3, cell4, cell5;
			
			
			if(traNganHangMa.trim().equals("VIETCOMBANK")) 
			{
				
				float[] TABLE_HEADER_WIDTHS_VIETCOMBANK = new float[] {3.0f * CONVERT, 3.0f * CONVERT, 3.0f * CONVERT, 0.3f * CONVERT, 2.9f * CONVERT, 2.9f * CONVERT,2.9f * CONVERT};
				float[] TABLE_CONTENT_WIDTHS_VIETCOMBANK = new float[] {9.0f * CONVERT, 10.5f * CONVERT};
				float[] TABLE_CONTENT_FIELDS_WIDTHS_VIETCOMBANK = new float[] {TABLE_CONTENT_WIDTHS_VIETCOMBANK[0]*2/5, TABLE_CONTENT_WIDTHS_VIETCOMBANK[0]*3/5};
			/*	float[] TABLE_FOOTER_WIDTHS = new float[] {8.0f * CONVERT, 10.0f * CONVERT};*/
				
				/*float[] TABLE_ACB_WIDTHS = new float[] {12.0f * CONVERT, 1.75f * CONVERT,  3.25f * CONVERT,  1.0f * CONVERT};
				
				PdfPCell cell, cell2, cell3;*/
				
				//Chữ Form quay góc 90 độ
				PdfContentByte cb = writer.getDirectContent();
				cb.beginText();
		        cb.setFontAndSize(bf, 8);
		        cb.setTextMatrix(0, 1, -1, 0, 0.1f*CONVERT, 1.0f * CONVERT);
		        cb.showText("               Form NHBL 04 - 06/99, Liên 1: Lưu, Liên 2: KH" + 
		        		"                                                                                                               " + "" +
        				"Form NHBL 04 - 06/99, Liên 1: Lưu, Liên 2: KH");
		        cb.endText();
		        
		        //Logo Vietcombank
		        Image hinhanh= null;
		        try{
	        		hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/vietcombanknew.png");
	        		hinhanh.scalePercent(66);
					hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
		        }catch (Exception e)
		        {
		        	e.printStackTrace();
		        }
		        
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(99.0f);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				PdfPTable table2 = new PdfPTable(7);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setHorizontalAlignment(Element.ALIGN_TOP);
				table2.setWidths(TABLE_HEADER_WIDTHS_VIETCOMBANK);
				table2.setWidthPercentage(100.0f);
				
				cell2 = hinhanh != null ? new PdfPCell(hinhanh) : new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_TOP);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setPaddingTop(0);
				cell2.setBorder(0);
				cell2.setFixedHeight(1.2f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngân hàng TMCP Ngoại Thương Việt Nam \nChi nhánh Thủ Đức \nKCX Linh Trung I, P. Linh Trung, Q. Thủ Đức, TPHCM \nMST: 0100112437-062", font8));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setPaddingTop(0);
				cell2.setBorder(0);
				cell2.setColspan(2);
				cell2.setFixedHeight(1.2f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("CHỨNG TỪ GIAO DỊCH \nỦY NHIỆM CHI -  PAYMENT ODER", font10bold));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setPaddingTop(0);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.setFixedHeight(1.2f * CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(""));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(1);
				table2.addCell(cell2);

				cell2.setColspan(3);
				table2.addCell(cell2);

				cell2 = new PdfPCell(new Paragraph("Ngày (Date):", font9italic));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(2);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(getDate(ngayghinhan), font9italic));
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
				/*cell.setBorderWidth(1);*/
				
				//Left Content table 
				table2 = new PdfPTable(2);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_CONTENT_WIDTHS_VIETCOMBANK);
				table2.setWidthPercentage(100.0f);
				
				//Left
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				cell2.setPaddingLeft(0.0f*CONVERT);
				cell2.setPaddingRight(0.2f*CONVERT);
				
				PdfPTable table3 = new PdfPTable(2);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
//				table3.setWidths(new float[] {TABLE_CONTENT_WIDTHS[0]});
				table3.setWidths(TABLE_CONTENT_FIELDS_WIDTHS_VIETCOMBANK);
				table3.setWidthPercentage(100.0f);
				
				//Đề nghị ghi nợ tài khoản
				cell3 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ĐỀ NGHỊ GHI NỢ TÀI KHOẢN", font9bold)); 
				p.add(new Chunk(" (Please Debit account):", font9italic));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidth(0);
				cell3.setColspan(2);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				
				p = new Paragraph();
				p.add(new Chunk("SỐ TK", font9bold));
				p.add(new Chunk("(A/C No.):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setFixedHeight(0.7f * CONVERT);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(traStk, font10bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthBottom(0);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//Tên TK + Địa chỉ
				p = new Paragraph();
				p.add(new Chunk("TÊN TK ", font9bold));
				p.add(new Chunk("(A/C name):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(traTen, font9bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthBottom(0);
				cell3.setBorderWidthLeft(0);
				table3.addCell(cell3);

				//Địa chỉ
				p = new Paragraph();
				p.add(new Chunk("ĐỊA CHỈ ", font9bold));
				p.add(new Chunk("(Address):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(""));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthBottom(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);

				//Tại NH
				p = new Paragraph();
				p.add(new Chunk("TẠI NH ", font9bold));
				p.add(new Chunk("(with bank):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidthRight(0);

				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(traNganHang, font8bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				table3.addCell(cell3);

				//Ghi có tài khoản
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("& GHI CÓ TÀI KHOẢN", font9bold)); 
				p.add(new Chunk(" (& Credit account:)", font9italic));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				cell3.setColspan(2);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Số tài khoản
				p = new Paragraph();
				p.add(new Chunk("SỐ TK ", font9bold)); 
				p.add(new Chunk("(A/C No.):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(nhanStk, font10bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthBottom(0);
				table3.addCell(cell3);

				//Tên TK
				p = new Paragraph();
				p.add(new Chunk("TÊN TK", font9bold)); 
				p.add(new Chunk("(A/C Name):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setFixedHeight(1.0f * CONVERT);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				table3.addCell(cell3);
				
				if (nhanTen.length() > 70)
					cell3 = new PdfPCell(new Paragraph(nhanTen, font7bold));
				else if (nhanTen.length() > 40)
					cell3 = new PdfPCell(new Paragraph(nhanTen, font8bold));
				else
					cell3 = new PdfPCell(new Paragraph(nhanTen, font9bold));
				
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthBottom(0);

				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Dia chi
				p = new Paragraph();
				p.add(new Chunk("ĐỊA CHỈ ", font9bold)); 
				p.add(new Chunk("(Address):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setFixedHeight(1.0f * CONVERT);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(""));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthBottom(0);

				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Tại NH
				p = new Paragraph();
				p.add(new Chunk("Tại NH ", font9bold)); 
				p.add(new Chunk("(with bank):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setFixedHeight(0.7f * CONVERT);
				cell3.setBorderWidthRight(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph(nhanNganHang + " - " + nhanChiNhanh, font7bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setFixedHeight(0.8f * CONVERT);
				cell3.setBorderWidthLeft(0);
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
				
				//Row1
				table3 = new PdfPTable(2);	
				table3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.setWidths(new float[] {TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]/2, TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]/2});
				table3.setWidthPercentage(100.0f);
				
				//Số tiền
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("SỐ TIỀN", font9bold)); 
				p.add(new Chunk(" (With amount)", font9italic));
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
				p.add(new Chunk(" (Bank charges)", font9bold)); 
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
				table3.setWidths(new float[] {TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]*4.0f/10, TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]*3.5f/10, TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]*1.0f/10, TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]*2.0f/10, TABLE_CONTENT_WIDTHS_VIETCOMBANK[1]*1/10});
				table3.setWidthPercentage(100.0f);
				
				//Row 2
				//Bằng số
				p = new Paragraph();
				p.add(new Chunk("BẰNG SỐ ", font9bold)); 
				p.add(new Chunk("(In figures):", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidthBottom(0);
				cell3.setBorderWidthRight(0);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//Số tiền bằng số
				cell3 = new PdfPCell(new Paragraph(sotientt, font10bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthBottom(0);
				cell3.setBorderWidthRight(0);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//VND
				cell3 = new PdfPCell(new Paragraph("VND", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidthBottom(0);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				//Phí trong
				p = new Paragraph();
				p.add(new Chunk("Phí trong\n", font9bold)); 
				p.add(new Chunk("Including", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//
				cell3 = new PdfPCell(new Paragraph(" ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				PdfPTable tb = new PdfPTable(1);	
				tb.setHorizontalAlignment(Element.ALIGN_CENTER);
				tb.setWidthPercentage(60.0f);
				PdfPCell cellt = new PdfPCell(new Paragraph(" ", font9));
				tb.addCell(cellt);
				cell3.addElement(tb);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				
				//Row 3
				//BẰNG CHỮ (In words): 3cell
				cell3 = new PdfPCell(new Paragraph("Số tiền bằng chữ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(3);
				cell3.setBorderWidthBottom(0);
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
				cell3.setBorderWidthTop(0);
				cell3.setBorderWidthBottom(0);
				cell3.setRowspan(2);
				cell3.setFixedHeight(1.2f * CONVERT);
				table3.addCell(cell3);
				
				//Phí ngoài
				p = new Paragraph();
				p.add(new Chunk("Phí ngoài\n", font9bold)); 
				p.add(new Chunk("Excluding", font9italic));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Cell check
				cell3 = new PdfPCell(new Paragraph(" ", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorderWidth(0);
				cell3.addElement(tb);
				cell3.setFixedHeight(0.7f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(2);
				cell3.setBorderWidth(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(3);
				cell3.setBorderWidthTop(0);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(2);
				cell3.setBorderWidth(0);
				cell3.setFixedHeight(0.8f * CONVERT);
				table3.addCell(cell3);
				//
				//Row 5
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Nội dung ", font9bu)); 
				p.add(new Chunk("(details of payment):", font9bui));
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorderWidth(0);
				cell3.setColspan(5);
				cell3.setFixedHeight(1.0f * CONVERT);
				table3.addCell(cell3);
				
				//Row6: Nội dung
				cell3 = new PdfPCell(new Paragraph(noidungtt, font10));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setColspan(5);
				cell3.setBorderWidthBottom(0);
				cell3.setMinimumHeight(1.5f * CONVERT);
				table3.addCell(cell3);
				
				//Row 7
				p = new Paragraph();
				p.add(new Chunk("KẾ TOÁN TRƯỞNG\n", font9bold)); 
//				p.add(new Chunk("Acc. holder & stamp", font9bi));
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(1);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthBottom(0);
				table3.addCell(cell3);
				
				p = new Paragraph();
				p.add(new Chunk("CHỦ TÀI KHOẢN KÝ & ĐÓNG DẤU\n", font9bold)); 
				cell3 = new PdfPCell(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setColspan(4);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthBottom(0);
				table3.addCell(cell3);
				
				//Row 8
				cell3 = new PdfPCell(new Paragraph("Chief accountant", font9bi));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setColspan(1);
				cell3.setBorderWidthRight(0);
				cell3.setBorderWidthTop(0);
				cell3.setFixedHeight(2.0f * CONVERT);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Acc. Holder & Stamp", font9bi));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setColspan(4);
				cell3.setBorderWidthLeft(0);
				cell3.setBorderWidthTop(0);
				cell3.setFixedHeight(2.0f * CONVERT);
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
				
				p = new Paragraph();
				p.add(new Chunk("DÀNH CHO NGÂN HÀNG ", font9bu)); 
				p.add(new Chunk("(For Bank's Use only)", font9bui));
				cell2 = new PdfPCell(p);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				cell2.setFixedHeight(0.5f * CONVERT);
				table2.addCell(cell2);
				cell2 = new PdfPCell(new Paragraph("", font9bold));
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
				
				table.setSpacingAfter(CONVERT * 1.0f); 
				document.add(table);
				document.add(table);
				//END VIETCOMBANK
			} else	if(traNganHangMa.trim().equals("VCB")) // 
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
				hinhanh.scalePercent(70);
				hinhanh.setAbsolutePosition(1.3f * CONVERT, document.getPageSize().getHeight() - 2.5f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				cell = new PdfPCell(new Paragraph("(Chứng từ được ban hành theo công văn số 6097/CT-QTTVAC ngày 16/04/2009 của Cục thuế Hà Nội)", font7italic)); //Header
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				table.addCell(cell);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(1);
				
				PdfPTable table2 = new PdfPTable(3);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_HEADER_WIDTHS_VIB);
				table2.setWidthPercentage(100.0f);			
				
				cell2 = new PdfPCell(new Paragraph("ỦY NHIỆM CHI-PAYMENT ORDER", font12bold));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(3);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("", font10));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setFixedHeight(0.5f*CONVERT);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày (Date)   " + getDate(ngayghinhan), font10bi));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setPaddingLeft(-0.2f*CONVERT);
				cell2.setFixedHeight(1.0f*CONVERT);
				cell2.setColspan(2);
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
				p.add(new Chunk(" (Please Debit account)", font9italic));
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
				
				cell4 = new PdfPCell(new Paragraph("SỐ TK (A/C No.): ", font9));
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
				
				cell4 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("TÊN TK ", font9)); 
				p.add(new Chunk(" (A/C name.): \n \n", font9italic));
				p.add(new Chunk("ĐỊA CHỈ ", font9)); 
				p.add(new Chunk(" (Address): ", font9italic));
				cell4.addElement(p);
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setBorder(0);
				cell4.setFixedHeight(1.2f * CONVERT);
				table4.addCell(cell4);
				
				cell4 = new PdfPCell(new Paragraph(traTen.toUpperCase(), font9bold));
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
				
				if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh;
				
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
				
				if(nhanChiNhanh.trim().length()>0)
					nhanNganHang = nhanNganHang + " - " + nhanChiNhanh;
				
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
				cell3 = new PdfPCell(new Paragraph(sotientt, font9bold));
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
				cell3 = new PdfPCell(new Paragraph(tienbangchu, font9bold));
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
				cell3 = new PdfPCell(new Paragraph("KẾ TOÁN TRƯỞNG KÝ", font9bold));
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
			else if(traNganHangMa.equals("ACB")) //Á CHÂU
			{
				/*//Logo ACB
		        Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/acb.png");
				hinhanh.scalePercent(75);
				hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);*/
								
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER + CONTENT
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				
				PdfPTable table2 = new PdfPTable(TABLE_ACB_WIDTHS.length);	// 4 cột
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidths(TABLE_ACB_WIDTHS);
				table2.setWidthPercentage(100.0f);
				
				// 1 dòng
				cell2 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI", font13bold)); 
				p.add(new Chunk("/PAYMENT ORDER", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length); 
				cell2.addElement(p);
				table2.addCell(cell2);
				
				String NGN = "";
				if(ngayghinhan.trim().length() > 0 )
				{
					String [] ngayHD = ngayghinhan.split("-");		
					NGN = ngayHD[2] +" / " + ngayHD[1] + " / " + ngayHD[0];
				}
				
				// 1 dòng
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ngày", font11bold)); 
				p.add(new Chunk("/Date", font10italic));
				p.add(new Chunk("  " + NGN, font10));
				cell2.addElement(p);
				//p.setAlignment(Element.ALIGN_CENTER);
				//cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				//cell2.setBorderWidth(1);
				cell2.setColspan(TABLE_ACB_WIDTHS.length - 2);
				cell2.setPaddingLeft(7.5f * CONVERT);				
				table2.addCell(cell2);
				
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số/ ", font10bold)); 
				p.add(new Chunk("No.", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setColspan(2);
				cell2.setBorder(0);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				//////////////////				
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ĐƠN VỊ TRẢ TIỀN", font11bold)); 
				p.add(new Chunk("/APPLICANT", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.setBorderWidth(0);
				cell2.setBorderWidthTop(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				/////////////
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tên tài khoản", font11bold)); 
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
				p.add(new Chunk("Số tài khoản", font11bold)); 
				p.add(new Chunk("/Acct No.", font10italic));
				p.add(new Chunk("  " + traStk, font12bold));
				p.add(new Chunk("       Tại ngân hàng Á Châu _ chi nhánh", font11bold)); 
				p.add(new Chunk("/At ACB _ Branch: "+traChiNhanh, font10italic)); 
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
				p.add(new Chunk("ĐƠN VỊ THỤ HƯỞNG", font11bold)); 
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
				p.add(new Chunk("Đơn vị thụ hưởng", font11bold)); 
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
				p.add(new Chunk("Số tài khoản", font11bold)); 
				p.add(new Chunk("/Acct No.", font10italic));
				p.add(new Chunk("               " + nhanStk, font10bold));
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
				p.add(new Chunk("CMND/Hộ chiếu", font11bold)); 
				p.add(new Chunk("/ID CARD/PP No.", font10italic));
				p.add(new Chunk("           	       		 ", font10bold));
				p.add(new Chunk("Ngày cấp", font10bold));
				p.add(new Chunk("/Date", font10italic));
				p.add(new Chunk("           			                     ", font10bold));
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
				p.add(new Chunk("Tại Ngân hàng", font11bold)); 
				p.add(new Chunk("/Beneficiary's Bank ", font10italic));
				p.add(new Chunk(nhanNganHang, font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				//cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tỉnh, TP: "+nhanChiNhanh, font11bold)); 
				p.add(new Chunk("/Province, City ", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(0);
				//cell2.setBorderWidthBottom(1);
				cell2.setColspan(TABLE_ACB_WIDTHS.length-1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				
				//Column1 
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
								
				PdfPTable table131 = new PdfPTable(4);	
				table131.setHorizontalAlignment(Element.ALIGN_CENTER);
				table131.setWidths(new float[] {2.5f * CONVERT, 2.5f * CONVERT ,  2.5f * CONVERT , 8.0f*CONVERT });
				table131.setWidthPercentage(100.0f);
							
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tiền bằng số \n", font10bold)); 
				p.add(new Chunk("Amount in figures", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				cell3.addElement(p);
				table131.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(sotientt, font11bold));
				p.add(new Chunk(" VNĐ", font9bold)); 
				p.setAlignment(Element.ALIGN_RIGHT);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setPaddingRight(0.2f * CONVERT);
				cell3.setBorder(0);
				cell3.addElement(p);
				cell3.setBackgroundColor(new BaseColor(219, 229, 241));
				table131.addCell(cell3);
								
				cell3 = new PdfPCell();				
				p = new Paragraph();
				p.add(new Chunk("Bằng chữ: \n", font10bold)); 
				p.add(new Chunk("/Amount in words ", font10italic));;
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.addElement(p);
				table131.addCell(cell3);
				
				cell3 = new PdfPCell();				
				p = new Paragraph();
				p.add(new Chunk(tienbangchu +" \n", font11)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.addElement(p);
				table131.addCell(cell3);
								
				cell2.addElement(table131);
				table2.addCell(cell2);	
			
				/////
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Nội dung", font11bold)); 
				p.add(new Chunk("/Details     ", font10italic));
				p.add(new Chunk(noidungtt, font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				/////
				
			/*	cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Phí chuyển tiền do/", font9bold)); 
				p.add(new Chunk("/Transfer fee to be paid by																	", font9italic));
				p.add(new Chunk("Đơn vị trả tiền chịu phí/", font9bold)); 
				p.add(new Chunk("/The applicant																			", font9italic));
				p.add(new Chunk("Đơn vị thụ hưởng chịu phí/", font9bold)); 
				p.add(new Chunk("/The beneficiary ", font9italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setColspan(TABLE_ACB_WIDTHS.length);
				cell2.addElement(p);
				table2.addCell(cell2);*/
				
				cell.addElement(table2);
				table.addCell(cell);
				
				////
				
				//FOOTER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				//cell.setBorderWidthTop(1);
				
				table2 = new PdfPTable(3);	
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				float columnSize = 18.0f / 3 * CONVERT;
				table2.setWidths(new float[] {columnSize, columnSize, columnSize});
				table2.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Phí chuyển tiền do/", font9bold)); 
				p.add(new Chunk("/Transfer fee to be paid by", font9italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Đơn vị trả tiền chịu phí/", font9bold)); 
				p.add(new Chunk("/The applicant	", font9italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Đơn vị thụ hưởng chịu phí/", font9bold)); 
				p.add(new Chunk("/The beneficiary ", font9italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorder(0);
				cell2.setBorderWidthBottom(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ĐƠN VỊ TRẢ TIỀN", font11bold)); 
				p.add(new Chunk("/Application", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("NGÂN HÀNG Á CHÂU", font11bold)); 
				p.add(new Chunk("/ACB", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(1);
				cell2.addElement(p);
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("NGÂN HÀNG B", font11bold)); 
				p.add(new Chunk("/B Bank", font10italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setBorderWidth(1);
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
				
				cell3 = new PdfPCell(new Paragraph("Kế toán trưởng", font11bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Chủ tài khoản", font11bold));
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
				p.add(new Chunk("Ghi sổ ngày", font11bold)); 
				p.add(new Chunk("/Post date.............", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setColspan(2);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Giao dịch viên", font11bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Kiểm soát viên", font11bold));
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
				p.add(new Chunk("Ghi sổ ngày", font11bold)); 
				p.add(new Chunk("/Post date.............", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setColspan(2);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Giao dịch viên", font11bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Kiểm soát viên", font11bold));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Teller", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table3.addCell(cell3);
				
				cell3 = new PdfPCell(new Paragraph("Dupervisor", font10italic));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setFixedHeight(1.5f * CONVERT);
				table3.addCell(cell3);
				
				cell2.addElement(table3);
				table2.addCell(cell2);
				
				cell.addElement(table2);
				table.addCell(cell);
				
				//FOOTER_IMG
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);		
				
				p = new Paragraph();
				p.add(new Chunk("	Contact Center 24/7: 1900 54 54 86 -(08) 38 247 247 | acb.com.vn | ", font8)); 
				cell.addElement(p);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setBorder(0);
				cell.setPaddingLeft(7.0f*CONVERT);
				
				table.addCell(cell);
				
				//Logo ACB
		        Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/acb.png");
				hinhanh.scalePercent(60);
				hinhanh.setAbsolutePosition(17.8f * CONVERT, document.getPageSize().getHeight() - 14.5f * CONVERT);
				document.add(hinhanh);
				
				document.add(table);
				
			}
			else if (traNganHangMa.equals("EXIMBANK")){
				//Logo EIB VIETINBANK
		        Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/EXIMBANK.png");
				hinhanh.scalePercent(80);
				hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				PdfPTable table1 = new PdfPTable(TABLE_VIETINKBANK_WIDTHS.length);	
				table1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.setWidths(TABLE_VIETINKBANK_WIDTHS);
				table1.setWidthPercentage(100.0f);
								
				Paragraph p = new Paragraph();
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI \n", font14bold)); 
				p.add(new Chunk("PAYMENT ORDER", font11bold)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setBorder(0);
				cell2.setColspan(2);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				String NGN = "";
				if(ngayghinhan.trim().length() > 0 )
				{
					String [] ngayHD = ngayghinhan.split("-");		
					NGN = ngayHD[2] +" / " + ngayHD[1] + " / " + ngayHD[0];
				}	
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số ", font10bold)); 
				p.add(new Chunk("/No \n", font8italic)); 
				p.add(new Chunk("Ngày ", font10bold)); 
				p.add(new Chunk("Date: ", font8italic)); 
				p.add(new Chunk(NGN, font11italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setBorder(0);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("CHUYỂN KHOẢN ", font8bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				//cell2.setPaddingTop(-0.2f*CONVERT);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				
				//CỘT 1
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				
				//BẢNG LỒNG
				PdfPTable table2 = new PdfPTable(2);	
				table2.setWidthPercentage(100);
				float[] withs2 = {100f, 200f};
				table2.setWidths(withs2);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Đơn vị trả tiền \n" , font9bold)); 		
				//p.add(new Chunk("Orderer’s Name ", font8italic));  
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setPaddingTop(-0.1f*CONVERT);
				table2.addCell(cell3);				
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traTen , font10bold)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				cell3.setRowspan(2);
				table2.addCell(cell3);
				
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				//p.add(new Chunk("Đơn vị trả tiền \n" , font9bold)); 		
				p.add(new Chunk("Orderer’s Name ", font8italic));  
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setPaddingTop(-0.3f*CONVERT);
				table2.addCell(cell3);				
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản \n" , font9bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traStk , font10bold)); 	 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setRowspan(2);
				cell3.setBorder(0);
				table2.addCell(cell3);
			
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				//p.add(new Chunk("Số tài khoản \n" , font9bold)); 	
				p.add(new Chunk("Account number" , font8italic)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.3f*CONVERT);
				cell3.setBorder(0);
				
				table2.addCell(cell3);
								
				/*if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh;*/
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tại Ngân hàng Xuất Nhập Khẩu Việt Nam - SGD/Chi nhánh/PGD: " , font10bold)); 	
				p.add(new Chunk(traChiNhanh , font10)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				cell3.setColspan(2);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("With VIETNAM EXPORT IMPORT BANK - M.T.O/Branch/T.O" , font8italic));  	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setColspan(2);
				cell3.setPaddingTop(-0.3f*CONVERT);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Đơn vị nhận tiền \n" , font10bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(nhanTen , font10bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setRowspan(2);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph(); 	
				p.add(new Chunk("Beneficiary’s Name" , font7italic)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setPaddingTop(-0.3f*CONVERT);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản " , font10bold)); 				
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setPaddingTop(-0.1f*CONVERT);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(traStk , font10bold)); 	 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setRowspan(2);
				cell3.setPaddingTop(-0.1f*CONVERT);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Account No." , font8italic)); 				
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.3f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Tại ngân hàng " , font10bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(traNganHang , font10bold)); 	 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setRowspan(2);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("With Bank " , font8italic)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.3f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Tỉnh/TP: " , font10bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("" , font10bold)); 	 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setRowspan(2);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Province/City " , font8italic)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.3f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				

				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tiền bằng chữ" , font10bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(tienbangchu , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				cell3.setRowspan(2);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Amount in words " , font7italic)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setPaddingTop(-0.3f*CONVERT);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell2.addElement(table2);
								
				table1.addCell(cell2);
				
				//CỘT 2
				cell2 = new PdfPCell();
				cell2.setBorder(0);				
				table1.addCell(cell2);	
				
				//CỘT 3
				cell2 = new PdfPCell();				
				cell2.setBorder(0);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setWidthPercentage(100);
				float[] withs1 = {60f};
				table3.setWidths(withs1);
											
				cell4 = new PdfPCell();				
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(3.3f*CONVERT);
				cell4.setBorder(0);
				table3.addCell(cell4);
								
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "CMND/Hộ chiếu: " , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);			
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "I.D No./PP No. " , font7italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.3f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Ngày cấp " , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);	
				cell4.setPaddingTop(-0.1f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Issued date " , font7italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.3f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Nơi cấp " , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);	
				cell4.setPaddingTop(-0.1f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Issued place " , font7italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.3f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Số tiền bằng số" , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.1f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Amount in figures" , font7italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.3f*CONVERT);
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( DinhDangTraphacoERP(sotientt)+" đồng" , font11bold); 
				p.setAlignment(Element.ALIGN_RIGHT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.1f*CONVERT);
				cell4.addElement(p);
				cell4.setBorderWidth(1);
				table3.addCell(cell4);
								
				cell2.addElement(table3);
				
				table1.addCell(cell2);	
				
				cell.addElement(table1);
				
				table.addCell(cell);
				
				document.add(table);	
				
				PdfPTable tablesign = new PdfPTable(2);	
				float[] widthsign = {90f, 300f };
				tablesign.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablesign.setWidths(widthsign);
				tablesign.setWidthPercentage(100);	
								
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Nội dung:" , font10bold));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( noidungtt , font10));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setRowspan(2);
				tablesign.addCell(cell);				

				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Details" , font7italic));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				tablesign.addCell(cell);
				
				document.add(tablesign);
				
				
				PdfPTable tablesign1 = new PdfPTable(6);	
				float[] widthsign1 = {150f, 100f, 150f, 150f, 150f, 150f };
				tablesign1.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablesign1.setWidths(widthsign1);
				tablesign1.setWidthPercentage(100);	
								
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "ĐV trả tiền ngày ……." , font9bold));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				cell.setBorderWidthLeft(1);
				cell.setColspan(2);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "NH A (Eximbank)" , font9bold));
				p.add(new Chunk( "(Eximbank) " , font9italic));
				p.add(new Chunk( "ghi sổ ngày   ……" , font9bold));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setColspan(2);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				tablesign1.addCell(cell);				

				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "NH B " , font9bold));
				p.add(new Chunk( "(Beneficiary’s Bank)" , font9italic));
				p.add(new Chunk( " ghi sổ ngày ………" , font9bold));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				cell.setColspan(2);
				tablesign1.addCell(cell);	
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Kế toán trưởng" , font9bold));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthLeft(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Chủ tài khoản" , font9bold));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);				
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Giao dịch viên" , font9bold));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Kiểm soát" , font9bold));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				cell.addElement(p);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Giao dịch viên" , font9bold));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.setBorder(0);
				cell.addElement(p);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Kiểm soát" , font9bold));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				p = new Paragraph();
				p.add(new Chunk( "Chief Accountant" , font7italic));
				p.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setFixedHeight(2.0f*CONVERT);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthBottom(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				p = new Paragraph();
				p.add(new Chunk( "Account holder" , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				cell.setBorderWidthBottom(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				p = new Paragraph();
				p.add(new Chunk( "Teller " , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthBottom(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				p = new Paragraph();
				p.add(new Chunk( "Verifier " , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				cell.setBorderWidthBottom(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				p = new Paragraph();
				p.add(new Chunk( "Teller " , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthBottom(1);
				tablesign1.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				p = new Paragraph();
				p.add(new Chunk( "Verifier " , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				cell.setBorderWidthBottom(1);
				tablesign1.addCell(cell);
				
				document.add(tablesign1);
				
				
			}
			else if (traNganHangMa.equals("SACOMBANK")){
				//Logo EIB SACOMBANK
		        Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/SACOMBANK.png");
				hinhanh.scalePercent(50);
				hinhanh.setAbsolutePosition(16.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				PdfPTable table1 = new PdfPTable(TABLE_SCBANK_WIDTHS.length);	
				table1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.setWidths(TABLE_SCBANK_WIDTHS);
				table1.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI \n", font14bold)); 
				p.add(new Chunk("PAYMENT ORDER", font11)); 
				p.setAlignment(Element.ALIGN_RIGHT);
				//cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setFixedHeight(1.3f * CONVERT);
				cell2.setPaddingRight(5.5f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				String NGN = "";
				if(ngayghinhan.trim().length() > 0 )
				{
					String [] ngayHD = ngayghinhan.split("-");		
					NGN = ngayHD[2] +" / " + ngayHD[1] + " / " + ngayHD[0];
				}	
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ngày / Date \n", font10));							
				p.add(new Chunk(NGN, font10));
				
				p.setAlignment(Element.ALIGN_LEFT);
				//cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setFixedHeight(1.5f * CONVERT);
				cell2.setPaddingLeft(-1.5f*CONVERT);
				cell2.setColspan(2);
				cell2.setBorder(0);
				cell2.addElement(p);
				table1.addCell(cell2);		
				
				//CỘT 1
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				
				//BẢNG LỒNG
				PdfPTable table2 = new PdfPTable(1);	
				table2.setWidthPercentage(100);
				float[] withs2 = {200f};
				table2.setWidths(withs2);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Cá nhân/Đơn vị chuyển tiền " , font10)); 		
				p.add(new Chunk("/Remitter: "+ traTen, font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				
				if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh;
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk("Tài khoản số " , font10)); 	
				p.add(new Chunk("/Account No. " , font10italic)); 
				p.add(new Chunk ( traStk +" \t \t ", font10bold)); 
				p.add(new Chunk("Tại chi nhánh/PGD/" , font10)); 
				p.add(new Chunk("/At Branch: " , font10italic));
				p.add(new Chunk(traNganHang , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
								
				cell3 = new PdfPCell();				
				p = new Paragraph();
				p.add(new Chunk("Cá nhân/Đơn vị thụ hưởng" , font10)); 
				p.add(new Chunk("/Beneficiary: ", font10italic)); 	
				p.add(new Chunk("\t "+nhanTen , font10bold)); 			
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
												
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk( "Tài khoản số " , font10)); 
				p.add(new Chunk( "/Account No. " , font10italic));
				p.add(new Chunk( ""+nhanStk , font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);				
				
				cell3 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Số CMND" , font10)); 
				p.add(new Chunk( "/HC/ID/PP No. \t \t \t  \t \t \t \t \t \t \t \t \t" , font10italic));
				p.add(new Chunk( "Ngày cấp" , font10)); 
				p.add(new Chunk( "/Date \t \t \t  \t \t \t \t \t \t" , font10italic));
				p.add(new Chunk( "Nơi cấp" , font10)); 
				p.add(new Chunk( "/Place \t \t \t  \t \t \t \t \t \t" , font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				

				if(nhanChiNhanh.trim().length()>0)
					nhanNganHang = nhanNganHang +" - "+ nhanChiNhanh;
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Tại ngân hàng" , font10)); 
				p.add(new Chunk( "/Beneficiary bank: \t ", font10italic));
				p.add(new Chunk( nhanNganHang +" \t \t \t \t \t \t  \t \t \t \t \t \t" , font10)); 
				p.add(new Chunk( "Tỉnh/TP" , font10)); 
				p.add(new Chunk( "/Province/City ", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);			
																
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Số tiền bằng chữ" , font10)); 
				p.add(new Chunk( "/Amount in words: " , font10italic)); 
				p.add(new Chunk( tienbangchu +"./", font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Nội dung" , font10)); 
				p.add(new Chunk( "/Narrative: " , font10italic)); 
				p.add(new Chunk( noidungtt , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
							
				cell2.addElement(table2);
								
				table1.addCell(cell2);
				
				//CỘT 2
				cell2 = new PdfPCell();
				cell2.setBorder(0);				
				table1.addCell(cell2);	
				
				//CỘT 3
				cell2 = new PdfPCell();				
				cell2.setBorder(0);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setWidthPercentage(100);
				float[] withs1 = {60f};
				table3.setWidths(withs1);
				
				cell4 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Số GD" , font10)); 
				p.add(new Chunk( "/Ref No. " , font10italic));
				p.setAlignment(Element.ALIGN_LEFT);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Mã GDV" , font10)); 
				p.add(new Chunk( "/Teller ID" , font10italic));
				p.setAlignment(Element.ALIGN_LEFT);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "PHẦN DO NGÂN HÀNG GHI \n" , font8bold)); 
				p.add(new Chunk( "Bank use only" , font8italic)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthTop(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "TÀI KHOẢN NỢ" , font8)); 
				p.add(new Chunk( "/Debit account" , font8italic)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthTop(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "TÀI KHOẢN CÓ" , font8)); 
				p.add(new Chunk( "/Credit account" , font8italic)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthTop(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				cell4.setFixedHeight(0.2f*CONVERT);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "SỐ TIỀN BẰNG SỐ \n" , font8bold)); 
				p.add(new Chunk( "Amount in figures" , font8italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.addElement(p);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthBottom(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( DinhDangTraphacoERP(sotientt)+" đồng" , font11bold); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.addElement(p);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthBottom(1);
				cell4.setBorderWidthTop(0);
				table3.addCell(cell4);
								
				cell2.addElement(table3);
				
				table1.addCell(cell2);	
											
				//FOOTER
				cell2 = new PdfPCell();
				cell2.setColspan(4);
				cell2.setBorder(0);	
				
				PdfPTable table4 = new PdfPTable(3);	
				table4.setWidthPercentage(100);
				float[] withs4 = {60f, 60f, 60f};
				table4.setWidths(withs4);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "CÁ NHÂN/ĐƠN VỊ CHUYỂN " , font8bold)); 
				p.add(new Chunk( "/Remitter" , font8bi));				
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "SACOMBANK \n" , font8bold)); 
				p.add(new Chunk( "Ghi sổ ngày" , font8));	
				p.add(new Chunk( "/Settlement date" , font8));	
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "NGÂN HÀNG B " , font8bold)); 
				p.add(new Chunk( "/Beneficiary bank \n" , font8bi));	
				p.add(new Chunk( "Ghi sổ ngày" , font8));
				p.add(new Chunk( "/Settlement date" , font8italic));
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell(); 
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán trưởng " , font7)); 
				p.add(new Chunk( "/Account \t \t \t \t \t \t \t \t \t \t " , font7italic));
				p.add(new Chunk( "Chủ tài khoản " , font7));
				p.add(new Chunk( "/Account holder " , font7italic));		
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
												
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán " , font7)); 
				p.add(new Chunk( "/Account \t \t \t \t \t \t \t \t \t \t " , font7italic));
				p.add(new Chunk( "Trưởng đơn vị " , font7));
				p.add(new Chunk( "/Approved by " , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(2.0f * CONVERT);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán " , font7)); 
				p.add(new Chunk( "/Account \t \t \t \t \t \t \t \t \t \t " , font7italic));
				p.add(new Chunk( "Trưởng đơn vị " , font7));
				p.add(new Chunk( "/Approved by " , font7italic));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(3.0f * CONVERT);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				
				cell2.addElement(table4);
				
				table1.addCell(cell2);				
				cell.addElement(table1);
				table.addCell(cell);
				
				document.add(table);				
				
			}
			else if (traNganHangMa.equals("ANBINH")){
				//Logo EIB SACOMBANK
		        Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/ABBANK.png");
				hinhanh.scalePercent(60);
				hinhanh.setAbsolutePosition(0.5f * CONVERT, document.getPageSize().getHeight() - 1.5f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				PdfPTable table1 = new PdfPTable(TABLE_ABBBANK_WIDTHS.length);	
				table1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.setWidths(TABLE_ABBBANK_WIDTHS);
				table1.setWidthPercentage(100.0f);				

				String NGN = "";
				if(ngayghinhan.trim().length() > 0 )
				{
					String [] ngayHD = ngayghinhan.split("-");		
					NGN = ngayHD[2] +" / " + ngayHD[1] + " / " + ngayHD[0];
				}	
				
				
				cell2 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI (Payment Order) \n", font12bold)); 
				p.add(new Chunk("Ngày ", font10)); 
				p.add(new Chunk("(Date): ", font10italic)); 
				p.add(new Chunk(NGN, font10)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setFixedHeight(1.0f * CONVERT);
				//cell2.setPaddingRight(5.5f*CONVERT);
				cell2.setBorder(0);
				cell2.setColspan(2);
				cell2.setRowspan(2);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Mã GDV ", font8));							
				p.add(new Chunk("(Teller ID):……………………", font8italic));
				
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setBorder(0);
				cell2.addElement(p);
				table1.addCell(cell2);	
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số bút toán ", font8));							
				p.add(new Chunk("(Transaction No):……….", font8italic));
				
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setBorder(0);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				
				//CỘT 1
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				
				//BẢNG LỒNG
				PdfPTable table2 = new PdfPTable(1);	
				table2.setWidthPercentage(100);
				float[] withs2 = {200f};
				table2.setWidths(withs2);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Người chuyển " , font10)); 		
				p.add(new Chunk(" (Ordering name): ", font10italic)); 
				p.add(new Chunk(traTen, font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Địa chỉ " , font10)); 		
				p.add(new Chunk("(Address): "+ traTen, font10italic)); 
				p.add(new Chunk(traTen, font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				
				if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh;
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk("Số tài khoản " , font10)); 	
				p.add(new Chunk("(A/C No): " , font10italic)); 
				p.add(new Chunk ( traStk +" \t \t ", font10bold)); 
				p.add(new Chunk("Tại " , font10)); 
				p.add(new Chunk("(at): " , font10italic));
				p.add(new Chunk(traNganHang , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setBackgroundColor(new BaseColor(240,255,255));
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tên tài khoản " , font10)); 		
				p.add(new Chunk(" (A/C name): "+ traTen, font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setBackgroundColor(new BaseColor(240,255,255));
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ngày chuyển " , font10)); 		
				p.add(new Chunk(" (Value Day): ", font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setBackgroundColor(new BaseColor(240,255,255));
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Lọai tiền " , font10)); 		
				p.add(new Chunk(" (currency): VND", font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setBackgroundColor(new BaseColor(240,255,255));
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tiền " , font10)); 		
				p.add(new Chunk(" (Amount):" +DinhDangTraphacoERP(sotientt), font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setBackgroundColor(new BaseColor(240,255,255));
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Bằng chữ " , font10)); 		
				p.add(new Chunk("(In words):" + tienbangchu, font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setBackgroundColor(new BaseColor(240,255,255));
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tỷ giá " , font10)); 		
				p.add(new Chunk("(Exchange rate):", font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tiền quy đổi " , font10)); 		
				p.add(new Chunk(" (Coversion amount):", font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ngưởi thụ hưởng " , font10bold)); 		
				p.add(new Chunk(" (Beneficiary): ", font10italic)); 
				p.add(new Chunk(nhanTen, font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số CMND/Hộ chiếu " , font10)); 		
				p.add(new Chunk(" (ID/Passport): ", font10italic)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Ngày cấp " , font10)); 		
				p.add(new Chunk("(Issued Date): \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ", font10italic)); 
				p.add(new Chunk("Nơi cấp " , font10)); 		
				p.add(new Chunk("(Inssued place): \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ", font10italic));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản " , font10)); 		
				p.add(new Chunk("(Beneficiary A/c): ", font10italic)); 
				p.add(new Chunk(nhanStk, font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				if(nhanChiNhanh.trim().length()>0)
					nhanNganHang = nhanNganHang +" - "+ nhanChiNhanh;
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tại ngân hàng " , font10)); 		
				p.add(new Chunk("(Beneficiary's Bank): ", font10italic)); 
				p.add(new Chunk(nhanNganHang, font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);		
				cell2.addElement(table2);
								
				table1.addCell(cell2);
				
				//CỘT 2
				cell2 = new PdfPCell();
				cell2.setBorder(0);				
				table1.addCell(cell2);	
				
				//CỘT 3
				cell2 = new PdfPCell();				
				cell2.setBorder(0);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setWidthPercentage(100);
				float[] withs1 = {60f};
				table3.setWidths(withs1);	
				
				cell4 = new PdfPCell(); 
				p.setAlignment(Element.ALIGN_CENTER);		
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(5.5f*CONVERT);
				cell4.setBorder(0);
				table3.addCell(cell4);				
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Phí chuyển tiền" , font9)); 
				p.add(new Chunk( "(Bank charges):" , font9italic)); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				cell4.setBackgroundColor(new BaseColor(255,250,205));
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Người chuyển " , font9)); 
				p.add(new Chunk( "(Our) \t\t\t\t\t\t\t " , font9italic)); 
				p.add(new Chunk( "Người nhận " , font9));
				p.add(new Chunk( "(Beneficiary) " , font9italic)); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Nội dung " , font9)); 
				p.add(new Chunk( "(Payment details):" , font9italic)); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( noidungtt , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setBorder(0);
				table3.addCell(cell4);
								
				cell2.addElement(table3);
				
				table1.addCell(cell2);	
											
				//FOOTER
				cell2 = new PdfPCell();
				cell2.setColspan(4);
				cell2.setBorder(0);	
				
				PdfPTable table4 = new PdfPTable(3);	
				table4.setWidthPercentage(100);
				float[] withs4 = {60f, 60f, 60f};
				table4.setWidths(withs4);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Phần dành cho ABBANK ghi ngày chuyển tiền " , font8bold)); 
				p.add(new Chunk( " (Transfer date):" , font8bi));				
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorder(0);
				cell5.setColspan(3);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell(); 
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán trưởng \t \t \t \t \t \t \t \t \t \t" , font8bold)); 
				p.add(new Chunk( "Chủ tài khoản ( Account holder )" , font8bold));		
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
								
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Giao dịch viên \n" , font8bold)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(1.0f * CONVERT);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Kiểm soát \t \t \t \t \t \t \t \t \t \t  \t \t \t \t \t  \t \t \t \t \t" , font8bold)); 
				p.add(new Chunk( "GĐ/Trưởng phòng " , font8bold));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(1.0f * CONVERT);
				cell5.setBorderWidth(1);
				table4.addCell(cell5);
				
				
				cell2.addElement(table4);
				
				table1.addCell(cell2);				
				cell.addElement(table1);
				table.addCell(cell);
				
				document.add(table);				
				
			}
			else if (traNganHangMa.equals("DONGA")){
				//Logo EIB SACOMBANK
		        Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/DONGA.jpg");
				hinhanh.scalePercent(50);
				hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				PdfPTable table1 = new PdfPTable(TABLE_DABANK_WIDTHS.length);	
				table1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.setWidths(TABLE_DABANK_WIDTHS);
				table1.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI \n", font14bold)); 
				p.add(new Chunk("Chuyển khỏan, chuyển tiền thư, điện", font11)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setFixedHeight(1.3f * CONVERT);
				/*cell2.setPaddingRight(5.5f*CONVERT);*/
				cell2.setBorder(0);
				cell2.setColspan(2);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				String NGN = "";
				if(ngayghinhan.trim().length() > 0 )
				{
					String [] ngayHD = ngayghinhan.split("-");		
					NGN = ngayHD[2] +" / " + ngayHD[1] + " / " + ngayHD[0];
				}	
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số \n", font10));
				p.add(new Chunk("Ngày: " + NGN, font10));
				p.setAlignment(Element.ALIGN_LEFT);
				//cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setFixedHeight(1.5f * CONVERT);
				//scell2.setPaddingLeft(-1.5f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);
				table1.addCell(cell2);		
				
				//CỘT 1
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				
				//BẢNG LỒNG
				PdfPTable table2 = new PdfPTable(2);	
				table2.setWidthPercentage(100);
				float[] withs2 = {100f, 200f};
				table2.setWidths(withs2);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tên đơn vị trả tiền " , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traTen , font10bold)); 		 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tài khỏan số: " , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh;
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traStk , font10bold)); 		 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tại ngân hàng: " , font10)); 		 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traNganHang + " \t \t \t \t \t \t  \t \t \t \t \t \t" , font10bold)); 
				p.add(new Chunk( "Tỉnh/TP" , font10)); 	 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Địa chỉ : " , font10)); 		 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("" , font10)); 		 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
								
				cell3 = new PdfPCell();				
				p = new Paragraph();
				p.add(new Chunk("Tên đơn vị nhận tiền: " , font10)); 			
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();				
				p = new Paragraph();	
				p.add(new Chunk(nhanTen , font10bold)); 			
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
												
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk( "Tài khoản số: " , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);	
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk( nhanStk , font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);		
								
				if(nhanChiNhanh.trim().length()>0)
					nhanNganHang = nhanNganHang +" - "+ nhanChiNhanh;
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Tại ngân hàng: " , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);	
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( nhanNganHang +" \t \t \t \t \t \t  \t \t \t \t \t \t" , font10bold)); 
				p.add(new Chunk( "Tỉnh/TP" , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);	
				
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Địa chỉ: " , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);	
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk("" , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
																				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Số tiền bằng chữ: " , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( tienbangchu +"./", font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( "Nội dung" , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph(); 
				p.add(new Chunk( noidungtt , font10)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
							
				cell2.addElement(table2);
								
				table1.addCell(cell2);
				
				//CỘT 2
				cell2 = new PdfPCell();
				cell2.setBorder(0);				
				table1.addCell(cell2);	
				
				//CỘT 3
				cell2 = new PdfPCell();				
				cell2.setBorder(0);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setWidthPercentage(100);
				float[] withs1 = {60f};
				table3.setWidths(withs1);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "PHẦN DO NGÂN HÀNG GHI \n" , font8bold)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Tài khỏan ghi Nợ" , font10)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setBackgroundColor(new BaseColor(220,220,220));
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthTop(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "" , font8)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setFixedHeight(0.7f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				table3.addCell(cell4);
				
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Tài khỏan Có" , font10)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBackgroundColor(new BaseColor(220,220,220));
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthTop(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "" , font8)); 
				p.setAlignment(Element.ALIGN_CENTER);				
				cell4.addElement(p);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setFixedHeight(0.7f*CONVERT);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "SỐ TIỀN BẰNG SỐ \n" , font8bold)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBackgroundColor(new BaseColor(220,220,220));
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.addElement(p);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( DinhDangTraphacoERP(sotientt)+" VND" , font11bold); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.addElement(p);
				cell4.setBorderWidthTop(0);
				cell4.setBorderWidthLeft(1);
				cell4.setBorderWidthRight(1);
				cell4.setBorderWidthBottom(1);
				
				table3.addCell(cell4);
								
				cell2.addElement(table3);
				
				table1.addCell(cell2);	
											
				//FOOTER
				cell2 = new PdfPCell();
				cell2.setColspan(4);
				cell2.setBorder(0);	
				
				PdfPTable table4 = new PdfPTable(3);	
				table4.setWidthPercentage(100);
				float[] withs4 = {60f, 60f, 60f};
				table4.setWidths(withs4);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Đơn vị trả tiền " , font10bold)); 			
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthTop(1);
				table4.addCell(cell5);
				
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Ngân hàng A \n " , font10bold)); 
				p.add(new Chunk( "Ghi sổ ngày:……………." , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthTop(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Ngân hàng B \n" , font10bold)); 
				p.add(new Chunk( "Ghi sổ ngày:……………." , font10bold)); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthTop(1);
				cell5.setBorderWidthRight(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell(); 
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" , font10bold)); 
				p.add(new Chunk( "Chủ tài khoản " , font10bold));		
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthBottom(1);
				table4.addCell(cell5);
												
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" , font10bold)); 
				p.add(new Chunk( "Trưởng phòng kế toán " , font10bold));
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(3.0f * CONVERT);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthBottom(1);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" , font10bold)); 
				p.add(new Chunk( "Trưởng phòng kế toán " , font10bold));
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(3.0f * CONVERT);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthBottom(1);
				cell5.setBorderWidthRight(1);
				table4.addCell(cell5);
				
				
				cell2.addElement(table4);
				
				table1.addCell(cell2);				
				cell.addElement(table1);
				table.addCell(cell);
				
				document.add(table);				
				
			}
			else if (traNganHangMa.equals("VIETINBANK")){
				//Logo EIB VIETINBANK
		        Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/VIETINKBANK.png");
				hinhanh.scalePercent(70);
				hinhanh.setAbsolutePosition(16.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
				document.add(hinhanh);
				
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);
				
				//HEADER
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				PdfPTable table1 = new PdfPTable(TABLE_VIETINKBANK_WIDTHS.length);	
				table1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.setWidths(TABLE_VIETINKBANK_WIDTHS);
				table1.setWidthPercentage(100.0f);
				
				cell2 = new PdfPCell();
				Paragraph p = new Paragraph();
				p.add(new Chunk("Ngân Hàng TMCP Công Thương Việt Nam", font10bold));
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Lệnh Chi \n", font14bold)); 
				p.add(new Chunk("Payment Order", font11)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				String NGN = "";
				if(ngayghinhan.trim().length() > 0 )
				{
					String [] ngayHD = ngayghinhan.split("-");		
					NGN = ngayHD[2] +" / " + ngayHD[1] + " / " + ngayHD[0];
				}	
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số ", font11)); 
				p.add(new Chunk("No \t \t \t \t ", font11italic)); 
				p.add(new Chunk("Ngày ", font11)); 
				p.add(new Chunk("Date ", font11italic)); 
				p.add(new Chunk(NGN, font11italic)); 
				p.setAlignment(Element.ALIGN_RIGHT);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Liên 2 Dành cho khách hàng Copy 2 Customer's Copy ", font8bold)); 
				cell2.setBackgroundColor(new BaseColor(220,220,220));
				p.setAlignment(Element.ALIGN_RIGHT);
				cell2.setPaddingTop(-0.2f*CONVERT);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.addElement(p);
				table1.addCell(cell2);
				
				
				//CỘT 1
				cell2 = new PdfPCell();
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setBorderWidth(0);
				
				//BẢNG LỒNG
				PdfPTable table2 = new PdfPTable(2);	
				table2.setWidthPercentage(100);
				float[] withs2 = {100f, 200f};
				table2.setWidths(withs2);
								
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tên người trả tiền " , font10bold)); 		
				p.add(new Chunk("Payer ", font10));  
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);				
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traTen , font10bold)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản " , font10bold)); 	
				p.add(new Chunk("A/C number" , font10)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traStk , font10bold)); 	 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Tại ngân hàng " , font10bold)); 	
				p.add(new Chunk("With Bank" , font10)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh;
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(traNganHang , font10bold));  	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("Số tiền bằng chữ \n" , font10bold)); 	
				p.add(new Chunk("Amount in words" , font10)); 	
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
								
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(tienbangchu , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Tên người nhận tiền " , font10bold)); 	
				p.add(new Chunk("Payee" , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(nhanTen , font10bold)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Số tài khoản " , font10bold)); 		
				p.add(new Chunk("A/C number" , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(traStk , font10bold)); 	 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Tại ngân hàng " , font10bold)); 		
				p.add(new Chunk("With Bank" , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(traNganHang , font10bold)); 	 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk("Nội dung " , font10bold)); 		
				p.add(new Chunk("Remarks " , font10)); 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);
				
				cell3 = new PdfPCell(); 
				p = new Paragraph();
				p.add(new Chunk(noidungtt , font10bold)); 	 		
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(p);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				table2.addCell(cell3);	
							
				cell2.addElement(table2);
								
				table1.addCell(cell2);
				
				//CỘT 2
				cell2 = new PdfPCell();
				cell2.setBorder(0);				
				table1.addCell(cell2);	
				
				//CỘT 3
				cell2 = new PdfPCell();				
				cell2.setBorder(0);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setWidthPercentage(100);
				float[] withs1 = {60f};
				table3.setWidths(withs1);
											
				cell4 = new PdfPCell();				
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setFixedHeight(1.5f*CONVERT);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "SỐ TIỀN BẰNG SỐ Amount in figures" , font8)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setPaddingTop(-0.2f*CONVERT);
				cell4.setBackgroundColor(new BaseColor(220,220,220));
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
				
				cell4 = new PdfPCell();
				p = new Paragraph( DinhDangTraphacoERP(sotientt)+" VND" , font11bold); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell4.setFixedHeight(1.0f*CONVERT);
				cell4.setVerticalAlignment(Element.ALIGN_TOP);
				cell4.setBackgroundColor(new BaseColor(220,220,220));
				cell4.addElement(p);
				cell4.setBorder(0);
				table3.addCell(cell4);
								
				cell2.addElement(table3);
				
				table1.addCell(cell2);	
				
				cell2 = new PdfPCell();
				cell2.setBackgroundColor(new BaseColor(220,220,220));
				cell2.setFixedHeight(0.2f*CONVERT);
				cell2.setBorder(0);
				cell2.setColspan(3);
				cell2.addElement(p);
				table1.addCell(cell2);
								
				
				//FOOTER
				cell2 = new PdfPCell();
				cell2.setColspan(4);
				cell2.setBorder(0);	
				
				PdfPTable table4 = new PdfPTable(4);	
				table4.setWidthPercentage(100);
				float[] withs4 = {60f, 60f, 60f, 60f};
				table4.setWidths(withs4);
								
				cell5 = new PdfPCell(); 
				p = new Paragraph( ); 
				p.add(new Chunk( "Người trả tiền Payer " , font8)); 	
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setBorder(0);
				cell5.setColspan(2);
				cell5.setPaddingTop(-0.2f*CONVERT);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell(); 
				p = new Paragraph( ); 
				p.add(new Chunk( "Ngày hạch toán Accounting date ……/…../……… " , font8)); 	
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setBorder(0);
				cell5.setColspan(2);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell(); 
				p = new Paragraph( ); 
				p.add(new Chunk( "Kế toán trưởng " , font8bold)); 
				p.add(new Chunk( "Chief accountant" , font8));		
				p.setAlignment(Element.ALIGN_LEFT);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setBorder(0);
				table4.addCell(cell5);
												
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Chủ tài khoản " , font8bold)); 
				p.add(new Chunk( "A/c holder " , font8));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setFixedHeight(2.0f * CONVERT);
				cell5.setBorder(0);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Giao dịch viên " , font8bold)); 
				p.add(new Chunk( "Teller " , font8));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setFixedHeight(2.0f * CONVERT);
				cell5.setBorder(0);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Kiểm soát viên " , font8bold)); 
				p.add(new Chunk( "Supervisor " , font8));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setFixedHeight(2.0f * CONVERT);
				cell5.setBorder(0);
				table4.addCell(cell5);
				
				cell5 = new PdfPCell();
				p = new Paragraph( ); 
				p.add(new Chunk( "Ký tên, đóng dấu " , font8bold)); 
				p.add(new Chunk( "Signature & Seal " , font8));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setHorizontalAlignment(Element.ALIGN_TOP);
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setBorder(0);
				cell5.setColspan(4);
				table4.addCell(cell5);
				
				cell2.addElement(table4);
				table1.addCell(cell2);
				
				cell.addElement(table1);
				
				//Chữ Form quay góc 90 độ
				PdfContentByte cb = writer.getDirectContent();
				cb.beginText();
		        cb.setFontAndSize(bf, 8);
		        cb.setTextMatrix(0, 1, -1, 0, 20.6f*CONVERT, 0.8f * CONVERT);
		        cb.showText("Mẫu số 07/NHCT10");
		        cb.endText();
				
				table.addCell(cell);
				
				document.add(table);				
				
			}
			else if(traNganHangMa.equals("AGB")){				
				PdfContentByte cb = writer.getDirectContent();
				cb.beginText();
		        cb.setFontAndSize(bf, 6);
		        cb.setTextMatrix(0, 1, -1, 0, 0.9f*CONVERT, 4.0f * CONVERT);
		        cb.showText("Mẫu DP005 ban hành kèm theo Quyết định 1371/QĐ-NHNo-TCKT ngày 16/11/2011");
		        cb.endText();
		        		        
				PdfPTable table = new PdfPTable(1);	
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.setWidths(TABLE_WIDTHS);
				table.setWidthPercentage(100.0f);				
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorderWidth(0);
				
				//HEADER
				
				PdfPTable table1 = new PdfPTable(TABLE_AGB_WIDTHS.length);	
				table1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table1.setWidths(TABLE_AGB_WIDTHS);
				table1.setWidthPercentage(100.0f);
				
				//CỘT 1
				cell1 = new PdfPCell();
				
				cell1.setHorizontalAlignment(Element.ALIGN_TOP);
				cell1.setVerticalAlignment(Element.ALIGN_TOP);
				cell1.setBorder(0);
				
				PdfPTable tablelogo = new PdfPTable(1);	
				tablelogo.setHorizontalAlignment(Element.ALIGN_TOP);
				tablelogo.setWidthPercentage(100);
				float[] withslogo = {100f};
				tablelogo.setWidths(withslogo);
				
				Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/agribank.jpg");
				hinhanh.scalePercent(70);
				hinhanh.setAlignment(Element.ALIGN_CENTER);
				
				PdfPCell cellslogo = new PdfPCell(hinhanh);
				cellslogo.setPadding(5);
				cellslogo.setBorder(0);
				tablelogo.addCell(cellslogo);
				
				cellslogo = new PdfPCell();
				cellslogo.setBorder(0);	
				cellslogo.setFixedHeight(0.2f*CONVERT);
				tablelogo.addCell(cellslogo);
				
				cellslogo = new PdfPCell();
				cellslogo.setHorizontalAlignment(Element.ALIGN_TOP);
				cellslogo.setVerticalAlignment(Element.ALIGN_TOP);
				Paragraph p = new Paragraph();
				p = new Paragraph("..............................................", font9);
				p.setAlignment(Element.ALIGN_LEFT);
				cellslogo.setPaddingTop(-0.5f*CONVERT);
				cellslogo.setBorder(0);
				cellslogo.addElement(p);				
				tablelogo.addCell(cellslogo);
				
				cell1.addElement(tablelogo);
				table1.addCell(cell1);				
				
				//CỘT 2				
				cell1 = new PdfPCell();
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_CENTER);
				cell1.setBorder(0);
				
				PdfPTable table3 = new PdfPTable(1);	
				table3.setHorizontalAlignment(Element.ALIGN_TOP);
				table3.setWidthPercentage(100);
				float[] withstable3 = {100f};
				table3.setWidths(withstable3);
				
				cell3 = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk("ỦY NHIỆM CHI\n", font15bold)); 
				p.add(new Chunk("Payment Order", font11bi)); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setBorder(0);
				cell3.addElement(p);				
				table3.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph("Ngày ......./....... /20.......", font9);
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setPaddingLeft(2.3f*CONVERT);
				cell3.setBorder(0);
				cell3.addElement(p);				
				table3.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph("Date", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setPaddingLeft(2.3f*CONVERT);
				cell3.setPaddingTop(-0.1f*CONVERT);
				cell3.setBorder(0);
				cell3.addElement(p);				
				table3.addCell(cell3);
				
				cell1.addElement(table3);
				
				table1.addCell(cell1);
				
				
				//CỘT 3
				cell1 = new PdfPCell();
				cell1.setHorizontalAlignment(Element.ALIGN_BOTTOM);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setBorder(0);
				
				PdfPTable table6 = new PdfPTable(1);	
				table6.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				table6.setWidthPercentage(100);
				float[] withstable6 = {100f};
				table6.setWidths(withstable6);
				
				cell3 = new PdfPCell();
				p = new Paragraph("Số: ........................", font7);				
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setPaddingTop(-1.2f*CONVERT);
				cell3.addElement(p);				
				table6.addCell(cell3);
				
				cell3 = new PdfPCell();
				p = new Paragraph("No", font6italic);				
				p.setAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_TOP);
				cell3.setVerticalAlignment(Element.ALIGN_TOP);
				cell3.setBorder(0);
				cell3.setPaddingTop(-0.8f*CONVERT);
				cell3.addElement(p);				
				table6.addCell(cell3);
				
				cell1.addElement(table6);
								
				table1.addCell(cell1); 
				
				//CỘT 4
				cell1 = new PdfPCell();
				p = new Paragraph();
				cell1.setHorizontalAlignment(Element.ALIGN_TOP);
				cell1.setVerticalAlignment(Element.ALIGN_TOP);
				cell1.setBorder(0);
				
				//BẢNG LỒNG
				PdfPTable table2 = new PdfPTable(1);	
				table2.setHorizontalAlignment(Element.ALIGN_TOP);
				table2.setWidthPercentage(100);
				float[] withs2 = {100f};
				table2.setWidths(withs2);
				
				cell2 = new PdfPCell();
				p = new Paragraph("Liên 1:   Ngân hàng", font9);
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_BOTTOM);
				cell2.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell2.setBorder(0);
				cell2.addElement(p);				
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph("Original \t \t \t \t \t \t \t \t \t \t Bank", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_TOP);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setPaddingTop(-0.1f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);				
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph("Số bút toán: .....................", font9);
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_BOTTOM);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setPaddingTop(-0.1f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);				
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph("Seq No", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_TOP);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setPaddingTop(-0.1f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);				
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph("Loại tiền: .........................", font9);
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_BOTTOM);
				cell2.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell2.setPaddingTop(-0.1f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);				
				table2.addCell(cell2);
				
				cell2 = new PdfPCell();
				p = new Paragraph("Currency", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_TOP);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				cell2.setPaddingTop(-0.1f*CONVERT);
				cell2.setBorder(0);
				cell2.addElement(p);				
				table2.addCell(cell2);
				
				cell1.addElement(table2);
				
				table1.addCell(cell1);
				
				cell.addElement(table1);
				table.addCell(cell);
				document.add(table);
				
				PdfPTable tablecontents = new PdfPTable(2);	
				float[] withscontents = {500f,250f};
				tablecontents.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablecontents.setWidths(withscontents);
				tablecontents.setWidthPercentage(100);		
				
				//CỘT 1
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				cell.setBorderWidth(1);
				
				//BẢNG LỒNG CỘT 1
				
				PdfPTable table5 = new PdfPTable(2);	
				float[] withstable5 = {400f,300f};
				table5.setHorizontalAlignment(Element.ALIGN_TOP);
				table5.setWidths(withstable5);
				table5.setWidthPercentage(100);	
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Đơn vị trả tiền: "+ traTen, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.2f*CONVERT);
				cell5.setBorder(0);		
				cell5.setColspan(2);				
				table5.addCell(cell5);	
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Applicant", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);		
				cell5.setColspan(2);				
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Số tài khoản: "+ traStk, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Điện thoại: "+traDienthoai, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Account No", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Tel", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				if(traChiNhanh.trim().length()>0)
					traNganHang = traNganHang + " - "+ traChiNhanh ;
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Tại ngân hàng: "+traNganHang, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setColspan(2);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("At bank", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setColspan(2);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Đơn vị thụ hưởng: "+nhanTen, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setColspan(2);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Beneficiary", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setColspan(2);
				cell5.setBorder(0);					
				table5.addCell(cell5);
								
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("CMT/Hộ chiếu: ", font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Ngày cấp: ", font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("ID/Passport No", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Date of issue", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Nơi cấp:", font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Điện thoại:", font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Place of issue", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Tel", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);	
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Số tài khoản:"+nhanStk, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);
				cell5.setColspan(2);
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Account No", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);
				cell5.setColspan(2);
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				if(nhanChiNhanh.trim().length()>0)
					nhanNganHang = nhanNganHang + " - " + nhanChiNhanh;
					
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("Tại ngân hàng: "+nhanNganHang, font10);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);
				cell5.setColspan(2);
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph("At bank", font6italic);
				p.setAlignment(Element.ALIGN_LEFT);
				cell5.addElement(p);
				cell5.setColspan(2);
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);					
				table5.addCell(cell5);
				
				cell.addElement(table5);
				
				tablecontents.addCell(cell);
				
				//CỘT 2
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);				
				cell.setBorderWidth(1);		
				
				//BẢNG LỒNG CỘT 2
				PdfPTable table7 = new PdfPTable(1);	
				float[] withstable7 = {500f};
				table7.setHorizontalAlignment(Element.ALIGN_LEFT);
				table7.setWidths(withstable7);
				table7.setWidthPercentage(100);	
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setBorderWidth(1);	
				
				//BẢNG LỒNG 
				PdfPTable table8 = new PdfPTable(1);	
				float[] withstable8 = {500f};
				table8.setHorizontalAlignment(Element.ALIGN_LEFT);
				table8.setWidths(withstable8);
				table8.setWidthPercentage(100);	
				
				PdfPCell cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);	
				p = new Paragraph();
				p.add(new Chunk("Dành cho ngân hàng  ", font10));
				p.add(new Chunk("(For bank use only)", font7italic));				
				p.setAlignment(Element.ALIGN_LEFT);	
				cell8.setBackgroundColor(new BaseColor(142, 30, 32));
				cell8.addElement(p);	
				cell8.setBorder(0);	
				cell8.setPaddingTop(-0.1f*CONVERT);
				cell8.setFixedHeight(0.8f*CONVERT);
				table8.addCell(cell8);
								
				cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);	
				p = new Paragraph("Tài khoản ghi nợ:",font10);			
				p.setAlignment(Element.ALIGN_LEFT);	
				cell8.addElement(p);
				cell8.setBorder(0);	
				table8.addCell(cell8);
				
				cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);	
				p = new Paragraph("Debit account",font7italic);			
				p.setAlignment(Element.ALIGN_LEFT);	
				cell8.setPaddingTop(-0.1f*CONVERT);
				cell8.addElement(p);
				cell8.setBorder(0);	
				table8.addCell(cell8);
				
				cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);
				cell8.setBorderWidth(1);
				cell8.setFixedHeight(0.6f*CONVERT);
				table8.addCell(cell8);
				
				cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);	
				p = new Paragraph("Tài khoản ghi có:",font10);			
				p.setAlignment(Element.ALIGN_LEFT);	
				cell8.setPaddingTop(-0.1f*CONVERT);
				cell8.addElement(p);
				cell8.setBorder(0);	
				table8.addCell(cell8);
				
				cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);	
				p = new Paragraph("Creadit account",font7italic);			
				p.setAlignment(Element.ALIGN_LEFT);	
				cell8.setPaddingTop(-0.1f*CONVERT);
				cell8.addElement(p);
				cell8.setBorder(0);	
				table8.addCell(cell8);
				
				cell8 = new PdfPCell();
				cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell8.setVerticalAlignment(Element.ALIGN_TOP);
				cell8.setBorderWidth(1);
				cell8.setFixedHeight(0.6f*CONVERT);
				table8.addCell(cell8);
				
				
				cell5.addElement(table8);
				table7.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				cell5.setFixedHeight(0.1f*CONVERT);
				cell5.setBorderWidth(0);				
								
				table7.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				p = new Paragraph("Số tiền bằng số ", font10);	
				cell5.addElement(p);	
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthRight(1);
				cell5.setBorderWidthTop(1);
								
				table7.addCell(cell5);
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				p = new Paragraph("Amount in figures", font7italic);	
				cell5.addElement(p);
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthRight(1);
								
				table7.addCell(cell5);				
				
				cell5 = new PdfPCell();
				cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell5.setVerticalAlignment(Element.ALIGN_TOP);
				p = new Paragraph(sotientt+" VND", font13);	
				p.setAlignment(Element.ALIGN_CENTER);
				cell5.addElement(p);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell5.setBackgroundColor(new BaseColor(252, 218, 213));
				cell5.setPaddingTop(-0.1f*CONVERT);
				cell5.setFixedHeight(1.1f*CONVERT);
				cell5.setBorder(0);
				cell5.setBorderWidthLeft(1);
				cell5.setBorderWidthRight(1);
				cell5.setBorderWidthBottom(1);
								
				table7.addCell(cell5);		
				
				cell.addElement(table7);
				
				tablecontents.addCell(cell);
				
				document.add(tablecontents);
				
				PdfPTable tablefooter = new PdfPTable(1);	
				float[] withsfooter = {754f};
				tablefooter.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablefooter.setWidths(withsfooter);
				tablefooter.setWidthPercentage(100);		
				
				//CỘT 1
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph( "Số tiền bằng chữ: " +tienbangchu+"./", font10); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				cell.setBorderWidthTop(1);
				
				tablefooter.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph( "Amount in words " , font7italic); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(p);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				
				tablefooter.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph( "Nội dung: " , font10); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(p);
				cell.setBorder(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				
				tablefooter.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph( "Details " , font7italic); 
				p.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(p);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setFixedHeight(0.6f*CONVERT);
				cell.setBorder(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				cell.setBorderWidthBottom(1);
				
				tablefooter.addCell(cell);
				
				document.add(tablefooter);
				
				PdfPTable tablesign = new PdfPTable(3);	
				float[] widthsign = {300f, 300f, 300f};
				tablesign.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablesign.setWidths(widthsign);
				tablesign.setWidthPercentage(100);	
								
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				p = new Paragraph();
				p.add(new Chunk( "Đơn vị trả tiền " , font9));
				p.add(new Chunk("(Applicant)", font6italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph();
				p.add(new Chunk( "Ngân hàng A " , font9));
				p.add(new Chunk("(Bank of Applicant)", font6italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph();
				p.add(new Chunk( "Ngân hàng B " , font9));
				p.add(new Chunk("(Bank of Beneficiary)", font6italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph();
				p.add(new Chunk( "Ghi sổ ngày " , font7));
				p.add(new Chunk("(Settied date) ....../...../........", font6italic));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell.addElement(p);
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph();
				p.add(new Chunk( "Ghi sổ ngày " , font7));
				p.add(new Chunk("(Settied date) ....../...../........", font6italic));
				p.setAlignment(Element.ALIGN_CENTER);	
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph();
				p.add(new Chunk( "Kế toán trưởng \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t Chủ tài khoản" , font7));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell.addElement(p);
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph();
				p.add(new Chunk("Giao dịch viên \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t Kiểm soát" , font7));
				p.setAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph();
				p.add(new Chunk("Giao dịch viên \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t Kiểm soát" , font7));
				p.setAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.2f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				p = new Paragraph();
				p.add(new Chunk( "Chief accountant \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t Account holder " , font6italic));
				p.setAlignment(Element.ALIGN_CENTER);				
				cell.addElement(p);
				cell.setPaddingTop(-0.3f*CONVERT);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph();
				p.add(new Chunk( "Teller \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t        Supervisor " , font6italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.3f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				p = new Paragraph();
				p.add(new Chunk( "Teller \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t        Supervisor " , font6italic));
				p.setAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.3f*CONVERT);
				cell.addElement(p);
				cell.setBorder(0);
				
				tablesign.addCell(cell);
				
				document.add(tablesign);								
			}
			else if (traNganHangMa.equals("IVB")){ //INDOVINA BANK
								
				Paragraph hd = new Paragraph();
				
				PdfPTable tableheader = new PdfPTable(1);	
				tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableheader.setWidthPercentage(100);
				
				cell = new PdfPCell();
				hd = new Paragraph("TO: INDOVINA BANK LTD. HAI PHONG" , font11bold);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.addElement(hd);
				tableheader.addCell(cell);
				
				cell = new PdfPCell();
				hd = new Paragraph("Kính gửi: Ngân hàng Indovina Hải Phòng" ,font11italic);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				tableheader.addCell(cell);
				
				
				cell = new PdfPCell();
				hd = new Paragraph("TRANSACTIONS APPLYCATION" , font13bold);
				cell.addElement(hd);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				tableheader.addCell(cell);
				
				
				cell = new PdfPCell();
				hd = new Paragraph("Yêu cầu chi trả" , font11italic);
				cell.addElement(hd);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				cell.setBorder(0);				
				
				tableheader.addCell(cell);
				document.add(tableheader);
				
				PdfPTable table1 =new PdfPTable(2);
				table1.setWidthPercentage(100);
				float[] withs1 = {100f,200f};
				table1.setWidths(withs1);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("Name of applicant \n", font11bold));
				hd.add(new Chunk("Tên Cá nhân/công ty yêu cầu", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setBorder(0);
				
				table1.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph( traTen , font11bold);
				hd.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);				
				table1.addCell(cell);	
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("Passport No \n", font11bold));
				hd.add(new Chunk("Số CMND/Hộ chiếu", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setBorder(0);
				
				table1.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph( "" , font11italic);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				table1.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("Address in Vietnam \n", font11bold));
				hd.add(new Chunk("Địa chỉ tại Việt Nam", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setBorder(0);
				
				table1.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph( "Số 71 Lê Lai, Ngô Quyền, Hải Phòng" , font11bold);//"Số 71 Lê Lai, Ngô Quyền, Hải Phòng"
				hd.setAlignment(Element.ALIGN_LEFT);		
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				table1.addCell(cell);	
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("Account with IVB \n", font11bold));
				hd.add(new Chunk("Tài khoản tại IVB", font11italic));				
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setBorder(0);
				
				table1.addCell(cell);
				
				//Account with IVB
				cell= new PdfPCell();
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				PdfPTable table11 =new PdfPTable(1);
				table11 = new PdfPTable(1);	
				table11.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				float[] withstable11 = { 10.0f*CONVERT };
				table11.setWidths(withstable11);				
				table11.setWidthPercentage(80);
				
				cell3 = new PdfPCell();
				hd = new Paragraph();
				hd = new Paragraph( traStk , font11bi);		//"3001024-002"		
				hd.setAlignment(Element.ALIGN_CENTER);
				cell3.addElement(hd);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_CENTER);	
				cell3.setPaddingBottom(0.1f*CONVERT);
				cell3.setBorderWidthTop(1);
				cell3.setBorderWidthRight(1);
				cell3.setBorderWidthBottom(1);
				cell3.setBorderWidthLeft(1);
				
				table11.addCell(cell3);
				
				cell.addElement(table11);
				
				table1.addCell(cell);	
				
				document.add(table1);
				
				PdfPTable table2 = new PdfPTable(1);
				table2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.setWidthPercentage(100);
				
				// (1) TRANSACTIONS APPLIED FOR: (Please mark - X - for appropriate item)
				cell = new PdfPCell();
				hd = new Paragraph();
				hd.add(new Chunk("1) TRANSACTIONS APPLIED FOR: (Please mark - X - for appropriate item) \n", font11bold));
				hd.add(new Chunk("Nghiệp vụ yêu cầu (Đề nghị đánh dấu -X- vào mục thích hợp) \n", font11italic));
				hd.add(new Chunk("( ) To receive Vietnamese Dong (In cash) \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Nhận tiền đông (tiền mặt) \n", font11italic));
				hd.add(new Chunk("( ) To receive foreign currency (in cash) \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Nhận ngoại tệ (tiền mặt) \n", font11italic));
				hd.add(new Chunk("Kind of currency \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Loại ngoại tệ \n", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				table2.addCell(cell);
				
				document.add(table2);
				
				PdfPTable table3 =new PdfPTable(2);
				table3.setWidthPercentage(100);
				float[] withstable3 = {100f,200f};
				table3.setWidths(withstable3);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("( ) To Transfer to A/C No: \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Chuyển vào tài khoản số", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setBorder(0);
				
				table3.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph( nhanStk , font11bi);// "12410000270665"
				hd.setAlignment(Element.ALIGN_LEFT);	
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				table3.addCell(cell);	
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t With: \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Tại", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table3.addCell(cell);
				
				if(nhanChiNhanh.trim().length()>0)
					nhanNganHang =nhanNganHang + " - "+nhanChiNhanh;
				
				cell= new PdfPCell();	
				hd = new Paragraph( nhanNganHang , font11bi); //"NH - BIDV Chi Nhánh Hoàn Kiếm Hà Nội"
				hd.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				table3.addCell(cell);	
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t In favour of: \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Cho người thụ hưởng là", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table3.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph( nhanTen ,font11bi);//"DN Tư Nhân - XN Quốc Anh"
				hd.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table3.addCell(cell);
				document.add(table3);
				
				
				PdfPTable table4 =new PdfPTable(2);
				table4.setWidthPercentage(100);
				float[] withstable4 = {100f,200f};
				table4.setWidths(withstable4);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("( ) To Transfer abroad by \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Chuyển tiền ra nước ngoài bằng", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd = new Paragraph( "" , font11bi);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t - Bank draft \n", font11bold));
				hd.add(new Chunk("\t \t \t \t   Hối phiếu ngân hàng", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd = new Paragraph( "" , font11bi);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t - Mail \n", font11bold));
				hd.add(new Chunk("\t \t \t \t   Thư", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph( "" , font11italic);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
								
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t - Cable \n", font11bold));
				hd.add(new Chunk("\t \t \t \t   Điện", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);				
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd = new Paragraph( "" , font11bold);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
								
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t - With details: \n", font11bold));
				hd.add(new Chunk("\t \t \t \t   Chi tiết", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd = new Paragraph( noidungtt , font11bi); //"TT tiền hàng HĐ 0000665 theo ĐN 05/09/2014"
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("( ) Other \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Yêu cầu khác", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd = new Paragraph( "" , font11bi);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table4.addCell(cell);
				
				document.add(table4);
				
				PdfPTable table5 = new PdfPTable(1);
				table5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table5.setWidthPercentage(100);
				
				// (1) TRANSACTIONS APPLIED FOR: (Please mark - X - for appropriate item)
				cell = new PdfPCell();
				hd = new Paragraph();
				hd.add(new Chunk("2) MEANS OF PAYMENT TO IVB: (Please mark - X - for appropriate item) \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Phương thức thanh toán cho IVB (Đề nghị đánh dấu -X- vào mục thích hợp) \n", font11italic));
				hd.add(new Chunk("( ) (In cash) \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t ( ) In checques \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Bằng tiền mặt  \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  Bằng séc \n", font11italic));
				hd.add(new Chunk("( ) By debiting applicant-s account with IVB \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Ghi nợ tài khoản tại IVB \n", font11italic));				
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.setPaddingTop(-0.1f*CONVERT);
				table5.addCell(cell);
				
				document.add(table5);
				
				PdfPTable table6 =new PdfPTable(2);
				table6.setWidthPercentage(100);
				float[] withs6 = {100f,200f};
				table6.setWidths(withs6);
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("For the amount of \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Số tiền", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table6.addCell(cell);
				
				//Account with IVB
				cell= new PdfPCell();
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				
				PdfPTable table61 =new PdfPTable(1);
				table61 = new PdfPTable(1);	
				table61.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				float[] withstable61 = { 10.0f*CONVERT };
				table61.setWidths(withstable61);				
				table61.setWidthPercentage(80);
				
				cell3 = new PdfPCell();
				hd = new Paragraph();
				hd = new Paragraph( sotientt +" VND" , font11bi);				
				hd.setAlignment(Element.ALIGN_CENTER);
				cell3.addElement(hd);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_CENTER);
				cell3.setPaddingBottom(0.2f*CONVERT);
				cell3.setBorderWidthTop(1);
				cell3.setBorderWidthRight(1);
				cell3.setBorderWidthBottom(1);
				cell3.setBorderWidthLeft(1);
				
				table61.addCell(cell3);
				
				cell.addElement(table61);
				
				table6.addCell(cell);	
				
				cell= new PdfPCell();	
				hd = new Paragraph();
				hd.add(new Chunk("\t \t \t \t In word: \n", font11bold));
				hd.add(new Chunk("\t \t \t \t Bằng chữ", font11italic));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table6.addCell(cell);
				
				cell= new PdfPCell();	
				hd = new Paragraph(tienbangchu+"./" , font11bi);
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				cell.setBorder(0);
				
				table6.addCell(cell);
				
				document.add(table6);
				
				
				PdfPTable table7 = new PdfPTable(1);	
				table7.setHorizontalAlignment(Element.ALIGN_CENTER);
				table7.setWidthPercentage(100);
				
				cell = new PdfPCell();
				hd = new Paragraph("Hải Phòng, ngày   /   /" , font11);
				hd.setAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(0);
				cell.addElement(hd);
				cell.setPaddingTop(-0.1f*CONVERT);
				table7.addCell(cell);
				
				document.add(table7);
			}
			
			document.close(); 
		}
		catch(Exception e)
		{
			System.out.println("Exception In Phieu Chi: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private boolean CreateUyNhiemChi(OutputStream out, IErpUynhiemchi obj)  throws Exception
	{
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "";

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100005), '') as congty, " +
							"	isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, " +
							" 	isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, isnull(ncc.sotknganhang,'') as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, " +
							" 	isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, isnull(nv.sotaikhoan,'') as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, " +
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
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccTen");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
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
			return false;
		}
		return true;
		
			
	}


	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return ngay + "/" + thang + "/" + nam;
	}
	
    // điều chuyển tiền
	private void CreateDieuChuyenTien(Document document, ServletOutputStream outstream, IErpUynhiemchi pxkBean, String hdId){

		try{
			dbutils db = new dbutils();
			document.setMargins(0.0f*CONVERT, 1.0f*CONVERT, 4.8f*CONVERT, 2.0f*CONVERT);
						
			
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 0.5f*CONVERT, 0.5f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
				
				
			PdfPTable tableheader_ct =new PdfPTable(1);
			tableheader_ct.setWidthPercentage(100);			
			
			//header
			createHeaderDCT(document, bf);
			// body
			createBodyDCT(document, bf);
			// footer
			createFooterDCT(document, bf);
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	private void createBodyDCT(Document document, BaseFont bf) {
		try{
			Font font = new Font(bf,8,Font.BOLD);
			Font font_i = new Font(bf,8, Font.ITALIC);
			Font font_n = new Font(bf,8, Font.NORMAL);
			Font font_bold = new Font(bf,8, Font.BOLD);
			
			PdfPTable table =new PdfPTable(1);
			table.setWidthPercentage(100);			
			PdfPCell cell = new PdfPCell();	
			Chunk c = new Chunk("Tên Đơn vị mua/ chuyển tiền/ ", font);
			
			Paragraph hd = new Paragraph( c);
			c = new Chunk ("Buyer/Remitter:", font_i);
			hd.add(c);
			
			c = new Chunk("                   Công ty cổ phần Traphaco ",font);
			hd.add(c);
			cell.addElement(hd);
			
			hd = new Paragraph();
			c = new Chunk ("Địa chỉ/", font_n);
			c.setTextRise(6f);
			hd.add(c);
			
			c = new Chunk("Addresss: ",font_i);
			c.setTextRise(6f);
			hd.add(c);
			
			c = new Chunk("              75 Yên Ninh - Ba Đình - Hà Nội                 ",font_n);
			c.setTextRise(6f);
			hd.add(c);
			
			c = new Chunk("                                                         Điện thoại",font_n);
			c.setTextRise(6f);
			hd.add(c);
			
			c = new Chunk("         0436 815 095",font_n);
			c.setTextRise(6f);
			hd.add(c);
			
			cell.setBorder(1);
			cell.addElement(hd);
			
			cell.setBorderWidthLeft((float) 0.5);
			cell.setBorderWidthRight((float) 0.5);
			//cell.setBorderWidthTop(1);*/
			
			cell.setPaddingBottom(0);
			table.addCell(cell);
			
			
			
			// cell 2
			hd = new Paragraph("I/ ĐỀ NGHỊ MUA NGOẠI TỆ/ FX Application",font_bold);
			c = new Chunk("I/ ĐỀ NGHỊ MUA NGOẠI TỆ/ ",font_bold);
			hd.add(c);
			
			c = new Chunk("FX Application",font_i);
			hd.add(c);
			
			cell = new PdfPCell();
			
			cell.addElement(hd);
			
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			// cell 3
			cell = new PdfPCell();
			hd = new Paragraph();
			c = new Chunk("Số ngoại tệ xin mua bằng số/ ",font_n);
			c.setTextRise(5f);
			hd.add(c);
			
			c = new Chunk("Amount:",font_i);
			c.setTextRise(5f);
			hd.add(c);
			
			cell.addElement(hd);
			hd = new Paragraph();
			c = new Chunk("Bằng chũ/ ",font_n);
			c.setTextRise(8f);
			hd.add(c);
			
			c = new Chunk("in word:",font_i);
			c.setTextRise(8f);
			hd.add(c);
			
			cell.addElement(hd);
			
			hd = new Paragraph();
			c = new Chunk("Mục đích sử dụng ngoại tệ/ ",font_n);
			c.setTextRise(8f);
			hd.add(c);
			
			c = new Chunk("Purpose:",font_i);
			c.setTextRise(8f);
			hd.add(c);
			
			cell.addElement(hd);
			cell.setPaddingTop(0);
			cell.setPaddingBottom(0);
			hd.setSpacingAfter(2);
			
			
			
			table.addCell(cell);
			
			// cell 4
			cell = new PdfPCell();
			hd = new Paragraph("II/ LỆNH CHI KIỂM YÊU CẦU CHUYỂN TIỀN NGOẠI TỆ/PAYMENT/REMITTANCE ORDER IN FOREIGN CURRENCY ",font_bold);
			hd.setSpacingAfter(2);
			cell.addElement(hd);
			table.addCell(cell);
			table.setSpacingAfter(0);
			
			document.add(table);
			
			// Kết thúc table 1
			table = new PdfPTable(4);
			table.setWidths(new float[]{(float)0.5,(float)3.5,3,(float)3.5});
			table.setWidthPercentage(100);	
			// cell 1
			
			cell = new PdfPCell(new Paragraph("32A",font_n));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			// cell 2
			hd = new Paragraph();
			Chunk chunk =new Chunk("Ngày giá trị/",font_bold);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			chunk =new Chunk(" value date",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			PdfPTable t = new PdfPTable(2);
			PdfPCell cel = new PdfPCell(new Paragraph("Ngày giá trị/",font_bold ));
			
			cell = new PdfPCell();	
			cell.addElement(hd);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);	
			table.addCell(cell);
			
			// cell 3
			hd = new Paragraph();			
			chunk =new Chunk("Loại, Số tiền/ ",font_bold);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			chunk =new Chunk("Amount in figures:",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			cell = new PdfPCell();
			
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// cell 4
			hd = new Paragraph("52,600.00 USD",font_n);
			cell = new PdfPCell();
			
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// cell 2.1
			cell = new PdfPCell();
		
			table.addCell(cell);
			
			// cell 2.2
			cell = new PdfPCell();
	
			table.addCell(cell);
			
			
			
			// cell 2.3
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			hd = new Paragraph();			
			chunk =new Chunk("Bằng chữ/ ",font_bold);
			chunk.setTextRise(6f);
			hd.add(chunk);
			
			chunk =new Chunk("Amt in word",font_i);
			chunk.setTextRise(6f);
			hd.add(chunk);
			
			table1.setWidths(new float[]{5,10});
			cell = new PdfPCell();
			cell.addElement(hd);
			cell.setBorderWidthBottom(0);
			cell.setBorderWidthLeft(0);
			cell.setBorderWidthTop(0);
			cell.setPaddingBottom(1);
			cell.setBorderWidthRight((float) 0.2);
			table1.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(" Nam hai ngan sau tram do la My",font_n));
			cell.setBorder(0);
			table1.addCell(cell);
			
			cell = new PdfPCell();
			cell.setColspan(2);
			cell.setPaddingBottom(1);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.addElement(table1);
			table.addCell(cell);
			
			document.add(table);
			
			
			// Table2 56A
			table = new PdfPTable(4);
			table.setWidths(new float[]{(float)0.5,2,6,2});
			table.setWidthPercentage(100);	
			// cell 1
			/*hd = new Paragraph("56A",font_n);*/
			cell = new PdfPCell(new Paragraph("56A",font_n));
			
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			// cell 2
			t = new PdfPTable(1);
			cel = new PdfPCell(new Paragraph("NH trung gian",font_n));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			
			cel = new PdfPCell(new Paragraph("Intermediary Bank",font_i ));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			//hd = new Paragraph("NH trung gian \n Intermediary Bank",font_n);
			cell = new PdfPCell();
			 cell.setRowspan(2);
			 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.addElement(t);
			
			//cell.setBorderWidthRight(1);
			//cell.setBorderWidthTop(1);
			
			table.addCell(cell);
			
			// cell 3
			PdfPTable tLon = new PdfPTable(2);
			tLon.setWidthPercentage(100);
			tLon.setWidths(new float[]{(float)3,2});
			
			//nội dung trong cell 1
	
			hd = new Paragraph();
			chunk = new Chunk("Tên NH/ ",font_n);
			chunk.setTextRise(6f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Bank name",font_i);
			chunk.setTextRise(6f);
			hd.add(chunk);
			
			cel = new PdfPCell();
			cel.addElement(hd);
			cel.setHorizontalAlignment(Element.ALIGN_LEFT);
			cel.setBorder(0);
			cel.setPaddingBottom(0);
			cel.setPaddingTop(0);
			tLon.addCell(cel);
			
			//cell 2
			cel = new PdfPCell(new Paragraph("",font_n));
			cel.setHorizontalAlignment(Element.ALIGN_LEFT);
			cel.setBorder(0);
			tLon.addCell(cel);
			
			//cell 3
			hd = new Paragraph();
			chunk = new Chunk("Địa chỉ/ ",font_n);
			chunk.setTextRise(9f);
			hd.add(chunk);
				
			chunk = new Chunk("Address:",font_i);
			chunk.setTextRise(9f);
			hd.add(chunk);
			
			cel = new PdfPCell();
			cel.addElement(hd);
			cel.setHorizontalAlignment(Element.ALIGN_LEFT);
			cel.setBorder(0);
			cel.setPaddingBottom(0);
			cel.setPaddingTop(0);
			tLon.addCell(cel);
			
			//cell 4
			cel = new PdfPCell(new Paragraph("",font_n));
			cel.setHorizontalAlignment(Element.ALIGN_LEFT);
			cel.setBorder(0);
			tLon.addCell(cel);

			cell = new PdfPCell();
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.addElement(tLon);
			
			table.addCell(cell);
			
			// cell 4
			hd = new Paragraph();
			chunk = new Chunk("Mã NH/ ",font_n);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Bank code:",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			cell = new PdfPCell();		
			cell.addElement(hd);
			cell.setBorderWidthRight(1);	
			table.addCell(cell);
			
			// cell 4.1
			cell = new PdfPCell();
			table.addCell(cell);
			
			//  cell 57A
			//hd = new Paragraph("57A",font_n);
			cell = new PdfPCell(new Paragraph("57A",font_n));
			cell.setRowspan(2);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			// NH thụ hưởng
			t = new PdfPTable(1);
			cel = new PdfPCell(new Paragraph("NH người hưởng",font_n));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			
			cel = new PdfPCell(new Paragraph("Beneficiary's Bank",font_i ));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			
			cell = new PdfPCell();
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.addElement(t);
			
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setRowspan(2);
			cell.addElement(t);
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setRowspan(2);
			cell.addElement(tLon);
			table.addCell(cell);
			
			//Mã NH/bank code
			hd = new Paragraph();
			chunk = new Chunk("Mã NH/ ",font_n);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Bank code:",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			cell = new PdfPCell();
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// cell 4.1
			cell = new PdfPCell();
			
			table.addCell(cell);
			
			// 59 
			//  cell 
			//hd = new Paragraph("59",font_n);
			cell = new PdfPCell(new Paragraph("59",font_n));
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			// NH thụ hưởng
			t = new PdfPTable(1);
			cel = new PdfPCell(new Paragraph("Người hưởng",font_n));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			
			cel = new PdfPCell(new Paragraph("Beneficiary",font_i ));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			//hd = new Paragraph("NH trung gian \n Intermediary Bank",font_n);
			cell = new PdfPCell();
			 cell.setRowspan(2);
			 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.addElement(t);
	
			/*cell.setBorderWidthRight(1);
			cell.setBorderWidthLeft(1);*/
			table.addCell(cell);
			
			
		//	hd = new Paragraph("Tên / Name: \n Địa chỉ/ Address:",font_n);
			cell = new PdfPCell();
			 cell.setRowspan(2);
			cell.addElement(tLon);
			
			//cell.setBorderWidthRight(1);
			//cell.setBorderWidthTop(1);
			
			table.addCell(cell);
			
			//Mã NH/bank code
			//hd = new Paragraph("Số TK hoặc số IBAN",font_n);
			cell = new PdfPCell(new Paragraph("Số TK hoặc số IBAN",font_n));
			cell.setPaddingBottom(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			//cell.addElement(hd);
			
			//cell.setBorderWidthRight(1);
			//cell.setBorderWidthTop(1);
			
			table.addCell(cell);
			
			// cell 4.1
			cell = new PdfPCell(new Paragraph("411"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//70
			
			cell = new PdfPCell(new Paragraph("70",font_n));
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			// NH thụ hưởng
			
			cell = new PdfPCell(new Paragraph("Nội dung", font_n));	
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//Mã NH/bank code
			hd = new Paragraph("",font_n);
			cell = new PdfPCell();
			cell.setRowspan(2);
			cell.setColspan(2);
			cell.addElement(hd);
			
			
			table.addCell(cell);
			
			// cell 4.1
			cell = new PdfPCell(new Paragraph("411"));
			table.addCell(cell);
			document.add(table);
			
			// table mới 71 trở đi.
			table = new PdfPTable(5);
			table.setWidths(new float[]{(float)0.5,(float)2.0,4,1,3});
			table.setWidthPercentage(100);	
			// cell 1
			//hd = new Paragraph("71A",font_n);
			cell = new PdfPCell(new Paragraph("71A",font_n));
			//cell.addElement(hd);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			// cell 2
			// NH thụ hưởng
			t = new PdfPTable(1);
			cel = new PdfPCell(new Paragraph("Phí",font_n));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			
			cel = new PdfPCell(new Paragraph("change",font_i ));
			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel.setBorder(0);
			t.addCell(cel);
			
			hd = new Paragraph("Phí \n change",font_n);
			cell = new PdfPCell();
			cell.addElement(t);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			
			table.addCell(cell);
			
			// cell 3 gộp với 4
			hd = new Paragraph("() OUR    Phí người chuyển chịu/ All charges ' to be borne by Remitter" +
					"/n ( X ) SHA    Phí chia sẻ 2 bên/ Charge're to be shared" +
					"/n BEN     Phí người hưởng chịu/   All charge're to be borne by Ben ",font_n);
			cell = new PdfPCell();
			cell.setColspan(2);
			cell.addElement(hd);
			/*cell.setBorderWidthLeft(1);
			cell.setBorderWidthRight(1);*/
			//cell.setBorderWidthTop(1);
			
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			// cell 5
			hd = new Paragraph(" Nộp TM/ by cash \n Ghi nợ TK / Debit A/C ",font_n);
			cell = new PdfPCell();
			hd.setSpacingAfter(5);
			
			cell.setColspan(2);
			cell.addElement(hd);
			//cell.setBorderWidthLeft(1);
		//	cell.setBorderWidthRight(1);
		//	cell.setBorderWidthTop(1);
			
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			// dòng thanh toán
			// cell 1,2,3
			hd = new Paragraph("       Hình thức thanh toán/Paid by ",font_n);
			hd.setSpacingAfter(5);
			
			cell = new PdfPCell();
			cell.setColspan(3);
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// cell 4
			hd = new Paragraph("Tỷ giá/Ex ",font_n);
			cell = new PdfPCell();
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// cell 5
			hd = new Paragraph("Số tiền tương đương/ Equal to",font_n);
			cell = new PdfPCell();
			cell.addElement(hd);
			//cell.setBorderWidthLeft(1);
			//cell.setBorderWidthRight(1);
			//cell.setBorderWidthTop(1);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			// cell ngoại tệ 3 dòng
			// cell 1,2,3
			hd = new Paragraph("Bằng ngoại tệ/ In foreign curency: " +
					"\n Ghi nợ TK ngoại tệ / Debit Foreign Currency AC:" +
					"\n Thanh toán tiền ngoại tệ bằng VND/ Forex Payment in VND" +
					"\n Ghi nợ TK VNĐ của chúng tôi/ Debit our VND AC:" +
					"\n Ghi nợ TK khác/ Debit other AC ",font_n);
			hd = new Paragraph();
			//dòng 1
			chunk = new Chunk(" Bằng ngoại tệ/ ",font_n);
			chunk.setUnderline(0.2f, 3f);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("In foreign curency:",font_i);
			chunk.setTextRise(5f);
			chunk.setUnderline(0.2f, 3f);
			hd.add(chunk);
			
			//dòng 2
			chunk = new Chunk("\n           Ghi nợ TK ngoại tệ / ",font_n);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Debit Foreign Currency AC:",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			//dòng 3
			chunk = new Chunk("\n Ghi nợ TK VNĐ của chúng tôi/ ",font_n);
			chunk.setUnderline(0.2f, 3f);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Forex Payment in VND",font_i);
			chunk.setTextRise(5f);
			chunk.setUnderline(0.2f, 3f);
			hd.add(chunk);
			
			//dòng 4
			chunk = new Chunk("\n           Ghi nợ TK VNĐ của chúng tôi/ ",font_n);
			
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Debit our VND AC:",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			//dòng 5
			chunk = new Chunk("\n       Ghi nợ TK khác/ ",font_i);
			
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			
			chunk = new Chunk("Debit other AC",font_i);
			chunk.setTextRise(5f);
			hd.add(chunk);
			
			cell = new PdfPCell();
			cell.setColspan(3);
			cell.addElement(hd);
			cell.setPaddingBottom(5);
			
			table.addCell(cell);
			
			// cell 4
			hd = new Paragraph();
			cell = new PdfPCell();
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// cell 5
			hd = new Paragraph();
			cell = new PdfPCell();
			cell.addElement(hd);
			
			table.addCell(cell);
			
			// Dòng ghi nhiều nhất
			
			cell = new PdfPCell();
			cell.setColspan(5);
			Paragraph p = new Paragraph("III/ CAM KẾT CỦA ĐƠN VỊ/ The Remitter hereby undertakes to:",font_bold);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			cell.addElement(p);
			
			p = new Paragraph("        Chấp nhận tỷ giá ngoại tệ ngân hàng xác nhận bán/" +
					"We agree the offer of Vietinbank........................ Branch at effect date.\n",font_n);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			cell.addElement(p);
			
			p = new Paragraph("        Tuân thủ mọi qui định hiện hành về quản lý ngoại hối của nước CHXHCN Việt Nam" +
					", sử dung ngoại tệ đúng mục đích, chịu hoàn toàn trách nhiệm trước pháp luật về việc" +
					" sử dụng số ngoại tệ xin mua và về tính trung thực của các chứng từ.\n", font_n);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			cell.addElement(p);
			
			p = new Paragraph("        Hoàn trả bản sao y bản gốc chứng từ, gồm: hóa đơn thương mại, tờ khai hải quan," +
					"vận đơn và các chứng từ khác có liên quan đến nhập khẩu hàng hóa và dịch vụ, chậm" +
					" nhất vào ngày:   /    /     (trường hợp yêu cầu thanh toán ứng với trước hợp đồng ngoại thương)\n", font_n);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			cell.addElement(p);
			
			p = new Paragraph("        Chịu mọi rủi ro( nếu có) và bồi thường thiệt hại xảy ra NHTMCPCTVN khi yêu cầu NH" +
					" thực hiện các giao dịch thanh toán với các nước cấm vận hoặc nằm trong danh sách OFAC," +
					"FATF,..../ Bear risk( if any) and compensate for damages to Vietinbank wh\n", font_n);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			cell.addElement(p);
			
		//	cell.addElement(hd);
			
			PdfPTable tabledv =new PdfPTable(3);

			PdfPCell ce = new PdfPCell(new Paragraph("Đơn vị mua/ chuyển tiền/ Buyer/ Remitter", font));
			ce.setColspan(3);
			ce.setBorder(0);
			ce.setHorizontalAlignment(Element.ALIGN_CENTER);
			tabledv.addCell(ce);
			
			ce = new PdfPCell(new Paragraph("Kế toán trưởng/ Chief Accountant", font));
			ce.setHorizontalAlignment(Element.ALIGN_CENTER);
			ce.setBorder(0);
			tabledv.addCell(ce);
			
			
			ce = new PdfPCell(new Paragraph("   ", font));
			ce.setHorizontalAlignment(Element.ALIGN_CENTER);
			ce.setBorder(0);
			tabledv.addCell(ce);
			
			ce = new PdfPCell(new Paragraph("Chủ tài khoản/Account holder", font));
			ce.setHorizontalAlignment(Element.ALIGN_CENTER);
			ce.setBorder(0);
			tabledv.addCell(ce);
			// set thêm Đon vị chuyển tiền
		/*	hd = new Paragraph("Đơn vị mua/ chuyển tiền/ Buyer/ Remitter \n" +
					"                Kế toán trưởng/ Chief Accountant                    " +
					"                                                   Chủ tài khoản/Account holder ",font);
			*/
			hd = new Paragraph();
			hd.add(tabledv);
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.setSpacingBefore(10);
			hd.setSpacingAfter(30);
			cell.addElement(hd);
			
			/*cell.setBorderWidthLeft(1);
			cell.setBorderWidthRight(1);*/
			//cell.setBorderWidthTop(1);
			table.addCell(cell);
			
			// Phần dành cho NH
			cell = new PdfPCell();
			cell.setColspan(5);
			hd = new Paragraph(" Phần dành cho Ngân hàng/ for Bank use only",font);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);

			// set thêm Đon vị chuyển tiền
			hd = new Paragraph("Ngày hạch toán/ Acounting date  ...../..../.....\n" +
					" Số tiền duyệt bán và chuyển/ Amount approved ................................." +
					".............................................................................." +
					".........................................................................\n" +
					" Tỷ giá bán/ Selling rate...................................................." +
					"............................................................................." +
					"............................................................................." +
					"..................\n" +
					" Số tiền duyệt bán và chuyển bằng chữ/ Amount approved in word.............." +
					"............................................................................" +
					"..................................................................\n............" +
					"............................................................................" +
					"............................................................................" +
					"............................................................................" +
					"......................\n"+
					"Số tiền tương đương/ Equal to: ............................................." +
					"............................................................................" +
					"............................................................................" +
					"...............",font_n);
			
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingAfter(5);
			cell.addElement(hd);
			
			/*cell.setBorderWidthLeft(1);
			cell.setBorderWidthRight(1);*/
			//cell.setBorderWidthTop(1);
			table.addCell(cell);
			table.setSpacingAfter(10);
			document.add(table);
			
			
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
	}
	private void createFooterDCT(Document document, BaseFont bf) {
		// TODO Auto-generated method stub
		try{
			float CONVERT = 28.346457f; // = 1cm
			float[] size = {4*CONVERT , 1*CONVERT, 4*CONVERT, 1*CONVERT, 4*CONVERT, 1*CONVERT, 4*CONVERT, 1*CONVERT, 3*CONVERT };
			
			
			PdfPTable table =new PdfPTable(9);
			table.setWidths(size);
			PdfPCell c = new PdfPCell(new Paragraph("CB P.TTTM",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("LĐ P.TTTM",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Giao dịch viên",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Kiểm soát viên",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Giám đốc",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
	
			
			c = new PdfPCell(new Paragraph("TF Staff",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Manager of TF",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Teller",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Supervisor",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph(" ",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			c = new PdfPCell(new Paragraph("Director",new Font(bf, 10, Font.BOLD)));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBorder(0);
			table.addCell(c);
			
			document.add(table);
			
			
			
			/*hd = new Paragraph( "  TF Staff                   " +
					"      Manager of TF              Teller" +
					"                        Supervisor                           " +
					"  Director", new Font(bf, 10, Font.BOLD));
			hd.setAlignment(Element.ALIGN_JUSTIFIED_ALL);*/
			
			//document.add(hd);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void createHeaderDCT(Document document, BaseFont bf) {
		try{
		
		Paragraph hd = new Paragraph( "GIẤY ĐỀ NGHỊ MUA NGOẠI TỆ GIAO NGAY KIÊM LỆNH CHI\n " +
				" KIÊM YÊU CẦU CHUYỂN TIỀN NGOẠI TỆ", new Font(bf, 10, Font.BOLD));
		hd.setAlignment(Element.ALIGN_CENTER);
		
		Font font = new Font(bf,8,Font.BOLD);
		Font font_i = new Font(bf,8,Font.ITALIC);
		document.add(hd);
		
		hd = new Paragraph( "SPOT FX APPLICATION & PAYMENT/ REMITTANCE ORDER IN FOREIGN CURRENCY", new Font(bf, 9, Font.ITALIC));
		hd.setAlignment(Element.ALIGN_CENTER);
		
		document.add(hd);
		Font font_n = new Font(bf,8,Font.NORMAL);
		
		
		hd = new Paragraph("         Số/No                           Ngày/date                    Liên/copy                              ", font_n);
		hd.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		Chunk ch = new Chunk("         Số/",font_n);
		hd.add(ch);
		
		ch = new Chunk("No",font_i);
		hd.add(ch);
		
		ch = new Chunk("                           Ngày/",font_n);
		hd.add(ch);
		
		ch = new Chunk("date                    ",font_i);
		hd.add(ch);
		
		ch = new Chunk("Liên/",font_n);
		hd.add(ch);
		
		ch = new Chunk("copy                              ",font_i);
		hd.add(ch);
		
		Chunk c = new Chunk("MD04", font);
		hd.add(c);
		document.add(hd);
		
		hd = new Paragraph( "Kính gửi/To", new Font(bf, 9, Font.BOLDITALIC));
		
		hd.setSpacingBefore(5);
		hd.setSpacingAfter(10);
		
		document.add(hd);
		
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
	}

	private void CreateUyNhiemChiBPPdf(Document document, ServletOutputStream outstream, IErpUynhiemchi pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			document.setMargins(0.0f*CONVERT, 1.0f*CONVERT, 4.8f*CONVERT, 2.0f*CONVERT);
			
			
			String query = 
			"SELECT UPPER(dvth.TEN) TENBOPHAN FROM ERP_THANHTOANHOADON tthd INNER JOIN erp_donvithuchien dvth on tthd.DVTH_FK = dvth.PK_SEQ "+
			"WHERE  tthd.pk_seq = "+hdId+"";
			
			String TENBP ="";
			ResultSet rsbp = db.get(query);
			if(rsbp!=null){
				while(rsbp.next()){
					TENBP = rsbp.getString("TENBOPHAN");
				}	
			}
			rsbp.close();
						
			
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			
			query = 
				" SELECT TEN, DIACHI  FROM ERP_CONGTY  WHERE PK_SEQ = 100005";
				
				String TENCT = "";
				String DIACHICT = "";
				ResultSet rsbp_ct = db.get(query);
				if(rsbp_ct!=null){
					while(rsbp_ct.next()){
						TENCT = rsbp_ct.getString("TEN");
						DIACHICT = rsbp_ct.getString("DIACHI");
					}	
				}
				rsbp_ct.close();
				
				
			PdfPTable tableheader_ct =new PdfPTable(1);
			tableheader_ct.setWidthPercentage(100);			
			
			//TÊN CTY
			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Paragraph hd = new Paragraph( TENCT, new Font(bf, 10, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.7f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader_ct.addCell(cell);
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			//ĐỊA CHỈ CTY
			hd = new Paragraph(DIACHICT, new Font(bf, 10, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.7f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader_ct.addCell(cell);
			
			document.add(tableheader_ct);
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			hd = new Paragraph("CHI PHÍ CÔNG TÁC PHÍ " + TENBP, new Font(bf, 14, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(2.0f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			hd = new Paragraph("Số chứng từ: " + hdId, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			document.add(tableheader);
			
			String[] th = new String[]{ "STT", "HỌ VÀ TÊN", "SỐ TÀI KHOẢN", "TỔNG SỐ TIỀN","NH", "GHI CHÚ"};			
			
			PdfPTable nhanvien = new PdfPTable(th.length);
			nhanvien.setSpacingBefore(0.5f*CONVERT);
			nhanvien.setWidthPercentage(100);
			nhanvien.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 2.5f*CONVERT,13.0f*CONVERT, 10.0f*CONVERT, 8.0f*CONVERT,8.0f*CONVERT ,8.0f*CONVERT};
			nhanvien.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();
			
			for (int j = 0; j < th.length; j++)
			{
				System.out.println(th[j]);
				cells = new PdfPCell(new Paragraph(th[j], new Font(bf, 10, Font.BOLD)));
				if (j <= 1 ){ //STT
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				}
				else{							
						if(j == 2 )//ĐƠN VỊ TÍNH
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}								
						else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
				}
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);				
			/*	cells.setBorderWidth(1);*/
				cells.setFixedHeight(0.8f * CONVERT);	
				nhanvien.addCell(cells);
			}
			
			query =
				" select nv.MA as MADOITUONG,nv.TEN, nv.SOTAIKHOAN, tt.SOTIENTT, UPPER(dvth.TEN) TENBOPHAN, nh.MA MANGANHANG \n"+
				" from \n"+ 
				" 	ERP_THANHTOANHOADON_HOADONBOPHAN tt \n"+   
				" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ \n"+  
				" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tt.NHANVIEN_FK \n"+
				"	INNER JOIN erp_donvithuchien dvth on tthd.DVTH_FK = dvth.PK_SEQ "+
				"   INNER JOIN ERP_NGANHANG  nh on tthd.nganhang_fk = nh.PK_sEQ "+
				" WHERE TT.LOAIHD = 6 and tthd.pk_seq = "+hdId+"";
			
			System.out.println(query);
			
			String HOTENNV="";
			String SOTAIKHOAN="";
			int stt =0;
			double SOTIENTT =0;
			
			double SUMSOTIENTT =0;
			
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					stt++;
					HOTENNV = rs.getString("TEN");
					SOTAIKHOAN = rs.getString("SOTAIKHOAN");
					SOTIENTT = rs.getDouble("SOTIENTT");
					
					SUMSOTIENTT += Math.round(SOTIENTT);
					
					String[] arr = new String[] { Integer.toString(stt), HOTENNV , SOTAIKHOAN ,DinhDangTraphacoERP(formatter1.format(SOTIENTT)), rs.getString("MANGANHANG"),"" };
					
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j <= 1 ){ //STT
							if(j == 0 )
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							if(j == 1 )
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								if(j == 2 || j==4)//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								}
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						/*cells.setBorderWidth(1);*/
						cells.setPaddingBottom(0.2f*CONVERT);
						nhanvien.addCell(cells);
					}
				}
			}
			rs.close();
			
			//DÒNG TỔNG CỘNG
			String[] arr = new String[] { "", "Tổng cộng" , "" ,DinhDangTraphacoERP(formatter1.format(SUMSOTIENTT)), "","" };
			
			for (int j = 0; j < th.length; j++)
			{
				System.out.println(arr[j]);
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.BOLD)));
				if (j <= 1 ){ //STT
					if(j == 0 )
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(j == 1 )
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					//cells.setPaddingLeft(-0.5f*CONVERT);
				}
				else{							
						if(j == 2 || j==4)//ĐƠN VỊ TÍNH
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
				}
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*cells.setBorderWidth(1);*/
				//cells.setFixedHeight(0.8f * CONVERT);	
				nhanvien.addCell(cells);
			}
			
			document.add(nhanvien);
			
			PdfPTable tablefooter =new PdfPTable(2);
			tablefooter.setSpacingBefore(1.0f*CONVERT);	
			float[] withs1 = {300f, 500f};
			tablefooter.setWidths(withs1);
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			hd = new Paragraph("Ngày ...... tháng ........ năm ......" , new Font(bf, 12, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(2);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablefooter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph("TỔNG GIÁM ĐỐC" , new Font(bf, 12, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);			
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablefooter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph("KẾ TOÁN TRƯỞNG" , new Font(bf, 12, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablefooter.addCell(cell);			
			document.add(tablefooter);
			
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
	
	public class HeaderAndFooter extends PdfPageEventHelper {

	    private String name = "";


	    protected Phrase footer;
	    protected Phrase header;
	    BaseFont bf;

	    /*
	     * Font for header and footer part.
	     *//*
	    private static Font headerFont = new Font(bf, 10, Font.BOLD);

	    private static Font footerFont = new Font(Font.TIMES_ROMAN, 9,
	            Font.BOLD,Color.blue);*/


	    /*
	     * constructor
	     */
	    public HeaderAndFooter(String name) {
	        super();

	        this.name = name;


	        header = new Phrase("***** Header *****");
	        footer = new Phrase("**** Footer ****");
	    }


	    @Override
	    public void onEndPage(PdfWriter writer, Document document) {

	        PdfContentByte cb = writer.getDirectContent();
	        
	        try {
				bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			} catch (DocumentException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	        
	        //header content
	        String headerContent = "Số chứng từ: " +name;

	        //header content
	        String footerContent = headerContent;
	        /*
	         * Header
	         */ 
	        if(writer.getPageNumber()>1){
		        ColumnText.showTextAligned(cb, Element.ALIGN_MIDDLE, new Phrase(headerContent,new Font(bf, 12, Font.NORMAL)), 
		                document.leftMargin() + 12.5f*CONVERT, document.top() + 10, 0);				
		        
	        }

	        /*
	         * Foooter
	         */
	        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase("Trang: "+String.format(" %d ", 
	                writer.getPageNumber()),new Font(bf, 10, Font.BOLD)), 
	                document.right() - 2 , document.bottom() - 20, 0);

	    }

	}
	
	@SuppressWarnings("static-access")
	private void CREATE_UNC_VIETTINBANK_JASPER(ServletOutputStream outstream, HttpServletRequest request, HttpServletResponse response,IErpUynhiemchi tthdBean)
	{
		
		try {
			
			
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			String doituongkhac_fk="";
			String ncc_fk="";
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk="", donvithuchien = "" , pk_seq ="",machungtu="";
			String bophanId="",khachhang_fk="";
			dbutils db = new dbutils();
			String query =  " select tthd.khachhang_fk,tthd.DVTH_FK,tthd.SOTAIKHOAN as SoTaiKhoan, tthd.ncc_fk,isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100000), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT,tthd.sochungtu, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai, \n"+
							" tthd.doituongkhac_fk,isnull(dtk.diachi,'') as diachidt,isnull(dtk.tendoituong,'') tendt,isnull(dtk.sotaikhoan,'') sotaikhoandt, "
							+ "isnull(nhdt.ten,'') as nganhangdt,isnull(cndt.ten,'') as chinhanhdt \n"+
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join erp_doituongkhac dtk on dtk.pk_seq=tthd.DOITUONGKHAC_FK \n" +
							" left join ERP_NGANHANG nhdt on dtk.nganhang_fk = nhdt.pk_seq \n"+  
							" left join ERP_CHINHANH cndt on dtk.chinhanh_fk = cndt.pk_seq \n"+
														
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
				
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
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						 doituongkhac_fk=rs.getString("doituongkhac_fk");
						 ncc_fk=rs.getString("ncc_fk");
						pk_seq = rs.getString("pk_seq");
						machungtu=rs.getString("sochungtu");
						
						khachhang_fk=rs.getString("khachhang_fk");
						bophanId=rs.getString("dvth_fk");
						if(nhanvien_fk != null) {
							
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
							nhanChiNhanh = rs.getString("nvChiNhanh");
							System.out.println("nhan Ten : " +nhanTen+" "+nhanvien_fk);
						} else if(doituongkhac_fk!=null){
							
							nhanTen = rs.getString("tendt");
							nhanDiaChi = rs.getString("diachidt");
							nhanStk = rs.getString("sotaikhoandt");
							nhanNganHang = rs.getString("nganhangdt");
							nhanChiNhanh = rs.getString("chinhanhdt");
							System.out.println("nhan Ten : " +nhanTen+" "+doituongkhac_fk);
						}else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccDiaChi");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
							nhanChiNhanh = rs.getString("nccChiNhanh");
							System.out.println("nhan Ten : " +nhanTen);
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
					
					}
					rs.close();
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			String query1="";
			/*tthdBean.getNhanVienId().trim().length() <= 0 */
			if(bophanId==null && ncc_fk==null && 
					nhanvien_fk ==null
					&& khachhang_fk==null
					&& (doituongkhac_fk == null || doituongkhac_fk.trim().length() == 0)
					) // tra Khac
			{
				query1="select top (1) case when "
						 + " \n  tthd.NHANVIEN_FK is not null then nv.ten "
						 + " \n  when tthd.ncc_Fk is not null then ncc.ten "
						 + " \n  when tthd.khachhang_fk is not null  then kh.ten "
						 + " \n  else dtk.tendoituong end as Ten,"
						 
						 + " \n case when "
						 + " \n tthd.NHANVIEN_FK is not null then nv.diachi "
						 + " \n when tthd.ncc_Fk is not null then ncc.diachi "
						 + " \n when tthd.khachhang_fk is not null  then kh.diachi "
						 + " \n else dtk.diachi end as diachi,"
						 
						 + " \n case when "
						 + " \n tthd.NHANVIEN_FK is not null then nv.sotaikhoan "
						 + " \n when tthd.ncc_Fk is not null then ncc.sotaikhoan "
						 + " \n when tthd.khachhang_fk is not null  then kh.sotaikhoan "
						 + " \n else dtk.sotaikhoan end as sotaikhoan,"
						 
						 + " \n case when "
						 + " \n tthd.NHANVIEN_FK is not null then nvnh.TEN "
						 + " \n when tthd.ncc_Fk is not null then nccnh.ten "
						 + " \n when tthd.khachhang_fk is not null then khnh.ten "
						 + " \n else dtknh.ten end as nhanNganHang, "
						 
						 + " \n case when "
						 + " \n tthd.NHANVIEN_FK is not null then nvcn.TEN "
						 + " \n when tthd.ncc_Fk is not null then ncccn.ten "
						 + " \n when tthd.khachhang_fk is not null then khccn.ten "
						 + " \n else dtkcn.ten end as nhanChiNhanh"
						 
						 + " \n  from ERP_THANHTOANHOADON_PHINGANHANG tthd "
						 + " \n  left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK =nv.pk_Seq "
						 + " \n  left join erp_nhacungcap ncc on tthd.ncc_Fk=ncc.pk_seq "
						 + " \n  left join khachhang kh on tthd.khachhang_fk=kh.pk_seq "
						 + " \n left join erp_doituongkhac dtk on tthd.doituongkhac_fk=dtk.pk_seq "
						 
						 + " \n left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq "
						 + " \n left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  "
						 + " \n left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq "
						 + " \n left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq"
						 
						 + " \n left join ERP_NGANHANG dtknh on dtk.nganhang_fk = dtknh.pk_seq "  
						 + " left join ERP_CHINHANH dtkcn on dtk.chinhanh_fk = dtkcn.pk_seq   "
						 + " left join ERP_NGANHANG khnh on kh.nganhang_fk = khnh.pk_seq  "
						 + " left join ERP_CHINHANH khccn on kh.chinhanh_fk = khccn.pk_seq "
						 
						 + " \n  where tthd.THANHTOANHD_FK='"+tthdBean.getId()+"'";
			
			
			System.out.println("doi tuong khac :"+ query1+ " s "+tthdBean.getDoiTuongChiPhiKhac());
			
			ResultSet rs1 = db.get(query1);
			
				try 
				{
					while(rs1.next())
					{
						
							nhanTen = rs1.getString("Ten")==null?"":rs1.getString("Ten");
							nhanDiaChi = rs1.getString("diachi")==null?"":rs1.getString("diachi");
							nhanStk = rs1.getString("sotaikhoan")==null?"":rs1.getString("sotaikhoan");
							nhanNganHang = rs1.getString("nhanNganHang")==null?"":rs1.getString("nhanNganHang");
							nhanChiNhanh = rs1.getString("nhanChiNhanh")==null?"":rs1.getString("nhanChiNhanh");
						
					}
					rs1.close();
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			
			db.shutDown();
			} 
			
			
			System.out.println("nhan Ten : " +nhanTen);
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HOADONTAICHINH.pdf");
			// gán dũ liệu vao hashmap
			HashMap<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("logo", getServletContext().getInitParameter("pathPDF")+"/LOGO_VIETTINBANK.png"); 
			parameterMap.put("sochungtu", machungtu); 
			parameterMap.put("tennguoitra", "Công ty TNHH Traphaco Hưng Yên"); 
			parameterMap.put("diachitra", "Thôn Bình Lương, xã Tân Quang, huyện Văn Lâm, tỉnh Hưng Yên, Việt Nam"); 
			parameterMap.put("sotaikhoantra", traStk); 
			parameterMap.put("nganhangtra", traNganHang + " - " + traChiNhanh); 
			parameterMap.put("tennguoihuong", nhanTen); 
			parameterMap.put("diachinhan", nhanDiaChi); 
			parameterMap.put("sotaikhoannhan",nhanStk); 
			parameterMap.put("nganhangnhan", nhanNganHang  + "-" + nhanChiNhanh );
			parameterMap.put("sotienbangchu", tienbangchu); 
			parameterMap.put("sotienbangso", formatter.format(sotien) + " VND"); 
			parameterMap.put("noidung",noidungtt); 
			
			
			// in hoa don
			try {
				DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); 
				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font","true"); 
				ServletOutputStream servletOutputStream = response.getOutputStream();
				JRDataSource data= new JREmptyDataSource();
				try
				{		
					response.setContentType("application/pdf");
					InputStream reportStream =  new FileInputStream (getServletContext().getInitParameter("pathjasper") + "\\ErpPhieuUyNhiemChi_ViettinBank.jasper");
					JasperPrint jasperPrint;
					jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, data);
					JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
					servletOutputStream.flush();
					servletOutputStream.close();

				} catch (Exception e)
				{
					// display stack trace in the browser
					StringWriter stringWriter = new StringWriter();
					PrintWriter printWriter = new PrintWriter(stringWriter);
					e.printStackTrace(printWriter);
					response.setContentType("text/plain");
					response.getOutputStream().print(stringWriter.toString());
					e.printStackTrace();
				}
				return;


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception print PDF: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}


	}
	
	@SuppressWarnings("static-access")
	private void CREATE_UNC_ERROR(ServletOutputStream outstream, HttpServletRequest request, HttpServletResponse response,IErpUynhiemchi tthdBean)
	{
		
		try {
			
		
		
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HOADONTAICHINH.pdf");
			// gán dũ liệu vao hashmap
			HashMap<String, Object> parameterMap = new HashMap<String, Object>();
			// in hoa don
			try {
				DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); 
				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font","true"); 
				ServletOutputStream servletOutputStream = response.getOutputStream();
				JRDataSource data= new JREmptyDataSource();
				try
				{		
					response.setContentType("application/pdf");
					InputStream reportStream =  new FileInputStream (getServletContext().getInitParameter("pathjasper") + "\\ErpPhieuUyNhiemChi_Error.jasper");
					JasperPrint jasperPrint;
					jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, data);
					JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
					servletOutputStream.flush();
					servletOutputStream.close();

				} catch (Exception e)
				{
					// display stack trace in the browser
					StringWriter stringWriter = new StringWriter();
					PrintWriter printWriter = new PrintWriter(stringWriter);
					e.printStackTrace(printWriter);
					response.setContentType("text/plain");
					response.getOutputStream().print(stringWriter.toString());
					e.printStackTrace();
				}
				return;


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception print PDF: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}


	}

	
}


