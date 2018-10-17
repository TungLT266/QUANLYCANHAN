package geso.traphaco.distributor.servlets.hoadontaichinh;


import geso.traphaco.center.beans.khachhang.imp.Khachhang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCBangCanDoiPhatSinhCongNo;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.BCBangCanDoiPhatSinhCongNo;
import geso.traphaco.center.util.Utility;




import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
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
import jxl.write.NumberFormat;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class BCBangCanDoiPhatSinhCongNoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCBangCanDoiPhatSinhCongNoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCBangCanDoiPhatSinhCongNo obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();	    	   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);   
	    String userTen = (String) session.getAttribute("userTen");
	    String nppId= util.getIdNhapp(userId);
	    
	    obj = new BCBangCanDoiPhatSinhCongNo();
	    obj.setUserId(userId);	
	   
	    String view = request.getParameter("view");
	    System.out.println("view "+view);
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
	   	 
	    obj.createRs();
	    session.setAttribute("obj", obj);
	   
   	   	
		if(!view.equals("TT"))
		{
			String	nextJSP = "/TraphacoHYERP/pages/Distributor/BCBangCanDoiPhatSinhCongNo.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String	nextJSP = "/TraphacoHYERP/pages/Center/BCBangCanDoiPhatSinhCongNo.jsp";
			response.sendRedirect(nextJSP);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    
	    Utility util = new Utility();
	    IBCBangCanDoiPhatSinhCongNo obj = new BCBangCanDoiPhatSinhCongNo();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    String nppId= util.getIdNhapp(userId);
	    
	    String userTen = (String)session.getAttribute("userTen");     
	    System.out.println("userTen : "+ userTen );
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
		    
	    String tungay = request.getParameter("tuNgay");
	    if(tungay == null)
	    	tungay = ""; 
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denNgay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setKhId(khId);
	    	    
	    String ddkdId = request.getParameter("ddkdId");
	    if(ddkdId == null)
	    	ddkdId = "";
	    obj.setDdkdId(ddkdId);
	    
	    String Vung = util.antiSQLInspection(request.getParameter("vungId"));
	    if(Vung==null)
        	Vung="";
        obj.setvungId(Vung);
        
        String khuvuc = util.antiSQLInspection(request.getParameter("khuvucId"));
        if(khuvuc==null)
        	khuvuc="";
        obj.setkhuvucId(khuvuc);
	    
	    String nvgnId = request.getParameter("nvgnId");
	    if(nvgnId == null)
	    	nvgnId = "";
	    obj.setNvgnId(nvgnId);
	   	   
	    String ttId = util.antiSQLInspection(request.getParameter("ttId"));
        if(ttId==null)
        	ttId="";
        obj.setTtId(ttId);
        
        String doitacId = util.antiSQLInspection(request.getParameter("doitacId"));        
        if(doitacId==null)
        	doitacId="";
        obj.setDoiTacId(doitacId);
                
        String type = util.antiSQLInspection(request.getParameter("type"));
        if(type==null)
        	type="";
        obj.settype(type);
                
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
        System.out.println(action);
	    if (action.equals("excel") )
	    {
	    	String queryBC = "";
	    	if(obj.gettype().equals("0")){
	    		queryBC = obj.getBCTheoKH();
	    		ToExcel(response, obj, queryBC, tungay, denngay, khId, userTen, nppId);
	    	}
	    	if(obj.gettype().equals("1")){
	    		queryBC = obj.getBCTheoDoiTac();
	    		ToExcelDoiTac(response, obj, queryBC, tungay, denngay, doitacId, userTen, nppId);
	    	}
	    }
	    else
	    {
	    	obj.createRs();
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	if(!view.equals("TT"))
			{
				String	nextJSP = "/TraphacoHYERP/pages/Distributor/BCBangCanDoiPhatSinhCongNo.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String	nextJSP = "/TraphacoHYERP/pages/Center/BCBangCanDoiPhatSinhCongNo.jsp";
				response.sendRedirect(nextJSP);
			}
	    }
	}

	
	private void ToExcel(HttpServletResponse response, IBCBangCanDoiPhatSinhCongNo obj, String query, String tuNgay, String denNgay, String KhachHangid, String userTen, String nppId) throws IOException
	{
		System.out.println("dsasga: "+ userTen);
		
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BangCanDoiPhatSinhCongNo.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 11;

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
			
			
			
			sheet = w.createSheet("Sheet1", k);//ten sheet
			
			int f=1;
			if(nppId!=null){
				if(nppId.trim().length()>0)
				{
					sheet.addCell(new Label(0, 1, userTen));								
					sheet.mergeCells(0, 1, 2, 1);
					
					ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+nppId+"'");
					String diachinpp="";
					if(dc!=null)
					{
						while (dc.next())
							diachinpp=dc.getString("DiaChi");
					}
					
					sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
				}
			}
			/*if(nppId==null){
				if(nppID.trim().length()>0)
				{
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
					sheet.addCell(new Label(0, 1, userTen));								
					sheet.mergeCells(0, 1, 2, 1);
					sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
				}
			}*/
				
			sheet.addCell(new Label(0, 4, "BẢNG CÂN ĐỐI PHÁT SINH CÔNG NỢ ", celltieude));			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 4, 7, 4);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			
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
					
					sheet.addCell(new Label(1,5, "Khách hàng: "+tenkh));
					sheet.mergeCells(1, 5, 6, 5);
				}
			else
			{
				sheet.addCell(new Label(1,5, "Khách hàng: tất cả các khách hàng"));
			}
			
			f++;
			sheet.addCell(new Label(1, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(2, 6, tuNgay)); // lấy ngày đã chọn
			
			f++;
			sheet.addCell(new Label(1, 7, "Đến ngày: "));
			sheet.addCell(new Label(2, 7, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			f=f+3;
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
						
			//Tiêu đề
			sheet.addCell(new Label(0, 9, "STT", cellFormat));
			sheet.mergeCells(0, 9, 0, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(1, 9, "NVGN", cellFormat));
			sheet.mergeCells(1, 9, 1, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(2, 9, "TDV", cellFormat));
			sheet.mergeCells(2, 9, 2, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(3, 9, "Loại KH", cellFormat));
			sheet.mergeCells(3, 9, 3, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger		
						
			sheet.addCell(new Label(4, 9, "Mã KH", cellFormat));
			sheet.mergeCells(4, 9, 4, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(5, 9, "Tên KH", cellFormat));
			sheet.mergeCells(5, 9, 5, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(6, 9, "Địa chỉ", cellFormat));
			sheet.mergeCells(6, 9, 6, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(7, 9, "Số dư đầu kỳ", cellFormat));
			sheet.mergeCells(7, 9, 8, 9);			
			sheet.addCell(new Label(7, 10, "Dư nợ đầu kỳ", cellFormat));			
			sheet.addCell(new Label(8, 10, "Dư có đầu kỳ", cellFormat));
			
			sheet.addCell(new Label(9, 9, "Phát sinh nợ", cellFormat));
			sheet.mergeCells(9, 9, 9, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(10, 9, "Phát sinh có", cellFormat));
			sheet.mergeCells(10, 9, 12, 9);			
			sheet.addCell(new Label(10, 10, "Bù trừ ck tháng", cellFormat));		
			sheet.addCell(new Label(11, 10, "Số tiền tt", cellFormat));
			sheet.addCell(new Label(12, 10, "Tổng PS có", cellFormat));
			
			sheet.addCell(new Label(13, 9, "Số dư cuối kỳ", cellFormat));
			sheet.mergeCells(13, 9, 14, 9);			
			sheet.addCell(new Label(13, 10, "Dư nợ cuối kỳ", cellFormat));			
			sheet.addCell(new Label(14, 10, "Dư có cuối kỳ", cellFormat));
			
			//sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 35);
			sheet.setColumnView(6, 40);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			sheet.setColumnView(9, 20);
			sheet.setColumnView(10, 20);
			sheet.setColumnView(11, 20);
			sheet.setColumnView(12, 20);
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
			
			//Lấy dữ liệu
			ResultSet rs = db.get(query);
			int stt = 0;
			
			double dunodauky=0;
			double ducodauky=0;
			double phatsinhno=0;
			
			double ckthang=0;
			double phatsinhco=0;
			
			double dunocuoiky=0;
			double ducocuoiky=0;
			
			double sono=0;
			
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
		
			String maFast="";
			String LoaiKH="";
			double total_dunodauky=0;
			double total_ducodauky=0;
			double total_phatsinhno=0;
			double total_phatsinhco=0;
			
			double total_psc=0;
			
			double total_dunocuoiky=0;
			double total_ducocuoiky=0;
			if(rs!=null)
			{
				while (rs.next())
				{
					stt++;				
					String type = "0";
					cformat = type.equals("1") ? cellFormat3 : cellFormat2;
					number = new Number(0, j, stt, cformat);
					sheet.addCell(number);
					
					label = new Label(1, j, rs.getString("NVGN"), cformat3);
					sheet.addCell(label);
					
					label = new Label(2, j, rs.getString("DDKD"), cformat3);
					sheet.addCell(label);
					
					LoaiKH=rs.getString("LoaiKH");
					label = new Label(3, j, LoaiKH, cformat3); sheet.addCell(label);
					
					maFast=rs.getString("MaFast");
					label = new Label(4, j, maFast, cformat3); sheet.addCell(label);
					
					label = new Label(5, j, rs.getString("TEN"), cformat3);
					sheet.addCell(label);
					
					label = new Label(6, j, rs.getString("diachi"), cformat3);
					sheet.addCell(label);
					
					dunodauky = rs.getDouble("dunodauky");					
					ducodauky = rs.getDouble("ducodauky");
					
					if(dunodauky-ducodauky >=0)
					{
						number = new Number(7, j, dunodauky-ducodauky, inFormat);sheet.addCell(number);
						label = new Label(8, j, "", cformat3);sheet.addCell(label);
						total_dunodauky+= (dunodauky-ducodauky);
					}
					
					if(dunodauky - ducodauky <0)
					{						
						label = new Label(7, j, "", cformat3);sheet.addCell(label);
						number = new Number(8, j,ducodauky-dunodauky, inFormat);	sheet.addCell(number);
						total_ducodauky+=(ducodauky-dunodauky);
					}
					
					sheet.addCell(new Label(10, 10, "Bù trừ ck tháng", cellFormat));		
					sheet.addCell(new Label(11, 10, "Số tiền tt", cellFormat));
					sheet.addCell(new Label(12, 10, "Tổng PS có", cellFormat));
					
					phatsinhno = rs.getDouble("phatsinhno");number = new Number(9, j, phatsinhno, inFormat);sheet.addCell(number);
					
					ckthang = rs.getDouble("ckthang");number = new Number(10, j,ckthang, inFormat);sheet.addCell(number);
					
					phatsinhco = rs.getDouble("phatsinhco");number = new Number(11, j,phatsinhco, inFormat);sheet.addCell(number);	//so tien tt
					
					total_psc = ckthang+phatsinhco;	number = new Number(12, j,total_psc, inFormat);sheet.addCell(number);	// tong so tien ps c
					
					dunocuoiky= dunodauky+phatsinhno-total_psc-ducodauky;
					
					number =new Number(13, j,0, inFormat);sheet.addCell(number);
					number =new Number(14, j,0, inFormat);sheet.addCell(number);
					total_phatsinhno += phatsinhno;
					total_phatsinhco += total_psc;
					
					if(dunocuoiky>=0)
					{
						number =new Number(13, j,dunocuoiky, inFormat);sheet.addCell(number);
						total_dunocuoiky += dunocuoiky;
					}
					else
					{
						dunocuoiky=dunocuoiky*(-1);
						total_ducocuoiky += dunocuoiky;
						number =new Number(14, j,dunocuoiky, inFormat);sheet.addCell(number);
					}
					j++;
			}
			}
			else{throw new Exception("KHONG CO DU LIEU TRONG BAO CAO NAY...");}
			
			
			label = new Label(0, j, "Tổng cộng", cformat3);sheet.addCell(label);
			cformat3.setFont(cellTitle);
			sheet.mergeCells(0, j, 5, j);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger

			
			inFormat.setFont(cellTitle);
					
			number = new Number(7, j, total_dunodauky, inFormat);sheet.addCell(number);
			number = new Number(8, j,total_ducodauky, inFormat);	sheet.addCell(number);
			number = new Number(9, j, total_phatsinhno, inFormat);sheet.addCell(number);
			number = new Number(12, j,total_phatsinhco, inFormat);sheet.addCell(number);
			
			
			number =new Number(13, j,total_dunocuoiky, inFormat);sheet.addCell(number);
			number =new Number(14, j,total_ducocuoiky, inFormat);sheet.addCell(number);
			
			
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
	
	private void ToExcelDoiTac(HttpServletResponse response, IBCBangCanDoiPhatSinhCongNo obj, String query, String tuNgay, String denNgay, String doitacId, String userTen, String nppId) throws IOException
	{
		System.out.println("dsasga: "+ userTen);
		
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BangCanDoiPhatSinhCongNo.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 11;

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
			
			
			
			sheet = w.createSheet("Sheet1", k);//ten sheet
			
			int f=1;
			if(nppId!=null){
				if(nppId.trim().length()>0)
				{
					sheet.addCell(new Label(0, 1, userTen));								
					sheet.mergeCells(0, 1, 2, 1);
					
					ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+nppId+"'");
					String diachinpp="";
					if(dc!=null)
					{
						while (dc.next())
							diachinpp=dc.getString("DiaChi");
					}
					
					sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
				}
			}
			/*if(nppId==null){
				if(nppID.trim().length()>0)
				{
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
					sheet.addCell(new Label(0, 1, userTen));								
					sheet.mergeCells(0, 1, 2, 1);
					sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
				}
			}*/
				
			sheet.addCell(new Label(0, 4, "BẢNG CÂN ĐỐI PHÁT SINH CÔNG NỢ ", celltieude));			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 4, 7, 4);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			
			if(doitacId.length()>0)
				{
					ResultSet kh = db.get("select Ten, DiaChi from NHAPHANPHOI where PK_SEQ='"+doitacId+"'");
					
					String diachikh="";
					String tenkh="";
					if(kh!=null)
					{
						while (kh.next()){
							diachikh=kh.getString("DiaChi");
							tenkh=kh.getString("Ten");
						}
					}
					
					sheet.addCell(new Label(1,5, "Khách hàng: "+tenkh));
					sheet.mergeCells(1, 5, 6, 5);
				}
			else
			{
				sheet.addCell(new Label(1,5, "Khách hàng: tất cả các khách hàng"));
			}
			
			f++;
			sheet.addCell(new Label(1, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(2, 6, tuNgay)); // lấy ngày đã chọn
			
			f++;
			sheet.addCell(new Label(1, 7, "Đến ngày: "));
			sheet.addCell(new Label(2, 7, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			f=f+3;
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
						
			//Tiêu đề
			sheet.addCell(new Label(0, 9, "STT", cellFormat));
			sheet.mergeCells(0, 9, 0, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(1, 9, "Mã Đối tác", cellFormat));
			sheet.mergeCells(1, 9, 1, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(2, 9, "Tên đối tác", cellFormat));
			sheet.mergeCells(2, 9, 2, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(3, 9, "Địa chỉ", cellFormat));
			sheet.mergeCells(3, 9, 3, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger		
						
/*			sheet.addCell(new Label(4, 9, "Mã KH", cellFormat));
			sheet.mergeCells(4, 9, 4, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(5, 9, "Tên KH", cellFormat));
			sheet.mergeCells(5, 9, 5, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(6, 9, "Địa chỉ", cellFormat));
			sheet.mergeCells(6, 9, 6, 10);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
*/			
			sheet.addCell(new Label(4, 9, "Số dư đầu kỳ", cellFormat));
			sheet.mergeCells(4, 9, 5, 9);	
			
			sheet.addCell(new Label(4, 10, "Dư nợ đầu kỳ", cellFormat));			
			sheet.addCell(new Label(5, 10, "Dư có đầu kỳ", cellFormat));
			
			sheet.addCell(new Label(6, 9, "Phát sinh trong kỳ", cellFormat));
			sheet.mergeCells(6, 9, 7, 9);// bắt đầu từ cột thứ 0, dòng thứ mấy , cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(6, 10, "PS Nợ", cellFormat));			
			sheet.addCell(new Label(7, 10, "PS Có", cellFormat));
						
			sheet.addCell(new Label(8, 9, "Số dư cuối kỳ", cellFormat));
			sheet.mergeCells(8, 9, 9, 9);			
			sheet.addCell(new Label(8, 10, "Dư nợ cuối kỳ", cellFormat));			
			sheet.addCell(new Label(9, 10, "Dư có cuối kỳ", cellFormat));
			
			//sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 35);
			sheet.setColumnView(6, 40);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			sheet.setColumnView(9, 20);
			sheet.setColumnView(10, 20);
			sheet.setColumnView(11, 20);
			sheet.setColumnView(12, 20);
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
			
			//Lấy dữ liệu
			ResultSet rs = db.get(query);
			int stt = 0;
			
			double dunodauky=0;
			double ducodauky=0;
			double phatsinhno=0;
			
			double ckthang=0;
			double phatsinhco=0;
			
			double dunocuoiky=0;
			double ducocuoiky=0;
			
			double sono=0;
			
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
		
			String maFast="";
			String LoaiKH="";
			double total_dunodauky=0;
			double total_ducodauky=0;
			double total_phatsinhno=0;
			double total_phatsinhco=0;
			
			double total_psc=0;
			
			double total_dunocuoiky=0;
			double total_ducocuoiky=0;
			if(rs!=null)
			{
				while (rs.next())
				{
					stt++;				
					String type = "0";
					cformat = type.equals("1") ? cellFormat3 : cellFormat2;
					number = new Number(0, j, stt, cformat);
					sheet.addCell(number);
					
					label = new Label(1, j, rs.getString("MAFAST"), cformat3);
					sheet.addCell(label);
					
					label = new Label(2, j, rs.getString("TEN"), cformat3);
					sheet.addCell(label);
					
					LoaiKH=rs.getString("diachi");
					label = new Label(3, j, LoaiKH, cformat3); sheet.addCell(label);
					/*
					maFast=rs.getString("MaFast");
					label = new Label(4, j, maFast, cformat3); sheet.addCell(label);
					
					label = new Label(5, j, rs.getString("TEN"), cformat3);
					sheet.addCell(label);
					
					label = new Label(6, j, rs.getString("diachi"), cformat3);
					sheet.addCell(label);*/
					
					dunodauky = rs.getDouble("dunodauky");					
					ducodauky = rs.getDouble("ducodauky");
					
					if(dunodauky-ducodauky >=0)
					{
						number = new Number(4, j, dunodauky-ducodauky, inFormat);sheet.addCell(number);
						label = new Label(5, j, "", cformat3);sheet.addCell(label);
						total_dunodauky+= (dunodauky-ducodauky);
					}
					
					if(dunodauky - ducodauky <0)
					{						
						label = new Label(4, j, "", cformat3);sheet.addCell(label);
						number = new Number(5, j,ducodauky-dunodauky, inFormat);	sheet.addCell(number);
						total_ducodauky+=(ducodauky-dunodauky);
					}
					
					/*sheet.addCell(new Label(6, 10, "Bù trừ ck tháng", cellFormat));		
					sheet.addCell(new Label(7, 10, "Số tiền tt", cellFormat));
					sheet.addCell(new Label(8, 10, "Tổng PS có", cellFormat));*/
					
					phatsinhno = rs.getDouble("phatsinhno");number = new Number(6, j, phatsinhno, inFormat);sheet.addCell(number);
					
					//ckthang = rs.getDouble("ckthang");number = new Number(10, j,ckthang, inFormat);sheet.addCell(number);
					
					phatsinhco = rs.getDouble("phatsinhco");number = new Number(7, j,phatsinhco, inFormat);sheet.addCell(number);	//so tien tt
					
					//total_psc = ckthang+phatsinhco;	number = new Number(12, j,total_psc, inFormat);sheet.addCell(number);	// tong so tien ps c
					
					dunocuoiky= dunodauky+phatsinhno-phatsinhco-ducodauky;
					
					number =new Number(8, j,0, inFormat);sheet.addCell(number);
					number =new Number(9, j,0, inFormat);sheet.addCell(number);
					
					total_phatsinhno += phatsinhno;
					total_phatsinhco += phatsinhco;
					
					if(dunocuoiky>=0)
					{
						number =new Number(8, j,dunocuoiky, inFormat);sheet.addCell(number);
						total_dunocuoiky += dunocuoiky;
					}
					else
					{
						dunocuoiky=dunocuoiky*(-1);
						total_ducocuoiky += dunocuoiky;
						number =new Number(9, j,dunocuoiky, inFormat);sheet.addCell(number);
					}
					j++;
			}
			}
			else{throw new Exception("KHONG CO DU LIEU TRONG BAO CAO NAY...");}
			
			
			label = new Label(0, j, "Tổng cộng", cformat3);sheet.addCell(label);
			cformat3.setFont(cellTitle);
			sheet.mergeCells(0, j, 3, j);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger

			
			inFormat.setFont(cellTitle);
					
			number = new Number(4, j, total_dunodauky, inFormat);sheet.addCell(number);
			number = new Number(5, j,total_ducodauky, inFormat);	sheet.addCell(number);
			number = new Number(6, j, total_phatsinhno, inFormat);sheet.addCell(number);
			number = new Number(7, j,total_phatsinhco, inFormat);sheet.addCell(number);
			
			
			number =new Number(8, j,total_dunocuoiky, inFormat);sheet.addCell(number);
			number =new Number(9, j,total_ducocuoiky, inFormat);sheet.addCell(number);
			
			
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

