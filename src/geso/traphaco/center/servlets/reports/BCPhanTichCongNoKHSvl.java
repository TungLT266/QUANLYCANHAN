package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;

public class BCPhanTichCongNoKHSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public BCPhanTichCongNoKHSvl() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		IStockintransit obj = new Stockintransit();	
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setnppId("");
		
		String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    
		obj.init();
		
		
		obj.setuserId(userId);
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		if(view.equals("TT"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BCPhanTichCongNoKH_center.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChietKhauQuy.jsp";
			response.sendRedirect(nextJSP);
		}
		
		String nextJSP = "/TraphacoHYERP/pages/Center/BCPhanTichCongNoKH_center.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");
			
			String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));
			obj.settungay(tuNgay);
			
			String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
			obj.setdenngay(denNgay);
									
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId"))!=null?
				util.antiSQLInspection(request.getParameter("userId")):"");
						
			obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
					util.antiSQLInspection(request.getParameter("nppId")):"");
			
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
					util.antiSQLInspection(request.getParameter("kenhId")):"");
			
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?
					util.antiSQLInspection(request.getParameter("dvkdId")):"");
			
			obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
					util.antiSQLInspection(request.getParameter("vungId")):"");
									
			obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
					util.antiSQLInspection(request.getParameter("khuvucId")):"");
			
			obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
					util.antiSQLInspection(request.getParameter("chungloaiId")):"");
			
			//obj.init();
			session.setAttribute("obj", obj);
					
			
			String query = 	 "select kh.maFAST, kh.TEN, isnull(THOIHANNO, 90) as thoihanNO, isnull(ndk.tongtienAVAT, 0) as nodauky, \n " +
				 "		isnull(psn.tongtienAVAT, 0) as phatsinhno, isnull(psn.tongtienAVAT, 0) as phatsinhco, \n " +
				 "		isnull(no_trong_han.tongtienAVAT, 0) as notronghan, isnull(no_qua_han.tongtienAVAT, 0) as noquahan, \n " +
				 "		isnull(no_qua_han_1_60.tongtienAVAT, 0) as no_qua_han_1_60, isnull(no_qua_han_61_120.tongtienAVAT, 0) as no_qua_han_61_120, \n " +
				 "		isnull(no_qua_han_121_180.tongtienAVAT, 0) as no_qua_han_121_180, isnull(no_qua_han_tren_180.tongtienAVAT, 0) as no_qua_han_tren_180 \n " +
				 "from KHACHHANG kh left join \n " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT \n " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join \n " +
				 "		( \n " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT \n " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK    \n    " +
				 "			where tt.TRANGTHAI = 1 \n " +
				 "			group by tthd.khachhang_fk, tthd.hoadonnpp_fk \n" +
				 "		) " +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk \n" +
				 "	where hd.trangthai not in (3, 5) ";				 
					 if(tuNgay.trim().length()>0)
					 {
						 query += " and hd.ngayxuatHD < '"+tuNgay+"' ";
					 }
				
				query += "group by hd.khachhang_fk \n " +
				 ") \n " +				 
				 "ndk on kh.pk_seq = ndk.khachhang_fk left join " +
				 "( " +
				 "	select khachhang_fk, sum(tongtienAVAT) as  tongtienAVAT  \n " +
				 "	from HOADON \n  " +
				 "	where trangthai not in (3, 5) " ;
					if(tuNgay.trim().length()>0)
					 {
						 query += " and ngayxuatHD >= '"+tuNgay+"' ";
					 }
					if(denNgay.trim().length()>0)
					{
						query+=" and ngayxuatHD <= '"+denNgay+"' ";
					}				
				 query += "	group by khachhang_fk \n" +
				 ") " +
				 " psn on kh.pk_seq = psn.khachhang_fk left join \n" +
				 " ( " +
				 "	select  tthd.khachhang_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "	from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
				 "			inner join HOADON hd on tthd.HOADONNPP_FK =  hd.PK_SEQ     " +
				 "	where tt.TRANGTHAI = 1 ";
					 if(tuNgay.trim().length()>0)
					 {
						 query += " and hd.ngayxuatHD >= '"+tuNgay+"' ";
					 }
					if(denNgay.trim().length()>0)
					{
						query+=" and hd.ngayxuatHD <= '"+denNgay+"' ";
					}
				query +=  "	group by tthd.khachhang_fk \n" +
				 ") \n" +
				 "psc on kh.pk_seq = psc.khachhang_fk left join " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT  " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join  " +
				 "		( " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK        " +
				 "			where tt.TRANGTHAI = 1   " +
				 "			group by tthd.khachhang_fk, tthd.hoadonnpp_fk " +
				 "		) " +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk " +
				 "	where hd.trangthai not in (3, 5)and Datediff( dd, hd.NGAYXUATHD, getdate() ) <= k.THOIHANNO " +
				 "	group by hd.khachhang_fk " +
				 ") " +
				 "no_trong_han on kh.pk_seq = no_trong_han.khachhang_fk left join " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT  " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join  " +
				 "		( " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK        " +
				 "			where tt.TRANGTHAI = 1   " +
				 "			group by tthd.khachhang_fk, tthd.hoadonnpp_fk " +
				 "		) " +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk " +
				 "	where hd.trangthai not in (3, 5) and Datediff( dd, hd.NGAYXUATHD, getdate() ) > k.THOIHANNO " +
				 "	group by hd.khachhang_fk " +
				 ") " +
				 "no_qua_han on kh.pk_seq = no_qua_han.khachhang_fk left join " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT  " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join  " +
				 "		( " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK        " +
				 "			where tt.TRANGTHAI = 1 ";
				 	if(tuNgay.trim().length()>0)
					 {
						 query += " and ngaychungtu < '"+tuNgay+"' ";
					 }				
				 query += "	group by tthd.khachhang_fk, tthd.hoadonnpp_fk \n" +
				 "		) \n" +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk \n" +
				 "	where hd.trangthai not in (3, 5)  " +
				 "			and Datediff( dd, hd.NGAYXUATHD, getdate() ) > k.THOIHANNO and Datediff( dd, hd.NGAYXUATHD, getdate() ) <= ( k.THOIHANNO + 60 ) " +
				 "	group by hd.khachhang_fk " +
				 ") " +
				 "no_qua_han_1_60 on kh.pk_seq = no_qua_han_1_60.khachhang_fk left join " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT  " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join  " +
				 "		( " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK        " +
				 "			where tt.TRANGTHAI = 1 ";
				 if(tuNgay.trim().length()>0)
				 {
					 query += " and ngaychungtu < '"+tuNgay+"' ";
				 }	
				 
				 query +=
				 "			group by tthd.khachhang_fk, tthd.hoadonnpp_fk " +
				 "		) " +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk " +
				 "	where hd.trangthai not in (3, 5)  " +
				 "		and Datediff( dd, hd.NGAYXUATHD, getdate() ) > ( k.THOIHANNO + 60 ) and Datediff( dd, hd.NGAYXUATHD, getdate() ) <= ( k.THOIHANNO + 120 ) " +
				 "	group by hd.khachhang_fk " +
				 ") " +
				 "no_qua_han_61_120 on kh.pk_seq = no_qua_han_61_120.khachhang_fk left join " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT  " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join  " +
				 "		( " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK        " +
				 "			where tt.TRANGTHAI = 1 ";
				 if(tuNgay.trim().length()>0)
				 {
					 query += " and ngaychungtu < '"+tuNgay+"' ";
				 }
				 query+=
				 "			group by tthd.khachhang_fk, tthd.hoadonnpp_fk " +
				 "		) " +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk " +
				 "	where hd.trangthai not in (3, 5)  " +
				 "		and Datediff( dd, hd.NGAYXUATHD, getdate() ) > ( k.THOIHANNO + 120 ) and Datediff( dd, hd.NGAYXUATHD, getdate() ) <= ( k.THOIHANNO + 180 ) " +
				 "	group by hd.khachhang_fk " +
				 ") " +
				 "no_qua_han_121_180 on kh.pk_seq = no_qua_han_121_180.khachhang_fk left join " +
				 "( " +
				 "	select hd.khachhang_fk, sum(tongtienAVAT - isnull(tt.sotienTT, 0) ) as  tongtienAVAT  " +
				 "	from HOADON hd inner join KHACHHANG k on hd.khachhang_fk = k.pk_seq left join  " +
				 "		( " +
				 "			select  tthd.khachhang_fk, tthd.hoadonnpp_fk, sum(isnull( tthd.sotienTT, 0 ) ) as sotienTT  " +
				 "			from ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK        " +
				 "			where tt.TRANGTHAI = 1  ";
				 if(tuNgay.trim().length()>0)
				 {
					 query += " and ngaychungtu < '"+tuNgay+"' ";
				 }
				 query+=				 
				 "			group by tthd.khachhang_fk, tthd.hoadonnpp_fk " +
				 "		) " +
				 "		tt on hd.khachhang_fk = tt.khachhang_fk and hd.pk_seq = tt.hoadonnpp_fk " +
				 "	where hd.trangthai not in (3, 5)  and Datediff( dd, hd.NGAYXUATHD, getdate() ) > ( k.THOIHANNO + 180 )  " +
				 "	group by hd.khachhang_fk " +
				 ") " +
				 " no_qua_han_tren_180 on kh.pk_seq = no_qua_han_tren_180.khachhang_fk " +
				 " where kh.trangthai = '1'  ";
				 			
			System.out.println("Cau query: "+ query);
			
			String action = util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("taomoi"))
			{	
		        	ToExcel(response, obj, query);	
			} 
			
	}
	
	private void ToExcel(HttpServletResponse response, IStockintransit obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
			NumberFormat formatter = new DecimalFormat("#,###,###");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoPhanTichCongNoKH.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 8;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			sheet = w.createSheet("PhanTichCongNoKH", k);//ten sheet
			sheet.addCell(new Label(0, 1, "BÁO CÁO PHÂN TÍCH CÔNG NỢ KHÁCH HÀNG ", celltieude));			
			//sheet.addMergedRegion(new CellRangeAddress(1,1,4,1))		
			
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 1, 7, 1);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			//sheet.addCell(new Label(0, 3, "Ngày tạo: "));// cột dòng
			//sheet.addCell(new Label(1, 3, tuNgay)); // lấy ngày đã chọn
			
			//sheet.addCell(new Label(0, 4, "Đến ngày: "));
			//sheet.addCell(new Label(1, 4, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
					
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.addCell(new Label(0, 7, "STT", cellFormat));
			//sheet.mergeCells(0, 7, 0 , 8); //bắt đầu từ cột 0,  dòng mấy , số cột merger, số dòng merger
			sheet.addCell(new Label(1, 7, "Mã FAST", cellFormat));
			//sheet.mergeCells(1, 7, 1 , 8);
			sheet.addCell(new Label(2, 7, "Tên khách hàng", cellFormat));
			//sheet.mergeCells(2, 7, 2 , 8);
			sheet.addCell(new Label(3, 7, "Hạn thanh toán", cellFormat));
			//sheet.mergeCells(3, 7, 4 , 8);
			sheet.addCell(new Label(4, 7, "Nợ đầu kỳ", cellFormat));
			//sheet.mergeCells(4, 7, 5 , 8);
			sheet.addCell(new Label(5, 7, "PS nợ", cellFormat));
			//sheet.mergeCells(5, 7, 6 , 8);
			sheet.addCell(new Label(6, 7, "PS có", cellFormat));
			//sheet.mergeCells(6, 7, 7 , 8);
			sheet.addCell(new Label(7, 7, "Nợ CK", cellFormat));
			//sheet.mergeCells(7, 7, 8 , 8);
			sheet.addCell(new Label(8, 7, "Trong hạn", cellFormat));
			//sheet.mergeCells(8, 7, 9 , 8);
			sheet.addCell(new Label(9, 7, "Tổng quá hạn", cellFormat));
			
			sheet.addCell(new Label(10, 7, "QH 1-60", cellFormat));
			//sheet.mergeCells(9, 7, 10 , 10);
			//sheet.addCell(new Label(10, 7, "NỢ QUÁ HẠN CHIA THEO NGÀY NỢ", cellFormat));			
			//sheet.mergeCells(10, 7, 13 , 7);			
			sheet.addCell(new Label(11, 7, "QH 61-120", cellFormat));	
			sheet.addCell(new Label(12, 7, "QH 121-180", cellFormat));	
			sheet.addCell(new Label(13, 7, "QH >180", cellFormat));	
			
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 70);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			sheet.setColumnView(9, 20);
			sheet.setColumnView(10, 20);
			sheet.setColumnView(11, 20);
			sheet.setColumnView(12, 20);
			sheet.setColumnView(13, 20);
			sheet.setColumnView(14, 20);
			sheet.setColumnView(15, 20);

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = new WritableCellFormat(cellFont);
			
			WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
			cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
			cformat1.setAlignment(Alignment.RIGHT);
			cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			Label label;
			Number number;

			int stt = 0;
			double ndk = 0;
			double psn=0;
			double psc=0;
			double nck=0;
			
			if(rs!=null)
			{
			while (rs.next())
			{
				stt++;				
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("mafast"), cformat3);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("TEN"), cformat3);
				sheet.addCell(label);	
				label = new Label(3, j, rs.getString("thoihanNO"), cformat3);
				sheet.addCell(label);
				ndk = rs.getDouble("nodauky");
				label = new Label(4, j, formatter.format(ndk), cformat1);
				sheet.addCell(label);
				psn = rs.getDouble("phatsinhno");
				label = new Label(5, j, formatter.format(psn), cformat1);
				sheet.addCell(label);
				psc = rs.getDouble("phatsinhco");
				label = new Label(6, j, formatter.format(psc), cformat1);
				sheet.addCell(label);
				nck=ndk+psn-psc;
				label = new Label(7, j, formatter.format(nck), cformat1);
				sheet.addCell(label);
				label = new Label(8, j, formatter.format(rs.getDouble("notronghan")), cformat1);
				sheet.addCell(label);
				label = new Label(9, j, formatter.format(rs.getDouble("noquahan")), cformat1);
				sheet.addCell(label);
				label = new Label(10, j, formatter.format(rs.getDouble("no_qua_han_1_60")), cformat1);
				sheet.addCell(label);
				label = new Label(11, j, formatter.format(rs.getDouble("no_qua_han_61_120")), cformat1);
				sheet.addCell(label);
				label = new Label(12, j, formatter.format(rs.getDouble("no_qua_han_121_180")), cformat1);
				sheet.addCell(label);
				label = new Label(13, j, formatter.format(rs.getDouble("no_qua_han_tren_180")), cformat1);
				sheet.addCell(label);
				
				//SoTienTT += rs.getDouble("SoTienTT");				
				//label = new Label(7, j, formatter.format(rs.getDouble("SoTienConLai")),cformat1);
				//SoTienConLai+= rs.getDouble("SoTienConLai");
				//sheet.addCell(label);
				j++;
			}
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}
			/*if(j>8)
			{
			sheet.addCell(new Label(1, j, "TỔNG CỘNG", cellFormat));
			label = new Label(6, j, formatter.format(SoTienTT), cformat1);
			sheet.addCell(label);
			
			label = new Label(7, j, formatter.format(SoTienConLai), cformat1);
			sheet.addCell(label);
			}*/
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Lỗi : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
	}
	
	
	
	public String getQuery(IStockintransit obj)
	{
		
		String sql=  " SELECT ISNULL(D.TEN, 'CHUA XAC DINH') AS CHANEL, H.TEN AS REGION,  F.TEN AS AREA,  "+
					 " E.TEN AS DISTRIBUTOR, E.SITECODE AS DISTCODE,   B.TEN AS SKU, B.MA AS SKUCODE, A.NGAY AS DATE, "+
					 " C.TEN AS WAREHOUSE, G.TEN AS PROVINCE,  I.DONVIKINHDOANH AS BUSINESSUNIT, K.TEN AS BRANDS,  "+
					 " J.TEN AS CATEGORY, A.SOLUONG AS PIECE,  CASE WHEN A.SOLUONG IS NULL THEN 0 ELSE A.SOLUONG/ISNULL(QC.SOLUONG1,1) END AS QUATITY, "+
					 " CASE WHEN A.SOLUONG * NPPK.GIAMUA IS NULL THEN 0 ELSE A.SOLUONG * NPPK.GIAMUA END AS AMOUNT,QC.SOLUONG1 AS QC1,QC.SOLUONG2 AS QC2,A.SOLO "+
					 " FROM ( SELECT * FROM TONKHONGAY_CHITIET  WHERE NGAY >='" + obj.gettungay() + "' and ngay <= '" + obj.getdenngay() + "' AND SOLUONG > 0 ) A  "+
					 " INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ INNER JOIN KHO C ON A.KHO_FK = C.PK_SEQ   "+
					 " INNER JOIN KENHBANHANG D ON A.KBH_FK = D.PK_SEQ	INNER JOIN NHAPHANPHOI E ON A.NPP_FK = E.PK_SEQ  "+
					 " INNER JOIN KHUVUC F ON E.KHUVUC_FK = F.PK_SEQ LEFT JOIN TINHTHANH G ON E.TINHTHANH_FK = G.PK_SEQ  "+
					 " INNER JOIN VUNG H ON F.VUNG_FK = H.PK_SEQ INNER JOIN DONVIKINHDOANH I ON B.DVKD_FK = I.PK_SEQ  "+
					 " INNER JOIN CHUNGLOAI J ON B.CHUNGLOAI_FK = J.PK_SEQ INNER JOIN NHANHANG K ON B.NHANHANG_FK = K.PK_SEQ   "+
					 " LEFT JOIN QUYCACH QC ON A.SANPHAM_FK = QC.SANPHAM_FK AND QC.DVDL2_FK=100018  "+
					 " LEFT JOIN(  "+
					 " 	SELECT B.KENH_FK,C.NPP_FK  ,D.SANPHAM_FK, D.GIAMUANPP *0.97 AS GIAMUA  "+
					 " 	FROM BANGGIAMUANPP B   "+
					 " 	INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK    "+
					 " 	INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK  "+
					 " 	WHERE  B.TUNGAY <='" + obj.gettungay() + "' AND   "+
					 " 	B.PK_SEQ =    "+
					 " 	(     "+
					 " 	SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG  "+
					 " 	INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK    "+
					 " 	WHERE BG.TUNGAY <= '" + obj.gettungay() + "' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK "+
					 " 	ORDER BY TUNGAY DESC   "+
					 " 	)  "+
					 "  "+
					 " )NPPK ON  NPPK.SANPHAM_FK=B.PK_SEQ  AND NPPK.NPP_FK =A.NPP_FK   AND A.KBH_FK=NPPK.KENH_FK "+
					 " WHERE 1=1";
	    
		geso.traphaco.center.util.Utility Uti_center = new geso.traphaco.center.util.Utility();
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and d.pk_seq ='" + obj.getkenhId() + "'";
		else
			sql = sql + " and d.pk_seq in " + Uti_center.quyen_kenh(obj.getuserId());
		
		if (obj.getvungId().length() > 0)
			sql = sql + " and h.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and f.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and i.PK_SEQ ='" + obj.getdvkdId() + "'";
		
		if(obj.getnppId().length() > 0)
			sql = sql + " and e.pk_seq ='" + obj.getnppId() + "'";
		else
			sql = sql + " and e.pk_seq in " + Uti_center.quyen_npp(obj.getuserId());
		
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and k.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and j.pk_seq ='" + obj.getchungloaiId() + "'";
		
		sql  =sql + " order by a.npp_fk,a.ngay,a.sanpham_fk ";
		 System.out.print("Ton kho ngay: " + sql + "\n");
		 return sql;
	}
		
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :  hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	

}
