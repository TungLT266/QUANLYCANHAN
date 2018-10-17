package geso.traphaco.distributor.servlets.hoadontaichinh;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCTongHopSDCongNo;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.BCTongHopSDCongNo;
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


public class BCTongHopSDCongNoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCTongHopSDCongNoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCTongHopSDCongNo obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();	    	   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String userTen = (String) session.getAttribute("userTen");
	    
	    obj = new BCTongHopSDCongNo();
	    
	    session.setAttribute("userTen", userTen);
	    obj.setUserId(userId);	
	    
		String nppId= util.getIdNhapp(userId);		
		
		String view = request.getParameter("view");
	    System.out.println("TT "+view);
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
				String	nextJSP = "/TraphacoHYERP/pages/Distributor/BCTongHopSDCongNo.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String	nextJSP = "/TraphacoHYERP/pages/Center/BCTongHopSDCongNo.jsp";
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
	    IBCTongHopSDCongNo obj = new BCTongHopSDCongNo();	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String nppId= util.getIdNhapp(userId);
	    obj.setUserId(userId);
	    
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
	    
	    String Vung = util.antiSQLInspection(request.getParameter("vungId"));
	    if(Vung==null)
        	Vung="";
        obj.setvungId(Vung);
        
        String khuvuc = util.antiSQLInspection(request.getParameter("khuvucId"));
        if(khuvuc==null)
        	khuvuc="";
        obj.setkhuvucId(khuvuc);
        
        String ttId = util.antiSQLInspection(request.getParameter("ttId"));
        if(ttId==null)
        	ttId="";
        obj.setTtId(ttId);
        
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setKhId(khId);
	    
    	
	    String ddkdId = request.getParameter("ddkdId");
	    if(ddkdId == null)
	    	ddkdId = "";
	    obj.setDdkdId(ddkdId);

	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    obj.settype(type);
	    
	    String doitacId = request.getParameter("doitacId");
	    if(doitacId == null)
	    	doitacId = "";
	    obj.setDoiTacId(doitacId);
	    	    
	    String nvgnId = request.getParameter("nvgnId");
	    if(nvgnId == null)
	    	nvgnId = "";
	    obj.setNvgnId(nvgnId);

	    String view = request.getParameter("view");
        if(view == null)
  	    	view = "NPP";
        if(view.equals("TT"))
		{
			 nppId = util.antiSQLInspection(request.getParameter("nppId"));
			 System.out.println("nppId1"+nppId);
			if (nppId == null)
					nppId = "";
				obj.setNppId(nppId);
		}else
		{
			nppId=util.getIdNhapp(userId);
			System.out.println("nppId2:"+ nppId);
			obj.setNppId(nppId);
		}
	   
        
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
				String	nextJSP = "/TraphacoHYERP/pages/Distributor/BCTongHopSDCongNo.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String	nextJSP = "/TraphacoHYERP/pages/Center/BCTongHopSDCongNo.jsp";
				response.sendRedirect(nextJSP);
			}
	    }
	}

	private void ToExcel(HttpServletResponse response, IBCTongHopSDCongNo obj, String query, String tuNgay, String denNgay, String KhachHangid, String userTen, String nppId) throws IOException
	{
		System.out.println("dsasga: "+ userTen);
		
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
		
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
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			
			sheet = w.createSheet("ChiTietCongNoKH", k);//ten sheet
			
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
			
			sheet.addCell(new Label(0, 4, "BẢNG TỔNG HỢP SỐ DƯ CÔNG NỢ CUỐI KỲ", celltieude));			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 4, 5, 4);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			
			if(KhachHangid.length()>0)
				{
					sheet.addCell(new Label(1,5, "Tài khoản: 13116 - Phải thu của khách hàng "+KhachHangid));
					sheet.mergeCells(1, 5, 5, 5);
				}
			else
			{
				sheet.addCell(new Label(1,5, "Tài khoản: 13116 - Phải thu của khách hàng"));
				sheet.mergeCells(1, 5, 5, 5);
			}
			
			f++;
			sheet.addCell(new Label(1, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(2, 6, tuNgay)); // lấy ngày đã chọn
			
			f++;
			sheet.addCell(new Label(1, 7, "Đến ngày: "));
			sheet.addCell(new Label(2, 7, denNgay)); // lấy ngày đã chọn
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
						
			//Tiêu đề
			sheet.addCell(new Label(0, 9, "STT", cellFormat));
			sheet.addCell(new Label(1, 9, "Mã Fast", cellFormat));
			sheet.addCell(new Label(2, 9, "Tên khách hàng", cellFormat));
			sheet.addCell(new Label(3, 9, "Số dư nợ", cellFormat));
			sheet.addCell(new Label(4, 9, "Số dư có", cellFormat));
			
			//sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 50);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
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
			
			Label label;
			Number number;
			
			//Lấy dữ liệu
			ResultSet rs = db.get(query);
			int stt = 0;
			
			double totalpsn=0;
			double totalpsc=0;
			double dunodauky=0;
			double ducodauky=0;
			double phatsinhno=0;
			double phatsinhco=0;
			
			double dunocuoiky=0;
			double ducocuoiky=0;			
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		
			
			if(rs!=null)
			{
				j=10;
				while (rs.next())
				{
					stt++;				
					String type = "0";
					cformat = type.equals("1") ? cellFormat3 : cellFormat2;
					
					/*number = new Number(0, j, stt, cformat);
					sheet.addCell(number);
					label = new Label(1, j, rs.getString("MaFast"), cformat3);
					sheet.addCell(label);
					label = new Label(2, j, rs.getString("Ten"), cformat3);
					sheet.addCell(label);*/
					dunodauky = rs.getDouble("dunodauky");
					ducodauky = rs.getDouble("ducodauky");
					phatsinhno = rs.getDouble("phatsinhno");
					phatsinhco = rs.getDouble("phatsinhco");
					dunocuoiky= dunodauky+phatsinhno-phatsinhco-ducodauky;
					
					number =new Number(3, j,0, inFormat);sheet.addCell(number);
					number =new Number(4, j,0, inFormat);sheet.addCell(number);
					
					if(dunocuoiky>0)
					{
						number = new Number(0, j, stt, cformat);
						sheet.addCell(number);
						label = new Label(1, j, rs.getString("MaFast"), cformat3);
						sheet.addCell(label);
						label = new Label(2, j, rs.getString("Ten"), cformat3);
						sheet.addCell(label);
						totalpsn += dunocuoiky;
						number = new Number(3, j,dunocuoiky, inFormat);sheet.addCell(number);	
						j++;
					}
					if(dunocuoiky<0)
					{
						number = new Number(0, j, stt, cformat);
						sheet.addCell(number);
						label = new Label(1, j, rs.getString("MaFast"), cformat3);
						sheet.addCell(label);
						label = new Label(2, j, rs.getString("Ten"), cformat3);
						sheet.addCell(label);
						dunocuoiky=dunocuoiky*(-1);
						totalpsc += dunocuoiky;
						number = new Number(4, j,dunocuoiky, inFormat);sheet.addCell(number);
						j++;
					}					
					
				}
				
				label = new Label(0, j, "Tổng cộng", cformat3);sheet.addCell(label);
				cformat3.setFont(cellTitle);
				sheet.mergeCells(0, j, 2, j);
				inFormat.setFont(cellTitle);

				number = new Number(3, j,totalpsn, inFormat);sheet.addCell(number);
				number = new Number(4, j,totalpsc, inFormat);sheet.addCell(number);
				
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}
			
		
			
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
	
	private void ToExcelDoiTac(HttpServletResponse response, IBCTongHopSDCongNo obj, String query, String tuNgay, String denNgay, String KhachHangid, String userTen, String nppId) throws IOException
	{
		System.out.println("dsasga: "+ userTen);
		
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
		
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
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			
			sheet = w.createSheet("ChiTietCongNoKH", k);//ten sheet
			
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
			
			sheet.addCell(new Label(0, 4, "BẢNG TỔNG HỢP SỐ DƯ CÔNG NỢ CUỐI KỲ", celltieude));			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 4, 5, 4);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			
			if(KhachHangid.length()>0)
				{
					sheet.addCell(new Label(1,5, "Tài khoản: 13116 - Phải thu của khách hàng "+KhachHangid));
					sheet.mergeCells(1, 5, 5, 5);
				}
			else
			{
				sheet.addCell(new Label(1,5, "Tài khoản: 13116 - Phải thu của khách hàng"));
				sheet.mergeCells(1, 5, 5, 5);
			}
			
			f++;
			sheet.addCell(new Label(1, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(2, 6, tuNgay)); // lấy ngày đã chọn
			
			f++;
			sheet.addCell(new Label(1, 7, "Đến ngày: "));
			sheet.addCell(new Label(2, 7, denNgay)); // lấy ngày đã chọn
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
						
			//Tiêu đề
			sheet.addCell(new Label(0, 9, "STT", cellFormat));
			sheet.addCell(new Label(1, 9, "Mã Fast", cellFormat));
			sheet.addCell(new Label(2, 9, "Tên khách hàng", cellFormat));
			sheet.addCell(new Label(3, 9, "Số dư nợ", cellFormat));
			sheet.addCell(new Label(4, 9, "Số dư có", cellFormat));
			
			//sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 50);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
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
			
			Label label;
			Number number;
			
			//Lấy dữ liệu
			ResultSet rs = db.get(query);
			int stt = 0;
			
			double totalpsn=0;
			double totalpsc=0;
			double dunodauky=0;
			double ducodauky=0;
			double phatsinhno=0;
			double phatsinhco=0;
			
			double dunocuoiky=0;
			double ducocuoiky=0;			
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		
			
			if(rs!=null)
			{
				j=10;
				while (rs.next())
				{
					stt++;				
					String type = "0";
					cformat = type.equals("1") ? cellFormat3 : cellFormat2;
					
					/*number = new Number(0, j, stt, cformat);
					sheet.addCell(number);
					label = new Label(1, j, rs.getString("MaFast"), cformat3);
					sheet.addCell(label);
					label = new Label(2, j, rs.getString("Ten"), cformat3);
					sheet.addCell(label);*/
					dunodauky = rs.getDouble("dunodauky");
					ducodauky = rs.getDouble("ducodauky");
					phatsinhno = rs.getDouble("phatsinhno");
					phatsinhco = rs.getDouble("phatsinhco");
					dunocuoiky= dunodauky+phatsinhno-phatsinhco-ducodauky;
					
					number =new Number(3, j,0, inFormat);sheet.addCell(number);
					number =new Number(4, j,0, inFormat);sheet.addCell(number);
					
					if(dunocuoiky>0)
					{
						number = new Number(0, j, stt, cformat);
						sheet.addCell(number);
						label = new Label(1, j, rs.getString("MaFast"), cformat3);
						sheet.addCell(label);
						label = new Label(2, j, rs.getString("Ten"), cformat3);
						sheet.addCell(label);
						totalpsn += dunocuoiky;
						number = new Number(3, j,dunocuoiky, inFormat);sheet.addCell(number);	
						j++;
					}
					if(dunocuoiky<0)
					{
						number = new Number(0, j, stt, cformat);
						sheet.addCell(number);
						label = new Label(1, j, rs.getString("MaFast"), cformat3);
						sheet.addCell(label);
						label = new Label(2, j, rs.getString("Ten"), cformat3);
						sheet.addCell(label);
						dunocuoiky=dunocuoiky*(-1);
						totalpsc += dunocuoiky;
						number = new Number(4, j,dunocuoiky, inFormat);sheet.addCell(number);
						j++;
					}					
					
				}
				
				label = new Label(0, j, "Tổng cộng", cformat3);sheet.addCell(label);
				cformat3.setFont(cellTitle);
				sheet.mergeCells(0, j, 2, j);
				inFormat.setFont(cellTitle);

				number = new Number(3, j,totalpsn, inFormat);sheet.addCell(number);
				number = new Number(4, j,totalpsc, inFormat);sheet.addCell(number);
				
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}
			
		
			
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
