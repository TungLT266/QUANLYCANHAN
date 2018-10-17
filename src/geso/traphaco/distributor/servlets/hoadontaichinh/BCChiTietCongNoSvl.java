package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.center.beans.khuvuc.imp.Khuvuc;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCChiTietCongNo;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.BCChiTietCongNo;
import geso.traphaco.distributor.beans.reports.IBCCongNoKH;
import geso.traphaco.distributor.beans.reports.imp.BCCongNoKH;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import java.io.*;
import java.text.SimpleDateFormat;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.examples.MergedCells;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.examples.MergingCells;
import org.apache.poi.hssf.record.MergeCellsRecord.MergedRegion;

import Z.DB;

import com.extentech.formats.XLS.Mergedcells;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BCChiTietCongNoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    public BCChiTietCongNoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		IBCChiTietCongNo obj = new BCChiTietCongNo();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Utility util=new Utility();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		/*String nppId= util.getIdNhapp(userId);*/
		
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	session.setAttribute("loi", "");
    	
    	System.out.println("userId" + userId);
    	
    	obj.setUserId(userId);
    	obj.setUserName(userTen);
    		  		
    	
    	
    	 String nppId ="";
    	 String view = request.getParameter("view");
    	  if(view == null)
  	    	view = "NPP";
    	 if(view.equals("TT"))
 		{
 			 nppId = util.antiSQLInspection(request.getParameter("nppId"));
 			if (nppId == null)
 					nppId = "";
 				obj.setNppId(nppId);
 		}else
 		{
 			nppId=util.getIdNhapp(userId);
 			obj.setNppId(nppId);
 		}
    	 
    	 obj.init1();
    	String nextJSP ="";
    	if(view.equals("TT"))
    	{
    		nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChiTietCongNo.jsp";
    	}
    	if(view.equals("NPP"))
    	{
    		nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChiTietCongNo.jsp";
    	}
		
    	session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}
