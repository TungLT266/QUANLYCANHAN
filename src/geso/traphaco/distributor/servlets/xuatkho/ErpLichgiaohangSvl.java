package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNppList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpSoxuathangNppList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.servlets.report.ReportAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.CellView;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpLichgiaohangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpLichgiaohangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpSoxuathangNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpSoxuathangNppList();
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.initLICHGIAOHANG("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLichGiaoHang.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    HttpSession session = request.getSession();	    
	    
		IErpSoxuathangNppList obj = new ErpSoxuathangNppList();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();

	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String nvgnId = request.getParameter("nvgnId");
	    if(nvgnId == null)
	    	nvgnId = "";
	    obj.setNvgnId(nvgnId);
	    
	    String kbhId = request.getParameter("kbhId");
	    if(kbhId == null)
	    	kbhId = "";
	    obj.setKbhId(kbhId);
	    
	    // bổ sung khách hàng và kho
	    String khoId = request.getParameter("khohhid");
	    if(khoId == null){
	    	khoId = "";
	    }
	    obj.setKhoId(khoId);
	    
	    String khachhangId = request.getParameter("khachhangId");
	    if(khachhangId == null){
	    	khachhangId = "";
	    }
	    obj.setKhachhangId(khachhangId);
	    
	    if( action.equals("LichGHChung") )
	    {
	    	String condition = "";
			if( tungay.trim().length() > 0 )
			{
				//condition += " and a.NGAYXUATHD >= '" + tungay + "' ";
				condition += " and a.NGAYDONHANG >= '" + tungay + "' ";
			}
			if( denngay.trim().length() > 0 )
			{
				//condition += " and a.NGAYXUATHD <= '" + denngay + "' ";
				condition += " and a.NGAYDONHANG <= '" + denngay + "' ";
			}
			if( obj.getKbhId().trim().length() > 0 )
			{
				//condition += " AND b.pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk = '" + obj.getKbhId() + "' ) ";
				condition += " AND a.kbh_fk = '" + obj.getKbhId() + "' ";
			}
			
			// lọc thêm kho và khách hàng
			if(obj.getKhoId().trim().length() >0)
			{
				condition += " and a.KHO_FK = " + obj.getKhoId();
			}
			
			if(obj.getKhachhangId().trim().length() >0)
			{
				condition += " and a.KHACHHANG_FK=" + obj.getKhachhangId();
			}
			//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
			String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "b", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
			condition += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getUserId() + "' ) ) ) ";
			
			//LỌC THEO NVGN
			if( nvgnId.trim().length() > 0 )
				condition += " and i.pk_seq = '" + nvgnId + "' ";
			else //PHAN QUYEN THEO NHAN VIEN
			{
				int[] quyen = new int[10];
				quyen = util.GetquyenNew("ErpSoxuathangNppSvl", "&loai=1", userId );
				
				//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
				if( quyen[util.HIENTHIALL] == 0 )
					condition += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "i", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
				else //Nếu tích vào nút “hiển thị tất cả” trong bộ phân quyền thì phần “chọn nhân viên cung ứng” trong “lịch giao hàng” sẽ ứng theo địa bàn (hiển thị chỗ này sẽ show ra những cung ứng có cùng địa bàn với người đăng nhập, VD: 1,3,5)
				{
					condition += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN_ALL", "i", "pk_seq", obj.getLoainhanvien(), userId );
					//condition += " and a.pk_seq in ( select nvgn_fk from NHANVIEN where nvgn_fk is not null " + 
					//								" and pk_seq in ( select nhanvien_fk from nhanvien_diaban where diaban_fk in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + userId + "' ) ) ) ";
				}
			}
			
	    	/*String query = "select 0 as sort, isnull(i.TEN, '') as nvgn, f.machungtu, a.SOHOADON, a.NGAYXUATHD, kho.TEN as kho, "+
	    			"isnull(b.maFAST, c.maFAST) as maFAST, a.TENXUATHD, isnull(b.DIACHI, c.diachi) as diachi, f.ngaydonhang as ngaylap, "+
	    			"isnull(g.NgayYeuCau, '') as ngaygiaohang, a.tongtienavat as thanhtien, j.ten as congty "+ 
	    			"from ERP_HOADONNPP a "+
	    			"left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+ 
	    			"left join ERP_HOADONNPP_DDH d on a.PK_SEQ = d.HOADONNPP_FK "+
	    			"left join ERP_SOXUATHANGNPP_DDH e on a.pk_seq = e.hoadon_fk "+
	    			"left join ERP_DONDATHANGNPP f on d.DDH_FK = f.PK_SEQ "+
	    			"left join ERP_SOXUATHANGNPP g on e.soxuathang_fk = g.PK_SEQ "+
	    			"left join NHANVIENGIAONHAN i on g.NVGN_FK = i.PK_SEQ "+
	    			"left join NHAPHANPHOI j on j.pk_seq = a.NPP_FK "+
	    			"left join KHO on f.Kho_FK = kho.PK_SEQ "+
	    			"where a.pk_seq > 0 " + condition + " and a.trangthai <> 3  and i.TEN is not null "+
	    			"union "+
	    			"select 1 as sort, isnull(i.TEN, '') as nvgn, f.machungtu, a.SOHOADON, a.NGAYXUATHD, kho.TEN as kho, "+
	    			"isnull(b.maFAST, c.maFAST) as maFAST, a.TENXUATHD, isnull(b.DIACHI, c.diachi) as diachi, f.ngaydonhang as ngaylap, "+
	    			"isnull(g.NgayYeuCau, '') as ngaygiaohang, a.tongtienavat as thanhtien, j.ten as congty "+ 
	    			"from ERP_HOADONNPP a "+
	    			"left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+ 
	    			"left join ERP_HOADONNPP_DDH d on a.PK_SEQ = d.HOADONNPP_FK "+
	    			"left join ERP_SOXUATHANGNPP_DDH e on a.pk_seq = e.hoadon_fk "+
	    			"left join ERP_DONDATHANGNPP f on d.DDH_FK = f.PK_SEQ "+
	    			"left join ERP_SOXUATHANGNPP g on e.soxuathang_fk = g.PK_SEQ "+
	    			"left join NHANVIENGIAONHAN i on g.NVGN_FK = i.PK_SEQ "+
	    			"left join NHAPHANPHOI j on j.pk_seq = a.NPP_FK "+
	    			"left join KHO on f.Kho_FK = kho.PK_SEQ "+
	    			"where a.pk_seq > 0 " + condition + " and a.trangthai <> 3  and i.TEN is null "+
	    			"order by sort, nvgn asc ";*/
			
			String query =  "  select distinct 0 as sort, isnull(i.TEN, '') as nvgn, a.machungtu,  " + 
							"  	isnull(b.maFAST, c.maFAST) as maFAST, isnull(b.DIACHI, c.diachi) as diachi, a.ngaydonhang as ngaylap,  " + 
							"  	isnull(g.NgayYeuCau, '') as ngaygiaohang, a.tongthanhtoan as thanhtien, kho.TEN as kho, j.ten as congty , " + 
							"  		 (	Select hd.sohoadon + ', ' AS [text()]   " + 
							"  			From ERP_HOADONNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadonnpp_fk = hd.pk_seq   " + 
							"  			Where YCXK1.ddh_fk = a.pk_seq and hd.trangthai not in ( 3, 5 )  " + 
							"  			For XML PATH ('') )  as SOHOADON,  " + 
							"  		 (	Select hd.NGAYXUATHD + ', ' AS [text()]   " + 
							"  			From ERP_HOADONNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadonnpp_fk = hd.pk_seq   " + 
							"  			Where YCXK1.ddh_fk = a.pk_seq and hd.trangthai not in ( 3, 5 )  " + 
							"  			For XML PATH ('') )  as NGAYXUATHD,  " + 
							"  		 (	Select hd.TENXUATHD + ', ' AS [text()]   " + 
							"  			From ERP_HOADONNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadonnpp_fk = hd.pk_seq   " + 
							"  			Where YCXK1.ddh_fk = a.pk_seq and hd.trangthai not in ( 3, 5 )   " + 
							"  			For XML PATH ('') )  as TENXUATHD " + 
							"  from ERP_DONDATHANGNPP a  " + 
							"  	left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ  " + 
							"  	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ   " + 
							"  	inner join ERP_SOXUATHANGNPP_DDH e on a.pk_seq = e.ddh_fk  " + 
							"  	inner join ERP_SOXUATHANGNPP g on e.soxuathang_fk = g.PK_SEQ  " + 
							"  	left join NHANVIENGIAONHAN i on g.NVGN_FK = i.PK_SEQ  " + 
							"  	left join NHAPHANPHOI j on j.pk_seq = a.NPP_FK  " + 
							"	left join KHO on a.Kho_FK = kho.PK_SEQ "+
							"  where a.pk_seq > 0  and a.trangthai <> 3  and i.TEN is not null " + condition;
			query += " order by isnull(i.TEN, '') asc ";
			
	    	System.out.println("::: LAY BC CHUNG: " + query );
	    	this.XuatExcelChung(response, query, tungay, denngay);
	    }
	    else
	    {
	    	String condition = "";
			if( tungay.trim().length() > 0 )
				condition += " and a.NGAYXUATHD >= '" + tungay + "' ";
			if( denngay.trim().length() > 0 )
				condition += " and a.NGAYXUATHD <= '" + denngay + "' ";
			if( obj.getNvgnId().trim().length() > 0 )
				condition += " AND b.pk_seq in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + obj.getNvgnId() + "' ) ";
			if( obj.getKbhId().trim().length() > 0 )
				condition += " AND b.pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk = '" + obj.getKbhId() + "' ) ";
			
			// lọc thêm kho và khách hàng
			
			if(obj.getKhoId().trim().length() >0){
				condition += " and a.KHO_FK = " + obj.getKhoId();
			}
			if(obj.getKhachhangId().trim().length() >0){
				condition += " and a.KHACHHANG_FK=" + obj.getKhachhangId();
			}
			//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
			String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "b", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
			condition += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getUserId() + "' ) ) ) ";
			
	    	String query =  "select ( select ten from NHANVIENGIAONHAN where pk_seq = '" + obj.getNvgnId() + "' ) as nvgn, " + 
	    					" a.SOHOADON, a.NGAYXUATHD, isnull(b.maFAST, c.maFAST) as maFAST, a.TENXUATHD, isnull(b.DIACHI, c.diachi) as diachi, d.TEN, d.SOLO, d.SOLUONG, d.DONVI  "+
							"from ERP_HOADONNPP a left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ "+
							"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+
							"	inner join ERP_HOADONNPP_SP_CHITIET d on a.PK_SEQ = d.hoadon_fk "+
							"	inner join SANPHAM e on d.MA = e.MA "+
							"where a.pk_seq > 0 " + condition + 
	    					"order by a.SOHOADON asc ";
	    	
	    	System.out.println("::: LAY BC: " + query );
	    	this.XuatExcel(response, query, tungay, denngay);
	    }

	}
	
	private void XuatExcel(HttpServletResponse response, String query, String tungay, String denngay) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LichGiaoHang.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Lịch giao hàng", 0);
			
			sheet.addCell(new Label(0, 0, "LỊCH GIAO HÀNG"));
			
			sheet.addCell(new Label(0, 1, "Từ ngày: "));
			sheet.addCell(new Label(1, 1, tungay));

			sheet.addCell(new Label(0, 2, "Đến ngày: "));
			sheet.addCell(new Label(1, 2, denngay));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "NV giao nhận", cellFormat));
			sheet.addCell(new Label(2, 4, "Số hóa đơn", cellFormat));
			sheet.addCell(new Label(3, 4, "Ngày tháng", cellFormat));
			sheet.addCell(new Label(4, 4, "Mã khách hàng", cellFormat));
			sheet.addCell(new Label(5, 4, "Tên khách hàng", cellFormat));
			sheet.addCell(new Label(6, 4, "Địa chỉ", cellFormat));
			sheet.addCell(new Label(7, 4, "Sản phẩm", cellFormat));
			sheet.addCell(new Label(8, 4, "Đơn vị", cellFormat));
			sheet.addCell(new Label(9, 4, "Số lô", cellFormat));
			sheet.addCell(new Label(10, 4, "Số lượng", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 5;
			int stt = 1;
			while (rs.next())
			{
				Label label;

				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(1, j, rs.getString("NVGN"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(2, j, rs.getString("SOHOADON"), cellFormat2);
				sheet.addCell(label);

				label = new Label(3, j, rs.getString("NGAYXUATHD"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("MAFAST"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(5, j, rs.getString("TENXUATHD"), cellFormat2);
				sheet.addCell(label);

				label = new Label(6, j, rs.getString("diachi"), cellFormat2);
				sheet.addCell(label);

				label = new Label(7, j, rs.getString("TEN"), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, rs.getString("DONVI"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(9, j, rs.getString("SOLO"), cellFormat2);
				sheet.addCell(label);

				label = new Label(10, j, rs.getString("SOLUONG"), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 0; x <= 10; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void XuatExcelChung(HttpServletResponse response, String query, String tungay, String denngay) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LichGiaoHang.xls");
			OutputStream out = response.getOutputStream();
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\LichGiaoHang.xls");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "LỊCH GIAO HÀNG");
		   
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + tungay + "   Đến ngày: " + denngay);

			cell = cells.getCell("AZ1");
			Style style2=cell.getStyle();
			
			int countRow = 4; 
			cells.merge(countRow, 0, countRow+1, 0);
			cell = cells.getCell(countRow, 0);
			Style s = cell.getStyle();
			s.setHAlignment(TextAlignmentType.CENTER);
			s.setVAlignment(TextAlignmentType.JUSTIFY);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "STT");
			cells.merge(countRow, 1, countRow, 4);
			cell = cells.getCell(countRow, 1);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Người giao hàng");
			cells.merge(countRow, 5, countRow, 6);
			cell = cells.getCell(countRow, 5);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Công ty");
			cells.merge(countRow, 7, countRow, 8);
			cell = cells.getCell(countRow, 7);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Kho hàng hóa");
			countRow++;
			cell = cells.getCell(countRow, 1);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Đơn đặt hàng");
			cell = cells.getCell(countRow, 2);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Số hóa đơn");
			cell = cells.getCell(countRow, 3);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Mã khách hàng");
			cell = cells.getCell(countRow, 4);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Khách hàng");
			cell = cells.getCell(countRow, 5);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Địa chỉ giao hàng");
			cell = cells.getCell(countRow, 6);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Ngày lập");
			cell = cells.getCell(countRow, 7);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Ngày giao hàng");
			cell = cells.getCell(countRow, 8);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Thành tiền");
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			countRow++; 
			int stttong = 0, stt = 0;
			String nvgn = "", nvgncur = "";
			double tongtien = 0, tien = 0;
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			while (rs.next())
			{
				nvgncur = rs.getString("nvgn");
				if(!nvgn.equals(nvgncur) && tongtien > 0)
				{
					cells.merge(countRow, 0, countRow, 7);
					cell = cells.getCell(countRow, 0);
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Tổng tiền");
					
					cell = cells.getCell(countRow, 8);
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, formatter.format(tongtien));
					
					countRow++;
				}
				if(!nvgn.equals(nvgncur))
				{
					stttong++;
					cell = cells.getCell(countRow, 0);
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, stttong+"");
					
					cells.merge(countRow, 1, countRow, 4);
					cell = cells.getCell(countRow, 1);
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, nvgncur);
					
					cells.merge(countRow, 5, countRow, 6);
					cell = cells.getCell(countRow, 5);
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, rs.getString("congty"));
					
					cells.merge(countRow, 7, countRow, 8);
					cell = cells.getCell(countRow, 7);
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, rs.getString("kho") + " " + rs.getString("congty"));
					countRow++;
					stt = 0;
					tongtien = 0;
					nvgn = nvgncur;
				}
				stt++;
				cell = cells.getCell(countRow, 0);
				s = cell.getStyle();
				s.setHAlignment(TextAlignmentType.CENTER);
				s.setVAlignment(TextAlignmentType.JUSTIFY);
				cell.setStyle(s);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, stt+"");
				cell = cells.getCell(countRow, 1);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("machungtu"));
				cell = cells.getCell(countRow, 2);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("sohoadon"));
				cell = cells.getCell(countRow, 3);
				cell.setStyle(s);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("mafast"));
				cell = cells.getCell(countRow, 4);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("tenxuathd"));
				cell = cells.getCell(countRow, 5);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("diachi"));
				cell = cells.getCell(countRow, 6);
				cell.setStyle(s);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("ngaylap"));
				cell = cells.getCell(countRow, 7);
				cell.setStyle(s);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString("ngaygiaohang"));
				tien = rs.getDouble("thanhtien");
				tongtien += tien;
				cell = cells.getCell(countRow, 8);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, formatter.format(tien));
				
				countRow++;
			}
			
			cells.merge(countRow, 0, countRow, 7);
			cell = cells.getCell(countRow, 0);
			cell.setStyle(style2);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Tổng tiền");
			
			cell = cells.getCell(countRow, 8);
			cell.setStyle(style2);
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, formatter.format(tongtien));
			
			for (int i = 0; i < 9; i++) {
				int j = 4;
				while(j <= countRow)
				{
					cell = cells.getCell(j, i);
					Style style = cell.getStyle();
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
					style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
					style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
					style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
					cell.setStyle(style);
					j++;
				}
			}
			
			workbook.save(out);
			fstream.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
		
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream, IErpSoxuathangNppList obj, String userid ) throws IOException, DocumentException
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		NumberFormat formatter = new DecimalFormat("#,###,###");
		try
		{
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font4normal = new Font(bf, 8, Font.NORMAL);
			Font font10normal = new Font(bf, 10, Font.NORMAL);
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			String tencongty = "";
			String tennvgn = "";
			String query = "select a.ten, b.ten as nvgnTEN from nhaphanphoi a, NHANVIENGIAONHAN b where a.pk_seq = " + obj.getNppId() + " and b.pk_seq = '" + obj.getNvgnId() + "' " ;
			ResultSet rs = db.get(query);
			try 
			{
				rs.next();
				tencongty = rs.getString("ten");
				tennvgn = rs.getString("nvgnTEN");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			Paragraph tieude1 = new Paragraph(tencongty.toUpperCase(), font4);
			tieude1.setIndentationRight(0);
			document.add(tieude1);

			Paragraph tieudebm = new Paragraph("BM-20-2" , font4normal);
			tieudebm.setIndentationLeft(10);			
			tieudebm.setAlignment(Element.ALIGN_RIGHT);
			document.add(tieudebm);
			
			String str_tieude = "LỊCH GIAO HÀNG";
			Paragraph tieude = new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(5);
			document.add(tieude);
			
			tieude = new Paragraph("Nhân viên giao hàng: " + tennvgn, font10normal);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(10);
			document.add(tieude);
			
			float[] withs4 = { 6.0f, 10.0f, 10.0f, 50.0f, 20.0f, 12.0f, 12.0f};
			PdfPTable table4 = new PdfPTable(withs4);
			table4.setWidthPercentage(100);
			table4.setHorizontalAlignment(Element.ALIGN_LEFT);
			String[] th = new String[] { "STT", "Số hóa đơn", "Ngày tháng", "Tên khách hàng (địa chỉ)", "Sản phẩm", "Số lô", "Số lượng" };
			PdfPCell[] cell4 = new PdfPCell[7];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBackgroundColor(BaseColor.LIGHT_GRAY );
				table4.addCell(cell4[i]);
			}	
			
			query = "select a.SOHOADON, a.NGAYXUATHD, isnull(b.maFAST, c.maFAST) as maFAST, a.TENXUATHD, isnull(b.DIACHI, c.diachi) as diachi, d.TEN, d.SOLO, d.SOLUONG  "+
					 "from ERP_HOADONNPP a left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ "+
					 "	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+
					 "	inner join ERP_HOADONNPP_SP_CHITIET d on a.PK_SEQ = d.hoadon_fk "+
					 "	inner join SANPHAM e on d.MA = e.MA "+
					 "where a.npp_fk = '" + obj.getNppId() + "' and a.NGAYXUATHD >= '" + obj.getTungay() + "' and d.SOLUONG > 0 ";
			
			if( obj.getDenngay().trim().length() > 0 )
				query += " AND a.NGAYXUATHD <= '" + obj.getDenngay() + "' ";
			if( obj.getNvgnId().trim().length() > 0 )
				query += " AND b.pk_seq in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + obj.getNvgnId() + "' ) ";
			if( obj.getKbhId().trim().length() > 0 )
				query += " AND b.pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk = '" + obj.getKbhId() + "' ) ";
			
			// bổ sung Kho và khách hàng
			// lọc thêm kho và khách hàng
			
			if(obj.getKhoId().trim().length() >0){
				query += " and a.KHO_FK = " + obj.getKhoId();
			}
			if(obj.getKhachhangId().trim().length() >0){
				query += " and a.KHACHHANG_FK=" + obj.getKhachhangId();
			}
			
			//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
			String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "b", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
			query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getUserId() + "' ) ) ) ";
			
			System.out.println("::: THONG TIN SP: " + query );
			rs = db.get(query);
			if( rs != null )
			{
				int stt = 1;
				while( rs.next() )
				{
					PdfPCell cells = new PdfPCell(new Paragraph( Integer.toString(stt) , font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
					
					cells = new PdfPCell(new Paragraph( rs.getString("SOHOADON") , font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
					
					cells = new PdfPCell(new Paragraph( rs.getString("NGAYXUATHD"), font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
					
					cells = new PdfPCell(new Paragraph( rs.getString("TENXUATHD") + " (" + rs.getString("diachi") + ")" , font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
					
					cells = new PdfPCell(new Paragraph( rs.getString("TEN") , font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
					
					cells = new PdfPCell(new Paragraph( rs.getString("SOLO") , font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
					
					cells = new PdfPCell(new Paragraph( formatter.format( rs.getDouble("soluong")), font4normal));
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(5);
					table4.addCell(cells);
				}
				rs.close();
			}
			
			document.add(table4);
			document.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			db.shutDown();
			e.printStackTrace();
		}
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
