package geso.traphaco.distributor.servlets.reports;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.reports.IBKTienThuTrongNgay;
import geso.traphaco.distributor.beans.reports.imp.BKTienThuTrongNgay;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.text.NumberFormat;
import java.text.DecimalFormat;;

/**
 * Servlet implementation class BKTienThuTrongNgaySvl
 */

public class BKTienThuTrongNgaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utility util=new Utility();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BKTienThuTrongNgaySvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		
    	session.setAttribute("loi", "");
    	
    	IBKTienThuTrongNgay bc = new BKTienThuTrongNgay();
    	
    	bc.setUserId(userId);
     	bc.createRs();
     	
    	session.setAttribute("bc",bc);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BKTienThuTrongNgay.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        
		IBKTienThuTrongNgay bctt = new BKTienThuTrongNgay();
		
        String userId = (String) session.getAttribute("userId");
        bctt.setUserId(userId);
        
        String user = (String) session.getAttribute("userId");
        bctt.setUserId(userId);
        
        String ngayKS = bctt.getNgayKS();
        
        
        String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));
        if(tuNgay== null) tuNgay="";
        bctt.setTuNgay(tuNgay);
        
        String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
        if(denNgay== null) denNgay="";
        bctt.setDenNgay(denNgay);
        
        String _scheme_nvbh = "";
		String _scheme_nvgn = "";
		String _scheme_kh = "";
		String _scheme_pnt = "";
        
        String[] nvbhIds = request.getParameterValues("nvbhId");
		if (nvbhIds != null)
		{
		
			for(int i = 0; i < nvbhIds.length; i++)
			{
				_scheme_nvbh += nvbhIds[i] + ",";
			}
			
			if(_scheme_nvbh.trim().length() > 0)
			{
				_scheme_nvbh = _scheme_nvbh.substring(0, _scheme_nvbh.length() - 1);
			}
		}
		
        String[] nvgnIds = request.getParameterValues("nvgnId");
		if (nvgnIds != null)
		{

			for(int i = 0; i < nvgnIds.length; i++)
			{
				_scheme_nvgn += nvgnIds[i] + ",";
			}
			
			if(_scheme_nvgn.trim().length() > 0)
			{
				_scheme_nvgn = _scheme_nvgn.substring(0, _scheme_nvgn.length() - 1);
			}
		}
		
		  String[] khIds = request.getParameterValues("khId");
			if (khIds != null)
			{

				for(int i = 0; i < khIds.length; i++)
				{
					_scheme_kh += khIds[i] + ",";
				}
				
				if(_scheme_kh.trim().length() > 0)
				{
					_scheme_kh = _scheme_kh.substring(0, _scheme_kh.length() - 1);
				}
			}
			
			 String[] phieunoptienIds = request.getParameterValues("phieunoptienId");
				if (phieunoptienIds != null)
				{

					for(int i = 0; i < phieunoptienIds.length; i++)
					{
						_scheme_pnt += phieunoptienIds[i] + ",";
					}
					
					if(_scheme_pnt.trim().length() > 0)
					{
						_scheme_pnt = _scheme_pnt.substring(0, _scheme_pnt.length() - 1);
					}
				}
        
				bctt.setNvbhIds(_scheme_nvbh);
				bctt.setNvgnIds(_scheme_nvgn);
				bctt.setKhIds(_scheme_kh);
				bctt.setPhieuNopTienIds(_scheme_pnt);
        
        
        String action = request.getParameter("action");
        
       
        	OutputStream out = response.getOutputStream(); 
			String userTen = (String)session.getAttribute("userTen");
			bctt.init();
	    	response.setContentType("application/xlsm");
	        response.setHeader("Content-Disposition", "attachment; filename=BKTienThuTrongNgay.xlsm");

			CreatePivotTable(out,response,request, bctt , userTen, ngayKS);
			bctt.DBclose();
       
		
	}

	private void CreatePivotTable(OutputStream out,HttpServletResponse response, HttpServletRequest request, IBKTienThuTrongNgay bctt,
		 String userTen, String ngayKS) throws IOException 
	{
		FileInputStream fstream = null;		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BangKeThuTienTheoNgay.xlsm");
		Workbook workbook = new Workbook();		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook,  bctt, userTen, ngayKS);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, bctt);

	     workbook.save(out);
	     
	}

	private void CreateStaticData(Workbook workbook, IBKTienThuTrongNgay obj) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

	    Font font2= new Font();
	    Style style;
	    
	    font2.setSize(10);// size chu
	    
	    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	    
    	ResultSet rs = obj.getBKTienThuTrongNgay();
    	
    	if(rs != null)
    	{
    		try {
    			tieude(workbook, "7");
    			cells.setColumnWidth(0, 23.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 40.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 20.0f);			
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 20.0f);
				int i = 8;
				Cell cell = null;
				double tongKenh = 0;
				double tong = 0;
				double tongsodu=0;
    			while (rs.next()) {
    				
					
    				String so = rs.getString("ID");
    				String[] ngay = rs.getString("NGAYCT").split("-");
    				String nguoiNop = rs.getString("NGUOINOPTIEN");
    				if(nguoiNop.trim().length() > 0)
    				{
    					String ktcuoicung = nguoiNop.substring(nguoiNop.length() - 1, nguoiNop.length()) ;
    					if(ktcuoicung.equals(","))  nguoiNop = nguoiNop.substring(0, nguoiNop.length() - 1);
    				}
    				
    				String hinhthuctt = rs.getString("HINHTHUCTT");
    				if(hinhthuctt.trim().length() > 0)
    				{
    					String ktcuoicung = hinhthuctt.substring(hinhthuctt.length() - 1, hinhthuctt.length()) ;
    					if(ktcuoicung.equals(","))  hinhthuctt = hinhthuctt.substring(0, hinhthuctt.length() - 1);
    				}
    				
    				
    					
    				double soTien=0;
    				
    				int isthutt = rs.getInt("ISTHUTT");
    			
    				try{
    				soTien = rs.getDouble("SOTIENNOP");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double sotienthu=0;
    				try{
    					sotienthu= rs.getDouble("SOTIENTT");
    				}catch(Exception er){
    					
    				}
    				
    				double sodu=0;
    				try{
    					sodu = rs.getDouble("SOTIENDU");
    				}catch(Exception er){
    					
    				}

    				
					 cell = cells.getCell("A" + Integer.toString(i)); 
					 cell.setValue(so) ;	
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.LEFT);
					 style.setFont(font2);					
					 CreateBorderSetting(workbook,"A" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 cell = cells.getCell("B" + Integer.toString(i));
					 cell.setValue(ngay[2]+ '/' + ngay[1] + '/' + ngay[0]);
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.CENTER);
					 style.setFont(font2);
					 cell.setStyle(style);
					 CreateBorderSetting(workbook,"B" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 cell = cells.getCell("C" + Integer.toString(i)); 
					 cell.setValue(nguoiNop);
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.LEFT);
					 style.setFont(font2);
					 CreateBorderSetting(workbook,"C" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 
					 cell = cells.getCell("D" + Integer.toString(i));
					 cell.setValue(formatter.format(soTien) );
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.RIGHT);
					 CreateBorderSetting(workbook,"D" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 
					 cell = cells.getCell("E" + Integer.toString(i));
					 cell.setValue(formatter.format(sotienthu) );	
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.RIGHT);
					 CreateBorderSetting(workbook,"E" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 
					 cell = cells.getCell("F" + Integer.toString(i));
					 cell.setValue(formatter.format(sodu) );	
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.RIGHT);
					 CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 cell = cells.getCell("G" + Integer.toString(i)); 
					 cell.setValue(hinhthuctt);		
					 style = cell.getStyle();
					 style.setHAlignment(TextAlignmentType.LEFT);
					 cell.setStyle(style);
					 CreateBorderSetting(workbook,"G" + Integer.toString(i));
					 if(isthutt == 1)
					 {
						style.setColor(Color.SILVER);
					 }
					 cell.setStyle(style);
					 
					 
					 tongKenh += soTien;
					 tong += sotienthu;
					 tongsodu+=sodu;
					 
					 i++;
				}
    			rs.close();
    			Font f = new Font();
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Tổng số tiền thu trong kỳ ");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 Style style1 = cell.getStyle();
				 f.setBold(true);
				 style1.setFont(f);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"C" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(formatter.format(tongKenh) );
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"G" + Integer.toString(i));
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 //tongKenh = 0;
				 i++;
    			
    			
    			f.setColor(Color.GREEN);
 
    			
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Tổng cộng");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 style1.setHAlignment(TextAlignmentType.LEFT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"C" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(formatter.format( tongKenh) );
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(formatter.format(tong) );
				 CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(formatter.format(tongsodu));
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"G" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style1.setFont(f);
				 style1.setHAlignment(TextAlignmentType.RIGHT);
				 cell.setStyle(style1);
				 
				 
				 i+=3;
				 
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("Kế toán");
				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Thủ quỹ");
				 cell.setStyle(style);
				 //CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("loi dien du lieu1! ");
			}
    	}
		
	}
	
	private void tieude(Workbook workbook,String a) throws IOException{

		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    
	    Font font2= new Font();
	    Style style;
	    
	    font2.setSize(10);// size chu

   	   
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("Số phiếu nộp tiền/ thu tiền"); 	
	    CreateBorderSetting(workbook,"A"+a); getCellStyle(workbook,"A"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	    
	    cell = cells.getCell("B"  + a); cell.setValue("Ngày nộp tiền");		
	    CreateBorderSetting(workbook,"B"+a);	getCellStyle(workbook,"B"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	    cell = cells.getCell("C"  + a); cell.setValue("Họ tên người nộp");			
	    CreateBorderSetting(workbook,"C"+a);	getCellStyle(workbook,"C"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	    cell = cells.getCell("D"  + a); cell.setValue("Số tiền nộp");	
	    CreateBorderSetting(workbook,"D"+a);	getCellStyle(workbook,"D"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	    cell = cells.getCell("E"  + a); cell.setValue("Số tiền đã thanh toán");
	    CreateBorderSetting(workbook,"E"+a);	getCellStyle(workbook,"E"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	    cell = cells.getCell("F"  + a); cell.setValue("Số tiền dư");	
	    CreateBorderSetting(workbook,"F"+a);	getCellStyle(workbook,"F"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	    cell = cells.getCell("G"  + a); cell.setValue("Hình thức thanh toán");		
	    CreateBorderSetting(workbook,"G"+a);	getCellStyle(workbook,"G"+a,Color.BLACK,true,12);
	    ReportAPI.setCellBackground(cell, Color.BLUE, BorderLineType.THIN, false, 0);
	    style = cell.getStyle();
	    style.setFont(font2);
	    cell.setStyle(style);
	    
	}
	
	 public void CreateBorderSetting(Workbook workbook,String fileName) throws IOException
	    {
		 Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
	        Cells cells = worksheet.getCells();
	        Cell cell;
	        Style style;

	        cell = cells.getCell(fileName);
	        style = cell.getStyle();
	        

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
	        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
	        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
	        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

	        cell.setStyle(style);

	       
	    }

	private void CreateStaticHeader(Workbook workbook, IBKTienThuTrongNgay obj, String userTen, String ngayKS) 
	{
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue(userTen);   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    //font2.setColor(Color.RED);//mau chu
	    //font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    font2 = new Font();
	    cell = cells.getCell("C2");
	    cell.setValue("BẢNG KÊ THU TIỀN");
	     		      
	    style = cell.getStyle();
	  
	    
	    //font3.setColor(Color.RED);//mau chu
	    font2.setSize(14);// size chu
	    font2.setBold(true);
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu       
	    cell.setStyle(style);
	    
	    ResultSet rs = obj.getBKTienThuTrongNgay_KT();
	    int isthutt = 0;
	    int demdong_thutt = 0;
	    
	    if(rs != null)
    	{
    		try
    		{
    			while(rs.next())
    			{
    				isthutt = rs.getInt("ISTHUTT");
    				demdong_thutt += isthutt;
    			}
    			rs.close();
    		}   	
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
	    
	    if(!obj.getTuNgay().equals("") || !obj.getDenNgay().equals(""))
	    {
		    font2 = new Font();
		    cell = cells.getCell("A3");
		    cell.setValue("Từ " + obj.getTuNgay() + " đến " + obj.getDenNgay());
	    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		    cell.setStyle(style);
	    }
	    if(demdong_thutt > 0) // Có phiếu thu trực tiếp thì hiện dòng Ghi chú
	    {
	    	font2 = new Font();
		    cell = cells.getCell("A4");
		    cell.setValue("Ghi chú");
	    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		    cell.setStyle(style);
		    
		    cell = cells.getCell("B4");
		    cell.setValue(""); 
	    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setColor(Color.SILVER);
		    cell.setStyle(style);

		    
		    cell = cells.getCell("C4");
		    cell.setValue("Thu tiền trực tiếp"); 
	    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setHAlignment(TextAlignmentType.LEFT);
		    cell.setStyle(style);
	    }
	    
	    try{
	    dbutils db = new dbutils();	    
	    if(obj.getNvbhIds().length() > 0)
	    {
	    	String sql = "select Ten from DAIDIENKINHDOANH where pk_seq in ("+ obj.getNvbhIds() +")";
	    	System.out.println("ddkd : "+sql);	    	
	    	ResultSet rsnvbh = db.get(sql);
	    	String nvbhstr = "";
	    	while(rsnvbh.next())
	    	{
	    		nvbhstr += rsnvbh.getString("ten")+",";
	    	}
	    	rsnvbh.close();
		    cell = cells.getCell("A4");
		    cell.setValue("Trình dược viên : " + nvbhstr.substring(0, nvbhstr.length()-1));
		    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		    cell.setStyle(style);
	    }
	    
	    if(obj.getNvgnIds().length() > 0)
	    {
	    	String sql = "select Ten from NHANVIENGIAONHAN where pk_seq in ("+ obj.getNvgnIds() +")";	    		    
	    	ResultSet rsnvgn = db.get(sql);
	    	String nvgnstr = "";
	    	while(rsnvgn.next())
	    	{
	    		nvgnstr += rsnvgn.getString("ten")+",";
	    	}
	    	rsnvgn.close();
	    	
		    cell = cells.getCell("A5");
		    cell.setValue("Nhân viên giao nhận : " + nvgnstr.substring(0, nvgnstr.length()-1));
		    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		    cell.setStyle(style);
	    }
	    
	    if(obj.getKhIds().length() > 0)
	    {
	    	String sql = "select Ten from KHACHHANG where pk_seq in ("+ obj.getKhIds() +")";	    		    
	    	ResultSet rsKH = db.get(sql);
	    	String KHstr = "";
	    	while(rsKH.next())
	    	{
	    		KHstr += rsKH.getString("ten")+",";
	    	}
	    	rsKH.close();
	    	
		    cell = cells.getCell("A6");
		    cell.setValue("Khách hàng : " + KHstr.substring(0, KHstr.length()-1));
		    		    
		    style = cell.getStyle();
		    style.setFont(font2);
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		    cell.setStyle(style);
	    }
	    }catch(Exception ex){}    	
	    worksheet.setName("bkTienThuTrongNgay");
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
		 style.setHAlignment(TextAlignmentType.CENTER);
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}

}