//
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		IBCChiTietCongNo obj = new BCChiTietCongNo();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        Utility util = new Utility();
        String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));
        obj.setTuNgay(tuNgay);
        String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
        obj.setDenNgay(denNgay);
        
        String KhachHang = util.antiSQLInspection(request.getParameter("KhachHang"));
        if(KhachHang==null)
        	KhachHang="";
        obj.setKHId(KhachHang);
        
        String Vung = util.antiSQLInspection(request.getParameter("vungId"));
        System.out.println("Vung "+ Vung);
        if(Vung==null)
        	Vung="";
        obj.setvungId(Vung);
        
        String khuvuc = util.antiSQLInspection(request.getParameter("khuvucId"));
        if(khuvuc==null)
        	khuvuc="";
        obj.setkhuvucId(khuvuc);
        
        String ttId = util.antiSQLInspection(request.getParameter("ttId"));
        System.out.println("ttId "+ Vung);
        if(ttId==null)
        	ttId="";
        obj.setTtId(ttId);
        
        String type = util.antiSQLInspection(request.getParameter("type"));
        if(type==null)
        	type="";
        obj.settype(type);
        
        String doitacId = util.antiSQLInspection(request.getParameter("doitacId"));
        if(doitacId==null)
        	doitacId="";
        obj.setDoiTacId(doitacId);
        
        session.setAttribute("tungay", tuNgay);
        session.setAttribute("denngay", denNgay);
        session.setAttribute("vungId", Vung);
        session.setAttribute("khuvucId", khuvuc);
        String action = request.getParameter("action");
        String userTen = (String)session.getAttribute("userTen");
        String userId = (String) session.getAttribute("userId"); 
        
        
        obj.setUserId(userId);
        obj.setUserName(userTen);
        
        String nppId= util.getIdNhapp(userId);
        
        String nppID="";
        
        String view = request.getParameter("view");
        if(view == null)
  	    	view = "NPP";
        if(view.equals("TT"))
		{
			 nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
					nppId = "";
				obj.setNppId(nppId);
		}else
		{
			nppId=util.getIdNhapp(userId);
			obj.setNppId(nppId);
		}
       
        
	   	 if(action.equals("excel")){
	   		 
	   		if(obj.gettype().equals("0")){
	   		 	String queryddk = obj.getDuNoDauKyKH();
	   		 	String queryTheoKH = obj.getBCTheoKH();
		    	ToExcelTheoKH(response, obj, queryTheoKH, tuNgay, denNgay, obj.getKHId(), userTen, nppId, nppID, queryddk);
	   		}
	   		
	   		if(obj.gettype().equals("1")){
	   			String queryddk = obj.getDuNoDauKyDoiTac();
	   		 	String queryTheoDoiTac = obj.getBCTheoDoiTac();
		    	ToExcelTheoDoiTac(response, obj, queryTheoDoiTac, tuNgay, denNgay, obj.getDoiTacId(), userTen, nppId, nppID, queryddk);
	   		}
	     }
        
        else
        {
        
	    	String nextJSP ="";
	    	if(view.equals("TT"))
	    	{
	    		nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoChiTietCongNo.jsp";
	    	}
	    	if(view.equals("NPP"))
	    	{
	    		nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoChiTietCongNo.jsp";
	    	}
    	
    	obj.init1();
    	
    	session.setAttribute("obj", obj);
    	
		response.sendRedirect(nextJSP);
        }
	}

	private void ToExcelTheoDoiTac(HttpServletResponse response, IBCChiTietCongNo obj, String query, String tuNgay, String denNgay, String DoiTacid, String userTen, String nppId, String nppID, String queryddk) throws IOException
	{		
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
			//NumberFormat formatter = new DecimalFormat("#,###,###");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietCongNoDoiTac_distributor.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 10;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);
			
			
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			
			sheet = w.createSheet("ChiTietCongNoKH", k);//ten sheet
			
			int f=1;
			if(nppId!=null){			
				sheet.addCell(new Label(0, f, userTen));								
				sheet.mergeCells(0, f, 2, f);
				
				ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+nppId+"'");
				String diachinpp="";
				if(dc!=null)
				{
					while (dc.next())
						diachinpp=dc.getString("DiaChi");
				}
				
				f++;
				sheet.addCell(new Label(0, f, diachinpp, cellFormatTD));
				f=f+2;
			}
			
			else {
				
				
				ResultSet dc = db.get("select Ten, DiaChi from NHAPHANPHOI where PK_SEQ='"+nppID+"'");
				String diachinpp="";
				String tennpp="";
				if(dc!=null)
				{
					while (dc.next()){
						diachinpp=dc.getString("DiaChi");
						tennpp=dc.getString("Ten");
					}
				}
				sheet.addCell(new Label(0, f, tennpp));								
				sheet.mergeCells(0, f, 2, f);
				
				f++;
				sheet.addCell(new Label(0, f, diachinpp, cellFormatTD));
				f=f+2;
			}
			
			sheet.addCell(new Label(0, f, "BÁO CÁO CHI TIẾT CÔNG NỢ KHÁCH HÀNG ", celltieude));			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, f, 6, f);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			f=f+2;
			if(DoiTacid.length()>0)
				{	
					ResultSet kh = db.get("select Ten, DiaChi from NHAPHANPHOI where PK_SEQ='"+DoiTacid+"'");
					
					String diachikh="";
					String tenkh="";
					if(kh!=null)
					{
						while (kh.next()){
							diachikh=kh.getString("DiaChi");
							tenkh=kh.getString("Ten");
						}
					}
					
					sheet.addCell(new Label(1,f, "Đối tác: "+tenkh));
					sheet.mergeCells(1, f, 6, f);
				}
			else
			{
				sheet.addCell(new Label(1,f, "Đối tác: tất cả các đối tác"));
			}
			
			f++;
			sheet.addCell(new Label(1, f, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(2, f, tuNgay)); // lấy ngày đã chọn
			
			f++;
			sheet.addCell(new Label(1, f, "Đến ngày: "));
			sheet.addCell(new Label(2, f, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			f=f+2;
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
			
			WritableCellFormat cellF = new WritableCellFormat(cellFont);
			cellF.setAlignment(Alignment.RIGHT);	
			
			//Tiêu đề
			sheet.addCell(new Label(3, f-1, "DƯ ĐẦU KỲ"));
			sheet.addCell(new Label(0, f, "STT", cellFormat));
			sheet.addCell(new Label(1, f, "Ngày", cellFormat));
			sheet.addCell(new Label(2, f, "Chứng từ số", cellFormat));
			sheet.addCell(new Label(3, f, "Diễn giải", cellFormat));
			sheet.addCell(new Label(4, f, "PSN", cellFormat));
			sheet.addCell(new Label(5, f, "PSC", cellFormat));
			
			//sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);


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
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat inFormat2 = new WritableCellFormat(dp3);
			Label label;
			Number number;
			
			
			//Lấy dữ liệu dư nợ khách hàng
			ResultSet rsdn = db.get(queryddk);
			double totaldndk=0;
			double totaldcdk=0;
			double dndk=0;
			double dcdk=0;
			double totaldn=0;			
			
			if(rsdn!=null)
			{
				while (rsdn.next())
				{
					dndk = rsdn.getDouble("dunodauky");
					dcdk=rsdn.getDouble("ducodauky");
					totaldndk+=dndk;
					totaldcdk+=dcdk;
				}
			}
			totaldn=totaldndk-totaldcdk;
			
			if(totaldn>0)
			{
				number = new Number(4, f-1,totaldn, inFormat2);sheet.addCell(number);
			}
			if(totaldn<0)
			{
				number = new Number(5, f-1,totaldn*(-1), inFormat2);sheet.addCell(number);
			}
			if(totaldn ==0)
			{
				label = new Label(4, f-1, "0", cellF);sheet.addCell(label);
			}			
			
			//Lấy dữ liệu
			ResultSet rs = db.get(query);
			int stt = 0;
			
			double totalpsn=0;
			double totalpsc=0;
			double psn=0;
			double psc=0;			
			
			if(rs!=null)
			{
				f++;
				j=f;
				while (rs.next())
				{
					stt++;				
					String type = "0";
					cformat = type.equals("1") ? cellFormat3 : cellFormat2;
					
					number = new Number(0, j, stt, cformat);
					sheet.addCell(number);
					label = new Label(1, j, rs.getString("NGAY"), cformat3);
					sheet.addCell(label);
					label = new Label(2, j, rs.getString("CHUNGTU"), cformat3);
					sheet.addCell(label);	
					label = new Label(3, j, rs.getString("DIENGIAI"), cformat3);
					sheet.addCell(label);
					psn = rs.getDouble("PSN");
					number = new Number(4, j, psn, inFormat);sheet.addCell(number);
					psc = rs.getDouble("PSC");
					number = new Number(5, j, psc, inFormat);sheet.addCell(number);
					totalpsn+=psn;
					totalpsc+=psc;					
					j++;
			}
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}
			
			sheet.addCell(new Label(3, j, "TỔNG CỘNG", cellFormat));
			number = new Number(4, j, totalpsn, inFormat);sheet.addCell(number);
			
			number = new Number(5, j, totalpsc, inFormat);sheet.addCell(number);
			j++;
			sheet.addCell(new Label(3, j, "DƯ CUỐI KỲ", cellFormat));
			double total=totalpsn-totalpsc;
			
			label = new Label(4, j, "0", cformat1);
			sheet.addCell(label);
			
			label = new Label(5, j, "0", cformat1);
			sheet.addCell(label);
			
			total= totaldn + total;
			
			if(total>=0)
			{
				number = new Number(4, j, total, inFormat);sheet.addCell(number);
			}
			else
			{
				total = total*(-1);
				number = new Number(5, j, total, inFormat);sheet.addCell(number);
			}
			
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
	
	private void ToExcelTheoKH(HttpServletResponse response, IBCChiTietCongNo obj, String query, String tuNgay, String denNgay, String KhachHangid, String userTen, String nppId, String nppID, String queryddk) throws IOException
	{
		System.out.println("dsasga: "+ userTen);
		
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
			//NumberFormat formatter = new DecimalFormat("#,###,###");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietCongNoKH_distributor.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 10;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);
			
			
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			
			sheet = w.createSheet("ChiTietCongNoKH", k);//ten sheet
			
			int f=1;
			if(nppId!=null){			
				sheet.addCell(new Label(0, f, userTen));								
				sheet.mergeCells(0, f, 2, f);
				
				ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+nppId+"'");
				String diachinpp="";
				if(dc!=null)
				{
					while (dc.next())
						diachinpp=dc.getString("DiaChi");
				}
				
				f++;
				sheet.addCell(new Label(0, f, diachinpp, cellFormatTD));
				f=f+2;
			}
			
			else {
				
				
				ResultSet dc = db.get("select Ten, DiaChi from NHAPHANPHOI where PK_SEQ='"+nppID+"'");
				String diachinpp="";
				String tennpp="";
				if(dc!=null)
				{
					while (dc.next()){
						diachinpp=dc.getString("DiaChi");
						tennpp=dc.getString("Ten");
					}
				}
				sheet.addCell(new Label(0, f, tennpp));								
				sheet.mergeCells(0, f, 2, f);
				
				f++;
				sheet.addCell(new Label(0, f, diachinpp, cellFormatTD));
				f=f+2;
			}
			
			sheet.addCell(new Label(0, f, "BÁO CÁO CHI TIẾT CÔNG NỢ KHÁCH HÀNG ", celltieude));			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, f, 6, f);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			f=f+2;
			if(KhachHangid.length()>0)
				{	
					ResultSet kh = db.get("select Ten, DiaChi from KHACHHANG where PK_SEQ='"+KhachHangid+"'");
					
					String diachikh="";
					String tenkh="";
					if(kh!=null)
					{
						while (kh.next()){
							diachikh=kh.getString("DiaChi");
							tenkh=kh.getString("Ten");
						}
					}
					
					sheet.addCell(new Label(1,f, "Khách hàng: "+tenkh));
					sheet.mergeCells(1, f, 6, f);
				}
			else
			{
				sheet.addCell(new Label(1,f, "Khách hàng: tất cả các khách hàng"));
			}
			
			f++;
			sheet.addCell(new Label(1, f, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(2, f, tuNgay)); // lấy ngày đã chọn
			
			f++;
			sheet.addCell(new Label(1, f, "Đến ngày: "));
			sheet.addCell(new Label(2, f, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			f=f+2;
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
			
			WritableCellFormat cellF = new WritableCellFormat(cellFont);
			cellF.setAlignment(Alignment.RIGHT);	
			
			//Tiêu đề
			sheet.addCell(new Label(3, f-1, "DƯ ĐẦU KỲ"));
			sheet.addCell(new Label(0, f, "STT", cellFormat));
			sheet.addCell(new Label(1, f, "Ngày", cellFormat));
			sheet.addCell(new Label(2, f, "Chứng từ số", cellFormat));
			sheet.addCell(new Label(3, f, "Diễn giải", cellFormat));
			sheet.addCell(new Label(4, f, "PSN", cellFormat));
			sheet.addCell(new Label(5, f, "PSC", cellFormat));
			
			//sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);


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
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat inFormat2 = new WritableCellFormat(dp3);
			Label label;
			Number number;
			
			
			//Lấy dữ liệu dư nợ khách hàng
			ResultSet rsdn = db.get(queryddk);
			double totaldndk=0;
			double totaldcdk=0;
			double dndk=0;
			double dcdk=0;
			double totaldn=0;			
			
			if(rsdn!=null)
			{
				while (rsdn.next())
				{
					dndk = rsdn.getDouble("dunodauky");
					dcdk=rsdn.getDouble("ducodauky");
					totaldndk+=dndk;
					totaldcdk+=dcdk;
				}
			}
			totaldn=totaldndk-totaldcdk;
			
			if(totaldn>0)
			{
				number = new Number(4, f-1,totaldn, inFormat2);sheet.addCell(number);
			}
			if(totaldn<0)
			{
				number = new Number(5, f-1,totaldn*(-1), inFormat2);sheet.addCell(number);
			}
			if(totaldn ==0)
			{
				label = new Label(4, f-1, "0", cellF);sheet.addCell(label);
			}			
			
			//Lấy dữ liệu
			ResultSet rs = db.get(query);
			int stt = 0;
			
			double totalpsn=0;
			double totalpsc=0;
			double psn=0;
			double psc=0;			
			
			if(rs!=null)
			{
				f++;
				j=f;
				while (rs.next())
				{
					stt++;				
					String type = "0";
					cformat = type.equals("1") ? cellFormat3 : cellFormat2;
					
					number = new Number(0, j, stt, cformat);
					sheet.addCell(number);
					label = new Label(1, j, rs.getString("NGAY"), cformat3);
					sheet.addCell(label);
					label = new Label(2, j, rs.getString("CHUNGTU"), cformat3);
					sheet.addCell(label);	
					label = new Label(3, j, rs.getString("DIENGIAI"), cformat3);
					sheet.addCell(label);
					psn = rs.getDouble("PSN");
					number = new Number(4, j, psn, inFormat);sheet.addCell(number);
					psc = rs.getDouble("PSC");
					number = new Number(5, j, psc, inFormat);sheet.addCell(number);
					totalpsn+=psn;
					totalpsc+=psc;					
					j++;
			}
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}
			
			sheet.addCell(new Label(3, j, "TỔNG CỘNG", cellFormat));
			number = new Number(4, j, totalpsn, inFormat);sheet.addCell(number);
			
			number = new Number(5, j, totalpsc, inFormat);sheet.addCell(number);
			j++;
			sheet.addCell(new Label(3, j, "DƯ CUỐI KỲ", cellFormat));
			double total=totalpsn-totalpsc;
			
			label = new Label(4, j, "0", cformat1);
			sheet.addCell(label);
			
			label = new Label(5, j, "0", cformat1);
			sheet.addCell(label);
			
			total= totaldn + total;
			
			if(total>=0)
			{
				number = new Number(4, j, total, inFormat);sheet.addCell(number);
			}
			else
			{
				total = total*(-1);
				number = new Number(5, j, total, inFormat);sheet.addCell(number);
			}
			
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
	
	
}
